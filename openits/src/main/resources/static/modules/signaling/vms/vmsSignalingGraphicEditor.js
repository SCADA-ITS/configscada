import VmsSignalingZoneEditor from "./vmsSignalingZoneEditor.js"
import { util } from "../../../lib/util.js";
import DataView from "../../../component/dataView.js";

export default class VmsSignalingGraphicEditor extends VmsSignalingZoneEditor {
	
	constructor(width, height, config, data, onZoneDataChange) {

		super(width, height, config, data, onZoneDataChange);
		
		this.uid = webix.uid();
		this.containerId = undefined;
		
		this.dataViewMain = undefined;
		this.dataViewAlt = undefined;
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
								{ height: 20 },
								drawTabs(),
								{ height: 20 },
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
			let label = i18n.t("LBL_GRAPHIC_EDITOR");
			let description = i18n.t("LBL_GRAPHIC_EDITOR_DESCRIPTION");

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
						template: "<span>" + label + "</span> <br> <span style='font-size: 13px'>" + description + "</span>",
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
										id: "view-signalling-graphic-editor-option-modes-" + self.uid,
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
												
												let tabInstance = $$("view-signalling-graphic-editor-tabs-" + self.uid);
												
												if (tabInstance) {
												
													if (newv == VmsSignalingZoneEditor.ALTERNANCE_MODE) {
													
														tabInstance.getTabbar().showOption("view-signalling-graphic-editor-alt-" + self.uid);
													}
													else {
														
														tabInstance.getTabbar().hideOption("view-signalling-graphic-editor-alt-" + self.uid);								
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
		
		function drawTabs() {
			
			let viewTabs = {
				id: "view-signalling-graphic-editor-tabs-" + self.uid,
				view: "tabview",
				css: "secondary-tab",
				animate: false,
				cells: [drawPictogramTable(false), drawPictogramTable(true)],
				multiview: {
					keepViews:true
		        },
				tabbar: {
					tabMargin: 6,
					on: {
						onAfterRender: webix.once(function() {
							
							if (self.dataViewMain && Array.isArray(self.data.graphics) && self.data.graphics.length === 1 && self.data.graphics[0].value) {
								
								self.dataViewMain.select([self.data.graphics[0].value]);
							}
							
							if (isAlternance() && self.dataViewAlt && Array.isArray(self.data.graphics) && self.data.graphics.length === 1 && self.data.graphics[0].alternance) {
								
								self.dataViewAlt.select([self.data.graphics[0].alternance]);
							}
						})
					}
				}
			};
			
			function drawPictogramTable(isAlt) {
				
				let metadata = {
					
					options: [
						{ id: DataView.Options.ICON_SIZE, value: self.config.pictogramsSize },
						{ id: DataView.Options.SEARCH, value: true },
						{ id: DataView.Options.GROUP, value: true },
						{ id: DataView.Options.SORTED, value: false }
					]
				}
							
				let dataView = new DataView(metadata, getDataViewData(), null);
				
				let viewPictogramTable = {
					
					header: !isAlt ? i18n.t("LBL_VMS_MAIN") : i18n.t("LBL_VMS_ALTERNANCE"),
					body: {
						id: (!isAlt ? "view-signalling-graphic-editor-main-" : "view-signalling-graphic-editor-alt-") + self.uid,
						rows: [
							{
								cols: [
									{ width: 1, css: { "border-left": "solid 1px #475466 !important" } },
									{ width: 15 },
									{
										rows: [
											{ height: 15 },
											dataView.getView(),
											{ height: 15 },	
										]
									},
									{ width: 11 },
									{ width: 1, css: { "border-left": "solid 1px #475466 !important" } },
									{ width: 3 }
									
								]		
							},
							{ height: 1, css: { "border-top": "solid 1px #475466 !important" } },
							{ height: 1 }
						]
					}
				}
				
				if (isAlt) {
					
					self.dataViewAlt = dataView; 
				}
				else {
					
					self.dataViewMain = dataView;
				}
				
				return viewPictogramTable;	
			}
			
			function getDataViewData() {
				
				let data;
				
				if (Array.isArray(self.config.graphics) && self.config.graphics.length > 0) {
					
					data = self.config.graphics.map(e => { 
					
						let d = {
							id: e.id,
							image: self.config.pictogramsPath + "/" + e.id + self.config.pictogramsExtension,
							//value: ("00000" + parseInt(e.id, 10)).slice(-5),
							value: e.id,
							group: e.group
						};
						
						return d;
						
					});
				}
				
				data.sort((a, b) => (parseInt(a.value, 10) > parseInt(b.value, 10)) ? 1 : ((parseInt(b.value, 10) > parseInt(a.value, 10)) ? -1 : 0));
				
				return data;
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
		
								let data = getData();
								
								if (data) {
									
									self.data = data;
									
									self.onZoneDataChange(self.data);
								
									$$(self.containerId).close();	
								}
								else {
									
									webix.alert({
										title: i18n.t("LBL_WRONG_SELECTION"),
										text: i18n.t("LBL_WRONG_SELECTION_DESCRIPTION"),
										type: "alert-error",
										width: 500
									});
								}
							}
						}
					}
				]
			};
			
			return viewToolbarActions;
		}
		
		function getData() {
			
			let data = {};
			let optionModesInstance = $$("view-signalling-graphic-editor-option-modes-" + self.uid);
				
			data.zone = self.data.zone;
			
			if (optionModesInstance) {
				
				if (optionModesInstance.getValue() == VmsSignalingZoneEditor.ALTERNANCE_MODE) {
				
					data["alternance_ms"] = self.config.properties.defaultAlternanceMs;
				}
				else if (optionModesInstance.getValue() == VmsSignalingZoneEditor.FLASHING_MODE) {
					
					data["flashing_on_ms"] = self.config.properties.defaultFlashingOnMs;
					data["flashing_off_ms"] = self.config.properties.defaultFlashingOffMs;
				}
				
				data.graphics= [];

				let graphic = {	id: 1 }				
				let mainSelectedIds = self.dataViewMain.getSelectedIds();
				
				if (Array.isArray(mainSelectedIds) && mainSelectedIds.length === 1) {
					
					graphic.value = mainSelectedIds[0];
					
					data.graphics.push(graphic);
					
					if (optionModesInstance.getValue() == VmsSignalingZoneEditor.ALTERNANCE_MODE) {
						
						let altSelectedIds = self.dataViewAlt.getSelectedIds();
						
						if (Array.isArray(altSelectedIds) && altSelectedIds.length === 1) {
							
							graphic.alternance = altSelectedIds[0];
						}
						else {
							
							data = undefined;
						}
					}
				}
				else {
					
					data = undefined;
				}
			}
			
			return data;
		}
		
		function isAlternance() {
			
			let isAlt = false;
			let graphicNum = 1;
			let graphicData = undefined;
			
			if (Array.isArray(self.data.graphics)) {
				
				while ((graphicData = self.data.graphics.find(e => e.id === graphicNum)) && !isAlt) {
							
					isAlt |= graphicData.alternance ? true : false; 
					graphicNum++; 
				}	
			}
							
			return isAlt;
		}
		
		function isFlashing() {
			
			return self.data["flashing_on_ms"] ? true : false;
		}
		
		function initialize() {
			
			let optionModes = $$("view-signalling-graphic-editor-option-modes-" + self.uid);
			
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
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_EDIT_GRAPHIC") + " - " + i18n.t("LBL_PANEL_ZONE") + " " + self.config.zone, view, self.height, self.width, true);
		
		initialize();
	}
	
	delete() {
		
		let self = this;
	
		let data = {
			zone: this.data.zone,
			graphic: function() {
				
				let graphicArray = [];
				
				if (Array.isArray(self.data.graphics) && self.data.graphics.length > 0) {
					
					let id = 0;
					
					while (id++ < self.data.graphics.length) {
					
						graphicArray.push({id: id});	
					} 
				}
				
				return graphicArray;
			}()
		};
		
		this.onZoneDataChange(data);
	}
}	
