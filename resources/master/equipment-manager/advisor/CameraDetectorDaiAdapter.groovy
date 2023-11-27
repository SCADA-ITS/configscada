import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.equipment.manager.helper.GroovyHelper;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;

/**
 *
 * CameraDetectorDaiAdapter: update alarm of camera Detector DAI
 *
 */

class CameraDetectorDaiAdapter {

	static final Long PARAM_TYPE_MEASURE = 2L;

	// Element type params
	static final Long PARAM_ID_INCIDENT = 3L;
	static final Long PARAM_MEASURE_PLACE = 4L;
	static final Long PARAM_MEASURE_TYPE_INCIDENT = 5L;

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

	// DAI Type incident
	static final int DAI_VEH_WRONG_DIRECTION = 1;
	static final int DAI_VEH_SLOW = 2;
	static final int DAI_PEDESTRIAN = 3;
	static final int DAI_OBJECT = 4;
	static final int DAI_LOW_VISIBILITY = 5;
	static final int DAI_VEH_STOP = 6;

	// DAI Place
	static final int DAI_LEFT_ROADSIDE = 1;
	static final int DAI_LEFT_RAIL = 2;
	static final int DAI_RIGHT_RAIL = 3;
	static final int DAI_RIGHT_ROADSIDE = 4;

	final List<Long> alarmConfigIdList = Collections.unmodifiableList(Arrays.asList(ALARM_VEH_WRONG_DIRECTION,
			ALARM_OBJ_LEFT_ROADSIDE, ALARM_OBJ_RIGHT_ROADSIDE, ALARM_VEH_SLOW_LEFT_ROADSIDE,
			ALARM_VEH_SLOW_RIGHT_ROADSIDE, ALARM_OBJ_LEFT_RAIL, ALARM_OBJ_RIGHT_RAIL, ALARM_VEH_SLOW_LEFT_RAIL,
			ALARM_VEH_SLOW_RIGHT_RAIL, ALARM_PEDESTRIAN, ALARM_VEH_STOP_LEFT_ROADSIDE, ALARM_VEH_STOP_RIGHT_ROADSIDE,
			ALARM_VEH_STOP_LEFT_RAIL, ALARM_VEH_STOP_RIGHT_RAIL));

	org.apache.logging.log4j.Logger log;

	CameraDetectorDaiAdapter(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean updateAlarms(List<Pair<ElementValue, ElementValue>> elementValues) {

		boolean updateAlarms = false;

		if (!CollectionUtils.isEmpty(elementValues)) {

			for (int i = 0; i < elementValues.size() && !updateAlarms; i++) {

				ElementValue elementValue = elementValues.get(i).getRight();

				if (elementValue.getElementTypeParamId() != null
						&& ( elementValue.getElementTypeParamId().equals(PARAM_MEASURE_PLACE) ||
							elementValue.getElementTypeParamId().equals(PARAM_MEASURE_TYPE_INCIDENT)) ) {

					updateAlarms = true;
				}
			}
		}

		return updateAlarms;
	}

	void deactivationOldAlarms(Element element) {

		SetDeactivationAlarmsCommand setDeactivationAlarmsCommand = null;

		if (!CollectionUtils.isEmpty(element.getAlarms())) {

			for (Alarm alarm : element.getAlarms()) {

				if (alarmConfigIdList.contains(alarm.getAlarmConfigId()) && alarm.getDeactivation() == null) {

					if (setDeactivationAlarmsCommand == null) {

						setDeactivationAlarmsCommand = new SetDeactivationAlarmsCommand();
					}
					setDeactivationAlarmsCommand.add(element, alarm.getAlarmConfig());
				}
			}
		}

		if (setDeactivationAlarmsCommand != null) {

			GroovyHelper.deactivateAlarms(setDeactivationAlarmsCommand);
		}
	}

	void activationNewAlarms(Element element) {

		int incidentType = 0;
		int place = 0;

		if (!CollectionUtils.isEmpty(element.getElementValues())) {

			for (ElementValue elementValue : element.getElementValues()) {

				if (elementValue.getParamTypeId() != null && elementValue.getElementTypeParamId() != null
						&& elementValue.getParamTypeId().equals(PARAM_TYPE_MEASURE) && elementValue.getValue() != null
						&& !elementValue.getValue().equals("null")) {

					if (elementValue.getElementTypeParamId().equals(PARAM_MEASURE_TYPE_INCIDENT)) {

						incidentType = Integer.parseInt(elementValue.getValue());

					} else if (elementValue.getElementTypeParamId().equals(PARAM_MEASURE_PLACE)) {

						place = Integer.parseInt(elementValue.getValue());
					}
				}

			}

		}

		if (incidentType != 0 && place != 0) {

			AlarmConfig alarmConfig = new AlarmConfig();

			if (incidentType == DAI_VEH_WRONG_DIRECTION) {

				alarmConfig.setId(ALARM_VEH_WRONG_DIRECTION);

			} else if (incidentType == DAI_VEH_SLOW) {

				if (place == DAI_LEFT_ROADSIDE) {

					alarmConfig.setId(ALARM_VEH_SLOW_LEFT_ROADSIDE);

				} else if (place == DAI_RIGHT_ROADSIDE) {

					alarmConfig.setId(ALARM_VEH_SLOW_RIGHT_ROADSIDE);

				} else if (place == DAI_LEFT_RAIL) {

					alarmConfig.setId(ALARM_VEH_SLOW_LEFT_RAIL);

				} else if (place == DAI_RIGHT_RAIL) {

					alarmConfig.setId(ALARM_VEH_SLOW_RIGHT_RAIL);
				}

			} else if (incidentType == DAI_OBJECT) {

				if (place == DAI_LEFT_ROADSIDE) {

					alarmConfig.setId(ALARM_OBJ_LEFT_ROADSIDE);

				} else if (place == DAI_RIGHT_ROADSIDE) {

					alarmConfig.setId(ALARM_OBJ_RIGHT_ROADSIDE);

				} else if (place == DAI_LEFT_RAIL) {

					alarmConfig.setId(ALARM_OBJ_LEFT_RAIL);

				} else if (place == DAI_RIGHT_RAIL) {

					alarmConfig.setId(ALARM_OBJ_RIGHT_RAIL);
				}

			} else if (incidentType == DAI_PEDESTRIAN) {

				alarmConfig.setId(ALARM_PEDESTRIAN);

			} else if (incidentType == DAI_VEH_STOP) {

				if (place == DAI_LEFT_ROADSIDE) {

					alarmConfig.setId(ALARM_VEH_STOP_LEFT_ROADSIDE);

				} else if (place == DAI_RIGHT_ROADSIDE) {

					alarmConfig.setId(ALARM_VEH_STOP_RIGHT_ROADSIDE);

				} else if (place == DAI_LEFT_RAIL) {

					alarmConfig.setId(ALARM_VEH_STOP_LEFT_RAIL);

				} else if (place == DAI_RIGHT_RAIL) {

					alarmConfig.setId(ALARM_VEH_STOP_RIGHT_RAIL);
				}
			}

			if (alarmConfig.getId() != null) {

				SetActivationAlarmsCommand setActivationAlarmsCommand = new SetActivationAlarmsCommand();
				setActivationAlarmsCommand.add(element, alarmConfig);
				GroovyHelper.activateAlarms(setActivationAlarmsCommand);
			}

		}
	}

	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {

		try {

			if (updateAlarms(elementValues)) {

				deactivationOldAlarms(element);

				activationNewAlarms(element);

			}
			return true;

		} catch (Exception e) {

			log.error(e.getMessage());
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return false;
	}

}