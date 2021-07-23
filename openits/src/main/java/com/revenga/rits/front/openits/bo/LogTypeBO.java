package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class LogTypeBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@Autowired
	VO2UIAdapter vo2uiAdapter;

	@Autowired
	VODataSourceAdapter voDataSourceAdapter;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> logTypes = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_LOG_TYPES,
				null);

		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_LOG_TYPES).getFieldId(), logTypes);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> logType = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_LOG_TYPE,
				new Object[] { id });

		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_LOG_TYPE).getFieldId(), logType);
	}

	@SuppressWarnings("unchecked")
	public Boolean sendAuditLogs(Object input) {

		Boolean res = false;

		Map<String, Object> sendAuditLog = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_AUDIT_LOGS,
				(Map<String, Object>) input);

		res = checkMapIsBoolean(sendAuditLog);

		return res;

	}

	@SuppressWarnings("unchecked")
	public Boolean sendRecognizeAuditLogs(Object input) {

		Boolean res = false;

		Map<String, Object> sendAuditLog = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_RECOGNIZE_AUDIT_LOGS, (Map<String, Object>) input);

		res = checkMapIsBoolean(sendAuditLog);

		return res;

	}
}
