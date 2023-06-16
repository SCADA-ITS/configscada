package com.revenga.rits.front.openits.backoffice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.backoffice.BackOfficeUtil.DMLType;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistResult;
import com.revenga.rits.front.openits.service.BackOfficeManager;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
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
		
		return (new BackOfficePersist(this)).persist(backOfficeId, persist);
	}
	
	public PersistResult filler(Integer backOfficeId, String columnName, Persist persist) {
		
		return (new BackOfficeFiller(this)).onChange(backOfficeId, columnName, persist);
	}
	
	public Table getTable(Connection conn, BackOffice backOffice, String tableName) throws SQLException {
		
		return getTable(conn, backOffice, tableName, null , null);
	}
	
	public Table getTable(Connection conn, BackOffice backOffice, String tableName, Table parentTable) throws SQLException {
		
		return getTable(conn, backOffice, tableName, parentTable, null);
	}
	
	private Table getTable(Connection conn, BackOffice backOffice, String tableName, String conditions) throws SQLException {
		
		return getTable(conn, backOffice, tableName, null, conditions);
	}
	
	private Table getTable(Connection conn, BackOffice backOffice, String tableName, Table parentTable, String conditions) throws SQLException {
		
		Table table = this.referencedTables.get(tableName);
		
		if (table == null) {
			
			BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this);
			
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
					
					backOfficeAssignment.assignReferTables(conn, backOffice, table);
					
					if (Boolean.TRUE.equals(this.requestRows)) {
					
						backOfficeAssignment.assignRows(conn, backOffice, table, parentTable, conditions);
					}
					
					backOfficeAssignment.assignReferencedByTables(conn, backOffice, table);
					
					backOfficeAssignment.translateMetadata(conn, backOffice, table);
				}
			}
			else {
				
				table = new Table(tableName);
				
				this.referencedTables.put(tableName, table);
				
				backOfficeAssignment.assignColumns(conn, backOffice, table);
				
				backOfficeAssignment.assignReferTables(conn, backOffice, table);
				
				if (Boolean.TRUE.equals(this.requestRows)) {
				
					backOfficeAssignment.assignRows(conn, backOffice, table, parentTable, conditions);
				}
				
				backOfficeAssignment.assignReferencedByTables(conn, backOffice, table);
				
				backOfficeAssignment.assignMetadata(conn, backOffice, table);			
				
				this.backOfficeManager.putTable(backOffice, table);
			}
			
			if (table != null) {
				
				traceTable(table);
			}
		}
		
		return table;
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
