const DefaultOptionValues = { 
	1: true,
	2: true,
	3: 66
};

export default class ListView {

	constructor(data, options, events) {

		this.uid = webix.uid();
		this.data = data;
		this.options = options;
		this.events = events;
	}

	getView() {

		let self = this;

		let view = {
			id: "listview-" + this.uid,
			borderless: true,
			rows: [
				//{ height: 6 },
				{
					view: "toolbar",
					hidden: !getOptions(ListView.Options.SEARCH),
					elements: [
						//{ width: 4 },
						{
							view: "search",
							id: "txtSearch-" + this.uid,
							placeholder: i18n.t("LBL_SEARCH"),
							on: {
								onTimedKeyPress: function() {
									
									let list = $$("list-" + self.uid);
									
									let value = this.getValue().toLowerCase(); // input data is derived
									
									list.filter(function(obj) {  // here it filters all titles from the list
									
										let res = false;

										res |= (obj.alias.toLowerCase().indexOf(value) !== -1);
										res |= (obj.description.toLowerCase().indexOf(value) !== -1);
	
										return res;
									});
								}
							}
						},
						//{ width: 4 }
					]
				},
				getList()
				//{ height: 6 }
			]
		};

		function getList() {

			let list;

			if (Array.isArray(self.data) && self.data.length > 0) {

				list = {
					view: "list",
					id: "list-" + self.uid,
					select: true,
					type: {

						template: function(obj) {

							let html = "";
							let marginTop = 0;
							
							if (getOptions(ListView.Options.ICON)) {
								
								let image = obj.image;
																
								html = `<image class="list-photo" src="img/${image}" onerror="this.onerror=null;this.src='img/not_found_50.png';" />`;
							}
							
							if (getOptions(ListView.Options.ROW_HEIGHT) < DefaultOptionValues[ListView.Options.ROW_HEIGHT]) {
								
								marginTop = Math.round((getOptions(ListView.Options.ROW_HEIGHT) - DefaultOptionValues[ListView.Options.ROW_HEIGHT]) / 2); 
							}
							
							html += `<div class="text" style="margin-top: ${marginTop}px">
								  		<span class="list-first-line">${obj.alias} </span>
								  		<span class="list-second-line">${obj.description} </span>
									</div>`; 

							return html;
						},
						height: getOptions(ListView.Options.ROW_HEIGHT)
					},
					data: self.data,
					on: {
						onAfterSelect: function() {
							
							let currentContext = this;
							
							if (self.events && self.events[ListView.EventTypes.ON_SELECT_ROW]) {

								eval(self.events[ListView.EventTypes.ON_SELECT_ROW])(this.getSelectedId(true), function(value) {
									
									self.enable(value, currentContext);
								});
							}
						}
					}
				};
			}

			return list;
		}
		
		function getOptions(option) {
			
			let value = undefined;
			
			if (self.options) {
			
				value = self.options[option];
			}
			
			if (value === undefined) {
					
				value = DefaultOptionValues[option];
			}
			
			return value;
		}

		return view;
	}
	
	getSelectedId() {
		
		let list = $$("list-" + this.uid);
		let selectedId = undefined;
		
		if (list) {
			
			selectedId = list.getSelectedId();
		}
		
		return selectedId;
	}
	
	getSelectedItems() {
		
		let list = $$("list-" + this.uid);
		let selectedItems = [];
		
		if (list != undefined) {

			selectedItems = list.getSelectedItem(true);
		}

		return selectedItems;
	}
	
	enable(value, context) {
		
		let list = context ? context : $$("list-" + this.uid);
		
		if (list) {
			
			if (value) {
									
				list.enable();
			} 
			else {
				
				list.disable();
			}	
		}
	}
	
	select(id) {
		
		let list = $$("list-" + this.uid);
		
		if (list) {
			
			if (id) {
				
				list.select(id);
				list.showItem(id);	
			}
			else {
				
				list.selectAll();
			}
			
			list.refresh();
		}		
	}
	
	selectByIndex(idx) {
		
		let list = $$("list-" + this.uid);
		
		if (list && idx > -1) {
			
			let id = list.getIdByIndex(idx);
			
			if (id) {
				
				this.select(list.getIdByIndex(idx));
				list.showItem(id);	
				list.refresh();	
			}
		}		
	}
	
	unselect(id) {
		
		let list = $$("list-" + this.uid);
		
		if (list) {
			
			if (id) {
				
				list.unselect(id);	
			}
			else {
				
				list.unselectAll();
			}
			
			list.refresh();
		}	
	}

	clear() {
		let list = $$("list-" + this.uid);

		if (list) {

			list.clearAll();
		}	
	}

	add(items) {

		let list = $$("list-" + this.uid);

		if (Array.isArray(items) && items.length > 0) {

			items.forEach(dat => {

				if (list.find(function(e) { return e.id === dat.id; }), true) {

					list.add(dat);
				}
			});
		}
		else if (!Array.isArray(items)) {

			if (dataTable.find(function(e) { return e.id === items.id; }), true) {

				dataTable.add(items);
			}
		}
	}
}

Object.defineProperty(ListView, 'EventTypes', {
	value: {}
});


Object.defineProperty(ListView.EventTypes, 'ON_SELECT_ROW', {
	value: 1,
	writable: false,
	enumerable: true,
	configurable: false
});


Object.defineProperty(ListView, 'Options', {
	value: {}
});


Object.defineProperty(ListView.Options, 'ICON', {
	value: 1,
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(ListView.Options, 'SEARCH', {
	value: 2,
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(ListView.Options, 'ROW_HEIGHT', {
	value: 3,
	writable: false,
	enumerable: true,
	configurable: false
});
