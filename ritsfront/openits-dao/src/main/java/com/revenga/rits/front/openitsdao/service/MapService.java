package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.Map;

public interface MapService {

	public List<Map> getAll();
	public Map getById(Integer id);
}
