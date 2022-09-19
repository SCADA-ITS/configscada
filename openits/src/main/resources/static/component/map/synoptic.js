import { util } from "../../lib/util.js";
import { adapter } from "../../lib/adapter.js";
import GenericMap from "./genericMap.js";
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

export default class Synoptic extends GenericMap {

	constructor(metadata, data, events, w, h, options) {

		super(metadata, data, events, w, h, options);
		this.stomp = {};
		
		// Snap main paper
		this.s = undefined;
		this.synoptic = undefined;
		this.flagMouseMove = false;
		
		this.panZoomTrigger = undefined;
		
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
							
							window.addEventListener("resize", callbackOnResize);
						},
						onDestruct: function() {
		
							let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
			
							if (stompSubscriptorService && self.idStompSubscriptor) {
		
								stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
							}	
							
							stop();
							
							window.removeEventListener("resize", callbackOnResize);				
						}
					}
				},
				{ id: "syn-toolbar-main-" + self.uid },
				{ id: "syn-toolbar-actions-" + self.uid },
				{ id: "syn-window-actions-" + self.uid }
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
		
		function getMultiViewCellActions() {
			
			let cells = [];
			
			if (Array.isArray(self.metadata.layers) && self.metadata.layers.length > 0) {
				
				let elementLayers = self.metadata.layers.map(layer => {
						
					let newLayer = webix.copy(layer);
					
					newLayer.label = i18n.t(newLayer.label);
					newLayer.image = "tree_layer.png";
					
					return newLayer;
				})
				
				let dataMapLayer = [{
					layers: elementLayers,
					layerRelationships: self.metadata.layerRelationships,
					layerType: MapLayer.TYPE_ELEMENT
				}]
				
				let customTreeOptions = webix.copy(MapLayer.DEFAULT_TREE_OPTIONS);
				
				customTreeOptions.push({ treeOption: { id: 10 }, value: getIdsToUncheck() });
				
				self.mapLayer = new MapLayer(dataMapLayer, function(layersId) {
					
					let elementLayerIds = [];
					
					if (Array.isArray(layersId) && layersId.length > 0) {
						
						let elementLayerIdsFiltered = layersId.filter(layerId => layerId.includes(MapLayer.TYPE_ELEMENT.prefix) && !layerId.includes("ROOT"));
						
						if (Array.isArray(elementLayerIdsFiltered) && elementLayerIdsFiltered.length > 0) {
							
							elementLayerIds = elementLayerIdsFiltered.map(elementLayerId => parseInt(elementLayerId.replace(MapLayer.TYPE_ELEMENT.prefix, '')));
						}
					}
					
					showLayers(elementLayerIds);
					//showLayers(layersId)
				}, customTreeOptions);
				
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
			
			return cells;
		}
			
		function drawSynoptic() {
			
			self.s = Snap("#svg-syn-" + self.uid);
			self.s.attr({ width: self.w, height: self.h });
			
			Snap.load(self.metadata.baseLayer, function(fragment) {
				
				self.synoptic = fragment.select(MAIN_TAG);
						
				self.s.append(self.synoptic);
				
				if (Array.isArray(self.metadata.layers) && self.metadata.layers.length > 0) {
					
					self.synoptic.mouseup(onMouseUp);
					self.synoptic.mousemove(function() { self.flagMouseMove = true });
					self.synoptic.mousedown(function() { self.flagMouseMove = false });
					
					loadLayerBase();
					
					loadGraphicIcons();
				}
				else {
				
					onBeforeLoadGraphicIcon();
				}
			});
			
			function onMouseUp(e) {
			
				if (!self.flagMouseMove && e.which === GenericMap.MOUSE_EVENT_LEFT_PRESSED) {
								
					unselectAll();
				}
			}
		}
		
		function callbackOnResize() {
				
			if ($$(self.uid) && $$("syn-" + self.uid)) {

				let containerWidth = $$(self.uid).$width;
				let containerHeight = $$(self.uid).$height;

				let synopticNode = $$("syn-" + self.uid).getNode().querySelector("#" + "svg-syn-" + self.uid);

				if (synopticNode && self.panZoomTrigger) {

					synopticNode.style.width = containerWidth;
					synopticNode.style.height = containerHeight;

					self.panZoomTrigger.resize();
					self.panZoomTrigger.reset();
				}
			}
		}
		
		function select(elementId) {
			
			if (self.graphicIcons) {
				
				let graphicIcons = getGraphicIcons(elementId)
				
				if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
					
					unselectAll();
					
					graphicIcons[0].select();
					
					if (self.panZoomTrigger) {
						
						let currentZoom = self.panZoomTrigger.getZoom();
						
						self.panZoomTrigger.zoom(1);
						
						self.panZoomTrigger.pan({
							x: (self.panZoomTrigger.getSizes().width / 2) - graphicIcons[0].layerElement.x,
							y: (self.panZoomTrigger.getSizes().height / 2) - graphicIcons[0].layerElement.y
						});
						
						self.panZoomTrigger.zoom(currentZoom);
					}
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
								
								self.panZoomTrigger.zoomIn();
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
								
								self.panZoomTrigger.zoomOut();								
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
								
								self.panZoomTrigger.reset();
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
						hidden: (Array.isArray(self.graphicIcons) && self.graphicIcons.length === 0),
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
					css: "button",
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
					css: "button",
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
				
				let cmdCallbacks = undefined;
				
				if (self.events && Array.isArray(self.events[GenericMap.EventTypes.ON_COMMAND_EXEC]) && 
					self.events[GenericMap.EventTypes.ON_COMMAND_EXEC].length > 0) {
						
					cmdCallbacks = self.events[GenericMap.EventTypes.ON_COMMAND_EXEC];
				}
				
				let contextMenu = new MapContextMenu(self.metadata.commands, getAllPks(), getSelectedPks, cmdCallbacks);
				
				let contextMenuView = contextMenu.getView();
				contextMenuView.master = "svg-syn-" + self.uid;

			    webix.ui(contextMenuView);
			}
		}
		
		function getIdsToUncheck() {
			
			let idsToUncheck;
			let layers = self.metadata.layers;
			
			if (Array.isArray(layers) && layers.length > 0) {
				
				let layersToUncheck = layers.filter(layer => layer.active === false);
				
				if (Array.isArray(layersToUncheck) && layersToUncheck.length > 0) {

					idsToUncheck = layersToUncheck.map(layer => { return MapLayer.TYPE_ELEMENT.prefix + layer.id });
				}	
			}
			
			return idsToUncheck;
		}			
		
		function getPanelActions() {
			
			let viewPanelActions;
			
			viewPanelActions = {
				id: "syn-window-actions-" + self.uid,
				//css: "syn-window-actions",
				hidden: true,
				top: 70, 
				right: 10,
				width: 300,
				height: self.h - 200,
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
			
			onBeforeLoadGraphicIcon();
		}
		
		function onBeforeLoadGraphicIcon() {
			
			if (self.layerElements > 0) {
				
				if (++self.layerElementsLoaded === self.layerElements) {
				
					self.panZoomTrigger = svgPanZoom("#svg-syn-" + self.uid, OPTIONS_PAN_ZOOM);
					
					self.panZoomTrigger.setBeforePan(beforePan);
					
					loadElementInfo();	
				}	
			}
			else {
				
				self.panZoomTrigger = svgPanZoom("#svg-syn-" + self.uid, OPTIONS_PAN_ZOOM);
					
				self.panZoomTrigger.setBeforePan(beforePan);
						
				webix.ui(getToolbarMain(), $$("syn-toolbar-main-" + self.uid));
			}
			

	        function beforePan(oldPan, newPan) {
		
	        	let   gutterWidth = 100
		            , gutterHeight = 100
		            , sizes = this.getSizes()
		            , leftLimit = -((sizes.viewBox.x + sizes.viewBox.width) * sizes.realZoom) + gutterWidth
		            , rightLimit = sizes.width - gutterWidth - (sizes.viewBox.x * sizes.realZoom)
		            , topLimit = -((sizes.viewBox.y + sizes.viewBox.height) * sizes.realZoom) + gutterHeight
		            , bottomLimit = sizes.height - gutterHeight - (sizes.viewBox.y * sizes.realZoom);
	
	          	let customPan = {};
	          	customPan.x = Math.max(leftLimit, Math.min(rightLimit, newPan.x));
	          	customPan.y = Math.max(topLimit, Math.min(bottomLimit, newPan.y));
	
	          	return customPan;
	        }
		}
		
		function loadElementInfo() {
			
			let loadedElements = 0;
			
			self.data.forEach(element => {
			
				try {
	
					const lazy = async _ => {
						
						updateGraphicIcons(element);
						
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
		
			let arrGraphicIcons = self.graphicIcons.get(elementId);
			
			if (arrGraphicIcons) {
				
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
		
		function updateGraphicIcons(element) {
			
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
					
					self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, TOPIC_ELEMENTS, onMessage, fields);
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
						
						updateGraphicIcons(element);
					}
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
