package com.revenga.rits.front.openits.model.backoffice;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class Table extends Basic {
	
	private static final long serialVersionUID = 6887327590735622987L;
	
	public enum RequestType { INSERT, UPDATE, DELETE, FILLER, COMMAND }
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class Trigger extends GroovyBasic {
		
		private static final long serialVersionUID = 7426288403894220149L;
		
		private static final String PACKAGE_NAME = "com.revenga.rits.front.openits.backoffice.groovy.trigger";
		
		public enum Type { BEFORE, AFTER }
		
		private static final Map<Pair<Trigger.Type, RequestType>, String> triggerMethod = new HashMap<>() {

			private static final long serialVersionUID = 1L;

			{
				put(new ImmutablePair<>(Trigger.Type.BEFORE, RequestType.INSERT), "onBeforeInsert");
				put(new ImmutablePair<>(Trigger.Type.BEFORE, RequestType.UPDATE), "onBeforeUpdate");
				put(new ImmutablePair<>(Trigger.Type.BEFORE, RequestType.DELETE), "onBeforeDelete");
				put(new ImmutablePair<>(Trigger.Type.AFTER, RequestType.INSERT), "onAfterInsert");
				put(new ImmutablePair<>(Trigger.Type.AFTER, RequestType.UPDATE), "onAfterUpdate");
				put(new ImmutablePair<>(Trigger.Type.AFTER, RequestType.DELETE), "onAfterDelete");
			}
		};
		
		public Trigger(String name) {
			
			super();
			
			this.name = name;
			
			this.id = Objects.hash(this.getClass().getName(), this.name);
			this.packageName = PACKAGE_NAME; 
		}
		
		public static Object call(Trigger.Type triggerType, RequestType requestType, Object instance) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
			String methodName = triggerMethod.get(new ImmutablePair<>(triggerType, requestType));
			
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
	public static class Command extends GroovyBasic {
		
		private static final long serialVersionUID = 7426288403894220149L;
		
		private static final String PACKAGE_NAME = "com.revenga.rits.front.openits.backoffice.groovy.command";
		
		private Boolean requireConfirmation;
		
		public Command(String name) {
			
			super();
			
			this.name = name;
			
			this.id = Objects.hash(this.getClass().getName(), this.name);
			this.packageName = PACKAGE_NAME;
		}
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
