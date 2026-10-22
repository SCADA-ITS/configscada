package com.revenga.rits.back.incident.manager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.map.HashedMap;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmCondition;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarmType;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentAlarmConditionAlarmRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentAlarmConditionRepository;

import lombok.Getter;

public class IncidentAlarmEntitiesManager {

	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.retention_time_master"));

	private static IncidentAlarmEntitiesManager instance;

	private AlarmConfigRepository alarmConfigRepository;
	private ImsIncidentAlarmConditionRepository incidentAlarmConditionRepository;
	private ImsIncidentAlarmConditionAlarmRepository incidentAlarmConditionAlarmRepository;

	/* Map<incidentAlarmConditionAlarmId, alarm> mapAlarmsCause1 */
	private Map<Long, Alarm> mapAlarmsCase1 = null;

	/*
	 * Map<incidentAlarmConditionAlarmId, Map<elementId, alarm>> mapAlarmsCause2_3
	 */
	private Map<Long, Map<Long, Alarm>> mapAlarmsCase2_3 = null;

	/*
	 * Mapa de referencia, el cual sera inicializado con los valores que permiten
	 * satisfacer cada una de las condiciones
	 */
	private Map<Long, Map<Long, Set<Long>>> referenceConditionState;

	/* Mapa que contiene que condiciones son satisfechas actualmente */
	private Map<Long, Map<Long, Set<Long>>> currentConditionState;

	/*
	 * Si la condicion es de tipo ANY_EQUIPMENT_TYPE_ALARMED el Set de los
	 * anteriores mapas estara a nulo, ya que la condicion sera satisfecha por
	 * diferentes elementos. En este mapa se guardan los elementos que cumplen cada
	 * condicion de este tipo.
	 */

	private Map<Long, Set<Long>> currentElements;

	/*
	 * Si la condicion es de tipo ANY_EQUIPMENT_TYPE_ALARMED en este mapa se
	 * guardaran para cada condicion los posibles elementos que satisfacen la
	 * condicion
	 */
	private Map<Long, Set<Long>> referenceElements;

	private Map<Long, List<ImsIncidentAlarmConditionAlarm>> alarmConfigToConditionAlarm;

	@Getter
	private Set<Long> alarmConfigWithtreatment;

	public static IncidentAlarmEntitiesManager getInstance() {

		if (instance == null) {

			instance = new IncidentAlarmEntitiesManager();
		}

		return instance;
	}

	private IncidentAlarmEntitiesManager() {

		this.alarmConfigRepository = new AlarmConfigRepository(false);

		this.incidentAlarmConditionRepository = new ImsIncidentAlarmConditionRepository(true,
				CACHE_RETENTION_TIME_MASTER);
		this.incidentAlarmConditionAlarmRepository = new ImsIncidentAlarmConditionAlarmRepository(true,
				CACHE_RETENTION_TIME_MASTER);

		mapAlarmsCase1 = new HashedMap<>();
		mapAlarmsCase2_3 = new HashedMap<>();

		referenceConditionState = new HashedMap<>();
		currentConditionState = new HashedMap<>();
		currentElements = Collections.synchronizedMap(new HashedMap<>());
		referenceElements = new HashedMap<>();
		alarmConfigToConditionAlarm = new HashedMap<>();
		alarmConfigWithtreatment = new HashSet<>();

		EntitiesManagerHelper.cacheRepository(this);
	}

	public void initMaps() {

		List<ImsIncidentAlarmCondition> incidentAlarmConditions = incidentAlarmConditionRepository.getAll(false);

		for (ImsIncidentAlarmCondition incidentAlarmCondition : incidentAlarmConditions) {

			if (incidentAlarmCondition.getAuto() != null && incidentAlarmCondition.getAuto().booleanValue()) {

				referenceConditionState.put(incidentAlarmCondition.getId(), new HashedMap<>());
				currentConditionState.put(incidentAlarmCondition.getId(),
						Collections.synchronizedMap(new HashedMap<>()));
			}

		}

		List<ImsIncidentAlarmConditionAlarm> incidentAlarmConditionAlarms = incidentAlarmConditionAlarmRepository
				.getAll(false);

		for (ImsIncidentAlarmConditionAlarm incidentAlarmConditionAlarm : incidentAlarmConditionAlarms) {

			ImsIncidentAlarmCondition incidentAlarmCondition = incidentAlarmConditionRepository
					.get(incidentAlarmConditionAlarm.getIncidentAlarmConditionId(), false);

			if (incidentAlarmCondition != null && incidentAlarmCondition.getAuto() != null
					&& incidentAlarmCondition.getAuto().booleanValue()
					&& incidentAlarmConditionAlarm.getIncidentAlarmConditionAlarmTypeId() != null) {

				Set<Long> elements = null;

				long alarmType = incidentAlarmConditionAlarm.getIncidentAlarmConditionAlarmTypeId().longValue();

				AlarmConfig alarmConfig = alarmConfigRepository.get(incidentAlarmConditionAlarm.getAlarmConfigId(),
						false);

				if (alarmConfig != null && alarmConfig.getTreatment() != null
						&& alarmConfig.getTreatment().booleanValue()) {
					
					alarmConfigWithtreatment.add(alarmConfig.getId());
				}
				
				if (alarmType == ImsIncidentAlarmConditionAlarmType.ANY_EQUIPMENT_TYPE_ALARMED) {

					if (alarmConfig != null && alarmConfig.getElementTypeId() != null
							&& incidentAlarmCondition.getStretchId() != null) {

						Set<Long> setReferenceElements = EntitiesManager.getInstance().getElementByStretch(
								alarmConfig.getElementTypeId(), incidentAlarmCondition.getStretchId());

						if (setReferenceElements != null) {

							referenceElements.put(incidentAlarmConditionAlarm.getId(), setReferenceElements);
						}

					}

				} else if (alarmType == ImsIncidentAlarmConditionAlarmType.ALL_EQUIPMENT_TYPE_ALARMED) {

					if (alarmConfig != null && alarmConfig.getElementTypeId() != null
							&& incidentAlarmCondition.getStretchId() != null) {

						elements = EntitiesManager.getInstance().getElementByStretch(alarmConfig.getElementTypeId(),
								incidentAlarmCondition.getStretchId());

					}
				}
				referenceConditionState.get(incidentAlarmConditionAlarm.getIncidentAlarmConditionId())
						.put(incidentAlarmConditionAlarm.getId(), elements);

				if (!alarmConfigToConditionAlarm.containsKey(incidentAlarmConditionAlarm.getAlarmConfigId())) {

					alarmConfigToConditionAlarm.put(incidentAlarmConditionAlarm.getAlarmConfigId(), new ArrayList<>());
				}

				alarmConfigToConditionAlarm.get(incidentAlarmConditionAlarm.getAlarmConfigId())
						.add(incidentAlarmConditionAlarm);

			}
		}

	}

	public List<ImsIncidentAlarmConditionAlarm> getAlarmConditionAlarmByAlarmConfig(Long alarmConfigId) {

		return alarmConfigToConditionAlarm.get(alarmConfigId);
	}

	public ImsIncidentAlarmCondition getAlarmCondition(Long incidentAlarmConditionId) {

		return incidentAlarmConditionRepository.get(incidentAlarmConditionId, false);
	}

	public boolean checkAlarmCondition(Long incidentAlarmConditionId) {

		return referenceConditionState.get(incidentAlarmConditionId)
				.equals(currentConditionState.get(incidentAlarmConditionId));
	}

	public Long getLocationByAlarmCondition(Long incidentAlarmConditionId) {

		Element element = null;

		Map<Long, Set<Long>> incidentAlarmConditionsMap = referenceConditionState.get(incidentAlarmConditionId);

		if (incidentAlarmConditionsMap != null) {

			/* Only return locationId if only we have one element */

			for (Entry<Long, Set<Long>> entry : incidentAlarmConditionsMap.entrySet()) {

				Element newElement = null;

				ImsIncidentAlarmConditionAlarm incidentAlarmConditionAlarm = incidentAlarmConditionAlarmRepository
						.get(entry.getKey(), false);

				if (incidentAlarmConditionAlarm != null) {

					long alarmType = incidentAlarmConditionAlarm.getIncidentAlarmConditionAlarmTypeId().longValue();

					if (alarmType == ImsIncidentAlarmConditionAlarmType.EQUIPMENT_ALERTED) {

						newElement = new Element();
						newElement.setId(incidentAlarmConditionAlarm.getElementId());
						newElement.setElementTypeId(incidentAlarmConditionAlarm.getElementTypeId());

					} else if (alarmType == ImsIncidentAlarmConditionAlarmType.ANY_EQUIPMENT_TYPE_ALARMED) {

						Set<Long> elements = currentElements.get(entry.getKey());

						if (elements != null && elements.size() == 1) {

							AlarmConfig alarmConfig = alarmConfigRepository
									.get(incidentAlarmConditionAlarm.getAlarmConfigId(), false);

							if (alarmConfig != null && alarmConfig.getElementTypeId() != null) {

								newElement = new Element();
								newElement.setId(elements.iterator().next());
								newElement.setElementTypeId(alarmConfig.getElementTypeId());
							}
						}

					} else if (alarmType == ImsIncidentAlarmConditionAlarmType.ALL_EQUIPMENT_TYPE_ALARMED) {

						Set<Long> elements = entry.getValue();

						if (elements != null && elements.size() == 1) {

							AlarmConfig alarmConfig = alarmConfigRepository
									.get(incidentAlarmConditionAlarm.getAlarmConfigId(), false);

							if (alarmConfig != null && alarmConfig.getElementTypeId() != null) {

								newElement = new Element();
								newElement.setId(elements.iterator().next());
								newElement.setElementTypeId(alarmConfig.getElementTypeId());
							}
						}
					}

					if (newElement == null) {

						return null;

					} else if (element == null) {

						element = newElement;

					} else if (!element.equals(newElement)) {

						return null;
					}
				}
			}
		}

		if (element != null) {

			element = EntitiesManager.getInstance().getElement(element.getElementTypeId(), element.getId());
		}

		return element != null ? element.getLocationId() : null;
	}

	public List<ImsIncidentReportAlarm> getIncidentReportAlarmsByAlarmCondition(Long incidentAlarmConditionId) {

		List<ImsIncidentReportAlarm> incidentReportAlarms = null;

		Map<Long, Set<Long>> incidentAlarmConditionsMap = referenceConditionState.get(incidentAlarmConditionId);

		if (incidentAlarmConditionsMap != null) {

			for (Entry<Long, Set<Long>> entry : incidentAlarmConditionsMap.entrySet()) {

				ImsIncidentReportAlarm incidentReportAlarm = null;

				ImsIncidentAlarmConditionAlarm incidentAlarmConditionAlarm = incidentAlarmConditionAlarmRepository
						.get(entry.getKey(), false);

				if (incidentAlarmConditionAlarm != null) {

					long alarmType = incidentAlarmConditionAlarm.getIncidentAlarmConditionAlarmTypeId().longValue();

					if (alarmType == ImsIncidentAlarmConditionAlarmType.EQUIPMENT_ALERTED) {

						incidentReportAlarm = new ImsIncidentReportAlarm();
						incidentReportAlarm.setAlarmConfigId(incidentAlarmConditionAlarm.getAlarmConfigId());
						incidentReportAlarm.setElementId(incidentAlarmConditionAlarm.getElementId());
						incidentReportAlarm.setElementTypeId(incidentAlarmConditionAlarm.getElementTypeId());

						if (mapAlarmsCase1 != null && mapAlarmsCase1.containsKey(incidentAlarmConditionAlarm.getId())) {

							Alarm oAlarm = mapAlarmsCase1.get(incidentAlarmConditionAlarm.getId());

							if (oAlarm != null) {
								incidentReportAlarm.setActivation(oAlarm.getActivation());
							}
						}

						if (incidentReportAlarms == null) {

							incidentReportAlarms = new ArrayList<>();
						}

						incidentReportAlarms.add(incidentReportAlarm);

					} else if (alarmType == ImsIncidentAlarmConditionAlarmType.ANY_EQUIPMENT_TYPE_ALARMED) {

						Set<Long> elements = currentElements.get(entry.getKey());

						if (elements != null) {

							AlarmConfig alarmConfig = alarmConfigRepository
									.get(incidentAlarmConditionAlarm.getAlarmConfigId(), false);

							if (alarmConfig != null && alarmConfig.getElementTypeId() != null) {

								for (Long elementId : elements) {

									incidentReportAlarm = new ImsIncidentReportAlarm();
									incidentReportAlarm
											.setAlarmConfigId(incidentAlarmConditionAlarm.getAlarmConfigId());
									incidentReportAlarm.setElementId(elementId);
									incidentReportAlarm.setElementTypeId(alarmConfig.getElementTypeId());

									if (mapAlarmsCase2_3 != null
											&& mapAlarmsCase2_3.containsKey(incidentAlarmConditionAlarm.getId())) {

										Map<Long, Alarm> alarmsElement = mapAlarmsCase2_3
												.get(incidentAlarmConditionAlarm.getId());

										if (alarmsElement != null && alarmsElement.size() > 0
												&& alarmsElement.containsKey(elementId)
												&& alarmsElement.get(elementId).getActivation() != null) {

											incidentReportAlarm
													.setActivation(alarmsElement.get(elementId).getActivation());
										}
									}

									if (incidentReportAlarms == null) {

										incidentReportAlarms = new ArrayList<>();
									}

									incidentReportAlarms.add(incidentReportAlarm);
								}
							}
						}

					} else if (alarmType == ImsIncidentAlarmConditionAlarmType.ALL_EQUIPMENT_TYPE_ALARMED) {

						Set<Long> elements = entry.getValue();

						if (elements != null) {

							AlarmConfig alarmConfig = alarmConfigRepository
									.get(incidentAlarmConditionAlarm.getAlarmConfigId(), false);

							if (alarmConfig != null && alarmConfig.getElementTypeId() != null) {

								for (Long elementId : elements) {

									incidentReportAlarm = new ImsIncidentReportAlarm();
									incidentReportAlarm
											.setAlarmConfigId(incidentAlarmConditionAlarm.getAlarmConfigId());
									incidentReportAlarm.setElementId(elementId);
									incidentReportAlarm.setElementTypeId(alarmConfig.getElementTypeId());

									if (mapAlarmsCase2_3 != null
											&& mapAlarmsCase2_3.containsKey(incidentAlarmConditionAlarm.getId())) {

										Map<Long, Alarm> alarmsElement = mapAlarmsCase2_3
												.get(incidentAlarmConditionAlarm.getId());

										if (alarmsElement != null && alarmsElement.size() > 0
												&& alarmsElement.containsKey(elementId)
												&& alarmsElement.get(elementId).getActivation() != null) {

											incidentReportAlarm
													.setActivation(alarmsElement.get(elementId).getActivation());
										}
									}

									if (incidentReportAlarms == null) {
										incidentReportAlarms = new ArrayList<>();
									}

									incidentReportAlarms.add(incidentReportAlarm);
								}
							}
						}
					}
				}

			}
		}

		return incidentReportAlarms;
	}

	public boolean isElementInAlarmConditionAlarm(Long incidentAlarmConditionAlarmId, Long incidentAlarmConditionId,
			Long elementId) {

		Set<Long> elements = null;

		if (incidentAlarmConditionId != null) {

			// ALL_EQUIPMENT_TYPE_ALARMED
			elements = referenceConditionState.get(incidentAlarmConditionId).get(incidentAlarmConditionAlarmId);

		} else {
			// ANY_EQUIPMENT_TYPE_ALARMED
			elements = referenceElements.get(incidentAlarmConditionAlarmId);
		}

		return elements != null && elements.contains(elementId);
	}

	/********************************
	 * Use case 1 EQUIPMENT_ALERTED *
	 ********************************/

	public void addUseCase1(Long incidentAlarmConditionId, Long incidentAlarmConditionAlarmId, Alarm alarm) {

		Map<Long, Set<Long>> incidentAlarmCondition = currentConditionState.get(incidentAlarmConditionId);

		if (incidentAlarmCondition != null) {

			incidentAlarmCondition.put(incidentAlarmConditionAlarmId, null);
		}

		if (!mapAlarmsCase1.containsKey(incidentAlarmConditionAlarmId)) {
			mapAlarmsCase1.put(incidentAlarmConditionAlarmId, alarm);
		}
	}

	public void removeUseCase1(Long incidentAlarmConditionId, Long incidentAlarmConditionAlarmId, Alarm alarm) {

		Map<Long, Set<Long>> incidentAlarmCondition = currentConditionState.get(incidentAlarmConditionId);

		if (incidentAlarmCondition != null) {

			incidentAlarmCondition.remove(incidentAlarmConditionAlarmId);
		}

		if (mapAlarmsCase1.containsKey(incidentAlarmConditionAlarmId)) {

			// Removing the existing key mapping
			mapAlarmsCase1.remove(incidentAlarmConditionAlarmId);
		}
	}

	/*****************************************
	 * Use case 2 ANY_EQUIPMENT_TYPE_ALARMED *
	 *****************************************/

	public void addUseCase2(ImsIncidentAlarmConditionAlarm incidentAlarmConditionAlarm, Alarm alarm) {

		if (!currentElements.containsKey(incidentAlarmConditionAlarm.getId())) {

			currentElements.put(incidentAlarmConditionAlarm.getId(), Collections.synchronizedSet(new HashSet<>()));
		}

		currentElements.get(incidentAlarmConditionAlarm.getId()).add(alarm.getTargetElementId());

		if (incidentAlarmConditionAlarm.getMinAlarmedElements() == null
				|| currentElements.get(incidentAlarmConditionAlarm.getId()).size() >= incidentAlarmConditionAlarm
						.getMinAlarmedElements().intValue()) {

			Map<Long, Set<Long>> incidentAlarmCondition = currentConditionState
					.get(incidentAlarmConditionAlarm.getIncidentAlarmConditionId());

			if (incidentAlarmCondition != null) {

				incidentAlarmCondition.put(incidentAlarmConditionAlarm.getId(), null);
			}
		}

		if (!mapAlarmsCase2_3.containsKey(incidentAlarmConditionAlarm.getId())) {

			Map<Long, Alarm> elementMap = new HashMap<>();

			elementMap.put(alarm.getTargetElementId(), alarm);
			mapAlarmsCase2_3.put(incidentAlarmConditionAlarm.getId(), elementMap);

		} else {
			mapAlarmsCase2_3.get(incidentAlarmConditionAlarm.getId()).put(alarm.getTargetElementId(), alarm);
		}
	}

	public void removeUseCase2(ImsIncidentAlarmConditionAlarm incidentAlarmConditionAlarm, Alarm alarm) {

		Set<Long> incidentAlarmConditionAlarmElements = currentElements.get(incidentAlarmConditionAlarm.getId());

		if (incidentAlarmConditionAlarmElements != null) {

			incidentAlarmConditionAlarmElements.remove(alarm.getTargetElementId());

			if (incidentAlarmConditionAlarmElements.isEmpty()
					|| (incidentAlarmConditionAlarm.getMinAlarmedElements() != null
							&& incidentAlarmConditionAlarmElements.size() < incidentAlarmConditionAlarm
									.getMinAlarmedElements().intValue())) {

				Map<Long, Set<Long>> incidentAlarmCondition = currentConditionState
						.get(incidentAlarmConditionAlarm.getIncidentAlarmConditionId());

				if (incidentAlarmCondition != null) {

					incidentAlarmCondition.remove(incidentAlarmConditionAlarm.getId());
				}
			}

			if (incidentAlarmConditionAlarmElements.isEmpty()) {

				currentElements.remove(incidentAlarmConditionAlarm.getId());
			}
		}

		if (mapAlarmsCase2_3.containsKey(incidentAlarmConditionAlarm.getId())) {

			if (mapAlarmsCase2_3.get(incidentAlarmConditionAlarm.getId()) != null) {

				mapAlarmsCase2_3.get(incidentAlarmConditionAlarm.getId()).remove(alarm.getTargetElementId());
			}

			if (mapAlarmsCase2_3.get(incidentAlarmConditionAlarm.getId()).size() == 0) {

				mapAlarmsCase2_3.remove(incidentAlarmConditionAlarm.getId());
			}
		}
	}

	/*****************************************
	 * Use case 3 ALL_EQUIPMENT_TYPE_ALARMED *
	 *****************************************/

	public void addUseCase3(Long incidentAlarmConditionAlarmId, Long incidentAlarmConditionId, Alarm alarm) {

		Map<Long, Set<Long>> incidentAlarmCondition = currentConditionState.get(incidentAlarmConditionId);

		if (incidentAlarmCondition != null) {

			if (!incidentAlarmCondition.containsKey(incidentAlarmConditionAlarmId)) {

				incidentAlarmCondition.put(incidentAlarmConditionAlarmId, Collections.synchronizedSet(new HashSet<>()));
			}
			incidentAlarmCondition.get(incidentAlarmConditionAlarmId).add(alarm.getTargetElementId());
		}

		if (!mapAlarmsCase2_3.containsKey(incidentAlarmConditionAlarmId)) {

			Map<Long, Alarm> elementMap = new HashMap<>();
			elementMap.put(alarm.getTargetElementId(), alarm);
			mapAlarmsCase2_3.put(incidentAlarmConditionAlarmId, elementMap);

		} else {

			mapAlarmsCase2_3.get(incidentAlarmConditionAlarmId).put(alarm.getTargetElementId(), alarm);
		}
	}

	public void removeUseCase3(Long incidentAlarmConditionAlarmId, Long incidentAlarmConditionId, Alarm alarm) {

		Map<Long, Set<Long>> incidentAlarmCondition = currentConditionState.get(incidentAlarmConditionId);

		if (incidentAlarmCondition != null) {

			Set<Long> incidentAlarmConditionAlarm = incidentAlarmCondition.get(incidentAlarmConditionAlarmId);

			if (incidentAlarmConditionAlarm != null) {

				incidentAlarmConditionAlarm.remove(alarm.getTargetElementId());

				if (incidentAlarmConditionAlarm.isEmpty()) {

					incidentAlarmCondition.remove(incidentAlarmConditionAlarmId);
				}
			}
		}

		if (mapAlarmsCase2_3.containsKey(incidentAlarmConditionAlarmId)) {

			if (mapAlarmsCase2_3.get(incidentAlarmConditionAlarmId) != null) {
				mapAlarmsCase2_3.get(incidentAlarmConditionAlarmId).remove(alarm.getTargetElementId());
			}

			if (mapAlarmsCase2_3.get(incidentAlarmConditionAlarmId).size() == 0) {

				mapAlarmsCase2_3.remove(incidentAlarmConditionAlarmId);
			}
		}
	}
}
