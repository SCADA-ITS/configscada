package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.Tree;
import com.revenga.rits.front.openitsdao.repository.TreeRepository;
import com.revenga.rits.front.openitsdao.service.TreeService;

@Service
public class TreeServiceImpl implements TreeService {

	@Autowired
	TreeRepository treeRepository;
	
	@Override
	public List<Tree> getAll() {

		return treeRepository.findAll();
	}

	@Override
	public Tree getById(Integer id) {
		
		return treeRepository.findById(id);
	}
}
