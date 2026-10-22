import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;


import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOnCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class EvacuationByFireDetTask {

	static final Long ELEMENT_TYPE_FIRE_DET = 15L;

	static final Long ELEMENT_TYPE_PARAM_CIRCUIT_TYPE = 5L;
	static final String ELEMENT_VALUE = "Guiado";
	static final Long COMMAND_ELEMENT_TYPE_CIRCUIT_ON = 2L;

	org.apache.logging.log4j.Logger log;

	EvacuationByFireDetTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null ) {

			Set<Long> fireDetIds = EntitiesManager.getInstance()
					.getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentTypeStretch.getStretchId());

			if (!CollectionUtils.isEmpty(fireDetIds)) {

				Long fireDetId = fireDetIds.iterator().next();

				log.info("EvacuationByFireDetTask - fireDetId: " + fireDetId);

				List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE,
						ELEMENT_TYPE_FIRE_DET, fireDetId);

				if (!CollectionUtils.isEmpty(elementHierarchies)) {

					Long tubeId = elementHierarchies.get(0).getParentElementId();

					log.info("EvacuationByFireDetTask - tubeId: " + tubeId);

					elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_ILLUMINATION,
							ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						Long illuminationId = elementHierarchies.get(0).getParentElementId();
						log.info("EvacuationByFireDetTask - illuminationId: " + illuminationId);

						elements = new ArrayList<>();
						elements.add(new Element(ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId));
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

			Set<Long> fireDetIds = EntitiesManager.getInstance().getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentReport.getAffectionStretchId());

			if (!CollectionUtils.isEmpty(fireDetIds)) {

				Long fireDetId = fireDetIds.iterator().next();

				//log.info("EvacuationByFireDetTask - fireDetId: " + fireDetId);

				List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE,
						ELEMENT_TYPE_FIRE_DET, fireDetId);

				if (!CollectionUtils.isEmpty(elementHierarchies)) {

					Long tubeId = elementHierarchies.get(0).getParentElementId();

					//log.info("EvacuationByFireDetTask - tubeId: " + tubeId);

					elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_ILLUMINATION,
							ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						Long illuminationId = elementHierarchies.get(0).getParentElementId();

						//log.info("EvacuationByFireDetTask - illuminationId: " + illuminationId);

						elementHierarchies = EntitiesManager.getInstance().getsByParent(
								ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ElementType.ELEMENT_TYPE_TUBE);

						if (!CollectionUtils.isEmpty(elementHierarchies)) {

							for (ElementHierarchy elementHierarchie : elementHierarchies) {

								log.info("EvacuationByFireDetTask - tubeId: " + elementHierarchie.getChildElementId());

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
											log.info("EvacuationByFireDetTask - circuitId: " + circuitHierarchie.getChildElementId());
											setCircuitsOnCommand.addElement(new Element(ElementType.ELEMENT_TYPE_CIRCUIT, circuitHierarchie.getChildElementId()));
										}
									}

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
		return commands;
	}
}
