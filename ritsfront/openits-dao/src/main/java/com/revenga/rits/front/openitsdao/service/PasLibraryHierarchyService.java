package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibraryHierarchy;

public interface PasLibraryHierarchyService {

	public List<PasLibraryHierarchy> getAll();
	public List<PasLibraryHierarchy> getByNodeParentId(Integer id);
	public PasLibraryHierarchy getById(Integer id);
	
	public <S extends PasLibraryHierarchy> S save(S pasLibraryHierarchy);
	public PasLibraryHierarchy deleteById(Integer id);
	public PasLibraryHierarchy delete(PasLibraryHierarchy pasLibraryHierarchy);
}
