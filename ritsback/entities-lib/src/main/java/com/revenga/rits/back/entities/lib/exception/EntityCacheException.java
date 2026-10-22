package com.revenga.rits.back.entities.lib.exception;

import com.revenga.rits.back.data.core.model.BasicElement;

public class EntityCacheException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7094391427968485904L;
	
	public enum Operation { PUT, DEL, EXPIRE }

	public EntityCacheException(Operation operation, BasicElement basicElement) {
		
		super("Exception in cache operation " + operation.name() + ": " + basicElement.toString());
	}
}
