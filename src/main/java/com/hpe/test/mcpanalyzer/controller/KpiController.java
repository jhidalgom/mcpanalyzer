package com.hpe.test.mcpanalyzer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hpe.test.mcpanalyzer.model.kpi.KpiResult;

@RestController
public class KpiController {
	
	@RequestMapping(value="/kpis", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KpiResult> getKpis() {
		
		return new ResponseEntity<KpiResult>(new KpiResult(),HttpStatus.OK);
	};
	
}
