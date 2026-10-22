package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "queueId", "commandElementTypeId", "pmCommandElementId", "elementTypeId", "elementId",
		"commandElementTypeParamId" })
public class PmCommandElementValue extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4168196699894936108L;

	/**
	 * 
	 */
	private Long queueId;
	private Long commandElementTypeId;
	private Long pmCommandElementId;

	private Long elementTypeId;
	private Long elementId;
	private Long commandElementTypeParamId;

	private String value;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"commandElementTypeId=commandElementTypeId", "elementTypeId=elementTypeId", "commandElementTypeParamId=id"})
	private CommandElementTypeParam commandElementTypeParam;
}
