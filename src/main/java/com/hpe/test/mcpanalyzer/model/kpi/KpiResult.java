package com.hpe.test.mcpanalyzer.model.kpi;

import java.util.List;
import java.math.BigInteger;

public class KpiResult {
	
	private BigInteger processedFiles;
	private BigInteger totalRows;
	private BigInteger totalCalls;
	private BigInteger totalMessages;
	private BigInteger differentOriginCountries;
	private BigInteger differentDestinationCountries;
	private List<JsonProcessDuration> jsonProcessDurations;
	
	public BigInteger getProcessedFiles() {
		return processedFiles;
	}
	public void setProcessedFiles(BigInteger processedFiles) {
		this.processedFiles = processedFiles;
	}
	public BigInteger getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(BigInteger totalRows) {
		this.totalRows = totalRows;
	}
	public BigInteger getTotalCalls() {
		return totalCalls;
	}
	public void setTotalCalls(BigInteger totalCalls) {
		this.totalCalls = totalCalls;
	}
	public BigInteger getTotalMessages() {
		return totalMessages;
	}
	public void setTotalMessages(BigInteger totalMessages) {
		this.totalMessages = totalMessages;
	}
	public BigInteger getDifferentOriginCountries() {
		return differentOriginCountries;
	}
	public void setDifferentOriginCountries(BigInteger differentOriginCountries) {
		this.differentOriginCountries = differentOriginCountries;
	}
	public BigInteger getDifferentDestinationCountries() {
		return differentDestinationCountries;
	}
	public void setDifferentDestinationCountries(BigInteger differentDestinationCountries) {
		this.differentDestinationCountries = differentDestinationCountries;
	}
	public List<JsonProcessDuration> getJsonProcessDurations() {
		return jsonProcessDurations;
	}
	public void setJsonProcessDurations(List<JsonProcessDuration> jsonProcessDurations) {
		this.jsonProcessDurations = jsonProcessDurations;
	}
	
}
