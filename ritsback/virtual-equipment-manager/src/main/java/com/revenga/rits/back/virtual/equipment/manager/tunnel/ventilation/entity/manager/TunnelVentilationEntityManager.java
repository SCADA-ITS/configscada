package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.virtual.equipment.manager.helper.ElementHelper;
import com.revenga.rits.back.virtual.equipment.manager.helper.SetElementStatesChangeCommandHelper;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.EmergencyAlgorithm;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.FansByRange;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.StretchConfig;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.StretchTube;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.TunnelVentilationSystem;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.WindSpeedRange;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TunnelVentilationEntityManager {

	protected static final String FILE_CONSTANTS = "constants.properties";

	public static final long ELEMENT_TYPE_PARAM_VENTILATION_EMERGENCY_ARGORITHM_CONFIGS = Long.parseLong(ConfigProperty
			.getInstance().getProperty(FILE_CONSTANTS, "element.type.ventilation.param.emergency_algorithm_configs"));

	private Map<Long, TunnelVentilationSystem> tunnelVentilationSystems;

	private void load(List<Element> elements) {

		if (tunnelVentilationSystems != null) {

			tunnelVentilationSystems.clear();

		} else {

			tunnelVentilationSystems = new HashedMap<>();
		}

		if (CollectionUtils.isNotEmpty(elements)) {

			ObjectMapper objectMapper = new ObjectMapper();

			for (Element element : elements) {

				if (element.getId() != null && CollectionUtils.isNotEmpty(element.getElementValues())) {

					TunnelVentilationSystem tunnelVentilationSystem = new TunnelVentilationSystem();

					tunnelVentilationSystem.setCurrentStateId(EntityManager.getInstance()
							.getConnectedElementTypeStateId(ElementType.ELEMENT_TYPE_VENTILATION));

					for (ElementValue elementValue : element.getElementValues()) {

						if (elementValue.getElementTypeParamId() != null && elementValue.getValue() != null
								&& ELEMENT_TYPE_PARAM_VENTILATION_EMERGENCY_ARGORITHM_CONFIGS == elementValue
										.getElementTypeParamId().intValue()) {

							try {

								EmergencyAlgorithm emergencyAlgorithm = objectMapper.readValue(elementValue.getValue(),
										EmergencyAlgorithm.class);

								if (emergencyAlgorithm != null) {

									tunnelVentilationSystem.setEmergencyAlgorithm(emergencyAlgorithm);

									if (CollectionUtils.isNotEmpty(emergencyAlgorithm.getConfigs())) {

										for (StretchTube stretchTube : emergencyAlgorithm.getConfigs()) {

											loadStretchTube(stretchTube);

											if (CollectionUtils.isNotEmpty(stretchTube.getStretchs())) {

												for (StretchConfig stretchConfig : stretchTube.getStretchs()) {

													loadStretchConfig(stretchConfig);
												}
											}

											// SORT by order
											Collections.sort(stretchTube.getStretchs());
										}

									}

									tunnelVentilationSystems.put(element.getId(), tunnelVentilationSystem);

								}

							} catch (JsonProcessingException e) {

								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));
							}
						}
					}

				}
			}
		}

	}

	private void loadStretchConfig(StretchConfig stretchConfig) {

		if (stretchConfig != null) {

			if (stretchConfig.getStretchId() != null) {

				Key key = new Key(stretchConfig.getStretchId());

				if (key.getClazz() == Stretch.class) {

					Stretch stretch = new Stretch();
					stretch.setPks(key.getIds());
					stretchConfig.setStretch(stretch);
				}
			}

			if (CollectionUtils.isNotEmpty(stretchConfig.getFansOrderId())) {

				List<Element> elements = ElementHelper.getElements(stretchConfig.getFansOrderId());
				stretchConfig.setFanOrder(elements);
			}

			if (CollectionUtils.isNotEmpty(stretchConfig.getCandidatesFansId())) {

				List<Element> elements = ElementHelper.getElements(stretchConfig.getCandidatesFansId());
				stretchConfig.setCandidatesFans(elements);
			}
		}
	}

	private void loadStretchTube(StretchTube stretchTube) {

		if (stretchTube != null) {

			if (CollectionUtils.isNotEmpty(stretchTube.getWindSpeedSensorsId())) {

				List<ElementValue> elementValues = ElementHelper.getElementValues(stretchTube.getWindSpeedSensorsId());
				stretchTube.setWindSpeedSensors(elementValues);
			}

			if (CollectionUtils.isNotEmpty(stretchTube.getWindDirectionSensorsId())) {

				List<ElementValue> elementValues = ElementHelper
						.getElementValues(stretchTube.getWindDirectionSensorsId());
				stretchTube.setWindDirectionSensors(elementValues);
			}
		}
	}

	private StretchConfig getStretchAlarmConfig(Long ventilationId) {

		StretchConfig stretchConfig = null;

		if (ventilationId != null) {

			TunnelVentilationSystem ventilation = tunnelVentilationSystems.get(ventilationId);

			if (ventilation != null && ventilation.getEmergencyAlgorithm() != null
					&& CollectionUtils.isNotEmpty(ventilation.getEmergencyAlgorithm().getConfigs())
					&& ventilation.getStretchAlarmId() != null) {

				for (StretchTube stretchTube : ventilation.getEmergencyAlgorithm().getConfigs()) {

					if (CollectionUtils.isNotEmpty(stretchTube.getStretchs())) {

						stretchConfig = stretchTube.getStretchs().stream()
								.filter(e -> e.getStretch() != null
										&& ventilation.getStretchAlarmId().equals(e.getStretch().getId()))
								.findFirst().orElse(null);

						if (stretchConfig != null) {

							return stretchConfig;
						}
					}
				}
			}
		}

		return stretchConfig;
	}

	private StretchTube getStretchTube(Long ventilationId, Long stretchAlarmId) {

		if (ventilationId != null && stretchAlarmId != null) {

			TunnelVentilationSystem ventilation = tunnelVentilationSystems.get(ventilationId);

			if (ventilation != null && ventilation.getEmergencyAlgorithm() != null
					&& CollectionUtils.isNotEmpty(ventilation.getEmergencyAlgorithm().getConfigs())) {

				for (StretchTube stretchTube : ventilation.getEmergencyAlgorithm().getConfigs()) {

					if (CollectionUtils.isNotEmpty(stretchTube.getStretchs())) {

						StretchConfig stretchConfig = stretchTube.getStretchs().stream()
								.filter(e -> e.getStretch() != null && stretchAlarmId.equals(e.getStretch().getId()))
								.findFirst().orElse(null);

						if (stretchConfig != null) {

							return stretchTube;
						}
					}
				}
			}
		}

		return null;
	}

	private List<StretchTube> getStretchTubeNotAlamed(Long ventilationId) {

		List<StretchTube> stretchTubes = null;

		if (ventilationId != null) {

			TunnelVentilationSystem ventilation = tunnelVentilationSystems.get(ventilationId);

			if (ventilation != null && ventilation.getEmergencyAlgorithm() != null
					&& CollectionUtils.isNotEmpty(ventilation.getEmergencyAlgorithm().getConfigs())
					&& ventilation.getStretchAlarmId() != null) {

				for (StretchTube stretchTube : ventilation.getEmergencyAlgorithm().getConfigs()) {

					if (CollectionUtils.isNotEmpty(stretchTube.getStretchs())) {

						StretchConfig stretchConfig = stretchTube.getStretchs().stream()
								.filter(e -> e.getStretch() != null
										&& ventilation.getStretchAlarmId().equals(e.getStretch().getId()))
								.findFirst().orElse(null);

						if (stretchConfig == null) {

							if (stretchTubes == null) {

								stretchTubes = new ArrayList<>();
							}
							stretchTubes.add(stretchTube);
						}
					}
				}
			}
		}

		return stretchTubes;
	}

	private FansByRange getFansByRange(Long ventilationId) {

		FansByRange fansByRange = null;

		if (ventilationId != null) {

			TunnelVentilationSystem ventilation = tunnelVentilationSystems.get(ventilationId);

			if (ventilation != null && ventilation.getCurrentWindSpeedRangeId() != null) {

				StretchConfig stretchConfig = getStretchAlarmConfig(ventilationId);

				if (stretchConfig != null && CollectionUtils.isNotEmpty(stretchConfig.getFansToStartByRange())) {

					fansByRange = stretchConfig.getFansToStartByRange().stream()
							.filter(e -> ventilation.getCurrentWindSpeedRangeId().equals(e.getRangeId())).findFirst()
							.orElse(null);
				}
			}
		}

		return fansByRange;
	}

	public TunnelVentilationEntityManager(final List<Element> elements) {

		load(elements);
	}

	public ElementValue getWindSpeedByVentilation(final Element elemenVentilation, final Stretch stretch) {

		if (elemenVentilation != null && stretch != null) {

			StretchTube stretchTube = getStretchTube(elemenVentilation.getId(), stretch.getId());

			if (stretchTube != null && CollectionUtils.isNotEmpty(stretchTube.getWindSpeedSensors())) {

				for (ElementValue windSpeedSensor : stretchTube.getWindSpeedSensors()) {

					ElementValue elementValue = EntityManager.getInstance().getElementValue(
							windSpeedSensor.getElementTypeId(), windSpeedSensor.getElementId(),
							windSpeedSensor.getElementTypeParamId(), windSpeedSensor.getParamTypeId());

					if (elementValue != null && elementValue.getValue() != null
							&& (elementValue.getElementValueStateId() == null
									|| elementValue.getElementValueStateId().equals(ElementValueState.STATE_ONLINE)
									|| elementValue.getElementValueStateId().equals(ElementValueState.STATE_ALERTED))) {

						return elementValue;
					}
				}
			}
		}

		return null;
	}

	public ElementValue getWindDirectionByVentilation(final Element elemenVentilation, final Stretch stretch) {

		if (elemenVentilation != null && stretch != null) {

			StretchTube stretchTube = getStretchTube(elemenVentilation.getId(), stretch.getId());

			if (stretchTube != null && CollectionUtils.isNotEmpty(stretchTube.getWindDirectionSensors())) {

				for (ElementValue windDirectionSensor : stretchTube.getWindDirectionSensors()) {

					ElementValue elementValue = EntityManager.getInstance().getElementValue(
							windDirectionSensor.getElementTypeId(), windDirectionSensor.getElementId(),
							windDirectionSensor.getElementTypeParamId(), windDirectionSensor.getParamTypeId());

					if (elementValue != null && elementValue.getValue() != null
							&& (elementValue.getElementValueStateId() == null
									|| elementValue.getElementValueStateId().equals(ElementValueState.STATE_ONLINE)
									|| elementValue.getElementValueStateId().equals(ElementValueState.STATE_ALERTED))) {

						return elementValue;
					}
				}
			}
		}

		return null;
	}

	public Long getWindSpeedRange(final Element elemenVentilation, final ElementValue elementValueSpeed,
			final ElementValue elementValueDirection, final Stretch stretch) {

		WindSpeedRange windSpeedRange = null;

		if (elemenVentilation != null && stretch != null) {

			TunnelVentilationSystem ventilation = tunnelVentilationSystems.get(elemenVentilation.getId());

			if (ventilation != null && ventilation.getEmergencyAlgorithm() != null) {

				StretchTube stretchTube = getStretchTube(elemenVentilation.getId(), stretch.getId());

				if (stretchTube != null && CollectionUtils.isNotEmpty(stretchTube.getWindSpeedRanges())) {

					if (elementValueSpeed != null && elementValueSpeed.getValue() != null
							&& elementValueDirection != null && elementValueDirection.getValue() != null) {

						try {

							Double valueSpeed = Double.parseDouble(elementValueSpeed.getValue());
							valueSpeed = Math.abs(valueSpeed);
							Double valueDirection = Double.parseDouble(elementValueDirection.getValue());
							Double value = valueDirection >= 0 ? valueSpeed : -valueSpeed;

							windSpeedRange = stretchTube.getWindSpeedRanges().stream()
									.filter(range -> (range.getMin() == null || value >= range.getMin())
											&& (range.getMax() == null || value <= range.getMax()))
									.findFirst().orElse(null);

						} catch (NumberFormatException e) {

							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));
						}

					} else if (elementValueDirection != null && elementValueDirection.getValue() != null
							&& stretchTube.getDefaultRangeId() != null) {

						Double valueDirection = Double.parseDouble(elementValueDirection.getValue());

						if (valueDirection < 0) {

							log.info("Wind speed null use defaul range: " + stretchTube.getDefaultRangeId());

							windSpeedRange = stretchTube.getWindSpeedRanges().stream()
									.filter(range -> stretchTube.getDefaultRangeId().equals(range.getId())).findFirst()
									.orElse(null);
						}

					}
				}
			}
		}

		return windSpeedRange != null ? windSpeedRange.getId() : null;
	}

	public boolean isState(final Element elementVentilation, final Long stateId) {

		boolean resp = false;

		if (elementVentilation != null && stateId != null) {

			TunnelVentilationSystem tunnelVentilationSystem = tunnelVentilationSystems.get(elementVentilation.getId());

			if (tunnelVentilationSystem != null && stateId.equals(tunnelVentilationSystem.getCurrentStateId())) {

				resp = true;
			}
		}

		return resp;
	}

	public Command updateVentilation(final Element elemenVentilation, final Long currentwindSpeedRangeId,
			final Long stateId, final Long stretchId) {

		Command command = null;

		if (elemenVentilation != null && stateId != null) {

			TunnelVentilationSystem tunnelVentilationSystem = tunnelVentilationSystems.get(elemenVentilation.getId());

			if (tunnelVentilationSystem != null) {

				command = SetElementStatesChangeCommandHelper.get(elemenVentilation.getId(),
						elemenVentilation.getElementTypeId(), stateId);

				if (command != null) {

					tunnelVentilationSystem.setCurrentStateId(stateId);
					tunnelVentilationSystem.setStretchAlarmId(stretchId);
					tunnelVentilationSystem.setCurrentWindSpeedRangeId(currentwindSpeedRangeId);

					log.info("Update ventilation: " + elemenVentilation.getId() + ", state: " + stateId
							+ ",  stretch alarm: " + stretchId + ", current wind speed range: "
							+ currentwindSpeedRangeId);
				}
			}
		}

		return command;
	}

	public Integer getTargetFans(Long ventilationId) {

		FansByRange fansByRange = getFansByRange(ventilationId);

		return fansByRange != null ? fansByRange.getFans() : null;
	}

	public List<Long> getCandidatesFans(Long ventilationId) {

		List<Long> candidates = new ArrayList<>();

		StretchConfig stretchConfig = getStretchAlarmConfig(ventilationId);

		Long alarmedStretchId = stretchConfig != null && stretchConfig.getStretch() != null
				? stretchConfig.getStretch().getId()
				: null;

		if (alarmedStretchId != null) {

			if (CollectionUtils.isNotEmpty(stretchConfig.getCandidatesFans())) {

				log.info("Candidates to the affected stretch " + alarmedStretchId + ", vetilation " + ventilationId);

				stretchConfig.getCandidatesFans().forEach(e -> {

					if (e.getId() != null) {

						candidates.add(e.getId());
						log.info("Add fan " + e.getId());
					}
				});

			} else {

				StretchTube stretchTube = getStretchTube(ventilationId, alarmedStretchId);

				log.info("Candidates upstream to the affected stretch " + alarmedStretchId + ", vetilation "
						+ ventilationId);

				if (stretchTube != null && CollectionUtils.isNotEmpty(stretchTube.getStretchs())) {

					// Primero se empieza por los más alejados aguas arriva
					boolean stop = false;
					for (int index = 0; index < stretchTube.getStretchs().size() && !stop; index++) {

						if (stretchTube.getStretchs().get(index).getStretch() != null && !alarmedStretchId
								.equals(stretchTube.getStretchs().get(index).getStretch().getId())) {

							log.info("In " + stretchTube.getStretchs().get(index).getStretchId());
							if (CollectionUtils.isNotEmpty(stretchTube.getStretchs().get(index).getFanOrder())) {

								stretchTube.getStretchs().get(index).getFanOrder().forEach(e -> {

									if (e.getId() != null) {

										candidates.add(e.getId());
										log.info("Add fan " + e.getId());
									}

								});
							}

						} else {

							stop = true;
						}
					}

					// Luego los más alejados aguas abajo
					stop = false;
					log.info("Candidates downstream including the affected section " + alarmedStretchId
							+ ", vetilation " + ventilationId);

					for (int index = stretchTube.getStretchs().size() - 1; index >= 0 && !stop; index--) {

						log.info("In " + stretchTube.getStretchs().get(index).getStretchId());
						if (CollectionUtils.isNotEmpty(stretchTube.getStretchs().get(index).getFanOrder())) {

							for (int i = stretchTube.getStretchs().get(index).getFanOrder().size() - 1; i >= 0; i--) {
								
								if (stretchTube.getStretchs().get(index).getFanOrder().get(i).getId() != null) {

									candidates.add(stretchTube.getStretchs().get(index).getFanOrder().get(i).getId());
									log.info("Add fan "
											+ stretchTube.getStretchs().get(index).getFanOrder().get(i).getId());
								}
							}

						}

						// Por último inserto el tramo afectado
						if (stretchTube.getStretchs().get(index).getStretch() != null
								&& alarmedStretchId.equals(stretchTube.getStretchs().get(index).getStretch().getId())) {
							stop = true;
						}
					}
				}

			}

		} else {

			log.error("Not found alarmed stretch in ventilation " + ventilationId);
		}

		return candidates;
	}

	public Integer getTargetFansOtherTube(Long ventilationId) {

		FansByRange fansByRange = getFansByRange(ventilationId);

		return fansByRange != null ? fansByRange.getFansOtherTubes() : null;
	}

	public List<List<Long>> getCandidatesFansOtherTubes(Long ventilationId) {

		List<List<Long>> tubesCandidates = new ArrayList<>();

		StretchConfig stretchConfig = getStretchAlarmConfig(ventilationId);

		Long alarmedStretchId = stretchConfig != null && stretchConfig.getStretch() != null
				? stretchConfig.getStretch().getId()
				: null;

		if (alarmedStretchId != null) {

			List<StretchTube> stretchTubes = getStretchTubeNotAlamed(ventilationId);

			if (CollectionUtils.isNotEmpty(stretchTubes)) {

				for (StretchTube stretchTube : stretchTubes) {

					log.info("Candidates upstream in tube not affected " + stretchTube.getAlias() + ", vetilation: "
							+ ventilationId);

					List<Long> candidates = new ArrayList<>();

					if (CollectionUtils.isNotEmpty(stretchTube.getStretchs())) {

						for (StretchConfig sConfig : stretchTube.getStretchs()) {

							log.info("In " + sConfig.getStretchId());

							if (CollectionUtils.isNotEmpty(sConfig.getFanOrder())) {

								sConfig.getFanOrder().forEach(e -> {

									if (e.getId() != null) {

										candidates.add(e.getId());
										log.info("Add fan " + e.getId());
									}

								});
							}
						}
					}

					tubesCandidates.add(candidates);

				}
			}

		} else {

			log.error("Not found alarmed stretch in ventilation " + ventilationId);
		}

		return tubesCandidates;
	}

	public boolean checkStartFans(Long ventilationId, Long nStartedFansInVentilation) {

		boolean resp = true;

		if (ventilationId != null && nStartedFansInVentilation != null) {

			TunnelVentilationSystem ventilation = tunnelVentilationSystems.get(ventilationId);

			if (ventilation != null && ventilation.getEmergencyAlgorithm() != null
					&& ventilation.getEmergencyAlgorithm().getMaxNfansStart() != null
					&& nStartedFansInVentilation >= ventilation.getEmergencyAlgorithm().getMaxNfansStart()) {

				resp = false;

				log.info("It is not allowed to start more fans in tunnel " + ventilationId + ". Max fans allowed: "
						+ ventilation.getEmergencyAlgorithm().getMaxNfansStart() + ", current fans started "
						+ nStartedFansInVentilation);

			}
		}
		return resp;
	}

}