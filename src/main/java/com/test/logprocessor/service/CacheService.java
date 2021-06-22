package com.test.logprocessor.service;

import com.test.logprocessor.db.mappers.LogEntryMapper;
import com.test.logprocessor.db.repositories.LogEntryRepository;
import com.test.logprocessor.model.cache.CacheLogEntry;
import com.test.logprocessor.model.domain.LogEntry;
import com.test.logprocessor.model.json.LogEntryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Implementation of cache. Saves the Log entry with partial data in DB and retains important
 * descriptor in cache. When 2nd entry received then this descriptor will be used to complete the
 * data entry in DB also to calculate the duration and alert.
 */
@Slf4j
@Service
public class CacheService {

    private LogEntryRepository repository;

    @Autowired
    public CacheService(LogEntryRepository repository){
        this.repository = repository;
    }

    @CachePut(value = "partialLogEntries", key = "#logEntryDto.id")
    public CacheLogEntry save(LogEntryDto logEntryDto){
        LogEntry logEntry = LogEntryMapper.fromDto(logEntryDto);
        this.repository.save(logEntry);

        return CacheLogEntry.builder()
                .id(logEntryDto.getId())
                .timestamp(logEntryDto.getTimestamp())
                .type(logEntryDto.getLogDescriptorDto().getType())
                .build();
    }

    @Cacheable(value = "partialLogEntries", key = "#id",  unless = "#result == null" )
    public CacheLogEntry getLogEntryById(String id){
        return null;
    }

    @CacheEvict(value = "partialLogEntries", key = "#id")
    public void evictLogEntry(String id){
        log.debug("Removing cache entry for id: {}", id);
    }
}
