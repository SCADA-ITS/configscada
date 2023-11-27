
import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand.Action;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class ActiveAlarmsDaiByFireDet {

	GroovyShell shell;
	org.apache.logging.log4j.Logger log;

	static final Long DAI_ELEMENT_TYPE = 2L;
	static final Long ELEMENT_TYPE_FIRE_DET = 15L;

	// Alarms
	static final Long ALARM_VEH_WRONG_DIRECTION = 1301L;
	static final Long ALARM_OBJ_LEFT_ROADSIDE = 1302L;
	static final Long ALARM_OBJ_RIGHT_ROADSIDE = 1303L;
	static final Long ALARM_VEH_SLOW_LEFT_ROADSIDE = 1304L;
	static final Long ALARM_VEH_SLOW_RIGHT_ROADSIDE = 1305L;
	static final Long ALARM_OBJ_LEFT_RAIL = 1306L;
	static final Long ALARM_OBJ_RIGHT_RAIL = 1307L;
	static final Long ALARM_VEH_SLOW_LEFT_RAIL = 1308L;
	static final Long ALARM_VEH_SLOW_RIGHT_RAIL = 1309L;
	static final Long ALARM_PEDESTRIAN = 1310L;
	static final Long ALARM_VEH_STOP_LEFT_ROADSIDE = 1311L;
	static final Long ALARM_VEH_STOP_RIGHT_ROADSIDE = 1312L;
	static final Long ALARM_VEH_STOP_LEFT_RAIL = 1313L;
	static final Long ALARM_VEH_STOP_RIGHT_RAIL = 1314L;

	final List<Long> alarmConfigIdList = Collections.unmodifiableList(Arrays.asList(ALARM_VEH_WRONG_DIRECTION,
	ALARM_OBJ_LEFT_ROADSIDE, ALARM_OBJ_RIGHT_ROADSIDE, ALARM_VEH_SLOW_LEFT_ROADSIDE,
	ALARM_VEH_SLOW_RIGHT_ROADSIDE, ALARM_OBJ_LEFT_RAIL, ALARM_OBJ_RIGHT_RAIL, ALARM_VEH_SLOW_LEFT_RAIL,
	ALARM_VEH_SLOW_RIGHT_RAIL, ALARM_PEDESTRIAN, ALARM_VEH_STOP_LEFT_ROADSIDE, ALARM_VEH_STOP_RIGHT_ROADSIDE,
	ALARM_VEH_STOP_LEFT_RAIL, ALARM_VEH_STOP_RIGHT_RAIL));

	ActiveAlarmsDaiByFireDet(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null ) {

			Set<Long> fireDetIds = EntitiesManager.getInstance()
					.getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentTypeStretch.getStretchId());

			if (!CollectionUtils.isEmpty(fireDetIds)) {

				Long fireDetId = fireDetIds.iterator().next();
				log.info("ActiveAlarmsDaiByFireDet - fireDetId: " + fireDetId);

				List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE,
						ELEMENT_TYPE_FIRE_DET, fireDetId);

				if (!CollectionUtils.isEmpty(elementHierarchies)) {

					Long tubeId = elementHierarchies.get(0).getParentElementId();
					log.info("ActiveAlarmsDaiByFireDet - tubeId: " + tubeId);

					elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_ILLUMINATION,
							ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						elements = new ArrayList<>();

						Long illuminationId = elementHierarchies.get(0).getParentElementId();
						log.info("ActiveAlarmsDaiByFireDet - illuminationId: " + illuminationId);

						Element element = EntitiesManager.getInstance().getElement(ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId)

						if(element != null && element.getLocationId() != null) {
							// In Tube locationId == stretchId

							Set<Long> camerasIds = EntitiesManager.getInstance()
									.getElementByStretch(ElementType.ELEMENT_TYPE_CAMERA, element.getLocationId());

							for (Long elementId : camerasIds) {

								elements.add(new Element(ElementType.ELEMENT_TYPE_CAMERA, elementId));
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
		try {

			Set<Long> fireDetIds = EntitiesManager.getInstance()
					.getElementByStretch(ELEMENT_TYPE_FIRE_DET, incidentReport.getAffectionStretchId());

			if (!CollectionUtils.isEmpty(fireDetIds)) {

				Long fireDetId = fireDetIds.iterator().next();
				log.info("ActiveAlarmsDaiByFireDet - fireDetId: " + fireDetId);

				List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_TUBE,
						ELEMENT_TYPE_FIRE_DET, fireDetId);

				if (!CollectionUtils.isEmpty(elementHierarchies)) {

					Long tubeId = elementHierarchies.get(0).getParentElementId();
					log.info("ActiveAlarmsDaiByFireDet - tubeId: " + tubeId);

					elementHierarchies = EntitiesManager.getInstance().getsByChild(ElementType.ELEMENT_TYPE_ILLUMINATION,
							ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						Long illuminationId = elementHierarchies.get(0).getParentElementId();
						log.info("ActiveAlarmsDaiByFireDet - illuminationId: " + illuminationId);

						Element element = EntitiesManager.getInstance().getElement(ElementType.ELEMENT_TYPE_ILLUMINATION,  illuminationId)

						if(element != null && element.getLocationId() != null) {
							// In Tube locationId == stretchId

							Set<Long> elements = EntitiesManager.getInstance()
									.getElementByStretch(ElementType.ELEMENT_TYPE_CAMERA, element.getLocationId());

							SetAlarmsFilteredCommand alarmsFilteredCommand = new SetAlarmsFilteredCommand();

							for (Long elementId : elements) {

								for (Long alarmConfigId : alarmConfigIdList) {

									AlarmFiltered alarmFiltered = new AlarmFiltered();
									alarmFiltered.setAlarmConfigId(alarmConfigId);
									alarmFiltered.setElementTypeId(DAI_ELEMENT_TYPE);
									alarmFiltered.setElementId(elementId);

									alarmFiltered.setComment("LBL_ACTIVATE_DAI_ALARMS_BY_TASK : "+" Id: "+incidentReport.getId());
									alarmFiltered.setUserId(User.USER_SYSTEM);
									alarmsFilteredCommand.addAlarmFilteredAction(alarmFiltered, Action.FILTER_OFF);
								}
							}

							commands.add(alarmsFilteredCommand);
						}
					}
				}
			}
		} catch (Exception e) {

			log.error(e.getMessage());
			log.error(ExceptionUtils.getStackTrace(e));
		}

		return commands;

	}
}