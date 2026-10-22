package com.revenga.rits.front.openits.backoffice.groovy;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractBackOfficeFiller extends AbstractBackOfficeGroovy {

	protected AbstractBackOfficeFiller(org.apache.logging.log4j.Logger log) {

		super(log);
	}
	
	public abstract boolean onChange();
}
