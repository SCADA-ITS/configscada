package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class LogTypeBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;

	public List<Map<String, Object>> getLogTypes() {
		
		List<Map<String, Object>> logTypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetLogTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetLogTypes()).getFieldId(), logTypes);
	}
				
	public Map<String, Object> getLogType(String id) {
		
		Map<String, Object> logType = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetLogType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetLogType()).getFieldId(), logType);
	}

}
