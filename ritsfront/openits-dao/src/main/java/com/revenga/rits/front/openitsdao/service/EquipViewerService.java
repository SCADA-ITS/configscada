package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.EquipViewer;

public interface EquipViewerService {

	public List<EquipViewer> getAll();
	public EquipViewer getById(String id);
}
