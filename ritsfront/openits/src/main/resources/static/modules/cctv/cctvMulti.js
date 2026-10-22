import TreeView from "../../component/treeview.js";
import { util } from "../../lib/util.js";
import CamViewer from "./camviewer/camViewer.js";

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
		this.arrCamViewer = [];

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
							nodeChildId: e.child.id,
							scenarioId: e.child.scenario ? e.child.scenario.id: undefined 
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
					
					let node = self.nodes.find(e => e.scenario && e.scenario.id === self.scenarioId);
					
					if (node) {
					
						relationship = self.relationships.find(e => e.nodeChildId === node.id);
					}
				}
				
				let itemId = undefined;
				
				if (relationship) {
					
					itemId = relationship.nodeParentId + "_" + relationship.nodeChildId;
					
					$$("view-cctv-tree-" + self.uid).hide();
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
					{ 
						view: "abslayout",
						cells: [
							{
								id: "view-cctv-content-" + self.uid,
								relative: true
							},
							{
								id: "view-cctv-pager-" + self.uid
							}
						]

					}
				],
				on: {
					onAfterCollapse: function() {

						if ($$("view-cctv-content-" + self.uid)) {

							let newCells = $$("view-cctv-content-" + self.uid).getChildViews()[0].getNode().querySelector('.camviewer-matrix');

							for (let camViewer of self.arrCamViewer) {

								camViewer.w = newCells.clientWidth;
								camViewer.h = newCells.clientHeight;
								camViewer.resize(camViewer.uid);
							}


						}
					},
					onAfterExpand: function() {

						if ($$("view-cctv-content-" + self.uid)) {

							let newCells = $$("view-cctv-content-" + self.uid).getChildViews()[0].getNode().querySelector('.camviewer-matrix');

							for (let camViewer of self.arrCamViewer) {

								camViewer.w = newCells.clientWidth;
								camViewer.h = newCells.clientHeight;
								camViewer.resize(camViewer.uid);
							}

							
						}
					}
				}
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
							
							for(let camViewer of self.arrCamViewer){
																
								camViewer.stop();
							}
							
							self.arrCamViewer = [];
							
							setTimeout(function() {
			
								let node = self.nodes.find(e => e.id === item.id_);
							
								if (node && node.scenario) {
									
									loadContent(node.scenario.id);	
								}
									
							}, 200);
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
				let viewPager;
				let pages = 1;
				self.arrCamViewer = [];
				
				self.scenarioId = scenarioId;
				
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.cctvScenario, { id: self.scenarioId })).then(function(response) {
					
					let scenario;
					let position = 1;
					
					if (response) {
						
						scenario = response.json();
						
						viewContent = { 
							id: "view-cctv-content-" + self.uid,
							view: "multiview",
							keepViews:true,
							relative: true,
							animate: false,
							cells: [{}]
						}
						
						let viewContentClear = webix.copy(viewContent);
						
						if (Array.isArray(scenario.elements) && scenario.elements.length > 0) {
							
							viewContent.cells = [];
							
							pages = Math.floor(scenario.elements.length / (scenario.nRows * scenario.nColumns)) + 1;
							
							for (let page = 1; page <= pages; page++) {
								
								viewContent.cells.push(getMatrixView(page))
							}
						}
				
						// First clear
						webix.ui(viewContentClear, $$(viewContent.id));
						
						// Then put new matrix
						webix.ui(viewContent, $$(viewContent.id));
						
						loadCameras();
						
						// Put pager
						viewPager = getViewPager(scenario);
						webix.ui(viewPager, $$(viewPager.id));
					
						let container = $$(self.parent.args.containerId);
	
						if (container) {
		
							util.ui.setTitle(container.config.id, i18n.t("LBL_CCTV") + " - " + scenario.alias);
						}
					}
					
					function getMatrixView(page) {
						
						let viewMatrix = { id: "page-" + page + "-" + self.uid };
						
						viewMatrix.rows = [];
						
						for (let row = 0; row < scenario.nRows; row++) {
							
							let rowView = { cols: [] };
							
							for (let col = 0; col < scenario.nColumns; col++) {

								let cell = { 
									id: "cell-" + (position++) + "-" + self.uid,
									css: { "background-color": "#9e9e9e", "border": "2px solid", "box-sizing": "border-box" } 
								}
								
								rowView.cols.push(cell);
							}
							
							viewMatrix.rows.push(rowView);
						}
						
						return viewMatrix;
					}
					
					function loadCameras() {
						
						if (Array.isArray(scenario.elements) && scenario.elements.length > 0) {
							
							let pos = 1;
							
							scenario.elements.forEach(e => {
								
								loadCamera(e.id, pos++);
							})
						}
					}
					
					function loadCamera(elementId, pos) {
						
						let w = $$("cell-1-" + self.uid).$width;
						let h = $$("cell-1-" + self.uid).$height;						
						//let fit = (h > w) ? true: self.parent.config.multi.fit;
						let fit = self.parent.config.multi.fit;

						if ($$("cell-" + pos + "-" + self.uid)) {
							
							webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.element, { id: elementId })).then(function(elementResponse) {
			
								if (elementResponse != null) {
									
									let element = elementResponse.json();
							
									if (element) {
										
										let container;
										
										if (self.parent.args.containerId) {
											
											container = $$(self.parent.args.containerId);
										}
																		
										let camViewer = new CamViewer(self.parent.config, element, w, h, CamViewer.MODE_MULTI, fit, false, container);
							
										let viewCamViewer = camViewer.getView();
										
										self.arrCamViewer.push(camViewer);

										let newViewCamViewer = {
											id: "cell-" + pos + "-" + self.uid,
											css: { "background-color": "#9e9e9e" },
											rows: [
												viewCamViewer
											]
										};
										
										webix.ui(newViewCamViewer, $$("cell-" + pos + "-" + self.uid));
									}
								}
							});	
						}
					}
				});
				
				function getViewPager(scenario) {
					
					let numberOfCameras = scenario.elements.length;
					let numberOfCells = scenario.nColumns * scenario.nRows;
					
					let showPager = (numberOfCameras > numberOfCells) ? false : true;
					
					let viewToolbarPager = {
						id: "view-cctv-pager-" + self.uid,
						view: "toolbar",
						css: "syn-toolbar-zoom",
						borderless: true,
						top: 10, 
						left: 10,
						height: 100,
						hidden: showPager,
						cols: [
							{ width: 1 }
						]
					};
					
					let viewCboPage = {
						rows: [
							{},
							{
								css: { "background": "var(--blue-mid2-opacity-5) !important", "border-radius": "10px !important" },
								height: 35,
								cols: [
									{
										view: "button",
										type: "icon",
										icon:"mdi mdi-table-multiple",
										tooltip: i18n.t("LBL_PAGE"),
										css: "button-zoom-search",
										width: 38, height: 40,
										on: {
											onItemClick: function() {
												
												let cboPage = $$("cbo-page-" + self.uid);
												let cboPageSeparator = $$("cbo-page-sep-" + self.uid);
												
												if (cboPage && cboPageSeparator) {
													
													if (cboPage.isVisible() && cboPageSeparator.isVisible()) {
		
														cboPage.hide();
														cboPageSeparator.hide();
													}
													else {
														
														cboPage.show();
														cboPageSeparator.show();
													}
												}
											}
										}
									},
									{
										rows: [
											{},
											{
												view: "combo",
												borderless: true,
												id: "cbo-page-" + self.uid,
												hidden: true,
												width: 200, height: 30,
												value: 1,
												options: {
													view: "suggest",
													filter: function(obj, value) {
														
														return (obj.value.toLowerCase().indexOf(value.toLowerCase()) !== -1)
													},
													body: {
														view: "list",
														data: function() {
													
															let data = [];
															
															for (let page = 1; page <= pages; page++) {
								
																data.push({
																	
																	id: page,
																	value: i18n.t("LBL_PAGE") + " " + page
																});
															}
													
															return data;
														}(),
														template: function(obj) {
								
															return `<span title="${obj.value}" class="search-list-item-text">${obj.value} </span>`;
														},
		        										yCount: 7
													}
												},
												on:{
													onChange: function(newValue, oldValue, config){
												        
														let multiview = $$("view-cctv-content-" + self.uid); 
														let page = $$("page-" + newValue + "-" + self.uid);
														
														if (multiview && page) {
															
															multiview.setValue(page.config.id);
															
															for(let camViewer of self.arrCamViewer){
																
																camViewer.resize(camViewer.uid);
															}
														}
												 	}
												}
											},
											{}
										]
									},
									{ 
										id: "cbo-page-sep-" + self.uid,
										width: 10,
										hidden: true
									}
								]
							},	
							{}	
						]
					};
					
					viewToolbarPager.cols.push(viewCboPage);
					
					return viewToolbarPager;
				} 
			}
			
			return viewMain;
		}
	}
}
