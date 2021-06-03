import GenericModule from "../../../modules/genericModule.js";

const TREE_PANEL_WIDTH = 275;

export default class VmsSignalingModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.vmsSignallingTree = undefined;
		this.vmsSignallingContent = undefined;
		self.element = undefined;
	}

	loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();
		}
		
		function load(element) {
			
			let view = {};
			
			self.element = element;
				
			let container = $$(self.args.containerId);

			if (container) {
			
				util.ui.setWindowTitle(container, i18n.t(self.element.elementType.labelDescription) + " - " + element.alias);
			}
			
			view = drawMain(self.element);
			
			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
			
			webix.ui(view, $$(self.uid));
			
			if (self.element) {
				
				if (self.VmsSingallingTree) {
				
					self.VmsSingallingTree.select(self.element);
				}
			}
		}
		
		function drawMain(element) {
			
			let container = $$(self.args.containerId);

			if (container) {
				
				self.containerWidth += container.$width;
			}
			
			let viewMain = {
				id: self.uid,
				cols: []
			};
			
			let viewMainTreeHierarchyHidden = true;
			
			if (self.vmsSignaling) {
				
				viewMainTreeHierarchyHidden = (self.args.selectedPks.length === 1);
			}

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
				
				self.vmsSignallingTree = new VmsSingallingTree(self, element, onSelectElement);
				
				view = self.vmsSignallingTree.getView();
				
				return view;
			}
			
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
				
				$$("view-main-body-" + self.uid).show();
					
				webix.ui(getViewContent(elem), $$("view-main-content-" + self.uid));
				
				if (self.vmsSignallingContent) {
						
					self.vmsSignallingContent.loadData();
				}
				
				$$("view-main-loading-" + self.uid).hide();
			}
			
			function getViewContent(element) {
				
				let view = {};
				
				self.vmsSignallingContent = new VmsSinganallingContent(self, element);
				
				view = self.vmsSignallingContent.getView();
				
				return view;
			}
			
			return viewMain;
		}
	}
}

