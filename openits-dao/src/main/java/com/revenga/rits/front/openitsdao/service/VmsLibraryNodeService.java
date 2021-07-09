package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VmsLibraryNode;

public interface VmsLibraryNodeService {

	public List<VmsLibraryNode> getAll();
	public VmsLibraryNode getById(Integer id);
	
	public VmsLibraryNode save(VmsLibraryNode vmsLibraryNode);
	public VmsLibraryNode save(VmsLibraryNode vmsLibraryNode, List<Integer> parentNodeIds);
	public VmsLibraryNode deleteById(Integer id);
	public VmsLibraryNode delete(VmsLibraryNode vmsLibraryNode);
}
