import { util } from "../../lib/util.js";
import { travelTimeConfigUtils } from "./travelTimeConfigUtils.js";
import MessageBox from "../../component/messageBox.js";
import SearchDialogBox from "../../component/searchDialogBox.js";

export default class TravelTimeConfigAssignPanel {

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
			
			travelTimeConfigUtils.getTravelTime(self.config, self.travelTimeId).then((travelTime) => {
				
				self.travelTime = travelTime;
				
				addPanel();
			});
		}
		
		function addPanel() {
		
			let dialogPanel = webix.copy(SearchDialogBox.CALLBACK);				
			
			dialogPanel.fillDialogEntity = fillDialogEntity;
			dialogPanel.pkField = "id"
			dialogPanel.fields = ["alias=LBL_PANEL", "subtype=LBL_ELEMENT_SUBTYPE"];
			dialogPanel.sortFieldId = 1;
			dialogPanel.height = 600;
			dialogPanel.width = 800;
			
			function fillDialogEntity(dialogEntity, onFilledDialogEntity) {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self	.config.restEndpoints.elementsByType, { id: self.config.vms.elementTypeId}))
			
					.then(function(elementsResponse) {
						
						let elements = elementsResponse.json();
						
						if (Array.isArray(elements) && elements.length > 0) {
							
							let elementsFiltered = elements.filter(e => {
								
								let isTravelTimeEnabled = true;
							
								if (Array.isArray(e.elementValues)) {
								
									let elementValueTravelTimeEnabled = e.elementValues.find(ev => ev.elementTypeParam.id === self.config.vms.params.travelTimeEnabled);
									
									if (elementValueTravelTimeEnabled) {
									
										isTravelTimeEnabled &= IS_TRUE(elementValueTravelTimeEnabled.value);	
									}
									else {
										
										isTravelTimeEnabled &= false;
									}
								}
								else {
										
									isTravelTimeEnabled &= false;
								}
								
								isTravelTimeEnabled &= (!self.travelTime.panels || (self.travelTime.panels && self.travelTime.panels.indexOf(e.alias) === -1));
								
								return isTravelTimeEnabled;
							});
							
							dialogEntity.data = elementsFiltered.map(e => {
								
								return {
									id: e.id,
									alias: e.alias,
									subtype: e.elementSubtype ? i18n.t(e.elementSubtype.labelAlias) : ""
								}
							})
						}
						
						onFilledDialogEntity(dialogEntity);
					}
				);	
			
			}
			
			(new SearchDialogBox(i18n.t("LBL_SELECT_PANEL"), dialogPanel, onAccept)).show();
			
			function onAccept(selection) {

				if (Array.isArray(selection) && selection.length > 0) {
					
					let panel = selection[0];
					
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.element, { id: panel.id }))
						.then(elementResponse => {
						
							if (elementResponse) {
								
								let element = elementResponse.json();
								
								if (element) {
									
									let childIds = [];
									let isUpdatable = false;
								
									if (Array.isArray(element.childs) && element.childs.length > 0) {
										
										childIds = element.childs.map(e => {
											
											return e.id;
										})
										
										if (Array.isArray(childIds) && !childIds.includes(self.travelTime.id)) {
											
											childIds.push(self.travelTime.id);
											isUpdatable = true;
										}
									}
									else {
										
										childIds.push(self.travelTime.id);
										isUpdatable = true;
									}
									
									if (isUpdatable) {
										
										let bodyPanel = {
					
											element: {
												
												id: element.id,
												alias: element.alias,
												elementTypeId: element.elementType.id,
												childIds: childIds
											}
										}
										
										webix.ajax().headers(HEADER_POST).put(appConfig.restUrl + self.config.restEndpoints.updateElement, JSON.stringify(bodyPanel))
											.then(function(updatePanelResponse) {
												
												if (updatePanelResponse) {
													
													if (updatePanelResponse.json()) {
														
														let panelAlias = [];
					
														if (self.travelTime.panels) {
															
															panelAlias = self.travelTime.panels.split(",");
															
															if (!panelAlias.includes(panel.alias)) {
															
																panelAlias.push(panel.alias);	
															}
														}
														else {
															
															self.travelTime.panels = panel.alias;
															panelAlias.push(panel.alias);
														}
														
														panelAlias.sort();
														
														let bodyTravelTime = {
															
															element: {
																
																id: self.travelTime.id,
																alias: self.travelTime.alias,
																description: self.travelTime.description,
																elementTypeId: self.config.travelTime.elementTypeId,
																childIds: self.travelTime.sections.map(e => e.id),
																elementValues: [
																	{ id: util.element.getElementValueId(self.config.travelTime.params.panels, self.travelTime.id), value: panelAlias.join(",") }
																]
															}
														}
														
														webix.ajax().headers(HEADER_POST).put(appConfig.restUrl + self.config.restEndpoints.updateElement, JSON.stringify(bodyTravelTime))
															.then(function(updateItemResponse) {
																
																if (updateItemResponse) {
								
																	let updateItem = updateItemResponse.json();
																	
																	if (updateItem) {
																	
																		window.em.update(EntityType.ELEMENT, updateItem.id, updateItem);
																		
																		if (self.args.callbacks) {
				
																			self.args.callbacks[0]();
																		}
																	}
																	else {
																		
																		(new MessageBox(MessageBox.ERROR, i18n.t("LBL_ERROR_OF_PERSISTENCE"), null, null, null)).show();
																	}
																}
															}
														);
													}
													else {
										
														(new MessageBox(MessageBox.ERROR, i18n.t("LBL_ERROR_OF_PERSISTENCE"), null, null, null)).show();
													}
												}
											}
										);
									}
									else {
										
										(new MessageBox(MessageBox.ERROR, i18n.t("LBL_ERROR_PANEL_IS_ALREADY_ASSIGNED"), null, null, null)).show();
									}
								}
							}
						}
					);

				}				
			}
		}
	}
}