package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.MenuItem;

public interface MenuItemService {

	public List<MenuItem> getAll();
	public MenuItem getById(Integer id);
}
