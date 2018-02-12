package com.hpe.test.mcpanalyzer.service.impl;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hpe.test.mcpanalyzer.model.message.Call;
import com.hpe.test.mcpanalyzer.model.message.Message;
import com.hpe.test.mcpanalyzer.model.message.Text;
import com.hpe.test.mcpanalyzer.model.metrics.MetricsResult;
import com.hpe.test.mcpanalyzer.model.processor.ProcessedFile;
import com.hpe.test.mcpanalyzer.repository.ProcessedFileRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MetricsServiceImplTest {
	
	private static final Optional<String> OPT_FILE_DATE = Optional.of("20180101");
	private static final BigInteger DURATION = BigInteger.TEN;
	private static final int ROWS_FIELD_ERRORS = 5;
	private static final int ROWS_MISSING_FIELDS = 1;
	private static final BigInteger TIMESTAMP = BigInteger.valueOf(12345678);
	
	@Mock
	private ProcessedFileRepository processedFileRepositoryMock;
	
	@InjectMocks
	private MetricsServiceImpl systemUnderTest;
	
	@Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shouldReturnMetricsWhenTheFileHasBeenProcessed() throws Exception {
		when(processedFileRepositoryMock.findOneByFileDate(any(String.class))).thenReturn(createMockProcessedFile());
		
		MetricsResult result = systemUnderTest.getMetrics(OPT_FILE_DATE);
		
		Assert.assertNotNull(result);
		Assert.assertEquals(BigInteger.ONE, result.getBlankMessages());
		Assert.assertEquals(BigDecimal.valueOf((new Long(1).floatValue()/3)*100), result.getPercentageOKCalls());
		Assert.assertEquals(BigInteger.valueOf(ROWS_MISSING_FIELDS), result.getRowsMissingFields());
		Assert.assertEquals(BigInteger.valueOf(ROWS_FIELD_ERRORS), result.getRowsWithFieldErrors());
		
	}
	
	@Test(expected=FileNotFoundException.class)
	public void shouldThrowFileNotFoundWhenTheProcessedFileWasntRetrieved() throws Exception {
		when(processedFileRepositoryMock.findOneByFileDate(any(String.class))).thenReturn(null);
		
		systemUnderTest.getMetrics(OPT_FILE_DATE);
	}
	
	private ProcessedFile createMockProcessedFile() {
		ProcessedFile pf = new ProcessedFile();
		
		pf.setFileDate(OPT_FILE_DATE.get());
		pf.setProcessDuration(DURATION);
		pf.setRowsWithFieldErrors(ROWS_FIELD_ERRORS);
		pf.setRowsWithMissingFields(ROWS_MISSING_FIELDS);
		List<Message> messageList = new ArrayList<>();
		
		Message.Type[] types = {Message.Type.CALL,Message.Type.CALL,Message.Type.CALL,Message.Type.MESSAGE,Message.Type.MESSAGE};
		String[] origins = {"44123542","34551546","3487895","5945782","44456788"};
		String[] destinations = {"44567892","44578595","5945684","4456482","44865481"};
		
		for ( int i = 0; i < types.length; i++ ) {
			Message m = null;
			if(types[i] == Message.Type.CALL) {
				m = new Call();
				m.setType(Message.Type.CALL);
				m.setOrigin(origins[i]);
				m.setDestination(destinations[i]);
				m.setTimestamp(TIMESTAMP);
				((Call)m).setDuration(i%2==1?BigInteger.TEN:BigInteger.ONE);
				((Call)m).setStatusCode(i%2==1?Call.Status.OK:Call.Status.KO);
				((Call)m).setStatusDescription("dont matter");
			} else {
				m = new Text();
				m.setType(Message.Type.MESSAGE);
				m.setOrigin(origins[i]);
				m.setDestination(destinations[i]);
				m.setTimestamp(TIMESTAMP);
				((Text)m).setMessageContent(i%2==1?"":"SOMETHING");
				((Text)m).setMessageStatus(i%2==1?Text.Status.DELIVERED:Text.Status.SEEN);
			}
			messageList.add(m);
		}
		
		pf.setMessageList(messageList);
		
		return pf;
	}

}
