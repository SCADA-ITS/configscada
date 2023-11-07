	package com.revenga.rits.front.openits.model.backoffice;

import java.util.ArrayList;
import java.util.List;
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
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class Filler extends GroovyBasic {
		
		private static final long serialVersionUID = -1605938528996576885L;

		private static final String PACKAGE_NAME = "com.revenga.rits.front.openits.backoffice.groovy.filler";
		
		public Filler(String name) {
			
			super();
			
			this.name = name;
			
			this.id = Objects.hash(this.getClass().getName(), this.name);
			this.packageName = PACKAGE_NAME; 
		}
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
	
	private Boolean needsTranslation;
	
	private Boolean nullable;
	
	private Boolean primaryKey;
	
	private Boolean identity;
	
	private String defaultValue;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Filler> fillers = new ArrayList<>();
	
	@JsonIgnore
	private Integer metadataId;
	
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
