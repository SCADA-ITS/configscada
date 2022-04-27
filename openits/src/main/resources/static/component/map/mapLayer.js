import { util } from "../../lib/util.js";
import TreeView from "../treeview.js";

const DEFAULT_TREE_OPTIONS = [
	{ treeOption: { id: 2}, value: true },	// multiselect
	{ treeOption: { id: 3}, value: true }, 	// sorted
	{ treeOption: { id: 4}, value: true }, 	// cmd_buttons
	{ treeOption: { id: 5}, value: true }, 	// search
	{ treeOption: { id: 6}, value: true },	// expand_all
	{ treeOption: { id: 7}, value: true }	// check_all
];
	
export default class MapLayer {
	
	static get TYPE_ELEMENT() { return { id: "ELEMENT_ROOT", label: i18n.t("LBL_EQUIPMENTS"), image: "tree_equipment.png", prefix: "ELEMENT_"} }
	static get TYPE_INCIDENT() { return { id: "INCIDENT_ROOT", label: i18n.t("LBL_INCIDENTS"), image: "tree_incident.png", prefix: "INCIDENT_"} }

	constructor(data, onLayersSelectedChange, treeOptions) {

		this.uid = webix.uid();
		
		this.data = data;
		this.onLayersSelectedChange = onLayersSelectedChange;
		this.treeOptions = treeOptions ? treeOptions : DEFAULT_TREE_OPTIONS;

		this.treeWindow = undefined;
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
			
			if (self.treeOptions.find(e => e.treeOption.id === 2).value) {
				
				events[TreeView.EventTypes.ON_CHECK_ITEM] = onCheckItem;	
			}
			else {
				
				events[TreeView.EventTypes.ON_SELECT_ITEM] = onSelectItem;
			}
	
			let treeLayers = new TreeView(getMetadata(), getData(), events);
			
			function getMetadata() {
				
				let metadata;
				
				metadata = {
					id: 1,
					label: "LBL_LAYERS",
					treeOptionValues: self.treeOptions
				};
				
				return metadata;
			}
			
			function getData() {
				
				let data = [];
				
				if (Array.isArray(self.data) && self.data.length > 0) {
					
					let layers = [];
					let layerRelationships = [];
					
					if (self.data.length === 1) {
						
						layers = getLayersWithPrefix(self.data[0].layers, self.data[0].layerType.prefix);						
						layerRelationships = getLayersRelWithPrefix(self.data[0].layerRelationships, self.data[0].layerType.prefix);					
					}
					else {
						
						self.data.forEach(dat => {
							
							if (Array.isArray(dat.layers) && dat.layers.length > 0) {
								
								let srcLayers = getLayersWithPrefix(dat.layers, dat.layerType.prefix);
								let srcLayersRel = getLayersRelWithPrefix(dat.layerRelationships, dat.layerType.prefix);
								
								layers.push(...srcLayers);
								
								layers.push({
									id: dat.layerType.id,
									label: dat.layerType.label,
									image: dat.layerType.image
								});
								
								layerRelationships.push(...srcLayersRel);
								
								let setChildLayers = new Set(srcLayersRel.map(e => e.childLayerId));
								let setParentLayers = new Set(srcLayersRel.map(e => e.parentLayerId));
								
								if (setChildLayers.size > 0 && setParentLayers.size > 0) {
									
									let allLayers = new Set([...setChildLayers, ...setParentLayers]);
	
									let rootLayers = Array.from(allLayers).filter(childLayer => setParentLayers.has(childLayer) && !setChildLayers.has(childLayer));
								
									if (Array.isArray(rootLayers) && rootLayers.length > 0) {
		
										rootLayers.forEach(rootLayer => {
											
											layerRelationships.push({
											
												id: webix.uid(),
												parentLayerId: dat.layerType.id,
												childLayerId: rootLayer
											});
										})
									}
								}
								else {
										
									srcLayers.forEach(srcLayer => {
										
										layerRelationships.push({
											
											id: webix.uid(),
											parentLayerId: dat.layerType.id,
											childLayerId: srcLayer.id
										});	
									});
								}	
							} 
						})
					}
				
					data = util.tree.getNodes(layers, layerRelationships, { parentNodeId: "parentLayerId", childNodeId: "childLayerId" }, false);	
				}
				
				function getLayersWithPrefix(srcLayers, prefix) {
					
					let srcLayersWithPrefix = [];
					
					if (Array.isArray(srcLayers) && srcLayers.length > 0) {
						
						srcLayersWithPrefix = srcLayers.map(srcLayer => { 
							
							let layer = webix.copy(srcLayer);
							
							layer.id = prefix + srcLayer.id;
	
							return layer;
						});	
					}
					
					return srcLayersWithPrefix; 
				}
				
				function getLayersRelWithPrefix(srcLayersRel, prefix) {
					
					let srcLayersRelWithPrefix = [];
					
					if (Array.isArray(srcLayersRel) && srcLayersRel.length > 0) {
						
						srcLayersRelWithPrefix = srcLayersRel.map(srcLayerRel => { 
							
							let layerRel = webix.copy(srcLayerRel);
							
							layerRel.parentLayerId = prefix + srcLayerRel.parentLayerId;
							layerRel.childLayerId = prefix + srcLayerRel.childLayerId;
	
							return layerRel;
						}); 
					}
					
					return srcLayersRelWithPrefix;
				}
				
				return data;
			}
			
			function onCheckItem(items) {
				
				self.onLayersSelectedChange(items.map(e => e.id_));
			}
			
			function onSelectItem(item) {
				
				self.onLayersSelectedChange([item.id_]);
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
