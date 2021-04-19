import GenericModule from "../../modules/genericModule.js";
import EquipmentViewerTreeHierarchy from "./equipmentViewerTreeHierarchy.js";
import EquipmentViewerContent  from "./equipmentViewerContent.js";
import { util } from "../../lib/util.js";

const TREE_PANEL_WIDTH = 275;

export default class EquipmentViewerModule extends GenericModule {
	
	static get DEFAULT_EQUIPMENT_VIEWER() { 
		
		return {
				
			showInfoTab: true,
			showChilds: true	
		} 
	} 

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;

		this.element = undefined;		
		this.equipmentViewer = EquipmentViewerModule.DEFAULT_EQUIPMENT_VIEWER;
		this.equipmentViewerTreeHierarchy = undefined;
		this.equipmentViewerContent = undefined;
		this.containerWidth = 0;
		this.firstRequest = false;
	}

	getView() {

		let view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: true
								},
								{
									id: "loading-" + this.uid,
									rows: [util.ui.getViewDrawNoData()],
									hidden: false
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};

		return view;
	}

	loadData() {

		let self = this;
		
		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		loadElementData();
		
		function adjustWindow(element) {
			
			if (self.equipmentViewer && element) {
				
				let container = $$(self.args.containerId);

				if (container) {
			
					let prevWindowWidth = container.$width;
					let newWindowWidth = prevWindowWidth; 
					
					let viewMainTreeHierarchyHidden = !self.equipmentViewer.showChilds || !(Array.isArray(element.childs) && element.childs.length > 0);
					
					newWindowWidth -= viewMainTreeHierarchyHidden ? TREE_PANEL_WIDTH : 0;
				
					util.ui.resizeWindow(self.args.containerId, newWindowWidth);
					util.ui.centerWindow(self.args.containerId);
				}
			}
		}
		
		function loadElementData() {
				
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length === 1) {
				
				if (self.moduleAction.id === self.config.moduleAction.view) {
					
					requestEquipment(self.args.selectedPks[0]);
				}
				else if (self.moduleAction.id === self.config.moduleAction.viewFromAlarm) {
	
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.alarm, { id: self.args.selectedPks[0] })).then(function(alarmResponse) {
						
						if (alarmResponse != null) {
						
							let alarm = alarmResponse.json();
							
							if (alarm) {
								
								requestEquipment(alarm.targetElement.id)
							}
						}
					});
				}
				
				function requestEquipment(elementId) {
				
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.element, { id: elementId })).then(function(elementResponse) {
				
						if (elementResponse != null) {
						
							let element = elementResponse.json();
							
							if (element) {
								
								webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.equipmentViewer, { id: element.elementType.id })).then(function(equipmentViewerResponse) {
									
									if (equipmentViewerResponse) {
									
										let equipmentViewer = equipmentViewerResponse.json();
									
										if (equipmentViewer) {
											
											self.equipmentViewer = equipmentViewer;
										}
										
										adjustWindow(element);
										
										load(element);	
									} 
								});	
							}
						}	
					});	
				}
			}
		}
		
		function load(element, equipmentViewer) {
			
			let view = {};
			
			self.element = element;
				
			if (self.element) {
				
				let container = $$(self.args.containerId);

				if (container) {
				
					util.ui.setWindowTitle(container, i18n.t(self.element.elementType.labelDescription) + " - " + element.alias);
				}
				
				view = drawMain(self.element);
				
				$$("loading-" + self.uid).hide();
				$$(self.uid).show();
				
				webix.ui(view, $$(self.uid));
				
				if (self.element) {
					
					if (self.equipmentViewerTreeHierarchy) {
					
						self.equipmentViewerTreeHierarchy.select(self.element);
					}
				}
			}
		}
	
		function calculateContainerWidth(element) {
			
			let viewMainTreeHierarchy = $$("view-main-tree-hierarchy-" + self.uid);
			let width = 0;
			let showViewMainTreeHierarchy = (Array.isArray(element.childs) && element.childs.length > 0);
			
			let container = $$(self.args.containerId);

			if (container) {
				
				width += container.$width;
			}
			
			if (viewMainTreeHierarchy && showViewMainTreeHierarchy) {
				
				width -= viewMainTreeHierarchy.$width;	
			}
			
			if (width > 0) {
			
				self.containerWidth = width;	
			}
		}
		
		function drawMain(element) {
			
			let container = $$(self.args.containerId);

			if (container) {
				
				self.containerWidth += container.$width;
			}
			
			let viewMain = {
				id: self.uid,
				cols: [],
				on: {
					onAfterCollapse() {
						
						if (self.equipmentViewerContent) {
							
							calculateContainerWidth(element);
							
							self.equipmentViewerContent.refresh();
						}
					},
					onAfterExpand() {
						
						if (self.equipmentViewerContent) {
							
							calculateContainerWidth(element);
							
							self.equipmentViewerContent.refresh();
						}
					}
				}
			};
			
			let viewMainTreeHierarchyHidden = true;
			
			if (self.equipmentViewer) {
				
				viewMainTreeHierarchyHidden = !self.equipmentViewer.showChilds || !(Array.isArray(element.childs) && element.childs.length > 0);
			}

			let viewMainTreeHierarchy = {
				id: "view-main-tree-hierarchy-" + self.uid,
				hidden: viewMainTreeHierarchyHidden,
				header: i18n.t("LBL_EQUIPMENT_HIERARCHY"),
				body: {
					width: TREE_PANEL_WIDTH,
					rows: [getViewTreeHierarchy(element)]
				}
			};
					
			if (!viewMainTreeHierarchyHidden) {
				
				self.containerWidth -= viewMainTreeHierarchy.body.width;	
			}
			
			let viewMainResizer = { 
				id: "view-main-resizer-" + self.uid,
				hidden: viewMainTreeHierarchyHidden,
				view: "resizer" 
			};
			
			let viewMainContent = {
				body: {
					id: "view-main-content-" + self.uid
				}
			};
			
			let viewMainBody = {
				id: "view-main-body-" + self.uid,
				hidden: true,
				rows: [
					{
						cols: [viewMainContent],
						on: {
							onAfterCollapse() {
								
								if (self.equipmentViewerContent) {
									
									calculateContainerWidth(element);
									
									self.equipmentViewerContent.refresh();
								}
							},
							onAfterExpand() {
								
								if (self.equipmentViewerContent) {
									
									calculateContainerWidth(element);
									
									self.equipmentViewerContent.refresh();
								}
							}
						}
					}
				]	
			};

			let viewMainLoading = {
				id: "view-main-loading-" + self.uid,
				hidden: false,
				rows: [util.ui.getViewDrawNoData()],
			};
			
			viewMain.cols = [viewMainTreeHierarchy, viewMainResizer, viewMainBody, viewMainLoading];
			
			function getElementFromElementId(elementId) {
				
				if (self.element) {
					
					if (self.element.id === elementId) {
						
						return self.element;
					}
					else {
						
						if (Array.isArray(self.element.childs) && self.element.childs.length > 0) {
							
							return self.element.childs.find(child => child.id === elementId);
						}
					}
				}
			}
			
			function onSelectElement(elementId) {
				
				$$("view-main-body-" + self.uid).hide();
				$$("view-main-loading-" + self.uid).show();
				
				let elem = getElementFromElementId(elementId);
				
				if (!self.firstRequest) {
					self.firstRequest = !self.firstRequest;
					
					doSelect(self.equipmentViewer);	
				}
				else {
					
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.equipmentViewer, { id: elem.elementType.id })).then(function(dat) {
				
						if (dat != null) {
							
							let equipmentViewer = dat.json();
							
							doSelect(equipmentViewer);
						}	
					});
				}
				
				function doSelect(equipmentViewer) {
				
					$$("view-main-body-" + self.uid).show();
					
					webix.ui(getViewContent(elem, equipmentViewer), $$("view-main-content-" + self.uid));
					
					if (self.equipmentViewerContent) {
							
						self.equipmentViewerContent.loadData();
					}
					
					
					$$("view-main-loading-" + self.uid).hide();	
				}
			}
			
			function getViewTreeHierarchy(element) {
				
				let view = {};
				
				self.equipmentViewerTreeHierarchy = new EquipmentViewerTreeHierarchy(self, element, onSelectElement);
				
				view = self.equipmentViewerTreeHierarchy.getView();
				
				return view;
			}
			
			function getViewContent(element, equipmentViewer) {
				
				let view = {};
				
				self.equipmentViewerContent = new EquipmentViewerContent(self, element, equipmentViewer);
				
				view = self.equipmentViewerContent.getView();
				
				return view;
			}
			
			return viewMain;
		}
	}
}	
