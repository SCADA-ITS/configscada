package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AuditLogBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_AUDIT_LOGS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_AUDIT_LOG;
	}
	
	public List<Map<String, Object>> getByLogType(String id) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_AUDIT_LOGS_BY_LOG_TYPE, new Object[] { id });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_AUDIT_LOGS_BY_LOG_TYPE, elements);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean send(Object input) {
		
		Boolean res = false;

		Map<String, Object> sendCommandElement = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_AUDIT_LOGS, (Map<String, Object>) input);

		res = checkMapIsBoolean(sendCommandElement);

		return res;

	}
	
	@SuppressWarnings("unchecked")
	public Boolean sendRecognize(Object input) {

		Boolean res = false;

		Map<String, Object> sendAuditLog = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_RECOGNIZE_AUDIT_LOGS, (Map<String, Object>) input);

		res = checkMapIsBoolean(sendAuditLog);

		return res;

	}
}
