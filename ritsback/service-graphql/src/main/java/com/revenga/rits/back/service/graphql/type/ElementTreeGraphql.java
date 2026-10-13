package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ElementTree;
import com.revenga.rits.back.data.core.model.ElementTreeNode;
import com.revenga.rits.back.data.core.model.ElementTreeNodeHierarchy;
import com.revenga.rits.back.front.core.model.helper.ElementTreeHelper;
import com.revenga.rits.back.front.core.model.vo.ElementTreeNodeHierarchyVO;
import com.revenga.rits.back.front.core.model.vo.ElementTreeNodeVO;
import com.revenga.rits.back.front.core.model.vo.ElementTreeVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class ElementTreeGraphql extends ElementTreeVO {

	private static final long serialVersionUID = 3745249508349015689L;

	private ElementTree elementTree;
	
	private MapRequests mapRequests;
	
	public ElementTreeGraphql(final ElementTree elementTree, MapRequests mapRequests) {

		ElementTreeHelper.assign(elementTree, this);

		this.elementTree = elementTree;
		this.mapRequests = mapRequests;
	}

	public ElementTreeGraphql(final ElementTree elementTree) {

		ElementTreeHelper.assign(elementTree, this);

		this.elementTree = elementTree;
		this.mapRequests = null;
	}
	
	@Override
	public List<ElementTreeNodeVO> getElementTreeNodes() {

		if (super.getElementTreeNodes() == null && this.elementTree != null) {
			
			List<ElementTreeNode> elementTreeNodes = EntitiesManager.getInstance().getElementTreeNodeRepository()
					.getAll(false).stream().filter(e -> e.getElementTreeId().equals(this.elementTree.getId()))
					.sorted(Comparator.comparing(ElementTreeNode::getPosition)).collect(Collectors.toList());
						
				if (!CollectionUtils.isEmpty(elementTreeNodes)) {
					
					List<ElementTreeNodeVO> elementTreeNodesVO = new ArrayList<>();
					
					elementTreeNodes.forEach(elementTreeNode -> elementTreeNodesVO.add(new ElementTreeNodeGraphql(elementTreeNode)));

					super.setElementTreeNodes(elementTreeNodesVO);
				}
		} 
		else if(this.elementTree == null) {
			log.error("elementTree has null pointer");
		}
		
		return super.getElementTreeNodes();
	}
	
	@Override
	public List<ElementTreeNodeHierarchyVO> getElementTreeNodeHierarchies() {

		if (super.getElementTreeNodeHierarchies() == null && this.elementTree != null) {
			
			List<ElementTreeNodeHierarchy> elementTreeNodeHierarchies = EntitiesManager.getInstance()
					.getElementTreeNodeHierarchyRepository().getAll(false);
						
				if (!CollectionUtils.isEmpty(elementTreeNodeHierarchies)) {
					
					List<ElementTreeNodeHierarchyVO> elementTreeNodeHierarchiesVO = new ArrayList<>();
					
					elementTreeNodeHierarchies.forEach(elementTreeNodeHierarchy -> elementTreeNodeHierarchiesVO.add(new ElementTreeNodeHierarchyGraphql(elementTreeNodeHierarchy)));

					super.setElementTreeNodeHierarchies(elementTreeNodeHierarchiesVO);
				}
		} 
		else if(this.elementTree == null) {
			log.error("elementTree has null pointer");
		}
		
		return super.getElementTreeNodeHierarchies();
	}
}
