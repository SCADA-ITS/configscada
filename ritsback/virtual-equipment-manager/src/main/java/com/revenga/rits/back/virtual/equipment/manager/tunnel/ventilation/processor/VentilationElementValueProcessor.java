package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetCreateIncidentReportCommand;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ElementValueProcessorThread;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.SmConfig;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.Tube;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.TunnelVentilatonSystemService;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.FanEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.TunnelVentilationEntityManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class VentilationElementValueProcessor extends ElementValueProcessorThread {

	private TunnelVentilationEntityManager ventilationEntityManager;
	private TunnelVentilatonSystemService tunnelVentilatonSystemService;
	private FanEntityManager fanEntityManager;

	public VentilationElementValueProcessor(TunnelVentilatonSystemService tunnelVentilatonSystemService,
			TubeEntityManager tubeEntityManager, TunnelVentilationEntityManager ventilationEntityManager,
			FanEntityManager fanEntityManager) {

		super(tubeEntityManager);

		this.tunnelVentilatonSystemService = tunnelVentilatonSystemService;
		this.fanEntityManager = fanEntityManager;
		this.ventilationEntityManager = ventilationEntityManager;
	}

	@Override
	public List<Command> processValues(Set<ElementValue> elementValues) {

		List<Command> commands = null;

		if (CollectionUtils.isNotEmpty(elementValues)) {

			Instant now = Instant.now();

			for (ElementValue elementValue : elementValues) {

				if (elementValue.getElementId() != null
						&& Long.valueOf(ElementType.ELEMENT_TYPE_FAN).equals(elementValue.getElementTypeId())
						&& Long.valueOf(FanEntityManager.ELEMENT_TYPE_PARAM_FAN_STATE)
								.equals(elementValue.getElementTypeParamId())
						&& Long.valueOf(ParamType.MEASURE).equals(elementValue.getParamTypeId())
						&& fanEntityManager != null) {

					Long fanState = null;

					try {

						fanState = elementValue.getValue() != null ? Long.parseLong(elementValue.getValue()) : null;

					} catch (NumberFormatException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
						fanState = null;
					}

					if (fanEntityManager.updateFanState(elementValue.getElementId(), null, fanState, true)) {

						List<Long> tubeIds = this.tubeEntityManager.getTubesByChild(elementValue.getElementTypeId(),
								elementValue.getElementId());

						if (CollectionUtils.isNotEmpty(tubeIds)) {

							for (Long elementTubeId : tubeIds) {

								if (elementTubeId != null) {

									TubeEntityManager.getInstance().checkVentilationRegime(
											new Element(ElementType.ELEMENT_TYPE_TUBE, elementTubeId));
								}
							}
						}
					}

				} else {

					processValue(now.toEpochMilli(), elementValue);
				}
			}

			log.debug(this.getClass().getSimpleName() + " => Time to processValues element values: " + elementValues
					+ " => " + Duration.between(now, Instant.now()).toMillis() + " ms.");

		}

		return commands;
	}

	@Override
	protected void checkValidityMeasures() {

		if (mapElementValues != null && !mapElementValues.isEmpty()) {

			long now = Instant.now().toEpochMilli();

			for (Entry<String, Map<String, List<Pair<ElementValue, Long>>>> entryTube : mapElementValues.entrySet()) {

				if (entryTube.getValue() != null && !entryTube.getValue().isEmpty()) {

					Tube tube = tubeEntityManager.getTube(entryTube.getKey());

					if (tube != null && tube.getTubeVentilationRegimes() != null
							&& tube.getTubeVentilationRegimes().getMeasuresValidityPeriod_s() != null) {

						synchronized (entryTube.getValue()) {

							checkValidityMeasuresInTube(
									tube.getTubeVentilationRegimes().getMeasuresValidityPeriod_s().longValue(), now,
									entryTube.getValue());
						}

					} else {

						synchronized (entryTube.getValue()) {

							checkValidityMeasuresInTube(DEFAULT_VALIDITY_PERIDOD, now, entryTube.getValue());
						}
					}
				}
			}
		}
	}

	@Override
	protected List<Command> checkPeriodTime() {

		List<Command> commands = null;

		if (mapElementValues != null && !mapElementValues.isEmpty() && lastProcessingTime != null
				&& !lastProcessingTime.isEmpty()) {

			Long now = Instant.now().toEpochMilli();

			for (Entry<String, Long> entryTube : lastProcessingTime.entrySet()) {

				if (entryTube.getValue() != null) {

					Tube tube = tubeEntityManager.getTube(entryTube.getKey());

					if (tube != null) {

						String tubeId = entryTube.getKey();
						Element elementTube = tubeEntityManager.getElementTube(tubeId);

						long lastTime = entryTube.getValue().longValue();

						long peridodTime = (tube.getTubeVentilationRegimes() != null
								&& tube.getTubeVentilationRegimes().getPeriodTime_s() != null)
										? tube.getTubeVentilationRegimes().getPeriodTime_s().longValue()
										: DEFAULT_PERIOD_TIME;

						if ((lastTime + (peridodTime * 1000)) < now) {

							List<ElementValue> elementValues = null;
							List<ElementValue> elementMaxValues = new ArrayList<>();

							synchronized (mapElementValues.get(tubeId)) {

								elementValues = processMeasures(mapElementValues.get(tubeId));
							}

							// we take the maximum value for each type of sensor
							if (CollectionUtils.isNotEmpty(elementValues)) {

								elementValues.forEach(newElementValue -> {

									ElementValue currentMaxValue = elementMaxValues.stream()
											.filter(e -> e.getElementTypeId().equals(newElementValue.getElementTypeId())
													&& e.getElementTypeParamId()
															.equals(newElementValue.getElementTypeParamId())
													&& e.getParamTypeId().equals(newElementValue.getParamTypeId()))
											.findFirst().orElse(null);

									if (currentMaxValue == null && newElementValue.getValue() != null) {

										elementMaxValues.add(newElementValue);

									} else if (newElementValue.getValue() != null
											&& Double.parseDouble(currentMaxValue.getValue()) < Double
													.parseDouble(newElementValue.getValue())) {
										
										elementMaxValues.remove(currentMaxValue);
										elementMaxValues.add(newElementValue);
									}

								});
							}

							Long currentRegimePropoused = tubeEntityManager.getVentilationRegimeProposed(tube, null);
							Long regimePropoused = tubeEntityManager.getVentilationRegimeProposed(tube, elementMaxValues);

							// Update Regime Proposed
							if (regimePropoused != null && !regimePropoused.equals(currentRegimePropoused)) {

								Command command = tubeEntityManager.sendVentilaionRegimePropoused(elementTube,
										regimePropoused);

								if (command != null) {

									if (commands == null) {

										commands = new ArrayList<>();
									}
									commands.add(command);
								}

								// Update Regime
								Element elementVentilation = EntityManager.getInstance().getFirtParentByType(
										ElementType.ELEMENT_TYPE_VENTILATION, ElementType.ELEMENT_TYPE_TUBE,
										elementTube.getId());

								if (tunnelVentilatonSystemService != null && ventilationEntityManager
										.isState(elementVentilation, ElementTypeState.STATE_UP)) {

									SmConfig config = tubeEntityManager.getCurrentVentilationSmConfig(elementTube);

									if (config != null && (config.getChangeCurrentRegime() == null
											|| config.getChangeCurrentRegime().booleanValue())) {

										log.info("Set current ventilation regime " + regimePropoused + " in tube "
												+ elementTube.getId());

										SetCreateIncidentReportCommand setCreateIncidentReportCommand = tubeEntityManager
												.setTargetRegime(elementTube, regimePropoused);

										if (setCreateIncidentReportCommand != null) {

											if (commands == null) {

												commands = new ArrayList<>();
											}
											commands.add(setCreateIncidentReportCommand);
										}

									} else {
										log.info("Not set current ventilation regime " + regimePropoused + " in tube "
												+ elementTube.getId());
									}
								}
							}

							lastProcessingTime.put(entryTube.getKey(), now);
						}
					} else {
						throw new NullPointerException("Not found tube" + entryTube.getKey());
					}
				}
			}
		}

		return commands;

	}

}
