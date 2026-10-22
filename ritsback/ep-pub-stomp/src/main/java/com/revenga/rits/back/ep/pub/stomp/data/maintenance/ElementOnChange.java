package com.revenga.rits.back.ep.pub.stomp.data.maintenance;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.entities.lib.repository.entity.EntityOnChange;
import com.revenga.rits.back.ep.pub.stomp.service.EntitiesManager;

public class ElementOnChange extends EntityOnChange {

	public ElementOnChange() {
		
		super(Element.class);
	}

	@Override
	protected void create() {
		
		if (basicElement instanceof Element) {
			
			EntitiesManager.getInstance().updateElementManagementAreaMap((Element) basicElement);
		}
	}

	@Override
	protected void update() {
		
		if (basicElement instanceof Element) {
			
			EntitiesManager.getInstance().updateElementManagementAreaMap((Element) basicElement);
		}
	}

	@Override
	protected void delete() {
		
		if (basicElement instanceof Element) {
			
			EntitiesManager.getInstance().updateElementManagementAreaMap((Element) basicElement);
		}
	}
}
