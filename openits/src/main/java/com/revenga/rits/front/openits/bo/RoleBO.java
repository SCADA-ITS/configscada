package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class RoleBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_ROLES;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_ROLE;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createRole(Object input) {
		
		Map<String, Object> createdRole = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_ROLE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_ROLE, createdRole);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateRole(Object input) {
			
		Map<String, Object> updatedRole = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_ROLE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_ROLE, updatedRole);
	}
	
	public Map<String, Object> deleteRole(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("roleId", id);
		
		Map<String, Object> deletedRole = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_ROLE, input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_ROLE, deletedRole);
	}

}
