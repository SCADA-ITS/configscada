package com.revenga.rits.back.historical.manager.executor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
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
import com.revenga.rits.back.historical.manager.executor.adapter.ElementAdapter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledPeriodDataJob implements Job {

	public ScheduledPeriodDataJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		log.debug("- EXECUTE SCHEDULED PERIOD DATA -");

		SchedulerContext schedulerContext;

		try {
			schedulerContext = context.getScheduler().getContext();
			ElementService  elementService = (ElementService) schedulerContext.get(ElementService.class.getSimpleName());

			JobDataMap data = context.getJobDetail().getJobDataMap();

			Short elementTypeId = (short) data.getInt(ElementService.ELEMENT_TYPE_ID);

			if (elementTypeId != null) {

				List<Element> elements = new ArrayList<>();
				List<ElementValue> elementValueConfigs = EntitiesManager.getInstance()
						.getElementValueConfig(elementTypeId.longValue());
				List<ElementValue> elementValueMeasures = EntitiesManager.getInstance()
						.getElementValueMeasure(elementTypeId.longValue());

				while (!elementValueMeasures.isEmpty()) {

					Long elementId = null;

					for (int i = elementValueMeasures.size() - 1; i >= 0 && elementId == null; i--) {

						elementId = elementValueMeasures.get(i).getElementId();

						if (elementId == null) {

							elementValueMeasures.remove(i);
						}
					}
					if (elementId != null) {

						Long id = elementId;

						List<ElementValue> configValues = elementValueConfigs.stream()
								.filter(b -> b.getElementId().equals(id)).collect(Collectors.toList());

						elementValueConfigs.removeAll(configValues);

						List<ElementValue> measuresValues = elementValueMeasures.stream()
								.filter(elementValue -> id.equals(elementValue.getElementId()))
								.collect(Collectors.toList());

						elementValueMeasures.removeAll(measuresValues);
						
						Element element = new Element();
						element.setId(id);
						element.setElementTypeId(elementTypeId.longValue());
						element.setElementValues(new ArrayList<>(measuresValues));
						element.getElementValues().addAll(configValues);
						elements.add(element);
					}
				}
				if(!CollectionUtils.isEmpty(elements)){
					
					HistoricalElementParserV1 adapter = ElementAdapter.getInstance().getAdapters()
							.get(elementTypeId.shortValue());

					List<ElementTypeParam> elementTypeParams = EntitiesManager.getInstance()
							.getElementTypeParams(elementTypeId.longValue());

					elementService.insertHistoric(adapter, elementTypeParams, elements);
				}

			}

		} catch (

		SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

	}
}