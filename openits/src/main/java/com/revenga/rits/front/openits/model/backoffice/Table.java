package com.revenga.rits.front.openits.model.backoffice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.front.openits.util.DBUtil.ColType;
import com.revenga.rits.front.openits.util.DBUtil.PersistenceType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class Table extends Basic {
	
	private static final long serialVersionUID = 6887327590735622987L;
	
	@Data
	public static class TableMetadata implements Serializable {

		private static final long serialVersionUID = 5331438666910347282L;
		
		@Data
		public static class Tab implements Serializable {
			
			private static final long serialVersionUID = 4199740125852638602L;
			
			private String label;
			private List<Field> fields;
		}
		
		@Data
		public static class Field implements Serializable {
			
			private static final long serialVersionUID = -793822838493352111L;
			
			private String name;
		}
		
		@Data
		public static class Grid implements Serializable {
			
			private static final long serialVersionUID = -3673069280484549825L;
			
			private List<Field> fields;
		}
		
		@Data
		public static class Trigger implements Serializable {
			
			private static final long serialVersionUID = -3823233926074563767L;

			private PersistenceType operationType;
			
			private String groovy;
		}
		
		private String mdiIcon;
		private Integer width;
		private Integer height;
		private List<Tab> tabs;
		
		private List<Field> fields;
		
		private List<Trigger> triggers = new ArrayList<>();
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
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Column> columns = new ArrayList<>();
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Table> referTables = new ArrayList<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Row> rows = new ArrayList<>();
	
	private TableMetadata metadata;
	
	public Table(String name) {
		
		super(name);
	}
}
