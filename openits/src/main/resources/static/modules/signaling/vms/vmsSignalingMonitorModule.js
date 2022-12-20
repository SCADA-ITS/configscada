import { util } from "../../../lib/util.js";
import GenericModule from "../../genericModule.js";
import TreeView from "../../../component/treeview.js";
import GraphicIcon from "../../../component/graphicIcon/graphicIcon.js";
import MessageBox from "../../../component/messageBox.js";

const MODULE_PARAM_HIERARCHIES = 10;
const QUEUE_COLOR_ACTIVE = "#009688";
const QUEUE_COLOR_NO_ACTIVE = "#f44336";
const QUEUE_COLOR_UNAVAILABLE = "#475466";
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
		this.scrollView = undefined;
		this.elementsWithQueues = [];
		this.virtualTree = undefined;

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
			]
		}

		return view;
	}

	loadData() {

		let self = this;
		let userData = util.user.getUserData();

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsTreeByRoadHierarchies, { id: userData.id })).then(function(elementsTreeResponse) {

			if (elementsTreeResponse != null) {

				let trees = elementsTreeResponse.json();
				let elementTreeHierarchiesValues;
				let elementTreeHierarchies = self.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_HIERARCHIES);

				if (Array.isArray(trees) && trees.length > 0) {

					try {

						elementTreeHierarchiesValues = JSON.parse(elementTreeHierarchies.value);

					} catch { }

					elementTreeHierarchiesValues.forEach(value => {

						let elementTree = trees.find(tree => tree.id === "ElementTree:" + value);

						if (elementTree) {

							self.elementsTree.push(elementTree);
						}
					});
				}

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsQueuesByElementTypeId, { id: self.config.elementTypeId })).then(function(elementTypeElementsResponse) {

					if (elementTypeElementsResponse != null) {

						self.elements = elementTypeElementsResponse.json();
						self.elementsWithQueues = self.elements.filter(element => element.queue && Array.isArray(element.queue.commandElements) && element.queue.commandElements.length > 0);
						let withTreeConfig = (Array.isArray(self.elementsTree) && self.elementsTree.length > 0) ? true : false;

						if (withTreeConfig || (Array.isArray(self.elementsWithQueues) && self.elementsWithQueues.length > 0)) {

							webix.ui(drawContent(), $$(self.uid));

							if (!withTreeConfig) {

								$$("loading-switcher-" + self.uid).hide();
								self.virtualTree.select("root");
							}
						} else {

							let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_ITEMS_TO_SHOW"), null, null, null);
							ms.show();

							$$(MAIN_TAB_ID).getTabbar().removeOption(self.args.containerId);
						}
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

			if (Array.isArray(self.elementsTree) && self.elementsTree.length > 0) {

				treeEquipments.push(getSwitcherViews());
				treeEquipments.push({ height: 1, borderless: false });
				treeEquipments.push(getSwitcherButtons());

			} else {

				treeEquipments.push(getVmsTree());
			}

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

				if (Array.isArray(self.elementsTree) && self.elementsTree.length > 0) {

					for (let elTree of self.elementsTree) {

						let tree = getViewTreeEquipments(elTree);
						switcher.cols[0].rows.push(tree);
					}
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

								elementTreeNode = {
									id: element.id,
									label: element.alias,
									image: element.elementType.elementTypePhoto,
									stateColor: util.element.getColor({ id: element.elementTypeState.id }),
									stateLabel: i18n.t(element.elementTypeState.labelAlias),
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

							let idHTML = self.elementsTree[i - idx].id.replace(":", "-");

							options.push({
								id: self.elementsTree[i - idx].id + "-" + self.uid + "_" + self.treeViewsArr[i].uid,
								value: `<div id='${idHTML + "-" + self.uid + "_" + self.treeViewsArr[i].uid}' title='${i18n.t(self.elementsTree[i - idx].labelAlias)}' style='text-align: center; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
									   <span class='ico webix_icon mdi mdi-family-tree'></span>
									   <span>${i18n.t(self.elementsTree[i - idx].labelAlias)}</span>
									    </div>`
							});
						}
					}
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

															let list = $$("carousel-list-" + self.uid);
															let nextIcon = $$("next-icon-" + self.uid);
															let prevIcon = $$("previous-icon-" + self.uid);

															if (id.startsWith("ElementTree:")) {

																let idHTML = id.replace(":", "-");
																let newTreeId = id.split("_")[1];
																let treeView = self.treeViewsArr.find(e => e.uid == newTreeId);
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
															}

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

														let idHTML = options[0].id.replace(":", "-");
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

			function getVmsTree() {

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

				self.elementsWithQueues.forEach(item => {

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
						stateColor: util.element.getColor({ id: item.elementTypeState.id }),
						stateLabel: i18n.t(item.elementTypeState.labelAlias),
					});

					relationship.childNodeId = item.id;
					relationships.push(relationship);
				});

				data = util.tree.getNodes(items, relationships, { parentNodeId: "parentNodeId", childNodeId: "childNodeId" }, false);
				self.virtualTree = new TreeView(metadata, data, events);

				return self.virtualTree.getView();
			}

			return viewMain;
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
		let elementType;
		let elementsSet = new Set();
		let foldersArr = [];
		let rootSelected = false;

		$$("loadingContent-" + self.uid).show();
		$$("multi-" + self.uid).hide();

		if (item) {

			if (item.id === "root") {

				rootSelected = true;
				treeData = [];

				if (Array.isArray(self.treeViewsArr) && self.treeViewsArr.length > 0) {

					for (let treeView of self.treeViewsArr) {

						if (treeView.dataMap.has(self.treeViewsArr[0].getFirstFolder().id_)) {

							foldersArr = treeView.getChilds(item, 1);
						}
					}

				} else if (self.virtualTree && self.virtualTree.dataMap.has(self.virtualTree.getFirstFolder().id_)) {

					foldersArr = self.virtualTree.getChilds(item, 1);
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

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsQueuesByElementTypeId, { id: self.config.elementTypeId })).then(function(elementTypeElementsResponse) {

					if (elementTypeElementsResponse != null) {

						let elements = elementTypeElementsResponse.json();

						if (Array.isArray(elements) && elements.length > 0) {

							for (let dat of treeData) {

								let element = elements.find(e => e.id === dat.id_);

								if (element) {

									elementsSet.add(element);
								}
							}

							elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.config.elementTypeId);

							let newScrollContent = {
								id: "scroll-view-content-" + self.uid,
								rows: [getScrollViewContent()]
							};

							webix.ui(newScrollContent, $$("scroll-view-content-" + self.uid));
							$$("multi-" + self.uid).show();
							$$("scroll-view-content-" + self.uid).show();
							$$("loadingContent-" + self.uid).hide();
						}
					}
				});
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

					if (rootSelected) {

						for (let folder of foldersArr) {

							let elements = [];
							let idHTML = folder.id_.replace(":", "");

							folder.data.forEach(dat => {

								let el = elementsArr.find(e => e.id === dat.id_);

								if (el) {

									elements.push(el);
								}
							});

							let windowPanel = {
								rows: [
									{ height: 15 },
									{
										css: { "background-color": "white !important" },
										cols: [
											{ width: 15 },
											{
												width: 600,
												height: 650,
												header: function() {

													return `<div>
																	<span class='ico webix_icon mdi mdi-view-headline'></span>
																	<span id='${idHTML}' class="aliaspush" style='text-decoration: underline; cursor: pointer; color: var(--blue-mid2) !important;'>${i18n.t(folder.value)}</span>
																</div>`;
												},
												body: {
													view: "scrollview",
													scroll: "y",
													width: 600,
													css: { "box-sizing": "border-box" },
													body: {
														rows: getPanels(elements)
													}
												},
												onClick: {
													aliaspush: function() {

														let elementsIdsArr = [];

														elements.forEach(e => {

															elementsIdsArr.push(e.id);
														});

														let modAction = self.config.action.vmsSignalingModuleEdit;
														let module = util.module.getFromModuleAction(modAction);

														if (module) {

															let args = {
																selectedPks: elementsIdsArr
															};

															util.module.open(module, null, VIEW_TYPE_WINDOW, null, args);
														}

														return false;
													}
												}
											},
											{ width: 15 }
										]
									},
									{}
								]
							};

							windowPanelArr.push(windowPanel);
						}

					} else {

						let idHTML = item.id_.replace(":", "");

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
											height: 650,
											header: function() {

												return `<div>
															<span class='ico webix_icon mdi mdi-view-headline'></span>
															<span id=${idHTML} style='text-decoration: underline; cursor: pointer; color: var(--blue-mid2) !important;' class="aliaspush">${i18n.t(item.value)}</span>
															</div>`;
											},
											body: {
												view: "scrollview",
												scroll: "y",
												width: 600,
												css: { "box-sizing": "border-box" },
												body: {
													rows: getPanels(elementsArr)
												}
											},
											onClick: {
												aliaspush: function() {

													let elementsIdsArr = [];

													elementsArr.forEach(e => {

														elementsIdsArr.push(e.id);
													});

													let modAction = self.config.action.vmsSignalingModuleEdit;
													let module = util.module.getFromModuleAction(modAction);

													if (module) {

														let args = {
															selectedPks: elementsIdsArr
														};

														util.module.open(module, null, VIEW_TYPE_WINDOW, null, args);
													}

													return false;
												}
											}
										},
										{ width: 15 }
									],
								},
								{}
							]
						};

						windowPanelArr.push(windowPanel);
					}

					function getPanels(elements) {

						let panelsArr = [];
						let commandElement;
						let elementData = new Map();

						for (let element of elements) {

							let elementTypeCopy = webix.copy(elementType);

							if (element.queue && Array.isArray(element.queue.commandElements) && element.queue.commandElements.length > 0) {

								element.queue.commandElements.forEach(cmd => {

									let svgId = webix.uid();
									let priority = elementTypeCopy.priorities.find(e => e.id === cmd.priority.id);

									if (priority) {

										let priorityEnable = element.queue.priorities.find(e => e.id === priority.id);

										priority.enable = priorityEnable.enable;
									}

									let commandElementArr = element.queue.commandElements.filter(e => e.priority.id === priority.id);
									commandElement = commandElementArr.find(e => e.id === cmd.id);

									let validSince = util.toLocaleString(cmd.startTime / 1000);
									let validUntil = "---";

									if (cmd.endTime < MAX_EPOCH_TIME) {

										validUntil = util.toLocaleString(cmd.endTime / 1000);
									}

									elementData.set(svgId, {
										priority: priority,
										command: commandElement,
										validUntil: validUntil,
										validSince: validSince,
										element: element
									});
								});
							}
						}

						let elementDataArr = Array.from(elementData).map(e => {

							return {
								id: e[0],
								priority: e[1].priority,
								command: e[1].command,
								validUntil: e[1].validUntil,
								validSince: e[1].validSince,
								element: e[1].element
							}
						});

						for (let el of elementDataArr) {

							let panel;

							panel = {
								priority: el.priority.priority,
								enable: (el.command.enable) ? 1 : 0,
								css: { "border-top": "1px solid var(--blue-backcolor)", "border-bottom": "1px solid var(--blue-backcolor)", "box-sizing": "border-box" },
								rows: [
									{
										height: 150,
										cols: [
											{
												height: 149,
												width: 150,
												gravity: 1,
												rows: [
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
													{
														view: "label",
														label: i18n.t(el.priority.labelAlias),
														align: "center",
														css: "signaling-monitor-text-panel-priority",
													},
													{
														view: "template",
														css: { "border-top": "0px", "border-left": "0px", "border-right": "0px", "border-bottom": "0px" },
														template: function() {

															let icon = (el.command.conflict) ? "mdi mdi-alert-circle" : "";

															return `<div class="signaling-monitor-text-panel-icon">
																	<span title="${i18n.t("LBL_CONFLICT")}"class="ico webix_icon ${icon}"></span>
																	</div>`
														}
													}
												]
											},
											{
												gravity: 2,
												rows: [
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

																if (elementData.has(el.id)) {

																	let data = elementData.get(el.id);

																	drawGraphicIcon(el.id, data.element, data.command, data.priority, function(graphicIconInstance) {

																		config.graphicIconInstance = graphicIconInstance;
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
													{
														borderless: true,
														css: { "text-align": "center" },
														height: 20,
														tooltip: i18n.t("LBL_SINCE") + ": " + el.validSince + " " + i18n.t("LBL_UNTIL") + ": " + el.validUntil,
														template: function() {

															return `<div>
																	<span><p style='font-size:11px; font-style:italic; display: block; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; margin-top: 0px;'><strong>${i18n.t("LBL_SINCE")}</strong>: ${el.validSince} <strong>${i18n.t("LBL_UNTIL")}</strong>: ${el.validUntil}</p></span>
																	</div>`;
														}
													}
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

							function drawGraphicIcon(svgId, element, commandElement, priority, onAfterRender) {

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

												let color = QUEUE_COLOR_UNAVAILABLE;

												if (priority.enable) {

													color = (commandElement && commandElement.enable) ? QUEUE_COLOR_ACTIVE : QUEUE_COLOR_NO_ACTIVE;
												}

												graphicIconInstance.updateState({
													color: color
												});

												let jsonValue = [];

												if (commandElement && Array.isArray(commandElement.commandElementValues) &&
													commandElement.commandElementValues.length > 0) {

													let commandElementValue = commandElement.commandElementValues.find(e => e.commandElementTypeParam.id === self.vmsSignalingModuleConfig.commands.sendMessage.commandElementValues[0].commandElementTypeParamId);

													if (commandElementValue) {

														jsonValue = JSON.parse(commandElementValue.value);
													}

												} else {

													jsonValue = JSON.parse([{ "zone": 1, "graphics": [] }, { "zone": 2, "alternance_ms": 5000, "align": "center", "texts": [{ "id": 1, "value": "APAGAR" }, { "id": 2, "value": "APAGAR" }, { "id": 3, "value": "APAGAR" }] }]);
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

						if (Array.isArray(panelsArr) && panelsArr.length === 0) {

							panelsArr.push({ rows: [util.ui.getViewNoData()] });
						}

						panelsArr.sort((a, b) => (a.priority - b.priority) || (b.enable - a.enable));

						return panelsArr;
					}

					return windowPanelArr;
				}

				return scrollView;
			}
		}
	}
}