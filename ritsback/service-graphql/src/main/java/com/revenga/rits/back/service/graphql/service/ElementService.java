package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.front.core.model.helper.ElementHelper;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueStateVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.ElementGraphql;
import com.revenga.rits.back.service.graphql.type.ElementTypeStateGraphql;
import com.revenga.rits.back.service.graphql.type.ElementValueStateGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ElementService implements QueryInterface<ElementVO>, MutationInterface<ElementVO> {
	
	@Autowired 
	LocationService locationService;

	private static final Long N_ENTITIES_TO_GET_ALL = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "service.element.n_entities_to_get_all"));

	@Override
	public List<ElementVO> gets(List<String> elementIds) {

		List<ElementVO> elementsVO = new ArrayList<>();

		if (CollectionUtils.isEmpty(elementIds)) {

			List<Element> elements = EntitiesManager.getInstance().getElementRepository().getAll(false);
			
			if (!CollectionUtils.isEmpty(elements)) {

				MapRequests mapRequests =  new MapRequests(MapRequests.Mode.GET_ALL);
				
				for (Element element : elements) {

					//Filtering if the element is Visible or not.
					if (element!= null && (element.getVisible() == null || element.getVisible().booleanValue())) {
						
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

						//Filtering if the element is Visible or not.
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
	public List<ElementVO> getAll() {

		List<ElementVO> elementsVO = new ArrayList<>();

		List<Element> elements = EntitiesManager.getInstance().getElementRepository().getAll(false);

		if (!CollectionUtils.isEmpty(elements)) {

			MapRequests mapRequests =  new MapRequests(MapRequests.Mode.GET_ALL);
			for (Element element : elements) {

				//Filtering if the element is Visible or not.
				if (element!= null && (element.getVisible() == null || element.getVisible().booleanValue())) {
					
					elementsVO.add(new ElementGraphql(element, mapRequests));
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

				//Filtering if the element is Visible or not.
				if (element!= null && (element.getVisible() == null || element.getVisible().booleanValue())) {

					elementVO = Optional.of(new ElementGraphql(element));					
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementVO;
	}

	public List<ElementVO> getByType(final String elementTypeId) {
		
		List<ElementVO> elementVO = new ArrayList<>();

		try {

			Key key = new Key(elementTypeId);

			if (key.getClazz() == ElementType.class) {


				MapRequests mapRequests =  new MapRequests(MapRequests.Mode.GET_BY_TYPE);
				
				for (Element element : EntitiesManager.getInstance().getElementRepository()
						.gets(Long.valueOf(key.getId(1)), false)) {

					// Filtering if the element is Visible or not.
					if (element != null && (element.getVisible() == null || element.getVisible().booleanValue())) {

						elementVO.add(new ElementGraphql(element, mapRequests));
					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementVO;
	}
	
	public List<ElementVO> getByLocations(final List<String> locationIds, final String elementTypeId) {
		
		List<ElementVO> elementVO = new ArrayList<>();

		try {
			
			if (!CollectionUtils.isEmpty(locationIds)) {
				
				List<Element> elements = null;
				
				if (!StringUtils.isEmpty(elementTypeId)) {
					
					Key elementTypeKey = new Key(elementTypeId);
					
					elements = EntitiesManager.getInstance().getElementRepository()
							.gets(Long.valueOf(elementTypeKey.getId(1)), false);
				}
				else {
					
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
									if (elementInLocation != null && (elementInLocation.getVisible() == null
											|| elementInLocation.getVisible().booleanValue())) {

										elementVO.add(new ElementGraphql(elementInLocation));
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

		return elementVO;
	}
	
	public List<ElementVO> getByStretchs(final List<String> stretchIds, final String elementTypeId) {
		
		List<ElementVO> elementsVO = null;

		try {
			
			List<LocationVO> locations = locationService.getByStretchs(stretchIds);
			
			if (!CollectionUtils.isEmpty(locations)) {
				
				List<String> locationIds = locations.stream()
						.map(BasicElementVO::getId)
						.collect(Collectors.toList());
				
				elementsVO = getByLocations(locationIds, elementTypeId);
				
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
	
//	private List<Location> getLocations(final List<String> stretchIds) {
//		
//		List<Location> locationsResult = null;
//		
//		List<Location> locations = EntitiesManager.getInstance().getLocationRepository().getAll(false);
//		List<StretchLocation> stretchLocations = EntitiesManager.getInstance().getStretchLocationRepository().getAll(false);
//		
//		List<StretchLocation> stretchLocationsFiltered = stretchLocations.stream().filter(stretchLocation -> {
//
//			return (!CollectionUtils.isEmpty(stretchIds)
//					&& stretchIds.contains((new Key(Stretch.class, stretchLocation.getStretchId())).getValue()));
//					
//		}).collect(Collectors.toList());
//		
//		if (!CollectionUtils.isEmpty(stretchLocationsFiltered)) {
//		
//			locationsResult = locations.stream().filter(location -> {
//				
//				return (stretchLocationsFiltered.stream().filter(e -> e.getLocationId().equals(location.getId())).findFirst().orElse(null) != null);
//
//			}).collect(Collectors.toList());
//		}
//		
//		return locationsResult;
//	}

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

}
