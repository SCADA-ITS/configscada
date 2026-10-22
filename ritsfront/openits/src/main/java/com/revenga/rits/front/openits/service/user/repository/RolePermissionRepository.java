package com.revenga.rits.front.openits.service.user.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RolePermissionRepository extends GenericRepository {

	public static final String ROLE_PREFIX = "RolePermission";
	public static final String TABLE_NAME = "conf.role_permissions";
	
	public static final String PK_ROLE_PERMISSION_ROLE_ID = "role_id";
	public static final String PK_ROLE_PERMISSION_SYSTEM_PERMISSION_ID = "system_permission_id";
	
	public static final String FIELD_ROLE_PERMISSION_ROLE_ID = "roleId";
	public static final String FIELD_ROLE_PERMISSION_SYSTEM_PERMISSION_ID = "systemPermissionId";
	
	public static final String FIELD_INPUT_ROLE_PERMISSION_ROLE_ID = "roleId";
	public static final String FIELD_INPUT_ROLE_PERMISSION_SYSTEM_PERMISSION_ID = "systemPermissionId";
	
	private static final String QRY_ROLE_PERMISSIONS = String.format("SELECT role_id, system_permission_id " +
													   				 "  FROM %s", TABLE_NAME);
	
	private static final String INSERT_ROLE_PERMISSION = String.format("INSERT INTO %s (role_id, system_permission_id, " +
																	   "       enabled, visible, created_at, updated_at )" +
																	   "     VALUES(?, ?, ?, ?, ?, ?)", TABLE_NAME);
	
	private static final String DELETE_ROLE_PERMISSION = String.format("DELETE FROM %s WHERE role_id = ? AND system_permission_id = ?", TABLE_NAME);
			   

	public RolePermissionRepository(Connection conn) {
		super(conn);
		
		this.qryAll = QRY_ROLE_PERMISSIONS;
		this.mapPks.put(PK_ROLE_PERMISSION_ROLE_ID, FIELD_ROLE_PERMISSION_ROLE_ID);
		this.mapPks.put(PK_ROLE_PERMISSION_SYSTEM_PERMISSION_ID, FIELD_ROLE_PERMISSION_SYSTEM_PERMISSION_ID);
		this.prefix = ROLE_PREFIX;
		this.tableName = TABLE_NAME;
	}

	public RolePermissionRepository(Connection conn, List<CacheRepository> cacheRepositories) {
		super(conn, cacheRepositories);
		
		this.qryAll = QRY_ROLE_PERMISSIONS;
		this.mapPks.put(PK_ROLE_PERMISSION_ROLE_ID, FIELD_ROLE_PERMISSION_ROLE_ID);
		this.mapPks.put(PK_ROLE_PERMISSION_SYSTEM_PERMISSION_ID, FIELD_ROLE_PERMISSION_SYSTEM_PERMISSION_ID);
		this.prefix = ROLE_PREFIX;
		this.tableName = TABLE_NAME;
	}
	
	@Override
	public void fill(List<Map<String, Object>> roles) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> create(Map<String, Object> input) throws EntityPersistenceException {
		
		Map<String, Object> newRolePermission = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(INSERT_ROLE_PERMISSION)) {

				StringBuilder sbSql = new StringBuilder();
				
				sbSql.append(INSERT_ROLE_PERMISSION);
				
				try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
					
					int paramIdx = 1;

					String idStr = (String) input.get(FIELD_ID);
					
					String[] idsStr = getId(idStr);
					
					if (!ArrayUtils.isEmpty(idsStr) && idsStr.length == 2) {
					
						// role_id
						Long roleId = Long.valueOf(idsStr[0]);
						Long systemPermissionId = Long.valueOf(idsStr[1]); 
						
						stmt.setLong(paramIdx++, roleId);
						
						// system_permission_id
						stmt.setLong(paramIdx++, systemPermissionId);
						
						// enabled
						stmt.setBoolean(paramIdx++, true);
						
						// visible
						stmt.setBoolean(paramIdx++, true);
						
						// created_at
						stmt.setTimestamp(paramIdx++, new Timestamp(Instant.now().toEpochMilli()));
						
						// updated_at
						stmt.setTimestamp(paramIdx++, new Timestamp(Instant.now().toEpochMilli()));

						if (stmt.executeUpdate() > 0) {
						
							newRolePermission = get(new ImmutablePair<String, Object>(PK_ROLE_PERMISSION_ROLE_ID, roleId),
									new ImmutablePair<String, Object>(PK_ROLE_PERMISSION_SYSTEM_PERMISSION_ID, systemPermissionId));
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
			
			throw new EntityPersistenceException(Operation.CREATE, this.getClass());
		}

		return newRolePermission;
	}

	@Override
	public Map<String, Object> update(Map<String, Object> input) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> delete(String input) throws EntityPersistenceException {
		
		Map<String, Object> deletedRolePermission = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(DELETE_ROLE_PERMISSION) && !StringUtils.isEmpty(input)) {
				
				String[] ids = getId(input);
				
				if (!ArrayUtils.isEmpty(ids)) {
					
					Long roleId = Long.valueOf(ids[0]);
					Long systemPermissionId = Long.valueOf(ids[1]);
					
					Map<String, Object> rolePermission = get(
							new ImmutablePair<String, Object>(PK_ROLE_PERMISSION_ROLE_ID, roleId),
							new ImmutablePair<String, Object>(PK_ROLE_PERMISSION_SYSTEM_PERMISSION_ID, systemPermissionId));
					
					if (MapUtils.isEmpty(rolePermission)) {
					
						throw new  EntityPersistenceException(Operation.DELETE, this.getClass(), "Role permission not found");
					}
						
					StringBuilder sbSql = new StringBuilder();
					
					sbSql.append(DELETE_ROLE_PERMISSION);
					
					try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
						
						int paramIdx = 1;

						// role_id
						stmt.setLong(paramIdx++, roleId);
						
						// system_permission_id
						stmt.setLong(paramIdx++, systemPermissionId);
						
														
						if (stmt.executeUpdate() > 0) {
							
							deletedRolePermission = rolePermission;		
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

		return deletedRolePermission;
	}
}
