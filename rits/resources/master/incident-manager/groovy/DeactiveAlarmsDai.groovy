
import java.time.Instant;
import java.util.List;

import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand.Action;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.ElementType;

class DeactiveAlarmsDai {
	
	GroovyShell shell;
	org.apache.logging.log4j.Logger log;
	
	static final Long DAI_ELEMENT_TYPE = 2L;
	
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
		
	DeactiveAlarmsDai(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		this.log = log;
	}
	
	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = new ArrayList<>();
		
		try {
			
			Set<Long> elements = EntitiesManager.getInstance()
			.getElementByStretch(ElementType.ELEMENT_TYPE_CAMERA, incidentReport.getAffectionStretchId());

			SetAlarmsFilteredCommand alarmsFilteredCommand = new SetAlarmsFilteredCommand();

			for (Long elementId : elements) {

				for (Long alarmConfigId : alarmConfigIdList) {

					AlarmFiltered alarmFiltered = new AlarmFiltered();
					alarmFiltered.setAlarmConfigId(alarmConfigId);
					alarmFiltered.setElementTypeId(DAI_ELEMENT_TYPE);
					alarmFiltered.setElementId(elementId);
					alarmFiltered.setAlarmDisabled(true);
					alarmFiltered.setFilteredUntil(1997689200000L);
					alarmFiltered.setCreatedAt(Instant.now().toEpochMilli());
					alarmFiltered.setComment("LBL_DEACTIVATE_DAI_ALARMS_BY_TASK : "+" Id: "+incidentReport.getId());
					alarmFiltered.setUserId(User.USER_SYSTEM);
					alarmsFilteredCommand.addAlarmFilteredAction(alarmFiltered, Action.FILTER_ON);
				}
			}

			commands.add(alarmsFilteredCommand);
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.error(ExceptionUtils.getStackTrace(e));
		}
		
		return commands;
	}
}