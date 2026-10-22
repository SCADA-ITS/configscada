package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.SelectorTree;
import com.revenga.rits.front.openitsdao.repository.SelectorTreeRepository;
import com.revenga.rits.front.openitsdao.service.SelectorTreeService;

@Service
public class SelectorTreeServiceImpl implements SelectorTreeService {

	@Autowired
	SelectorTreeRepository selectorTreeRepository;
	
	@Override
	public List<SelectorTree> getAll() {

		return selectorTreeRepository.findAll();
	}

	@Override
	public SelectorTree getById(Integer id) {
		
		return selectorTreeRepository.findById(id);
	}
}
