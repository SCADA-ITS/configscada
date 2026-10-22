package com.revenga.rits.back.data.core.model.command.virtual;

import com.revenga.rits.back.data.core.model.command.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetDaliIlluminationRegimeCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7572932161403887950L;

	Long elementTypeId;
	Long elementId;

	Long regime;

	public SetDaliIlluminationRegimeCommand() {

		super(Command.SET_DALI_ILLUMINATION_REGIME);
	}
}
