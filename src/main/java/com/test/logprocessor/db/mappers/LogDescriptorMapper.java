package com.test.logprocessor.db.mappers;

import com.test.logprocessor.model.domain.LogDescriptor;
import com.test.logprocessor.model.json.LogDescriptorDto;
import com.test.logprocessor.model.json.types.LogType;

public class LogDescriptorMapper {
    public static LogDescriptor fromDto(LogDescriptorDto logDescriptorDto){
        LogDescriptor logDescriptor = new LogDescriptor();
        logDescriptor.setHost(logDescriptorDto.getHost());
        if (logDescriptorDto.getType() != null) {
            logDescriptor.setType(logDescriptorDto.getType().toString());
        }
        return logDescriptor;
    }

    public static LogDescriptorDto toDto(LogDescriptor logDescriptor){
        LogDescriptorDto logDescriptorDto = new LogDescriptorDto();
        logDescriptorDto.setHost(logDescriptor.getHost());
        if (logDescriptor.getType() != null) {
            logDescriptorDto.setType(LogType.valueOf(logDescriptor.getType()));
        }
        return logDescriptorDto;
    }
}
