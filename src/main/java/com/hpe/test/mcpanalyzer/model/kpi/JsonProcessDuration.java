package com.hpe.test.mcpanalyzer.model.kpi;

import java.math.BigInteger;

public class JsonProcessDuration {
	
	private String fileName;
	private BigInteger duration;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public BigInteger getDuration() {
		return duration;
	}
	public void setDuration(BigInteger duration) {
		this.duration = duration;
	}
	public JsonProcessDuration(String fileName, BigInteger duration) {
		this.fileName = fileName;
		this.duration = duration;
	}
	
}
