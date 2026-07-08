// Uncomment following line for groovy file
package config.groovy.backoffice.backoffice.trigger;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.trigger;

import java.sql.PreparedStatement;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;

public class SupportServiceResponseTimeTrigger extends AbstractBackOfficeTrigger {

	public SupportServiceResponseTimeTrigger(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onBeforeInsert() throws Exception {

		return true;
	}

	@Override
	public boolean onAfterInsert() throws Exception {

		fillResponseTimeInDatabase();

		return true;
	}

	@Override
	public boolean onBeforeUpdate() throws Exception {

		return true;
	}

	@Override
	public boolean onAfterUpdate() throws Exception {

		fillResponseTimeInDatabase();

		return true;
	}

	private void fillResponseTimeInDatabase() throws Exception {

		Long supportServiceId = getSupportServiceId();

		if (supportServiceId == null) {
			return;
		}

		updateResponseTime(supportServiceId);
	}

	private Long getSupportServiceId() throws Exception {

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

	private void updateResponseTime(Long supportServiceId) throws Exception {

		final String qry = String.format(
			"UPDATE %s.support_services support_service " +
			"   SET response_time = calculated.response_time " +
			"  FROM ( " +
			"       SELECT " +
			"           service.id AS support_service_id, " +
			"           CASE " +
			"               WHEN service.arrival_time IS NULL " +
			"                   OR incident.detection_time IS NULL " +
			"                   OR service.arrival_time < incident.detection_time " +
			"               THEN NULL " +

			"               WHEN duration.total_minutes >= 1440 THEN " +
			"                   (duration.total_minutes / 1440)::text || " +
			"                   CASE " +
			"                       WHEN (duration.total_minutes / 1440) = 1 " +
			"                       THEN ' día' " +
			"                       ELSE ' días' " +
			"                   END || " +
			"                   CASE " +
			"                       WHEN ((duration.total_minutes %% 1440) / 60) > 0 " +
			"                            AND (duration.total_minutes %% 60) > 0 " +
			"                       THEN ', ' || " +
			"                           ((duration.total_minutes %% 1440) / 60)::text || " +
			"                           CASE " +
			"                               WHEN ((duration.total_minutes %% 1440) / 60) = 1 " +
			"                               THEN ' hora y ' " +
			"                               ELSE ' horas y ' " +
			"                           END || " +
			"                           (duration.total_minutes %% 60)::text || " +
			"                           CASE " +
			"                               WHEN (duration.total_minutes %% 60) = 1 " +
			"                               THEN ' minuto' " +
			"                               ELSE ' minutos' " +
			"                           END " +

			"                       WHEN ((duration.total_minutes %% 1440) / 60) > 0 " +
			"                       THEN ' y ' || " +
			"                           ((duration.total_minutes %% 1440) / 60)::text || " +
			"                           CASE " +
			"                               WHEN ((duration.total_minutes %% 1440) / 60) = 1 " +
			"                               THEN ' hora' " +
			"                               ELSE ' horas' " +
			"                           END " +

			"                       WHEN (duration.total_minutes %% 60) > 0 " +
			"                       THEN ' y ' || " +
			"                           (duration.total_minutes %% 60)::text || " +
			"                           CASE " +
			"                               WHEN (duration.total_minutes %% 60) = 1 " +
			"                               THEN ' minuto' " +
			"                               ELSE ' minutos' " +
			"                           END " +

			"                       ELSE '' " +
			"                   END " +

			"               WHEN duration.total_minutes >= 60 THEN " +
			"                   (duration.total_minutes / 60)::text || " +
			"                   CASE " +
			"                       WHEN (duration.total_minutes / 60) = 1 " +
			"                       THEN ' hora' " +
			"                       ELSE ' horas' " +
			"                   END || " +
			"                   CASE " +
			"                       WHEN (duration.total_minutes %% 60) > 0 " +
			"                       THEN ' y ' || " +
			"                           (duration.total_minutes %% 60)::text || " +
			"                           CASE " +
			"                               WHEN (duration.total_minutes %% 60) = 1 " +
			"                               THEN ' minuto' " +
			"                               ELSE ' minutos' " +
			"                           END " +
			"                       ELSE '' " +
			"                   END " +

			"               ELSE " +
			"                   duration.total_minutes::text || " +
			"                   CASE " +
			"                       WHEN duration.total_minutes = 1 " +
			"                       THEN ' minuto' " +
			"                       ELSE ' minutos' " +
			"                   END " +
			"           END AS response_time " +

			"       FROM %s.support_services service " +
			"       INNER JOIN %s.incidents incident " +
			"           ON incident.id = service.incident_id " +

			"       CROSS JOIN LATERAL ( " +
			"           SELECT FLOOR( " +
			"               EXTRACT( " +
			"                   EPOCH FROM ( " +
			"                       service.arrival_time - incident.detection_time " +
			"                   ) " +
			"               ) / 60 " +
			"           )::bigint AS total_minutes " +
			"       ) duration " +

			"       WHERE service.id = ? " +
			"   ) calculated " +

			" WHERE support_service.id = calculated.support_service_id",
			this.backOffice.getSchema(),
			this.backOffice.getSchema(),
			this.backOffice.getSchema()
		);

		PreparedStatement stmt = null;

		try {

			stmt = this.conn.prepareStatement(qry);
			stmt.setLong(1, supportServiceId);
			stmt.executeUpdate();
		}
		finally {

			if (stmt != null) {
				stmt.close();
			}
		}
	}
}