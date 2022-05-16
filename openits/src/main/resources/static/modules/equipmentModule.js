import { util } from "../lib/util.js";
import GenericMultiGridModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";
import TreeView from "../component/treeview.js";
import OptionDialogBox from "../component/optionDialogBox.js";

const MODULE_TYPE_GENERIC_MULTI_GRID_PARAM_ENTITY_TYPE = 7;
const TOPIC_ELEMENTS = "/topic/element.>";

export default class EquipmentModule extends GenericMultiGridModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.dataTable = undefined;
		this.listView = undefined;
		this.treeView = undefined;
		this.elementHierarchies = undefined;
		this.idStompSubscriptor = undefined;
		this.itemsTree = [];
		this.elements = undefined;
	}


	getView() {

		let self = this;

		let view = {
			css: "custom_window",
			cols: [
				{
					css: { "background-color": "white" },
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: "loading-" + self.uid,
									rows: [util.ui.getViewDrawLoading()]
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};

		return view;
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

		webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.hierarchy).then(function(hierarchyResponse) {

			if (hierarchyResponse != null) {

				self.elementHierarchies = hierarchyResponse.json();

				webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.elements, JSON.stringify([])).then(function(elementsResponse) {

					if (elementsResponse != null) {

						self.elements = elementsResponse.json();

						webix.ui(drawContent(jsonEquipmentTypesWithGridAssignedCopy), $$("loading-" + self.uid));

						$$("switcher-views-" + self.uid).show();
						$$("loading-switcher-" + self.uid).hide();

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
						}
					}
				});
			}
		});

		function drawContent(jsonEquipmentTypesWithGridAssigned) {

			let viewMain;
			let rowsviewMainListEquipments = [];

			rowsviewMainListEquipments.push({
				id: "loading-switcher-" + self.uid,
				hidden: false,
				cols: [
					util.ui.getViewDrawLoading(true)
				]
			});

			if (self.elementHierarchies) {

				rowsviewMainListEquipments.push(getSwitcherViews());
				rowsviewMainListEquipments.push({ height: 1, borderless: false });
				rowsviewMainListEquipments.push(getSwitcherButtons());

			} else {

				rowsviewMainListEquipments.push(getViewListEquipments());
			}

			viewMain = {
				cols: [
					{
						id: "view-main-list-equipments-" + self.uid,
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
					onAfterExpand: function(obj) {

						if ($$("equipment-content-" + self.uid)) {

							let switcherValue = $$("switcher-" + self.uid).getValue();

							if (switcherValue === "switcher-element-hierarchy-" + self.uid) {

								let elementSelected = self.treeView.getSelected();
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
					},
					onAfterCollapse: function() {

						if ($$("equipment-content-" + self.uid)) {

							let switcherValue = $$("switcher-" + self.uid).getValue();

							if (switcherValue === "switcher-element-hierarchy-" + self.uid) {

								let elementSelected = self.treeView.getSelected();
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
			};

			function getSwitcherViews() {

				let switcher;

				switcher = {
					id: "switcher-views-" + self.uid,
					hidden: true,
					cols: [
						{
							rows: [
								getViewListEquipments(),
								getViewTreeEquipments()
							]
						}
					]
				};

				return switcher;
			}

			function getViewListEquipments() {

				let listView = {};
				listView.rows = [];
				let viewListEquipments = {};

				if (jsonEquipmentTypesWithGridAssigned != undefined && jsonEquipmentTypesWithGridAssigned.length > 0) {

					let options = {};

					options[ListView.Options.MODE] = ListView.DrawMode.DEFAULT;

					let events = {};

					events[ListView.EventTypes.ON_SELECT_ROW] = function(ids) {

						self.callbackSelectedElemenTypeList(ids);
					};

					self.listView = new ListView(jsonEquipmentTypesWithGridAssigned, options, events);
					viewListEquipments = self.listView.getView();

				} else {

					viewListEquipments = self.getViewDrawLoading();
				}

				listView = viewListEquipments;

				return listView;
			}

			function getViewTreeEquipments() {

				let treeView = {};
				let events = {};

				events[TreeView.EventTypes.ON_SELECT_ANY] = function(item, callbackEnable) {

					self.callbackSelectedItemTree(item);
				};

				let itemRelationships = [];

				if (Array.isArray(self.elements) && self.elements.length > 0) {

					self.itemsTree = self.elements.map(e => {

						return {
							id: e.id,
							label: e.alias + " (" + i18n.t(e.elementType.labelAlias) + ")",
							image: e.elementType.elementTypePhoto,
							stateColor: util.element.getColor({ id: e.elementTypeState.id }),
							stateLabel: i18n.t(e.elementTypeState.labelAlias),
						}
					});
				}

				if (Array.isArray(self.elementHierarchies) && self.elementHierarchies.length > 0) {

					itemRelationships = self.elementHierarchies.map(e => {

						return {
							id: e.id,
							nodeParentId: e.parent.id,
							nodeChildId: e.child.id
						}
					});
				}

				let treeData = util.tree.getNodes(self.itemsTree, itemRelationships, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);

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
					treeCommands: [],
					stomp: {
						topic: TOPIC_ELEMENTS,
						adapter: adapterElement2Tree
					}
				}

				function adapterElement2Tree(data, getItemsById_, update) {

					if (Array.isArray(data.basicElementsVO) && data.basicElementsVO.length > 0) {

						let elementId = data.basicElementsVO[0].id;

						if (Array.isArray(self.itemsTree) && self.itemsTree.length > 0) {

							let elementAux = self.itemsTree.find(e => e.id === elementId);

							if (elementAux) {

								webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementId, { id: elementAux.id })).then(function(elementsResponse) {

									if (elementsResponse != null) {

										let element = elementsResponse.json();
										let currentItems = self.treeView.getItemsById_(elementId);

										if (Array.isArray(currentItems) && currentItems.length > 0) {

											for (let currentItem of currentItems) {

												let stateColor = (element.elementTypeState.color) ? element.elementTypeState.color : util.element.getColor({ id: element.elementTypeState.id });

												currentItem.props.stateColor = stateColor;
												currentItem.props.stateLabel = i18n.t(element.elementTypeState.labelAlias);

												self.treeView.updateById_(elementId, currentItem);
											}
										}
									}
								});
							}
						}
					}
				}

				self.treeView = new TreeView(metadata, treeData, events);

				treeView = self.treeView.getView();
				treeView.hidden = true;

				return treeView;
			}

			function getSwitcherButtons() {

				let switcherButtonsView;

				switcherButtonsView = {
					css: { "background-color": "var(--blue-soft-opacity-1)" },
					height: 50,
					cols: [
						{ width: 10 },
						{
							rows: [
								{ height: 5 },
								{
									id: "switcher-" + self.uid,
									view: "segmented",
									css: "segment-custom-2",
									value: "switcher-element-types-" + self.uid,
									options: [
										{
											id: "switcher-element-types-" + self.uid,
											value: `<div>
													<span class='ico webix_icon mdi mdi-format-list-bulleted'></span>
													<span>${i18n.t("LBL_LIST")}</span>
												</div>`,
										},
										{
											id: "switcher-element-hierarchy-" + self.uid,
											value: `<div>
													<span class='ico webix_icon mdi mdi-family-tree'></span>
													<span>${i18n.t("LBL_TREE")}</span>
												</div>`,
										}
									],
									tooltip: function(obj) {

										if (obj.id === "switcher-element-types-" + self.uid) {

											return i18n.t("LBL_SELECTION_BY_TYPE");
										}
										else if (obj.id === "switcher-element-hierarchy-" + self.uid) {

											return i18n.t("LBL_SELECTION_BY_HIERARCHY");
										}
									},
									on: {
										onChange(newv, oldv) {

											const VIEWS = {
												TABLE: 0,
												EQUIPMENT: 1
											};

											$$("loading-switcher-" + self.uid).show();
											$$("switcher-views-" + self.uid).hide();

											webix.delay(function() {

												let contentViews = $$("multi-" + self.uid).getChildViews();

												$$("switcher-views-" + self.uid).show();
												$$("loading-switcher-" + self.uid).hide();

												if (newv === "switcher-element-types-" + self.uid) {

													$$("listview-" + self.listView.uid).show();
													$$("treeview-" + self.treeView.uid).hide();
													$$("resizer-" + self.uid).enable();

													$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {

														return `<div>
																<span class='ico webix_icon mdi mdi-view-headline'></span>
																<span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
															</div>`;
													});

													$$("view-main-list-equipments-" + self.uid).refresh();

													contentViews[VIEWS.TABLE].show();
													contentViews[VIEWS.EQUIPMENT].hide();

												} else if (newv === "switcher-element-hierarchy-" + self.uid) {

													$$("treeview-" + self.treeView.uid).show();
													$$("listview-" + self.listView.uid).hide();
													$$("resizer-" + self.uid).disable();

													$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {

														return `<div>
																<span class='ico webix_icon mdi mdi-view-headline'></span>
																<span>${i18n.t("LBL_EQUIPMENT_TREE")}</span>
															</div>`;
													});

													$$("view-main-list-equipments-" + self.uid).refresh();

													let itemSelected = self.treeView.getSelected();

													if (Array.isArray(itemSelected) && itemSelected.length > 0) {

														contentViews[VIEWS.EQUIPMENT].show();

													} else {

														let firstItemId = $$("tree-" + self.treeView.uid).getFirstId();
														self.treeView.select(firstItemId);
														contentViews[VIEWS.EQUIPMENT].show();
													}

													contentViews[VIEWS.TABLE].hide();
												}
											});
										}
									}
								},
								{ height: 5 }
							]
						},
						{ width: 10 }
					]
				};

				return switcherButtonsView;
			}

			return viewMain;
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
		let modAction = self.config.action.equipmentViewer;
		let equipmentSelected = [];

		$$("loadingContent-" + self.uid).show();
		$$("multi-" + self.uid).hide();

		if (Array.isArray(item) && item.length > 0 && item[0].id_) {

			element = window.em.getByField(EntityType.ELEMENT, "id", item[0].id_);

			if (element && element.defaultModuleAction) {

				defaultModAction = JSON.parse(element.defaultModuleAction);

				if (Array.isArray(defaultModAction) && defaultModAction.length > 0) {

					getOptionDialogBox()

				} else {

					modAction = defaultModAction;
					launchModule();
				}

			} else {

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
			}

		} else {

			let viewNoData = {
				id: "equipment-content-" + self.uid,
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

			equipmentSelected.push(item[0].id_);
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
