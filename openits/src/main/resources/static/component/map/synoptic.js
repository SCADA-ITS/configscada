import { util } from "../../lib/util.js";
import MapLayer from "./mapLayer.js";
import MapAction from "./mapAction.js";
import MapContextMenu from "./mapContextMenu.js";
import GraphicIcon from "../graphicIcon/graphicIcon.js";

const MAIN_TAG = "#main";
const TOPIC_ELEMENTS = "/topic/element.>";
const OPTIONS_PAN_ZOOM = {
	controlIconsEnabled: false,
	dblClickZoomEnabled: false
};

export default class Synoptic {

	constructor(metadata, data, events, w, h, options) {

		this.metadata = metadata;
		this.data = data;
		this.events = events;
		this.w = w;
		this.h = h;
		this.options = options;
		this.stomp = {};
		this.uid = webix.uid();
		
		// Snap main paper
		this.s = undefined;
		this.synoptic = undefined;
		this.flagMouseMove = false;
		
		this.panZoomTiger = undefined;
		
		this.graphicIcons = new Map();
		this.baseLayers = new Map();
		this.layerElements = 0;
		this.layerElementsLoaded = 0;
		
		this.mapLayer = undefined;
		this.mapAction = undefined;
		
		this.idStompSubscriptor = undefined;
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
					id: "syn-" + self.uid,
					template: function() {
						return `<div oncontextmenu="return ${Array.isArray(self.metadata.commands) && self.metadata.commands.length > 0}">
									<svg id=${"svg-syn-" + self.uid}></svg>
								</div>`
					},
					on: {
						onAfterRender: function() {
		
							drawSynoptic();
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
			
		function drawSynoptic() {
			
			self.s = Snap("#svg-syn-" + self.uid);
			self.s.attr({ width: self.w, height: self.h });
			
			Snap.load(self.metadata.baseLayer, function(fragment) {
				
				self.synoptic = fragment.select(MAIN_TAG);
						
				self.s.append(self.synoptic);
				
				self.synoptic.mouseup(onMouseUp);
				self.synoptic.mousemove(function() { self.flagMouseMove = true });
				self.synoptic.mousedown(function() { self.flagMouseMove = false });
				
				loadLayerBase();
				
				loadGraphicIcons();			
			});
			
			function onMouseUp() {
			
				if (!self.flagMouseMove) {
								
					unselectAll();
				}
			}
		}
		
		function select(elementId) {
			
			if (self.graphicIcons) {
				
				let graphicIcons = getGraphicIcons(elementId)
				
				if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
					
					unselectAll();
					
					graphicIcons.forEach(graphicIcon => {
						graphicIcon.select();
					});
				}
			}
		}
		
		function unselectAll() {
			
			var allItems = self.s.selectAll("*");
				
			allItems.forEach(e => {
				
				if (e.attr('id') === 'selBox') {
					
					e.attr({ visibility: "hidden" });
				}
			});	
		}
		
		function getToolbarMain() {
			
			let viewToolbarMain = {
				
				view: "toolbar",
				id: "syn-toolbar-main-" + self.uid,
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
								
								self.panZoomTiger.zoomIn();
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
								
								self.panZoomTiger.zoomOut();								
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
						css: "button-zoom-reset",
						width: 40, height: 40,
						on: {
							onItemClick: function() {
								
								self.panZoomTiger.reset();
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
						css: { "background": "#009688 !important", "border-radius": "10px !important" },
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
										
										let cboSearch = $$("syn-toolbar-zoom-cbo-search-" + self.uid);
										let cboSearchSeparator = $$("syn-toolbar-zoom-cbo-search-sep-" + self.uid);
										
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
										id: "syn-toolbar-zoom-cbo-search-" + self.uid,
										hidden: true,
										width: 200, height: 30,
										suggest: function() {
											
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
																	value: element.alias + " - " + i18n.t(elementType.labelAlias)
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
										on:{
											onChange: function(newValue, oldValue, config){
										        
												select(newValue);
										 	}
										}
									},
									{}
								]
							},
							{ 
								id: "syn-toolbar-zoom-cbo-search-sep-" + self.uid,
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
				id: "syn-toolbar-actions-" + self.uid,
				css: "syn-toolbar-actions",
				borderless: true,
				top: 20, 
				right: 20,
				cols: [
					{ width: 1 }
				]
			};
			
			if (Array.isArray(self.metadata.layers) && self.metadata.layers.length > 0) {
				
				let viewButtonLayers = {
					view: "button",
					type: "icon",
					icon:"mdi mdi-layers",
					tooltip: i18n.t("LBL_SHOW_LAYERS"),
					css: "button-layers",
					width: 40, height: 40,
					on: {
						onItemClick: function() {
							
							$$("syn-window-actions-" + self.uid).show();
							self.mapLayer.show();
							
							webix.html.removeCss( $$("syn-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-actions");
							webix.html.addCss( $$("syn-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-layers");
							$$("syn-window-actions-toolbar-title-" + self.uid).setValues({ title: i18n.t("LBL_LAYERS"), icon: "mdi mdi-layers"})
						}
					}
				};
				
				viewToolbarActions.cols.push(viewButtonLayers);	
			}
			
			if (Array.isArray(self.metadata.commands) && self.metadata.commands.length > 0) {
				
				let viewButtonActions = {
					view: "button",
					type: "icon",
					icon:"mdi mdi-dots-vertical",
					tooltip: i18n.t("LBL_SHOW_ACTIONS"),
					css: "button-actions",
					width: 40, height: 40,
					on: {
						onItemClick: function() {
							
							$$("syn-window-actions-" + self.uid).show();
							self.mapAction.show();
							
							webix.html.removeCss( $$("syn-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-layers");
							webix.html.addCss( $$("syn-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-actions");
							$$("syn-window-actions-toolbar-title-" + self.uid).setValues({ title: i18n.t("LBL_ACTIONS"), icon: "mdi mdi-dots-vertical"})
						}
					}
				};
				
				viewToolbarActions.cols.push(viewButtonActions);	
			} 
			
			return viewToolbarActions;	
		}
	
		function loadContextMenu() {
			
			if (Array.isArray(self.metadata.commands) && self.metadata.commands.length > 0) {
				
				let contextMenu = new MapContextMenu(self.metadata.commands, getAllPks(), getSelectedPks);
				
				let contextMenuView = contextMenu.getView();
				contextMenuView.master = "svg-syn-" + self.uid;

			    webix.ui(contextMenuView);
			}
		}
		
		function getPanelActions() {
			
			let viewPanelActions = {
				id: "syn-window-actions-" + self.uid,
				//css: "syn-window-actions",
				hidden: true,
				top: 70, 
				right: 10,
				width: 300,
				height: self.h - 80,
				rows: [
					{
						id: "syn-window-actions-toolbar-" + self.uid,
						view: "toolbar",
						height: 25,
						cols: [
							{
								id: "syn-window-actions-toolbar-title-" + self.uid,
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
										
										$$("syn-window-actions-" + self.uid).hide();
									}
								}
							},
						]
					},
					{
						id: "syn-window-actions-multiview-" + self.uid,
						view: "multiview",
						keepViews:true,
						animate: false,
						cells: getMultiViewCellActions()		
					}
				]
			};
			
			return viewPanelActions;
		}
		
		function loadLayerBase() {
			
			let layers = self.metadata.layers;
			
			layers.forEach(layer => {
				
				if (layer.baseLayer) {
					
					Snap.load(layer.baseLayer, function(fragment) {
				
						let baseLayer = fragment.select(MAIN_TAG);
						
						self.baseLayers.set(layer.id, baseLayer);
						
						baseLayer.mouseup(onMouseUp);
						baseLayer.mousemove(function() { self.flagMouseMove = true });
						baseLayer.mousedown(function() { self.flagMouseMove = false });
											
						self.s.append(baseLayer);
					});
					
					function onMouseUp() {
			
						if (!self.flagMouseMove) {
										
							unselectAll();
						}
					}
				}
			});
		}
		
		function loadGraphicIcons() {

			let layers = self.metadata.layers;
			
			layers.forEach(layer => {
				
				layer.layerElements.forEach(layerElement => {
					
					createGraphicIcon(layerElement,
						self.data.find(element => element.id === layerElement.elementId),
						{ type: GraphicIcon.CONTAINER_TYPE_SNAP, obj: self.s },
						function(graphicIconInstance) {

							onLoadGraphicIcon(layerElement.elementId, graphicIconInstance);
						});
						
					self.layerElements++;
				});
			});
		}
		
		function onLoadGraphicIcon(elementId, graphicIconInstance) {
			
			addGraphicIcon(elementId, graphicIconInstance);
			
			if (++self.layerElementsLoaded === self.layerElements) {
				
				self.panZoomTiger = svgPanZoom("#svg-syn-" + self.uid, OPTIONS_PAN_ZOOM);
				
				loadElementInfo();
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
							
							connectWS();
							
							webix.ui(getToolbarMain(), $$("syn-toolbar-main-" + self.uid));
							webix.ui(getToolbarActions(), $$("syn-toolbar-actions-" + self.uid));
							webix.ui(getPanelActions(), $$("syn-window-actions-" + self.uid));
							
							loadContextMenu();
						}
					}
					
					lazy();
				}
				catch (error) {
					
					console.log(error);
				}	
			});
			 
		}
				
		function addGraphicIcon(elementId, graphicIcon) {
		
			let arrGraphicIcons;
			
			if (arrGraphicIcons = self.graphicIcons.get(elementId)) {
				
				arrGraphicIcons.push(graphicIcon);
			}
			else {
				
				self.graphicIcons.set(elementId, [graphicIcon]);
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
					if (graphicIcons[0].isSelected()) {
						
						selectedPks.add(elementId);
					}
				}
			});
				
			return Array.from(selectedPks);
		}
		
		function getAllPks() {
			
			return Array.from(self.graphicIcons.keys());
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
		
					let jsFile = window.location.protocol + '//' + window.location.host + '/' + graphicIcon.jsDrawFile;
		
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
			
			function onMessage(element) {

				if (element) {
					
					if (self.options && self.options[Synoptic.Options.DEBUG_MODE]) {
						
						console.log("RX >> " + JSON.stringify(element));
					}
					
					let graphicIcons = getGraphicIcons(element.id);
					
					if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
						
						graphicIcons.forEach(graphicIcon => {
						
							if (element.elementTypeState) {
								
								let elementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", element.elementTypeState.id);
								
								if (elementTypeState) {
									
									if (self.options && self.options[Synoptic.Options.DEBUG_MODE]) {
						
										console.log("Update state for: " + element.id + " with " + JSON.stringify(elementTypeState));
									}
									
									graphicIcon.updateState(elementTypeState); 
								}
							}
							
							if (Array.isArray(element.elementValues) && element.elementValues.length > 0) {
								
								let elementValues = getElementValues();
								
								if (self.options && self.options[Synoptic.Options.DEBUG_MODE]) {
						
									console.log("Update values for: " + element.id + " with " + JSON.stringify(elementValues));
								}
								
								graphicIcon.updateValues(elementValues);
							}
							
							// TODO ALARMS
						});
					}
				}
				
				function getElementValues() {
					
					let elementValuesToUpdate = [];
								
					let elementAux = self.data.find(e => e.id === element.id);
					
					if (elementAux && Array.isArray(elementAux.elementValues) && elementAux.elementValues.length > 0) {
						
						element.elementValues.forEach(elementValue => {
							
							let elementValueAux = elementAux.elementValues.find(e => e.id === elementValue.id);
							
							if (elementValueAux && elementValueAux.elementTypeParam) {
								
								elementValue.elementTypeParam = elementValueAux.elementTypeParam;
								
								elementValuesToUpdate.push(elementValue);
							}	
						})	
					}
					
					return elementValuesToUpdate;
				}
			}
		}
		
		return view;			
	}
} 

Object.defineProperty(Synoptic, 'Options', {
    value: {}
});

Object.defineProperty(Synoptic.Options, "DEBUG_MODE", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});
