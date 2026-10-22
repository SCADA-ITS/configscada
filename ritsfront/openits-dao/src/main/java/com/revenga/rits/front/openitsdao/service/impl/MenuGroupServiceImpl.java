package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.MenuGroup;
import com.revenga.rits.front.openitsdao.repository.MenuGroupRepository;
import com.revenga.rits.front.openitsdao.service.MenuGroupService;

@Service
public class MenuGroupServiceImpl implements MenuGroupService {

	@Autowired
	MenuGroupRepository menuGroupRepository;
	
	@Override
	public List<MenuGroup> getAll() {

		return menuGroupRepository.findAll();
	}

	@Override
	public MenuGroup getById(Integer id) {
		
		return menuGroupRepository.findById(id);
	}
}
