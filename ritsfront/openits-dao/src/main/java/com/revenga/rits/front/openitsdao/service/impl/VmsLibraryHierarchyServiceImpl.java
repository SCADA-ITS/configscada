package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revenga.rits.front.openitsdao.model.VmsLibraryHierarchy;
import com.revenga.rits.front.openitsdao.repository.VmsLibraryHierarchyRepository;
import com.revenga.rits.front.openitsdao.service.VmsLibraryHierarchyService;

@Service
public class VmsLibraryHierarchyServiceImpl implements VmsLibraryHierarchyService {

	@Autowired
	VmsLibraryHierarchyRepository vmsLibraryHierarchyRepository;
	
	@Override
	public List<VmsLibraryHierarchy> getAll() {

		return vmsLibraryHierarchyRepository.findAll();
	}

	@Override
	public VmsLibraryHierarchy getById(Integer id) {
		
		return vmsLibraryHierarchyRepository.findById(id);
	}
	
	@Override
	public List<VmsLibraryHierarchy> getByNodeParentId(Integer id) {
		
		List<VmsLibraryHierarchy> hierarchies = null;
		try {
			hierarchies = vmsLibraryHierarchyRepository.findByNodeParentId(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hierarchies;
	}
	
	@Override
	public List<VmsLibraryHierarchy> getByNodeChildId(Integer id) {
		
		return vmsLibraryHierarchyRepository.findByNodeChildId(id);
	}

	
//	@Override
//	@Transactional
//	public VmsLibraryHierarchy save(VmsLibraryHierarchy vmsLibraryHierarchy) {
//		
//		return vmsLibraryHierarchyRepository.save(vmsLibraryHierarchy);
//	}
	

	@Override
	@Transactional
	public VmsLibraryHierarchy deleteById(Integer id) {
		
		VmsLibraryHierarchy vmsLibraryHierarchy = vmsLibraryHierarchyRepository.findById(id);
		
		vmsLibraryHierarchyRepository.deleteById(id);
		
		return vmsLibraryHierarchy;
	}

	@Override
	@Transactional
	public <S extends VmsLibraryHierarchy> S save(S vmsLibraryHierarchy) {
		
		return vmsLibraryHierarchyRepository.save(vmsLibraryHierarchy);
	}
}
