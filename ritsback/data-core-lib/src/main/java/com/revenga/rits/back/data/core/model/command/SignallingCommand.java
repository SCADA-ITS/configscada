package com.revenga.rits.back.data.core.model.command;

import java.io.Serializable;
import java.util.List;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"elementTypeId", "signallingCommandId"})
public class SignallingCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7572932161403887950L;
	
	Long elementTypeId;
	Long signallingCommandId;
	
	Long elementId;
	
	@Data
	public static class SignallingParam implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3348417699513832396L;
		
		int id;
		int type;
		String value;
	}	
	
	@EqualsAndHashCode.Exclude
	private List<SignallingParam> signallingParams = null;
	
	public SignallingCommand() {

		super(Command.SIGNALLING_COMMAND);
	}
}
