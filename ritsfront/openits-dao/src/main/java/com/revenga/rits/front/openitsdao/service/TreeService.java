package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.Tree;

public interface TreeService {

	public List<Tree> getAll();
	public Tree getById(Integer id);
}
