package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.ModuleValue;

public interface ModuleValueService {

	public List<ModuleValue> getAll();
	
	public <S extends ModuleValue> S save(S moduleValue);
}
