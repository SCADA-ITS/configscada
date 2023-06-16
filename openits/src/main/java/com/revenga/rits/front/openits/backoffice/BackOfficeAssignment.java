package com.revenga.rits.front.openits.backoffice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revenga.rits.front.openits.backoffice.BackOfficeUtil.DMLType;
import com.revenga.rits.front.openits.model.backoffice.Basic;
import com.revenga.rits.front.openits.model.backoffice.Column;
import com.revenga.rits.front.openits.model.backoffice.Column.Filler;
import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Row.RowData;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.Command;
import com.revenga.rits.front.openits.model.backoffice.Table.Trigger;
import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.BackOffice;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackOfficeAssignment {
	
	BackOfficeProcessor backOfficeProcessor;
	
	public BackOfficeAssignment(BackOfficeProcessor backOfficeProcessor) {
		
		this.backOfficeProcessor = backOfficeProcessor;
	}
	
	List<Column> assignColumns(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		List<Column> cols = table.getColumns();
		
		String sql = BackOfficeUtil.getDmlLib(BackOfficeUtil.getDBType(conn), DMLType.COLUMNS_TABLE);
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, table.getName());
			stmt.setString(2, backOffice.getSchema());
			
			if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
				
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
	
	List<Table> assignReferTables(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		List<Table> referTables = null;
		
		List<Column> refColumns = table.getColumns().stream().filter(e -> e.getRefColumn() != null)
				.map(Column::getRefColumn).collect(Collectors.toList());
		
		if (!CollectionUtils.isEmpty(refColumns)) {
			
			referTables = refColumns.stream().map(Column::getTable).collect(Collectors.toList());
			
			for (Table referTable : referTables) {
				
				this.backOfficeProcessor.getTable(conn, backOffice, referTable.getName());
			}
		}
		
		if (CollectionUtils.isEmpty(referTables)) {
		
			String sql = BackOfficeUtil.getDmlLib(BackOfficeUtil.getDBType(conn), DMLType.REFERENCE_TABLES);
			
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				referTables = new ArrayList<>();
				
				stmt.setString(1, table.getName());
				stmt.setString(2, backOffice.getSchema());
				
				if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
					
					log.debug(stmt);					
				}
				
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					String sourceTableName = rs.getString("source");
					String sourceCol = rs.getString("source_col");
					String targetTableName = rs.getString("target");
					String targetCol = rs.getString("target_col");
					
					if (sourceTableName.equals(table.getName())) {
						
						Table referTable = this.backOfficeProcessor.getTable(conn, backOffice, targetTableName);
						
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
	
	List<String> assignReferencedByTables(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		List<String> referTableNames = table.getReferTableNames();
		
		if (!CollectionUtils.isEmpty(referTableNames)) {
			
			for (String tableName : referTableNames) {
				
				this.backOfficeProcessor.getTable(conn, backOffice, tableName, table);
			}
		}
		else {
			
			String sql = BackOfficeUtil.getDmlLib(BackOfficeUtil.getDBType(conn), DMLType.REFERENCED_BY_TABLE);
			
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				stmt.setString(1, backOffice.getSchema()+ "." + table.getName());
				
				if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
					
					log.debug(stmt);					
				}
				
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					String tableReferenceName = rs.getString("table_reference");
					
					tableReferenceName = tableReferenceName.replace(backOffice.getSchema()+ ".", "");
					
					Table referTable = this.backOfficeProcessor.getTable(conn, backOffice, tableReferenceName, table);
					
					if (referTable != null) {
					
						referTableNames.add(referTable.getName());
					}
				}
			}
		}
		
		return referTableNames;
	}
	
	List<Row> assignRows(Connection conn, BackOffice backOffice, Table table, Table parentTable, String conditions) throws SQLException {
	
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

						if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
							
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
									rowData.setValue(this.backOfficeProcessor.getBackOfficeManager().translate(
											backOffice, conn, this.backOfficeProcessor.getLocaleCode(), value));
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
	
	void assignMetadata(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		assignMetadataTable(conn, backOffice, table);
		assignMetadataColumns(conn, backOffice, table);
		assignTriggers(conn, backOffice, table);
		assignFillers(conn, backOffice, table);
		assignCommands(conn, backOffice, table);
	}
	
	void translateMetadata(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		if (!StringUtils.isEmpty(this.backOfficeProcessor.getLocaleCode())) {
		
			this.backOfficeProcessor.getBackOfficeManager().translateBasic(backOffice, conn, this.backOfficeProcessor.getLocaleCode(), table);
			
			table.setLabelSingularTranslated(this.backOfficeProcessor.getBackOfficeManager().translate(backOffice, conn, this.backOfficeProcessor.getLocaleCode(), table.getLabelSingular()));
			
			List<Column> cols = table.getColumns();
			
			if (!CollectionUtils.isEmpty(cols)) {
				
				for (Column col : cols) {
					
					this.backOfficeProcessor.getBackOfficeManager().translateBasic(backOffice, conn, this.backOfficeProcessor.getLocaleCode(), col);
				}
			}
			
			List<Command> cmds = table.getCommands();
			
			if (!CollectionUtils.isEmpty(cmds)) {
				
				for (Command cmd : cmds) {
					
					this.backOfficeProcessor.getBackOfficeManager().translateBasic(backOffice, conn, this.backOfficeProcessor.getLocaleCode(), cmd);
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
			
			if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
				
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
				
				if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
					
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
			
			if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
				
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
	
	private void assignFillers(Connection conn, BackOffice backOffice, Table table) throws SQLException {
		
		if (!CollectionUtils.isEmpty(table.getColumns())) {
			
			for (Column column : table.getColumns()) {
				
				assignFillers(conn, backOffice, column);
			}
		}
	}
	
	private void assignFillers(Connection conn, BackOffice backOffice, Column column) throws SQLException {
		
		final String QRY_TABLE_FILLERS = String.format(
				"SELECT fill.name, fill.groovy " +
				"  FROM %s.sg_metadata_columns c, " +
				"       %s.sg_metadata_column_fillers fill " + 
				" WHERE fill.sg_metadata_column_id = c.id " + 
				"   AND c.column_name = ?",
				backOffice.getSchema(), backOffice.getSchema());

		try (PreparedStatement stmt = conn.prepareStatement(QRY_TABLE_FILLERS)) {
			
			stmt.setString(1, column.getName());
			
			if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
				
				log.debug(stmt);					
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Filler filler = new Filler(rs.getString("name"));
				
				filler.setGroovy(rs.getString("groovy"));
				
				column.getFillers().add(filler);				
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
			
			if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
				
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
}
