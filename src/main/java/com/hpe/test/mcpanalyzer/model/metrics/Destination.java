package com.hpe.test.mcpanalyzer.model.metrics;

import java.math.BigInteger;

public class Destination {
	private String destinationNumber;
	private BigInteger numberOfCalls;
	
	public String getDestinationNumber() {
		return destinationNumber;
	}
	public void setDestinationNumber(String destinationNumber) {
		this.destinationNumber = destinationNumber;
	}
	public BigInteger getNumberOfCalls() {
		return numberOfCalls;
	}
	public void setNumberOfCalls(BigInteger numberOfCalls) {
		this.numberOfCalls = numberOfCalls;
	}
	public Destination(String destinationNumber, BigInteger numberOfCalls) {
		this.destinationNumber = destinationNumber;
		this.numberOfCalls = numberOfCalls;
	}
}