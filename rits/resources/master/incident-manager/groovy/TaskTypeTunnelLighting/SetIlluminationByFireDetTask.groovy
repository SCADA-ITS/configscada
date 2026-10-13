import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;


import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationModeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationRegimeCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class SetIlluminationByFireDetTask {

	static final Long ELEMENT_TYPE_FIRE_DET = 15L;
	static final Long TASK_TYPE_PARAM_MODE = 1L;
	static final Long TASK_TYPE_PARAM_REGIME = 2L;
	
	org.apache.logging.log4j.Logger log;

	SetIlluminationByFireDetTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null ) {

			Set<Long> fireDetIds = EntitiesManager.getInstance()
			.getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentTypeStretch.getStretchId());

			if (!CollectionUtils.isEmpty(fireDetIds)) {

				Long fireDetId = fireDetIds.iterator().next();

				log.info("SetIlluminationByFireDetTask - fireDetId: " + fireDetId);
				
				List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE, 
					ELEMENT_TYPE_FIRE_DET, fireDetId);

				if (!CollectionUtils.isEmpty(elementHierarchies)) {
					
					Long tubeId = elementHierarchies.get(0).getParentElementId();
					
					log.info("SetIlluminationByFireDetTask - tubeId: " + tubeId);
					
					elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_ILLUMINATION,
						ElementType.ELEMENT_TYPE_TUBE, tubeId);
					
					if (!CollectionUtils.isEmpty(elementHierarchies)) {
					
						Long illuminationId = elementHierarchies.get(0).getParentElementId();
						log.info("SetIlluminationByFireDetTask - illuminationId: " + illuminationId);
						
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
		String valueMode = null;
		String valueRegime = null;

		if (incidentReport.getAffectionStretchId() != null && !CollectionUtils.isEmpty(values)) {

			ImsIncidentTypeTaskValue incidentTypeTaskValue = null;

			for (ImsIncidentTypeTaskValue value : values) {

				if (TASK_TYPE_PARAM_MODE.equals(value.getTaskTypeParamId())) {

					incidentTypeTaskValue = value;
					break;
				}
			}

			valueMode = incidentTypeTaskValue != null ? incidentTypeTaskValue.getValue() : null;

			incidentTypeTaskValue = null;
			for (ImsIncidentTypeTaskValue value : values) {

				if (TASK_TYPE_PARAM_REGIME.equals(value.getTaskTypeParamId())) {

					incidentTypeTaskValue = value;
					break;
				}
			}

			valueRegime = incidentTypeTaskValue != null ? incidentTypeTaskValue.getValue() : null;

			if (valueMode != null || valueRegime != null) {
		
				Set<Long> fireDetIds = EntitiesManager.getInstance().getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentReport.getAffectionStretchId());

				if (!CollectionUtils.isEmpty(fireDetIds)) {
	
					Long fireDetId = fireDetIds.iterator().next();
	
					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE,
					ELEMENT_TYPE_FIRE_DET, fireDetId);
	
					if (!CollectionUtils.isEmpty(elementHierarchies)) {
						
						Long tubeId = elementHierarchies.get(0).getParentElementId();
						
						elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_ILLUMINATION,
						ElementType.ELEMENT_TYPE_TUBE, tubeId);
					
						if (!CollectionUtils.isEmpty(elementHierarchies)) {
						
							Long illuminationId = elementHierarchies.get(0).getParentElementId();
							
							elementHierarchies = EntitiesManager.getInstance().getsByParent(
								ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ElementType.ELEMENT_TYPE_TUBE);

							if (!CollectionUtils.isEmpty(elementHierarchies)) {

								for (ElementHierarchy elementHierarchie : elementHierarchies) {
								
									if (valueMode != null) {
										
										try {
											SetIlluminationModeCommand setIlluminationModeCommand = new SetIlluminationModeCommand();
	
											setIlluminationModeCommand.setElementTypeId(ElementType.ELEMENT_TYPE_TUBE);
											setIlluminationModeCommand.setElementId(elementHierarchie.getChildElementId());
											setIlluminationModeCommand.setMode(Long.parseLong(valueMode));
	
											if (commands == null) {
												commands = new ArrayList<>();
											}
											commands.add(setIlluminationModeCommand);
	
										} catch (NumberFormatException e) {
	
											log.error(e.getMessage());
											log.debug(ExceptionUtils.getStackTrace(e));
										}
									}
										
									if (valueRegime != null) {
	
										try {
											SetIlluminationRegimeCommand setIlluminationRegimeCommand = new SetIlluminationRegimeCommand();
	
											setIlluminationRegimeCommand.setElementTypeId(ElementType.ELEMENT_TYPE_TUBE);
											setIlluminationRegimeCommand.setElementId(elementHierarchie.getChildElementId());
											setIlluminationRegimeCommand.setRegime(Long.parseLong(valueRegime));
	
											if (commands == null) {
												commands = new ArrayList<>();
											}
											commands.add(setIlluminationRegimeCommand);
	
										} catch (NumberFormatException e) {

											log.error(e.getMessage());
											log.debug(ExceptionUtils.getStackTrace(e));
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
