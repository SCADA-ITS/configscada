package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibraryNode;

public interface PasLibraryNodeService {

	public List<PasLibraryNode> getAll();
	public PasLibraryNode getById(Integer id);
	
	public <S extends PasLibraryNode> S save(S pasLibraryNode);
	public <S extends PasLibraryNode> S save(S pasLibraryNode, List<Integer> parentNodeIds);
	public PasLibraryNode deleteById(Integer id);
}
