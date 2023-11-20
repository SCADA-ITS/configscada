package com.revenga.rits.front.openits.service.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.service.user.repository.GenericRepository.EntityPersistenceException;
import com.revenga.rits.front.openits.service.user.repository.I18nLabelRepository;
import com.revenga.rits.front.openits.service.user.repository.I18nLocaleRepository;
import com.revenga.rits.front.openits.service.user.repository.RoleRepository;
import com.revenga.rits.front.openits.service.user.repository.SystemPermissionRepository;
import com.revenga.rits.front.openits.service.user.repository.UserRepository;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserManager {

	private static final Boolean USERMGR_ENABLED = Boolean.valueOf(AppInfra.getProperty("usermgr.enabled"));
	public static final String USERMGR_SQL_JDBC = AppInfra.getProperty("usermgr.sql.jdbc");
	public static final String USERMGR_SQL_USER = AppInfra.getProperty("usermgr.sql.user");
	public static final String USERMGR_SQL_PASSWORD = AppInfra.getProperty("usermgr.sql.password");
	
	private static final String FIELD_USER = "user";
	private static final String FIELD_ROLE = "role";
	
	private Connection conn;
	
	@PostConstruct
	private void init() {
		
		try {
			
			if (USERMGR_ENABLED.booleanValue() && (this.conn == null || this.conn.isClosed())) {
					
				this.conn = DriverManager.getConnection(USERMGR_SQL_JDBC, USERMGR_SQL_USER,
						AES.decrypt(USERMGR_SQL_PASSWORD));
				
				this.conn.setAutoCommit(false);
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	// USERS
	
	public List<Map<String, Object>> getUsers() {
	
		UserRepository userRepository = new UserRepository(this.conn);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> users = userRepository.gets();
		
		return users;
	}
	
	public List<Map<String, Object>> getUsersWithRole() {
		
		UserRepository userRepository = new UserRepository(this.conn);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> users = userRepository.gets();
		
		userRepository.fill(users);
		
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUser(String userIdStr) {
		
		Map<String, Object> user = null;
		UserRepository userRepository = new UserRepository(this.conn);
		
		String[] ids = userRepository.getId(userIdStr);
		
		if (!ArrayUtils.isEmpty(ids)) {
		
			user = userRepository
					.get(new ImmutablePair<String, Object>(UserRepository.PK_USER_ID, ids[0]));
			
			userRepository.fill(user);
		}
			
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUserByName(String userNameStr) {
		
		Map<String, Object> user = null;
		UserRepository userRepository = new UserRepository(this.conn);
		
		user = userRepository
				.get(new ImmutablePair<String, Object>(UserRepository.FIELD_DB_NAME, userNameStr));
			
		userRepository.fill(user);
			
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createUser(Object input) {
		
		Map<String, Object> newUser = null;
		
		try {
			
			UserRepository userRepository = new UserRepository(this.conn);
			
			if (input instanceof Map) {
				
				Map<String, Object> inputMap = (Map<String, Object>) input;
				
				Map<String, Object> user = (Map<String, Object>) inputMap.get(FIELD_USER);
				
				if (!MapUtils.isEmpty(user)) {
				
					newUser = userRepository.create(user);
					
					if (!MapUtils.isEmpty(newUser)) {
						
						userRepository.fill(newUser);
						
						userRepository.commit();
					}
				}
			}
			
		} catch (EntityPersistenceException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return newUser;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateUser(Object input) {
		
		Map<String, Object> updatedUser = null;
		
		try {
			
			if (input instanceof Map) {
				
				Map<String, Object> inputMap = (Map<String, Object>) input;
				
				Map<String, Object> user = (Map<String, Object>) inputMap.get(FIELD_USER);
				
				if (!MapUtils.isEmpty(user)) {
					
					UserRepository userRepository = new UserRepository(this.conn);
			
					updatedUser = userRepository.update(user);
			
					if (!MapUtils.isEmpty(updatedUser)) {
						
						userRepository.fill(updatedUser);
						
						userRepository.commit();
					}
				}
			}
			
		} catch (EntityPersistenceException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return updatedUser;
	}

	public Map<String, Object> deleteUser(String id) {
		
		Map<String, Object> deletedUser = null;
		
		try {
			
			UserRepository userRepository = new UserRepository(this.conn);
			
			deletedUser = userRepository.delete(id);
			
			if (!MapUtils.isEmpty(deletedUser)) {
				
				userRepository.commit();
			}
			
		} catch (EntityPersistenceException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return deletedUser;
	}
	
	public Boolean sendKillUser(Object killUser) {
		// TODO Auto-generated method stub
		return true;
	}
	
	// ROLES
	
	public List<Map<String, Object>> getRoles() {
		
		RoleRepository roleRepository = new RoleRepository(this.conn);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> roles = roleRepository.gets();
		
		roleRepository.fill(roles);
		
		return roles;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getRole(String roleIdStr) {
		
		Map<String, Object> role = null;
		RoleRepository roleRepository = new RoleRepository(this.conn);
		
		String[] ids = roleRepository.getId(roleIdStr);
		
		if (!ArrayUtils.isEmpty(ids)) {
			
			role = roleRepository
					.get(new ImmutablePair<String, Object>(RoleRepository.PK_ROLE_ID, ids[0]));
			
			roleRepository.fill(role);
		}

		return role;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createRole(Object input) {
		
		Map<String, Object> newRole = null;
		
		try {
			
			if (input instanceof Map) {
				
				Map<String, Object> inputMap = (Map<String, Object>) input;
				
				Map<String, Object> role = (Map<String, Object>) inputMap.get(FIELD_ROLE);
				
				if (!MapUtils.isEmpty(role)) {
			
					RoleRepository roleRepository = new RoleRepository(this.conn);
					
					newRole = roleRepository.create(role);
					
					if (!MapUtils.isEmpty(newRole)) {
						
						roleRepository.fill(newRole);
						
						roleRepository.commit();
					}
				}
			}
			
		} catch (EntityPersistenceException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return newRole;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> updateRole(Object input) {
		
		Map<String, Object> updatedRole = null;
		
		try {
			
			if (input instanceof Map) {

				Map<String, Object> inputMap = (Map<String, Object>) input;
				
				Map<String, Object> role = (Map<String, Object>) inputMap.get(FIELD_ROLE);
				
				if (!MapUtils.isEmpty(role)) {
					
					RoleRepository roleRepository = new RoleRepository(this.conn);
					
					updatedRole = roleRepository.update(role);
					
					if (!MapUtils.isEmpty(updatedRole)) {
						
						roleRepository.fill(updatedRole);
						
						roleRepository.commit();
					}
				}
			}
			
		} catch (EntityPersistenceException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return updatedRole;
		
	}

	public Map<String, Object> deleteRole(String id) {
		
		Map<String, Object> deletedRole = null;
		
		try {
			
			RoleRepository roleRepository = new RoleRepository(this.conn);
			
			deletedRole = roleRepository.delete(id);
			
			if (!MapUtils.isEmpty(deletedRole)) {
				
				roleRepository.commit();
			}
			
		} catch (EntityPersistenceException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return deletedRole;
	}

	// SYSTEM_PERMISSIONS

	public List<Map<String, Object>> getSystemPermissions() {
		
		SystemPermissionRepository systemPermissionRepository = new SystemPermissionRepository(this.conn);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> systemPermissions = systemPermissionRepository.gets();
		
		return systemPermissions;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getSystemPermission(String systemPermissionidStr) {
		
		Map<String, Object> systemPermission = null;
		SystemPermissionRepository systemPermissionRepository = new SystemPermissionRepository(this.conn);
		
		String[] ids = systemPermissionRepository.getId(systemPermissionidStr);
		
		if (!ArrayUtils.isEmpty(ids)) {
		
			systemPermission = systemPermissionRepository.get(new ImmutablePair<String, Object>(
					SystemPermissionRepository.PK_SYSTEM_PERMISSION_ID, ids[0]));
		}

		return systemPermission;
	}
	
	// I18N_LOCALES
	
	public List<Map<String, Object>> getLocales() {
		
		I18nLocaleRepository i18nLocaleRepository = new I18nLocaleRepository(this.conn);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> i18nLocales = i18nLocaleRepository.gets();
		
		return i18nLocales;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getLocale(String localeIdStr) {
		
		Map<String, Object> u18nLocale = null;
		I18nLocaleRepository i18nLocaleRepository = new I18nLocaleRepository(this.conn);
		
		String[] ids = i18nLocaleRepository.getId(localeIdStr);
		
		if (!ArrayUtils.isEmpty(ids)) {
		
			u18nLocale = i18nLocaleRepository.get(new ImmutablePair<String, Object>(
					I18nLocaleRepository.PK_LOCALE_CODE, ids[0]));
		}

		return u18nLocale;
	}
	
	
	// I18N_LABELS
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTranslationsByLocale(String localeId) {
		
		List<Map<String, Object>> i18nLabels = null; 
		I18nLabelRepository i18nLabelRepository = new I18nLabelRepository(this.conn);
		I18nLocaleRepository i18nLocaleRepository = new I18nLocaleRepository(this.conn);
		
		String[] idsStr = i18nLocaleRepository.getId(localeId);
		
		if (!ArrayUtils.isEmpty(idsStr)) {
		
			i18nLabels = i18nLabelRepository.gets(new ImmutablePair<String, Object>(I18nLabelRepository.PK_LOCALE_CODE, idsStr[0]));
		}
		
		return i18nLabels;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTranslationsByLocaleAndModule(String localeId, Integer moduleId) {
		
		List<Map<String, Object>> i18nLabels = null; 
		I18nLabelRepository i18nLabelRepository = new I18nLabelRepository(this.conn);
		I18nLocaleRepository i18nLocaleRepository = new I18nLocaleRepository(this.conn);
		
		String[] idsStr = i18nLocaleRepository.getId(localeId);
		
		if (!ArrayUtils.isEmpty(idsStr)) {
		
			i18nLabels = i18nLabelRepository.gets(
					new ImmutablePair<String, Object>(I18nLabelRepository.PK_LOCALE_CODE, idsStr[0]),
					new ImmutablePair<String, Object>(I18nLabelRepository.PK_MODULE_ID, moduleId));
		}
		
		return i18nLabels;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getLabelTranslated(String localeId, Integer moduleId, String label) {
		
		Map<String, Object> i18nLabel = null; 
		I18nLabelRepository i18nLabelRepository = new I18nLabelRepository(this.conn);
		I18nLocaleRepository i18nLocaleRepository = new I18nLocaleRepository(this.conn);
		
		String[] idsStr = i18nLocaleRepository.getId(localeId);
		
		if (!ArrayUtils.isEmpty(idsStr)) {
		
			i18nLabel = i18nLabelRepository.get(
					new ImmutablePair<String, Object>(I18nLabelRepository.PK_LOCALE_CODE, idsStr[0]),
					new ImmutablePair<String, Object>(I18nLabelRepository.PK_MODULE_ID, moduleId),
					new ImmutablePair<String, Object>(I18nLabelRepository.PK_LABEL, label));
		}
		
		return i18nLabel;
	}
}
