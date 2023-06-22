package com.revenga.rits.front.openits.model.backoffice;

import java.io.File;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Attachment implements Serializable {

	private static final long serialVersionUID = -2199350032533894812L;
	
	public enum Type { IMAGE, OTHER }
	
	private Integer id;
	private Type type;
	@JsonIgnore
	private Table table;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer tableId;
	
	private Integer idValue;
	
	private String groupName;
	private String fileName;
	private String description;
	private Integer position;
	
	public Integer getTableId() {
		
		if (tableId != null) {
			
			return tableId;
		}
		else {
			
			if (table != null) {
				
				return table.getId();
			}
		}
		
		return null;
	}
	
	public String getPath(String baseDir, Integer backofficeId) {
		
		return String.join(File.separator, baseDir, "backoffice", backofficeId.toString(),
				this.getTable().getName(), this.getId().toString(),
				this.getType().toString());
	}
}
