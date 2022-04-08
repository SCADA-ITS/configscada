import { util } from "../../../lib/util.js";
import GenericModule from "../../../modules/genericModule.js";
import VmsSignalingContent from "./vmsSignalingContent.js";
import VmsSignalingTree from "./vmsSignalingTree.js";
import VmsSignalingQueue from "./vmsSignalingQueue.js";
import MessageBox from "../../../component/messageBox.js";

const TREE_PANEL_WIDTH = 325;
const QUEUE_PANEL_WIDTH = 400;

export default class VmsSignalingModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.args = args;
		this.vmsSignalingTree = undefined;
		this.vmsSignalingContent = undefined;
		this.elementType = undefined;
		this.currentElement = undefined;
		this.elements = undefined;
		this.elementsSelected = undefined;
		this.currentViewContent = undefined;		
	}

	loadData() {

		let self = this;
		let isModuleActionElements = (self.moduleAction && self.moduleAction.id === self.config.moduleAction.elements && Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0);
		let isModuleActionCommandEdit = (self.moduleAction && self.moduleAction.id === self.config.moduleAction.commandEdit);
		let multiselectAllowed = (isModuleActionCommandEdit && self.args.selectedPks && self.args.selectedPks.length > 1) ? true : undefined;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();
		}
		
		self.elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.config.vms.type);
		
		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsByType, { id: self.config.vms.type })).then(function(elementsResponse) {
			
			if (elementsResponse != null) {
						
				self.elements = elementsResponse.json();
				
				if (Array.isArray(self.elements) && self.elements.length > 0) {
					
					if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
				
						getElementFromElementId(self.args.selectedPks[0], function(element) {
						
							self.currentElement = element;
							
							load();	
						});
					}
					else {
						
						load();	
					}
				}
				else {
					
					let container = $$(self.args.containerId);
					
					if (container) {
						
						container.close();
						
						let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_NO_VMS_AVAILABLE_DESCRIPTION"), null, null);
						ms.show();
					}
				}
			}
		});
		
		function getElementFromElementId(elementId, onResponse) {
			
			if (self.currentElement && self.currentElement.id === elementId) {
			
				onResponse(self.currentElement);	
			}
			else {
				
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.element, { id: elementId })).then(function(elementResponse) {
			
					if (elementResponse) {
						
						onResponse(elementResponse.json());	
					}
				});
			}
		}
		
		function load() {
			
			let view = {};
			
			view = drawMain();
			
			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
			
			webix.ui(view, $$(self.uid));

			if (isModuleActionElements || isModuleActionCommandEdit) {

				self.elementsSelected = self.elements.filter(e => self.args.selectedPks.includes(e.id));
				
				$$("view-main-tree-hierarchy-" + self.uid).hide();
				$$("view-main-resizer-" + self.uid).hide();
			}
			
			if (!self.currentElement) {
				
				self.elements.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
				
				getElementFromElementId(self.elements[0].id, function(element) {
					
					self.currentElement = element;
				
					self.elementsSelected = [self.currentElement];
					
					if (self.vmsSignalingTree) {
				
						self.vmsSignalingTree.select(self.currentElement);
					}	
				});
			}
			else {
				
				if (self.vmsSignalingTree) {
				
					self.vmsSignalingTree.select(self.currentElement);
				}
			}
		}
		
		function drawMain() {
			
			let container = $$(self.args.containerId);

			if (container) {
				
				self.containerWidth += container.$width;
			}
			
			let viewMain = {
				id: self.uid,
				cols: [],
				on: {
						
					onAfterExpand: function(id) {
						
						let viewMainQueueInstance = $$("view-main-queue-" + self.uid);
						
						if (viewMainQueueInstance && viewMainQueueInstance.isVisible() && self.vmsSignalingQueue) {
							
							self.vmsSignalingQueue.update(self.currentElement);
						}
					}
				}
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
			
			let viewMainQueueResizer = { 
				id: "view-main-queue-resizer-" + self.uid,
				hidden: true,
				view: "resizer" 
			};

			let viewQueue = getViewQueue();
			
			let viewMainQueue = {
				
				id: "view-main-queue-" + self.uid,
				hidden: true,
				header: function() {
					
					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_SIGNALLING_PRIORITY")}</span>
							</div>`;
				},
				body: {
					width: QUEUE_PANEL_WIDTH,
					rows: [viewQueue ? viewQueue : {}]
				}
			}
			
			viewMain.cols = [viewMainTreeHierarchy, viewMainResizer, viewMainBody, viewMainQueue, viewMainLoading];

			function getViewTree() {
				
				let view = {};
				
				self.vmsSignalingTree = new VmsSignalingTree(self.elements, onSelectElement);
				
				view = self.vmsSignalingTree.getView();
				
				return view;
			}
			
			function getViewQueue() {
				
				let view;
				
				self.vmsSignalingQueue = new VmsSignalingQueue(self.config, self.elementType.priorities, QUEUE_PANEL_WIDTH);
				
				view = self.vmsSignalingQueue.getView();
				
				return view;
			}
			
			function onSelectElement(elementId) {
				
					
				$$("view-main-body-" + self.uid).hide();
				$$("view-main-loading-" + self.uid).show();
				
				$$("view-loading-panel-" + self.uid).show();
				$$("view-panel-" + self.uid).hide();
				
				$$("view-main-queue-" + self.uid).hide();
				
				if (self.vmsSignalingContent) {
					
					self.vmsSignalingContent.setPriorityOptions();	
				}

				getElementFromElementId(elementId, onResponse);	
				
				function onResponse(element) {
					
					self.currentElement = element;
				
					let previousViewContent = self.currentViewContent;
					
					webix.ui(getViewContent(), $$(previousViewContent));
					
					if (Array.isArray(self.vmsSignalingContent.signalingCmds) && self.vmsSignalingContent.signalingCmds.length > 0) {
						
						self.vmsSignalingContent.signalingCmds.forEach(signalingCmd => {
							
							signalingCmd.loadData();
						});
					}
					
					drawViewQueue();
					
					$$("view-main-loading-" + self.uid).hide();
					$$("view-main-body-" + self.uid).show();
					
					webix.delay(function() {
	
						$$("view-loading-panel-" + self.uid).hide();
						$$("view-panel-" + self.uid).show();
						
					}, null, null, 500);	
				}

			}
			
			function drawViewQueue() {
				
				let viewMainQueueVisible = Array.isArray(self.elementType.priorities) && self.elementType.priorities.length > 0;
				viewMainQueueVisible &= !isModuleActionCommandEdit;
				viewMainQueueVisible &= (self.vmsSignalingQueue != null);
				viewMainQueueVisible &= !(Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 1);
				viewMainQueueVisible &= !(self.vmsSignalingContent && Array.isArray(self.vmsSignalingContent.selectedElements) && self.vmsSignalingContent.selectedElements.length > 1)
				
				if (viewMainQueueVisible) {
					
					$$("view-main-queue-" + self.uid).show();
					self.vmsSignalingQueue.update(self.currentElement);
					
					self.vmsSignalingContent.setPriorityOptions(self.elementType.priorities);
				}
				else {
					
					$$("view-main-queue-" + self.uid).hide();
					self.vmsSignalingContent.setPriorityOptions();
				}
			}
			
			function getViewContent() {
				
				let view = {};
				
				if (self.currentElement) {
					
					let subtypeElements = self.elements.filter(e => e.elementSubtype.id === self.currentElement.elementSubtype.id);
					
					if (Array.isArray(self.elementsSelected) && self.elementsSelected.length == 1){
						
						self.elementsSelected = [self.currentElement];
					}
					
					let currentCommandElement = undefined;
									
					if (self.args.data && self.args.data.commandElement) {
						
						currentCommandElement = self.args.data.commandElement;
					}
					
					self.vmsSignalingContent = new VmsSignalingContent(self.config, self.currentElement, subtypeElements, self.elementsSelected, currentCommandElement, onUpdate, onOrderEdit, multiselectAllowed, (self.args && self.args.readOnly));
					
					view = self.vmsSignalingContent.getView();
					
					self.currentViewContent = view.id;	
				}
				
				return view;
			}
			
			function onUpdate(selectedElements){
				
				if(!isModuleActionElements && !isModuleActionCommandEdit && Array.isArray(selectedElements)) {
					
					let actualSelection = self.vmsSignalingTree.treeView.getSelected();
					
					actualSelection.forEach(actual => {
						
						let sameElement = selectedElements.find(el => el.id === actual.id_);
						
						if(!sameElement){
							
							let newElement = selectedElements.find(el => el.id != actual.id_);
							
							if(newElement){
							
								self.vmsSignalingTree.select(newElement);			
							}							
						}
						else {
							
							drawViewQueue();
						}
					});
				}
			}

			function onOrderEdit() {

				self.args.callbacks[0]();
			}

			return viewMain;
		}
	}
	
	getSignaling() {
		
		let self = this;
		let commandElement = undefined;
		
		if (self.vmsSignalingContent) {
					
			commandElement = self.vmsSignalingContent.getCommandElement();
		}
		
		return commandElement;
	}
}

