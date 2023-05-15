package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.repository.BackOfficeRepository;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;

@Service
public class BackOfficeServiceImpl implements BackOfficeService {

	@Autowired
	BackOfficeRepository backOfficeRepository;
	
	@Override
	public List<BackOffice> getAll() {

		return backOfficeRepository.findAll();
	}

	@Override
	public BackOffice getById(Integer id) {
		
		return backOfficeRepository.findById(id);
	}
}
