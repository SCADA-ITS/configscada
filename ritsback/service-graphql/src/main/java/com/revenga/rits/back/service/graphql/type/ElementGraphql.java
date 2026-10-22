package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementSubtype;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveCommand;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.PmElementTypeQueue;
import com.revenga.rits.back.data.core.model.PmQueue;
import com.revenga.rits.back.data.core.model.VmsGraphicGroup;
import com.revenga.rits.back.data.core.model.VmsGraphicGroupElement;
import com.revenga.rits.back.entities.lib.repository.AlarmRepository;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.front.core.model.helper.ElementHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementSubtypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.front.core.model.vo.PmQueueVO;
import com.revenga.rits.back.front.core.model.vo.VmsGraphicGroupVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class ElementGraphql extends ElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -873097050961304269L;

	private Element element;

	private ElementState elementState;

	private MapRequests mapRequests;

	public ElementGraphql(final Element element, MapRequests mapRequests) {

		ElementHelper.assign(element, this);

		this.element = element;
		this.mapRequests = mapRequests;
	}

	public ElementGraphql(final Element element) {

		ElementHelper.assign(element, this);

		this.element = element;
		this.mapRequests = null;
	}

	@Override
	public List<ElementValueVO> getElementValues() {

		if (super.getElementValues() == null) {

			if (element != null) {

				List<ElementValue> elementValues = null;

				if (mapRequests == null) {

					elementValues = EntitiesManager.getInstance().getElementValueRepository()
							.gets(element.getElementTypeId(), element.getId(), false);

				} else {

					List<BasicElement> basicElement = null;

					if (mapRequests.isGetAll()) {

						basicElement = mapRequests.get(ElementValueRepository.class, "getAll", null);

					} else {

						basicElement = mapRequests.get(ElementValueRepository.class, "getsByElementType",
								element.getElementTypeId());
					}

					if (!CollectionUtils.isEmpty(basicElement)) {

						elementValues = basicElement.stream()
								.filter(be -> element.getElementTypeId().equals(((ElementValue) be).getElementTypeId())
										&& element.getId().equals(((ElementValue) be).getElementId()))
								.map(ElementValue.class::cast).collect(Collectors.toList());
					}
				}

				if (!CollectionUtils.isEmpty(elementValues)) {

					List<ElementValueVO> elementValuesVO = new ArrayList<>();

					for (ElementValue elementValue : elementValues) {

						elementValuesVO.add(new ElementValueGraphql(elementValue));
					}
					super.setElementValues(elementValuesVO);
				}

			} else {

				log.error("element has null pointer");
			}
		}

		return super.getElementValues();
	}

	@Override
	public ElementTypeVO getElementType() {

		if (super.getElementType() == null && element != null) {

			ElementType elementType = EntitiesManager.getInstance().getElementTypeRepository()
					.get(element.getElementTypeId(), false);

			if (elementType != null) {

				super.setElementType(new ElementTypeGraphql(elementType));
			}
		}

		return super.getElementType();
	}

	@Override
	public ElementSubtypeVO getElementSubtype() {

		if (super.getElementSubtype() == null && element != null) {

			ElementSubtype elementSubtype = EntitiesManager.getInstance().getElementSubtypeRepository()
					.get(element.getElementSubtypeId(), element.getElementTypeId(), false);

			if (elementSubtype != null) {

				super.setElementSubtype(new ElementSubtypeGraphql(elementSubtype));
			}
		}

		return super.getElementSubtype();
	}

	@Override
	public LocationVO getLocation() {

		if (super.getLocation() == null && element != null && element.getLocationId() != null) {

			Location location = EntitiesManager.getInstance().getLocationRepository().get(element.getLocationId(),
					false);

			if (location != null) {

				super.setLocation(new LocationGraphql(location));
			}
		}

		return super.getLocation();
	}

	@Override
	@Deprecated
	public Long getState() {

		if (super.getState() == ElementTypeState.STATE_UNKNOWN) {

			if (element != null && element.getId() != null) {

				if (elementState == null) {

					if (mapRequests == null) {

						elementState = EntitiesManager.getInstance().getElementStateRepository()
								.get(element.getElementTypeId(), element.getId(), false);
					} else {

						List<BasicElement> basicElement = null;

						if (mapRequests.isGetAll()) {

							basicElement = mapRequests.get(ElementStateRepository.class, "getAll", null);

						} else {

							basicElement = mapRequests.get(ElementStateRepository.class, "getsByElementType",
									element.getElementTypeId());
						}

						if (!CollectionUtils.isEmpty(basicElement)) {

							elementState = (ElementState) basicElement.stream().filter(
									be -> element.getElementTypeId().equals(((ElementState) be).getElementTypeId())
											&& element.getId().equals(((ElementState) be).getElementId()))
									.findFirst().orElse(null);
						}
					}

				}

				if (elementState != null) {

					super.setState(elementState.getStateId());
				}

			} else {

				log.error("element has null pointer");
			}
		}

		return super.getState();
	}

	@Override
	public ElementStateVO getElementState() {

		if (element != null) {
			
			if (elementState == null) {

				if (mapRequests == null) {

					elementState = EntitiesManager.getInstance().getElementStateRepository()
							.get(element.getElementTypeId(), element.getId(), false);
				} else {

					List<BasicElement> basicElement = null;

					if (mapRequests.isGetAll()) {

						basicElement = mapRequests.get(ElementStateRepository.class, "getAll", null);

					} else {

						basicElement = mapRequests.get(ElementStateRepository.class, "getsByElementType",
								element.getElementTypeId());
					}

					if (!CollectionUtils.isEmpty(basicElement)) {

						elementState = (ElementState) basicElement.stream()
								.filter(be -> element.getElementTypeId().equals(((ElementState) be).getElementTypeId())
										&& element.getId().equals(((ElementState) be).getElementId()))
								.findFirst().orElse(null);
					}
				}
			}

			if (elementState != null) {

				super.setElementState(new ElementStateGraphql(elementState));
			}
		}
		return super.getElementState();
	}

	@Override
	public ElementTypeStateVO getElementTypeState() {

		if (super.getElementTypeState() == null) {

			if (element != null) {

				if (elementState == null) {

					if (mapRequests == null) {

						elementState = EntitiesManager.getInstance().getElementStateRepository()
								.get(element.getElementTypeId(), element.getId(), false);
					} else {

						List<BasicElement> basicElement = null;

						if (mapRequests.isGetAll()) {

							basicElement = mapRequests.get(ElementStateRepository.class, "getAll", null);

						} else {

							basicElement = mapRequests.get(ElementStateRepository.class, "getsByElementType",
									element.getElementTypeId());
						}

						if (!CollectionUtils.isEmpty(basicElement)) {

							elementState = (ElementState) basicElement.stream().filter(
									be -> element.getElementTypeId().equals(((ElementState) be).getElementTypeId())
											&& element.getId().equals(((ElementState) be).getElementId()))
									.findFirst().orElse(null);
						}
					}
				}

				if (elementState != null) {

					ElementTypeState elementTypeState = EntitiesManager.getInstance().getElementTypeStateRepository()
							.get(elementState.getElementTypeId(), elementState.getStateId(), false);

					if (elementTypeState != null) {

						super.setElementTypeState(new ElementTypeStateGraphql(elementTypeState));
					}
				}

			} else {

				log.error("element has null pointer");
			}
		}

		return super.getElementTypeState();
	}

	@Override
	public List<ElementVO> getChilds() {

		List<ElementVO> elementChildsVO = new ArrayList<>();

		if (super.getChilds() == null) {

			if (element != null) {

				List<ElementHierarchy> elementHierarchyParents = EntitiesManager.getInstance()
						.getElementHierarchiesRepository()
						.getsByParent(element.getElementTypeId(), element.getId(), false);

				if (!CollectionUtils.isEmpty(elementHierarchyParents)) {

					for (ElementHierarchy elementHierarchyParent : elementHierarchyParents) {

						Element elementChild = EntitiesManager.getInstance().getElementRepository().get(
								elementHierarchyParent.getChildElementTypeId(),
								elementHierarchyParent.getChildElementId(), false);

						if (elementChild != null) {

							elementChildsVO.add(new ElementGraphql(elementChild, mapRequests));
						}
					}

					super.setChilds(elementChildsVO);
				}

			} else {

				log.error("element has null pointer");
			}
		}

		return super.getChilds();
	}

	@Override
	public List<ElementVO> getParents() {

		List<ElementVO> elementParentVO = new ArrayList<>();

		if (super.getParents() == null) {

			if (element != null) {

				List<ElementHierarchy> elementHierarchyChilds = EntitiesManager.getInstance()
						.getElementHierarchiesRepository()
						.getsByChild(element.getElementTypeId(), element.getId(), false);

				if (!CollectionUtils.isEmpty(elementHierarchyChilds)) {

					for (ElementHierarchy elementHierarchyChild : elementHierarchyChilds) {

						Element elementParent = EntitiesManager.getInstance().getElementRepository().get(
								elementHierarchyChild.getParentElementTypeId(),
								elementHierarchyChild.getParentElementId(), false);

						if (elementParent != null) {

							elementParentVO.add(new ElementGraphql(elementParent, mapRequests));
						}
					}

					super.setParents(elementParentVO);
				}

			} else {

				log.error("element has null pointer");
			}
		}

		return super.getParents();
	}

	@Override
	public List<AlarmVO> getAlarms() {

		if (super.getAlarms() == null) {

			if (element != null && element.getElementTypeId() != null && element.getId() != null) {

				List<Alarm> alarms = null;

				if (mapRequests == null) {

					alarms = EntitiesManager.getInstance().getAlarmRepository()
							.getsByTargetElement(element.getElementTypeId(), element.getId(), false);
				} else {

					List<BasicElement> basicElement = null;

					if (mapRequests.isGetAll()) {

						basicElement = mapRequests.get(AlarmRepository.class, "getAll", null);

					} else {

						basicElement = mapRequests.get(AlarmRepository.class, "getsByElementType",
								element.getElementTypeId());
					}

					if (!CollectionUtils.isEmpty(basicElement)) {

						alarms = basicElement.stream()
								.filter(be -> element.getElementTypeId().equals(((Alarm) be).getTargetElementTypeId())
										&& element.getId().equals(((Alarm) be).getTargetElementId()))
								.map(Alarm.class::cast).collect(Collectors.toList());
					}
				}

				if (!CollectionUtils.isEmpty(alarms)) {

					List<AlarmVO> alarmsVO = new ArrayList<>();

					for (Alarm alarm : alarms) {

						if (!ObjectUtils.isEmpty(alarm)) {

							alarmsVO.add(new AlarmGraphql(alarm));
						}
					}
					super.setAlarms(alarmsVO);
				}

			} else {

				log.error("element has null pointer");
			}
		}

		return super.getAlarms();
	}

	@Override
	public List<CommandElementTypeVO> getCommandElements() {

		if (super.getCommandElements() == null) {

			List<CommandElementTypeVO> commandElementTypesVO = null;

			if (element == null) {
				log.error("element has null pointer");
			} else if (element.getElementSubtypeId() != null && element.getElementTypeId() != null) {

				List<ElementSubtypeActiveCommand> elementSubtypeActiveCommands = EntitiesManager.getInstance()
						.getElementSubtypeActiveCommandRepository()
						.getsByElementSubtype(element.getElementTypeId(), element.getElementSubtypeId(), false);

				if (!CollectionUtils.isEmpty(elementSubtypeActiveCommands)) {

					commandElementTypesVO = new ArrayList<>();

					for (ElementSubtypeActiveCommand oElement : elementSubtypeActiveCommands) {

						CommandElementType commandElementType = EntitiesManager.getInstance()
								.getCommandElementTypeRepository()
								.get(oElement.getCommandElementTypeId(), oElement.getElementTypeId(), false);

						if (commandElementType != null) {

							commandElementTypesVO.add(new CommandElementTypeGraphql(commandElementType));
						}
					}
				}

			} else if (element.getElementTypeId() != null) {

				List<CommandElementType> commandElementTypes = EntitiesManager.getInstance()
						.getCommandElementTypeRepository().getsByElementType(element.getElementTypeId(), false);

				if (!CollectionUtils.isEmpty(commandElementTypes)) {

					commandElementTypesVO = new ArrayList<>();

					for (CommandElementType oElement : commandElementTypes) {

						commandElementTypesVO.add(new CommandElementTypeGraphql(oElement));
					}
				}
			}

			super.setCommandElements(commandElementTypesVO);
		}

		return super.getCommandElements();
	}

	/**
	 * Method that return a GraphicGroup list associated to an ElementGraphql object
	 */
	@Override
	public List<VmsGraphicGroupVO> getGraphicGroups() {

		if (super.getGraphicGroups() == null && element != null) {

			List<VmsGraphicGroupElement> graphicGroupElements = EntitiesManager.getInstance()
					.getVmsGraphicGroupElementRepository()
					.getsByElement(element.getElementTypeId(), element.getId(), false);

			if (!CollectionUtils.isEmpty(graphicGroupElements)) {

				List<VmsGraphicGroupVO> graphicGroupsVO = new ArrayList<>();

				for (VmsGraphicGroupElement graphicGroupElement : graphicGroupElements) {

					VmsGraphicGroup graphicGroup = EntitiesManager.getInstance().getVmsGraphicGroupRepository()
							.get(graphicGroupElement.getGraphicGroupId(), false);

					graphicGroupsVO.add(new VmsGraphicGroupGraphql(graphicGroup));
				}

				if (!CollectionUtils.isEmpty(graphicGroupsVO)) {

					super.setGraphicGroups(graphicGroupsVO);
				}
			}
		}

		return super.getGraphicGroups();
	}

	@Override
	public PmQueueVO getQueue() {

		if (super.getQueue() == null && element != null) {

			List<PmElementTypeQueue> pmElementTypeQueue = EntitiesManager.getInstance()
					.getPmElementTypeQueueRepository().getsByElementType(element.getElementTypeId(), false);

			if (CollectionUtils.isNotEmpty(pmElementTypeQueue)) {

				PmQueue pmQueue = EntitiesManager.getInstance().getPmQueueRepository()
						.get(pmElementTypeQueue.get(0).getQueueId(), false);

				if (pmQueue != null) {

					super.setQueue(new PmQueueGraphql(pmQueue, element, mapRequests));
				}
			}

		}

		return super.getQueue();
	}
}
