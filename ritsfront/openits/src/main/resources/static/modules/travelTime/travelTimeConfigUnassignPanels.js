import { util } from "../../lib/util.js";
import MessageBox from "../../component/messageBox.js";

export default class TravelTimeConfigUnassignPanels {

	constructor(config, args, travelTimeId) {

		this.config = config;
		this.args = args;
		this.travelTimeId = travelTimeId;
		 
		this.travelTime = {
			id: undefined,
			alias: undefined,
			description: undefined,
			descendingDirection: false,
			pkIni: undefined,
			pkEnd: undefined,
			message: undefined,
			panels: undefined,
			sections: []
		}
	}
	
	load() {
			
		let self = this;
		
		if (self.travelTimeId) {
			
			let message = i18n.t("LBL_QUESTION_DELETE_ASSOCIATED_PANELS"); 
					
			(new MessageBox(MessageBox.CONFIRM, message, null, onAccept, null)).show();
		}
		
		function onAccept() {
				
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementWithParents, { id: self.travelTimeId }))
				.then(elementResponse => {
				
					if (elementResponse) {
						
						let element = elementResponse.json();
						
						if (Array.isArray(element.parents) && element.parents.length > 0) {
							
							let panelsParents = element.parents.filter(e => e.elementType.id === self.config.vms.elementTypeId);
							
							if (Array.isArray(panelsParents) && panelsParents.length > 0) {
							
								deletePanels(element, panelsParents.map(e => {return e.id}));	
							}
						}
					}
				}
			);
		}
		
		function deletePanels(travelTime, panelIds) {
			
			let updatedPanels = 0;
			
			panelIds.forEach(panelId => {
				
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.element, { id: panelId }))
					.then(elementResponse => {
					
						if (elementResponse) {
							
							let element = elementResponse.json();
							let childIds = [];
							
							if (Array.isArray(element.childs)) {
								
								element.childs = element.childs.filter(e => e.id != travelTime.id);
								
								if (Array.isArray(element.childs) && element.childs.length > 0) {
								
									childIds = element.childs.map(e => { return e.id });
								}
							}
							
							let bodyPanel = {
			
								element: {
									
									id: element.id,
									alias: element.alias,
									elementTypeId: element.elementType.id,
									childIds: childIds
								}
							}
							
							webix.ajax().headers(HEADER_POST).put(appConfig.restUrl + self.config.restEndpoints.updateElement, JSON.stringify(bodyPanel))
								.then(function(updatedPanelResponse) {
									
									if (updatedPanelResponse && updatedPanelResponse.json()) {
									
										if (panelIds.length === ++updatedPanels) {
										
											let bodyTravelTime = {
							
												element: {
													
													id: travelTime.id,
													alias: travelTime.alias,
													description: travelTime.description,
													elementTypeId: travelTime.elementType.id,
													elementValues: [
														{ id: util.element.getElementValueId(self.config.travelTime.params.panels, travelTime.id), value: "" }
													]
												}
											}
											
											webix.ajax().headers(HEADER_POST).put(appConfig.restUrl + self.config.restEndpoints.updateElement, JSON.stringify(bodyTravelTime))
												.then(function(updateItemResponse) {
													
													if (updateItemResponse) {
					
														let updateItem = updateItemResponse.json();
														
														if (updateItem) {
															
															window.em.update(EntityType.ELEMENT, updateItem.id, updateItem);	
														}
														else {
															
															(new MessageBox(MessageBox.ERROR, i18n.t("LBL_ERROR_OF_PERSISTENCE"), null, null, null)).show();
														}
														
														if (self.args.callbacks) {
		
															self.args.callbacks[0]();
														}	
													}
												}
											);
										}	
									}
									else {
										
										(new MessageBox(MessageBox.ERROR, i18n.t("LBL_ERROR_OF_PERSISTENCE"), null, null, null)).show();
									}
								}
							);
						}
					}
				);
			});
		}
	}
}