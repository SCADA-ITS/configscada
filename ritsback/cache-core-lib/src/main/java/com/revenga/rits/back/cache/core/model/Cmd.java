package com.revenga.rits.back.cache.core.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Data
@EqualsAndHashCode(callSuper = false)
@Log4j2
public class Cmd extends GenericKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2603171208242246031L;

	public static final String CMD_PREFIX = "CMD:";
	
	private Long timestamp;
	private Long timestampFilter;
	private String uuid;
	private Map<Integer, String> ids = new HashMap<>();

	public Cmd(Command command) {
		
		super();
		this.clazz = command.getClass();
		this.ids = getIds(command);
	}

	public Cmd(String value) {
		
		super();
		set(value);
	}
	
	public void set(String value) {

		String valueAux = value;
		
		try {
			
			if (!StringUtils.isEmpty(valueAux)) {
						
				String[] splitItems = valueAux.replaceFirst(CMD_PREFIX, "").split(":");
				
				if (splitItems.length > 0) {
					
					this.clazz = ClassesUtil.getModelClassBySimpleClassName(splitItems[0]);
				}
				
				if (splitItems.length > 1) {
					
					this.timestamp = Long.valueOf(splitItems[1]);
				}
				
				if (splitItems.length > 2) {
					
					this.uuid = splitItems[2];
				}
				
				if (splitItems.length > 3) {
					
					int idPos = 1;
					
					for (int i = 3; i < splitItems.length; i++) {
						
						ids.put(idPos++, splitItems[i]);
					}
				}
			}
			
		} catch (ClassNotFoundException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public String getValue() {
		
		StringBuilder sbValue = new StringBuilder();
				
		sbValue.append(CMD_PREFIX);
		sbValue.append(clazz.getSimpleName());
		sbValue.append(":");

		if (timestampFilter == null) {
		
			sbValue.append(this.timestamp != null ? String.valueOf(this.timestamp): "*");
		}
		else {
			
			sbValue.append("*" + timestampFilter);
		}
		
		sbValue.append(":");
		sbValue.append(this.uuid != null ? this.uuid : "*");
		
		if (!MapUtils.isEmpty(ids)) {
			
			sbValue.append(":");
			sbValue.append(String.join(":", ids.values()));
		}
			
		return sbValue.toString();
	}
	
	public String getValueForPut() {
		
		StringBuilder sbValue = new StringBuilder();
		
		this.timestamp = Instant.now().toEpochMilli();
		this.uuid = UUID.randomUUID().toString();
		
		sbValue.append(CMD_PREFIX);
		sbValue.append(clazz.getSimpleName());
		sbValue.append(":");
		sbValue.append(String.valueOf(this.timestamp));
		sbValue.append(":");
		sbValue.append(this.uuid);
		
		if (!MapUtils.isEmpty(ids)) {
			
			sbValue.append(":");
			sbValue.append(String.join(":", ids.values()));
		}
			
		return sbValue.toString();
	}

	private Map<Integer, String> getIds(Command command) {
		
		Map<Integer, String> idsMap = new HashMap<>();
		PrimaryKeys pks = null;
		
		try {
			
			if (this.clazz == null) {
				
				throw new NullPointerException("this.clazz is null");
			}
			
			pks = this.clazz.getAnnotation(PrimaryKeys.class);
			
			if (pks != null) {
			
				for (int idPos = 1; idPos <= pks.fields().length; idPos++) {
					
					Field field = ClassesUtil.getField(this.clazz, pks.fields()[idPos - 1]);
					
					field.setAccessible(true);
					
					idsMap.put(idPos, "*");
					
					if (field.get(command) != null) {
					
						String id = field.get(command).toString();
						
						if (!StringUtils.isEmpty(id)) {
						
							idsMap.put(idPos, id);
						}
					}					
				}
			}	
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
		
		return idsMap;
	}
	
	/**
	 * 
	 * Checks if key contains patterns
	 * 
	 * @return True if key contains patterns. Otherwise returns False 
	 */
	public boolean hasFilterPatterns() {
		
		return this.getValue().contains("*");
	}
}

