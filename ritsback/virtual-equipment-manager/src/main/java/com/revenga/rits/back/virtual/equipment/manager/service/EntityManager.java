package com.revenga.rits.back.virtual.equipment.manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.SmStateMachine;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetElementMeasuresChangeCommand;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.repository.AlarmRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementHierarchiesRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.PmQueuePriorityRepository;
import com.revenga.rits.back.entities.lib.repository.SmStateMachineRepository;
import com.revenga.rits.back.front.core.model.helper.BasicElementHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntityManager {

	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "entities.manager.retention_time_conf"));

	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.retention_time_master"));

	private static EntityManager instance;

	private ElementValueRepository elementValueRepository;
	private ElementRepository elementRepository;
	private ElementTypeParamRepository elementTypeParamRepository;
	private ElementHierarchiesRepository elementHierarchiesRepository;
	private CommandElementTypeRepository commandElementTypeRepository;
	private CommandElementTypeParamRepository commandElementTypeParamRepository;
	private AlarmRepository alarmRepository;
	private PmQueuePriorityRepository pmQueuePriorityRepository;

	private Map<Long, SmStateMachine> stateMachines;

	private EntityManager() {

		this.elementValueRepository = new ElementValueRepository(false);
		this.elementTypeParamRepository = new ElementTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.elementRepository = new ElementRepository(false);
		this.elementHierarchiesRepository = new ElementHierarchiesRepository(true, CACHE_RETENTION_TIME_CONF);
		this.commandElementTypeRepository = new CommandElementTypeRepository(true);
		this.commandElementTypeParamRepository = new CommandElementTypeParamRepository(true);
		this.alarmRepository = new AlarmRepository(false);
		this.pmQueuePriorityRepository = new PmQueuePriorityRepository(true, CACHE_RETENTION_TIME_MASTER);

		this.stateMachines = new HashedMap<>();
	}

	public static EntityManager getInstance() {

		if (instance == null) {

			instance = new EntityManager();
		}

		return instance;
	}

	public List<Element> getElements(final Long elementTypeId) {

		return elementRepository.gets(elementTypeId, false);
	}

	public List<Element> getElementWithValues(final Long elementTypeId) {

		List<Element> elements = null;

		List<ElementValue> elementValues = elementValueRepository.getsByElementType(elementTypeId, false);

		if (CollectionUtils.isNotEmpty(elementValues)) {

			while (!elementValues.isEmpty()) {

				Long elementId = null;

				for (int i = elementValues.size() - 1; i >= 0 && elementId == null; i--) {

					elementId = elementValues.get(i).getElementId();

					if (elementId == null) {

						elementValues.remove(i);
					}
				}

				if (elementId != null) {

					Long id = elementId;

					Element element = new Element();
					element.setId(id);
					element.setElementTypeId(elementTypeId);

					List<ElementValue> values = elementValues.stream().filter(b -> b.getElementId().equals(id))
							.collect(Collectors.toList());

					element.setElementValues(values);

					if (elements == null) {

						elements = new ArrayList<>();
					}

					elements.add(element);
					elementValues.removeAll(values);
				}
			}
		}
		return elements;
	}

	public void initElements(final Long elementTypeId) {

		Long connectedElementTypeStateId = getConnectedElementTypeStateId(elementTypeId);

		if (connectedElementTypeStateId != null) {

			List<Element> elements = getElements(elementTypeId);

			if (CollectionUtils.isNotEmpty(elements)) {

				SetElementStatesChangeCommand setElementStatesChangeCommand = new SetElementStatesChangeCommand();

				for (Element element : elements) {

					ElementState elementState = new ElementState();
					elementState.setElementId(element.getId());
					elementState.setElementTypeId(element.getElementTypeId());
					elementState.setStateId(connectedElementTypeStateId);

					setElementStatesChangeCommand.addElementState(elementState);
				}

				if (EntityManager.getInstance().sendCommand(setElementStatesChangeCommand) == null) {

					log.error("SendCommand " + setElementStatesChangeCommand);
				}

			}
		} else {

			log.error("Not found ConnectedElementTypeStateId in element type " + elementTypeId);
		}

	}

	public Long getConnectedElementTypeStateId(final Long elementTypeId) {

		Long connectedElementTypeStateId = null;

		if (elementTypeId != null) {

			ElementType elementType = (new ElementTypeRepository(false)).get(elementTypeId, false);

			if (elementType != null && elementType.getConnectedElementTypeStateId() != null) {

				connectedElementTypeStateId = elementType.getConnectedElementTypeStateId();
			}
		}
		return connectedElementTypeStateId;
	}

	public Element getFirtParentByType(final Long parentElementTypeId, final Long childElementTypeId,
			final Long childElementId) {

		Element element = null;

		if (parentElementTypeId != null && childElementTypeId != null && childElementId != null) {

			List<ElementHierarchy> elementHierarchies = this.elementHierarchiesRepository
					.getsByChild(parentElementTypeId, childElementTypeId, childElementId, false);

			if (CollectionUtils.isNotEmpty(elementHierarchies)) {

				ElementHierarchy elementHierarchie = elementHierarchies.get(0);

				if (elementHierarchie != null) {

					element = new Element();
					element.setId(elementHierarchie.getParentElementId());
					element.setElementTypeId(elementHierarchie.getParentElementTypeId());
				}
			}
		}
		return element;
	}

	public List<Element> getParentsByType(final Long parentElementTypeId, final Long childElementTypeId,
			final Long childElementId) {

		List<Element> elements = null;

		if (parentElementTypeId != null && childElementTypeId != null && childElementId != null) {

			List<ElementHierarchy> elementHierarchies = this.elementHierarchiesRepository
					.getsByChild(parentElementTypeId, childElementTypeId, childElementId, false);

			if (CollectionUtils.isNotEmpty(elementHierarchies)) {

				for (ElementHierarchy elementHierarchie : elementHierarchies) {

					if (elementHierarchie.getParentElementId() != null
							&& elementHierarchie.getParentElementTypeId() != null) {

						Element element = new Element();
						element.setId(elementHierarchie.getParentElementId());
						element.setElementTypeId(elementHierarchie.getParentElementTypeId());

						if (elements == null) {

							elements = new ArrayList<>();
						}

						elements.add(element);
					}
				}
			}
		}
		return elements;
	}

	public Element getFirtChildByType(final Long parentElementTypeId, final Long parentElementId,
			final Long childElementTypeId) {

		Element element = null;

		if (parentElementTypeId != null && parentElementId != null && childElementTypeId != null) {

			List<ElementHierarchy> elementHierarchies = this.elementHierarchiesRepository
					.getsByParent(parentElementTypeId, parentElementId, childElementTypeId, false);

			if (CollectionUtils.isNotEmpty(elementHierarchies)) {

				ElementHierarchy elementHierarchie = elementHierarchies.get(0);

				if (elementHierarchie != null) {

					element = new Element();
					element.setId(elementHierarchie.getChildElementId());
					element.setElementTypeId(elementHierarchie.getChildElementTypeId());
				}
			}
		}
		return element;
	}

	public List<Element> getChildByType(final Long parentElementTypeId, final Long parentElementId,
			final Long childElementTypeId) {

		List<Element> elements = null;

		if (parentElementTypeId != null && parentElementId != null && childElementTypeId != null) {

			List<ElementHierarchy> elementHierarchies = this.elementHierarchiesRepository
					.getsByParent(parentElementTypeId, parentElementId, childElementTypeId, false);

			if (CollectionUtils.isNotEmpty(elementHierarchies)) {

				for (ElementHierarchy elementHierarchy : elementHierarchies) {

					Element element = new Element();
					element.setId(elementHierarchy.getChildElementId());
					element.setElementTypeId(elementHierarchy.getChildElementTypeId());

					if (elements == null) {

						elements = new ArrayList<>();
					}
					elements.add(element);
				}

			}
		}
		return elements;
	}

	public Command sendCommand(Command command) {

		log.info("SendCommand " + command);
		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}

	public void sendCommands(List<Command> commands) {

		List<ElementState> elementStates = null;
		Map<String, List<ElementValue>> mapElementValues = null;
		List<Command> cmds = null;

		if (CollectionUtils.isNotEmpty(commands)) {

			for (Command command : commands) {

				if (command instanceof SetElementMeasuresChangeCommand) {

					SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = (SetElementMeasuresChangeCommand) command;

					for (Element element : setElementMeasuresChangeCommand.getElements()) {

						String elementId = BasicElementHelper.getIdFromElement(element);

						if (CollectionUtils.isNotEmpty(element.getElementValues())) {

							for (ElementValue elementValue : element.getElementValues()) {

								if (mapElementValues == null) {

									mapElementValues = new HashedMap<>();
								}

								List<ElementValue> elementValues = mapElementValues.get(elementId);

								if (elementValues == null) {

									elementValues = new ArrayList<>();
									mapElementValues.put(elementId, elementValues);
								}
								elementValues.add(elementValue);
							}
						}
					}

				} else if (command instanceof SetElementStatesChangeCommand) {

					SetElementStatesChangeCommand setElementStatesChangeCommand = (SetElementStatesChangeCommand) command;

					if (CollectionUtils.isNotEmpty(setElementStatesChangeCommand.getElementStates())) {

						for (ElementState elementState : setElementStatesChangeCommand.getElementStates()) {

							if (elementStates == null) {

								elementStates = new ArrayList<>();
							}

							elementStates.add(elementState);
						}
					}
				} else {

					if (cmds == null) {

						cmds = new ArrayList<>();
					}
					cmds.add(command);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(elementStates)) {

			SetElementStatesChangeCommand setElementStatesChangeCommand = new SetElementStatesChangeCommand();

			for (ElementState elementState : elementStates) {

				setElementStatesChangeCommand.addElementState(elementState);
			}
			sendCommand(setElementStatesChangeCommand);

			try {

				Thread.sleep(200);

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
		}

		if (mapElementValues != null && !mapElementValues.isEmpty()) {

			SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = null;

			for (Entry<String, List<ElementValue>> entry : mapElementValues.entrySet()) {

				if ((CollectionUtils.isNotEmpty(entry.getValue()))) {

					Element element = getElement(entry.getKey());
					element.setElementValues(entry.getValue());

					if (setElementMeasuresChangeCommand == null) {

						setElementMeasuresChangeCommand = new SetElementMeasuresChangeCommand();
					}
					setElementMeasuresChangeCommand.addElement(element);
				}
			}

			if (setElementMeasuresChangeCommand != null) {

				sendCommand(setElementMeasuresChangeCommand);
			}
		}

		if (CollectionUtils.isNotEmpty(cmds)) {

			for (Command cmd : cmds) {

				sendCommand(cmd);
			}
		}
	}

	public ElementValue getElementValue(final Long elementTypeId, final Long elementId, final Long elementTypeParamId,
			final Long paramTypeId) {

		return elementValueRepository.get(elementTypeId, elementId, elementTypeParamId, paramTypeId, false);
	}

	public List<ElementValue> getByElementTypeParam(final Long elementTypeId, final Long elementTypeParamId,
			final Long paramTypeId) {

		return elementValueRepository.getByElementTypeParam(elementTypeId, elementTypeParamId, paramTypeId, false);
	}

	public CommandElementType getCommandElementType(final Long commandElementTypeId, final Long elementTypeId) {

		return commandElementTypeRepository.get(commandElementTypeId, elementTypeId, false);
	}

	public CommandElementTypeParam getCommandElementTypeParam(final Long commandElementTypeId, final Long elementTypeId,
			final Long comandElementTypeParamId) {

		return commandElementTypeParamRepository.get(commandElementTypeId, elementTypeId, comandElementTypeParamId,
				false);
	}
	
	public PmQueuePriority getPmQueuePriority(final Long queueId, final Long id) {
		
		return pmQueuePriorityRepository.get(queueId, id, false);
	}

	public ElementTypeParam getElementTypeParam(final Long elementTypeId, final Long paramTypeId, final Long id) {

		return elementTypeParamRepository.get(elementTypeId, paramTypeId, id, false);
	}

	public Element getElement(final String elementId) {

		Element element = null;

		Key key = new Key(elementId);

		if (key.getClazz() == Element.class) {

			element = new Element();
			element.setPks(key.getIds());
		}

		return element;
	}

	public List<Alarm> getsAlarmByElementType(final Long elementTypeId) {

		return alarmRepository.getsByElementType(elementTypeId, false);
	}

	public SmStateMachine getStateMachine(Long id) {

		SmStateMachine stateMachine = null;

		if (id != null) {

			if (stateMachines.containsKey(id)) {

				stateMachine = stateMachines.get(id);

			} else {
				stateMachine = (new SmStateMachineRepository()).get(id, true);

				if (stateMachine != null) {

					stateMachines.put(id, stateMachine);
				}
			}
		}

		return stateMachine;
	}

	public List<ElementHierarchy> getElementHierarchyByParentType(final Long parentElementTypeId) {

		List<ElementHierarchy> elementHierarchies = null;

		if (parentElementTypeId != null) {

			elementHierarchies = new ElementHierarchiesRepository(false).getsByParent(parentElementTypeId, false);
		}
		return elementHierarchies;
	}

}
