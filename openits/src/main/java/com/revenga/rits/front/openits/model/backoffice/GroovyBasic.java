package com.revenga.rits.front.openits.model.backoffice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class GroovyBasic extends Basic {
	
	private static final long serialVersionUID = -8133168767393817311L;
	
	protected String groovy;
	protected String packageName;
}