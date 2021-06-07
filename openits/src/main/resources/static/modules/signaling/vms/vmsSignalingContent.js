import GraphicIcon from "../../../component/graphicIcon/graphicIcon.js";
import VmsSignalingZoneEditor from "./vmsSignalingZoneEditor.js";
import VmsSignalingTextEditor from "./vmsSignalingTextEditor.js";
import VmsSignalingGraphicEditor from "./vmsSignalingGraphicEditor.js";
import { util } from "../../../lib/util.js";

export default class VmsSignalingContent {

	constructor(parent, element) {

		this.parent = parent;
		this.element = element;
		this.uid = webix.uid();
		
		this.panelMain = undefined;
		this.panelAlt = undefined;
	}

	getView() {

		let self = this;
		let view = {
			
			rows: [
				{ height: 20 },
				getViewHeader(),
				{ height: 20 },
				getViewToolbar(),
				{ height: 20 },
				getViewSignaling(),
				{ height: 20 },
				getViewPriority(),
				{ height: 20 },
				getViewSchedule(),
				{ height: 20 },
				{},
				getViewActions(),
				{ height: 20 }
			]
		};
		
		function getViewHeader() {
			
			let viewHeader = {
				
			};
			
			return viewHeader;
		}
		
		function getViewToolbar() {
			
			let viewToolbar = {
				
			};
			
			return viewToolbar;
		}
		
		function getViewSignaling() {
			
			let viewSignaling = {
				
				cols: [
					{},
					{
						id: "vms-multiview-" + self.uid,
						view: "multiview",
						keepViews:true,
						animate: false,
						cells: getPanels()		
					},
					//getGraphicIcon(),
					{}
				]
			};
			
			function getPanels() {
				
				let property = self.parent.config.properties.find(e => e.id === self.element.elementSubtype.id);
				let cells = [];
				
				if (property) {
					
					cells.push(drawPanel(false));
					
					if (property.alternance) {
					
						cells.push(drawPanel(true));	
					}
				}
				
				return cells;
			}
			
			function drawPanel(isAlternance) {
				
				let viewPanel = {
					id: "vms-main-" + (isAlternance ? "alt-" : "") + self.uid,
					width: 625,
					rows: [
						{
							cols: function() {
								
								let columns = [];
								
								let colChangePanel = {
									width: 100,
									rows: [
										{},
										{
											view: "button",
											width: 75,
											label: isAlternance ? i18n.t("LBL_PANEL_MAIN") : i18n.t("LBL_PANEL_ALTERNANCE"),
											tooltip: isAlternance ? i18n.t("LBL_VIEW_PANEL_MAIN") : i18n.t("LBL_VIEW_PANEL_ALTERNANCE"),
											type: "iconTop",
											icon: isAlternance ? "mdi mdi-chevron-left": "mdi mdi-chevron-right",
											on: {
		
												onItemClick() {
		
													let viewToChange = $$("vms-main-" + (!isAlternance ? "alt-" : "") + self.uid);

													if (viewToChange) {
															
														viewToChange.show();
													}	
												}
											}
										},
										{}
									]
								};
								
								let colSvg = {
									rows: [
										{ height: 20},
										{},
										{
											height: 250,
											borderless: true,
											template: function() {
													
												return `<div>
															<svg id=${"vms-main-svg-" + (isAlternance ? "alt-" : "") + self.uid}></svg>
														</div>`
											},
											on: {
												onAfterRender: function() {
								
													drawGraphicIcon(this.$width, this.$height);
												}
											}
										},
										{},
										{ height: 20 }
									]
								};
								
								let colBlank = { width: 1 };
								
								if (isAlternance) {
									
									columns = [colChangePanel, colSvg, colBlank];
								}
								else {
									
									columns = [colBlank, colSvg, colChangePanel];
								}
								
								return columns;
								
							}(),
						}
					]
					
				};
				
				function drawGraphicIcon(w, h) {

					let property = self.parent.config.properties.find(e => e.id === self.element.elementSubtype.id);
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
							
							let s = Snap("#" + "vms-main-svg-" + (isAlternance ? "alt-" : "") + self.uid);
							s.attr({ width: w, height: h });
							
							let container = { 
								type: GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW, 
								obj: s
							}
							
							let events = {};
			
							events[GraphicIcon.EventTypes.ON_ZONE_EDIT_CLIC] = onZoneEditClic;
							events[GraphicIcon.EventTypes.ON_ZONE_DELETE_CLIC] = onZoneDeleteClic;
							
							let graphicIconInstance = new mod.default(layerElement, self.element, container, events);
							
							if (graphicIconInstance) {
								
								if (isAlternance) {
								
									self.panelAlt = graphicIconInstance;
								}
								else {
									
									self.panelMain = graphicIconInstance;
								}
								
								graphicIconInstance.load(function() {
									
									graphicIconInstance.loadRender(function() {
										
										if (self.element.elementTypeState) {
									
											graphicIconInstance.updateState(self.element.elementTypeState);	
										}
										
										if (Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0) {
											
											graphicIconInstance.updateValues(self.element.elementValues);
										}
										
										if (Array.isArray(self.element.alarms) && self.element.alarms.length > 0) {
											
											graphicIconInstance.updateAlarms(self.element.alarms);
										}
									});
								});	
							}
							
							
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
								
								let zoneEditor;
								
								if (zoneConfig.type === VmsSignalingZoneEditor.TYPE_TEXT) {
									
									zoneEditor = new VmsSignalingTextEditor(zoneConfig, zoneData, onZoneDataChange);
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
						
						
						console.log(zoneData);
					}
					
					function onZoneDeleteClic(zoneData) {
						
						console.log(zoneData);
					}
					
					function onZoneDataChange(newZoneData) {
						
					}
				}
				
				return viewPanel;
			}
			
			return viewSignaling;
		}
		
		function getViewPriority() {
			
			let viewPriority = {
				
			};
			
			return viewPriority;
		}
		
		function getViewSchedule() {
			
			let viewSchedule = {
				
			};
			
			return viewSchedule;
		}
		
		function getViewActions() {
			
			let viewActions = {
				
			};
			
			return viewActions;
		}
		
		function getConfigPanel() {
			
			let configPanel;
			
			if (self.element && Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0) {
				
				let jsonConfig = self.element.elementValues.find(elementValue => elementValue.elementTypeParam.id === self.parent.config.vms.jsonConfig);
				
				if (jsonConfig && jsonConfig.value) {
					
					configPanel = JSON.parse(jsonConfig.value);
				}
			}
			
			return configPanel;
		}
		
		return view;
	}
	
	loadData() {
		
	}
}	
