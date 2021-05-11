import { util } from "../lib/util.js";
import TreeView from "./treeview.js";
import SynopticLayer from "./synopticLayer.js";
import SynopticAction from "./synopticAction.js";
import { adapter } from "../lib/adapter.js";
import GraphicIcon from "../component/graphicIcon/graphicIcon.js";

const MAIN_TAG = "#main";
const TOPIC_ELEMENTS = "/topic/element.>";

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
		
		this.graphicIcons = new Map();
		this.synopticLayerElements = 0;
		this.synopticLayerElementsLoaded = 0;
		
		this.idStompSubscriptor = undefined;
	}

	getView() {

		let self = this;
		let view;
		
		view = {
			view: "abslayout",
			cells: [
				{
					view: "template",
					relative: true,
					css: { "background": "#9e9e9e" },
					id: "syn-" + self.uid,
					template: function() {
						return `<svg id=${"svg-syn-" + self.uid}></svg>`
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
				getToolbarActions(),
				{
					id: "syn-window-actions-" + self.uid,
					//css: "syn-window-actions",
					hidden: true,
					top: 60, 
					right: 10,
					width: 300,
					height: self.h - 70,
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
					
				}
			]
		}
		
		function getMultiViewCellActions() {
			
			let cells = [];
			
			let synopticLayer = new SynopticLayer(self.uid, self.metadata, onLayersSelectedChange);
			let synopticAction = new SynopticAction(self.uid);
			
			cells.push(synopticLayer.getView());
			cells.push(synopticAction.getView());
			
			function onLayersSelectedChange() {
				
			}
			
			return cells;
		}
			
		function drawSynoptic() {
			
			let flagMouseMove = false;
			self.s = Snap("#svg-syn-" + self.uid);
			self.s.attr({ width: self.w, height: self.h });
			
			
			Snap.load(self.metadata.baseLayer, function(fragment) {
				
				self.synoptic = fragment.select(MAIN_TAG);
						
				self.s.append(self.synoptic);
				
				self.synoptic.mouseup(onMouseUp);
				self.synoptic.mousemove(function() { flagMouseMove = true; });
				self.synoptic.mousedown(function() { flagMouseMove = false;});
				
				loadGraphicIcons();			
			});
			
			function onMouseUp() {
			
				if (!flagMouseMove) {
								
					var allItems = self.s.selectAll("*");
				
					allItems.forEach(e => {
						
						if (e.attr('id') === 'selBox') {
							
							e.attr({ visibility: "hidden" });
						}
					});	
				}
			}
		}
		
		function getToolbarActions() {
		
			let viewToolbarActions = {
				
				view: "toolbar",
				css: "syn-toolbar-actions",
				borderless: true,
				top: 10, 
				right: 10,
				cols: [
					{ width: 1 }
				]
			};
			
			let viewButtonLayers = {
				view: "button",
				type: "icon",
				icon:"mdi mdi-layers",
				css: "button-layers",
				width: 40, height: 40,
				on: {
					onItemClick: function() {
						
						$$("syn-window-actions-" + self.uid).show();
						$$("synoptic-layer-" + self.uid).show();
						
						webix.html.removeCss( $$("syn-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-actions");
						webix.html.addCss( $$("syn-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-layers");
						$$("syn-window-actions-toolbar-title-" + self.uid).setValues({ title: i18n.t("LBL_LAYERS"), icon: "mdi mdi-layers"})
					}
				}
			};
			
			let viewButtonActions = {
				view: "button",
				type: "icon",
				icon:"mdi mdi-dots-vertical",
				css: "button-actions",
				width: 40, height: 40,
				on: {
					onItemClick: function() {
						
						$$("syn-window-actions-" + self.uid).show();
						$$("synoptic-action-" + self.uid).show();
						
						webix.html.removeCss( $$("syn-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-layers");
						webix.html.addCss( $$("syn-window-actions-toolbar-" + self.uid).getNode(), "syn-panel-actions");
						$$("syn-window-actions-toolbar-title-" + self.uid).setValues({ title: i18n.t("LBL_ACTIONS"), icon: "mdi mdi-dots-vertical"})
					}
				}
			};
			
			viewToolbarActions.cols.push(viewButtonLayers);
			viewToolbarActions.cols.push(viewButtonActions);
			
			return viewToolbarActions;	
		}	
		
		function loadGraphicIcons() {

			let synopticLayers = self.metadata.synopticLayers;
			
			synopticLayers.forEach(synLayer => {
				
				synLayer.synopticLayerElements.forEach(synLayerElement => {
					
					createGraphicIcon(synLayerElement,
						self.data.find(element => element.id === synLayerElement.elementId),
						{ type: GraphicIcon.CONTAINER_TYPE_SNAP, obj: self.s },
						function(graphicIconInstance) {

							onLoadGraphicIcon(synLayerElement.elementId, graphicIconInstance);
						});
						
					self.synopticLayerElements++;
				});
			});
		}
		
		function onLoadGraphicIcon(elementId, graphicIconInstance) {
			
			addGraphicIcon(elementId, graphicIconInstance);
			
			if (++self.synopticLayerElementsLoaded === self.synopticLayerElements) {
				
				let optionsPanZoom = {
					controlIconsEnabled: true,
					dblClickZoomEnabled: false
				}
				
				svgPanZoom("#svg-syn-" + self.uid, optionsPanZoom);
				
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
		
		function createGraphicIcon(layerElement, element, container, onCreate) {
			
			let graphicIcon = layerElement.graphicIcon;
			
			try {
	
				const lazy = async _ => {
		
					let jsFile = window.location.protocol + '//' + window.location.host + '/' + graphicIcon.jsDrawFile;
		
					const mod = await import(jsFile);
					
					let graphicIconInstance = new mod.default(layerElement, element, container);
					
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
					
					let graphicIcons = getGraphicIcons(element.id);
					
					if (Array.isArray(graphicIcons) && graphicIcons.length > 0) {
						
						graphicIcons.forEach(graphicIcon => {
						
							if (element.elementTypeState) {
								
								graphicIcon.updateState(element.elementTypeState);	
							}
							
							if (Array.isArray(element.elementValues) && element.elementValues.length > 0) {
								
								graphicIcon.updateValues(getElementValues());
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
