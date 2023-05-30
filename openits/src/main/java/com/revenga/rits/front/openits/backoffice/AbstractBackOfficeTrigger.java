package com.revenga.rits.front.openits.backoffice;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Row.RowData;
import com.revenga.rits.front.openits.model.backoffice.Table.PersistenceType;

import lombok.Data;

@Data
public abstract class AbstractBackOfficeTrigger {

	protected Connection conn;
	protected String schema;
	protected String table;
	protected Integer id;
	protected Row oldRow;
	protected Row newRow;
	protected org.apache.logging.log4j.Logger log;

	protected AbstractBackOfficeTrigger(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	protected AbstractBackOfficeTrigger(Connection conn, String schema, String table, Integer id, Row oldRow, Row newRow, org.apache.logging.log4j.Logger log) {
	
		this.conn = conn;
		this.schema = schema;
		this.table = table;
		this.id = id;
		this.oldRow = oldRow;
		this.newRow = newRow;
	}

	public boolean onBeforeInsert() throws Exception{

		return true;
	}

	public boolean onBeforeUpdate() throws Exception {

		return true;
	}

	public boolean onBeforeDelete() throws Exception {

		return true;
	}
	
	public boolean onAfterInsert() throws Exception {

		return true;
	}

	public boolean onAfterUpdate() throws Exception {

		return true;
	}

	public boolean onAfterDelete() throws Exception {

		return true;
	}

	public void trace(PersistenceType persistenceType) {
		
		String prefix = String.format("Trigger %s on %s.%s: ", persistenceType,
				this.schema, this.table);
		
		log.debug(prefix + "Executing...");
		
		if (this.oldRow != null) {
			
			this.log.debug(String.format("%s Old values => %s", prefix, this.oldRow));
		}
		
		if (this.newRow != null) {
		
			this.log.debug(String.format("%s New values => %s", prefix, this.newRow));
		}
	}
	
	protected String getFieldValue(Row row, String fieldName) {
		
		String value = null;
		
		if (row != null) {
			
			List<RowData> rowDataList = row.getData();
			
			if (!CollectionUtils.isEmpty(rowDataList)) {
				
				for (RowData rowData : rowDataList) {
					
					if (rowData.getColumn().getName().equals(fieldName)) {
						
						value = rowData.getValue();
						
						break;
					}
				}
			}
		}
		
		return value;
	}
	
	public void traceSQL(Statement stmt) {
		
		log.debug(String.format("Executing DML on Trigger: %s", stmt.toString()));
	}
}
