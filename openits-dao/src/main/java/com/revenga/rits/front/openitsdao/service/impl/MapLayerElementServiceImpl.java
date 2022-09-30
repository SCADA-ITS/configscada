package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.MapLayerElement;
import com.revenga.rits.front.openitsdao.repository.MapLayerElementRepository;
import com.revenga.rits.front.openitsdao.service.MapLayerElementService;

@Service
public class MapLayerElementServiceImpl implements MapLayerElementService {

	@Autowired
	MapLayerElementRepository mapLayerElementRepository;
	
	@Override
	public List<MapLayerElement> getAll() {

		return mapLayerElementRepository.findAll();
	}
}
