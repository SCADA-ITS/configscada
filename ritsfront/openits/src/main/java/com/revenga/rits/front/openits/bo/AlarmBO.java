package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AlarmBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_ALARMS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_ALARM;
	}

	public List<Map<String, Object>> getAlarms(List<String> ids) {
		
		List<Map<String, Object>> alarms = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARMS_ARRAY,
				new Object[] { ids });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ALARMS_ARRAY, alarms);

	}

	public List<Map<String, Object>> getAlarmsByAlarmType(String id) {

		List<Map<String, Object>> alarms = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARMS_BY_ALARM_TYPE, new Object[] { id });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ALARMS_BY_ALARM_TYPE, alarms);
	}

	public List<Map<String, Object>> getAlarmsBySource(String sourceElementId) {

		List<Map<String, Object>> alarms = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARMS_BY_SOURCE, new Object[] { sourceElementId });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ALARMS_BY_SOURCE, alarms);
	}

	public List<Map<String, Object>> getAlarmsBySourceAndAlarmType(String sourceElementId, String alarmTypeId) {

		List<Map<String, Object>> alarms = voDataSourceAdapter.getList(
				VODataSourceAdapter.DATA_SOURCE_GET_ALARMS_BY_SOURCE_AND_ALARM_TYPE,
				new Object[] { sourceElementId, alarmTypeId });

		return vo2uiAdapter.convert(
				VODataSourceAdapter.DATA_SOURCE_GET_ALARMS_BY_SOURCE_AND_ALARM_TYPE,
				alarms);
	}

	@SuppressWarnings("unchecked")
	public Boolean sendAlarms(Object input) {

		Boolean res = false;

		Map<String, Object> sendAlarms = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_ALARMS,
				(Map<String, Object>) input);

		res = checkMapIsBoolean(sendAlarms);

		return res;

	}

	@SuppressWarnings("unchecked")
	public Boolean sendRecognizeAlarms(Object input) {

		Boolean res = false;

		Map<String, Object> sendRecognizeAlarms = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_RECOGNIZE_ALARM, (Map<String, Object>) input);

		res = checkMapIsBoolean(sendRecognizeAlarms);

		return res;

	}

	@SuppressWarnings("unchecked")
	public Boolean sendDeactivationAlarms(Object input) {

		Boolean res = false;

		Map<String, Object> sendDeactivationAlarms = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_DEACTIVATION_ALARMS, (Map<String, Object>) input);

		res = checkMapIsBoolean(sendDeactivationAlarms);

		return res;

	}

}
