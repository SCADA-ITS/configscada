import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;

class LockCameraByGalibo{

	static final Long ELEMENT_TYPE_CAMERA = 2L;
	static final Long ELEMENT_TYPE_GALIBO = 17L;
	static final Long LOCK_CAMERA_COMMAND = 1L;
	
	static final Long PRESET_COMMAND = 2L;
	static final int DATA_TYPE_ALPHANUMERIC = 2;
	static final Long PARAM_CONFIG_PTZ = 7L;
	static final Long PARAM_CONFIG = 1L;
	static final String PRESET_TYPE = "5";
	static final int PARAM_ID_1 = 1;
	static final int PARAM_ID_2 = 2;
	static final Long ELEMENT_SUBTYPE_DOMO = 3L;


	org.apache.logging.log4j.Logger log;

	LockCameraByGalibo(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}


	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		
		List<Command> commands = null;
		List<ImsIncidentReportAlarm> incidentReportAlarms = IncidentEntitiesManager.getInstance()
			.getIncidentReportAlarms(incidentReport.getId());
			
		if (incidentReportAlarms.isEmpty()){
			Set<Long> ids = EntitiesManager.getInstance().getElementByStretch(ELEMENT_TYPE_GALIBO, incidentReport.getAffectionStretchId());
			
			if(ids != null) {
				
				for (Long id : ids) {
					
					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(ELEMENT_TYPE_GALIBO, id, ELEMENT_TYPE_CAMERA);
					
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
				log.debug("No se encuentra galibo en el tramo afectado");
			}
		}else{
			Element galibo = EntitiesManager.getInstance().getElement(incidentReportAlarms.get(0).getElementTypeId(), incidentReportAlarms.get(0).getElementId());
			
			List<ElementHierarchy> cameraChild = EntitiesManager.getInstance().getsByParent(galibo.getElementTypeId(), galibo.getId(), ELEMENT_TYPE_CAMERA);
			if(cameraChild != null){
				log.debug("Camara a enclavar: " + cameraChild.getAt(0));		
				Element camara = EntitiesManager.getInstance().getElement(ELEMENT_TYPE_CAMERA, cameraChild.getAt(0).getChildElementId());	
	
				if(camara != null){
					if (commands == null) {
						commands = new ArrayList<>();
					}	
					if(camara.getElementSubtypeId() == ELEMENT_SUBTYPE_DOMO){
						commands.add(rellenaSignallingCommand(cameraChild.getAt(0).getChildElementId()));
						commands.add(rellenaPreset(cameraChild.getAt(0).getChildElementId()));
					}else{
						commands.add(rellenaSignallingCommand(cameraChild.getAt(0).getChildElementId()));
					}
	
				}else{
					log.error("Este poste sos no tiene camara asociada");
				}
			}else{
				log.error("Este poste sos no tiene camara asociada");
			}
		}
		
		return commands;
	}
	
	SignallingCommand rellenaSignallingCommand(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(LOCK_CAMERA_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
		signallingCommand.setElementId(element);
		
		return signallingCommand;
	}

    SignallingCommand rellenaPreset(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(PRESET_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
		signallingCommand.setElementId(element);

		signallingCommand.setSignallingParams(paramPreset(PRESET_TYPE));
		
		return signallingCommand;
	}

	List<SignallingParam> paramPreset(String paramValue){
		List<SignallingParam> signallingParams = new ArrayList<>();
		
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_1);
		signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
		signallingParam.setValue(paramValue);

		signallingParams.add(signallingParam);

		return signallingParams;
	}
}
