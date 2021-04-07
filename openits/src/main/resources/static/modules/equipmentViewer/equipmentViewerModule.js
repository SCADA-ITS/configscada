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
									rows: [getViewDrawNoData()],
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
		
		function getViewDrawNoData() {

			return {
				view: "template",
				template: function() {
	
					let icon = "<img src='img/loading.svg' align='center'>";
	
					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
				}
			}
		}

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
						
						loadEquipmentViewerData(element);	
					}	
				});
			}
		}
		
		function loadEquipmentViewerData(element) {
			
			if (element && element.elementType && element.elementType.id) {
			
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.equipmentViewer, { id: element.elementType.id })).then(function(dat) {
				
					if (dat != null) {
						
						let equipmentViewer = dat.json();
						
						load(element, equipmentViewer);
							
					}	
				});	
			}
		}
		
		function load(element, equipmentViewer) {
			
			let view = {};
			
			self.element = element;
			
			if (equipmentViewer) {
			
				self.equipmentViewer = equipmentViewer;	
			}		
		
			if (self.element) {
			
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
					
					if (self.equipmentViewerContent) {
						
						self.equipmentViewerContent.loadData();
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
				hidden: !self.equipmentViewer.showChilds,
				header: i18n.t("LBL_EQUIPMENT_HIERARCHY"),
				body: {
					width: 300,
					rows: [getViewTreeHierarchy(element)]
				}
			};
			
			let viewMainResizer = { 
				id: "view-main-resizer-" + self.uid,
				hidden: !self.equipmentViewer.showChilds,
				view: "resizer" 
			};
			
			let viewMainContent = {
				id: "view-main-content-" + self.uid,
				body: getViewContent(element)
			};
			
			let viewMainActionPanel = {
				id: "view-main-action-panel-" + self.uid,
				header: i18n.t("LBL_EQUIPMENT_ACTION_PANEL"),
				body: getViewActionPanel(element)
			};
			
			viewMain.cols = [viewMainTreeHierarchy, viewMainResizer, viewMainContent, viewMainActionPanel];
			
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
				
				webix.ui(getViewContent(getElementFromElementId(elementId)), $$("view-main-content-" + self.uid));
				webix.ui(getViewActionPanel(getElementFromElementId(elementId)), $$("viewActionPanel-" + self.uid));
			}
			
			function getViewTreeHierarchy(element) {
				
				let view = {};
				
				self.equipmentViewerTreeHierarchy = new EquipmentViewerTreeHierarchy(self, element, onSelectElement);
				
				view = self.equipmentViewerTreeHierarchy.getView();
				
				return view;
			}
			
			function getViewContent(element) {
				
				let view = {};
				
				self.equipmentViewerContent = new EquipmentViewerContent(self, element);
				
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
			
			function getViewActionPanel(element) {
				
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
