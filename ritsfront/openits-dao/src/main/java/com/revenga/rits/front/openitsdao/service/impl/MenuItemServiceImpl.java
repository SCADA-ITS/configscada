package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.MenuItem;
import com.revenga.rits.front.openitsdao.repository.MenuItemRepository;
import com.revenga.rits.front.openitsdao.service.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

	@Autowired
	MenuItemRepository menuItemRepository;
	
	@Override
	public List<MenuItem> getAll() {

		return menuItemRepository.findAll();
	}

	@Override
	public MenuItem getById(Integer id) {
		
		return menuItemRepository.findById(id);
	}

}
