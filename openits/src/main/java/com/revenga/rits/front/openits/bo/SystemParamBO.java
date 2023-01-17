package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class SystemParamBO extends GenericBO {
			
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public static final String SYSTEM_PARAM_USER_LOGIN_RETRIES = "SystemParam:2";
	public static final String SYSTEM_PARAM_DEFAULT_ACCOUNT_LOCKED_TIME_MINUTES = "SystemParam:4";
	public static final String SYSTEM_PARAM_MAX_SESSIONS = "SystemParam:16";
	
	private static final String FIELD_VALUE = "value";
	
	@Override
	public List<Map<String, Object>> getList() {
		
		return getSystemParams(new ArrayList<>());
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		List<Map<String, Object>> systemParams = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_SYSTEM_PARAMS, new String[] { id } );
		
		if (!CollectionUtils.isEmpty(systemParams)) {
		
			return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_SYSTEM_PARAMS, systemParams.get(0));
		}
		
		return null;
	}
	
	public List<Map<String, Object>> getSystemParams(List<String> ids) {
		
		List<Map<String, Object>> systemParams = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_SYSTEM_PARAMS, new Object[] { ids });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_SYSTEM_PARAMS, systemParams);
	}
	
	public Object getSystemValue(String id) {
		
		Map<String, Object> systemParam = getItem(id);
		
		if (systemParam != null) {
			
			return systemParam.get(FIELD_VALUE);
		}
		
		return null;
	}
	
	public Object getSystemValue(String id, Object defaultValue) {
		
		Object obj = defaultValue;
		
		Map<String, Object> systemParam = getItem(id);
		
		if (systemParam != null && systemParam.get(FIELD_VALUE) != null) {
				
			obj = systemParam.get(FIELD_VALUE);
		}
		
		return obj;
	}
}
