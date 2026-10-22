package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.VmsLibraryElementSubtype;
import com.revenga.rits.front.openitsdao.repository.VmsLibraryElementSubtypeRepository;
import com.revenga.rits.front.openitsdao.service.VmsLibraryElementSubtypeService;

@Service
public class VmsLibraryElementSubtypeServiceImpl implements VmsLibraryElementSubtypeService {

	@Autowired
	VmsLibraryElementSubtypeRepository vmsLibraryElementSubtypeRepository;
	
	@Override
	public List<VmsLibraryElementSubtype> getAll() {

		return vmsLibraryElementSubtypeRepository.findAll();
	}

	@Override
	public VmsLibraryElementSubtype getByElementSubtypeId(String elementSubtypeId) {
		
		return vmsLibraryElementSubtypeRepository.findByElementSubtypeId(elementSubtypeId);
	}
}
