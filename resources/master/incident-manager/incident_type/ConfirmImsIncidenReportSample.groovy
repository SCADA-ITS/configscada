import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentReport;

/**
 *
 * ConfirmImsIncidenReportSample.groovy: Groovy for change state of equipment
 *
 */
class ConfirmImsIncidenReportSample {

	org.apache.logging.log4j.Logger log;
	
	ConfirmImsIncidenReportSample(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(ImsIncidentReport incidentReport) {
		
		try {

			log.info("GROOVY EXECUTED!!!");
			log.info("ConfirmImsIncidenReportSample::process(Incident Report:" + incidentReport.getId()+")");

			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
