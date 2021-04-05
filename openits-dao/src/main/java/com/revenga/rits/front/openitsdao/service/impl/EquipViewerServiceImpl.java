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
	EquipViewerRepository EquipViewerRepository;
	
	@Override
	public List<EquipViewer> getAll() {

		return EquipViewerRepository.findAll();
	}

	@Override
	public EquipViewer getById(String id) {
		
		return EquipViewerRepository.findById(id);
	}
}
