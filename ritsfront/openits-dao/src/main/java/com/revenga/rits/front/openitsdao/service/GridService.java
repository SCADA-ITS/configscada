package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.Grid;

public interface GridService {

	public List<Grid> getAll();
	public Grid getById(Integer id);
}
