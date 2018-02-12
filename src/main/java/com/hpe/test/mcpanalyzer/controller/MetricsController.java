package com.hpe.test.mcpanalyzer.controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

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

import com.hpe.test.mcpanalyzer.model.metrics.MetricsResult;
import com.hpe.test.mcpanalyzer.service.MetricsService;

@RestController
public class MetricsController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MetricsService metricsService;
	
	@RequestMapping(value= {"/metrics", "/metrics/{date}"}, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MetricsResult> getMetrics(@PathVariable("date") Optional<String> requestedDate) {
		
		MetricsResult result = null;
		
		if(requestedDate.isPresent()) {
			if(!isValidDate(requestedDate.get())) {
				log.info("The requestedDate ({}) had an incorrect format", requestedDate);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		try {
			result = metricsService.getMetrics(requestedDate);
		} catch (FileNotFoundException fnf) {
			log.info("No metrics were found", requestedDate);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("Metrics calculated successfully");
		return new ResponseEntity<MetricsResult>(result,HttpStatus.OK);
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
}
