package com.revenga.rits.front.openits.backoffice.groovy;

import java.sql.Connection;

import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Request.RequestResult.RequestResultMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractBackOfficeFiller extends AbstractBackOfficeGroovy {

	protected AbstractBackOfficeFiller(org.apache.logging.log4j.Logger log) {

		super(log);
	}
	
	protected AbstractBackOfficeFiller(Connection conn, String schema, Table table, Integer id, Row oldRow, Row newRow, RequestResultMessage requestResultMessage, org.apache.logging.log4j.Logger log) {
	
		super(conn, schema, table, id, oldRow, newRow, requestResultMessage, log);
	}

	public abstract boolean onChange();
}
