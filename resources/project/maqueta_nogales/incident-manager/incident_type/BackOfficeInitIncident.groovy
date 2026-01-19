package com.revenga.rits.back.incident.manager.service;

import com.revenga.rits.back.data.core.model.ImsIncidentGroup;
import com.revenga.rits.back.data.core.model.ImsIncidentLevel;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

class BackOfficeInitIncident {

	org.apache.logging.log4j.Logger log
	private static final String CONNECTION_URL = "jdbc:postgresql://192.168.88.191:5430/rits";
	private static final String DB_USER = "rits";
	private static final String DB_PASSWORD = "rits";
	private static final String DB_SCHEMA = "nogales_gip";
	private static final String DB_SCHEMA_CONF = "conf";
	private static final String ELEMENT_TYPE_SOS = "11";
	private static final String ELEMENT_ID_CAUSE_POSTE_SOS = "1";

	 BackOfficeInitIncident(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

	 boolean process(ImsIncidentReport incidentReport) {
        Connection connection = null;
        
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, DB_USER, DB_PASSWORD);

            String insertStatement = createInsertStatement(incidentReport);

            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.executeUpdate();
            

            log.info("Groovy executed after Incident Report Confirmed.");

            return true;

        } catch (SQLException e) {
            log.error(e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error closing connection: " + e.getMessage());
                }
            }
        }

        return false;
    }
    
    String obtainAliasElement(Long elementId){
    	Connection connection = DriverManager.getConnection(CONNECTION_URL, DB_USER, DB_PASSWORD);
    
    	PreparedStatement preparedStatement = connection.prepareStatement("SELECT alias from " + DB_SCHEMA_CONF + ".elements where element_type_id = " + ELEMENT_TYPE_SOS + " and element_id = " + String.valueOf(elementId));
		ResultSet rset = preparedStatement.executeQuery();
		String alias = "";
		
		while (rset.next()) {
			
			alias = rset.getString("alias");
			
			break;
		}
		
		preparedStatement = connection.prepareStatement("SELECT * from " + DB_SCHEMA + ".elements where alias = '" + alias + "'");
		rset = preparedStatement.executeQuery();
		
		while (rset.next()) {
			
			alias = rset.getString("id");
			
			break;
		}
		
		return alias;
    }

    String createInsertStatement(ImsIncidentReport incidentReport) {
		
        String incidentReportId = null;
        String detectionType = null;
        String detectionOrigin = null;
        String incidentType = null;
        String incidentSubType = null;
        String level = null;
        String userName = null;
        String alias = null;

        if (incidentReport != null) {
            if (incidentReport.getId() != null) {
                incidentReportId = String.valueOf(incidentReport.getId());
            }

            List<ImsIncidentReportAlarm> incidentAlarms = IncidentEntitiesManager.getInstance()
                    .getIncidentAlarmsByIncidentReport(incidentReport.getId());


            if (!incidentAlarms.isEmpty()) {
                detectionType = "LBL_AUTO";
                detectionOrigin = ELEMENT_ID_CAUSE_POSTE_SOS;
                alias = obtainAliasElement(incidentAlarms.get(0).getElementId());
            } else {
                detectionType = "LBL_MANUAL";
                	
            }

            if (incidentReport.getIncidentTypeId() != null) {
                ImsIncidentType imsIncidentType = IncidentEntitiesManager.getInstance().getIncidentType(incidentReport.getIncidentTypeId());
				
                if (imsIncidentType != null) {
                    incidentSubType = imsIncidentType.getAlias();

                    if (imsIncidentType.getIncidentGroupId() != null) {
                        ImsIncidentGroup imsIncidentGroup = IncidentEntitiesManager.getInstance()
                                .getIncidentGroup(imsIncidentType.getIncidentGroupId());
                        incidentType = imsIncidentGroup.getLabelAlias();
                    }
                }
            }

            if (incidentReport.getIncidentLevelId() != null) {
                ImsIncidentLevel incidentLevel = IncidentEntitiesManager.getInstance().getIncidentLevel(incidentReport.getIncidentLevelId());

                if (incidentLevel != null) {
                    level = incidentLevel.getLabelAlias();
                }
            }

            if (incidentReport.getCurrentUserId() != null) {
                User user = EntitiesManager.getInstance().getUser(incidentReport.getCurrentUserId());

                if (user != null) {
                    userName = user.getFullName();
                }
            }
        }

        List<ColumnValuePair> columnValuePairs = List.of(
            new ColumnValuePair("incident_report_id", incidentReportId),
            new ColumnValuePair("type_detection", detectionType),
            new ColumnValuePair("incident_type", incidentType),
            new ColumnValuePair("incident_sub_type", incidentSubType),
            new ColumnValuePair("level", level),
            new ColumnValuePair("user_n", userName),
            new ColumnValuePair("element_id", alias),
            new ColumnValuePair("source_id", detectionOrigin)
        );
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (ColumnValuePair pair : columnValuePairs) {
            if (pair.value != null) {
                columns.append(pair.column).append(", ");
                values.append("'").append(pair.value).append("', ");
            }
        }
        columns.delete(columns.length() - 2, columns.length()); // Remove the trailing comma and space
        values.delete(values.length() - 2, values.length()); // Remove the trailing comma and space

        String insertStatement = "INSERT INTO " + DB_SCHEMA + ".incidents (" + columns + ") VALUES (" + values + ");";

        return insertStatement;
    }
    
    static class ColumnValuePair {
        String column;
        String value;

        public ColumnValuePair(String column, String value) {
            this.column = column;
            this.value = value;
        }
    }
}