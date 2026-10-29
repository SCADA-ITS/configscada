// Uncomment following line for groovy file
package config.groovy.backoffice.backoffice.trigger;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.trigger;

import java.sql.PreparedStatement;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;

public class ClosureTotalTimeTrigger extends AbstractBackOfficeTrigger {

	public ClosureTotalTimeTrigger(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onBeforeInsert() throws Exception {

		return true;
	}

	@Override
	public boolean onAfterInsert() throws Exception {

		fillTotalTimeInDatabase();

		return true;
	}

	@Override
	public boolean onBeforeUpdate() throws Exception {

		return true;
	}

	@Override
	public boolean onAfterUpdate() throws Exception {

		fillTotalTimeInDatabase();

		return true;
	}

	private void fillTotalTimeInDatabase() throws Exception {

		Long closureId = getClosureId();

		if (closureId == null) {
			return;
		}

		updateTotalTime(closureId);
	}

	private Long getClosureId() throws Exception {

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

	private void updateTotalTime(Long closureId) throws Exception {

		final String qry = String.format(
			"UPDATE %s.closures c " +
			"   SET total_time = CASE " +
			"       WHEN c.start_hour IS NULL OR c.end_hour IS NULL THEN NULL " +
			"       WHEN c.end_hour < c.start_hour THEN NULL " +
			"       ELSE ( " +
			"           SELECT CASE " +
			"               WHEN duration.days > 0 AND duration.hours > 0 AND duration.minutes > 0 THEN " +
			"                   duration.days || CASE WHEN duration.days = 1 THEN ' día, ' ELSE ' días, ' END || " +
			"                   duration.hours || CASE WHEN duration.hours = 1 THEN ' hora y ' ELSE ' horas y ' END || " +
			"                   duration.minutes || CASE WHEN duration.minutes = 1 THEN ' minuto' ELSE ' minutos' END " +

			"               WHEN duration.days > 0 AND duration.hours > 0 THEN " +
			"                   duration.days || CASE WHEN duration.days = 1 THEN ' día y ' ELSE ' días y ' END || " +
			"                   duration.hours || CASE WHEN duration.hours = 1 THEN ' hora' ELSE ' horas' END " +

			"               WHEN duration.days > 0 AND duration.minutes > 0 THEN " +
			"                   duration.days || CASE WHEN duration.days = 1 THEN ' día y ' ELSE ' días y ' END || " +
			"                   duration.minutes || CASE WHEN duration.minutes = 1 THEN ' minuto' ELSE ' minutos' END " +

			"               WHEN duration.days > 0 THEN " +
			"                   duration.days || CASE WHEN duration.days = 1 THEN ' día' ELSE ' días' END " +

			"               WHEN duration.hours > 0 AND duration.minutes > 0 THEN " +
			"                   duration.hours || CASE WHEN duration.hours = 1 THEN ' hora y ' ELSE ' horas y ' END || " +
			"                   duration.minutes || CASE WHEN duration.minutes = 1 THEN ' minuto' ELSE ' minutos' END " +

			"               WHEN duration.hours > 0 THEN " +
			"                   duration.hours || CASE WHEN duration.hours = 1 THEN ' hora' ELSE ' horas' END " +

			"               ELSE " +
			"                   duration.minutes || CASE WHEN duration.minutes = 1 THEN ' minuto' ELSE ' minutos' END " +
			"           END " +
			"           FROM ( " +
			"               SELECT " +
			"                   total_minutes / 1440 AS days, " +
			"                   (total_minutes %% 1440) / 60 AS hours, " +
			"                   total_minutes %% 60 AS minutes " +
			"               FROM ( " +
			"                   SELECT FLOOR(EXTRACT(EPOCH FROM (c.end_hour - c.start_hour)) / 60)::bigint AS total_minutes " +
			"               ) calculated_minutes " +
			"           ) duration " +
			"       ) " +
			"   END " +
			" WHERE c.id = ?",
			this.backOffice.getSchema()
		);

		PreparedStatement stmt = null;

		try {

			stmt = this.conn.prepareStatement(qry);
			stmt.setLong(1, closureId);
			stmt.executeUpdate();
		}
		finally {

			if (stmt != null) {
				stmt.close();
			}
		}
	}
}