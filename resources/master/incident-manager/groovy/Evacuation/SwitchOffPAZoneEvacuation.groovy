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

class SwitchOffPAZoneEvacuation {

	static final Long ELEMENT_TYPE_PA_SYSTEM = 5L;
	static final Long ELEMENT_TYPE_PA_ZONE = 16L;
	static final Long ELEMENT_TYPE_PARAM_ZONE_ID = 5L;
	static final Long COMMAND_ELEMENT_TYPE_SWITCH_OFF_ZONES = 1L;
	static final Integer COMMAND_ELEMENT_TYPE_PARAM_ZONES = 1;
	static final Integer COMMAND_ELEMENT_TYPE_PARAM_ZONES_DATA_TYPE = 2;

	org.apache.logging.log4j.Logger log;

	SwitchOffPAZoneEvacuation(org.apache.logging.log4j.Logger log) {

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
				
					log.debug("SwitchOffPAZoneEvacuation(getElements) tubo afectado 2000:" + tubeId);

					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(
							ElementType.ELEMENT_TYPE_ILLUMINATION, ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {
						
						for (ElementHierarchy elementHierarchie : elementHierarchies) {
							
							log.debug("   SwitchOffPAZoneEvacuation(getElements) tunel en el que busco zonas 2002:" + elementHierarchie.getParentElementId());
							List<ElementHierarchy> elementZones = EntitiesManager.getInstance().getsByParent(
								ElementType.ELEMENT_TYPE_ILLUMINATION, elementHierarchie.getParentElementId(), ELEMENT_TYPE_PA_ZONE);
							
							for (ElementHierarchy elementZone : elementZones) {	
								log.debug("      SwitchOffPAZoneEvacuation(getElements) encontrada zona 16:" + elementZone.getChildElementId()); 	
								elements = new ArrayList<>();
								elements.add(new Element(ELEMENT_TYPE_PA_ZONE, elementZone.getChildElementId()));
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
	
				Set<Long> tubesId = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_TUBE,
								incidentReport.getAffectionStretchId());
		
				if (!CollectionUtils.isEmpty(tubesId)) {
		
				for (Long tubeId : tubesId) {
				
					log.debug("SwitchOffPAZoneEvacuation(process) tubo afectado 2000:" + tubeId);
	
					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(
							ElementType.ELEMENT_TYPE_ILLUMINATION, ElementType.ELEMENT_TYPE_TUBE, tubeId);
	
					if (!CollectionUtils.isEmpty(elementHierarchies)) {
	
						for (ElementHierarchy elementHierarchie : elementHierarchies) {
	
							Long illuminationId = elementHierarchie.getParentElementId();
							log.debug("SwitchOffPAZoneEvacuation(process) tunnel afectado 2002:" + illuminationId);
	
							List<ElementHierarchy> elementZones = EntitiesManager.getInstance().getsByParent(
									ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ELEMENT_TYPE_PA_ZONE);
	
							if (!CollectionUtils.isEmpty(elementZones)) {
	
								for (ElementHierarchy elementZone : elementZones) {
									
									log.debug("SwitchOffPAZoneEvacuation(process) zona donde difundiré el mensaje 16:" + elementZone.getChildElementId());
									if (elements == null) {
	
										elements = new HashSet<>();
									}
									elements.add(elementZone.getChildElementId());
								}
							}
						}
	
						if (!CollectionUtils.isEmpty(elements)) {

							Map<Long, List<String>> map = new HashedMap<>();
		
							for (Long elementId : elements) {
		
								elementHierarchies = EntitiesManager.getInstance()
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
				}
			}
		}
		
		return commands;
	}
}
