package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.ElectricalPanel;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.StartRampConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElectricalPanelEntityManager {

	protected static final String FILE_CONSTANTS = "constants.properties";

	public static final long ELEMENT_TYPE_PARAM_ELECTRICAL_PANEL_START_RAMP_S = Long.parseLong(ConfigProperty
			.getInstance().getProperty(FILE_CONSTANTS, "element.type.electrical_panel.param.start_ramp_seconds"));

	public static final long ELEMENT_TYPE_PARAM_ELECTRICAL_PANE_START_RAMP_N_FANS = Long.parseLong(ConfigProperty
			.getInstance().getProperty(FILE_CONSTANTS, "element.type.electrical_panel.param.start_ramp_n_fans"));

	public static final long ELEMENT_TYPE_PARAM_ELECTRICAL_PANE_START_RAMP_CONFIGS = Long.parseLong(ConfigProperty
			.getInstance().getProperty(FILE_CONSTANTS, "element.type.electrical_panel.param.start_ramp_configs"));

	public static final long ELEMENT_TYPE_PARAM_ELECTRICAL_PANE_MAX_N_FANS_START = Long.parseLong(ConfigProperty
			.getInstance().getProperty(FILE_CONSTANTS, "element.type.electrical_panel.param.max_n_fans_start"));

	private Map<Long, Pair<Integer, Long>> mapAccesControl;

	private Map<Long, ElectricalPanel> electricalPanels;

	public ElectricalPanelEntityManager(final List<Element> elements) {

		load(elements);

		if (!CollectionUtils.isEmpty(elements) && electricalPanels != null) {
			mapAccesControl = new HashMap<>();

			for (Entry<Long, ElectricalPanel> entry : electricalPanels.entrySet()) {

				ElectricalPanel electricalPanel = entry.getValue();

				// Configure ramp by array
				if (!CollectionUtils.isEmpty(electricalPanel.getStartRampConfigs())) {

					MutablePair<Integer, Long> pair = new MutablePair<>(
							electricalPanel.getStartRampConfigs().get(0).getId().intValue(), 0L);
					mapAccesControl.put(entry.getKey(), pair);

					// Configure ramp by n Fan and seconds
				} else if (electricalPanel.getStartRampNFan() != null
						&& electricalPanel.getStartRampSeconds() != null) {

					MutablePair<Integer, Long> pair = new MutablePair<>(electricalPanel.getStartRampNFan(), 0L);

					mapAccesControl.put(entry.getKey(), pair);
				}
			}
		}
	}

	public boolean acquire(final Long electricalPanelId, final Integer countStartFan) {

		Pair<Integer, Long> pair = null;

		if (mapAccesControl != null && mapAccesControl.containsKey(electricalPanelId)
				&& electricalPanels.containsKey(electricalPanelId)) {

			pair = mapAccesControl.get(electricalPanelId);

			if (!CollectionUtils.isEmpty(electricalPanels.get(electricalPanelId).getStartRampConfigs())) {

				pair = acquire(electricalPanels.get(electricalPanelId).getStartRampConfigs(), countStartFan,
						pair.getLeft().longValue(), pair.getRight());
			} else {

				pair = acquire(electricalPanels.get(electricalPanelId).getStartRampNFan(),
						electricalPanels.get(electricalPanelId).getStartRampSeconds(), pair.getLeft(), pair.getRight());
			}
		}

		if (pair != null) {

			mapAccesControl.put(electricalPanelId, pair);
			return true;
		}

		return false;
	}

	public boolean checkStartFans(final Element electricalPanel, final Long nStartedFans) {

		boolean resp = false;

		if (electricalPanel != null && nStartedFans != null) {

			ElectricalPanel panel = electricalPanels.get(electricalPanel.getId());

			if (panel != null) {

				if (nStartedFans < panel.getMaxNfansStart()) {

					resp = true;
				} else {
					log.info("It is not allowed to start more fans. Max fans allowed: " + panel.getMaxNfansStart()
							+ ", current fans started " + nStartedFans);
				}
			}
		}

		return resp;
	}

	public Integer getMaxNfansStart(final Long electricalPanelId) {

		Integer maxNfansStart = null;

		if (electricalPanelId != null) {

			ElectricalPanel panel = electricalPanels.get(electricalPanelId);

			if (panel != null) {

				maxNfansStart = panel.getMaxNfansStart();
			}
		}

		return maxNfansStart;
	}

	private void load(List<Element> elements) {

		if (electricalPanels != null) {

			electricalPanels.clear();

		} else {

			electricalPanels = new HashedMap<>();
		}

		if (CollectionUtils.isNotEmpty(elements)) {

			ObjectMapper objectMapper = new ObjectMapper();

			for (Element element : elements) {

				if (element.getId() != null && CollectionUtils.isNotEmpty(element.getElementValues())) {

					try {

						ElectricalPanel electricalPanel = new ElectricalPanel();

						for (ElementValue elementValue : element.getElementValues()) {

							if (elementValue.getElementTypeParamId() != null && elementValue.getValue() != null) {

								if (ELEMENT_TYPE_PARAM_ELECTRICAL_PANE_START_RAMP_N_FANS == elementValue
										.getElementTypeParamId().intValue()) {

									electricalPanel.setStartRampNFan(Integer.parseInt(elementValue.getValue()));

								} else if (ELEMENT_TYPE_PARAM_ELECTRICAL_PANEL_START_RAMP_S == elementValue
										.getElementTypeParamId().intValue()) {

									electricalPanel.setStartRampSeconds(Integer.parseInt(elementValue.getValue()));

								} else if (ELEMENT_TYPE_PARAM_ELECTRICAL_PANE_MAX_N_FANS_START == elementValue
										.getElementTypeParamId().intValue()) {

									electricalPanel.setMaxNfansStart(Integer.parseInt(elementValue.getValue()));

								} else if (ELEMENT_TYPE_PARAM_ELECTRICAL_PANE_START_RAMP_CONFIGS == elementValue
										.getElementTypeParamId().intValue()) {

									if (elementValue.getValue() != null) {

										try {

											List<StartRampConfig> startRampConfigs = objectMapper.readValue(
													elementValue.getValue(),
													new TypeReference<List<StartRampConfig>>() {
													});

											if (CollectionUtils.isNotEmpty(startRampConfigs)) {

												electricalPanel.setStartRampConfigs(startRampConfigs);

											}

										} catch (JsonProcessingException e) {

											log.error(e.getMessage());
											log.debug(ExceptionUtils.getStackTrace(e));

										}
									}
								}

							}
						}
						electricalPanels.put(element.getId(), electricalPanel);

					} catch (NumberFormatException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
			}
		}
	}

	private Pair<Integer, Long> acquire(final Integer startRampNFan, final Integer startRampSeconds,
			final Integer count, final Long lastTime) {

		Pair<Integer, Long> pair = null;

		if (startRampNFan != null && startRampSeconds != null && lastTime != null && count != null) {

			Long now = Instant.now().toEpochMilli();

			if ((count.intValue() == 0)
					|| (count.intValue() > 0 && (now - lastTime) / 1000 >= startRampSeconds.longValue())) {

				pair = new MutablePair<>(1, now);

			} else if (count.intValue() < startRampNFan) {

				pair = new MutablePair<>(count.intValue() + 1, now);
			}
		}
		return pair;
	}

	private Pair<Integer, Long> acquire(final List<StartRampConfig> startRampConfigs, final Integer countStartFan,
			Long currentConfigId, Long lastTime) {

		Pair<Integer, Long> pair = null;

		if (CollectionUtils.isNotEmpty(startRampConfigs) && countStartFan != null && currentConfigId != null
				&& lastTime != null) {

			StartRampConfig newConfig = startRampConfigs.stream()
					.filter(e -> CollectionUtils.isNotEmpty(e.getStartRampNFans())
							&& e.getStartRampNFans().contains(countStartFan))
					.findFirst().orElse(null);

			if (newConfig != null && newConfig.getStartRampSeconds() != null) {

				Long now = Instant.now().toEpochMilli();

				if (newConfig.getId().longValue() <= currentConfigId.longValue()
						|| ((now - lastTime) / 1000 >= newConfig.getStartRampSeconds().longValue())) {

					pair = new MutablePair<>(newConfig.getId().intValue(), now);
				}
			}
		}
		return pair;
	}

}