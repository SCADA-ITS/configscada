package com.revenga.rits.back.io.controller.data.maintenance;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.entities.lib.repository.entity.EntityOnChange;
import com.revenga.rits.back.io.controller.service.ControllerManager;
import com.revenga.rits.back.io.controller.service.RouteService;

public class ElementOnChange extends EntityOnChange {

	private RouteService routeService;

	public ElementOnChange() {
		
		super(Element.class);
		
		routeService = ControllerManager.getInstance().getControllerService().getRouteService(); 
	}

	@Override
	protected void create() {
		
		if (basicElement instanceof Element) {
			
			routeService.addElement((Element) basicElement);
		}
	}

	@Override
	protected void update() {
		
		if (basicElement instanceof Element) {
			
			routeService.updateElement((Element) basicElement);
		}
	}

	@Override
	protected void delete() {
		
		if (basicElement instanceof Element) {
			
			routeService.deleteElement((Element) basicElement);
		}
	}
}
