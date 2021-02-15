export default class ListView {

	constructor(title, data, onSelect) {

		this.uid = webix.uid();
		this.title = title;
		this.data = data;
		this.onSelect = onSelect;
	}

	getView() {

		let self = this;

		let view = {
			id: "listview-" + this.uid,
			rows: [
				{ height: 6 },
				{
					view: "toolbar",
					elements: [
						{ width: 4 },
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
						{ width: 4 }
					]
				},
				getList(),
				{ height: 6 }
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

							let image = obj.image;

							if (image == undefined) {
								image = "not_found_50.png";
							}

							return `<image class="list-photo" src="img/${image}" />
									<div class="text">
								  		<span class="list-first-line">${obj.alias} </span>
								  		<span class="list-second-line">${obj.description} </span>
									</div>`
						},
						height: 66
					},
					data: self.data,
					on: {
						onSelectChange: function() {

							if (self.onSelect) {

								self.onSelect(this.getSelectedId(true));
							}
						}
					}
				};
			}

			return list;
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
