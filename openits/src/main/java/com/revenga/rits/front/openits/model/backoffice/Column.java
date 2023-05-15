package com.revenga.rits.front.openits.model.backoffice;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revenga.rits.front.openits.util.DBUtil.ColType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class Column extends Basic {
	
	private static final long serialVersionUID = 6576786859530145231L;
	
	@Data
	public static class ColumnMetadata implements Serializable {
		
		private static final long serialVersionUID = 5747863839138739437L;
		
		private String name;
		private Integer position;
		private Integer maxLength;
		private Integer multiLine;
		private String mdiIcon;
		private Boolean refName;
		private Boolean required;
		private Boolean visible;
	}
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Table table;
	
	@Setter(AccessLevel.NONE)
	private Integer tableId;
	
	private ColType colType;
	
	private Column refColumn;
	
	private Integer position;
	
	private ColumnMetadata metadata;
	
	public Column(String name) {
		
		super(name);
	}
	
	public Integer getTableId() {
		
		return table != null ? table.getId() : null;
	}
}
