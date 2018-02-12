package com.hpe.test.mcpanalyzer.controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hpe.test.mcpanalyzer.service.ProcessFileService;

@RestController
public class AnalyzerController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProcessFileService processFileService;
	
	@RequestMapping(value="/{date}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AnalyzerResponse> processFile(@PathVariable("date") String requestedDate) {
		
		boolean success = false;
		
		if(!isValidDate(requestedDate)) {
			log.info("The requestedDate ({}) had an incorrect format", requestedDate);
			return new ResponseEntity<AnalyzerResponse>(new AnalyzerResponse("KO","Incorrect date format, must be YYYYMMDD"), HttpStatus.BAD_REQUEST);
		}
		
		try {
			log.info("Attempting to process file ({})", requestedDate);
			success = processFileService.processFile(requestedDate);
		} catch (FileNotFoundException fnf) {
			return new ResponseEntity<AnalyzerResponse>(new AnalyzerResponse("KO","File not found"),HttpStatus.NOT_FOUND);
		}
		
		if(!success) {
			return new ResponseEntity<AnalyzerResponse>(new AnalyzerResponse("KO","That file was already processed"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("File ({}) processed successfully", requestedDate);
		return new ResponseEntity<AnalyzerResponse>(new AnalyzerResponse("OK","File processed successfully"), HttpStatus.OK);
	};
	
	private static boolean isValidDate(String value) {
		LocalDate date = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			date = LocalDate.parse(value, formatter);
		} catch (DateTimeParseException dtpe) {
			
		}
		
		return date != null;
    }
	
	public class AnalyzerResponse {
		private String responseCode;
		private String description;
		
		public AnalyzerResponse(String responseCode, String description) {
			this.responseCode = responseCode;
			this.description = description;
		}

		public String getResponseCode() {
			return responseCode;
		}

		public void setResponseCode(String responseCode) {
			this.responseCode = responseCode;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
	
}
