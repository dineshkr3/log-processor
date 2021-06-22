package com.test.logprocessor.model.json;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.logprocessor.model.json.types.LogType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogDescriptorDto {

    @JsonProperty("type")
    private LogType type;

    @JsonProperty("host")
    private String host;
}
