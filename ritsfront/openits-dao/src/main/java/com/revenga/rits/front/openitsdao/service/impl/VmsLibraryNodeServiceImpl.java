package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openitsdao.model.VmsLibraryHierarchy;
import com.revenga.rits.front.openitsdao.model.VmsLibraryNode;
import com.revenga.rits.front.openitsdao.repository.VmsLibraryNodeRepository;
import com.revenga.rits.front.openitsdao.service.VmsLibraryNodeService;

@Service
public class VmsLibraryNodeServiceImpl implements VmsLibraryNodeService {

	@Autowired
	VmsLibraryNodeRepository vmsLibraryNodeRepository;
	
	@Autowired
	VmsLibraryHierarchyServiceImpl vmsLibraryHierarchyService;
	
	@Override
	public List<VmsLibraryNode> getAll() {

		return vmsLibraryNodeRepository.findAll();
	}

	@Override
	public VmsLibraryNode getById(Integer id) {
		
		return vmsLibraryNodeRepository.findById(id);
	}
	
	@Override
	@Transactional
	public <S extends VmsLibraryNode> S save(S vmsLibraryNode) {
		
		return save(vmsLibraryNode, null);
	}

	@Override
	@Transactional
	public <S extends VmsLibraryNode> S save(S vmsLibraryNode, List<Integer> parentNodeIds) {
		
		S vmsLibraryNodeSaved = null;
				
		boolean exists = (getById(vmsLibraryNode.getId()) != null);
		
		vmsLibraryNodeSaved = vmsLibraryNodeRepository.save(vmsLibraryNode);
		
		boolean newLibraryNode = (!exists && vmsLibraryNodeSaved != null);
		
		if (newLibraryNode && !CollectionUtils.isEmpty(parentNodeIds)) {
			
			for (Integer parentNodeId : parentNodeIds) {
				
				VmsLibraryNode vmsLibraryNodeParent = getById(parentNodeId);
				
				if (vmsLibraryNodeParent != null ) {
					
					List<VmsLibraryHierarchy> vmsLibraryHierarchyList = vmsLibraryHierarchyService.getByNodeParentId(vmsLibraryNodeParent.getId());
					
					boolean insertHierarchy = true;
					
					if (!CollectionUtils.isEmpty(vmsLibraryHierarchyList)) {
						
						VmsLibraryHierarchy vmsLibraryHierarchyFound = vmsLibraryHierarchyList.stream().filter(e -> e.getNodeChildId().equals(vmsLibraryNode.getId())).findFirst().orElse(null);
						
						insertHierarchy = (vmsLibraryHierarchyFound == null);
					}
					if (insertHierarchy) {
						
						VmsLibraryHierarchy vmsLibraryHierarchy = new VmsLibraryHierarchy();
						
						vmsLibraryHierarchy.setNodeParentId(vmsLibraryNodeParent.getId());
						vmsLibraryHierarchy.setNodeChildId(vmsLibraryNodeSaved.getId());
						vmsLibraryHierarchy.setEnabled(true);
						vmsLibraryHierarchy.setVisible(true);
						vmsLibraryHierarchy.setVmsLibrary(vmsLibraryNode.getVmsLibrary());
						
						vmsLibraryHierarchyService.save(vmsLibraryHierarchy);
					}
				}
			}
		}
		
		return vmsLibraryNodeSaved;
	}

	@Override
	@Transactional
	public VmsLibraryNode deleteById(Integer id) {
		
		VmsLibraryNode vmsLibraryNode = getById(id);
		
		if (vmsLibraryNode != null) {
		
			deleteChilds(id);
			
			deleteParents(id);
			
			vmsLibraryNodeRepository.delete(vmsLibraryNode);
		}
		
		return vmsLibraryNode;
	}
	
	private void deleteChilds(Integer childId) {
		
		List<VmsLibraryHierarchy> libraryHierarchyChilds = vmsLibraryHierarchyService.getByNodeChildId(childId);
		
		if (!CollectionUtils.isEmpty(libraryHierarchyChilds)) {
			
			for (VmsLibraryHierarchy vmsLibraryHierarchyChild : libraryHierarchyChilds) {
				
				vmsLibraryHierarchyService.deleteById(vmsLibraryHierarchyChild.getId());
			}
		}
	}
	
	private void deleteParents(Integer parentId) {
		
		List<VmsLibraryHierarchy> libraryHierarchyParents = vmsLibraryHierarchyService.getByNodeParentId(parentId);
		
		if (!CollectionUtils.isEmpty(libraryHierarchyParents)) {
			
			for (VmsLibraryHierarchy vmsLibraryHierarchyParent : libraryHierarchyParents) {
				
				vmsLibraryHierarchyService.deleteById(vmsLibraryHierarchyParent.getId());
				
				deleteById(vmsLibraryHierarchyParent.getNodeChildId());
			}
		}
	}
}
