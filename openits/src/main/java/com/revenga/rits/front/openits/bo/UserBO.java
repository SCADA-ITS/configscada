package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlTypeAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

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
	
	public Map<String, Object> updateUser(String input) {
		
		Map<String, Object> updatedUser = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceUpdateUser(), input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceUpdateUser()).getFieldId(), updatedUser);
	}
}
