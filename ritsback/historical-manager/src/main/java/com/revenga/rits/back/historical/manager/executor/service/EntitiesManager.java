package com.revenga.rits.back.historical.manager.executor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.ProcessInfo;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetGenericData;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.repository.AuditLogRepository;
import com.revenga.rits.back.entities.lib.repository.AuditLogValueRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.GenericDataRepository;
import com.revenga.rits.back.entities.lib.repository.HistoricalRepository;
import com.revenga.rits.back.entities.lib.repository.LogSubtypeRepository;
import com.revenga.rits.back.entities.lib.repository.LogTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.LogTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ManagementAreaRepository;
import com.revenga.rits.back.entities.lib.repository.ProcessInfoRepository;
import com.revenga.rits.back.entities.lib.repository.UserRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntitiesManager {

	public static final Long DEFAULT_CACHE_RETENTION_TIME = 600000L;
	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.retention_time_master"));
	
	private static final Long PROCESS_INFO_ELEMENT_TYPE = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "process.info.element.type"));

	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.retention_time_conf"));

	private static EntitiesManager instance;

	private Map<String, HistoricalRepository> map;

	private ElementTypeParamRepository elementTypeParamRepository;
	private LogTypeRepository logTypeRepository;
	private LogSubtypeRepository logSubtypeRepository;
	private LogTypeParamRepository logTypeParamRepository;
	private AuditLogRepository auditLogRepository;
	private AuditLogValueRepository auditLogValueRepository;
	private ElementValueRepository elementValueConfigRepository;
	private ElementValueRepository elementValueMeasureRepository;
	private GenericDataRepository genericDataRepository;
	private ProcessInfoRepository processInfoRepository;
	private ElementRepository elementRepository;
	private ManagementAreaRepository managementAreaRepository;
	
	@Getter
	Scheduler scheduler;
	
	private UserRepository userRepository;

	private Long noRecognizeAuditLogs;
	
	private EntitiesManager() {

		initQuartzScheduler();
		List<EntityRepository> repositories = new ArrayList<>();
		map = new HashedMap<>();

		this.elementTypeParamRepository = new ElementTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.logTypeRepository = new LogTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.logTypeParamRepository = new LogTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.logSubtypeRepository = new LogSubtypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.userRepository = new UserRepository(true, CACHE_RETENTION_TIME_CONF);
		this.processInfoRepository = new ProcessInfoRepository(true);
		this.elementRepository = new ElementRepository(false);
		
		this.elementValueConfigRepository = new ElementValueRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementValueMeasureRepository = new ElementValueRepository(false);
		
		this.auditLogValueRepository = new AuditLogValueRepository(true, CACHE_RETENTION_TIME_CONF);
		repositories.add(this.logTypeParamRepository);
		repositories.add(this.auditLogValueRepository);

		this.auditLogRepository = new AuditLogRepository(true, CACHE_RETENTION_TIME_CONF, repositories);
		
		this.genericDataRepository =  new GenericDataRepository(false);
		
		this.managementAreaRepository = new ManagementAreaRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_MASTER, null, false, false, null));
		
		refreshGenericData(null);
	}

	public static EntitiesManager getInstance() {

		if (instance == null) {

			instance = new EntitiesManager();
		}

		return instance;
	}

	private void addEntity(String entity) {

		Class<?> clazz;

		if (!map.containsKey(entity)) {

			try {

				clazz = ClassesUtil.getModelClassBySimpleClassName(entity);

				HistoricalRepository repository = new HistoricalRepository(clazz, true, DEFAULT_CACHE_RETENTION_TIME);

				map.put(entity, repository);

			} catch (ClassNotFoundException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	private void initQuartzScheduler() {

		try {

			if (scheduler == null) {
				
				SchedulerFactory sf = new StdSchedulerFactory();
				scheduler = sf.getScheduler();
				
				if (!scheduler.isStarted()) {
					
					scheduler.start();
				}
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	public BasicElement getFromCache(String entity, BasicElement basicElement) {

		Key key = new Key(basicElement);
		
		
		if (key.hasFilterPatterns()) {
			
			return null;
		}
		
		String[] sIds = key.getArrayOfIds();

		if (!map.containsKey(entity)) {

			addEntity(entity);
		}

		return map.get(entity).getFromCache(sIds, false);
	}

	public List<ProcessInfo> getAllProcessInfo(){
		
		return processInfoRepository.getAll(false);
	}
	
	public List<Element> getProcessInfoElements() {

		return elementRepository.gets(PROCESS_INFO_ELEMENT_TYPE, false);
	}
	
	public LogType getLogType(Long logTypeId) {

		return logTypeId != null ? logTypeRepository.get(logTypeId, false) : null;
	}

	public LogSubtype getLogSubtype(Long logSubtypeId) {

		return logSubtypeId != null ? logSubtypeRepository.get(logSubtypeId, false) : null;
	}

	public User getUser(Long userId) {

		return userId != null ? userRepository.get(userId, false) : null;
	}

	public List<ElementTypeParam> getElementTypeParams(Long elementTypeId) {

		return elementTypeId != null ? elementTypeParamRepository.gets(elementTypeId, false) : null;
	}

	public List<LogTypeParam> getLogTypeParams(Long logTypeId) {

		return logTypeId != null ? logTypeParamRepository.getsByLogType(logTypeId, false) : null;
	}

	public Command sendCommand(final Command command) {

		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}
	
	public AuditLog createAuditLog(final AuditLog auditLog) {

		return  auditLogRepository.create(auditLog);
	}
	
	public AuditLog deleteAuditLog(final AuditLog auditLog) {

		return  auditLogRepository.delete(auditLog);
	}
	
	public List<AuditLog> deleteAuditLogs(final List<AuditLog> auditLogs) {

		return  auditLogRepository.delete(auditLogs);
	}
	
	public List<AuditLog> getAllAuditLog() {

		return auditLogRepository.getAll(false);
	}
	
	
	public List<ElementValue> getElementValueConfig(Long elementTypeId) {

		return elementValueConfigRepository.getsByElementType(elementTypeId, ParamType.CONFIG, false);
	}

	public List<ElementValue> getElementValueMeasure(Long elementTypeId) {

		return elementValueMeasureRepository.getsByElementType(elementTypeId, ParamType.MEASURE, false);
	}
	
	public boolean refreshGenericData(Long inc){
		
		if(inc != null){
			
			noRecognizeAuditLogs += inc;
			
		} else {
			
			List<AuditLog> auditLogs = auditLogRepository.getAll(false);
			
			noRecognizeAuditLogs = CollectionUtils.isNotEmpty(auditLogs) ? auditLogs.size() : 0L;
		}
		
		return putGenericData(noRecognizeAuditLogs.toString());
	}
	
	private boolean putGenericData(String value) {
		
		boolean res = false;
		
		if(value != null){
			
			GenericData genericData = new GenericData();
			genericData.setId(GenericData.GENERIC_DATA_NO_RECOGNIZE_AUDIT_LOGS);
			genericData.setValue(value);
	
			List<ManagementArea> managementAreas = getManagementAreas();

			if (CollectionUtils.isNotEmpty(managementAreas)) {

				for (ManagementArea managementArea : managementAreas) {

					genericData.addManagementAreaValue(managementArea.getId(), value);
				}
			}
			
			res = ((genericDataRepository.put(genericData)) != null);
			
			SetGenericData setGenericData = new SetGenericData();
			setGenericData.setGenericDatas(Arrays.asList(genericData));
			
			res &= ((sendCommand(setGenericData)) != null);
		}
		
		return res;
	}
	
	public List<ManagementArea> getManagementAreas() {

		return this.managementAreaRepository.getAll(false);
	}
}
