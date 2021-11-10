import { util } from "../../../lib/util.js";
import GenericModule from "../../genericModule.js";
import PlanLibraryPersist from "./planLibraryPersist.js";
import PlanLibraryContent from "./planLibraryContent.js";
import TreeView from "../../../component/treeview.js";
import OptionButtonList from "../../../component/optionButtonList.js";
import MessageBox from "../../../component/messageBox.js";
import InputDialogBox from "../../../component/inputDialogBox.js";

const TREE_PANEL_WIDTH = 325;

const PLAN_MODE_TYPE_AUTO = 1;
const PLAN_MODE_TYPE_SEMI = 2;
const PLAN_MODE_TYPE_MANUAL = 3;

const PLAN_SUBTYPE_FIXED_ID = "PlanSubtype:1";

export default class PlanLibraryModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.planLibraryPersist = undefined;
		this.treeView = undefined;
		this.optionButtonList = undefined;
		
		this.planNodes = undefined;
		this.planHierarchies = undefined;
		this.editPermission = util.module.isAllowedPermission(module.config.requiredPermissions.edit);
	}

	loadData() {

		let self = this;
		let view = {};

		$$("loading-" + self.uid).show();

		view = {
			id: self.uid,
			hidden: true,
			cols: [
				drawTree(),
				drawResizer(),
				drawContent(),
				getViewOptionButtonList(),
				drawLoading()
			]
		}

		function load(planNodeId) {

			let promises = [];

			promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.planNodes));
			promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.planHierarchies));
			promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.plans));

			webix.promise.all(promises).then(function(responses) {

				self.planNodes = responses[0] && responses[0].json() ? responses[0].json() : [];
				self.planHierarchies = responses[1] && responses[1].json() ? responses[1].json() : [];
				self.plans = responses[2]  && responses[2].json() ? responses[2].json() : [];

				if ($$(self.uid)) {

					self.planLibraryPersist = new PlanLibraryPersist(self.config);

					drawViewTree();

					if (self.treeView) {

						self.treeView.select("root");
						self.treeView.expand("root");

						if (planNodeId) {

							self.treeView.select(planNodeId);

							let selectedItems = self.treeView.getSelected();

							if (Array.isArray(selectedItems) && selectedItems.length === 1) {

								let selectedItem = selectedItems[0];
								
								self.treeView.update(planNodeId, selectedItem);

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
						
						afterLoad();
						
					}
				}
			});
		}
		
		function getViewOptionButtonList() {

			let viewOptionButtonList = {
				id: "view-option-dialog-" + self.uid
			};

			let actions = [
					{ id: 1, label: "LBL_NEW_FOLDER", icon: "mdi mdi-folder-plus-outline", default: false },
					{ id: 2, label: "LBL_NEW_ITEM", icon: "mdi mdi-file-plus-outline", default: false },
					{ id: 3, label: "LBL_DELETE", icon: "mdi mdi-delete-outline", default: false }
				];

			if (Array.isArray(actions) && actions.length > 0) {

				let options = [];

				for (let action of actions) {
					
					options.push({
						id: action.id,
						label: i18n.t(action.label),
						description: i18n.t(action.label.toUpperCase() + "_DESCRIPTION"),
						default: action.default,
						disabled: !self.editPermission, 
						icon: action.icon
					});
				}

				self.optionButtonList = new OptionButtonList(options, onAceptOptionButtonList, OptionButtonList.MODE_BUTTONS);

				viewOptionButtonList = {
					id: "view-option-dialog-" + self.uid,
					css: { "background-color": "white" },
					rows: [
						{ height: 10 },
						{
							cols: [
								{ width: 10 },
								{
									view: "label",
									label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-folder-outline"></i></div>`,
									align: "center",
									width: 30
								},
								{ width: 5 },
								{
									view: "label",
									id: "label-plan-lib-selected-" + self.uid,
									css: "alarm-view-header-details-text",
									align: "left"
								},
								{}
							]
						},
						{ height: 5 },
						{						
							cols: [
								{ width: 20 },
								self.optionButtonList.getView(),
								{}
							]
						},
						{},							
					]
				}
			}
				
			return viewOptionButtonList;
		}

		function onAceptOptionButtonList(buttonId) {

			let actions = getMetadata().treeCommands;
			let itemsSelected = self.treeView.getSelected();
			let callbackAction = actions.find(e => e.id === buttonId);
			let selectedPks = [];

			if (callbackAction) {
				selectedPks.push(itemsSelected[0].id_);
				callbackAction.callbacks[0].function(selectedPks);
			}
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

		function drawViewTree() {

			let treeView = {
				id: "view-tree-" + self.uid,
				cols: []
			};

			function getEvents() {

				let events = {};
				
				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {

					let planNode = self.planNodes.find(e => e.id === item.id_);

					if (planNode && planNode.plan) {
					
						$$("plan-lib-loading-" + self.uid).show();
						$$("view-option-dialog-" + self.uid).hide();
						$$("plan-lib-resizer-" + self.uid).hide();
						$$("plan-lib-content-" + self.uid).hide();
												
						
						
						webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.plan, { id: planNode.plan.id })).then(function(response) {

							let plan = response ? response.json() : undefined;

							if (plan) {

								let planLibraryContent = new PlanLibraryContent(self.config, self.uid, plan, onUpdate);

								webix.ui(planLibraryContent.getView(), $$("plan-lib-content-" + self.uid));

								$$("plan-lib-loading-" + self.uid).hide();
								$$("plan-lib-content-" + self.uid).show();
								$$("plan-lib-resizer-" + self.uid).show();
							}
						});
					}
				};

				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(folder) {
					
					if (!self.moduleAction) {
						
						$$("plan-lib-loading-" + self.uid).hide();
						$$("plan-lib-content-" + self.uid).hide();
						$$("plan-lib-resizer-" + self.uid).show();
						$$("view-option-dialog-" + self.uid).show();
	
						$$("label-plan-lib-selected-" + self.uid).setValue(`<span class="alarm-view-header-details-text">${folder.value}</span>`);	
					}
				}

				function onUpdate() {
					
					let itemsSelected = self.treeView.getSelected();
		
					if (Array.isArray(itemsSelected) && itemsSelected.length > 0) {
		
						let selected = itemsSelected[0];
							
						load(selected.id);
					}
				}

				return events;
			}

			function getTreeData() {

				let data = [];
				let itemsTree = [];
				let itemRelationships = [];
				
				if (Array.isArray(self.planNodes) && Array.isArray(self.plans)) {
					
					itemsTree = self.planNodes.map(e => {

						let plan = self.plans.find(p => (e.plan && p.id === e.plan.id));
						
						return {
	
							id: e.id,
							label: plan ? plan.alias : e.alias,
							plan: e.plan,
							folder: !e.plan
						}
					});	
				}
				
				if (Array.isArray(self.planHierarchies)) {
					
					itemRelationships = self.planHierarchies.map(e => {

						return {
	
							id: e.id,
							nodeParentId: e.parent.id,
							nodeChildId: e.child.id
						}
					});	
				}

				data = util.tree.getNodes(itemsTree, itemRelationships, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);

				return data;
			}

			self.treeView = new TreeView(getMetadata(), getTreeData(), getEvents());

			treeView.cols.push(self.treeView.getView());

			webix.ui(treeView, $$("view-tree-" + self.uid));
		}

		function getMetadata() {
			
			let metadata = {
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
				treeCommands: []
			}

			if (self.editPermission) {

				metadata.treeCommands = [
					{ id: 1, callbacks: [{ commandId: 1, function: onNewFolder }], position: 1, label: "LBL_NEW_FOLDER", icon: "mdi mdi-folder-plus-outline", default: false, multiselect: false, itemRequired: true, grouped: false },
					{ id: 2, callbacks: [{ commandId: 2, function: onNewItem }], position: 2, label: "LBL_NEW_ITEM", icon: "mdi mdi-file-plus-outline", default: false, multiselect: false, itemRequired: true, grouped: false },
					{ id: 3, callbacks: [{ commandId: 3, function: onDelete }], position: 3, label: "LBL_DELETE", icon: "mdi mdi-delete-outline", default: false, multiselect: false, itemRequired: true, grouped: false }
				];				
			}		
			
			return metadata;
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
			
			let i = new InputDialogBox (i18n.t("LBL_NEW_FOLDER"), i18n.t("LBL_NEW_FOLDER_DESCRIPTION"), onAccept, true, null, {id: DataType.ALPHANUMERIC});
			i.show();

			function onAccept(folderName) {
				
				// TODO: POST with result

				if (self.planLibraryPersist) {

					self.planLibraryPersist.createFolder(folderName, parent.id_).then(function(data) {

						load(parent.id_ ? parent.id_ + "_" + data.id : data.id + "_" + data.id);
					})
					.catch(function() {

					});
				}				
			}
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

			let i = new InputDialogBox (i18n.t("LBL_NEW_PLAN"), i18n.t("LBL_NEW_PLAN_DESCRIPTION"), onAccept, true, null, {id: DataType.ALPHANUMERIC});
			i.show();
			
			function onAccept(planName){

				let newPlan = {
					alias: planName,
					description: planName,
					planModeType: PLAN_MODE_TYPE_AUTO,
					planSubtypeId: PLAN_SUBTYPE_FIXED_ID
				};				
				
				self.planLibraryPersist.createPlan(newPlan).then(function(resultPlan) {

					if (resultPlan && resultPlan.id) {
						
						self.planLibraryPersist.createItem(planName, parent.id_, resultPlan.id)
						.then(function(resultItem) {
	
							load((parent && parent.id != "root") ? parent.id_ + "_" + resultItem.id : resultItem.id + "_" + resultItem.id);
						})
						.catch(function() {
	
						});	
					}
				});				
			}
		}

		function onDelete(selectedPks) {

			let parent = undefined;
			let itemsSelected = self.treeView.getSelected();

			if (Array.isArray(itemsSelected) && itemsSelected.length > 0 && Array.isArray(selectedPks) && selectedPks.length > 0) {

				let selected = itemsSelected[0];

				if (selected.id === "root") {

					let ms = new MessageBox (MessageBox.WARN, i18n.t("LBL_ROOT_CANNOT_BE_DELETED_DESCRIPTION"), null, null);
					ms.show();
					
				}
				else {

					let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_DIALOG_DELETE_PLAN_DESCRIPTION"), onAccept, null);
					ms.show();
					
					function onAccept() {
					
						if (selected.id_ === selectedPks[0]) {

							parent = self.treeView.getParent(selected);
						}

						if (selected.id_ === selectedPks[0]) {

							if (selected.props.folder) {

								let childFolders = self.treeView.getChilds(selected, TreeView.ItemTypes.FOLDER);
								let childItems = self.treeView.getChilds(selected, TreeView.ItemTypes.ITEM);

								let deletedFolders = 0;
								let deletedItems = 0;
								let deletedPlans = 0;

								childFolders.push(selected);

								let foldersCount = Array.isArray(childFolders) ? childFolders.length : 0;;
								let itemsCount = Array.isArray(childItems) ? childItems.length : 0;
								let plansCount = itemsCount;

								deleteTreeItems();

								function deleteTreeItems() {

									if (Array.isArray(childItems) && childItems.length > 0) {

										childItems.forEach(childItem => {

											self.planLibraryPersist.deleteItem(childItem.id_)
												.then(function(data) {

													console.log("TREE ITEM DELETE => " + childItem.value);

													if (++deletedItems === itemsCount) {

														deleteTreeFolders();
														deletePlans();
													}
												})
												.catch(function() {

												});
										});
									}
									else {
										
										deleteTreeFolders();
									}
								}
								
								function deleteTreeFolders() {
									
									if (Array.isArray(childFolders) && childFolders.length > 0) {
										
										childFolders.forEach(childFolder => {
											
											self.planLibraryPersist.deleteItem(childFolder.id_)
											.then(function(data) {
												
												console.log("TREE FOLDER DELETE => " + childFolder.value);
			
												deletedFolders++;
												
												doWhenAsyncEnds();						
											})
											.catch(function() {
			
											});
										});
									}
								}
								
								function deletePlans() {
								
									if (Array.isArray(childItems) && childItems.length > 0) {
										
										childItems.forEach(childItem => {
											
											let planNode = self.planNodes.find(p => p.id === childItem.id_);
										
											if (planNode && planNode.plan && planNode.plan.id) {
												
												self.planLibraryPersist.deletePlan(planNode.plan.id)
												.then(function(data) {
													
													console.log("PLAN DELETE => " + planNode.plan.id);
			
													deletedPlans++;
														
													doWhenAsyncEnds();
												})
												.catch(function() {
				
												});
											}
										});
									}
								}
								
								function doWhenAsyncEnds() {
									
									if (deletedFolders === foldersCount && deletedItems === itemsCount && deletedPlans === plansCount) {
									
										load(parent ? parent.id : undefined);	
									}
								}
							}
							else {
								
								let planNode = self.planNodes.find(p => p.id === selected.id_);
										
								if (planNode && planNode.plan && planNode.plan.id) {
									
									self.planLibraryPersist.deleteItem(selected.id_)
										.then(function(data) {

											console.log("TREE ITEM DELETE => " + selected.id_);

											self.planLibraryPersist.deletePlan(planNode.plan.id)
												.then(function(data) {

													console.log("PLAN DELETE => " + planNode.plan.id);

													load(parent ? parent.id : undefined);

												})
												.catch(function() {

												});
										})
										.catch(function() {

										})

								}
							}
						}						
					};
		
				}
			}
		}

		function drawResizer() {

			let viewResizer = {};

			viewResizer = {
				id: "plan-lib-resizer-" + self.uid,
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
		
		function afterLoad() {
			
			let viewComposer = $$("view-composer-" + self.uid);

			if (viewComposer) {

				viewComposer.hide();
			}

			if (self.treeView) {

				if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.edit) {

					let planId = self.args.selectedPks[0];
					let node = self.planHierarchies.find(hierarchy => hierarchy.child.plan && hierarchy.child.plan.id === planId);

					if (node) {

						let treeNodeId = node.parent.id + "_" + node.child.id;

						self.treeView.select(treeNodeId);
						$$("view-plan-lib-tree-" + self.uid).hide();	
					}
				}
			}

			$$("loading-" + self.uid).hide();
			$$(self.uid).show();	
		}

		webix.ui(view, $$(self.uid));

		load();
		
	}
}	
