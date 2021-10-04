import IncidentWizardDrawStep from "./incidentWizardDrawStep.js";

export default class IncidentWizardDrawStepLocationFromMap extends IncidentWizardDrawStep {

	constructor(parent, step) {

		super(parent, step);
	}
	
	getView() {
		
		let parent = this.parent;
		let step = this.step;
		let olMap = undefined;
		let selectedArea = this.parent.args.selectedArea;
		
		let viewStepLocationFromMap;

		viewStepLocationFromMap = {

			rows: [
				{
					cols: [
						{ width: 45 },
						{
							rows: [
								{ height: 15 },
								{
									view: "label",
									label: `<span class="ico webix_icon mdi mdi-numeric-${step.position}-box" style="font-size: 30px;"></span><span class="content-wizard-label">${i18n.t(step.stepLabel)}</span>`
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
				id: "view-map-" + parent.uid,
				template: function() {
								
					let t;
					let h = $$("view-map-" + parent.uid).$height;
					let w = $$("view-map-" + parent.uid).$width;
					
					t = `<div>
							<div id="map-${parent.uid}" style="height: ${h}px; width: ${w}px">
						 </div>`;
					
					return t;
				},
				on: {
					onAfterRender: function() {
	
						olMap = createMap();
						
					}
				}
			};
			
			function createMap() {
					
				let map;
				
				let configMap = {
					target: "map-" + parent.uid,
					controls: [],
					interactions : ol.interaction.defaults({doubleClickZoom :false}),
					layers: [
						new ol.layer.Tile({
	
							source: new ol.source.OSM()
						})
					],
					view: new ol.View({
						center: ol.proj.fromLonLat([((selectedArea.startCoordinates[0] + selectedArea.endCoordinates[0]) / 2), 
													((selectedArea.startCoordinates[1] + selectedArea.endCoordinates[1]) / 2)]),
						extent: function() {
							
							let ext = undefined;
							
							if (Array.isArray(selectedArea.startCoordinates) && selectedArea.startCoordinates.length === 2 &&
								Array.isArray(selectedArea.endCoordinates) && selectedArea.endCoordinates.length === 2) {
							
								let destLoc = [selectedArea.endCoordinates[0], selectedArea.endCoordinates[1]];
								let currentLoc = [selectedArea.startCoordinates[0], selectedArea.startCoordinates[1]];
								
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
			
			return viewMap;
		}

		return viewStepLocationFromMap;
	}
}	
