package com.revenga.rits.back.virtual.equipment.manager.tunnel.illumination;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetAlarmsToIlluminationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOffCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOnCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetDaliIlluminationRegimeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToIlluminationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToIlluminationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationModeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationRegimeCommand;
import com.revenga.rits.back.data.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.illumination.processor.IlluminationValueProcessor;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.RegimeConfig;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.SmConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TunnelIlluminationSystemService {

	protected static final String FILE_CONSTANTS = "constants.properties";

	public static final long SEND_CIRCUIT_OFF = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.type.send_circuit_off"));

	public static final long SEND_CIRCUIT_ON = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.type.send_circuit_on"));

	private TubeEntityManager tubeEntityManager;
	private DaliEntityManager daliEntityManager;
	private IlluminationValueProcessor elementValueProcessor;

	GenericExecutor genericExecutor;

	public TunnelIlluminationSystemService(TunnelIlluminationSystemExecutor incidentExecutor) {

		this.genericExecutor = incidentExecutor;
		this.tubeEntityManager = TubeEntityManager.getInstance();

		this.daliEntityManager = new DaliEntityManager(
				EntityManager.getInstance().getElementWithValues(ElementType.ELEMENT_TYPE_DALI));

		this.elementValueProcessor = new IlluminationValueProcessor(this, this.tubeEntityManager);
	}

	public List<Command> init() {

		List<Command> commands = null;

		EntityManager.getInstance().initElements(ElementType.ELEMENT_TYPE_ILLUMINATION);

		List<Element> elementsIllumination = EntityManager.getInstance()
				.getElements(ElementType.ELEMENT_TYPE_ILLUMINATION);

		if (CollectionUtils.isNotEmpty(elementsIllumination)) {

			for (Element elementIllumination : elementsIllumination) {

				List<Command> cmdUpdateTubes = initSmProcessors(elementIllumination);

				if (cmdUpdateTubes != null) {

					if (commands == null) {

						commands = new ArrayList<>();
					}
					commands.addAll(cmdUpdateTubes);
				}
			}
		}

		if (elementValueProcessor != null) {

			elementValueProcessor.init();
		}

		initMeasures();
		
		return commands;
	}

	public void deInit() {

		if (elementValueProcessor != null) {

			elementValueProcessor.deInit();
		}

	}

	public List<Command> setIlluminationModeCommand(SetIlluminationModeCommand cmd) {

		List<Command> commands = null;

		if (cmd.getElementId() != null && cmd.getElementTypeId() != null
				&& cmd.getElementTypeId().equals(ElementType.ELEMENT_TYPE_TUBE) && cmd.getMode() != null) {
			
			Element elementTube = new Element();
			elementTube.setId(cmd.getElementId());
			elementTube.setElementTypeId(cmd.getElementTypeId());

			SmConfig oldConfig = tubeEntityManager.getCurrentIlluminationSmConfig(elementTube);

			Command command = tubeEntityManager.sendIlluminationMode(elementTube, cmd.getMode());

			if (command != null) {

				Long regimePropoused = null;

				commands = new ArrayList<>();
				commands.add(command);

				SmConfig newConfig = tubeEntityManager.getCurrentIlluminationSmConfig(elementTube);

				if (newConfig != null && newConfig.getStateMachineId() != null
						&& (oldConfig == null || oldConfig.getStateMachineId() == null
								|| !newConfig.getStateMachineId().equals(oldConfig.getStateMachineId()))) {

					regimePropoused = tubeEntityManager.initSmIlluminationProcessor(elementTube);

					if (regimePropoused != null) {

						command = tubeEntityManager.sendIlluminationRegimePropoused(elementTube, regimePropoused);

						if (command != null) {
							commands.add(command);
						}
					}
					
					initMeasures();
				} else {

					regimePropoused = tubeEntityManager.getIlluminationRegimeProposed(elementTube, null);
				}

				if (regimePropoused != null && newConfig != null && (newConfig.getChangeCurrentRegime() == null
						|| newConfig.getChangeCurrentRegime().booleanValue())) {

					List<Command> setRegimeCommands = setRegime(elementTube, regimePropoused);
					log.info(
							"Change current illumination regime " + regimePropoused + " in tube " + cmd.getElementId());

					if (CollectionUtils.isNotEmpty(setRegimeCommands)) {

						commands.addAll(setRegimeCommands);
					}
				} else {
					log.info("Not change current illumination regime " + regimePropoused + " in tube "
							+ cmd.getElementId());
				}
			}
			
			
		}
		return commands;
	}

	public List<Command> setIlluminationRegimeCommand(SetIlluminationRegimeCommand cmd) {

		List<Command> commands = null;

		if (cmd.getElementId() != null && cmd.getElementTypeId() != null
				&& cmd.getElementTypeId().equals(ElementType.ELEMENT_TYPE_TUBE) && cmd.getRegime() != null) {

			Element elementTube = new Element();
			elementTube.setId(cmd.getElementId());
			elementTube.setElementTypeId(cmd.getElementTypeId());

			if (tubeEntityManager.isIlluminationManualMode(elementTube)) {

				commands = setRegime(elementTube, cmd.getRegime());
				log.info("Change current illumination regime " + cmd.getRegime() + " in tube " + cmd.getElementId());

				if (CollectionUtils.isNotEmpty(commands)) {

					List<Command> commandsDaliRegimeToNull = sendDaliRegimeToNull(elementTube);

					if (CollectionUtils.isNotEmpty(commandsDaliRegimeToNull)) {

						commands.addAll(commandsDaliRegimeToNull);
					}
				}
			} else {

				log.error("Illumination mode in tube " + cmd.getElementId() + " must be manual (0)");
			}

		}
		return commands;
	}

	public List<Command> SetAlarmsToIlluminationCommand(SetAlarmsToIlluminationCommand cmd) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Command> SetElementStatesToIlluminationCommand(SetElementStatesToIlluminationCommand cmd) {

		List<Command> commandsToSend = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(cmd.getElementStates())) {

			for (ElementState elementState : cmd.getElementStates()) {

				if (elementState != null && elementState.getElementId() != null
						&& elementState.getElementTypeId() != null) {

					List<Long> tubeIds = this.tubeEntityManager.getTubesByChild(elementState.getElementTypeId(),
							elementState.getElementId());

					if (CollectionUtils.isNotEmpty(tubeIds)) {

						for (Long elementTubeId : tubeIds) {

							if (elementTubeId != null) {

								List<Command> commands = checkElementInCurrentRegime(
										new Element(ElementType.ELEMENT_TYPE_TUBE, elementTubeId),
										new Element(elementState.getElementTypeId(), elementState.getElementId()));

								if (CollectionUtils.isNotEmpty(commands)) {

									commandsToSend.addAll(commands);
								}

							}
						}
					}
				}
			}
		}
		return commandsToSend;
	}

	public void SetElementValuesToIlluminationCommand(SetElementValuesToIlluminationCommand cmd) {

		elementValueProcessor.processValues(cmd.getElementValues());
	}

	public List<Command> SetCircuitsOffCommand(SetCircuitsOffCommand cmd) {
		return setCircuitCommand(cmd.getElements(), SEND_CIRCUIT_OFF);
	}

	public List<Command> SetCircuitsOnCommand(SetCircuitsOnCommand cmd) {
		return setCircuitCommand(cmd.getElements(), SEND_CIRCUIT_ON);
	}

	public List<Command> SetDaliIlluminationRegimeCommand(SetDaliIlluminationRegimeCommand cmd) {

		List<Command> commands = null;

		if (cmd.getElementId() != null && cmd.getElementTypeId() != null
				&& cmd.getElementTypeId().equals(ElementType.ELEMENT_TYPE_DALI) && cmd.getRegime() != null) {

			Element elemenTube = tubeEntityManager.getFirtTubeByChild(cmd.getElementTypeId(), cmd.getElementId());

			if (elemenTube != null && tubeEntityManager.isIlluminationManualMode(elemenTube)) {

				Element elemenDali = new Element();
				elemenDali.setElementTypeId(cmd.getElementTypeId());
				elemenDali.setId(cmd.getElementId());

				RegimeConfig config = daliEntityManager.getRegimeConfig(elemenDali, cmd.getRegime());

				if (config != null && CollectionUtils.isNotEmpty(config.getSignallingCommands())) {

					Command command = daliEntityManager.sendRegime(elemenDali, cmd.getRegime());

					if (command != null) {

						commands = new ArrayList<>();
						commands.add(command);

						for (SignallingCommand signallingCommand : config.getSignallingCommands()) {

							if (signallingCommand != null) {

								commands.add(signallingCommand);
							}
						}

						command = tubeEntityManager.sendIlluminationRegimeToNull(elemenTube);

						if (command != null) {

							commands.add(command);
						}
					}
				}

			} else {

				log.error("Illumination mode in tube " + elemenTube.getId() + " must be manual (0)");
			}

		}
		return commands;
	}

	public List<Command> setRegime(final Element elementTube, final Long regime) {

		List<Command> commands = null;

		if (elementTube != null && regime != null) {

			RegimeConfig config = tubeEntityManager.getIlluminationRegimeConfig(elementTube, regime);

			if (config != null && CollectionUtils.isNotEmpty(config.getSignallingCommands())) {

				Command command = tubeEntityManager.sendIlluminationRegime(elementTube, regime);

				if (command != null) {

					commands = new ArrayList<>();
					commands.add(command);

					for (SignallingCommand signallingCommand : config.getSignallingCommands()) {

						if (signallingCommand != null) {

							commands.add(signallingCommand);
						}
					}
				}
			}
		}

		return commands;
	}

	public List<Command> checkElementInCurrentRegime(final Element elementTube, final Element element) {

		List<Command> commands = null;

		if (elementTube != null && element != null) {

			RegimeConfig config = tubeEntityManager.getCurrentIlluminationRegimeConfig(elementTube);

			if (config != null && CollectionUtils.isNotEmpty(config.getSignallingCommands())) {

				commands = config.getSignallingCommands().stream()
						.filter(e -> e.getElementTypeId() != null
								&& e.getElementTypeId().equals(element.getElementTypeId()) && e.getElementId() != null
								&& e.getElementId().equals(element.getId()))
						.collect(Collectors.toList());
			}
		}

		return commands;
	}

	public void initMeasures() {

		List<Element> elementsIllumination = EntityManager.getInstance()
				.getElements(ElementType.ELEMENT_TYPE_ILLUMINATION);

		if (CollectionUtils.isNotEmpty(elementsIllumination)) {

			Set<ElementTypeParam> elementTypeParams = null;

			for (Element elementIllumination : elementsIllumination) {

				Set<ElementTypeParam> elementTypeParamsTemp = getIlluminationElementTypeParam(elementIllumination);

				if (CollectionUtils.isNotEmpty(elementTypeParamsTemp)) {

					if (elementTypeParams == null) {

						elementTypeParams = elementTypeParamsTemp;

					} else {

						elementTypeParams.addAll(elementTypeParamsTemp);
					}
				}
			}

			if (CollectionUtils.isNotEmpty(elementTypeParams)) {

				log.info("InitMeasures with elementTypeParams " + elementTypeParams);
				
				Set<ElementValue> elementValues = new HashSet<>();

				for (ElementTypeParam elementTypeParam : elementTypeParams) {


					List<ElementValue> elementValuesTemp = EntityManager.getInstance().getByElementTypeParam(
							elementTypeParam.getElementTypeId(), elementTypeParam.getId(),
							elementTypeParam.getParamTypeId());

					if (CollectionUtils.isNotEmpty(elementValuesTemp)) {

						elementValuesTemp.forEach(elementValue -> {

							if (elementValue.getValue() != null && (elementValue.getElementValueStateId() == null
									|| elementValue.getElementValueStateId().equals(ElementValueState.STATE_ONLINE)
									|| elementValue.getElementValueStateId().equals(ElementValueState.STATE_ALERTED)))

								elementValues.add(elementValue);
						});

					}
				}

				if (CollectionUtils.isNotEmpty(elementValues)) {
					
					elementValueProcessor.processValues(elementValues);
				}

			}
		}

	}

	private List<Command> setCircuitCommand(Set<Element> elements, Long commandTypeId) {

		List<Command> commands = null;

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Element elemenTube = tubeEntityManager.getFirtTubeByChild(element.getElementTypeId(), element.getId());

				if (tubeEntityManager.isIlluminationManualMode(elemenTube)) {

					SignallingCommand signallingCommand = SignallingCommandHelper.get(element.getId(),
							element.getElementTypeId(), commandTypeId);

					if (signallingCommand != null) {

						if (commands == null) {

							commands = new ArrayList<>();
						}

						commands.add(signallingCommand);

						Command command = tubeEntityManager.sendIlluminationRegimeToNull(elemenTube);

						if (command != null) {

							commands.add(command);
						}
					}

				} else {
					log.error("Illumination mode in tube " + elemenTube.getId() + " must be manual (0)");
				}
			}
		}

		return commands;
	}

	private List<Command> sendDaliRegimeToNull(final Element elementTube) {

		List<Command> commands = null;

		if (elementTube != null) {

			List<Element> elementDalis = EntityManager.getInstance().getChildByType(elementTube.getElementTypeId(),
					elementTube.getId(), ElementType.ELEMENT_TYPE_DALI);

			if (CollectionUtils.isNotEmpty(elementDalis)) {

				for (Element elementDali : elementDalis) {

					Command command = daliEntityManager.sendRegimeToNull(elementDali);

					if (command != null) {

						if (commands == null) {

							commands = new ArrayList<>();
						}
						commands.add(command);
					}
				}
			}
		}

		return commands;
	}

	private List<Command> initSmProcessors(final Element elementIllumination) {

		List<Command> commands = null;

		if (elementIllumination != null) {

			List<Element> elementTubes = EntityManager.getInstance().getChildByType(
					elementIllumination.getElementTypeId(), elementIllumination.getId(), ElementType.ELEMENT_TYPE_TUBE);

			if (CollectionUtils.isNotEmpty(elementTubes)) {

				for (Element elementTube : elementTubes) {

					Command command = null;
					List<Command> setRegimeCommands = null;

					Long regimePropoused = tubeEntityManager.initSmIlluminationProcessor(elementTube);

					if (regimePropoused != null) {

						command = tubeEntityManager.sendIlluminationRegimePropoused(elementTube, regimePropoused);

						SmConfig config = tubeEntityManager.getCurrentIlluminationSmConfig(elementTube);

						if (config != null && (config.getChangeCurrentRegime() == null
								|| config.getChangeCurrentRegime().booleanValue())) {

							setRegimeCommands = setRegime(elementTube, regimePropoused);
							log.info("Change current illumination regime " + regimePropoused + " in tube "
									+ elementTube.getId());
						} else {
							log.info("Not change current illumination regime " + regimePropoused + " in tube "
									+ elementTube.getId());
						}
					}

					if (command != null) {

						if (commands == null) {

							commands = new ArrayList<>();
						}
						commands.add(command);

						if (CollectionUtils.isNotEmpty(setRegimeCommands)) {

							commands.addAll(setRegimeCommands);
						}
					}
				}
			}

		}

		return commands;
	}

	private Set<ElementTypeParam> getIlluminationElementTypeParam(final Element elementIllumination) {

		Set<ElementTypeParam> elementTypeParams = null;

		if (elementIllumination != null) {

			List<Element> elementTubes = EntityManager.getInstance().getChildByType(
					elementIllumination.getElementTypeId(), elementIllumination.getId(), ElementType.ELEMENT_TYPE_TUBE);

			if (CollectionUtils.isNotEmpty(elementTubes)) {

				for (Element elementTube : elementTubes) {

					Set<ElementTypeParam> elementTypeParamsTemp = tubeEntityManager
							.getIlluminationElementTypeParam(elementTube);

					if (CollectionUtils.isNotEmpty(elementTypeParamsTemp)) {

						if (elementTypeParams == null) {

							elementTypeParams = elementTypeParamsTemp;

						} else {

							elementTypeParams.addAll(elementTypeParamsTemp);
						}
					}
				}
			}
		}
		return elementTypeParams;
	}

}
