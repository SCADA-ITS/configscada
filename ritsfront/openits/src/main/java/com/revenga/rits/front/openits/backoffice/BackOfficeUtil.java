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
import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeGroovy;
import com.revenga.rits.front.openits.backoffice.model.Column;
import com.revenga.rits.front.openits.backoffice.model.Column.ColType;
import com.revenga.rits.front.openits.backoffice.model.GroovyBasic;
import com.revenga.rits.front.openits.backoffice.model.Request;
import com.revenga.rits.front.openits.backoffice.model.Request.RequestInput;
import com.revenga.rits.front.openits.backoffice.model.Request.RequestResult;
import com.revenga.rits.front.openits.backoffice.model.Row;
import com.revenga.rits.front.openits.backoffice.model.Row.RowData;
import com.revenga.rits.front.openits.backoffice.model.Table;
import com.revenga.rits.front.openits.backoffice.model.Table.RequestType;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackOfficeUtil {

	public enum DBType {
		
		POSTGRESQL("postgresql"), MYSQL("mysql"), MSSQL("sqlserver"), ORACLE("oracle");
		
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
		
		//
		// PostgreSQL
		//
		DmlLibrary dmlLibraryPSQL = new DmlLibrary();
		
		dmlLibraryPSQL.add(DMLType.REFERENCE_TABLES,
				"SELECT tc.table_name as source, " + 
				"       kcu.column_name as source_col, " + 
				"       ccu.table_name AS target, " + 
				"       ccu.column_name AS target_col " +
				"  FROM information_schema.table_constraints AS tc " + 
				"       JOIN information_schema.key_column_usage AS kcu " + 
				"         ON tc.constraint_name = kcu.constraint_name " + 
				"       JOIN information_schema.constraint_column_usage AS ccu " + 
				"         ON ccu.constraint_name = tc.constraint_name " + 
				" WHERE constraint_type = 'FOREIGN KEY' " + 
				"   AND tc.table_schema = ? AND tc.table_name = ?");
		
		dmlLibraryPSQL.add(DMLType.REFERENCED_BY_TABLE,
				"SELECT conrelid::regclass as table_reference " + 
				"  FROM pg_constraint " + 
				" WHERE confrelid = (? || '.' || ?)::regclass");
		
		dmlLibraryPSQL.add(DMLType.COLUMNS_TABLE,
				"SELECT a.attname AS column_name, " +
				"       a.attnum AS ordinal_position, " +
				"       i.udt_name AS data_type, " +
				"       NOT a.attnotnull AS is_nullable, " +
				"           CASE " +
				"               WHEN c.conrelid IS NOT NULL THEN TRUE " +
				"               ELSE FALSE " +
				"           END AS primary_key, " +
				"       false is_identity, " +
				"       i.column_default AS default_value " +
				"  FROM pg_attribute AS a " +
				"       LEFT OUTER JOIN pg_constraint c ON (c.conrelid = a.attrelid " +
				"                                         AND c.contype = 'p' " +
				"                                         AND a.attnum = any(c.conkey)) " +
				"       LEFT OUTER JOIN information_schema.columns i ON(a.attname = i.column_name) " +
				" WHERE a.attrelid = (? || '.' || ?)::regclass " +
				"   AND a.attnum > 0 " +
				"   AND NOT a.attisdropped " +
				"   AND a.attrelid = (i.table_schema  || '.' || i.table_name )::regclass");
		
		dmlLibraryPSQL.add(DMLType.TABLES,
				"SELECT table_name " + 
				"  FROM information_schema.tables " + 
				" WHERE table_schema = ?");

		dmlMap.put(DBType.POSTGRESQL, dmlLibraryPSQL);
		
		//
		// MSSQL
		//
		DmlLibrary dmlLibraryMSSQL = new DmlLibrary();
		
		dmlLibraryMSSQL.add(DMLType.REFERENCE_TABLES,
				"SELECT OBJECT_NAME(fk.parent_object_id) AS source, " +
			    "       COL_NAME(fkc.parent_object_id, fkc.parent_column_id) AS source_col, " +
			    "       OBJECT_NAME(fk.referenced_object_id) AS target, " +
			    "       COL_NAME(fkc.referenced_object_id, fkc.referenced_column_id) AS target_col " +
				"  FROM sys.foreign_keys AS fk " +
				"       JOIN sys.foreign_key_columns AS fkc " +
			    "         ON fk.object_id = fkc.constraint_object_id " +
				" WHERE OBJECT_SCHEMA_NAME(fk.parent_object_id) = ? " +
				"   AND OBJECT_NAME(fk.parent_object_id) = ?");
		
		dmlLibraryMSSQL.add(DMLType.REFERENCED_BY_TABLE,
				"SELECT OBJECT_SCHEMA_NAME(fk.parent_object_id) + '.' + OBJECT_NAME(fk.parent_object_id) as table_reference " + 
				"  FROM sys.foreign_keys AS fk " +
				" WHERE OBJECT_SCHEMA_NAME(fk.referenced_object_id) = ? " + 
				"   AND OBJECT_NAME(fk.referenced_object_id) = ? ");
		
		dmlLibraryMSSQL.add(DMLType.COLUMNS_TABLE,
				"SELECT DISTINCT c.column_name AS column_name, " +
				"       c.ordinal_position AS ordinal_position, " +
				"       c.data_type AS data_type, " +
				"       CAST(CASE WHEN c.is_nullable = 'YES' THEN 1 ELSE 0 END AS BIT) AS is_nullable, " +
				"       CAST(ISNULL(OBJECTPROPERTY(OBJECT_ID(K.CONSTRAINT_SCHEMA + '.' + K.CONSTRAINT_NAME), 'IsPrimaryKey'), 0) AS BIT) AS primary_key, " +
				"       CAST(ISNULL(COLUMNPROPERTY(OBJECT_ID(k.TABLE_NAME), c.column_name, 'IsIdentity'), 0) AS BIT) AS is_identity, " +
				"       c.column_default as default_value  " +
				"  FROM information_schema.columns C LEFT OUTER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE K " +
			  	"       ON (c.TABLE_SCHEMA = K.TABLE_SCHEMA " +
				"            AND c.TABLE_NAME = k.TABLE_NAME " + 
				"            AND C.COLUMN_NAME = K.COLUMN_NAME) " +  
			  	" WHERE c.table_schema = ? " +
			  	"   AND c.table_name = ? ");
		
		dmlLibraryMSSQL.add(DMLType.TABLES, 
				"SELECT table_name " +
				"  FROM information_schema.tables " +
				" WHERE table_schema = ? " +
				"   AND table_type = 'BASE TABLE'");
		
		dmlMap.put(DBType.MSSQL, dmlLibraryMSSQL);
		
		// TODO MYSQL, ORACLE
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
		else if (column.getColType().equals(ColType.SHORT)) {
			
			value = String.valueOf(rs.getShort(column.getName()));
		}
		else if (column.getColType().equals(ColType.INTEGER)) {
			
			value = String.valueOf(rs.getInt(column.getName()));
		}
		else if (column.getColType().equals(ColType.LONG)) {
			
			value = String.valueOf(rs.getLong(column.getName()));
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
				.filter(dbType -> StringUtils.containsIgnoreCase(url, dbType.getDbName())).findFirst().orElse(null);	
	}
	
	public static void setColType(Column col, DBType dbType, String dataType) {
		
		if (dbType.equals(DBType.POSTGRESQL)) {
			
			setColTypePostgreSQL(col, dataType);
		}
		else if (dbType.equals(DBType.MSSQL)) {
			
			setColTypeMSSQL(col, dataType);
		}
	}
	
	private static void setColTypePostgreSQL(Column col, String dataType) {
		
		if (!StringUtils.isEmpty(dataType)) {
		
			if (dataType.equals(("int2"))) {
				
				col.setColType(ColType.SHORT);
			}
			else if (dataType.equals(("int4"))) {
				
				col.setColType(ColType.INTEGER);
			}
			else if (dataType.equals(("int8"))) {
				
				col.setColType(ColType.LONG);
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
	
	private static void setColTypeMSSQL(Column col, String dataType) {
		
		if (!StringUtils.isEmpty(dataType)) {
		
			if (StringUtils.equalsAny(dataType, "tinyint", "smallint")) {
				
				col.setColType(ColType.SHORT);
			}
			else if (StringUtils.equalsAny(dataType, "int")) {
				
				col.setColType(ColType.INTEGER);
			}
			else if (StringUtils.equalsAny(dataType, "bigint")) {
				
				col.setColType(ColType.LONG);
			}
			else if (StringUtils.equalsAny(dataType, "float", "decimal", "numeric")) {
				
				col.setColType(ColType.DECIMAL);
			}
			else if (StringUtils.equalsAny(dataType, "varchar", "char", "nvarchar")) {
				
				col.setColType(ColType.STRING);
			}
			else if (StringUtils.equalsAny(dataType, "datetime", "datetime2", "date", "datetimeoffset")) {
				
				col.setColType(ColType.TIMESTAMP);
			}
			else if (StringUtils.equalsAny(dataType, "bit")) {
				
				col.setColType(ColType.BOOLEAN);
			}
		}
	}
	
	public static void setColTypeJdbc(Column col, int sqlType) {
		
		switch (sqlType) {
		
			case java.sql.Types.TINYINT:
			case java.sql.Types.SMALLINT:
				
				col.setColType(ColType.SHORT);
				break;
				
			case java.sql.Types.INTEGER:
				
				col.setColType(ColType.INTEGER);
				break;
				
			case java.sql.Types.BIGINT:
				
				col.setColType(ColType.LONG);
				break;
				
			case java.sql.Types.FLOAT:
			case java.sql.Types.DECIMAL:
			case java.sql.Types.NUMERIC:
				
				col.setColType(ColType.DECIMAL);
				break;
				
			case java.sql.Types.VARCHAR:
			case java.sql.Types.CHAR:
			case java.sql.Types.NVARCHAR:
				
				col.setColType(ColType.STRING);
				break;
				
			case java.sql.Types.DATE:
			case java.sql.Types.TIMESTAMP:
				
				col.setColType(ColType.TIMESTAMP);
				break;
				
			case java.sql.Types.BIT:
				
				col.setColType(ColType.BOOLEAN);
				break;
				
			default:
		}
	}
	
	public static String getDml(Connection conn, BackOffice backOffice, Table table, Request request) throws SQLException {
		
		String sql = null;
		
		if (!StringUtils.isEmpty(request.getTableName())) {
			
			if (request.getRequestType().equals(RequestType.INSERT)) {
				
				sql = getDmlInsert(conn, backOffice, table, request);
			}
			else if (request.getRequestType().equals(RequestType.UPDATE)) {
				
				sql = getDmlUpdate(conn, backOffice, table, request);
			}
			else if (request.getRequestType().equals(RequestType.DELETE)) {
				
				sql = getDmlDelete(backOffice, table, request);
			}
		}
		
		return sql;
		
	}
	
	private static String getDmlInsert(Connection conn, BackOffice backOffice, Table table, Request request) throws SQLException {

		Set<Column> insertedColumns = new HashSet<>();
		String sql = null;
		request.setId(getNextId(conn, backOffice, table));
		
		if (request.getId() != null) {
			
			Column columnId = table.getColumns().stream().filter(e -> e.getPrimaryKey().booleanValue()).findFirst().orElse(null);
			
			if (columnId != null) {
				
				List<String> colNames = new ArrayList<>();
				List<String> colValues = new ArrayList<>();
				
				// Insert received column values in request.getRequestInput()
				if (!CollectionUtils.isEmpty(request.getRequestInput())) {
					
					for (RequestInput requestInput : request.getRequestInput()) {
						
						Column column = table.getColumns().stream()
								.filter(e -> e.getName().equals(requestInput.getColName())
										&& (e.getPrimaryKey() == null || !e.getPrimaryKey()))
								.findFirst().orElse(null);
						
						if (column != null) {
							
							colNames.add(column.getName());
							colValues.add(getValueForDml(conn, column.getColType(), requestInput.getNewValue()));
							
							insertedColumns.add(column);
						}
					}
				}
				
				// Now, insert column values with not null restriction
				List<Column> columnsNotNull = table.getColumns().stream()
						.filter(e -> !insertedColumns.contains(e) && (e.getNullable() == null || !e.getNullable())
								&& (e.getPrimaryKey() == null || !e.getPrimaryKey()))
						.collect(Collectors.toList());

				if (!CollectionUtils.isEmpty(columnsNotNull)) {
					
					for (Column columnNotNull : columnsNotNull) {
						
						colNames.add(columnNotNull.getName());
						colValues.add(getValueForDml(conn, columnNotNull.getColType(), getDefaultColumnValue(conn, backOffice, table, columnNotNull)));
						
						insertedColumns.add(columnNotNull);
					}
				}
				
				if (colNames.size() == colValues.size()) {
					
					if (insertedColumns.isEmpty()) {
						
						sql = String.format("INSERT INTO %s.%s DEFAULT VALUES",
								backOffice.getSchema(),
								table.getName());
					}
					else {
						
						sql = String.format("INSERT INTO %s.%s(%s) VALUES(%s)",
								backOffice.getSchema(),
								table.getName(),
								String.join(", ", colNames.stream().toArray(String[]::new)),
								String.join(", ", colValues.stream().toArray(String[]::new)));
					}
				}
			}
		}
		
		return sql;
	}
	
	private static String getDefaultColumnValue(Connection conn, BackOffice backOffice, Table table, Column column) throws SQLException {
		
		String defaultColumnValue = null;
		
		if (column.getRefColumn() != null) {

			defaultColumnValue = String.valueOf(getFirstId(conn, backOffice, column.getRefColumn().getTable()));
		}
		else {
			
			if (!StringUtils.isEmpty(column.getDefaultValue())) {
				
				defaultColumnValue = getDefaultColumnValueFromTable(conn, column, column.getDefaultValue());
			}
			else {
				
				defaultColumnValue = getDefaultColumnValueFromConstant(conn, column);
			
				log.warn(String.format("Field %s.%s has no default value. Default value is established to: '%s'",
						table.getName(), column.getName(), defaultColumnValue));
			}
		}
		
		return defaultColumnValue;
	}
	
	private static String getDefaultColumnValueFromTable(Connection conn, Column column, String defaultValueExpression) throws SQLException {
		
		String defaultValue = null;
		final String fieldName = "defaultValue";
		
		try (Statement stmt = conn.createStatement()) {
			
			if (!StringUtils.isEmpty(defaultValueExpression)) {
				
				final String sql = String.format("SELECT %s as %s", defaultValueExpression, fieldName);
				
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					
					if (column.getColType().equals(ColType.STRING)) {
						
						defaultValue = rs.getString(fieldName); 
					}
					else if (column.getColType().equals(ColType.SHORT)) {
						
						defaultValue = String.valueOf(rs.getShort(fieldName));
					}
					else if (column.getColType().equals(ColType.INTEGER)) {
						
						defaultValue = String.valueOf(rs.getInt(fieldName));
					}
					else if (column.getColType().equals(ColType.LONG)) {
						
						defaultValue = String.valueOf(rs.getLong(fieldName));
					}
					else if (column.getColType().equals(ColType.DECIMAL)) {
						
						defaultValue = String.valueOf(rs.getDouble(fieldName));
					}
					else if (column.getColType().equals(ColType.TIMESTAMP)) {
						
						defaultValue = String.valueOf(rs.getTimestamp(fieldName) != null ? rs.getTimestamp(fieldName).getTime() : null);
					}
					else if (column.getColType().equals(ColType.BOOLEAN)) {
						
						defaultValue = String.valueOf(rs.getBoolean(fieldName));
					} 
				}
			}
		}
		
		return defaultValue;
	}
	
	private static String getDefaultColumnValueFromConstant(Connection conn, Column column) throws SQLException {
		
		String defaultValue = null;
		
		if (column.getColType().equals(ColType.STRING)) {
			
			defaultValue = "";
		}
		else if (column.getColType().equals(ColType.SHORT) ||
				 column.getColType().equals(ColType.INTEGER) ||
				 column.getColType().equals(ColType.LONG)) {
			
			defaultValue = String.valueOf(0);
		}
		else if (column.getColType().equals(ColType.DECIMAL)) {
			
			defaultValue = String.valueOf(0.0);
		}
		else if (column.getColType().equals(ColType.TIMESTAMP)) {
			
			defaultValue = String.valueOf(Instant.now().toEpochMilli());
		}
		else if (column.getColType().equals(ColType.BOOLEAN)) {
			
			if (DBType.POSTGRESQL.equals(getDBType(conn))) {
			
				defaultValue = Boolean.FALSE.toString();
			}
			else if (DBType.MSSQL.equals(getDBType(conn))) {
				
				defaultValue = String.valueOf(BooleanUtils.toInteger(Boolean.FALSE));
			}
		}
		
		return defaultValue;
	}
	
	private static String getDmlUpdate(Connection conn, BackOffice backOffice, Table table, Request request) throws NumberFormatException, SQLException {

		String sql = null;
		
		if (!CollectionUtils.isEmpty(request.getRequestInput()) && request.getId() != null) {
			
			Column columnId = table.getColumns().stream().filter(e -> e.getPrimaryKey().booleanValue()).findFirst().orElse(null);
			
			if (columnId != null) {
			
				List<String> updateValues = new ArrayList<>();
				
				for (RequestInput requestInput : request.getRequestInput()) {
					
					if (!StringUtils.equals(requestInput.getOldValue(), requestInput.getNewValue())) {
						
						updateValues.add(requestInput.getColName() + " = " + getValueForDml(conn, requestInput.getColType(), requestInput.getNewValue()));
					}
				}
				
				if (!CollectionUtils.isEmpty(updateValues)) {
			
					sql = String.format("UPDATE %s.%s SET %s WHERE %s = %d", backOffice.getSchema(),
							request.getTableName(), String.join(",", updateValues), columnId.getName(),
							request.getId());
				}
			}
		}
		
		return sql;
	}
	
	public static Long getNextId(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		Long nextId = null;
		
		Column columnId = table.getColumns().stream().filter(e -> e.getPrimaryKey().booleanValue()).findFirst().orElse(null);
		
		if (columnId != null) {
			
			try (Statement stmt = conn.createStatement()) {
				
				String sql = null;
				
				if (DBType.POSTGRESQL.equals(getDBType(conn))) {
					
					if (!StringUtils.isEmpty(columnId.getDefaultValue()) && 
						StringUtils.containsIgnoreCase(columnId.getDefaultValue(), "nextval")) {
						
						String sequenceName = columnId.getDefaultValue().replace("nextval('", "").replace("'::regclass)", "").trim();
						
						sql = String.format("SELECT " + 
								"       case when (last_value = 1 and is_called = false) then 1 " + 
								"       else last_value + 1 end as next_id " + " FROM %s ", sequenceName);
					}
				}
				else if (DBType.MSSQL.equals(getDBType(conn))) {
					
					if (columnId.getIdentity().booleanValue()) {
						
						sql = String.format("SELECT CASE WHEN CONVERT(INT, last_value) IS NULL THEN 1 " +
											"       ELSE CONVERT(INT, last_value) + IDENT_INCR('%s.%s') end as next_id " +
											"  FROM sys.identity_columns " +
											" WHERE object_id = OBJECT_ID('%s.%s')",
											backOffice.getSchema(), table.getName(),
											backOffice.getSchema(), table.getName());
					}
					else if (!StringUtils.isEmpty(columnId.getDefaultValue())) {
						
						String sequenceName = columnId.getDefaultValue().replace("NEXT VALUE FOR ", "").trim();
						
						sql = String.format("SELECT current_value + 1 as next_id " + 
											"  FROM sys.sequences " + 
											" WHERE name = %s", sequenceName);
					}
				}
				
				if (!StringUtils.isEmpty(sql)) {
							
					ResultSet rs = stmt.executeQuery(sql);

					if (rs.next()) {
						
						nextId = rs.getLong("next_id");
					}
				}
			}
		}
		
		return nextId;
	}
	
	private static Integer getFirstId(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		Integer firstId = null;
		
		Column columnId = table.getColumns().stream().filter(e -> e.getPrimaryKey().booleanValue()).findFirst().orElse(null);
		
		if (columnId != null) {
			
			try (Statement stmt = conn.createStatement()) {
				
				String sql = null;
				
				if (DBType.POSTGRESQL.equals(getDBType(conn))) {
			
					sql = String.format("SELECT %s as first_id FROM %s.%s LIMIT 1", columnId.getName(),
							backOffice.getSchema(), table.getName());
				}
				else if (DBType.MSSQL.equals(getDBType(conn))) {
					
					sql = String.format("SELECT TOP 1 %s as first_id FROM %s.%s", columnId.getName(), backOffice.getSchema(),
							table.getName());
				}
				
				if (!StringUtils.isEmpty(sql)) {
							
					ResultSet rs = stmt.executeQuery(sql);

					if (rs.next()) {
						
						firstId = rs.getInt("first_id");
					}
				}
			}
		}
		
		return firstId;
	}
	
	private static String getDmlDelete(BackOffice backOffice, Table table, Request request) {
		
		String sql = null;
		
		if (request.getId() != null) {
			
			Column columnId = table.getColumns().stream().filter(e -> e.getPrimaryKey().booleanValue()).findFirst().orElse(null);
			
			if (columnId != null) {
			
				sql = String.format("DELETE FROM %s.%s WHERE %s = %d", backOffice.getSchema(), request.getTableName(),
						columnId.getName(), request.getId());
			}
		}
		
		return sql;
	}
	
	private static String getValueForDml(Connection conn, ColType colType, String value) throws NumberFormatException, SQLException {
		
		String formattedValue = null;
		
		formattedValue = value;
		
		if (formattedValue != null) {
			
			if (colType.equals(ColType.STRING)) {
				
				formattedValue = "'" + value + "'";
			}
			else if (colType.equals(ColType.TIMESTAMP)) {
				
				if (DBType.POSTGRESQL.equals(getDBType(conn))) {
					
					formattedValue = "'" + new SimpleDateFormat(DATE_FORMAT).format(new Timestamp(Long.valueOf(value))) + "'";
				
				} else if (DBType.MSSQL.equals(getDBType(conn))) {
					
					formattedValue = String.format("CAST(DATEADD(SECOND, %d, '1970-01-01') as DATETIMEOFFSET)", (Long.valueOf(value) / 1000)); 
				}
			}
			else if (colType.equals(ColType.BOOLEAN)) {
				
				if (DBType.POSTGRESQL.equals(getDBType(conn))) {

					formattedValue = Long.valueOf(value) != BooleanUtils.toInteger(Boolean.FALSE)
							? Boolean.TRUE.toString()
							: Boolean.FALSE.toString();
					
				} else if (DBType.MSSQL.equals(getDBType(conn))) {

					formattedValue = Long.valueOf(value) != BooleanUtils.toInteger(Boolean.FALSE)
							? String.valueOf(BooleanUtils.toInteger(Boolean.TRUE))
							: String.valueOf(BooleanUtils.toInteger(Boolean.FALSE));
				}
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
	
	public static AbstractBackOfficeGroovy newGroovyInstance(Environment env, GroovyManager groovyManager, GroovyBasic groovyBasic,
			Connection conn, BackOffice backOffice, Table table, Request request, RequestResult requestResult) throws Exception {
		
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
				
				backOfficeGroovyCloned.setEnv(env);
				backOfficeGroovyCloned.setConn(conn);
				backOfficeGroovyCloned.setBackOffice(backOffice);
				backOfficeGroovyCloned.setTable(table);
				
				if (request != null) {
					
					backOfficeGroovyCloned.setId(request.getId());
					backOfficeGroovyCloned.setRequestParams(request.getRequestParams());
				}
				
				backOfficeGroovyCloned.setDbType(BackOfficeUtil.getDBType(conn));
				
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
	
	public static String getConditions(Connection conn, Table table, String conditions) throws SQLException {

		String[] conditionsArr = null;
		
		if (!StringUtils.isEmpty(conditions)) {
			
			conditionsArr = conditions.trim().split("AND");
			
			if (!ArrayUtils.isEmpty(conditionsArr)) {
				
				for (int i = 0; i < conditionsArr.length; i++) {
					
					String condition = conditionsArr[i];
					
					String[] items = condition.trim().split(" ");
					
					if (!ArrayUtils.isEmpty(items) && items.length == 3 && !CollectionUtils.isEmpty(table.getColumns())) {

						Column col = table.getColumns().stream().filter(e -> e.getName().equals(items[0])).findFirst().orElse(null);
						
						if (col != null) {
							
							if (col.getColType().equals(ColType.DECIMAL) || 
								col.getColType().equals(ColType.SHORT) ||
								col.getColType().equals(ColType.INTEGER) ||
								col.getColType().equals(ColType.LONG)) {
								
								items[2] = items[2].replace("'", "");
							}
							else if (col.getColType().equals(ColType.TIMESTAMP)) {
								
								items[2] = items[2].replace("'", "");
								
								if (Instant.parse(items[2]) != null) {
									
									if (DBType.POSTGRESQL.equals(getDBType(conn))) {
										
										items[2] = "to_timestamp('" + items[2] + "', 'YYYY-MM-DD''T''HH24:MI:SSZ')";
									}
									else if (DBType.MSSQL.equals(getDBType(conn))){
										
										items[2] = "CONVERT(DATETIME, '" + items[2] + "', 127)";
									}
								}
							}
						}
						
						if (!StringUtils.isEmpty(table.getSqlView())) {
							
							items[0] = String.format("A.%s", items[0]);
						}
					}
					
					conditionsArr[i] = String.join(" ", items);
				}
			}
		}
		
		return !ArrayUtils.isEmpty(conditionsArr) ? String.join(" AND ", conditionsArr) : conditions;
	}
}
