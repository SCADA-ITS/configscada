// Uncomment following line for groovy file
package config.groovy.backoffice.backoffice.trigger;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.trigger;

import java.sql.PreparedStatement;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;

public class CctCallsTrigger extends AbstractBackOfficeTrigger {

	public CctCallsTrigger(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onBeforeInsert() throws Exception {

		return true;
	}

	@Override
	public boolean onAfterInsert() throws Exception {

		fillCctCallValuesInDatabase();

		return true;
	}

	@Override
	public boolean onBeforeUpdate() throws Exception {

		return true;
	}

	@Override
	public boolean onAfterUpdate() throws Exception {

		fillCctCallValuesInDatabase();

		return true;
	}

	private void fillCctCallValuesInDatabase() throws Exception {

		Long callId = getCallId();

		if (callId == null) {
			return;
		}

		updateCctCallValues(callId);
	}

	private Long getCallId() throws Exception {

		if (this.id != null) {
			return this.id;
		}

		if (this.newRow != null) {

			String rowId = getFieldValue(this.newRow, "id");

			if (rowId != null && !rowId.trim().isEmpty()) {
				return Long.valueOf(rowId);
			}
		}

		if (this.oldRow != null) {

			String rowId = getFieldValue(this.oldRow, "id");

			if (rowId != null && !rowId.trim().isEmpty()) {
				return Long.valueOf(rowId);
			}
		}

		return null;
	}

	private void updateCctCallValues(Long callId) throws Exception {

		final String qry = String.format(
			"UPDATE %s.cct_calls c " +
			"   SET call_number = COALESCE( " +
			"           c.call_number, " +
			"           ( " +
			"               SELECT COALESCE(MAX(c2.call_number), 0) + 1 " +
			"                 FROM %s.cct_calls c2 " +
			"                WHERE c2.id <> c.id " +
			"           ) " +
			"       ), " +
			"       call_datetime = COALESCE( " +
			"           c.call_datetime, " +
			"           CURRENT_TIMESTAMP " +
			"       ), " +
			"       operator = COALESCE( " +
			"           NULLIF(TRIM(c.operator), ''), " +
			"           ( " +
			"               SELECT au.user_name " +
			"                 FROM hist.audit_users au " +
			"                WHERE au.user_name IS NOT NULL " +
			"                  AND TRIM(au.user_name) <> '' " +
			"                ORDER BY au.time_stamp DESC " +
			"                LIMIT 1 " +
			"           ) " +
			"       ) " +
			" WHERE c.id = ?",
			this.backOffice.getSchema(),
			this.backOffice.getSchema()
		);

		PreparedStatement stmt = null;

		try {

			stmt = this.conn.prepareStatement(qry);
			stmt.setLong(1, callId);
			stmt.executeUpdate();
		}
		finally {

			if (stmt != null) {
				stmt.close();
			}
		}
	}
}