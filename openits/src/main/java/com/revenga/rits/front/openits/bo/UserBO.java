package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlTypeAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class UserBO extends GenericBO {
		
	@Autowired
	GraphqlTypeAdapter graphqlTypeAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_USERS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_USER;
	}
	
	public List<Map<String, Object>> getListWithRole() {

		List<Map<String, Object>> users = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_USERS_WITH_ROLE, null);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_USERS_WITH_ROLE, users);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createUser(Object input) {
		
		Map<String, Object> createdUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_USER, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_USER, createdUser);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateUser(Object input) {
			
		Map<String, Object> updatedUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_USER, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_USER, updatedUser);
	}
	
	public Map<String, Object> deleteUser(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("userId", id);
		
		Map<String, Object> deletedUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_USER, input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_USER, deletedUser);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean sendKillUser(Object input) {
		
	    Boolean res = false;
	    
	    Map<String, Object> sendKillUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_KILL_USER, (Map<String, Object>) input);
	    
	    res = checkMapIsBoolean(sendKillUser);

	    return res;
	}

}
