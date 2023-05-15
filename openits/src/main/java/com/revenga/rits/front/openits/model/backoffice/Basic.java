package com.revenga.rits.front.openits.model.backoffice;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public abstract class Basic implements Serializable {

	private static final long serialVersionUID = 1905122041950251207L;

	protected Integer id;

	protected String name;
	protected String label;
	protected String labelDescription;
	
	protected Basic(String name) {
		
		super();
		
		this.name = name;
		this.id = Objects.hash(this.getClass().getName(), this.name);
	}
}
