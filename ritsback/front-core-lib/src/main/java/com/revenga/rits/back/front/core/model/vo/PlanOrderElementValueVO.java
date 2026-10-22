package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanOrderElementValueVO extends BasicElementVO {

	private static final long serialVersionUID = 5324230581637479464L;

	protected String value;
	protected String elementId;
	protected String elementTypeParamId;

	@EqualsAndHashCode.Exclude
	private ElementVO element;

	@EqualsAndHashCode.Exclude
	private ElementTypeParamVO elementTypeParam;
}
