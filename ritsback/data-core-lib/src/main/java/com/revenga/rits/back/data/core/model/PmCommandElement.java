package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.annotation.TimeStampField;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeys(fields = { "queueId", "commandElementTypeId", "id", "elementTypeId", "elementId" })
public class PmCommandElement extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4168196699894936108L;

	/**
	 * 
	 */
	private Long commandElementTypeId;

	private Long elementTypeId;
	private Long elementId;

	private Long queueId;
	private Long priorityId;

	@TimeStampField
	private Long startTime;

	@TimeStampField
	private Long endTime;
	
	private Boolean enable;
	
	private String source;
	
	private Boolean conflict;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List <PmCommandElementValue> pmCommandElementValues;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "commandElementTypeId=id"})
	private CommandElementType commandElementType;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}
