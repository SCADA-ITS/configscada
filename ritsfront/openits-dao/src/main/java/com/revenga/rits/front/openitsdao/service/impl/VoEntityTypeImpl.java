package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.VoEntityType;
import com.revenga.rits.front.openitsdao.repository.VoEntityTypeRepository;
import com.revenga.rits.front.openitsdao.service.VoEntityTypeService;

@Service
public class VoEntityTypeImpl implements VoEntityTypeService {

	@Autowired
	VoEntityTypeRepository voEntityTypeRepository;

	@Override
	public List<VoEntityType> getAll() {
		
		return voEntityTypeRepository.findAll();
	}

	@Override
	public VoEntityType getById(String id) {
		
		return voEntityTypeRepository.findById(id);
	}
}
