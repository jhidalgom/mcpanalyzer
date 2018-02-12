package com.hpe.test.mcpanalyzer.service;

import java.io.FileNotFoundException;

public interface ProcessFileService {
	public boolean processFile(String requestedDate) throws FileNotFoundException;
}
