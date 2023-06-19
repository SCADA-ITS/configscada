package com.revenga.rits.front.openits.model.backoffice;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public abstract class Basic implements Serializable, Cloneable {

	private static final long serialVersionUID = 1905122041950251207L;

	protected Integer id;

	protected String name;
	@JsonIgnore
	protected String label;
	@JsonIgnore
	protected String labelDescription;
	@JsonProperty("label")
	protected String labelTranslated;
	@JsonProperty("labelDescription")
	protected String labelDescriptionTranslated;
	protected String mdiIcon;
	
	protected Basic() {
		
		super();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
}
