package com.revenga.rits.front.openits.backoffice.groovy;

import java.util.Map;

import com.revenga.rits.front.openitsdao.model.BackOffice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractBackOfficeTask extends AbstractBackOfficeGroovy {

	protected BackOffice backOffice;
	protected Map<String, Object> params;
	protected String baseDir;
	
	
	protected AbstractBackOfficeTask(org.apache.logging.log4j.Logger log) {

		super(log);
	}
	
	public abstract boolean onExecute();
}
