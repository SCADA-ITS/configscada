package com.revenga.rits.front.openits.adapter.impl;

import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revenga.rits.front.openits.util.ConversionUtil;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GraphqlTypeAdapter {
	
	public static final String USER_DATA = "user";
	
	private static final String USER_FIELD_ID = "id";
	private static final String USER_FIELD_ROLE = "role";
	private static final String USER_FIELD_ROLE_ID = "roleId";
	private static final String USER_FIELD_USER_PERMISSIONS = "userPermissions";
	public Map<String, Object> getUserForMutation(Map<String, Object> user) {
		
		Map<String, Object> userForMutation = null;
		Map<String, Object> userData = null;
		Object objRole = null;
		
		try {
			
			userForMutation = ConversionUtil.clone(user);
			userData = (Map<String, Object>) userForMutation.get(USER_DATA);
			
			if (userData != null) {
			
				objRole = userData.remove(USER_FIELD_ROLE);
				userData.remove(USER_FIELD_USER_PERMISSIONS);
				
				
				if (objRole instanceof Map) {
					
					@SuppressWarnings("unchecked")
					Map<String, Object> role = (Map<String, Object>) objRole;
					
					userData.put(USER_FIELD_ROLE_ID, role.get(USER_FIELD_ID));
				}
			}

			
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return userForMutation;
	}
}
