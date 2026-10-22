package com.revenga.rits.back.historical.manager.executor;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.HistoricalPeriodParserV1;
import com.revenga.rits.back.adapter.lib.model.HistoricalPeriodParserV1.Fields.Field;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeRepository;
import com.revenga.rits.back.historical.manager.executor.model.PeriodExecutorModel;
import com.revenga.rits.back.historical.manager.executor.service.PeriodDataMangerDB;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Deprecated
public class PeriodDataExecutor extends GenericExecutor {

	private static final String PARAM_INTEGRATION_PERIOD = "integration_period_s";
	private static final String PARAM_ELEMENT_TYPE_ID = "element_type_id";
	private static final String PARAM_FIELD_TARGET_TIMESTAMP = "field_target_timestamp";
	private static final String PARAM_FIELD_TARGET_ELEMENT_ID = "field_target_element_id";
	private static final String PARAM_TARGET_TABLE = "target_table";
	private static final String PARAM_TABLE_SPACE = "table_space";
	private static final String PARAM_PARSER_FILE = "parser_file";

	private Map<Long, Map<String, String>> elementValues;
	private Map<String, ElementTypeParam> elementTypeParams;

	private PeriodDataMangerDB historicMangerDB;
	private PeriodDataThread historicStorageThread;

	public PeriodDataExecutor(Executor executor, short threadNum) {

		super(executor, threadNum);
		historicMangerDB = new PeriodDataMangerDB();
		historicStorageThread = new PeriodDataThread();
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			if (!(queueItem instanceof ElementValue)) {

				throw new ClassCastException("Item isn't a ElementValue");
			}

			ElementValue valueItem = (ElementValue) queueItem;

			if (valueItem.getValue() == null || valueItem.getElementId() == null
					|| valueItem.getElementTypeParamId() == null || valueItem.getParamTypeId() == null) {

				throw new NullPointerException("elementValue has some null field");
			}

			if (!elementValues.containsKey(valueItem.getElementId())) {

				throw new NullPointerException("element not found");
			}

			String elementTypeParam = valueItem.getParamTypeId() + ":" + valueItem.getElementTypeParamId();
			if (!elementTypeParams.containsKey(elementTypeParam)) {

				throw new NullPointerException("elementTypeParam not found");
			}

			elementValues.get(valueItem.getElementId()).put(elementTypeParam, valueItem.getValue());

		} catch (Exception e) {

			log.debug(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	@Override
	protected void initialize() {

		try {

			HistoricalPeriodParserV1 historicalPeriodParserV1 = (HistoricalPeriodParserV1) ExecutorUtil
					.loadParser(getParamValue(PARAM_PARSER_FILE), HistoricalPeriodParserV1.class);

			PeriodExecutorModel periodExecutorModel = getPeriodExecutorModel();

			initializeValues(periodExecutorModel, historicalPeriodParserV1);

			historicMangerDB.setHistoricalPeriodParserV1(historicalPeriodParserV1);
			historicMangerDB.setElementTypeParams(this.elementTypeParams);
			historicMangerDB.setHistoricExecutorModel(periodExecutorModel);

			if (!ExecutorUtil.existTable(periodExecutorModel.getTargetTable()) && !historicMangerDB.createTable()) {

				throw new IOException("CreateTable error");
			}

			historicStorageThread.setElementValues(this.elementValues);
			historicStorageThread.setHistoricMangerDB(this.historicMangerDB);
			historicStorageThread.setIntegrationPeriod(periodExecutorModel.getIntegrationPeriod() * 1000);

			String threadNameRouterService = "Thread-" + historicStorageThread.getClass().getSimpleName();
			(new Thread(historicStorageThread, threadNameRouterService)).start();

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			stop();
		}
	}

	private PeriodExecutorModel getPeriodExecutorModel() {

		PeriodExecutorModel model = new PeriodExecutorModel();

		String value = getParamValue(PARAM_INTEGRATION_PERIOD);
		if (value == null) {

			throw new NullPointerException("integration_period_s not found in the executor");

		} else {
			model.setIntegrationPeriod(Long.parseLong(value));
		}

		value = getParamValue(PARAM_ELEMENT_TYPE_ID);
		if (value == null) {

			throw new NullPointerException("element_type_id not found in the executor");
		} else {
			model.setElementTypeId(Long.parseLong(value));
		}

		value = getParamValue(PARAM_FIELD_TARGET_TIMESTAMP);
		if (value == null) {

			throw new NullPointerException("field_target_timestamp not found in the executor");
		} else {

			model.setFieldTargetTimestamp(value);
		}

		value = getParamValue(PARAM_FIELD_TARGET_ELEMENT_ID);
		if (value == null) {

			throw new NullPointerException("field_target_elementId not found in the executor");

		} else {

			model.setFieldTargetElementId(value);
		}

		value = getParamValue(PARAM_TARGET_TABLE);
		if (value == null) {

			throw new NullPointerException("target_table not found in the executor");

		} else {

			model.setTargetTable(value);
		}

		value = getParamValue(PARAM_TABLE_SPACE);
		if (value == null) {

			throw new NullPointerException("table_space not found in the executor");

		} else {

			model.setTableSpace(value);
		}

		return model;
	}

	@Override
	protected Object publish() {

		return null;
	}

	private void initializeValues(PeriodExecutorModel periodExecutorModel,
			HistoricalPeriodParserV1 historicalPeriodParserV1) throws IOException {

		if (periodExecutorModel == null) {

			throw new NullPointerException("Not found model");
		}

		if (periodExecutorModel.getElementTypeId() == null) {

			throw new IOException("Not found element_type_id in executor");
		}

		if (periodExecutorModel.getElementTypeId() != historicalPeriodParserV1.getElementTypeId()) {

			throw new IOException("Parse file is not compatible with this executor");
		}

		if (CollectionUtils.isEmpty(historicalPeriodParserV1.getFields().getField())) {

			throw new IOException("Fields not found");
		}

		ElementTypeRepository elementTypeRepository = new ElementTypeRepository();

		ElementType elementType = elementTypeRepository.get(periodExecutorModel.getElementTypeId(), true);

		if (elementType == null) {

			throw new IOException("ElementType not found");
		}

		if (CollectionUtils.isEmpty(elementType.getElementTypeParams())) {

			throw new IOException("Not param in elementType");
		}

		ElementRepository elementRepository = new ElementRepository();
		List<Element> elements = elementRepository.gets(periodExecutorModel.getElementTypeId(), false);

		if (CollectionUtils.isEmpty(elements)) {

			throw new NullPointerException("No element of this element_type was found");
		}

		elementValues = Collections.synchronizedMap(new HashMap<Long, Map<String, String>>());

		for (Element e : elements) {

			elementValues.put(e.getId(), Collections.synchronizedMap(new HashMap<String, String>()));
		}

		elementTypeParams = new HashMap<>();

		for (Field field : historicalPeriodParserV1.getFields().getField()) {

			for (ElementTypeParam elementTypeParam : elementType.getElementTypeParams()) {

				if (elementTypeParam.getId() == field.getElementTypeParam().getElementTypeParamId()
						&& elementTypeParam.getParamTypeId() == field.getElementTypeParam().getParamTypeId()) {

					elementTypeParams.put(elementTypeParam.getParamTypeId() + ":" + elementTypeParam.getId(),
							elementTypeParam);
				}

			}
		}

	}

	@Override
	protected void end() {

		try {
			historicStorageThread.stop();

			Instant start;
			Duration timeElapsed;
			boolean stopped;
			start = Instant.now();

			do {
				stopped = !historicStorageThread.isAlive();
				Thread.sleep(1L);

				timeElapsed = Duration.between(start, Instant.now());

			} while (timeElapsed.toMillis() < 5000L && !stopped);

		} catch (InterruptedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}

	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub
		
	}

}
