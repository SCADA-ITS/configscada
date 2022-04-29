import { util } from "../../lib/util.js";
import GenericModule from "../genericModule.js";
import IncidentTypeContent from "./incidentTypeContent.js";
import TreeView from "../../component/treeview.js";
import OptionButtonList from "../../component/optionButtonList.js";
import MessageBox from "../../component/messageBox.js";
import InputDialogBox from "../../component/inputDialogBox.js";

const TREE_PANEL_WIDTH = 450;
const PICTOGRAM_INCIDENT_TYPE_PATH = "incident_type";
const PICTOGRAM_INCIDENT_TYPE_UNKNOWN = "incident_type/undefined.png";

export default class IncidentTypeModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.args = args;
		
		this.firstGroupId = undefined;
		this.incidentTypes = undefined;
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

		function load(incidentTypeId) {

			webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.incidentTypes).then(function(response) {
				
				if (response) {
					
					self.incidentTypes = response.json();

					drawViewTree();
	
					if (self.treeView) {
	
						if (incidentTypeId) {
							
							let incidentType = self.incidentTypes.find(e => e.id === incidentTypeId);
							
							if (incidentType) {
								
								self.treeView.select(incidentType.incidentGroup.id + "_" + incidentType.id);
	
								let selectedItems = self.treeView.getSelected();
		
								if (Array.isArray(selectedItems) && selectedItems.length === 1) {
		
									let selectedItem = selectedItems[0];
									
									self.treeView.update(incidentTypeId, selectedItem);
		
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
						else {
							
							if (self.firstGroupId) {
								
								self.treeView.select(self.firstGroupId);
								self.treeView.expand(self.firstGroupId);	
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
					{ id: 1, label: "LBL_NEW_ITEM", icon: "mdi mdi-file-plus-outline", default: false },
					{ id: 2, label: "LBL_DELETE", icon: "mdi mdi-delete-outline", default: false }
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

				self.optionButtonList = new OptionButtonList(options, onAcceptOptionButtonList, OptionButtonList.MODE_BUTTONS);

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

		function onAcceptOptionButtonList(buttonId) {

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
				id: "incident-type-tree-" + self.uid,
				header: function() {

					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_INCIDENT_TYPES")}</span>
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

					let incidentType = self.incidentTypes.find(e => e.id === item.id_);

					if (incidentType) {
					
						$$("incident-type-loading-" + self.uid).show();
						$$("view-option-dialog-" + self.uid).hide();
						$$("incident-type-resizer-" + self.uid).hide();
						$$("incident-type-content-" + self.uid).hide();
												
						webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentType, { id: incidentType.id })).then(function(response) {

							if (response) {
								
								incidentType = response.json();
								
								let incidentTypeContent = new IncidentTypeContent(self.config, self.uid, incidentType, onUpdate);

								webix.ui(incidentTypeContent.getView(), $$("incident-type-content-" + self.uid));

								$$("incident-type-loading-" + self.uid).hide();
								$$("incident-type-content-" + self.uid).show();
								$$("incident-type-resizer-" + self.uid).show();	
							}
						});
					}
				};

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
				let mapGroups = new Map();
				let itemsTree = [];
				let itemRelationships = [];
				
				if (Array.isArray(self.incidentTypes)) {
					
					self.incidentTypes.forEach(incidentType => {
						
						let incidentGroup = mapGroups.get(incidentType.incidentGroup.id);
						
						if (!incidentGroup) {
							
							mapGroups.set(incidentType.incidentGroup.id, 
								{
									incidentGroup: incidentType.incidentGroup,
									incidentTypes: [incidentType]
								}
							);
						}
						else {
							
							incidentGroup.incidentTypes.push(incidentType);
						}

					});
					
					mapGroups.forEach((value) => {
						
						itemsTree.push({
							
							id: value.incidentGroup.id,
							label: i18n.t(value.incidentGroup.labelAlias),
							folder: true
						});
						
						if (!self.firstGroupId) {
									
							self.firstGroupId = value.incidentGroup.id + "_" + value.incidentGroup.id;
						}
						
						if (Array.isArray(value.incidentTypes)) {
							
							value.incidentTypes.forEach(incidentType => {
							
								itemRelationships.push({
									
									id: webix.uid(),
									nodeParentId: value.incidentGroup.id,
									nodeChildId: incidentType.id
								})
								
								itemsTree.push({
							
									id: incidentType.id,
									label: incidentType.alias + (incidentType.description ? " (" + incidentType.description + ")" : ""),
									description: incidentType.description,
									image: incidentType.icon ? PICTOGRAM_INCIDENT_TYPE_PATH + "/" + incidentType.icon : PICTOGRAM_INCIDENT_TYPE_UNKNOWN,
									stateColor: incidentType.incidentLevel.color,
									stateLabel: i18n.t(incidentType.incidentLevel.labelAlias),
									folder: false
								})
							})
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
				label: "LBL_INCIDENT_TYPES",
				treeOptionValues: [
					{ value: false, treeOption: { id: 2 } },
					{ value: true, treeOption: { id: 3 } },
					{ value: true, treeOption: { id: 4 } },
					{ value: true, treeOption: { id: 5 } },
					{ value: false, treeOption: { id: 6 } }
				],
				treeCommands: [],
				stomp: {
					topic: undefined,
					adapter: adapterElement2Tree
				}
			}

			if (self.editPermission) {

				metadata.treeCommands = [
					{ id: 1, callbacks: [{ commandId: 2, function: onNewItem }], position: 2, label: "LBL_NEW_ITEM", icon: "mdi mdi-file-plus-outline", default: false, multiselect: false, itemRequired: true, grouped: false },
					{ id: 2, callbacks: [{ commandId: 3, function: onDelete }], position: 3, label: "LBL_DELETE", icon: "mdi mdi-delete-outline", default: false, multiselect: false, itemRequired: true, grouped: false }
				];				
			}
			
			function adapterElement2Tree() {
				
			}
			
			return metadata;
		}

		function onNewItem(selectedPks) {

			
		}

		function onDelete(selectedPks) {

		}

		function drawResizer() {

			let viewResizer = {};

			viewResizer = {
				id: "incident-type-resizer-" + self.uid,
				view: "resizer"
			}

			return viewResizer;
		}

		function drawContent() {

			let viewContent;
			
			viewContent = {
				id: "incident-type-content-" + self.uid,
				css: { "background-color": "white" }		
			};

			return viewContent;
		}

		function drawLoading() {

			let viewLoading = util.ui.getViewDrawLoading();

			viewLoading.id = "incident-type-loading-" + self.uid;
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

					let incidentTypeId = self.args.selectedPks[0];
					let incidentType = self.incidentTypes.find(e => e.id === incidentTypeId);

					if (incidentType) {

						let treeNodeId = incidentType.incidentGroup.id + "_" + incidentType.id;

						self.treeView.select(treeNodeId);
						$$("incident-type-tree-" + self.uid).hide();	
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

