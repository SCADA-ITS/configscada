package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VmsLibraryNode;

public interface VmsLibraryNodeService {

	public List<VmsLibraryNode> getAll();
	public VmsLibraryNode getById(Integer id);
	
	public <S extends VmsLibraryNode> S save(S vmsLibraryNode);
	public <S extends VmsLibraryNode> S save(S vmsLibraryNode, List<Integer> parentNodeIds);
	//public VmsLibraryNode save(VmsLibraryNode vmsLibraryNode);
	//public VmsLibraryNode save(VmsLibraryNode vmsLibraryNode, List<Integer> parentNodeIds);
	public VmsLibraryNode deleteById(Integer id);
}
