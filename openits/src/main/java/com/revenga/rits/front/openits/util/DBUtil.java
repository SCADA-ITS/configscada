package com.revenga.rits.front.openits.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.model.backoffice.Column;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistValue;

import lombok.Getter;

public class DBUtil {

	public enum DBType {
		
		POSTGRESQL("postgresql"), MYSQL("mysql"), MSSQL("mssql"), ORACLE("oracle");
		
		@Getter
		private String dbName;

		DBType(String dbName) {
			
			this.dbName = dbName;
		}
	}
	
	public enum PersistenceType { INSERT, UPDATE, DELETE }
	
	public enum DMLType { REFERENCE_TABLES, REFERENCED_BY_TABLE, COLUMNS_TABLE }
	
	public static class DmlLibrary {
		
		Map<DMLType, String> dmlMap = new EnumMap<>(DMLType.class);
		
		public void add(DMLType dmlType, String query) {
			
			dmlMap.put(dmlType, query);
		}
		
		public String get(DMLType dmlType) {
			
			return dmlMap.get(dmlType);
		}
	}
	
	protected static final Map<DBType, DmlLibrary> dmlMap;
	
	static {
		
		dmlMap = new EnumMap<>(DBType.class);
		
		DmlLibrary dmlLibrary = new DmlLibrary();
		
		dmlLibrary.add(DMLType.REFERENCE_TABLES,
				"SELECT tc.table_name as source, kcu.column_name as source_col, ccu.table_name AS target, ccu.column_name AS target_col " +
				"  FROM information_schema.table_constraints AS tc " + 
				"       JOIN information_schema.key_column_usage AS kcu " + 
				"         ON tc.constraint_name = kcu.constraint_name " + 
				"       JOIN information_schema.constraint_column_usage AS ccu " + 
				"         ON ccu.constraint_name = tc.constraint_name " + 
				" WHERE constraint_type = 'FOREIGN KEY' AND tc.table_name = ? AND tc.table_schema = ?");
		dmlLibrary.add(DMLType.REFERENCED_BY_TABLE,
				"SELECT conrelid::regclass as table_reference " + 
				"  FROM pg_constraint " + 
				" WHERE confrelid = ?::regclass");
		dmlLibrary.add(DMLType.COLUMNS_TABLE,
				"SELECT column_name as column_name, ordinal_position as ordinal_position, udt_name as data_type " + 
				"  FROM information_schema.columns " + 
				" WHERE table_name = ? AND table_schema = ?");

		dmlMap.put(DBType.POSTGRESQL, dmlLibrary);
		
		// TODO MYSQL, MSSQL, ORACLE
	}
	
	public enum ColType { INTEGER, DECIMAL, STRING, BOOLEAN, TIMESTAMP }
	
	public static String getDmlLib(DBType dbtype, DMLType dmlType) {
		
		String query = null;
		
		DmlLibrary dmlLibrary = dmlMap.get(dbtype);
		
		if (dmlLibrary != null) {
			
			query = dmlLibrary.get(dmlType);
		}
		
		return query;
	}
	
	public static String getStringValueFromResultSet(Column column, ResultSet rs) throws SQLException {
		
		String value = null;
		
		if (column.getColType().equals(ColType.STRING)) {
		
			value = rs.getString(column.getName()); 
		}
		else if (column.getColType().equals(ColType.INTEGER)) {
			
			value = String.valueOf(rs.getInt(column.getName()));
		}
		else if (column.getColType().equals(ColType.DECIMAL)) {
			
			value = String.valueOf(rs.getDouble(column.getName()));
		}
		else if (column.getColType().equals(ColType.TIMESTAMP)) {
			
			value = String.valueOf(rs.getTimestamp(column.getName()).getTime());
		}
		
		return value;
	}
	
	public static DBType getDBType(Connection conn) throws SQLException {
		
		DatabaseMetaData metaData = conn.getMetaData();
		String url = metaData.getURL();
		
		for (DBType dbType : List.of(DBType.values())) {
			
			if (StringUtils.containsIgnoreCase(url, dbType.toString())) {
				
				return dbType;
			}
		}
		
		return null;
	}
	
	public static void setColType(Column col, DBType dbType, String dataType) {
		
		if (dbType.equals(DBType.POSTGRESQL)) {
			
			setColTypePostgreSQL(col, dataType);
		}
	}
	
	private static void setColTypePostgreSQL(Column col, String dataType) {
		
		if (!StringUtils.isEmpty(dataType)) {
		
			if (dataType.equals(("int4"))) {
				
				col.setColType(ColType.INTEGER);
			}
			else if (dataType.equals(("float8"))) {
				
				col.setColType(ColType.DECIMAL);
			}
			else if (dataType.equals(("varchar"))) {
				
				col.setColType(ColType.STRING);
			}
			else if (dataType.equals(("timestamptz"))) {
				
				col.setColType(ColType.TIMESTAMP);
			}
		}
	}
	
	public static String getDml(Persist persist) {
		
		String sql = null;
		
		if (persist.getId() != null && !StringUtils.isEmpty(persist.getTableName())) {
			
			if (persist.getPersistenceType().equals(PersistenceType.INSERT)) {
				
				sql = getDmlInsert(persist);
			}
			else if (persist.getPersistenceType().equals(PersistenceType.UPDATE)) {
				
				sql = getDmlUpdate(persist);
			}
			else if (persist.getPersistenceType().equals(PersistenceType.DELETE)) {
				
				sql = getDmlDelete(persist);
			}
		}
		
		return sql;
		
	}
	
	private static String getDmlInsert(Persist persist) {

		String sql = "INSERT INTO %s(id%s) VALUES(" + persist.getId() + "%s)";
		
		if (!CollectionUtils.isEmpty(persist.getPersistValues())) {
		
			StringBuilder sbColNames = new StringBuilder();
			StringBuilder sbValues = new StringBuilder();
			
			for (PersistValue persistValue : persist.getPersistValues()) {
				
				String valueAddon = persistValue.getColType().equals(ColType.STRING) ? "'" : "";
				
				sbColNames.append(", ");
				sbColNames.append(persistValue.getColName());
				
				sbValues.append(", ");
				sbValues.append(valueAddon + persistValue.getNewValue() + valueAddon); 
			}
			
			sql = String.format(sql, persist.getTableName(), sbColNames.toString(), sbValues.toString());
		}
		else {
			
			sql = String.format(sql, persist.getTableName(), "", "");
		}
		
		return sql;
	}
	
	private static String getDmlUpdate(Persist persist) {

		String sql = null;
		
		if (!CollectionUtils.isEmpty(persist.getPersistValues())) {
		
			List<String> updateValues = new ArrayList<>();
			
			for (PersistValue persistValue : persist.getPersistValues()) {
				
				if (!StringUtils.equals(persistValue.getOldValue(), persistValue.getNewValue())) {
					
					String valueAddon = persistValue.getColType().equals(ColType.STRING) ? "'" : "";
					
					updateValues.add(persistValue.getColName() + " = " + valueAddon + persistValue.getNewValue() + valueAddon);
				}
			}
			
			if (!CollectionUtils.isEmpty(updateValues)) {
		
				sql = String.format("UPDATE %s SET %s WHERE id = %d", persist.getTableName(),
						String.join(",", updateValues), persist.getId());
			}
		}
		
		return sql;
	}
	
	private static String getDmlDelete(Persist persist) {
		
		return String.format("DELETE FROM %s WHERE id = %d", persist.getTableName(), persist.getId());
	}
}
