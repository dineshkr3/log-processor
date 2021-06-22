package com.test.logprocessor.service;

import com.test.logprocessor.db.repositories.LogEntryRepository;
import com.test.logprocessor.model.cache.CacheLogEntry;
import com.test.logprocessor.model.domain.LogEntry;
import com.test.logprocessor.model.json.LogDescriptorDto;
import com.test.logprocessor.model.json.LogEntryDto;
import com.test.logprocessor.model.json.types.LogType;
import com.test.logprocessor.model.json.types.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogTransformerServiceTest {

    private LogTransformerService objectToBeTested;

    @Mock
    private CacheService cacheService;

    @Mock
    private LogEntryRepository repository;

    @BeforeEach
    void setUp() {
        objectToBeTested = new LogTransformerService(4, cacheService, repository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void transformLogEntry_when_first_entry_received() {
        LogEntryDto logEntryDto = buildLogEntryDto("test_id", 1491377495210L, State.STARTED);
        when(cacheService.getLogEntryById("test_id")).thenReturn(null);
        objectToBeTested.transformLogEntry(logEntryDto);
        verify(cacheService, times(1)).save(logEntryDto);
    }

    @Test
    void transformLogEntry_when_second_entry_received() {
        LogEntryDto logEntryDto = buildLogEntryDto("test_id", 1491377495210L, State.STARTED);
        when(cacheService.getLogEntryById("test_id")).thenReturn(buildCacheLogEntry("test_id", 1491377495210L));
        objectToBeTested.transformLogEntry(logEntryDto);
        verify(repository, times(1)).save(any(LogEntry.class));
        verify(cacheService, times(1)).evictLogEntry("test_id");
    }


    private LogEntryDto buildLogEntryDto(String testId, long timestamp, State state){
        LogEntryDto logEntryDto = new LogEntryDto();
        logEntryDto.setId(testId);
        logEntryDto.setState(state);
        logEntryDto.setTimestamp(timestamp);
        LogDescriptorDto  logDescriptorDto = new LogDescriptorDto();

        logDescriptorDto.setType(LogType.APPLICATION_LOG);
        logDescriptorDto.setHost("host");

        logEntryDto.setLogDescriptorDto(logDescriptorDto);

        return logEntryDto;
    }
    private CacheLogEntry buildCacheLogEntry(String testId, long timestamp){
        return CacheLogEntry.builder()
                .type(LogType.APPLICATION_LOG)
                .id(testId)
                .timestamp(timestamp).build();
    }
}