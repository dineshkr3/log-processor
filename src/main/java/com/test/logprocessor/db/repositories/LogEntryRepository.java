package com.test.logprocessor.db.repositories;

import com.test.logprocessor.model.domain.LogEntry;
import org.springframework.data.repository.CrudRepository;

public interface LogEntryRepository extends CrudRepository<LogEntry, String> {
    LogEntry findLogEntryById(String id);

}
