package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.model.VoEntityHierarchy;
import com.revenga.rits.front.openitsdao.repository.VoEntityHierarchyRepository;
import com.revenga.rits.front.openitsdao.service.VoEntityHierarchyService;

@Service
public class VoEntityHierarchyImpl implements VoEntityHierarchyService {

	@Autowired
	VoEntityHierarchyRepository voEntityHierarchyRepository;

	@Override
	public List<VoEntityHierarchy> getAll() {
		
		return voEntityHierarchyRepository.findAll();
	}

	@Override
	public VoEntityHierarchy getById(String id) {
		
		return voEntityHierarchyRepository.findById(id);
	}

	@Override
	public List<VoEntityHierarchy> getByVoEntityParent(VoEntity voEntityParent) {
		
		return voEntityHierarchyRepository.findByVoEntityParent(voEntityParent);
	}
}
