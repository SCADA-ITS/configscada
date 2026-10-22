package com.revenga.rits.back.io.controller.driver;

public class DriverException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1793555904177634613L;

	public DriverException(GenericDriver genericDriver, String message) {
		
		super("Exception in " + genericDriver.getClass().getSimpleName() + ": " + message);
	}
}
