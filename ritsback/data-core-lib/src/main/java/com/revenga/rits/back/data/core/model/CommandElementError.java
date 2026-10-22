package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommandElementError extends BasicElement {

	public static final long ELEMENT_NOT_CONNECTED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "command.error.element_not_connected"));

	public static final long NOT_COMPLY_PRECONDITIONS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "command.error.not_comply_preconditions"));

	public static final long TIME_OUT_EXPIRED = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "command.error.timeout_expired"));

	public static final long GROOVY_NOT_FOUND = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "command.error.groovy_not_found"));

	public static final long COMMAND_NOT_FOUND = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "command.error.command_not_found"));

	public static final long ELEMENT_NOT_FOUND = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "command.error.element_not_found"));

	public static final long INCIDENT_REPORT_NOT_FOUND = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "command.error.incident_report_not_found"));

	public static final long INCIDENT_TYPE_TASK_NOT_FOUND = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "command.error.incident_type_task_not_found"));

	/**
	 * 
	 */
	private static final long serialVersionUID = 4168196699894936108L;

	/**
	 * 
	 */
	private Long error;
	private Long elementTypeId;
	private Long elementId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<CommandElementPrecondition> commandElementPreconditions;

	public String getToStr() {

		StringBuilder stringBuilder = new StringBuilder();

		if (Long.valueOf(ELEMENT_NOT_CONNECTED).equals(error)) {

			stringBuilder.append("LBL_ELEMENT_NOT_CONNECTED");

		} else if (Long.valueOf(NOT_COMPLY_PRECONDITIONS).equals(error)) {

			stringBuilder.append("LBL_NOT_COMPLY_PRECONDITIONS => ");

			if (commandElementPreconditions != null) {

				commandElementPreconditions.forEach(
						commandElementPrecondition -> stringBuilder.append(commandElementPrecondition.getToStr()));
			}

		} else if (Long.valueOf(TIME_OUT_EXPIRED).equals(error)) {

			stringBuilder.append("LBL_TIME_OUT_EXPIRED");

		} else if (Long.valueOf(GROOVY_NOT_FOUND).equals(error)) {

			stringBuilder.append("LBL_GROOVY_NOT_FOUND");

		} else if (Long.valueOf(COMMAND_NOT_FOUND).equals(error)) {

			stringBuilder.append("LBL_COMMAND_NOT_FOUND");

		} else if (Long.valueOf(ELEMENT_NOT_FOUND).equals(error)) {

			stringBuilder.append("LBL_ELEMENT_NOT_FOUND");
			
		} else if (Long.valueOf(INCIDENT_REPORT_NOT_FOUND).equals(error)) {

			stringBuilder.append("LBL_INCIDENT_NOT_FOUND");

		} else if (Long.valueOf(INCIDENT_TYPE_TASK_NOT_FOUND).equals(error)) {

			stringBuilder.append("LBL_INCIDENT_TYPE_TASK_NOT_FOUND");
		}

		return stringBuilder.toString();
	}
}
