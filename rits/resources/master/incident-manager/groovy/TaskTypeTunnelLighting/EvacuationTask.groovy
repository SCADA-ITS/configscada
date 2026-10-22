import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOnCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class EvacuationTask {

	static final Long ELEMENT_TYPE_PARAM_CIRCUIT_TYPE = 5L;
	static final String ELEMENT_VALUE = "Guiado";
	static final Long COMMAND_ELEMENT_TYPE_CIRCUIT_ON = 2L;

	org.apache.logging.log4j.Logger log;

	EvacuationTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null) {

			Set<Long> tubesId = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_TUBE,
					incidentTypeStretch.getStretchId());

			if (!CollectionUtils.isEmpty(tubesId)) {

				elements = new ArrayList<>();

				for (Long tubeId : tubesId) {
				
					log.debug("EvacuationTask(getElements) tubo afectado 2000:" + tubeId);

					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(
							ElementType.ELEMENT_TYPE_ILLUMINATION, ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						Long Id = elementHierarchies.get(0).getParentElementId();
						log.debug("EvacuationTask(getElements) tunnel afectado 2002:" + Id);

						elements = new ArrayList<>();
						elements.add(new Element(ElementType.ELEMENT_TYPE_ILLUMINATION, Id));
					}

				}
			}
		}

		return elements;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = null;
		Set<Long> elements = null;

		if (incidentReport.getAffectionStretchId() != null) {
	
			elements = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_TUBE,
					incidentReport.getAffectionStretchId());
													
			if (!CollectionUtils.isEmpty(elements)) {		
													
				for (Long elementId : elements) {
				
					log.debug("EvacuationTask(process) - El tubo afectado es 2000:" + elementId);
															
					List<ElementHierarchy> illuminationsId = EntitiesManager.getInstance().getsByChild(
							ElementType.ELEMENT_TYPE_ILLUMINATION, ElementType.ELEMENT_TYPE_TUBE, elementId);	
							
					Long illuminationId = illuminationsId.get(0).getParentElementId();
					log.debug("EvacuationTask(process) El elemento de iluminación afectado es 2002:" + illuminationId);
						
					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(
							ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ElementType.ELEMENT_TYPE_TUBE);												
													
					if (!CollectionUtils.isEmpty(elementHierarchies)) {
											
						for (ElementHierarchy elementHierarchie : elementHierarchies) {
											
							log.info("EvacuationTask(process) El tubo donde voy a cambiar circuitos es 2000:" + elementHierarchie.getChildElementId());
											
							List<ElementHierarchy> circuitHierarchies = EntitiesManager.getInstance().getsByParent(
									ElementType.ELEMENT_TYPE_TUBE, elementHierarchie.getChildElementId(), ElementType.ELEMENT_TYPE_CIRCUIT);
											
							if (!CollectionUtils.isEmpty(circuitHierarchies)) {
											
								SetCircuitsOnCommand setCircuitsOnCommand = null;
											
								for (ElementHierarchy circuitHierarchie : circuitHierarchies) {
											
									ElementValue elementValue = EntitiesManager.getInstance().getConfigValue(
											ElementType.ELEMENT_TYPE_CIRCUIT, circuitHierarchie.getChildElementId(),
											ELEMENT_TYPE_PARAM_CIRCUIT_TYPE);
											
									if (elementValue != null && ELEMENT_VALUE.equals(elementValue.getValue()) ) {
											
										if(setCircuitsOnCommand == null){
											
											setCircuitsOnCommand = new SetCircuitsOnCommand();
											setCircuitsOnCommand.setElementTypeId(ElementType.ELEMENT_TYPE_TUBE);
											setCircuitsOnCommand.setElementId(elementHierarchie.getChildElementId());
										}
										
										log.info("   EvacuationTask(process) El circuito sobre el que voy a actuar es 84:" + circuitHierarchie.getChildElementId());
										setCircuitsOnCommand.addElement(new Element(ElementType.ELEMENT_TYPE_CIRCUIT, circuitHierarchie.getChildElementId()));
											
										if(setCircuitsOnCommand != null){
																		
											if (commands == null) {
												commands = new ArrayList<>();
											}
											commands.add(setCircuitsOnCommand);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return commands;
	}
}
