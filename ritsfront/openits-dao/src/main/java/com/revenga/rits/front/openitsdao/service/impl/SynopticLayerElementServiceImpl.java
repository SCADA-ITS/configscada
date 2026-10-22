package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.SynopticLayerElement;
import com.revenga.rits.front.openitsdao.repository.SynopticLayerElementRepository;
import com.revenga.rits.front.openitsdao.service.SynopticLayerElementService;

@Service
public class SynopticLayerElementServiceImpl implements SynopticLayerElementService {

	@Autowired
	SynopticLayerElementRepository synopticLayerElementRepository;
	
	@Override
	public List<SynopticLayerElement> getAll() {

		return synopticLayerElementRepository.findAll();
	}
}
