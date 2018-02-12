package com.hpe.test.mcpanalyzer.model.message;

import java.util.EnumSet;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "text")
public class Text extends Message {
	
	public enum Status {
		
		DELIVERED("DELIVERED"),
		SEEN("SEEN");
		
		private String status;
		
		Status(String status) {
			this.status = status;
		}
		
		public String status() {
			return this.status;
		}
		
		public static Status getStatusFromString(String statusString) {
			for (Text.Status status : EnumSet.allOf(Text.Status.class)) {
	        	if (status.status().equals(statusString))
	        		return status;
	        }
			return null;
		}
	}
	
	private String messageContent;
	@Enumerated(EnumType.STRING)
	private Status messageStatus;
	
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public Status getMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(Status messageStatus) {
		this.messageStatus = messageStatus;
	}
	
}
