package com.hpe.test.mcpanalyzer.model.processor;

import java.math.BigInteger;

public class ProcessedFile {
	
	private String fileDate;
	private int rowsWithFieldErrors;
	private int rowsWithMissingFields;
	private BigInteger processDuration;
	
	public ProcessedFile() {
		this.rowsWithFieldErrors = 0;
		this.rowsWithMissingFields = 0;
	}
	
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public int getRowsWithFieldErrors() {
		return rowsWithFieldErrors;
	}
	public void setRowsWithFieldErrors(int rowsWithFieldErrors) {
		this.rowsWithFieldErrors = rowsWithFieldErrors;
	}
	public int getRowsWithMissingFields() {
		return rowsWithMissingFields;
	}
	public void setRowsWithMissingFields(int rowsWithMissingFields) {
		this.rowsWithMissingFields = rowsWithMissingFields;
	}
	public BigInteger getProcessDuration() {
		return processDuration;
	}
	public void setProcessDuration(BigInteger processDuration) {
		this.processDuration = processDuration;
	}

}
