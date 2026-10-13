package com.revenga.rits.back.historical.manager.executor;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.command.SetHistoricElementCommand;
import com.revenga.rits.back.historical.manager.executor.adapter.ElementAdapter;
import com.revenga.rits.back.historical.manager.executor.service.ElementService;
import com.revenga.rits.back.historical.manager.executor.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementCommandExecutor extends GenericExecutor {

	ElementService elementService;

	public ElementCommandExecutor(Executor executor, short threadNum) {

		super(executor, threadNum);
		elementService = new ElementService();
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			if (queueItem instanceof SetHistoricElementCommand) {

				SetHistoricElementCommand setHistoricElementCommand = (SetHistoricElementCommand) queueItem;

				while (!setHistoricElementCommand.getElements().isEmpty()) {

					Long elementTypeId = null;

					for (int i = setHistoricElementCommand.getElements().size() - 1; i >= 0
							&& elementTypeId == null; i--) {

						elementTypeId = setHistoricElementCommand.getElements().get(i).getElementTypeId();

						if (elementTypeId == null) {

							setHistoricElementCommand.getElements().remove(i);
						}
					}

					if (elementTypeId != null) {

						Long id = elementTypeId;

						List<Element> elements = setHistoricElementCommand.getElements().stream()
								.filter(b -> id.equals(b.getElementTypeId())).collect(Collectors.toList());

						setHistoricElementCommand.getElements().removeAll(elements);

						HistoricalElementParserV1 adapter = ElementAdapter.getInstance().getAdapters()
								.get(elementTypeId.shortValue());

						List<ElementTypeParam> elementTypeParams = EntitiesManager.getInstance()
								.getElementTypeParams(elementTypeId);

						if (!elementService.insertHistoric(adapter, elementTypeParams, elements)) {

							log.error("InsertHistoric error");
						}

					}
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

				Map<Short, HistoricalElementParserV1> adapters = ElementAdapter.getInstance().getAdapters();

				for (Entry<Short, HistoricalElementParserV1> entry : adapters.entrySet()) {

					List<ElementTypeParam> elementTypeParams = EntitiesManager.getInstance()
							.getElementTypeParams(Long.valueOf(entry.getKey()));

					if (!CollectionUtils.isEmpty(elementTypeParams)) {

						if (!elementService.init(entry.getValue(), elementTypeParams)) {

							log.error("Init error");
						}
					}

				}

			} catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
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

			Map<Short, HistoricalElementParserV1> adapters = ElementAdapter.getInstance().getAdapters();

			for (Entry<Short, HistoricalElementParserV1> entry : adapters.entrySet()) {

				List<ElementTypeParam> elementTypeParams = EntitiesManager.getInstance()
						.getElementTypeParams(Long.valueOf(entry.getKey()));

				if (!CollectionUtils.isEmpty(elementTypeParams)) {

					if (!elementService.deInit(entry.getValue(),elementTypeParams )) {

						log.error("DeInit error");
					}
				}
			}
			elementService.stop();
		}

	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

}
