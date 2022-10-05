import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";
import TreeView from "../component/treeview.js";
import OptionDialogBox from "../component/optionDialogBox.js";

const MODULE_TYPE_GENERIC_MULTI_GRID_PARAM_ENTITY_TYPE = 7;
const TOPIC_ELEMENTS = "/topic/element.>";

export default class EquipmentModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.dataTable = undefined;
		this.listView = undefined;
		this.treeView = undefined;
		this.elementHierarchies = undefined;
		this.idStompSubscriptor = undefined;
		this.treeCustom = undefined;
		this.idStompSubscriptor = undefined;
		this.itemsTree = [];
		this.elements = [];
		this.treeViewsArr = [];
	}

	loadData() {

		let self = this;
		let entity = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_TYPE_GENERIC_MULTI_GRID_PARAM_ENTITY_TYPE).value;
		let jsonEquipmentTypesWithGridAssigned = window.em.getAll(EntityType[entity]);
		let jsonEquipmentTypesWithGridAssignedCopy = undefined;

		jsonEquipmentTypesWithGridAssigned = jsonEquipmentTypesWithGridAssigned.filter(e => e.visible === true && e.grid != undefined && e.grid !== "");

		jsonEquipmentTypesWithGridAssignedCopy = webix.copy(jsonEquipmentTypesWithGridAssigned);

		jsonEquipmentTypesWithGridAssignedCopy.forEach(item => {

			item.labelAlias = i18n.t(item.labelAlias);
			item.alias = item.labelAlias;
			item.labelAlias = i18n.t(item.labelAlias);
			item.labelDescription = i18n.t(item.labelDescription);
			item.description = item.labelDescription;
		});

		jsonEquipmentTypesWithGridAssignedCopy.sort((a, b) => (a.labelAlias > b.labelAlias) ? 1 : ((b.labelAlias > a.labelAlias) ? -1 : 0));

		if (self.config.treeElements || self.config.treeCustom) {

			let promises = [];
			let onlyTreeElements = undefined;

			if (self.config.treeElements) {

				promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.hierarchy));
				onlyTreeElements = true;
			}

			if (self.config.treeCustom) {

				let userData = util.user.getUserData();
				promises.push(webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsTreeByUser, { id: userData.id })));
			}


			webix.promise.all(promises).then(function(responses) {

				if (onlyTreeElements) {

					self.elementHierarchies = (responses[0] != null) && responses[0].json() ? responses[0].json() : [];
					self.treeCustom = (responses[1] != null) && responses[1].json() ? responses[1].json() : [];

				} else {

					self.treeCustom = (responses[0] != null) && responses[0].json() ? responses[0].json() : [];
				}

				loadElements();
			});

		} else {

			loadElements();
		}

		function loadElements() {

			webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.elements, JSON.stringify([])).then(function(elementsResponse) {

				if (elementsResponse != null) {

					self.elements = elementsResponse.json();

					webix.ui(drawContent(), $$(self.uid));

					$$("loading-switcher-" + self.uid).hide();

					$$(self.uid).show();
					$$("loading-" + self.uid).hide();

					let listEquipmentTypes = $$("listview-" + self.listView.uid);

					if (!webix.isUndefined(listEquipmentTypes)) {

						listEquipmentTypes.attachEvent("onAfterSelect", function(id) {

							webix.extend($$(self.uid), webix.ProgressBar);

							$$(self.uid).showProgress({
								type: "bottom",
								hide: false
							});

							webix.delay(function() {

								self.callbackSelectedElemenTypeList(id[0]);
							}, null, null, 1);
						});

						self.listView.select(self.listView.data[0].id);
						connectWS();
					}
				}
			});
		}

		function drawContent() {

			let viewMain;
			let rowsviewMainListEquipments = [];

			rowsviewMainListEquipments.push({
				id: "loading-switcher-" + self.uid,
				hidden: false,
				cols: [
					util.ui.getViewDrawLoading(true)
				]
			});

			if (self.elementHierarchies || self.treeCustom) {

				rowsviewMainListEquipments.push(getSwitcherViews());
				rowsviewMainListEquipments.push({ height: 1, borderless: false });
				rowsviewMainListEquipments.push(getSwitcherButtons());

			} else {

				if ($$("switcher-views-" + self.uid)) {
					
					$$("switcher-views-" + self.uid).hide();
				}
				
				rowsviewMainListEquipments.push(getViewListEquipments());
			}

			viewMain = {
				id: self.uid,
				cols: [
					{
						id: "view-main-list-equipments-" + self.uid,
						width: 350,
						maxWidth: 400,
						header: function() {

							return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
							</div>`;
						},
						body: {
							width: 350,
							rows: rowsviewMainListEquipments
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
										id: "table-content-" + self.uid
									},
									{
										id: "equipment-content-" + self.uid
									}
								]
							}
						]
					}
				],
				on: {
					onAfterExpand: function() {

						onExpandCollapse();
					},
					onAfterCollapse: function() {

						onExpandCollapse();
					},
					onDestruct: function() {

						let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

						if (stompSubscriptorService) {

							if (self.idStompSubscriptor) {

								stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
							}
						}
					}
				}
			};

			function onExpandCollapse() {

				if ($$("equipment-content-" + self.uid)) {

					let switcher = $$("switcher-" + self.uid);

					if (switcher) {

						let switcherValue = switcher.getValue();

						if (switcherValue === "switcher-element-hierarchy-" + self.uid) {

							let elementSelected = self.treeView.getSelected();

							if (Array.isArray(elementSelected) && elementSelected.length > 0) {

								let elementSelectedId = elementSelected[0].id_;
								let element = window.em.getByField(EntityType.ELEMENT, "id", elementSelectedId);

								if (element && !element.defaultModuleAction) {

									let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);

									if (elementType && !elementType.defaultModuleAction) {

										self.callbackSelectedItemTree(elementSelected);
									}
								}
							}
						}
					}
				}
			}

			function getSwitcherViews() {

				let switcher;

				switcher = {
					id: "switcher-views-" + self.uid,
					hidden: (!self.config.treeElements && !self.config.treeCustom) ? true : false,
					cols: [
						{
							rows: [
								getViewListEquipments()
							]
						}
					]
				};

				if (Array.isArray(self.elementHierarchies) && self.elementHierarchies.length > 0) {

					let tree = getViewTreeEquipments();

					switcher.cols[0].rows.push(tree);
				}

				if (Array.isArray(self.treeCustom) && self.treeCustom.length > 0) {

					for (let treeCustom of self.treeCustom) {

						let tree = getViewTreeEquipments(treeCustom);

						switcher.cols[0].rows.push(tree);
					}
				}

				return switcher;
			}

			function getViewListEquipments() {

				let listView = {};
				listView.rows = [];
				let viewListEquipments = {};

				if (jsonEquipmentTypesWithGridAssignedCopy != undefined && jsonEquipmentTypesWithGridAssignedCopy.length > 0) {

					let options = {};

					options[ListView.Options.MODE] = ListView.DrawMode.DEFAULT;

					let events = {};

					events[ListView.EventTypes.ON_SELECT_ROW] = function(ids) {

						self.callbackSelectedElemenTypeList(ids);
					};

					self.listView = new ListView(jsonEquipmentTypesWithGridAssignedCopy, options, events);
					viewListEquipments = self.listView.getView();

				} else {

					viewListEquipments = self.getViewDrawLoading();
				}

				listView = viewListEquipments;

				return listView;
			}

			function getViewTreeEquipments(treeCustom) {

				let treeView = {};
				let events = {};
				let itemRelationships = [];
				let itemsTree;
				let elements = self.elements;
				let hierarchies = self.elementHierarchies;
				let isTreeCustom = (treeCustom) ? true : false;

				if (isTreeCustom) {

					elements = treeCustom.elementTreeNodes;
					hierarchies = webix.copy(treeCustom.elementTreeNodeHierarchies);
				}

				events[TreeView.EventTypes.ON_SELECT_ANY] = function(item, callbackEnable) {

					self.callbackSelectedItemTree(item);

				};

				if (Array.isArray(elements) && elements.length > 0) {

					itemsTree = elements.map(el => {

						if (isTreeCustom) {

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

						} else {

							return {
								id: el.id,
								label: el.alias + " (" + i18n.t(el.elementType.labelAlias) + ")",
								image: el.elementType.elementTypePhoto,
								stateColor: util.element.getColor({ id: el.elementTypeState.id }),
								stateLabel: i18n.t(el.elementTypeState.labelAlias),
							}
						}
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
					label: "LBL_EQUIPMENTS",
					treeOptionValues: [
						{ value: false, treeOption: { id: 2 } },
						{ value: true, treeOption: { id: 3 } },
						{ value: true, treeOption: { id: 4 } },
						{ value: true, treeOption: { id: 5 } },
						{ value: true, treeOption: { id: 6 } },
						{ value: false, treeOption: { id: 8 } }
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

				let options = [
					{
						id: "switcher-element-types-" + self.uid,
						value: `<div id='${"switcher-element-types-" + self.uid}' style='text-align: center; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
									<span class='ico webix_icon mdi mdi-format-list-bulleted'></span>
									<span>${i18n.t("LBL_LIST")}</span>
								</div>`
					}
				];

				if (Array.isArray(self.elementHierarchies) && self.elementHierarchies.length > 0) {

					options.push({
						id: "ElementTree:0-" + self.uid + "_" + self.treeViewsArr[0].uid,
						value: `<div id='${"ElementTree-0-" + self.uid + "_" + self.treeViewsArr[0].uid}' style='text-align: center; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
									<span class='ico webix_icon mdi mdi-family-tree'></span>
									<span>${i18n.t("LBL_TREE")}</span>
								</div>`
					});

					withTreeElements = true;
				}

				if (Array.isArray(self.treeCustom) && self.treeCustom.length > 0) {

					let idx = withTreeElements ? 1 : 0;

					for (let i = idx; i <= self.treeCustom.length; i++) {

						if (self.treeCustom[i - idx]) {

							let idHTML = self.treeCustom[i - idx].id.replace(":", "-");

							options.push({
								id: self.treeCustom[i - idx].id + "-" + self.uid + "_" + self.treeViewsArr[i].uid,
								value: `<div id='${idHTML + "-" + self.uid + "_" + self.treeViewsArr[i].uid}' title='${i18n.t(self.treeCustom[i - idx].labelAlias)}' style='text-align: center; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
									   <span class='ico webix_icon mdi mdi-family-tree'></span>
									   <span>${i18n.t(self.treeCustom[i - idx].labelAlias)}</span>
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
															TABLE: 0,
															EQUIPMENT: 1
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

															if (id === "switcher-element-types-" + self.uid) {

																HTMLelement = $$("carousel-list-" + self.uid).getNode().querySelector('#' + id);

																$$("listview-" + self.listView.uid).show();
																$$("resizer-" + self.uid).enable();

																for (let treeIds of self.treeViewsArr) {

																	$$("treeview-" + treeIds.uid).hide();
																}

																$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {

																	return `<div>
																			  <span class='ico webix_icon mdi mdi-view-headline'></span>
																			  <span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
																			</div>`;
																});

																$$("view-main-list-equipments-" + self.uid).refresh();

																contentViews[VIEWS.TABLE].show();
																contentViews[VIEWS.EQUIPMENT].hide();
																util.ui.setTitle(self.args.containerId, i18n.t("LBL_EQUIPMENTS"), null);
																HTMLelement.scrollIntoView({ inline: "center", block: "end", behavior: "smooth" });
															}

															if (id.startsWith("ElementTree:")) {

																let idHTML = id.replace(":", "-");
																let newTreeId = id.split("_")[1];
																let treeView = self.treeViewsArr.find(e => e.uid == newTreeId);
																HTMLelement = $$("carousel-list-" + self.uid).getNode().querySelector('#' + idHTML);

																for (let treeIds of self.treeViewsArr) {

																	$$("treeview-" + treeIds.uid).hide();
																}

																$$("treeview-" + newTreeId).show();
																$$("listview-" + self.listView.uid).hide();
																$$("resizer-" + self.uid).disable();

																$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {

																	return `<div>
																			  <span class='ico webix_icon mdi mdi-view-headline'></span>
																			  <span>${i18n.t("LBL_EQUIPMENT_TREE")}</span>
																			</div>`;
																});

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

																	let firstItem = treeView.getFirstItem();
																	treeView.select(firstItem.id);
																	contentViews[VIEWS.EQUIPMENT].show();
																}

																contentViews[VIEWS.TABLE].hide();
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

														$$(this).blockEvent();
														$$(this).select("switcher-element-types-" + self.uid);
														$$(this).unblockEvent();
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

			return viewMain;
		}

		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, TOPIC_ELEMENTS, onMessage);
			}

			function onMessage(data) {

				let items;

				if (data.elementTypeState) {

					for (let treeView of self.treeViewsArr) {

						items = treeView.getItemsById_(data.id);

						if (Array.isArray(items) && items.length > 0) {

							items.forEach(item => {

								let newElementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", data.elementTypeState.id);

								if (newElementTypeState.color != item.props.stateColor) {

									let newItem = webix.copy(item);

									newItem.props.stateColor = newElementTypeState.color;
									newItem.props.stateLabel = i18n.t(newElementTypeState.labelAlias);

									treeView.update(newItem.id, newItem);
								}
							});
						}
					}
				}
			}
		}
	}


	callbackSelectedElemenTypeList(id) {

		let self = this;
		let item = self.listView.getSelectedItems();
		let dataGridId = item[0].grid;
		let dataGridParams = id ? id : item[0].id;

		if (dataGridId != undefined && dataGridParams != undefined) {

			$$("loadingContent-" + self.uid).show();
			$$("multi-" + self.uid).hide();
			self.listView.enable();

			webix.ajax().get(appConfig.restUrl + "grids/" + dataGridId + "/" + dataGridParams).then(function(dat) {

				if (dat != null) {

					let metadata = dat.json().metadata;
					let data = dat.json().data;
					self.dataTable = new DataTable(dataGridId, dataGridParams, metadata, data);

					let newTable = {
						id: "table-content-" + self.uid,
						rows: self.dataTable.getView()
					};

					webix.ui(newTable, $$("table-content-" + self.uid));
					self.dataTable.callbacks();
					self.listView.enable(true);
					$$("multi-" + self.uid).show();
					$$("loadingContent-" + self.uid).hide();
				}
			}, function(response) { util.ajax.handleError(response); });
		}
	}

	callbackSelectedItemTree(item) {

		let self = this;
		let element = undefined;
		let elementType = undefined;
		let defaultModAction = undefined;
		let elementId = undefined;
		let modAction = self.config.action.equipmentViewer;
		let equipmentSelected = [];

		$$("loadingContent-" + self.uid).show();
		$$("multi-" + self.uid).hide();

		if (Array.isArray(item) && item.length > 0 && item[0].id_) {

			elementId = (item[0].aux && item[0].aux.elementId) ? item[0].aux.elementId : item[0].id_;

			element = window.em.getByField(EntityType.ELEMENT, "id", elementId);

			if (element && element.defaultModuleAction) {

				defaultModAction = JSON.parse(element.defaultModuleAction);

				if (Array.isArray(defaultModAction) && defaultModAction.length > 0) {

					getOptionDialogBox()

				} else {

					modAction = defaultModAction;
					launchModule();
				}

			} else {

				if (element && element.elementType) {

					elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);

					if (elementType && elementType.defaultModuleAction) {

						defaultModAction = JSON.parse(elementType.defaultModuleAction);

						if (Array.isArray(defaultModAction) && defaultModAction.length > 0) {

							getOptionDialogBox();

						} else {

							modAction = defaultModAction;
							launchModule();
						}

					} else {

						launchModule();
					}

				} else {

					$$("multi-" + self.uid).show();
					$$("loadingContent-" + self.uid).hide();
				}
			}

		} else {

			let viewNoData = {
				id: "equipment-content-" + self.uid,
				css: { "box-sizing": "border-box" },
				cols: [util.ui.getViewNoData()]
			};

			webix.ui(viewNoData, $$("equipment-content-" + self.uid));
			$$("multi-" + self.uid).show();
			$$("loadingContent-" + self.uid).hide();
		}

		function getOptionDialogBox() {

			let options = [];

			for (let modAct of defaultModAction) {

				let mod = util.module.getFromModuleAction(modAct);

				options.push({
					id: modAct,
					label: mod.name,
					icon: null,
					description: i18n.t("LBL_MODE_" + mod.description.toUpperCase() + "_DESCRIPTION"),
				});
			}

			let optionDialog = new OptionDialogBox(i18n.t("LBL_SELECT_ANY_OPTION"), options, selectModuleAction, OptionDialogBox.MODE_TOGGLE);
			optionDialog.show();

			function selectModuleAction(obj) {

				modAction = obj;
				$$(optionDialog.optionWindow).close();
				launchModule();
			}
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
					$$("loadingContent-" + self.uid).hide();
				}
			}
		}
	}
}
