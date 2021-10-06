import GraphicIconIncident from "../graphicIcon/graphicIconIncident.js";
import { util } from "../../lib/util.js";
import StompSubscriptorService from "../../services/stompSubscriptorService.js";

const TOPIC_INCIDENT_REPORTS = "/topic/incidentReport.>"
const REST_ENDPOINT_INCIDENT_REPORTS_SUMMARY = "incidents/reports/summary"
const REST_ENDPOINT_INCIDENT_REPORT_SUMMARY = "incidents/reports/${id}/summary"

export default class MapIncident {

	constructor(parent) {
		
		this.parent = parent;
		this.olMap = parent.olMap;
		this.uid = parent.uid;
		this.metadata = parent.metadata;
		this.idStompSubscriptor = undefined;
		this.incidentReports = new Map();
		this.graphicIcons = new Map();
		
		this.snap = undefined;
		this.drawBox = undefined;
		this.polygon = undefined;
	}
	
	load() {
		
		let self = this;
		
		loadIncidents();
		
		function loadIncidents() {
			
			webix.ajax().get(appConfig.restUrl + REST_ENDPOINT_INCIDENT_REPORTS_SUMMARY).then(
				
				function(dat) {

					if (dat != null) {

						let allIncidentReports = dat.json();
						
						if (Array.isArray(allIncidentReports) && allIncidentReports.length > 0) {
							
							let filteredIncidentReports = allIncidentReports.filter(e => {
							
								let res = false;
								
								if (e.location && e.location.lon && e.location.lat) {
								
									//res = isInside(e.location.lon, e.location.lat);
									
									let limitCoordinates = [self.metadata.limitInitLon, self.metadata.limitInitLat, self.metadata.limitEndLon, self.metadata.limitEndLat];
									
									res = util.ui.isInside(limitCoordinates, e.location.lon, e.location.lat);	
								}
								
								return res;
							});
							
							if (Array.isArray(filteredIncidentReports) && filteredIncidentReports.length > 0) {
								
								self.incidentReports = new Map(filteredIncidentReports.map(filteredIncidentReport => [filteredIncidentReport.id, filteredIncidentReport]));
								
								loadGraphicIcons();	
							}
						}
					}
					
					connectWS();
				}
			);
		}
		
//		function isInside(lon, lat) {
//			
//			let res = true;
//			let ext, point, elementPoint, feature, coordsFeatures;
//					
//			if (self.metadata.limitInitLon && self.metadata.limitInitLat && self.metadata.limitEndLon && self.metadata.limitEndLat) {
//	
//				let destLoc = [self.metadata.limitEndLon, self.metadata.limitEndLat];
//				let currentLoc = [self.metadata.limitInitLon, self.metadata.limitInitLat];
//				
//				ext = ol.extent.boundingExtent([destLoc, currentLoc]);
//				ext = ol.proj.transformExtent(ext, ol.proj.get('EPSG:4326'), ol.proj.get('EPSG:3857'));
//				
//				point = ol.proj.fromLonLat([parseFloat(lon), parseFloat(lat)]);
//				elementPoint = new ol.geom.Point(point);
//				feature = new ol.Feature(elementPoint);
//				coordsFeatures = feature.getGeometry().getCoordinates();
//				
//				res = ol.extent.containsXY(ext, coordsFeatures[0], coordsFeatures[1]);
//			}
//			
//			return res;
//		}
		
		function loadGraphicIcons() {
			
			if (self.incidentReports) {
				
				self.incidentReports.forEach(incidentReport => {
					
					loadGraphicIcon(incidentReport);
				})
			}
		}
		
		function unloadGraphicIcon(incidentReportId) {
			
			let graphicIcon = self.graphicIcons.get(incidentReportId);
			
			if (graphicIcon) {
			
				self.graphicIcons.delete(incidentReportId);
				
				let svgId = `svg-${util.getSimpleId(incidentReportId)}-${self.uid}`;
				
				let olOverlay = self.olMap.getOverlayById(svgId.replace("svg", "ol-overlay"),);
			    
				if (olOverlay) {
			       
					self.olMap.removeOverlay(olOverlay);
			    }
			} 
		}
		
		function loadGraphicIcon(incidentReport) {
				
			let layerIncident = {
				id: incidentReport.id + "-" + self.uid,
				incidentId: incidentReport.id,
				horizontalFlip: false,
				label: incidentReport.incidentType.alias,
				showText: true,
				tooltip: true,
				graphicIcon: self.metadata.mapIncident.incidentGraphicIcon,
				incidentReportModuleAction: self.metadata.mapIncident.incidentReportModuleAction
			};
			
			let s = Snap("#" + createSvgIcon());
			
			let container = { 
				type: GraphicIconIncident.CONTAINER_TYPE_OPEN_LAYER,
				obj: s
			};
					
			createGraphicIcon(incidentReport, onLoadGraphicIcon);
									
			function onLoadGraphicIcon(graphicIcon) {
				
				// Hidden
				graphicIcon.hide();
				self.graphicIcons.set(incidentReport.id, graphicIcon);
				
				let olOverlay = new ol.Overlay({
								id: graphicIcon.container.obj.node.id.replace("svg", "ol-overlay"),
								position: ol.proj.fromLonLat([incidentReport.location.lon, incidentReport.location.lat]),
								positioning: 'center-center',
								element: graphicIcon.container.obj.node,
								stopEvent: false
							});
							
				self.olMap.addOverlay(olOverlay);
				
				updateGraphicIcon(incidentReport);
				
				graphicIcon.show();
			}
			
			function createGraphicIcon(incident, onCreate) {
				
				let events = {};
				
				events[GraphicIconIncident.EventTypes.ON_DBL_CLIC] = onDblClic;
				events[GraphicIconIncident.EventTypes.ON_MOUSE_OVER] = onMouseOver;
				events[GraphicIconIncident.EventTypes.ON_MOUSE_OUT] = onMouseOut;
				events[GraphicIconIncident.EventTypes.ON_MOUSE_WHEEL] = onMouseWheel;
				
				let graphicIconInstance = new GraphicIconIncident(layerIncident, incident, container, events);
				
				graphicIconInstance.load(function() {
							
					graphicIconInstance.loadRender(function() {
						
						onLoaded(graphicIconInstance);	
					});
				});
				
				function onMouseOver() {
					
					self.olMap.getInteractions().forEach(function(interaction){interaction.setActive(false);});
					self.parent.mouseOverSvg = true;
				}
				
				function onMouseOut() {
					
					self.olMap.getInteractions().forEach(function(interaction){interaction.setActive(true);});
					self.parent.mouseOverSvg = false;						
				}
				
				function onMouseWheel() {
					
					self.olMap.getInteractions().forEach(function(interaction){interaction.setActive(true);});
					self.parent.mouseOverSvg = false;							
				}
				
				function onDblClic() {
					
					if (self.mapAction) {
						
						// TODO
					} 
				}
				
				function onLoaded() {
					
					onCreate(graphicIconInstance);
				}
			}
			
			function createSvgIcon() {
				
				let svgId = `svg-${util.getSimpleId(layerIncident.id)}`;
				let svgGraphicIcon = document.createElementNS("http://www.w3.org/2000/svg", "svg");
				let w = layerIncident.graphicIcon.width;
				let h = layerIncident.graphicIcon.height;
				
				svgGraphicIcon.setAttribute("id", svgId);
				svgGraphicIcon.setAttribute("width", w);
				svgGraphicIcon.setAttribute("height", h);
				
				document.querySelector("#svg-main-container-" + self.uid).appendChild(svgGraphicIcon);
				
				return svgId;
			}
		}
		
		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {
				
				self.idStompSubscriptor = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(TOPIC_INCIDENT_REPORTS), onMessage);
			}
			
			function onMessage(be, action) {

				if (be && action) {
					
					if (action === StompSubscriptorService.ACTION_CREATE) {

						createIncident(be.id);						
					}
					else if (action === StompSubscriptorService.ACTION_UPDATE) {

						updateIncident(be.id);						
					}
					else if (action === StompSubscriptorService.ACTION_DELETE) {
						
						deleteIncident(be.id);
					}
				}
			}
		}
		
		function createIncident(incidentReportId) {
			
			getIncident(incidentReportId, function(incidentReport) {
				
				self.incidentReports.set(incidentReport.id, incidentReport);
				
				loadGraphicIcon(incidentReport);	
			});	
		}
		
		function updateIncident(incidentReportId) {
			
			getIncident(incidentReportId, function(incidentReport) {
				
				self.incidentReports.set(incidentReport.id, incidentReport);
				
				updateGraphicIcon(incidentReport);
			});
		}
		
		function deleteIncident(incidentReportId) {
		
			if (self.incidentReports.delete(incidentReportId)) {
				
				unloadGraphicIcon(incidentReportId);				
			}
		}
		
		function getIncident(incidentReportId, onResponse) {
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(REST_ENDPOINT_INCIDENT_REPORT_SUMMARY, { id: incidentReportId })).then(

				function(dat) {
					
					if (dat) {
						
						let incidentReport = dat.json();
					
						if (onResponse && incidentReport) {
						
							onResponse(incidentReport);	
						}	
					}
				}
			);	
		}
		
		function updateGraphicIcon(incidentReport) {
			
			let graphicIcon = self.graphicIcons.get(incidentReport.id); 
			
			if (graphicIcon && incidentReport) {
				
				graphicIcon.updateIncident(incidentReport);
			}
		}
	}
	
	unload() {
		
		let self = this;
	
		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
			
		if (stompSubscriptorService && self.idStompSubscriptor) {

			stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
		}		
	}
	
	// PINTAR AREA EN EL MAPA
	addSquare(btnDrawArea) {
		
		let self = this;
		
		// ACTIVAR EL LISTENER SINGLECLICK DEL MAPA MIENTRAS SE AÑADEN LINEAS.
		self.olMap.on('singleclick', function(evt) { evt.preventDefault() });

		let source = new ol.source.Vector();

		// SEA CREA LA CAPA, SE APLICA ESTILO DEL AREA UNA VEZ PINTADA Y SE AÑADE LA CAPA AL MAPA
		let boxLayer = new ol.layer.Vector({
			source: source,
			style: new ol.style.Style({
				fill: new ol.style.Fill({
					color: 'rgba(255 204 51 / 0.6)'  // COLOR DEL RELLENO DEL AREA
				}),
				stroke: new ol.style.Stroke({
					color: '#ffcc33', // COLOR DE LA LINEA DEL AREA
					width: 3
				})
			})
		});

		self.olMap.addLayer(boxLayer);

		addInteractions();
		
		function addInteractions() {

			// PINTADO DEL AREA CON EL ESTILO MIENTRAS SE PINTA 
			self.drawBox = new ol.interaction.DragBox({
				source: source,
				type: "Circle",
				geometryFunction: ol.interaction.Draw.createBox(),
				onBoxEnd: dragEnd  // FUNCION QUE SE EJECUTA TRAS TERMINAR EL PINTADO
			});

			self.olMap.addInteraction(self.drawBox);

			self.snap = new ol.interaction.Snap({ source: source });
			self.olMap.addInteraction(self.snap);
		}

		// EVENTOS AL TERMINAR DE PINTAR EL AREA
		function dragEnd() {

			let coordinates = self.drawBox.getGeometry().getExtent();
			let coord01 = [coordinates[0], coordinates[3]];
			let coord02 = [coordinates[2], coordinates[1]];

			let startCoordinates = ol.proj.transform(coord01, 'EPSG:3857', 'EPSG:4326');
			let endCoordinates = ol.proj.transform(coord02, 'EPSG:3857', 'EPSG:4326');

			console.log("start: LON: " + startCoordinates[0] + " - " + "LON: " + startCoordinates[1] + "   " + "end: LON: " + endCoordinates[0] + " - " + "LAT: " + endCoordinates[1]);

			if (self.metadata.mapIncident.incidentWizardModuleAction) {
			
				let module = util.module.getFromModuleAction(self.metadata.mapIncident.incidentWizardModuleAction.id);
				
				let args = {
					
					selectedArea: {
						startCoordinates: startCoordinates,
						endCoordinates: endCoordinates
					}
				}
				
				util.module.open(module, self.metadata.mapIncident.incidentWizardModuleAction, VIEW_TYPE_WINDOW, null, args);	
			}
			
			webix.html.triggerEvent(btnDrawArea.getInputNode(), "MouseEvents", "click");

			self.polygon = self.drawBox.getGeometry();
			
			document.querySelector("#map-" + self.uid).querySelector('canvas').style.filter="brightness(100%)";
		}
	}
	
	removeSquare() {
		
		let self = this;
		
		self.olMap.un('singleclick', function(evt) { evt.preventDefault() });
		self.olMap.removeInteraction(self.drawBox);
	}
	
	show() {
		
		if (this.graphicIcons && this.graphicIcons.size > 0) {
		
			this.graphicIcons.forEach(graphicIcon => graphicIcon.show());
		}
	}
	
	hide() {
		
		if (this.graphicIcons && this.graphicIcons.size > 0) {
		
			this.graphicIcons.forEach(graphicIcon => graphicIcon.hide());
		}
	}
} 
