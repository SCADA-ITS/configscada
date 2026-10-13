package com.revenga.rits.back.io.controller.module.rtms;

public class RtmsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1793555904177634613L;

	public RtmsException(String message) {
		
		super("Exception in RTMS: " + message);
	}
}
