package com.hpe.test.mcpanalyzer.model.processor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hpe.test.mcpanalyzer.model.message.Message;

@Entity
@Table(name = "processed_file")
public class ProcessedFile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true)
	private String fileDate;
	private int rowsWithFieldErrors;
	private int rowsWithMissingFields;
	private BigInteger processDuration;
	
	@OneToMany(mappedBy="processedFile", cascade = CascadeType.ALL, targetEntity=Message.class)
	private List<Message> messageList;
	
	public ProcessedFile() {
		this.rowsWithFieldErrors = 0;
		this.rowsWithMissingFields = 0;
		this.messageList = new ArrayList<>();
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
	public List<Message> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}
	public void addMessageToList(Message message) {
		this.messageList.add(message);
	}

}
