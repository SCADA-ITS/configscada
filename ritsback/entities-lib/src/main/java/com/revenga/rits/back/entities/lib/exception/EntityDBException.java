package com.revenga.rits.back.entities.lib.exception;

import com.revenga.rits.back.data.core.model.BasicElement;

public class EntityDBException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7094391427968485904L;
	
	public enum Operation { INSERT, UPDATE, DELETE }

	public EntityDBException(Operation operation, BasicElement basicElement) {
		
		super("Exception in db operation " + operation.name() + ": " + basicElement.toString());
	}
}
