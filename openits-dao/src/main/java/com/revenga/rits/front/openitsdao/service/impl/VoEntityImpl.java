package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.repository.VoEntityRepository;
import com.revenga.rits.front.openitsdao.service.VoEntityService;

@Service
public class VoEntityImpl implements VoEntityService {

	@Autowired
	VoEntityRepository voEntityRepository;

	@Override
	public List<VoEntity> getAll() {
		
		return voEntityRepository.findAll();
	}

	@Override
	public VoEntity getById(String id) {
		
		return voEntityRepository.findById(id);
	}
	

}
