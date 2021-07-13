package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VmsLibraryHierarchy;

public interface VmsLibraryHierarchyService {

	public List<VmsLibraryHierarchy> getAll();
	public List<VmsLibraryHierarchy> getByNodeParentId(Integer id);
	public VmsLibraryHierarchy getById(Integer id);
	
	//public VmsLibraryHierarchy save(VmsLibraryHierarchy vmsLibraryHierarchy);
	public <S extends VmsLibraryHierarchy> S save(S vmsLibraryHierarchy);
	public VmsLibraryHierarchy deleteById(Integer id);
	public VmsLibraryHierarchy delete(VmsLibraryHierarchy vmsLibraryHierarchy);
}
