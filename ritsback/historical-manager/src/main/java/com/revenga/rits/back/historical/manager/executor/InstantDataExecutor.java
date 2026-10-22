package com.revenga.rits.back.historical.manager.executor;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV1;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.historical.manager.executor.model.InstantExecutorModel;
import com.revenga.rits.back.historical.manager.executor.service.InstantDataMangerDB;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Deprecated
public class InstantDataExecutor extends GenericExecutor {

	private static final String PARAM_TARGET_TABLE = "target_table";
	private static final String PARAM_TABLE_SPACE = "table_space";
	private static final String PARAM_PARSER_FILE = "parser_file";

	private HistoricalInstantParserV1 historicalInstantParserV1;
	private InstantDataMangerDB instantcMangerDB;

	public InstantDataExecutor(Executor executor, short threadNum) {

		super(executor, threadNum);
		instantcMangerDB = new InstantDataMangerDB();
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			
			if (queueItem.getClass() != ClassesUtil.getModelClassBySimpleClassName(historicalInstantParserV1.getModel())) {

				throw new ClassCastException("Item not supported");
			}
			else{
				instantcMangerDB.insertHistoric((BasicElement)queueItem);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	@Override
	protected void initialize() {

		try {

			historicalInstantParserV1 = (HistoricalInstantParserV1) ExecutorUtil
					.loadParser(getParamValue(PARAM_PARSER_FILE), HistoricalInstantParserV1.class);

			InstantExecutorModel instantExecutorModel = getIstantExecutorModel();

			instantcMangerDB.setHistoricalInstantParserV1(this.historicalInstantParserV1);
			instantcMangerDB.setInstantExecutorModel(instantExecutorModel);

			if (!ExecutorUtil.existTable(instantExecutorModel.getTargetTable()) && !instantcMangerDB.createTable()) {

				throw new IOException("CreateTable error");
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			stop();
		}
	}

	private InstantExecutorModel getIstantExecutorModel() {

		InstantExecutorModel model = new InstantExecutorModel();

		String value = getParamValue(PARAM_TARGET_TABLE);

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
		// Auto-generated method stub
		return null;
	}

	@Override
	protected void end() {
		// Auto-generated method stub

	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub
		
	}

}
