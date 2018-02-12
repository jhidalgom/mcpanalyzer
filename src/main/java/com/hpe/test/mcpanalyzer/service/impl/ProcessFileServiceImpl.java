package com.hpe.test.mcpanalyzer.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hpe.test.mcpanalyzer.service.ProcessFileService;

@Service("processFileService")
public class ProcessFileServiceImpl implements ProcessFileService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${JSON_FILE_URL}")
	private String jsonFileUrl;

	@Override
	public boolean processFile(String requestedDate) throws FileNotFoundException {
		
		String httpRequestUrl = jsonFileUrl.replace("YYYYMMDD", requestedDate);
		Scanner s = null;
		
		try {
			URL url = new URL(httpRequestUrl);
			s = new Scanner(url.openStream());
			while(s.hasNext()) {
				String line = s.nextLine();
				log.debug("Line read: << {} >>", line);
			}
		} catch(IOException ex) {
			log.info("The requested file ({}) was not found on the server or it is inaccessible", requestedDate);
			throw new FileNotFoundException();
		} finally {
			try { s.close(); } catch (Exception e) {}
		}
		
		return true;
	}

}
