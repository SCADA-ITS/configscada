package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ElementTreeNode;
import com.revenga.rits.back.data.core.model.ElementTreeNodeHierarchy;
import com.revenga.rits.back.front.core.model.helper.ElementTreeNodeHierarchyHelper;
import com.revenga.rits.back.front.core.model.vo.ElementTreeNodeHierarchyVO;
import com.revenga.rits.back.front.core.model.vo.ElementTreeNodeVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class ElementTreeNodeHierarchyGraphql extends ElementTreeNodeHierarchyVO {

	private static final long serialVersionUID = 7022899449227586465L;

	private ElementTreeNodeHierarchy elementTreeNodeHierarchy;

	private MapRequests mapRequests;

	public ElementTreeNodeHierarchyGraphql(final ElementTreeNodeHierarchy elementTreeNodeHierarchy, MapRequests mapRequests) {

		ElementTreeNodeHierarchyHelper.assign(elementTreeNodeHierarchy, this);

		this.elementTreeNodeHierarchy = elementTreeNodeHierarchy;
		this.mapRequests = mapRequests;
	}

	public ElementTreeNodeHierarchyGraphql(final ElementTreeNodeHierarchy elementTreeNodeHierarchy) {

		ElementTreeNodeHierarchyHelper.assign(elementTreeNodeHierarchy, this);

		this.elementTreeNodeHierarchy = elementTreeNodeHierarchy;
		this.mapRequests = null;
	}


	@Override
	public ElementTreeNodeVO getChild() {

		if (super.getChild() == null && this.elementTreeNodeHierarchy != null) {

				ElementTreeNode elementTreeNodeChild = EntitiesManager.getInstance().getElementTreeNodeRepository().get(this.elementTreeNodeHierarchy.getNodeChildId(), false);
						
				if (elementTreeNodeChild != null) {
					
					ElementTreeNodeVO elementTreeNodeChildVO = new ElementTreeNodeGraphql(elementTreeNodeChild);

					super.setChild(elementTreeNodeChildVO);
				}
		} 
		else if(this.elementTreeNodeHierarchy == null) {
			log.error("elementTreeNodeHierarchy has null pointer");
		}
		
		return super.getChild();
	}
	
	@Override
	public ElementTreeNodeVO getParent() {

		if (super.getParent() == null && this.elementTreeNodeHierarchy != null) {

			ElementTreeNode elementTreeNodeParent = EntitiesManager.getInstance().getElementTreeNodeRepository().get(this.elementTreeNodeHierarchy.getNodeParentId(), false);
					
			if (elementTreeNodeParent != null) {
				
				ElementTreeNodeVO elementTreeNodeParentVO = new ElementTreeNodeGraphql(elementTreeNodeParent);

				super.setParent(elementTreeNodeParentVO);
			}
		} 
		else if(this.elementTreeNodeHierarchy == null) {
			log.error("elementTreeNodeHierarchy has null pointer");
		}
	
		return super.getParent();
	}
	
}
