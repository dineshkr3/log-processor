package com.test.logprocessor.model.cache;

import com.test.logprocessor.model.json.types.LogType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CacheLogEntry {
    private String id;
    private LogType type;
    private long timestamp;
}
