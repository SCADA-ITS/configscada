// Uncomment following line for groovy file
package config.groovy.backoffice.backoffice.trigger;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.trigger;

import java.sql.PreparedStatement;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;

public class IncidentDetectionTimeTrigger extends AbstractBackOfficeTrigger {

	public IncidentDetectionTimeTrigger(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onBeforeInsert() throws Exception {

		fillDetectionTimeInRow();

		return true;
	}

	@Override
	public boolean onAfterInsert() throws Exception {

		fillDetectionTimeInDatabase();

		return true;
	}

	@Override
	public boolean onBeforeUpdate() throws Exception {

		fillDetectionTimeInRow();

		return true;
	}

	@Override
	public boolean onAfterUpdate() throws Exception {

		fillDetectionTimeInDatabase();

		return true;
	}

	private void fillDetectionTimeInRow() throws Exception {

		if (this.newRow == null) {
			return;
		}

		String currentDetectionTime = getFieldValue(this.newRow, "detection_time");

		if (currentDetectionTime != null && !currentDetectionTime.trim().isEmpty()) {
			return;
		}

		setFieldValue(this.newRow, "detection_time", "now()");
	}

	private void fillDetectionTimeInDatabase() throws Exception {

		Long incidentId = getIncidentId();

		if (incidentId == null) {
			return;
		}

		updateDetectionTime(incidentId);
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

	private void updateDetectionTime(Long incidentId) throws Exception {

		final String qry = String.format(
			"UPDATE %s.incidents " +
			"   SET detection_time = now() " +
			" WHERE id = ? " +
			"   AND detection_time IS NULL",
			this.backOffice.getSchema()
		);

		PreparedStatement stmt = null;

		try {

			stmt = this.conn.prepareStatement(qry);
			stmt.setLong(1, incidentId);
			stmt.executeUpdate();
		}
		finally {

			if (stmt != null) {
				stmt.close();
			}
		}
	}
}