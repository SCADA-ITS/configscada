import TreeView from "../../component/treeview.js";

export default class VmsSingallingTree {
	
	constructor(onSelectElement) {

		this.onSelectElement = onSelectElement;
		this.treeView = undefined;
	}
	
	getView() {
		
		let self = this;
		
		function getEvents() {
		
			let events = {};
			
			if (self.onSelectElement) {
			
				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(item) { 
					self.onSelectElement(item.id_) 
				};
				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) { 
					self.onSelectElement(item.id_) 
				};	
			}
			
			return events;	
		}
		
		function getMetadata() {
			
			return {
				
				id: -1,
				label: "LBL_EQUIPMENT_HIERARCHY",
				treeOptionValues: [
					{ value: false, treeOption: { id: 5 } },
					{ value: true, treeOption: { id: 6 } }
				]
			}
		}
		
		function getTreeData() {
			
			let data = [];
			
			// TODO
			
			return data;
		}

		self.treeView = new TreeView(getMetadata(), getTreeData(), getEvents());
		
		return self.treeView.getView();
	}
	
	select(element) {
		
		this.treeView.select(element.id);
	}
}	
