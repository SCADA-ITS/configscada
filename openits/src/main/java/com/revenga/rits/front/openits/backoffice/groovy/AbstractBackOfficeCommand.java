package com.revenga.rits.front.openits.backoffice.groovy;

import java.sql.Connection;

import com.revenga.rits.front.openits.backoffice.model.Row;
import com.revenga.rits.front.openits.backoffice.model.Table;
import com.revenga.rits.front.openits.backoffice.model.Request.RequestResult.RequestResultMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractBackOfficeCommand extends AbstractBackOfficeGroovy {

	protected AbstractBackOfficeCommand(org.apache.logging.log4j.Logger log) {

		super(log);
	}

	protected AbstractBackOfficeCommand(Connection conn, String schema, Table table, Long id, Row oldRow, Row newRow,
			RequestResultMessage requestResultMessage, org.apache.logging.log4j.Logger log) {

		super(conn, schema, table, id, oldRow, newRow, requestResultMessage, log);
	}

	public abstract boolean onExecute();
}
