package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.SelectorTree;

public interface SelectorTreeService {

	public List<SelectorTree> getAll();
	public SelectorTree getById(Integer id);
}
