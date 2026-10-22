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
 * Defines log type
 * 
 * @author agomez
 */

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class LogType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;

	public static final long LOG_TYPE_IMS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.ims"));

	public static final long LOG_TYPE_PLANS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.plans"));

	public static final long LOG_TYPE_COMMANDS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.commands"));
	
	public static final long LOG_TYPE_PROCESSES = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.processes"));

	private Long levelOutputDb;
	private Long levelOutputStomp;
	private Long levelTreatment;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "id=logTypeId" })
	private List<LogTypeParam> logTypeParams;

}
