package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.Grid;
import com.revenga.rits.front.openitsdao.repository.GridRepository;
import com.revenga.rits.front.openitsdao.service.GridService;

@Service
public class GridServiceImpl implements GridService {

	@Autowired
	GridRepository gridRepository;
	
	@Override
	public List<Grid> getAll() {

		return gridRepository.findAll();
	}

	@Override
	public Grid getById(Integer id) {
		
		return gridRepository.findById(id);
	}
}
