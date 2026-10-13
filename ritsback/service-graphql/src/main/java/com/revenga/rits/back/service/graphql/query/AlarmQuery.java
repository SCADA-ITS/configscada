package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.AlarmLevelVO;
import com.revenga.rits.back.front.core.model.vo.AlarmFilteredVO;
import com.revenga.rits.back.front.core.model.vo.AlarmTypeVO;
import com.revenga.rits.back.front.core.model.vo.AlarmVO;
import com.revenga.rits.back.front.core.model.vo.ThresholdOperationVO;
import com.revenga.rits.back.service.graphql.service.AlarmConfigService;
import com.revenga.rits.back.service.graphql.service.AlarmLevelService;
import com.revenga.rits.back.service.graphql.service.AlarmFilteredService;
import com.revenga.rits.back.service.graphql.service.AlarmService;
import com.revenga.rits.back.service.graphql.service.AlarmTypeService;
import com.revenga.rits.back.service.graphql.service.ThresholdOperationService;

@Component
public class AlarmQuery implements GraphQLQueryResolver {

	@Autowired
	private AlarmConfigService alarmConfigService;

	@Autowired
	private AlarmService alarmService;
	
	@Autowired
	private ThresholdOperationService thresholdOperationService;
	
	@Autowired
	private AlarmTypeService alarmTypeService;
	
	@Autowired
	private AlarmLevelService alarmLevelService;

	@Autowired
	private AlarmFilteredService alarmFilteredService;
	
	public List<AlarmConfigVO> getAlarmConfigs() {

		return alarmConfigService.getAll();
	}

	public Optional<AlarmConfigVO> getAlarmConfig(final String alarmConfigId) {

		return alarmConfigService.get(alarmConfigId);
	}
	
	public List<AlarmConfigVO> getAlarmConfigsByAlarmType(final String alarmTypeId) {

		return alarmConfigService.getAlarmConfigsByAlarmType(alarmTypeId);
	}
	
	public List<AlarmConfigVO> getAlarmConfigsByAlarmLevel(final String alarmLevelId) {

		return alarmConfigService.getAlarmConfigsByAlarmLevel(alarmLevelId);
	}
	
	public List<AlarmConfigVO> getAlarmConfigsByElementType(final String elementTypeId) {

		return alarmConfigService.getAlarmConfigsByElementType(elementTypeId);
	}
	
	public List<ThresholdOperationVO> getThresholdOperations() {

		return thresholdOperationService.getAll();
	}

	public Optional<ThresholdOperationVO> getThresholdOperation(final String thresholdOperationId) {

		return thresholdOperationService.get(thresholdOperationId);
	}
	
	public List<AlarmTypeVO> getAlarmTypes() {

		return alarmTypeService.getAll();
	}

	public Optional<AlarmTypeVO> getAlarmType(final String alarmTypeId) {

		return alarmTypeService.get(alarmTypeId);
	}
	
	public List<AlarmLevelVO> getAlarmLevels() {

		return alarmLevelService.getAll();
	}

	public Optional<AlarmLevelVO> getAlarmLevel(final String alarmLevelId) {

		return alarmLevelService.get(alarmLevelId);
	}
	
	public List<AlarmVO> getAlarms(final List<String> alarmIds) {

		return alarmService.gets(alarmIds);
	}

	
	public List<AlarmVO> getAlarmsByType(final String alarmTypeId) {

		return alarmService.getAll(alarmTypeId);
	}

	public Optional<AlarmVO> getAlarm(final String alarmId) {

		return alarmService.get(alarmId);
	}

	public List<AlarmVO> getAlarmsByAlarmConfig(final String alarmConfigId) {

		return alarmService.getByAlarmConfig(alarmConfigId);
	}
	
	public List<AlarmVO> getAlarmsBySource(final String sourceElementId, final String alarmTypeId) {

		return alarmService.getBySource(sourceElementId,alarmTypeId);
	}

	public List<AlarmVO> getAlarmsByTarget(final String targetElementId, final String alarmTypeId) {

		return alarmService.getByTarget(targetElementId,alarmTypeId);
	}
	
	public List<AlarmFilteredVO> getAlarmFiltereds() {

		return alarmFilteredService.getAll();
	}

	public Optional<AlarmFilteredVO> getAlarmFiltered(final String alarmFilteredId) {

		return alarmFilteredService.get(alarmFilteredId);
	}

	public List<AlarmFilteredVO> getAlarmFilteredsByAlarmConfig(final String alarmConfigId) {

		return alarmFilteredService.getAlarmFilteredByAlarmConfig(alarmConfigId);
	}

	public List<AlarmFilteredVO> getAlarmFilteredsByElement(final String elementId) {

		return alarmFilteredService.getAlarmFilteredsByElement(elementId);
	}
}