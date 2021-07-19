import VmsSignalingComposer from "./vmsSignalingComposer.js";
import VmsSignalingLibrary from "./vmsSignalingLibrary.js";
import VmsSignalingMulti from "./vmsSignalingMulti.js";
import { util } from "../../../lib/util.js";

export default class VmsSignalingContent {

	constructor(config, element, subtypeElements, selectedElements) {

		this.config = config;
		this.element = element;
		this.subtypeElements = subtypeElements;
		this.selectedElements = selectedElements ? selectedElements : [element];
		this.commandsElement = [];
		this.selectedCommand = config.commands.sendMessage.commandElementTypeId;
		this.uid = webix.uid();
		
		this.panelMain = undefined;
		this.panelAlt = undefined;
		this.composer = undefined;
		
		this.hasMessage = false;
		
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
					]
				},
				{
					cols: [
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
						label: function() {
							
							let label = self.element.alias;
							
							if (Array.isArray(self.selectedElements) && self.selectedElements.length > 1) {
								
								label += " " + i18n.t("LBL_AND").toLowerCase() + " " + (self.selectedElements.length - 1) + " " + i18n.t("LBL_MORE_SELECTED") + " ...";
							}
							 
							return `<span class="alarm-view-header-details-text">${label}</span>`;
						}(),
						align: "left",
						height: 50
					},
					{}
				]
			};
			
			return viewHeader;
		}
		
		function getViewToolbar() {
			
			const TOOLBAR_BUTTONS_WIDTH = 130;
			
			let viewToolbar = {
				id: "tbMain-" + self.uid,
				height: 39,
				css: {"margin-left":"2px !important"},
				cols: []
			};
			
			viewToolbar.cols.push(
				{ 
					view: "button", 
					type: "icon", 
					icon: "mdi mdi-file-outline", 
					css: "toolbar-button-text", 
					width: TOOLBAR_BUTTONS_WIDTH, 
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
			
			viewToolbar.cols.push(
				{ 
					view: "button", 
					type: "icon", 
					icon: "mdi mdi-folder-open-outline", 
					css: "toolbar-button-text", 
					width: TOOLBAR_BUTTONS_WIDTH, 
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
			
			viewToolbar.cols.push(
				{
					id: "btn-undo-" + self.uid, 
					view: "button",
					disabled: true, 
					type: "icon", 
					icon: "mdi mdi-undo", 
					css: "toolbar-button-text", 
					width: TOOLBAR_BUTTONS_WIDTH, 
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
										let btnUndo = $$("btn-undo-" + self.uid);

										self.hasMessage = false;
										btnUndo.disable();
																				
										if (btnSend && btnUndo && self.selectedCommand == self.config.commands.sendMessage.commandElementTypeId) {
											
											btnSend.disable();
										}	
									}
								});
							}
						}
					} 
				}
			);
			
			viewToolbar.cols.push(
				{ 
					view: "button", 
					type: "icon", 
					icon: "mdi mdi-information-outline", 
					css: "toolbar-button-text", 
					width: TOOLBAR_BUTTONS_WIDTH, 
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

			viewToolbar.cols.push({});
			
			return viewToolbar;
			
			function loadOnAccept(jsonData) {
				
				if (self.composer) {
					
					self.composer.update(jsonData);
					
					
				}
			}
		}
		
		function getViewCommands() {
			
			if (Array.isArray(self.element.commandElements) && self.element.commandElements.length > 0) {
				
				self.commandsElement = self.element.commandElements.map(cmdElement => {
					
					return {
						
						id: cmdElement.id,
						value: i18n.t(cmdElement.labelAlias)
					}
				})
			}
			
			let viewCommands = {
				id: "view-signalling-commands-" + self.uid,
				view: "radio",
				vertical: false,
				value: self.config.commands.sendMessage.commandElementTypeId,
				options: self.commandsElement,
				on: {
					onChange: function(newv) {
						
						self.selectedCommand = newv;
						
						let btnSend = $$("btn-send-" + self.uid);
						let btnUndo = $$("btn-undo-" + self.uid);
										
						if (btnSend) {
							
							if (newv != self.config.commands.sendMessage.commandElementTypeId) {
							
								btnSend.enable();
								
							}
							else {
								
								self.hasMessage ? btnSend.enable() : btnSend.disable();
							}	
						}
					}
				}
			}
			
			return viewCommands;
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
							{ width: 60 },
							getViewCommands(),
							{ width: 60 }	
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
					let btnUndo = $$("btn-undo-" + self.uid);
				
					if (btnSend && btnUndo) {
						
						self.hasMessage = true;
						btnSend.enable();
						btnUndo.enable();
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
						id: "btn-multisignaling-" + self.uid,
						css: "action-aux-button", 
						width: 130, 
						height: 35,
						label: i18n.t("LBL_MULTISIGNALING"), 
						tooltip: i18n.t("LBL_VMS_SELECTED") + ": " + Array.isArray(self.selectedElements) ? self.selectedElements.length : 1,
						badge: Array.isArray(self.selectedElements) ? self.selectedElements.length : 1,
						on: {
							onItemClick() {
								
								let vmsSingalingMulti = new VmsSignalingMulti(self.config, self.subtypeElements, self.selectedElements, self.element, onAccept);
								
								vmsSingalingMulti.show();
								
								function onAccept(selectedElements) {
									
									self.selectedElements = selectedElements;
																				
									let btnMultisignaling = $$("btn-multisignaling-" + self.uid);
									
									if (btnMultisignaling) {
										
										btnMultisignaling.config.badge = selectedElements.length;
										btnMultisignaling.config.tooltip = i18n.t("LBL_VMS_SELECTED") + ": " + selectedElements.length;
										
										btnMultisignaling.refresh();	
									}
								}
							}
						} 
					},
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
									title: function() {
									
										let commandSelected = self.commandsElement.find(e => e.id == self.selectedCommand);
										let title = (commandSelected ? commandSelected.value : i18n.t("LBL_SEND_VMS_SIGNALING_CONFIRM")); 
										
										title += " (" + self.selectedElements.length + " " + i18n.t("LBL_SELECTED") + ")";
										
										return title;
									}(),
									ok: i18n.t("LBL_YES"),
									cancel: i18n.t("LBL_NO"),
									text: i18n.t("LBL_SEND_VMS_SIGNALING_CONFIRM_DESCRIPTION"),
									width: 600
								}).then(function() {
									
									let isCmdSendMessage = (self.selectedCommand == self.config.commands.sendMessage.commandElementTypeId);
										
									let body = {
										commands : []
									};
									
									let sendMessageCmd = {};
								
									self.selectedElements.forEach(element => {

										sendMessageCmd = {};
										
										if (isCmdSendMessage) {
											
											sendMessageCmd = self.config.commands.sendMessage;
										}
										else {
											
											sendMessageCmd.commandElementTypeId = self.selectedCommand;
										}
										
										if (isCmdSendMessage && Array.isArray(sendMessageCmd.commandElementValues) && sendMessageCmd.commandElementValues.length === 1) {
											
											sendMessageCmd.commandElementValues[0].value = JSON.stringify(self.composer.jsonData);
										}
										
										sendMessageCmd.elementId = element.id;
										
										body.commands.push(webix.copy(sendMessageCmd));	
										
										sendMessageCmd = undefined;
										
									});
									
									if (Array.isArray(body.commands) && body.commands.length > 0) {
										
										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body).then(function(data) {
												
											webix.alert({
												title: i18n.t("LBL_COMMAND_SEND"),
											  	ok: i18n.t("LBL_ACCEPT"),
											  	text: i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"),
												width: 600
											})
										});	
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
}	
