package com.revenga.rits.front.openits.backoffice.groovy;

import java.sql.Connection;
import java.sql.Statement;

import com.revenga.rits.front.openits.backoffice.model.Row;
import com.revenga.rits.front.openits.backoffice.model.Table;
import com.revenga.rits.front.openits.backoffice.model.Request.RequestResult.RequestResultMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractBackOfficeTrigger extends AbstractBackOfficeGroovy {

	protected AbstractBackOfficeTrigger(org.apache.logging.log4j.Logger log) {

		super(log);
	}

	protected AbstractBackOfficeTrigger(Connection conn, String schema, Table table, Long id, Row oldRow, Row newRow,
			RequestResultMessage requestResultMessage, org.apache.logging.log4j.Logger log) {

		super(conn, schema, table, id, oldRow, newRow, requestResultMessage, log);
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
