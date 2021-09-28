import { util } from "../../lib/util.js";
import { adapter } from "../../lib/adapter.js";
import GenericMap from "./genericMap.js";
import MapLayer from "./mapLayer.js";
import MapAction from "./mapAction.js";
import MapContextMenu from "./mapContextMenu.js";
import MapZone from "./mapZone.js";
import GraphicIcon from "../graphicIcon/graphicIcon.js";
import MapIncident from "./mapIncident.js"; 

const TOPIC_ELEMENTS = "/topic/element.>";

const ALL_ZOOM_LEVEL = 0;
const MAX_ZOOM_LEVEL = 20;
const MIN_ZOOM_LEVEL = 1;

const ZINDEX_BACK = 0;
const ZINDEX_FRONT = 9999;

export default class OpenMap extends GenericMap {

	constructor(metadata, data, events, w, h, options) {

		super(metadata, data, events, w, h, options)
		this.stomp = {};

		this.graphicIcons = new Map();
		this.zoomLevelGraphicIcons = new Map();

		this.idStompSubscriptor = undefined;
		
		this.olMap = undefined;
		this.flagMouseMove = false;
		this.mouseOverSvg = false;
		this.currentZoomLevel = undefined;
		this.layerElements = 0;
		this.layerElementsLoaded = 0;
		this.layersVisible = [];
		this.maxZoomLevel = MAX_ZOOM_LEVEL;
		this.minZoomLevel = MIN_ZOOM_LEVEL;
		this.darkModeActive = false;
		
		this.mapLayer = undefined;
		this.mapAction = undefined;
		this.mapZone = undefined;
		this.mapIncident = undefined;
		
		this.afterRenderCallback = undefined;
		
	}

	getView(afterRenderCallback) {

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
									
						let t;
						
						t = `<div oncontextmenu="return ${Array.isArray(self.metadata.commands) && self.metadata.commands.length > 0}">
									<div id="map-${self.uid}" style="height: ${self.h}px; width: ${self.w}px">
									<div id="svg-main-container-${self.uid}" style="display: none;"></div>
								 </div>`;
						
						return t;
					},
					on: {
						onAfterRender: function() {
		
							self.afterRenderCallback = afterRenderCallback;
							self.olMap = createMap();
							
							loadGraphicIcons();
							loadIncidents();
						},
						onDestruct: function() {
		
							let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
			
							if (stompSubscriptorService && self.idStompSubscriptor) {
		
								stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
							}
							
							if (self.mapIncident) {
								
								self.mapIncident.unload();
							}	
							
							stop();				
						}
					}
				},
				{ id: "map-toolbar-main-" + self.uid },
				{ id: "map-toolbar-actions-" + self.uid },
				{ id: "map-window-actions-" + self.uid }
			]
		}
		
		function stop() {
			
			if (self.graphicIcons) {
														
				self.graphicIcons.forEach(function(value, key) {
					
					let graphicIcons = value;
					
					if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
						
						graphicIcons.forEach(graphicIcon => {
							
							graphicIcon.stop();
						});
					}
				});
			}
		}
			
		function getSvgId(layerElement, layerElementZoom) {
			
			let id;
			
			id = `svg-${layerElement.id}-${layerElementZoom ? layerElementZoom.id : 0}-${self.uid}`;
				
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
				
				let cmdCallbacks = undefined;
				
				if (self.events && Array.isArray(self.events[GenericMap.EventTypes.ON_COMMAND_EXEC]) && 
					self.events[GenericMap.EventTypes.ON_COMMAND_EXEC].length > 0) {
						
					cmdCallbacks = self.events[GenericMap.EventTypes.ON_COMMAND_EXEC];
				}
				
				self.mapAction = new MapAction(self.metadata.commands, getAllPks(), getSelectedPks, cmdCallbacks);
				cells.push(self.mapAction.getView());
			}
			
			if(Array.isArray(self.metadata.mapZones) && self.metadata.mapZones.length > 0){
				self.mapZone = new MapZone(self.metadata.mapZones, function(zoneCoor) {
					gotoZone(zoneCoor)
				});
				cells.push(self.mapZone.getView());
			}
			
			return cells;
		}
			
		function createMap() {
					
			let map = new ol.Map({
				target: "map-" + self.uid,
				controls: [],
				interactions : ol.interaction.defaults({doubleClickZoom :false}),
				layers: [
					new ol.layer.Tile({

						source: new ol.source.OSM()
					})
				],
				view: new ol.View({
					center: ol.proj.fromLonLat([self.metadata.longitude, self.metadata.latitude]),
					//extent: [ -553203.4061760335, 4366939.736625225, -531934.8030556832, 4374793.6412815275 ],
					extent: function() {
						
						let ext = undefined;
						
						if (self.metadata.limitInitLon && self.metadata.limitInitLat && self.metadata.limitEndLon && self.metadata.limitEndLat) {
				
							let destLoc = [self.metadata.limitEndLon, self.metadata.limitEndLat];
							let currentLoc = [self.metadata.limitInitLon, self.metadata.limitInitLat];
							
							ext = ol.extent.boundingExtent([destLoc, currentLoc]);
							ext = ol.proj.transformExtent(ext, ol.proj.get('EPSG:4326'), ol.proj.get('EPSG:3857'));
						}
						
						return ext;			
					}(),
					zoom: self.metadata.zoom,
					maxZoom: self.metadata.zoomMax,
					minZoom: self.metadata.zoomMin
				})
			});
			
			
			
			
			map.on('moveend', onChangeZoom);
						
			if (self.options && self.options[Map.Options.DEBUG_MODE]) {
			
				map.on('singleclick', function(evt) {
				
					let coor = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
	
					console.log("(LON, LAT) => " + coor.join());
		
					evt.preventDefault();
				});	
			}
			
			map.getViewport().addEventListener('mousedown', function(evt){
			    self.flagMouseMove = false;
			}, false);
			
			map.getViewport().addEventListener('mousemove', function(evt){
			    self.flagMouseMove = true;
			}, false);
			
			map.getViewport().addEventListener('mouseup', function(evt){
			    
				if (!self.flagMouseMove && !self.mouseOverSvg && evt.which === GenericMap.MOUSE_EVENT_LEFT_PRESSED) {
								
					self.unselectAll();
				}
			}, false); 
			
			self.currentZoomLevel = self.metadata.zoom;
			self.maxZoomLevel = self.metadata.zoomMax;
			self.minZoomLevel = self.metadata.zoomMin;
			
			webix.delay(function() {

				map.updateSize();
			}, null, null, 1000);
			
			return map;
		}
		
		function darkMode() {

			if (self.darkModeActive) {
				
				document.querySelector("#map-" + self.uid).querySelector('canvas').style.filter="invert(0%)";
			}
			else {
				
				document.querySelector("#map-" + self.uid).querySelector('canvas').style.filter="invert(90%)";
			}
			
			self.darkModeActive = !self.darkModeActive;
		}
		
		function onChangeZoom(e) {
			
			var newZoom = self.olMap.getView().getZoom();
			
			self.olMap.getInteractions().forEach(function(interaction){interaction.setActive(true);});
			self.mouseOverSvg = false;
			
			if (self.currentZoomLevel != Math.round(newZoom)) {
				console.log('zoom end, new zoom: ' + newZoom);
				self.currentZoomLevel = Math.round(newZoom);
				
				refreshVisibleGraphicIcons();
			}
		}
		
		function refreshVisibleGraphicIcons() {
			
			let selectedGraphicIcons = new Set();
			
			// Set hide other zoom level graphicIcons
			self.zoomLevelGraphicIcons.forEach((value, key) => {
				
				if (key !== ALL_ZOOM_LEVEL && key !== self.currentZoomLevel) {
					
					let graphicIcons = value;
					
					if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
						
						graphicIcons.forEach(graphicIcon => {
							
							if (graphicIcon.isSelected()) {
								
								selectedGraphicIcons.add(graphicIcon);
							}
							
							graphicIcon.hide();
							
							zIndexOverlay(graphicIcon, ZINDEX_BACK);
						});
					}	
				}
				else {
						
					let graphicIcons = value;
				
					if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
						
						graphicIcons.forEach(graphicIcon => {
							
							if (self.isElementLayerVisible(graphicIcon)) {
							
								graphicIcon.show();
								
								zIndexOverlay(graphicIcon, ZINDEX_FRONT);	
							}
							else {
								
								graphicIcon.hide();
								
								zIndexOverlay(graphicIcon, ZINDEX_BACK);
							}
						});
					}
				}
			});
			
			// Set visible new zoom level graphicIcons
			let newZoomLevelGraphicIcons = self.zoomLevelGraphicIcons.get(self.currentZoomLevel);
			
			if (Array.isArray(newZoomLevelGraphicIcons) && newZoomLevelGraphicIcons.length > 0) {
				
				newZoomLevelGraphicIcons.forEach(newZoomLevelGraphicIcon => {
					
					if (self.isElementLayerVisible(newZoomLevelGraphicIcon)) {
						
						newZoomLevelGraphicIcon.show();
						
						zIndexOverlay(newZoomLevelGraphicIcon, ZINDEX_FRONT);
					
						if (selectedGraphicIcons.has(newZoomLevelGraphicIcon)) {
							
							newZoomLevelGraphicIcon.select();
						}	
					}
					else {
						
						newZoomLevelGraphicIcon.hide();
						
						zIndexOverlay(newZoomLevelGraphicIcon, ZINDEX_BACK);
					}
				});
			} 
			
			function zIndexOverlay(graphicIcon, zIndex) {
			
				let overlayId = graphicIcon.container.obj.node.id.replace("svg", "ol-overlay");
				let layerElement = graphicIcon.layerElement;
							
				let olOverlayDiv = self.olMap.getOverlayById(overlayId);
				
				if (olOverlayDiv) {
					
					let zIndexCalculated = zIndex;
					
					if (layerElement.zIndexFront) {
					
						zIndexCalculated = zIndex + layerElement.zIndexFront;
					}
					
					olOverlayDiv.element.style.zIndex = zIndexCalculated;
				}	
			}
		}
		
	
		function loadGraphicIcons() {
			
			if (Array.isArray(self.metadata.layers) && self.metadata.layers.length > 0) {
			
				self.metadata.layers.forEach(layer => {
				
					layer.layerElements.forEach(layerElement => {
						
						if (Array.isArray(layerElement.layerElementZooms) && layerElement.layerElementZooms.length > 0) {
						
							layerElement.layerElementZooms.forEach(layerElementZoom => {
								
								// Check if zoom layer element zoom config is between map zoom limits
								let isLayerElementZoomBetweenLimits = layerElementZoom.zoomLower >= self.minZoomLevel && layerElementZoom.zoomUpper <= self.maxZoomLevel;
								
								// Check if lon or lat is equals to 0
								if (layerElementZoom.longitude == 0 || layerElementZoom.latitude == 0) {
									
									let lonLat = ol.proj.transform(self.olMap.getView().getCenter(), 'EPSG:3857', 'EPSG:4326');
									layerElementZoom.longitude = lonLat[0];
									layerElementZoom.latitude = lonLat[1]; 
								}
								
								if (isLayerElementZoomBetweenLimits) {
									
									loadGraphicIcon(layerElement, layerElementZoom);	
								}
							});
						}
						else {
							
							// Check if lon or lat is equals to 0
							if (layerElement.longitude == 0 || layerElement.latitude == 0) {
								
								let lonLat = ol.proj.transform(self.olMap.getView().getCenter(), 'EPSG:3857', 'EPSG:4326');
								layerElement.longitude = lonLat[0];
								layerElement.latitude = lonLat[1]; 
							}
							
							loadGraphicIcon(layerElement);
						}
					});
				});	
			}
			
			function loadGraphicIcon(layerElement, layerElementZoom) {
				
				let layerElem = webix.copy(layerElement);
				let s = Snap("#" + createSvgIcon());
				
				let container = { 
					type: GraphicIcon.CONTAINER_TYPE_OPEN_LAYER,
					obj: s
				};
				
				if (layerElementZoom) {
					
					layerElem.layerElementZoom = layerElementZoom;
					
					layerElem.graphicIcon = layerElementZoom.graphicIcon;
					layerElem.longitude = layerElementZoom.longitude;
					layerElem.latitude = layerElementZoom.latitude;
					layerElem.horizontalFlip = layerElementZoom.horizontalFlip;
					layerElem.rotate = layerElementZoom.rotate;
					layerElem.tooltip = layerElementZoom.tooltip;
					layerElem.showText = layerElementZoom.showText;
					layerElem.textColor = layerElementZoom.textColor;
				}
				
				self.layerElements++;
								
				createGraphicIcon(layerElem,
								self.data.find(element => element.id === layerElem.elementId),
								container,
								function(graphicIconInstance) {
			
									onLoadGraphicIcon(layerElem, graphicIconInstance);
								}
				);
				
				function createSvgIcon() {
					
					let svgId = getSvgId(layerElem, layerElementZoom);
					let svgGraphicIcon = document.createElementNS("http://www.w3.org/2000/svg", "svg");
					let w = layerElementZoom ? layerElementZoom.graphicIcon.width : layerElem.graphicIcon.width;
					let h = layerElementZoom ? layerElementZoom.graphicIcon.height : layerElem.graphicIcon.height;
					
					if ((layerElem && layerElem.showText) || (layerElementZoom && layerElementZoom.showText)) {

						let element = window.em.getByField(EntityType.ELEMENT, "id", layerElem.elementId);
						
						if (element) {
							
							let fontSize = GraphicIcon.FONT_SIZE;
							let text = document.createElement("div");
							text.style.fontSize = fontSize + "px";
							text.style.fontFamily = "monospace";
							text.style.fontWeight = "bold";
							text.style.position = "absolute";
							text.style.visibility = "hidden";
							text.style.height = "auto";
							text.style.width = "auto";
							text.style.whiteSpace = "nowrap";
							text.innerHTML = element.alias;
							document.body.appendChild(text);
							
							let width = (text.clientWidth + fontSize);
							
							w = width > w ? width : w;
							h += GraphicIcon.FONT_SIZE + 5;
						}						
					}
					
					svgGraphicIcon.setAttribute("id", svgId);
					svgGraphicIcon.setAttribute("width", w);
					svgGraphicIcon.setAttribute("height", h);
					
					document.querySelector("#svg-main-container-" + self.uid).appendChild(svgGraphicIcon);
										
					return svgId;
				}
			}
			
			function onLoadGraphicIcon(layerElement, graphicIconInstance) {
				
				// Hidden
				graphicIconInstance.hide();
			
				addGraphicIcon(layerElement, graphicIconInstance);
				
				if (++self.layerElementsLoaded === self.layerElements) {
					
					loadElementInfo();
				}
			}
		}
		
		function loadIncidents() {
			
			if (self.metadata.mapIncident) {
				
				self.mapIncident = new MapIncident(self);
				
				self.mapIncident.load();
			}
		}
		
		function loadElementInfo() {
			
			let loadedElements = 0;

			self.zoomLevelGraphicIcons.forEach((value, key) => {
				
				let graphicIcons = value;
				
				if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
					
					graphicIcons.forEach(graphicIcon => {
						
						let element = self.data.find(e => e.id === graphicIcon.element.id);
						
						try {
	
							const lazy = async _ => {
								
								if (element.elementTypeState) {
											
									graphicIcon.updateState(element.elementTypeState);	
								}
								
								if (Array.isArray(element.elementValues) && element.elementValues.length > 0) {
									
									graphicIcon.updateValues(element.elementValues);
								}
								
								if (++loadedElements === self.layerElements) {
									
									connectWS();
									
									webix.ui(getToolbarMain(), $$("map-toolbar-main-" + self.uid));
									webix.ui(getToolbarActions(), $$("map-toolbar-actions-" + self.uid));
									webix.ui(getPanelActions(), $$("map-window-actions-" + self.uid));
									
									linkGraphicIcons();
									
									loadContextMenu();
									
									if (afterRenderCallback) {
										
										afterRenderCallback();
									}
								}
							}
							
							lazy();
						}
						catch (error) {
							
							console.log(error);
						}
					});
				}
			})
			
			function linkGraphicIcons() {
			
				self.graphicIcons.forEach((value, key) => {
					
					let graphicIcons = value;
					
					if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
						
						graphicIcons.forEach(graphicIcon => {
							
							let olOverlay = new ol.Overlay({
								id: graphicIcon.container.obj.node.id.replace("svg", "ol-overlay"),
								position: ol.proj.fromLonLat([graphicIcon.layerElement.longitude, graphicIcon.layerElement.latitude]),
								positioning: 'center-center',
								element: graphicIcon.container.obj.node,
								stopEvent: false
							});
							
							self.olMap.addOverlay(olOverlay);
							
							let olOverlayDiv = graphicIcon.container.obj.node.parentNode;
							
							if (olOverlayDiv) {
							
								if (graphicIcon.layerElement.layerElementZoom) {
								
									olOverlayDiv.style.zIndex = self.maxZoomLevel - graphicIcon.layerElement.layerElementZoom.zoomLower;
								}
							}
						});
					}
				});
			
				// Set visible all zoom level graphicIcons
				let allZoomLevelGraphicIcons = self.zoomLevelGraphicIcons.get(ALL_ZOOM_LEVEL);
				
				if (Array.isArray(allZoomLevelGraphicIcons) && allZoomLevelGraphicIcons.length > 0) {
					
					allZoomLevelGraphicIcons.forEach(allZoomLevelGraphicIcon => {
						
						allZoomLevelGraphicIcon.show();
					});
				} 
				
				// Refresh current zoom level graphicIcons
				refreshVisibleGraphicIcons();
			}
			 
		}
				
		function addGraphicIcon(layerElement, graphicIcon) {
		
			let arrGraphicIcons = self.graphicIcons.get(layerElement.elementId);
			
			if (arrGraphicIcons) {
				
				arrGraphicIcons.push(graphicIcon);
			}
			else {
				
				self.graphicIcons.set(layerElement.elementId, [graphicIcon]);
			}
		
			// Add to zoom map
			if (layerElement.layerElementZoom) {
				
				for (let zoomLvl = layerElement.layerElementZoom.zoomLower; zoomLvl <= layerElement.layerElementZoom.zoomUpper; zoomLvl++) {
					
					let customZoomGraphicIcons = self.zoomLevelGraphicIcons.get(zoomLvl);
					
					if (customZoomGraphicIcons) {
						
						customZoomGraphicIcons.push(graphicIcon);
					}
					else {
						
						self.zoomLevelGraphicIcons.set(zoomLvl, [graphicIcon]);
					}
				}  
			}
			else {
				
				let allZoomGraphicIcons = self.zoomLevelGraphicIcons.get(ALL_ZOOM_LEVEL);
				
				if (allZoomGraphicIcons) {
				
					allZoomGraphicIcons.push(graphicIcon);	
				}
				else {
						
					self.zoomLevelGraphicIcons.set(ALL_ZOOM_LEVEL, [graphicIcon]);
				}
			}
		}
		
		function getGraphicIcons(elementId) {
			
			return self.graphicIcons.get(elementId);  
		}
		
		function getSelectedPks() {
			
			let selectedPks = new Set();
			
			self.graphicIcons.forEach((value, key) => {
				
				let elementId = key;
				let graphicIcons = value;
				
				if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
					
					// One selected, no more is required
					if (graphicIcons.findIndex(e => e.isSelected()) !== -1) {
						
						selectedPks.add(elementId);
					}
				}
			});
				
			return Array.from(selectedPks);
		}
		
		function getAllPks() {
			
			return Array.from(self.graphicIcons.keys());
		}
		
		function gotoZone(zoneCoor){

			self.olMap.getView().animate({
				center: ol.proj.fromLonLat([zoneCoor[0].lon,  zoneCoor[0].lat]),
				zoom: zoneCoor[0].zoom,
				duration: 1000
			});
			
		}
		
		function showLayers(layersId) {
			
			self.layersVisible = layersId;
			
			refreshVisibleGraphicIcons();
		}
		
		function createGraphicIcon(layerElement, element, container, onCreate) {
			
			let graphicIcon = layerElement.graphicIcon;
			
			try {
	
				const lazy = async _ => {
		
					let jsFile = util.getContextPath() + "/" + graphicIcon.jsDrawFile;
		
					const mod = await import(jsFile);
					
					let events = {};
			
					events[GraphicIcon.EventTypes.ON_DBL_CLIC] = onDblClic;
					events[GraphicIcon.EventTypes.ON_MOUSE_OVER] = onMouseOver;
					events[GraphicIcon.EventTypes.ON_MOUSE_OUT] = onMouseOut;
					events[GraphicIcon.EventTypes.ON_MOUSE_WHEEL] = onMouseWheel;
					
					let graphicIconInstance = new mod.default(layerElement, element, container, events);
					
					graphicIconInstance.load(function() {
						
						graphicIconInstance.loadRender(function() {
							
							onLoaded(graphicIconInstance);	
						});
					});
					
					function onMouseOver() {
				
						self.olMap.getInteractions().forEach(function(interaction){interaction.setActive(false);});
						self.mouseOverSvg = true;
					}
					
					function onMouseOut() {
						
						self.olMap.getInteractions().forEach(function(interaction){interaction.setActive(true);});
						self.mouseOverSvg = false;						
					}
					
					function onMouseWheel() {
						
						self.olMap.getInteractions().forEach(function(interaction){interaction.setActive(true);});
						self.mouseOverSvg = false;							
					}
				}
				
				lazy();
			}
			catch (error) {
				
				console.log(error);
			}
			
			function onDblClic() {
				
				if (self.mapAction) {
					
					self.mapAction.executeDefault();
					
					self.unselectAll();
				} 
			}
			
			function onLoaded(graphicIconInstance) {
				
				onCreate(graphicIconInstance);
			}
		}
		
		function reloadCboSearch() {
			
			// TODO: Only show in search combo box visible elements
		}
		
		function getToolbarMain() {
			
			let viewToolbarMain = {
				
				view: "toolbar",
				id: "map-toolbar-main-" + self.uid,
				css: "syn-toolbar-zoom",
				borderless: true,
				top: 10, 
				left: 10,
				height: 100,
				cols: [
					{ width: 1 }
				]
			};
			
			let viewButtonsZoom = {
				rows: [
					{},
					{
						view: "button",
						type: "icon",
						icon:"mdi mdi-plus-thick",
						tooltip: i18n.t("LBL_ZOOM_IN"),
						css: "button-zoom",
						width: 30, height: 30,
						on: {
							onItemClick: function() {
								
								zoom(self.olMap.getView().getZoom() + 1);
							}
						}		
					}, 
					{
						view: "button",
						type: "icon",
						icon:"mdi mdi-minus-thick",
						tooltip: i18n.t("LBL_ZOOM_OUT"),
						css: "button-zoom",
						width: 30, height: 30,
						on: {
							onItemClick: function() {
								
								zoom(self.olMap.getView().getZoom() - 1);
							}
						}		
					},
					{}
				]
			};
			
			let viewButtonResetZoom = {
				rows: [
					{},
					{
						view: "button",
						type: "icon",
						icon:"mdi mdi-restore",
						tooltip: i18n.t("LBL_RESET"),
						css: "button",
						width: 40, height: 40,
						on: {
							onItemClick: function() {
								
								self.olMap.getView().animate({
									center: ol.proj.fromLonLat([self.metadata.longitude, self.metadata.latitude]),
									zoom: self.metadata.zoom,
									duration: 1000
								});
							}
						}
					},
					{}
				]
			};
			
			let viewButtonSearch = {
				rows: [
					{},
					{
						css: { "background": "var(--blue-mid2-opacity-5) !important", "border-radius": "10px !important" },
						height: 35,
						cols: [
							{
								view: "button",
								type: "icon",
								icon:"mdi mdi-magnify",
								tooltip: i18n.t("LBL_SEARCH"),
								css: "button-zoom-search",
								width: 38, height: 40,
								on: {
									onItemClick: function() {
										
										let cboSearch = $$("map-toolbar-zoom-cbo-search-" + self.uid);
										let cboSearchSeparator = $$("map-toolbar-zoom-cbo-search-sep-" + self.uid);
										
										if (cboSearch && cboSearchSeparator) {
											
											if (cboSearch.isVisible() && cboSearchSeparator.isVisible()) {

												cboSearch.hide();
												cboSearchSeparator.hide();
											}
											else {
												
												cboSearch.show();
												cboSearchSeparator.show();
											}
										}
									}
								}
							},
							{
								rows: [
									{},
									{
										view: "combo",
										borderless: true,
										id: "map-toolbar-zoom-cbo-search-" + self.uid,
										hidden: true,
										width: 200, height: 30,
										options: {
											view: "suggest",
											filter: function(obj, value) {
												
												return (obj.value.toLowerCase().indexOf(value.toLowerCase()) !== -1)
											},
											body: {
												view: "list",
												data: function() {
											
													let data = [];
											
													if (self.graphicIcons) {
														
														self.graphicIcons.forEach(function(value, key) {
															
															let elementId = key;
															let graphicIcons = value;
															
															if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
																
																let element = self.data.find(e => e.id === elementId);
																
																if (element) {
																	
																	let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);
																	
																	if (elementType) {
																	
																		let item = {
																			id: elementId,
																			value: element.alias + " - " + i18n.t(elementType.labelAlias),
																			image: elementType.elementTypePhoto
																		}
																		
																		data.push(item);		
																	}
																}
															}
														});
													}
													
													if (data.length > 0) {
														
														data.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));	
													}
													
													return data;
												}(),
												template: function(obj) {
							
													let html = "";
													
													let image = obj.image;
													
													if (image) {
													
														html = `<image class="search-list-item-image" src="img/${image}" onerror="this.onerror=null;this.src='img/not_found_50.png';" />`;	
													}
								
													html += `<span title="${obj.value}" class="search-list-item-text">${obj.value} </span>`;
						
													return html;
												},
        										yCount: 7
											}
										},
										on:{
											onChange: function(newValue, oldValue, config){
										        
												self.select(newValue);
										 	}
										}
									},
									{}
								]
							},
							{ 
								id: "map-toolbar-zoom-cbo-search-sep-" + self.uid,
								width: 10,
								hidden: true
							}
						]
					},	
					{}	
				]
				
			};
						
			viewToolbarMain.cols.push(viewButtonsZoom);
			viewToolbarMain.cols.push(viewButtonResetZoom);
			viewToolbarMain.cols.push(viewButtonSearch);
			
			return viewToolbarMain;
		}
		
		function getToolbarActions() {
		
			let viewToolbarActions = {
				
				view: "toolbar",
				id: "map-toolbar-actions-" + self.uid,
				css: "syn-toolbar-actions",
				borderless: true,
				top: 20, 
				right: 20,
				cols: [
					{ width: 1 }
				]
			};
			
			if (Array.isArray(self.metadata.mapZones) && self.metadata.mapZones.length > 0) {
				
				let viewButtonZones = {
					view: "button",
					type: "icon",
					icon:"mdi mdi-map-search",
					tooltip: i18n.t("LBL_SHOW_ZONES"),
					css: "button",
					width: 40, height: 40,
					on: {
						onItemClick: function() {
							
							$$("map-window-actions-" + self.uid).show();
							self.mapZone.show();
							
							webix.html.removeCss( $$("map-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-actions");
							webix.html.addCss( $$("map-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-layers");
							$$("map-window-actions-toolbar-title-" + self.uid).setValues({ title: i18n.t("LBL_ZONES"), icon: "mdi mdi-map-search"});
						}
					}
				};
				
				viewToolbarActions.cols.push(viewButtonZones);	
			}
			
			if (Array.isArray(self.metadata.layers) && self.metadata.layers.length > 0) {
				
				let viewButtonLayers = {
					view: "button",
					type: "icon",
					icon:"mdi mdi-layers",
					tooltip: i18n.t("LBL_SHOW_LAYERS"),
					css: "button",
					width: 40, height: 40,
					on: {
						onItemClick: function() {
							
							$$("map-window-actions-" + self.uid).show();
							self.mapLayer.show();
							
							webix.html.removeCss( $$("map-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-actions");
							webix.html.addCss( $$("map-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-layers");
							$$("map-window-actions-toolbar-title-" + self.uid).setValues({ title: i18n.t("LBL_LAYERS"), icon: "mdi mdi-layers"});
						}
					}
				};
				
				viewToolbarActions.cols.push(viewButtonLayers);	
			}
			
			if (self.metadata.darkMode) {
				
				let viewButtonDarkMode = {
					view: "button",
					type: "icon",
					icon:"mdi mdi-weather-night",
					tooltip: i18n.t("LBL_DARK_MODE"),
					css: "button",
					width: 40, height: 40,
					on: {
						onItemClick: function() {
							
							darkMode();
						}
					}
				};
				
				viewToolbarActions.cols.push(viewButtonDarkMode);	
			}
			
			if (Array.isArray(self.metadata.commands) && self.metadata.commands.length > 0) {
				
				let viewButtonActions = {
					view: "button",
					type: "icon",
					icon:"mdi mdi-dots-vertical",
					tooltip: i18n.t("LBL_SHOW_ACTIONS"),
					css: "button",
					width: 40, height: 40,
					on: {
						onItemClick: function() {
							
							$$("map-window-actions-" + self.uid).show();
							self.mapAction.show();
							
							webix.html.removeCss( $$("map-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-layers");
							webix.html.addCss( $$("map-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-actions");
							$$("map-window-actions-toolbar-title-" + self.uid).setValues({ title: i18n.t("LBL_ACTIONS"), icon: "mdi mdi-dots-vertical"});
						}
					}
				};
				
				viewToolbarActions.cols.push(viewButtonActions);	
			} 
			
			return viewToolbarActions;	
		}
		
		function loadContextMenu() {
			
			if (Array.isArray(self.metadata.commands) && self.metadata.commands.length > 0) {
				
				let cmdCallbacks = undefined;
				
				if (self.events && Array.isArray(self.events[GenericMap.EventTypes.ON_COMMAND_EXEC]) && 
					self.events[GenericMap.EventTypes.ON_COMMAND_EXEC].length > 0) {
						
					cmdCallbacks = self.events[GenericMap.EventTypes.ON_COMMAND_EXEC];
				}
				
				let contextMenu = new MapContextMenu(self.metadata.commands, getAllPks(), getSelectedPks, cmdCallbacks);
				
				let contextMenuView = contextMenu.getView();
				contextMenuView.master = "map-" + self.uid;

			    webix.ui(contextMenuView);
			}
		}
		
		function getPanelActions() {
			
			let viewPanelActions;
			
			viewPanelActions = {
				id: "map-window-actions-" + self.uid,
				//css: "syn-window-actions",
				hidden: true,
				top: 70, 
				right: 10,
				width: 300,
				height: self.h - 200,
				rows: [
					{
						id: "map-window-actions-toolbar-" + self.uid,
						view: "toolbar",
						height: 25,
						cols: [
							{
								id: "map-window-actions-toolbar-title-" + self.uid,
								css: "header",
								width: 250,
								borderless: true,
								template: function(obj) {
									
									return `<div class="title"><span class="${obj.icon}">${obj.title}</span></div>`;
								}
							},
							{},
							{
								view: "icon",
								icon: "mdi mdi-close",
								on: {
									onItemClick: function(id) {
										
										$$("map-window-actions-" + self.uid).hide();
									}
								}
							},
						]
					},
					{
						id: "map-window-actions-multiview-" + self.uid,
						view: "multiview",
						keepViews:true,
						animate: false,
						cells: getMultiViewCellActions()		
					}
				]
			};
			
			return viewPanelActions;
		}
		
		function zoom(level) {
			
			self.olMap.getView().animate({
				zoom: level,
			  	duration: 250
			});
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
					
					if (self.options && self.options[Map.Options.DEBUG_MODE]) {
						
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
	
	select(elementId) {
		
		let self = this;
			
		if (!selectInAllZoom()) {
			
			selectInOtherZooms();
		}
		
		function selectInAllZoom() {
			
			let selected = false;
		
			let allZoomLevelGraphicIcons = self.zoomLevelGraphicIcons.get(ALL_ZOOM_LEVEL);
				
			if (Array.isArray(allZoomLevelGraphicIcons) && allZoomLevelGraphicIcons.length > 0) {
				
				let graphicIcon = allZoomLevelGraphicIcons.find(allZoomLevelGraphicIcon => allZoomLevelGraphicIcon.element.id === elementId);
			
				if (graphicIcon && self.isElementLayerVisible(graphicIcon)) {
					
					self.unselectAll();
	
					graphicIcon.select();
					
					selected = graphicIcon.isSelected()
					
					if (selected) {
						
						self.goto(graphicIcon, self.currentZoomLevel);
					}
				}	
			}
			
			return selected;
		}
		
		function selectInOtherZooms() {
			
			let zoomLevels = Array.from(self.zoomLevelGraphicIcons.keys());
			
			// Ascending sort. Select min zoom level for element...
			zoomLevels.sort((a, b) => a - b); 
			
			// But add currentZoomLevel to the beginning/end of all zoomLevels to eval
			const index = zoomLevels.indexOf(self.currentZoomLevel);
			
			if (index > -1) {
				zoomLevels.splice(index, 1);
				zoomLevels.unshift(self.currentZoomLevel);
			} 
			
			let graphicIcon = undefined;
			let zoomLevel = self.currentZoomLevel;
			
			for (let i = 0; !graphicIcon && i < zoomLevels.length; i++) {
				
				zoomLevel = zoomLevels[i];
				let graphicIcons = self.zoomLevelGraphicIcons.get(zoomLevel);
				
				if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
					
					graphicIcon = graphicIcons.find(e => e.element.id === elementId);
				}
			}
			
			if (graphicIcon && self.isElementLayerVisible(graphicIcon)) {
				
				self.unselectAll();
				
				self.goto(graphicIcon, zoomLevel);
				
				// Make sure it is selected after zoom
				webix.delay(function() {
					
					graphicIcon.select();

				}, null, null, 4000);
			}
			else {
				
				webix.alert({
					title: i18n.t("LBL_LAYER_NOT_VISIBLE"),
					text: i18n.t("LBL_LAYER_NOT_VISIBLE_FOR_ELEMENT_DESCRIPTION"),
					type: "alert-error",
					width: 500
				});
			}
		}
	}
	
	unselectAll() {
			
		this.graphicIcons.forEach((value) => {
			
			let graphicIcons = value;
			
			if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
				
				graphicIcons.forEach(graphicIcon => {
					
					graphicIcon.unselect();
				});
			}
		});
	}
	
	goto(graphicIcon, zoomLvl) {
				
		let lon = graphicIcon.layerElement.longitude;
		let lat = graphicIcon.layerElement.latitude;
		
		this.olMap.getView().animate({
			center: ol.proj.fromLonLat([lon, lat]),
			zoom: zoomLvl,
			duration: 3000
		});
	}
	
	isElementLayerVisible(graphicIcon) {
			
		let isVisible = false;
		
		for (let i = 0; !isVisible && i < this.metadata.layers.length; i++) {
			 
			let layer = this.metadata.layers[i];
			
			if (this.layersVisible.find(layerVisible => layerVisible === layer.id)) {
				
				let layerElement = layer.layerElements.find(e => e.elementId === graphicIcon.element.id);
			
				if (layerElement) {
					
					isVisible = true;
				}	
			}
		}
		
		return isVisible;
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


