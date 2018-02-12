package com.hpe.test.mcpanalyzer.model.message;

import java.math.BigInteger;
import java.util.EnumSet;

public class Call extends Message {
	
	public enum Status {
		
		OK("OK"),
		KO("KO");
		
		private String status;
		
		Status(String status) {
			this.status = status;
		}
		
		public String status() {
			return this.status;
		}
		
		public static Status getStatusFromString(String statusString) {
			for (Call.Status status : EnumSet.allOf(Call.Status.class)) {
	        	if (status.status().equals(statusString))
	        		return status;
	        }
			return null;
		}
	}
	
	private BigInteger duration;
	private Status statusCode;
	private String statusDescription;
	
	
	public BigInteger getDuration() {
		return duration;
	}
	public void setDuration(BigInteger duration) {
		this.duration = duration;
	}
	public Status getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Status statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
}
