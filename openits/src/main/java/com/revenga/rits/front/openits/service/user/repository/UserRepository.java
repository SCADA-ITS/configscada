package com.revenga.rits.front.openits.service.user.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.util.CollectionUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserRepository extends GenericRepository {

	public static final String USER_PREFIX = "User";
	public static final String TABLE_NAME = "conf.users";
    
	public static final String PK_USER_ID = "user_id";
	public static final String FK_ROLE_ID = "role_id";
	
	public static final String FIELD_DB_NAME = "name_";
	
	public static final String FIELD_USER_ID = "userId";
	private static final String FIELD_USER_ROLE_ID = "roleId";
	private static final String FIELD_USER_LOCALE_CODE = "localeCode";
	private static final String FIELD_USER_NAME = "name";
	private static final String FIELD_USER_PASSWORD = "password";
	private static final String FIELD_USER_PASSWORD_EXPIRED_AT = "passwordExpiredAt";
	private static final String FIELD_USER_SESSION_EXPIRE_TIME_S = "sessionExpireTimeS";
	private static final String FIELD_USER_LOCKED_UNTIL = "lockedUntil";
	private static final String FIELD_USER_PRESET = "preset";
	private static final String FIELD_USER_ALLOW_MULTIPLE_SESSIONS = "allowMultipleSessions";
	private static final String FIELD_USER_FULL_NAME = "fullName";
	private static final String FIELD_USER_EMAIL = "email";
	
	private static final String FIELD_USER_LOCALE = "locale";
	private static final String FIELD_USER_ROLE = "role";
	private static final String FIELD_USER_USER_PERMISSIONS = "userPermissions";
	private static final String FIELD_USER_MANAGEMENT_AREAS = "managementAreas";
	
	private static final String FIELD_INPUT_USER_ROLE_ID = "roleId";
	private static final String FIELD_INPUT_USER_NAME = "name";
	private static final String FIELD_INPUT_USER_PASSWORD = "password";
	private static final String FIELD_INPUT_USER_PASSWORD_EXPIRED_AT = "passwordExpiredAt";
	private static final String FIELD_INPUT_USER_SESSION_EXPIRE_TIME_S = "sessionExpireTimeS";
	private static final String FIELD_INPUT_USER_LOCKED_UNTIL = "lockedUntil";
	private static final String FIELD_INPUT_USER_PRESET = "preset";
	private static final String FIELD_INPUT_USER_ALLOW_MULTIPLE_SESSIONS = "allowMultipleSessions";
	private static final String FIELD_INPUT_USER_FULL_NAME = "fullName";
	private static final String FIELD_INPUT_USER_EMAIL = "email";
	private static final String FIELD_INPUT_USER_LOCALE_ID = "localeId";
	private static final String FIELD_INPUT_USER_USER_PERMISSION_IDS = "userPermissionIds";
	
	private static final String QRY_USERS = String.format("SELECT user_id, role_id, name_, password_, password_expired_at, " +
														  "       session_expire_time_s, locked_until, preset, allow_multiple_sessions, " +
														  "       full_name, email, locale_code " +
														  "  FROM %s", TABLE_NAME);
	
	private static final String INSERT_USER = String.format("INSERT INTO %s(user_id, role_id, name_, password_, password_expired_at, " +
														     "       session_expire_time_s, locked_until, preset, allow_multiple_sessions, " +
														     "       full_name, email, locale_code, enabled, visible, created_at, updated_at) " +
														     " VALUES (?, ?, ?, ?, ?, " +
														     "         ?, ?, ?, ?, " +
														     "         ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME);
	
	private static final String UPDATE_USER = String.format("UPDATE %s " + 
															 "   SET role_id = ?, name_ = ?, password_ = ?, password_expired_at = ?, " +
															 "       session_expire_time_s = ?, locked_until = ?, preset = ?, allow_multiple_sessions = ?, " +
															 "       full_name = ?, email = ?, locale_code = ? " +
															 " WHERE user_id = ?", TABLE_NAME);
	
	private static final String DELETE_USER = String.format("DELETE FROM %s " + 
			 												" WHERE user_id = ?", TABLE_NAME);

	public UserRepository(Connection conn) {
		super(conn);
		
		this.qryAll = QRY_USERS;
		this.mapPks.put(PK_USER_ID, FIELD_USER_ID);
		this.prefix = USER_PREFIX;
		this.tableName = TABLE_NAME;
		
//		this.remapedFields = Map.ofEntries(
//				  new AbstractMap.SimpleEntry<String, String>("full_name", "fullname")
//				);
	}
	
	public UserRepository(Connection conn, List<CacheRepository> cacheRepositories) {
		super(conn, cacheRepositories);
		
		this.qryAll = QRY_USERS;
		this.mapPks.put(PK_USER_ID, FIELD_USER_ID);
		this.prefix = USER_PREFIX;
		this.tableName = TABLE_NAME;
	}
	
	@Override
	public void fill(List<Map<String, Object>> entities) {
		
		if (!CollectionUtils.isEmpty(entities)) {
			
			cache(I18nLocaleRepository.class);
			cache(SystemPermissionRepository.class);
			cache(ManagementAreaRepository.class);
			
			for (Map<String, Object> entity : entities) {
				
				fillEntity(entity, RoleRepository.class, 
						UserRepository.FIELD_USER_ROLE_ID, UserRepository.FIELD_USER_ROLE, 
						RoleRepository.FIELD_ROLE_ID, RoleRepository.PK_ROLE_ID);
				fillEntity(entity, I18nLocaleRepository.class, 
						UserRepository.FIELD_USER_LOCALE_CODE, UserRepository.FIELD_USER_LOCALE, 
						I18nLocaleRepository.FIELD_LOCALE_CODE,	I18nLocaleRepository.PK_LOCALE_CODE);
			}
			
			fillUserPermissions(entities);
			
			fillUserManagementAreas(entities);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fillUserPermissions(List<Map<String, Object>> users) {
	
		UserPermissionRepository userPermissionRepository = new UserPermissionRepository(this.conn);
		
		for (Map<String, Object> user : users) {
			
			List<Map<String, Object>> systemPermissions = new ArrayList<>();
			
			Object userIdObj = user.get(FIELD_USER_ID);
			
			if (userIdObj instanceof Long) {
				
				Long userId = (Long) userIdObj;

				List<Map<String, Object>> userPermissions = userPermissionRepository
						.gets(new ImmutablePair<String, Object>(UserPermissionRepository.PK_USER_PERMISSION_USER_ID, userId));

				if (!CollectionUtils.isEmpty(userPermissions)) {
					
					List<Map<String, Object>> systemPermissionsList = cache(SystemPermissionRepository.class);
					
					if (!CollectionUtils.isEmpty(systemPermissionsList)) {
						
						for (Map<String, Object> userPermission : userPermissions) {
							
							Object systemPermissionIdObj = userPermission.get(SystemPermissionRepository.FIELD_SYSTEM_PERMISSION_ID);
							
							if (systemPermissionIdObj instanceof Long) {
								
								Long systemPermissionId = (Long) systemPermissionIdObj;
								
								Map<String, Object> systemPermission = systemPermissionsList.stream()
										.filter(e -> systemPermissionId
												.equals(e.get(SystemPermissionRepository.FIELD_SYSTEM_PERMISSION_ID)))
										.findFirst().orElse(null);
								
								if (systemPermission != null) {
									
									systemPermissions.add(systemPermission);
								}
							}
						}
					}
				}
				
				if (!CollectionUtils.isEmpty(systemPermissions)) {
					
					user.put(FIELD_USER_USER_PERMISSIONS, systemPermissions);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fillUserManagementAreas(List<Map<String, Object>> users) {
	
		ManagementAreaUserRepository userManagementAreaRepository = new ManagementAreaUserRepository(this.conn);
		
		for (Map<String, Object> user : users) {
			
			List<Map<String, Object>> managementAreas = new ArrayList<>();
			
			Object userIdObj = user.get(FIELD_USER_ID);
			
			if (userIdObj instanceof Long) {
				
				Long userId = (Long) userIdObj;

				List<Map<String, Object>> userManagementAreas = userManagementAreaRepository
						.gets(new ImmutablePair<String, Object>(ManagementAreaUserRepository.PK_MANAGEMENT_AREA_USER_USER_ID, userId));

				if (!CollectionUtils.isEmpty(userManagementAreas)) {
					
					List<Map<String, Object>> managementAreaList = cache(ManagementAreaRepository.class);
					
					if (!CollectionUtils.isEmpty(managementAreaList)) {
						
						for (Map<String, Object> userManagementArea : userManagementAreas) {
							
							Object managementAreaIdObj = userManagementArea.get(ManagementAreaRepository.FIELD_MANAGEMENT_AREA_ID);
							
							if (managementAreaIdObj instanceof Long) {
								
								Long managementAreaId = (Long) managementAreaIdObj;
								
								Map<String, Object> managementArea = managementAreaList.stream()
										.filter(e -> managementAreaId
												.equals(e.get(ManagementAreaRepository.FIELD_MANAGEMENT_AREA_ID)))
										.findFirst().orElse(null);
								
								if (managementArea != null) {
									
									managementAreas.add(managementArea);
								}
							}
						}
					}
				}
				
				if (!CollectionUtils.isEmpty(managementAreas)) {
					
					user.put(FIELD_USER_MANAGEMENT_AREAS, managementAreas);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> create(Map<String, Object> input) throws EntityPersistenceException {
		
		Map<String, Object> newUser = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(INSERT_USER)) {

				StringBuilder sbSql = new StringBuilder();
				
				sbSql.append(INSERT_USER);
				
				try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
					
					int paramIdx = 1;
					Long id = getNextId(PK_USER_ID);
					
					// user_id
					stmt.setLong(paramIdx++, id);
					
					// role_id
					RoleRepository roleRepository = new RoleRepository(this.conn);
					
					String roleIdStr = (String) input.get(FIELD_INPUT_USER_ROLE_ID);
					
					if (!StringUtils.isEmpty(roleIdStr)) {
					
						String[] ids = roleRepository.getId(roleIdStr);
						
						if (!ArrayUtils.isEmpty(ids) && NumberUtils.isCreatable(ids[0])) {
				
							stmt.setLong(paramIdx++, Long.valueOf(ids[0]));
						}
					}
					
					// name_
					stmt.setString(paramIdx++, (String) input.get(FIELD_INPUT_USER_NAME));
					
					// password_
					stmt.setString(paramIdx++, (String) input.get(FIELD_INPUT_USER_PASSWORD));
					
					// password_expired_at
					stmt.setTimestamp(paramIdx++, input.get(FIELD_INPUT_USER_PASSWORD_EXPIRED_AT) != null ? new Timestamp((Long) input.get(FIELD_INPUT_USER_PASSWORD_EXPIRED_AT)) : null);
					
					// session_expired_time_s
					stmt.setObject(paramIdx++, input.get(FIELD_INPUT_USER_SESSION_EXPIRE_TIME_S), Types.INTEGER);
					
					// locked_until
					stmt.setTimestamp(paramIdx++, input.get(FIELD_INPUT_USER_LOCKED_UNTIL) != null ? new Timestamp((Long) input.get(FIELD_INPUT_USER_LOCKED_UNTIL)) : null);
					
					// preset
					stmt.setBoolean(paramIdx++, (Boolean) input.get(FIELD_INPUT_USER_PRESET));
					
					// allow_multiple_sessions
					stmt.setBoolean(paramIdx++, (Boolean) input.get(FIELD_INPUT_USER_ALLOW_MULTIPLE_SESSIONS));
					
					// full_name
					stmt.setString(paramIdx++, (String) input.get(FIELD_INPUT_USER_FULL_NAME));
					
					// email
					stmt.setString(paramIdx++, (String) input.get(FIELD_INPUT_USER_EMAIL));
					
					// Locale
					I18nLocaleRepository i18nlocaleRepository = new I18nLocaleRepository(this.conn);
					
					String localeIdStr = (String) input.get(FIELD_INPUT_USER_LOCALE_ID);
					
					if (!StringUtils.isEmpty(localeIdStr)) {
					
						String[] ids = i18nlocaleRepository.getId(localeIdStr);
						
						if (!ArrayUtils.isEmpty(ids) && !StringUtils.isEmpty(ids[0])) {
				
							stmt.setString(paramIdx++, ids[0]);
						}
					}
					
					// enabled
					stmt.setBoolean(paramIdx++, true);
					
					// visible
					stmt.setBoolean(paramIdx++, true);
					
					// created_at
					stmt.setTimestamp(paramIdx++, new Timestamp(Instant.now().toEpochMilli()));
					
					// updated_at
					stmt.setTimestamp(paramIdx++, new Timestamp(Instant.now().toEpochMilli()));

					if (stmt.executeUpdate() > 0) {
					
						// user_permissions
						Object systemPermissionIdsObj = input.get(FIELD_INPUT_USER_USER_PERMISSION_IDS);
						
						if (systemPermissionIdsObj instanceof List) {
							
							List<String> systemPermissionIdsStr = (List<String>) systemPermissionIdsObj;
							
							if (!CollectionUtils.isEmpty(systemPermissionIdsStr)) {
								
								SystemPermissionRepository systemPermissionRepository = new SystemPermissionRepository(this.conn);
								UserPermissionRepository userPermissionRepository = new UserPermissionRepository(this.conn);
								
								for (String systemPermissionIdStr : systemPermissionIdsStr) {
									
									Map<String, Object> userPermission = new HashMap<>();
									
									String[] ids = systemPermissionRepository.getId(systemPermissionIdStr);
											
									if (!ArrayUtils.isEmpty(ids) && NumberUtils.isCreatable(ids[0])) {
									
										String userPermissionRepositoryIdStr = userPermissionRepository.getIdStr(String.valueOf(id), ids[0]);
										
										if (!StringUtils.isEmpty(userPermissionRepositoryIdStr)) {
									
											userPermission.put(FIELD_ID, userPermissionRepositoryIdStr);
											
											userPermissionRepository.create(userPermission);
										}
									}
								}
							}
						}
						
						newUser = get(new ImmutablePair<String, Object>(PK_USER_ID, id));
					}
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

				try {
					
					conn.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			
			throw new EntityPersistenceException(Operation.CREATE, this.getClass());
		}

		return newUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> update(Map<String, Object> input) throws EntityPersistenceException {
		
		Map<String, Object> updatedUser = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(UPDATE_USER)) {
				
				String idStr = (String) input.get(FIELD_ID);
				
				if (!StringUtils.isEmpty(idStr)) {
					
					String[] idsUser = getId(idStr);
					
					if (!ArrayUtils.isEmpty(idsUser)) {
						
						Long id = Long.valueOf(idsUser[0]);
						
						Map<String, Object> user = get(new ImmutablePair<String, Object>(UserRepository.PK_USER_ID, id));
						
						if (MapUtils.isEmpty(user)) {
						
							throw new  EntityPersistenceException(Operation.UPDATE, this.getClass(), "User not found");
						}
						
						fill(user);
						
						StringBuilder sbSql = new StringBuilder();
						
						sbSql.append(UPDATE_USER);
						
						try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
							
							int paramIdx = 1;
							
							// role_id
							String roleIdStr = (String) input.get(FIELD_INPUT_USER_ROLE_ID);
							
							if (!StringUtils.isEmpty(roleIdStr)) {
								
								RoleRepository roleRepository = new RoleRepository(this.conn); 
								
								String[] idsRole = roleRepository.getId(roleIdStr);
								
								if (!ArrayUtils.isEmpty(idsRole)) {
								
									stmt.setLong(paramIdx++, Long.valueOf(idsRole[0]));
								}
							}
							else {
								
								stmt.setLong(paramIdx++, (Long) user.get(FIELD_USER_ROLE_ID));
							}
						
							// name_
							stmt.setString(paramIdx++, (String) (input.get(FIELD_INPUT_USER_NAME) != null ? 
									input.get(FIELD_INPUT_USER_NAME) : user.get(FIELD_USER_NAME)));
							
							// password_
							stmt.setString(paramIdx++, (String) (input.get(FIELD_INPUT_USER_PASSWORD) != null ? 
									input.get(FIELD_INPUT_USER_PASSWORD) : user.get(FIELD_USER_PASSWORD)));
							
							// password_expired_at
							if (input.get(FIELD_INPUT_USER_PASSWORD_EXPIRED_AT) != null) {
								
								stmt.setTimestamp(paramIdx++, input.get(FIELD_INPUT_USER_PASSWORD_EXPIRED_AT) != null ? 
										new Timestamp((Long) input.get(FIELD_INPUT_USER_PASSWORD_EXPIRED_AT)) : null);
							}
							else {
								
								stmt.setTimestamp(paramIdx++, user.get(FIELD_USER_PASSWORD_EXPIRED_AT) != null ? 
										new Timestamp((Long) user.get(FIELD_USER_PASSWORD_EXPIRED_AT)) : null);
							}
							
							// session_expired_time_s
							stmt.setObject(paramIdx++, (Integer) (input.get(FIELD_INPUT_USER_SESSION_EXPIRE_TIME_S) != null ? 
									input.get(FIELD_INPUT_USER_SESSION_EXPIRE_TIME_S) : user.get(FIELD_USER_SESSION_EXPIRE_TIME_S)), Types.INTEGER);
							
							// locked_until
							if (input.get(FIELD_INPUT_USER_LOCKED_UNTIL) != null) {
								
								stmt.setTimestamp(paramIdx++, input.get(FIELD_INPUT_USER_LOCKED_UNTIL) != null ? 
										new Timestamp((Long) input.get(FIELD_INPUT_USER_LOCKED_UNTIL)) : null);
							}
							else {
								
								stmt.setTimestamp(paramIdx++, user.get(FIELD_USER_LOCKED_UNTIL) != null ? 
										new Timestamp((Long) user.get(FIELD_USER_LOCKED_UNTIL)) : null);
							}
							
							// preset
							stmt.setBoolean(paramIdx++, (Boolean) (input.get(FIELD_INPUT_USER_PRESET) != null ? 
									input.get(FIELD_INPUT_USER_PRESET) : user.get(FIELD_USER_PRESET)));
							
							// allow_multiple_sessions
							stmt.setBoolean(paramIdx++, (Boolean) (input.get(FIELD_INPUT_USER_ALLOW_MULTIPLE_SESSIONS) != null ? 
									input.get(FIELD_INPUT_USER_ALLOW_MULTIPLE_SESSIONS) : user.get(FIELD_USER_ALLOW_MULTIPLE_SESSIONS)));
							
							// full_name
							stmt.setString(paramIdx++, (String) (input.get(FIELD_INPUT_USER_FULL_NAME) != null ? 
									input.get(FIELD_INPUT_USER_FULL_NAME) : user.get(FIELD_USER_FULL_NAME)));
							
							// email
							stmt.setString(paramIdx++, (String) (input.get(FIELD_INPUT_USER_EMAIL) != null ? 
									input.get(FIELD_INPUT_USER_EMAIL) : user.get(FIELD_USER_EMAIL)));
							
							// locale_code
							String localeIdStr = (String) input.get(FIELD_INPUT_USER_LOCALE_ID);
							
							if (!StringUtils.isEmpty(localeIdStr)) {
								
								I18nLocaleRepository i18nlocaleRepository = new I18nLocaleRepository(this.conn);
								
								String[] idsLocale = i18nlocaleRepository.getId(localeIdStr);
								
								if (!ArrayUtils.isEmpty(idsLocale)) {
									
									stmt.setString(paramIdx++, idsLocale[0]);
								}
							}
							else {
								
								stmt.setString(paramIdx++, (String) user.get(FIELD_USER_LOCALE_CODE));
							}
							
							// user_id
							stmt.setLong(paramIdx++, id);
							
							// Treatment user permissions
							
							if (input.get(FIELD_INPUT_USER_USER_PERMISSION_IDS) instanceof List) {
								
								List<String> systemPermissionIdsStr = (List<String>) input.get(FIELD_INPUT_USER_USER_PERMISSION_IDS);
								
								if (systemPermissionIdsStr != null) {
									
									UserPermissionRepository userPermissionRepository = new UserPermissionRepository(this.conn);
									
									// Delete old user permissions
									List<Map<String, Object>> userPermissions = (List<Map<String, Object>>) user.get(FIELD_USER_USER_PERMISSIONS);
									
									if (!CollectionUtils.isEmpty(userPermissions)) {
										
										for (Map<String, Object> userPermission : userPermissions) {
											
											String userPermissionIdStr = userPermissionRepository.getIdStr(String.valueOf(user.get(FIELD_USER_ID)),
													String.valueOf(userPermission.get(
															UserPermissionRepository.FIELD_USER_PERMISSION_SYSTEM_PERMISSION_ID)));
											
											if (!StringUtils.isEmpty(userPermissionIdStr)) {
												
												userPermissionRepository.delete(userPermissionIdStr);
											}
										}
									}
									
									// Create new user permissions
									if (!CollectionUtils.isEmpty(systemPermissionIdsStr)) {
										
										SystemPermissionRepository systemPermissionRepository = new SystemPermissionRepository(this.conn);
										
										for (String systemPermissionIdStr : systemPermissionIdsStr) {
											
											Map<String, Object> userPermission = new HashMap<>();
											
											String[] ids = systemPermissionRepository.getId(systemPermissionIdStr);
													
											if (!ArrayUtils.isEmpty(ids) && NumberUtils.isCreatable(ids[0])) {
											
												String userPermissionRepositoryIdStr = userPermissionRepository.getIdStr(String.valueOf(id), ids[0]);
												
												if (!StringUtils.isEmpty(userPermissionRepositoryIdStr)) {
											
													userPermission.put(FIELD_ID, userPermissionRepositoryIdStr);
													
													userPermissionRepository.create(userPermission);
												}
											}
										}
									}
								}
							}
							
							if (stmt.executeUpdate() > 0) {
								
								updatedUser = get(new ImmutablePair<String, Object>(PK_USER_ID, id));			
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

				try {
					
					conn.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			
			throw new EntityPersistenceException(Operation.UPDATE, this.getClass());
		}

		return updatedUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> delete(String input) throws EntityPersistenceException {
		
		Map<String, Object> deletedUser = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(DELETE_USER) && !StringUtils.isEmpty(input)) {
				
				String[] idsUser = getId(input);
				
				if (!ArrayUtils.isEmpty(idsUser)) {
					
					Long id = Long.valueOf(idsUser[0]);
					
					Map<String, Object> user = get(new ImmutablePair<String, Object>(UserRepository.PK_USER_ID, id));
					
					if (MapUtils.isEmpty(user)) {
						
						throw new  EntityPersistenceException(Operation.DELETE, this.getClass(), "User not found");
					}
					
					fill(user);
					
					StringBuilder sbSql = new StringBuilder();
					
					sbSql.append(DELETE_USER);
					
					try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
						
						int paramIdx = 1;

						// user_id
						stmt.setLong(paramIdx++, id);
						
						// Delete old user permissions
						UserPermissionRepository userPermissionRepository = new UserPermissionRepository(this.conn);
						
						List<Map<String, Object>> userPermissions = (List<Map<String, Object>>) user.get(FIELD_USER_USER_PERMISSIONS);
						
						if (!CollectionUtils.isEmpty(userPermissions)) {
							
							for (Map<String, Object> userPermission : userPermissions) {
								
								String userPermissionIdStr = userPermissionRepository.getIdStr(String.valueOf(user.get(FIELD_USER_ID)),
										String.valueOf(userPermission.get(
												UserPermissionRepository.FIELD_USER_PERMISSION_SYSTEM_PERMISSION_ID)));
								
								if (!StringUtils.isEmpty(userPermissionIdStr)) {
									
									userPermissionRepository.delete(userPermissionIdStr);
								}
							}
						}
														
						if (stmt.executeUpdate() > 0) {
							
							deletedUser = user;		
						}
					}
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

				try {
					
					conn.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			
			throw new EntityPersistenceException(Operation.DELETE, this.getClass());
		}

		return deletedUser;
	}
}
