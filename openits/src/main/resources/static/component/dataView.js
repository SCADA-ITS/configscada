import { util } from "../lib/util.js";

const DefOptionValues = { 1: false, 2: true, 3: false, 4: true, 5: 48, 6: false };
const _TRUE = "true";
const _FALSE = "false";

export default class DataView {
	
	constructor(metadata, data, events) {

		this.metadata = metadata;
		this.data = data;
		this.uid = webix.uid();
		this.events = events;
		this.groups = [{
			id: 1, 
			value: i18n.t("LBL_ALL")	
		}];
		
	}

	getView() {

		let self = this;
		let view;
		
		makeGroups();
		
		view = {
			rows: [
				drawToolbar(),
				{ height: 5 },
				{
					cols: [
						{ width: 4 },
						drawDataView(),		
						{ width: 4 }
					]
				}
			]
		};
		
		function makeGroups() {
			
			let groups = new Set();
			
			self.data.forEach(e => {
				
				if (e.group && !groups.has(e.group)) {
				
					groups.add(e.group);
					
					self.groups.push({
						id: self.groups.length + 1,
						value: e.group
					});	
				}
			});
			
			self.groups.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));
		}
		
		function drawToolbar() {
			
			let viewtoolbar = {
			
				view: "toolbar",
				borderless: true,
				elements: function() {
					
					let elements = [];
					
					elements.push({ width: 4 });
					
					if (getOptions(DataView.Options.CMD_BUTTONS)) {
						
						elements.push(drawCommandButtons());	
					}
					
					elements.push({});
					
					if (getOptions(DataView.Options.GROUP)) {
						
						elements.push(drawComboGroup());	
					}
					
					//elements.push({ width: 4 });
					
					if (getOptions(DataView.Options.SEARCH)) {
						
						elements.push(drawSearchBox());	
					}
					
					//elements.push({ width: 4 });
					
					return elements;
				}(),
//				elements: [
//					{ width: 4 },
//					drawCommandButtons(),
//					{},
//					drawSearchBox(),
//					{ width: 4 }
//				]
			};
			
			function drawCommandButtons() {
				
				let viewCommandButtons = {
					
				}
				
				return viewCommandButtons;
			}
			
			function drawComboGroup() {
			
				let viewComboGroup ={
					
					view: "richselect",
					id: "cboGroup-" + self.uid,
					value: 1,
					width: 300,
					options: self.groups,
					on: {
						onChange(newValue, oldValue) {
							
							if (newValue != oldValue) {
								
								let txtSearch = $$("txtSearch-" + self.uid);
								
								if (txtSearch) {
									
									txtSearch.setValue("");
								}
							}
							
							let dataView = $$("dataview-" + self.uid);
							
							if (dataView) {
								
								let group = self.groups.find(e => e.id == newValue);
								
								if (group && group.value) {
									
									if (group.id === 1) {
								
										dataView.filter();
									}
									else {
										
										dataView.filter(function(obj) {  // here it filters all titles from the dataset
					
											let res = false;
											
											if (obj.group) {
												
												res = (obj.group.toLowerCase().indexOf(group.value.toLowerCase()) != -1);
											}
	
											return res;
										});	
									}	
								}
							}
						}
					}
				};
				
				return viewComboGroup;
			}
			
			function drawSearchBox() {
				
				let viewSearchBox = {
					
					view: "search",
					id: "txtSearch-" + self.uid,
					width: 300,
					placeholder: i18n.t("LBL_SEARCH"),
					on: {
						onTimedKeyPress: function() {
							
							let value = this.getValue().toLowerCase(); // input data is derived
							
							$$("dataview-" + self.uid).filter(function(obj) {  // here it filters all titles from the dataset
			
								if (getOptions(DataView.Options.GROUP)) {
								
									let cboGroup = $$("cboGroup-" + self.uid);
									
									if (cboGroup) {
										
										let selectedGroupId = cboGroup.getValue();
										
										if (selectedGroupId != 1) {
											
											return obj.group == cboGroup.getText() && obj.value.toLowerCase().indexOf(value) != -1;
										}
										else {
											
											return obj.value.toLowerCase().indexOf(value) != -1;
										}
									}
								}
								else {
									
									return obj.value.toLowerCase().indexOf(value) != -1;
								}
							});
							
							//$$("tree-" + self.uid).filter("#value#", this.getValue());
						}
					}
				}
				
				return viewSearchBox;
			}
			
			return viewtoolbar;
		}
		
		function drawDataView() {
			
			let iconSize = getOptions(DataView.Options.ICON_SIZE);
			
			let viewDataView = {
				
				view: "dataview",
				id: "dataview-" + self.uid,
  				select: getOptions(DataView.Options.MULTISELECT) ? "multiselect" : true,
  				type: {
				    height: iconSize + 40,
					width: iconSize + 40
					//width: auto
				},
  				template: function(obj) {
					
					let html = `<image style="margin: 10px auto 0px; display: block; height: ${iconSize}px; width: ${iconSize}px" src="${obj.image}" onerror="this.onerror=null;this.src='img/not_found_50.png';" />`;
					
					html += `<div style="text-align: center; font-weight: 500">
						  		<span>${obj.value} </span>
							</div>`; 

					return html;
				},
  				data: self.data,
				on: {
					
					onAfterLoad: function() {
						
						if (getOptions(DataView.Options.SORTED)) {
							
							this.sort("#value#", "asc");	
						}
					}	
				}
			}
			
			return viewDataView;
		}
		
		function getOptions(optionId) {
	
			let metadata = self.metadata;
			let option = undefined;
	
			if (metadata) {
	
				if (Array.isArray(metadata.options) && metadata.options.length > 0) {
				
					option = metadata.options.find(e => e.id === optionId);	
				}
				
				if (option) {
	
					if (option.value === _TRUE) {
	
						return true;
	
					} else if (option.value === _FALSE) {
	
						return false;
	
					} else {
	
						return option.value;
					}
				}
				else {
		
					return DefOptionValues[optionId];
				}
			}
		}
		
		return view;
	}
	
	getSelectedIds() {

		let dataView = $$("dataview-" + this.uid);
		let selectedIds;
		
		if (dataView) {
			
			selectedIds = dataView.getSelectedId(true);	
		}	
		
		return selectedIds;
	}
	
	select(ids) {

		let dataView = $$("dataview-" + this.uid);
		
		if (Array.isArray(ids) && ids.length > 0) {
			
			dataView.select(ids);
			dataView.showItem(ids[0]);
		}	
	}
}

Object.defineProperty(DataView, 'EventTypes', {
    value: {}
});

Object.defineProperty(DataView.EventTypes, "ON_SELECT_ITEM", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(DataView, 'Options', {
    value: {}
});

Object.defineProperty(DataView.Options, "MULTISELECT", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(DataView.Options, "SORTED", {
    value: 2,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(DataView.Options, "CMD_BUTTONS", {
    value: 3,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(DataView.Options, "SEARCH", {
    value: 4,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(DataView.Options, "ICON_SIZE", {
    value: 5,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(DataView.Options, "GROUP", {
    value: 6,
    writable : false,
    enumerable : true,
    configurable : false
});
