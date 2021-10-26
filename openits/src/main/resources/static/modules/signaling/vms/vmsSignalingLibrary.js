import { util } from "../../../lib/util.js";
import VmsSignalingComposer from "./vmsSignalingComposer.js";
import VmsSignalingLibraryPersist from "./vmsSignalingLibraryPersist.js";
import TreeView from "../../../component/treeview.js";
import OptionButtonList from "../../../component/optionButtonList.js";
import MessageBox from "../../../component/messageBox.js";
import InputDialogBox from "../../../component/inputDialogBox.js";

const TREE_PANEL_WIDTH = 325;

export default class VmsSignalingLibrary {

	constructor(config, element, onAccept) {
		
		this.config = config;
		this.element = element;
		this.onAccept = onAccept;
		
		this.uid = webix.uid();
		this.vmsSignalingLibraryPersist = undefined;
		this.containerId = undefined;
		
		this.treeView = undefined;
		this.composer = undefined;
		this.undoJsonData = undefined;
		this.optionButtonList = undefined;
		
		this.editLibraryPermission = util.module.isAllowedPermission(config.requiredPermissions.editLibrary);
		//this.editLibraryPermission = true;
	}

	show() {

		let self = this;	
		let view = {};
		
		view = {
			cols: [
				{
					id: self.uid,
					rows: [
						drawHeader(),
						{
							cols: [				
								drawTree(),
								drawResizer(),
								drawContent()
							]		
						},
						{
							cols: [
								{ width: 20 },
								drawActionButtons(),
								{ width: 20 }		
							]
						},
						{
							height: 10
						}
					]
				}
			]
		}
		
		
		function load(libraryNodeId) {
		
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.vmsLibraryBySubtype, { id: self.element.elementSubtype.id })).then(function(response) {
			
				if (response) {
					
					let vmsLib = response.json();
					
					if (vmsLib && vmsLib.vmsLibrary) {
						
						self.vmsSignalingLibraryPersist = new VmsSignalingLibraryPersist(self.config, vmsLib.vmsLibrary.id);
						
						drawViewTree(vmsLib.vmsLibrary.vmsLibraryNodes, vmsLib.vmsLibrary.vmsLibraryHierarchies);
					}
					
					if ($$(self.uid)) {
					
						if (self.treeView) {
							
							self.treeView.select("root");
							self.treeView.expand("root");
								
							if (libraryNodeId) {
				
								self.treeView.select(libraryNodeId);
								
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
				}	
			});
		}
		
		function drawHeader() {
			
			let viewHeader = {};
			let label = i18n.t("LBL_VMS_LIBRARY");
			let description = i18n.t("LBL_VMS_LIBRARY_DESCRIPTION");

			viewHeader = {

				css: "global-header",
				cols: [
					{
						view: "label",
						label: "<img src='img/books.png' class='grid-icon'/>",
						height: 75,
						css: "icon"
					},
					{
						view: "label",
						height: 75,
						width: 900,
						template: "<span>" + label + "</span> <br> <span style='font-size: 13px'>" + description + "</span>",
						css: "label"
					}
				]
			};

			return viewHeader;
		}
		
		function drawTree() {
			
			let viewTree = {};
			
			viewTree = {
				id: "view-signaling-lib-tree-" + self.uid,
				header: function() {
					
					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_CATEGORIES")}</span>
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
				let headerMain = $$("headerMain-" + self.uid);
				let tbMain = $$("tbMain-" + self.uid);
				let viewComposer = $$("view-composer-" + self.uid);
				let lblSelected = $$("label-lib-selected-" + self.uid);
				let btnSelect = $$("btn-select-" + self.uid);
				let btnSave = $$("btn-save-" + self.uid);
				let btnUndo = $$("btn-undo-" + self.uid);
				let viewOptionDialog = $$("view-option-dialog-" + self.uid);

				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {

					let vmsItemLib = items.find(e => e.id === item.id_);

					if (vmsItemLib && self.composer && lblSelected && viewComposer && headerMain && tbMain && btnSelect && btnUndo && btnSave) {

						headerMain.show();
						tbMain.show();
						viewComposer.show();
						viewOptionDialog.hide();

						if (vmsItemLib.signaling) {

							self.composer.update(JSON.parse(vmsItemLib.signaling));
							lblSelected.setValue(`<span class="alarm-view-header-details-text">${vmsItemLib.name}</span>`);

							self.undoJsonData = JSON.parse(vmsItemLib.signaling);
						}
						else {

							self.composer.clear();
							lblSelected.setValue(`<span class="alarm-view-header-details-text">${vmsItemLib.name + " (" + i18n.t("LBL_NO_CONFIG") + ")"}</span>`);

							self.undoJsonData = self.composer.getJsonDataClear();
						}

						btnSelect.enable();
						btnSave.disable();
						btnUndo.disable();
					}
				};

				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(folder) {
					
					if (folder.value) {

						lblSelected.setValue(`<span class="alarm-view-header-details-text">${folder.value}</span>`);

					} else {

						lblSelected.setValue(`<span class="alarm-view-header-details-text">${folder.value + " (" + i18n.t("LBL_NO_CONFIG") + ")"}</span>`);
					}
						
					if (viewComposer && headerMain && tbMain && btnSelect && viewOptionDialog) {

						headerMain.show();
						viewOptionDialog.show();
						tbMain.hide();
						viewComposer.hide();

						btnSelect.disable();
					}
				}

				return events;
			}

			function getTreeData() {

				let data = [];

				let itemsTree = items.map(e => {

					return {

						id: e.id,
						label: e.name,
						signaling: e.signaling,
						folder: !e.signaling
					}
				});

				data = util.tree.getNodes(itemsTree, relationships, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);

				return data;
			}

			self.treeView = new TreeView(getMetadata(), getTreeData(), getEvents());

			treeView.cols.push(self.treeView.getView());

			webix.ui(treeView, $$("view-tree-" + self.uid));
		}

		function getMetadata() {

			let metadata;
			
			metadata = {

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
					{ id: 1, callbacks: [{ commandId: 1, function: onNewFolder }], position: 1, label: "LBL_NEW_FOLDER", icon: "mdi mdi-folder-plus-outline", default: false, multiselect: false, itemRequired: true, grouped: false, disabled: !self.editLibraryPermission },
					{ id: 2, callbacks: [{ commandId: 2, function: onNewItem }], position: 2, label: "LBL_NEW_ITEM", icon: "mdi mdi-file-plus-outline", default: false, multiselect: false, itemRequired: true, grouped: false, disabled: !self.editLibraryPermission },
					{ id: 3, callbacks: [{ commandId: 3, function: onDelete }], position: 3, label: "LBL_DELETE", icon: "mdi mdi-delete-outline", default: false, multiselect: false, itemRequired: true, grouped: false, disabled: !self.editLibraryPermission }
				]
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

				let i = new InputDialogBox (i18n.t("LBL_NEW_FOLDER"), i18n.t("LBL_NEW_FOLDER_DESCRIPTION"), onAccept, true, null, DataType.ALPHANUMERIC);
				i.show();
				
				function onAccept(result){
					
					if (self.vmsSignalingLibraryPersist) {

						self.vmsSignalingLibraryPersist.createFolder(result, parent.id_)
							.then(function(data) {

								load(parent.id_ ? parent.id_ + "_" + data.id : data.id + "_" + data.id);
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

				let i = new InputDialogBox (i18n.t("LBL_NEW_SIGNALING"), i18n.t("LBL_NEW_SIGNALING_DESCRIPTION"), onAccept, true, null, DataType.ALPHANUMERIC);
				i.show();
				
				function onAccept(result) {
					
					self.vmsSignalingLibraryPersist.saveItem(undefined, result, JSON.stringify(self.composer.getJsonDataClear()), parent.id_)
						.then(function(data) {

							load((parent && parent.id != "root") ? parent.id_ + "_" + data.id : data.id + "_" + data.id);
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

					} else {

						let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_DIALOG_DELETE_VMS_SIGNALING_DESCRIPTION"), onAccept, null);
						ms.show();
						
						function onAccept() {

							if (selected.id_ === selectedPks[0]) {

								parent = self.treeView.getParent(selected);
							}

							if (selected.id_ === selectedPks[0]) {

								self.vmsSignalingLibraryPersist.deleteItem(selected.id_)
									.then(function(data) {

										load(parent ? parent.id : undefined);
									});
							}		
						}
					}
				}

			}
			
			return metadata;
		}

		function drawResizer() {

			let viewResizer = {};

			viewResizer = {
				id: "view-signaling-lib-resizer-" + self.uid,
				view: "resizer"
			}

			return viewResizer;
		}

		function drawContent() {

			let viewContent = {
				id: "view-signaling-lib-content-" + self.uid,
				css: { "background-color": "white" },
				rows: [

					getViewContentHeader(),
					getViewContentToolbar(),
					{
						cols: [
							{},
							getViewComposer(),
							getViewOptionButtonList(),
							{}
						]
					},
					{}
				]
			};

			function getViewContentHeader() {
			
				let viewContentHeader;
				
				viewContentHeader = {
					id: "headerMain-" + self.uid,
					hidden: true,
					cols: [
						{
							view: "label",
							label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-view-headline"></i></div>`,
							align: "center",
							height: 50,
							width: 56
						},
						{
							id: "label-lib-selected-" + self.uid,
							view: "label",
							label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_NO_SELECTED")}</span>`,
							align: "left",
							height: 50
						},
						{}
					]
				};
				
				return viewContentHeader;
			}
			
			function getViewContentToolbar() {
			
				const TOOLBAR_BUTTONS_WIDTH = 130;
			
				let viewContentToolbar = {
					id: "tbMain-" + self.uid,
					height: 39,
					css: {"margin-left":"2px !important"},
					cols: []
				};
				
				viewContentToolbar.cols.push({ width: 20 });
				
				viewContentToolbar.cols.push(
					{ 
						view: "button",
						id: "btn-save-" + self.uid,
						type: "icon",
						disabled: true, 
						icon: "mdi mdi-content-save", 
						css: "toolbar-button-text", 
						width: TOOLBAR_BUTTONS_WIDTH, 
						label: i18n.t("LBL_SAVE"), 
						tooltip: i18n.t("LBL_SAVE"),
						on: {
							onItemClick() {
		
								if (self.composer) {
									
									let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_DIALOG_SAVE_CHANGES_DESCRIPTION"), onAccept, null);
									ms.show();
									
									function onAccept() {

										if (self.composer) {
											
											let currentJsonData = self.composer.jsonData;
											let selectedItems = self.treeView.getSelected();
											
											if (currentJsonData && self.vmsSignalingLibraryPersist && Array.isArray(selectedItems) && selectedItems.length === 1) {
												
												let selectedItem = selectedItems[0];
												
												let parent = self.treeView.getParent(selectedItem);
												let parentId = undefined;
												
												if (parent) {
													
													parentId = parent.id_; 
												}
												
												self.vmsSignalingLibraryPersist.saveItem(selectedItem.id_, selectedItem.value, JSON.stringify(currentJsonData), parentId)
													.then(function(data) {
														
														load(parentId ? parentId + "_" + data.id : data.id + "_" + data.id);
													});
											}
										
											let btnSave = $$("btn-save-" + self.uid);
											let btnUndo = $$("btn-undo-" + self.uid);
											
											if (btnSave) {
												
												btnSave.disable();
											}	
											
											if (btnUndo) {
												
												btnUndo.disable();
											}
										}										
									}														
								}
							}
						} 
					}
				);
				
				viewContentToolbar.cols.push(
					{ 
						view: "button", 
						id: "btn-undo-" + self.uid,
						type: "icon", 
						disabled: true,
						icon: "mdi mdi-undo", 
						css: "toolbar-button-text", 
						width: TOOLBAR_BUTTONS_WIDTH, 
						label: i18n.t("LBL_UNDO"), 
						tooltip: i18n.t("LBL_UNDO"),
						on: {
							onItemClick() {
		
								if (self.composer) {
									
									let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_DIALOG_UNDO_CHANGES_DESCRIPTION"), onAccept, null);
									ms.show();
									
									function onAccept() {

										if (self.undoJsonData) {
											
											self.composer.update(self.undoJsonData);
										
											let btnSave = $$("btn-save-" + self.uid);
											let btnUndo = $$("btn-undo-" + self.uid);
											
											if (btnSave) {
												
												btnSave.disable();
											}	
											
											if (btnUndo) {
												
												btnUndo.disable();
											}
										}																			
									}							
								}
							}
						} 
					}
				);
				
				viewContentToolbar.cols.push({});
				
				return viewContentToolbar;
			}
			
			function getViewComposer() {
				
				let viewComposer;
			
				self.composer = new VmsSignalingComposer(self.config, self.element, null, onUpdateComposer, onAfterRenderComposer);
				
				viewComposer = {
					id: "view-composer-" + self.uid,
					cols: [
						{ width: 30 },
						self.composer.getView(),
						{ width: 30 }
					]
				}

				return viewComposer;
			}

			function getViewOptionButtonList() {

				let actions = getMetadata().treeCommands;

				if (Array.isArray(actions) && actions.length > 0) {

					let options = [];

					for (let action of actions) {
						
						options.push({
							id: action.id,
							label: i18n.t(action.label),
							description: i18n.t(action.label.toUpperCase() + "_DESCRIPTION"),
							default: action.default,
							disabled: action.disabled,
							icon: action.icon
						});
					}

					self.optionButtonList = new OptionButtonList(options, onAcceptOptionButtonList, OptionButtonList.MODE_BUTTONS);

					let viewOptionButtonList;
					
					viewOptionButtonList = {
						id: "view-option-dialog-" + self.uid,
						rows: [
							{height: 30},
							{						
								cols: [
									{ width: 30 },
									self.optionButtonList.getView(),
									{ width: 30 }
								]}							
						]
					}

					return viewOptionButtonList;
				}

			}

			return viewContent;
		}
		
		function onAcceptOptionButtonList(buttonId){
			
			let actions = getMetadata().treeCommands;
			let itemsSelected = self.treeView.getSelected();
			let callbackAction = actions.find(e => e.id === buttonId);
			let selectedPks = [];
			
			if (callbackAction){
				selectedPks.push(itemsSelected[0].id_);
				callbackAction.callbacks[0].function(selectedPks);
			}
		}

		function onAfterRenderComposer() {

			if (self.composer) {
				
				self.composer.clear();	
			}
		}
		
		function onUpdateComposer() {
			
			if (self.composer) {
				
				let btnSave = $$("btn-save-" + self.uid);
				let btnUndo = $$("btn-undo-" + self.uid);
				
				if (btnSave) {
					
					btnSave.enable();	
				}
				
				if (btnUndo) {
					
					btnUndo.enable();
				}
			}
		}
		
		function drawActionButtons() {
			
			let viewToolbarActions = {};
			
			viewToolbarActions = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: [
					{ 
						view: "button",
						css: "dialog-cancel", 
						width: 130,
						height: 35, 
						label: i18n.t("LBL_CANCEL"),
						on: {
							onItemClick() {
							
								$$(self.containerId).close();
							}
						}
					},
					{},
					{ 
						id: "btn-select-" + self.uid,
						view: "button", 
						css: "dialog-accept",
						width: 130,
						height: 35,
						label: i18n.t("LBL_SELECT"),
						on: {
							onItemClick() {
		
								if (self.onAccept && self.composer) {
									
									if (Array.isArray(self.composer.jsonData) && self.composer.jsonData.length > 0) {
										
										self.onAccept(self.composer.jsonData);
										
										$$(self.containerId).close();	
									}
								}
							}
						}
					}
				]
			};
			
			return viewToolbarActions;
		}
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_VMS_LIBRARY") + " - " + i18n.t(self.element.elementSubtype.labelAlias), view, 650, 1050, true);
		
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
