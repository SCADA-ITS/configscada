package com.revenga.rits.front.openits.bo;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class LogTypeBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_LOG_TYPES;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_LOG_TYPE;
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
