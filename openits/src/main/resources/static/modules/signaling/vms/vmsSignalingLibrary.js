import VmsSignalingComposer from "./vmsSignalingComposer.js";
import VmsSignalingLibraryPersist from "./vmsSignalingLibraryPersist.js";
import TreeView from "../../../component/treeview.js";
import { util } from "../../../lib/util.js";

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
	}

	show() {

		let self = this;	
		let view = {};
		
		view = {
			cols: [
				{
					id: "loading-" + self.uid,
					hidden: false,
					rows: [
						util.ui.getViewDrawLoading()
					]
				},
				{
					id: self.uid,
					hidden: true,
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
		
			if ($$(self.uid)) {
	
				$$("loading-" + self.uid).show();
				$$(self.uid).hide();
			}
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.vmsLibraryBySubtype, { id: self.element.elementSubtype.id })).then(function(response) {
			
				if (response) {
					
					let vmsLib = response.json();
					
					if (vmsLib && vmsLib.vmsLibrary) {
						
						self.vmsSignalingLibraryPersist = new VmsSignalingLibraryPersist(self.config, vmsLib.vmsLibrary.id);
						
						drawViewTree(vmsLib.vmsLibrary.vmsLibraryNodes, vmsLib.vmsLibrary.vmsLibraryHierarchies);
						
						if (libraryNodeId) {
							
							if(self.treeView) {
				
								self.treeView.select(libraryNodeId)
							}
						}
					}
					
					if ($$(self.uid)) {

						$$("loading-" + self.uid).hide();
						$$(self.uid).show();
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
				let viewComposer = $$("view-composer-" + self.uid);
				
				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) { 
					
					let vmsItemLib = items.find(e => e.id === item.id_);
					let lblSelected = $$("label-lib-selected-" + self.uid);
					
					let btnAccept = $$("btn-accept-" + self.uid);
					
					if (vmsItemLib && self.composer && lblSelected && viewComposer && btnAccept) {
						
						viewComposer.show();
						
						if (vmsItemLib.signaling) {
						
							self.composer.update(JSON.parse(vmsItemLib.signaling));
							lblSelected.setValue(`<span class="alarm-view-header-details-text">${vmsItemLib.name}</span>`);
							
							btnAccept.enable();

						}
						else {
							
							self.composer.clear();
							lblSelected.setValue(`<span class="alarm-view-header-details-text">${vmsItemLib.name + " (" + i18n.t("LBL_NO_CONFIG") + ")"}</span>`);
							
							btnAccept.disable();
						}
					}
				};
				
				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(folder) {
					
					if (viewComposer) {
						
						viewComposer.hide();
					}
				}
				
				return events;	
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
						{ value: false, treeOption: { id: 6 } }
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
					width: 500,
					
				}).then(function(result){
				  	
					// TODO: POST with result
					
					if (self.vmsSignalingLibraryPersist) {
						
						self.vmsSignalingLibraryPersist.createFolder(result, parent.id_)
						.then(function(data) {
							
							load(data.id);
						})
						.catch(function() {
							
						});	
					}
					
					
				}).fail(function(){
				  	
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
					title: i18n.t("LBL_NEW_SIGNALING"),
				  	text: i18n.t("LBL_NEW_SIGNALING_DESCRIPTION"),
				  	ok: i18n.t("LBL_ACCEPT"),
				  	cancel: i18n.t("LBL_CANCEL"),
				  	input: {
				    	required: true,
				    	placeholder: i18n.t("LBL_NEW_SIGNALING_NAME")
				  	},
					width: 500,
					
				}).then(function(result){
				  	
					// TODO: POST with result
					
					self.vmsSignalingLibraryPersist.saveItem(undefined, result, undefined, parent.id_)
					.then(function(data) {
						
						load(data.id);
					})
					.catch(function() {
						
					});
					
				}).fail(function(){
				  	
				});	
			}
			
			function onDelete(selectedPks) {
				
				let itemsSelected = self.treeView.getSelected();
					
				if (Array.isArray(itemsSelected) && itemsSelected.length > 0 && Array.isArray(selectedPks) && selectedPks.length > 0) {
					
					webix.confirm({
						title: i18n.t("LBL_DIALOG_DELETE_VMS_SIGNALING"),
						ok: i18n.t("LBL_YES"),
						cancel: i18n.t("LBL_NO"),
						text: i18n.t("LBL_DIALOG_DELETE_VMS_SIGNALING_DESCRIPTION"),
						width: 600
					}).then(function() {
						
						let selected = itemsSelected[0];
					
						if (selected.id_ === selectedPks[0]) {
						
							self.vmsSignalingLibraryPersist.deleteItem(selected.id_);
						}
					});
				}
			}
			
			function getTreeData() {
				
				let data = [];
				
				let itemsTree = items.map(e => {
						
					return {
						
						id: e.id,
						label: e.name,
						signaling: e.signaling,
						enabled: e.enabled,
						visible: e.visible
					}
				});
				
				data = util.tree.getNodes(itemsTree, relationships, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);
				
				return data;
			}
			
			self.treeView = new TreeView(getMetadata(), getTreeData(), getEvents());
			
			treeView.cols.push(self.treeView.getView());
			
			webix.ui(treeView, $$("view-tree-" + self.uid));
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
				css: { "background-color": "white"},
				rows: [
					
					getViewContentHeader(),
					getViewContentToolbar(),
					{
						cols: [
							{},
							getViewComposer(),
							{}
						]
					},
					{}
				]
			};
			
			function getViewContentHeader() {
			
				let viewContentHeader = {
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
			
				let viewContentToolbar = {
					view: "toolbar",
					height: 50,
					id: "tbMain-" + self.uid,
					borderless: true,
					elements: []
				}
				
				viewContentToolbar.elements.push({ width: 20 });
				//viewContentToolbar.elements.push({ view: "button", type: "icon", icon: "mdi mdi-content-save-outline", css: "toolbar-button-text", width: 130, label: i18n.t("LBL_SAVE"), tooltip: i18n.t("LBL_SAVE_TO_LIBRARY") });
				
				return viewContentToolbar;
			}
			
			function getViewComposer() {
				
				let viewComposer;
			
				self.composer = new VmsSignalingComposer(self.config, self.element, onUpdateComposer, onAfterRenderComposer);
				
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
			
			return viewContent;
		}
		
		function onAfterRenderComposer() {
			
			self.composer.clear();
		}
		
		function onUpdateComposer() {
			
			// TODO
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
						id: "btn-accept-" + self.uid,
						view: "button", 
						css: "dialog-accept",
						disabled: true,
						width: 130,
						height: 35,
						label: i18n.t("LBL_ACCEPT"),
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
			
		}, null, null, 300);
	}
}	
