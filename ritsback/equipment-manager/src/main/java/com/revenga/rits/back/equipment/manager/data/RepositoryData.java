package com.revenga.rits.back.equipment.manager.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigMeasureRepository;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigRepository;
import com.revenga.rits.back.entities.lib.repository.AlarmFilteredRepository;
import com.revenga.rits.back.entities.lib.repository.AlarmLevelRepository;
import com.revenga.rits.back.entities.lib.repository.AlarmRepository;
import com.revenga.rits.back.entities.lib.repository.AlarmTargetElementRepository;
import com.revenga.rits.back.entities.lib.repository.AlarmTypeRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementPreconditionRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementValuePreconditionRepository;
import com.revenga.rits.back.entities.lib.repository.ElementGroupRepository;
import com.revenga.rits.back.entities.lib.repository.ElementHierarchiesRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementSpreadStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementStateAlarmRepository;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementSubtypeActiveParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementSubtypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamUnitRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeStateTransitionRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;
import com.revenga.rits.back.equipment.manager.data.maintenance.AlarmConfigMeasureOnChange;
import com.revenga.rits.back.equipment.manager.data.maintenance.AlarmConfigOnChange;
import com.revenga.rits.back.entities.lib.repository.GenericDataRepository;
import com.revenga.rits.back.entities.lib.repository.PmCommandElementRepository;
import com.revenga.rits.back.entities.lib.repository.PmCommandElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.PmDisabledQueuePriorityRepository;
import com.revenga.rits.back.entities.lib.repository.PmElementTypeQueueRepository;
import com.revenga.rits.back.entities.lib.repository.PmQueuePriorityRepository;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.entities.lib.repository.ThresholdOperationRepository;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings("unused")
public class RepositoryData {
	
	private static final String FILE_PROPERTIES = "application.properties";
	
	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_conf"));
	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_master"));
	private static final Long CACHE_RETENTION_TIME_STATIC = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_static"));
	
	private ElementRepository elementRepository;
	private ElementTypeRepository elementTypeRepository;
	private ElementGroupRepository elementGroupRepository;
	private ElementHierarchiesRepository elementHierarchiesRepository;
	private ElementStateRepository elementStateRepository;
	private ElementTypeStateRepository elementTypeStateRepository;
	private ElementTypeParamRepository elementTypeParamRepository;
	private ElementTypeParamUnitRepository elementTypeParamUnitRepository;
	private ElementSubtypeRepository elementSubtypeRepository;
	private ElementSubtypeActiveParamRepository elementSubtypeActiveParamRepository;
	private ElementValueRepository elementValueRepository;
	private ElementTypeStateTransitionRepository elementTypeStateTransitionRepository;
	private ElementSpreadStateRepository elementSpreadStateRepository;
	private ElementStateAlarmRepository elementStateAlarmRepository;
	
	private AlarmConfigRepository alarmConfigRepository;
	private AlarmConfigMeasureRepository alarmConfigMeasureRepository;
	private AlarmTargetElementRepository alarmTargetElementRepository;
	private AlarmRepository alarmRepository;
	private AlarmTypeRepository alarmTypeRepository;
	private AlarmLevelRepository alarmLevelRepository;
	private AlarmFilteredRepository alarmFilteredRepository;
	private GenericDataRepository genericDataRepository;
	private ThresholdOperationRepository thresholdOperationRepository;
	
	private CommandElementPreconditionRepository commandElementPreconditionRepository;
	private CommandElementValuePreconditionRepository commandElementValuePreconditionRepository;
	private CommandElementTypeRepository commandElementTypeRepository;
	private CommandElementTypeParamRepository commandElementTypeParamRepository;

	private PmQueuePriorityRepository pmQueuePriorityRepository;
	private PmElementTypeQueueRepository pmElementTypeQueueRepository;
	private PmCommandElementRepository pmCommandElementRepository;
	private PmCommandElementValueRepository pmCommandElementValueRepository;
	private PmDisabledQueuePriorityRepository pmDisabledQueuePriorityRepository;
	
	private SystemParamValueRepository systemParamValueRepository;
	
	private Map<Class<?>, EntityRepository> repositories;
	
	public RepositoryData() {
		super();
		
		customInstanceRepositories();
		initialize();
	}
	
	public void startSubscriptions() {
		
		if (this.alarmConfigRepository != null) {
			
			this.alarmConfigRepository.startSubscription();
		}
		
		if (this.alarmConfigMeasureRepository != null) {
			
			this.alarmConfigMeasureRepository.startSubscription();
		}
		
		if (this.elementStateAlarmRepository!= null) {
			
			this.elementStateAlarmRepository.startSubscription();
		}
	}
	
	private void customInstanceRepositories() {
		
		this.alarmConfigRepository = new AlarmConfigRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_MASTER, null, false, true, new AlarmConfigOnChange()));
		this.alarmConfigMeasureRepository = new AlarmConfigMeasureRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_MASTER, null, false, true, new AlarmConfigMeasureOnChange()));
		this.elementStateAlarmRepository = new ElementStateAlarmRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_MASTER, null, false, true, null));
	}

	/**
	 * if is not instanced, initialize all declared members of type EntityRepository to cacheable with CACHE_RETENTION_TIME_MASTER by default 
	 * 
	 */
	
	private void initialize() {
		
		List<Field> fields = ClassesUtil.getFields(this.getClass());

		for (Field field : fields) {
			
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {

				continue;
			}
			
			try {
				
				if (field.getType().getSuperclass() == EntityRepository.class) {
					
					field.setAccessible(true);
					
					if (field.get(this) == null) {
					
						field.set(this, (field.getType().getDeclaredConstructor(Boolean.class, Long.class).newInstance(true,
								CACHE_RETENTION_TIME_MASTER)));
					}
				}
				
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}
	
	public void load() {
		
		EntitiesManagerHelper.cacheRepository(this);

		pmCommandElementRepository.setRepositories(List.of(this.pmCommandElementValueRepository));

		commandElementPreconditionRepository.setRepositories(List.of(this.elementValueRepository,
				this.elementTypeParamRepository, this.elementStateRepository, this.alarmRepository,
				this.commandElementValuePreconditionRepository, this.elementRepository, this.alarmConfigRepository,
				this.elementTypeStateRepository, this.thresholdOperationRepository));
	}

	public Map<Class<?>, EntityRepository> getRepositories() {
		
		if (this.repositories != null) {
			
			return this.repositories;
			
		}
		
		this.repositories = new HashMap<>();
		
		List<Field> fields = ClassesUtil.getFields(this.getClass());

		Comparator<Field> compareByClassName = (Field o1, Field o2) -> (o1.getName()).compareTo(o2.getName());

		Collections.sort(fields, compareByClassName);

		for (Field field : fields) {

			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {

				continue;
			}
			
			try {

				field.setAccessible(true);
				
				Object obj = field.get(this);
				
				if (obj instanceof EntityRepository) {
					
					EntityRepository entityRepository = (EntityRepository) obj; 
					
					this.repositories.put(entityRepository.getClazz(), entityRepository);
				}
			}
			catch (IllegalArgumentException | IllegalAccessException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return repositories;
	}
	
	public EntityRepository getRepository(Class<?> clazz) {
		
		EntityRepository entityRepository = null;
		
		if (this.repositories == null) {
			
			getRepositories();
		}
		
		if (!MapUtils.isEmpty(this.repositories)) {
			
			entityRepository = this.repositories.get(clazz);
		}
		
		return entityRepository;
	}
	
	public Map<Class<?>, List<BasicElement>> getMapCollections() {
		
		Map<Class<?>, List<BasicElement>> mapCollections = new HashMap<>();
		
		Map<Class<?>, EntityRepository> mapRepositories = this.getRepositories();
		
		for (Map.Entry<Class<?>, EntityRepository> entry : mapRepositories.entrySet()) {
			
			EntityRepository repositoryObj = entry.getValue();
			Class<?> clazz = repositoryObj.getClazz();		

			mapCollections.put(clazz, repositoryObj.getsFromCache(false));
		}
		
		return mapCollections;
	}
	
	public List<BasicElement> getCollection(Class<?> clazz) {
		
		List<BasicElement> bes = null;
		Map<Class<?>, EntityRepository> mapRepositories = this.getRepositories();
		
		if (!MapUtils.isEmpty(mapRepositories)) {
			
			EntityRepository entityRepository = mapRepositories.get(clazz);
			
			if (entityRepository != null) {
				
				bes = entityRepository.getsFromCache(false);
			}
		}
		
		return bes;
	}
}