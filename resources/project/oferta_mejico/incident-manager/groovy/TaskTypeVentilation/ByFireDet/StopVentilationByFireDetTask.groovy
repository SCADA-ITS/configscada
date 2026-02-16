import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;


import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToVentilationCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class StopVentilationByFireDetTask {

	static final Long ELEMENT_TYPE_FIRE_DET = 15L;

	org.apache.logging.log4j.Logger log;

	StopVentilationByFireDetTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	
	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null ) {

			Set<Long> fireDetIds = EntitiesManager.getInstance()
			.getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentTypeStretch.getStretchId());

			if (!CollectionUtils.isEmpty(fireDetIds)) {

				Long fireDetId = fireDetIds.iterator().next();

				log.info("StopVentilationByFireDetTask - fireDetId: " + fireDetId);
				
				List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE, 
					ELEMENT_TYPE_FIRE_DET, fireDetId);

				if (!CollectionUtils.isEmpty(elementHierarchies)) {
					
					Long tubeId = elementHierarchies.get(0).getParentElementId();
					
					log.info("StopVentilationByFireDetTask - tubeId: " + tubeId);
					
					elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_VENTILATION,
						ElementType.ELEMENT_TYPE_TUBE, tubeId);
					
					if (!CollectionUtils.isEmpty(elementHierarchies)) {
					
						Long ventilationId = elementHierarchies.get(0).getParentElementId();
						log.info("StopVentilationByFireDetTask - ventilationId: " + ventilationId);
						
						elements = new ArrayList<>();
						elements.add(new Element(ElementType.ELEMENT_TYPE_VENTILATION, ventilationId));	
					}
				}
			}	
		}

		return elements;
	}
	
	
	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = null;

		if (incidentReport.getAffectionStretchId() != null) {

			Set<Long> fireDetIds = EntitiesManager.getInstance()
			.getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentReport.getAffectionStretchId());

			if (!CollectionUtils.isEmpty(fireDetIds)) {

				Long fireDetId = fireDetIds.iterator().next();

				List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE,
				ELEMENT_TYPE_FIRE_DET, fireDetId);

				if (!CollectionUtils.isEmpty(elementHierarchies)) {
					
					Long tubeId = elementHierarchies.get(0).getParentElementId();
					
					elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_VENTILATION,
						ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {
					
						Long ventilationId = elementHierarchies.get(0).getParentElementId();
						
						SetElementStatesToVentilationCommand setElementStatesToVentilationCommand = new SetElementStatesToVentilationCommand();
						
						ElementState elementState = new ElementState();
						elementState.setElementTypeId(ElementType.ELEMENT_TYPE_VENTILATION);
						elementState.setElementId(ventilationId);
						elementState.setStateId(ElementTypeState.STATE_STOPPED);
						
						setElementStatesToVentilationCommand.addElementState(elementState);
						commands = new ArrayList<>();
										
						commands.add(setElementStatesToVentilationCommand);
					}
				}
			}
		}
		return commands;
	}
}
