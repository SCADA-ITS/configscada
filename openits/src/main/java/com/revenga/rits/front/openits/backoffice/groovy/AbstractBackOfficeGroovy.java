package com.revenga.rits.front.openits.backoffice.groovy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.backoffice.BackOfficeUtil.DBType;
import com.revenga.rits.front.openits.backoffice.model.Request.RequestResult.RequestResultMessage;
import com.revenga.rits.front.openits.backoffice.model.Row;
import com.revenga.rits.front.openits.backoffice.model.Row.RowData;
import com.revenga.rits.front.openits.backoffice.model.Table;
import com.revenga.rits.front.openits.backoffice.model.Table.RequestType;
import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.BackOffice;

import lombok.Data;

@Data
public abstract class AbstractBackOfficeGroovy implements Cloneable {

	protected Environment env;
	protected Connection conn;
	protected BackOffice backOffice;
	protected Table table;
	protected Map<String, Object> requestParams;
	protected Long id;
	protected Row oldRow;
	protected Row newRow;
	protected RequestResultMessage requestResultMessage;
	protected org.apache.logging.log4j.Logger log;
	protected DBType dbType;

	protected AbstractBackOfficeGroovy(org.apache.logging.log4j.Logger log) {

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
	
	protected String readFromInputStream(InputStream inputStream) throws IOException {
		
		StringBuilder resultStringBuilder = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			
			String line;
			
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		
		return resultStringBuilder.toString();
	}
	
	protected Object getParamsObject(Class<?> clazz) {
		
		return ConversionUtil.mapToObject(clazz, this.requestParams);		
	}

	public void trace(RequestType persistenceType) {
		
		String prefix = String.format("Trigger %s on %s.%s: ", persistenceType,
				this.backOffice.getSchema(), this.table);
		
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
