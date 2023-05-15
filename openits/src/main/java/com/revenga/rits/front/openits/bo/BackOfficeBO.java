package com.revenga.rits.front.openits.bo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.model.backoffice.Basic;
import com.revenga.rits.front.openits.model.backoffice.Column;
import com.revenga.rits.front.openits.model.backoffice.Column.ColumnMetadata;
import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Row.RowData;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistResult;
import com.revenga.rits.front.openits.model.backoffice.Table.TableMetadata;
import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openits.util.DBUtil;
import com.revenga.rits.front.openits.util.DBUtil.DMLType;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BackOfficeBO {

	private Map<String, Table> referencedTables = new HashMap<>();
	private Map<String, Map<String, String>> translations = new HashMap<>();
	
	@Autowired
	BackOfficeService backOfficeService;
	
	@Autowired
	SessionBO sessionBO;
	
	public List<BackOffice> getList() {
		
		return backOfficeService.getAll();
	}

	public BackOffice getItem(Integer id) {
		
		return backOfficeService.getById(id);
	}

	public List<Table> get(Integer backOfficeId, String tableName) {

		return get(backOfficeId, tableName, null);
	}

	public List<Table> get(Integer backOfficeId, String tableName, String conditions) {

		List<Table> tables = null;
		
		BackOffice backOffice = getItem(backOfficeId);
		
		if (backOffice != null) {
		
			this.referencedTables.clear();
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
			
				getTable(conn, backOffice, tableName, conditions);
				
				if (!CollectionUtils.isEmpty(this.referencedTables)) {
				
					tables = this.referencedTables.values().stream().collect(Collectors.toList());
				}
			}
			catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return tables;
	}
	
	public PersistResult persist(Integer backOfficeId, Persist persist) {
		
		PersistResult persistResult = new PersistResult();
		BackOffice backOffice = getItem(backOfficeId);
		
		if (backOffice != null) {
			
			String sql = DBUtil.getDml(persist);
			
			if (!StringUtils.isEmpty(sql)) {
			
				try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
						AES.decrypt(backOffice.getPassword()))) {
					
					Table table = new Table(persist.getTableName());
					
					assignColumns(conn, backOffice, table);
					
					List<Row> rowsOld = assignRows(conn, backOffice, table, null, "id = " + persist.getId());
					
					if (!CollectionUtils.isEmpty(rowsOld) && rowsOld.size() == 1) {
						
						try (Statement stmt = conn.createStatement()) {

							if (stmt.executeUpdate(sql) == 1) {
								
								Table tableNew = new Table(persist.getTableName());
								
								tableNew.setColumns(table.getColumns());
								
								List<Row> rowsNew = assignRows(conn, backOffice, tableNew, null, "id = " + persist.getId());
								
								if (!CollectionUtils.isEmpty(rowsNew) && rowsNew.size() == 1) {
									
									persistResult.setOldRow(rowsOld.get(0));
									persistResult.setNewRow(rowsNew.get(0));
								}
							}
						}
					}
					
				} catch (Exception e) {
					
					persistResult.setError(e.getMessage());
					persistResult.setErrorDescription(ExceptionUtils.getStackTrace(e));
				}
			}
		}
		
		return persistResult;
	}

	private Table getTable(Connection conn, BackOffice backOffice, String tableName) throws SQLException {
		
		return getTable(conn, backOffice, tableName, null , null);
	}
	
	private Table getTable(Connection conn, BackOffice backOffice, String tableName, Table parentTable) throws SQLException {
		
		return getTable(conn, backOffice, tableName, parentTable, null);
	}
	
	private Table getTable(Connection conn, BackOffice backOffice, String tableName, String conditions) throws SQLException {
		
		return getTable(conn, backOffice, tableName, null, conditions);
	}
	
	private Table getTable(Connection conn, BackOffice backOffice, String tableName, Table parentTable, String conditions) throws SQLException {
		
		Table table = this.referencedTables.get(tableName);
		
		if (table == null) {
			
			table = new Table(tableName);
			
			this.referencedTables.put(tableName, table);
			
			assignColumns(conn, backOffice, table);
			
			assignReferTables(conn, backOffice, table);
			
			assignRows(conn, backOffice, table, parentTable, conditions);
			
			assignReferencedByTables(conn, backOffice, table);
			
			assignMetadata(conn, backOffice, table);
			
			traceTable(table);
		}
		
		return table;
	}
	
	private List<Column> assignColumns(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		List<Column> cols = table.getColumns();
		
		try (PreparedStatement stmt = conn.prepareStatement(DBUtil.getDmlLib(DBUtil.getDBType(conn), DMLType.COLUMNS_TABLE))) {
			
			stmt.setString(1, table.getName());
			stmt.setString(2, backOffice.getSchema());
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Column col = new Column(rs.getString("column_name"));

				col.setPosition(rs.getInt("ordinal_position"));
				
				DBUtil.setColType(col, DBUtil.getDBType(conn), rs.getString("data_type"));
				
				col.setTable(table);
				cols.add(col);			
			}
			
			Collections.sort(cols, Comparator.comparingInt(Column::getPosition));
		}
		
		return cols;
	}
	
	private List<Table> assignReferTables(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		List<Table> referTables = new ArrayList<>();
		
		try (PreparedStatement stmt = conn.prepareStatement(DBUtil.getDmlLib(DBUtil.getDBType(conn), DMLType.REFERENCE_TABLES))) {
			
			stmt.setString(1, table.getName());
			stmt.setString(2, backOffice.getSchema());
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				String sourceTableName = rs.getString("source");
				String sourceCol = rs.getString("source_col");
				String targetTableName = rs.getString("target");
				String targetCol = rs.getString("target_col");
				
				if (sourceTableName.equals(table.getName())) {
					
					Table referTable = getTable(conn, backOffice, targetTableName);
					
					if (referTable != null) {
						
						referTables.add(referTable);
						
						if (!CollectionUtils.isEmpty(table.getColumns()) &&
							!CollectionUtils.isEmpty(referTable.getColumns())) {
								
							Column sourceColumn = table.getColumns().stream().filter(e -> e.getName().equals(sourceCol))
									.findFirst().orElse(null);
							
							Column targetColumn = referTable.getColumns().stream().filter(e -> e.getName().equals(targetCol))
									.findFirst().orElse(null);
							
							if (sourceColumn != null && targetColumn != null) {
								
								sourceColumn.setRefColumn(targetColumn);
							}
						}
					}
				}
			}
		}
		
		return referTables;
	}
	
	private List<Table> assignReferencedByTables(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		List<Table> referencedByTables = new ArrayList<>();
		
		try (PreparedStatement stmt = conn.prepareStatement(DBUtil.getDmlLib(DBUtil.getDBType(conn), DMLType.REFERENCED_BY_TABLE))) {
			
			stmt.setString(1, backOffice.getSchema()+ "." + table.getName());
			
			ResultSet rs = stmt.executeQuery();
			
			List<Table> referTables = table.getReferTables();
			
			while (rs.next()) {
				
				String tableReferenceName = rs.getString("table_reference");
				
				tableReferenceName = tableReferenceName.replace(backOffice.getSchema()+ ".", "");
				
				Table referTable = getTable(conn, backOffice, tableReferenceName, table);
				
				if (referTable != null) {
				
					referTables.add(referTable);
					
					referencedByTables.add(referTable);
				}
			}
		}
		
		return referencedByTables;
	}
	
	private List<Row> assignRows(Connection conn, BackOffice backOffice, Table table, Table parentTable, String conditions) throws SQLException {
	
		List<Row> rows = table.getRows();
		
		if (CollectionUtils.isEmpty(rows) && !CollectionUtils.isEmpty(table.getColumns())) {
			
			String sql = "";
			
			List<String> columnNames = table.getColumns().stream().map(Basic::getName).collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(columnNames)) {
				
				sql = String.format("SELECT %s FROM %s.%s", String.join(",", columnNames), backOffice.getSchema(),
						table.getName());
				
				if (parentTable != null) {
					
					Column colId = parentTable.getColumns().stream().filter(e -> e.getName().equals("id")).findFirst()
							.orElse(null);
					
					Set<String> idsParent = new HashSet<>(); 
					
					for (Row parentRow : parentTable.getRows()) {
						
						RowData parentRowDataColumnId = parentRow.getData().stream().filter(e -> e.getColumnId().equals(colId.getId())).findFirst().orElse(null);
						
						if (parentRowDataColumnId != null) {
							
							idsParent.add(parentRowDataColumnId.getValue());
						}
					}
					
					Column colRef = table.getColumns().stream()
							.filter(e -> e.getRefColumn() != null
									&& e.getRefColumn().getTable().getId().equals(parentTable.getId()))
							.findFirst().orElse(null);
					
					if (colRef != null) {
					
						sql += String.format(" WHERE %s IN(%s)", colRef.getName(),
								String.join(",", idsParent.stream().collect(Collectors.toList())));
					}
				}
				
				if (!StringUtils.isEmpty(conditions)) {
					
					sql += (!sql.contains("WHERE") ? " WHERE " : " AND ") + conditions;
				}
				
				try (Statement stmt = conn.createStatement()) {

					ResultSet rs = stmt.executeQuery(sql);

					while (rs.next()) {
						
						Row row = new Row();
						
						List<RowData> rowData = row.getData();
						
						for (Column column : table.getColumns()) {
							
							rowData.add(new RowData(column, DBUtil.getStringValueFromResultSet(column, rs)));
						}
						
						rows.add(row);
					}
				}
			}
		}
		
		return rows;
	}
	
	private void loadI18nLabels(Connection conn, BackOffice backOffice) throws SQLException {
		
		final String QRY_I18N_LABELS = String.format("SELECT label, translation FROM %s.sg_i18n_labels WHERE locale_code = ?", backOffice.getSchema());
		final String localeCode = getLocaleCode();
		
		if (!StringUtils.isEmpty(localeCode)) {
			
			Map<String, String> translationsByLocale = this.translations.get(localeCode);
			
			if (translationsByLocale == null) {
				
				try (PreparedStatement stmt = conn.prepareStatement(QRY_I18N_LABELS)) {
					
					stmt.setString(1, localeCode);
					
					ResultSet rs = stmt.executeQuery();
					
					if (rs.next() ) {
						
						translationsByLocale = new HashMap<>();
						
						this.translations.put(localeCode, translationsByLocale);
					 
						do {
						
							translationsByLocale.put(rs.getString("label"), rs.getString("translation"));
							
					    } while (rs.next());
					}
				}
			}
		}
	}
	
	private String getTranslation(String label, Connection conn, BackOffice backOffice) throws SQLException {
		
		String translation = null;
		final String localeCode = getLocaleCode();
		Map<String, String> translationsByLocale = this.translations.get(localeCode);
		
		if (CollectionUtils.isEmpty(translationsByLocale)) {
			
			loadI18nLabels(conn, backOffice);
			
			translationsByLocale = this.translations.get(localeCode);
		}
		
		if (!CollectionUtils.isEmpty(translationsByLocale)) {
			
			translation = translationsByLocale.get(label);
		}
		
		return translation;
	}
	
	private void assignMetadata(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		Integer tableId = null;
		
		final String QRY_TABLE_METADATA = String
				.format("SELECT id, label, label_description, metadata FROM %s.sg_metadata_table WHERE table_name = ?", backOffice.getSchema());	
		
		final String QRY_COLUMN_METADATA = String
				.format("SELECT id, label, label_description, column_name, metadata FROM %s.sg_metadata_column WHERE sg_metadata_table_id = ?", backOffice.getSchema());
		
		try (PreparedStatement stmt = conn.prepareStatement(QRY_TABLE_METADATA)) {
			
			stmt.setString(1, table.getName());
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				tableId = rs.getInt("id");
				table.setId(tableId);
				table.setLabel(getTranslation(rs.getString("label"), conn, backOffice));
				table.setLabelDescription(getTranslation(rs.getString("label_description"), conn, backOffice));
				String metadata = rs.getString("metadata");
				
				if (!StringUtils.isEmpty(metadata)) {
				
					try {
						
						table.setMetadata((new ObjectMapper()).readValue(metadata, TableMetadata.class));
						
					} catch (JsonProcessingException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
			}
		}
		
		if (tableId != null && !CollectionUtils.isEmpty(table.getColumns())) {
			
			List<Column> columns = table.getColumns();
			
			try (PreparedStatement stmt = conn.prepareStatement(QRY_COLUMN_METADATA)) {
				
				stmt.setInt(1, tableId);
				
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					String columnName = rs.getString("column_name");
					
					if (!StringUtils.isEmpty(columnName)) {
					
						Column column = columns.stream().filter(e -> e.getName().equals(columnName)).findFirst()
								.orElse(null);
						
						if (column != null) {
							
							column.setId(rs.getInt("id"));
							column.setLabel(getTranslation(rs.getString("label"), conn, backOffice));
							column.setLabelDescription(getTranslation(rs.getString("label_description"), conn, backOffice));
							
							String metadata = rs.getString("metadata");
							
							if (!StringUtils.isEmpty(metadata)) {
							
								try {
									
									column.setMetadata((new ObjectMapper()).readValue(metadata, ColumnMetadata.class));
									
								} catch (JsonProcessingException e) {
									
									log.error(e.getMessage());
									log.debug(ExceptionUtils.getStackTrace(e));
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void traceTable(Table table) {
		
		log.debug(String.format("Table: %s(%d)", table.getName(), table.getId()));
		
		if (!CollectionUtils.isEmpty(table.getColumns())) {

			log.debug(String.format("\tColumns: %s",
					String.join(",", table.getColumns().stream()
							.map(e -> String.format("%s(%d) [%s%s]", e.getName(), e.getId(), e.getColType(),
									(e.getRefColumn() != null
											? String.format(" FK -> %s.%s", e.getRefColumn().getTable().getName(), e.getRefColumn().getName())
											: "")
									))
							.collect(Collectors.toList()))));
		}
		
		if (!CollectionUtils.isEmpty(table.getReferTables())) {
			
			log.debug(String.format("\tTables using: %s", String.join(",", table.getReferTables().stream()
					.map(e -> String.format("%s(%d)", e.getName(), e.getId())).collect(Collectors.toList()))));
		}
		
		if (!CollectionUtils.isEmpty(table.getRows())) {
			
			log.debug(String.format("\tRows: %d", table.getRows().size()));
		}
	}
	
	private String getLocaleCode() {
		
		String localeCode = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
		
		UserSessionUI userSession = sessionBO.getUserSession(httpSession.getId());
		
		if (userSession != null) {
			
			Object localeObj = userSession.getUser().get(UserSessionUI.USER_LOCALE);
			
			if (localeObj != null) {
				
				Map<String, Object> localeMap = ConversionUtil.objectToMap(localeObj);
				
				localeCode = String.valueOf(localeMap.get(UserSessionUI.USER_LOCALE_CODE));			
			}
		}

		return localeCode;
	}
}
