package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revenga.rits.front.openitsdao.model.ModuleValue;
import com.revenga.rits.front.openitsdao.repository.ModuleValueRepository;
import com.revenga.rits.front.openitsdao.service.ModuleValueService;

@Service
public class ModuleValueServiceImpl implements ModuleValueService {

	@Autowired
	ModuleValueRepository moduleValueRepository;
	
	@Override
	public List<ModuleValue> getAll() {

		return moduleValueRepository.findAll();
	}

	@Override
	@Transactional
	public <S extends ModuleValue> S save(S moduleValue) {
		
		return moduleValueRepository.save(moduleValue);
	}
}
