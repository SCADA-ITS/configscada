package com.revenga.rits.front.openits.backoffice.groovy;

import java.sql.Statement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractBackOfficeTrigger extends AbstractBackOfficeGroovy {

	protected AbstractBackOfficeTrigger(org.apache.logging.log4j.Logger log) {

		super(log);
	}

	public boolean onBeforeInsert() throws Exception {

		return true;
	}

	public boolean onBeforeUpdate() throws Exception {

		return true;
	}

	public boolean onBeforeDelete() throws Exception {

		return true;
	}

	public boolean onAfterInsert() throws Exception {

		return true;
	}

	public boolean onAfterUpdate() throws Exception {

		return true;
	}

	public boolean onAfterDelete() throws Exception {

		return true;
	}

	public void traceSQL(Statement stmt) {

		log.debug(String.format("Executing DML on Trigger: %s", stmt.toString()));
	}
}
