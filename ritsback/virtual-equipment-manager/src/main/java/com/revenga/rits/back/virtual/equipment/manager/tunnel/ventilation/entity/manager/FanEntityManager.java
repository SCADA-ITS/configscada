package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.Fan;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor.FansProcessor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FanEntityManager {

	private static final String FILE_CONSTANTS = "constants.properties";
	private static final String APPLICATION_PROPERTIES = "application.properties";

	public static final long ELEMENT_TYPE_PARAM_DELAY_BEFORE_START = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.fan.param.delay_before_start"));

	public static final long ELEMENT_TYPE_PARAM_TOTAL_OPERATING_HOURS = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.fan.param.total_operating_hours"));

	public static final long ELEMENT_TYPE_PARAM_FAN_STATE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.fan.param.fan_state"));

	public static final long FAN_STATE_VALUE_STOP = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.value.fan_state.stop"));

	public static final long FAN_STATE_VALUE_START = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.value.fan_state.start"));

	public static final long FAN_STATE_VALUE_REVERSE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.value.fan_state.reverse"));

	public static final long DEFAULT_DELAY_BEFORE_START = Long.parseLong(
			ConfigProperty.getInstance().getProperty(APPLICATION_PROPERTIES, "fan.default.delay_before_start"));

	public static final long COUNT_FAN_OPERATING_HOURS_TASK_PERIOD = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(APPLICATION_PROPERTIES, "fan.count_fan_operating_hours_task.period"));

	public static final long WAITING_TIME_FOR_CONFIRMATION = Long.parseLong(
			ConfigProperty.getInstance().getProperty(APPLICATION_PROPERTIES, "fan.waiting_time_for_confirmation"));

	public static final long MS_IN_HOURS = 3600000;

	public static final List<Long> FAN_ALARM_CONFIGS = Arrays
			.asList(ClassesUtil.getStringArray(
					ConfigProperty.getInstance().getProperty(APPLICATION_PROPERTIES, "fan.alarm_config")))
			.stream().map(Long::parseLong).collect(Collectors.toList());

	public static final List<Long> FAN_NO_CONENCTION_ALARM_CONFIG = Arrays
			.asList(ClassesUtil.getStringArray(
					ConfigProperty.getInstance().getProperty(APPLICATION_PROPERTIES, "fan.no_connection_alarm_config")))
			.stream().map(Long::parseLong).collect(Collectors.toList());

	public static final long ALARM_NOT_ARRIVED = Long.parseLong(
			ConfigProperty.getInstance().getProperty(APPLICATION_PROPERTIES, "fan.alarm_signalling_not_arrived"));

	// Map <electricalPanelId, Map<fanId, fan>>
	private Map<Long, Map<Long, Fan>> fansInElectricalPanel;

	// Map <tubeId, List<electricalPanelId, fanId>>
	private Map<Long, List<Pair<Long, Long>>> fansInTubes;

	// Map <ventilationId, Map<fanId, fan>>
	private Map<Long, Map<Long, Fan>> fansInVentilations;

	// Map<fanId, ventilationId>>
	@Getter
	private Map<Long, Long> ventilationByFans;

	// Map <fanId, mutex>
	private Map<Long, Semaphore> fansMutex;

	// Set <ventilatioId, fanId>
	private Set<Pair<Long, Long>> fansWaitConfirmation;

	public FanEntityManager(final List<Element> elements, final List<Alarm> alarms) {

		load(elements, alarms);
	}

	// Get started fans in a electrical panel system, confirmed or not confirmed
	public Long getStartedFanByElectricalPanel(Long electricalPanelId) {

		Long count = null;

		if (electricalPanelId != null && fansInElectricalPanel.containsKey(electricalPanelId)) {

			count = fansInElectricalPanel.get(electricalPanelId).entrySet().stream()
					.filter(e -> isFanState(e.getKey(), electricalPanelId, FAN_STATE_VALUE_START)
							|| isFanState(e.getKey(), electricalPanelId, FAN_STATE_VALUE_REVERSE))
					.count();
		}

		return count;
	}

	// Get started fans in a electrical panel system, take fanState and last
	// fanState
	public Long getStartedFanByElectricalPanelMax(Long electricalPanelId) {

		Long count = null;

		if (electricalPanelId != null && fansInElectricalPanel.containsKey(electricalPanelId)) {

			count = fansInElectricalPanel.get(electricalPanelId).entrySet().stream()
					.filter(e -> (e.getValue().getFanState() != null
							&& !e.getValue().getFanState().equals(FAN_STATE_VALUE_STOP))
							|| (e.getValue().getLastFanState() != null
									&& !e.getValue().getLastFanState().equals(FAN_STATE_VALUE_STOP)))
					.count();
		}

		return count;
	}

	// Get started fans in a tube, only if the state is confirmed. If the fan is not
	// confirmed, take lastFanState
	public Long getCurrentStartedFanByTube(Long tubeId) {

		Long count = null;

		if (tubeId != null) {

			List<Pair<Long, Long>> fans = fansInTubes.get(tubeId);

			if (CollectionUtils.isNotEmpty(fans)) {

				count = 0L;

				for (Pair<Long, Long> pair : fans) {

					Long electricalPanelId = pair.getKey();
					Long fanId = pair.getValue();

					if (electricalPanelId != null && fanId != null
							&& fansInElectricalPanel.containsKey(electricalPanelId)) {

						Fan fan = fansInElectricalPanel.get(electricalPanelId).get(fanId);

						if (fan != null && acquire(fanId)) {

							Long fanState = null;

							fanState = isFanConfirmed(electricalPanelId, fanId) ? fan.getFanState()
									: fan.getLastFanState();

							if (fanState != null && !fanState.equals(FAN_STATE_VALUE_STOP)) {

								count++;
							}

							release(fanId);
						}
					}
				}
			}
		}

		return count;
	}

	// // Get started fans in a tube, take fanState
	public Long getStartedFanByTube(Long tubeId) {

		Long count = null;

		if (tubeId != null) {

			List<Pair<Long, Long>> fans = fansInTubes.get(tubeId);

			if (CollectionUtils.isNotEmpty(fans)) {

				count = 0L;

				for (Pair<Long, Long> pair : fans) {

					Long electricalPanelId = pair.getKey();
					Long fanId = pair.getValue();

					if (electricalPanelId != null && fanId != null
							&& fansInElectricalPanel.containsKey(electricalPanelId)) {

						Fan fan = fansInElectricalPanel.get(electricalPanelId).get(fanId);

						if (fan != null && acquire(fanId)) {

							if (fan.getFanState() != null && !fan.getFanState().equals(FAN_STATE_VALUE_STOP)) {

								count++;
							}
						}

						release(fanId);
					}
				}
			}
		}
		return count;
	}

	// Get started fans in a electrical panel system, confirmed or not confirmed
	public Long getStartedFanByVentilation(Long ventilationId) {

		Long count = null;

		if (ventilationId != null && fansInVentilations.containsKey(ventilationId)) {

			count = fansInVentilations.get(ventilationId).entrySet().stream()
					.filter(e -> isFanState(e.getKey(), null, FAN_STATE_VALUE_START)
							|| isFanState(e.getKey(), null, FAN_STATE_VALUE_REVERSE))
					.count();
		}

		return count;
	}

	public List<Element> updateOperatingHours(Long timer) {

		List<Element> elements = null;

		if (timer != null && fansInElectricalPanel != null && !fansInElectricalPanel.isEmpty()) {

			for (Entry<Long, Map<Long, Fan>> entry : fansInElectricalPanel.entrySet()) {

				Long electricalPanelId = entry.getKey();
				if (entry.getValue() != null && !entry.getValue().isEmpty()) {

					for (Entry<Long, Fan> entryFan : entry.getValue().entrySet()) {

						Long fanId = entryFan.getKey();

						if (fanId != null && acquire(fanId)) {

							Fan fan = entryFan.getValue();
							Long fanState = null;

							if (isFanConfirmed(electricalPanelId, fanId)) {

								fanState = fan.getFanState();

							} else {

								fanState = fan.getLastFanState();
							}

							if (fanState != null && (fanState.equals(FAN_STATE_VALUE_START)

									|| fanState.equals(FAN_STATE_VALUE_REVERSE))) {

								fan.setOperatingOffset(fan.getOperatingOffset() + timer);

								long periods = fan.getOperatingOffset() / MS_IN_HOURS;

								if (periods > 0) {

									fan.setOperatingOffset(fan.getOperatingOffset() % MS_IN_HOURS);
									fan.setTotalOperatingHours(fan.getTotalOperatingHours() + periods);

									Element element = new Element(ElementType.ELEMENT_TYPE_FAN, fanId);

									ElementValue elementValue = new ElementValue();
									elementValue.setElementId(fanId);
									elementValue.setElementTypeId(ElementType.ELEMENT_TYPE_FAN);
									elementValue.setParamTypeId(ParamType.MEASURE);
									elementValue.setElementTypeParamId(ELEMENT_TYPE_PARAM_TOTAL_OPERATING_HOURS);
									elementValue.setValue(fan.getTotalOperatingHours().toString());

									List<ElementValue> elementValues = new ArrayList<>();
									elementValues.add(elementValue);

									element.setElementValues(elementValues);

									if (elements == null) {

										elements = new ArrayList<>();
									}
									log.info("To update operational hours(" + fan.getTotalOperatingHours().toString()
											+ ") " + "in fan " + fanId);
									elements.add(element);
								}

							}
							release(fanId);
						}
					}
				}
			}
		}

		return elements;
	}

	public Element updateOperatingHours(Long timer, Element targetFan) {

		Element element = null;

		if (timer != null && targetFan != null && fansInElectricalPanel != null && !fansInElectricalPanel.isEmpty()) {

			for (Entry<Long, Map<Long, Fan>> entry : fansInElectricalPanel.entrySet()) {

				if (entry.getValue() != null && !entry.getValue().isEmpty()) {

					for (Entry<Long, Fan> entryFan : entry.getValue().entrySet()) {

						Long targetFanId = targetFan.getId();
						Long fanId = entryFan.getKey();
						Fan fan = entryFan.getValue();

						if (fanId != null && targetFanId.equals(fanId) && acquire(fanId)) {

							fan.setOperatingOffset(timer);
							fan.setTotalOperatingHours(timer);

							element = new Element(ElementType.ELEMENT_TYPE_FAN, fanId);

							ElementValue elementValue = new ElementValue();
							elementValue.setElementId(fanId);
							elementValue.setElementTypeId(ElementType.ELEMENT_TYPE_FAN);
							elementValue.setParamTypeId(ParamType.MEASURE);
							elementValue.setElementTypeParamId(ELEMENT_TYPE_PARAM_TOTAL_OPERATING_HOURS);
							elementValue.setValue(fan.getTotalOperatingHours().toString());

							List<ElementValue> elementValues = new ArrayList<>();
							elementValues.add(elementValue);

							element.setElementValues(elementValues);

							log.info("To update operational hours(" + fan.getTotalOperatingHours().toString() + ") "
									+ "in fan " + fanId);

							release(fanId);

							return element;
						}

					}

				}
			}
		}

		return element;

	}

	public List<Pair<Long, Long>> getAlarmedFans() {

		List<Pair<Long, Long>> alarmedFans = null;

		if (fansInElectricalPanel != null) {

			for (Entry<Long, Map<Long, Fan>> entry : fansInElectricalPanel.entrySet()) {

				if (entry.getValue() != null) {

					for (Entry<Long, Fan> entry2 : entry.getValue().entrySet()) {

						Fan fan = entry2.getValue();

						if (fan != null && !fan.getAlarmConfigIds().isEmpty()) {

							Long fanId = entry2.getKey();
							Long electricalPanelId = entry.getKey();

							Pair<Long, Long> pair = new MutablePair<>(electricalPanelId, fanId);

							if (alarmedFans == null) {

								alarmedFans = new ArrayList<>();
							}
							alarmedFans.add(pair);
						}
					}
				}
			}
		}

		return alarmedFans;
	}

	public void checkNotConfirmedFans() {

		if (fansWaitConfirmation != null) {

			List<Pair<Long, Long>> fansToRemove = new ArrayList<>();

			List<Element> elementsToAlarm = null;

			synchronized (fansWaitConfirmation) {

				Long now = Instant.now().toEpochMilli();

				for (Pair<Long, Long> pair : fansWaitConfirmation) {

					Long electricalPanelId = pair.getLeft();
					Long fanId = pair.getRight();

					if (fanId != null && electricalPanelId != null && acquire(fanId)) {

						Map<Long, Fan> mapFan = fansInElectricalPanel.get(electricalPanelId);

						if (mapFan != null) {

							Fan fan = mapFan.get(fanId);

							if (fan == null || fan.getLastUpdateFanState() == null
									|| now - fan.getLastUpdateFanState() > WAITING_TIME_FOR_CONFIRMATION) {

								fansToRemove.add(new MutablePair<>(electricalPanelId, fanId));

								if (fan != null) {

									log.warn("The confirmation has not arrived for fan: " + fanId + ",  fan state: "
											+ fan.getFanState() + ", last fan state: " + fan.getLastFanState()
											+ " update value form the cache");

									if (!fan.getAlarmConfigIds().contains(ALARM_NOT_ARRIVED)) {

										if (elementsToAlarm == null) {

											elementsToAlarm = new ArrayList<>();
										}

										elementsToAlarm.add(new Element(ElementType.ELEMENT_TYPE_FAN, fanId));
									}

									updateFanState(fanId, electricalPanelId, fan);

									List<Long> tubeIds = TubeEntityManager.getInstance()
											.getTubesByChild(ElementType.ELEMENT_TYPE_FAN, fanId);

									for (Long tubeId : tubeIds) {

										if (tubeId != null) {

											TubeEntityManager.getInstance().checkVentilationRegime(
													new Element(ElementType.ELEMENT_TYPE_TUBE, tubeId));
										}
									}
								}
							}
						} else {

							fansToRemove.add(new MutablePair<>(electricalPanelId, fanId));
						}

						release(fanId);
					}
				}
				if (CollectionUtils.isNotEmpty(fansToRemove)) {

					fansWaitConfirmation.removeAll(fansToRemove);
				}
			}

			if (CollectionUtils.isNotEmpty(elementsToAlarm)) {

				SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
				AlarmConfig alarmConfig = new AlarmConfig();
				alarmConfig.setId(ALARM_NOT_ARRIVED);

				elementsToAlarm.forEach(element -> setActivationAlarmsCommandList.add(element, alarmConfig));

				EntityManager.getInstance().sendCommand(setActivationAlarmsCommandList);
			}

		}

	}

	public boolean updateFanState(Long fanId, Long electricalPanelId, Long fanState, Boolean fanStateConfirmed) {

		boolean update = false;

		if (fanId != null && acquire(fanId)) {

			Fan fan = null;

			if (electricalPanelId != null && fansInElectricalPanel.containsKey(electricalPanelId)) {

				fan = fansInElectricalPanel.get(electricalPanelId).get(fanId);

			} else {

				for (Entry<Long, Map<Long, Fan>> entry : fansInElectricalPanel.entrySet()) {

					if (entry.getValue().containsKey(fanId)) {

						fan = entry.getValue().get(fanId);
						electricalPanelId = entry.getKey();
						break;
					}
				}
			}

			if (fan != null) {

				if (fanStateConfirmed != null) {

					if (fanStateConfirmed.booleanValue() && !isFanConfirmed(electricalPanelId, fanId)) {

						confirmedFanState(electricalPanelId, fanId);
						fan.setLastFanState(fanState);
						log.info("Confirmed fan: " + fanId);
						update = true;

					} else if (!fanStateConfirmed.booleanValue() && isFanConfirmed(electricalPanelId, fanId)) {

						setToConfirmedFanSate(electricalPanelId, fanId);
						log.info("Set to confirmed fan: " + fanId);
						update = true;
					}
				}

				if (update || (fanState != null && !fanState.equals(fan.getFanState()))
						|| (fan.getFanState() != null && !fan.getFanState().equals(fanState))) {

					fan.setFanState(fanState);
					fan.setLastUpdateFanState(Instant.now().toEpochMilli());

					log.info("Update fan: " + fanId + ", fan state: " + fanState + ", last update: "
							+ fan.getLastUpdateFanState());
					update = true;
				}

				if (fanStateConfirmed != null && fanStateConfirmed.booleanValue()
						&& fan.getAlarmConfigIds().contains(ALARM_NOT_ARRIVED)) {

					Element element = new Element(ElementType.ELEMENT_TYPE_FAN, fanId);
					AlarmConfig alarmConfig = new AlarmConfig();
					alarmConfig.setId(ALARM_NOT_ARRIVED);

					SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();
					setDeactivationAlarmsCommandList.add(element, alarmConfig);

					EntityManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);
				}

			}

			release(fanId);
		}
		return update;
	}

	private boolean updateFanState(Long fanId, Long electricalPanelId, Fan fan) {

		boolean resp = false;

		if (fan != null && fanId != null && electricalPanelId != null) {

			Long fanState = null;

			ElementValue elementValue = EntityManager.getInstance().getElementValue(ElementType.ELEMENT_TYPE_FAN, fanId,
					ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE);

			if (elementValue != null) {

				try {

					if (elementValue.getValue() != null) {

						fanState = Long.parseLong(elementValue.getValue());
					}

				} catch (NumberFormatException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					fanState = null;
				}

			} else {

				fanState = fan.getLastFanState();
				log.error("Not found fan state in cache for fan " + fanId + ", restore value with last fan state "
						+ fanState);
			}

			log.info("Confirmed fan: " + fanId);

			fan.setFanState(fanState);
			fan.setLastFanState(fanState);
			fan.setLastUpdateFanState(Instant.now().toEpochMilli());

			log.info("Update fan: " + fanId + ", fan state: " + fanState + ", last update: "
					+ fan.getLastUpdateFanState());

			resp = true;
		}

		return resp;
	}

	public boolean updateAlarm(Long fanId, Long electricalPanelId, Alarm alarm) {

		boolean resp = false;

		if (fanId != null && alarm != null && alarm.getAlarmConfigId() != null
				&& FAN_ALARM_CONFIGS.contains(alarm.getAlarmConfigId()) && acquire(fanId)) {

			Fan fan = null;

			if (electricalPanelId != null && fansInElectricalPanel.containsKey(electricalPanelId)) {

				fan = fansInElectricalPanel.get(electricalPanelId).get(fanId);

			} else {

				for (Entry<Long, Map<Long, Fan>> entry : fansInElectricalPanel.entrySet()) {

					if (entry.getValue().containsKey(fanId)) {

						fan = entry.getValue().get(fanId);
						break;
					}
				}
			}

			if (fan != null) {

				if (alarm.getAlarmConfigId() != null) {

					if (alarm.getDeactivation() != null && fan.getAlarmConfigIds().contains(alarm.getAlarmConfigId())) {

						fan.getAlarmConfigIds().remove(alarm.getAlarmConfigId());
						log.info("Remove alarm: " + alarm.getAlarmConfigId() + " in fan: " + fanId);

					} else if (alarm.getDeactivation() == null && alarm.getActivation() != null
							&& !fan.getAlarmConfigIds().contains(alarm.getAlarmConfigId())) {

						fan.getAlarmConfigIds().add(alarm.getAlarmConfigId());
						log.info("Add alarm: " + alarm.getAlarmConfigId() + " in fan: " + fanId);

						if (FAN_NO_CONENCTION_ALARM_CONFIG.contains(alarm.getAlarmConfigId())) {

							log.info("Force stop fan: " + fanId);
							EntityManager.getInstance().sendCommand(SignallingCommandHelper.get(fanId,
									ElementType.ELEMENT_TYPE_FAN, FansProcessor.SEND_FAN_STOP));
						}
					}
				}

				resp = true;
			}

			release(fanId);
		}

		return resp;

	}

	public boolean checkSendCommand(Long fanId, Long electricalPanelId) {

		boolean resp = false;

		if (fanId != null && acquire(fanId)) {

			Fan fan = null;

			if (electricalPanelId != null && fansInElectricalPanel.containsKey(electricalPanelId)) {

				fan = fansInElectricalPanel.get(electricalPanelId).get(fanId);

			} else {

				for (Entry<Long, Map<Long, Fan>> entry : fansInElectricalPanel.entrySet()) {

					if (entry.getValue().containsKey(fanId)) {

						fan = entry.getValue().get(fanId);
						break;
					}
				}
			}

			if (fan != null) {

				long now = Instant.now().toEpochMilli();

				if (isFanConfirmed(electricalPanelId, fanId)
						&& fan.getLastUpdateFanState() + (fan.getDelayBeforeStart() * 1000) < now) {

					resp = true;
				}
			}

			release(fanId);
		}

		return resp;
	}

	public boolean isFanState(Long fanId, Long electricalPanelId, Long fanState) {

		boolean resp = false;

		if (fanId != null && fanState != null && acquire(fanId)) {

			Fan fan = null;

			if (electricalPanelId != null && fansInElectricalPanel.containsKey(electricalPanelId)) {

				fan = fansInElectricalPanel.get(electricalPanelId).get(fanId);

			} else {

				for (Entry<Long, Map<Long, Fan>> entry : fansInElectricalPanel.entrySet()) {

					if (entry.getValue().containsKey(fanId)) {

						fan = entry.getValue().get(fanId);
						break;
					}
				}
			}

			if (fan != null) {
				resp = fanState.equals(fan.getFanState());
			}

			release(fanId);
		}

		return resp;
	}

	public boolean isFanValidToStartInEmegency(Long fanId, Long electricalPanelId) {

		boolean resp = false;

		if (fanId != null && acquire(fanId)) {

			Fan fan = null;

			if (electricalPanelId != null && fansInElectricalPanel.containsKey(electricalPanelId)) {

				fan = fansInElectricalPanel.get(electricalPanelId).get(fanId);

			} else {

				for (Entry<Long, Map<Long, Fan>> entry : fansInElectricalPanel.entrySet()) {

					if (entry.getValue().containsKey(fanId)) {

						fan = entry.getValue().get(fanId);
						electricalPanelId = entry.getKey();
						break;
					}
				}
			}

			if (fan != null && electricalPanelId != null) {

				if (!isFanConfirmed(electricalPanelId, fanId)) {

					log.info("Fan status must be confirmed to be candidate, fan: " + fanId);

				} else if (fan.getAlarmConfigIds().stream().anyMatch(FAN_NO_CONENCTION_ALARM_CONFIG::contains)) {

					log.info("Fan cannot have no connection alarms to be candidate, fan: " + fanId + ", alarms: "
							+ fan.getAlarmConfigIds());

				} else if (!Long.valueOf(FAN_STATE_VALUE_STOP).equals(fan.getFanState())) {

					log.info("Fan status must be STOP to be canidadate, fan: " + fanId + ", fan status: "
							+ fan.getFanState());

				} else {

					resp = true;
				}
			}

			release(fanId);
		}

		return resp;
	}

	public List<Pair<Long, Long>> getFansToStart(Long tubeId) {

		List<Pair<Long, Long>> fansToStart = null;

		if (ObjectUtils.allNotNull(tubeId)) {

			List<Pair<Long, Long>> fansInTube = fansInTubes.get(tubeId);

			if (CollectionUtils.isNotEmpty(fansInTube)) {

				List<MyFan> list = new ArrayList<>();

				for (Pair<Long, Long> fan : fansInTube) {

					Long electricalPanelId = fan.getLeft();
					Long fanId = fan.getRight();

					if (isFanValidToStart(electricalPanelId, fanId)) {

						MyFan myFan = new MyFan();
						myFan.setElectricalPanelId(electricalPanelId);
						myFan.setFanId(fanId);
						myFan.setOperationalMs(getOperationalMs(electricalPanelId, fanId));

						list.add(myFan);
					}
				}

				if (CollectionUtils.isNotEmpty(list)) {

					Collections.sort(list);
					fansToStart = new ArrayList<>();

					for (MyFan myFan : list) {

						fansToStart.add(new MutablePair<>(myFan.getElectricalPanelId(), myFan.getFanId()));
					}
				}

			}
		}

		return fansToStart;
	}

	public List<Pair<Long, Long>> getFansToStop(Long nFansToStop, Long tubeId) {

		List<Pair<Long, Long>> fansToStop = null;

		if (ObjectUtils.allNotNull(nFansToStop, tubeId)) {

			List<Pair<Long, Long>> fansInTube = fansInTubes.get(tubeId);

			if (CollectionUtils.isNotEmpty(fansInTube)) {

				List<MyFan> list = new ArrayList<>();

				for (Pair<Long, Long> fan : fansInTube) {

					Long electricalPanelId = fan.getLeft();
					Long fanId = fan.getRight();

					if (isFanValidToStop(electricalPanelId, fanId)) {

						MyFan myFan = new MyFan();
						myFan.setElectricalPanelId(electricalPanelId);
						myFan.setFanId(fanId);
						myFan.setOperationalMs(getOperationalMs(electricalPanelId, fanId));

						list.add(myFan);
					}
				}

				if (CollectionUtils.isNotEmpty(list)) {

					Collections.sort(list);
					fansToStop = new ArrayList<>();
					int count = nFansToStop.intValue();

					while (count > 0 && !list.isEmpty()) {

						MyFan myFan = list.get(list.size() - 1);

						fansToStop.add(new MutablePair<>(myFan.getElectricalPanelId(), myFan.getFanId()));

						list.remove(myFan);
						count--;
					}

				}

			}
		}

		return fansToStop;
	}

	private void initFan(Element element, List<Alarm> activeAlarms) {

		if (element != null && element.getId() != null && CollectionUtils.isNotEmpty(element.getElementValues())) {

			try {

				Element elementTube = TubeEntityManager.getInstance().getFirtTubeByChild(ElementType.ELEMENT_TYPE_FAN,
						element.getId());

				Element elementElectricalPanel = EntityManager.getInstance().getFirtParentByType(
						ElementType.ELEMENT_TYPE_ELECTRICAL_PANEL, ElementType.ELEMENT_TYPE_FAN, element.getId());

				if (elementElectricalPanel != null && elementTube != null) {

					Element elementVentilation = EntityManager.getInstance().getFirtParentByType(
							ElementType.ELEMENT_TYPE_VENTILATION, ElementType.ELEMENT_TYPE_TUBE, elementTube.getId());

					Fan fan = new Fan();
					fan.setDelayBeforeStart(DEFAULT_DELAY_BEFORE_START);
					fan.setLastUpdateFanState(Instant.now().toEpochMilli());
					fan.setOperatingOffset(0L);
					fan.setTotalOperatingHours(0L);
					fan.setLastFanState(null);
					fan.setFanState(null);
					fan.setAlarmConfigIds(new HashSet<>());

					if (CollectionUtils.isNotEmpty(activeAlarms)) {

						List<Alarm> fanAlarms = activeAlarms.stream()
								.filter(alarm -> alarm.getTargetElementId() != null
										&& alarm.getTargetElementTypeId() != null
										&& alarm.getTargetElementId().equals(element.getId())
										&& alarm.getTargetElementTypeId().equals(ElementType.ELEMENT_TYPE_FAN))
								.collect(Collectors.toList());

						if (CollectionUtils.isNotEmpty(fanAlarms)) {

							for (Alarm alarm : fanAlarms) {

								if (alarm.getAlarmConfigId() != null) {

									fan.getAlarmConfigIds().add(alarm.getAlarmConfigId());

									if (FAN_NO_CONENCTION_ALARM_CONFIG.contains(alarm.getAlarmConfigId())) {

										log.info("Force stop fan: " + element.getId());
										EntityManager.getInstance()
												.sendCommand(SignallingCommandHelper.get(element.getId(),
														ElementType.ELEMENT_TYPE_FAN, FansProcessor.SEND_FAN_STOP));
									}
								}
							}
						}
					}

					for (ElementValue elementValue : element.getElementValues()) {

						if (elementValue.getElementTypeParamId() != null && elementValue.getValue() != null
								&& elementValue.getParamTypeId() != null) {

							if (ELEMENT_TYPE_PARAM_DELAY_BEFORE_START == elementValue.getElementTypeParamId().intValue()
									&& ParamType.CONFIG == elementValue.getParamTypeId().intValue()) {

								fan.setDelayBeforeStart(Long.parseLong(elementValue.getValue()));

							} else if (ELEMENT_TYPE_PARAM_FAN_STATE == elementValue.getElementTypeParamId().intValue()
									&& ParamType.MEASURE == elementValue.getParamTypeId().intValue()) {

								fan.setLastFanState(Long.parseLong(elementValue.getValue()));
								fan.setFanState(Long.parseLong(elementValue.getValue()));

							} else if (ELEMENT_TYPE_PARAM_TOTAL_OPERATING_HOURS == elementValue.getElementTypeParamId()
									.intValue() && ParamType.MEASURE == elementValue.getParamTypeId().intValue()) {

								fan.setTotalOperatingHours(Long.parseLong(elementValue.getValue()));
							}
						}
					}

					if (!fansInElectricalPanel.containsKey(elementElectricalPanel.getId())) {

						fansInElectricalPanel.put(elementElectricalPanel.getId(), new HashedMap<>());
					}
					fansInElectricalPanel.get(elementElectricalPanel.getId()).put(element.getId(), fan);

					if (!fansInTubes.containsKey(elementTube.getId())) {

						fansInTubes.put(elementTube.getId(), new ArrayList<>());
					}
					fansInTubes.get(elementTube.getId())
							.add(new MutablePair<>(elementElectricalPanel.getId(), element.getId()));

					if (elementVentilation != null) {
						
						if (!fansInVentilations.containsKey(elementVentilation.getId())) {

							fansInVentilations.put(elementVentilation.getId(), new HashedMap<>());
						}
						fansInVentilations.get(elementVentilation.getId()).put(element.getId(), fan);

						ventilationByFans.put(element.getId(), elementVentilation.getId());
					}

					fansMutex.put(element.getId(), new Semaphore(1));
				}
			} catch (NumberFormatException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

	}

	private void load(List<Element> elements, List<Alarm> alarms) {

		if (fansWaitConfirmation != null) {

			fansWaitConfirmation.clear();

		} else {

			fansWaitConfirmation = Collections.synchronizedSet(new HashSet<>());
		}

		if (fansInElectricalPanel != null) {

			fansInElectricalPanel.clear();

		} else {

			fansInElectricalPanel = new HashedMap<>();
		}

		if (fansInTubes != null) {

			fansInTubes.clear();

		} else {

			fansInTubes = new HashedMap<>();
		}

		if (fansInVentilations != null) {

			fansInVentilations.clear();

		} else {

			fansInVentilations = new HashedMap<>();
		}

		if (ventilationByFans != null) {

			ventilationByFans.clear();

		} else {

			ventilationByFans = new HashedMap<>();
		}

		if (fansMutex != null) {

			fansMutex.clear();

		} else {

			fansMutex = new HashedMap<>();
		}

		if (CollectionUtils.isNotEmpty(elements)) {

			List<Alarm> activeAlarms = null;

			if (CollectionUtils.isNotEmpty(alarms)) {

				activeAlarms = alarms.stream().filter(alarm -> alarm.getDeactivation() == null
						&& alarm.getAlarmConfigId() != null && FAN_ALARM_CONFIGS.contains(alarm.getAlarmConfigId()))
						.collect(Collectors.toList());
			}

			for (Element element : elements) {

				initFan(element, activeAlarms);
			}
		}
	}

	private void setToConfirmedFanSate(final Long electricalPanelId, final Long fanId) {

		if (fansWaitConfirmation != null) {

			fansWaitConfirmation.add(new MutablePair<>(electricalPanelId, fanId));
		}
	}

	private void confirmedFanState(final Long electricalPanelId, final Long fanId) {

		if (fansWaitConfirmation != null) {

			fansWaitConfirmation.remove(new MutablePair<>(electricalPanelId, fanId));
		}
	}

	private boolean isFanConfirmed(final Long electricalPanelId, final Long fanId) {

		return fansWaitConfirmation != null
				&& !fansWaitConfirmation.contains(new MutablePair<>(electricalPanelId, fanId));
	}

	private boolean acquire(final Long fanId) {

		boolean acquire = false;
		Semaphore mutex = fansMutex.get(fanId);

		try {
			if (mutex != null) {

				acquire = mutex.tryAcquire(5000, TimeUnit.MILLISECONDS);
			}

		} catch (InterruptedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
		return acquire;
	}

	private void release(final Long fanId) {

		Semaphore mutex = fansMutex.get(fanId);

		if (mutex != null) {

			mutex.release();
		}
	}

	private Long getOperationalMs(final Long electricalPanelId, final Long fanId) {

		Long operationalMs = 0L;

		if (ObjectUtils.allNotNull(electricalPanelId, fanId)) {

			Map<Long, Fan> fans = fansInElectricalPanel.get(electricalPanelId);

			if (fans != null) {

				Fan fan = fans.get(fanId);

				if (fan != null && acquire(fanId)) {

					if (fan.getOperatingOffset() != null) {
						operationalMs += fan.getOperatingOffset();
					}

					if (fan.getTotalOperatingHours() != null) {
						operationalMs += (fan.getTotalOperatingHours() * MS_IN_HOURS);
					}
					release(fanId);
				}
			}
		}

		return operationalMs;
	}

	private boolean isFanValidToStart(final Long electricalPanelId, final Long fanId) {

		boolean resp = false;

		if (ObjectUtils.allNotNull(electricalPanelId, fanId)) {

			Map<Long, Fan> fans = fansInElectricalPanel.get(electricalPanelId);

			if (fans != null) {

				Fan fan = fans.get(fanId);

				if (fan != null && acquire(fanId)) {

					if (!isFanConfirmed(electricalPanelId, fanId)) {

						log.info("Fan status must be confirmed to be candidate, fan: " + fanId);

					} else if (!Long.valueOf(FAN_STATE_VALUE_STOP).equals(fan.getFanState())) {

						log.info("Fan status must be STOP to be canidadate, fan: " + fanId + ", fan status: "
								+ fan.getFanState());

					} else if (!fan.getAlarmConfigIds().isEmpty()) {

						log.info("Fan cannot have alarms to be candidate, fan: " + fanId + ", alarms: "
								+ fan.getAlarmConfigIds());

					} else {

						resp = true;
					}

					release(fanId);

				} else {

					log.error("Acquire: " + "fanId: " + fanId);
				}

			} else {

				log.error("Not found fans in electrical panel " + electricalPanelId);
			}

		} else {
			log.error("Any value is null, electricalPanelId: " + electricalPanelId + ", fanId: " + fanId);
		}

		return resp;
	}

	private boolean isFanValidToStop(final Long electricalPanelId, final Long fanId) {

		boolean resp = false;

		if (ObjectUtils.allNotNull(electricalPanelId, fanId)) {

			Map<Long, Fan> fans = fansInElectricalPanel.get(electricalPanelId);

			if (fans != null) {

				Fan fan = fans.get(fanId);

				if (fan != null && acquire(fanId)) {

					if (isFanConfirmed(electricalPanelId, fanId)
							&& (Long.valueOf(FAN_STATE_VALUE_START).equals(fan.getFanState())
									|| Long.valueOf(FAN_STATE_VALUE_REVERSE).equals(fan.getFanState()))) {

						resp = true;
					}
					release(fanId);
				}
			}
		}

		return resp;
	}

	@Data
	private class MyFan implements Comparable<MyFan> {

		private Long fanId;
		private Long electricalPanelId;

		@EqualsAndHashCode.Exclude
		private Long operationalMs;

		@Override
		public int compareTo(MyFan myFan) {

			if (myFan == null || myFan.operationalMs == null) {

				return 1;

			} else if (this.operationalMs == null) {

				return -1;

			} else {

				return this.operationalMs.compareTo(myFan.operationalMs);
			}
		}
	}

}