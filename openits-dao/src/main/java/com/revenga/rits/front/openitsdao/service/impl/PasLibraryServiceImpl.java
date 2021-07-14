package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibrary;
import com.revenga.rits.front.openitsdao.repository.PasLibraryRepository;
import com.revenga.rits.front.openitsdao.service.PasLibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasLibraryServiceImpl implements PasLibraryService {

	@Autowired
	PasLibraryRepository pasLibraryRepository;
	
	@Override
	public List<PasLibrary> getAll() {

		return pasLibraryRepository.findAll();
	}

	@Override
	public PasLibrary getById(Integer id) {
		
		return pasLibraryRepository.findById(id);
	}
}
