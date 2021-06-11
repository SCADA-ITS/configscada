import GraphicIcon from "../../../component/graphicIcon/graphicIcon.js";
import VmsSignalingZoneEditor from "./vmsSignalingZoneEditor.js";
import VmsSignalingTextEditor from "./vmsSignalingTextEditor.js";
import VmsSignalingGraphicEditor from "./vmsSignalingGraphicEditor.js";
import { util } from "../../../lib/util.js";

export default class VmsSignalingComposer {

	constructor(config, element) {
		
		const jsonDataFakeArray = [
			[{"zone":1,"alternance_ms":5000,"graphic":[{"id":1,"value":81,"alternance":744}]},{"zone":2,"alternance_ms":5000,"text":[{"id":1,"value":"ACCIDENTE A 3 KM","alternance":"REDUZCA LA VELOCIDAD"},{"id":2,"value":"EN CARRIL DERECHO","alternance":"Y CIRCULE POR EL"},{"id":3,"value":"","alternance":"CARRIL IZQUIERDO"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphic":[{"id":1,"value":85}]}],
			[{"zone":1,"alternance_ms":5000,"graphic":[{"id":1,"value":22,"alternance":29}]},{"zone":2,"alternance_ms":5000,"text":[{"id":1,"value":"PRECAUCION SR. CONDUCTOR","alternance":"TE ESTOY VIENDO","color":"#ff0000"},{"id":2,"value":"LA SENDA ES PELIGROSA","alternance":"LOS PELOS DE LA NARIZ","color":"#00ff00"},{"id":3,"value":"--------","alternance":"CARA CULO","color":"#0000ff"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphic":[{"id":1,"value":28}]}],
			[{"zone":1,"graphic":[{"id":1,"value":438}]},{"zone":2,"alternance_ms":5000,"text":[{"id":1,"value":"CAMPAÑA"},{"id":2,"value":"CONTROL USO"},{"id":3,"value":"CINTURON"}]}]
		];

		this.config = config;
		this.element = element;
		this.uid = webix.uid();
		
		this.jsonDataFake = jsonDataFakeArray[(new Date()).getSeconds() % jsonDataFakeArray.length];
		//this.jsonDataFake = jsonDataFakeArray[Math.round(Math.random() * (jsonDataFakeArray.length - 1))];
		
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
							<!--<svg id=${"vms-composer-svg-" + self.uid} style="display: none"></svg>-->
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
	
					events[GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC] = function(zoneData, zoneInfo) {
						
						onZoneEvent(GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC, zoneData, zoneInfo);
					};
					events[GraphicIcon.EventTypes.ON_ZONE_DELETE_CLIC] = function(zoneData) {
						
						onZoneEvent(GraphicIcon.EventTypes.ON_ZONE_DELETE_CLIC, zoneData);
					};
					
					self.graphicIconInstance = new mod.default(layerElement, self.element, container, events);
					
					self.graphicIconInstance.load(function() {
						
						self.graphicIconInstance.loadRender(function() {
							
							self.jsonData = self.getJsonDataInitialized();
							
							if (Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0) {
																
								self.update();
								
//								let svgElement = document.getElementById("vms-composer-svg-" + self.uid);
//								
//								if (svgElement) {
//									
//									svgElement.style.display = "block";	
//								}
							}
						});
					});	
				}
				
				lazy();	
			}
			
			function onZoneEvent(eventType, zoneData, zoneInfo) {
				
				let configPanel = getConfigPanel();
				
				if (Array.isArray(configPanel) && configPanel.length > 0) {
					
					let zoneConfig = configPanel.find(e => e.zone === zoneData.zone);
					
					if (zoneConfig) {
						
						if (zoneInfo) {
						
							zoneConfig.w = zoneInfo.w;
							zoneConfig.h = zoneInfo.h;
							zoneConfig.fontInfo = zoneInfo.fontInfo;	
						}
						
						zoneConfig.properties = property;
						
						let zoneEditor;
						
						if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_TEXT) {
							
							zoneEditor = new VmsSignalingTextEditor(self.config.vms.textEditor.width, 
																	self.config.vms.textEditor.height,
																	zoneConfig, 
																	zoneData, 
																	onZoneDataChange);
						}
						else if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_GRAPHIC) {

							zoneConfig.graphics = getGraphics(zoneConfig.vms_group_id);
							
							if (zoneConfig.graphics) {
								
								zoneConfig.pictogramsPath = self.config.vms.pictogramsPath;
								zoneConfig.pictogramsExtension = self.config.vms.pictogramsExtension;
								zoneConfig.pictogramsSize = self.config.vms.pictogramsSize;
								
								zoneEditor = new VmsSignalingGraphicEditor(self.config.vms.graphicEditor.width, 
																		   self.config.vms.graphicEditor.height, 
																		   zoneConfig, 
																		   zoneData, 
																		   onZoneDataChange);	
							}
							else {
							
								webix.alert({
									title: i18n.t("LBL_NO_PICTOGRAMS_ASSOCIATED_TO_ZONE"),
									text: i18n.t("LBL_NO_PICTOGRAMS_ASSOCIATED_TO_ZONE_DESCRIPTION") + " " + zoneConfig.vms_group_id,
									type: "alert-error",
									width: 500
								});	
							}
						}
						else if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_DUAL) {
							
							// TODO QUESTION: TEXT OR GRAPHIC
						}
						else {
							
							webix.alert({
								title: i18n.t("LBL_UNKNOWN_ZONE_TYPE"),
								text: i18n.t("LBL_UNKNOWN_ZONE_TYPE_DESCRIPTION") + " " + zoneConfig.type,
								type: "alert-error",
								width: 500
							});
						}
						
						if (zoneEditor) {
							
							if (eventType === GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC) {
								
								zoneEditor.show();
							}
							else if (eventType === GraphicIcon.EventTypes.ON_ZONE_DELETE_CLIC) {
								
								zoneEditor.delete();
							}
						}
					}
				}
			}
			
			function onZoneDataChange(newZoneData) {
				
				console.log(newZoneData);
				
				self.updateZone(newZoneData);
			}
			
			function getGraphics(vmsGroupId) {
				
				let graphics = undefined;
				
				if (Array.isArray(self.element.graphicGroups) && self.element.graphicGroups.length > 0) {
							
					let graphicGroup = self.element.graphicGroups.find(e => e.id.split(":")[1] == vmsGroupId);
					
					if (graphicGroup && Array.isArray(graphicGroup.graphics) && graphicGroup.graphics.length > 0) {
						
						graphics = graphicGroup.graphics.map(e => e.id.split(":")[2]);
						
						graphics = graphicGroup.graphics.map(e => {
							
							let graphic = {
								id: e.id.split(":")[2],
								group: (e.graphicCategory && e.graphicCategory.labelAlias) ? i18n.t(e.graphicCategory.labelAlias) : null
							};
							
							return graphic;
						});
					}
				}
				
				return graphics;
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
