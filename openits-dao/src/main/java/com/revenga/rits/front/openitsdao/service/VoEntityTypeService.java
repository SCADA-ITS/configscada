package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VoEntityType;

public interface VoEntityTypeService {

	public List<VoEntityType> getAll();
	public VoEntityType getById(String id);
}
