import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";
import { util } from "../../../lib/util.js";

const PARAM_JSON_VALUE = "ElementTypeParam:71:2:3";

const G_ID_ZONE_PREFIX = "zone";
const G_ID_LINE_PREFIX = "line";
const G_ID_GRAPHIC_PREFIX = "graphic";
const DEFAULT_TEXT_COLOR = "#ffffff";
const PATH_PICTOGRAMS = "img/pictograms";
const NO_PICTOGRAM = "img/undefined.png";
const TIME_ANIMATION_MS = 5000;
const TIME_BLINKING_MS = 1000;

const TEXT_ALIGN_CENTER = "center";
const TEXT_ALIGN_LEFT = "left";
const TEXT_ALIGN_RIGHT = "right";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:71:0", "ElementTypeState:71:2"];

const ZONE_ELEMENT_TYPE = {
	texts: "texts",
	graphics: "graphics"
}

	
export default class Vms extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);
		
		const jsonDataFakeArray = [
			[{"zone":1,"graphics":[{"id":1,"value":438}]},{"zone":2,"alternance_ms":5000,"align": TEXT_ALIGN_LEFT,"texts":[{"id":1,"value":"CAMPAÑA"},{"id":2,"value":"CONTROL USO"},{"id":3,"value":"CINTURON"}]}],
			[{"zone":1,"graphics":[{"id":1,"value":438}]},{"zone":2,"alternance_ms":5000,"align": TEXT_ALIGN_LEFT,"texts":[{"id":1,"value":"CAMPAÑA"},{"id":2,"value":"CONTROL USO"},{"id":3,"value":"CINTURON"}]}],
			[{"zone":1,"graphics":[{"id":1,"value":438}]},{"zone":2,"alternance_ms":5000,"align": TEXT_ALIGN_LEFT,"texts":[{"id":1,"value":"CAMPAÑA"},{"id":2,"value":"CONTROL USO"},{"id":3,"value":"CINTURON"}]}]
		];
		
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		
		this.linesAlternance = [];
		this.graphicsAlternance = [];
		this.linesBlinking = [];
		this.graphicsBlinking = [];
		this.animateReady = false;
		this.eventsReady = false;
		this.isAlternance = false;
		this.isBlinking = false;
		this.isAnimated = false;
		
		this.intervalAlternanceId = undefined;
		this.intervalBlinkingId = undefined;
	
		this.jsonDataFake = webix.copy(jsonDataFakeArray[Math.round(Math.random() * (jsonDataFakeArray.length - 1))]);
		
		this.jsonData = this.getDataInitialized();
		
		this.pauseAnimate = true;
		
		this.mapZoneBBox = undefined;
	}
	
	stop() {
		
		if (this.intervalAlternanceId) {
			
			clearInterval(this.intervalAlternanceId);	
		}
		
		if (this.intervalBlinkingId) {
			
			clearInterval(this.intervalBlinkingId);	
		}
	}
	
	getZoneDataInitialized(zoneId) {
		
		let zoneData = {
			zone: zoneId
		};
		
		let gZone;
		
		if (gZone = this.g.select("#" + G_ID_ZONE_PREFIX + zoneId)) {

			let graphicId = 0;
			
			while (gZone.select("#" + G_ID_GRAPHIC_PREFIX + zoneId + "-" + (++graphicId))) {
				
				if (!zoneData[ZONE_ELEMENT_TYPE.graphics]) {
					
					zoneData[ZONE_ELEMENT_TYPE.graphics] = [];
				}
				
				zoneData[ZONE_ELEMENT_TYPE.graphics].push({ id: graphicId });
			}
			
			let lineId = 0;
			
			while (gZone.select("#" + G_ID_LINE_PREFIX + zoneId + "-" + (++lineId))) {
				
				zoneData.align = TEXT_ALIGN_LEFT;
				
				if (!zoneData[ZONE_ELEMENT_TYPE.texts]) {
					
					zoneData[ZONE_ELEMENT_TYPE.texts] = [];
				}
				
				zoneData[ZONE_ELEMENT_TYPE.texts].push({ id: lineId, value: "" });
			}
		}
		
		return zoneData;
	}
	
	getDataInitialized() {
			
		let data = [];
		let zoneId = 0;
		
		while(this.g.select("#" + G_ID_ZONE_PREFIX + (++zoneId))) {

			data.push(this.getZoneDataInitialized(zoneId));
		}
		
		return data;
	}
	
	getElementInfoInitial() {
			
		let elementInfo = {
			values: []
		};
		
		elementInfo.values.push({
			id: PARAM_JSON_VALUE,
			value: JSON.stringify(this.getDataInitialized())
		});
		
		return elementInfo;
	}

	render(elementInfo) {
		
		let self = this;
		
		createMapBBox();
		
		if (!self.mapZoneBBox) {
			
			return;
		}
		
		self.pauseAnimate = true;
		
		this.elementInfo = elementInfo;
	
		let value = this.getValue(PARAM_JSON_VALUE);
		
		if (!value) {
			
			if (USE_VMS_FAKE_DATA) {
				
				self.jsonData = this.jsonDataFake;	
			}
		}
		else {
			
			if (!self.jsonData || (self.jsonData && JSON.stringify(self.jsonData) != value)) {
				
				try {
					
					self.jsonData = JSON.parse(value);	
				}
				catch {
					
					throw new Error("VMS can't evaluate json string: " + value);
				}
			}
		}
		
		self.linesAlternance = [];
		self.graphicsAlternance = [];
		self.linesBlinking = [];
		self.graphicsBlinking = [];
		self.eventsReady = false;
		self.isAlternance = false;
		self.isBlinking = false;
		
		enableEvents();
				
		if (this.gState) {
			
			let treatmentValue = undefined;
			
			if (elementInfo.state) {
				
				this.gState.attr({ fill: elementInfo.state.color && (!this.events || !this.events[GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC]) ? elementInfo.state.color : "none" });
				
				treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);
				
				if (!treatmentValue) {
					
					self.jsonData = self.getDataInitialized();	
				}	
			}
			
			let gZone;
			let zoneId = 0;
			
			while(gZone = this.g.select("#" + G_ID_ZONE_PREFIX + (++zoneId))) {

				renderZone(gZone, zoneId);
			}
		}
		
		animate();
		
		self.pauseAnimate = false;
		
		function renderZone(gZone, zoneId) {
			
			let zoneData = getZoneData(zoneId);
			
			renderGraphics();
			renderLines();	
			
						
			function renderGraphics() {
				
				let gGraphic;
				let graphicId = 0;
				
				while (gGraphic = gZone.select("#" + G_ID_GRAPHIC_PREFIX + zoneId + "-" + (++graphicId))) {
					
					let graphicData = getElementData(zoneId, ZONE_ELEMENT_TYPE.graphics, graphicId);
					
					if (graphicData) {
					
						if (graphicData.alternance) {
							
							self.graphicsAlternance.push(zoneId + "-" + graphicId);
						}
						
						if (zoneData && (zoneData.flashing_on_ms || zoneData.flashing_off_ms)) {
							
							self.graphicsBlinking.push(zoneId + "-" + graphicId);
						}
					}
					
					renderGraphic(gGraphic, graphicData);
				}
			}
			
			function renderLines() {
				
				let gLine;
				let lineId = 0;
				
				while (gLine = gZone.select("#" + G_ID_LINE_PREFIX + zoneId + "-" + (++lineId))) {
					
					let lineData = getElementData(zoneId, ZONE_ELEMENT_TYPE.texts, lineId);
					
					if (lineData) {
					
						if (lineData.alternance != null) {
							
							self.linesAlternance.push(zoneId + "-" + lineId);
						}
						
						if (zoneData && (zoneData.flashing_on_ms || zoneData.flashing_off_ms)) {
							
							self.linesBlinking.push(zoneId + "-" + lineId);
						}
						
						lineData.align = (zoneData && zoneData.align) ? zoneData.align : "left";
						lineData.zoneWidth = self.mapZoneBBox.get(zoneId).width;
					}
					
					renderLine(gLine, lineData);
				}
			}
		}
		
		function renderLine(gLine, lineData) {
			
			if (gLine) {
				
				let text = "";
				
				if (lineData) {
					
					text = (lineData.val != null) ? lineData.val : lineData.value;
					
					if (!text) {
						
						text = "";
					}
									
					gLine.attr( { text: text, fill: lineData.color ? lineData.color : DEFAULT_TEXT_COLOR } );
					
					alignLine(gLine, lineData, text);
				}
				else {
					
					gLine.attr( { text: '' } );
				}
			}
		}
		
		function alignLine(gLine, lineData, text) {
				
			if (lineData.align) {
			
				let textWidth = util.ui.calculateTextWidth(text, gLine.attr("font-family"), gLine.attr("font-size"), gLine.attr("letter-spacing"));
				let x = 0;
				
				if (lineData.align === TEXT_ALIGN_LEFT) {
					
					x -= (lineData.zoneWidth / 2) - lineData.zoneWidth * 0.05;
				}
				else if (lineData.align === TEXT_ALIGN_RIGHT) {
					
					x = (lineData.zoneWidth / 2) - (textWidth * 2 / 3) - lineData.zoneWidth * 0.05;
				}
				
				let styleArray = gLine.attr("style").split(";");
				
				if (styleArray.length > 0) {
					
					let styleFinalArray = [];
					
					styleArray.forEach(s => {
						
						let styleName = (s.split(":")[0]).trim();
						
						if (styleName && styleName != "text-align" && styleName != "text-anchor") {
							
							styleFinalArray.push(s);
						}
						
					});
					
					styleFinalArray.push("text-align: " + lineData.align);
					
					if (lineData.align === TEXT_ALIGN_CENTER) {
						
						styleFinalArray.push("text-anchor: middle");	
					}
					
					gLine.attr({'style': styleFinalArray.join(";")});
					
					gLine.transform('T' + x);
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
						"font-size": gLine.attr("font-size"),
						"letter-spacing": gLine.attr("letter-spacing")
					}
				}
			}
			
			return fontInfo;
		}
		
		function getZoneData(zoneId) {
			
			let zoneData;
			
			if (zoneId && Array.isArray(self.jsonData) && self.jsonData.length > 0) {
				
				zoneData = self.jsonData.find(e => e.zone == zoneId);
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

			self.isAnimated = (Array.isArray(self.linesAlternance) && self.linesAlternance.length > 0) ||
				(Array.isArray(self.graphicsAlternance) && self.graphicsAlternance.length > 0) ||
				(Array.isArray(self.linesBlinking) && self.graphicsAlternance.length > 0) ||
				(Array.isArray(self.graphicsBlinking) && self.graphicsBlinking.length > 0); 
			
			if (self.animateReady || !self.isAnimated) {
				
				return;
			}
			else {
				
				self.animateReady = !self.animateReady;
			}
					
			self.intervalAlternanceId = setInterval(function(){
				
				if (!self.pauseAnimate) {
					
					animateAlternance(ZONE_ELEMENT_TYPE.texts);
					animateAlternance(ZONE_ELEMENT_TYPE.graphics);
					
					self.isAlternance = !self.isAlternance;	
				}
				
			}, TIME_ANIMATION_MS);

			self.intervalBlinkingId = setInterval(function(){
				
				if (!self.pauseAnimate) {
				
					animateBlinking(ZONE_ELEMENT_TYPE.texts);
					animateBlinking(ZONE_ELEMENT_TYPE.graphics);
					
					self.isBlinking = !self.isBlinking;	
				}
				
			}, TIME_BLINKING_MS);
			
			function animateAlternance(zoneElementType) {
				
				let arrAlternanceElements = (zoneElementType === ZONE_ELEMENT_TYPE.texts ? self.linesAlternance : self.graphicsAlternance);
				let gIdPrefix = (zoneElementType === ZONE_ELEMENT_TYPE.texts ? G_ID_LINE_PREFIX : G_ID_GRAPHIC_PREFIX);
				
				arrAlternanceElements.forEach(elementId => {
					
					let gElement = self.g.select("#" + gIdPrefix + elementId);
					
					if (gElement) {
						
						let zoneId = elementId.split("-")[0];
						let id = elementId.split("-")[1];
						
						let elementData = getElementData(zoneId, zoneElementType, id);
						
						if (elementData) {

							let newElementData = webix.copy(elementData);
							
							newElementData.val = self.isAlternance ? newElementData.value : newElementData.alternance;
							
							if (zoneElementType === ZONE_ELEMENT_TYPE.texts) {
							
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
				
				let arrBlinkingElements = (zoneElementType === ZONE_ELEMENT_TYPE.texts ? self.linesBlinking : self.graphicsBlinking);
				let gIdPrefix = (zoneElementType === ZONE_ELEMENT_TYPE.texts ? G_ID_LINE_PREFIX : G_ID_GRAPHIC_PREFIX);
				
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
							
							if (zoneElementType === ZONE_ELEMENT_TYPE.texts) {
							
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
	
					let zoneBBox = self.mapZoneBBox.get(zoneId);
					
					let gEditButton = g.image("img/graphic_icon/resources/pencil.svg", 
												zoneBBox.cx + zoneBBox.width / 2 - (zoneBBox.height * 0.2) * 2, 
												zoneBBox.y,
												zoneBBox.height * 0.2,
												zoneBBox.height * 0.2);
												
					gEditButton.attr( {cursor: "pointer" });
					
					createTooltip(gEditButton, i18n.t("LBL_EDIT"));
					
					gZone.append(gEditButton);
					
					gEditButton.click(function(e) {

						let zoneData = webix.copy(getZoneData(zoneId));
						let zoneDataInitialized = self.getZoneDataInitialized(zoneId);

						let zoneDataWithTexts = zoneData && Array.isArray(zoneData.texts) && zoneData.texts.length > 0;
						let zoneDataWithGraphics = zoneData && Array.isArray(zoneData.graphics) && zoneData.graphics.length > 0;  
						
						if (!zoneData || (zoneData && !zoneDataWithTexts && !zoneDataWithGraphics)) {
							
							zoneData = zoneDataInitialized;
						}
						else {
							
							if (!zoneData.graphics) {
							
								zoneData.graphics = zoneDataInitialized.graphics;
							}
							
							if (!zoneData.texts) {
							
								zoneData.texts = zoneDataInitialized.texts;
							}
						}
						
						let fontInfo = getFontInfo(zoneId);
						
						let zoneInfo = {
							w: zoneBBox.width,
							h: zoneBBox.height,
							fontInfo: fontInfo
						}
						
						eval(self.events[GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC])(zoneData, zoneInfo);
					});
				}
			}
			
			function enableDelete(gZone, zoneId) {
				
				if (self.events[GraphicIcon.EventTypes.ON_ZONE_DELETE_CLIC]) {
					
					let g = self.s.g();
					
					let zoneBBox = self.mapZoneBBox.get(zoneId);
												
					let gDeleteButton = g.image("img/graphic_icon/resources/clear.svg", 
												zoneBBox.cx + zoneBBox.width / 2 - (zoneBBox.height * 0.2) * 1, 
												zoneBBox.y,
												zoneBBox.height * 0.2,
												zoneBBox.height * 0.2);
												
					gZone.append(gDeleteButton);
					
					gDeleteButton.attr( {cursor: "pointer" });
					
					createTooltip(gDeleteButton, i18n.t("LBL_CLEAR"));
					
					gDeleteButton.click(function(e) {
						
						let zoneData = getZoneData(zoneId);
						
						if (!zoneData) {
							
							zoneData = self.getZoneDataInitialized(zoneId);
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
				
				let tooltip = document.getElementById("graphic-icon-tooltip-2");
				
				if (tooltip) {
					
					tooltip.innerHTML = `<div>${text}</div>`;
											
					tooltip.style.display = "block";
					tooltip.style.left = e.pageX + 10 + 'px';
					tooltip.style.top = e.pageY + 10 + 'px';
					tooltip.style.zIndex = 99999;	
				}
			}
			
			function hideTooltip() {
				
				var tooltip = document.getElementById("graphic-icon-tooltip-2");
				
				if (tooltip) {
				
					tooltip.style.display = "none";	
				}
			}
		}
		
		function createMapBBox() {
			
			if (!self.mapZoneBBox) {
				
				let gZone;
				let zoneId = 0;
				
				self.mapZoneBBox = new Map();
				
				while (self.mapZoneBBox && (gZone = self.g.select("#" + G_ID_ZONE_PREFIX + (++zoneId)))) {
	
					let bBox = gZone.getBBox();
					
					if (bBox.width > 0) {
						
						self.mapZoneBBox.set(zoneId, webix.copy(gZone.getBBox()));
					}
					else {
						
						self.mapZoneBBox.clear();
						self.mapZoneBBox = undefined;
					}
				}
			}
		}
	}
	
	getConfiguration() {
		
		return {

			isAnimated: this.isAnimated 
		}				
	}
	
	reset() {
		
		this.render(this.getElementInfoInitial());		
	}
}
