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
		self.currentElement = undefined;
		self.elements = undefined;
	}

	loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();
		}
		
		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsByType, { id: self.config.vms.type })).then(function(elementsResponse) {
			
			if (elementsResponse != null) {
						
				self.elements = elementsResponse.json();
				
				if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
				
					self.currentElement = getElementFromElementId(self.args.selectedPks[0]); 
				}
				
				load();
			}
		});
		
		function load() {
			
			let view = {};
			
//			let container = $$(self.args.containerId);
//
//			if (container) {
//			
//				util.ui.setWindowTitle(container, i18n.t(self.currentElement.elementType.labelDescription) + " - " + self.currentElement.alias);
//			}
			
			view = drawMain();
			
			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
			
			webix.ui(view, $$(self.uid));
			
			if (!self.currentElement) {
				
				self.currentElement = self.elements[0];
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
					}
				]	
			};

			let viewMainLoading = {
				id: "view-main-loading-" + self.uid,
				hidden: false,
				rows: [util.ui.getViewDrawNoData()],
			};
			
			viewMain.cols = [viewMainTreeHierarchy, viewMainResizer, viewMainBody, viewMainLoading];
			

			function getViewTree() {
				
				let view = {};
				
				self.vmsSignalingTree = new VmsSignalingTree(self.elements, self.currentElement, onSelectElement);
				
				view = self.vmsSignalingTree.getView();
				
				return view;
			}
			
			function getElementFromElementId(elementId) {
				
				return self.elements.find(e => e.id === elementId);
			}
			
			function onSelectElement(elementId) {
				
				$$("view-main-body-" + self.uid).hide();
				$$("view-main-loading-" + self.uid).show();
				
				self.currentElement = getElementFromElementId(elementId);
				
				$$("view-main-body-" + self.uid).show();
					
				webix.ui(getViewContent(), $$("view-main-content-" + self.uid));
				
				if (self.vmsSignalingContent) {
						
					self.vmsSignalingContent.loadData();
				}
				
				$$("view-main-loading-" + self.uid).hide();
			}
			
			function getViewContent() {
				
				let view = {};
				
				self.vmsSignalingContent = new VmsSignalingContent(self, self.currentElement);
				
				view = self.vmsSignalingContent.getView();
				
				return view;
			}
			
			return viewMain;
		}
	}
}

