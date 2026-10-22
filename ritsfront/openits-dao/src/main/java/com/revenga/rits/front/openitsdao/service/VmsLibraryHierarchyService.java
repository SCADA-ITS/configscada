package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VmsLibraryHierarchy;

public interface VmsLibraryHierarchyService {

	public List<VmsLibraryHierarchy> getAll();
	public List<VmsLibraryHierarchy> getByNodeParentId(Integer id);
	public List<VmsLibraryHierarchy> getByNodeChildId(Integer id);
	public VmsLibraryHierarchy getById(Integer id);
	
	public <S extends VmsLibraryHierarchy> S save(S vmsLibraryHierarchy);
	public VmsLibraryHierarchy deleteById(Integer id);
}
