package com.revenga.rits.front.openits.model.backoffice;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revenga.rits.front.openits.model.backoffice.Column.ColType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class Table extends Basic {
	
	private static final long serialVersionUID = 6887327590735622987L;
	
	public enum PersistenceType { INSERT, UPDATE, DELETE, FILL }
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class GroovyBasic extends Basic {
		
		private static final long serialVersionUID = -8133168767393817311L;
		
		protected String groovy;
		protected String packageName;
	}
	
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class Trigger extends GroovyBasic {
		
		private static final long serialVersionUID = 7426288403894220149L;
		
		private static final String PACKAGE_NAME = "com.revenga.rits.front.openits.backoffice.groovy.trigger";
		
		public enum Type { BEFORE, AFTER }
		
		private static final Map<Pair<Trigger.Type, PersistenceType>, String> triggerMethod = new HashMap<>() {

			private static final long serialVersionUID = 1L;

			{
				put(new ImmutablePair<>(Trigger.Type.BEFORE, PersistenceType.INSERT), "onBeforeInsert");
				put(new ImmutablePair<>(Trigger.Type.BEFORE, PersistenceType.UPDATE), "onBeforeUpdate");
				put(new ImmutablePair<>(Trigger.Type.BEFORE, PersistenceType.DELETE), "onBeforeDelete");
				put(new ImmutablePair<>(Trigger.Type.AFTER, PersistenceType.INSERT), "onAfterInsert");
				put(new ImmutablePair<>(Trigger.Type.AFTER, PersistenceType.UPDATE), "onAfterUpdate");
				put(new ImmutablePair<>(Trigger.Type.AFTER, PersistenceType.DELETE), "onAfterDelete");
			}
		};
		
		public Trigger(String name) {
			
			super();
			
			this.name = name;
			
			this.id = Objects.hash(this.getClass().getName(), this.name);
			this.packageName = PACKAGE_NAME; 
		}
		
		public static Object call(Trigger.Type triggerType, PersistenceType persistenceType, Object instance) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
			String methodName = triggerMethod.get(new ImmutablePair<>(triggerType, persistenceType));
			
			if (!StringUtils.isEmpty(methodName) && instance != null) {
				
		        Method method = instance.getClass().getMethod(methodName);
		        
		        if (method != null) {
		        	
		        	return method.invoke(instance);
		        }
			}
			
			return null;
		}
	}
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class Command extends Basic {
		
		private static final long serialVersionUID = 7426288403894220149L;
		private String groovy;
		
		public Command(String name) {
			
			super();
			
			this.name = name;
			
			this.id = Objects.hash(this.getClass().getName(), this.name);
		}
	}
	
	@Data
	public static class Persist implements Serializable {

		private static final long serialVersionUID = -7083949386292171354L;
		
		@Data
		public static class PersistResult implements Serializable {
		
			private static final long serialVersionUID = 5812000347882814475L;
			
			private Row oldRow;
			private Row newRow;
			private String error;
			private String errorDescription;
		}
		
		@Data
		public static class PersistValue implements Serializable {
			
			private static final long serialVersionUID = 8519188939495926713L;
			
			private String colName;
			private String oldValue;
			private String newValue;
			private ColType colType;
		}
		
		private String tableName;
		private Integer id;
		private PersistenceType persistenceType;
		
		private List<PersistValue> persistValues;
	}
	
	@JsonIgnore
	private String labelSingular;
	@JsonProperty("labelSingular")
	private String labelSingularTranslated;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Column> columns = new ArrayList<>();
	
	private List<String> referTableNames = new ArrayList<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Row> rows = new ArrayList<>();
	
	private Map<String, Object> metadata;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Trigger> triggers = new ArrayList<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Command> commands = new ArrayList<>();
	
	public Table(String name) {
		
		super();
		
		this.name = name;
		this.id = Objects.hash(this.getClass().getName(), this.name);
	}
}
