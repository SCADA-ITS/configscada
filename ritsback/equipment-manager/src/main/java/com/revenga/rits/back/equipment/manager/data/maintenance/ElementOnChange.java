package com.revenga.rits.back.equipment.manager.data.maintenance;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.entities.lib.repository.entity.EntityOnChange;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

public class ElementOnChange extends EntityOnChange {

	public ElementOnChange() {
		
		super(Element.class);
	}

	@Override
	protected void create() {
		
		if (basicElement instanceof Element) {
			
			EntitiesManager.getInstance().getRootData().loadElement((Element) basicElement);
		}
	}

	@Override
	protected void update() {
		
		if (basicElement instanceof Element) {
			
			EntitiesManager.getInstance().getRootData().updateElement((Element) basicElement);
		}
	}

	@Override
	protected void delete() {
		
		if (basicElement instanceof Element) {
			
			EntitiesManager.getInstance().getRootData().deleteElement((Element) basicElement);
		}
	}
}
