package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.MenuGroup;

public interface MenuGroupService {

	public List<MenuGroup> getAll();
	public MenuGroup getById(Integer id);
}
