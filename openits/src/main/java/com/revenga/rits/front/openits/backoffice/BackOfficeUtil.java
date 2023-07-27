package com.revenga.rits.front.openits.backoffice;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeGroovy;
import com.revenga.rits.front.openits.model.backoffice.Column;
import com.revenga.rits.front.openits.model.backoffice.Column.ColType;
import com.revenga.rits.front.openits.model.backoffice.GroovyBasic;
import com.revenga.rits.front.openits.model.backoffice.Request;
import com.revenga.rits.front.openits.model.backoffice.Request.RequestInput;
import com.revenga.rits.front.openits.model.backoffice.Request.RequestResult;
import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Row.RowData;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.RequestType;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackOfficeUtil {

	public enum DBType {
		
		POSTGRESQL("postgresql"), MYSQL("mysql"), MSSQL("mssql"), ORACLE("oracle");
		
		@Getter
		private String dbName;

		DBType(String dbName) {
			
			this.dbName = dbName;
		}
	}

	public enum DMLType { REFERENCE_TABLES, REFERENCED_BY_TABLE, COLUMNS_TABLE, TABLES }
	
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
		dmlLibrary.add(DMLType.TABLES, 
				"SELECT table_name FROM information_schema.tables where table_schema = ?");

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
			
			value = String.valueOf(rs.getTimestamp(column.getName()) != null ? rs.getTimestamp(column.getName()).getTime() : null);
		}
		else if (column.getColType().equals(ColType.BOOLEAN)) {
			
			value = String.valueOf(rs.getBoolean(column.getName()));
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
			else if (dataType.equals(("bool"))) {
				
				col.setColType(ColType.BOOLEAN);
			}
		}
	}
	
	public static String getDml(Connection conn, BackOffice backOffice, Request request) throws SQLException {
		
		String sql = null;
		
		if (!StringUtils.isEmpty(request.getTableName())) {
			
			if (request.getRequestType().equals(RequestType.INSERT)) {
				
				sql = getDmlInsert(conn, backOffice, request);
			}
			else if (request.getRequestType().equals(RequestType.UPDATE)) {
				
				sql = getDmlUpdate(backOffice, request);
			}
			else if (request.getRequestType().equals(RequestType.DELETE)) {
				
				sql = getDmlDelete(backOffice, request);
			}
		}
		
		return sql;
		
	}
	
	private static String getDmlInsert(Connection conn, BackOffice backOffice, Request request) throws SQLException {

		String sql = null;
		
		request.setId(getNextId(conn, backOffice, request.getTableName()));
		
		if (request.getId() != null) {
			
			sql = "INSERT INTO %s.%s(id%s) VALUES(" + request.getId() + "%s)";
			
			if (!CollectionUtils.isEmpty(request.getRequestInput())) {
			
				StringBuilder sbColNames = new StringBuilder();
				StringBuilder sbValues = new StringBuilder();
				
				for (RequestInput requestInput : request.getRequestInput()) {
					
					sbColNames.append(", ");
					sbColNames.append(requestInput.getColName());
					
					sbValues.append(", ");
					sbValues.append(getValueForDml(requestInput.getColType(), requestInput.getNewValue())); 
				}
				
				sql = String.format(sql, backOffice.getSchema(), request.getTableName(), sbColNames.toString(), sbValues.toString());
			}
			else {
				
				sql = String.format(sql, backOffice.getSchema(), request.getTableName(), "", "");
			}
		}
		
		return sql;
	}
	
	private static String getDmlUpdate(BackOffice backOffice, Request request) {

		String sql = null;
		
		if (!CollectionUtils.isEmpty(request.getRequestInput()) && request.getId() != null) {
		
			List<String> updateValues = new ArrayList<>();
			
			for (RequestInput requestInput : request.getRequestInput()) {
				
				if (!StringUtils.equals(requestInput.getOldValue(), requestInput.getNewValue())) {
					
					updateValues.add(requestInput.getColName() + " = " + getValueForDml(requestInput.getColType(), requestInput.getNewValue()));
				}
			}
			
			if (!CollectionUtils.isEmpty(updateValues)) {
		
				sql = String.format("UPDATE %s.%s SET %s WHERE id = %d", backOffice.getSchema(), request.getTableName(),
						String.join(",", updateValues), request.getId());
			}
		}
		
		return sql;
	}
	
	public static Integer getNextId(Connection conn, BackOffice backOffice, String tableName) throws SQLException {
		
		Integer nextId = null;
		
		try (Statement stmt = conn.createStatement()) {
			
			String sql = null;
			
			if (DBType.POSTGRESQL.equals(getDBType(conn))) {
		
				sql = String.format("SELECT nextval('%s.%s_id_seq'::regclass) as next_id", backOffice.getSchema(),
						tableName);
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
	
	private static String getDmlDelete(BackOffice backOffice, Request request) {
		
		String sql = null;
		
		if (request.getId() != null) {
			
			sql = String.format("DELETE FROM %s.%s WHERE id = %d", backOffice.getSchema(), request.getTableName(), request.getId());
		}
		
		return sql;
	}
	
	private static String getValueForDml(ColType colType, String value) {
		
		String formattedValue = null;
		
		formattedValue = value;
		
		if (formattedValue != null) {
			
			if (colType.equals(ColType.STRING)) {
				
				formattedValue = "'" + value + "'";
			}
			else if (colType.equals(ColType.TIMESTAMP)) {
				
				formattedValue = "'" + new SimpleDateFormat(DATE_FORMAT).format(new Timestamp(Long.valueOf(value))) + "'";
			}
			else if (colType.equals(ColType.BOOLEAN)) {
				
				formattedValue = Long.valueOf(value) != 0 ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
			}
		}
		else {
			
			formattedValue = "null";
		}
		
		return formattedValue;
	}
	
	@NonNull
	public static void setValue(Row row, String colName, String value) {
		
		if (row != null && !CollectionUtils.isEmpty(row.getData())) {
			
			List<RowData> rowData = row.getData();
			
			RowData rowDataField = rowData.stream()
					.filter(e -> e.getColumn() != null && e.getColumn().getName().equals(colName)).findFirst()
					.orElse(null);
			
			if (rowDataField != null) {
				
				rowDataField.setValue(value);			
			}
		}
	}
	
	public static AbstractBackOfficeGroovy newGroovyInstance(GroovyManager groovyManager, GroovyBasic groovyBasic,
			Connection conn, String schema, Table table, Integer id, RequestResult requestResult) throws Exception {
		
		String groovyFile = groovyBasic.getGroovy();
		
		if (!StringUtils.isEmpty(groovyFile)) {
			
			Object groovyInstance = null;
			
			try {
				
				Path path = Paths.get(groovyFile);
				
				if (path != null) {
					
					String fileName = path.getFileName().toString();
					
					if (!StringUtils.isEmpty(fileName)) {
				
						Class<?> clazz = Class.forName(
								groovyBasic.getPackageName() + "." + fileName.substring(0, fileName.indexOf(".")));
						
						if (AbstractBackOfficeGroovy.class.isAssignableFrom(clazz)) {
						
							groovyInstance = clazz.getDeclaredConstructor(org.apache.logging.log4j.Logger.class).newInstance(log);
						}
					}
				}
				
			} catch (ClassNotFoundException e) {
				
				// Nothing to do
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				
				throw e;
			}
			
			if (groovyInstance == null) {
				
				groovyInstance = groovyManager.getGroovyObject(groovyFile, GroovyManager.GroovyType.BACKOFFICE);
			}
			
			if (groovyInstance instanceof AbstractBackOfficeGroovy) {
				
				AbstractBackOfficeGroovy backOfficeGroovy = (AbstractBackOfficeGroovy) groovyInstance;
				
				AbstractBackOfficeGroovy backOfficeGroovyCloned = (AbstractBackOfficeGroovy) backOfficeGroovy.clone();
				
				if (backOfficeGroovyCloned.getLog() == null) {
					
					backOfficeGroovyCloned.setLog(log);					
				}
				
				backOfficeGroovyCloned.setConn(conn);
				backOfficeGroovyCloned.setSchema(schema);
				backOfficeGroovyCloned.setTable(table);
				backOfficeGroovyCloned.setId(id);
				
				if (requestResult != null) {
					
					backOfficeGroovyCloned.setOldRow(requestResult.getOldRow());
					backOfficeGroovyCloned.setNewRow(requestResult.getNewRow());
					backOfficeGroovyCloned.setRequestResultMessage(requestResult.getRequestResultMessage());
				}
				
				return backOfficeGroovyCloned;
			}
		}
		
		return null;
	}
}
