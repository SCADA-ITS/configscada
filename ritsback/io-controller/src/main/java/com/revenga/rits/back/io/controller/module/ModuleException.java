package com.revenga.rits.back.io.controller.module;

public class ModuleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4615905942879651627L;

	public ModuleException(GenericModule genericModule, String message) {
		
		super("Exception in " + genericModule.getName() + ": " + message);
	}
}
