package com.hpe.test.mcpanalyzer.service.impl;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpe.test.mcpanalyzer.model.kpi.KpiResult;
import com.hpe.test.mcpanalyzer.model.processor.ProcessedFile;
import com.hpe.test.mcpanalyzer.repository.ProcessedFileRepository;
import com.hpe.test.mcpanalyzer.service.KpiService;

@Service
public class KpiServiceImpl implements KpiService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProcessedFileRepository processedFileRepository;

	@Override
	public KpiResult getKpis() throws FileNotFoundException {
		
		Iterable<ProcessedFile> processedFiles = processedFileRepository.findAll();
		
		if(!processedFiles.iterator().hasNext()) {
			log.info("No data was found.  Process some files first");
			throw new FileNotFoundException();
		}
			
		return new KpiResult();
	}
	
}
