package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.EquipViewer;
import com.revenga.rits.front.openitsdao.repository.EquipViewerRepository;
import com.revenga.rits.front.openitsdao.service.EquipViewerService;

@Service
public class EquipViewerServiceImpl implements EquipViewerService {

	@Autowired
	EquipViewerRepository equipViewerRepository;
	
	@Override
	public List<EquipViewer> getAll() {

		return equipViewerRepository.findAll();
	}

	@Override
	public EquipViewer getById(String id) {
		
		return equipViewerRepository.findById(id);
	}
}
