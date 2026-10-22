package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.Module;

public interface ModuleService {

	public List<Module> getAll();
	public Module getById(Integer id);
	public List<Module> getByModuleTypeId(Integer moduleTypeId); 
}
