package com.test.logprocessor.service;

import com.test.logprocessor.db.mappers.LogEntryMapper;
import com.test.logprocessor.db.repositories.LogEntryRepository;
import com.test.logprocessor.model.cache.CacheLogEntry;
import com.test.logprocessor.model.domain.LogEntry;
import com.test.logprocessor.model.json.LogEntryDto;
import com.test.logprocessor.model.json.types.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Responsible for Transforming and processing data for saving in db.
 */
@Slf4j
@Service
public class LogTransformerService {


    private int thresholdAlert;

    private CacheService cacheService;
    private LogEntryRepository logEntryRepository;

    @Autowired
    public LogTransformerService(@Value("${threshold.alert}")int thresholdAlert,  CacheService cacheService, LogEntryRepository logEntryRepository){
        this.thresholdAlert = thresholdAlert;
        this.cacheService = cacheService;
        this.logEntryRepository = logEntryRepository;
    }

    /**
     * This method will transform LogEntryDto to database entry if START and FINISHED event is received.
     * In case of first entry method will will add partial entry to db and cache.
     * @param logEntryDto
     */
    public void transformLogEntry(LogEntryDto logEntryDto){

        CacheLogEntry cacheLogEntry = cacheService.getLogEntryById(logEntryDto.getId());
        if ( cacheLogEntry != null){
            //Make the final entry to db
            long duration = calculateDuration(logEntryDto, cacheLogEntry);
            boolean alert = calculateAlert(duration);
            log.debug("Alert and duration calculated for id: {}, Duration: {}, Alert: {} ", logEntryDto.getId(), duration, alert);
            LogEntry logEntry = LogEntryMapper.fromDto(logEntryDto);
            logEntry.setDuration(duration);
            logEntry.setAlert(alert);
            this.logEntryRepository.save(logEntry);
            flagLongEvent(logEntry);

            cacheService.evictLogEntry(logEntryDto.getId());
        }else{
            //Save partial data to db and cache
            cacheService.save(logEntryDto);
        }
    }
    private long calculateDuration(LogEntryDto logEntryDto, CacheLogEntry cacheLogEntry){
        long startTimestamp = getTimestampByState(State.STARTED, logEntryDto, cacheLogEntry);
        long finishedTimestamp = getTimestampByState(State.FINISHED, logEntryDto, cacheLogEntry);
        return  finishedTimestamp - startTimestamp;
    }
    private long getTimestampByState(State state,LogEntryDto logEntryDto, CacheLogEntry cacheLogEntry){
        return logEntryDto.getState().equals(state) ? logEntryDto.getTimestamp() : cacheLogEntry.getTimestamp();
    }

    private boolean calculateAlert(long duration){
       return  duration > thresholdAlert ? true : false;
    }
    private void flagLongEvent(LogEntry logEntry){
        if (logEntry.isAlert()) {
            log.info("ALERT: ==> ID: {}, took more than {} ms to finish. Time taken in ms: {}", logEntry.getId(), thresholdAlert, logEntry.getDuration());
        }
    }
}
