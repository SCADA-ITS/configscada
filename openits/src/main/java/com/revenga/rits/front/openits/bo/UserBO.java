package com.revenga.rits.front.openits.bo;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlTypeAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.service.user.SessionService;
import com.revenga.rits.front.openits.service.user.UserManager;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserBO extends GenericBO {

	private static final String AUTH_ENCRYPTED_PASSWORD = "auth.encryptedPassword";
	private static final String FIELD_USER = "user";
	
	private static final Boolean USERMGR_ENABLED = Boolean.valueOf(AppInfra.getProperty("usermgr.enabled"));
	
	@Autowired
	GraphqlTypeAdapter graphqlTypeAdapter;
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	UserManager userManager;
	
	@Autowired
	private Environment env;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> list = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			list = userManager.getUsers();
		}
		else {
		
			list = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_USERS, null);
		}
		
		if (Boolean.parseBoolean(env.getProperty(AUTH_ENCRYPTED_PASSWORD)) && !CollectionUtils.isEmpty(list)) {
			
			list.forEach(this::decryptPasswordField);
		}

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_USERS, list);
	}
	
	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> obj = null;
		
		if (USERMGR_ENABLED.booleanValue()) {

			obj = userManager.getUser(id);
		}
		else {
		
			obj = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_USER,
					new Object[] { id });
		}

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_USER,
				Boolean.parseBoolean(env.getProperty(AUTH_ENCRYPTED_PASSWORD)) ? decryptPasswordField(obj) : obj);
	}
	
	public Map<String, Object> getByName(String name) {
		
		Map<String, Object> obj = null;
		
		if (USERMGR_ENABLED.booleanValue()) {

			obj = userManager.getUserByName(name);
		}
		else {
		
			obj = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_USER_BY_NAME,
					new Object[] { name });
		}

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_USER_BY_NAME,
				Boolean.parseBoolean(env.getProperty(AUTH_ENCRYPTED_PASSWORD)) ? decryptPasswordField(obj) : obj);
	}
	
	public List<Map<String, Object>> getListWithRole() {

		List<Map<String, Object>> users = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			users = userManager.getUsersWithRole();
		}
		else {
		
			users = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_USERS_WITH_ROLE, null);
		}
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_USERS_WITH_ROLE, users);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createUser(Object input) {
		
		if (Boolean.parseBoolean(env.getProperty(AUTH_ENCRYPTED_PASSWORD))) {
			
			encryptPasswordField((Map<String, Object>) ((Map<String, Object>) input).get(FIELD_USER));
		}
		
		Map<String, Object> createdUser = null; 
				
		if (USERMGR_ENABLED.booleanValue()) {
			
			createdUser = userManager.createUser(input);
		}
		else {
			
			createdUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_USER, (Map<String, Object>) input); 
		}
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_USER, createdUser);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateUser(Object input) {
			
		if (Boolean.parseBoolean(env.getProperty(AUTH_ENCRYPTED_PASSWORD))) {
			
			encryptPasswordField((Map<String, Object>) ((Map<String, Object>) input).get(FIELD_USER));
		}
		
		Map<String, Object> updatedUser = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			updatedUser = userManager.updateUser(input);
		}
		else {
			
			updatedUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_USER, (Map<String, Object>) input);
		}
		
		Map<String, Object> updatedUserConverted = vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_USER, updatedUser); 
		
		sessionService.updateUser(updatedUserConverted);
		
		return updatedUserConverted;
	}
	
	private Map<String, Object> encryptPasswordField(Map<String, Object> userMap) {
		
		try {

			String password = (String) userMap.get(UserSessionUI.USER_FIELD_PASSWORD);
			
			if (!StringUtils.isEmpty(password)) {
				
				userMap.put(UserSessionUI.USER_FIELD_PASSWORD, AES.encrypt(password));
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return userMap;
	}
	
	private Map<String, Object> decryptPasswordField(Map<String, Object> userMap) {
		
		if (!MapUtils.isEmpty(userMap)) {
			
			String password = (String) userMap.get(UserSessionUI.USER_FIELD_PASSWORD);
			
			if (!StringUtils.isEmpty(password)) {
				
				// Initialize User field password with random string value
				userMap.put(UserSessionUI.USER_FIELD_PASSWORD, String.valueOf(Instant.now().toEpochMilli()));
				
				// Set with decrypted password 
				try {
					
					userMap.put(UserSessionUI.USER_FIELD_PASSWORD, AES.decrypt(password));
					
				} catch (Exception e) {
					// Nothing 
				}
			}
		}
		
		return userMap;
	}
	
	public Map<String, Object> deleteUser(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("userId", id);
		
		Map<String, Object> deletedUser = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			deletedUser = userManager.deleteUser(id);
		}
		else {
		
			deletedUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_USER, input);
		}
		
		Map<String, Object> deletedUserConverted = vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_USER, deletedUser);
		
		sessionService.deleteUser(deletedUserConverted);
		
		return deletedUserConverted;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean sendKillUser(Object input) {
		
	    Boolean res = false;
	    
	    Map<String, Object> sendKillUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_KILL_USER, (Map<String, Object>) input);
	    
	    res = checkMapIsBoolean(sendKillUser);

	    return res;
	}

}
