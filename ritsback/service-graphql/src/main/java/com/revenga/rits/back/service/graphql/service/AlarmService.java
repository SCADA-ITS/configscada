package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAlarmsCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.front.core.model.helper.command.SetAlarmsCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetDeactivationAlarmsCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetRecognizeAlarmsCommandHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmVO;
import com.revenga.rits.back.service.graphql.type.AlarmGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AlarmService implements QueryInterface<AlarmVO> {

	private static final Long N_ENTITIES_TO_GET_ALL = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "service.alarm.n_entities_to_get_all"));

	@Deprecated
	public Boolean sendAlarms(List<String> alarmConfigs, List<String> targetElementIds, String creationComments,
			String userId) {

		// We check if the user param has the correct format and is not null
		if ((CollectionUtils.isEmpty(alarmConfigs) && !CollectionUtils.isEmpty(targetElementIds)) || userId == null) {

			return false;
		}

		// We check that the userId exist into the database
		Key key = new Key(userId);

		if (key.getClazz() == User.class) {

			User user = EntitiesManager.getInstance().getUserRepository().get(Long.valueOf(key.getId(1)), false);

			if (user == null) {

				return false;
			}
		} else {
			return false;
		}

		SetAlarmsCommand setAlarmsCommand = SetAlarmsCommandHelper.get(alarmConfigs, targetElementIds, creationComments,
				userId);

		if (setAlarmsCommand != null) {

			EntitiesManager.getInstance().sendCommand(setAlarmsCommand);

		} else {

			return false;
		}

		return true;
	}

	public Boolean sendRecognizeAlarms(List<String> alarmIds, String recognizedComments, String userId,
			Boolean disagree) {

		if (CollectionUtils.isEmpty(alarmIds)) {

			return false;
		}

		// We check that the userId exist into the database
		if (userId != null && !userId.isEmpty() && userId.split(":").length == 2) {

			Key key = new Key(userId);

			if (key.getClazz() == User.class) {

				User user = EntitiesManager.getInstance().getUserRepository().get(Long.valueOf(key.getId(1)), false);

				if (user == null) {

					userId = "";
				}
			}
		}

		SetRecognizeAlarmsCommand setRecognizeAlarmsCommand = SetRecognizeAlarmsCommandHelper.get(alarmIds,
				recognizedComments, userId, disagree);

		if (setRecognizeAlarmsCommand != null) {

			EntitiesManager.getInstance().sendCommand(setRecognizeAlarmsCommand);

		} else {

			return false;
		}

		return true;
	}

	public Boolean sendDeactivationAlarms(List<String> alarmIds, String deactivationComments, String userId) {

		if (CollectionUtils.isEmpty(alarmIds)) {

			return false;
		}

		// We check that the userId exist into the database
		if (userId != null && !userId.isEmpty() && userId.split(":").length == 2) {

			Key key = new Key(userId);

			if (key.getClazz() == User.class) {

				User user = EntitiesManager.getInstance().getUserRepository().get(Long.valueOf(key.getId(1)), false);

				if (user == null) {

					userId = "";
				}
			}
		}

		SetDeactivationAlarmsCommand setDeactivationAlarmsCommand = SetDeactivationAlarmsCommandHelper.get(alarmIds,
				deactivationComments, userId);

		if (setDeactivationAlarmsCommand != null) {

			EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommand);

		} else {

			return false;
		}

		return true;
	}


	@Override
	public Optional<AlarmVO> get(String alarmId) {

		Optional<AlarmVO> alarmVO = null;
		try {
			Key key = new Key(alarmId);

			if (key.getClazz() == Alarm.class) {

				Alarm alarm = EntitiesManager.getInstance().getAlarmRepository().get(Long.valueOf(key.getId(1)), false);

				// Filter if elements of the alarm are Visible or not.

				if (alarm != null) {

					Element sourceElement = EntitiesManager.getInstance().getElementRepository()
							.get(alarm.getSourceElementTypeId(), alarm.getSourceElementId(), false);

					Element targetElement = null;

					if (sourceElement != null && sourceElement.getElementTypeId().equals(alarm.getTargetElementTypeId())
							&& sourceElement.getId().equals(alarm.getTargetElementId())) {

						targetElement = sourceElement;

					} else {

						targetElement = EntitiesManager.getInstance().getElementRepository()
								.get(alarm.getTargetElementTypeId(), alarm.getTargetElementId(), false);
					}

					if (sourceElement != null && targetElement != null
							&& (sourceElement.getVisible() == null || sourceElement.getVisible().booleanValue())
							&& (targetElement.getVisible() == null || targetElement.getVisible().booleanValue())) {

						alarmVO = Optional.of(new AlarmGraphql(alarm));

					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Alarm");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmVO;
	}

	public List<AlarmVO> getAll(String alarmTypeId, Long managementAreaId) {

		List<AlarmVO> alarmsVO = null;
		List<Alarm> alarms = null;

		try {

			Key keyAlarmType = new Key(alarmTypeId);

			if (alarmTypeId != null) {

				if (keyAlarmType.getClazz() == AlarmType.class) {

					alarms = EntitiesManager.getInstance().getAlarmRepository()
							.getAll(Long.valueOf(keyAlarmType.getId(1)), false);
				}

			} else {

				alarms = EntitiesManager.getInstance().getAlarmRepository().getAll(false);
			}

			if (!CollectionUtils.isEmpty(alarms)) {

				alarmsVO = new ArrayList<>();

				for (Alarm alarm : alarms) {

					// Filter if elements of the alarm are Visible or not.
					Element sourceElement = EntitiesManager.getInstance().getElementRepository()
							.get(alarm.getSourceElementTypeId(), alarm.getSourceElementId(), false);

					Element targetElement = null;

					if (sourceElement != null && sourceElement.getElementTypeId().equals(alarm.getTargetElementTypeId())
							&& sourceElement.getId().equals(alarm.getTargetElementId())) {

						targetElement = sourceElement;

					} else {

						targetElement = EntitiesManager.getInstance().getElementRepository()
								.get(alarm.getTargetElementTypeId(), alarm.getTargetElementId(), false);
					}

					if (alarm != null && sourceElement != null && targetElement != null
							&& (sourceElement.getVisible() == null || sourceElement.getVisible().booleanValue())
							&& (targetElement.getVisible() == null || targetElement.getVisible().booleanValue())
							&& checkManagementArea(targetElement, sourceElement, managementAreaId)) {

						alarmsVO.add(new AlarmGraphql(alarm));
					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Alarm");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmsVO;
	}

	public List<AlarmVO> getBySource(String sourceElementId, String alarmTypeId) {

		List<AlarmVO> alarmsVO = null;
		List<Alarm> alarms = null;

		try {
			Key keyElement = new Key(sourceElementId);
			Key keyAlarmType = new Key(alarmTypeId);

			if (keyElement.getClazz() == Element.class) {

				if (alarmTypeId != null) {

					if (keyAlarmType.getClazz() == AlarmType.class) {

						alarms = EntitiesManager.getInstance().getAlarmRepository().getsBySourceElement(
								Long.valueOf(keyElement.getId(1)), Long.valueOf(keyElement.getId(2)),
								Long.valueOf(keyAlarmType.getId(1)), false);
					}

				} else {

					alarms = EntitiesManager.getInstance().getAlarmRepository().getsBySourceElement(
							Long.valueOf(keyElement.getId(1)), Long.valueOf(keyElement.getId(2)), false);
				}

				if (!CollectionUtils.isEmpty(alarms)) {

					alarmsVO = new ArrayList<>();

					for (Alarm alarm : alarms) {

						// Filter if elements of the alarm are Visible or not.
						Element sourceElement = EntitiesManager.getInstance().getElementRepository()
								.get(alarm.getSourceElementTypeId(), alarm.getSourceElementId(), false);

						Element targetElement = null;

						if (sourceElement != null
								&& sourceElement.getElementTypeId().equals(alarm.getTargetElementTypeId())
								&& sourceElement.getId().equals(alarm.getTargetElementId())) {

							targetElement = sourceElement;

						} else {

							targetElement = EntitiesManager.getInstance().getElementRepository()
									.get(alarm.getTargetElementTypeId(), alarm.getTargetElementId(), false);
						}

						if (alarm != null && sourceElement != null && targetElement != null
								&& (sourceElement.getVisible() == null || sourceElement.getVisible().booleanValue())
								&& (targetElement.getVisible() == null || targetElement.getVisible().booleanValue())) {

							alarmsVO.add(new AlarmGraphql(alarm));
						}
					}
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Alarm");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmsVO;
	}

	public List<AlarmVO> getByTarget(String targetElementId, String alarmTypeId) {

		List<AlarmVO> alarmsVO = null;
		List<Alarm> alarms = null;

		try {
			Key keyElement = new Key(targetElementId);
			Key keyAlarmType = new Key(alarmTypeId);

			if (keyElement.getClazz() == Element.class) {

				if (alarmTypeId != null) {

					if (keyAlarmType.getClazz() == AlarmType.class) {

						alarms = EntitiesManager.getInstance().getAlarmRepository().getsByTargetElement(
								Long.valueOf(keyElement.getId(1)), Long.valueOf(keyElement.getId(2)),
								Long.valueOf(keyAlarmType.getId(1)), false);
					}

				} else {

					alarms = EntitiesManager.getInstance().getAlarmRepository().getsByTargetElement(
							Long.valueOf(keyElement.getId(1)), Long.valueOf(keyElement.getId(2)), false);

				}

				if (!CollectionUtils.isEmpty(alarms)) {

					alarmsVO = new ArrayList<>();

					for (Alarm alarm : alarms) {

						// Filter if elements of the alarm are Visible or not.
						Element sourceElement = EntitiesManager.getInstance().getElementRepository()
								.get(alarm.getSourceElementTypeId(), alarm.getSourceElementId(), false);

						Element targetElement = null;

						if (sourceElement != null
								&& sourceElement.getElementTypeId().equals(alarm.getTargetElementTypeId())
								&& sourceElement.getId().equals(alarm.getTargetElementId())) {

							targetElement = sourceElement;

						} else {

							targetElement = EntitiesManager.getInstance().getElementRepository()
									.get(alarm.getTargetElementTypeId(), alarm.getTargetElementId(), false);
						}

						if (alarm != null && sourceElement != null && targetElement != null
								&& (sourceElement.getVisible() == null || sourceElement.getVisible().booleanValue())
								&& (targetElement.getVisible() == null || targetElement.getVisible().booleanValue())) {

							alarmsVO.add(new AlarmGraphql(alarm));
						}
					}
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Alarm");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmsVO;
	}

	public List<AlarmVO> getByAlarmConfig(String alarmConfigId, Long managementAreaId) {

		List<AlarmVO> alarmsVO = null;

		try {
			Key key = new Key(alarmConfigId);

			if (key.getClazz() == AlarmConfig.class) {

				List<Alarm> alarms = EntitiesManager.getInstance().getAlarmRepository()
						.getsByAlarmConfig(Long.valueOf(key.getId(1)), false);

				if (!CollectionUtils.isEmpty(alarms)) {

					alarmsVO = new ArrayList<>();

					for (Alarm alarm : alarms) {

						// Filter if elements of the alarm are Visible or not.
						Element sourceElement = EntitiesManager.getInstance().getElementRepository()
								.get(alarm.getSourceElementTypeId(), alarm.getSourceElementId(), false);

						Element targetElement = null;

						if (sourceElement != null
								&& sourceElement.getElementTypeId().equals(alarm.getTargetElementTypeId())
								&& sourceElement.getId().equals(alarm.getTargetElementId())) {

							targetElement = sourceElement;

						} else {

							targetElement = EntitiesManager.getInstance().getElementRepository()
									.get(alarm.getTargetElementTypeId(), alarm.getTargetElementId(), false);
						}

						if (alarm != null && sourceElement != null && targetElement != null
								&& (sourceElement.getVisible() == null || sourceElement.getVisible().booleanValue())
								&& (targetElement.getVisible() == null || targetElement.getVisible().booleanValue())
								&& checkManagementArea(targetElement, sourceElement, managementAreaId)) {

							alarmsVO.add(new AlarmGraphql(alarm));
						}
					}
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Alarm");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmsVO;
	}

	/**
	 * Recibirá una lista de ids de alarmas a consultar, en caso de que la sita sea
	 * vacía, retornara todas las alarmas.
	 */

	public List<AlarmVO> gets(List<String> alarmIds, Long managementAreaId) {
		List<AlarmVO> alarmsVO = new ArrayList<>();

		if (CollectionUtils.isEmpty(alarmIds)) {

			List<Alarm> alarms = EntitiesManager.getInstance().getAlarmRepository().getAll(false);

			if (!CollectionUtils.isEmpty(alarms)) {

				for (Alarm alarm : alarms) {

					// Filter if elements of the alarm are Visible or not.
					Element sourceElement = EntitiesManager.getInstance().getElementRepository()
							.get(alarm.getSourceElementTypeId(), alarm.getSourceElementId(), false);

					Element targetElement = null;

					if (sourceElement != null && sourceElement.getElementTypeId().equals(alarm.getTargetElementTypeId())
							&& sourceElement.getId().equals(alarm.getTargetElementId())) {

						targetElement = sourceElement;

					} else {

						targetElement = EntitiesManager.getInstance().getElementRepository()
								.get(alarm.getTargetElementTypeId(), alarm.getTargetElementId(), false);
					}

					if (alarm != null && sourceElement != null && targetElement != null
							&& (sourceElement.getVisible() == null || sourceElement.getVisible().booleanValue())
							&& (targetElement.getVisible() == null || targetElement.getVisible().booleanValue())
							&& checkManagementArea(targetElement, sourceElement, managementAreaId)) {

						alarmsVO.add(new AlarmGraphql(alarm));

					}
				}
			}
		} else {

			List<Alarm> alarms = null;

			if (alarmIds.size() >= N_ENTITIES_TO_GET_ALL) {

				alarms = EntitiesManager.getInstance().getAlarmRepository().getAll(false);
			}

			for (String alarmId : alarmIds) {

				try {

					Key key = new Key(alarmId);

					if (key.getClazz() == Alarm.class) {

						Alarm alarm = null;

						if (alarms != null) {

							alarm = alarms.stream().filter(j -> j.getId().equals(Long.valueOf(key.getId(1))))
									.findFirst().orElse(null);

						} else {
							alarm = EntitiesManager.getInstance().getAlarmRepository().get(Long.valueOf(key.getId(1)),
									false);
						}

						// Filter if elements of the alarm are Visible or not.

						if (alarm != null) {

							Element sourceElement = EntitiesManager.getInstance().getElementRepository()
									.get(alarm.getSourceElementTypeId(), alarm.getSourceElementId(), false);

							Element targetElement = null;

							if (sourceElement != null
									&& sourceElement.getElementTypeId().equals(alarm.getTargetElementTypeId())
									&& sourceElement.getId().equals(alarm.getTargetElementId())) {

								targetElement = sourceElement;

							} else {

								targetElement = EntitiesManager.getInstance().getElementRepository()
										.get(alarm.getTargetElementTypeId(), alarm.getTargetElementId(), false);
							}

							if (alarm != null && sourceElement != null && targetElement != null
									&& (sourceElement.getVisible() == null || sourceElement.getVisible().booleanValue())
									&& (targetElement.getVisible() == null || targetElement.getVisible().booleanValue())
									&& checkManagementArea(targetElement, sourceElement, managementAreaId)) {

								alarmsVO.add(new AlarmGraphql(alarm));

							}
						}
					}

				} catch (Exception e) {

					log.error("The id does not correspond to the required object: Alarm");
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		return alarmsVO;
	}

	public List<AlarmVO> getHistoricalAlarms(List<String> elementIds, Long initDate, Long finalDate) {

		List<AlarmVO> alarmsVO = new ArrayList<>();

		List<Alarm> alarms = null;

		if (CollectionUtils.isNotEmpty(elementIds)) {

			alarms = EntitiesManager.getInstance().getAlarmRepository().getHistoricalData(elementIds, initDate,
					finalDate);
		}

		if (CollectionUtils.isNotEmpty(alarms)) {

			for (Alarm alarm : alarms) {

				if (alarm != null) {

					alarmsVO.add(new AlarmGraphql(alarm));
				}
			}
		}

		return alarmsVO;
	}

	@Override
	public List<AlarmVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean checkManagementArea(final Element targetElement, final Element sourceElement,
			final Long managementAreaId) {

		boolean resp = false;

		List<ManagementAreaElement> managementAreaElements = EntitiesManager.getInstance()
				.getManagementAreaElementRepository()
				.gets(targetElement.getElementTypeId(), targetElement.getId(), false);

		if (CollectionUtils.isEmpty(managementAreaElements) || (managementAreaId != null
				&& managementAreaElements.stream().filter(e -> e.getId().equals(managementAreaId)).count() > 0)) {

			if (targetElement.getElementTypeId().equals(sourceElement.getElementTypeId())
					&& targetElement.getId().equals(sourceElement.getId())) {

				resp = true;

			} else {

				managementAreaElements = EntitiesManager.getInstance().getManagementAreaElementRepository()
						.gets(sourceElement.getElementTypeId(), sourceElement.getId(), false);

				if (CollectionUtils.isEmpty(managementAreaElements)
						|| (managementAreaId != null && managementAreaElements.stream()
								.filter(e -> e.getId().equals(managementAreaId)).count() > 0)) {

					resp = true;
				}
			}

		}

		return resp;
	}

	@Override
	public List<AlarmVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
