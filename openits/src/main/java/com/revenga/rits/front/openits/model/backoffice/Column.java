package com.revenga.rits.front.openits.model.backoffice;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class Column extends Basic {
	
	private static final long serialVersionUID = 6576786859530145231L;
	
	public enum ColType { INTEGER, DECIMAL, STRING, BOOLEAN, TIMESTAMP }
	
//	@Data
//	public static class ColumnMetadata implements Serializable {
//		
//		private static final long serialVersionUID = 5747863839138739437L;
//		
//		private String name;
//		private Integer position;
//		private Integer maxLength;
//		private Integer multiLine;
//		private String mdiIcon;
//		private Boolean refName;
//		private Boolean required;
//		private Boolean visible;
//	}
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Table table;
	
	@Setter(AccessLevel.NONE)
	private Integer tableId;
	
	private ColType colType;
	
	private Column refColumn;
	
	private Integer position;
	
	private Map<String, Object> metadata;
	
	public Column(Table table, String name) {
		
		super();
		
		this.table = table;
		this.name = name;
		
		this.id = Objects.hash(this.getClass().getName(), this.table.getName() + this.name);
	}
	
	public Integer getTableId() {
		
		return table != null ? table.getId() : null;
	}
}
