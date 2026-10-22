import TreeView from "../../component/treeview.js";

export default class EquipmentViewerTreeHierarchy {
	
	constructor(parent, element, onSelectElement) {

		this.parent = parent;
		this.element = element;
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
			
			let parent = {
				id: self.element.id,
				id_: self.element.id,
				value: self.element.alias,
				data: [],
				props: {
					folder: true,
					translate: false
				}
			}
			
			if (Array.isArray(self.element.childs) && self.element.childs.length > 0) {
				
				self.element.childs.forEach(e => {
					
					let child = {
						
						id: e.id,
						id_: e.id,
						value: e.alias,
						props: {}
					}
					
					parent.data.push(child);
				});
			}
			
			data.push(parent);
			
			return data;
		}

		self.treeView = new TreeView(getMetadata(), getTreeData(), getEvents());
		
		return self.treeView.getView();
	}
	
	select(element) {
		
		this.treeView.select(element.id);
	}
}	
