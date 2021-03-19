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
								
								if (image == undefined) {
									image = "not_found_50.png";
								}
								
								html = `<image class="list-photo" src="img/${image}" />`;
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
						onSelectChange: function() {
							
							if (self.events && self.events[ListView.EventTypes.ON_SELECT_ROW]) {
								
								eval(self.events[ListView.EventTypes.ON_SELECT_ROW])(this.getSelectedId(true));
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
	
	select(id) {
		
		let list = $$("list-" + this.uid);
		
		if (list) {
			
			list.select(id);
			list.refresh();
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
