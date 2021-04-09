import GenericModule from "../../modules/genericModule.js";
import EquipmentViewerTreeHierarchy from "./equipmentViewerTreeHierarchy.js";
import EquipmentViewerContent  from "./equipmentViewerContent.js";
import { util } from "../../lib/util.js";

export default class EquipmentViewerModule extends GenericModule {
	
	static get DEFAULT_EQUIPMENT_VIEWER() { 
		
		return {
				
			showInfoTab: true,
			showChilds: false	
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
		
		function loadElementData() {
				
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length === 1) {
		
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.element, { id: self.args.selectedPks[0] })).then(function(dat) {
				
					if (dat != null) {
					
						let element = dat.json();
						
						load(element);	
					}	
				});
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
			
				if (self.moduleAction.id === self.config.moduleAction.view) {
	
					view = moduleActionView(self.element);
				}
				
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

		function moduleActionView(element) {
			
			return drawMain(element);
		}
		
		function drawMain(element) {
			
			let viewMain = {
				id: self.uid,
				cols: []
			};
			
			let viewMainTreeHierarchy = {
				id: "view-main-tree-hierarchy-" + self.uid,
				header: i18n.t("LBL_EQUIPMENT_HIERARCHY"),
				body: {
					width: 300,
					rows: [getViewTreeHierarchy(element)]
				}
			};
			
			let viewMainResizer = { 
				id: "view-main-resizer-" + self.uid,
				view: "resizer" 
			};
			
			let viewMainContent = {
				body: {
					id: "view-main-content-" + self.uid
				} // getViewContent(element)
			};
			
			let viewMainActionPanel = {
				header: i18n.t("LBL_EQUIPMENT_ACTION_PANEL"),
				body: {
					id: "view-main-action-panel-" + self.uid,
					width: 300
				} // getViewActionPanel(element)
			};
			
			let viewMainBody = {
				id: "view-main-body-" + self.uid,
				hidden: true,
				rows: [
					{
						cols: [viewMainContent, viewMainActionPanel]
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
				
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.equipmentViewer, { id: elem.elementType.id })).then(function(dat) {
				
					if (dat != null) {
						
						let equipmentViewer = dat.json();
						
						$$("view-main-body-" + self.uid).show();
						
						webix.ui(getViewContent(elem, equipmentViewer), $$("view-main-content-" + self.uid));
						webix.ui(getViewActionPanel(elem, equipmentViewer), $$("viewActionPanel-" + self.uid));
						
						if (self.equipmentViewerContent) {
								
							self.equipmentViewerContent.loadData();
						}
						
						
						$$("view-main-loading-" + self.uid).hide();
					}	
				});	
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

//				view = {
//					borderless: true,
//					rows: [
//						{height: 6},
//						{
//							cols: [
//								{width: 6},
//								self.equipmentViewerContent.getView(),		
//								{width: 6}
//							]
//						},
//						{height: 6}
//					]
//				}
				
				return view;
			}
			
			function getViewActionPanel(element, equipmentViewer) {
				
				let view = {};
				
				view = {

					id: "viewActionPanel-" + self.uid,
					width: 300,
					css: "panel-action",
					rows: [
						{}
					]
				}
				
				return view;
			}
			
			return viewMain;
		}
		
		
	}
}	
