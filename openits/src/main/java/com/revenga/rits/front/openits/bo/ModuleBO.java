package com.revenga.rits.front.openits.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openits.util.ResourcesUtil;
import com.revenga.rits.front.openitsdao.model.Module;
import com.revenga.rits.front.openitsdao.service.ModuleService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ModuleBO extends GenericBO {
	
	private static final String FIELD_CONFIG = "config";
	private static final String MODULE_FILE_DIR = "config/module";
	private static final String MODULE_FILE_TEMPLATE = "module_%d.json";
	
	@Autowired
	ModuleService moduleService;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<Module> modules = moduleService.getAll();
		
		if (!CollectionUtils.isEmpty(modules)) {
			
			for (Module module : modules) {
				
				Map<String, Object> moduleMap = ConversionUtil.objectToMap(module);
				
				moduleMap.put(FIELD_CONFIG, getModuleConfig(module.getId()));
				
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
		
		moduleMap.put(FIELD_CONFIG, getModuleConfig(module.getId()));
		
		return moduleMap;
	}
	
	public List<Map<String, Object>> getByModuleTypeId(Integer moduleTypeId) {
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<Module> modules = moduleService.getByModuleTypeId(moduleTypeId);
		
		if (!CollectionUtils.isEmpty(modules)) {
			
			for (Module module : modules) {
				
				Map<String, Object> moduleMap = ConversionUtil.objectToMap(module);
				
				moduleMap.put(FIELD_CONFIG, getModuleConfig(module.getId()));
				
				list.add(moduleMap);
			}
		}
		
		return list;
	}
	
	private Map<String, Object> getModuleConfig(Integer moduleId) {
		
		Map<String, Object> map = null;
		File fileConfig;
		
		fileConfig = ResourcesUtil.getFile(MODULE_FILE_DIR + File.separator + String.format(MODULE_FILE_TEMPLATE, moduleId));
		
		try (BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(fileConfig)))) {
			
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
		
		return map;
	}
}
