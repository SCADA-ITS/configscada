package com.revenga.rits.back.io.controller.app;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import lombok.Getter;

public class ArgumentProcessor {
	
	private static ArgumentProcessor instance;
	
	public static final String ARG_XML_ADAPTER = "xml.adapter";
	
	@Getter
	private Map<String, String> args;
	
	public static ArgumentProcessor getInstance() {
		
		if (instance == null) {
			
			instance = new ArgumentProcessor();
		}
			
		return instance;
	}
	
	public String getArgumentValue(String argument) {
		
		String value = null;
		
		if (!MapUtils.isEmpty(args)) {
			
			value = args.get(argument);
		}
		
		return value;
	}
	
	public void setArgs(String[] args) {
		
		if (!ArrayUtils.isEmpty(args)) {
			
			this.args.clear();
			
			for (String arg : args) {
				
				String key = null; 
				String value = null;
				
				if (arg.contains("--")) {
					
					key = arg.substring(arg.indexOf("--") + 2);
					
					if (arg.contains("=")) {
						
						key = key.substring(0, key.indexOf('='));
						value = arg.substring(arg.indexOf('=') + 1);
					}
					
					this.args.put(key, value);
				}
			}
		}
	}

	public ArgumentProcessor() {
		
		args = new HashMap<>();
		
		
	}

}
