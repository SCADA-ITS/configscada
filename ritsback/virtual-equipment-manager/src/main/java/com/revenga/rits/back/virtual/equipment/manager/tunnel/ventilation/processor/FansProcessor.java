package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.ElectricalPanelEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.FanEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.TunnelVentilationEntityManager;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FansProcessor {

	@Data
	public class FanCommand {

		private Long electricalPanelId;
		private Long tubeId;
		private SignallingCommand command;
	}

	private Set<FanCommand> commands;
	private ElectricalPanelEntityManager electricalPanelEntityManager;
	private FanEntityManager fanEntityManager;
	private TunnelVentilationEntityManager tunnelVentilationEntityManager;
	private FansProcessorThread fansProcessorThread;
	private TargetFansProcessorThread targetFansProcessorThread;
	private TubeEntityManager tubeEntityManager;

	protected static final String FILE_CONSTANTS = "constants.properties";

	public static final long SEND_FAN_STOP = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.type.send_fan_stop"));

	public static final long SEND_FAN_START = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.type.send_fan_start"));

	public static final long SEND_FAN_START_REVERSE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.type.send_fan_start_reverse"));

	public static final Integer MAX_COMMANDS_IN_LIST = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty("application.properties", "tunnel.ventilation.max_commands_in_list"));

	public FansProcessor(ElectricalPanelEntityManager electricalPanelEntityManager, FanEntityManager fanEntityManager,
			TubeEntityManager tubeEntityManager, TunnelVentilationEntityManager tunnelVentilationEntityManager) {

		commands = Collections.synchronizedSet(new LinkedHashSet<>());
		this.electricalPanelEntityManager = electricalPanelEntityManager;
		this.fanEntityManager = fanEntityManager;
		this.tubeEntityManager = tubeEntityManager;
		this.tunnelVentilationEntityManager = tunnelVentilationEntityManager;
	}

	private Boolean initFansProcessorThread() {

		Boolean ok = true;

		if (fansProcessorThread != null) {

			fansProcessorThread.stop();
		}

		fansProcessorThread = new FansProcessorThread(electricalPanelEntityManager, fanEntityManager, tunnelVentilationEntityManager, commands);

		String threadNameService = "Thread" + fansProcessorThread.getClass().getSimpleName();

		(new Thread(fansProcessorThread, threadNameService)).start();

		return ok;
	}

	private Boolean initTargetFansProcessorThread() {

		Boolean ok = true;

		if (targetFansProcessorThread != null) {

			targetFansProcessorThread.stop();
		}

		targetFansProcessorThread = new TargetFansProcessorThread(this, fanEntityManager, tubeEntityManager,
				electricalPanelEntityManager);

		String threadNameService = "Thread" + targetFansProcessorThread.getClass().getSimpleName();

		(new Thread(targetFansProcessorThread, threadNameService)).start();

		return ok;
	}

	private Boolean deInitFansProcessorThread() {

		Boolean ok = true;

		if (fansProcessorThread != null) {

			try {
				fansProcessorThread.stop();

				Instant start;
				Duration timeElapsed;
				boolean stopped;
				start = Instant.now();

				do {
					stopped = !fansProcessorThread.isAlive();
					Thread.sleep(1L);

					timeElapsed = Duration.between(start, Instant.now());

				} while (timeElapsed.toMillis() < 5000L && !stopped);
			}

			catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
				ok = false;
			}
		}
		return ok;
	}

	private Boolean deInitTargetFansProcessorThread() {

		Boolean ok = true;

		if (targetFansProcessorThread != null) {

			try {
				targetFansProcessorThread.stop();

				Instant start;
				Duration timeElapsed;
				boolean stopped;
				start = Instant.now();

				do {
					stopped = !targetFansProcessorThread.isAlive();
					Thread.sleep(1L);

					timeElapsed = Duration.between(start, Instant.now());

				} while (timeElapsed.toMillis() < 5000L && !stopped);
			}

			catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
				ok = false;
			}
		}
		return ok;
	}

	public Boolean init() {

		return initFansProcessorThread() && initTargetFansProcessorThread();
	}

	public Boolean deInit() {

		Boolean resp = deInitFansProcessorThread();
		resp = deInitTargetFansProcessorThread() && resp;

		return resp;
	}

	public void addComand(final Long electricalPanelId, final Long tubeId, final SignallingCommand signallingCommand) {

		if (electricalPanelId != null && signallingCommand != null
				&& commands.size() < MAX_COMMANDS_IN_LIST.intValue()) {

			FanCommand command = new FanCommand();
			command.setCommand(signallingCommand);
			command.setTubeId(tubeId);
			command.setElectricalPanelId(electricalPanelId);

			commands.add(command);

			log.info("Command:" + command + " added in FansProcessor");

		} else {

			log.error("The queue reached the maximum number of elements");
		}
	}

	public void addCommands(final List<Pair<Long, Long>> fans, final Long tubeId, final Long commandElementTypeId) {

		if (commandElementTypeId != null && CollectionUtils.isNotEmpty(fans)) {

			for (Pair<Long, Long> fan : fans) {

				Long electricalPanelId = fan.getLeft();
				Long fanId = fan.getRight();

				if (electricalPanelId != null && fanId != null) {

					addComand(electricalPanelId, tubeId,
							SignallingCommandHelper.get(fanId, ElementType.ELEMENT_TYPE_FAN, commandElementTypeId));
				}
			}
		}
	}

	public Set<Long> getTubeWithPendingWork() {

		Set<Long> tubesId = new HashSet<>();

		if (CollectionUtils.isNotEmpty(commands)) {

			synchronized (commands) {

				commands.forEach(command -> {

					if (command.getTubeId() != null) {

						tubesId.add(command.getTubeId());
					}
				});
			}
		}
		return tubesId;
	}

}
