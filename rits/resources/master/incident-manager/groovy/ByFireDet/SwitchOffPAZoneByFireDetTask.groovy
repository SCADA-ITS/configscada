import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashSet;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;

class SwitchOffPAZoneByFireDetTask {

	static final Long ELEMENT_TYPE_FIRE_DET = 15L;

	static final Long ELEMENT_TYPE_PA_SYSTEM = 5L;
	static final Long ELEMENT_TYPE_PA_ZONE = 16L;
	static final Long ELEMENT_TYPE_PARAM_ZONE_ID = 5L;
	static final Long COMMAND_ELEMENT_TYPE_SWITCH_OFF_ZONES = 1L;
	static final Integer COMMAND_ELEMENT_TYPE_PARAM_ZONES = 1;
	static final Integer COMMAND_ELEMENT_TYPE_PARAM_ZONES_DATA_TYPE = 2;

	org.apache.logging.log4j.Logger log;

	SwitchOffPAZoneByFireDetTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null ) {

			Set<Long> fireDetIds = EntitiesManager.getInstance()
					.getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentTypeStretch.getStretchId());

			if (!CollectionUtils.isEmpty(fireDetIds)) {

				Long fireDetId = fireDetIds.iterator().next();
				log.info("SwitchOffPAZoneByFireDetTask - fireDetId: " + fireDetId);

				List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE,
						ELEMENT_TYPE_FIRE_DET, fireDetId);

				if (!CollectionUtils.isEmpty(elementHierarchies)) {

					Long tubeId = elementHierarchies.get(0).getParentElementId();
					log.info("SwitchOffPAZoneByFireDetTask - tubeId: " + tubeId);

					elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_ILLUMINATION,
							ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						Long illuminationId = elementHierarchies.get(0).getParentElementId();
						log.info("SwitchOffPAZoneByFireDetTask - illuminationId: " + illuminationId);

						elementHierarchies = EntitiesManager.getInstance().getsByParent(
								ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ELEMENT_TYPE_PA_ZONE);

						if (!CollectionUtils.isEmpty(elementHierarchies)) {

							for (ElementHierarchy elementHierarchie : elementHierarchies) {

								if(elements == null){

									elements = new ArrayList<>();
								}

								elements.add(new Element(ELEMENT_TYPE_PA_ZONE, elementHierarchie.getChildElementId()));
							}
						}
					}
				}
			}
		}

		return elements;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = new ArrayList<>();
		Set<Long> elements = null;

		if (incidentReport.getAffectionStretchId() != null) {


			Set<Long> fireDetIds = EntitiesManager.getInstance()
					.getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentReport.getAffectionStretchId());

			if (!CollectionUtils.isEmpty(fireDetIds)) {

				Long fireDetId = fireDetIds.iterator().next();
				//log.info("SendMessagePAGroupByFireDetTask - fireDetId: " + fireDetId);

				List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE,
						ELEMENT_TYPE_FIRE_DET, fireDetId);

				if (!CollectionUtils.isEmpty(elementHierarchies)) {

					Long tubeId = elementHierarchies.get(0).getParentElementId();
					//log.info("SendMessagePAGroupByFireDetTask - tubeId: " + tubeId);

					elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_ILLUMINATION,
							ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						Long illuminationId = elementHierarchies.get(0).getParentElementId();
						//log.info("SendMessagePAGroupByFireDetTask - illuminationId: " + illuminationId);

						elementHierarchies = EntitiesManager.getInstance().getsByParent(
								ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ELEMENT_TYPE_PA_ZONE);

						if (!CollectionUtils.isEmpty(elementHierarchies)) {

							for (ElementHierarchy elementHierarchie : elementHierarchies) {

								if (elements == null) {

									elements = new HashSet<>();
								}
								elements.add(elementHierarchie.getChildElementId());
							}
						}
					}
				}
			}

			if (!CollectionUtils.isEmpty(elements)) {

				Map<Long, List<String>> map = new HashedMap<>();

				for (Long elementId : elements) {

					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance()
							.getsByChild(ELEMENT_TYPE_PA_SYSTEM, ELEMENT_TYPE_PA_ZONE, elementId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						for (ElementHierarchy elementHierarchy : elementHierarchies) {

							if (elementHierarchy.getParentElementId() != null) {

								ElementValue elementValue = EntitiesManager.getInstance().getConfigValue(
										ELEMENT_TYPE_PA_ZONE, elementId,
										ELEMENT_TYPE_PARAM_ZONE_ID);

								if (elementValue != null && elementValue.getValue() != null) {

									if (!map.containsKey(elementHierarchy.getParentElementId())) {

										map.put(elementHierarchy.getParentElementId(), new ArrayList<>());
									}

									map.get(elementHierarchy.getParentElementId()).add("\""+ elementValue.getValue() + "\"");
								}
							}
						}
					}
				}

				for (Entry<Long, List<String>> entry : map.entrySet()) {

					SignallingCommand signallingCommand = new SignallingCommand();
					signallingCommand.setElementTypeId(ELEMENT_TYPE_PA_SYSTEM);
					signallingCommand.setSignallingCommandId(COMMAND_ELEMENT_TYPE_SWITCH_OFF_ZONES);
					signallingCommand.setElementId(entry.getKey());
					signallingCommand.setSignallingParams(new ArrayList<>());

					SignallingParam signallingParam = new SignallingParam();
					signallingParam.setId(COMMAND_ELEMENT_TYPE_PARAM_ZONES);
					signallingParam.setType(COMMAND_ELEMENT_TYPE_PARAM_ZONES_DATA_TYPE);
					signallingParam.setValue(entry.getValue().toString());

					signallingCommand.getSignallingParams().add(signallingParam);

					commands.add(signallingCommand);
				}
			}

		}

		return commands;
	}
}
