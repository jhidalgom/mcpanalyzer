package com.hpe.test.mcpanalyzer.model.message;

import java.math.BigInteger;
import java.util.EnumSet;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hpe.test.mcpanalyzer.mapper.MessageTypeDeserializer;
import com.hpe.test.mcpanalyzer.model.processor.ProcessedFile;

@Entity
@Table(name = "message")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Message {
	
	@JsonDeserialize(using = MessageTypeDeserializer.class)
	public enum Type {
		
		CALL("CALL"),
		MESSAGE("MSG");
		
		private String type;
		
		Type(String type) {
			this.type = type;
		}
		
		public String type() {
			return this.type;
		}
		
		public static Type getTypeFromString(String typeString) {
			for (Message.Type type : EnumSet.allOf(Message.Type.class)) {
	        	if (type.type().equals(typeString))
	        		return type;
	        }
			return null;
		}
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private BigInteger id;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	private BigInteger timestamp;
	private String origin;
	private String destination;
	
	@ManyToOne(targetEntity = ProcessedFile.class)
	@JoinColumn(name = "processed_file_id")
	private ProcessedFile processedFile;
	
	public Message () {
		
	}
	
	public Message (Type type, BigInteger timestamp, String origin, String destination) {
		this.type = type;
		this.timestamp = timestamp;
		this.origin = origin;
		this.destination = destination;
	}
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public BigInteger getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(BigInteger timestamp) {
		this.timestamp = timestamp;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public ProcessedFile getProcessedFile() {
		return processedFile;
	}
	public void setProcessedFile(ProcessedFile processedFile) {
		this.processedFile = processedFile;
	}
	
}
