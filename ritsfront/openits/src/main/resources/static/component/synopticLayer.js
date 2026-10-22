import { util } from "../lib/util.js";
import TreeView from "../component/treeview.js";

export default class SynopticLayer {

	constructor(uid, metadata, onLayersSelectedChange) {

		this.uid = uid;
		this.layers = metadata.synopticLayers;
		this.layerRelationships = metadata.synopticLayerRelationships;
		this.treeWindow = undefined;
		
		this.onLayersSelectedChange = onLayersSelectedChange;
	}

	getView() {

		let self = this;
		let view;	

		view = {
			id: "synoptic-layer-" + self.uid,
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
						{ treeOption: { id: 6}, value: true }	// expand_all
					]
				};
				
				return metadata;
			}
			
			function getData() {
				
				let data = util.tree.getNodes(self.layers, self.layerRelationships, { parentNodeId: "parentLayerId", childNodeId: "childLayerId" }, true);
				
				return data;
			}
			
			function onCheckItem(items) {
				
				console.log(items.join());
			}
			
			return treeLayers.getView();
		}
		
		return view;
	}
}
