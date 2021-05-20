import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_JSON_VALUE = "ElementTypeParam:71:2:3";

const G_ID_ZONE_PREFIX = "zone";
const G_ID_LINE_PREFIX = "line";
const G_ID_GRAPHIC_PREFIX = "graphic";
const DEFAULT_TEXT_COLOR = "#ffffff";
const PATH_PICTOGRAMS = "img/pictograms";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:71:0", "ElementTypeState:71:2"];

const ZONE_ELEMENT_TYPE = {
	line: "text",
	graphic: "graphic"
}

const jsonDataFake = [
	{
		"zone": 1,
		"alternance_ms": 5000,
		"graphic": [
			{
				"id": 1,
				"value": 20,
				"alternance": 22
			}
		]
	},
	{
		"zone": 2,
		"alternance_ms": 5000,
		"text": [
			{
				"id": 1,
				"value": "line 1",
				"alternance": "line 1 alternance",
				"color": "#ff0000"
			},
			{
				"id": 2,
				"value": "line 2",
				"alternance": "line 2 alternance",
				"color": "#00ff00"
			},
			{
				"id": 3,
				"value": "line 3",
				"alternance": "line 3 alternance",
				"color": "#0000ff"
			}
		]
	},
	{
		"zone": 3,
		"flashing_on_ms": 1000,
		"flashing_off_ms": 1000,
		"graphic": [
			{
				"id": 1,
				"value": 30
			}
		]
	}
];

export default class Vms extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, layerElement, ratio) {

		super(s, g, elementInfo, layerElement, ratio);
		
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		
		this.mapLinesAlternance = new Map();
		this.mapGraphicsAlternance = new Map();
		
	}
	
	render(elementInfo) {
		
		let self = this;
		
		this.elementInfo = elementInfo;
		
		let jsonData = this.getValue(PARAM_JSON_VALUE);
		
		if (!jsonData) {
			
			jsonData = jsonDataFake;
		}
				
		if (this.gState) {
			
			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);
			
			let gZone;
			let zoneId = 0;
			
			while(gZone = this.g.select("#" + G_ID_ZONE_PREFIX + (++zoneId))) {

				renderZone(gZone, zoneId);
			}	
		}
		
		function renderZone(gZone, zoneId) {
			
			let zoneData = getZoneData(zoneId);
			
			if (zoneData) {
				
				renderGraphics();
				renderLines();	
			}
			
						
			function renderGraphics() {
				
				let gGraphic;
				let graphicId = 0;
				
				while (gGraphic = gZone.select("#" + G_ID_GRAPHIC_PREFIX + zoneId + "-" + (++graphicId))) {
					
					let graphicData = getElementData(zoneId, ZONE_ELEMENT_TYPE.graphic, graphicId);
					
					renderGraphic(gGraphic, graphicData);
				}
			}
			
			function renderLines() {
				
				let gLine;
				let lineId = 0;
				
				while (gLine = gZone.select("#" + G_ID_LINE_PREFIX + zoneId + "-" + (++lineId))) {
					
					let lineData = getElementData(zoneId, ZONE_ELEMENT_TYPE.line, lineId);
					
					renderLine(gLine, lineData, zoneData);
				}
			}
		}
		
		function renderLine(gLine, lineData, zoneData) {
			
			if (gLine) {
				
				if (lineData) {
					
					let text = lineData.val ? lineData.val : lineData.value;
					
					if (zoneData) {
						
						let lineAlternance = self.mapLinesAlternance.get(lineData.id);
					
						if (!lineAlternance) {
							
							self.mapLinesAlternance.set(lineData.id, { object: gLine, data: lineData, alternance: false, period: zoneData.alternance_ms, nextChange: new Date() + zoneData.alternance_ms })
						}	
					}
					
					gLine.attr( { text: text, color: lineData.color ? lineData.color : DEFAULT_TEXT_COLOR } );
				}
				else {
					
					gLine.attr( { text: '' } );
				}
			}
		}
		
		function renderGraphic(gGraphic, graphicData, zoneData) {
			
			if (gGraphic) {
				
				if (graphicData) {
					
					let graphic = graphicData.val ? graphicData.val : graphicData.value;
					
					if (zoneData) {
						
						let graphicAlternance = self.mapGraphicsAlternance.get(graphicData.id);
					
						if (!graphicAlternance) {
							
							self.mapGraphicsAlternance.set(graphicData.id, { object: gGraphic, data: lineData, alternance: false, period: zoneData.alternance_ms, nextChange: new Date() + zoneData.alternance_ms });
						}	
					}
					
					gGraphic.attr( { "xlink:href": PATH_PICTOGRAMS + "/" + graphic + ".bmp" });

						
					console.log(gGraphic.attr());
				}
				else {
					
					
				}
			}
		}
		
		function getElementData(zoneId, elementType, id) {
			
			let elementData;
			let zoneData = getZoneData(zoneId);
			
			if (zoneData && Array.isArray(zoneData[elementType])) {
				
				elementData = zoneData[elementType].find(e => e.id === id);
			} 
			
			return elementData;
		}
		
		function getZoneData(zoneId) {
			
			let zoneData;
			
			if (Array.isArray(jsonData) && jsonData.length > 0) {
				
				zoneData = jsonData.find(e => e.zone === zoneId);
			}
			
			return zoneData;
		}
		
		// Alternance and flashing
		function animate() {
					
			setInterval(function(){
				
				animateTextAlternances();
				
			}, 100);
			
			function animateTextAlternances() {
				
				let now = new Date();

				self.mapLinesAlternance.forEach(function(lineAlternance, lineId) {
					
					if (lineAlternance.nextChange < now) {
						
						let newLineAlternance = webix.copy(lineAlternance);
						let newLineData = webix.copy(lineAlternance.lineData);
						
						newLineData.val = newLineAlternance.alternance ? newLineData.alternance : newLineData.value;
						
						newLineAlternance.alternance = !newLineAlternance.alternance;
						newLineAlternance.data = newLineData;
						 					
						renderLine(lineAlternance.object, newLineData);

						self.mapLinesAlternance.set(lineId, newLineAlternance);
					}
				});
			}
			
			function animateGraphicAlternances(gZone, zoneId) {
				
				// TODO
			}
		}
		
		function blink(gObj, time_ms){
			
			gObj.animate({
		      fill: '#000'
		    }, time_ms, indefinite);
		};
	}
}

