package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibraryElement;
import com.revenga.rits.front.openitsdao.repository.PasLibraryElementRepository;
import com.revenga.rits.front.openitsdao.service.PasLibraryElementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasLibraryElementServiceImpl implements PasLibraryElementService {

	@Autowired
	PasLibraryElementRepository pasLibraryElementRepository;
	
	@Override
	public List<PasLibraryElement> getAll() {

		return pasLibraryElementRepository.findAll();
	}

	@Override
	public PasLibraryElement getByElementId(String elementId) {
		
		return pasLibraryElementRepository.findByElementId(elementId);
	}
}
