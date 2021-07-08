import CCTVSingle from "./cctvSingle.js";
import TreeView from "../../component/treeview.js";
import { util } from "../../lib/util.js";
import CamViewer from "./camViewer.js";

const TREE_PANEL_WIDTH = 325;

export default class CCTVMulti {

	constructor(parent, scenarioId, onLoad) {

		this.uid = webix.uid();
		this.parent = parent;
		this.scenarioId = scenarioId;
		this.onLoad = onLoad;
		
		this.nodes = undefined;
		this.relationships = undefined;
		this.scenario = undefined;
		
		this.treeScenes = undefined; 
	}

	loadData() {
		
		let self = this;
		
		webix.ajax().get(appConfig.restUrl + self.parent.config.restEndpoints.cctvHierarchies).then(function(response) {
			
			doOnResponseHierarchies(response);
				
			self.onLoad(drawMainView(), afterRender);
		});
		
		function doOnResponseHierarchies(response) {
			
			if (response != null) {

				let hierarchies = response.json();
				
				if (Array.isArray(hierarchies) && hierarchies.length > 0) {
				
					self.relationships = hierarchies.map(e => {
						
						return {
							
							nodeParentId: e.parent.id,
							nodeChildId: e.child.id
						}
					});
					
					let nodesMap = new Map();
					
					hierarchies.forEach(e => {
						
						let node = {
							id: e.id,
							label: e.alias,
							parent: {
								id: e.parent.id,
								label: e.parent.alias,
								scenario: e.parent.scenario 
							},
							child: {
								id: e.child.id,
								label: e.child.alias,
								scenario: e.child.scenario 
							}
						}
						
						nodesMap.set(node.parent.id, node.parent);
						nodesMap.set(node.child.id, node.child);
					});
					
					self.nodes = Array.from(nodesMap.values());
				}
			}
		}
		
		function afterRender() {
			
			if (self.treeScenes && self.relationships) {
				
				let relationship = undefined;
				
				if (self.scenarioId) {
					
					relationship = self.relationships.find(e => e.nodeChildId === self.scenarioId);
				}
				
				let itemId = undefined;
				
				if (relationship) {
					
					itemId = relationship.nodeParentId + "_" + relationship.nodeChildId; 	
				}
				else {
					
					itemId = self.treeScenes.getFirstItem() ? self.treeScenes.getFirstItem().id : undefined;
				}
				
				if (itemId) {
					
					self.treeScenes.select(itemId);
				}
			}
		}
		
		function drawMainView() {
			
			let viewMain;
			
			viewMain = {
				id: self.parent.uid,
				cols: [				
					drawTree(),
					{ view: "resizer" },
					{ id: "view-cctv-content-" + self.uid }
				]
			}
			
			function drawTree() {
			
				let viewTree = {};
				
				viewTree = {
					id: "view-cctv-tree-" + self.uid,
					header: function() {
						
						return `<div>
									<span class='ico webix_icon mdi mdi-view-headline'></span>
									<span>${i18n.t("LBL_SCENES")}</span>
								</div>`;
					},
					body: {
						width: TREE_PANEL_WIDTH,
						rows: [drawViewTree()]
					}
				};
				
				function drawViewTree() {
				
					let treeView = {
						id: "view-tree-" + self.uid,
						cols: []
					};
					
					self.treeView = new TreeView(getMetadata(), getTreeData(), getEvents());
				
					function getEvents() {
				
						let events = {};
						
						events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {
							
							let node = self.nodes.find(e => e.id === item.id_);
							
							if (node && node.scenario) {
								
								loadContent(node.scenario.id);	
							}
						};
						
						return events;	
					}
					
					function getMetadata() {
				
						return {
							
							id: -1,
							label: "LBL_CCTV_SCENES",
							treeOptionValues: [
								{ value: true, treeOption: { id: 6 } }
							]
						}
					}
					
					function getTreeData() {
						
						let data = [];
						
						data = util.tree.getNodes(self.nodes, self.relationships, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);
						
						return data;
					}
					
					self.treeScenes = new TreeView(getMetadata(), getTreeData(), getEvents());
					
					treeView.cols.push(self.treeScenes.getView());
					
					return treeView;
				}
				
				return viewTree;
			}
			
			function loadContent(scenarioId) {
			
				let viewContent;
				
				self.scenarioId = scenarioId;
				
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.cctvScenario, { id: self.scenarioId })).then(function(response) {
					
					let scenario;
					
					if (response) {
						
						scenario = response.json();
						
						viewContent = { 
							id: "view-cctv-content-" + self.uid,
						}
						
						let viewContentClear = webix.copy(viewContent);
						
						makeMatrixView();
				
						// First clear
						webix.ui(viewContentClear, $$(viewContent.id));
						
						// Then put new matrix
						webix.ui(viewContent, $$(viewContent.id));
						
						loadCameras();
					}
					
					function makeMatrixView() {
						
						viewContent.rows = [];
						let position = 1;
						
						for (let row = 0; row < scenario.nRows; row++) {
							
							let rowView = { cols: [] };
							
							for (let col = 0; col < scenario.nColumns; col++) {

								let cell = { 
									id: "cell-" + (position++) + "-" + self.uid,
									css: { "background-color": "#9e9e9e", "border": "2px solid" } 
								}
								
								rowView.cols.push(cell);
							}
							
							viewContent.rows.push(rowView);
						}
					}
					
					function loadCameras() {
						
						if (Array.isArray(scenario.elements) && scenario.elements.length > 0) {
							
							let position = 1;
							
							scenario.elements.forEach(e => {
								
								loadCamera(e.id, position++);
							})
						}
					}
					
					function loadCamera(elementId, position) {
						
						if ($$("cell-" + position + "-" + self.uid)) {
							
							webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.element, { id: elementId })).then(function(elementResponse) {
			
								if (elementResponse != null) {
									
									let element = elementResponse.json();
							
									if (element) {
								
										let w = !self.parent.config.multi.fit ? $$("cell-" + position + "-" + self.uid).$width + "px" : "100%";
										let h = !self.parent.config.multi.fit ? $$("cell-" + position + "-" + self.uid).$height + "px" : "100%";
								
										let camViewer = new CamViewer(self.parent.config.camConf, element, w, h);
							
										let viewCamViewer = camViewer.getView();
										
										let viewMain = {
											id: "cell-" + position + "-" + self.uid,
											rows: [
												viewCamViewer
											]
										};
										
										webix.ui(viewMain, $$("cell-" + position + "-" + self.uid));
									}
								}
							});	
						}
					}
				});
			}
			
			return viewMain;
		}
	}
}
