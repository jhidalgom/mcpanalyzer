package com.hpe.test.mcpanalyzer.model.metrics;

import java.math.BigDecimal;

public class CallDuration {
	
	private String country;
	private BigDecimal averageDuration;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public BigDecimal getAverageDuration() {
		return averageDuration;
	}
	public void setAverageDuration(BigDecimal duration) {
		this.averageDuration = duration;
	}
	
	public CallDuration (String country, BigDecimal averageDuration) {
		this.country = country;
		this.averageDuration = averageDuration;
	}
	
}
