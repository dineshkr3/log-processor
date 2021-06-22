package com.test.logprocessor.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class LogDescriptor {
    private String type;
    private String host;
}
