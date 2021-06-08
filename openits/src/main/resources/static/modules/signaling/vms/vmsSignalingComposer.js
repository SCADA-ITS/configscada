import GraphicIcon from "../../../component/graphicIcon/graphicIcon.js";
import VmsSignalingZoneEditor from "./vmsSignalingZoneEditor.js";
import VmsSignalingTextEditor from "./vmsSignalingTextEditor.js";
import VmsSignalingGraphicEditor from "./vmsSignalingGraphicEditor.js";
import { util } from "../../../lib/util.js";

const jsonDataFakeArray = [
	[{"zone":1,"alternance_ms":5000,"graphic":[{"id":1,"value":81,"alternance":744}]},{"zone":2,"alternance_ms":5000,"text":[{"id":1,"value":"ACCIDENTE A 3 KM","alternance":"REDUZCA LA VELOCIDAD"},{"id":2,"value":"EN CARRIL DERECHO","alternance":"Y CIRCULE POR EL"},{"id":3,"value":"","alternance":"CARRIL IZQUIERDO"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphic":[{"id":1,"value":85}]}],
	[{"zone":1,"alternance_ms":5000,"graphic":[{"id":1,"value":22,"alternance":99901}]},{"zone":2,"alternance_ms":5000,"text":[{"id":1,"value":"PRECAUCION SR. CONDUCTOR","alternance":"TE ESTOY VIENDO","color":"#ff0000"},{"id":2,"value":"LA SENDA ES PELIGROSA","alternance":"LOS PELOS DE LA NARIZ","color":"#00ff00"},{"id":3,"value":"--------","alternance":"CARA CULO","color":"#0000ff"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphic":[{"id":1,"value":99900}]}],
	[{"zone":1,"graphic":[{"id":1,"value":438}]},{"zone":2,"alternance_ms":5000,"text":[{"id":1,"value":"CAMPAÑA"},{"id":2,"value":"CONTROL USO"},{"id":3,"value":"CINTURON"}]}]
];

export default class VmsSignalingComposer {

	constructor(config, element) {

		this.config = config;
		this.element = element;
		this.uid = webix.uid();
		this.jsonDataFake = jsonDataFakeArray[Math.round(Math.random() * (jsonDataFakeArray.length - 1))];
		
		this.graphicIconInstance = undefined;
		
		this.jsonData = [];
	}

	getView() {
		
		let self = this;
				
		let view = {
			id: "vms-composer-" + self.uid,
			height: 250,
			width: 625,
			borderless: true,
			template: function() {
					
				return `<div>
							<svg id=${"vms-composer-svg-" + self.uid}></svg>
						</div>`
			},
			on: {
				onAfterRender: function() {

					drawGraphicIcon(this.$width, this.$height);
				},
				onDestruct: function() {
		
					self.graphicIconInstance.stop();				
				}
			}
		};
		
		function drawGraphicIcon(w, h) {

			let property = self.config.properties.find(e => e.id === self.element.elementSubtype.id);
			let graphicIcon = undefined;
								
			if (graphicIcon = property.graphicIcon) {
				
				graphicIcon.width = w;
				graphicIcon.height = h;
				
				const lazy = async _ => {
		
					let jsFile = util.getContextPath() + '/' + graphicIcon.jsDrawFile; 
		
					const mod = await import(jsFile);
					
					let layerElement = {
						graphicIcon: graphicIcon
					}
					
					let s = Snap("#" + "vms-composer-svg-" + self.uid);
					s.attr({ width: w, height: h });
					
					let container = { 
						type: GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW, 
						obj: s
					}
					
					let events = {};
	
					events[GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC] = onZoneEditClic;
					events[GraphicIcon.EventTypes.ON_ZONE_DELETE_CLIC] = onZoneDeleteClic;
					
					self.graphicIconInstance = new mod.default(layerElement, self.element, container, events);
					
					self.graphicIconInstance.load(function() {
						
						self.graphicIconInstance.loadRender(function() {
							
							self.jsonData = self.getJsonDataInitialized();
							
							if (Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0) {
								
								//self.graphicIconInstance.updateValues(self.element.elementValues);
								
								self.update();
							}
						});
					});	
				}
				
				lazy();	
			}
			
			function onZoneEditClic(zoneData, zoneInfo) {
				
				let configPanel = getConfigPanel();
				
				if (Array.isArray(configPanel) && configPanel.length > 0) {
					
					let zoneConfig = configPanel.find(e => e.zone === zoneData.zone);
					
					if (zoneConfig) {
						
						zoneConfig.w = zoneInfo.w;
						zoneConfig.h = zoneInfo.h;
						zoneConfig.fontInfo = zoneInfo.fontInfo;
						zoneConfig.properties = property;
						
						let zoneEditor;
						
						if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_TEXT) {
							
							zoneEditor = new VmsSignalingTextEditor(zoneConfig, zoneData, onZoneDataChange);
							
							zoneEditor.show();
						}
						else if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_GRAPHIC) {
							
							zoneEditor = new VmsSignalingGraphicEditor(zoneConfig, zoneData, onZoneDataChange);
						}
						else if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_DUAL) {
							
							// TODO QUESTION: TEXT OR GRAPHIC
						}
						else {
							
							// TODO ERROR: UNKNOWN ZONE TYPE
						}
					}
				}
				
				
				//console.log(zoneData);
			}
			
			function onZoneDeleteClic(zoneData) {
				
				console.log(zoneData);
			}
			
			function onZoneDataChange(newZoneData) {
				
				console.log(newZoneData);
				
				self.updateZone(newZoneData);
			}
			
			function getConfigPanel() {
			
				let configPanel;
				
				if (self.element && Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0) {
					
					let jsonConfig = self.element.elementValues.find(e => e.elementTypeParam.id === self.config.vms.jsonConfig);
					
					if (jsonConfig && jsonConfig.value) {
						
						configPanel = JSON.parse(jsonConfig.value);
					}
				}
				
				return configPanel;
			}
		}
		
		return view;
	}
	
	updateZone(zoneData) {
		
		let zoneDelete = this.jsonData.findIndex(e => e.zone === zoneData.zone);
		
		if (zoneDelete !== -1) {
			
			this.jsonData.splice(zoneDelete, 1)
		}
		
		this.jsonData.push(zoneData);
				
		this.update();
	}
	
	getJsonDataInitialized() {
		
		let jsonData = this.jsonDataFake;
		
		if (!jsonData && this.element && Array.isArray(this.element.elementValues) && this.element.elementValues.length > 0) {
			
			let elementValueJsonData = this.element.elementValues.find(e => e.elementTypeParam.id === this.config.vms.jsonData);
		
			if (elementValueJsonData && elementValueJsonData.value) {
				
				jsonData = JSON.parse(elementValueJsonData.value);
			}
			else {
				
				let elementValueJsonConfig = this.element.elementValues.find(e => e.elementTypeParam.id === this.config.vms.jsonConfig);
		
				if (elementValueJsonConfig && elementValueJsonConfig.value) {
					
					let jsonConfig = JSON.parse(elementValueJsonConfig.value);
					
					jsonConfig.forEach(e => {
						
						jsonData.push({
							zone: e.zone,
							graphic: e.type == VmsSignalingZoneEditor.TYPE_GRAPHIC ? [] : undefined,
							text: e.type == VmsSignalingZoneEditor.TYPE_TEXT ? [] : undefined
						})
					})
				}
			}
		}
		
		return jsonData;
	}
	
	update() {
		
		let elementValueJsonData = {
			
			elementTypeParam: {
				id: this.config.vms.jsonData
			},
			value: JSON.stringify(this.jsonData)
		}
		
		this.graphicIconInstance.updateValues([elementValueJsonData]);
	}
}	
