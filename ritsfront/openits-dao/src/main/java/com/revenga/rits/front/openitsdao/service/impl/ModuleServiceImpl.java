package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.Module;
import com.revenga.rits.front.openitsdao.repository.ModuleRepository;
import com.revenga.rits.front.openitsdao.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	ModuleRepository moduleRepository;
	
	@Override
	public List<Module> getAll() {

		return moduleRepository.findAll();
	}
	
	@Override
	public Module getById(Integer id) {
		
		return moduleRepository.findById(id);
	}
	
	@Override
	public List<Module> getByModuleTypeId(Integer moduleTypeId) {
		
		return moduleRepository.findAll().stream().filter(e -> e.getModuleType().getId().equals(moduleTypeId))
				.collect(Collectors.toList());
	}
}
