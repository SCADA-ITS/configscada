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
public class UserPermissionRepository extends GenericRepository {

	public static final String USER_PREFIX = "UserPermission";
	public static final String TABLE_NAME = "conf.user_permissions";
	
	public static final String PK_USER_PERMISSION_USER_ID = "user_id";
	public static final String PK_USER_PERMISSION_SYSTEM_PERMISSION_ID = "system_permission_id";
	
	public static final String FIELD_USER_PERMISSION_USER_ID = "userId";
	public static final String FIELD_USER_PERMISSION_SYSTEM_PERMISSION_ID = "systemPermissionId";
	
	public static final String FIELD_INPUT_USER_PERMISSION_USER_ID = "userId";
	public static final String FIELD_INPUT_USER_PERMISSION_SYSTEM_PERMISSION_ID = "systemPermissionId";
	
	private static final String QRY_USER_PERMISSIONS = String.format("SELECT user_id, system_permission_id " +
													   				 "  FROM %s", TABLE_NAME);
	
	private static final String INSERT_USER_PERMISSION = String.format("INSERT INTO %s (user_id, system_permission_id, " +
																	   "       enabled, visible, created_at, updated_at )" +
																	   "     VALUES(?, ?, ?, ?, ?, ?)", TABLE_NAME);
	
	private static final String DELETE_USER_PERMISSION = String.format("DELETE FROM %s WHERE user_id = ? AND system_permission_id = ?", TABLE_NAME);
			   

	public UserPermissionRepository(Connection conn) {
		super(conn);
		
		this.qryAll = QRY_USER_PERMISSIONS;
		this.mapPks.put(PK_USER_PERMISSION_USER_ID, FIELD_USER_PERMISSION_USER_ID);
		this.mapPks.put(PK_USER_PERMISSION_SYSTEM_PERMISSION_ID, FIELD_USER_PERMISSION_SYSTEM_PERMISSION_ID);
		this.prefix = USER_PREFIX;
		this.tableName = TABLE_NAME;
	}

	public UserPermissionRepository(Connection conn, List<CacheRepository> cacheRepositories) {
		super(conn, cacheRepositories);
		
		this.qryAll = QRY_USER_PERMISSIONS;
		this.mapPks.put(PK_USER_PERMISSION_USER_ID, FIELD_USER_PERMISSION_USER_ID);
		this.mapPks.put(PK_USER_PERMISSION_SYSTEM_PERMISSION_ID, FIELD_USER_PERMISSION_SYSTEM_PERMISSION_ID);
		this.prefix = USER_PREFIX;
		this.tableName = TABLE_NAME;
	}
	
	@Override
	public void fill(List<Map<String, Object>> users) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> create(Map<String, Object> input) throws EntityPersistenceException {
		
		Map<String, Object> newUserPermission = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(INSERT_USER_PERMISSION)) {

				StringBuilder sbSql = new StringBuilder();
				
				sbSql.append(INSERT_USER_PERMISSION);
				
				try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
					
					int paramIdx = 1;

					String idStr = (String) input.get(FIELD_ID);
					
					String[] idsStr = getId(idStr);
					
					if (!ArrayUtils.isEmpty(idsStr) && idsStr.length == 2) {
					
						// user_id
						Long userId = Long.valueOf(idsStr[0]);
						Long systemPermissionId = Long.valueOf(idsStr[1]); 
						
						stmt.setLong(paramIdx++, userId);
						
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
						
							newUserPermission = get(new ImmutablePair<String, Object>(PK_USER_PERMISSION_USER_ID, userId),
									new ImmutablePair<String, Object>(PK_USER_PERMISSION_SYSTEM_PERMISSION_ID, systemPermissionId));
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

		return newUserPermission;
	}

	@Override
	public Map<String, Object> update(Map<String, Object> input) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> delete(String input) throws EntityPersistenceException {
		
		Map<String, Object> deletedUserPermission = null;
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(DELETE_USER_PERMISSION) && !StringUtils.isEmpty(input)) {
				
				String[] ids = getId(input);
				
				if (!ArrayUtils.isEmpty(ids)) {
					
					Long userId = Long.valueOf(ids[0]);
					Long systemPermissionId = Long.valueOf(ids[1]);
					
					Map<String, Object> userPermission = get(
							new ImmutablePair<String, Object>(PK_USER_PERMISSION_USER_ID, userId),
							new ImmutablePair<String, Object>(PK_USER_PERMISSION_SYSTEM_PERMISSION_ID, systemPermissionId));
					
					if (MapUtils.isEmpty(userPermission)) {
					
						throw new  EntityPersistenceException(Operation.DELETE, this.getClass(), "User permission not found");
					}
						
					StringBuilder sbSql = new StringBuilder();
					
					sbSql.append(DELETE_USER_PERMISSION);
					
					try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
						
						int paramIdx = 1;

						// user_id
						stmt.setLong(paramIdx++, userId);
						
						// system_permission_id
						stmt.setLong(paramIdx++, systemPermissionId);
						
														
						if (stmt.executeUpdate() > 0) {
							
							deletedUserPermission = userPermission;		
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

		return deletedUserPermission;
	}
}
