import { util } from "../lib/util.js";
import TreeView from "./treeview.js";
import { adapter } from "../lib/adapter.js";
import GraphicIcon from "../component/graphicIcon/graphicIcon.js";

const MAIN_TAG = "#main";

export default class Synoptic {

	constructor(metadata, data, events, w, h) {

		this.metadata = metadata;
		this.data = data;
		this.events = events;
		this.w = w;
		this.h = h;
		this.stomp = {};
		this.uid = webix.uid();
		
		// Snap main paper
		this.s = undefined;
		this.synoptic = undefined;
		
		this.layerElements = new Map();
	}
	
	addLayerElement(elementId, graphicIcon) {
		
		let arrGraphicIcons;
		
		if (arrGraphicIcons = this.layerElements.get(elementId)) {
			
			arrGraphicIcons.push(graphicIcon);
		}
		else {
			
			this.layerElements.set(elementId, [graphicIcon]);
		}
	}
	
	createGraphicIcon(layerElement, element, container, onCreate) {
		
		let graphicIcon = layerElement.graphicIcon;
		
		try {

			const lazy = async _ => {
	
				let jsFile = window.location.protocol + '//' + window.location.host + '/' + graphicIcon.jsDrawFile;
	
				const mod = await import(jsFile);
				
				let graphicIconInstance = new mod.default(layerElement, element, container);
				
				graphicIconInstance.load(function() {
					
					onLoaded(graphicIconInstance);
				});
			}
			
			lazy();
		}
		catch (error) {
			
			console.log(error);
		}
		
		function onLoaded(graphicIconInstance) {
			
			onCreate(graphicIconInstance);
		}
	}

	getView() {

		let self = this;
		let view;
		
		view = {
			view: "template",
			css: { "background": "#9e9e9e" },
			id: "syn-" + self.uid,
			template: function() {
				return `<svg id=${"svg-syn-" + self.uid}></svg>`
			},
			on: {
				onAfterRender: function() {
		        
					drawSynoptic();
				}
			}
		}
			
		
		function drawSynoptic() {
			
			self.s = Snap("#svg-syn-" + self.uid);
			self.s.attr({ width: self.w, height: self.h });
			
			
			Snap.load(self.metadata.baseLayer, function(fragment) {
				
				self.synoptic = fragment.select(MAIN_TAG);
						
				self.s.append(self.synoptic);
				
				let optionsPanZoom = {
					controlIconsEnabled: true,
					dblClickZoomEnabled: false
				}
				
				loadGraphicIcons();
				
				//svgPanZoom("#svg-syn-" + self.uid, optionsPanZoom);
			});
		}
		
		function loadGraphicIcons() {

			let synopticLayers = self.metadata.synopticLayers;
			
			synopticLayers.forEach(synLayer => {
				
				synLayer.synopticLayerElements.forEach(synLayerElement => {
					
					self.createGraphicIcon(synLayerElement,
						self.data.find(element => element.id === synLayerElement.elementId),
						{ type: GraphicIcon.CONTAINER_TYPE_SNAP, obj: self.s },
						function(graphicIconInstance) {

							onLoadGraphicIcon(synLayerElement.elementId, graphicIconInstance);
						});
						
					
				});
			});
		}
		
		function onLoadGraphicIcon(elementId, graphicIconInstance) {
			
			self.addLayerElement(elementId, graphicIconInstance);
			
		}
		
		return view;			
	}
}
