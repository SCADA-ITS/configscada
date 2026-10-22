package com.revenga.rits.back.historical.manager.executor.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.back.adapter.lib.model.HistoricalAuditLogParserV1;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

import lombok.Getter;

public class AuditLogAdapter {

	private static final String ADAPTERS_PATH = "/adapters/manager/historical-manager/log_type";
	private static AuditLogAdapter instance;

	@Getter
	private Map<Short, HistoricalAuditLogParserV1> adapters;

	public static AuditLogAdapter getInstance() {

		if (instance == null) {

			instance = new AuditLogAdapter();
		}

		return instance;
	}

	private AuditLogAdapter() {

		super();

		initialize();
	}

	private void initialize() {

		adapters = new HashMap<>();

		load(HistoricalAuditLogParserV1.class);
	}

	private void load(Class<HistoricalAuditLogParserV1> adapterType) {

		SessionAdapter sessionAdapter = new SessionAdapter();
		List<String> adaptersFileName = ResourcesUtil.getFiles(ADAPTERS_PATH);

		for (String adapterFileName : adaptersFileName) {

			Object adapter = adapterType.cast((sessionAdapter)
					.getAdapter(ADAPTERS_PATH + File.separator + adapterFileName, HistoricalAuditLogParserV1.class));

			if (adapter != null) {
				adapters.put(adapterType.cast((adapter)).getLogTypeId(), adapterType.cast((adapter)));
			}
			adapterType.cast((adapter));
		}

	}
}
