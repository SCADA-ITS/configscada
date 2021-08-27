import GraphicIcon from "../../../component/graphicIcon/graphicIcon.js";
import VmsSignalingZoneEditor from "./vmsSignalingZoneEditor.js";
import VmsSignalingTextEditor from "./vmsSignalingTextEditor.js";
import VmsSignalingGraphicEditor from "./vmsSignalingGraphicEditor.js";
import OptionDialogBox from "../../../component/optionDialogBox.js";
import { util } from "../../../lib/util.js";

const TEXT_ALIGN_CENTER = "center";
const TEXT_ALIGN_LEFT = "left";
const TEXT_ALIGN_RIGHT = "right";

export default class VmsSignalingComposer {

	constructor(config, element, initialJsonData, onUpdate, onAfterRender) {
		
		const jsonDataFakeArray = [
			[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":81,"alternance":744}]},{"zone":2,"alternance_ms":5000,"align": TEXT_ALIGN_LEFT,"texts":[{"id":1,"value":"ACCIDENTE","alternance":"REDUZCA"},{"id":2,"value":"A 3 KM EN","alternance":"LA VELOCIDAD"},{"id":3,"value":"CARRIL DER.","alternance":"USE CAR. IZD"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphics":[{"id":1,"value":85}]}],
			[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":22,"alternance":29}]},{"zone":2,"alternance_ms":5000,"align": TEXT_ALIGN_LEFT,"texts":[{"id":1,"value":"PRECAUCION","alternance":"ATENCION","color":"#ff0000"},{"id":2,"value":"LA SENDA","alternance":"AMIGO","color":"#00ff00"},{"id":3,"value":"ES PELIGROSA","alternance":"CONDUCTOR","color":"#0000ff"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphics":[{"id":1,"value":28}]}],
			[{"zone":1,"graphics":[{"id":1,"value":438}]},{"zone":2,"alternance_ms":5000,"align": TEXT_ALIGN_LEFT,"texts":[{"id":1,"value":"CAMPAÑA"},{"id":2,"value":"CONTROL USO"},{"id":3,"value":"CINTURON"}]}]
		]
		
		this.config = config;
		this.element = element;
		this.onUpdate = onUpdate;
		this.onAfterRender = onAfterRender;
		this.uid = webix.uid();
		
		this.jsonDataFake = jsonDataFakeArray[(new Date()).getSeconds() % jsonDataFakeArray.length];
		
		this.graphicIconInstance = undefined;
		this.jsonData = this.getJsonDataClear();
		
		if (initialJsonData) {
			
			if (Array.isArray(initialJsonData) && initialJsonData.length > 0) {
				
				this.initialJsonData = initialJsonData;
				this.jsonData = initialJsonData;	
			}
			else {
				
				this.initialJsonData = this.getJsonDataClear();
			}
		}

		this.dualZoneEditor = undefined;
	}

	getView() {
		
		let self = this;
				
		let view = {
			id: "vms-composer-" + self.uid,
			height: !self.initialJsonData ? 250 : 100,
			width: 625,
			borderless: true,
			template: function() {
					
				return `<div>
							<!--<svg id=${"vms-composer-svg-" + self.uid} style="display: none"></svg>-->
							<svg id=${"vms-composer-svg-" + self.uid} height="${!self.initialJsonData ? 250 : 100}px"></svg>
						</div>`
			},
			on: {
				onAfterRender: function() {

					drawGraphicIcon(this.$width, this.$height);
				},
				onDestruct: function() {
		
					if (self.graphicIconInstance) {
						
						self.graphicIconInstance.stop();	
					}
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
					
					self.graphicIconInstance = new mod.default(layerElement, self.element, container, self.onUpdate ? events : undefined);
					
					self.graphicIconInstance.load(function() {
						
						self.graphicIconInstance.loadRender(function() {
							
							self.jsonData = self.getCurrentSignalingJsonData();
							
							if (Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0) {
																
								self.update(self.jsonData);
								
								if (self.onAfterRender) {
						
									self.onAfterRender();
								}
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
						
							let dZone = document.querySelector("#" + "vms-composer-svg-" + self.uid).querySelector("#zone" + zoneData.zone);
							
							if (dZone) {
								
								zoneConfig.clientWidth = dZone.getBoundingClientRect().width;
								zoneConfig.clientHeight = dZone.getBoundingClientRect().height;
							}
						
							zoneConfig.w = zoneInfo.w;
							zoneConfig.h = zoneInfo.h;
							zoneConfig.fontInfo = zoneInfo.fontInfo;	
						}
						
						zoneConfig.properties = property;
						
						let zoneEditor;
						
						if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_TEXT) {

							zoneEditor = drawTextEditor();

						} else if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_GRAPHIC) {

							zoneEditor = drawGraphicEditor();

						} else if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_DUAL) {

							let optionDialogBox;

							if (eventType === GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC) {

								let options = [
									{
										id: "text",
										label: i18n.t("LBL_TEXT_EDITOR"),
										icon: "mdi mdi-form-textbox-password",
										description: i18n.t("LBL_TEXT_EDITOR_DESCRIPTION"),
										default: false
									},
									{
										id: "graphic",
										label: i18n.t("LBL_GRAPHIC_EDITOR"),
										icon: "mdi mdi-image-edit",
										description: i18n.t("LBL_GRAPHIC_EDITOR_DESCRIPTION"),
										default: false
									}

								];

								optionDialogBox = new OptionDialogBox(i18n.t("LBL_SELECT_ANY_OPTION"), options, onAccept, OptionDialogBox.MODE_TOGGLE)
								optionDialogBox.show();

							} else if (eventType === GraphicIcon.EventTypes.ON_ZONE_DELETE_CLIC && self.dualZoneEditor != undefined) {

								self.dualZoneEditor.delete();
							}


							function onAccept(obj) {

								if (obj === VmsSignalingZoneEditor.TYPE_TEXT) {

									zoneEditor = drawTextEditor();

								} else if (obj === VmsSignalingZoneEditor.TYPE_GRAPHIC) {

									zoneEditor = drawGraphicEditor();
								}

								if (zoneEditor) {

									$$(optionDialogBox.optionWindow).close();
									self.dualZoneEditor = zoneEditor;

									if (eventType === GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC) {

										zoneEditor.show();
									}
								}
							}

						} else {

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


						function drawTextEditor() {

							let editor = new VmsSignalingTextEditor(self.config.vms.textEditor.width,
								self.config.vms.textEditor.height,
								zoneConfig,
								zoneData,
								onZoneDataChange);

							return editor;
						}


						function drawGraphicEditor() {

							zoneConfig.graphics = getGraphics(zoneConfig.vms_group_id);

							if (zoneConfig.graphics) {

								zoneConfig.pictogramsPath = self.config.vms.pictogramsPath;
								zoneConfig.pictogramsExtension = self.config.vms.pictogramsExtension;
								zoneConfig.pictogramsSize = self.config.vms.pictogramsSize;

								let editor = new VmsSignalingGraphicEditor(self.config.vms.graphicEditor.width,
									self.config.vms.graphicEditor.height,
									zoneConfig,
									zoneData,
									onZoneDataChange);

								return editor;
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
						
						graphics = graphicGroup.graphics.map(e => {
							
							let graphic = {
								id: e.id.split(":")[2],
								group: (e.graphicCategory && e.graphicCategory.labelAlias) ? i18n.t(e.graphicCategory.labelAlias) : null,
								value: e.alias
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
		
		this.update(this.jsonData);
	}
	
	getJsonDataClear() {
		
		let jsonData = undefined;
		let elementValueJsonConfig = this.element.elementValues.find(e => e.elementTypeParam.id === this.config.vms.jsonConfig);
		
		if (elementValueJsonConfig && elementValueJsonConfig.value) {
			
			let jsonConfig = JSON.parse(elementValueJsonConfig.value);
			
			if (Array.isArray(jsonConfig) && jsonConfig.length > 0) {
				
				jsonData = [];
				
				jsonConfig.forEach(e => {
				
					jsonData.push({
						zone: e.zone,
						graphics: e.type == VmsSignalingZoneEditor.TYPE_GRAPHIC ? [] : undefined,
						texts: e.type == VmsSignalingZoneEditor.TYPE_TEXT ? [] : undefined
					})
				})	
			} 
		}
		
		return jsonData;
	}
	
	getCurrentSignalingJsonData() {
		
//		let jsonData = undefined;
//				
//		if (this.element && Array.isArray(this.element.elementValues) && this.element.elementValues.length > 0) {
//			
//			jsonData = this.getJsonDataClear();
//			
//			let elementValueJsonData = this.element.elementValues.find(e => e.elementTypeParam.id === this.config.vms.jsonData);
//		
//			if (elementValueJsonData && elementValueJsonData.value) {
//				
//				jsonData = JSON.parse(elementValueJsonData.value);
//			}
//			else {
//				
//				jsonData = USE_VMS_FAKE_DATA ? this.jsonDataFake : undefined;
//			}	
//		}
		
		return this.jsonData;
	}
	
	update(jsonData) {
		
		if (!jsonData) {
			
			jsonData = this.getJsonDataClear();
		}
		
		let jsonDataCopy = webix.copy(jsonData);
		
		this.jsonData = jsonDataCopy;
		
		let elementValueJsonData = {
		
			elementTypeParam: {
				id: this.config.vms.jsonData
			},
			value: JSON.stringify(jsonDataCopy)
		}
		
		this.graphicIconInstance.updateState({
			id: "ElementTypeState:71:1"
		});
		this.graphicIconInstance.updateValues([elementValueJsonData]);
		
		
		if (this.onUpdate) {
			
			this.onUpdate(jsonDataCopy);	
		}
	}
	
	clear() {
		
		this.update(this.getJsonDataClear());
	}
}	
