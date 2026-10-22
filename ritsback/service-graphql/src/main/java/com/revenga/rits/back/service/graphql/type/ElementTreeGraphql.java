package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ElementTree;
import com.revenga.rits.back.data.core.model.ElementTreeNode;
import com.revenga.rits.back.data.core.model.ElementTreeNodeHierarchy;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
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

	private Long managementAreaId;

	public ElementTreeGraphql(final ElementTree elementTree, MapRequests mapRequests, final Long managementAreaId) {

		ElementTreeHelper.assign(elementTree, this);

		this.elementTree = elementTree;
		this.mapRequests = mapRequests;
		this.managementAreaId = managementAreaId;
	}

	public ElementTreeGraphql(final ElementTree elementTree, final Long managementAreaId) {

		ElementTreeHelper.assign(elementTree, this);

		this.elementTree = elementTree;
		this.mapRequests = null;
		this.managementAreaId = managementAreaId;
	}

	@Override
	public List<ElementTreeNodeVO> getElementTreeNodes() {

		if (super.getElementTreeNodes() == null && this.elementTree != null) {

			List<ElementTreeNode> elementTreeNodes = EntitiesManager.getInstance().getElementTreeNodeRepository()
					.getAll(false).stream().filter(e -> e.getElementTreeId().equals(this.elementTree.getId()))
					.sorted(Comparator.comparing(ElementTreeNode::getPosition)).collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(elementTreeNodes)) {

				List<ElementTreeNodeVO> elementTreeNodesVO = new ArrayList<>();

				elementTreeNodes.forEach(elementTreeNode -> {

					if (checkManagementArea(elementTreeNode)) {

						elementTreeNodesVO.add(new ElementTreeNodeGraphql(elementTreeNode));
					}

				});

				super.setElementTreeNodes(elementTreeNodesVO);
			}
		} else if (this.elementTree == null) {
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

				elementTreeNodeHierarchies.forEach(elementTreeNodeHierarchy -> {

					if (checkManagementArea(elementTreeNodeHierarchy)) {

						elementTreeNodeHierarchiesVO.add(new ElementTreeNodeHierarchyGraphql(elementTreeNodeHierarchy));
					}
				});

				super.setElementTreeNodeHierarchies(elementTreeNodeHierarchiesVO);

			}
		} else if (this.elementTree == null)

		{
			log.error("elementTree has null pointer");
		}

		return super.getElementTreeNodeHierarchies();
	}

	private boolean checkManagementArea(final ElementTreeNode elementTreeNode) {

		return elementTreeNode.getManagementAreaId() == null
				|| elementTreeNode.getManagementAreaId().equals(this.managementAreaId);
	}

	private boolean checkManagementArea(final ElementTreeNodeHierarchy elementTreeNodeHierarchy) {

		boolean resp = false;

		ElementTreeNode nodeParent = EntitiesManager.getInstance().getElementTreeNodeRepository()
				.get(elementTreeNodeHierarchy.getNodeParentId(), false);

		ElementTreeNode nodeChild = EntitiesManager.getInstance().getElementTreeNodeRepository()
				.get(elementTreeNodeHierarchy.getNodeChildId(), false);

		if (nodeParent != null && nodeChild != null) {

			List<ManagementAreaElement> managementAreaParent = EntitiesManager.getInstance()
					.getManagementAreaElementRepository()
					.gets(nodeParent.getElementTypeId(), nodeParent.getElementId(), false);

			boolean parent = CollectionUtils.isEmpty(managementAreaParent) || (this.managementAreaId != null
					&& managementAreaParent.stream().filter(e -> e.getId().equals(this.managementAreaId)).count() > 0);

			if (parent) {
				List<ManagementAreaElement> managementAreaChild = EntitiesManager.getInstance()
						.getManagementAreaElementRepository()
						.gets(nodeChild.getElementTypeId(), nodeChild.getElementId(), false);

				boolean child = CollectionUtils.isEmpty(managementAreaChild)
						|| (this.managementAreaId != null && managementAreaChild.stream()
								.filter(e -> e.getId().equals(this.managementAreaId)).count() > 0);

				if (child) {
					
					resp = true;
				}
			}
		}
		return resp;
	}
}
