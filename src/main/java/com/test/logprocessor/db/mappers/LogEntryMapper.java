package com.test.logprocessor.db.mappers;

import com.test.logprocessor.model.domain.LogEntry;
import com.test.logprocessor.model.json.LogEntryDto;

public class LogEntryMapper {
    public static LogEntry fromDto(LogEntryDto logEntryDto){
        LogEntry logEntry = new LogEntry();
        logEntry.setId(logEntryDto.getId());
        if (logEntryDto.getLogDescriptorDto() != null) {
            logEntry.setLogDescriptor(LogDescriptorMapper.fromDto(logEntryDto.getLogDescriptorDto()));
        }
        return logEntry;
    }


}
