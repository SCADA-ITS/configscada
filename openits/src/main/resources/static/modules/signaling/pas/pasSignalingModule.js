import { util } from "../../../lib/util.js";
import GenericModule from "../../genericModule.js";
import PasSignalingContent from "./pasSignalingContent.js";
import PasSignalingList from "./pasSignalingList.js";
import PasSignalingOrderEdit from "./pasSignalingOrderEdit.js";
import MessageBox from "../../../component/messageBox.js";

const LIST_PANEL_WIDTH = 300;

export default class VmsSignalingModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.args = args;
		this.pasSignalingList = undefined;
		this.pasSignalingContent = undefined;
		this.pasSignalingOrderEdit = undefined;
		this.currentElement = undefined;
		this.elements = undefined;
		this.selectedChildElementsHierarchyMap = new Map();
		this.currentViewContent = undefined;
	}

	loadData() {

		let self = this;
		
		if ((self.moduleAction && self.moduleAction.id === self.config.moduleAction.commandEdit) && (self.args.selectedPks && self.args.selectedPks.length > 1)) {
			
			$$(self.args.containerId).close();
			
			let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_MULTISELECTION_NOT_ALLOWED_IN_PAS_MODULE"), null, null);
			ms.show();
		
		} else {

			if ($$(this.uid)) {
	
				$$("loading-" + this.uid).show();
				$$(this.uid).hide();
			}
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsByTypeSummary, { id: self.config.pas.type })).then(function(elementsResponse) {	

				if (elementsResponse != null) {
	
					self.elements = elementsResponse.json();
	
					if (Array.isArray(self.elements) && self.elements.length > 0) {
	
						if (self.moduleAction && self.moduleAction.id == self.config.moduleAction.pas) {
	
							self.elements = self.elements.filter(element => self.args.selectedPks.includes(element.id));
							self.currentElement = getElementFromElementId(self.args.selectedPks[0]);
	
							load();
	
						} else if (self.moduleAction && self.moduleAction.id == self.config.moduleAction.zone) {
	
							webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.elementsHierarchy).then(function(hierarchyResponse) {
	
								if (hierarchyResponse != null) {
	
									let elementsHierarchy = hierarchyResponse.json();
	
									let pasHierarchy = elementsHierarchy.filter(element => element.parent.elementType.id == self.config.pas.type);
									pasHierarchy = pasHierarchy.filter(element => self.args.selectedPks.includes(element.child.id));
	
									pasHierarchy.forEach(element => {
	
										if (self.args.selectedPks.includes(element.child.id) && !self.selectedChildElementsHierarchyMap.get(element.parent.id)) {
	
											self.selectedChildElementsHierarchyMap.set(element.parent.id, []);
										}
	
										if (self.args.selectedPks.includes(element.child.id)) {
	
											self.selectedChildElementsHierarchyMap.get(element.parent.id).push(element.child.id);
										}
									});
	
									let elementsId = pasHierarchy.map(e => { return e.parent.id; });
									self.elements = self.elements.filter(element => elementsId.includes(element.id));
									self.currentElement = getElementFromElementId(elementsId[0]);
	
									load();
								}
							});
		
						} else {
					
							if(self.args.selectedPks){
							
								self.currentElement = getElementFromElementId(self.args.selectedPks[0]);	
							}
							
							load();					
						}
			
					} else {
	
						let container = $$(self.args.containerId);
	
						if (container) {
	
							container.close();
	
							let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_PAS_AVAILABLE_DESCRIPTION"), null, null);
							ms.show();
						}
					}
				}
			});
			
			function load() {
				
				let view = {};
				
				view = drawMain();
				
				$$("loading-" + self.uid).hide();
				$$(self.uid).show();
				
				webix.ui(view, $$(self.uid));
				
				if (!self.currentElement) {
					
					self.elements.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
					
					self.currentElement = self.elements[0];
				}
				
				if (self.pasSignalingList) {
					
					self.pasSignalingList.select(self.currentElement);
				}
			}
			
			function drawMain() {
				
				let container = $$(self.args.containerId);
	
				if (container) {
					
					self.containerWidth += container.$width;
				}
				
				let viewMain = {
					id: self.uid,
					cols: []
				};			
	
				let viewMainListHierarchy = {
					id: "view-main-list-hierarchy-" + self.uid,
					hidden: self.elements.length == 1,
					header: function() {
						
						return `<div>
									<span class='ico webix_icon mdi mdi-view-headline'></span>
									<span>${i18n.t("LBL_PAS")}</span>
								</div>`;
					},
					body: {
						width: LIST_PANEL_WIDTH,
						rows: [getViewList()]
					}
				};
						
				let viewMainResizer = { 
					id: "view-main-resizer-" + self.uid,
					hidden: self.elements.length == 1,
					view: "resizer" 
				};
				
				self.currentViewContent = "view-main-content-" + self.uid;
				
				let viewMainContent = {
					body: {
						rows: [
							{
								id: self.currentViewContent		
							}
						]
						
					}
				};
				
				let viewMainBody = {
					id: "view-main-body-" + self.uid,
					hidden: true,
					rows: [
						{
							id: "view-loading-panel-" + self.uid,
							hidden: true,
							rows: [util.ui.getViewDrawLoading(true)],
						},
						{
							id: "view-panel-" + self.uid,
							hidden: false,
							cols: [viewMainContent],
						}
					]	
				};
	
				let viewMainLoading = {
					id: "view-main-loading-" + self.uid,
					hidden: false,
					rows: [util.ui.getViewDrawLoading()],
				};
				
				viewMain.cols = [viewMainListHierarchy, viewMainResizer, viewMainBody, viewMainLoading];
				
	
				function getViewList() {
					
					let view = {};
					
					self.pasSignalingList = new PasSignalingList(self.elements, onSelectElement);
					
					view = self.pasSignalingList.getView();
					
					return view;
				}
				
				function onSelectElement(elementId) {
					
					$$("view-main-body-" + self.uid).hide();
					$$("view-main-loading-" + self.uid).show();
					
					$$("view-loading-panel-" + self.uid).show();
					$$("view-panel-" + self.uid).hide();
					
					self.currentElement = getElementFromElementId(elementId);
					
					loadContent();
	
					$$("view-main-loading-" + self.uid).hide();
					$$("view-main-body-" + self.uid).show();
	
					//TODO DEPRECATED sustituir por evento, no usar DELAY siempre que se pueda
				webix.delay(function() {
	
						$$("view-loading-panel-" + self.uid).hide();
						$$("view-panel-" + self.uid).show();
					}, null, null, 500);
				}
				
				function loadContent() {
					
					let view = {};
					
					let previousViewContent = self.currentViewContent;
	
					if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.commandEdit) {
	
						$$("view-main-list-hierarchy-" + self.uid).hide();
						$$("view-main-resizer-" + self.uid).hide();
	
						self.pasSignalingOrderEdit = new PasSignalingOrderEdit(self.config, self.currentElement, self.args);
						view = self.pasSignalingOrderEdit.getView();
						self.pasSignalingOrderEdit.loadData();
	
					} else {
	
						self.pasSignalingContent = new PasSignalingContent(self.config, self.currentElement, self.selectedChildElementsHierarchyMap.get(self.currentElement.id));
						view = self.pasSignalingContent.getView();	
						self.pasSignalingContent.loadData();			
										
					}
	
					self.currentViewContent = view.id;
					
					webix.ui(view, $$(previousViewContent));

				}
				
				return viewMain;
			}
	
			function getElementFromElementId(elementId) {
					
				return self.elements.find(e => e.id == elementId);
			}			
		}


	}
	
		getSignaling() {
		
		let self = this;
		let commandElement = undefined;
		
		if (self.pasSignalingOrderEdit) {
					
			commandElement = self.pasSignalingOrderEdit.getCommandElement();
		}
		
		return commandElement;
	}
}

