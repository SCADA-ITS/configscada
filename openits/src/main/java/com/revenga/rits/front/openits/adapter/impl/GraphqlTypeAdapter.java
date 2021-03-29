package com.revenga.rits.front.openits.adapter.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private static final String USER_FIELD_LOCALE = "locale";
	private static final String USER_FIELD_LOCALE_ID = "localeId";
	private static final String USER_FIELD_USER_PERMISSIONS = "userPermissions";
	private static final String USER_FIELD_USER_PERMISSION_IDS = "userPermissionIds";
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUserForMutation(Map<String, Object> user) {
		
		Map<String, Object> userForMutation = null;
		Map<String, Object> userData = null;
		Object objRole = null;
		Object objLocale = null;
		Object objUserPermissions = null;
		
		try {
			
			userForMutation = ConversionUtil.clone(user);
			userData = (Map<String, Object>) userForMutation.get(USER_DATA);
			
			if (userData != null) {
			
				objRole = userData.remove(USER_FIELD_ROLE);
				objLocale = userData.remove(USER_FIELD_LOCALE);
				objUserPermissions = userData.remove(USER_FIELD_USER_PERMISSIONS);
				
				
				if (objRole instanceof Map) {
					
					Map<String, Object> role = (Map<String, Object>) objRole;
					
					userData.put(USER_FIELD_ROLE_ID, role.get(USER_FIELD_ID));
				}
				
				if (objLocale instanceof Map) {
					
					Map<String, Object> locale = (Map<String, Object>) objLocale;
					
					userData.put(USER_FIELD_LOCALE_ID, locale.get(USER_FIELD_ID));
				}
				
				if (objUserPermissions instanceof List) {
				
					List<Map<String, Object>> listUserPermissions = (List<Map<String, Object>>) objUserPermissions;
					
					List<String> userPermissions = new ArrayList<>();
					
					listUserPermissions.forEach(e -> userPermissions.add((String) e.get(USER_FIELD_ID)));
					
					userData.put(USER_FIELD_USER_PERMISSION_IDS, userPermissions);
				}
			}
			
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return userForMutation;
	}
}
