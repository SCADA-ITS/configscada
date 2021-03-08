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
		
		let searchView = {
			
			view: "toolbar",
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
	
		let drawTree = function() {
			
			let multiSelect = getTreeOptions(GridOptions.MULTISELECT);
			
			let treeView = {
				id: "tree-" + self.uid,
				view: "tree",
				threeState: multiSelect,
				template: function(obj, common) {
					
					let value = obj.value;
					let image = obj.image;
					
					if (obj.translate) {
						
						value = i18n.t(value);
					}
					
					if (image == undefined) {
						image = "img/not_found_50.png";
					}
					
					let nodeValue = `<div>	
						<img class="table-field-icons" src="${image}" 
							 onerror="this.onerror=null; this.style.display = 'none'"/>
						<span>${value}</span>
					</div>`;
					
					if (multiSelect) {
						
						return common.icon(obj, common) + common.checkbox(obj, common) + nodeValue; 
					}
					else {
						
						return common.icon(obj, common) + nodeValue;
					}
			    },
				data: self.data
			};
			
			return treeView;
		}
		
		view = {
			id: "treeview-" + this.uid,
			rows: [
				{ height: 6 },
				searchView,
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
