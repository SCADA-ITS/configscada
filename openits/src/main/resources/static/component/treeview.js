import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";

const GridOptions = {
	ICON: 1,
	MULTISELECT: 2,
	SORTED: 3,
	CMD_BUTTONS: 4,
	SEARCH: 5

};

const DefOptionValues = { 2: true, 3: true, 4: true, 5: true };
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
		value: "img/not_found_50.png"
	},
	DISABLED: {
		name: "disabled",
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
		
		let getTreeOptions = function(option) {
	
			let metadata = self.metadata;
			let optionValue = undefined;
	
			if (metadata) {
	
				optionValue = metadata.treeOptionValues.find(e => e.treeOption.id === option);
				
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
		
		let multiSelect = getTreeOptions(GridOptions.MULTISELECT);
		
		if (multiSelect) {
			
			let viewSelectAll = { 
				id: "tb_btn-select-all-" + this.uid, 
				view: "icon", 
				icon: "mdi mdi-check-box-outline", 
				value: i18n.t("LBL_SELECT_ALL"), 
				tooltip: true,
				on: {
					onItemClick: function(id, e) {
						
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
					onItemClick: function(id, e) {
						
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
				onItemClick: function(id, e) {
					
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
				onItemClick: function(id, e) {
					
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
			
			let data = applyProperties(self.data);
			
			let treeView = {
				id: "tree-" + self.uid,
				view: "tree",
				threeState: multiSelect,
				template: function(obj, common) {
					
					let value = obj.value;
					
					
					obj.disabled = getTreeOptions(GridOptions.DISABLED);
									
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

					let nodeValue = undefined;
					
					if (obj.image) {
						
						nodeValue = `<div>	
										<img src="${obj.image}" 
											 onerror="this.onerror=null; this.style.display = 'none'"/>
										<span style="font-weight: ${fontWeight}; letter-spacing: ${letterSpacing}; opacity: ${opacity};]">${value}</span>
									</div>`;	
					}
					else {
						
						nodeValue = `<div>	
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
					}
				}
			};
			
			return treeView;
		}
		
		view = {
			id: "treeview-" + this.uid,
			rows: [
				{ height: 6 },
				searchView,
				tbActionView,
				drawTree(),
				{ height: 6 }
			]
		};
			
		return view;
	}
}

Object.defineProperty(TreeView, 'EventTypes', {
    value: {}
});

Object.defineProperty(TreeView.EventTypes, "ON_CHANGE_TEXT", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(TreeView.EventTypes, "ON_CHECK_ITEM", {
    value: 2,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(TreeView.EventTypes, "ON_SELECT_ITEM", {
    value: 3,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(TreeView.EventTypes, "ON_ITEM_DBL_CLIC", {
    value: 4,
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
