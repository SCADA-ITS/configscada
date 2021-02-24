package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlTypeAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class UserBO extends GenericBO {
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired
	GraphqlTypeAdapter graphqlTypeAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> users = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_USERS, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_USERS).getFieldId(), users);
	}

	public List<Map<String, Object>> getListWithRole() {

		List<Map<String, Object>> users = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_USERS_WITH_ROLE, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_USERS_WITH_ROLE).getFieldId(), users);
	}
	
	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> user = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_USER, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_USER).getFieldId(), user);
	}
	
	public Map<String, Object> createUser(Object input) {
		
		Map<String, Object> createdUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_USER, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_CREATE_USER).getFieldId(), createdUser);
	}
	
	public Map<String, Object> updateUser(Object input) {
			
		Map<String, Object> updatedUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_USER, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_UPDATE_USER).getFieldId(), updatedUser);
	}
	
	public Map<String, Object> deleteUser(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("userId", id);
		
		Map<String, Object> deletedUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_USER, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_DELETE_USER).getFieldId(), deletedUser);
	}
	
	public Boolean sendKillUser(Object input) {
		
	    Boolean res = false;
	    
	    Map<String, Object> sendKillUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_KILL_USER, (Map<String, Object>) input);
	    
	    res = checkMapIsBoolean(sendKillUser);

	    return res;
	}

}
