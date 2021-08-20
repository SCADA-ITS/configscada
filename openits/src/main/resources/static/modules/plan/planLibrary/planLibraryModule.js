import GenericModule from "../../genericModule.js";
import PlanLibraryPersist from "./planLibraryPersist.js";
import PlanLibraryContent from "./planLibraryContent.js";
import TreeView from "../../../component/treeview.js";
import { util } from "../../../lib/util.js";

const TREE_PANEL_WIDTH = 325;

export default class PlanLibraryModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.planLibraryPersist = undefined;
		this.treeView = undefined;
	}

	loadData() {

		let self = this;
		let view = {};

		$$("loading-" + self.uid).show();

		view = {
			id: self.uid,
			cols: [
				drawTree(),
				drawResizer(),
				drawContent(),
				drawLoading()
			]
		}

		function load(planNodeId) {

			let promises = [];

			promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.planNodes));
			promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.planHierarchies));

			webix.promise.all(promises).then(function(responses) {

				let planNodes = responses[0] ? responses[0].json() : undefined;
				let planHierarchies = responses[1] ? responses[1].json() : undefined;

				if (planNodes && planHierarchies && $$(self.uid)) {

					self.planLibraryPersist = new PlanLibraryPersist(self.config);

					drawViewTree(planNodes, planHierarchies);

					if (self.treeView) {

						self.treeView.select("root");
						self.treeView.expand("root");

						if (planNodeId) {

							self.treeView.select(planNodeId);

							let selectedItems = self.treeView.getSelected();

							if (Array.isArray(selectedItems) && selectedItems.length === 1) {

								let selectedItem = selectedItems[0];

								if (selectedItem.props && selectedItem.props.folder) {

									self.treeView.expand(selectedItem.id)
								}
								else {

									let parent = self.treeView.getParent(selectedItem);

									if (parent) {

										self.treeView.expand(parent.id);
									}
								}
							}
						}
					}
				}
			});
		}

		function drawTree() {

			let viewTree = {};

			viewTree = {
				id: "view-plan-lib-tree-" + self.uid,
				header: function() {

					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_PLANS")}</span>
							</div>`;
				},
				body: {
					width: TREE_PANEL_WIDTH,
					rows: [
						{
							id: "view-tree-" + self.uid
						}
					]
				}
			};

			return viewTree;
		}

		function drawViewTree(items, relationships) {

			let treeView = {
				id: "view-tree-" + self.uid,
				cols: []
			};

			self.treeView = new TreeView(getMetadata(), getTreeData(), getEvents());

			function getEvents() {

				let events = {};

				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {

					let planNode = items.find(e => e.id === item.id_);

					if (planNode && planNode.plan) {

						$$("plan-lib-loading-" + self.uid).show();
						$$("plan-lib-content-" + self.uid).hide();

						webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.plan, { id: planNode.plan.id })).then(function(response) {

							let plan = response ? response.json() : undefined;

							if (plan) {

								let planLibraryContent = new PlanLibraryContent(self.config, self.uid, plan);

								webix.ui(planLibraryContent.getView(), $$("plan-lib-content-" + self.uid));

								$$("plan-lib-loading-" + self.uid).hide();
								$$("plan-lib-content-" + self.uid).show();
							}
						});
					}
				};

				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(folder) {

				}

				return events;
			}

			function getTreeData() {

				let data = [];

				let itemsTree = items.map(e => {

					return {

						id: e.id,
						label: e.alias,
						plan: e.plan,
						folder: !e.plan
					}
				});

				let itemRelationships = relationships.map(e => {

					return {

						id: e.id,
						nodeParentId: e.parent.id,
						nodeChildId: e.child.id
					}
				});

				data = util.tree.getNodes(itemsTree, itemRelationships, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);

				return data;
			}

			self.treeView = new TreeView(getMetadata(), getTreeData(), getEvents());

			treeView.cols.push(self.treeView.getView());

			webix.ui(treeView, $$("view-tree-" + self.uid));
		}

		function getMetadata() {

			return {

				id: -1,
				label: "LBL_LIBRARY",
				treeOptionValues: [
					{ value: false, treeOption: { id: 2 } },
					{ value: true, treeOption: { id: 3 } },
					{ value: true, treeOption: { id: 4 } },
					{ value: true, treeOption: { id: 5 } },
					{ value: false, treeOption: { id: 6 } },
					{ value: true, treeOption: { id: 8 } }
				],
				treeCommands: [
					{ id: 1, callbacks: [{ commandId: 1, function: onNewFolder }], position: 1, label: "LBL_NEW_FOLDER", icon: "mdi mdi-folder-plus-outline", default: false, multiselect: false, itemRequired: true, grouped: false },
					{ id: 2, callbacks: [{ commandId: 2, function: onNewItem }], position: 2, label: "LBL_NEW_ITEM", icon: "mdi mdi-file-plus-outline", default: false, multiselect: false, itemRequired: true, grouped: false },
					{ id: 3, callbacks: [{ commandId: 3, function: onDelete }], position: 3, label: "LBL_DELETE", icon: "mdi mdi-delete-outline", default: false, multiselect: false, itemRequired: true, grouped: false }
				]
			}
		}


		function onNewFolder(selectedPks) {

			let parent = undefined;
			let itemsSelected = self.treeView.getSelected();

			if (Array.isArray(itemsSelected) && itemsSelected.length > 0 && Array.isArray(selectedPks) && selectedPks.length > 0) {

				let selected = itemsSelected[0];

				if (selected.id_ === selectedPks[0]) {

					parent = selected.props.folder ? selected : self.treeView.getParent(selected);
				}
			}

			webix.prompt({
				title: i18n.t("LBL_NEW_FOLDER"),
				text: i18n.t("LBL_NEW_FOLDER_DESCRIPTION"),
				ok: i18n.t("LBL_ACCEPT"),
				cancel: i18n.t("LBL_CANCEL"),
				input: {
					required: true,
					placeholder: i18n.t("LBL_NEW_FOLDER_NAME")
				},
				width: 600,

			}).then(function(result) {

				// TODO: POST with result

				if (self.planLibraryPersist) {

					self.planLibraryPersist.createFolder(result, parent.id_)
						.then(function(data) {

							load(parent.id_ ? parent.id_ + "_" + data.id : data.id + "_" + data.id);
						})
						.catch(function() {

						});
				}


			}).fail(function() {

			});
		}

		function onNewItem(selectedPks) {

			let parent = undefined;
			let itemsSelected = self.treeView.getSelected();

			if (Array.isArray(itemsSelected) && itemsSelected.length > 0 && Array.isArray(selectedPks) && selectedPks.length > 0) {

				let selected = itemsSelected[0];

				if (selected.id_ === selectedPks[0]) {

					parent = selected.props.folder ? selected : self.treeView.getParent(selected);
				}
			}

			webix.prompt({
				title: i18n.t("LBL_NEW_PLAN"),
				text: i18n.t("LBL_NEW_PLAN_DESCRIPTION"),
				ok: i18n.t("LBL_ACCEPT"),
				cancel: i18n.t("LBL_CANCEL"),
				input: {
					required: true,
					placeholder: i18n.t("LBL_NEW_PLAN_NAME")
				},
				width: 600,

			}).then(function(result) {

				self.planLibraryPersist.createItem(result, parent.id_)
					.then(function(data) {

						load((parent && parent.id != "root") ? parent.id_ + "_" + data.id : data.id + "_" + data.id);
					})
					.catch(function() {

					});

			}).fail(function() {

			});
		}

		function onDelete(selectedPks) {

			let parent = undefined;
			let itemsSelected = self.treeView.getSelected();

			if (Array.isArray(itemsSelected) && itemsSelected.length > 0 && Array.isArray(selectedPks) && selectedPks.length > 0) {

				let selected = itemsSelected[0];

				if (selected.id === "root") {

					webix.alert({
						title: i18n.t("LBL_ROOT_CANNOT_BE_DELETED"),
						text: i18n.t("LBL_ROOT_CANNOT_BE_DELETED_DESCRIPTION"),
						type: "alert-warning",
						width: 500
					});
				}
				else {

					webix.confirm({
						title: i18n.t("LBL_DIALOG_DELETE_PLAN"),
						ok: i18n.t("LBL_YES"),
						cancel: i18n.t("LBL_NO"),
						text: i18n.t("LBL_DIALOG_DELETE_PLAN_DESCRIPTION"),
						width: 600
					}).then(function(result) {

						if (selected.id_ === selectedPks[0]) {

							parent = self.treeView.getParent(selected);
						}

						if (selected.id_ === selectedPks[0]) {

							self.planLibraryPersist.deleteItem(selected.id_)
								.then(function(data) {

									load(parent ? parent.id : undefined);
								})
								.catch(function() {

								});
						}
					});
				}
			}

		}

		function drawResizer() {

			let viewResizer = {};

			viewResizer = {
				view: "resizer"
			}

			return viewResizer;
		}

		function drawContent() {

			let viewContent = {
				id: "plan-lib-content-" + self.uid,
				css: { "background-color": "white" }
			};

			return viewContent;
		}

		function drawLoading() {

			let viewLoading = util.ui.getViewDrawLoading();

			viewLoading.id = "plan-lib-loading-" + self.uid;
			viewLoading.hidden = true;

			return viewLoading;
		}

		$$("loading-" + self.uid).hide();
		webix.ui(view, $$(self.uid));

		load();

		webix.delay(function() {

			let viewComposer = $$("view-composer-" + self.uid);

			if (viewComposer) {

				viewComposer.hide();
			}

			if (self.treeView) {

				self.treeView.select("root");
				self.treeView.expand("root");
			}

		}, null, null, 300);
	}
}	
