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

class SendMessagePAGroupEvacuation {

	static final Long ELEMENT_TYPE_PA_SYSTEM = 5L;
	static final Long ELEMENT_TYPE_PA_GROUP = 96L;
	static final Long ELEMENT_TYPE_PARAM_GROUP_ID = 5L;
	static final Long COMMAND_ELEMENT_TYPE_SEND_MESSAGE_GROUPS = 4L;
	static final Integer COMMAND_ELEMENT_TYPE_PARAM_GROUPS = 1;
	static final Integer COMMAND_ELEMENT_TYPE_PARAM_GROUPS_DATA_TYPE = 2;
	static final Integer COMMAND_ELEMENT_TYPE_MESSAGE_ID = 2;
	static final Integer COMMAND_ELEMENT_TYPE_MESSAGE_ID_DATA_TYPE = 2;

	static final Long TASK_TYPE_PARAM_MESSAGE_IDS = 1L;

	org.apache.logging.log4j.Logger log;

	SendMessagePAGroupEvacuation(org.apache.logging.log4j.Logger log) {

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
										
					log.debug("SendMessagePAGroupEvacuation(getElements) tubo afectado 2000:" + tubeId);
					
					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(
							ElementType.ELEMENT_TYPE_ILLUMINATION, ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {
					
						Long illuminationId = elementHierarchies.get(0).getParentElementId();
						log.debug("SendMessagePAGroupEvacuation(getElements) tunnel afectado 2002:" + illuminationId);
							
						List<ElementHierarchy> elementGroups = EntitiesManager.getInstance().getsByParent(
							ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ELEMENT_TYPE_PA_GROUP);
							
						if (!CollectionUtils.isEmpty(elementGroups)) {
						
							for (ElementHierarchy elementGroup : elementGroups) {		
								log.debug("SendMessagePAGroupEvacuation(getElements) grupos del tunel 96:" + elementGroup.getChildElementId()); 
								elements = new ArrayList<>();
								elements.add(new Element(ELEMENT_TYPE_PA_GROUP, elementGroup.getChildElementId()));
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
	    
		    String value = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(values,
						TASK_TYPE_PARAM_MESSAGE_IDS);
			
			if (value != null) {
	
				Set<Long> tubesId = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_TUBE,
								incidentReport.getAffectionStretchId());
		
				if (!CollectionUtils.isEmpty(tubesId)) {
		
					for (Long tubeId : tubesId) {
					
						log.debug("SendMessagePAGroupEvacuation(process) tubo afectado 2000:" + tubeId);
		
						List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(
								ElementType.ELEMENT_TYPE_ILLUMINATION, ElementType.ELEMENT_TYPE_TUBE, tubeId);
		
						if (!CollectionUtils.isEmpty(elementHierarchies)) {
		
							for (ElementHierarchy elementHierarchie : elementHierarchies) {
		
								Long illuminationId = elementHierarchie.getParentElementId();
								log.debug("SendMessagePAGroupEvacuation(process) tunnel afectado 2002:" + illuminationId);
		
								List<ElementHierarchy> elementGroups = EntitiesManager.getInstance().getsByParent(
										ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ELEMENT_TYPE_PA_GROUP);
		
								if (!CollectionUtils.isEmpty(elementGroups)) {
		
									for (ElementHierarchy elementGroup : elementGroups) {
										
										log.debug("SendMessagePAGroupEvacuation(process) grupo donde difundiré el mensaje 96:" + elementGroup.getChildElementId());
										if (elements == null) {
		
											elements = new HashSet<>();
										}
										elements.add(elementGroup.getChildElementId());
									}
								}
							}
		
							if (!CollectionUtils.isEmpty(elements)) {
		
								Map<Long, List<String>> map = new HashedMap<>();
			
								for (Long elementId : elements) {
			
									elementHierarchies = EntitiesManager.getInstance()
											.getsByChild(ELEMENT_TYPE_PA_SYSTEM, ELEMENT_TYPE_PA_GROUP, elementId);
			
									if (!CollectionUtils.isEmpty(elementHierarchies)) {
			
										for (ElementHierarchy elementHierarchy : elementHierarchies) {
			
											if (elementHierarchy.getParentElementId() != null) {
			
												ElementValue elementValue = EntitiesManager.getInstance().getConfigValue(
														ELEMENT_TYPE_PA_GROUP, elementId,
														ELEMENT_TYPE_PARAM_GROUP_ID);
			
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
									signallingCommand.setSignallingCommandId(COMMAND_ELEMENT_TYPE_SEND_MESSAGE_GROUPS);
									signallingCommand.setElementId(entry.getKey());
									signallingCommand.setSignallingParams(new ArrayList<>());
			
									SignallingParam signallingParam = new SignallingParam();
									signallingParam.setId(COMMAND_ELEMENT_TYPE_PARAM_GROUPS);
									signallingParam.setType(COMMAND_ELEMENT_TYPE_PARAM_GROUPS_DATA_TYPE);
									signallingParam.setValue(entry.getValue().toString());
									signallingCommand.getSignallingParams().add(signallingParam);
			
									signallingParam = new SignallingParam();
									signallingParam.setId(COMMAND_ELEMENT_TYPE_MESSAGE_ID);
									signallingParam.setType(COMMAND_ELEMENT_TYPE_MESSAGE_ID_DATA_TYPE);
									signallingParam.setValue(value);
									signallingCommand.getSignallingParams().add(signallingParam);
			
									commands.add(signallingCommand);
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
