import { util } from "../../../../lib/util.js";
import { adapter } from "../../../../lib/adapter.js";
import GenericModule from "../../../genericModule.js";
import TreeView from "../../../../component/treeview.js";
import GraphicIcon from "../../../../component/graphicIcon/graphicIcon.js";

const MODULE_PARAM_CONFIG = 8;
const MAX_EPOCH_TIME = 4102444799000;

export default class VmsSignalingMonitorModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.module = module;
		this.config = module.config;
		this.moduleAction = moduleAction;
		this.args = args;

		this.elements = undefined;
		this.elementsTree = [];
		this.treeViewsArr = [];
		this.scrollViewsArr = [];
		this.dataToUpdate = [];
		this.scrollView = undefined;
		this.virtualTree = undefined;
		this.elementsMap = new Map();
		this.graphicIconInstanceMap = new Map();
		this.idStompSubscriptorElements = undefined;
		this.setTimeOut = undefined;

		this.uid = webix.uid();

		let vmsSignalingModule = window.em.getByField(EntityType.MODULE, "id", this.config.action.vmsSignalingModule);

		if (vmsSignalingModule) {

			this.vmsSignalingModuleConfig = vmsSignalingModule.config;
		}
	}

	getView() {

		let self = this;
		let view;

		view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: self.uid,
									rows: [util.ui.getViewDrawLoading()]
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			],
			on: {
				
				onDestruct: function() {

					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
					if (stompSubscriptorService && self.idStompSubscriptorElements) {
	
						stompSubscriptorService.unsubscribe(self.idStompSubscriptorElements);
					}
				}	
			}
		}

		return view;
	}

	loadData() {

		let self = this;
		let userData = util.user.getUserData();
		let elementsWithoutTree = [];

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsTreeByRoadHierarchies, { id: userData.id })).then(function(elementsTreeResponse) {

			if (elementsTreeResponse != null) {

				let trees = elementsTreeResponse.json();
				let elementTreeHierarchiesValues;
				let elementTreeHierarchies = self.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_CONFIG);

				if (Array.isArray(trees) && trees.length > 0) {

					try {

						elementTreeHierarchiesValues = JSON.parse(elementTreeHierarchies.value);

					} catch { }

					if (elementTreeHierarchiesValues && Array.isArray(elementTreeHierarchiesValues.treeCustom) && elementTreeHierarchiesValues.treeCustom.length>0) {

						elementTreeHierarchiesValues.treeCustom.forEach(value => {

							let elementTree = trees.find(tree => tree.id === "ElementTree:" + value);

							if (elementTree) {

								self.elementsTree.push(elementTree);
							}
						});
					}
				}

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsByElementTypeId, { id: self.config.elementTypeId })).then(function(elementTypeElementsResponse) {

					if (elementTypeElementsResponse != null) {

						self.elements = elementTypeElementsResponse.json();
						let elementsCopy = webix.copy(self.elements);
						let elementsWithTree = [];

						self.elementsTree.forEach(tree => {

							tree.elementTreeNodes.forEach(node => {

								if (node.element && node.element.id) {

									elementsWithTree.push(node.element.id);
								}
							});
						});

						elementsWithTree.forEach(eWithTree => {

							elementsCopy = elementsCopy.filter(e => e.id != eWithTree);
						});

						elementsWithoutTree = elementsCopy;

						for (let element of self.elements) {

							self.elementsMap.set(element.id, element);
						}

						webix.ui(drawContent(), $$(self.uid));

						connectWS();
					}
				});
			}
		});


		function drawContent() {

			let viewMain;
			let treeEquipments = [];

			treeEquipments.push({
				id: "loading-switcher-" + self.uid,
				hidden: false,
				cols: [
					util.ui.getViewDrawLoading(true)
				]
			});

			treeEquipments.push(getSwitcherViews());
			treeEquipments.push({ height: 1, borderless: false });
			treeEquipments.push(getSwitcherButtons());

			viewMain = {
				id: "view-main-" + self.uid,
				css: { "background-color": "white" },
				cols: [
					{
						id: "view-main-list-equipments-" + self.uid,
						width: 350,
						maxWidth: 400,
						header: function() {

							return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_SIGNALING_HIERARCHIES")}</span>
							</div>`;
						},
						body: {
							width: 350,
							rows: treeEquipments
						}
					},
					{
						id: "resizer-" + self.uid,
						view: "resizer"
					},
					{
						cols: [
							{
								id: "loadingContent-" + self.uid,
								rows: [util.ui.getViewDrawLoading()]
							},
							{
								id: "multi-" + self.uid,
								view: "multiview",
								hidden: true,
								keepViews: true,
								animate: false,
								cells: [
									{
										id: "equipment-content-" + self.uid
									},
									{
										id: "scroll-view-content-" + self.uid
									}
								]
							}
						]
					}
				]
			};

			function getSwitcherViews() {

				let switcher;

				switcher = {
					id: "switcher-views-" + self.uid,
					cols: [
						{
							rows: []
						}
					]
				};

				for (let elTree of self.elementsTree) {

					let tree = getViewTreeEquipments(elTree);
					switcher.cols[0].rows.push(tree);
				}

				if (Array.isArray(elementsWithoutTree) && elementsWithoutTree.length > 0) {

					let virtualTree = getVirtualTree();
					switcher.cols[0].rows.push(virtualTree);
				}

				return switcher;
			}

			function getViewTreeEquipments(elementTree) {

				let treeView = {};
				let events = {};
				let itemRelationships = [];
				let itemsTree;
				let elements = elementTree.elementTreeNodes;
				let hierarchies = elementTree.elementTreeNodeHierarchies;

				events[TreeView.EventTypes.ON_SELECT_ANY] = function(item, callbackEnable) {

					self.callbackSelectedItemTree(item);
				};

				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(item, callbackEnable) {

					self.callbackSelectedFolderTree(item);
				};

				if (Array.isArray(elements) && elements.length > 0) {
					
					itemsTree = elements.map(el => {

						let elementTreeNode = {};

						if (!el.element) {

							elementTreeNode = {
								id: el.id,
								label: el.labelAlias,
								folder: true
							};

						} else {

							let element = self.elements.find(e => e.id === el.element.id);

							if (element) {
								
								element.position = el.position;

								elementTreeNode = {
									id: element.id,
									label: element.alias,
									image: element.elementType.elementTypePhoto,
									stateColor: element.elementState ? util.element.getColorByState(element.elementState) : util.element.getColor(element.elementTypeState),
									stateLabel: element.elementState ? util.element.getAliasState(element.elementState) : i18n.t(element.elementTypeState.labelAlias)
								};

								if (hierarchies) {

									let childHierarchy = hierarchies.find(h => h.child.id === el.id);

									if (childHierarchy) {

										childHierarchy.child = element;
										childHierarchy.child.id = element.id;
									}
								}
							}
						}

						return elementTreeNode;
					});
				}

				if (Array.isArray(hierarchies) && hierarchies.length > 0) {

					itemRelationships = hierarchies.map(e => {

						return {
							id: (e.id) ? e.id : webix.uid(),
							nodeParentId: e.parent.id,
							nodeChildId: e.child.id
						}
					});
				}

				let treeData = util.tree.getNodes(itemsTree, itemRelationships, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);

				let metadata = {
					id: -1,
					label: "LBL_SIGNALING_HIERARCHIES",
					treeOptionValues: [
						{ value: false, treeOption: { id: 2 } },
						{ value: true, treeOption: { id: 3 } },
						{ value: true, treeOption: { id: 4 } },
						{ value: true, treeOption: { id: 5 } },
						{ value: true, treeOption: { id: 6 } },
						{ value: true, treeOption: { id: 8 } }
					],
					treeCommands: []
				};

				let tree = new TreeView(metadata, treeData, events);

				treeView = tree.getView();
				treeView.hidden = true;
				self.treeViewsArr.push(tree);

				return treeView;
			}

			function getSwitcherButtons() {

				let switcherButtonsView;
				let withTreeElements = undefined;
				let options = [];

				if (Array.isArray(self.elementsTree) && self.elementsTree.length > 0) {

					let idx = withTreeElements ? 1 : 0;

					for (let i = idx; i <= self.elementsTree.length; i++) {

						if (self.elementsTree[i - idx]) {

							let idHTML = self.elementsTree[i - idx].id.replaceAll(":", "-");
							let firstId = self.elementsTree[i - idx].elementTreeNodes.find(e => !e.element);

							options.push({
								id: self.elementsTree[i - idx].id + "-" + self.uid + "_" + self.treeViewsArr[i].uid,
								firstId: firstId.id,
								value: `<div id='${idHTML + "-" + self.uid + "_" + self.treeViewsArr[i].uid}' title='${i18n.t(self.elementsTree[i - idx].labelAlias)}' style='text-align: center; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
									   <span class='ico webix_icon mdi mdi-family-tree'></span>
									   <span>${i18n.t(self.elementsTree[i - idx].labelAlias)}</span>
									    </div>`
							});
						}
					}
				}

				if (Array.isArray(elementsWithoutTree) && elementsWithoutTree.length > 0) {

					let firstVirtualId = self.treeViewsArr[self.treeViewsArr.length - 1].data[0].id_;

					options.push({
						id: "virtual-" + self.uid + "_" + self.treeViewsArr[self.treeViewsArr.length - 1].uid,
						firstId: firstVirtualId,
						value: `<div id='${"virtual-" + self.uid + "_" + self.treeViewsArr[self.treeViewsArr.length - 1].uid}' title='${i18n.t("LBL_VIRTUAL")}' style='text-align: center; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
							   <span class='ico webix_icon mdi mdi-family-tree'></span>
							   <span>${i18n.t("LBL_VIRTUAL")}</span>
						    </div>`
					});
				}

				switcherButtonsView = {
					css: { "background-color": "var(--blue-soft-opacity-1)" },
					height: 50,
					cols: [
						{
							id: "previous-icon-" + self.uid,
							width: 30,
							view: "icon",
							icon: "mdi mdi-play",
							css: "carousel-button-transform carousel-button-disabled",
							disabled: true,
							on: {
								onItemClick: function() {

									let switcher = $$("switcher-" + self.uid);

									if (switcher) {

										let actualPosition = switcher.getScrollState().x;
										switcher.scrollTo(actualPosition - 100, 0);
									}
								}
							}
						},
						{
							rows: [

								{
									id: "switcher-" + self.uid,
									view: "scrollview",
									css: "equipment-carousel",
									height: 41,
									scroll: "x",
									layout: "x",
									borderless: true,
									body: {
										cols: [
											{
												id: "carousel-list-" + self.uid,
												view: "list",
												css: "carousel-list",
												data: options,
												layout: "x",
												select: true,
												type: {
													width: 175
												},
												on: {
													onItemClick: function(id) {

														const VIEWS = {
															EQUIPMENT: 0,
															SCROLLVIEW: 1
														};

														let HTMLelement;

														$$("loading-switcher-" + self.uid).show();
														$$("switcher-views-" + self.uid).hide();

														webix.delay(function() {

															let contentViews = $$("multi-" + self.uid).getChildViews();

															$$("switcher-views-" + self.uid).show();
															$$("loading-switcher-" + self.uid).hide();

															let treeView;
															let newTreeId;
															let list = $$("carousel-list-" + self.uid);
															let nextIcon = $$("next-icon-" + self.uid);
															let prevIcon = $$("previous-icon-" + self.uid);
															let idHTML = id.replaceAll(":", "-");

															newTreeId = id.split("_")[1];
															treeView = self.treeViewsArr.find(e => e.uid == newTreeId);
															HTMLelement = $$("carousel-list-" + self.uid).getNode().querySelector('#' + idHTML);

															for (let treeIds of self.treeViewsArr) {

																$$("treeview-" + treeIds.uid).hide();
															}

															$$("treeview-" + newTreeId).show();
															$$("resizer-" + self.uid).disable();
															$$("view-main-list-equipments-" + self.uid).refresh();

															let itemSelected = treeView.getSelected();
															let element = undefined;

															if (itemSelected[0] && itemSelected[0].id_) {

																element = window.em.getByField(EntityType.ELEMENT, "id", itemSelected[0].id_);
															}

															if (Array.isArray(itemSelected) && itemSelected.length > 0 && element != undefined) {

																treeView.unselectAll();
																treeView.select(itemSelected[0].id);
																contentViews[VIEWS.EQUIPMENT].show();
																util.ui.setTitle(self.args.containerId, i18n.t(itemSelected[0].value), null);

															} else {

																treeView.unselect("root");
																treeView.select("root");
															}

															contentViews[VIEWS.SCROLLVIEW].hide();
															HTMLelement.scrollIntoView({ inline: "center", block: "end", behavior: "smooth" });

															if (nextIcon && prevIcon) {

																let firstItemId = list.getFirstId();
																let lastItemId = list.getLastId();
																let selectedItemId = list.getSelectedItem();

																if (selectedItemId.id === firstItemId) {

																	prevIcon.disable();
																	webix.html.addCss(prevIcon.$view, "carousel-button-disabled");
																	nextIcon.enable();
																	webix.html.removeCss(nextIcon.$view, "carousel-button-disabled");

																} else if (selectedItemId.id === lastItemId) {

																	nextIcon.disable();
																	webix.html.addCss(nextIcon.$view, "carousel-button-disabled");
																	prevIcon.enable();
																	webix.html.removeCss(prevIcon.$view, "carousel-button-disabled");

																} else {

																	nextIcon.enable();
																	prevIcon.enable();
																	webix.html.removeCss(prevIcon.$view, "carousel-button-disabled");
																	webix.html.removeCss(nextIcon.$view, "carousel-button-disabled");
																}
															}
														});
													},
													onAfterRender: function() {

														let idHTML = options[0].id.replaceAll(":", "-");
														let HTMLelement = $$("carousel-list-" + self.uid).getNode().querySelector('#' + idHTML);
														webix.html.triggerEvent(HTMLelement, "MouseEvent", "click");
													}
												}
											}
										]
									}
								}
							]
						},
						{
							id: "next-icon-" + self.uid,
							width: 30,
							view: "icon",
							icon: "mdi mdi-play",
							on: {
								onItemClick: function() {

									let switcher = $$("switcher-" + self.uid);

									if (switcher) {

										let actualPosition = switcher.getScrollState().x;
										switcher.scrollTo(actualPosition + 50, 0);
									}
								}
							}
						}
					]
				};

				return switcherButtonsView;
			}

			function getVirtualTree() {

				let treeView = {};
				let events = {};

				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {

					self.callbackSelectedItemTree([item]);
				};

				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(item) {

					self.callbackSelectedFolderTree(item);
				};

				let metadata = {
					id: -1,
					label: "LBL_EQUIPMENT_HIERARCHY",
					treeOptionValues: [
						{ value: false, treeOption: { id: 2 } },
						{ value: true, treeOption: { id: 3 } },
						{ value: true, treeOption: { id: 4 } },
						{ value: true, treeOption: { id: 5 } },
						{ value: true, treeOption: { id: 6 } },
						{ value: true, treeOption: { id: 8 } }
					]
				};

				let data = [];
				let items = [];
				let relationships = [];

				elementsWithoutTree.forEach(item => {

					let relationship = {};
					let elementSubType = items.find(e => e.id === item.elementSubtype.id);

					if (elementSubType) {

						relationship.parentNodeId = elementSubType.id;

					} else {

						items.push({
							id: item.elementSubtype.id,
							label: i18n.t(item.elementSubtype.labelAlias)
						});

						relationship.parentNodeId = item.elementSubtype.id;
					}

					items.push({
						id: item.id,
						label: item.alias,
						image: item.elementType.elementTypePhoto,
						stateColor: item.elementState ? util.element.getColorByState(item.elementState) : util.element.getColor(item.elementTypeState),
						stateLabel: item.elementState ? util.element.getAliasState(item.elementState) : i18n.t(item.elementTypeState.labelAlias)
					});

					relationship.childNodeId = item.id;
					relationships.push(relationship);
				});

				data = util.tree.getNodes(items, relationships, { parentNodeId: "parentNodeId", childNodeId: "childNodeId" }, false);

				self.virtualTree = new TreeView(metadata, data, events);

				treeView = self.virtualTree.getView();
				treeView.hidden = true;
				self.treeViewsArr.push(self.virtualTree);

				return treeView;
			}

			return viewMain;
		}
		
		function connectWS() {
			
			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {
				
				let topic = self.config.topic.element + ".>";
				
				let fields = [{
					target: "id",
					values: self.elements.map(e => e.id)
				}];
				
				self.idStompSubscriptorElements = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessage, fields);
			}
			
			function onMessage(data) {
				
				let elementInMap;
				
				if (data && data.id) {
					
					if (self.elementsMap && self.elementsMap.has(data.id)) {
					
						elementInMap = self.elementsMap.get(data.id);
						
						if (elementInMap) {
							
							updateGraphicIcon();
						
							updateTree();	
						}
					}
				}
				function updateGraphicIcon() {
						
					let elementTypeParams = [];
					let elementAux = self.elementsMap.get(elementInMap.id);
					
					if (elementAux && Array.isArray(elementAux.elementValues) && elementAux.elementValues.length > 0) {
						
						elementTypeParams = elementAux.elementValues.map(elementValue => elementValue.elementTypeParam);
					}
					
					let element = adapter.ws.element.get(data, elementTypeParams);
				
					if (element) {
						
						let graphicIcon = self.graphicIconInstanceMap.get(elementInMap.id);
						
						if (element.elementState) {
									
							if (graphicIcon) {
								
								graphicIcon.updateElementState(element.elementState);	
							}
							
							elementInMap.elementState = element.elementState;
						}
						
						if (Array.isArray(element.elementValues) && element.elementValues.length > 0) {
							
							if (graphicIcon) {
								
								graphicIcon.updateValues(element.elementValues);
							}
							
							element.elementValues.forEach(ev => {
							
								let elementValue = elementInMap.elementValues.find(e => e.id === ev.id);
								
								if (elementValue) {
									
									elementValue.value = ev.value;
								}	
							});
						}
					}
				}
				
				function updateTree() {
					
					if (Array.isArray(self.treeViewsArr)) {
						
						for (let treeView of self.treeViewsArr) {

							let items = treeView.getItemsById_(elementInMap.id);
	
							if (Array.isArray(items) && items.length > 0) {
	
								items.forEach(item => {
	
									let newColor = util.element.getColorByState(elementInMap.elementState);
									let newLabel = util.element.getAliasState(elementInMap.elementState);
									
									if (newColor != item.props.stateColor || newLabel != item.props.stateLabel) {
	
										let newItem = webix.copy(item);
	
										newItem.props.stateColor = newColor;
										newItem.props.stateLabel = newLabel;
	
										treeView.update(newItem.id, newItem);
									}
								});
							}
						}	
					}
					
				}
			}
		}
	}

	callbackSelectedItemTree(item) {

		let self = this;
		let elementId = undefined;
		let modAction = self.config.action.vmsSignalingModuleElements;
		let equipmentSelected = [];

		$$("loadingContent-" + self.uid).show();
		$$("multi-" + self.uid).hide();

		if (Array.isArray(item) && item.length > 0 && item[0].id_) {

			if (item.length === 1) {

				elementId = (item[0].aux && item[0].aux.elementId) ? item[0].aux.elementId : item[0].id_;
				launchModule();
			}

		} else {

			let viewNoData = {
				id: "equipment-content-" + self.uid,
				css: { "box-sizing": "border-box" },
				cols: [util.ui.getViewNoData()]
			};

			webix.ui(viewNoData, $$("equipment-content-" + self.uid));
			$$("multi-" + self.uid).show();
			$$("equipment-content-" + self.uid).show();
			$$("loadingContent-" + self.uid).hide();
		}

		function launchModule() {

			equipmentSelected.push(elementId);
			let mod = util.module.getFromModuleAction(modAction);

			if (mod) {

				let containerWidth = $$("loadingContent-" + self.uid).$width;
				let moduleAction = { id: modAction };

				let args = {
					selectedPks: equipmentSelected,
					containerId: self.args.containerId,
					containerWidth: containerWidth
				};

				util.module.open(mod, moduleAction, VIEW_TYPE_CUSTOM, drawContent, args);

				function drawContent(view) {

					let equipmentViewer = {
						id: "equipment-content-" + self.uid,
						css: { "box-sizing": "border-box" },
						cols: [view]
					};

					webix.ui(equipmentViewer, $$("equipment-content-" + self.uid));

					$$("multi-" + self.uid).show();
					$$("equipment-content-" + self.uid).show();
					$$("loadingContent-" + self.uid).hide();
				}
			}
		}
	}

	callbackSelectedFolderTree(item) {

		let self = this;
		let treeData;
		let elementsSet = new Set();
		let foldersArr = [];
		let rootSelected = false;

		if (item) {

			if (item.id === "root") {

				rootSelected = true;
				treeData = [];

				if (Array.isArray(self.treeViewsArr) && self.treeViewsArr.length > 0) {

					let itemListSelected = $$("carousel-list-" + self.uid).getSelectedItem();

					for (let treeView of self.treeViewsArr) {

						if (treeView.dataMap.has(itemListSelected.firstId)) {

							foldersArr = treeView.getChilds(item, 1);
						}
					}
				}

				getData(foldersArr);

				function getData(foldersArr) {

					for (let element of foldersArr) {

						if (!element.props.folder) {

							treeData.push(element);

						} else {

							getData(element.data);
						}
					}
				}

			} else {

				if (Array.isArray(self.treeViewsArr) && self.treeViewsArr.length > 0) {

					for (let i = 0; !treeData && i < self.treeViewsArr.length; i++) {

						let treeView = self.treeViewsArr[i].data.find(tree => tree.id === item.id);

						if (treeView) {

							treeData = self.treeViewsArr[i].getChilds(item, 2);
						}
					}

				} else if (self.virtualTree) {

					let treeView = self.virtualTree.data.find(tree => tree.id === item.id);

					if (treeView) {

						treeData = self.virtualTree.getChilds(item, 2);
					}
				}
			}

			if (Array.isArray(treeData) && treeData.length > 0) {

				for (let dat of treeData) {

					if (self.elementsMap.has(dat.id_)) {

						let element = self.elementsMap.get(dat.id_);
						elementsSet.add(element);
					}
				}

				let newScrollContent = {
					id: "scroll-view-content-" + self.uid,
					rows: [getScrollViewContent()]
				};

				webix.ui(newScrollContent, $$("scroll-view-content-" + self.uid));
				$$("multi-" + self.uid).show();
				$$("scroll-view-content-" + self.uid).show();
				$$("loadingContent-" + self.uid).hide();
			}

			function getScrollViewContent() {

				let scrollView;

				scrollView = {
					view: "scrollview",
					scroll: "x",
					css: { "box-sizing": "border-box" },
					body: {
						cols:
							getWindowPanels()
					}
				};

				function getWindowPanels() {

					let windowPanelArr = [];
					let elementsArr = Array.from(elementsSet);
					
					elementsArr.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
					
					self.graphicIconInstanceMap.clear();

					if (rootSelected) {

						for (let folder of foldersArr) {

							let elements = [];
							let idHTML = folder.id_.replaceAll(":", "");

							folder.data.forEach(dat => {

								if (self.elementsMap.has(dat.id_)) {

									let el = self.elementsMap.get(dat.id_);
									elements.push(el);
								}
							});
							
							elements.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

							let windowPanel = {
								rows: [
									{ height: 15 },
									{
										css: { "background-color": "white !important" },
										cols: [
											{ width: 15 },
											{
												//width: 600,
												//height: 650,
												header: function() {

													return `<div>
																	<span class='ico webix_icon mdi mdi-view-headline'></span>
																	<span id='${idHTML}'>${folder.value}</span>
																</div>`;
												},
												body: {
													view: "scrollview",
													scroll: "y",
													width: 600,
													css: { "box-sizing": "border-box" },
													body: {
														id: "scrollview-" + idHTML + "-" + self.uid,
														rows: getPanels(elements)
													}
												},
												on: {
													onItemClick: function() {

														if (this.config.collapsed) {

															let childs = $$("scrollview-" + idHTML + "-" + self.uid).getChildViews();

															childs.forEach(child => {

																child.reconstruct();
															});
														}
													}
												}
											},
											{ width: 15 }
										]
									},
									{ height: 15 }
								]
							};

							windowPanelArr.push(windowPanel);
						}

					} else {

						let idHTML = item.id_.replaceAll(":", "");

						let windowPanel = {
							rows: [
								{ height: 15 },
								{
									css: { "background-color": "white !important", "box-sizing": "border-box" },
									cols: [
										{ width: 15 },
										{
											id: "window-panel-" + idHTML + "-" + self.uid,
											width: 600,
											header: function() {

												return `<div>
															<span class='ico webix_icon mdi mdi-view-headline'></span>
															<span id='${idHTML}'>${item.value}</span>
															</div>`;
											},
											body: {
												view: "scrollview",
												scroll: "y",
												width: 600,
												css: { "box-sizing": "border-box" },
												body: {
													id: "scrollview-" + idHTML + "-" + self.uid,
													rows: getPanels(elementsArr)
												}
											},
											on: {
												onItemClick: function() {

													if (this.config.collapsed) {

														let childs = $$("scrollview-" + idHTML + "-" + self.uid).getChildViews();

														childs.forEach(child => {

															child.reconstruct();
														});
													}
												}
											}
										},
										{ width: 15 }
									],
								},
								{ height: 15 }
							]
						};

						windowPanelArr.push(windowPanel);
					}

					return windowPanelArr;
				}

				function getPanels(elements) {

					let panelsArr = [];
					let elementDataArr = self.getDataPanels(elements);

					for (let el of elementDataArr) {

						let panel;

						panel = {
							id: "panel-" + self.uid + "-" + el.id,
							location: parseFloat(el.element.location.alias),
							css: { "border-top": "1px solid var(--blue-backcolor)", "border-bottom": "1px solid var(--blue-backcolor)", "box-sizing": "border-box" },
							rows: [
								{
									height: 150,
									cols: [
										{
											//height: 149,
											width: 150,
											gravity: 1,
											rows: [
												{},
												{
													view: "label",
													label: el.element.alias,
													align: "center",
													css: "signaling-monitor-text-panel-alias",
													on: {
														onItemClick: function() {

															let modAction = self.config.action.vmsSignalingModuleEdit;
															let module = util.module.getFromModuleAction(modAction);

															if (module) {

																let args = {
																	selectedPks: [el.element.id]
																};

																util.module.open(module, null, VIEW_TYPE_WINDOW, null, args);
															}
														}
													}
												},
												{
													view: "label",
													label: el.element.location.alias,
													align: "center",
													css: "signaling-monitor-text-panel-pk",
												},
												{}
											]
										},
										{
											gravity: 2,
											rows: [
												{},
												{
													id: "svg-container-" + self.uid + "-" + el.id,
													height: 125,
													css: { "box-sizing": "border-box", "border-width": "inherit !important" },
													template: function() {

														return `<div>
																<svg id=${"vms-composer-svg-" + el.id} style='padding-left:20px;'></svg>
																</div>`;
													},
													on: {
														onAfterRender: function() {

															let config = this.config;
															let data = elementDataArr.find(e => e.id === el.id);

															if (data) {

																drawGraphicIcon(el.id, data.element, data.command, function(graphicIconInstance) {

																	config.graphicIconInstance = graphicIconInstance;

																	if (self.elementsMap.has(data.element.id)) {

																		self.graphicIconInstanceMap.set(data.element.id, graphicIconInstance);
																	}
																});
															}
														},
														onDestruct: function() {

															if (this.config.graphicIconInstance) {

																this.config.graphicIconInstance.stop();
															}
														}
													}
												},
												{}
											]
										}
									]
								},
								{
									height: 2,
									css: { "background-color": "var(--blue-backcolor)" }
								}
							]
						};

						panelsArr.push(panel);

						function drawGraphicIcon(svgId, element, commandElement, onAfterRender) {

							let property = self.vmsSignalingModuleConfig.properties.find(e => e.id === element.elementSubtype.id);
							let graphicIcon = undefined;
							let svgContainer = $$("svg-container-" + self.uid + "-" + svgId);

							if ((graphicIcon = property.graphicIcon) != null) {

								graphicIcon.width = svgContainer.$width * 0.9;
								graphicIcon.height = svgContainer.$height * 0.9;

								const lazy = async _ => {

									let jsFile = util.getContextPath() + '/' + graphicIcon.jsDrawFile;

									const mod = await import(jsFile);

									let layerElement = {
										graphicIcon: graphicIcon
									}

									let s = Snap("#" + "vms-composer-svg-" + svgId);
									s.attr({
										width: svgContainer.$width * 0.9,
										height: svgContainer.$height * 0.9
									});

									let container = {
										type: GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW,
										obj: s
									}

									let graphicIconInstance = new mod.default(layerElement, element, container);

									graphicIconInstance.load(function() {

										graphicIconInstance.loadRender(function() {
											
											graphicIconInstance.updateElementState(element.elementState);

											let jsonValue = [];

											if (commandElement && commandElement.value) {

												jsonValue = JSON.parse(commandElement.value);

											} else {

												try {

													jsonValue = JSON.parse([{ "zone": 1, "graphics": [] }, { "zone": 2, "alternance_ms": 5000, "align": "center", "texts": [{ "id": 1, "value": "APAGAR" }, { "id": 2, "value": "APAGAR" }, { "id": 3, "value": "APAGAR" }] }]);

												} catch (error) { }

											}

											let elementValueJsonData = {

												elementTypeParam: {
													id: self.vmsSignalingModuleConfig.vms.jsonData
												},
												value: JSON.stringify(jsonValue)
											}

											graphicIconInstance.updateValues([elementValueJsonData]);

											if (onAfterRender) {

												onAfterRender(graphicIconInstance);
											}
										});
									});
								}

								lazy();
							}
						}
					}

					return panelsArr;
				}

				return scrollView;
			}
		}
	}

	getDataPanels(elements) {

		let self = this;
		let elementData = new Map();

		for (let element of elements) {

			let commandElement;
			let svgId = element.id.replaceAll(":", "") + self.args.containerId;

			
			commandElement = element.elementValues.find(e=> e.elementTypeParam.id === self.vmsSignalingModuleConfig.vms.jsonData);
			
			elementData.set(svgId, {
				command: commandElement,
				element: element
			});
		}

		let elementDataArr = Array.from(elementData).map(e => {

			return {
				id: e[0],
				command: e[1].command,
				element: e[1].element,
				elementId: e[1].element.id
			}
		});

		return elementDataArr;
	}
}