package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.Tube;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.FanEntityManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RegimesProcessorThread implements Runnable {

	private FanEntityManager fanEntityManager;
	private TubeEntityManager tubeEntityManager;

	private boolean stop;
	private boolean alive;

	public RegimesProcessorThread(final FanEntityManager fanEntityManager, final TubeEntityManager tubeEntityManager) {

		super();
		this.fanEntityManager = fanEntityManager;
		this.tubeEntityManager = tubeEntityManager;
	}

	@Override
	public void run() {

		try {
			log.info(Thread.currentThread().getName() + " is running");

			while (!stop) {

				Map<Long, Tube> tubes = this.tubeEntityManager.getTubes();
				List<Command> commands = null;

				if (tubes != null) {

					for (Entry<Long, Tube> tubeEntry : tubes.entrySet()) {

						Tube tube = tubeEntry.getValue();
						Long tubeId = tubeEntry.getKey();

						if (tubeId != null && tube.getCheckVentilationRegime() != null
								&& tube.getCheckVentilationRegime().booleanValue()) {

							Long startedFans = fanEntityManager.getCurrentStartedFanByTube(tubeId);

							Command command = this.tubeEntityManager.updateVentilationRegime(
									new Element(ElementType.ELEMENT_TYPE_TUBE, tubeId), startedFans);

							if (command != null) {

								if (commands == null) {

									commands = new ArrayList<>();
								}
								commands.add(command);
							}
						}
					}
				}

				if (CollectionUtils.isNotEmpty(commands)) {

					for (Command command : commands) {

						EntityManager.getInstance().sendCommand(command);
					}
				}
				Thread.sleep(1000);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
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
