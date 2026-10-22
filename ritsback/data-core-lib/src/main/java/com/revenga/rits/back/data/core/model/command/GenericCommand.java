package com.revenga.rits.back.data.core.model.command;

import java.io.Serializable;
import java.util.List;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"type", "subType"})
public class GenericCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3999005669540548561L;
	
	public static final int TYPE_IO_CONTROLLER = 1;
	
	public static final int SUBTYPE_REQUEST_STATE = 1;
	public static final int SUBTYPE_REQUEST_MEASURES = 2;
	public static final int SUBTYPE_REQUEST_ALARMS = 3;
	public static final int SUBTYPE_SET_MEASURES = 4;
	
	private Integer type;
	private Integer subType;
	
	private List<DataStruct> dataStructs = null;
	
	public GenericCommand() {

		super(Command.GENERIC_COMMAND);
	}
	
	@Data
	public static class DataStruct implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3228237030858485931L;
	
		private Long id;
		private Long elementId;
		private Long elementType;
		private List<Param> params = null;
		private List<ConfigParam> configParams = null;
		
		@Data
		public static class Param implements Serializable {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7838424926224102230L;
			
			public static final int TYPE_BOOLEAN = 1;
			public static final int TYPE_STRING = 2;
			public static final int TYPE_LONG = 3;
			public static final int TYPE_FLOAT = 4;

			Long id;
			Long type;
			String value;
		}
		
		@Data
		public static class ConfigParam implements Serializable {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7594280963200063999L;
			
			private Long id;
			private Long elementId;
			private Long elementType;
			private List<Param> params = null;
		}
	}
}
