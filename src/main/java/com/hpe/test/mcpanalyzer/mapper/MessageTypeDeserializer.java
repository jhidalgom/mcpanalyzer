package com.hpe.test.mcpanalyzer.mapper;

import java.io.IOException;
import java.util.EnumSet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hpe.test.mcpanalyzer.model.message.Message;

public class MessageTypeDeserializer extends StdDeserializer<Message.Type> { 

	private static final long serialVersionUID = -3788138786559429391L;

	public MessageTypeDeserializer() { 
        this(null); 
    } 
 
    public MessageTypeDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public Message.Type deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode messageNode = jp.getCodec().readTree(jp);
        
        JsonNode typeNode = messageNode.get("message_type");
        if(typeNode == null)
        	return null;
        
        String messageType = typeNode.asText();
        
        for (Message.Type type : EnumSet.allOf(Message.Type.class)) {
        	if (type.type().equals(messageType))
        		return type;
        }
 
        return null;
    }
}
