package com.revenga.rits.front.openits.adapter.impl;

import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revenga.rits.front.openits.util.ConversionUtil;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GraphqlTypeAdapter {
	
	private static final String USER_FIELD_ID = "id";
	private static final String USER_FIELD_ROLE = "role";
	private static final String USER_FIELD_ROLE_ID = "roleId";
	private static final String USER_FIELD_USER_PERMISSIONS = "userPermissions";
	public String getUserForMutation(String user) {
		
		Map<String, Object> userForMutation = null;
		String userForMutationStr = null;
		Object objRole = null;
		
		try {
			
			userForMutation = ConversionUtil.stringToMap(user);
			objRole = userForMutation.remove(USER_FIELD_ROLE);
			userForMutation.remove(USER_FIELD_USER_PERMISSIONS);
			
			
			if (objRole instanceof Map) {
				
				@SuppressWarnings("unchecked")
				Map<String, Object> role = (Map<String, Object>) objRole;
				
				userForMutation.put(USER_FIELD_ROLE_ID, role.get(USER_FIELD_ID));
			}
			
			userForMutationStr = ConversionUtil.mapToString(userForMutation); 
			
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return userForMutationStr;
	}
}
