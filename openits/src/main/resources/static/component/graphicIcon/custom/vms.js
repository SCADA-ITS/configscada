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
		this.eventsReady = false;
		this.isAlternance = false;
		this.isBlinking = false;
		
		this.intervalAlternanceId = undefined;
		this.intervalBlinkingId = undefined;
	
		this.jsonDataFake = jsonDataFakeArray[Math.round(Math.random() * (jsonDataFakeArray.length - 1))];
		
		this.jsonData = undefined;
	}
	
	stop() {
		
		if (this.intervalAlternanceId) {
			
			clearInterval(this.intervalAlternanceId);	
		}
		
		if (this.intervalBlinkingId) {
			
			clearInterval(this.intervalBlinkingId);	
		}
	}

	render(elementInfo) {
		
		let self = this;
		
		this.elementInfo = elementInfo;
	
		let value = this.getValue(PARAM_JSON_VALUE);
		
		if (!value) {
			
			self.jsonData = this.jsonDataFake;
		}
		else {
			
			if (!self.jsonData || (self.jsonData && JSON.stringify(self.jsonData) != value)) {
				
				self.jsonData = JSON.parse(value);
				
				self.linesAlternance = [];
				self.graphicsAlternance = [];
				self.linesBlinking = [];
				self.graphicsBlinking = [];
				//self.animateReady = false;
				self.eventsReady = false;
				self.isAlternance = false;
				self.isBlinking = false;
			}
		}
		
		enableEvents();
				
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
				
				renderGraphics();
				renderLines();	
			}
			
						
			function renderGraphics() {
				
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
			
			function renderLines() {
				
				let gLine;
				let lineId = 0;
				
				while (gLine = gZone.select("#" + G_ID_LINE_PREFIX + zoneId + "-" + (++lineId))) {
					
					let lineData = getElementData(zoneId, ZONE_ELEMENT_TYPE.line, lineId);
					
					if (lineData) {
					
						if (lineData.alternance) {
							
							self.linesAlternance.push(zoneId + "-" + lineId);
						}
						
						if (zoneData.flashing_on_ms || zoneData.flashing_off_ms) {
							
							self.linesBlinking.push(zoneId + "-" + lineId);
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
		
		function getFontInfo(zoneId) {
			
			let fontInfo = undefined;
			
			let gZone = self.g.select("#" + G_ID_ZONE_PREFIX + zoneId);
			
			if (gZone) {
				
				const firstLineId = 1;
				
				let gLine = gZone.select("#" + G_ID_LINE_PREFIX + zoneId + "-" + firstLineId);
				
				if (gLine) {
					
					fontInfo = {
						
						"font-family": gLine.attr("font-family"),
						"font-size": gLine.attr("font-size")
					}
				}
			}
			
			return fontInfo;
		}
		
		function getZoneData(zoneId) {
			
			let zoneData;
			
			if (Array.isArray(self.jsonData) && self.jsonData.length > 0) {
				
				zoneData = self.jsonData.find(e => e.zone == zoneId);
			}
			
			return zoneData;
		}
		
		function getZoneDataInitialized(zoneId) {
		
			let zoneData = {
				zone: zoneId
			};
			
			let gZone;
			
			if (gZone = self.g.select("#" + G_ID_ZONE_PREFIX + zoneId)) {

				let graphicId = 0;
				
				while (gZone.select("#" + G_ID_GRAPHIC_PREFIX + zoneId + "-" + (++graphicId))) {
					
					if (!zoneData.graphic) {
						
						zoneData.graphic = [];
					}
					
					zoneData.graphic.push({ id: graphicId });
				}
				
				let lineId = 0;
				
				while (gZone.select("#" + G_ID_LINE_PREFIX + zoneId + "-" + (++lineId))) {
					
					if (!zoneData.text) {
						
						zoneData.text = [];
					}
					
					zoneData.graphic.push({ id: lineId });
				}
			}
			
			return zoneData;
		}
		
		function getElementData(zoneId, elementType, id) {
			
			let elementData;
			let zoneData = getZoneData(zoneId);
			
			if (zoneData && Array.isArray(zoneData[elementType])) {
				
				elementData = zoneData[elementType].find(e => e.id == id);
			} 
			
			return elementData;
		}
		
		// Alternance and flashing
		function animate() {
			
			if (self.animateReady) {
				
				return;
			}
			else {
				
				self.animateReady = !self.animateReady;
			}
					
			self.intervalAlternanceId = setInterval(function(){
				
				animateAlternance(ZONE_ELEMENT_TYPE.line);
				animateAlternance(ZONE_ELEMENT_TYPE.graphic);
				
				self.isAlternance = !self.isAlternance;
				
			}, TIME_ANIMATION_MS);

			self.intervalBlinkingId = setInterval(function(){
				
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
		
		function enableEvents() {
			
			if (self.eventsReady) {
				
				return;
			}
			else {
				
				self.eventsReady = !self.eventsReady;
			}
			
			if (self.events) {
				
				let gZone;
				let zoneId = 0;
				
				while(gZone = self.g.select("#" + G_ID_ZONE_PREFIX + (++zoneId))) {
	
					enableEdit(gZone, zoneId);
					enableDelete(gZone, zoneId);
				}	
			}
			
			function enableEdit(gZone, zoneId) {
			
				if (self.events[GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC]) {
					
					let g = self.s.g();
	
					let gEditButton = g.image("img/graphic_icon/resources/pencil.svg", 
												gZone.getBBox().cx + gZone.getBBox().width / 2 - (gZone.getBBox().height * 0.2) * 2, 
												gZone.getBBox().height * 0.1,
												gZone.getBBox().height * 0.2,
												gZone.getBBox().height * 0.2);
												
					gEditButton.attr( {cursor: "pointer" });
					
					createTooltip(gEditButton, i18n.t("LBL_EDIT"));
					
					gZone.append(gEditButton);
					
					gEditButton.click(function(e) {
						
						let zoneData = getZoneData(zoneId);
						
						if (!zoneData) {
							
							zoneData = getZoneDataInitialized(zoneId);
						}
						
						let fontInfo = getFontInfo(zoneId);
						
						let zoneInfo = {
							w: gZone.getBBox().width,
							h: gZone.getBBox().height,
							fontInfo: fontInfo
						}
						
						eval(self.events[GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC])(zoneData, zoneInfo);
					});
				}
			}
			
			function enableDelete(gZone, zoneId) {
				
				if (self.events[GraphicIcon.EventTypes.ON_ZONE_DELETE_CLIC]) {
					
					let g = self.s.g();
	
					let gDeleteButton = g.image("img/graphic_icon/resources/clear.svg", 
												gZone.getBBox().cx + gZone.getBBox().width / 2 - (gZone.getBBox().height * 0.2) * 1, 
												gZone.getBBox().height * 0.1,
												gZone.getBBox().height * 0.2,
												gZone.getBBox().height * 0.2);
					gZone.append(gDeleteButton);
					
					gDeleteButton.attr( {cursor: "pointer" });
					
					createTooltip(gDeleteButton, i18n.t("LBL_CLEAR"));
					
					gDeleteButton.click(function(e) {
						
						let zoneData = getZoneData(zoneId);
						
						if (!zoneData) {
							
							zoneData = getZoneDataInitialized(zoneId);
						}
						
						eval(self.events[GraphicIcon.EventTypes.ON_ZONE_DELETE_CLIC])(zoneData);
					});
				}
			}
			
			function createTooltip(gButton, text) {
				
				let tooltip = document.createElement("div");
				tooltip.setAttribute("id", "graphic-icon-tooltip-2");
				tooltip.setAttribute("class", "graphic-icon-tooltip-simple");
				tooltip.setAttribute("display", "none");
				tooltip.setAttribute("style", "position: absolute; display: none;");
				document.body.appendChild(tooltip);
								
				gButton.hover(
					function(e) { 
						
						showTooltip(e, text);
					},
					function() {
						
						hideTooltip();
					}
				);
			}
			
			function showTooltip(e, text) {
				
				let tooltip = document.getElementById("graphic-icon-tooltip");
				
				if (tooltip) {
					
					tooltip.innerHTML = `<div>${text}</div>`;
											
					tooltip.style.display = "block";
					tooltip.style.left = e.pageX + 10 + 'px';
					tooltip.style.top = e.pageY + 10 + 'px';
					tooltip.style.zIndex = 99999;	
				}
			}
			
			function hideTooltip() {
				
				var tooltip = document.getElementById("graphic-icon-tooltip");
				
				if (tooltip) {
				
					tooltip.style.display = "none";	
				}
			}
		}
	}
}

