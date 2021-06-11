import VmsSignalingZoneEditor from "./vmsSignalingZoneEditor.js"
import { util } from "../../../lib/util.js";

export default class VmsSignalingTextEditor extends VmsSignalingZoneEditor {

	constructor(width, height, config, data, onZoneDataChange) {

		super(width, height, config, data, onZoneDataChange);
		
		this.uid = webix.uid();
		this.containerId = undefined;
	}

	show() {

		let self = this;	
		let view = {};
		
		view = {
			
			rows: [
				drawHeader(),
				{
					cols: [				
						{ width: 20 },
						{
							rows: [
								
								{ height: 20 },
								drawOptionModes(),
								{ height: 10 },
								drawOptionAligns(),
								{ height: 30 },
								drawTabs(),
								{ height: 10 },
								//{},
								drawActionButtons(),
								{ height: 10 },
							]
						},
						{ width: 20 }
					]		
				}
			]
		};
		
		function drawHeader() {

			let header = {};
			let label = i18n.t("LBL_TEXT_EDITOR");
			let description = i18n.t("LBL_TEXT_EDITOR_DESCRIPTION");

			header = {

				id: "viewHeader-" + self.uid,
				css: "global-header",
				cols: [
					{
						view: "label",
						label: "<img src='img/grid/default.png' class='grid-icon'/>",
						height: 75,
						css: "icon"
					},
					{
						view: "label",
						height: 75,
						id: "label-bottom-" + self.uid,
						template: "<span>" + i18n.t(label) + "</span> <br> <span style='font-size: 13px'>" + i18n.t(description) + "</span>",
						css: "label"
					}
				]
			};

			return header;
		};
		
		function drawOptionModes() {
			
			let viewOptionModes = {
				
				cols: [
					{
						rows: [
							{
								cols: [
									{
										view: "label",
										label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-text-box-multiple-outline"></i></div>`,
										align: "center",
										height: 50,
										width: 56
									},
									{
										view: "label",
										css: "alarm-view-header-details-text",
										label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_SIGNALING_MODE")}</span>`,
										align: "left",
										height: 50
									},
									{}
								]
							},
							{
								cols: [
									{ width: 55 },
									{
										id: "view-signalling-text-editor-option-modes-" + self.uid,
										view: "radio",
										vertical: false,
										options: function() {
											
											let options = [
												{ "id": VmsSignalingZoneEditor.SINGLE_MODE, "value": i18n.t("LBL_VMS_SINGLE_MODE") }
											];
											
											if (self.config.properties.blinking) {
												
												options.push({ "id": VmsSignalingZoneEditor.FLASHING_MODE, "value": i18n.t("LBL_VMS_FLASHING_MODE") });
											}
											
											if (self.config.properties.alternance) {
												
												options.push({ "id": VmsSignalingZoneEditor.ALTERNANCE_MODE, "value": i18n.t("LBL_VMS_ALTERNANCE_MODE") });
											}
											
											return options;
										}(),
										on: {
											onChange: function(newv) {
												
												let tabInstance = $$("view-signalling-text-editor-tabs-" + self.uid);
												
												if (tabInstance) {
												
													if (newv == VmsSignalingZoneEditor.ALTERNANCE_MODE) {
													
														tabInstance.getTabbar().showOption("view-signalling-text-editor-alt-" + self.uid);
													}
													else {
														
														tabInstance.getTabbar().hideOption("view-signalling-text-editor-alt-" + self.uid);								
													}	
												}
											}
										}
									}
								]
							}
						]
					}
				]
			};
			
			return viewOptionModes;
		}
		
		function drawOptionAligns() {
			
			let viewOptionAligns = {
				cols: [
					{
						rows: [
							{
								cols: [
									{
										view: "label",
										label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-format-align-left"></i></div>`,
										align: "center",
										height: 50,
										width: 56
									},
									{
										view: "label",
										css: "alarm-view-header-details-text",
										label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_SIGNALING_TEXT_ALIGN")}</span>`,
										align: "left",
										height: 50
									},
									{}
								]
							},
							{
								cols: [
									{ width: 55 },
									{
										id: "view-signalling-text-editor-option-aligns-" + self.uid,
										view: "radio",
										vertical: false,
										options: [
											{ "id": VmsSignalingZoneEditor.ALIGN_LEFT, "value": i18n.t("LBL_ALIGN_LEFT") },
											{ "id": VmsSignalingZoneEditor.ALIGN_CENTER, "value": i18n.t("LBL_ALIGN_CENTER") },
											{ "id": VmsSignalingZoneEditor.ALIGN_RIGHT, "value": i18n.t("LBL_ALIGN_RIGHT") }
										],
										on: {
											onChange: function(newv) {
						
												// TODO: Alignment text						
											}
										}
									}
								]
							}
							
						]
					}	
				]
			};
			
			
			return viewOptionAligns;
		}
		
		function drawTabs() {
			
			let viewTabs = {
				id: "view-signalling-text-editor-tabs-" + self.uid,
				view: "tabview",
				css: "secondary-tab",
				animate: false,
				borderless: true,
				cells: [drawTextEditor(false), drawTextEditor(true)],
				multiview: {
					keepViews:true
		        },
				tabbar: {
					tabMargin: 6
				}
			};
			
			function drawTextEditor(alternance) {
				
				let viewTextEditor = {
					
					header: !alternance ? i18n.t("LBL_VMS_MAIN") : i18n.t("LBL_VMS_ALTERNANCE"),
					body: {
						id: (!alternance ? "view-signalling-text-editor-main-" : "view-signalling-text-editor-alt-") + self.uid,
//						cols: [
//							{ width: 10 },
//							{
//								rows: [
//									{ height: 40 },
//									drawTextEditorLines(),
//									{}
//								],	
//							},
//							{ width: 10 }
//						]
						rows: [
							{
								cols: [
									{ width: 1, css: { "border-left": "solid 1px #475466 !important" } },
									{ width: 10 },
									{
										rows: [
											{},
											drawTextEditorLines(),
											{}
										]
									},
									{ width: 6 },
									{ width: 1, css: { "border-left": "solid 1px #475466 !important" } },
									{ width: 3 }
									
								]		
							},
							{ height: 1, css: { "border-top": "solid 1px #475466 !important" } },
							{ height: 1 }
						]
					}
				}
				
				function drawTextEditorLines() {
					
					let lineNum = 1;
					let lineData = undefined;
					let viewLines = {
						cols: [
							{
								rows: []
							}
						]
					};
					
					while (lineData = self.data.text.find(e => e.id === lineNum)) {
						
						viewLines.cols[0].rows.push(drawTextEditorLine());
						
						lineNum++;
					}
					
					function drawTextEditorLine() {
					
						let viewTextEditorLine = {
							
							cols: [
								{},
								{
									id: (!alternance ? "view-signalling-text-editor-main-input-" : "view-signalling-text-editor-alt-input-") + lineData.id + "-" + self.uid,
									view: "text", 
									width: 400,
									//css: { "font-family": self.config.fontInfo["font-family"]},
									//css: "vms-input-line",
									label: i18n.t("LBL_LINE") + lineData.id,
									labelWidth: 150,
									value: alternance ? lineData.alternance : lineData.value,
									on: {
										onAfterRender: function() {
											
											this.getNode().getElementsByTagName("input")[0].style["font-family"] = self.config.fontInfo["font-family"];
										}
									}
	
								},
								{
									id: (!alternance ? "view-signalling-text-editor-main-color-" : "view-signalling-text-editor-alt-color-") + lineData.id + "-" + self.uid,
									view: "colorpicker", 
									value: lineData.color ? lineData.color : self.config.properties.defaultTextColor,
									on:{
										onChange: function(newValue, oldValue, config){
												
											let colorInstance;
											
											if (this.config.id.indexOf("alt-") !== -1) {
												
												colorInstance = $$(this.config.id.replace("alt-", "main-"));	
											}
											else {
												
												colorInstance = $$(this.config.id.replace("main-", "alt-"));
											}
											
											if (colorInstance) {
												
												colorInstance.setValue(newValue);
											}
									    }
									}
								},
								{}
							]
						
							
						};
						
						return viewTextEditorLine;
					}
					
					return viewLines;
				}
				
				
				
				return viewTextEditor;
			}
			
			return viewTabs;
		}
		
		function drawActionButtons() {
			
			let viewToolbarActions = {};
			
			viewToolbarActions = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: [
					{ 
						view: "button",
						css: "dialog-cancel", 
						width: 130,
						height: 35, 
						label: i18n.t("LBL_CANCEL"),
						on: {
							onItemClick() {
							
								$$(self.containerId).close();
							}
						}
					},
					{},
					{ 
						view: "button", 
						css: "dialog-accept",
						width: 130,
						height: 35,
						label: i18n.t("LBL_ACCEPT"),
						on: {
							onItemClick() {
		
								self.data = getData();
								
								self.onZoneDataChange(self.data);
								
								$$(self.containerId).close();					
							}
						}
					}
				]
			};
			
			return viewToolbarActions;
		}
		
		function getData() {
			
			let data = {};
			let optionModesInstance = $$("view-signalling-text-editor-option-modes-" + self.uid);
				
			data.zone = self.data.zone;
			
			if (optionModesInstance) {
				
				if (optionModesInstance.getValue() == VmsSignalingZoneEditor.ALTERNANCE_MODE) {
				
					data["alternance_ms"] = self.config.properties.defaultAlternanceMs;
				}
				else if (optionModesInstance.getValue() == VmsSignalingZoneEditor.FLASHING_MODE) {
					
					data["flashing_on_ms"] = self.config.properties.defaultFlashingOnMs;
					data["flashing_off_ms"] = self.config.properties.defaultFlashingOffMs;
				}
				
				let inputDataMainInstance = undefined;
				let lineNum = 1;
				
				data.text = [];
				
				while (inputDataMainInstance = $$("view-signalling-text-editor-main-input-" + lineNum + "-" + self.uid)) {
					
					let lineData = {};
					
					lineData.id = lineNum;
					lineData.value = inputDataMainInstance.getValue();
					
					if (optionModesInstance.getValue() == VmsSignalingZoneEditor.ALTERNANCE_MODE) {
						
						let inputDataAltInstance = $$("view-signalling-text-editor-alt-input-" + lineNum + "-" + self.uid);
						
						if (inputDataAltInstance) {
							
							lineData.alternance = inputDataAltInstance.getValue(); 		
						}		
					}
					
					let colorDataInstance = $$("view-signalling-text-editor-main-color-" + lineNum + "-" + self.uid);
					
					if (colorDataInstance) {
						
						lineData.color = colorDataInstance.getValue();
					}
					
					data.text.push(lineData);
					
					lineNum++;
				}
			}
			
			return data;
		}
		
		function isAlternance() {
			
			let isAlt = false;
			let lineNum = 1;
			let lineData = undefined;
			
			if (Array.isArray(self.data.text)) {
				
				while ((lineData = self.data.text.find(e => e.id === lineNum)) && !isAlt) {
							
					isAlt |= lineData.alternance ? true : false; 
					lineNum++; 
				}	
			}
			
			return isAlt;
		}
		
		function isFlashing() {
			
			return self.data["flashing_on_ms"] ? true : false;
		}
		
		function initialize() {
			
			let optionModes = $$("view-signalling-text-editor-option-modes-" + self.uid);
			
			if (optionModes) {
				
				if (isAlternance()) {
				
					optionModes.setValue(VmsSignalingZoneEditor.ALTERNANCE_MODE);
				}
				else if(isFlashing()) {
					
					optionModes.setValue(VmsSignalingZoneEditor.FLASHING_MODE);
				}
				else {
					
					optionModes.setValue(VmsSignalingZoneEditor.SINGLE_MODE);
				}	
			}	
		}
		
		function getWindowHeight() {
			
			let lineNum = 1;
			let h = self.height;
			
			while (self.data.text.find(e => e.id === lineNum)) {
						
				lineNum++; 
			}
			
			h += lineNum * 30;
			
			return h;
		}
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_EDIT_TEXT") + " - " + i18n.t("LBL_PANEL_ZONE") + " " + self.config.zone, view, getWindowHeight(), self.width, true);
		
		initialize();
	}
	
	delete() {
		
		let self = this;
	
		let data = {
			zone: this.data.zone,
			text: function() {
				
				let textArray = [];
				
				if (Array.isArray(self.data.text) && self.data.text.length > 0) {
					
					let id = 0;
					
					while (id++ < self.data.text.length) {
					
						textArray.push({id: id});	
					} 
				}
				
				return textArray;
			}()
		};
		
		this.onZoneDataChange(data);
	}
}	
