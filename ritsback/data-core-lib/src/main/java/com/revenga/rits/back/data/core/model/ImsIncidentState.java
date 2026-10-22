package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ImsIncidentState extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2449767741216252915L;

	public static final long IMS_STATE_CANCEL = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "ims.state.cancel"));
	
	private Boolean finalState;
}

