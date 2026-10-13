package com.revenga.rits.back.historical.manager.executor;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV2;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.historical.manager.executor.service.InstantDataMangerDB;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmExecutor extends GenericExecutor {

	private static final String PARAM_PARSER_FILE = "parser_file";

	private HistoricalInstantParserV2 adapter;
	private InstantDataMangerDB instantcMangerDB;

	public AlarmExecutor(Executor executor, short threadNum) {

		super(executor, threadNum);
		instantcMangerDB = new InstantDataMangerDB();
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			if (queueItem instanceof SetAlarmsPublishCommand) {

				SetAlarmsPublishCommand setAlarmsPublishCommand = (SetAlarmsPublishCommand) queueItem;

				if (!CollectionUtils.isEmpty(setAlarmsPublishCommand.getAlarms())) {

					long createAt = Instant.now().toEpochMilli();

					setAlarmsPublishCommand.getAlarms().forEach(alarm -> {

						alarm.setCreatedAt(createAt);
						queuePreparedMessages.add(alarm);

					});
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	@Override
	protected void initialize() {

		if (threadNum == 0) {

			try {

				adapter = (HistoricalInstantParserV2) ExecutorUtil.loadParser(getParamValue(PARAM_PARSER_FILE),
						HistoricalInstantParserV2.class);

				if (!ExecutorUtil.existTable(adapter.getTargetTable()) && !instantcMangerDB.createTable(adapter)) {

					throw new IOException("CreateTable error");
				}

			} catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				stop();
			}
		}
	}

	@Override
	protected Object publish() {

		List<BasicElement> basicElements = new ArrayList<>();

		Object obj = null;

		while ((obj = queuePreparedMessages.poll()) != null) {

			if (obj instanceof Alarm) {

				basicElements.add((BasicElement) obj);
			}
		}
		instantcMangerDB.insertHistoric(adapter, basicElements);

		return null;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

}
