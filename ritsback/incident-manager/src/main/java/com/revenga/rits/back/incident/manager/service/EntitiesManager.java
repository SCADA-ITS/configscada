package com.revenga.rits.back.incident.manager.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementSubtype;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.PmElementTypeQueue;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.Spath;
import com.revenga.rits.back.data.core.model.SpathStretch;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.AlarmRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementHierarchiesRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.LocationRepository;
import com.revenga.rits.back.entities.lib.repository.PmElementTypeQueueRepository;
import com.revenga.rits.back.entities.lib.repository.PmQueuePriorityRepository;
import com.revenga.rits.back.entities.lib.repository.StretchLocationRepository;
import com.revenga.rits.back.entities.lib.repository.UserRepository;
import com.revenga.rits.back.front.core.model.helper.CommandElementTypeParamHelper;
import com.revenga.rits.back.front.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;
import com.revenga.rits.back.incident.manager.task.model.SignallingCommandToSpath;
import com.revenga.rits.back.incident.manager.task.model.SignallingCommandToStretch;

public class EntitiesManager {

	private static final String FILE_PROPERTIES = "application.properties";

	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_conf"));

	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_master"));

	private static EntitiesManager instance;

	private LocationRepository locationRepository;
	private StretchLocationRepository stretchLocationRepository;
	private AlarmRepository alarmRepository;
	private ElementRepository elementRepository;
	private ElementValueRepository elementValueConfigRepository;
	private ElementHierarchiesRepository elementHierarchiesRepository;
	private CommandElementTypeRepository commandElementTypeRepository;
	private CommandElementTypeParamRepository commandElementTypeParamRepository;
	private PmQueuePriorityRepository pmQueuePriorityRepository;
	private PmElementTypeQueueRepository pmElementTypeQueueRepository;
	private UserRepository userRepository;

	public static EntitiesManager getInstance() {

		if (instance == null) {

			instance = new EntitiesManager();
		}

		return instance;
	}

	private EntitiesManager() {

		this.locationRepository = new LocationRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.stretchLocationRepository = new StretchLocationRepository(true, CACHE_RETENTION_TIME_CONF);
		this.alarmRepository = new AlarmRepository(false);
		this.elementRepository = new ElementRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementHierarchiesRepository = new ElementHierarchiesRepository(true, CACHE_RETENTION_TIME_CONF);
		this.commandElementTypeRepository = new CommandElementTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.commandElementTypeParamRepository = new CommandElementTypeParamRepository(true,
				CACHE_RETENTION_TIME_MASTER);

		this.pmQueuePriorityRepository = new PmQueuePriorityRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.pmElementTypeQueueRepository = new PmElementTypeQueueRepository(true, CACHE_RETENTION_TIME_MASTER);

		this.userRepository = new UserRepository(true, CACHE_RETENTION_TIME_CONF);

		EntitiesManagerHelper.cacheRepository(this);

		this.elementValueConfigRepository = new ElementValueRepository(true, CACHE_RETENTION_TIME_CONF);
	}

	public User getUser(Long userId) {

		return userRepository.get(userId, false);
	}

	public List<StretchLocation> getStretchLocationByLocation(Long locationId) {

		return stretchLocationRepository.getByLocation(locationId, false);
	}

	public List<StretchLocation> getStretchLocationByStrech(Long stretchId) {

		return stretchLocationRepository.getByStretch(stretchId, false);
	}

	private List<StretchLocation> getStretchLocationByStrech(Long stretchId, Integer from, Integer to) {

		List<StretchLocation> stretchLocations = stretchLocationRepository.getByStretch(stretchId, false);

		if (!CollectionUtils.isEmpty(stretchLocations)) {
		
			stretchLocations = stretchLocations.stream().filter(stretchLocation -> stretchLocation.getPosition() != null 
					&& (from == null || stretchLocation.getPosition().intValue() >= from.intValue()) 
					&& (to == null || stretchLocation.getPosition().intValue() <= to.intValue())).collect(Collectors.toList());		
		}
		
		return stretchLocations;
	}

	public Alarm getAlarm(Long alarmId) {

		return alarmRepository.get(alarmId, false);
	}

	public Element getElement(final Long elementTypeId, final Long elementId) {

		return elementRepository.get(elementTypeId, elementId, false);
	}

	public List<Element> getElementByElementType(final Long elementTypeId) {

		return elementRepository.gets(elementTypeId, false);
	}

	public ElementValue getConfigValue(Long elementTypeId, Long elementId, Long elementTypeParamId) {

		return elementValueConfigRepository.get(elementTypeId, elementId, elementTypeParamId, ParamType.CONFIG, false);
	}

	public Set<Long> getElementByStretch(final Long elementTypeId, final Long stretchId) {

		Set<Long> elements = null;

		if (elementTypeId != null && stretchId != null) {

			List<Element> elementList = getElementByElementType(elementTypeId);

			if (CollectionUtils.isNotEmpty(elementList)) {

				List<StretchLocation> stretchLocations = EntitiesManager.getInstance()
						.getStretchLocationByStrech(stretchId);

				if (CollectionUtils.isNotEmpty(stretchLocations)) {

					for (Element element : elementList) {

						if (element != null && stretchLocations.stream()
								.filter(be -> be.getLocationId().equals(element.getLocationId())).findFirst()
								.orElse(null) != null) {

							if (elements == null) {

								elements = new HashSet<>();
							}
							elements.add(element.getId());
						}
					}
				}
			}
		}

		return elements;
	}

	public List<ElementHierarchy> getsByParent(Long parentElementTypeId, Long parentElementId,
			Long childElementTypeId) {

		return this.elementHierarchiesRepository.getsByParent(parentElementTypeId, parentElementId, childElementTypeId,
				false);
	}

	public List<ElementHierarchy> getsByChild(Long parentElementTypeId, Long childElementTypeId, Long childElementId) {

		return this.elementHierarchiesRepository.getsByChild(parentElementTypeId, childElementTypeId, childElementId,
				false);
	}

	public List<Element> getElementByStretchs(final Long elementTypeId, final List<Long> stretchIds) {

		List<Element> elements = new ArrayList<>();

		if (elementTypeId != null && CollectionUtils.isNotEmpty(stretchIds)) {

			List<Element> elementList = getElementByElementType(elementTypeId);

			if (CollectionUtils.isNotEmpty(elementList)) {

				List<Element> elementFiltered = new ArrayList<>(elementList);

				for (Long stretchId : stretchIds) {

					List<StretchLocation> stretchLocations = EntitiesManager.getInstance()
							.getStretchLocationByStrech(stretchId);

					if (CollectionUtils.isNotEmpty(stretchLocations)) {

						for (StretchLocation stretchLocation : stretchLocations) {

							List<Element> elementsInStretch = elementFiltered.stream()
									.filter(element -> stretchLocation.getLocationId().equals(element.getLocationId()))
									.collect(Collectors.toList());

							if (CollectionUtils.isNotEmpty(elementsInStretch)) {

								elementsInStretch.forEach(elementInStretch -> {

									elements.add(elementInStretch);
									elementFiltered.remove(elementInStretch);
								});
							}
						}
					}
				}
			}
		}

		return elements;
	}

	public List<Element> getElementByStretchs(final Long elementTypeId, final Long stretchId, final Integer from,
			final Integer to) {

		List<Element> elements = new ArrayList<>();

		if (elementTypeId != null && stretchId != null) {

			List<Element> elementList = getElementByElementType(elementTypeId);

			if (CollectionUtils.isNotEmpty(elementList)) {

				List<Element> elementFiltered = new ArrayList<>(elementList);

				List<StretchLocation> stretchLocations = EntitiesManager.getInstance()
						.getStretchLocationByStrech(stretchId, from, to);

				if (CollectionUtils.isNotEmpty(stretchLocations)) {

					for (StretchLocation stretchLocation : stretchLocations) {

						List<Element> elementsInStretch = elementFiltered.stream()
								.filter(element -> stretchLocation.getLocationId().equals(element.getLocationId()))
								.collect(Collectors.toList());

						if (CollectionUtils.isNotEmpty(elementsInStretch)) {

							elementsInStretch.forEach(elementInStretch -> {

								elements.add(elementInStretch);
								elementFiltered.remove(elementInStretch);
							});
						}
					}
				}
			}
		}

		return elements;
	}

	public Command getCommandByCommandElement(CommandElementVO commandElementVO) {

		Command command = null;

		if (commandElementVO != null) {

			Key commandElementTypeIdKey = new Key(commandElementVO.getCommandElementTypeId());

			if (commandElementTypeIdKey.getClazz() == CommandElementType.class) {

				CommandElementType commandElementType = new CommandElementType();
				commandElementType.setPks(commandElementTypeIdKey.getIds());

				commandElementType = commandElementTypeRepository.get(commandElementType.getId(),
						commandElementType.getElementTypeId(), false);

				if (commandElementType != null) {

					if (commandElementVO.getCommandElementValues() != null) {

						Key key = null;

						for (CommandElementValueVO commandElementValue : commandElementVO.getCommandElementValues()) {

							if (commandElementValue.getCommandElementTypeParamId() != null) {

								key = new Key(commandElementValue.getCommandElementTypeParamId());

								if (key.getClazz() == CommandElementTypeParam.class) {

									CommandElementTypeParam commandElementTypeParam = null;

									commandElementTypeParam = commandElementTypeParamRepository.get(
											Long.valueOf(key.getId(1)), Long.valueOf(key.getId(2)),
											Long.valueOf(key.getId(3)), false);

									if (commandElementTypeParam != null) {

										commandElementValue.setCommandElementTypeParam(
												CommandElementTypeParamHelper.get(commandElementTypeParam));
									}
								}
							}
						}
					}
					command = SignallingCommandHelper.get(commandElementVO);
				}
			}

		}

		return command;
	}

	public CommandElementType getCommandElementType(Long id, Long elementTypeId) {

		return (id != null && elementTypeId != null) ? commandElementTypeRepository.get(id, elementTypeId, false)
				: null;
	}

	public Long getSpathId(SignallingCommandToSpath signallingCommandToSpath,
			ImsIncidentTypeStretch incidentTypeStretch) {

		Long spathId = null;

		if (signallingCommandToSpath != null && incidentTypeStretch != null) {

			spathId = incidentTypeStretch.getSpathId();

			if (signallingCommandToSpath.getSpathId() != null) {

				Key spathKey = new Key(signallingCommandToSpath.getSpathId());

				if (spathKey.getClazz() == Spath.class) {

					Spath spath = new Spath();

					spath.setPks(spathKey.getIds());

					spathId = spath.getId();
				}
			}

		}

		return spathId;
	}

	public List<Element> getElements(SignallingCommandToSpath signallingCommandToSpath,
			List<SpathStretch> spathStretchs, Long elementType) {

		List<Element> elements = null;

		if (signallingCommandToSpath != null && CollectionUtils.isNotEmpty(spathStretchs)) {

			List<SpathStretch> spathStretchsInRange = null;

			if (signallingCommandToSpath.getFrom() != null && signallingCommandToSpath.getTo() != null) {

				spathStretchsInRange = spathStretchs.stream()
						.filter(spathStretch -> spathStretch.getPosition() != null
								&& spathStretch.getPosition() >= signallingCommandToSpath.getFrom()
								&& spathStretch.getPosition() <= signallingCommandToSpath.getTo())
						.collect(Collectors.toList());
			} else {

				spathStretchsInRange = spathStretchs;
			}

			if (CollectionUtils.isNotEmpty(spathStretchsInRange)) {

				List<Long> stretchsId = spathStretchsInRange.stream().map(SpathStretch::getRelStretchId)
						.collect(Collectors.toList());

				elements = EntitiesManager.getInstance().getElementByStretchs(elementType, stretchsId);

				if (CollectionUtils.isNotEmpty(elements)) {

					if (signallingCommandToSpath.getElementSubtypeId() != null) {

						Key elementSubtypeKey = new Key(signallingCommandToSpath.getElementSubtypeId());

						if (elementSubtypeKey.getClazz() == ElementSubtype.class) {

							ElementSubtype elementSubtype = new ElementSubtype();

							elementSubtype.setPks(elementSubtypeKey.getIds());

							// lanes: null
							if (signallingCommandToSpath.getLanes() == null) {

								elements = elements.stream().filter(
										element -> elementSubtype.getElementTypeId().equals(element.getElementTypeId())
												&& elementSubtype.getId().equals(element.getElementSubtypeId()))
										.collect(Collectors.toList());

								// lanes: []
							} else if (CollectionUtils.isEmpty(signallingCommandToSpath.getLanes())) {

								elements = elements.stream().filter(element -> {

									if (elementSubtype.getElementTypeId().equals(element.getElementTypeId())
											&& elementSubtype.getId().equals(element.getElementSubtypeId())
											&& element.getLocationId() != null) {

										Location location = locationRepository.get(element.getLocationId(), false);

										if (location != null && location.getLane() == null) {

											return true;
										}
									}
									return false;

								}).collect(Collectors.toList());

								// lanes : [1, 2 ....]
							} else {

								elements = elements.stream().filter(element -> {

									if (elementSubtype.getElementTypeId().equals(element.getElementTypeId())
											&& elementSubtype.getId().equals(element.getElementSubtypeId())
											&& element.getLocationId() != null) {

										Location location = locationRepository.get(element.getLocationId(), false);

										if (location != null && location.getLane() != null
												&& signallingCommandToSpath.getLanes().contains(location.getLane())) {

											return true;
										}
									}
									return false;

								}).collect(Collectors.toList());
							}
						}

						// lanes: []
					} else if (signallingCommandToSpath.getLanes() != null
							&& CollectionUtils.isEmpty(signallingCommandToSpath.getLanes())) {

						elements = elements.stream().filter(element -> {

							if (element.getLocationId() != null) {

								Location location = locationRepository.get(element.getLocationId(), false);

								if (location != null && location.getLane() == null) {

									return true;
								}
							}
							return false;

						}).collect(Collectors.toList());

						// lanes : [1, 2 ....]
					} else if (signallingCommandToSpath.getLanes() != null
							&& !CollectionUtils.isEmpty(signallingCommandToSpath.getLanes())) {

						elements = elements.stream().filter(element -> {

							if (element.getLocationId() != null) {

								Location location = locationRepository.get(element.getLocationId(), false);

								if (location != null && location.getLane() != null
										&& signallingCommandToSpath.getLanes().contains(location.getLane())) {

									return true;
								}
							}
							return false;

						}).collect(Collectors.toList());

					}
				}
			}
		}

		return elements;

	}

	public List<Element> getElements(SignallingCommandToStretch signallingCommandToStretch, Long stretchId,
			Long elementType) {

		List<Element> elements = null;

		if (signallingCommandToStretch != null && stretchId != null) {

			elements = EntitiesManager.getInstance().getElementByStretchs(elementType, stretchId,
					signallingCommandToStretch.getFrom(), signallingCommandToStretch.getTo());

			if (CollectionUtils.isNotEmpty(elements)) {

				if (signallingCommandToStretch.getElementSubtypeId() != null) {

					Key elementSubtypeKey = new Key(signallingCommandToStretch.getElementSubtypeId());

					if (elementSubtypeKey.getClazz() == ElementSubtype.class) {

						ElementSubtype elementSubtype = new ElementSubtype();

						elementSubtype.setPks(elementSubtypeKey.getIds());

						// lanes: null
						if (signallingCommandToStretch.getLanes() == null) {

							elements = elements.stream().filter(
									element -> elementSubtype.getElementTypeId().equals(element.getElementTypeId())
											&& elementSubtype.getId().equals(element.getElementSubtypeId()))
									.collect(Collectors.toList());

							// lanes: []
						} else if (CollectionUtils.isEmpty(signallingCommandToStretch.getLanes())) {

							elements = elements.stream().filter(element -> {

								if (elementSubtype.getElementTypeId().equals(element.getElementTypeId())
										&& elementSubtype.getId().equals(element.getElementSubtypeId())
										&& element.getLocationId() != null) {

									Location location = locationRepository.get(element.getLocationId(), false);

									if (location != null && location.getLane() == null) {

										return true;
									}
								}
								return false;

							}).collect(Collectors.toList());

							// lanes : [1, 2 ....]
						} else {

							elements = elements.stream().filter(element -> {

								if (elementSubtype.getElementTypeId().equals(element.getElementTypeId())
										&& elementSubtype.getId().equals(element.getElementSubtypeId())
										&& element.getLocationId() != null) {

									Location location = locationRepository.get(element.getLocationId(), false);

									if (location != null && location.getLane() != null
											&& signallingCommandToStretch.getLanes().contains(location.getLane())) {

										return true;
									}
								}
								return false;

							}).collect(Collectors.toList());
						}
					}

					// lanes: []
				} else if (signallingCommandToStretch.getLanes() != null
						&& CollectionUtils.isEmpty(signallingCommandToStretch.getLanes())) {

					elements = elements.stream().filter(element -> {

						if (element.getLocationId() != null) {

							Location location = locationRepository.get(element.getLocationId(), false);

							if (location != null && location.getLane() == null) {

								return true;
							}
						}
						return false;

					}).collect(Collectors.toList());

					// lanes : [1, 2 ....]
				} else if (signallingCommandToStretch.getLanes() != null
						&& !CollectionUtils.isEmpty(signallingCommandToStretch.getLanes())) {

					elements = elements.stream().filter(element -> {

						if (element.getLocationId() != null) {

							Location location = locationRepository.get(element.getLocationId(), false);

							if (location != null && location.getLane() != null
									&& signallingCommandToStretch.getLanes().contains(location.getLane())) {

								return true;
							}
						}
						return false;

					}).collect(Collectors.toList());

				}
			}
		}

		return elements;

	}

	public PmQueuePriority getIncidentPriority(Long elementTypeId) {

		PmQueuePriority pmQueuePriority = null;

		if (elementTypeId != null) {

			List<PmElementTypeQueue> pmElementTypeQueues = this.pmElementTypeQueueRepository
					.getsByElementType(elementTypeId, false);

			if (CollectionUtils.isNotEmpty(pmElementTypeQueues)) {

				List<PmQueuePriority> pmQueuePriorities = this.pmQueuePriorityRepository
						.getsIncidentPriorityByQueue(pmElementTypeQueues.get(0).getQueueId(), false);

				if (CollectionUtils.isNotEmpty(pmQueuePriorities)) {

					pmQueuePriority = pmQueuePriorities.get(0);
				}
			}
		}

		return pmQueuePriority;
	}
}
