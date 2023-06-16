package com.revenga.rits.front.openits.backoffice.groovy;

import java.sql.Connection;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Row.RowData;
import com.revenga.rits.front.openits.model.backoffice.Table.PersistenceType;

import lombok.Data;

@Data
public abstract class AbstractBackOfficeGroovy implements Cloneable {

	protected Connection conn;
	protected String schema;
	protected Table table;
	protected Integer id;
	protected Row oldRow;
	protected Row newRow;
	protected org.apache.logging.log4j.Logger log;

	protected AbstractBackOfficeGroovy(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	protected AbstractBackOfficeGroovy(Connection conn, String schema, Table table, Integer id, Row oldRow, Row newRow,
			org.apache.logging.log4j.Logger log) {
	
		this.conn = conn;
		this.schema = schema;
		this.table = table;
		this.id = id;
		this.oldRow = oldRow;
		this.newRow = newRow;
		this.log = log;
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
	
	protected void setFieldValue(Row row, String fieldName, String value) {
		
		if (row != null) {
			
			List<RowData> rowDataList = row.getData();
			
			if (!CollectionUtils.isEmpty(rowDataList)) {
				
				for (RowData rowData : rowDataList) {
					
					if (rowData.getColumn().getName().equals(fieldName)) {
						
						rowData.setValue(value);
						
						break;
					}
				}
			}
		}
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
	
	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
}
