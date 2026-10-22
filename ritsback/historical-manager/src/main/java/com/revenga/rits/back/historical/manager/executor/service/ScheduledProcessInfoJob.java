package com.revenga.rits.back.historical.manager.executor.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ProcessInfo;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledProcessInfoJob implements Job {

	public static final String ADAPTER = "adapter";
	
	public ScheduledProcessInfoJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		log.debug("- EXECUTE SCHEDULED PROCESS INFO -");

		SchedulerContext schedulerContext;

		try {
			schedulerContext = context.getScheduler().getContext();
			
			ProcessInfoService processInfoService = (ProcessInfoService) schedulerContext
					.get(ProcessInfoService.class.getSimpleName());

			if (processInfoService != null) {
				
				JobDataMap data = context.getJobDetail().getJobDataMap();

				HistoricalElementParserV1 adapter = (HistoricalElementParserV1) data.get(ADAPTER);

				if (adapter != null) {

					List<ProcessInfo> processesInfo = EntitiesManager.getInstance().getAllProcessInfo();
					
					Long elementTypeId = Long.valueOf(adapter.getElementTypeId());

					List<ElementTypeParam> elementTypeParams = EntitiesManager.getInstance().getElementTypeParams(elementTypeId);

					List<Element> elementProcesses = EntitiesManager.getInstance().getProcessInfoElements();
					
					Map<Element, ProcessInfo> elementProcessesInfo = new HashMap<>();

					Map<Element, List<ElementValue>> processesData = new HashMap<>();
					
					prepareData(adapter, processesInfo, elementProcesses, elementProcessesInfo,
							processesData);

					if (CollectionUtils.isNotEmpty(processesInfo) && CollectionUtils.isNotEmpty(elementTypeParams)
							&& CollectionUtils.isNotEmpty(elementProcesses) && MapUtils.isNotEmpty(elementProcessesInfo)
							&& MapUtils.isNotEmpty(processesData)) {

						processInfoService.insertHistoric(adapter, elementTypeParams, elementProcesses, processesData);

					}
				}
			}

		} catch (

		SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

	}

	private void prepareData(HistoricalElementParserV1 adapter, List<ProcessInfo> processesInfo,
			List<Element> elementProcesses, Map<Element, ProcessInfo> elementProcessesInfo,
			Map<Element, List<ElementValue>> processesData) {

		try {

			if (adapter != null && CollectionUtils.isNotEmpty(processesInfo)
					&& CollectionUtils.isNotEmpty(elementProcesses)) {

				for (Element element : elementProcesses) {

					ProcessInfo processInfo = processesInfo.stream().filter(e -> e.getMain().equals(element.getAlias()))
							.findFirst().orElse(null);

					processesInfo.remove(processInfo);
					
					if (processInfo != null) {

						for (Field field : ProcessInfo.class.getDeclaredFields()) {

							field.setAccessible(true);

							String fieldNamedFormatted = ExecutorUtil.camelCaseToSnakeCase(field.getName());
							
							com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1.Fields.Field adapterField = adapter
									.getFields().getField().stream().filter(e -> e.getName().equals(fieldNamedFormatted))
									.findFirst().orElse(null);

							if (adapterField != null) {
								
								processesData.computeIfAbsent(element, k -> new ArrayList<>());

								Object value = field.get(processInfo);

								Long elementTypeParamId = (long) adapterField.getElementTypeParam().getElementTypeParamId();
								
								ElementValue elementValue = new ElementValue();
								elementValue.setElementId(element.getId());
								elementValue.setElementTypeId(element.getElementTypeId());
								elementValue.setElementTypeParamId(elementTypeParamId);
								elementValue.setValue(value.toString());

								processesData.get(element).add(elementValue);
							}
							
						}

						elementProcessesInfo.put(element, processInfo);
					}
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(e.getStackTrace());
		}

	}
	
}