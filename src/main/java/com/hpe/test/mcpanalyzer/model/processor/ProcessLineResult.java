package com.hpe.test.mcpanalyzer.model.processor;

import com.hpe.test.mcpanalyzer.model.message.Message;

public class ProcessLineResult {
	
	private boolean hasMissingFields;
	private boolean hasFieldErrors;
	private Message resultObject;
	
	public ProcessLineResult(Message resultObject, boolean hasMissingFields, boolean hasFieldErrors) {
		this.resultObject = resultObject;
		this.hasMissingFields = hasMissingFields;
		this.hasFieldErrors = hasFieldErrors;
	}

	public boolean hasMissingFields() {
		return hasMissingFields;
	}

	public boolean hasFieldErrors() {
		return hasFieldErrors;
	}

	public Message getResultObject() {
		return resultObject;
	}
	
	public void setResultObject(Message resultObject) {
		this.resultObject = resultObject;
	}
	
}
