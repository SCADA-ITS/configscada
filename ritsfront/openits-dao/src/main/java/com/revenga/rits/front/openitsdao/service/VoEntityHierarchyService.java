package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.model.VoEntityHierarchy;

public interface VoEntityHierarchyService {

	public List<VoEntityHierarchy> getAll();
	public List<VoEntityHierarchy> getByVoEntityParent(VoEntity voEntityParent);
	public VoEntityHierarchy getById(String id);
}
