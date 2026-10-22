package com.revenga.rits.back.virtual.equipment.manager.tunnel.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.model.BasicElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@PrimaryKeys(fields = { "id" })
public class SmConfig extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2556343334183835970L;
	
	private Long mode;
	private Long stateMachineId;
	private Boolean changeCurrentRegime;
	private List<SmConfigsRegime> smConfigsRegimes;
}
