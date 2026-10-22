package com.revenga.rits.back.virtual.equipment.manager.tunnel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.SmState;
import com.revenga.rits.back.data.core.model.SmStateMachine;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetCreateIncidentReportCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.data.core.util.SmProcessor;
import com.revenga.rits.back.front.core.model.helper.BasicElementHelper;
import com.revenga.rits.back.front.core.model.helper.CommandElementTypeParamHelper;
import com.revenga.rits.back.front.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;
import com.revenga.rits.back.virtual.equipment.manager.helper.SetElementMeasuresChangeCommandHelper;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.Regime;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.RegimeConfig;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.SmConfig;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.SmConfigsRegime;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.Tube;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.TubeRegimes;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.TubeVentilationRegimes;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.VentilationRegime;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TubeEntityManager {

	protected static final String FILE_CONSTANTS = "constants.properties";

	/* VENTILATION */
	public static final long ELEMENT_TYPE_PARAM_VENTILATION_REGIMES_CONFIGS = Long.parseLong(ConfigProperty
			.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_regimes_configs"));

	public static final long ELEMENT_TYPE_PARAM_VENTILATION_MODE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_mode"));

	public static final long ELEMENT_TYPE_PARAM_VENTILATION_REGIME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_regime"));

	public static final long ELEMENT_TYPE_PARAM_VENTILATION_STATE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_state"));

	public static final long ELEMENT_TYPE_PARAM_VENTILATION_REGIME_PROPOUSED = Long.parseLong(ConfigProperty
			.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_regime_propoused"));

	public static final String VENTILATION_MODE_MANUAL = (ConfigProperty.getInstance().getProperty(FILE_CONSTANTS,
			"element.value.ventilation_mode.manual"));

	public static final String VENTILATION_MODE_AUTO = (ConfigProperty.getInstance().getProperty(FILE_CONSTANTS,
			"element.value.ventilation_mode.auto"));

	public static final String VENTILATION_MODE_AUTO_MAINTENACE = (ConfigProperty.getInstance()
			.getProperty(FILE_CONSTANTS, "element.value.ventilation_mode.auto_maintenace"));

	/* ILLUMINATION */
	public static final long ELEMENT_TYPE_PARAM_ILLUMINATION_REGIMES_CONFIGS = Long.parseLong(ConfigProperty
			.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.illumination_regimes_configs"));

	public static final long ELEMENT_TYPE_PARAM_ILLUMINATION_MODE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.illumination_mode"));

	public static final long ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.illumination_regime"));

	public static final long ELEMENT_TYPE_PARAM_ILLUMINATION_STATE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.illumination_state"));

	public static final long ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME_PROPOUSED = Long.parseLong(ConfigProperty
			.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.illumination_regime_propoused"));

	public static final String ILLUMINATION_MODE_MANUAL = (ConfigProperty.getInstance().getProperty(FILE_CONSTANTS,
			"element.value.illumination_mode.manual"));

	public static final String ILLUMINATION_MODE_AUTO = (ConfigProperty.getInstance().getProperty(FILE_CONSTANTS,
			"element.value.illumination_mode.auto"));

	@Getter
	private Map<Long, Tube> tubes;
	private Map<Long, Semaphore> tubesMutex;

	// <elementTypeId, elementId>, tubeElementId
	private Map<Pair<Long, Long>, List<Long>> tubesHierachies;

	private static TubeEntityManager instance;

	public static TubeEntityManager getInstance() {

		if (instance == null) {

			instance = new TubeEntityManager(
					EntityManager.getInstance().getElementWithValues(ElementType.ELEMENT_TYPE_TUBE));
		}

		return instance;
	}

	private TubeEntityManager(final List<Element> elements) {

		load(elements);

		loadHierachies();

		EntityManager.getInstance().initElements(ElementType.ELEMENT_TYPE_TUBE);
	}

	public List<Long> getTubesByChild(Long elementTypeId, Long elementId) {

		return tubesHierachies.get(new MutablePair<>(elementTypeId, elementId));
	}

	public Element getFirtTubeByChild(Long elementTypeId, Long elementId) {

		List<Long> tubesId = tubesHierachies.get(new MutablePair<>(elementTypeId, elementId));

		return CollectionUtils.isNotEmpty(tubesId) ? new Element(ElementType.ELEMENT_TYPE_TUBE, tubesId.get(0)) : null;
	}

	public Tube getTube(final String tubeId) {

		Tube tube = null;

		Key key = new Key(tubeId);

		if (key.getClazz() == Element.class) {

			Element element = new Element();
			element.setPks(key.getIds());

			if (element.getId() != null) {

				tube = tubes.get(element.getId());
			}
		}

		return tube;
	}

	public Tube getTube(final Element elemenTube) {

		Tube tube = null;

		if (elemenTube != null && elemenTube.getId() != null) {

			tube = tubes.get(elemenTube.getId());
		}
		return tube;
	}

	public Element getElementTube(final String tubeId) {

		Element element = null;

		Key key = new Key(tubeId);

		if (key.getClazz() == Element.class) {

			element = new Element();
			element.setPks(key.getIds());
		}

		return element;
	}

	public boolean isVentilationManualMode(final Element elemenTube) {

		boolean resp = false;

		if (elemenTube != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null && VENTILATION_MODE_MANUAL.equals(tube.getCurrentVentilationMode().toString())) {

				resp = true;
			}
		}

		return resp;
	}

	public Command sendVentilationMode(final Element elemenTube, final Long mode) {

		Command command = null;

		if (elemenTube != null && mode != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null && !Integer.valueOf(mode.intValue()).equals(tube.getCurrentVentilationMode())) {

				command = SetElementMeasuresChangeCommandHelper.get(elemenTube.getId(), elemenTube.getElementTypeId(),
						ELEMENT_TYPE_PARAM_VENTILATION_MODE, mode.toString());

				if (command != null) {

					tube.setCurrentVentilationMode(mode.intValue());
				}
			}
		}

		return command;
	}

	public Command sendVentilaionState(final Element elemenTube, final ElementTypeState elementTypeState) {

		Command command = null;

		if (elemenTube != null && elementTypeState != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null) {

				command = SetElementMeasuresChangeCommandHelper.get(elemenTube.getId(), elemenTube.getElementTypeId(),
						ELEMENT_TYPE_PARAM_VENTILATION_STATE, BasicElementHelper.getIdFromElement(elementTypeState));
			}
		}

		return command;
	}

	public Command sendVentilaionRegimePropoused(final Element elementTube, final Long regimePropoused) {

		Command command = null;

		if (elementTube != null && regimePropoused != null) {

			command = SetElementMeasuresChangeCommandHelper.get(elementTube.getId(), elementTube.getElementTypeId(),
					ELEMENT_TYPE_PARAM_VENTILATION_REGIME_PROPOUSED, regimePropoused.toString());
		}
		return command;
	}

	public Command updateVentilationRegime(final Element elementTube, final Long nFans) {

		Command command = null;

		if (elementTube != null && elementTube.getId() != null && acquire(elementTube.getId())) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null) {

				if (nFans != null && tube.getTubeVentilationRegimes() != null
						&& !CollectionUtils.isEmpty(tube.getTubeVentilationRegimes().getVentilationRegimes())) {

					VentilationRegime ventilationRegime = tube.getTubeVentilationRegimes().getVentilationRegimes()
							.stream().filter(e -> nFans.equals(e.getFans())).findFirst().orElse(null);

					log.info(nFans + " fans started, update ventilation regime "
							+ (ventilationRegime != null ? ventilationRegime.getId() : null) + " in tube "
							+ elementTube.getId());

					command = SetElementMeasuresChangeCommandHelper.get(elementTube.getId(),
							elementTube.getElementTypeId(), ELEMENT_TYPE_PARAM_VENTILATION_REGIME,
							ventilationRegime != null && ventilationRegime.getId() != null
									? ventilationRegime.getId().toString()
									: null);
				}
				tube.setCheckVentilationRegime(false);
			}

			release(elementTube.getId());

		}

		return command;
	}

	public SetCreateIncidentReportCommand setTargetRegime(final Element elementTube, final Long regime) {

		SetCreateIncidentReportCommand setCreateIncidentReportCommand = null;
		if (elementTube != null && acquire(elementTube.getId())) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null) {

				if (regime != null) {

					if (tube.getTubeVentilationRegimes() != null
							&& CollectionUtils.isNotEmpty(tube.getTubeVentilationRegimes().getVentilationRegimes())) {

						VentilationRegime ventilationRegime = tube.getTubeVentilationRegimes().getVentilationRegimes()
								.stream().filter(item -> regime.equals(item.getId())).findFirst().orElse(null);

						if (ventilationRegime != null && ventilationRegime.getFans() != null) {

							tube.setTargetFans(ventilationRegime.getFans());

							log.info("Set target fans " + ventilationRegime.getFans() + " in tube "
									+ elementTube.getId());

						} else {

							tube.setTargetFans(null);
							log.info("Set target fans null in tube " + elementTube.getId());
						}

						if (ventilationRegime != null && ventilationRegime.getLocation() != null
								&& ventilationRegime.getIncidentType() != null) {

							setCreateIncidentReportCommand = new SetCreateIncidentReportCommand();
							setCreateIncidentReportCommand.setLocationId(ventilationRegime.getLocation().getId());
							setCreateIncidentReportCommand
									.setIncidentTypeId(ventilationRegime.getIncidentType().getId());
						}
					}

				} else {

					tube.setTargetFans(null);
					log.info("Set target fans null in tube " + elementTube.getId());
				}

				if (tube.getTargetFans() == null || tube.getTargetFans().intValue() == 0) {

					tube.getTubeVentilationRegimes().setCurrentWindDirection(null);
				}
			}

			release(elementTube.getId());
		}

		return setCreateIncidentReportCommand;
	}

	public void checkVentilationRegime(final Element elementTube) {

		if (elementTube != null && acquire(elementTube.getId())) {

			Tube tube = tubes.get(elementTube.getId());

			tube.setCheckVentilationRegime(true);

			release(elementTube.getId());
		}
	}

	public SmConfig getCurrentVentilationSmConfig(final Element elementTube) {

		SmConfig config = null;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null && tube.getTubeVentilationRegimes() != null && tube.getCurrentVentilationMode() != null
					&& CollectionUtils.isNotEmpty(tube.getTubeVentilationRegimes().getSmConfigs())) {

				config = tube.getTubeVentilationRegimes().getSmConfigs().stream()
						.filter(item -> tube.getCurrentVentilationMode().equals(item.getMode().intValue())).findFirst()
						.orElse(null);
			}
		}

		return config;
	}

	public Long initSmVentilationProcessor(final Element elementTube) {

		Long regimePropoused = null;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());
			SmConfig config = getCurrentVentilationSmConfig(elementTube);

			if (tube != null && config != null && config.getStateMachineId() != null) {

				SmStateMachine stateMachine = EntityManager.getInstance().getStateMachine(config.getStateMachineId());

				if (stateMachine != null) {

					SmProcessor processor = new SmProcessor();
					processor.Init(stateMachine);
					tube.setSmVentilationProcessor(processor);

					SmState state = tube.getSmVentilationProcessor().getCurrentState();

					if (state != null && state.getId() != null
							&& CollectionUtils.isNotEmpty(config.getSmConfigsRegimes())) {

						SmConfigsRegime smConfigsRegime = config.getSmConfigsRegimes().stream()
								.filter(item -> item.getStateIds().contains(state.getId())).findFirst().orElse(null);

						regimePropoused = smConfigsRegime != null ? smConfigsRegime.getRegimeId() : null;

					}
				} else {
					log.error("Not found state machine: " + config.getStateMachineId());
				}
			} else {
				log.error("Not found ventilation state machine in tube " + elementTube.getId());
			}

		}
		return regimePropoused;
	}

	public Long getVentilationRegimeProposed(final Element elementTube, final List<ElementValue> elementValues) {

		Long regime = null;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());

			regime = getVentilationRegimeProposed(tube, elementValues);
		}
		return regime;
	}

	public Long getVentilationRegimeProposed(final Tube tube, final List<ElementValue> elementValues) {

		Long regime = null;

		if (tube != null && tube.getSmVentilationProcessor() != null
				&& CollectionUtils.isNotEmpty(tube.getTubeVentilationRegimes().getSmConfigs())
				&& tube.getCurrentVentilationMode() != null) {

			SmState state = null;

			if (CollectionUtils.isNotEmpty(elementValues)) {

				state = tube.getSmVentilationProcessor().update(elementValues);

			} else {

				state = tube.getSmVentilationProcessor().getCurrentState();
			}

			if (state != null && state.getId() != null) {

				SmConfig config = tube.getTubeVentilationRegimes().getSmConfigs().stream()
						.filter(item -> tube.getCurrentVentilationMode().equals(item.getMode().intValue())).findFirst()
						.orElse(null);

				if (config != null && CollectionUtils.isNotEmpty(config.getSmConfigsRegimes())) {

					Long stateId = state.getId();
					SmConfigsRegime smConfigsRegime = config.getSmConfigsRegimes().stream()
							.filter(item -> item.getStateIds().contains(stateId)).findFirst().orElse(null);

					regime = smConfigsRegime != null ? smConfigsRegime.getRegimeId() : null;
				}

			}
		}
		return regime;
	}

	public Set<ElementTypeParam> getVentilationElementTypeParam(final Element elementTube) {

		Set<ElementTypeParam> elementTypeParams = null;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null && tube.getSmIlluminationProcessor() != null) {

				elementTypeParams = tube.getSmVentilationProcessor().getElementTypeParam();
			}
		}

		return elementTypeParams;

	}
	
	public boolean isIlluminationManualMode(final Element elemenTube) {

		boolean resp = false;

		if (elemenTube != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null && ILLUMINATION_MODE_MANUAL.equals(tube.getCurrentIlluminationMode().toString())) {

				resp = true;
			}
		}

		return resp;
	}

	public Command sendIlluminationRegime(final Element elemenTube, final Long regime) {

		Command command = null;

		if (elemenTube != null && regime != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null && !regime.equals(tube.getCurrentIlluminationRegime())) {

				command = SetElementMeasuresChangeCommandHelper.get(elemenTube.getId(), elemenTube.getElementTypeId(),
						ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME, regime.toString());

				if (command != null) {

					tube.setCurrentIlluminationRegime(regime);
				}
			}
		}

		return command;
	}

	public Command sendIlluminationMode(final Element elemenTube, final Long mode) {

		Command command = null;

		if (elemenTube != null && mode != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null && !Integer.valueOf(mode.intValue()).equals(tube.getCurrentIlluminationMode())) {

				command = SetElementMeasuresChangeCommandHelper.get(elemenTube.getId(), elemenTube.getElementTypeId(),
						ELEMENT_TYPE_PARAM_ILLUMINATION_MODE, mode.toString());

				if (command != null) {

					tube.setCurrentIlluminationMode(mode.intValue());
				}
			}
		}

		return command;
	}

	public Command sendIlluminationRegimeToNull(final Element elemenTube) {

		Command command = null;

		if (elemenTube != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null && tube.getCurrentIlluminationRegime() != null) {

				command = SetElementMeasuresChangeCommandHelper.get(elemenTube.getId(), elemenTube.getElementTypeId(),
						ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME, null);

				if (command != null) {

					tube.setCurrentIlluminationRegime(null);
				}
			}
		}

		return command;
	}

	public Command sendIlluminationState(final Element elemenTube, final ElementTypeState elementTypeState) {

		Command command = null;

		if (elemenTube != null && elementTypeState != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null) {

				command = SetElementMeasuresChangeCommandHelper.get(elemenTube.getId(), elemenTube.getElementTypeId(),
						ELEMENT_TYPE_PARAM_ILLUMINATION_STATE, BasicElementHelper.getIdFromElement(elementTypeState));
			}
		}

		return command;
	}

	public Command sendIlluminationRegimePropoused(final Element elementTube, final Long regimePropoused) {

		Command command = null;

		if (elementTube != null && regimePropoused != null) {

			command = SetElementMeasuresChangeCommandHelper.get(elementTube.getId(), elementTube.getElementTypeId(),
					ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME_PROPOUSED, regimePropoused.toString());
		}
		return command;
	}

	public Regime getCurrentIlluminationRegime(final Element elemenTube) {

		Regime regime = null;

		if (elemenTube != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null && tube.getCurrentIlluminationRegime() != null && tube.getTubeIlluminationRegimes() != null
					&& CollectionUtils.isNotEmpty(tube.getTubeIlluminationRegimes().getRegimes())) {

				Long currentRegimeId = tube.getCurrentIlluminationRegime();

				regime = tube.getTubeIlluminationRegimes().getRegimes().stream()
						.filter(item -> currentRegimeId.equals(item.getId())).findFirst().orElse(null);
			}
		}
		return regime;
	}

	public Long getCurrentIlluminationRegimeId(final Element elemenTube) {

		Long currentRegimeId = null;

		if (elemenTube != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null) {

				currentRegimeId = tube.getCurrentIlluminationRegime();
			}
		}
		return currentRegimeId;
	}

	public RegimeConfig getIlluminationRegimeConfig(final Element elemenTube, final Long regimeValue) {

		RegimeConfig config = null;

		if (elemenTube != null && regimeValue != null) {

			Tube tube = tubes.get(elemenTube.getId());

			if (tube != null && tube.getTubeIlluminationRegimes() != null
					&& CollectionUtils.isNotEmpty(tube.getTubeIlluminationRegimes().getRegimes())) {

				Regime regime = tube.getTubeIlluminationRegimes().getRegimes().stream()
						.filter(item -> regimeValue.equals(item.getId())).findFirst().orElse(null);

				if (regime != null && CollectionUtils.isNotEmpty(regime.getConfigs())) {

					config = regime.getConfigs().get(0);
					regime.setCurrentRegimeConfigId(config.getId());

				} else {

					log.error("Not found regime " + regime);
				}
			}
		}

		return config;
	}

	public RegimeConfig getCurrentIlluminationRegimeConfig(final Element elemenTube) {

		Regime regime = getCurrentIlluminationRegime(elemenTube);

		return regime != null && regime.getConfigs() != null ? regime.getConfigs().get(0) : null;
	}

	public SmConfig getCurrentIlluminationSmConfig(final Element elementTube) {

		SmConfig config = null;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null && tube.getTubeIlluminationRegimes() != null && tube.getCurrentIlluminationMode() != null
					&& CollectionUtils.isNotEmpty(tube.getTubeIlluminationRegimes().getSmConfigs())) {

				config = tube.getTubeIlluminationRegimes().getSmConfigs().stream()
						.filter(item -> tube.getCurrentIlluminationMode().equals(item.getMode().intValue())).findFirst()
						.orElse(null);
			}
		}

		return config;
	}

	public Long initSmIlluminationProcessor(final Element elementTube) {

		Long regimePropoused = null;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());
			SmConfig config = getCurrentIlluminationSmConfig(elementTube);

			if (tube != null && config != null && config.getStateMachineId() != null) {

				SmStateMachine stateMachine = EntityManager.getInstance().getStateMachine(config.getStateMachineId());

				if (stateMachine != null) {

					SmProcessor processor = new SmProcessor();
					processor.Init(stateMachine);
					tube.setSmIlluminationProcessor(processor);

					SmState state = tube.getSmIlluminationProcessor().getCurrentState();

					if (state != null && state.getId() != null
							&& CollectionUtils.isNotEmpty(config.getSmConfigsRegimes())) {

						SmConfigsRegime smConfigsRegime = config.getSmConfigsRegimes().stream()
								.filter(item -> item.getStateIds().contains(state.getId())).findFirst().orElse(null);

						regimePropoused = smConfigsRegime != null ? smConfigsRegime.getRegimeId() : null;

					}
				}
			}
		}
		return regimePropoused;
	}

	public Long getIlluminationRegimeProposed(final Element elementTube, final List<ElementValue> elementValues) {

		Long regime = null;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null && tube.getSmIlluminationProcessor() != null
					&& CollectionUtils.isNotEmpty(tube.getTubeIlluminationRegimes().getSmConfigs())
					&& tube.getCurrentVentilationMode() != null) {

				SmState state = null;

				if (CollectionUtils.isNotEmpty(elementValues)) {

					state = tube.getSmIlluminationProcessor().update(elementValues);

				} else {

					state = tube.getSmIlluminationProcessor().getCurrentState();
				}

				if (state != null && state.getId() != null) {

					SmConfig config = tube.getTubeIlluminationRegimes().getSmConfigs().stream()
							.filter(item -> tube.getCurrentIlluminationMode().equals(item.getMode().intValue()))
							.findFirst().orElse(null);

					if (config != null && CollectionUtils.isNotEmpty(config.getSmConfigsRegimes())) {

						Long stateId = state.getId();
						SmConfigsRegime smConfigsRegime = config.getSmConfigsRegimes().stream()
								.filter(item -> item.getStateIds().contains(stateId)).findFirst().orElse(null);

						regime = smConfigsRegime != null ? smConfigsRegime.getRegimeId() : null;
					}

				}
			}
		}
		return regime;
	}

	public Set<ElementTypeParam> getIlluminationElementTypeParam(final Element elementTube) {

		Set<ElementTypeParam> elementTypeParams = null;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null && tube.getSmIlluminationProcessor() != null) {

				elementTypeParams = tube.getSmIlluminationProcessor().getElementTypeParam();
			}
		}

		return elementTypeParams;

	}
	
	public boolean checkWindDirection(final Element elementTube) {

		boolean resp = true;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null && tube.getTubeVentilationRegimes() != null) {

				ElementValue elementValue = getwindDirectionByTube(elementTube);

				try {

					if (elementValue != null && elementValue.getValue() != null) {

						Double value = Double.parseDouble(elementValue.getValue());

						resp = value == null || value.doubleValue() >= 0;
					}
					tube.getTubeVentilationRegimes().setCurrentWindDirection(resp);

				} catch (NumberFormatException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}

		}

		return resp;
	}

	public Boolean getCurrentWindDirectionByTube(Element elementTube) {

		Boolean resp = null;

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null && tube.getTubeVentilationRegimes() != null) {
				resp = tube.getTubeVentilationRegimes().getCurrentWindDirection();
			}

		}
		return resp;
	}

	private ElementValue getwindDirectionByTube(final Element elementTube) {

		if (elementTube != null) {

			Tube tube = tubes.get(elementTube.getId());

			if (tube != null && tube.getTubeVentilationRegimes() != null
					&& tube.getTubeVentilationRegimes().getWindDirection() != null) {

				ElementTypeParam windDirectionSensor = tube.getTubeVentilationRegimes().getWindDirection();

				if (windDirectionSensor.getElementTypeId() != null) {

					List<Element> elements = EntityManager.getInstance().getChildByType(ElementType.ELEMENT_TYPE_TUBE,
							elementTube.getId(), windDirectionSensor.getElementTypeId());

					if (!CollectionUtils.isEmpty(elements)) {

						for (Element windDirectionElementSensor : elements) {

							if (windDirectionElementSensor != null) {

								ElementValue elementValue = EntityManager.getInstance().getElementValue(
										windDirectionElementSensor.getElementTypeId(),
										windDirectionElementSensor.getId(), windDirectionSensor.getId(),
										windDirectionSensor.getParamTypeId());

								if (elementValue != null && elementValue.getValue() != null
										&& (elementValue.getElementValueStateId() == null
												|| elementValue.getElementValueStateId()
														.equals(ElementValueState.STATE_ONLINE)
												|| elementValue.getElementValueStateId()
														.equals(ElementValueState.STATE_ALERTED))) {

									return elementValue;
								}

							}
						}
					}
				}
			}
		}

		return null;
	}

	private void load(List<Element> elements) {

		if (tubesMutex != null) {

			tubesMutex.clear();

		} else {

			tubesMutex = new HashedMap<>();
		}

		if (tubes != null) {

			tubes.clear();

		} else {

			tubes = new HashedMap<>();
		}

		if (CollectionUtils.isNotEmpty(elements)) {

			ObjectMapper objectMapper = new ObjectMapper();

			for (Element element : elements) {

				if (CollectionUtils.isNotEmpty(element.getElementValues())) {

					Tube tube = new Tube();
					tube.setCheckVentilationRegime(true);

					try {
						for (ElementValue elementValue : element.getElementValues()) {

							if (elementValue.getElementTypeParamId() != null && elementValue.getValue() != null
									&& Long.valueOf(ParamType.CONFIG).equals(elementValue.getParamTypeId())) {

								if (ELEMENT_TYPE_PARAM_VENTILATION_REGIMES_CONFIGS == elementValue
										.getElementTypeParamId().intValue()) {

									TubeVentilationRegimes tubeVentilationRegimes = objectMapper
											.readValue(elementValue.getValue(), TubeVentilationRegimes.class);

									loadWindDirection(tubeVentilationRegimes);

									if (CollectionUtils.isNotEmpty(tubeVentilationRegimes.getVentilationRegimes())) {

										tube.setTubeVentilationRegimes(tubeVentilationRegimes);

										for (VentilationRegime regime : tubeVentilationRegimes
												.getVentilationRegimes()) {

											loadLocation(regime);
											loadIncidentType(regime);
										}
									}

								} else if (ELEMENT_TYPE_PARAM_ILLUMINATION_REGIMES_CONFIGS == elementValue
										.getElementTypeParamId().intValue()) {

									TubeRegimes tubeRegimes = objectMapper.readValue(elementValue.getValue(),
											TubeRegimes.class);

									if (CollectionUtils.isNotEmpty(tubeRegimes.getRegimes())) {

										for (Regime regime : tubeRegimes.getRegimes()) {

											if (CollectionUtils.isNotEmpty(regime.getConfigs())) {

												for (RegimeConfig regimeConfig : regime.getConfigs()) {

													loadCommandsRegimesConfig(regimeConfig);
												}
											}
										}
										tube.setTubeIlluminationRegimes(tubeRegimes);
									}
								}

							} else if (elementValue.getElementTypeParamId() != null && elementValue.getValue() != null
									&& Long.valueOf(ParamType.MEASURE).equals(elementValue.getParamTypeId())) {

								if (ELEMENT_TYPE_PARAM_VENTILATION_MODE == elementValue.getElementTypeParamId()
										.intValue()) {

									tube.setCurrentVentilationMode(Integer.parseInt(elementValue.getValue()));

								} else if (ELEMENT_TYPE_PARAM_ILLUMINATION_MODE == elementValue.getElementTypeParamId()
										.intValue()) {

									tube.setCurrentIlluminationMode(Integer.parseInt(elementValue.getValue()));

								} else if (ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME == elementValue
										.getElementTypeParamId().intValue()) {

									tube.setCurrentIlluminationRegime(Long.parseLong(elementValue.getValue()));
								}
							}
						}

						tubes.put(element.getId(), tube);
						tubesMutex.put(element.getId(), new Semaphore(1));

					} catch (JsonProcessingException | NumberFormatException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
			}
		}
	}

	private void loadWindDirection(TubeVentilationRegimes tubeVentilationRegimes) {

		if (tubeVentilationRegimes != null && tubeVentilationRegimes.getWindDirectionId() != null) {

			Key key = new Key(tubeVentilationRegimes.getWindDirectionId());

			if (key.getClazz() == ElementTypeParam.class) {

				ElementTypeParam elementTypeParam = new ElementTypeParam();
				elementTypeParam.setPks(key.getIds());
				tubeVentilationRegimes.setWindDirection(elementTypeParam);
			}
		}
	}

	private void loadLocation(VentilationRegime ventilationRegime) {

		if (ventilationRegime != null && ventilationRegime.getLocationId() != null) {

			Key key = new Key(ventilationRegime.getLocationId());

			if (key.getClazz() == Location.class) {

				Location location = new Location();
				location.setPks(key.getIds());
				ventilationRegime.setLocation(location);
			}
		}
	}

	private void loadIncidentType(VentilationRegime ventilationRegime) {

		if (ventilationRegime != null && ventilationRegime.getIncidentTypeId() != null) {

			Key key = new Key(ventilationRegime.getIncidentTypeId());

			if (key.getClazz() == ImsIncidentType.class) {

				ImsIncidentType incidentType = new ImsIncidentType();
				incidentType.setPks(key.getIds());
				ventilationRegime.setIncidentType(incidentType);
			}
		}
	}

	private void loadCommandsRegimesConfig(RegimeConfig config) {

		if (config != null && CollectionUtils.isNotEmpty(config.getCommands())) {

			for (CommandElementVO command : config.getCommands()) {

				if (command.getCommandElementValues() != null) {

					Key key = null;

					for (CommandElementValueVO commandElementValue : command.getCommandElementValues()) {

						if (commandElementValue.getCommandElementTypeParamId() != null) {

							key = new Key(commandElementValue.getCommandElementTypeParamId());

							if (key.getClazz() == CommandElementTypeParam.class) {

								CommandElementTypeParam commandElementTypeParam = null;

								commandElementTypeParam = EntityManager.getInstance().getCommandElementTypeParam(
										Long.valueOf(key.getId(1)), Long.valueOf(key.getId(2)),
										Long.valueOf(key.getId(3)));

								if (commandElementTypeParam != null) {

									commandElementValue.setCommandElementTypeParam(
											CommandElementTypeParamHelper.get(commandElementTypeParam));
								}
							}
						}
					}
				}

				config.addSignallingCommand(SignallingCommandHelper.get(command));
			}
		}
	}

	private boolean acquire(final Long tubeId) {

		boolean acquire = false;
		Semaphore mutex = tubesMutex.get(tubeId);

		try {
			if (tubeId != null && mutex != null) {

				acquire = mutex.tryAcquire(5000, TimeUnit.MILLISECONDS);
			}

		} catch (InterruptedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
		return acquire;
	}

	private void release(final Long tubeId) {

		Semaphore mutex = tubesMutex.get(tubeId);

		if (mutex != null) {

			mutex.release();
		}
	}

	private void loadHierachies() {

		tubesHierachies = new HashMap<>();

		List<ElementHierarchy> elementHierarchies = EntityManager.getInstance()
				.getElementHierarchyByParentType(ElementType.ELEMENT_TYPE_TUBE);

		if (!CollectionUtils.isEmpty(elementHierarchies)) {

			for (ElementHierarchy elementHierarchy : elementHierarchies) {

				if (elementHierarchy.getChildElementTypeId() != null && elementHierarchy.getChildElementId() != null
						&& elementHierarchy.getParentElementId() != null) {

					Pair<Long, Long> pair = new MutablePair<>(elementHierarchy.getChildElementTypeId(),
							elementHierarchy.getChildElementId());

					tubesHierachies.computeIfAbsent(pair, k -> new ArrayList<Long>());
					tubesHierachies.get(pair).add(elementHierarchy.getParentElementId());
				}
			}

		}
	}

}
