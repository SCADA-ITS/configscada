import { util } from "../lib/util.js";
import GenericMultiGridModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";
import TreeView from "../component/treeview.js";
import OptionDialogBox from "../component/optionDialogBox.js";

const MODULE_TYPE_GENERIC_MULTI_GRID_PARAM_ENTITY_TYPE = 7;
const ELEMENT_TYPE_ALL = "-1";

export default class EquipmentModule extends GenericMultiGridModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.dataTable = undefined;
		this.listView = undefined;
		this.treeView = undefined;
		this.elementHierarchies = undefined;
		this.viewMain = undefined;
	}

	getViewDrawLoading() {

		return {
			view: "template",
			id: "no-data-" + this.uid,
			template: function(data) {

				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
	}

	getView() {

		let self = this;
		let entity = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_TYPE_GENERIC_MULTI_GRID_PARAM_ENTITY_TYPE).value;

		let view = {
			cols: [
				{ width: 6 },
				{
					id: "loadingList-" + self.uid,
					header: i18n.t("LBL_" + entity),
					body: {
						width: 330,
						minWidth: 250,
						rows: [
							{ height: 6 },
							{
								template: function(data) {

									let icon = "<img src='img/loading.svg' align='center'>";

									return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
								}
							},
							{ height: 6 }
						]
					}
				},
				{ view: "resizer" },
				{
					body: {
						rows: [
							{ height: 6 },
							{
								cols: [
									{ width: 6 },
									{
										id: self.uid,
										rows: [self.getViewDrawLoading()]
									},
									{ width: 6 }
								]
							},
							{ height: 6 }
						]
					}
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

				self.drawEquipmentTypeList(jsonEquipmentTypesWithGridAssignedCopy);

				webix.ui(self.viewMain, $$("loadingList-" + self.uid));

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

	drawEquipmentTypeList(jsonEquipmentTypesWithGridAssigned) {

		let self = this;
		let rowsviewMainListEquipments = [];

		self.viewMain = {
			id: "equipmentTypeList-" + self.uid,
			cols: []
		};

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

		let viewMainListEquipments = {
			id: "view-main-list-equipments-" + self.uid,
			header: function() {

				return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
							</div>`;
			},
			body: {
				width: 325,
				rows: rowsviewMainListEquipments
			}
		};

		self.viewMain.cols = [viewMainListEquipments];

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

			let view = {};
			view.rows = [];
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

			view = viewListEquipments;

			return view;
		}


		function getViewTreeEquipments() {

			let view = {};
			let events = {};

			events[TreeView.EventTypes.ON_SELECT_ANY] = function(item, callbackEnable) {

				self.callbackSelectedItemTree(item);
			};

			let itemsTree = [];
			let itemRelationships = [];

			let elements = window.em.getAll(EntityType.ELEMENT);

			if (Array.isArray(elements) && elements.length > 0) {

				itemsTree = elements.map(e => {

					return {
						id: e.id,
						label: e.alias + " (" + i18n.t(e.elementType.labelAlias) + ")",
						image: e.elementType.elementTypePhoto
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

			let treeData = util.tree.getNodes(itemsTree, itemRelationships, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);

			let root = [{
				id: ELEMENT_TYPE_ALL,
				value: i18n.t("LBL_ELEMENT_TYPE_ALL"),
				data: treeData,
				props: {
					folder: true
				}
			}];

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
			}

			self.treeView = new TreeView(metadata, root, events);

			view = self.treeView.getView();
			view.hidden = true;

			return view;
		}

		function getSwitcherButtons() {

			let view;

			view = {
				css: { "background-color": "var(--blue-soft-opacity-1)" },
				height: 50,
				cols: [
					{ width: 10 },
					{
						rows: [
							{ height: 5 },
							{
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

										//console.log("init change => " + newv);
										$$("loading-switcher-" + self.uid).show();
										$$("switcher-views-" + self.uid).hide();

										webix.delay(function() {

											$$("switcher-views-" + self.uid).show();
											$$("loading-switcher-" + self.uid).hide();

											if (newv === "switcher-element-types-" + self.uid) {

												$$("listview-" + self.listView.uid).show();
												$$("treeview-" + self.treeView.uid).hide();

												$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {

													return `<div>
																	<span class='ico webix_icon mdi mdi-view-headline'></span>
																	<span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
																</div>`;
												});

												$$("view-main-list-equipments-" + self.uid).refresh();
												self.callbackSelectedElemenTypeList();

											} else if (newv === "switcher-element-hierarchy-" + self.uid) {

												$$("listview-" + self.listView.uid).hide();
												$$("treeview-" + self.treeView.uid).show();

												$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {

													return `<div>
																	<span class='ico webix_icon mdi mdi-view-headline'></span>
																	<span>${i18n.t("LBL_EQUIPMENT_TREE")}</span>
																</div>`;
												});

												$$("view-main-list-equipments-" + self.uid).refresh();

												let id = [];
												let itemSelected = self.treeView.getSelected();

												if (Array.isArray(itemSelected) && itemSelected.length > 0) {

													id.push({
														id_: itemSelected[0].id_
													});

													self.callbackSelectedItemTree(id);

												} else {

													$$("tree-" + self.treeView.uid).select(ELEMENT_TYPE_ALL);
												}
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
			return view;
		}
	}

	callbackSelectedElemenTypeList(id) {

		let self = this;
		let item = self.listView.getSelectedItems();
		let dataGridId = item[0].grid;
		let dataGridParams = id ? id : item[0].id;
		let equipmentContent = undefined;

		if (dataGridId != undefined && dataGridParams != undefined && $$(self.uid) != undefined) {

			if (self.dataTable) {

				let viewsToRemove = [];

				if ($$(self.dataTable.getViewId())) {

					equipmentContent = $$(self.dataTable.getViewId());

				} else {

					let content = $$(self.uid).getChildViews();
					equipmentContent = $$(content[0].config.id);
				}

				webix.ui(self.getViewDrawLoading(), equipmentContent);

				$$(self.uid).getChildViews().forEach(view => {

					viewsToRemove.push(view.config.id);
				});

				viewsToRemove.forEach(viewId => {

					if (viewId.indexOf("no-data-") === -1) {
						$$(self.uid).removeView(viewId);
					}
				});

			}

			self.listView.enable();

			webix.ajax().get(appConfig.restUrl + "grids/" + dataGridId + "/" + dataGridParams).then(function(dat) {

				if (dat != null) {

					let metadata = dat.json().metadata;
					let data = dat.json().data;
					self.dataTable = new DataTable(dataGridId, dataGridParams, metadata, data);

					let newTable = {
						id: self.uid,
						rows: self.dataTable.getView()
					};

					webix.ui(newTable, $$(self.uid));
					self.dataTable.callbacks();
					self.listView.enable(true);
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
		let containerWidth = $$(self.args.containerId).$width - 330;

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
				id: self.uid,
				cols: [util.ui.getViewNoData()]
			};

			webix.ui(viewNoData, $$(self.uid));
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

				let moduleAction = { id: modAction };

				let args = {
					selectedPks: equipmentSelected,
					containerId: self.args.containerId,
					containerWidth: containerWidth
				};

				util.module.open(mod, moduleAction, VIEW_TYPE_CUSTOM, drawContent, args);

				function drawContent(view) {

					let equipmentViewer = {
						id: self.uid,
						cols: [view]
					};

					webix.ui(equipmentViewer, $$(self.uid));
				}
			}
		}
	}
}
