package com.revenga.rits.back.incident.manager.service;

import com.revenga.rits.back.data.core.model.*;
import com.revenga.rits.back.data.core.model.command.Command;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;

import java.time.OffsetDateTime
import java.time.ZoneOffset


class SendEndIncident {

   org.apache.logging.log4j.Logger log
   private static final String CONNECTION_URL = "jdbc:postgresql://10.238.4.128:5432/controltrafico";
   private static final String DB_USER = "dbo_controltrafico";
   private static final String DB_PASSWORD = "12hJklfj9.pha9";
   private static final String DB_SCHEMA = "incidents";

    SendEndIncident(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

   List<Element> getElements(ImsIncidentTypeTask incidentTypeTask,ImsIncidentTypeStretch incidentTypeStretch) {

   	List<Element> elements = new ArrayList<>();

    	return elements;
    }

    List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

	try {
 		Connection connection = DriverManager.getConnection(CONNECTION_URL, DB_USER, DB_PASSWORD);
         	PreparedStatement psId = connection.prepareStatement("UPDATE " + DB_SCHEMA + ".incidents SET finish_at = ? WHERE incident_report_id = ?");

		OffsetDateTime finishAt = OffsetDateTime.now(ZoneOffset.UTC);
		String incidentReportId = incidentReport.getId(); 

        	psId.setTimestamp(1, finishAt);
		psId.setString(2, incidentReportId);

        	int rows = psId.executeUpdate();

        	if (rows > 0) {
            		log.info("Insert realizado correctamente.");
        	}

    	} catch (SQLException e) {
        	log.error("Error during incident processing: " + e.getMessage(), e);
    	}
    }
}
