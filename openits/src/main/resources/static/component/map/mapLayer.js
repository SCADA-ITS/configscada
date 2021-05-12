import { util } from "../../lib/util.js";
import TreeView from "../treeview.js";

export default class MapLayer {

	constructor(layers, layerRelationships, onLayersSelectedChange) {

		this.uid = webix.uid();
		this.layers = layers;
		this.layerRelationships = layerRelationships;
		this.treeWindow = undefined;
		
		this.onLayersSelectedChange = onLayersSelectedChange;
	}

	getView() {

		let self = this;
		let view;	

		view = {
			id: "map-layer-" + self.uid,
			rows: [
				getTreeLayers()
			]
		};
		
		function getTreeLayers() {
			
			let events = {};
			
			events[TreeView.EventTypes.ON_CHECK_ITEM] = onCheckItem;
	
			let treeLayers = new TreeView(getMetadata(), getData(), events);
			
			function getMetadata() {
				
				let metadata = {
					id: 1,
					label: "LBL_LAYERS",
					treeOptionValues: [
						{ treeOption: { id: 2}, value: true },	// multiselect
						{ treeOption: { id: 3}, value: true }, 	// sorted
						{ treeOption: { id: 4}, value: true }, 	// cmd_buttons
						{ treeOption: { id: 5}, value: true }, 	// search
						{ treeOption: { id: 6}, value: true },	// expand_all
						{ treeOption: { id: 7}, value: true }	// check_all
					]
				};
				
				return metadata;
			}
			
			function getData() {
				
				let data = util.tree.getNodes(self.layers, self.layerRelationships, { parentNodeId: "parentLayerId", childNodeId: "childLayerId" }, true);
				
				return data;
			}
			
			function onCheckItem(items) {
				
				self.onLayersSelectedChange(items.map(item => parseInt(item.split("_")[1])));
			}
			
			return treeLayers.getView();
		}
		
		return view;
	}
	
	show() {
		
		let mapLayer = $$("map-layer-" + this.uid);
		
		if (mapLayer) {
			
			mapLayer.show();	
		}
	}
}
