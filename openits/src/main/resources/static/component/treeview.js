import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";

const TreeOptions = {
	ICON: 1,
	MULTISELECT: 2,
	SORTED: 3,
	CMD_BUTTONS: 4,
	SEARCH: 5,
	EXPAND_ALL: 6,
	CHECK_ALL: 7
};

const DefOptionValues = { 2: false, 3: true, 4: false, 5: true, 6: false, 7: false };
const DefProperties = {
	TRANSLATE: {
		name: "translate",
		value: false
	},
	FOLDER: {
		name: "folder",
		value: false
	},
	IMAGE: {
		name: "image",
		value: "not_found_50.png"
	},
	DISABLED: {
		name: "disabled",
		value: false
	},
	VALUE_AUX: {
		name: "valueAux",
		value: undefined,
	},
	VALUE_AUX_TRANSLATE: {
		name: "valueAuxTranslate",
		value: false
	}
};

const _TRUE = "true";
const _FALSE = "false";

export default class TreeView {

	constructor(metadata, data, events) {

		this.metadata = metadata;
		this.data = data;
		this.stomp = {};
		this.uid = webix.uid();
		this.events = events;
	}

	getView() {

		let self = this;
		let view;
			
		let searchView = {
			
			view: "toolbar",
			borderless: true,
			elements: [
				{ width: 4 },
				{
					view: "search",
					id: "txtSearch-" + self.uid,
					placeholder: i18n.t("LBL_SEARCH"),
					on: {
						onTimedKeyPress: function() {
							
							let value = this.getValue().toLowerCase(); // input data is derived
							
							$$("tree-" + self.uid).filter(function(obj) {  // here it filters all titles from the dataset
			
								return obj.value.toLowerCase().indexOf(value) != -1;
							});
							
							//$$("tree-" + self.uid).filter("#value#", this.getValue());
						}
					}
				},
				{ width: 4 }
			]
		};
		
		let tbActionView = {
			
			view: "toolbar",
			borderless: true,
			elements: [
				{ width: 4 }
			]
		};
		
		let multiSelect = self.getTreeOptions(TreeOptions.MULTISELECT);
		
		if (multiSelect) {
			
			let viewSelectAll = { 
				id: "tb_btn-select-all-" + this.uid, 
				view: "icon", 
				icon: "mdi mdi-check-box-outline", 
				value: i18n.t("LBL_SELECT_ALL"), 
				tooltip: true,
				on: {
					onItemClick: function() {
						
						$$("tree-" + self.uid).checkAll();				
					}
				}
			}; 
			
			let viewUnselectAll = { 
				id: "tb_btn-unselect-all-" + this.uid, 
				view: "icon", 
				icon: "mdi mdi-checkbox-blank-outline", 
				value: i18n.t("LBL_UNSELECT_ALL"), 
				tooltip: true,
				on: {
					onItemClick: function() {
						
						$$("tree-" + self.uid).uncheckAll();				
					}
				}
			};
			
			tbActionView.elements.push(viewSelectAll);
			tbActionView.elements.push(viewUnselectAll);
		}
		
		let viewExpandAll = { 
			id: "tb_btn-expand-all-" + this.uid, 
			view: "icon", 
			icon: "mdi mdi-expand-all", 
			value: i18n.t("LBL_EXPAND_ALL"), 
			tooltip: true,
			on: {
				onItemClick: function() {
					
					$$("tree-" + self.uid).openAll();				
				}
			}
		};
		
		let viewColapseAll = { 
			id: "tb_btn-collapse-all-" + this.uid, 
			view: "icon", 
			icon: "mdi mdi-collapse-all", 
			value: i18n.t("LBL_COLLAPSE_ALL"), 
			tooltip: true,
			on: {
				onItemClick: function() {
					
					$$("tree-" + self.uid).closeAll();				
				}
			}
		};  
		
		tbActionView.elements.push(viewExpandAll);
		tbActionView.elements.push(viewColapseAll);
			
		tbActionView.elements.push({ width: 4 });
		
		drawCommands();
		
		view = {
			id: "treeview-" + this.uid,
			rows: []
		};
		
		view.rows.push({ height: 6 });
		
		if (self.getTreeOptions(TreeOptions.SEARCH)) {
			
			view.rows.push(searchView);		
		}
		
		if (self.getTreeOptions(TreeOptions.CMD_BUTTONS)) {
		
			view.rows.push(tbActionView);		
		}
		
		view.rows.push(drawTree());
		
		function applyProperties(data) {
			
			if (Array.isArray(data) && data.length > 0) {
				
				return data.map(e => {
					
					let item = {};
					
					item = e;
									
					if (getItemProps(item, DefProperties.TRANSLATE)) {
						
						item.value = i18n.t(item.value);
					}
					
					if (item.data && Array.isArray(item.data) && item.data.length > 0) {
					
						item.data = applyProperties(item.data);	
					}
					
					item.image = getItemProps(item, DefProperties.IMAGE); 
					item.disabled = getItemProps(item, DefProperties.DISABLED);

					return item;
				});
			}
		}
	
		function drawTree() {
			
			let data = applyProperties(webix.copy(self.data));		
			
			let treeView = {
				id: "tree-" + self.uid,
				view: "tree",
				borderless: true,
				css:  multiSelect ? "tree-view-multi" : "tree-view-single",
				select: !multiSelect,
				threeState: multiSelect,
				template: function(obj, common) {
					
					let value = obj.value;
					let valueAux = getItemProps(obj, DefProperties.VALUE_AUX);
					let valueAuxTranslate = getItemProps(obj, DefProperties.VALUE_AUX_TRANSLATE);
					
					if (valueAux && valueAuxTranslate) {
						
						valueAux = i18n.t(valueAux);
					}
					
					obj.disabled = self.getTreeOptions(TreeOptions.DISABLED);
									
					let fontWeight = 500;
					let letterSpacing = "0.2px";
					let opacity = 1;
					if (obj.$level > 1) {
						
						fontWeight = 100;
						letterSpacing = "0px";
					}
					
					if (obj.disabled) {
						
						opacity = 0.5;
					}
					
					if (valueAux) {
						
						value = value + " (" + valueAux + ")";
					}

					let nodeValue = undefined;
					
					if (obj.image) {
						
						let img = obj.image;
						 
						if (obj.image === DefProperties.IMAGE.value) {
						
							if (obj.props.folder) {
								
								img = "tree_folder.png";
							}
							else {
								
								img = "tree_document.png";
							}
						}
						
						nodeValue = `<div style="margin-left: 30px;">	
										<img src="img/${img}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
										<span style="font-weight: ${fontWeight}; letter-spacing: ${letterSpacing}; opacity: ${opacity};]">${value}</span>
									</div>`;	
					}
					else {
						
						nodeValue = `<div style="margin-left: 30px;">
										<span style="font-weight: ${fontWeight}; letter-spacing: ${letterSpacing}; opacity: ${opacity};]">${value}</span>
									</div>`;
					}
					
					if (multiSelect) {
						
						return common.icon(obj, common) + common.checkbox(obj, common) + nodeValue; 
					}
					else {
						
						return common.icon(obj, common) + nodeValue;
					}
			    },
				data: data,
				on: {
					onAfterLoad: function() {
						
						this.sort("#value#", "asc");
											
						let expandAll = self.getTreeOptions(TreeOptions.EXPAND_ALL);
						
						if (expandAll) {
							
							this.openAll();
						}
						
						let checkAll = self.getTreeOptions(TreeOptions.CHECK_ALL);
						let multiselect = self.getTreeOptions(TreeOptions.MULTISELECT);
						
						if (checkAll && multiselect) {
							
							this.checkAll();
						}
					},
					onAfterSelect: function(id) {
					    
						let item = this.getItem(id);
						let currentContext = this;
						
						if (item && !multiSelect) {
							
							let selectedItem = item.props && !item.props.folder;
						
							if (self.events) {
							
								// Item selected
								if (selectedItem && self.events[TreeView.EventTypes.ON_SELECT_ITEM]) {
									
									eval(self.events[TreeView.EventTypes.ON_SELECT_ITEM])(item, function(value) {
									
										self.enable(value, currentContext);
									});
								}
								// Folder selected
								else if (!selectedItem && self.events[TreeView.EventTypes.ON_SELECT_FOLDER]) {
									
									eval(self.events[TreeView.EventTypes.ON_SELECT_FOLDER])(item, function(value) {
									
										self.enable(value, currentContext);
									});
								}
								// Any selected 
								else if (self.events[TreeView.EventTypes.ON_SELECT_ANY]) {
									
									eval(self.events[TreeView.EventTypes.ON_SELECT_ANY])(getBranchItems(item), function(value) {
									
										self.enable(value, currentContext);
									});
								}
							}
						}
					},
					onItemDblClick: function(id) {
					    
						let item = this.getItem(id);
						
						if (item && !multiSelect) {
							
							let selectedItem = item.props && !item.props.folder;
						
							if (self.events) {
							
								// Item selected
								if (selectedItem && self.events[TreeView.EventTypes.ON_ITEM_DBL_CLIC]) {
									
									eval(self.events[TreeView.EventTypes.ON_ITEM_DBL_CLIC])(item);
								}
							}
						}
					},
					onItemCheck: function(id) {
						
						let item = this.getItem(id);
						
						if (item && multiSelect && self.events && self.events[TreeView.EventTypes.ON_CHECK_ITEM]) {
									
							let checkedList = self.getSelected();
							
							eval(self.events[TreeView.EventTypes.ON_CHECK_ITEM])(checkedList);
						}
					}
				}
			};
			
			return treeView;
		}
		
		function drawCommands() {
			
			let viewGroupCommands = [];
			
			if (Array.isArray(self.metadata.treeCommands) && self.metadata.treeCommands.length > 0) {
				
				self.metadata.treeCommands.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
				
				self.metadata.treeCommands.forEach(treeCommand => {
					
					if (treeCommand.grouped) {
						
						drawGroupCommands(treeCommand);
					}
					else {
						
						let viewButtonCommand = { 
							view: "icon", 
							icon: treeCommand.icon, 
							value: i18n.t(treeCommand.label), 
							tooltip: true,
							on: {
								onItemClick: function() {
								
									executeTreeCommand(treeCommand.id);			
								}
							}
						};
						
						tbActionView.elements.push(viewButtonCommand);
					}
					
				});
				
				if (viewGroupCommands.length > 0) {
					
//					let viewButtonGroupCommand = { 
//						view: "icon", 
//						icon: "mdi mdi-dots-vertical", 
//						popup: "groupCommands-" + self.uid
//					};
					
					let viewButtonGroupCommand = {
						view: "menu",
						id: "groupCommands-" + self.uid,
						autowidth: true,
						openAction: "click",
						width: 34,
						height: 34,
						css: "dt-submenu",
						subMenuPos: "down",
						data: [
							{ value: "", icon: "mdi mdi-dots-vertical", submenu: viewGroupCommands },
						],
						on: {
							onMenuItemClick: function(id) {
								
									let item = viewGroupCommands.find(e => e.id == id);
									
									if(item != undefined){
									executeTreeCommand(item.treeCommandId);	
									this.unselectAll();	
								}
							}
						}
				};
							
					tbActionView.elements.push(viewButtonGroupCommand);
					
//					webix.ui({
//  						view: "popup",
//					  	id: "groupCommands-" + self.uid,
//					  	//width:300,
//					  	body:{
//						
//							view: "list", 
//					    	data: viewGroupCommands,
//							borderless: true,
//					    	type: {
//
//								template: function(obj) {
//		
//									if (!obj.default) {
//										return `<span class = 'webix_icon ${obj.icon}'></span> <span style='position: relative; top: -1px; left: 10px;'>${obj.value}</span>`;
//									}
//									else {
//										return `<span class = 'webix_icon ${obj.icon}'></span> <span style='position: relative; top: -1px; left: 10px; font-weight: 500;'>${obj.value}</span>`;
//									}
//								}
//							},
//					    	autoheight:true,
//							autowidth:true,
//					    	select:true,
//							on: {
//								onAfterSelect: function(id) {
//									
//									let item = this.getItem(id);
//									
//									executeTreeCommand(item.treeCommandId);
//									
//									this.unselectAll();
//									this.getParentView().hide();			
//								}
//							}	    
//					  	}
//					});
//					
					
				}
			}
			
			function drawGroupCommands(treeCommand) {
				
				viewGroupCommands.push({

					id: webix.uid(),		
					value: i18n.t(treeCommand.label),
					icon: treeCommand.icon,
					treeCommandId: treeCommand.id  
				});
			}
			
			function executeTreeCommand(treeCommandId) {
			
				let treeCommand = self.metadata.treeCommands.find(e => {
					return e.id === treeCommandId
				});
				
				if (treeCommand) {
				
					util.module.executeCommand(treeCommand, null, function() { return self.getSelected().map(e => e.id_) });
				}
			}
		}
		
		function getItemProps(item, prop) {
	
			let propValue = undefined
	
			if (item && item.props) {
	
				propValue = item.props[prop.name];
			}
			
			if (!propValue) {
				
				propValue = prop.value; 
			}
			
			return propValue;
		}
		
		function getBranchItems(item, rootItem, branchItems) {
			
			let currentItem = undefined;
			
			if (!branchItems) {
				
				branchItems = [];
			}
		
			if (item) {

				if (!rootItem) {
					
					rootItem = getRoot(item.id);
					currentItem = rootItem;
					
				} else {
					
					currentItem = item;
				}

				if(currentItem) {	 
					
					branchItems.push(currentItem);
				
					if(currentItem.data && currentItem.data.length > 0) {
						
						currentItem.data.forEach(e => getBranchItems(e, rootItem, branchItems));
					}
				}

			}
			
			function getRoot(id, data) {
				
				let rootItm = undefined;
				
				if (!data){
					
					data = self.data;
				}
				
				if (id && data) {
					
					rootItm = data.find(e => e.id === id);
					
					let index = 0;
						
					while(!rootItm && index < data.length) {
					
						if(data[index].data && data[index].data.length > 0) {
							
							rootItm = getRoot(id, data[index].data);
						}
						index++;
					}
				}
				return rootItm;
			}
			
			return branchItems;
		}
			
		return view;
	}
	
	getTreeOptions(option) {
	
		let metadata = this.metadata;
		let optionValue = undefined;

		if (metadata) {

			if (Array.isArray(metadata.treeOptionValues) && metadata.treeOptionValues.length > 0) {
			
				optionValue = metadata.treeOptionValues.find(e => e.treeOption.id === option);	
			}
			
			if (optionValue) {

				if (optionValue.value === _TRUE) {

					return true;

				} else if (optionValue.value === _FALSE) {

					return false;

				} else {

					return optionValue.value;
				}
			}
			else {
	
				return DefOptionValues[option];
			}
		}
	}
	
	getSelected() {
		
		let tree = $$("tree-" + this.uid);
		let checkedItems = [];
		
		if (tree) {
			
			let multiSelect = this.getTreeOptions(TreeOptions.MULTISELECT);
			
			if (multiSelect) {
				
				let checkedList = tree.getChecked();
				
				if (Array.isArray(checkedList) && checkedList.length > 0) {
				
					//checkedItems = checkedList.filter(e => tree.getItem(e).props && !tree.getItem(e).props.folder);
					
					checkedList.forEach(e => {
						
						checkedItems.push(tree.getItem(e));
					})
				}
			}
			else {
				
				checkedItems.push(tree.getSelectedItem());
			}
		}
		
		return checkedItems;
	}
	
	getFirstItem() {
		
		let firstItem = undefined;
		
		if (Array.isArray(this.data) && this.data.length > 0) {
			
			firstItem = getData(webix.copy(this.data));
			
			function getData(data) {
				
				let item = undefined;
				
				if (Array.isArray(data) && data.length > 0) {
					
					data.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));
					
					for (let i = 0; !item && i < data.length; i++) {
						
						let d = data[i];
						
						if (d.props.folder === false) {
							
							item = d;
						}
						else {
							
							item = getData(d.data);
						}
					}
				}
				
				return item;
			}
		}
		
		return firstItem;
	}
	
	enable(value, context) {
		
		let tree = context ? context : $$("tree-" + this.uid);
			
		if (tree) {
			
			if (value) {
									
				tree.enable();
			} 
			else {
				
				tree.disable();
			}	
		}
	}
	
	selectAll() {
		
		this.select();
	}
	
	select(id) {
		
		let tree = $$("tree-" + this.uid);
		
		if (tree) {
			
			let multiSelect = this.getTreeOptions(TreeOptions.MULTISELECT);
			
			if (!multiSelect) {
				
				tree.select(id);
				tree.showItem(id);
			}
			else {
				
				if (id) {
					
					tree.checkAll(id);
					tree.showItem(id);
				}
				else {
					
					tree.checkAll();
				}
			}
		}
	}
	
	unselectAll() {
		
		this.unselect();
	}
	
	unselect(id) {
		
		let tree = $$("tree-" + this.uid);
		
		if (tree) {
			
			let multiSelect = this.getTreeOptions(TreeOptions.MULTISELECT);
			
			if (!multiSelect) {
				
				tree.unselect(id);
				tree.showItem(id);
			}
			else {
				
				if (id) {
					
					tree.uncheckAll(id);
					tree.showItem(id);
				}
				else {
					
					tree.uncheckAll();
				}
			}
		}	
	}
	
	expand(id) {
		
		let tree = $$("tree-" + this.uid);
		
		if (tree) {
			
			if (id) {
				
				tree.open(id);
				tree.showItem(id);	
			}
			else {
				
				tree.openAll();
			}
		}
	}
	
	collapse(id) {
		
		let tree = $$("tree-" + this.uid);
		
		if (tree) {
			
			if (id) {
				
				tree.close(id);
				tree.showItem(id);	
			}
			else {
				
				tree.closeAll();
			}
		}
	}
}

Object.defineProperty(TreeView, 'EventTypes', {
    value: {}
});

Object.defineProperty(TreeView.EventTypes, "ON_CHECK_ITEM", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(TreeView.EventTypes, "ON_SELECT_ITEM", {
    value: 2,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(TreeView.EventTypes, "ON_SELECT_ANY", {
    value: 3,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(TreeView.EventTypes, "ON_SELECT_FOLDER", {
    value: 4,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(TreeView.EventTypes, "ON_ITEM_DBL_CLIC", {
    value: 5,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(TreeView, "Modes", {
	value: {}
});

Object.defineProperty(TreeView.Modes, "SINGLE", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(TreeView.Modes, "MULTI", {
    value: 2,
	writable : false,
    enumerable : true,
    configurable : false
});
