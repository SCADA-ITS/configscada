import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetFireAlarmCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;

class LockCamera {

	static final Long ELEMENT_TYPE_CAMERA = 2L;
	static final Long ELEMENT_TYPE_FIRE_DET = 15L;
	static final Long LOCK_CAMERA_COMMAND = 1L;


	org.apache.logging.log4j.Logger log;

	LockCamera(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}


	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null ) {

			Set<Long> fireDetIds = EntitiesManager.getInstance().getElementByStretch(ELEMENT_TYPE_FIRE_DET,incidentTypeStretch.getStretchId());

			if (fireDetIds != null) {

				elements = new ArrayList<>();

				for (Long id : fireDetIds) {

					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(ELEMENT_TYPE_FIRE_DET, id, ELEMENT_TYPE_CAMERA);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						for (ElementHierarchy elementHierarchie : elementHierarchies) {

							elements.add(new Element(ElementType.ELEMENT_TYPE_CAMERA, elementHierarchie.getChildElementId()));
						}
					}
				}
			}
		}

		return elements;
	}
	
	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		List<Command> commands = null;
	
		List<ImsIncidentReportAlarm> incidentReportAlarms = IncidentEntitiesManager.getInstance()
			.getIncidentReportAlarms(incidentReport.getId());
			
		if (incidentReportAlarms.isEmpty()){
			Set<Long> fireDetIds = EntitiesManager.getInstance().getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentReport.getAffectionStretchId());
			
			if(fireDetIds != null){
				for (Long id : fireDetIds){
					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(ELEMENT_TYPE_FIRE_DET, id, ELEMENT_TYPE_CAMERA);
					
					if (!CollectionUtils.isEmpty(elementHierarchies)) {
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
					}else{
						log.debug("No se encuentra camara asociada para el tramo afectado");
					}		
				}
			}else{
				log.debug("No se encuentra zona de incendios en el tramo afectado");
			}
		}else{
			SignallingCommand signallingCommand = new SignallingCommand();
			
			signallingCommand.setSignallingCommandId(LOCK_CAMERA_COMMAND);
			signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
			signallingCommand.setElementId(incidentReportAlarms.get(0).getElementId());
			
			if (commands == null) {
				commands = new ArrayList<>();
			}
			commands.add(signallingCommand);
		}
		
		return commands;
	}

	
}
