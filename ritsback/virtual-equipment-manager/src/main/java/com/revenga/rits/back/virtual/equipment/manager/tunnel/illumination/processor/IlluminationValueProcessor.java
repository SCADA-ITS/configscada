package com.revenga.rits.back.virtual.equipment.manager.tunnel.illumination.processor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ElementValueProcessorThread;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.illumination.TunnelIlluminationSystemService;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.SmConfig;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.Tube;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IlluminationValueProcessor extends ElementValueProcessorThread {

	private TunnelIlluminationSystemService tunnelIlluminationSystemService;

	public IlluminationValueProcessor(TunnelIlluminationSystemService tunnelIlluminationSystemService,
			TubeEntityManager tubeEntityManager) {

		super(tubeEntityManager);

		this.tunnelIlluminationSystemService = tunnelIlluminationSystemService;
	}

	@Override
	protected void checkValidityMeasures() {

		if (mapElementValues != null && !mapElementValues.isEmpty()) {

			long now = Instant.now().toEpochMilli();

			for (Entry<String, Map<String, List<Pair<ElementValue, Long>>>> entryTube : mapElementValues.entrySet()) {

				if (entryTube.getValue() != null && !entryTube.getValue().isEmpty()) {

					Tube tube = tubeEntityManager.getTube(entryTube.getKey());

					if (tube != null && tube.getTubeIlluminationRegimes() != null
							&& tube.getTubeIlluminationRegimes().getMeasuresValidityPeriod_s() != null) {

						synchronized (entryTube.getValue()) {

							checkValidityMeasuresInTube(
									tube.getTubeIlluminationRegimes().getMeasuresValidityPeriod_s().longValue(), now,
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

						long peridodTime = (tube.getTubeIlluminationRegimes() != null
								&& tube.getTubeIlluminationRegimes().getPeriodTime_s() != null)
										? tube.getTubeIlluminationRegimes().getPeriodTime_s().longValue()
										: DEFAULT_PERIOD_TIME;

						if ((lastTime + (peridodTime * 1000)) < now) {

							List<ElementValue> elementValues = null;

							synchronized (mapElementValues.get(tubeId)) {

								elementValues = processMeasures(mapElementValues.get(tubeId));
							}

							Long currentRegimePropoused = tubeEntityManager.getIlluminationRegimeProposed(elementTube,
									null);
							Long regimePropoused = tubeEntityManager.getIlluminationRegimeProposed(elementTube,
									elementValues);

							// Update Regime Proposed
							if (regimePropoused != null && !regimePropoused.equals(currentRegimePropoused)) {

								Command command = tubeEntityManager.sendIlluminationRegimePropoused(elementTube,
										regimePropoused);

								if (command != null) {

									if (commands == null) {

										commands = new ArrayList<>();
									}
									commands.add(command);
								}
							}

							// Update Regime
							if (regimePropoused != null && !regimePropoused
									.equals(tubeEntityManager.getCurrentIlluminationRegimeId(elementTube))) {

								SmConfig config = tubeEntityManager.getCurrentIlluminationSmConfig(elementTube);

								if (config != null && (config.getChangeCurrentRegime() == null
										|| config.getChangeCurrentRegime().booleanValue())) {

									List<Command> setRegime = tunnelIlluminationSystemService.setRegime(elementTube,
											regimePropoused);
									log.info("Change current illumination regime " + regimePropoused + " in tube "
											+ elementTube.getId());

									if (CollectionUtils.isNotEmpty(setRegime)) {

										if (commands == null) {

											commands = new ArrayList<>();
										}
										commands.addAll(setRegime);
									}

								} else {
									log.info("Not change current illumination regime " + regimePropoused + " in tube "
											+ elementTube.getId());
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
