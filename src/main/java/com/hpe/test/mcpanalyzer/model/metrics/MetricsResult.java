package com.hpe.test.mcpanalyzer.model.metrics;

import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MetricsResult {
	
	private BigInteger rowsMissingFields;
	private BigInteger blankMessages;
	private BigInteger rowsWithFieldErrors;
	private List<DestinationCalls> callDestinationsByCountry;
	private BigDecimal percentageOKCalls;
	private List<CallDuration> callDurationsByCountry;
	private List<WordOccurrence> wordOccurenceRanking;
	
	public BigInteger getRowsMissingFields() {
		return rowsMissingFields;
	}
	public void setRowsMissingFields(BigInteger rowsMissingFields) {
		this.rowsMissingFields = rowsMissingFields;
	}
	public BigInteger getBlankMessages() {
		return blankMessages;
	}
	public void setBlankMessages(BigInteger blankMessages) {
		this.blankMessages = blankMessages;
	}
	public BigInteger getRowsWithFieldErrors() {
		return rowsWithFieldErrors;
	}
	public void setRowsWithFieldErrors(BigInteger rowsWithFieldErrors) {
		this.rowsWithFieldErrors = rowsWithFieldErrors;
	}
	public List<DestinationCalls> getCallDestinationsByCountry() {
		return callDestinationsByCountry;
	}
	public void setCallDestinationsByCountry(List<DestinationCalls> callDestinationsByCountry) {
		this.callDestinationsByCountry = callDestinationsByCountry;
	}
	public BigDecimal getPercentageOKCalls() {
		return percentageOKCalls;
	}
	public void setPercentageOKCalls(BigDecimal percentageOKCalls) {
		this.percentageOKCalls = percentageOKCalls;
	}
	public List<CallDuration> getCallDurationsByCountry() {
		return callDurationsByCountry;
	}
	public void setCallDurationsByCountry(List<CallDuration> callDurationsByCountry) {
		this.callDurationsByCountry = callDurationsByCountry;
	}
	public List<WordOccurrence> getWordOccurenceRanking() {
		return wordOccurenceRanking;
	}
	public void setWordOccurenceRanking(List<WordOccurrence> wordOccurenceRanking) {
		this.wordOccurenceRanking = wordOccurenceRanking;
	}
	
}
