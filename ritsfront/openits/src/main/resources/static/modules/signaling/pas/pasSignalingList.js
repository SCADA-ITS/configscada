import ListView from "../../../component/listview.js";

export default class PasSignalingList {
	
	constructor(elements, onSelectElement) {

		this.elements = elements;
		this.onSelectElement = onSelectElement;
		this.listView = undefined;
	}
	
	getView() {
		
		let self = this;
		
		function getEvents() {
		
			let events = {};
			
			if (self.onSelectElement) {
			
				events[ListView.EventTypes.ON_SELECT_ROW] = function(item) {

					self.onSelectElement(item);
				};	
			}
			
			return events;	
		}

		function getData() {
		
			let data = [];

			if (Array.isArray(self.elements) && self.elements.length > 0) {

				data = self.elements.map(e => {
					return {
						id: e.id,
						alias: e.alias,
						description: e.description,
						image: "img/grid/empty.png"
					};
				});
			}

			return data;
		}

		self.listView = new ListView(getData(), {}, getEvents());
		
		return self.listView.getView();
	}
	
	select(element) {
		
		this.listView.select(element.id);
	}
}	
