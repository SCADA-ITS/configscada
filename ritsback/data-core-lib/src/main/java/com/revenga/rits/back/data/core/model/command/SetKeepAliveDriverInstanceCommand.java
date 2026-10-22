package com.revenga.rits.back.data.core.model.command;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetKeepAliveDriverInstanceCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7875563440734618639L;
	
	private Long driverId;
	private List<String> elementIds;
	private Long lastUpdate;
	
	public SetKeepAliveDriverInstanceCommand() {

		super(Command.SET_KEEP_ALIVE_DRIVER_INSTANCE);
	}
}
