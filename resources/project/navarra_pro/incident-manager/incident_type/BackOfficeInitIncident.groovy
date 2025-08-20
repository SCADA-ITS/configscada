package com.revenga.rits.back.incident.manager.service;

import com.revenga.rits.back.data.core.model.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

class BackOfficeInitIncident {

    org.apache.logging.log4j.Logger log
    private static final String CONNECTION_URL = "jdbc:postgresql://10.238.4.128:5432/controltrafico";
    private static final String DB_USER = "dbo_controltrafico";
    private static final String DB_PASSWORD = "12hJklfj9.pha9";
    private static final String DB_SCHEMA = "incidents";

	 BackOfficeInitIncident(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

    boolean process(ImsIncidentReport incidentReport) {

        try {

	    Connection connection = DriverManager.getConnection(CONNECTION_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);
                
	    PreparedStatement ps = connection.prepareStatement(createInsertStatement(incidentReport));
	    ps.executeUpdate();

	    connection.commit();

            if (incidentReport.delayedIncidentReportId != null) {

		Long incidentIdFromDB = null;

		PreparedStatement psId = connection.prepareStatement(readIncidentId(incidentReport.getId()));
		ResultSet rs = psId.executeQuery();

		if (rs.next()) {
    			incidentIdFromDB = rs.getLong(1); 
		}

		rs.close();
		psId.close();

                PreparedStatement psMunicipios = connection.prepareStatement(
                        buildCopyMunicipalitiesStatement(incidentReport.delayedIncidentReportId, incidentIdFromDB));
                psMunicipios.executeUpdate();

		PreparedStatement psEntidades = connection.prepareStatement(
                        buildCopyEntitiesStatement(incidentReport.delayedIncidentReportId, incidentIdFromDB));
                psEntidades.executeUpdate();
            }

            connection.commit();
            log.info("Groovy executed after Incident Report Confirmed.");
            return true;

        } catch (SQLException e) {
            log.error("Error during incident processing: " + e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    private String buildCopyMunicipalitiesStatement(Long delayedIncidentReportId, Long incidentId) {
        return String.format(
                "INSERT INTO %s.assigned_municipalities (incident_id, municipality_id) " +
                "SELECT %d, municipality_id FROM %s.delayed_assigned_municipalities WHERE delayed_incident_id = %d",
                DB_SCHEMA, incidentId, DB_SCHEMA, delayedIncidentReportId);
    }

    private String buildCopyEntitiesStatement(Long delayedIncidentReportId, Long incidentId) {
        return String.format(
                "INSERT INTO %s.assigned_entities (incident_id, entity_id) " +
                "SELECT %d, entity_id FROM %s.delayed_assigned_entities WHERE delayed_incident_id = %d",
                DB_SCHEMA, incidentId, DB_SCHEMA, delayedIncidentReportId);
    }

    private String readIncidentId(Long incidentReportId) {
        return String.format(
                "SELECT id FROM %s.incidents WHERE incident_report_id = '%d'",
                DB_SCHEMA, incidentReportId);
    }

    private String createInsertStatement(ImsIncidentReport incidentReport) {
        String incidentReportId = null;
        String detectionType;
        String incidentType = null;
        String incidentSubType = null;
        String level = null;
        String location = null;
        String userName = null;
        String finishAt = null;

        if (incidentReport != null && incidentReport.getId() != null) {
            incidentReportId = String.valueOf(incidentReport.getId());

            List<ImsIncidentReportAlarm> incidentAlarms =
                    IncidentEntitiesManager.getInstance().getIncidentAlarmsByIncidentReport(incidentReport.getId());
            detectionType = incidentAlarms.isEmpty() ? "LBL_MANUAL" : "LBL_AUTO";

            if (incidentReport.getIncidentTypeId() != null) {
                ImsIncidentType type = IncidentEntitiesManager.getInstance().getIncidentType(incidentReport.getIncidentTypeId());
                if (type != null) {
                    incidentSubType = type.getDescription();
                    if (type.getIncidentGroupId() != null) {
                        ImsIncidentGroup group = IncidentEntitiesManager.getInstance().getIncidentGroup(type.getIncidentGroupId());
                        if (group != null) {
                            incidentType = group.getAlias();
                        }
                    }
                }
            }

            if (incidentReport.getIncidentLevelId() != null) {
                ImsIncidentLevel lvl = IncidentEntitiesManager.getInstance().getIncidentLevel(incidentReport.getIncidentLevelId());
                if (lvl != null) {
                    level = lvl.getLabelAlias();
                }
            }

            if (incidentReport.getAffectionStretchId() != null && incidentReport.getLocationId() != null) {
                Stretch stretch = IncidentEntitiesManager.getInstance().getStretch(incidentReport.getAffectionStretchId());
                Location loc = IncidentEntitiesManager.getInstance().getLocation(incidentReport.getLocationId());
                if (stretch != null && loc != null) {
                    location = stretch.getAlias() + " - " + loc.getAlias();
                }
            }

            if (incidentReport.getCurrentUserId() != null) {
                User user = EntitiesManager.getInstance().getUser(incidentReport.getCurrentUserId());
                if (user != null) {
                    userName = user.getFullName();
                }
            }
            
            if (incidentReport.getAutoCloseInMin() != null) {
				Long finishTimestamp = incidentReport.getGeneratedAt() + incidentReport.getAutoCloseInMin()*60000;
				
				finishAt = Instant.ofEpochMilli(finishTimestamp)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}
        }

        List<ColumnValuePair> values = List.of(
                new ColumnValuePair("incident_report_id", incidentReportId),
                new ColumnValuePair("incident_type", incidentType),
                new ColumnValuePair("incident_sub_type", incidentSubType),
                new ColumnValuePair("level", level),
                new ColumnValuePair("location", location),
                new ColumnValuePair("finish_at", finishAt)
        );

        StringBuilder columns = new StringBuilder();
        StringBuilder valStr = new StringBuilder();

        for (ColumnValuePair pair : values) {
            if (pair.value != null) {
                columns.append(pair.column).append(", ");
                valStr.append("'").append(pair.value).append("', ");
            }
        }

        // Eliminar la coma final
        if (columns.length() > 0) columns.setLength(columns.length() - 2);
        if (valStr.length() > 0) valStr.setLength(valStr.length() - 2);

        return String.format("INSERT INTO %s.incidents (%s) VALUES (%s);", DB_SCHEMA, columns, valStr);
    }

    private static class ColumnValuePair {
        String column;
        String value;

        ColumnValuePair(String column, String value) {
            this.column = column;
            this.value = value;
        }
    }
}
