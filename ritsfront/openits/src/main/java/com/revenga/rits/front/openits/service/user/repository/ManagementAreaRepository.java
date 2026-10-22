package com.revenga.rits.front.openits.service.user.repository;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ManagementAreaRepository extends GenericRepository {

	public static final String MANAGEMENT_AREAS_PREFIX = "ManagementArea";
	public static final String TABLE_NAME = "static.management_areas";
	
	public static final String PK_MANAGEMENT_AREA_ID = "management_area_id";
	
	public static final String FIELD_MANAGEMENT_AREA_ID = "managementAreaId";
	
	private static final String QRY_MANAGEMENT_AREAS = String.format("SELECT management_area_id, alias, description " +
														 			   "  FROM %s", TABLE_NAME);

	public ManagementAreaRepository(Connection conn) {
		super(conn);
		
		this.qryAll = QRY_MANAGEMENT_AREAS;
		this.prefix = MANAGEMENT_AREAS_PREFIX;
		this.mapPks.put(PK_MANAGEMENT_AREA_ID, FIELD_MANAGEMENT_AREA_ID);
		this.tableName = TABLE_NAME;
	}

	public ManagementAreaRepository(Connection conn, List<CacheRepository> cacheRepositories) {
		super(conn, cacheRepositories);
		
		this.qryAll = QRY_MANAGEMENT_AREAS;
		this.prefix = MANAGEMENT_AREAS_PREFIX;
		this.mapPks.put(PK_MANAGEMENT_AREA_ID, FIELD_MANAGEMENT_AREA_ID);
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
