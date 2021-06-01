import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_JSON_VALUE = "ElementTypeParam:71:2:3";

const G_ID_ZONE_PREFIX = "zone";
const G_ID_LINE_PREFIX = "line";
const G_ID_GRAPHIC_PREFIX = "graphic";
const DEFAULT_TEXT_COLOR = "#ffffff";
const PATH_PICTOGRAMS = "img/pictograms";
const NO_PICTOGRAM = "img/undefined.png";
const TIME_ANIMATION_MS = 5000;
const TIME_BLINKING_MS = 1000;

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:71:0", "ElementTypeState:71:2"];

const ZONE_ELEMENT_TYPE = {
	line: "text",
	graphic: "graphic"
}

const jsonDataFakeArray = [
	[{"zone":1,"alternance_ms":5000,"graphic":[{"id":1,"value":81,"alternance":744}]},{"zone":2,"alternance_ms":5000,"text":[{"id":1,"value":"ACCIDENTE A 3 KM","alternance":"REDUZCA LA VELOCIDAD"},{"id":2,"value":"EN CARRIL DERECHO","alternance":"Y CIRCULE POR EL"},{"id":3,"value":"","alternance":"CARRIL IZQUIERDO"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphic":[{"id":1,"value":85}]}],
	[{"zone":1,"alternance_ms":5000,"graphic":[{"id":1,"value":22,"alternance":99901}]},{"zone":2,"alternance_ms":5000,"text":[{"id":1,"value":"PRECAUCION SR. CONDUCTOR","alternance":"TE ESTOY VIENDO","color":"#ff0000"},{"id":2,"value":"LA SENDA ES PELIGROSA","alternance":"LOS PELOS DE LA NARIZ","color":"#00ff00"},{"id":3,"value":"--------","alternance":"CARA CULO","color":"#0000ff"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphic":[{"id":1,"value":99900}]}],
	[{"zone":1,"graphic":[{"id":1,"value":438}]},{"zone":2,"alternance_ms":5000,"text":[{"id":1,"value":"CAMPAÑA"},{"id":2,"value":"CONTROL USO"},{"id":3,"value":"CINTURON"}]}]
];

export default class Vms extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);
		
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		
		this.linesAlternance = [];
		this.graphicsAlternance = [];
		this.linesBlinking = [];
		this.graphicsBlinking = [];
		this.animateReady = false;
		this.isAlternance = false;
		this.isBlinking = false;
		
		this.jsonDataFake = jsonDataFakeArray[Math.round(Math.random() * (jsonDataFakeArray.length - 1))];
	}
	
	render(elementInfo) {
		
		let self = this;
		
		this.elementInfo = elementInfo;
		
		let jsonData = this.getValue(PARAM_JSON_VALUE);
		
		if (!jsonData) {
			
			jsonData = this.jsonDataFake;
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
		
		animate();
		
		function renderZone(gZone, zoneId) {
			
			let zoneData = getZoneData(zoneId);
			
			if (zoneData) {
				
				renderGraphics(zoneData);
				renderLines(zoneData);	
			}
			
						
			function renderGraphics(zoneData) {
				
				let gGraphic;
				let graphicId = 0;
				
				while (gGraphic = gZone.select("#" + G_ID_GRAPHIC_PREFIX + zoneId + "-" + (++graphicId))) {
					
					let graphicData = getElementData(zoneId, ZONE_ELEMENT_TYPE.graphic, graphicId);
					
					if (graphicData) {
					
						if (graphicData.alternance) {
							
							self.graphicsAlternance.push(zoneId + "-" + graphicId);
						}
						
						if (zoneData.flashing_on_ms || zoneData.flashing_off_ms) {
							
							self.graphicsBlinking.push(zoneId + "-" + graphicId);
						}
					
						renderGraphic(gGraphic, graphicData);
					}
				}
			}
			
			function renderLines(zoneData) {
				
				let gLine;
				let lineId = 0;
				
				while (gLine = gZone.select("#" + G_ID_LINE_PREFIX + zoneId + "-" + (++lineId))) {
					
					let lineData = getElementData(zoneId, ZONE_ELEMENT_TYPE.line, lineId);
					
					if (lineData) {
					
						if (lineData.alternance) {
							
							self.linesAlternance.push(zoneId + "-" + lineId);
						}
						
						if (zoneData.flashing_on_ms || zoneData.flashing_off_ms) {
							
							self.graphicsBlinking.push(zoneId + "-" + graphicId);
						}
					
						renderLine(gLine, lineData);
					}
				}
			}
		}
		
		function renderLine(gLine, lineData) {
			
			if (gLine) {
				
				if (lineData) {
					
					let text = lineData.val ? lineData.val : lineData.value;
									
					gLine.attr( { text: text, fill: lineData.color ? lineData.color : DEFAULT_TEXT_COLOR } );
				}
				else {
					
					gLine.attr( { text: '' } );
				}
			}
		}
		
		function renderGraphic(gGraphic, graphicData) {
			
			if (gGraphic) {
				
				if (graphicData) {
					
					let graphic = graphicData.val ? graphicData.val : graphicData.value;
					
					gGraphic.attr( { "xlink:href": PATH_PICTOGRAMS + "/" + graphic + ".bmp" });
				}
				else {
					
					gGraphic.attr( { "xlink:href": NO_PICTOGRAM });
				}
			}
		}
		
		function getElementData(zoneId, elementType, id) {
			
			let elementData;
			let zoneData = getZoneData(zoneId);
			
			if (zoneData && Array.isArray(zoneData[elementType])) {
				
				elementData = zoneData[elementType].find(e => e.id == id);
			} 
			
			return elementData;
		}
		
		function getZoneData(zoneId) {
			
			let zoneData;
			
			if (Array.isArray(jsonData) && jsonData.length > 0) {
				
				zoneData = jsonData.find(e => e.zone == zoneId);
			}
			
			return zoneData;
		}
		
		// Alternance and flashing
		function animate() {
			
			if (self.animateReady) {
				
				return;
			}
			else {
				
				self.animateReady = !self.animateReady;
			}
					
			setInterval(function(){
				
				animateAlternance(ZONE_ELEMENT_TYPE.line);
				animateAlternance(ZONE_ELEMENT_TYPE.graphic);
				
				self.isAlternance = !self.isAlternance;
				
			}, TIME_ANIMATION_MS);

			setInterval(function(){
				
				animateBlinking(ZONE_ELEMENT_TYPE.line);
				animateBlinking(ZONE_ELEMENT_TYPE.graphic);
				
				self.isBlinking = !self.isBlinking;
				
			}, TIME_BLINKING_MS);
			
			function animateAlternance(zoneElementType) {
				
				let arrAlternanceElements = (zoneElementType === ZONE_ELEMENT_TYPE.line ? self.linesAlternance : self.graphicsAlternance);
				let gIdPrefix = (zoneElementType === ZONE_ELEMENT_TYPE.line ? G_ID_LINE_PREFIX : G_ID_GRAPHIC_PREFIX);
				
				arrAlternanceElements.forEach(elementId => {
					
					let gElement = self.g.select("#" + gIdPrefix + elementId);
					
					if (gElement) {
						
						let zoneId = elementId.split("-")[0];
						let id = elementId.split("-")[1];
						
						let elementData = getElementData(zoneId, zoneElementType, id);
						
						if (elementData) {

							let newElementData = webix.copy(elementData);
							
							newElementData.val = self.isAlternance ? newElementData.value : newElementData.alternance;
							
							if (zoneElementType === ZONE_ELEMENT_TYPE.line) {
							
								renderLine(gElement, newElementData);
							} 
							else {
								
								renderGraphic(gElement, newElementData);
							}
						}
					}
				});
			}
			
			function animateBlinking(zoneElementType) {
				
				let arrBlinkingElements = (zoneElementType === ZONE_ELEMENT_TYPE.line ? self.linesBlinking : self.graphicsBlinking);
				let gIdPrefix = (zoneElementType === ZONE_ELEMENT_TYPE.line ? G_ID_LINE_PREFIX : G_ID_GRAPHIC_PREFIX);
				
				arrBlinkingElements.forEach(elementId => {
					
					let gElement = self.g.select("#" + gIdPrefix + elementId);
					
					if (gElement) {
						
						let zoneId = elementId.split("-")[0];
						let id = elementId.split("-")[1];
						
						let elementData = getElementData(zoneId, zoneElementType, id);
						
						if (elementData) {

							let newElementData = undefined;
							
							if (self.isBlinking) {
								
								newElementData = webix.copy(elementData);
							
								newElementData.val = newElementData.value;	
							}
							
							if (zoneElementType === ZONE_ELEMENT_TYPE.line) {
							
								renderLine(gElement, newElementData);
							} 
							else {
								
								renderGraphic(gElement, newElementData);
							}
						}
					}
				});
			}
		}
	}
}

