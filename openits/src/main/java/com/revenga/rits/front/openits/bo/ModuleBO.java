package com.revenga.rits.front.openits.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.Module;
import com.revenga.rits.front.openitsdao.model.ModuleParam;
import com.revenga.rits.front.openitsdao.model.ModuleValue;
import com.revenga.rits.front.openitsdao.service.ModuleService;
import com.revenga.rits.front.openitsdao.service.ModuleValueService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ModuleBO extends GenericBO {
	
	private static final String FIELD_CONFIG = "config";
	private static final String FIELD_HELP_AVAILABLE = "helpAvailable";
	private static final String MODULE_FILE_TEMPLATE = "module_%d.json";
	
	@Autowired
	ModuleService moduleService;
	
	@Autowired
	ModuleValueService moduleValueService;
	
	private static final String STATIC_FOLDER = "static";
	private static final String PROJECT_FOLDER = "project";
	private static final String MODULES_CONF_FOLDER = "modules_conf";
	private static final String ACTIVE_PROFILE = "spring.profiles.active";
	
	@Autowired
	private Environment env;
	
	@Autowired
	private HelpBO helpBO;
	
	private List<Module> modulesWithHelpAvailable;
	
	@PostConstruct
	private void postConstruct() {
		
		Instant timeRef = Instant.now();
		
		List<Module> modules = moduleService.getAll();
		
		log.info(this.getClass().getSimpleName() + ": Pre-load Modules. Items = " + modules.size() + " ("
				+ Duration.between(timeRef, Instant.now()).toMillis() + " ms.)");
		
		timeRef = Instant.now();
		
		this.modulesWithHelpAvailable = helpBO.getModulesWithHelpAvailable(modules);
		
		log.info(this.getClass().getSimpleName() + ": Check for help available in modules. Items = " + modules.size() + " ("
				+ Duration.between(timeRef, Instant.now()).toMillis() + " ms.)");
		
	}
		
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<Module> modules = moduleService.getAll();
		
		if (!CollectionUtils.isEmpty(modules)) {
			
			for (Module module : modules) {
				
				Map<String, Object> moduleMap = ConversionUtil.objectToMap(module);
				
				moduleMap.put(FIELD_CONFIG, getModuleConfig(module.getId()));
				
				if (!CollectionUtils.isEmpty(modulesWithHelpAvailable) && modulesWithHelpAvailable.stream()
						.filter(e -> e.getId().equals(module.getId())).findFirst().orElse(null) != null) {
				
					moduleMap.put(FIELD_HELP_AVAILABLE, true);
				}
				
				list.add(moduleMap);
			}
		}
		
		return list;
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		return null;
	}
	
	public Map<String, Object> getItem(Integer id) {
		
		Module module = moduleService.getById(id);
		
		Map<String, Object> moduleMap = ConversionUtil.objectToMap(module);
		
		if (!CollectionUtils.isEmpty(modulesWithHelpAvailable) && modulesWithHelpAvailable.stream()
				.filter(e -> e.getId().equals(module.getId())).findFirst().orElse(null) != null) {
		
			moduleMap.put(FIELD_HELP_AVAILABLE, true);
		}
		
		moduleMap.put(FIELD_CONFIG, getModuleConfig(module.getId()));
		
		return moduleMap;
	}
	
	public List<Map<String, Object>> getByModuleTypeId(Integer moduleTypeId) {
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<Module> modules = moduleService.getByModuleTypeId(moduleTypeId);
		
		if (!CollectionUtils.isEmpty(modules)) {
			
			for (Module module : modules) {
				
				Map<String, Object> moduleMap = ConversionUtil.objectToMap(module);
				Map<String, Object> moduleConfig = getModuleConfig(module.getId());
				
				if (!CollectionUtils.isEmpty(modulesWithHelpAvailable) && modulesWithHelpAvailable.stream()
						.filter(e -> e.getId().equals(module.getId())).findFirst().orElse(null) != null) {
				
					moduleMap.put(FIELD_HELP_AVAILABLE, true);
				}
				
				if (!MapUtils.isEmpty(moduleConfig)) {
				
					moduleMap.put(FIELD_CONFIG, getModuleConfig(module.getId()));
				}
				
				list.add(moduleMap);
			}
		}
		
		return list;
	}
	
	public ModuleValue save(Integer moduleId, ModuleValue moduleValue) {
		
		ModuleValue newModuleValue = null;
		
		Module module = moduleService.getById(moduleId);
		ModuleParam moduleParam = module.getModuleType().getModuleParams().stream()
				.filter(e -> e.getId().equals(moduleValue.getModuleParam().getId())).findFirst().orElse(null);
				
		if (moduleParam != null) {
			
			ModuleValue currentModuleValue = null;
			
			if (!CollectionUtils.isEmpty(module.getModuleValues())) {
			
				currentModuleValue = module.getModuleValues().stream()
						.filter(e -> e.getModuleParam().getId().equals(moduleParam.getId())).findFirst().orElse(null); 
			}
				
			if (currentModuleValue == null) {
				
				currentModuleValue = new ModuleValue();
				
				module.addModuleValue(currentModuleValue);
				
				currentModuleValue.setModuleParam(moduleParam);
			}
			
			currentModuleValue.setValue(moduleValue.getValue());
			
			newModuleValue = moduleValueService.save(currentModuleValue);
		}
				
		return newModuleValue;
	}
	
	private Map<String, Object> getModuleConfig(Integer moduleId) {
		
		Map<String, Object> map = null;
		InputStream is;
		
		is = this.getClass().getClassLoader().getResourceAsStream(PROJECT_FOLDER + File.separator + env.getProperty(ACTIVE_PROFILE) + File.separator + MODULES_CONF_FOLDER + File.separator + String.format(MODULE_FILE_TEMPLATE, moduleId));
		
		if (is == null) {
		
			is = this.getClass().getClassLoader().getResourceAsStream(STATIC_FOLDER + File.separator + MODULES_CONF_FOLDER + File.separator + String.format(MODULE_FILE_TEMPLATE, moduleId));
		}
		
		if (is != null) {
		
			try (BufferedReader buf = new BufferedReader(new InputStreamReader(is))) {
				
				String line = buf.readLine(); 
				StringBuilder sb = new StringBuilder();

				while(line != null) { 
					sb.append(line).append("\n"); 
					line = buf.readLine(); 
				} 
				
				String fileAsString = sb.toString();

				map = ConversionUtil.stringToMap(fileAsString);
				
			} catch (IOException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return map;
	}
}
