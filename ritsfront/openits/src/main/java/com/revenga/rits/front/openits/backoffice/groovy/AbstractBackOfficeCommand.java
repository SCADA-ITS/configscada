package com.revenga.rits.front.openits.backoffice.groovy;

public abstract class AbstractBackOfficeCommand extends AbstractBackOfficeGroovy {

	protected AbstractBackOfficeCommand(org.apache.logging.log4j.Logger log) {

		super(log);
	}

	public abstract boolean onExecute();
}
