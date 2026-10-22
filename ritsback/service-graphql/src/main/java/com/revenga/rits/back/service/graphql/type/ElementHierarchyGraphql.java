package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.front.core.model.helper.ElementHierarchyHelper;
import com.revenga.rits.back.front.core.model.vo.ElementHierarchyVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class ElementHierarchyGraphql extends ElementHierarchyVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1632470647575978121L;
	
	private ElementHierarchy elementHierarchy;
	
	private MapRequests mapRequests;

	public ElementHierarchyGraphql(final ElementHierarchy elementHierarchy, MapRequests mapRequests) {

		ElementHierarchyHelper.assign(elementHierarchy, this);

		this.elementHierarchy = elementHierarchy;
		this.mapRequests = mapRequests;
	}

	public ElementHierarchyGraphql(final ElementHierarchy elementHierarchy) {

		this.elementHierarchy = elementHierarchy;
		this.mapRequests = null;
		
		ElementHierarchyHelper.assign(elementHierarchy, this);

	}
	
	@Override
	public ElementVO getParent() {
		
		
		if (super.getParent() == null) {
			if (elementHierarchy != null && 
				(elementHierarchy.getParentElementId() != null && elementHierarchy.getParentElementTypeId() != null)) {
				
				Element elementParent = EntitiesManager.getInstance().getElementRepository().get(
						elementHierarchy.getParentElementTypeId(),
						elementHierarchy.getParentElementId(), false);

				if (elementParent != null) {
					
					super.setParent(new ElementGraphql(elementParent, mapRequests));
				}
			}
		}
	
		return super.getParent();			
	}
		
	@Override
	public ElementVO getChild() {
		
		
		if (super.getChild() == null) {
			
			if (elementHierarchy != null && 
				(elementHierarchy.getChildElementId() != null && elementHierarchy.getChildElementTypeId() != null)) {
				
				Element elementChild = EntitiesManager.getInstance().getElementRepository().get(
						elementHierarchy.getChildElementTypeId(),
						elementHierarchy.getChildElementId(), false);

				if (elementChild != null) {
					
					super.setChild(new ElementGraphql(elementChild, mapRequests));
				}
			}
		}
	
		return super.getChild();			
	}	
}
