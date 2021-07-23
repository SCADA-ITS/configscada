package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class RoleBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> roles = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ROLES, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ROLES).getFieldId(), roles);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> role = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ROLE, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ROLE).getFieldId(), role);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createRole(Object input) {
		
		Map<String, Object> createdRole = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_ROLE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_CREATE_ROLE).getFieldId(), createdRole);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateRole(Object input) {
			
		Map<String, Object> updatedRole = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_ROLE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_UPDATE_ROLE).getFieldId(), updatedRole);
	}
	
	public Map<String, Object> deleteRole(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("roleId", id);
		
		Map<String, Object> deletedRole = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_ROLE, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_DELETE_ROLE).getFieldId(), deletedRole);
	}

}
