package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.ModuleType;

public interface ModuleTypeService {

	public List<ModuleType> getAll();
	public ModuleType getById(Integer id);
}
