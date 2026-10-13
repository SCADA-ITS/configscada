import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;

class LockCamFireZone {

	static final Long ELEMENT_TYPE_CAMERA = 2L;
	static final Long ELEMENT_TYPE_FIRE_DET = 15L;
	static final Long LOCK_CAMERA_COMMAND = 1L;


	org.apache.logging.log4j.Logger log;

	LockCamFireZone(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}


	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		
		List<Command> commands = null;
		
		List<ImsIncidentReportAlarm> incidentReportAlarms = IncidentEntitiesManager.getInstance().getIncidentReportAlarms(incidentReport.getId());
		
		if (incidentReportAlarms == null || incidentReportAlarms.isEmpty()){
		
			Set<Long> fireDetIds = EntitiesManager.getInstance().getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentReport.getAffectionStretchId());
			
			if(fireDetIds != null) {
				
				for (Long id : fireDetIds) {
					
					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(ELEMENT_TYPE_FIRE_DET, id, ELEMENT_TYPE_CAMERA);
					
					if(elementHierarchies != null) {
						
						for (ElementHierarchy elementHierarchy : elementHierarchies){
							
							SignallingCommand signallingCommand = new SignallingCommand();
			
							signallingCommand.setSignallingCommandId(LOCK_CAMERA_COMMAND);
							signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
							signallingCommand.setElementId(elementHierarchy.getChildElementId());
							
							if (commands == null) {
								
								commands = new ArrayList<>();
							}
							
							commands.add(signallingCommand);
						}
					} else {
						log.debug("No se encuentra camara asociada para el tramo afectado");
					}		
				}
			} else {
				log.debug("No se encuentra zona de incendios en el tramo afectado");
			}
		} else {
			
			Element fireZone = EntitiesManager.getInstance().getElement(incidentReportAlarms.get(0).getElementTypeId(), incidentReportAlarms.get(0).getElementId());
			
			List<ElementHierarchy> cameraChild = EntitiesManager.getInstance().getsByParent(fireZone.getElementTypeId(), fireZone.getId(), ELEMENT_TYPE_CAMERA);
			if(cameraChild != null && cameraChild.getAt(0) != null){
				log.debug("Camara a enclavar: " + cameraChild.getAt(0));		
				Element camara = EntitiesManager.getInstance().getElement(ELEMENT_TYPE_CAMERA, cameraChild.getAt(0).getChildElementId());	
	
				if(camara != null){
						
					SignallingCommand signallingCommand = new SignallingCommand();
			
					signallingCommand.setSignallingCommandId(LOCK_CAMERA_COMMAND);
					signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
					signallingCommand.setElementId(camara.getId());
					
					if (commands == null) {
						commands = new ArrayList<>();
					}
					commands.add(signallingCommand);	
				}else{
					log.error("Esta zona de incendio sos no tiene camara asociada");
				}
			}else{
				log.error("Esta zona de incendio no tiene camara asociada");
			}
			
		}
		
		return commands;
	}
}
