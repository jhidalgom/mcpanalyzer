package com.hpe.test.mcpanalyzer.model.metrics;

import java.math.BigInteger;

public class WordOccurrence {
	private String word;
	private BigInteger occurrences;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public BigInteger getOccurrences() {
		return occurrences;
	}
	public void setOccurrences(BigInteger occurrences) {
		this.occurrences = occurrences;
	}
	public WordOccurrence(String word, BigInteger occurrences) {
		this.word = word;
		this.occurrences = occurrences;
	}
	
}
