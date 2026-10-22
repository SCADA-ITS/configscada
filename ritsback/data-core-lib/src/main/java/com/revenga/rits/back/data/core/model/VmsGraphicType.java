package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class VmsGraphicType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4151153782166280710L;

	// region VMS GRAPHIC TYPE constants

	public static final long GRAPHIC_TYPE_PICTOGRAM = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "vms.graphic.type.pictogram"));
	public static final long GRAPHIC_TYPE_CHARACTER = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "vms.graphic.type.character"));

	// endregion VMS GRAPHIC TYPE constants
}
