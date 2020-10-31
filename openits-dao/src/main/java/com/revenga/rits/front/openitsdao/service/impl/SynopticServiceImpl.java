package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.Synoptic;
import com.revenga.rits.front.openitsdao.repository.SynopticRepository;
import com.revenga.rits.front.openitsdao.service.SynopticService;

@Service
public class SynopticServiceImpl implements SynopticService {

	@Autowired
	SynopticRepository synopticRepository;
	
	@Override
	public List<Synoptic> getAll() {

		return synopticRepository.findAll();
	}

	@Override
	public Synoptic getById(Integer id) {
		
		return synopticRepository.findById(id);
	}
}
