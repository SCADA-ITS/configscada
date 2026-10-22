package com.revenga.rits.back.manager.lib.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.ResponseCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResponseManager {

	private static final String FILE_PROPERTIES = "manager-lib.properties";

	private static final Long TIME_OUT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "service.command_service.timeout"));

	private static ResponseManager instance;

	private Map<Long, Pair<Long, ResponseCommand>> mapResponses;

	public static ResponseManager getInstance() {

		if (instance == null) {

			instance = new ResponseManager();
		}

		return instance;
	}

	private ResponseManager() {

		mapResponses = Collections.synchronizedMap(new HashMap<>());
	}

	public ResponseCommand sendCommandSync(Command command) {

		Pair<Long, ResponseCommand> pair = null;
		ResponseCommand responseCommand = null;

		if (command != null) {

			if (command.getResponseCommand() instanceof ResponseCommand) {

				responseCommand = (ResponseCommand) command.getResponseCommand();

			} else {
				
				responseCommand = new ResponseCommand();
				command.setResponseCommand(responseCommand);
			}

			if (command.getUId() == null) {

				command.generateId();
			}

			Long uId = command.getUId();
			responseCommand.setUIdSource(uId);

			log.info("sendCommandSync " + command.getUId() + "--> " + command);
			CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);

			Instant now = Instant.now();

			while (pair == null && Duration.between(now, Instant.now()).toMillis() < TIME_OUT) {

				try {
					pair = mapResponses.get(uId);

					if (pair != null) {

						mapResponses.remove(uId);
					}

					Thread.sleep(1);

				} catch (InterruptedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}
			}

			if (pair == null) {

				log.error("sendCommandSync " + command.getUId() + ", timeout expired");
			}
		}

		return pair != null ? pair.getRight() : null;
	}

	public void add(ResponseCommand command) {

		if (command != null && command.getUIdSource() != null && mapResponses != null) {

			Pair<Long, ResponseCommand> pair = new MutablePair<>(Instant.now().toEpochMilli(), command);

			mapResponses.put(command.getUIdSource(), pair);
		}
	}

	public void clearMap() {

		if (mapResponses != null) {

			Long now = Instant.now().toEpochMilli();

			synchronized (mapResponses) {

				List<Long> toRemove = new ArrayList<>();

				for (Entry<Long, Pair<Long, ResponseCommand>> entry : mapResponses.entrySet()) {

					Pair<Long, ResponseCommand> pair = entry.getValue();

					if (pair.getLeft() + TIME_OUT < now) {

						toRemove.add(entry.getKey());
					}
				}
				toRemove.forEach(uId -> mapResponses.remove(uId));
			}

		}
	}
}
