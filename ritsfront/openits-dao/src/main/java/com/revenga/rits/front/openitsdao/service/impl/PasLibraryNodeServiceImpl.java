package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibraryHierarchy;
import com.revenga.rits.front.openitsdao.model.PasLibraryNode;
import com.revenga.rits.front.openitsdao.repository.PasLibraryHierarchyRepository;
import com.revenga.rits.front.openitsdao.repository.PasLibraryNodeRepository;
import com.revenga.rits.front.openitsdao.service.PasLibraryNodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class PasLibraryNodeServiceImpl implements PasLibraryNodeService {

	@Autowired
	PasLibraryNodeRepository pasLibraryNodeRepository;
	
	@Autowired
	PasLibraryHierarchyRepository pasLibraryHierarchyRepository;
	
	@Override
	public List<PasLibraryNode> getAll() {

		return pasLibraryNodeRepository.findAll();
	}

	@Override
	public PasLibraryNode getById(Integer id) {
		
		return pasLibraryNodeRepository.findById(id);
	}
	
	@Override
	@Transactional
	public <S extends PasLibraryNode> S save(S pasLibraryNode) {
		
		return save(pasLibraryNode, null);
	}

	@Override
	@Transactional
	public <S extends PasLibraryNode> S save(S pasLibraryNode, List<Integer> parentNodeIds) {
		
		S pasLibraryNodeSaved = null;
				
		boolean exists = (getById(pasLibraryNode.getId()) != null);
		
		pasLibraryNodeSaved = pasLibraryNodeRepository.save(pasLibraryNode);
		
		boolean newLibraryNode = (!exists && pasLibraryNodeSaved != null);
		
		if (newLibraryNode && !CollectionUtils.isEmpty(parentNodeIds)) {
			
			for (Integer parentNodeId : parentNodeIds) {
				
				PasLibraryNode pasLibraryNodeParent = getById(parentNodeId);
				
				if (pasLibraryNodeParent != null ) {
					
					List<PasLibraryHierarchy> pasLibraryHierarchyList = pasLibraryHierarchyRepository.findByNodeParentId(pasLibraryNodeParent.getId());
					
					boolean insertHierarchy = true;
					
					if (!CollectionUtils.isEmpty(pasLibraryHierarchyList)) {
						
						PasLibraryHierarchy pasLibraryHierarchyFound = pasLibraryHierarchyList.stream().filter(e -> e.getNodeChildId().equals(pasLibraryNode.getId())).findFirst().orElse(null);
						
						insertHierarchy = (pasLibraryHierarchyFound == null);
					}
					if (insertHierarchy) {
						
						PasLibraryHierarchy pasLibraryHierarchy = new PasLibraryHierarchy();
						
						pasLibraryHierarchy.setNodeParentId(pasLibraryNodeParent.getId());
						pasLibraryHierarchy.setNodeChildId(pasLibraryNodeSaved.getId());
						pasLibraryHierarchy.setEnabled(true);
						pasLibraryHierarchy.setVisible(true);
						pasLibraryHierarchy.setPasLibrary(pasLibraryNode.getPasLibrary());
						
						pasLibraryHierarchyRepository.save(pasLibraryHierarchy);
					}
				}
			}
		}
		
		return pasLibraryNodeSaved;
	}

	@Override
	@Transactional
	public PasLibraryNode deleteById(Integer id) {
		
		PasLibraryNode pasLibraryNode = getById(id);
		
		if (pasLibraryNode != null) {
		
			deleteChilds(id);
			
			deleteParents(id);
			
			pasLibraryNodeRepository.deleteById(id);
		}
		
		return pasLibraryNode;
	}
	
	private void deleteChilds(Integer childId) {
		
		pasLibraryNodeRepository.deleteById(childId);
		
		List<PasLibraryHierarchy> libraryHierarchyChilds = pasLibraryHierarchyRepository.findByNodeChildId(childId);
		
		if (!CollectionUtils.isEmpty(libraryHierarchyChilds)) {
			
			for (PasLibraryHierarchy pasLibraryHierarchyChild : libraryHierarchyChilds) {
				
				pasLibraryHierarchyRepository.deleteById(pasLibraryHierarchyChild.getId());
			}
		}
	}
	
	private void deleteParents(Integer parentId) {
		
		List<PasLibraryHierarchy> libraryHierarchyParents = pasLibraryHierarchyRepository.findByNodeParentId(parentId);
		
		if (!CollectionUtils.isEmpty(libraryHierarchyParents)) {
			
			for (PasLibraryHierarchy pasLibraryHierarchyParent : libraryHierarchyParents) {
				
				pasLibraryHierarchyRepository.deleteById(pasLibraryHierarchyParent.getId());
				
				deleteById(pasLibraryHierarchyParent.getNodeChildId());
			}
		}
	}
}
