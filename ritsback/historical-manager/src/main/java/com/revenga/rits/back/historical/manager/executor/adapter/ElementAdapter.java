package com.revenga.rits.back.historical.manager.executor.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

import lombok.Getter;

public class ElementAdapter {

	private static final String ADAPTERS_PATH = "/adapters/manager/historical-manager/element_type";
	private static ElementAdapter instance;
	
	@Getter
	private Map<Short, HistoricalElementParserV1> adapters;

	public static ElementAdapter getInstance() {

		if (instance == null) {

			instance = new ElementAdapter();
		}

		return instance;
	}

	private ElementAdapter() {

		super();

		initialize();
	}

	private void initialize() {

		adapters = new HashMap<>();

		load(HistoricalElementParserV1.class);
	}

	private void load(Class<HistoricalElementParserV1> adapterType) {

		SessionAdapter sessionAdapter = new SessionAdapter();
		List<String> adaptersFileName = ResourcesUtil.getFiles(ADAPTERS_PATH);

		for (String adapterFileName : adaptersFileName) {

			Object adapter = adapterType.cast((sessionAdapter).getAdapter(
					ADAPTERS_PATH + File.separator + adapterFileName, HistoricalElementParserV1.class));

			if (adapter != null) {
				adapters.put(adapterType.cast((adapter)).getElementTypeId(), adapterType.cast((adapter)));
			}
			adapterType.cast((adapter));
		}

	}
}
