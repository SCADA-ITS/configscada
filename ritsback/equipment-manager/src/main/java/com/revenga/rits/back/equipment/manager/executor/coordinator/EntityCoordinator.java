package com.revenga.rits.back.equipment.manager.executor.coordinator;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.equipment.manager.data.ElementData;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

public abstract class EntityCoordinator<T> {
	
	protected Element element;
	protected ElementData elementData;
	protected Key elementKey;

	protected EntityCoordinator(Element element) {
		super();
		
		this.element = element;
		this.elementData = EntitiesManager.getInstance().getRootData().getElementData(element.getElementTypeId());
		this.elementKey = new Key(element);
	}
	
	public abstract int update(T entity);
}
