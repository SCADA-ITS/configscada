package com.revenga.rits.back.historical.manager.executor;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.historical.manager.executor.service.EntitiesManager;
import com.revenga.rits.back.historical.manager.executor.service.ProcessInfoService;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProcessInfoExecutor extends GenericExecutor {

	ProcessInfoService processInfoService;
	private static final String PARAM_PARSER_FILE = "parser_file";
	private static final String CRON_EXPRESSION= "cron_expression";

	public ProcessInfoExecutor(Executor executor, short threadNum) {

		super(executor, threadNum);
		processInfoService = new ProcessInfoService();
	}

	@Override
	protected Object prepare(Object queueItem) {

		return null;
	}

	@Override
	protected void initialize() {
		
		if (threadNum == 0) {

			try {

				HistoricalElementParserV1 adapter = (HistoricalElementParserV1) ExecutorUtil.loadParser(getParamValue(PARAM_PARSER_FILE),
						HistoricalElementParserV1.class);

				String cronExpression = getParamValue(CRON_EXPRESSION);
				
				if (adapter != null) {

					List<ElementTypeParam> elementTypeParams = EntitiesManager.getInstance()
							.getElementTypeParams(Long.valueOf(adapter.getElementTypeId()));
					
					if (CollectionUtils.isNotEmpty(elementTypeParams)) {
						
						processInfoService.init(adapter, elementTypeParams, cronExpression);
					}
					
				} else {
					
					log.error("Cannot get Process Info Adapter");
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
		// Auto-generated method stub
		return null;
	}

	@Override
	protected void end() {
		
		if (threadNum == 0) {
			
			processInfoService.stop();
		}
	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

}
