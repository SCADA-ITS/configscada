package com.revenga.rits.back.io.controller.module;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

public class GenericEndpointHelper {
	
	private GenericEndpointHelper() {
		
		throw new IllegalStateException(this.getClass().getSimpleName());
	}
	
	public static ElementValue getElementValue(List<ElementValue> elementValues, ElementTypeParam paramState) {

		if (!CollectionUtils.isEmpty(elementValues) && paramState != null) {
		
			return elementValues.stream()
					.filter(e -> e.getElementTypeId().equals(paramState.getElementTypeId())
							&& e.getElementTypeParamId().equals(paramState.getId())
							&& e.getParamTypeId().equals(paramState.getParamTypeId()))
					.findFirst().orElse(null);
		}
		
		return null;
	}
	
	public static ElementState createElementState(Element element, Long stateId) {
		
		ElementState elementState = new ElementState();
		
		elementState.setElementId(element.getId());
		elementState.setElementTypeId(element.getElementTypeId());
		elementState.setStateId(stateId);
		
		return elementState;
	}
	
	public static boolean isDisconnectedState(ElementState elementState) {
		
		boolean res = false;
		Long disconnectedStateId = null;
		
		Element element = new Element();
		
		element.setElementTypeId(elementState.getElementTypeId());
		element.setId(elementState.getElementId());
		
		disconnectedStateId = getDisconnectedStateId(element);
		
		if (disconnectedStateId != null) {
			
			res = disconnectedStateId.equals(elementState.getStateId());
		}
			
		return res;
	}
	
	public static Long getInitialStateId(Element element) {
		
		ElementType elementType = EntitiesManager.getInstance().getElementType(element.getElementTypeId());
		Long disconnectedStateId = null;
		
		if (elementType != null) {
			
			disconnectedStateId = elementType.getInitialElementTypeStateId();
		}
		
		return disconnectedStateId;
	}
	
	public static Long getDisconnectedStateId(Element element) {
		
		ElementType elementType = EntitiesManager.getInstance().getElementType(element.getElementTypeId());
		Long disconnectedStateId = null;
		
		if (elementType != null) {
			
			disconnectedStateId = elementType.getDisconnectedElementTypeStateId();
		}
		
		return disconnectedStateId;
	}
	
	public static Long getConnectedStateId(Element element) {
		
		ElementType elementType = EntitiesManager.getInstance().getElementType(element.getElementTypeId());
		Long connectedStateId = null;
		
		if (elementType != null) {
			
			connectedStateId = elementType.getConnectedElementTypeStateId();
		}
		
		return connectedStateId;
	}
}
