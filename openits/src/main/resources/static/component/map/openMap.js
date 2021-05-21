import { util } from "../../lib/util.js";
import { adapter } from "../../lib/adapter.js";
import MapLayer from "./mapLayer.js";
import MapAction from "./mapAction.js";
import MapContextMenu from "./mapContextMenu.js";
import GraphicIcon from "../graphicIcon/graphicIcon.js";

const TOPIC_ELEMENTS = "/topic/element.>";

const MAX_ZOOM_LEVEL = 15;
const MIN_ZOOM_LEVEL = 1;

export default class OpenMap {

	constructor(metadata, data, events, options) {

		this.metadata = metadata;
		this.data = data;
		this.events = events;
		this.options = options;
		this.stomp = {};
		this.uid = webix.uid();

		this.graphicIcons = new Map();

		this.idStompSubscriptor = undefined;
		
		this.olMap = undefined;
		this.currentZoomLevel = undefined;
		this.layerElements = 0;
		this.maxZoomLevel = MAX_ZOOM_LEVEL;
		this.minZoomLevel = MIN_ZOOM_LEVEL;
	}

	getView() {

		let self = this;
		let view;
		
		view = {
			view: "abslayout",
			id: self.uid,
			cells: [
				{
					view: "template",
					relative: true,
					css: { "background": "#9e9e9e" },
					id: "view-map-" + self.uid,
					template: function() {
						
						let t = `<div oncontextmenu="return ${Array.isArray(self.metadata.commands) && self.metadata.commands.length > 0}">
									<div id="map-${self.uid}" style="height: 100vh; width: 100vw"></div>
									${getSvgTemplates()}
								 </div>`; 
						
						return t;
					},
					on: {
						onAfterRender: function() {
		
							self.olMap = createMap();

							self.currentZoomLevel = self.metadata.zoom;
							
							loadGraphicIcons();							
							
							return self.olMap;
						},
						onDestruct: function() {
		
							let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
			
							if (stompSubscriptorService && self.idStompSubscriptor) {
		
								stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
							}					
						}
					}
				},
				{ id: "syn-toolbar-main-" + self.uid },
				{ id: "syn-toolbar-actions-" + self.uid },
				{ id: "syn-window-actions-" + self.uid }
			]
		}
		
		function getSvgTemplates() {
			
			let template = `<div style="display: none;">`;
			
			if (Array.isArray(self.metadata.layers) && self.metadata.layers.length > 0) {
			
				self.metadata.layers.forEach(layer => {
				
					layer.layerElements.forEach(layerElement => {
						
						if (Array.isArray(layerElement.layerElementZooms) && layerElement.layerElementZooms.length > 0) {
						
							layerElement.layerElementZooms.forEach(layerElementZoom => {
								
								template += getLayerElementDiv(layerElement, layerElementZoom);
							});
						}
						else {
							
							template += getLayerElementDiv(layerElement);
						}
					});
				});	
			}
			
			template += `</div>`;
			
			function getLayerElementDiv(layerElement, layerElementZoom) {
			
				let layerElementDiv;
				
				let w = layerElementZoom ? layerElementZoom.graphicIcon.width : layerElement.graphicIcon.width;
				let h = layerElementZoom ? layerElementZoom.graphicIcon.height : layerElement.graphicIcon.height;
				
				layerElementDiv = `<svg id="${getSvgId(layerElement, layerElementZoom)}" viewBox="0 0 ${w} ${h}" style="width: 100%; height: auto"></svg>`;
				
				self.layerElements++;
				
				return layerElementDiv;
			}
			
			return template;
		}
		
		function getSvgId(layerElement, layerElementZoom) {
			
			let id = `svg-${layerElement.id}-${layerElementZoom ? layerElementZoom.id : 0}-${self.uid}`;
				
			return id;
		}
		
		function getMultiViewCellActions() {
			
			let cells = [];
			
			if (Array.isArray(self.metadata.layers) && self.metadata.layers.length > 0) {
				self.mapLayer = new MapLayer(self.metadata.layers, self.metadata.layerRelationships, function(layersId) {
					showLayers(layersId)
				});
				cells.push(self.mapLayer.getView());	
			}
			
			if (Array.isArray(self.metadata.commands) && self.metadata.commands.length > 0) {
				self.mapAction = new MapAction(self.metadata.commands, getAllPks(), getSelectedPks);
				cells.push(self.mapAction.getView());
			}
			
			return cells;
		}
			
		function createMap() {
					
			return new ol.Map({
				target: "map-" + self.uid,
				layers: [
					new ol.layer.Tile({
						// TODO: Implements other source URL's
						source: new ol.source.OSM()
					})
				],
				view: new ol.View({
					center: ol.proj.fromLonLat([self.metadata.longitude, self.metadata.latitude]),
					//extent: [ -553203.4061760335, 4366939.736625225, -531934.8030556832, 4374793.6412815275 ],
					zoom: self.metadata.zoom
				})
			});	
		}
		
	
		function loadGraphicIcons() {
			
			if (Array.isArray(self.metadata.layers) && self.metadata.layers.length > 0) {
			
				self.metadata.layers.forEach(layer => {
				
					layer.layerElements.forEach(layerElement => {
						
						if (Array.isArray(layerElement.layerElementZooms) && layerElement.layerElementZooms.length > 0) {
						
							layerElement.layerElementZooms.forEach(layerElementZoom => {
								
								loadGraphicIcon(layerElement, layerElementZoom);
							});
						}
						else {
							
							loadGraphicIcon(layerElement);
						}
					});
				});	
			}
			
			function loadGraphicIcon(layerElement, layerElementZoom) {
				
				let s = Snap("#" + getSvgId(layerElement, layerElementZoom));
				
				let container = { 
					type: GraphicIcon.CONTAINER_TYPE_OPEN_LAYER,
					obj: s
				};
				
				createGraphicIcon(layerElement,
								self.data.find(element => element.id === layerElement.elementId),
								container,
								function(graphicIconInstance) {
			
									onLoadGraphicIcon(layerElement.elementId, layerElementZoom ? layerElementZoom.zoom : 0, graphicIconInstance);
								}
				);
			}
			
			function onLoadGraphicIcon(elementId, zoom, graphicIconInstance) {
			
				addGraphicIcon(elementId, zoom, graphicIconInstance);
				
				if (++self.layerElementsLoaded === self.layerElements) {
					
					loadElementInfo();
				}
			}
		}
		
		function loadElementInfo() {
			
			let loadedElements = 0;
			
			self.data.forEach(element => {
				
				try {
	
					const lazy = async _ => {
						
						let graphicIcons = getGraphicIcons(element.id);
						
						if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
							
							graphicIcons.forEach(graphicIcon => {
								
								if (element.elementTypeState) {
									
									graphicIcon.updateState(element.elementTypeState);	
								}
								
								if (Array.isArray(element.elementValues) && element.elementValues.length > 0) {
									
									graphicIcon.updateValues(element.elementValues);
								}
								
								// TODO: Alarms

							});	
						}
						
						if (++loadedElements === self.data.length) {
							
//							connectWS();
//							
//							webix.ui(getToolbarMain(), $$("syn-toolbar-main-" + self.uid));
//							webix.ui(getToolbarActions(), $$("syn-toolbar-actions-" + self.uid));
//							webix.ui(getPanelActions(), $$("syn-window-actions-" + self.uid));
//							
//							loadContextMenu();
						}
					}
					
					lazy();
				}
				catch (error) {
					
					console.log(error);
				}	
			});
			 
		}
				
		function addGraphicIcon(elementId, zoom, graphicIcon) {
		
			let arrGraphicIcons;
			
			if (arrGraphicIcons = self.graphicIcons.get([elementId, zoom])) {
				
				arrGraphicIcons.push(graphicIcon);
			}
			else {
				
				self.graphicIcons.set([elementId, zoom], [graphicIcon]);
			}
		}
		
		function getGraphicIcons(elementId, zoom) {
			
			let graphicIconsArray = [];
			
			if (!zoom) {
				
				for (zoom = 0; i <= self.maxZoomLevel; i++) {
					
					let graphicIcons = self.graphicIcons.get([elementId, zoom]);
					
					if (graphicIcons) {
						
						graphicIconsArray = graphicIconsArray.concat(graphicIcons);
					}
				}
			}
			else {
				
				graphicIconsArray = self.graphicIcons.get([elementId, zoom])
			}
			
			return graphicIconsArray; 
		}
		
		function getSelectedPks() {
			
			let selectedPks = new Set();
			
			self.graphicIcons.forEach((value, key) => {
				
				let elementId = key[0];
				let graphicIcons = value;
				
				if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
					
					// One selected, no more is required
					if (graphicIcons[0].isSelected()) {
						
						selectedPks.add(elementId);
					}
				}
			});
				
			return Array.from(selectedPks);
		}
		
		function getAllPks() {
			
			return Array.from(self.graphicIcons.keys().map(e => e[0]));
		}
		
		function showLayers(layersId) {
			
			if (Array.isArray(layersId)) {
					
				self.metadata.layers.forEach(layer => {
					
					showBaseLayer(layer.id);
						
					layer.layerElements.forEach(layerElement => {
						
						let graphicIcons = getGraphicIcons(layerElement.elementId);
						
						graphicIcons.forEach(graphicIcon => {
							
							// Visible
							if (layersId.indexOf(layer.id) !== -1) {
							
								graphicIcon.show();
							}
							// No visible
							else {
								
								graphicIcon.hide();
							}	
						});
					});	
				});
			}
			
			function showBaseLayer(layerId) {
				
				let baseLayer = self.baseLayers.get(layerId);
				
				if (baseLayer) {
					
					// Visible
					if (layersId.indexOf(layerId) !== -1) {
					
						if (baseLayer.attr("visibility") != "visible") {
							
							baseLayer.attr({ visibility: "visible" });
						}
					}
					// No visible
					else {
	
						if (baseLayer.attr("visibility") != "hidden") {
							
							baseLayer.attr({ visibility: "hidden" });
						}
					}	
				}
			}
		}
		
		function createGraphicIcon(layerElement, element, container, onCreate) {
			
			let graphicIcon = layerElement.graphicIcon;
			
			try {
	
				const lazy = async _ => {
		
					let jsFile = util.getContextPath() + "/" + graphicIcon.jsDrawFile;
		
					const mod = await import(jsFile);
					
					let events = {};
			
					events[GraphicIcon.EventTypes.ON_DBL_CLIC] = onDblClic;
					
					let graphicIconInstance = new mod.default(layerElement, element, container, events);
					
					graphicIconInstance.load(function() {
						
						graphicIconInstance.loadRender(function() {
							
							onLoaded(graphicIconInstance);	
						});
					});
				}
				
				lazy();
			}
			catch (error) {
				
				console.log(error);
			}
			
			function onDblClic() {
				
				if (self.mapAction) {
					
					self.mapAction.executeDefault();
					
					unselectAll();
				} 
			}
			
			function onLoaded(graphicIconInstance) {
				
				onCreate(graphicIconInstance);
			}
		}
		
		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				let fields = undefined;
				
				if (Array.isArray(self.data) && self.data.length > 0) {
					
					fields = [];

					fields.push({
						target: "id",
						values: []
					});
					
					self.data.forEach(element => fields[0].values.push(element.id));
					
					self.idStompSubscriptor = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(TOPIC_ELEMENTS), onMessage, fields);
				}
			}
			
			function onMessage(data) {

				if (data) {
					
					if (self.options && self.options[Synoptic.Options.DEBUG_MODE]) {
						
						console.log("RX >> " + JSON.stringify(data));
					}
					
					let elementTypeParams = [];
				
					if (Array.isArray(self.data) && self.data.length > 0) {
						
						let elementAux = self.data.find(e => e.id === data.id);
						
						if (elementAux && Array.isArray(elementAux.elementValues) && elementAux.elementValues.length > 0) {
							
							elementTypeParams = elementAux.elementValues.map(elementValue => elementValue.elementTypeParam);
						}
					}
					
					let element = adapter.ws.element.get(data, elementTypeParams);
					
					if (element) {
						
						let graphicIcons = getGraphicIcons(element.id);
					
						if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
							
							graphicIcons.forEach(graphicIcon => {
							
								if (element.elementTypeState) {
									
									graphicIcon.updateState(element.elementTypeState);
								}
								
								if (Array.isArray(element.elementValues) && element.elementValues.length > 0) {
									
									graphicIcon.updateValues(element.elementValues);
								}
								
								// TODO ALARMS
							});
						}
					}
				}
			}
		}
		
		return view;			
	}
} 

Object.defineProperty(Map, 'Options', {
    value: {}
});

Object.defineProperty(Map.Options, "DEBUG_MODE", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});
