import VmsSignalingComposer from "./vmsSignalingComposer.js";
import VmsSignalingLibrary from "./vmsSignalingLibrary.js";
import { util } from "../../../lib/util.js";

export default class VmsSignalingContent {

	constructor(config, element) {

		this.config = config;
		this.element = element;
		this.uid = webix.uid();
		
		this.panelMain = undefined;
		this.panelAlt = undefined;
		this.composer = undefined;
		
		this.selectedElements = [element];
		this.undoJsonData = undefined;
	}

	getView() {

		let self = this;
		let view = {
			id: "view-main-content-" + self.uid,
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
						}
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
			
			viewToolbar.elements.push(
				{ 
					view: "button", 
					type: "icon", 
					icon: "mdi mdi-file-outline", 
					css: "toolbar-button-text", 
					width: 130, 
					label: i18n.t("LBL_NEW"), 
					tooltip: i18n.t("LBL_NEW_PANEL_COMPOSITION"),
					on: {
						onItemClick() {
	
							if (self.composer) {
								
								webix.confirm({
									title: i18n.t("LBL_DIALOG_NEW_VMS_CONFIG"),
									ok: i18n.t("LBL_YES"),
									cancel: i18n.t("LBL_NO"),
									text: i18n.t("LBL_DIALOG_NEW_VMS_CONFIG_DESCRIPTION"),
									width: 600
								}).then(function() {
	
									self.composer.clear();
								});
							}
						}
					} 
				}
			);
			
			viewToolbar.elements.push(
				{ 
					view: "button", 
					type: "icon", 
					icon: "mdi mdi-folder-open-outline", 
					css: "toolbar-button-text", 
					width: 130, 
					label: i18n.t("LBL_LOAD"), 
					tooltip: i18n.t("LBL_LOAD_PANEL_FROM_LIBRARY"),
					on: {
						onItemClick() {
	
							let vmsSignalingLibrary = new VmsSignalingLibrary(self.config, self.element, loadOnAccept);
							
							vmsSignalingLibrary.show();
						}
					}
				}
			);
			
			viewToolbar.elements.push(
				{ 
					view: "button", 
					type: "icon", 
					icon: "mdi mdi-undo", 
					css: "toolbar-button-text", 
					width: 130, 
					label: i18n.t("LBL_UNDO"), 
					tooltip: i18n.t("LBL_UNDO"),
					on: {
						onItemClick() {
	
							if (self.composer) {
								
								webix.confirm({
									title: i18n.t("LBL_DIALOG_UNDO_CHANGES"),
									ok: i18n.t("LBL_YES"),
									cancel: i18n.t("LBL_NO"),
									text: i18n.t("LBL_DIALOG_UNDO_CHANGES_DESCRIPTION"),
									width: 600
								}).then(function() {
	
									if (self.undoJsonData) {
										
										self.composer.update(self.undoJsonData);
									
										let btnSend = $$("btn-send-" + self.uid);
										
										if (btnSend) {
											
											btnSend.disable();
										}	
									}
								});
							}
						}
					} 
				}
			);
			
			//viewToolbar.elements.push({ view: "button", type: "icon", icon: "mdi mdi-content-save-outline", css: "toolbar-button-text", width: 130, label: i18n.t("LBL_SAVE"), tooltip: i18n.t("LBL_SAVE_TO_LIBRARY") });
			//viewToolbar.elements.push({ view: "button", type: "icon", icon: "mdi mdi-trash-can-outline", css: "toolbar-button-text", width: 130, label: i18n.t("LBL_DELETE"), tooltip: i18n.t("LBL_DELETE") });
			viewToolbar.elements.push(
				{ 
					view: "button", 
					type: "icon", 
					icon: "mdi mdi-information-outline", 
					css: "toolbar-button-text", 
					width: 130, 
					label: i18n.t("LBL_INFO"), 
					tooltip: i18n.t("LBL_VIEW_EQUIPMENT"),
					on: {
						onItemClick() {
	
							if (self.config.action && self.config.action.equipmentViewer) {
								
								let moduleActionId = parseInt(self.config.action.equipmentViewer);
								let elementIds = [self.element.id];
				
								if (moduleActionId && elementIds) {
				
									let module = util.module.getFromModuleAction(moduleActionId);
				
									if (module) {
				
										let moduleAction = { id: moduleActionId };
										let args = { selectedPks: elementIds };
				
										util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
									}
								}
							}
						}
					} 
				}
			);
			
			return viewToolbar;
			
			function loadOnAccept(jsonData) {
				
				if (self.composer) {
					
					self.composer.update(jsonData);
				}
			}
		}
		
		function getViewComposer() {
			
			let viewComposer;
			
			self.composer = new VmsSignalingComposer(self.config, self.element, onUpdateComposer);
			
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
							{},
							self.composer.getView(),
							{}
						]
					}
				]
			}
			
			return viewComposer;
		}
				
		function onUpdateComposer(jsonData) {
			
			if (self.composer) {
				
				if (!self.undoJsonData) {
				
					self.undoJsonData = webix.copy(self.composer.jsonData);
					
					return;
				}
				
				if (jsonData) {
					
					let btnSend = $$("btn-send-" + self.uid);
				
					if (btnSend) {
						
						btnSend.enable();
					}	
				}	
			}
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
						id: "btn-send-" + self.uid,
						css: "action-button",
						disabled: true,
						width: 130,
						height: 35,
						label: i18n.t("LBL_SEND"),
						on: {
							onItemClick() {
		
								webix.confirm({
									title: i18n.t("LBL_SEND_VMS_SIGNALING_CONFIRM"),
									ok: i18n.t("LBL_YES"),
									cancel: i18n.t("LBL_NO"),
									text: i18n.t("LBL_SEND_VMS_SIGNALING_CONFIRM_DESCRIPTION"),
									width: 600
								}).then(function() {
									
									if (self.config.commands && self.config.commands.sendMessage) {
										
										let body = {
											commands : []
										};
									
										self.selectedElements.forEach(element => {

											let sendMessageCmd = self.config.commands.sendMessage;
										
											sendMessageCmd.elementId = element.id;
											
											if (Array.isArray(sendMessageCmd.commandElementValues) && sendMessageCmd.commandElementValues.length === 1) {
												
												sendMessageCmd.commandElementValues[0].value = JSON.stringify(self.composer.jsonData);
											}
											
											body.commands.push(sendMessageCmd);	
											
										});
										
										if (Array.isArray(body.commands) && body.commands.length > 0) {
											
											webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body).then(function(data) {
													
												console.log(data);
											});	
										}	
									}
								});					
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
