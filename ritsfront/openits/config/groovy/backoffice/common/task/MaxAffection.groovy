package config.groovy.backoffice.common.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.front.openits.backoffice.BackOfficeException;
import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTask;

public class MaxAffection extends AbstractBackOfficeTask {

    private static final String CONNECTION_URL = "jdbc:postgresql://192.168.88.160:5430/rits";
    private static final String DB_USER = "rits";
    private static final String DB_PASSWORD = "rits";

    public MaxAffection(org.apache.logging.log4j.Logger log) {
        super(log);
    }

    @Override
    public boolean onExecute() {

        boolean res = false;

        try {
			
			Connection connection = DriverManager.getConnection(CONNECTION_URL, DB_USER, DB_PASSWORD)

            List<Long> incidentIds = new ArrayList<>();
            PreparedStatement psAllIds = connection.prepareStatement("SELECT incident_report_id FROM rt.ims_incident_reports");
            ResultSet rsIds = psAllIds.executeQuery();

            while (rsIds.next()) {
                incidentIds.add(rsIds.getLong("incident_report_id"));
            }

            rsIds.close();
            psAllIds.close();

            log.debug("Se han encontrado " + incidentIds.size() + " incident_report_id(s).");

            for (Long id : incidentIds) {
                String query = readMaxAffection(id);
                
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                String value = '';

                if (rs.next()) {
                    value = rs.getString("value");
                    log.debug(String.format("Incident %d → Máxima afección: %s", id, value));
                } else {
                    log.debug(String.format("Incident %d → No tiene registros de afección", id));
                }
                
                connection.setAutoCommit(false);
                
			    ps = connection.prepareStatement(updateStatement(id, value));
			    ps.executeUpdate();
		
			    connection.commit();

                rs.close();
                ps.close();
            }

            res = true;

        } catch (SQLException e) {
            log.error("Error durante el procesamiento de incidentes: " + e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
            return false;
        }

        return res;
    }
    
    private String updateStatement(Long incidentReportId, String value) {
		
		return String.format(
				"UPDATE incidents.incidents set max_affection = '%s' where incident_report_id = '%s'", 
				value, String.valueOf(incidentReportId));
	}

    /**
     * Devuelve la query que obtiene la máxima afección de un incident_report_id.
     */
    private String readMaxAffection(Long incidentReportId) {
	    return String.format(
	        "SELECT iirl.incident_report_id, iirlv.value " +
	        "FROM rt.ims_incident_report_logs iirl " +
	        "INNER JOIN rt.ims_incident_report_log_values iirlv " +
	        "ON iirlv.incident_report_log_id = iirl.incident_report_log_id " +
	        "AND iirlv.log_type_param_id = 2 " +
	        "WHERE iirl.incident_report_id = %d " +
	        "ORDER BY (CASE iirlv.value " +
	            "WHEN 'Circulación interrumpida' THEN 1 " +
	            "WHEN 'Circulación difícil' THEN 2 " +
	            "WHEN 'Circulación irregular' THEN 3 " +
	            "WHEN 'Circulación condicionada' THEN 4 " +
	            "ELSE 0 END) ASC " +
	        "LIMIT 1;",
	        incidentReportId
	    );
	}
}
