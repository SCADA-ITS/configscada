import GraphicIcon from "../../../component/graphicIcon/graphicIcon.js";
import VmsSignalingZoneEditor from "./vmsSignalingZoneEditor.js";
import VmsSignalingComposer from "./vmsSignalingComposer.js";
import VmsSignalingTextEditor from "./vmsSignalingTextEditor.js";
import VmsSignalingGraphicEditor from "./vmsSignalingGraphicEditor.js";
import { util } from "../../../lib/util.js";

export default class VmsSignalingContent {

	constructor(config, element) {

		this.config = config;
		this.element = element;
		this.uid = webix.uid();
		
		this.panelMain = undefined;
		this.panelAlt = undefined;
	}

	getView() {

		let self = this;
		let view = {
			
			rows: [
				getViewHeader(),
				{ height: 20 },
				{
					cols: [
						{ width: 20 },
						{
							rows: [
					
								getViewToolbar(),
								{ height: 20 },
								getViewComposer(),
								{ height: 20 },
								getViewPriority(),
								{ height: 20 },
								getViewSchedule(),
								{ height: 20 },
								{}
							]
						},
						{}
//						{ width: 20 }
					]
				},
				{
					cols: [
//						{ width: 20 },
						{},
						getViewActions(),	
						{ width: 20 }
					],	
				},
				{ height: 10 }
			]
			
		};
		
		function getViewHeader() {
			
			let viewHeader = {
				cols: [
					{
						view: "label",
						label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-view-headline"></i></div>`,
						align: "center",
						height: 50,
						width: 56
					},
					{
						view: "label",
						css: "alarm-view-header-details-text",
						label: `<span class="alarm-view-header-details-text">${self.element.alias}</span>`,
						align: "left",
						height: 50
					},
					{}
				]
			};
			
			return viewHeader;
		}
		
		function getViewToolbar() {
			
			let viewToolbar = {
				view: "toolbar",
				height: 50,
				id: "tbMain-" + self.uid,
				borderless: true,
				elements: []
			}
			
			viewToolbar.elements.push({ view: "button", type: "icon", icon: "mdi mdi-file-outline", css: "toolbar-button-text", width: 180, label: i18n.t("LBL_NEW"), tooltip: i18n.t("LBL_NEW_PANEL_COMPOSITION") });
			viewToolbar.elements.push({ view: "button", type: "icon", icon: "mdi mdi-folder-open-outline", css: "toolbar-button-text", width: 180, label: i18n.t("LBL_LOAD"), tooltip: i18n.t("LBL_LOAD_PANEL_FROM_LIBRARY") });
			viewToolbar.elements.push({ view: "button", type: "icon", icon: "mdi mdi-content-save-outline", css: "toolbar-button-text", width: 180, label: i18n.t("LBL_SAVE"), tooltip: i18n.t("LBL_SAVE_TO_LIBRARY") });
			viewToolbar.elements.push({ view: "button", type: "icon", icon: "mdi mdi-trash-can-outline", css: "toolbar-button-text", width: 180, label: i18n.t("LBL_DELETE"), tooltip: i18n.t("LBL_DELETE") });
			viewToolbar.elements.push({ view: "button", type: "icon", icon: "mdi mdi-information-outline", css: "toolbar-button-text", width: 180, label: i18n.t("LBL_INFO"), tooltip: i18n.t("LBL_VIEW_EQUIPMENT") });
			
			return viewToolbar;
		}
		
		function getViewComposer() {
			
			let viewComposer;
			
			let composer = new VmsSignalingComposer(self.config, self.element);
			
			viewComposer = {
				
				rows: [
					{
						cols: [
							{
								view: "label",
								label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-cog"></i></div>`,
								align: "center",
								height: 50,
								width: 56
							},
							{
								view: "label",
								css: "alarm-view-header-details-text",
								label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_SIGNALING")}</span>`,
								align: "left",
								height: 50
							},
							{}
						]
					},
					{
						cols: [
							{ width: 60 },
							composer.getView(),
							{ width: 30 }
						]
					}
				]
			}
			
			return viewComposer;
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
			
			let viewActions = {};
			
			viewActions = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: [
					{},
					{ 
						view: "button", 
						css: "action-button",
						width: 130,
						height: 35,
						label: i18n.t("LBL_SEND"),
						on: {
							onItemClick() {
		
								// TODO						
							}
						}
					}
				]
			};
			
			return viewActions;
		}
				
		return view;
	}
	
	loadData() {
		
	}
}	
