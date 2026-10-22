package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.ElectricalPanelEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.FanEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.TunnelVentilationEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor.FansProcessor.FanCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FansProcessorThread implements Runnable {

	public static final Integer MAX_TIME_TO_PROCESS = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty("application.properties", "tunnel.ventilation.max_time_to_process"));

	private Set<FanCommand> commands;
	private ElectricalPanelEntityManager electricalPanelEntityManager;
	private FanEntityManager fanEntityManager;
	private TunnelVentilationEntityManager tunnelVentilationEntityManager;

	private boolean stop;
	private boolean alive;

	public FansProcessorThread(final ElectricalPanelEntityManager electricalPanelEntityManager,
			final FanEntityManager fanEntityManager, TunnelVentilationEntityManager tunnelVentilationEntityManager,
			Set<FanCommand> commands) {

		super();
		this.electricalPanelEntityManager = electricalPanelEntityManager;
		this.fanEntityManager = fanEntityManager;
		this.tunnelVentilationEntityManager = tunnelVentilationEntityManager;
		this.commands = commands;
	}

	private void processSignalling() {

		if (CollectionUtils.isNotEmpty(commands)) {

			List<SignallingCommand> commandToSends = new ArrayList<>();
			List<FanCommand> commandToRemove = new ArrayList<>();

			synchronized (commands) {

				Long now = Instant.now().toEpochMilli();

				for (FanCommand command : commands) {

					if (command.getCommand() == null || command.getElectricalPanelId() == null) {

						commandToRemove.add(command);

					} else {

						// Remove command that spired process time
						if (command.getCommand().getCreatedAt() == null
								|| now - command.getCommand().getCreatedAt() > MAX_TIME_TO_PROCESS) {

							log.info("Process time has spired in command " + command);
							commandToRemove.add(command);

							// FAN_STATE_VALUE_XXXXX --> FAN_STATE_VALUE_STOP
						} else if (command.getCommand().getSignallingCommandId().equals(FansProcessor.SEND_FAN_STOP)) {

							if (!fanEntityManager.isFanState(command.getCommand().getElementId(),
									command.getElectricalPanelId(), FanEntityManager.FAN_STATE_VALUE_STOP)) {

								commandToSends.add(command.getCommand());
								fanEntityManager.updateFanState(command.getCommand().getElementId(),
										command.getElectricalPanelId(), FanEntityManager.FAN_STATE_VALUE_STOP, false);
							}

							commandToRemove.add(command);

						} else {

							Long nStartedFans = fanEntityManager
									.getStartedFanByElectricalPanel(command.getElectricalPanelId());
							Element elementElectricalPanel = new Element(ElementType.ELEMENT_TYPE_ELECTRICAL_PANEL,
									command.getElectricalPanelId());

							Long ventilationId = fanEntityManager.getVentilationByFans()
									.get(command.getCommand().getElementId());

							Long nStartedFansInVentilation = ventilationId != null
									? fanEntityManager.getStartedFanByVentilation(ventilationId)
									: null;

							// FAN_STATE_VALUE_REVERSE --> FAN_STATE_VALUE_START || FAN_STATE_VALUE_START
							// --> FAN_STATE_VALUE_REVERSE
							if ((command.getCommand().getSignallingCommandId().equals(FansProcessor.SEND_FAN_START)
									&& fanEntityManager.isFanState(command.getCommand().getElementId(),
											command.getElectricalPanelId(), FanEntityManager.FAN_STATE_VALUE_REVERSE))
									|| (command.getCommand().getSignallingCommandId()
											.equals(FansProcessor.SEND_FAN_START_REVERSE)
											&& fanEntityManager.isFanState(command.getCommand().getElementId(),
													command.getElectricalPanelId(),
													FanEntityManager.FAN_STATE_VALUE_START))) {

								SignallingCommand stopCmd = SignallingCommandHelper.get(
										command.getCommand().getElementId(), command.getCommand().getElementTypeId(),
										FansProcessor.SEND_FAN_STOP);

								if (stopCmd != null) {

									commandToSends.add(stopCmd);
									fanEntityManager.updateFanState(command.getCommand().getElementId(),
											command.getElectricalPanelId(), FanEntityManager.FAN_STATE_VALUE_STOP,
											false);
								}

								// FAN_STATE_VALUE_REVERSE --> !FAN_STATE_VALUE_REVERSE || FAN_STATE_VALUE_START
								// --> !FAN_STATE_VALUE_START || FAN_STATE_VALUE_STOP --> !FAN_STATE_VALUE_STOP
							} else if ((electricalPanelEntityManager.checkStartFans(elementElectricalPanel,
									nStartedFans))
									&& tunnelVentilationEntityManager.checkStartFans(ventilationId,
											nStartedFansInVentilation)
									&& ((command.getCommand().getSignallingCommandId()
											.equals(FansProcessor.SEND_FAN_START)
											&& !fanEntityManager.isFanState(command.getCommand().getElementId(),
													command.getElectricalPanelId(),
													FanEntityManager.FAN_STATE_VALUE_START))
											|| (command.getCommand().getSignallingCommandId()
													.equals(FansProcessor.SEND_FAN_START_REVERSE)
													&& !fanEntityManager.isFanState(command.getCommand().getElementId(),
															command.getElectricalPanelId(),
															FanEntityManager.FAN_STATE_VALUE_REVERSE)))) {

								if (fanEntityManager.checkSendCommand(command.getCommand().getElementId(),
										command.getElectricalPanelId())
										&& electricalPanelEntityManager.acquire(command.getElectricalPanelId(),
												nStartedFans.intValue())) {

									if (command.getCommand().getSignallingCommandId()
											.equals(FansProcessor.SEND_FAN_START)) {

										fanEntityManager.updateFanState(command.getCommand().getElementId(),
												command.getElectricalPanelId(), FanEntityManager.FAN_STATE_VALUE_START,
												false);

									} else if (command.getCommand().getSignallingCommandId()
											.equals(FansProcessor.SEND_FAN_START_REVERSE)) {

										fanEntityManager.updateFanState(command.getCommand().getElementId(),
												command.getElectricalPanelId(),
												FanEntityManager.FAN_STATE_VALUE_REVERSE, false);
									}

									commandToSends.add(command.getCommand());
									commandToRemove.add(command);
								}
							} else {
								// COMMAND DISCARTED not check start fans
								commandToRemove.add(command);
							}
						}
					}
				}
				commands.removeAll(commandToRemove);
			}

			if (CollectionUtils.isNotEmpty(commandToSends)) {

				int n = 0;

				for (SignallingCommand command : commandToSends) {

					if (EntityManager.getInstance().sendCommand(command) != null) {
						n++;

					} else {
						log.error("Sending command --> " + command);
					}
				}
				log.info(n + " commands sent");
				log.info(commands.size() + " commands in list");
			}
		}
	}

	@Override
	public void run() {

		try {
			log.info(Thread.currentThread().getName() + " is running");

			while (!stop) {

				this.fanEntityManager.checkNotConfirmedFans();
				processSignalling();
				Thread.sleep(100);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.error(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();

		} finally {

			this.setAlive(false);

			log.info(Thread.currentThread().getName() + " is stopped");
		}
	}

	public void stop() {

		this.stop = true;
	}

	public boolean isAlive() {

		return alive;
	}

	public void setAlive(boolean alive) {

		this.alive = alive;
	}

	public boolean getStop() {

		return this.stop;
	}
}
