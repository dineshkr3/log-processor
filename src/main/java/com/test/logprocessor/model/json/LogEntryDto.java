package com.test.logprocessor.model.json;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.test.logprocessor.model.json.types.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogEntryDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("state")
    private State state;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonUnwrapped
    private LogDescriptorDto logDescriptorDto;
}
