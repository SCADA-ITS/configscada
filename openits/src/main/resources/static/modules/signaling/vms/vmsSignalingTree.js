import TreeView from "../../../component/treeview.js";
import { util } from "../../../lib/util.js";

export default class VmsSignalingTree {
	
	constructor(elements, element, onSelectElement) {

		this.elements = elements;
		this.element = element;
		this.onSelectElement = onSelectElement;
		this.treeView = undefined;
	}
	
	getView() {
		
		let self = this;
		
		function getEvents() {
		
			let events = {};
			
			if (self.onSelectElement) {
			
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
					{ value: true, treeOption: { id: 6 } }
				]
			}
		}
		
		function getTreeData() {
			
			let data = [];
			let items = [];
			let relationships = [];
			
			self.elements.forEach(item => {
			
				let relationship = {};
				
				let elementSubType = items.find(e => e.id === item.elementSubtype.id);
				
				if (elementSubType) {
					
					relationship.parentNodeId = elementSubType.id;
				}
				else {
					
					items.push({
						id: item.elementSubtype.id,
						label: i18n.t(item.elementSubtype.labelAlias)
					});
					
					relationship.parentNodeId = item.elementSubtype.id;
				}
				
				items.push({
					id: item.id,
					label: item.alias
				});
				
				relationship.childNodeId = item.id;
				
				relationships.push(relationship);
				
			});

			
			data = util.tree.getNodes(items, relationships, { parentNodeId: "parentNodeId", childNodeId: "childNodeId" }, false);
			
			return data;
		}

		self.treeView = new TreeView(getMetadata(), getTreeData(), getEvents());
				
		return self.treeView.getView();
	}
	
	select(element) {
		
		this.treeView.select(element.elementSubtype.id + "_" + element.id);
	}
}	
