package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTreeNode;
import com.revenga.rits.back.front.core.model.helper.ElementTreeNodeHelper;
import com.revenga.rits.back.front.core.model.vo.ElementTreeNodeVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class ElementTreeNodeGraphql extends ElementTreeNodeVO {

	private static final long serialVersionUID = -1617230945253224655L;

	private ElementTreeNode elementTreeNode;

	private MapRequests mapRequests;

	public ElementTreeNodeGraphql(final ElementTreeNode elementTreeNode, MapRequests mapRequests) {

		ElementTreeNodeHelper.assign(elementTreeNode, this);

		this.elementTreeNode = elementTreeNode;
		this.mapRequests = mapRequests;
	}

	public ElementTreeNodeGraphql(final ElementTreeNode elementTreeNode) {

		ElementTreeNodeHelper.assign(elementTreeNode, this);

		this.elementTreeNode = elementTreeNode;
		this.mapRequests = null;
	}
	
	@Override
	public ElementVO getElement() {

		if (super.getElement() == null) {

			if (elementTreeNode != null) {

				Element element = EntitiesManager.getInstance().getElementRepository()
						.get(elementTreeNode.getElementTypeId(), elementTreeNode.getElementId(), false);
				
				if (element != null) {

					super.setElement(new ElementGraphql(element));
				}

			} else {

				log.error("commandElement has null pointer");
			}

		}
		return super.getElement();
	}
}
