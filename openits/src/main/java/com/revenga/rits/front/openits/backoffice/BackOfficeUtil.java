package com.revenga.rits.front.openits.backoffice;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.model.backoffice.Column;
import com.revenga.rits.front.openits.model.backoffice.Column.ColType;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistValue;
import com.revenga.rits.front.openits.model.backoffice.Table.PersistenceType;
import com.revenga.rits.front.openitsdao.model.BackOffice;

import lombok.Getter;
import lombok.NonNull;

public class BackOfficeUtil {

	public enum DBType {
		
		POSTGRESQL("postgresql"), MYSQL("mysql"), MSSQL("mssql"), ORACLE("oracle");
		
		@Getter
		private String dbName;

		DBType(String dbName) {
			
			this.dbName = dbName;
		}
	}

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
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
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
		
		if (rs.wasNull()) {
			
			value = null;
		}
		
		return value;
	}
	
	public static DBType getDBType(Connection conn) throws SQLException {
		
		DatabaseMetaData metaData = conn.getMetaData();
		String url = metaData.getURL();
		
		return List.of(DBType.values()).stream()
				.filter(dbType -> StringUtils.containsIgnoreCase(url, dbType.toString())).findFirst().orElse(null);	
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
	
	public static String getDml(Connection conn, BackOffice backOffice, Persist persist) throws SQLException {
		
		String sql = null;
		
		if (!StringUtils.isEmpty(persist.getTableName())) {
			
			if (persist.getPersistenceType().equals(PersistenceType.INSERT)) {
				
				sql = getDmlInsert(conn, backOffice, persist);
			}
			else if (persist.getPersistenceType().equals(PersistenceType.UPDATE)) {
				
				sql = getDmlUpdate(backOffice, persist);
			}
			else if (persist.getPersistenceType().equals(PersistenceType.DELETE)) {
				
				sql = getDmlDelete(backOffice, persist);
			}
		}
		
		return sql;
		
	}
	
	private static String getDmlInsert(Connection conn, BackOffice backOffice, Persist persist) throws SQLException {

		String sql = null;
		
		persist.setId(getNextId(conn, backOffice, persist));
		
		if (persist.getId() != null) {
			
			sql = "INSERT INTO %s.%s(id%s) VALUES(" + persist.getId() + "%s)";
			
			if (!CollectionUtils.isEmpty(persist.getPersistValues())) {
			
				StringBuilder sbColNames = new StringBuilder();
				StringBuilder sbValues = new StringBuilder();
				
				for (PersistValue persistValue : persist.getPersistValues()) {
					
					sbColNames.append(", ");
					sbColNames.append(persistValue.getColName());
					
					sbValues.append(", ");
					sbValues.append(getValue(persistValue.getColType(), persistValue.getNewValue())); 
				}
				
				sql = String.format(sql, backOffice.getSchema(), persist.getTableName(), sbColNames.toString(), sbValues.toString());
			}
			else {
				
				sql = String.format(sql, backOffice.getSchema(), persist.getTableName(), "", "");
			}
		}
		
		return sql;
	}
	
	private static String getDmlUpdate(BackOffice backOffice, Persist persist) {

		String sql = null;
		
		if (!CollectionUtils.isEmpty(persist.getPersistValues()) && persist.getId() != null) {
		
			List<String> updateValues = new ArrayList<>();
			
			for (PersistValue persistValue : persist.getPersistValues()) {
				
				if (!StringUtils.equals(persistValue.getOldValue(), persistValue.getNewValue())) {
					
					updateValues.add(persistValue.getColName() + " = " + getValue(persistValue.getColType(), persistValue.getNewValue()));
				}
			}
			
			if (!CollectionUtils.isEmpty(updateValues)) {
		
				sql = String.format("UPDATE %s.%s SET %s WHERE id = %d", backOffice.getSchema(), persist.getTableName(),
						String.join(",", updateValues), persist.getId());
			}
		}
		
		return sql;
	}
	
	public static Integer getNextId(Connection conn, BackOffice backOffice, Persist persist) throws SQLException {
		
		Integer nextId = null;
		
		try (Statement stmt = conn.createStatement()) {
			
			String sql = null;
			
			if (DBType.POSTGRESQL.equals(getDBType(conn))) {
		
				sql = String.format("SELECT nextval('%s.%s_id_seq'::regclass) as next_id", backOffice.getSchema(),
						persist.getTableName());
			}
			
			if (!StringUtils.isEmpty(sql)) {
			
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					
					nextId = rs.getInt("next_id");
				}
			}
		}
		
		return nextId;
	}
	
	private static String getDmlDelete(BackOffice backOffice, Persist persist) {
		
		String sql = null;
		
		if (persist.getId() != null) {
			
			sql = String.format("DELETE FROM %s.%s WHERE id = %d", backOffice.getSchema(), persist.getTableName(), persist.getId());
		}
		
		return sql;
	}
	
	@NonNull
	private static String getValue(ColType colType, String value) {
		
		String formattedValue = value;
		
		if (colType.equals(ColType.STRING)) {
			
			formattedValue = "'" + value + "'";
		}
		else if (colType.equals(ColType.TIMESTAMP)) {
			
			formattedValue = "'" + new SimpleDateFormat(DATE_FORMAT).format(new Timestamp(Long.valueOf(value))) + "'";
		}
		
		return formattedValue;
	}
}
