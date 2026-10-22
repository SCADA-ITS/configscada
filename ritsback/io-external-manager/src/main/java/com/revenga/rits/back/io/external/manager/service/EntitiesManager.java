package com.revenga.rits.back.io.external.manager.service;

import java.util.List;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;

import lombok.Getter;

public class EntitiesManager {

	ElementRepository elementRepository;
	AlarmConfigRepository alarmConfigRepository;

	@Getter
	private boolean initialized = false;

	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "entities.manager.retention_time_conf"));

	private static EntitiesManager instance;

	public static EntitiesManager getInstance() {

		if (instance == null) {

			instance = new EntitiesManager();
		}

		return instance;
	}

	private EntitiesManager() {

		this.elementRepository = new ElementRepository(true, CACHE_RETENTION_TIME_CONF);
		this.alarmConfigRepository = new AlarmConfigRepository(true, CACHE_RETENTION_TIME_CONF);
		EntitiesManagerHelper.cacheRepository(this);
	}

	public List<Element> getElements() {

		return elementRepository.getAll(false);
	}

	public List<Element> getElements(Long elementTypeId) {

		return elementRepository.gets(elementTypeId, false);
	}

	public Element getElement(Long elementTypeId, Long elementId) {

		return elementRepository.get(elementTypeId, elementId, false);
	}

	public AlarmConfig getAlarmConfig(Long alarmConfigId) {

		return alarmConfigRepository.get(alarmConfigId, false);
	}

}
