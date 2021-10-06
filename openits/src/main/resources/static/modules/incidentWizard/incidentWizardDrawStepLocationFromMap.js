import IncidentWizardDrawStep from "./incidentWizardDrawStep.js";
import { util } from "../../lib/util.js";
import MessageBox from "../../component/messageBox.js";

const POINTER_HAND = 'pointer'
const POINTER_DEFAULT = ''

export default class IncidentWizardDrawStepLocationFromMap extends IncidentWizardDrawStep {

	constructor(parent, step) {

		super(parent, step);
		this.uid = webix.uid();
		
		this.selectedArea = parent.args.selectedArea;
	}
	
	getView() {

		let self = this;		
		
		let viewStepLocationFromMap;

		viewStepLocationFromMap = {

			rows: [
				{
					id: "loading-" + self.uid,
					rows: [util.ui.getViewDrawLoading()],
					hidden: false
				},
				{
					id: self.uid,
					hidden: true,
					cols: [
						{ width: 45 },
						{
							rows: [
								{ height: 15 },
								{
									view: "label",
									label: `<span class="ico webix_icon mdi mdi-numeric-${self.step.position}-box" style="font-size: 30px;"></span><span class="content-wizard-label">${i18n.t(self.step.stepLabel)}</span>`
								},
								{ height: 17 },
								getMap()
							]
						},
						{ width: 45 }
					]				
				}
			]
		};
		
		function getMap() {
			
			let viewMap;
			
			viewMap = {
				view: "template",
				css: { "background": "#9e9e9e" },
				id: "view-map-" + self.parent.uid,
				template: function() {
								
					let t;
					let h = $$("view-map-" + self.parent.uid).$height;
					let w = $$("view-map-" + self.parent.uid).$width;
					
					t = `<div>
							<div id="map-${self.parent.uid}" style="height: ${h}px; width: ${w}px">
						 </div>`;
					
					return t;
				},
//				on: {
//					onAfterRender: function() {
//	
//						self.olMap = createMap();
//					}
//				}
			};
			
			
			
			return viewMap;
		}

		return viewStepLocationFromMap;
	}
	
	loadData() {
		
		let self = this;
		let olMap = undefined;
		
		loadLocations();
		
		function loadLocations() {
			
			let locations;
			
			webix.ajax().get(appConfig.restUrl + self.parent.config.restEndpoints.locations).then(
						
				function(dat) {

					if (dat != null) {
	
						locations = dat.json();
						
						if (Array.isArray(locations)) {
							
							locations = locations.filter(e => {
									return (self.parent.config.locationTypes.indexOf(e.locationType.id) !== -1)
								});
							
							$$("loading-" + self.uid).hide();
							$$(self.uid).show();
							
							olMap = createMap();
							
							if (olMap) {
								
								drawLocations();	
							}
							
						}
					}
				}
			);
			
			function createMap() {
					
				let map;
				
				let configMap = {
					target: "map-" + self.parent.uid,
					controls: [],
					interactions : ol.interaction.defaults({doubleClickZoom :false}),
					layers: [
						new ol.layer.Tile({
	
							source: new ol.source.OSM()
						})
					],
					view: new ol.View({
						center: ol.proj.fromLonLat([((self.selectedArea.startCoordinates[0] + self.selectedArea.endCoordinates[0]) / 2), 
													((self.selectedArea.startCoordinates[1] + self.selectedArea.endCoordinates[1]) / 2)]),
						extent: function() {
							
							let ext = undefined;
							
							if (Array.isArray(self.selectedArea.startCoordinates) && self.selectedArea.startCoordinates.length === 2 &&
								Array.isArray(self.selectedArea.endCoordinates) && self.selectedArea.endCoordinates.length === 2) {
							
								let destLoc = [self.selectedArea.endCoordinates[0], self.selectedArea.endCoordinates[1]];
								let currentLoc = [self.selectedArea.startCoordinates[0], self.selectedArea.startCoordinates[1]];
								
								ext = ol.extent.boundingExtent([destLoc, currentLoc]);
								ext = ol.proj.transformExtent(ext, ol.proj.get('EPSG:4326'), ol.proj.get('EPSG:3857'));
							}
							
							return ext;			
						}(),
						zoom: 5,
					})
				}; 
				
				map = new ol.Map(configMap);
				
				webix.delay(function() {

					map.updateSize();
				}, null, null, 1000);
				
				return map;
			}
			
			function drawLocations() {
				
				let marks = [];
				
				let tooltipDiv = document.createElement("div");
				tooltipDiv.setAttribute("id", "tooltip-location");
				tooltipDiv.setAttribute("class", "graphic-icon-tooltip-simple");
				tooltipDiv.setAttribute("display", "none");
				tooltipDiv.setAttribute("style", "position: absolute; display: none;");
				document.body.appendChild(tooltipDiv);
				
				if (Array.isArray(locations) && locations.length > 0) {
				
					locations.forEach(location => {
						
						if (Array.isArray(location.coordinates) && location.coordinates.length > 0) {
							
							let locationLon = location.coordinates[0].lon;
							let locationLat = location.coordinates[0].lat;
							
							let limitCoordinates = [self.selectedArea.startCoordinates[0],
													self.selectedArea.startCoordinates[1],
													self.selectedArea.endCoordinates[0],
													self.selectedArea.endCoordinates[1]];
							
							if (util.ui.isInside(limitCoordinates, locationLon, locationLat)) {
								
								// SE CREA EL MARCADOR EN LAS COORDENADAS INDICADAS
								let mark = new ol.Feature({
									location: location,
									geometry: new ol.geom.Point(
										ol.proj.fromLonLat([locationLon, locationLat])
									)
								});
								
								const iconStyle = new ol.style.Style({
									image: new ol.style.Icon({
//								    	anchor: [0.5, 46],
//								    	anchorXUnits: 'fraction',
//								    	anchorYUnits: 'pixels',
								    	src: 'img/marker.png',
										scale: 0.8
								  	}),
								});
								
								mark.setStyle(iconStyle);
				
								marks.push(mark);
							}	
						}
					});
				}
				
				if (marks.length > 0) {
					
					// SE CREA LA CAPA CON EL ARRAY DE MARCADORES Y SE AÑADE AL MAPA
					let locationsLayer = new ol.layer.Vector({
						source: new ol.source.Vector({
							features: marks
						})
					});
		
					olMap.addLayer(locationsLayer);
					
					// SE INYECTA EL EVENTO ONCLICK
					let selectLocation = new ol.interaction.Select(
						{ 
							condition: ol.events.condition.click,
							toggleCondition: ol.events.condition.never
						}
					);
					olMap.addInteraction(selectLocation);
		
					selectLocation.on('select', function(evt) {
		
						let mark = evt.selected[0];
						
						if (mark) {
		
							const iconStyle = new ol.style.Style({
								image: new ol.style.Icon({
//							    	anchor: [0.5, 46],
//							    	anchorXUnits: 'fraction',
//							    	anchorYUnits: 'pixels',
							    	src: 'img/marker-selected.png',
									scale: 0.8
							  	}),
							});
							
							mark.setStyle(iconStyle);
							
							self.parent.mapLocationSelected = mark.get('location');
						}
						else {
							
							self.parent.mapLocationSelected = undefined;
						}
						
						self.parent.incidentWizardAction.validateStep(self.parent.incidentWizardAction.getCurrentStep());
					});
					
					let tooltip = new ol.Overlay({
					  	element: tooltipDiv,
					  	//positioning: 'bottom-left',
						offset: [15, 15]
					});
					
					tooltip.setMap(olMap);
					
					
					// change mouse cursor when over marker
					olMap.on("pointermove", function (evt) {
					    
						let feature = this.forEachFeatureAtPixel(evt.pixel, function(feature, layer) {
							
							tooltip.setPosition(feature.getGeometry().getCoordinates());
						    tooltip.getElement().innerHTML = `<div>${feature.get('location').alias}</div>`;
							tooltip.getElement().style.display = "block";
		
					        return feature;
					    });
	
						this.getTargetElement().style.cursor = (feature ? POINTER_HAND : POINTER_DEFAULT);
						tooltip.getElement().style.display = (feature ? "block" : "none");
					});
				}
				else {
					
					(new MessageBox(MessageBox.WARN, i18n.t("LBL_NO_LOCATIONS_AVAILABLE_SELECTED_AREA"))).show();
				}
			}
		}
	}
}	
