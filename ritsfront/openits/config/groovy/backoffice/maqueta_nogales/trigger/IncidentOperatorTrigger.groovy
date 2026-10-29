// Uncomment following line for groovy file
package config.groovy.backoffice.backoffice.trigger;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.trigger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;

public class IncidentOperatorTrigger extends AbstractBackOfficeTrigger {

	public IncidentOperatorTrigger(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onBeforeInsert() throws Exception {

		fillOperatorInRow();

		return true;
	}

	@Override
	public boolean onAfterInsert() throws Exception {

		fillOperatorInDatabase();

		return true;
	}

	@Override
	public boolean onBeforeUpdate() throws Exception {

		fillOperatorInRow();

		return true;
	}

	@Override
	public boolean onAfterUpdate() throws Exception {

		fillOperatorInDatabase();

		return true;
	}

	private void fillOperatorInRow() throws Exception {

		if (this.newRow == null) {
			return;
		}

		String currentOperator = getFieldValue(this.newRow, "operator");

		if (currentOperator != null && !currentOperator.trim().isEmpty()) {
			return;
		}

		String operator = getLastConnectedUser();

		if (operator != null && !operator.trim().isEmpty()) {
			setFieldValue(this.newRow, "operator", operator);
		}
	}

	private void fillOperatorInDatabase() throws Exception {

		Long incidentId = getIncidentId();

		if (incidentId == null) {
			return;
		}

		String currentOperator = getCurrentIncidentOperator(incidentId);

		if (currentOperator != null && !currentOperator.trim().isEmpty()) {
			return;
		}

		String operator = getLastConnectedUser();

		if (operator == null || operator.trim().isEmpty()) {
			return;
		}

		updateIncidentOperator(incidentId, operator);
	}

	private Long getIncidentId() throws Exception {

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

	private String getLastConnectedUser() throws Exception {

		final String qry =
			"SELECT user_name " +
			"  FROM hist.audit_users " +
			" WHERE user_name IS NOT NULL " +
			" ORDER BY time_stamp DESC " +
			" LIMIT 1";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			stmt = this.conn.prepareStatement(qry);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getString("user_name");
			}

			return null;
		}
		finally {

			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}
		}
	}

	private String getCurrentIncidentOperator(Long incidentId) throws Exception {

		final String qry = String.format(
			"SELECT operator " +
			"  FROM %s.incidents " +
			" WHERE id = ?",
			this.backOffice.getSchema()
		);

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			stmt = this.conn.prepareStatement(qry);
			stmt.setLong(1, incidentId);

			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getString("operator");
			}

			return null;
		}
		finally {

			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}
		}
	}

	private void updateIncidentOperator(Long incidentId, String operator) throws Exception {

		final String qry = String.format(
			"UPDATE %s.incidents " +
			"   SET operator = ? " +
			" WHERE id = ?",
			this.backOffice.getSchema()
		);

		PreparedStatement stmt = null;

		try {

			stmt = this.conn.prepareStatement(qry);
			stmt.setString(1, operator);
			stmt.setLong(2, incidentId);
			stmt.executeUpdate();
		}
		finally {

			if (stmt != null) {
				stmt.close();
			}
		}
	}
}