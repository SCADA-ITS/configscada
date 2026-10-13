package com.revenga.rits.back.traffic.manager.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.LevelOfService;
import com.revenga.rits.back.data.core.model.LosArray;
import com.revenga.rits.back.data.core.model.LosSoftenedArray;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.repository.ElementHierarchiesRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.LevelOfServiceRepository;
import com.revenga.rits.back.entities.lib.repository.LosArrayRepository;
import com.revenga.rits.back.entities.lib.repository.LosSoftenedArrayRepository;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.traffic.manager.annotation.AElementId;
import com.revenga.rits.back.traffic.manager.annotation.AElementTypeParam;
import com.revenga.rits.back.traffic.manager.model.Detector;
import com.revenga.rits.back.traffic.manager.model.Section;
import com.revenga.rits.back.traffic.manager.model.TravelTime;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntitiesManager {

	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "entities.manager.retention_time_conf"));

	private static final Long CACHE_RETENTION_TIME_STATIC = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.retention_time_static"));

	private static final Long PARAM_TRAFFIC_PERIOD_TIME_MINUTES = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.system_params.traffic_period_time_minutes"));

	private static final Long PARAM_INIT_TRAFFIC_PERIOD_TIME_SECONDS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.system_params.init_traffic_period_seconds"));
	
	private static final Long TRAVEL_TIMES_PERIOD_TIME_MINUTES = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.system_params.travel_time_period_time_minutes"));

	private static final String CRON_EXPRESION = ConfigProperty.getInstance().getProperty("application.properties",
			"entities.manager.system_params.cron_expression");

	private ElementRepository elementRepository;
	private ElementValueRepository elementValueConfigRepository;
	private ElementValueRepository elementValueMeasureRepository;
	private ElementHierarchiesRepository elementHierarchiesRepository;
	private LosArrayRepository losArrayRepository;
	private LosSoftenedArrayRepository losSoftenedArrayRepository;
	private LevelOfServiceRepository levelOfServiceRepository;

	private Map<String, Pair<Field, Integer>> mapGetFieldByElementTypeParam;
	private Map<Pair<Field, Integer>, String> mapGetElementTypeParamByField;

	private Map<Class<?>, Long> mapElementType;
	private Map<Class<?>, Field> mapFieldElementId;

	@Getter
	private Integer period = null;
	
	@Getter 
	private Integer travelTimePeriods = null;

	@Getter
	Scheduler scheduler;

	private static EntitiesManager instance;

	// Private methods
	private EntitiesManager() {

		this.elementRepository = new ElementRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementHierarchiesRepository = new ElementHierarchiesRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementValueConfigRepository = new ElementValueRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementValueMeasureRepository = new ElementValueRepository(false);
		this.levelOfServiceRepository = new LevelOfServiceRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.losArrayRepository = new LosArrayRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.losSoftenedArrayRepository = new LosSoftenedArrayRepository(true, CACHE_RETENTION_TIME_STATIC);

		initQuartzScheduler();

		initMaps();

		SystemParamValue systemParamValuePeriod = (new SystemParamValueRepository(false))
				.get(PARAM_TRAFFIC_PERIOD_TIME_MINUTES, false);

		if (systemParamValuePeriod != null && systemParamValuePeriod.getValue() != null) {

			period = Math.min(Integer.parseInt(systemParamValuePeriod.getValue()), 30);
		}
		
		SystemParamValue systemParamValueTravelTimesPeriod = (new SystemParamValueRepository(false))
				.get(TRAVEL_TIMES_PERIOD_TIME_MINUTES, false);

		if (systemParamValueTravelTimesPeriod != null && systemParamValueTravelTimesPeriod.getValue() != null) {

			travelTimePeriods = Math.min(Integer.parseInt(systemParamValueTravelTimesPeriod.getValue()), 5);
		}
	}

	private void initQuartzScheduler() {

		try {

			SchedulerFactory sf = new StdSchedulerFactory();
			scheduler = sf.getScheduler();

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void initMaps() {

		mapElementType = new HashedMap<>();
		mapElementType.put(Detector.class, Detector.ELEMENT_TYPE);
		mapElementType.put(Section.class, Section.ELEMENT_TYPE);
		mapElementType.put(TravelTime.class, TravelTime.ELEMENT_TYPE);

		mapGetFieldByElementTypeParam = new HashedMap<>();
		mapGetElementTypeParamByField = new HashedMap<>();
		mapFieldElementId = new HashedMap<>();

		for (Map.Entry<Class<?>, Long> entry : mapElementType.entrySet()) {

			for (Field field : entry.getKey().getDeclaredFields()) {

				ElementTypeParam elementTypeParam = null;
				String[] properties = null;

				if (field.isAnnotationPresent(AElementTypeParam.class)) {

					AElementTypeParam aElementTypeParam = field.getAnnotation(AElementTypeParam.class);
					properties = aElementTypeParam.references();

					elementTypeParam = new ElementTypeParam();

					if (aElementTypeParam.paramType().equals(AElementTypeParam.paramType.MEASURE)) {

						elementTypeParam.setParamTypeId(ParamType.MEASURE);
					} else {
						elementTypeParam.setParamTypeId(ParamType.CONFIG);
					}

				} else if (field.isAnnotationPresent(AElementId.class)) {

					mapFieldElementId.put(entry.getKey(), field);
				}

				if (elementTypeParam != null && properties != null) {

					Integer index = 0;
					for (String property : properties) {

						String strValue = ConfigProperty.getInstance().getProperty("application.properties", property);

						if (!StringUtils.isEmpty(strValue)) {

							Long id = Long.parseLong(strValue);

							if (id != null) {

								Pair<Field, Integer> pair = null;

								if (properties.length == 1) {

									pair = new MutablePair<>(field, null);

								} else {

									pair = new MutablePair<>(field, index);
								}
								elementTypeParam.setElementTypeId(entry.getValue());
								elementTypeParam.setId(id);

								mapGetFieldByElementTypeParam.put(new Key(elementTypeParam).getSimpleValue(), pair);
								mapGetElementTypeParamByField.put(pair, new Key(elementTypeParam).getSimpleValue());
								index++;
							}
						} else {
							log.info("Property: " + property + "not found");
						}
					}

				}
			}
		}
	}

	private Pair<Field, Integer> getFieldByElementTypeParam(ElementTypeParam elementTypeParam) {

		Pair<Field, Integer> field = null;

		if (elementTypeParam != null) {

			Key key = new Key(elementTypeParam);

			field = mapGetFieldByElementTypeParam.get(key.getSimpleValue());

			if (field != null) {

				field.getLeft().setAccessible(true);
			} else {
				log.debug(key.getSimpleValue() + " not found");
			}

		}
		return field;
	}

	private ElementTypeParam getElementTypeParamByField(Pair<Field, Integer> field) {

		ElementTypeParam elementTypeParam = null;

		if (field != null) {

			String simpleValue = mapGetElementTypeParamByField.get(field);

			if (simpleValue != null) {

				Key key = new Key(simpleValue);

				if (key.getClazz() == ElementTypeParam.class) {

					elementTypeParam = new ElementTypeParam();
					elementTypeParam.setPks(key.getIds());
				}
			}
		}
		return elementTypeParam;
	}

	private void setValue(Object objSrc, Pair<Field, Integer> field, String value) {

		Object objectValue = null;

		if (ObjectUtils.allNotNull(objSrc, field, value)) {

			try {
				field.getLeft().setAccessible(true);

				if (field.getLeft().getGenericType().toString().contains(List.class.getName())
						&& field.getRight() != null) {

					ParameterizedType pt = (ParameterizedType) field.getLeft().getGenericType();

					if (pt.getActualTypeArguments().length == 1) {

						Object obj = null;
						Type t = pt.getActualTypeArguments()[0];
						Object listObj = field.getLeft().get(objSrc);

						obj = ClassesUtil.getValueFromString(t, value);

						if (obj != null && listObj != null
								&& ((List<?>) listObj).size() > field.getRight().intValue()) {

							((List<Object>) listObj).set(field.getRight().intValue(), obj);
							objectValue = listObj;
						}

					}
				} else {

					objectValue = ClassesUtil.getValueFromString(field.getLeft(), value);
				}

				if (objectValue != null) {

					field.getLeft().set(objSrc, objectValue);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	private String getValue(Object objSrc, Pair<Field, Integer> field) {

		String strValue = null;

		if (ObjectUtils.allNotNull(objSrc, field)) {

			field.getLeft().setAccessible(true);

			try {
				Object obj = field.getLeft().get(objSrc);

				if (field.getLeft().getGenericType().toString().contains(List.class.getName())
						&& field.getRight() != null) {

					if (((List<?>) obj).size() > field.getRight().intValue())
						obj = ((List<?>) obj).get(field.getRight());
				}

				if (obj != null) {

					strValue = obj.toString();
				}

			} catch (IllegalArgumentException | IllegalAccessException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}

		}
		return strValue;

	}

	// Public methods
	public static EntitiesManager getInstance() {

		if (instance == null) {

			instance = new EntitiesManager();
		}

		return instance;
	}

	public ElementValue getElementValue(Object objSrc, Long elementTypeId, Long paramTypeId, Long id) {

		ElementValue elementValue = null;

		if (ObjectUtils.allNotNull(objSrc, elementTypeId, paramTypeId, id)) {

			ElementTypeParam elementTypeParam = new ElementTypeParam();
			elementTypeParam.setId(id);
			elementTypeParam.setParamTypeId(paramTypeId);
			elementTypeParam.setElementTypeId(elementTypeId);

			Pair<Field, Integer> field = getFieldByElementTypeParam(elementTypeParam);

			if (field != null) {

				try {
					Long elementId = getId(objSrc);

					if (elementId != null) {

						elementValue = new ElementValue();
						elementValue.setElementId(elementId);
						elementValue.setElementTypeId(elementTypeId);
						elementValue.setParamTypeId(paramTypeId);
						elementValue.setElementTypeParamId(id);
						elementValue.setValue(getValue(objSrc, field));
					}

				} catch (IllegalArgumentException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
		return elementValue;
	}

	public ElementValue getElementValue(Object objSrc, Pair<Field, Integer> field) {

		ElementValue elementValue = null;

		if (ObjectUtils.allNotNull(objSrc, field)) {

			ElementTypeParam elementTypeParam = getElementTypeParamByField(field);

			if (elementTypeParam != null) {

				try {

					String value = getValue(objSrc, field);
					Long elementId = getId(objSrc);

					if (ObjectUtils.allNotNull(elementId, value)) {

						elementValue = new ElementValue();
						elementValue.setElementId(elementId);
						elementValue.setElementTypeId(elementTypeParam.getElementTypeId());
						elementValue.setParamTypeId(elementTypeParam.getParamTypeId());
						elementValue.setElementTypeParamId(elementTypeParam.getId());
						elementValue.setValue(value);
					}

				} catch (IllegalArgumentException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
		return elementValue;
	}

	public Long getId(final Object objSrc) {

		Long resp = null;

		if (objSrc != null) {

			Field field = mapFieldElementId.get(objSrc.getClass());

			if (field != null) {

				field.setAccessible(true);

				try {
					resp = (Long) field.get(objSrc);

				} catch (IllegalArgumentException | IllegalAccessException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
		return resp;
	}

	public void setElementValue(Object objDst, ElementValue elementValue) {

		if (ObjectUtils.allNotNull(objDst, elementValue)) {

			ElementTypeParam elementTypeParam = new ElementTypeParam();
			elementTypeParam.setId(elementValue.getElementTypeParamId());
			elementTypeParam.setParamTypeId(elementValue.getParamTypeId());
			elementTypeParam.setElementTypeId(elementValue.getElementTypeId());

			Pair<Field, Integer> field = getFieldByElementTypeParam(elementTypeParam);

			if (field != null) {

				try {
					setValue(objDst, field, elementValue.getValue());

				} catch (IllegalArgumentException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
	}

	public void setElementValue(Object objDst, Pair<Field, Integer> field, String value) {

		if (ObjectUtils.allNotNull(objDst, field, value)) {

			if (field != null) {

				try {
					setValue(objDst, field, value);

				} catch (IllegalArgumentException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
	}

	public void setId(Object objDst, Long id) {

		if (ObjectUtils.allNotNull(objDst, id)) {

			Field field = mapFieldElementId.get(objDst.getClass());

			if (field != null) {

				field.setAccessible(true);

				try {
					field.set(objDst, id);

				} catch (IllegalArgumentException | IllegalAccessException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

	}

	public List<Element> getElements(Long elementTypeId) {

		return elementRepository.gets(elementTypeId, false);
	}

	public List<ElementValue> getElementValueConfig(Long elementTypeId) {

		return elementValueConfigRepository.getsByElementType(elementTypeId, ParamType.CONFIG, false);
	}

	public List<ElementValue> getElementValueMeasure(Long elementTypeId) {

		return elementValueMeasureRepository.getsByElementType(elementTypeId, ParamType.MEASURE, false);
	}

	public Command sendCommand(Command command) {

		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}

	public List<ElementHierarchy> getHierarchiesByElementType(Long parentElementTypeId, Long childElementTypeId) {

		return elementHierarchiesRepository.getsByElementType(parentElementTypeId, childElementTypeId, false);
	}

	public String getCronExpression() {

		String cronExpression = null;

		SystemParamValue systemParamValueInit = (new SystemParamValueRepository(false))
				.get(PARAM_INIT_TRAFFIC_PERIOD_TIME_SECONDS, false);

		if (systemParamValueInit != null && systemParamValueInit.getValue() != null && this.period != null) {

			Integer init = Math.min(Integer.parseInt(systemParamValueInit.getValue()), 59);

			cronExpression = new String(CRON_EXPRESION);
			cronExpression = String.format(cronExpression, init, period);
		}

		return cronExpression;
	}
	
	public List<LevelOfService> getLevelOfServices() {

		return levelOfServiceRepository.getAll(false);
	}
	
	public LevelOfService getLevelOfServices(Long levelOfServiceId) {

		return levelOfServiceRepository.get(levelOfServiceId, false);
	}
	
	public List<LosArray> getLosArrays() {

		return losArrayRepository.getAll(false);
	}
	
	public List<LosSoftenedArray> getLosSoftenedArrays() {

		return losSoftenedArrayRepository.getAll(false);
	}
}
