package com.revenga.rits.back.service.graphql.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.front.core.model.helper.ElementHelper;
import com.revenga.rits.back.front.core.model.helper.ElementStateHelper;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.ElementGraphql;
import com.revenga.rits.back.service.graphql.type.ElementStateGraphql;
import com.revenga.rits.back.service.graphql.type.ElementTypeStateGraphql;
import com.revenga.rits.back.service.graphql.type.ElementValueGraphql;
import com.revenga.rits.back.service.graphql.type.ElementValueStateGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ElementService implements QueryInterface<ElementVO>, MutationInterface<ElementVO> {

	@Autowired
	LocationService locationService;

	private static final Long N_ENTITIES_TO_GET_ALL = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "service.element.n_entities_to_get_all"));

	private static final String HISTORICAL_XML_DIRECTORY = ConfigProperty.getInstance()
			.getProperty("application.properties", "historical.xml.directory");

	@Deprecated
	@Override
	public List<ElementVO> gets(List<String> elementIds) {

		List<ElementVO> elementsVO = new ArrayList<>();

		if (CollectionUtils.isEmpty(elementIds)) {

			List<Element> elements = EntitiesManager.getInstance().getElementRepository().getAll(false);

			if (!CollectionUtils.isEmpty(elements)) {

				MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_ALL);

				for (Element element : elements) {

					// Filtering if the element is Visible or not.
					if (element != null && (element.getVisible() == null || element.getVisible().booleanValue())) {

						elementsVO.add(new ElementGraphql(element, mapRequests));
					}
				}
			}
		} else {

			MapRequests mapRepository = elementIds.size() >= N_ENTITIES_TO_GET_ALL
					? new MapRequests(MapRequests.Mode.GET_ALL)
					: null;

			for (String elementId : elementIds) {

				try {

					Key key = new Key(elementId);

					if (key.getClazz() == Element.class) {

						Element element = EntitiesManager.getInstance().getElementRepository()
								.get(Long.valueOf(key.getId(1)), Long.valueOf(key.getId(2)), false);

						// Filtering if the element is Visible or not.
						if (element != null && (element.getVisible() == null || element.getVisible().booleanValue())) {

							elementsVO.add(new ElementGraphql(element, mapRepository));
						}
					}

				} catch (Exception e) {

					log.error("The id does not correspond to the required object: ElementType");
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		return elementsVO;
	}

	@Override
	@Deprecated
	public Optional<ElementVO> get(String elementKey) {

		Optional<ElementVO> elementVO = null;
		try {

			Key key = new Key(elementKey);

			if (key.getClazz() == Element.class) {

				Element element = EntitiesManager.getInstance().getElementRepository().get(Long.valueOf(key.getId(1)),
						Long.valueOf(key.getId(2)), false);

				// Filtering if the element is Visible or not.
				if (element != null && (element.getVisible() == null || element.getVisible().booleanValue())) {

					elementVO = Optional.of(new ElementGraphql(element));
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementVO;
	}

	public List<ElementVO> getByType(final String elementTypeId, final Long managementAreaId) {

		List<ElementVO> elementsVO = new ArrayList<>();

		try {

			Key key = new Key(elementTypeId);

			if (key.getClazz() == ElementType.class) {

				MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_BY_TYPE);

				for (Element element : EntitiesManager.getInstance().getElementRepository()
						.gets(Long.valueOf(key.getId(1)), false)) {

					// Filtering if the element is Visible or not.
					if (element != null && (element.getVisible() == null || element.getVisible().booleanValue())
							&& checkManagementArea(element, managementAreaId)) {

						elementsVO.add(new ElementGraphql(element, mapRequests));
					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementsVO;
	}

	public List<ElementVO> getByStretchs(final List<String> stretchIds, final String elementTypeId,
			final Long managementAreaId) {

		List<ElementVO> elementsVO = null;

		try {

			List<LocationVO> locations = locationService.getByStretchs(stretchIds);

			if (!CollectionUtils.isEmpty(locations)) {

				List<String> locationIds = locations.stream().map(BasicElementVO::getId).collect(Collectors.toList());

				elementsVO = getByLocations(locationIds, elementTypeId, managementAreaId);

				if (!StringUtils.isEmpty(elementTypeId) && !CollectionUtils.isEmpty(elementsVO)) {

					Key elementTypeKey = new Key(elementTypeId);

					if (elementTypeKey.getClazz() == ElementType.class) {

						elementsVO = elementsVO.stream()
								.filter(e -> e.getElementTypeId() != null && e.getElementTypeId().equals(elementTypeId))
								.collect(Collectors.toList());
					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementsVO;
	}

	public List<ElementTypeStateVO> getElementTypeStates(final String elementTypeId) {

		List<ElementTypeStateVO> elementTypeStateVO = new ArrayList<>();

		try {

			Key key = new Key(elementTypeId);

			if (key.getClazz() == ElementType.class) {

				List<ElementTypeState> elementTypeStates = EntitiesManager.getInstance().getElementTypeStateRepository()
						.getsByElementType(Long.valueOf(key.getId(1)), false);

				if (!CollectionUtils.isEmpty(elementTypeStates)) {

					for (ElementTypeState elementTypeState : elementTypeStates) {

						elementTypeStateVO.add(new ElementTypeStateGraphql(elementTypeState));
					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementTypeStateVO;
	}

	public List<ElementValueStateVO> getElementValueStates() {

		List<ElementValueStateVO> elementValueStateVO = new ArrayList<>();

		List<ElementValueState> elementValueStates = EntitiesManager.getInstance().getElementValueStateRepository()
				.getAll(false);

		if (!CollectionUtils.isEmpty(elementValueStates)) {

			for (ElementValueState elementValueState : elementValueStates) {

				elementValueStateVO.add(new ElementValueStateGraphql(elementValueState));
			}
		}

		return elementValueStateVO;
	}

	@Override
	public Optional<ElementVO> create(ElementVO elementVO) {

		Optional<ElementVO> resp = null;

		if (elementVO != null) {

			Element element = ElementHelper.get(elementVO);

			Element elementCreated = EntitiesManager.getInstance().getElementRepository().create(element);

			if (elementCreated != null) {

				resp = Optional.of(new ElementGraphql(elementCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ElementVO> clone(String elementId) {

		Optional<ElementVO> resp = null;

		if (elementId != null) {

			ElementVO elementVO = new ElementVO();
			elementVO.setId(elementId);

			Element element = ElementHelper.get(elementVO);

			Element elementClone = EntitiesManager.getInstance().getElementRepository().clone(element);

			if (elementClone != null) {

				resp = Optional.of(new ElementGraphql(elementClone));
			}
		}

		return resp;
	}

	@Override
	public Optional<ElementVO> update(ElementVO elementVO) {

		Optional<ElementVO> resp = null;

		if (elementVO != null) {

			Element element = ElementHelper.get(elementVO);

			Element elementUpdated = EntitiesManager.getInstance().getElementRepository().update(element);

			if (elementUpdated != null) {

				resp = Optional.of(new ElementGraphql(elementUpdated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ElementVO> delete(String elementId) {

		Optional<ElementVO> resp = null;

		if (elementId != null) {

			ElementVO elementVO = new ElementVO();
			elementVO.setId(elementId);

			Element element = ElementHelper.get(elementVO);

			Element elementDeleted = EntitiesManager.getInstance().getElementRepository().delete(element);

			if (elementDeleted != null) {

				resp = Optional.of(new ElementGraphql(elementDeleted));
			}
		}

		return resp;
	}

	public Boolean sendChangeElementState(List<ElementStateVO> elementStatesVO) {

		if (CollectionUtils.isNotEmpty(elementStatesVO)) {

			SetElementStatesChangeCommand elementStateChangeCommand = new SetElementStatesChangeCommand();

			for (ElementStateVO elementStateVO : elementStatesVO) {

				ElementState elementState = ElementStateHelper.get(elementStateVO);

				if (elementState != null) {

					elementStateChangeCommand.addElementState(elementState);
				}
			}

			return CollectionUtils.isNotEmpty(elementStateChangeCommand.getElementStates())
					&& EntitiesManager.getInstance().sendCommand(elementStateChangeCommand) != null;
		}

		return false;

	}

	public List<ElementStateVO> getHistoricalElementStates(List<String> elementIds, Long initDate, Long finalDate) {

		List<ElementStateVO> elementStatesVO = new ArrayList<>();

		List<ElementState> elementStates = null;

		if (CollectionUtils.isNotEmpty(elementIds)) {

			elementStates = EntitiesManager.getInstance().getElementStateRepository().getHistoricalData(elementIds,
					initDate, finalDate);
		}

		if (CollectionUtils.isNotEmpty(elementStates)) {

			for (ElementState elementState : elementStates) {

				if (elementState != null) {

					elementStatesVO.add(new ElementStateGraphql(elementState));
				}
			}
		}

		return elementStatesVO;
	}

	public List<ElementValueVO> getHistoricalElementValues(List<String> elementValueIds, Long initDate,
			Long finalDate) {

		List<ElementValueVO> elementValuesVO = new ArrayList<>();

		List<ElementValue> elementValues;

		Map<Long, Pair<String, Map<ElementTypeParam, String>>> mapAdapterInfo = new HashMap<>();

		Map<Long, Pair<Set<Long>, Set<ElementTypeParam>>> elementTypeParamElement = new HashMap<>();

		Map<Long, Map<Long, Set<ElementValue>>> mapElemementValues = new HashMap<>();

		getAdaptersMap(mapAdapterInfo);

		prepareElementValueToFill(elementValueIds, elementTypeParamElement, mapElemementValues);

		elementValues = EntitiesManager.getInstance().getElementValueRepository().getHistoricalData(mapAdapterInfo,
				elementTypeParamElement, mapElemementValues, initDate, finalDate);

		if (CollectionUtils.isNotEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {

				if (elementValue != null) {

					elementValuesVO.add(new ElementValueGraphql(elementValue));
				}
			}
		}

		return elementValuesVO;
	}

	public List<ElementVO> gets(List<String> elementIds, Long managementAreaId) {

		List<ElementVO> elementsVO = new ArrayList<>();

		if (CollectionUtils.isEmpty(elementIds)) {

			List<Element> elements = EntitiesManager.getInstance().getElementRepository().getAll(false);

			if (!CollectionUtils.isEmpty(elements)) {

				MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_ALL);

				for (Element element : elements) {

					// Filtering if the element is Visible or not.
					if (element != null && (element.getVisible() == null || element.getVisible().booleanValue())
							&& checkManagementArea(element, managementAreaId)) {

						elementsVO.add(new ElementGraphql(element, mapRequests));
					}
				}
			}
		} else {

			MapRequests mapRepository = elementIds.size() >= N_ENTITIES_TO_GET_ALL
					? new MapRequests(MapRequests.Mode.GET_ALL)
					: null;

			for (String elementId : elementIds) {

				try {

					Key key = new Key(elementId);

					if (key.getClazz() == Element.class) {

						Element element = EntitiesManager.getInstance().getElementRepository()
								.get(Long.valueOf(key.getId(1)), Long.valueOf(key.getId(2)), false);

						// Filtering if the element is Visible or not.
						if (element != null && (element.getVisible() == null || element.getVisible().booleanValue())
								&& checkManagementArea(element, managementAreaId)) {

							elementsVO.add(new ElementGraphql(element, mapRepository));
						}
					}

				} catch (Exception e) {

					log.error("The id does not correspond to the required object: ElementType");
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		return elementsVO;
	}

	private void getAdaptersMap(Map<Long, Pair<String, Map<ElementTypeParam, String>>> mapAdapterInfo) {

		File historicalXmlDirectoy = new File(ResourcesUtil.getPath(HISTORICAL_XML_DIRECTORY));

		SessionAdapter sessionAdapter = new SessionAdapter();

		if (historicalXmlDirectoy.isDirectory()) {

			for (File xmlFile : historicalXmlDirectoy.listFiles()) {

				if (xmlFile != null) {

					HistoricalElementParserV1 adapter = (HistoricalElementParserV1) sessionAdapter
							.getAdapter(HISTORICAL_XML_DIRECTORY + xmlFile.getName(), HistoricalElementParserV1.class);

					if (adapter != null) {

						Map<ElementTypeParam, String> elementTypeParamMap = new HashMap<>();

						for (HistoricalElementParserV1.Fields.Field field : adapter.getFields().getField()) {

							ElementTypeParam elementTypeParam = new ElementTypeParam();
							elementTypeParam.setElementTypeId((long) adapter.getElementTypeId());
							elementTypeParam.setParamTypeId((long) field.getElementTypeParam().getParamTypeId());
							elementTypeParam.setId((long) field.getElementTypeParam().getElementTypeParamId());

							elementTypeParamMap.put(elementTypeParam, field.getName());

						}

						Pair<String, Map<ElementTypeParam, String>> pair = new MutablePair<>(adapter.getTargetTable(),
								elementTypeParamMap);

						mapAdapterInfo.put((long) adapter.getElementTypeId(), pair);
					}
				}
			}
		}
	}

	private void prepareElementValueToFill(List<String> elementValueIds,
			Map<Long, Pair<Set<Long>, Set<ElementTypeParam>>> mapElementTypeParam,
			Map<Long, Map<Long, Set<ElementValue>>> mapElemementValues) {

		for (String elementValueId : elementValueIds) {

			if (elementValueId != null) {

				Key key = new Key(elementValueId);

				if (key.getClazz() == ElementValue.class) {

					ElementValue elementValue = new ElementValue();

					elementValue.setPks(key.getIds());

					mapElemementValues.computeIfAbsent(elementValue.getElementTypeId(), k -> new HashMap<>());

					Map<Long, Set<ElementValue>> mapSetElementValues = mapElemementValues
							.get(elementValue.getElementTypeId());

					mapSetElementValues.computeIfAbsent(elementValue.getElementId(), k -> new HashSet<>());

					ElementTypeParam elementTypeParam = new ElementTypeParam();
					elementTypeParam.setElementTypeId(elementValue.getElementTypeId());
					elementTypeParam.setParamTypeId(elementValue.getParamTypeId());
					elementTypeParam.setId(elementValue.getElementTypeParamId());
					elementValue.setElementTypeParam(elementTypeParam);

					mapSetElementValues.get(elementValue.getElementId()).add(elementValue);

					mapElementTypeParam.computeIfAbsent(elementValue.getElementTypeId(),
							k -> new MutablePair<>(new HashSet<>(), new HashSet<>()));

					mapElementTypeParam.get(elementValue.getElementTypeId()).getLeft().add(elementValue.getElementId());
					mapElementTypeParam.get(elementValue.getElementTypeId()).getRight().add(elementTypeParam);

				}
			}
		}
	}

	private List<ElementVO> getByLocations(final List<String> locationIds, final String elementTypeId,
			final Long managementAreaId) {

		List<ElementVO> elementsVO = new ArrayList<>();

		try {

			if (!CollectionUtils.isEmpty(locationIds)) {

				List<Element> elements = null;

				if (!StringUtils.isEmpty(elementTypeId)) {

					Key elementTypeKey = new Key(elementTypeId);

					elements = EntitiesManager.getInstance().getElementRepository()
							.gets(Long.valueOf(elementTypeKey.getId(1)), false);
				} else {

					elements = EntitiesManager.getInstance().getElementRepository().getAll(false);
				}

				if (!CollectionUtils.isEmpty(elements)) {

					List<Element> elementsFiltered = new ArrayList<>(elements);

					locationIds.forEach(locationId -> {

						Key keyLocation = new Key(locationId);

						if (keyLocation.getClazz() == Location.class) {

							List<Element> elementsInLocations = elementsFiltered.stream()
									.filter(e -> e.getLocationId() != null
											&& e.getLocationId().equals(Long.valueOf(keyLocation.getId(1))))
									.collect(Collectors.toList());

							if (!CollectionUtils.isEmpty(elementsInLocations)) {

								elementsInLocations.forEach(elementInLocation -> {

									// Filtering if the element is Visible or not.
									if (elementInLocation != null
											&& (elementInLocation.getVisible() == null
													|| elementInLocation.getVisible().booleanValue())
											&& checkManagementArea(elementInLocation, managementAreaId)) {

										elementsVO.add(new ElementGraphql(elementInLocation));
									}

									elementsFiltered.remove(elementInLocation);
								});
							}
						}
					});
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementsVO;
	}

	@Override
	public List<ElementVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean checkManagementArea(final Element element, final Long managementAreaId) {

		List<ManagementAreaElement> managementAreaElements = EntitiesManager.getInstance()
				.getManagementAreaElementRepository().gets(element.getElementTypeId(), element.getId(), false);

		return CollectionUtils.isEmpty(managementAreaElements) || (managementAreaId != null
				&& managementAreaElements.stream().filter(e -> e.getId().equals(managementAreaId)).count() > 0);
	}
}
