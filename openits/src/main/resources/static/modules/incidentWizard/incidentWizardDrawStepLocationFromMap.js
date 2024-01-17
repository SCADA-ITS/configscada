import IncidentWizardDrawStep from "./incidentWizardDrawStep.js";
import { util } from "../../lib/util.js";
import MessageBox from "../../component/messageBox.js";
import MapLayer  from "../../component/map/mapLayer.js";
import SearchDialogBox from "../../component/searchDialogBox.js";

const POINTER_HAND = 'pointer';
const POINTER_DEFAULT = '';
const TREE_PANEL_WIDTH = 275;
const PICTOGRAM_INCIDENT_TYPE_PATH = "incident_type";
const PICTOGRAM_INCIDENT_TYPE_UNKNOWN = "incident_type/undefined.png";
const DEFAULT_ZOOM = 5;

export default class IncidentWizardDrawStepLocationFromMap extends IncidentWizardDrawStep {

	constructor(parent, step) {

		super(parent, step);
		this.uid = webix.uid();
		
		this.selectedArea = parent.args.selectedArea;
		this.marks = [];
		this.olMap = undefined;
		this.incidentTypeIdSelected = undefined;
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
								getContent()
							]
						},
						{ width: 45 }
					]				
				}
			]
		};
		
		function getContent() {
			
			let viewContent = {};
			
			viewContent = {
				
				cols: [
					getTreeIncidentTypes(),
					{ view: "resizer" },
					{ id: "view-map-" + self.parent.uid } 
				],
				on: {
					onAfterCollapse: updateSize,
					onAfterExpand: updateSize				
				}
			}
			
			function updateSize() {
				
				if (self.olMap) {
							
					self.olMap.updateSize();
				}
			}
			
			function getTreeIncidentTypes() {
				
				let viewTree = {};
				
				viewTree = {
					width: TREE_PANEL_WIDTH,
					header: function() {
						
						return `<div>
									<span class='ico webix_icon mdi mdi-view-headline'></span>
									<span>${i18n.t("LBL_INCIDENT_TYPES")}</span>
								</div>`;
					},
					body: {
						id: "tree-incident-types-" + self.uid
					}
				};
				
				return viewTree;
			}
			
			return viewContent;
		}

		return viewStepLocationFromMap;
	}
	
	loadData(onReady) {
		
		let self = this;
		let incidentTypesLocations = new Map();
		let incidentTypesStretchs = new Map();
		
		loadLocations(onReadyLocations);
		
		function onReadyLocations() {
			
			if (onReady) {
				
				onReady();
			}
		}
		
		function getMap() {
				
			let viewMap;
			
			viewMap = {
				view: "abslayout",
				id: "view-map-" + self.parent.uid,
				cells: [
					{
						view: "template",
						relative: true,
						css: { "background": "#9e9e9e" },
						template: function() {
										
							let t;
							let h = $$("view-map-" + self.parent.uid).$height;
							
							t = `<div>
									<div id="map-${self.parent.uid}" style="height: ${h}px; width: 100%">
								 </div>`;
							
							return t;
						},
						on: {
						
							onViewResize: function() {
								
								if (self.olMap) {
									
									self.olMap.updateSize();	
								}
							}	
						}		
					},
					getToolbarMain()
				]
			};

			return viewMap;
		}
		
		function getToolbarMain() {
			
			let viewToolbarMain = {
				
				view: "toolbar",
				id: "map-toolbar-main-" + self.parent.uid,
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
									center: ol.proj.fromLonLat([((self.selectedArea.startCoordinates[0] + self.selectedArea.endCoordinates[0]) / 2), 
													((self.selectedArea.startCoordinates[1] + self.selectedArea.endCoordinates[1]) / 2)]),
									zoom: DEFAULT_ZOOM,
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
						view: "button",
						type: "icon",
						icon:"mdi mdi-magnify",
						tooltip: i18n.t("LBL_SEARCH"),
						css: "button",
						width: 40, height: 40,
						on: {
							onItemClick: function() {
								
								searchLocation();
							}
						}
					},
					{}	
				]
				
			};
						
			viewToolbarMain.cols.push(viewButtonsZoom);
			viewToolbarMain.cols.push(viewButtonResetZoom);
			viewToolbarMain.cols.push(viewButtonSearch);
			
			function searchLocation() {
				
				let incidentTypeStretchs;
				let incidentTypeLocations;
				
				if (self.incidentTypeIdSelected) {
					
					incidentTypeStretchs = incidentTypesStretchs.get(self.incidentTypeIdSelected);
					incidentTypeLocations = incidentTypesLocations.get(self.incidentTypeIdSelected);
					
					if (Array.isArray(incidentTypeStretchs) && incidentTypeStretchs.length > 0 &&
					    Array.isArray(incidentTypeLocations) && incidentTypeLocations.length > 0) {
						
						let dialogPlanEntity = webix.copy(SearchDialogBox.CALLBACK);				
						dialogPlanEntity.fillDialogEntity = fillDialogEntity;
						dialogPlanEntity.dataType = SearchDialogBox.DIALOG_DATA_TYPE_TREE;
						dialogPlanEntity.height = 600;
						dialogPlanEntity.width = 800;
						dialogPlanEntity.data = {};
						
						let s = new SearchDialogBox(i18n.t("LBL_SELECT_LOCATION"), dialogPlanEntity, onAccept);
						s.show();	
					}
					else {
					
						(new MessageBox(MessageBox.WARN, i18n.t("LBL_NO_LOCATIONS_AVAILABLE_SELECTED_INCIDENT_TYPE"))).show();	
					}
				}
				else {
					
					(new MessageBox(MessageBox.WARN, i18n.t("LBL_INCIDENT_TYPE_NOT_SELECTED"))).show();
				}
				
				function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
							
					let itemsTree = [];
					let itemRelationships = [];
					
					incidentTypeStretchs.forEach(stretch => {
						
						itemsTree.push({
							id: stretch.id,
							label: stretch.alias,
							folder: true
						});	
					});
					
					incidentTypeLocations.forEach(location => {
						
						itemsTree.push({
							id: location.id,
							label: location.alias,
							folder: false
						});
						
						if (Array.isArray(location.stretchs) && location.stretchs.length > 0) {
							
							location.stretchs.forEach(stretch => {
							
								itemRelationships.push({
									id: itemRelationships.length + 1,
									nodeParentId: stretch.id,
									nodeChildId: location.id
								});		
							});
						}
					});

					dialogEntity.data.nodes = itemsTree;
					dialogEntity.data.relationships = itemRelationships;
					dialogEntity.data.nodeParentId = "nodeParentId";
					dialogEntity.data.nodeChildId = "nodeChildId";
					
					onFilledDialogEntity(dialogEntity);			
				}
				
				function onAccept(selection) {
	
					if (Array.isArray(selection) && selection.length > 0 && Array.isArray(self.marks)) {
						
						self.parent.mapLocationSelected = undefined;
						
						self.marks.forEach(mark => {
							
							let markSelected = (mark.get("location") && mark.get("location").id === selection[0].id_);
							
							const iconStyle = new ol.style.Style({
								image: new ol.style.Icon({
							    	src: markSelected ? 'img/marker-selected.png' : 'img/marker.png',
									scale: 0.8
							  	})
							}); 
							
							mark.setStyle(iconStyle);
							
							if (markSelected) {
							
								self.parent.mapLocationSelected = mark.get('location');	
							}
						});

						self.parent.incidentWizardAction.validateStep(self.parent.incidentWizardAction.getCurrentStep());
						
					}				
				}
			}
			
			return viewToolbarMain;
		}
		
		function zoom(level) {
			
			self.olMap.getView().animate({
				zoom: level,
			  	duration: 250
			});
		}
		
		
		
		function loadLocations(onReadyLocations) {
			
			let locations;
			let stretchs = new Set();
			let incidentTypes = new Set();
			let incidentTypeStretchs;
			
			webix.ajax().get(appConfig.restUrl + self.parent.config.restEndpoints.locations).then(
						
				function(dat) {

					if (dat != null) {
	
						locations = dat.json();
						
						if (Array.isArray(locations)) {
							
							// Filter locations configured in json file
							locations = locations.filter(location => {
									return (self.parent.config.locationTypes.indexOf(location.locationType.id) !== -1)
								});
								
							// Filter locations in selected area
							if (Array.isArray(locations) && locations.length > 0) {
				
								locations = locations.filter(location => {
									
									let isInside = false;
									
									if (Array.isArray(location.coordinates) && location.coordinates.length > 0) {
										
										let locationLon = location.coordinates[0].lon;
										let locationLat = location.coordinates[0].lat;
										
										if (self.selectedArea && 
											Array.isArray(self.selectedArea.startCoordinates) && self.selectedArea.startCoordinates.length > 1 && 
											Array.isArray(self.selectedArea.endCoordinates) && self.selectedArea.endCoordinates.length > 1) {
											
											let limitCoordinates = [self.selectedArea.startCoordinates[0],
																	self.selectedArea.startCoordinates[1],
																	self.selectedArea.endCoordinates[0],
																	self.selectedArea.endCoordinates[1]];
											
											isInside = util.ui.isInside(limitCoordinates, locationLon, locationLat);	
										}
									}
									
									return isInside;
								});
								
								// Load stretchs associated to locations
								if (Array.isArray(locations) && locations.length > 0) {												
									
									locations.forEach(location => {
										
										if (Array.isArray(location.stretchs)) {
											
											location.stretchs.forEach(s => {
										
												stretchs.add(s.id);		
											});
										}
									});
									
									if (stretchs.size > 0) {
									
										webix.ajax().get(appConfig.restUrl + self.parent.config.restEndpoints.incidentTypeStretchsList).then(
											
											function(d) {
												
												if (d != null) {
			
													incidentTypeStretchs = d.json();
													
													if (Array.isArray(incidentTypeStretchs)) {
														
														incidentTypeStretchs = incidentTypeStretchs.filter(incidentTypeStretch => stretchs.has(incidentTypeStretch.stretch.id));
														
														if (Array.isArray(incidentTypeStretchs) && incidentTypeStretchs.length > 0) {
															
															incidentTypeStretchs.forEach(incidentTypeStretch => {
														
																incidentTypes.add(incidentTypeStretch.incidentType.id);		
															});
															
															associateIncidentTypesLocations();
															
															$$("loading-" + self.uid).hide();
															$$(self.uid).show();
															
															webix.ui(getViewMapLayer(), $$("tree-incident-types-" + self.uid));
															
															webix.ui(getMap(), $$("view-map-" + self.parent.uid));
															
															self.olMap = createMap();
														}
													}
													else {
														
														(new MessageBox(MessageBox.WARN, i18n.t("LBL_NO_LOCATIONS_AVAILABLE_SELECTED_AREA"), null, function() {

															if (self.parent.args && self.parent.args.containerId) {

																$$(self.parent.args.containerId).close();
															}
														})).show();													
													}
												}
												
												if (onReadyLocations) {
								
													onReadyLocations();
												}
											}
										);
									}
									else {
										
										if (onReadyLocations) {
								
											onReadyLocations();
										}
									}
								}
								else {
									
									if (onReadyLocations) {
								
										onReadyLocations();
									}
									
									(new MessageBox(MessageBox.WARN, i18n.t("LBL_NO_LOCATIONS_AVAILABLE_SELECTED_AREA"), null, function() {

										if (self.parent.args && self.parent.args.containerId) {

											$$(self.parent.args.containerId).close();
										}
									})).show();															
								}
							}
						}
						else {
							
							if (onReadyLocations) {
								
								onReadyLocations();
							}
						}
					}
				}
			);
			
			function associateIncidentTypesLocations() {
			
				if (Array.isArray(locations) && locations.length > 0 &&
					Array.isArray(incidentTypeStretchs) && incidentTypeStretchs.length > 0) {
						
					incidentTypeStretchs.forEach(incidentTypeStretch => {
						
						let incidentTypeId = incidentTypeStretch.incidentType.id;
						let stretchId = incidentTypeStretch.stretch.id;
						
						let locationsByIncidentType = locations.filter(location => location.stretchs.findIndex(locationStretch => locationStretch.id === stretchId) !== -1);
						
						if (Array.isArray(locationsByIncidentType) && locationsByIncidentType.length > 0) {
							
							let currentLocationsByIncidentTypes = incidentTypesLocations.get(incidentTypeId);
							
							if (!Array.isArray(currentLocationsByIncidentTypes)) {
								
								currentLocationsByIncidentTypes = [];
								
								incidentTypesLocations.set(incidentTypeId, currentLocationsByIncidentTypes);
							}
							
							locationsByIncidentType.forEach(locationItem => {
							
								if (currentLocationsByIncidentTypes.findIndex(e => e.id === locationItem.id) === -1) {
									
									currentLocationsByIncidentTypes.push(locationItem)	
								}
							});
							
							let currentStretchsByIncidentTypes = incidentTypesStretchs.get(incidentTypeId);
							
							if (!Array.isArray(currentStretchsByIncidentTypes)) {
								
								currentStretchsByIncidentTypes = [incidentTypeStretch.stretch];
								
								incidentTypesStretchs.set(incidentTypeId, currentStretchsByIncidentTypes);
							}
							else {
								
								currentStretchsByIncidentTypes.push(incidentTypeStretch.stretch);
							}
						}
					});
				}	
			}
			
			function getViewMapLayer() {
				
				let incidentTypesAvailable = window.em.getAll(EntityType.IMS_INCIDENT_TYPE);
				let dataMapLayer = [];
				let incidentTypeLayers = [];
				
				incidentTypesAvailable = incidentTypesAvailable.filter(e => incidentTypes.has(e.id));
				
				incidentTypeLayers = incidentTypesAvailable.map(incidentType => {
						
					return {
						
						id: incidentType.id,
						label: incidentType.alias + (incidentType.description ? " (" + incidentType.description + ")" : ""),
						description: incidentType.description,
						name: incidentType.alias,
						layerElements: [],
						visible: incidentType.visible,
						image: incidentType.icon ? PICTOGRAM_INCIDENT_TYPE_PATH + "/" + incidentType.icon : PICTOGRAM_INCIDENT_TYPE_UNKNOWN  
					}
				});
				
				dataMapLayer.push({
					layers: incidentTypeLayers,
					layerRelationships: [],
					layerType: MapLayer.TYPE_INCIDENT
				});
				
				let treeOptions = [
					{ treeOption: { id: 2}, value: false },	// multiselect
					{ treeOption: { id: 3}, value: true }, 	// sorted
					{ treeOption: { id: 4}, value: true }, 	// cmd_buttons
					{ treeOption: { id: 5}, value: true }, 	// search
					{ treeOption: { id: 6}, value: true },	// expand_all
					{ treeOption: { id: 7}, value: false }	// check_all
				]
				
				self.mapLayer = new MapLayer(dataMapLayer, function(layersId) {
										
					if (Array.isArray(layersId)) {
						
						let indidentTypeLayersSelected = layersId.filter(layerId => layerId.includes(MapLayer.TYPE_INCIDENT.prefix));
						
						indidentTypeLayersSelected = indidentTypeLayersSelected.map(e => e.replace(MapLayer.TYPE_INCIDENT.prefix, ""));
						
						if (Array.isArray(indidentTypeLayersSelected) && indidentTypeLayersSelected.length === 1 && indidentTypeLayersSelected[0]) {
							
							webix.ui(getMap(), $$("view-map-" + self.parent.uid));
																	
							self.olMap = createMap();
							
							self.incidentTypeIdSelected = indidentTypeLayersSelected[0]; 
							
							drawLocations(self.incidentTypeIdSelected);
							
							self.parent.mapIncidentTypeSelected = window.em.getByField(EntityType.IMS_INCIDENT_TYPE, "id", self.incidentTypeIdSelected);
							
							let stretchsByIncident = incidentTypesStretchs.get(self.incidentTypeIdSelected);
							
							if (Array.isArray(stretchsByIncident)) {
								
								self.parent.incidentData.stretchIds = stretchsByIncident.map(e => { return e.id } );
							}
						}
					}
				}, treeOptions);
				
				return self.mapLayer.getView();
			}
			
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
						zoom: DEFAULT_ZOOM,
					})
				}; 
				
				map = new ol.Map(configMap);
				
				webix.delay(function() {

					map.updateSize();
				}, null, null, 1000);
				
				return map;
			}
					
			function drawLocations(incidentTypeId) {
				
				self.marks = [];
				
				let tooltipDiv = document.createElement("div");
				tooltipDiv.setAttribute("id", "tooltip-location");
				tooltipDiv.setAttribute("class", "graphic-icon-tooltip-simple");
				tooltipDiv.setAttribute("display", "none");
				tooltipDiv.setAttribute("style", "position: relative; display: none;");
				document.body.appendChild(tooltipDiv);
				
				let locationsByIncidentType = incidentTypesLocations.get(incidentTypeId);
				
				if (Array.isArray(locationsByIncidentType) && locationsByIncidentType.length > 0) {
					
					locationsByIncidentType.forEach(location => {
						
						if (Array.isArray(location.coordinates) && location.coordinates.length > 0) {
							
							let locationLon = location.coordinates[0].lon;
							let locationLat = location.coordinates[0].lat;
							
							// SE CREA EL MARCADOR EN LAS COORDENADAS INDICADAS
							let mark = new ol.Feature({
								location: location,
								geometry: new ol.geom.Point(
									ol.proj.fromLonLat([locationLon, locationLat])
								)
							});
							
							const iconStyle = new ol.style.Style({
								image: new ol.style.Icon({
							    	src: 'img/marker.png',
									scale: 0.8
							  	})
							});
							
							mark.setStyle(iconStyle);
							self.marks.push(mark);
						}
					});
				}
				
				if (self.marks.length > 0) {
					
					// SE CREA LA CAPA CON EL ARRAY DE MARCADORES Y SE AÑADE AL MAPA
					let locationsLayer = new ol.layer.Vector({
						source: new ol.source.Vector({
							features: self.marks
						})
					});
		
					self.olMap.addLayer(locationsLayer);
					
					// SE INYECTA EL EVENTO ONCLICK
					let selectLocation = new ol.interaction.Select(
						{ 
							condition: ol.events.condition.click,
							toggleCondition: ol.events.condition.never
						}
					);
					self.olMap.addInteraction(selectLocation);
		
					selectLocation.on('select', function(evt) {
		
						let mark = evt.selected[0];
						
						if (mark) {
							
							// Unselect current
							if (self.parent.mapLocationSelected) {
								
								let currentMarkSelected = self.marks.find(m => m.get("location") && m.get("location").id === self.parent.mapLocationSelected.id);
								
								if (currentMarkSelected) {
									
									currentMarkSelected.setStyle(new ol.style.Style({
										image: new ol.style.Icon({
									    	src: 'img/marker.png',
											scale: 0.8
									  	})
									}));
								}
							}
		
							const iconStyle = new ol.style.Style({
								image: new ol.style.Icon({
							    	src: 'img/marker-selected.png',
									scale: 0.8
							  	})
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
					
					self.olMap.addOverlay(tooltip);
					
					
					// change mouse cursor when over marker
					self.olMap.on("pointermove", function (evt) {
					    
						let feature = this.forEachFeatureAtPixel(evt.pixel, function(f) {
							
							tooltip.setPosition(f.getGeometry().getCoordinates());
						    tooltip.getElement().innerHTML = `<div>${f.get('location').alias}</div>`;
							tooltip.getElement().style.display = "block";
		
					        return f;
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
