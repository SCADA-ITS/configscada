package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlTypeAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.util.ConversionUtil;

@Service
public class UserBO {
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired
	GraphqlTypeAdapter graphqlTypeAdapter;
	
	public List<Map<String, Object>> getUsers() {
		
		List<Map<String, Object>> users = voDataSourceAdapter
				.getList(voDataSourceAdapter.getDataSourceGetUsers(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetUsers()).getFieldId(), users);
	}
	
	public Map<String, Object> getUser(String id) {
		
		Map<String, Object> user = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetUser(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetUser()).getFieldId(), user);
	}
	
	public List<Map<String, Object>> getRoles() {
		
		List<Map<String, Object>> roles = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetRoles(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetRoles()).getFieldId(), roles);
	}
				
	public Map<String, Object> getRole(String id) {
		
		Map<String, Object> role = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetRole(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetRole()).getFieldId(), role);
	}
	
	public Map<String, Object> createUser(Object input) {
		
		Map<String, Object> createdUser = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceCreateUser(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceCreateUser()).getFieldId(), createdUser);
	}
	
	public Map<String, Object> updateUser(Object input) {
			
		Map<String, Object> updatedUser = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceUpdateUser(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceUpdateUser()).getFieldId(), updatedUser);
	}
	
	public Map<String, Object> deleteUser(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("userId", id);
		
		Map<String, Object> deletedUser = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceDeleteUser(), input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceDeleteUser()).getFieldId(), deletedUser);
	}
	
	public Map<String, Object> sendKillUsers(Object input) {
		
		Map<String, Object> deletedUser = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceDeleteUser(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceDeleteUser()).getFieldId(), deletedUser);
	}
}
