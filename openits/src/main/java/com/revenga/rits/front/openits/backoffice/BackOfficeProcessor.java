package com.revenga.rits.front.openits.backoffice;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revenga.rits.front.openits.backoffice.BackOfficeUtil.DMLType;
import com.revenga.rits.front.openits.model.backoffice.Basic;
import com.revenga.rits.front.openits.model.backoffice.Column;
import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Row.RowData;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.Command;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistResult;
import com.revenga.rits.front.openits.model.backoffice.Table.Trigger;
import com.revenga.rits.front.openits.service.BackOfficeManager;
import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackOfficeProcessor {
	
	GroovyManager groovyManager;
	BackOfficeService backOfficeService;
	BackOfficeManager backOfficeManager;
	String localeCode;
	Boolean requestRows;
	
	private Map<String, Table> referencedTables = new HashMap<>();
	
	public BackOfficeProcessor(GroovyManager groovyManager, BackOfficeService backOfficeService, BackOfficeManager backOfficeManager, String localeCode, Boolean requestRows) {
		
		this.groovyManager = groovyManager;
		this.backOfficeService = backOfficeService;
		this.backOfficeManager = backOfficeManager;
		this.localeCode = localeCode;
		this.requestRows = requestRows;
	}
	
	public List<Table> getAll(Integer backOfficeId) {
		
		List<Table> tables = null;
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		if (backOffice != null) {
			
			this.backOfficeManager.clearTables(backOffice);
			
			this.referencedTables.clear();
		
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				String sql = BackOfficeUtil.getDmlLib(BackOfficeUtil.getDBType(conn), DMLType.TABLES);
				
				try (PreparedStatement stmt = conn.prepareStatement(sql)) {
					
					stmt.setString(1, backOffice.getSchema());
					
					if (this.backOfficeManager.isShowSql()) {
						
						log.debug(stmt);					
					}
					
					ResultSet rs = stmt.executeQuery();
					
					while (rs.next()) {
						
						String tableName = rs.getString("table_name");
						
						if (!this.referencedTables.containsKey(tableName)) {
							
							getTable(conn, backOffice, tableName);
						}
					}
					
					if (!CollectionUtils.isEmpty(this.referencedTables)) {
						
						tables = this.referencedTables.values().stream().collect(Collectors.toList());
						
						Collections.sort(tables, Comparator.comparing(Table::getName));
					}
				}
			}
			catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return tables;
	}

	
	public List<Table> get(Integer backOfficeId, String tableName, String conditions) {

		List<Table> tables = null;
		
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		if (backOffice != null) {
		
			this.referencedTables.clear();
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
			
				getTable(conn, backOffice, tableName, conditions);
				
				if (!CollectionUtils.isEmpty(this.referencedTables)) {
				
					tables = this.referencedTables.values().stream().collect(Collectors.toList());
					
					Collections.sort(tables, Comparator.comparing(Table::getName));
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
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		String sql = null;
		
		if (backOffice != null) {
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				conn.setAutoCommit(false);
				
				sql = BackOfficeUtil.getDml(conn, backOffice, persist);
				
				if (!StringUtils.isEmpty(sql)) {
					
					Table tableRepository = this.backOfficeManager.getTable(backOffice, persist.getTableName());
					
					if (tableRepository != null) {
						
						Table table = (Table) tableRepository.clone();
						
						if (table != null) {
							
							table.getRows().clear();
							
							List<Row> rowsOld = assignRows(conn, backOffice, table, null, "id = " + persist.getId());
							
							if (!CollectionUtils.isEmpty(rowsOld) && rowsOld.size() == 1) {
								
								persistResult.setOldRow(rowsOld.get(0));
							}
								
							try (Statement stmt = conn.createStatement()) {
								
								//log.debug(String.format("Executing DML [%s]: %s", persist.getPersistenceType(), sql));
								
								if (this.backOfficeManager.isShowSql()) {
									
									log.debug(stmt);					
								}
								
								executeTriggers(Trigger.Type.BEFORE, conn, backOffice, table, persist, persistResult);
								
								int rowsAffected = stmt.executeUpdate(sql);
								
								//log.debug(String.format("Executed DML [%s] %d rows affected ", persist.getPersistenceType(), rowsAffected));

								if (rowsAffected == 1) {
									
									Table tableNew = new Table(persist.getTableName());
									
									tableNew.setColumns(table.getColumns());
									
									List<Row> rowsNew = assignRows(conn, backOffice, tableNew, null, "id = " + persist.getId());
									
									if (!CollectionUtils.isEmpty(rowsNew) && rowsNew.size() == 1) {
										
										persistResult.setNewRow(rowsNew.get(0));
									}
									
									executeTriggers(Trigger.Type.AFTER, conn, backOffice, table, persist, persistResult);
								}
								else {
									
									// TODO: Cascade rows affected???
								}
							}
							
							conn.commit();
						}
					}
				}
				
			} catch (Exception e) {
				
				String message = e.getMessage() + (!StringUtils.isEmpty(sql) ? " SQL: " + sql : "");
				String stackTrace = ExceptionUtils.getStackTrace(e);
				
				persistResult.setError(message);
				persistResult.setErrorDescription(ExceptionUtils.getStackTrace(e));
				
				log.error(message);
				log.debug(stackTrace);
			}
		}
		
		return persistResult;
	}

	private void executeTriggers(Trigger.Type triggerType, Connection conn, BackOffice backOffice, Table table, Persist persist, PersistResult persistResult) throws Exception {

		List<Trigger> triggers = table.getTriggers();
		
		if (!CollectionUtils.isEmpty(triggers)) {
			
			for (Trigger trigger : triggers) {
				
				AbstractBackOfficeTrigger backOfficeTrigger = getTriggerInstance(trigger, conn, backOffice, table, persist, persistResult);
				
				if (backOfficeTrigger != null) {
					
					backOfficeTrigger.trace(persist.getPersistenceType());
					
					boolean triggerResult = false;
					
					triggerResult = (boolean) Trigger.call(triggerType, persist.getPersistenceType(), backOfficeTrigger);
										
					if (!triggerResult) {
						
						throw new BackOfficePersistException(
								String.format("Trigger %s on table: %s.%s with operation: %s fails.", trigger.getGroovy(),
										backOffice.getSchema(), table.getName(), persist.getPersistenceType()));
					}
				}
			}
		}
	}
	
	private AbstractBackOfficeTrigger getTriggerInstance(Trigger trigger, Connection conn, BackOffice backOffice, Table table, Persist persist, PersistResult persistResult) {
		
		AbstractBackOfficeTrigger backOfficeTrigger = null;
		String groovyFile = trigger.getGroovy();
		
		if (!StringUtils.isEmpty(groovyFile)) {
			
			Object triggerInstance = null;
			
			try {
				
				Path path = Paths.get(groovyFile);
				
				if (path != null) {
					
					String fileName = path.getFileName().toString();
					
					if (!StringUtils.isEmpty(fileName)) {
				
						Class<?> clazz = Class.forName("com.revenga.rits.front.openits.backoffice.trigger."
								+ fileName.substring(0, fileName.indexOf(".")));
						
						if (AbstractBackOfficeTrigger.class.isAssignableFrom(clazz)) {
						
							triggerInstance = clazz.getDeclaredConstructor(org.apache.logging.log4j.Logger.class).newInstance(log);
						}
					}
				}
				
			} catch (ClassNotFoundException e) {
				
				// Nothing to do
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			
			if (triggerInstance == null) {
				
				triggerInstance = groovyManager.getGroovyObject(groovyFile, GroovyManager.GroovyType.BACKOFFICE);
			}
			
			if (triggerInstance instanceof AbstractBackOfficeTrigger) {
			
				backOfficeTrigger = (AbstractBackOfficeTrigger) triggerInstance;
				
				backOfficeTrigger.setConn(conn);
				backOfficeTrigger.setSchema(backOffice.getSchema());
				backOfficeTrigger.setTable(table.getName());
				backOfficeTrigger.setId(persist.getId());
				backOfficeTrigger.setOldRow(persistResult.getOldRow());
				backOfficeTrigger.setNewRow(persistResult.getNewRow());
			}
		}
		
		return backOfficeTrigger;
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
			
			if (this.backOfficeManager.containsTable(backOffice, tableName)) {
				
				Table tableRepository = this.backOfficeManager.getTable(backOffice, tableName);
				
				if (tableRepository != null) {
					
					try {
						
						table = (Table) tableRepository.clone();
						
					} catch (CloneNotSupportedException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
				
				if (table != null) {
					
					table.getRows().clear();
					
					this.referencedTables.put(tableName, table);
					
					assignReferTables(conn, backOffice, table);
					
					if (Boolean.TRUE.equals(this.requestRows)) {
					
						assignRows(conn, backOffice, table, parentTable, conditions);
					}
					
					assignReferencedByTables(conn, backOffice, table);
					
					translateMetadata(conn, backOffice, table);
				}
			}
			else {
				
				table = new Table(tableName);
				
				this.referencedTables.put(tableName, table);
				
				assignColumns(conn, backOffice, table);
				
				assignReferTables(conn, backOffice, table);
				
				if (Boolean.TRUE.equals(this.requestRows)) {
				
					assignRows(conn, backOffice, table, parentTable, conditions);
				}
				
				assignReferencedByTables(conn, backOffice, table);
				
				assignMetadata(conn, backOffice, table);			
				
				this.backOfficeManager.putTable(backOffice, table);
			}
			
			traceTable(table);
		}
		
		return table;
	}
	
	private List<Column> assignColumns(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		List<Column> cols = table.getColumns();
		
		String sql = BackOfficeUtil.getDmlLib(BackOfficeUtil.getDBType(conn), DMLType.COLUMNS_TABLE);
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, table.getName());
			stmt.setString(2, backOffice.getSchema());
			
			if (this.backOfficeManager.isShowSql()) {
				
				log.debug(stmt);					
			}

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
				Column col = new Column(table, rs.getString("column_name"));

				col.setPosition(rs.getInt("ordinal_position"));
				
				BackOfficeUtil.setColType(col, BackOfficeUtil.getDBType(conn), rs.getString("data_type"));
				
				cols.add(col);			
			}
			
			Collections.sort(cols, Comparator.comparingInt(Column::getPosition));
		}
		
		return cols;
	}
	
	private List<Table> assignReferTables(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		List<Table> referTables = null;
		
		List<Column> refColumns = table.getColumns().stream().filter(e -> e.getRefColumn() != null)
				.map(Column::getRefColumn).collect(Collectors.toList());
		
		if (!CollectionUtils.isEmpty(refColumns)) {
			
			referTables = refColumns.stream().map(Column::getTable).collect(Collectors.toList());
			
			for (Table referTable : referTables) {
				
				getTable(conn, backOffice, referTable.getName());
			}
		}
		
		if (CollectionUtils.isEmpty(referTables)) {
		
			String sql = BackOfficeUtil.getDmlLib(BackOfficeUtil.getDBType(conn), DMLType.REFERENCE_TABLES);
			
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				referTables = new ArrayList<>();
				
				stmt.setString(1, table.getName());
				stmt.setString(2, backOffice.getSchema());
				
				if (this.backOfficeManager.isShowSql()) {
					
					log.debug(stmt);					
				}
				
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
		}
		
		return referTables;
	}
	
	private List<String> assignReferencedByTables(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		List<String> referTableNames = table.getReferTableNames();
		
		if (!CollectionUtils.isEmpty(referTableNames)) {
			
			for (String tableName : referTableNames) {
				
				getTable(conn, backOffice, tableName, table);
			}
		}
		else {
			
			String sql = BackOfficeUtil.getDmlLib(BackOfficeUtil.getDBType(conn), DMLType.REFERENCED_BY_TABLE);
			
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				stmt.setString(1, backOffice.getSchema()+ "." + table.getName());
				
				if (this.backOfficeManager.isShowSql()) {
					
					log.debug(stmt);					
				}
				
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					String tableReferenceName = rs.getString("table_reference");
					
					tableReferenceName = tableReferenceName.replace(backOffice.getSchema()+ ".", "");
					
					Table referTable = getTable(conn, backOffice, tableReferenceName, table);
					
					if (referTable != null) {
					
						referTableNames.add(referTable.getName());
					}
				}
			}
		}
		
		return referTableNames;
	}
	
	private List<Row> assignRows(Connection conn, BackOffice backOffice, Table table, Table parentTable, String conditions) throws SQLException {
	
		List<Row> rows = table.getRows();
		
		if (!CollectionUtils.isEmpty(table.getColumns())) {
			
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
					
					if (colRef != null && !CollectionUtils.isEmpty(idsParent)) {
					
						sql += String.format(" WHERE %s IN(%s)", colRef.getName(),
								String.join(",", idsParent.stream().collect(Collectors.toList())));
					}
					else {
						
						sql = null;
					}
				}
				
				if (!StringUtils.isEmpty(sql)) {
					
					if (!StringUtils.isEmpty(conditions)) {
						
						sql += (!sql.contains("WHERE") ? " WHERE " : " AND ") + conditions;
					}
					
					try (Statement stmt = conn.createStatement()) {

						if (this.backOfficeManager.isShowSql()) {
							
							log.debug(stmt);					
						}
						
						ResultSet rs = stmt.executeQuery(sql);

						while (rs.next()) {
							
							Row row = new Row();
							
							List<RowData> rowDataList = row.getData();
							
							for (Column column : table.getColumns()) {
								
								String value = BackOfficeUtil.getStringValueFromResultSet(column, rs);
								RowData rowData = new RowData(column, value);
								
								if (Boolean.TRUE.equals(column.getNeedsTranslation())) {
									
									rowData.setLabel(value);
									rowData.setValue(this.backOfficeManager.translate(backOffice, conn, this.localeCode, value));
								}
								
								rowDataList.add(rowData);
							}
							
							rows.add(row);
						}
					}
				}
			}
		}
		
		return rows;
	}
	
	
	
	private void assignMetadata(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		assignMetadataTable(conn, backOffice, table);
		assignMetadataColumns(conn, backOffice, table);
		assignTriggers(conn, backOffice, table);
		assignCommands(conn, backOffice, table);
		
	}
	
	private void translateMetadata(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		if (!StringUtils.isEmpty(this.localeCode)) {
		
			backOfficeManager.translateBasic(backOffice, conn, this.localeCode, table);
			
			table.setLabelSingularTranslated(backOfficeManager.translate(backOffice, conn, this.localeCode, table.getLabelSingular()));
			
			List<Column> cols = table.getColumns();
			
			if (!CollectionUtils.isEmpty(cols)) {
				
				for (Column col : cols) {
					
					backOfficeManager.translateBasic(backOffice, conn, this.localeCode, col);
				}
			}
			
			List<Command> cmds = table.getCommands();
			
			if (!CollectionUtils.isEmpty(cmds)) {
				
				for (Command cmd : cmds) {
					
					backOfficeManager.translateBasic(backOffice, conn, this.localeCode, cmd);
				}
			}
		}
	}
	
	private void assignMetadataTable(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
//		Integer tableId = null;
		
		final String QRY_TABLE_METADATA = String
				.format("SELECT id, label, label_singular, label_description, metadata " + 
						"  FROM %s.sg_metadata_tables " + 
						" WHERE name = ?", 
						backOffice.getSchema());	
		
		try (PreparedStatement stmt = conn.prepareStatement(QRY_TABLE_METADATA)) {
			
			stmt.setString(1, table.getName());
			
			if (this.backOfficeManager.isShowSql()) {
				
				log.debug(stmt);					
			}
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				table.setLabel(rs.getString("label"));
				table.setLabelSingular(rs.getString("label_singular"));
				table.setLabelDescription(rs.getString("label_description"));
				String metadata = rs.getString("metadata");
				
				if (!StringUtils.isEmpty(metadata)) {

					try {
						
						table.setMetadata(ConversionUtil.stringToMap(metadata));	
						
					} catch (JsonProcessingException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
			}
		}
	}
	
	private void assignMetadataColumns(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		final String QRY_COLUMN_METADATA = String
				.format("SELECT cols.id, cols.label, cols.label_description, cols.column_name, cols.metadata, cols.needs_translation " + 
						"  FROM %s.sg_metadata_tables t, " +
						"       %s.sg_metadata_columns cols " +
						" WHERE cols.sg_metadata_table_id = t.id " +
						"   AND t.name = ? ",
						backOffice.getSchema(), backOffice.getSchema());
		
		if (table != null && !CollectionUtils.isEmpty(table.getColumns())) {
			
			List<Column> columns = table.getColumns();
			
			try (PreparedStatement stmt = conn.prepareStatement(QRY_COLUMN_METADATA)) {
				
				stmt.setString(1, table.getName());
				
				if (this.backOfficeManager.isShowSql()) {
					
					log.debug(stmt);					
				}
				
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					String columnName = rs.getString("column_name");
					
					if (!StringUtils.isEmpty(columnName)) {
					
						Column column = columns.stream().filter(e -> e.getName().equals(columnName)).findFirst()
								.orElse(null);
						
						if (column != null) {
							
							//column.setId(rs.getInt("id"));
							column.setLabel(rs.getString("label"));
							column.setLabelDescription(rs.getString("label_description"));
							column.setNeedsTranslation(rs.getBoolean("needs_translation"));
							
							String metadata = rs.getString("metadata");
							
							if (!StringUtils.isEmpty(metadata)) {
							
								try {
									
									column.setMetadata(ConversionUtil.stringToMap(metadata));
									
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
	
	private void assignTriggers(Connection conn, BackOffice backOffice, Table table) throws SQLException {
			
		final String QRY_TABLE_TRIGGERS = String.format(
				"SELECT trig.name, trig.groovy " +
				"  FROM %s.sg_metadata_tables t, " +
				"       %s.sg_metadata_table_triggers trig " + 
				" WHERE trig.sg_metadata_table_id = t.id " + 
				"   AND t.name = ?",
				backOffice.getSchema(), backOffice.getSchema());

		try (PreparedStatement stmt = conn.prepareStatement(QRY_TABLE_TRIGGERS)) {
			
			stmt.setString(1, table.getName());
			
			if (this.backOfficeManager.isShowSql()) {
				
				log.debug(stmt);					
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Trigger trigger = new Trigger(rs.getString("name"));
				
				trigger.setGroovy(rs.getString("groovy"));
				
				table.getTriggers().add(trigger);				
			}
		}
	}
	
	private void assignCommands(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		final String QRY_TABLE_COMMANDS = String.format(
				"SELECT cmds.id, cmds.name, cmds.label, cmds.label_description, cmds.groovy " +
				"  FROM %s.sg_metadata_tables t, " +
				"       %s.sg_metadata_table_commands cmds " +
				" WHERE cmds.sg_metadata_table_id = t.id " +
				"   AND t.name = ?",
				backOffice.getSchema(), backOffice.getSchema());
		
		try (PreparedStatement stmt = conn.prepareStatement(QRY_TABLE_COMMANDS)) {
			
			stmt.setString(1, table.getName());
			
			if (this.backOfficeManager.isShowSql()) {
				
				log.debug(stmt);					
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Command command = new Command(rs.getString("name"));
				
				command.setLabel(rs.getString("label"));
				command.setLabelDescription(rs.getString("label_description"));
				command.setGroovy(rs.getString("groovy"));
				
				table.getCommands().add(command);				
			}
		}
	}
	private void traceTable(Table table) {
		
		if (this.backOfficeManager.isTraceTable()) {
			
			log.debug(String.format("Table: %s(%d)", table.getName(), table.getId()));
			
			if (!CollectionUtils.isEmpty(table.getColumns())) {

				log.debug(String.format("   >> Columns: %s",
						String.join(",", table.getColumns().stream()
								.map(e -> String.format("%s(%d) [%s%s]", e.getName(), e.getId(), e.getColType(),
										(e.getRefColumn() != null
												? String.format(" FK -> %s.%s", e.getRefColumn().getTable().getName(), e.getRefColumn().getName())
												: "")
										))
								.collect(Collectors.toList()))));
			}
			
			if (!CollectionUtils.isEmpty(table.getRows())) {
				
				log.debug(String.format("   >> Rows: %d", table.getRows().size()));
			}
		}
	}
}
