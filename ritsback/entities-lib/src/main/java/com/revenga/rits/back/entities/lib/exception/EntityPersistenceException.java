package com.revenga.rits.back.entities.lib.exception;

import com.revenga.rits.back.data.core.model.BasicElement;

public class EntityPersistenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7094391427968485904L;
	
	public enum Operation { CREATE, UPDATE, DELETE }

	public EntityPersistenceException(Operation operation, BasicElement basicElement) {
		
		super("Exception in persistence operation " + operation.name() + ": " + basicElement.toString());
	}
}
