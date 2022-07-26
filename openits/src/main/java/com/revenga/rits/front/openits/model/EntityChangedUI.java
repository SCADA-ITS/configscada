package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class EntityChangedUI implements Serializable {
	
	private static final long serialVersionUID = -57367744694760444L;
	
	public static final int ENTITY_CREATED = 1;
	public static final int ENTITY_UPDATED = 2;
	public static final int ENTITY_DELETED = 3;		
	
	private int entityTypeId;
	private String id;
	private int operation;
	private long changed;
	private Map<String, Object> entity;
	
	public EntityChangedUI(int entityTypeId, String id, int operation, long changed) {
		
		super();
		this.entityTypeId = entityTypeId;
		this.id = id;
		this.operation = operation;
		this.changed = changed;
		this.entity = new HashMap<>();
	}
	
	public EntityChangedUI(int entityTypeId, String id, int operation, long changed, Map<String, Object> entity) {
		
		super();
		this.entityTypeId = entityTypeId;
		this.id = id;
		this.operation = operation;
		this.changed = changed;
		this.entity = entity;
	}
}