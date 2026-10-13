package com.revenga.rits.back.virtual.equipment.manager.tunnel.model;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@PrimaryKeys(fields = { "id" })
public class RegimeConfig extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5111519945469291315L;

	private List<CommandElementVO> commands;
	private List<SignallingCommand> signallingCommands;

	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

	public void addSignallingCommand(SignallingCommand signallingCommand) {

		if (signallingCommand != null) {

			if (signallingCommands == null) {

				signallingCommands = new ArrayList<>();
			}

			signallingCommands.add(signallingCommand);
		}
	}
}
