package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.service.user.UserManager;

@Service
public class RoleBO extends GenericBO {
	
	private static final Boolean USERMGR_ENABLED = Boolean.valueOf(AppInfra.getProperty("usermgr.enabled"));
	
	public static final String FIELD_ID = "id";
	public static final String FIELD_ALIAS = "alias";
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired
	UserManager userManager;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> list = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			list = userManager.getRoles();
		}
		else {
		
			list = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ROLES, null);
		}
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ROLES, list);
	}
	
	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> obj = null;
		
		if (USERMGR_ENABLED.booleanValue()) {

			obj = userManager.getRole(id);
		}
		else {
		
			obj = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ROLE,
					new Object[] { id });
		}

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ROLE, obj);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createRole(Object input) {
		
		Map<String, Object> createdRole = null; 
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			createdRole = userManager.createRole(input);
		}
		else {
			
			createdRole = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_ROLE, (Map<String, Object>) input);
		}
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_ROLE, createdRole);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateRole(Object input) {
			
		Map<String, Object> updatedRole = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			updatedRole = userManager.updateRole(input);
		}
		else {
			
			updatedRole = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_ROLE, (Map<String, Object>) input);
		}
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_ROLE, updatedRole);
	}
	
	public Map<String, Object> deleteRole(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("roleId", id);
		
		Map<String, Object> deletedRole = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			deletedRole = userManager.deleteRole(id);
		}
		else {
		
			deletedRole = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_ROLE, input);
		}
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_ROLE, deletedRole);
	}

}
