package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines type of element
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class IOControllerModuleType extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6204570493885780108L;

	public static final long MANGO = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "io.controller.module.type.mango"));
	public static final long CITILOG = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "io.controller.module.type.citilog"));
	public static final long RTMS = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "io.controller.module.type.rtms"));
		
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=moduleTypeId"})
	private List<IOControllerModuleTypeElementType> moduleTypeElementTypes;
}
