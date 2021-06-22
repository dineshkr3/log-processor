package com.test.logprocessor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.logprocessor.model.json.LogEntryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Handles the large file reading in a streaming fashion.
 */
@Slf4j
@Service
public class FileReaderService {

    private LogTransformerService logTransformerService;

    @Autowired
    public FileReaderService(LogTransformerService logTransformerService){
        this.logTransformerService = logTransformerService;
    }

    /**
     * This method will read file with streaming considering the large file.
     * File is written line by line and sent for further processing.
     * @param filePath
     */
    public void readFile(String filePath){
        ObjectMapper mapper = new ObjectMapper();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(logEntryStr -> {
                try {
                    LogEntryDto logEntryDto = mapper.readValue(logEntryStr, LogEntryDto.class);
                    logTransformerService.transformLogEntry(logEntryDto);
                } catch (JsonProcessingException exception) {
                    log.error("Error occurred while json processing: {}", exception.getMessage());
                }
            });

        } catch (IOException ioException) {
            log.error("Error received during IO:{} ", ioException.getMessage());
        }


    }

}
