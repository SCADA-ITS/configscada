package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.VmsLibrary;
import com.revenga.rits.front.openitsdao.repository.VmsLibraryRepository;
import com.revenga.rits.front.openitsdao.service.VmsLibraryService;

@Service
public class VmsLibraryServiceImpl implements VmsLibraryService {

	@Autowired
	VmsLibraryRepository vmsLibraryRepository;
	
	@Override
	public List<VmsLibrary> getAll() {

		return vmsLibraryRepository.findAll();
	}

	@Override
	public VmsLibrary getById(Integer id) {
		
		return vmsLibraryRepository.findById(id);
	}
}
