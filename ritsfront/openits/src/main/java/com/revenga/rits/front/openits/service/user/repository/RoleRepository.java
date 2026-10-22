package com.revenga.rits.front.openits.service.user.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class RoleRepository extends GenericRepository {

	private static final String ROLE_PREFIX = "Role";
	
	public static final String TABLE_NAME = "conf.roles";
	
	public static final String PK_ROLE_ID = "role_id";
	public static final String FIELD_ROLE_ID = "roleId";
	private static final String FIELD_ROLE_ALIAS = "alias";
	private static final String FIELD_ROLE_DESCRIPTION = "description";
	private static final String FIELD_ROLE_PRESET = "preset";
	private static final String FIELD_ROLE_ROLE_PERMISSIONS = "rolePermissions";
	
	private static final String FIELD_INPUT_ROLE_ALIAS = "alias";
	private static final String FIELD_INPUT_ROLE_DESCRIPTION = "description";
	private static final String FIELD_INPUT_ROLE_PRESET = "preset";
	private static final String FIELD_INPUT_ROLE_ROLE_PERMISSION_IDS = "rolePermissionIds";
	
	private static final String QRY_ROLES = String.format("SELECT role_id, alias, description, preset " +
														  "  FROM %s", TABLE_NAME);
	
	private static final String INSERT_ROLE = String.format("INSERT INTO %s(role_id, alias, description, preset, " +
															"               enabled, visible, created_at, updated_at) " +
															"    VALUES (?, ?, ?, ?, " +
														    "         ?, ?, ?, ?)", TABLE_NAME);

	private static final String UPDATE_ROLE = String.format("UPDATE %s " + 
															"   SET alias = ?, description = ?, preset = ? " +
															" WHERE role_id = ?", TABLE_NAME);

	private static final String DELETE_ROLE = String.format("DELETE FROM %s " + 
															" WHERE role_id = ?", TABLE_NAME);

	public RoleRepository(Connection conn) {
		super(conn);
		
		this.qryAll = QRY_ROLES;
		this.mapPks.put(PK_ROLE_ID, FIELD_ROLE_ID);
		this.prefix = ROLE_PREFIX;
		this.tableName = TABLE_NAME;
	}
	
	public RoleRepository(Connection conn, List<CacheRepository> cacheRepositories) {
		super(conn, cacheRepositories);
		
		this.qryAll = QRY_ROLES;
		this.mapPks.put(PK_ROLE_ID, FIELD_ROLE_ID);
		this.prefix = ROLE_PREFIX;
		this.tableName = TABLE_NAME;
	}
	
	@Override
	public void fill(List<Map<String, Object>> entities) {
		
		if (!CollectionUtils.isEmpty(entities)) {
			
			fillRolePermissions(entities);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fillRolePermissions(List<Map<String, Object>> roles) {
	
		RolePermissionRepository rolePermissionRepository = new RolePermissionRepository(this.conn);
		
		for (Map<String, Object> role : roles) {
			
			List<Map<String, Object>> systemPermissions = new ArrayList<>();
			
			Object roleIdObj = role.get(FIELD_ROLE_ID);
			
			if (roleIdObj instanceof Long) {
				
				Long roleId = (Long) roleIdObj;

				List<Map<String, Object>> rolePermissions = rolePermissionRepository
						.gets(new ImmutablePair<String, Object>(RolePermissionRepository.PK_ROLE_PERMISSION_ROLE_ID, roleId));

				if (!CollectionUtils.isEmpty(rolePermissions)) {
					
					List<Map<String, Object>> systemPermissionsList = cache(SystemPermissionRepository.class);
					
					if (!CollectionUtils.isEmpty(systemPermissionsList)) {
					
						for (Map<String, Object> rolePermission : rolePermissions) {
							
							Object systemPermissionIdObj = rolePermission.get(RolePermissionRepository.FIELD_ROLE_PERMISSION_SYSTEM_PERMISSION_ID);
							
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
					
					role.put(FIELD_ROLE_ROLE_PERMISSIONS, systemPermissions);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> create(Map<String, Object> input) throws EntityPersistenceException {
		
		Map<String, Object> newRole = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(INSERT_ROLE)) {

				StringBuilder sbSql = new StringBuilder();
				
				sbSql.append(INSERT_ROLE);
				
				try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
					
					int paramIdx = 1;
					Long id = getNextId(PK_ROLE_ID);
					
					// role_id
					stmt.setLong(paramIdx++, id);
					
					// alias
					stmt.setString(paramIdx++, (String) input.get(FIELD_INPUT_ROLE_ALIAS));
					
					// description
					stmt.setString(paramIdx++, (String) input.get(FIELD_INPUT_ROLE_DESCRIPTION));
					
					// preset
					stmt.setBoolean(paramIdx++, (Boolean) input.get(FIELD_INPUT_ROLE_PRESET));
					
					// enabled
					stmt.setBoolean(paramIdx++, true);
					
					// visible
					stmt.setBoolean(paramIdx++, true);
					
					// created_at
					stmt.setTimestamp(paramIdx++, new Timestamp(Instant.now().toEpochMilli()));
					
					// updated_at
					stmt.setTimestamp(paramIdx++, new Timestamp(Instant.now().toEpochMilli()));

					if (stmt.executeUpdate() > 0) {
					
						// role_permissions
						Object systemPermissionIdsObj = input.get(FIELD_INPUT_ROLE_ROLE_PERMISSION_IDS);
						
						if (systemPermissionIdsObj instanceof List) {
							
							List<String> systemPermissionIdsStr = (List<String>) systemPermissionIdsObj;
							
							if (!CollectionUtils.isEmpty(systemPermissionIdsStr)) {
								
								SystemPermissionRepository systemPermissionRepository = new SystemPermissionRepository(this.conn);
								RolePermissionRepository rolePermissionRepository = new RolePermissionRepository(this.conn);
								
								for (String systemPermissionIdStr : systemPermissionIdsStr) {
									
									Map<String, Object> rolePermission = new HashMap<>();
									
									String[] ids = systemPermissionRepository.getId(systemPermissionIdStr);
											
									if (!ArrayUtils.isEmpty(ids) && NumberUtils.isCreatable(ids[0])) {
									
										String rolePermissionRepositoryIdStr = rolePermissionRepository.getIdStr(String.valueOf(id), ids[0]);
										
										if (!StringUtils.isEmpty(rolePermissionRepositoryIdStr)) {
									
											rolePermission.put(FIELD_ID, rolePermissionRepositoryIdStr);
											
											rolePermissionRepository.create(rolePermission);
										}
									}
								}
							}
						}
						
						newRole = get(new ImmutablePair<String, Object>(PK_ROLE_ID, id));
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

		return newRole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> update(Map<String, Object> input) throws EntityPersistenceException{
		
		Map<String, Object> updatedRole = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(UPDATE_ROLE)) {
				
				String idStr = (String) input.get(FIELD_ID);
				
				if (!StringUtils.isEmpty(idStr)) {
					
					String[] idsRole = getId(idStr);
					
					if (!ArrayUtils.isEmpty(idsRole)) {
						
						Long id = Long.valueOf(idsRole[0]);
						
						Map<String, Object> role = get(new ImmutablePair<String, Object>(RoleRepository.PK_ROLE_ID, id));
						
						if (MapUtils.isEmpty(role)) {
						
							throw new  EntityPersistenceException(Operation.UPDATE, this.getClass(), "Role not found");
						}
						
						fill(role);
						
						StringBuilder sbSql = new StringBuilder();
						
						sbSql.append(UPDATE_ROLE);
						
						try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
							
							int paramIdx = 1;
							
							// alias
							stmt.setString(paramIdx++, (String) (input.get(FIELD_INPUT_ROLE_ALIAS) != null ? 
									input.get(FIELD_INPUT_ROLE_ALIAS) : role.get(FIELD_ROLE_ALIAS)));
							
							// description
							stmt.setString(paramIdx++, (String) (input.get(FIELD_INPUT_ROLE_DESCRIPTION) != null ? 
									input.get(FIELD_INPUT_ROLE_DESCRIPTION) : role.get(FIELD_ROLE_DESCRIPTION)));
							
							// preset
							stmt.setBoolean(paramIdx++, (Boolean) (input.get(FIELD_INPUT_ROLE_PRESET) != null ? 
									input.get(FIELD_INPUT_ROLE_PRESET) : role.get(FIELD_ROLE_PRESET)));
							
							// role_id
							stmt.setLong(paramIdx++, id);
							
							// Treatment role permissions
							
							if (input.get(FIELD_INPUT_ROLE_ROLE_PERMISSION_IDS) instanceof List) {
								
								List<String> systemPermissionIdsStr = (List<String>) input.get(FIELD_INPUT_ROLE_ROLE_PERMISSION_IDS);
								
								if (systemPermissionIdsStr != null) {
									
									RolePermissionRepository rolePermissionRepository = new RolePermissionRepository(this.conn);
									
									// Delete old role permissions
									List<Map<String, Object>> rolePermissions = (List<Map<String, Object>>) role.get(FIELD_ROLE_ROLE_PERMISSIONS);
									
									if (!CollectionUtils.isEmpty(rolePermissions)) {
										
										for (Map<String, Object> rolePermission : rolePermissions) {
											
											String rolePermissionIdStr = rolePermissionRepository.getIdStr(String.valueOf(role.get(FIELD_ROLE_ID)),
													String.valueOf(rolePermission.get(
															RolePermissionRepository.FIELD_ROLE_PERMISSION_SYSTEM_PERMISSION_ID)));
											
											if (!StringUtils.isEmpty(rolePermissionIdStr)) {
												
												rolePermissionRepository.delete(rolePermissionIdStr);
											}
										}
									}
									
									// Create new role permissions
									if (!CollectionUtils.isEmpty(systemPermissionIdsStr)) {
										
										SystemPermissionRepository systemPermissionRepository = new SystemPermissionRepository(this.conn);
										
										for (String systemPermissionIdStr : systemPermissionIdsStr) {
											
											Map<String, Object> rolePermission = new HashMap<>();
											
											String[] ids = systemPermissionRepository.getId(systemPermissionIdStr);
													
											if (!ArrayUtils.isEmpty(ids) && NumberUtils.isCreatable(ids[0])) {
											
												String rolePermissionRepositoryIdStr = rolePermissionRepository.getIdStr(String.valueOf(id), ids[0]);
												
												if (!StringUtils.isEmpty(rolePermissionRepositoryIdStr)) {
											
													rolePermission.put(FIELD_ID, rolePermissionRepositoryIdStr);
													
													rolePermissionRepository.create(rolePermission);
												}
											}
										}
									}
								}
							}
							
							if (stmt.executeUpdate() > 0) {
								
								updatedRole = get(new ImmutablePair<String, Object>(PK_ROLE_ID, id));			
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

		return updatedRole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> delete(String input) throws EntityPersistenceException {
		
		Map<String, Object> deletedRole = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(DELETE_ROLE) && !StringUtils.isEmpty(input)) {
				
				String[] idsRole = getId(input);
				
				if (!ArrayUtils.isEmpty(idsRole)) {
					
					Long id = Long.valueOf(idsRole[0]);
					
					Map<String, Object> role = get(new ImmutablePair<String, Object>(RoleRepository.PK_ROLE_ID, id));
					
					if (MapUtils.isEmpty(role)) {
						
						throw new  EntityPersistenceException(Operation.DELETE, this.getClass(), "Role not found");
					}
					
					fill(role);
					
					UserRepository userRepository = new UserRepository(this.conn);
					
					List<Map<String, Object>> users = userRepository.gets(new ImmutablePair<String, Object>(UserRepository.FK_ROLE_ID, id));
					
					if (!CollectionUtils.isEmpty(users)) {
						
						for (Map<String, Object> user : users) {
							
							userRepository.delete((String) user.get(FIELD_ID));
						}
					}
					
					StringBuilder sbSql = new StringBuilder();
					
					sbSql.append(DELETE_ROLE);
					
					try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
						
						int paramIdx = 1;

						// role_id
						stmt.setLong(paramIdx++, id);
						
						// Delete old role permissions
						RolePermissionRepository rolePermissionRepository = new RolePermissionRepository(this.conn);
						
						List<Map<String, Object>> rolePermissions = (List<Map<String, Object>>) role.get(FIELD_ROLE_ROLE_PERMISSIONS);
						
						if (!CollectionUtils.isEmpty(rolePermissions)) {
							
							for (Map<String, Object> rolePermission : rolePermissions) {
								
								String rolePermissionIdStr = rolePermissionRepository.getIdStr(String.valueOf(role.get(FIELD_ROLE_ID)),
										String.valueOf(rolePermission.get(
												RolePermissionRepository.FIELD_ROLE_PERMISSION_SYSTEM_PERMISSION_ID)));
								
								if (!StringUtils.isEmpty(rolePermissionIdStr)) {
									
									rolePermissionRepository.delete(rolePermissionIdStr);
								}
							}
						}
														
						if (stmt.executeUpdate() > 0) {
							
							deletedRole = role;		
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

		return deletedRole;
	}
}
