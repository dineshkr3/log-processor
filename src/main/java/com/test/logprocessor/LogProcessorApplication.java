package com.test.logprocessor;

import com.test.logprocessor.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LogProcessorApplication  implements CommandLineRunner {

	private FileReaderService fileReaderService;

	@Autowired
	public LogProcessorApplication(FileReaderService fileReaderService){
		this.fileReaderService = fileReaderService;
	}



	public static void main(String[] args) {
		SpringApplication.run(LogProcessorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Executing Log Processor Command Line Tool");
		log.info("Provided Logfile: {}", args);
		if ( args.length > 0 ){
			fileReaderService.readFile(args[0]);

		}else {
			log.error("Please provide valid input file!");
		}
	}
}
