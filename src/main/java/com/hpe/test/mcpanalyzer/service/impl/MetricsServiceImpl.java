package com.hpe.test.mcpanalyzer.service.impl;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hpe.test.mcpanalyzer.model.message.Call;
import com.hpe.test.mcpanalyzer.model.message.Message;
import com.hpe.test.mcpanalyzer.model.message.Text;
import com.hpe.test.mcpanalyzer.model.metrics.CallDuration;
import com.hpe.test.mcpanalyzer.model.metrics.Destination;
import com.hpe.test.mcpanalyzer.model.metrics.DestinationCalls;
import com.hpe.test.mcpanalyzer.model.metrics.MetricsResult;
import com.hpe.test.mcpanalyzer.model.metrics.WordOccurrence;
import com.hpe.test.mcpanalyzer.model.processor.ProcessedFile;
import com.hpe.test.mcpanalyzer.repository.ProcessedFileRepository;
import com.hpe.test.mcpanalyzer.service.MetricsService;

@Service
public class MetricsServiceImpl implements MetricsService {
	
	@Autowired
	private ProcessedFileRepository processedFileRepository;

	@Override
	public MetricsResult getMetrics(Optional<String> requestedDate) throws FileNotFoundException {
		ProcessedFile processedFile = null;
		if(requestedDate.isPresent()) {
			processedFile = processedFileRepository.findOneByFileDate(requestedDate.get());
		} else {
			Long maxId = processedFileRepository.getMaxId();
			processedFile = processedFileRepository.findOne(maxId);
		}
		
		if (processedFile == null) {
			throw new FileNotFoundException();
		}
		
		return calculateMetrics(processedFile);
	}
	
	private MetricsResult calculateMetrics(ProcessedFile processedFile) {
		MetricsResult result = new MetricsResult();
		
		result.setRowsMissingFields(BigInteger.valueOf(processedFile.getRowsWithMissingFields()));
		result.setRowsWithFieldErrors(BigInteger.valueOf(processedFile.getRowsWithFieldErrors()));
		
		Map<Message.Type, List<Message>> messagesByType = processedFile.getMessageList().stream()
				.collect(Collectors.groupingBy(Message::getType, Collectors.toList()));
		
		long blankMessages = messagesByType.get(Message.Type.MESSAGE).stream()
				.filter(m -> StringUtils.isEmpty(((Text)m).getMessageContent()))
				.count();
		result.setBlankMessages(BigInteger.valueOf(blankMessages));
		
		List<CallDuration> callDurations = messagesByType.get(Message.Type.CALL).stream()
				.collect(Collectors.groupingBy(a -> a.getOrigin().substring(0,2), Collectors.averagingDouble(c -> ((Call)c).getDuration().doubleValue())))
				.entrySet().stream()
				.map( e ->  new CallDuration(e.getKey(), new BigDecimal(e.getValue())))
				.collect(Collectors.toList());
		result.setCallDurationsByCountry(callDurations);
		
		Map<Call.Status, Long> callStatus = messagesByType.get(Message.Type.CALL).stream()
				.collect(Collectors.groupingBy( c -> ((Call)c).getStatusCode(), Collectors.counting()));
		Long okCalls = callStatus.get(Call.Status.OK)==null?0:callStatus.get(Call.Status.OK);
		Long koCalls = callStatus.get(Call.Status.KO)==null?0:callStatus.get(Call.Status.KO);
		result.setPercentageOKCalls(BigDecimal.valueOf((okCalls.floatValue() / (okCalls + koCalls))*100));
		
		List<DestinationCalls> callsByCountry = messagesByType.get(Message.Type.CALL).stream()
				.collect(Collectors.groupingBy(c -> c.getOrigin().substring(0, 2), Collectors.toList()))
				.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream()
						.collect(Collectors.groupingBy(m -> m.getDestination(), Collectors.counting()))
						.entrySet().stream().map(f -> new Destination(f.getKey(), BigInteger.valueOf(f.getValue())))
						.collect(Collectors.toList())))
				.entrySet().stream()
				.map(g -> new DestinationCalls(g.getKey(), g.getValue()))
				.collect(Collectors.toList());
		result.setCallDestinationsByCountry(callsByCountry);
		
		List<WordOccurrence> occurrences = messagesByType.get(Message.Type.MESSAGE).stream()
				.map(m -> Arrays.asList(((Text)m).getMessageContent().split(" ")))
				.flatMap(List::stream)
				.filter(w -> !StringUtils.isEmpty(w))
				.collect(Collectors.groupingBy(s -> s.toUpperCase(), Collectors.counting()))
				.entrySet().stream()
				.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
				.map(e -> new WordOccurrence(e.getKey(), BigInteger.valueOf(e.getValue())))
				.collect(Collectors.toList());
		result.setWordOccurenceRanking(occurrences);
		
		return result;
	}

}
