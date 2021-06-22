package com.test.logprocessor.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@ToString
@Getter
@Setter
@Entity(name = "LOG_ENTRY")
public class LogEntry {
    @Id
    @Column(name = "EVENT_ID")
    private String id;

    @Column(name = "EVENT_DURATION")
    private long duration;

    @Column(name = "ALERT")
    private boolean alert;

    @Embedded
    private LogDescriptor logDescriptor;
}
