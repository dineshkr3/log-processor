package com.test.logprocessor.service;

import com.test.logprocessor.model.json.LogEntryDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class FileReaderServiceTest {

    @Mock
    private LogTransformerService logTransformerService;

    private FileReaderService objectToBeTested ;
    @BeforeEach
    void setUp() {
        objectToBeTested = new FileReaderService(logTransformerService);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readFile() throws URISyntaxException {
        doNothing().when(logTransformerService).transformLogEntry(any(LogEntryDto.class));
        Path filePath = Paths.get(ClassLoader.getSystemResource("logfile.txt").toURI());
        objectToBeTested.readFile(filePath.toAbsolutePath().toString());
        verify(logTransformerService, times(6)).transformLogEntry(any());
    }

    @Test
    void readFile_invalid_json_should_skip_entry() throws URISyntaxException {
        doNothing().when(logTransformerService).transformLogEntry(any(LogEntryDto.class));
        Path filePath = Paths.get(ClassLoader.getSystemResource("logfile_error.txt").toURI());
        objectToBeTested.readFile(filePath.toAbsolutePath().toString());
        verify(logTransformerService, times(5)).transformLogEntry(any());
    }

    @Test
    void readFile_invalid_file_path() throws URISyntaxException {
        objectToBeTested.readFile("not_existing_file.txt");
        verify(logTransformerService, times(0)).transformLogEntry(any());
    }
}