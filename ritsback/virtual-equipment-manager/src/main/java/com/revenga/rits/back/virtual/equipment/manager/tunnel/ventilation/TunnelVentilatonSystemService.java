package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetCreateIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetElementMeasuresChangeCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetAlarmsToVentilationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToVentilationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToVentilationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansResetTotalHoursCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStartCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStartReverseCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStopCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetFireAlarmCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationModeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationRegimeCommand;
import com.revenga.rits.back.data.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.virtual.equipment.manager.helper.SetElementMeasuresChangeCommandHelper;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.SmConfig;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.ElectricalPanelEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.FanEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.TunnelVentilationEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor.CountFanOperatingHoursTask;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor.FansProcessor;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor.RegimesProcessor;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor.VentilationElementValueProcessor;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TunnelVentilatonSystemService {

	private static TunnelVentilatonSystemService instance;

	@Getter
	private TubeEntityManager tubeEntityManager;
	private FansProcessor fansProcessor;
	private RegimesProcessor regimesProcessor;
	private VentilationElementValueProcessor elementValueProcessor;
	private TunnelVentilationEntityManager ventilationEntityManager;
	private ElectricalPanelEntityManager electricalPanelEntityManager;
	private FanEntityManager fanEntityManager;
	private Timer timer;

	private TunnelVentilatonSystemService() {

		this.tubeEntityManager = TubeEntityManager.getInstance();

		this.ventilationEntityManager = new TunnelVentilationEntityManager(
				EntityManager.getInstance().getElementWithValues(ElementType.ELEMENT_TYPE_VENTILATION));

		this.fanEntityManager = new FanEntityManager(
				EntityManager.getInstance().getElementWithValues(ElementType.ELEMENT_TYPE_FAN),
				EntityManager.getInstance().getsAlarmByElementType(ElementType.ELEMENT_TYPE_FAN));

		this.electricalPanelEntityManager = new ElectricalPanelEntityManager(
				EntityManager.getInstance().getElementWithValues(ElementType.ELEMENT_TYPE_ELECTRICAL_PANEL));

		this.fansProcessor = new FansProcessor(this.electricalPanelEntityManager, this.fanEntityManager,
				this.tubeEntityManager, this.ventilationEntityManager);

		this.elementValueProcessor = new VentilationElementValueProcessor(this, this.tubeEntityManager,
				this.ventilationEntityManager, this.fanEntityManager);

		this.regimesProcessor = new RegimesProcessor(this.fanEntityManager, this.tubeEntityManager);
	}

	public static TunnelVentilatonSystemService getInstance() {

		if (instance == null) {

			instance = new TunnelVentilatonSystemService();
		}

		return instance;
	}

	public List<Command> init() {

		List<Command> commands = null;

		if (fansProcessor != null) {

			fansProcessor.init();
		}

		launchTask(FanEntityManager.COUNT_FAN_OPERATING_HOURS_TASK_PERIOD);

		EntityManager.getInstance().initElements(ElementType.ELEMENT_TYPE_VENTILATION);

		List<Element> elementsVentilation = EntityManager.getInstance()
				.getElementWithValues(ElementType.ELEMENT_TYPE_VENTILATION);

		if (CollectionUtils.isNotEmpty(elementsVentilation)) {

			for (Element elementVentilation : elementsVentilation) {

				List<Command> cmdUpdateTubes = initSmProcessors(elementVentilation);

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

		if (regimesProcessor != null) {

			regimesProcessor.init();
		}

		return commands;
	}

	public void deInit() {

		if (fansProcessor != null) {

			fansProcessor.deInit();
		}

		if (elementValueProcessor != null) {

			elementValueProcessor.deInit();
		}

		if (regimesProcessor != null) {

			regimesProcessor.deInit();
		}

		if (timer != null) {

			timer.cancel();
		}
	}

	public void setFansStartCommand(SetFansStartCommand cmd) {

		setFansCommand(cmd.getElements(), FansProcessor.SEND_FAN_START);
	}

	public void setFansStopCommand(SetFansStopCommand cmd) {

		setFansCommand(cmd.getElements(), FansProcessor.SEND_FAN_STOP);
	}

	public void setFansStartReverseCommand(SetFansStartReverseCommand cmd) {

		setFansCommand(cmd.getElements(), FansProcessor.SEND_FAN_START_REVERSE);
	}

	public List<Command> setFansResetTotalHoursCommand(SetFansResetTotalHoursCommand cmd) {

		List<Command> commands = null;
		Set<Element> elements = cmd.getElements();

		List<Element> fanElements = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Element elemenTube = tubeEntityManager.getFirtTubeByChild(element.getElementTypeId(), element.getId());

				if (tubeEntityManager.isVentilationManualMode(elemenTube)) {

					log.info("RESET FAN TOTAL HOURS");

					Element elementToAdd = this.fanEntityManager.updateOperatingHours(0L, element);

					if (elementToAdd != null) {

						fanElements.add(elementToAdd);
					}

				} else {
					log.error("Ventilation mode in tube " + elemenTube.getId() + " must be manual (0)");
				}
			}
		}

		if (CollectionUtils.isNotEmpty(fanElements)) {

			SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = SetElementMeasuresChangeCommandHelper
					.get(fanElements);

			if (setElementMeasuresChangeCommand != null) {

				commands = new ArrayList<>();
				commands.add(setElementMeasuresChangeCommand);
			}
		}
		return commands;
	}

	public List<Command> setVentilationModeCommand(SetVentilationModeCommand cmd) {

		List<Command> commands = null;

		if (cmd.getElementId() != null && cmd.getElementTypeId() != null
				&& cmd.getElementTypeId().equals(ElementType.ELEMENT_TYPE_TUBE) && cmd.getMode() != null) {

			Element elementTube = new Element();
			elementTube.setId(cmd.getElementId());
			elementTube.setElementTypeId(cmd.getElementTypeId());

			SmConfig oldConfig = tubeEntityManager.getCurrentVentilationSmConfig(elementTube);

			Command command = tubeEntityManager.sendVentilationMode(elementTube, cmd.getMode());

			if (command != null) {

				Long regimePropoused = null;

				commands = new ArrayList<>();
				commands.add(command);

				SmConfig newConfig = tubeEntityManager.getCurrentVentilationSmConfig(elementTube);

				if (newConfig != null && newConfig.getStateMachineId() != null
						&& (oldConfig == null || oldConfig.getStateMachineId() == null
								|| !newConfig.getStateMachineId().equals(oldConfig.getStateMachineId()))) {

					regimePropoused = tubeEntityManager.initSmVentilationProcessor(elementTube);

					if (regimePropoused != null) {

						command = tubeEntityManager.sendVentilaionRegimePropoused(elementTube, regimePropoused);
						if (command != null) {

							commands.add(command);
						}
					}
				} else {

					regimePropoused = tubeEntityManager.getVentilationRegimeProposed(elementTube, null);
				}

				if (regimePropoused != null && newConfig != null) {

					Element elementVentilation = EntityManager.getInstance().getFirtParentByType(
							ElementType.ELEMENT_TYPE_VENTILATION, elementTube.getElementTypeId(), elementTube.getId());

					if (elementVentilation != null
							&& ventilationEntityManager.isState(elementVentilation, ElementTypeState.STATE_UP)
							&& (newConfig.getChangeCurrentRegime() == null
									|| newConfig.getChangeCurrentRegime().booleanValue())) {

						log.info(
								"Set current ventilation regime " + regimePropoused + " in tube " + cmd.getElementId());

						SetCreateIncidentReportCommand setCreateIncidentReportCommand = tubeEntityManager
								.setTargetRegime(elementTube, regimePropoused);

						if (setCreateIncidentReportCommand != null) {

							commands.add(setCreateIncidentReportCommand);
						}

					} else {

						log.info("Not set current ventilation regime " + regimePropoused + " in tube "
								+ cmd.getElementId());
						tubeEntityManager.setTargetRegime(elementTube, null);
					}
				} else {
					tubeEntityManager.setTargetRegime(elementTube, null);
				}
			}

		}
		return commands;
	}

	public List<Command> setVentilationRegimeCommand(SetVentilationRegimeCommand cmd) {

		List<Command> commands = null;

		if (cmd.getElementId() != null && cmd.getElementTypeId() != null
				&& cmd.getElementTypeId().equals(ElementType.ELEMENT_TYPE_TUBE) && cmd.getRegime() != null) {

			Element elementTube = new Element();
			elementTube.setId(cmd.getElementId());
			elementTube.setElementTypeId(cmd.getElementTypeId());

			Element elementVentilation = EntityManager.getInstance().getFirtParentByType(
					ElementType.ELEMENT_TYPE_VENTILATION, ElementType.ELEMENT_TYPE_TUBE, cmd.getElementId());

			if (ventilationEntityManager.isState(elementVentilation, ElementTypeState.STATE_UP)
					&& tubeEntityManager.isVentilationManualMode(elementTube)) {

				log.info("Change current ventilatio regime " + cmd.getRegime() + " in tube " + cmd.getElementId());

				SetCreateIncidentReportCommand setCreateIncidentReportCommand = tubeEntityManager
						.setTargetRegime(elementTube, cmd.getRegime());

				if (setCreateIncidentReportCommand != null) {

					commands = new ArrayList<>();
					commands.add(setCreateIncidentReportCommand);
				}

			} else {

				log.error("Ventilation mode in tube " + cmd.getElementId() + " must be manual (0) and in state up");
			}
		}

		return commands;
	}

	public List<Command> setAlarmsToVentilationCommand(SetAlarmsToVentilationCommand cmd) {

		List<Command> commands = null;

		if (CollectionUtils.isNotEmpty(cmd.getAlarms())) {

			for (Alarm alarm : cmd.getAlarms()) {

				if (alarm.getTargetElementTypeId() != null
						&& alarm.getTargetElementTypeId().equals(ElementType.ELEMENT_TYPE_FAN)) {

					fanEntityManager.updateAlarm(alarm.getTargetElementId(), null, alarm);
				}
			}
		}

		return commands;
	}

	public List<Command> setElementStatesToVentilationCommand(SetElementStatesToVentilationCommand cmd) {

		List<Command> commands = null;

		if (CollectionUtils.isNotEmpty(cmd.getElementStates())) {

			for (ElementState elementState : cmd.getElementStates()) {

				List<Command> commandsAux = null;

				Element elementVentilation = new Element();
				elementVentilation.setId(elementState.getElementId());
				elementVentilation.setElementTypeId(elementState.getElementTypeId());

				if (Long.valueOf(ElementTypeState.STATE_EXTRACTION).equals(elementState.getStateId())
						&& ventilationEntityManager.isState(elementVentilation, ElementTypeState.STATE_EVACUATION)) {

					commandsAux = extractionPhase(elementVentilation);

				} else if (Long.valueOf(ElementTypeState.STATE_STOPPED).equals(elementState.getStateId())
						|| Long.valueOf(ElementTypeState.STATE_UP).equals(elementState.getStateId())) {

					if (Long.valueOf(ElementTypeState.STATE_STOPPED).equals(elementState.getStateId())) {

						sendStopAllFan(elementVentilation);
					}

					// Update Ventilation
					Command cmdUpdateVentilation = ventilationEntityManager.updateVentilation(elementVentilation, null,
							elementState.getStateId(), null);

					if (cmdUpdateVentilation != null) {

						if (commandsAux == null) {

							commandsAux = new ArrayList<>();
						}
						commandsAux.add(cmdUpdateVentilation);
					}

					// Update Tubes
					ElementTypeState elementTypeState = new ElementTypeState();
					elementTypeState.setElementTypeId(ElementType.ELEMENT_TYPE_VENTILATION);
					elementTypeState.setStateId(elementState.getStateId());

					List<Command> cmdUpdateTubes = sendVentilationState(elementVentilation, elementTypeState);

					if (cmdUpdateTubes != null) {

						if (commandsAux == null) {

							commandsAux = new ArrayList<>();
						}
						commandsAux.addAll(cmdUpdateTubes);
					}

					setNullToTargetFans(elementVentilation);

					if (cmdUpdateTubes != null) {

						if (commandsAux == null) {

							commandsAux = new ArrayList<>();
						}
						commandsAux.addAll(cmdUpdateTubes);
					}

					// Init Processor in Tubes
					if (Long.valueOf(ElementTypeState.STATE_UP).equals(elementState.getStateId())) {

						cmdUpdateTubes = initSmProcessors(elementVentilation);

						if (cmdUpdateTubes != null) {

							if (commandsAux == null) {

								commandsAux = new ArrayList<>();
							}
							commandsAux.addAll(cmdUpdateTubes);
						}
					}

				} else {

					log.error("State change not allowed");
				}

				if (commandsAux != null) {

					if (commands == null) {

						commands = commandsAux;

					} else {

						commands.addAll(commandsAux);
					}
				}
			}
		}
		return commands;
	}

	public List<Command> setFireAlarmCommand(SetFireAlarmCommand cmd) {

		List<Command> commands = null;

		if (cmd != null && cmd.getElement() != null && cmd.getStretch() != null) {

			commands = evacuationPhase(cmd.getElement(), cmd.getStretch());
		}

		return commands;
	}

	public List<Command> setElementValuesToVentilationCommand(SetElementValuesToVentilationCommand cmd) {

		return elementValueProcessor.processValues(cmd.getElementValues());
	}

	private void launchTask(Long period) {

		if (timer == null) {

			timer = new Timer();
		}

		timer.schedule(new CountFanOperatingHoursTask(this.fanEntityManager), 0, period);
	}

	private void setFansCommand(Set<Element> elements, Long commandTypeId) {

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Element elemenTube = tubeEntityManager.getFirtTubeByChild(element.getElementTypeId(), element.getId());

				if (tubeEntityManager.isVentilationManualMode(elemenTube)) {

					Element elementElectricalPanel = EntityManager.getInstance().getFirtParentByType(
							ElementType.ELEMENT_TYPE_ELECTRICAL_PANEL, element.getElementTypeId(), element.getId());

					if (elementElectricalPanel != null) {

						SignallingCommand signallingCommand = SignallingCommandHelper.get(element.getId(),
								element.getElementTypeId(), commandTypeId);

						if (signallingCommand != null) {

							fansProcessor.addComand(elementElectricalPanel.getId(), elemenTube.getId(),
									signallingCommand);

							tubeEntityManager.setTargetRegime(elemenTube, null);
						}
					}

				} else {
					log.error("Ventilation mode in tube " + elemenTube.getId() + " must be manual (0)");
				}
			}
		}
	}

	private void setNullToTargetFans(final Element elemenVentilation) {

		if (elemenVentilation != null) {

			List<Element> elementTubes = EntityManager.getInstance().getChildByType(
					elemenVentilation.getElementTypeId(), elemenVentilation.getId(), ElementType.ELEMENT_TYPE_TUBE);

			if (CollectionUtils.isNotEmpty(elementTubes)) {

				for (Element elementTube : elementTubes) {

					tubeEntityManager.setTargetRegime(elementTube, null);
				}
			}
		}

	}

	private List<Command> sendVentilationState(final Element elemenVentilation,
			final ElementTypeState elementTypeState) {

		List<Command> commands = null;

		if (elemenVentilation != null) {

			List<Element> elementTubes = EntityManager.getInstance().getChildByType(
					elemenVentilation.getElementTypeId(), elemenVentilation.getId(), ElementType.ELEMENT_TYPE_TUBE);

			if (CollectionUtils.isNotEmpty(elementTubes)) {

				for (Element elementTube : elementTubes) {

					Command command = tubeEntityManager.sendVentilaionState(elementTube, elementTypeState);

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

	private List<Command> initSmProcessors(final Element elementVentilation) {

		List<Command> commands = null;

		if (elementVentilation != null) {

			List<Element> elementTubes = EntityManager.getInstance().getChildByType(
					elementVentilation.getElementTypeId(), elementVentilation.getId(), ElementType.ELEMENT_TYPE_TUBE);

			if (CollectionUtils.isNotEmpty(elementTubes)) {

				for (Element elementTube : elementTubes) {

					Command command = null;

					Long regimePropoused = tubeEntityManager.initSmVentilationProcessor(elementTube);

					if (regimePropoused != null) {

						command = tubeEntityManager.sendVentilaionRegimePropoused(elementTube, regimePropoused);

						if (command != null
								&& ventilationEntityManager.isState(elementVentilation, ElementTypeState.STATE_UP)) {

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
								tubeEntityManager.setTargetRegime(elementTube, null);

							}
						}
					}

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

	private void sendStopAllFan(final Element elementVentilation) {

		if (elementVentilation != null) {

			List<Element> elementTubes = EntityManager.getInstance().getChildByType(
					elementVentilation.getElementTypeId(), elementVentilation.getId(), ElementType.ELEMENT_TYPE_TUBE);

			if (CollectionUtils.isNotEmpty(elementTubes)) {

				for (Element elementTube : elementTubes) {

					List<Element> elementFans = EntityManager.getInstance().getChildByType(
							elementTube.getElementTypeId(), elementTube.getId(), ElementType.ELEMENT_TYPE_FAN);

					if (CollectionUtils.isNotEmpty(elementFans)) {

						for (Element elementFan : elementFans) {

							Element elementElectricalPanel = EntityManager.getInstance().getFirtParentByType(
									ElementType.ELEMENT_TYPE_ELECTRICAL_PANEL, elementFan.getElementTypeId(),
									elementFan.getId());

							if (elementElectricalPanel != null) {

								fansProcessor.addComand(elementElectricalPanel.getId(), elementTube.getId(),
										SignallingCommandHelper.get(elementFan, FansProcessor.SEND_FAN_STOP));
							}

						}
					}
				}
			}

		}

	}

	private List<Command> evacuationPhase(final Element elementVentilation, final Stretch stretch) {

		List<Command> commands = null;

		// Only STOP -> EVACUATION
		if (elementVentilation != null && stretch != null
				&& (ventilationEntityManager.isState(elementVentilation, ElementTypeState.STATE_STOPPED))) {

			ElementValue elementValueSpeed = ventilationEntityManager.getWindSpeedByVentilation(elementVentilation,
					stretch);

			log.info("Element value for wind speed in ventilation(" + elementVentilation.getId() + "): "
					+ elementValueSpeed);

			ElementValue elementValueDirection = ventilationEntityManager
					.getWindDirectionByVentilation(elementVentilation, stretch);

			log.info("Element value for wind direction in ventilation(" + elementVentilation.getId() + "): "
					+ elementValueDirection);

			Long windSpeedRangeId = ventilationEntityManager.getWindSpeedRange(elementVentilation, elementValueSpeed,
					elementValueDirection, stretch);

			if (windSpeedRangeId == null) {

				log.warn("Null value in  windSpeedRange, ventilation id"
						+ elementVentilation.getId());
			}
			
			Command command = null;
			commands = new ArrayList<>();

			// Update ventilation
			command = ventilationEntityManager.updateVentilation(elementVentilation, windSpeedRangeId,
					ElementTypeState.STATE_EVACUATION, stretch.getId());

			if (command != null) {

				commands.add(command);

				// Update fans
				List<Pair<Long, Long>> fanTostart = getFansToStartEvacuation(elementVentilation.getId());

				log.info("Send start fans " + fanTostart);
				this.fansProcessor.addCommands(fanTostart, null, FansProcessor.SEND_FAN_START);

				// Update tubes
				ElementTypeState elementTypeState = new ElementTypeState();
				elementTypeState.setElementTypeId(ElementType.ELEMENT_TYPE_VENTILATION);
				elementTypeState.setStateId(ElementTypeState.STATE_EVACUATION);
				List<Command> commandsAux = sendVentilationState(elementVentilation, elementTypeState);

				if (commandsAux != null) {

					commands.addAll(commandsAux);
				}

				setNullToTargetFans(elementVentilation);

				if (commandsAux != null) {
					commands.addAll(commandsAux);
				}
			}

		} else {

			log.error("Ventilation system must be stopped");
		}

		return commands;
	}

	private List<Pair<Long, Long>> getFansToStartEvacuation(Long ventilationId) {

		List<Pair<Long, Long>> fanTostart = null;
		Map<Long, Integer> nFansStillBeStarted = new HashedMap<>();

		Integer tagetFans = ventilationEntityManager.getTargetFans(ventilationId);

		if (tagetFans != null) {

			log.info("Target fans for evacuation: " + tagetFans + ", in ventilation: " + ventilationId);

			List<Long> candidates = ventilationEntityManager.getCandidatesFans(ventilationId);

			if (CollectionUtils.isNotEmpty(candidates)) {

				log.info("Fans candidates for start in evacuation: " + candidates + ", ventilation: " + ventilationId);

				for (int index = 0; index < candidates.size() && tagetFans > 0; index++) {

					if (fanEntityManager.isFanValidToStartInEmegency(candidates.get(index), null)) {

						Element elementElectricalPanel = EntityManager.getInstance().getFirtParentByType(
								ElementType.ELEMENT_TYPE_ELECTRICAL_PANEL, ElementType.ELEMENT_TYPE_FAN,
								candidates.get(index));

						if (elementElectricalPanel != null) {

							// Filter be electrical panel
							Integer nFans = nFansStillBeStarted.get(elementElectricalPanel.getId());

							if (nFans == null) {

								Integer maxNfanStarted = electricalPanelEntityManager
										.getMaxNfansStart(elementElectricalPanel.getId());

								Long fanStarted = fanEntityManager
										.getStartedFanByElectricalPanelMax(elementElectricalPanel.getId());

								if (ObjectUtils.allNotNull(maxNfanStarted, fanStarted)) {

									nFans = maxNfanStarted - fanStarted.intValue();
								}
							}

							if (nFans != null && nFans > 0) {

								log.info("You can still start " + nFans + " fans in the electrical panel "
										+ elementElectricalPanel.getId());

								if (fanTostart == null) {

									fanTostart = new ArrayList<>();
								}

								fanTostart
										.add(new MutablePair<>(elementElectricalPanel.getId(), candidates.get(index)));
								tagetFans--;
								nFans--;

								nFansStillBeStarted.put(elementElectricalPanel.getId(), nFans);

							} else {

								log.info("It is not allowed to start more fans in electrical panel "
										+ elementElectricalPanel.getId());
							}
						} else {
							log.error("Not found electrical panel from fan " + candidates.get(index));
						}
					}

				}
			} else {
				log.error("No fans candidates for evacuation in ventilation " + ventilationId);
			}

		} else {
			log.error("Target fans null for evacuation in ventilation " + ventilationId);
		}

		return fanTostart;
	}

	private List<Command> extractionPhase(final Element elementVentilation) {

		List<Command> commands = null;

		if (elementVentilation != null) {

			// Update Fans
			Map<Long, Integer> nFansStillBeStarted = new HashedMap<>();

			List<Pair<Long, Long>> fanTostart = getFansToStartExtraction(elementVentilation.getId(),
					nFansStillBeStarted);
			log.info("Send start fans " + fanTostart);

			this.fansProcessor.addCommands(fanTostart, null, FansProcessor.SEND_FAN_START);

			List<Pair<Long, Long>> fanTostartReverse = getFansToStartReverseExtraction(elementVentilation.getId(),
					nFansStillBeStarted);

			log.info("Send start reverse fans " + fanTostartReverse);
			this.fansProcessor.addCommands(fanTostartReverse, null, FansProcessor.SEND_FAN_START_REVERSE);

			// Update Ventilation
			Command command = ventilationEntityManager.updateVentilation(elementVentilation, null,
					ElementTypeState.STATE_EXTRACTION, null);

			// Update Tubes
			if (command != null) {

				ElementTypeState elementTypeState = new ElementTypeState();
				elementTypeState.setElementTypeId(ElementType.ELEMENT_TYPE_VENTILATION);
				elementTypeState.setStateId(ElementTypeState.STATE_EXTRACTION);

				commands = new ArrayList<>();
				commands.add(command);

				List<Command> commandsAux = sendVentilationState(elementVentilation, elementTypeState);

				if (commandsAux != null) {

					commands.addAll(commandsAux);
				}
			}
		}
		return commands;

	}

	private List<Pair<Long, Long>> getFansToStartExtraction(final Long ventilationId,
			Map<Long, Integer> nFansStillBeStarted) {

		List<Pair<Long, Long>> fanTostart = null;

		if (nFansStillBeStarted != null && ventilationId != null) {

			// Start all fans in affected tube
			List<Long> candidates = ventilationEntityManager.getCandidatesFans(ventilationId);

			if (CollectionUtils.isNotEmpty(candidates)) {

				log.info("Fans candidates for start in extraction: " + candidates + ", ventilation: " + ventilationId);
				for (Long candidateId : candidates) {

					if (fanEntityManager.isFanValidToStartInEmegency(candidateId, null)) {

						Element elementElectricalPanel = EntityManager.getInstance().getFirtParentByType(
								ElementType.ELEMENT_TYPE_ELECTRICAL_PANEL, ElementType.ELEMENT_TYPE_FAN, candidateId);

						if (elementElectricalPanel != null) {

							// Filter be electrical panel
							Integer nFans = nFansStillBeStarted.get(elementElectricalPanel.getId());

							if (nFans == null) {

								Integer maxNfanStarted = electricalPanelEntityManager
										.getMaxNfansStart(elementElectricalPanel.getId());

								Long fanStarted = fanEntityManager
										.getStartedFanByElectricalPanelMax(elementElectricalPanel.getId());

								if (ObjectUtils.allNotNull(maxNfanStarted, fanStarted)) {

									nFans = maxNfanStarted - fanStarted.intValue();
								}
							}

							if (nFans != null && nFans > 0) {

								log.info("You can still start " + nFans + " fans in the electrical panel "
										+ elementElectricalPanel.getId());

								if (fanTostart == null) {

									fanTostart = new ArrayList<>();
								}

								fanTostart.add(new MutablePair<>(elementElectricalPanel.getId(), candidateId));
								nFans--;

								nFansStillBeStarted.put(elementElectricalPanel.getId(), nFans);

							} else {

								log.info("It is not allowed to start more fans in electrical panel "
										+ elementElectricalPanel.getId());
							}

						} else {
							log.error("Not found electrical panel from fan " + candidateId);
						}

					}
				}

			} else {
				log.error("No fans candidates for extraction in ventilation " + ventilationId);
			}

		}

		return fanTostart;
	}

	private List<Pair<Long, Long>> getFansToStartReverseExtraction(final Long ventilationId,
			Map<Long, Integer> nFansStillBeStarted) {

		List<Pair<Long, Long>> fanTostart = null;

		if (nFansStillBeStarted != null && ventilationId != null) {

			// Start fans in other tubes
			List<List<Long>> candidates = ventilationEntityManager.getCandidatesFansOtherTubes(ventilationId);

			if (CollectionUtils.isNotEmpty(candidates)) {

				for (List<Long> tubesCandidate : candidates) {

					if (CollectionUtils.isNotEmpty(tubesCandidate)) {

						log.info("Fans candidates for start in tube not affected: " + tubesCandidate + ", ventilation: "
								+ ventilationId);

						Integer tagetFansOther = ventilationEntityManager.getTargetFansOtherTube(ventilationId);

						if (tagetFansOther != null) {

							log.info("Target fans in tube not affected: " + tagetFansOther + " in ventilation: "
									+ ventilationId);

							for (int index = 0; index < tubesCandidate.size() && tagetFansOther > 0; index++) {

								if (fanEntityManager.isFanValidToStartInEmegency(tubesCandidate.get(index), null)) {

									Element elementElectricalPanel = EntityManager.getInstance().getFirtParentByType(
											ElementType.ELEMENT_TYPE_ELECTRICAL_PANEL, ElementType.ELEMENT_TYPE_FAN,
											tubesCandidate.get(index));

									if (elementElectricalPanel != null) {

										// Filter be electrical panel
										Integer nFans = nFansStillBeStarted.get(elementElectricalPanel.getId());

										if (nFans == null) {

											Integer maxNfanStarted = electricalPanelEntityManager
													.getMaxNfansStart(elementElectricalPanel.getId());

											Long fanStarted = fanEntityManager
													.getStartedFanByElectricalPanelMax(elementElectricalPanel.getId());

											if (ObjectUtils.allNotNull(maxNfanStarted, fanStarted)) {

												nFans = maxNfanStarted - fanStarted.intValue();
											}
										}

										if (nFans != null && nFans > 0) {

											log.info("You can still start " + nFans + " fans in the electrical panel "
													+ elementElectricalPanel.getId());

											if (fanTostart == null) {

												fanTostart = new ArrayList<>();
											}

											fanTostart.add(new MutablePair<>(elementElectricalPanel.getId(),
													tubesCandidate.get(index)));

											tagetFansOther--;
											nFans--;

											nFansStillBeStarted.put(elementElectricalPanel.getId(), nFans);

										} else {

											log.info("It is not allowed to start more fans in electrical panel "
													+ elementElectricalPanel.getId());
										}

									} else {
										log.error("Not found electrical panel from fan " + tubesCandidate.get(index));
									}
								}
							}

						} else {
							log.error("Target fans in not affected tube is null in ventilation " + ventilationId);
						}

					} else {
						log.error("No fans candidates in not affected tube in ventilation " + ventilationId);
					}
				}

			} else {
				log.info("There are not tubes unaffected in ventilation " + ventilationId);
			}
		}

		return fanTostart;
	}
}
