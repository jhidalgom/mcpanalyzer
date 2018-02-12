package com.hpe.test.mcpanalyzer.controller;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hpe.test.mcpanalyzer.model.kpi.KpiResult;
import com.hpe.test.mcpanalyzer.service.KpiService;

@RestController
public class KpiController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KpiService kpiService;
	
	@RequestMapping(value="/kpis", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KpiResult> getKpis() {
		KpiResult result = null;
		
		try {
			result = kpiService.getKpis();
		} catch (FileNotFoundException fnf) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("KPIs generated successfully");
		return new ResponseEntity<KpiResult>(result,HttpStatus.OK);
	};
	
}
