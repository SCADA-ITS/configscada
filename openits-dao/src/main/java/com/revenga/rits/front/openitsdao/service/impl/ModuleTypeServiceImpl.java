package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.ModuleType;
import com.revenga.rits.front.openitsdao.repository.ModuleTypeRepository;
import com.revenga.rits.front.openitsdao.service.ModuleTypeService;

@Service
public class ModuleTypeServiceImpl implements ModuleTypeService {

	@Autowired
	ModuleTypeRepository moduleTypeRepository;
	
	@Override
	public List<ModuleType> getAll() {

		return moduleTypeRepository.findAll();
	}
	
	@Override
	public ModuleType getById(Integer id) {
		
		return moduleTypeRepository.findById(id);
	}
}
