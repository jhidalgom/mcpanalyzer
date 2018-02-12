package com.hpe.test.mcpanalyzer.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hpe.test.mcpanalyzer.model.processor.ProcessedFile;

@Repository
public interface ProcessedFileRepository extends CrudRepository<ProcessedFile, Long>{
	
	@Transactional
	ProcessedFile findOneByFileDate(String fileDate);

}
