package com.revenga.rits.front.openits.backoffice.model;

import java.io.Serializable;
import java.util.List;

import com.revenga.rits.front.openits.backoffice.model.Column.ColType;
import com.revenga.rits.front.openits.backoffice.model.Table.RequestType;

import lombok.Data;

@Data
public class Request implements Serializable {

	private static final long serialVersionUID = -7083949386292171354L;
	
	@Data
	public static class RequestResult implements Serializable {
	
		private static final long serialVersionUID = 5812000347882814475L;
		
		@Data
		public static class RequestResultMessage implements Serializable {
			
			private static final long serialVersionUID = 797119295266928034L;
			
			private String info;
			private String infoDescription;
			private String warn;
			private String warnDescription;
			private String error;
			private String errorDescription;
		}
		
		private Row oldRow;
		private Row newRow;
		private RequestResultMessage requestResultMessage = new RequestResultMessage();
	}
	
	@Data
	public static class RequestInput implements Serializable {
		
		private static final long serialVersionUID = 8519188939495926713L;
		
		private String colName;
		private String oldValue;
		private String newValue;
		private ColType colType;
	}
	
	private Long id;
	private String tableName;
	private String columnName;
	private Integer commandId;
	private RequestType requestType;
	
	private List<RequestInput> requestInput;
}