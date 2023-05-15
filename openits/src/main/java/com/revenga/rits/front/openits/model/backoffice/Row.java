package com.revenga.rits.front.openits.model.backoffice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Row implements Serializable {
	
	private static final long serialVersionUID = 3515110338135944787L;
	
	@Data
	public static class RowData implements Serializable {
		
		private static final long serialVersionUID = 1689896293999857137L;
		
		@JsonIgnore
		private Column column;
		
		private Integer columnId;
		private String value;
		
		public RowData(Column column, String value) {
			
			super();
			this.column = column;
			this.value = value;
			this.columnId = null;
			
			if (column != null) {
				
				this.columnId = column.getId();
			}
		}
	}
	
	private List<RowData> data = new ArrayList<>();
}
