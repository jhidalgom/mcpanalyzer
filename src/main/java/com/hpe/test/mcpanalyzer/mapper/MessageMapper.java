package com.hpe.test.mcpanalyzer.mapper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpe.test.mcpanalyzer.model.message.Call;
import com.hpe.test.mcpanalyzer.model.message.Message;
import com.hpe.test.mcpanalyzer.model.message.Text;
import com.hpe.test.mcpanalyzer.model.processor.ProcessLineResult;

public class MessageMapper {
	
	private static final String[] EXPECTED_CALL_FIELD_ORDER = {"message_type", "timestamp", "origin", "destination", "duration", "status_code", "status_description"};
	private static final String[] EXPECTED_MSG_FIELD_ORDER = {"message_type", "timestamp", "origin", "destination", "message_content", "message_status"};
	
	public static ProcessLineResult processJsonLine (String json, Message.Type type) {
		if(type.equals(Message.Type.CALL))
			return retrieveCallFromJson(json);
		else if (type.equals(Message.Type.MESSAGE))
			return retrieveTextFromJson(json);
		else
			return null;
		
	}
	
	private static ProcessLineResult retrieveCallFromJson(String json) {
		
		Call resultObject = null;
		ProcessLineResult procLineResult = null;
		try {
			//Checking for missing fields
			Map<String,Object> jsonMap = new ObjectMapper().readValue(json, new TypeReference<Map<String,Object>>(){});	
			procLineResult = checkFieldIntegrityAndOrder(jsonMap, EXPECTED_CALL_FIELD_ORDER);
				
			resultObject = new Call();
			setCommonValues(jsonMap,resultObject);
			
	        if(StringUtils.isEmpty(jsonMap.get("duration"))) {
	        	resultObject.setDuration(null);
	        } else {
	        	resultObject.setDuration(new BigInteger(String.valueOf(jsonMap.get("duration"))));
	        }
	        resultObject.setStatusCode(Call.Status.getStatusFromString(String.valueOf(jsonMap.get("status_code"))));
	        resultObject.setStatusDescription(String.valueOf(jsonMap.get("status_description")));

	        if(!validateCallValues(resultObject))
	        	return new ProcessLineResult(null, false, true);
			
			procLineResult.setResultObject(resultObject);
		} catch (IOException e) {
			return new ProcessLineResult(null, false, true);
		}
		
		return procLineResult;
	}
	
	private static ProcessLineResult retrieveTextFromJson(String json) {
		
		Text resultObject = null;
		ProcessLineResult procLineResult = null;
		try {
			//Checking for missing fields
			Map<String,Object> jsonMap = new ObjectMapper().readValue(json, new TypeReference<Map<String,Object>>(){});	
			procLineResult = checkFieldIntegrityAndOrder(jsonMap, EXPECTED_MSG_FIELD_ORDER);
				
			resultObject = new Text();
			setCommonValues(jsonMap,resultObject);
	        
	        resultObject.setMessageContent(String.valueOf(jsonMap.get("message_content")));
	        resultObject.setMessageStatus(Text.Status.getStatusFromString(String.valueOf(jsonMap.get("message_status"))));
	        
	        if(!validateTextValues(resultObject))
	        	return new ProcessLineResult(null, false, true);
	        
			procLineResult.setResultObject(resultObject);
		} catch (IOException e) {
			return new ProcessLineResult(null, false, true);
		}
		
		return procLineResult;
	}
	
	private static ProcessLineResult checkFieldIntegrityAndOrder(Map<String,Object> jsonMap, String[] EXPECTED_FIELD_ORDER) {
		if(!jsonMap.keySet().containsAll(Arrays.asList(EXPECTED_FIELD_ORDER))) {
			return new ProcessLineResult(null, true, true);
		}
		
		//Checking for order and extra fields
		Iterator<String> it = jsonMap.keySet().iterator();
        for (int i = 0; i < EXPECTED_FIELD_ORDER.length; i++) {
        	String key = it.next();
        	if(!EXPECTED_FIELD_ORDER[i].equals(key)) {
        		return new ProcessLineResult(null, false, true);
        	}
        }
        
        if(it.hasNext()) {
        	//Extra fields
        	return new ProcessLineResult(null, false, true);
        }
        
        return new ProcessLineResult(null, false, false);
	}
	
	private static void setCommonValues(Map<String,Object> jsonMap, Message resultObject) {
		resultObject.setType(Message.Type.getTypeFromString(String.valueOf(jsonMap.get("message_type"))));
        if(StringUtils.isEmpty(jsonMap.get("timestamp"))) {
        	resultObject.setTimestamp(null);
        } else {
        	resultObject.setTimestamp(new BigInteger(String.valueOf(jsonMap.get("timestamp"))));
        }
        resultObject.setOrigin(String.valueOf(jsonMap.get("origin")));
        resultObject.setDestination(String.valueOf(jsonMap.get("destination")));
	}
	
	private static boolean validateCallValues(Call call) {
		if(!validateCommonFields(call))
			return false;
		if(call.getDuration() == null)
			return false;
		if(call.getStatusCode() == null)
			return false;
		if(call.getStatusDescription() == null)
			return false;
		
		return true;
	}
	
	private static boolean validateTextValues(Text text) {
		if(!validateCommonFields(text))
			return false;
		if(text.getMessageStatus() == null)
			return false;
		
		return true;
	}
	
	private static boolean validateCommonFields(Message message) {
		if(message.getType() == null)
			return false;
		if(message.getTimestamp() == null)
			return false;
		if(!validateMSISDN(message.getOrigin()))
			return false;
		if(!validateMSISDN(message.getDestination()))
			return false;
		
		return true;
	}
	
	private static boolean validateMSISDN(String msisdn) {
		if(StringUtils.isEmpty(msisdn) || msisdn.length() > 15)
			return false;
		
		return true;
	}
}
