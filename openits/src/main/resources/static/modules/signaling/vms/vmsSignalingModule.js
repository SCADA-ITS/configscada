import GenericModule from "../../../modules/genericModule.js";
import VmsSignalingContent from "./vmsSignalingContent.js";
import VmsSignalingTree from "./vmsSignalingTree.js";
import { util } from "../../../lib/util.js";

const TREE_PANEL_WIDTH = 325;

export default class VmsSignalingModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.vmsSignalingTree = undefined;
		this.vmsSignalingContent = undefined;
		this.currentElement = undefined;
		this.elements = undefined;
		this.elementsSelected = undefined;
		this.currentViewContent = undefined;		
	}

	loadData() {

		let self = this;
		let isModuleActionElements = (self.moduleAction && self.moduleAction.id === self.config.moduleAction.elements && Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0); 

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();
		}
		
		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsByType, { id: self.config.vms.type })).then(function(elementsResponse) {
			
			if (elementsResponse != null) {
						
				self.elements = elementsResponse.json();
				
				if (Array.isArray(self.elements) && self.elements.length > 0) {
					
					if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
				
						self.currentElement = getElementFromElementId(self.args.selectedPks[0]); 
					}
					
					load();	
				}
				else {
					
					let container = $$(self.args.containerId);
					
					if (container) {
						
						container.close();
						
						webix.alert({
							title: i18n.t("LBL_NO_VMS_AVAILABLE"),
							text: i18n.t("LBL_NO_VMS_AVAILABLE_DESCRIPTION"),
							type: "alert-error",
							width: 500
						});
					}
				}
			}
		});
		
		function getElementFromElementId(elementId) {
				
			return self.elements.find(e => e.id === elementId);
		}
		
		function load() {
			
			let view = {};
			
			view = drawMain();
			
			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
			
			webix.ui(view, $$(self.uid));
			
			if (isModuleActionElements) {
				
				self.currentElement = self.elements.find(e => e.id === self.args.selectedPks[0]);
				self.elementsSelected = self.elements.filter(e => self.args.selectedPks.includes(e.id));
				
				$$("view-main-tree-hierarchy-" + self.uid).hide();
				$$("view-main-resizer-" + self.uid).hide();
			}
			
			if (!self.currentElement) {
				
				self.elements.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
				
				self.currentElement = self.elements[0];
				self.elementsSelected = [self.currentElement];
			}
			
			if (self.vmsSignalingTree) {
				
				self.vmsSignalingTree.select(self.currentElement);
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
			
			let viewMainTreeHierarchyHidden = (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length === 1);
			

			let viewMainTreeHierarchy = {
				id: "view-main-tree-hierarchy-" + self.uid,
				hidden: viewMainTreeHierarchyHidden,
				header: function() {
					
					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_VMS")}</span>
							</div>`;
				},
				body: {
					width: TREE_PANEL_WIDTH,
					rows: [getViewTree()]
				}
			};
					
			let viewMainResizer = { 
				id: "view-main-resizer-" + self.uid,
				hidden: viewMainTreeHierarchyHidden,
				view: "resizer" 
			};
			
			self.currentViewContent = "view-main-content-" + self.uid;
			
			let viewMainContent = {
				body: {
					rows: [
						{
							id: self.currentViewContent,
							css: {"box-sizing": "border-box", "border": "none !important"},		
						}
					]
					
				}
			};
			
			let viewMainBody = {
				id: "view-main-body-" + self.uid,
				hidden: true,
				css: {"box-sizing": "border-box"},
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
			
			viewMain.cols = [viewMainTreeHierarchy, viewMainResizer, viewMainBody, viewMainLoading];
			

			function getViewTree() {
				
				let view = {};
				
				self.vmsSignalingTree = new VmsSignalingTree(self.elements, self.currentElement, onSelectElement);
				
				view = self.vmsSignalingTree.getView();
				
				return view;
			}
			
			function onSelectElement(elementId) {
				
				$$("view-main-body-" + self.uid).hide();
				$$("view-main-loading-" + self.uid).show();
				
				$$("view-loading-panel-" + self.uid).show();
				$$("view-panel-" + self.uid).hide();
				
				self.currentElement = getElementFromElementId(elementId);
				
				let previousViewContent = self.currentViewContent;
				
				webix.ui(getViewContent(), $$(previousViewContent));
				
				$$("view-main-loading-" + self.uid).hide();
				$$("view-main-body-" + self.uid).show();
				
				
				webix.delay(function() {

					$$("view-loading-panel-" + self.uid).hide();
					$$("view-panel-" + self.uid).show();
					
				}, null, null, 500);
				
			}
			
			function getViewContent() {
				
				let view = {};
				
				if (self.currentElement) {
					
					let subtypeElements = self.elements.filter(e => e.elementSubtype.id === self.currentElement.elementSubtype.id);
					
					if (Array.isArray(self.elementsSelected) && self.elementsSelected.length == 1){
						
					  	let currentArray = []
						currentArray.push(self.currentElement);
	
						self.elementsSelected = currentArray;
					}
				
					self.vmsSignalingContent = new VmsSignalingContent(self.config, self.currentElement, subtypeElements, self.elementsSelected);
					
					view = self.vmsSignalingContent.getView();
					
					self.currentViewContent = view.id;	
				}
				
				return view;
			}
			
			return viewMain;
		}
	}
}

