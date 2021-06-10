import VmsSignalingZoneEditor from "./vmsSignalingZoneEditor.js"
import { util } from "../../../lib/util.js";
import DataView from "../../../component/dataView.js";

const WINDOW_WIDTH = 1110;
const WINDOW_HEIGHT = 700;

export default class VmsSignalingGraphicEditor extends VmsSignalingZoneEditor {
	
	constructor(config, data, onZoneDataChange) {

		super(config, data, onZoneDataChange);
		
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
				css: "auxiliary-tab",
				animate: false,
				borderless: true,
				cells: [drawPictogramTable(false), drawPictogramTable(true)],
				multiview: {
					keepViews:true
		        },
				tabbar: {
					tabMargin: 6
				}
			};
			
			function drawPictogramTable(alternance) {
				
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
					
					header: !alternance ? i18n.t("LBL_VMS_MAIN") : i18n.t("LBL_VMS_ALTERNANCE"),
					body: {
						id: (!alternance ? "view-signalling-graphic-editor-main-" : "view-signalling-graphic-editor-alt-") + self.uid,
						cols: [
							{ width: 10 },
							dataView.getView(),
							{ width: 10 }
						]	
					}
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
				
				data.graphic= [];
				
				// TODO: Get data from datatables
			}
			
			return data;
		}
		
		function isAlternance() {
			
			let isAlt = false;
			let graphicNum = 1;
			let graphicData = undefined;
			
			while ((graphicData = self.data.graphic.find(e => e.id === graphicNum)) && !isAlt) {
						
				isAlt |= graphicData.alternance ? true : false; 
				graphicNum++; 
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
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_EDIT_GRAPHIC"), view, WINDOW_HEIGHT, WINDOW_WIDTH, true);
		
		initialize();
	}
	
	delete() {
		
		let self = this;
	
		let data = {
			zone: this.data.zone,
			graphic: function() {
				
				let graphicArray = [];
				
				if (Array.isArray(self.data.graphic) && self.data.graphic.length > 0) {
					
					let id = 0;
					
					while (id++ < self.data.graphic.length) {
					
						graphicArray.push({id: id});	
					} 
				}
				
				return graphicArray;
			}()
		};
		
		this.onZoneDataChange(data);
	}
}	
