package com.hpe.test.mcpanalyzer.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpe.test.mcpanalyzer.mapper.MessageMapper;
import com.hpe.test.mcpanalyzer.model.message.Message;
import com.hpe.test.mcpanalyzer.model.processor.ProcessLineResult;
import com.hpe.test.mcpanalyzer.model.processor.ProcessedFile;
import com.hpe.test.mcpanalyzer.repository.ProcessedFileRepository;
import com.hpe.test.mcpanalyzer.service.ProcessFileService;

@Service("processFileService")
public class ProcessFileServiceImpl implements ProcessFileService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${JSON_FILE_URL}")
	private String jsonFileUrl;
	
	@Autowired
	private ProcessedFileRepository processedFileRepository;

	@Override
	public boolean processFile(String requestedDate) throws FileNotFoundException {
		
		ProcessedFile processedFile = new ProcessedFile();
		int rowsWithMissingFields = 0;
		int rowsWithFieldErrors = 0;
		
		String httpRequestUrl = jsonFileUrl.replace("YYYYMMDD", requestedDate);
		Scanner s = null;
		String line = null;
		
		Long startTime = System.currentTimeMillis();
		
		try {
			URL url = new URL(httpRequestUrl);
			s = new Scanner(url.openStream());
			while(s.hasNext()) {
				try {
					line = s.nextLine();
					log.debug("Line read: << {} >>", line);
					Message.Type messageType = new ObjectMapper().readValue(line, Message.Type.class);
					if (messageType == null) {
						log.info("The JSON object did not have a proper message_type << {} >>", line);
						rowsWithFieldErrors++;
					} else {
						ProcessLineResult procLineResult = MessageMapper.processJsonLine(line, messageType);
						if(procLineResult.hasFieldErrors()) {
							log.info("Object mapping failed: field errors exist << {} >>", line);
							rowsWithFieldErrors++;
						}
						if(procLineResult.hasMissingFields()) {
							rowsWithMissingFields++;
						}
						
						if(!procLineResult.hasFieldErrors() && !procLineResult.hasMissingFields()) {
							Message resultObject = procLineResult.getResultObject();
							log.info("Successfully mapped object << {} >>", resultObject);
							resultObject.setProcessedFile(processedFile);
							processedFile.addMessageToList(resultObject);
						}
					}
					
				} catch (IOException e) {
					log.info("The JSON object did not have a proper format << {} >>", line);
					rowsWithFieldErrors++;
				}
			}
			
		} catch(IOException ex) {
			log.info("The requested file ({}) was not found on the server or it is inaccessible", requestedDate);
			throw new FileNotFoundException();
		} finally {
			try { s.close(); } catch (Exception e) {}
		}
		
		Long processTime = System.currentTimeMillis() - startTime;
		
		log.info("File process completed in {} ms: Rows with missing fields: {}  --- Rows with field errors: {}", processTime, rowsWithMissingFields, rowsWithFieldErrors);
		
		processedFile.setFileDate(requestedDate);
		processedFile.setRowsWithFieldErrors(rowsWithFieldErrors);
		processedFile.setRowsWithMissingFields(rowsWithMissingFields);
		processedFile.setProcessDuration(BigInteger.valueOf(processTime));
		
		try {
			processedFileRepository.save(processedFile);
		} catch (DataIntegrityViolationException e) {
			return false;
		}
		
		return true;
	}

}
