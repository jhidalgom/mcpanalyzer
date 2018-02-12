package com.hpe.test.mcpanalyzer.service;

import java.io.FileNotFoundException;
import java.util.Optional;

import com.hpe.test.mcpanalyzer.model.metrics.MetricsResult;

public interface MetricsService {
	public MetricsResult getMetrics(Optional<String> requestedDate) throws FileNotFoundException;
}
