package com.revenga.rits.back.data.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseToVirtualEquipmentManagerCommand extends ResponseCommand {

	private static final long serialVersionUID = -2036483902687417661L;

	public ResponseToVirtualEquipmentManagerCommand() {

		super();
	}

}
