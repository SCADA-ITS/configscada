package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.Map;
import com.revenga.rits.front.openitsdao.repository.MapRepository;
import com.revenga.rits.front.openitsdao.service.MapService;

@Service
public class MapServiceImpl implements MapService {

	@Autowired
	MapRepository mapRepository;
	
	@Override
	public List<Map> getAll() {

		return mapRepository.findAll();
	}

	@Override
	public Map getById(Integer id) {
		
		return mapRepository.findById(id);
	}
}
