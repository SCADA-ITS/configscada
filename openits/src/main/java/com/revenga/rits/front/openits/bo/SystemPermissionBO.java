package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class SystemPermissionBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> systemPermissions = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetSystemPermissions(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetSystemPermissions()).getFieldId(), systemPermissions);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> systemPermission = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetSystemPermission(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetSystemPermission()).getFieldId(), systemPermission);
	}

}
