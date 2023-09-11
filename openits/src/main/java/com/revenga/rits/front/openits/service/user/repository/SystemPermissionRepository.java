package com.revenga.rits.front.openits.service.user.repository;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class SystemPermissionRepository extends GenericRepository {

	public static final String SYSTEM_PERMISSIONS_PREFIX = "SystemPermission";
	public static final String TABLE_NAME = "master.system_permissions";
	
	public static final String PK_SYSTEM_PERMISSION_ID = "system_permission_id";
	
	public static final String FIELD_SYSTEM_PERMISSION_ID = "systemPermissionId";
	
	private static final String QRY_SYSTEM_PERMISSIONS = String.format("SELECT system_permission_id, alias, description, label_alias, label_description " +
														 			   "  FROM %s", TABLE_NAME);

	public SystemPermissionRepository(Connection conn) {
		super(conn);
		
		this.qryAll = QRY_SYSTEM_PERMISSIONS;
		this.prefix = SYSTEM_PERMISSIONS_PREFIX;
		this.mapPks.put(PK_SYSTEM_PERMISSION_ID, FIELD_SYSTEM_PERMISSION_ID);
		this.tableName = TABLE_NAME;
	}

	public SystemPermissionRepository(Connection conn, List<CacheRepository> cacheRepositories) {
		super(conn, cacheRepositories);
		
		this.qryAll = QRY_SYSTEM_PERMISSIONS;
		this.prefix = SYSTEM_PERMISSIONS_PREFIX;
		this.mapPks.put(PK_SYSTEM_PERMISSION_ID, FIELD_SYSTEM_PERMISSION_ID);
		this.tableName = TABLE_NAME;
	}
	
	@Override
	public void fill(List<Map<String, Object>> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> create(Map<String, Object> input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> update(Map<String, Object> input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> delete(String input) {
		// TODO Auto-generated method stub
		return null;
	}
}
