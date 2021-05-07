import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";

const TreeOptions = {
	ICON: 1,
	MULTISELECT: 2,
	SORTED: 3,
	CMD_BUTTONS: 4,
	SEARCH: 5,
	EXPAND_ALL: 6
};

const DefOptionValues = { 2: false, 3: true, 4: false, 5: true, 6: false };
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
		
		let getItemProps = function(item, prop) {
	
			let propValue = undefined
	
			if (item && item.props) {
	
				propValue = item.props[prop.name];
			}
			
			if (!propValue) {
				
				propValue = prop.value; 
			}
			
			return propValue;
		}
		
		let getBranchItems = function(item, rootItem, branchItems) {
			
			function getRoot(id, data) {
				
				let rootItem = undefined;
				
				if (!data){
					
					data = self.data;
				}
				
				if (id && data) {
					
					rootItem = data.find(e => e.id === id);
					
					let index = 0;
						
					while(!rootItem && index < data.length) {
					
						if(data[index].data && data[index].data.length > 0) {
							
							rootItem = getRoot(id, data[index].data);
						}
						index++;
					}
				}
				return rootItem;
			}
			
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
			
			return branchItems;
		}
		
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
							
							$$("tree-" + self.uid).filter("#value#", this.getValue());				
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
		
		let applyProperties = function(data) {
			
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
	
		let drawTree = function() {
			
			let data = applyProperties(webix.copy(self.data));
			
			console.log(data);
			
			let ids = data.map(e => {
				return e.id
			});
			
			console.log(ids.sort());
			
			
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
						
						
						
						nodeValue = `<div style="margin-left: 30px;">	
										<img src="img/${obj.image}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
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
					},
					onAfterSelect: function(id) {
					    
						let item = this.getItem(id);
						let currentContext = this;
						
						if (item && !multiSelect) {
							
							let selectedItem = item.props && !item.props.folder;
						
							if (self.events) {
							
								// Item selected
								if (selectedItem && self.events[TreeView.EventTypes.ON_SELECT_ITEM]) {
									
									eval(self.events[TreeView.EventTypes.ON_SELECT_ITEM])(item.id, function(value) {
									
										self.enable(value, currentContext);
									});
								}
								// Folder selected
								else if (!selectedItem && self.events[TreeView.EventTypes.ON_SELECT_FOLDER]) {
									
									eval(self.events[TreeView.EventTypes.ON_SELECT_FOLDER])(item.id, function(value) {
									
										self.enable(value, currentContext);
									});
								}
								// Any selected 
								else if (self.events[TreeView.EventTypes.ON_SELECT_ANY]) {
									
									eval(self.events[TreeView.EventTypes.ON_SELECT_ANY])(getBranchItems(item).map(item => item.id), function(value) {
									
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
									
									eval(self.events[TreeView.EventTypes.ON_ITEM_DBL_CLIC])(item.id);
								}
							}
						}
					},
					onItemCheck: function(id) {
						
						let item = this.getItem(id);
						
						if (item && multiSelect && self.events && self.events[TreeView.EventTypes.ON_CHECK_ITEM]) {
									
							let checkedList = this.getChecked();
							let checkedItems = [];
							
							if (Array.isArray(checkedList) && checkedList.length > 0) {
								
								checkedItems = checkedList.filter(e => this.getItem(e).props && !this.getItem(e).props.folder);
							}
							
							eval(self.events[TreeView.EventTypes.ON_CHECK_ITEM])(checkedItems);
						}
					}
				}
			};
			
			return treeView;
		}
		
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
		// view.rows.push({ height: 6 });
			
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
	
	getChecked() {
		
		let tree = $$("tree-" + this.uid);
		let checkedItems = [];
		
		if (tree) {
			
			let multiSelect = this.getTreeOptions(TreeOptions.MULTISELECT);
			
			if (multiSelect) {
				
				let checkedList = tree.getChecked();
				
				if (Array.isArray(checkedList) && checkedList.length > 0) {
				
					checkedItems = checkedList.filter(e => tree.getItem(e).props && !tree.getItem(e).props.folder);	
				}
			}
		}
		
		return checkedItems;
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
	
	select(id) {
		
		let tree = $$("tree-" + this.uid);
		
		if (tree) {
			
			let multiSelect = this.getTreeOptions(TreeOptions.MULTISELECT);
			
			if (!multiSelect) {
				
				tree.select(id);
				tree.showItem(id);
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
	
	unselect(id) {
		
		let tree = $$("tree-" + this.uid);
		
		if (tree) {
			
			if (id) {
				
				tree.unselect(id);	
			}
			else {
				
				tree.unselectAll();
			}
			
			tree.refresh();
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
