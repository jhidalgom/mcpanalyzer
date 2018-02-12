package com.hpe.test.mcpanalyzer.service;

import java.io.FileNotFoundException;

import com.hpe.test.mcpanalyzer.model.kpi.KpiResult;

public interface KpiService {
	public KpiResult getKpis() throws FileNotFoundException;
}
