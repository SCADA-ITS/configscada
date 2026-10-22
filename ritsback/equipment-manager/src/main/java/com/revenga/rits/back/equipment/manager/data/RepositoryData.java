package com.revenga.rits.back.equipment.manager.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.annotation.EntityRepositoryParam;
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
import com.revenga.rits.back.entities.lib.repository.GenericDataRepository;
import com.revenga.rits.back.entities.lib.repository.ManagementAreaElementRepository;
import com.revenga.rits.back.entities.lib.repository.ManagementAreaRepository;
import com.revenga.rits.back.entities.lib.repository.PmCommandElementRepository;
import com.revenga.rits.back.entities.lib.repository.PmCommandElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.PmDisabledQueuePriorityRepository;
import com.revenga.rits.back.entities.lib.repository.PmElementTypeQueueRepository;
import com.revenga.rits.back.entities.lib.repository.PmQueuePriorityRepository;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.entities.lib.repository.ThresholdOperationRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.equipment.manager.data.maintenance.AlarmConfigMeasureOnChange;
import com.revenga.rits.back.equipment.manager.data.maintenance.AlarmConfigOnChange;
import com.revenga.rits.back.equipment.manager.data.maintenance.ElementOnChange;

import lombok.Getter;
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
	
	@EntityRepositoryParam(subscribeChanges = true, entityOnChange = ElementOnChange.class)
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
	@EntityRepositoryParam(subscribeChanges = true)
	private ElementValueRepository elementValueRepository;
	private ElementTypeStateTransitionRepository elementTypeStateTransitionRepository;
	private ElementSpreadStateRepository elementSpreadStateRepository;
	@EntityRepositoryParam(subscribeChanges = true)
	private ElementStateAlarmRepository elementStateAlarmRepository;
	
	@EntityRepositoryParam(subscribeChanges = true, entityOnChange = AlarmConfigOnChange.class)
	private AlarmConfigRepository alarmConfigRepository;
	@EntityRepositoryParam(subscribeChanges = true, entityOnChange = AlarmConfigMeasureOnChange.class)
	private AlarmConfigMeasureRepository alarmConfigMeasureRepository;
	private AlarmTargetElementRepository alarmTargetElementRepository;
	@EntityRepositoryParam(subscribeChanges = true)
	private AlarmRepository alarmRepository;
	private AlarmTypeRepository alarmTypeRepository;
	private AlarmLevelRepository alarmLevelRepository;
	@EntityRepositoryParam(subscribeChanges = true)
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
	
	private ManagementAreaElementRepository  managementAreaElementRepository;
	private ManagementAreaRepository  managementAreaRepository;
	
	@Getter
	private Map<Class<?>, EntityRepository> repositories = new HashMap<>();
	private List<EntityRepository> repositoryList = new ArrayList<>();
	
	public RepositoryData() {
		super();
		
		//EntitiesManagerHelper.initialize(this, repositoryList, repositories);
	}
	
	public void load() {
		
		//EntitiesManagerHelper.cacheRepository(this);
		
		EntitiesManagerHelper.initialize(this, repositoryList, repositories);
	}
	
	public void startSubscriptions() {
		
		EntitiesManagerHelper.startSubscriptions(repositoryList);
	}
	
	public EntityRepository getRepository(Class<?> clazz) {
		
		return this.repositories.get(clazz);
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