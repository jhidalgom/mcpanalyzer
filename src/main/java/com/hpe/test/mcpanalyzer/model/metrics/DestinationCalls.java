package com.hpe.test.mcpanalyzer.model.metrics;

import java.util.List;

public class DestinationCalls {
	
	private String originCountry;
	private List<Destination> destinationList;
	
	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public List<Destination> getDestinationList() {
		return destinationList;
	}

	public void setDestinationList(List<Destination> destinationList) {
		this.destinationList = destinationList;
	}

	public DestinationCalls(String originCountry, List<Destination> destinationList) {
		this.originCountry = originCountry;
		this.destinationList = destinationList;
	}
}
