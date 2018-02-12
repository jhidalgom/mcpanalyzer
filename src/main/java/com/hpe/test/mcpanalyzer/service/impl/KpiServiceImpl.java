package com.hpe.test.mcpanalyzer.service.impl;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpe.test.mcpanalyzer.model.kpi.JsonProcessDuration;
import com.hpe.test.mcpanalyzer.model.kpi.KpiResult;
import com.hpe.test.mcpanalyzer.model.message.Message;
import com.hpe.test.mcpanalyzer.model.processor.ProcessedFile;
import com.hpe.test.mcpanalyzer.repository.ProcessedFileRepository;
import com.hpe.test.mcpanalyzer.service.KpiService;

@Service
public class KpiServiceImpl implements KpiService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProcessedFileRepository processedFileRepository;

	@Override
	public KpiResult getKpis() throws FileNotFoundException {
		
		Iterable<ProcessedFile> processedFiles = processedFileRepository.findAll();
		
		if(!processedFiles.iterator().hasNext()) {
			log.info("No data was found.  Process some files first");
			throw new FileNotFoundException();
		}
			
		return calculateKpis(processedFiles);
	}
	
	private KpiResult calculateKpis(Iterable<ProcessedFile> files) {
		KpiResult result = new KpiResult();
		
		List<ProcessedFile> fileList = new ArrayList<>();
		files.iterator().forEachRemaining(fileList::add);
		
		result.setProcessedFiles(BigInteger.valueOf(fileList.size()));
		
		long totalRows = fileList.stream().mapToLong(f -> f.getRowsWithFieldErrors() + f.getMessageList().size()).sum();
		result.setTotalRows(BigInteger.valueOf(totalRows));
		
		List<Message> allMessages = fileList.stream().map(f -> f.getMessageList()).flatMap(List::stream).collect(Collectors.toList());
		
		Map<Message.Type, Long> amountOfMessages = allMessages.stream().collect(Collectors.groupingBy(Message::getType, Collectors.counting()));
		
		result.setTotalCalls(BigInteger.valueOf(amountOfMessages.get(Message.Type.CALL)));
		result.setTotalMessages(BigInteger.valueOf(amountOfMessages.get(Message.Type.MESSAGE)));
		
		long diffOrigins = allMessages.stream().map(m -> m.getOrigin().substring(0, 2)).distinct().count();
		result.setDifferentOriginCountries(BigInteger.valueOf(diffOrigins));
		
		long diffDestinations = allMessages.stream().map(m -> m.getOrigin().substring(0, 2)).distinct().count();
		result.setDifferentDestinationCountries(BigInteger.valueOf(diffDestinations));
		
		List<JsonProcessDuration> durations = fileList.stream().map(f -> new JsonProcessDuration(f.getFileDate(), f.getProcessDuration())).collect(Collectors.toList());
		result.setJsonProcessDurations(durations);
		
		return result;
	}
	
}
