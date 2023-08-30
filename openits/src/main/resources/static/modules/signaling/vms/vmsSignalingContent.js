import { util } from "../../../lib/util.js";
import VmsSignalingComposer from "./vmsSignalingComposer.js";
import VmsSignalingLibrary from "./vmsSignalingLibrary.js";
import VmsSignalingMulti from "./vmsSignalingMulti.js";
import MessageBox from "../../../component/messageBox.js";
import GenericSignalingCommand from "../generic/genericSignalingCommand.js";

const MAX_ALTERNANCE_S = 10;
const MIN_ALTERNANCE_S = 1;

const MAX_FLASHING_S = 10;
const MIN_FLASHING_S = 1;

const DEFAULT_ALTERNANCE_S = 5;
const DEFAULT_FLASHING_ON_S = 5;
const DEFAULT_FLASHING_OFF_S = 5;

export default class VmsSignalingContent {

	constructor(config, element, subtypeElements, selectedElements, currentCommandElement, onUpdate, onEdit, multiselectAllowed, readOnly) {

		this.config = config;
		this.element = element;
		this.subtypeElements = subtypeElements;
		this.selectedElements = selectedElements ? selectedElements : [element];
		this.currentCommandElement = currentCommandElement;
		this.isEditAction = (onEdit != undefined && currentCommandElement != undefined);
		this.commandsElement = [];
		this.onUpdate = onUpdate;
		this.onEdit = onEdit;
		this.multiselectAllowed = multiselectAllowed;
		this.readOnly = readOnly;
		this.signalingCmds = [];
		this.priorityId = undefined;
		this.startTime = undefined;
		this.endTime = undefined;

		if (this.isEditAction && this.currentCommandElement) {
			
			this.selectedCommand = this.currentCommandElement.commandElementType;
		}
		else {

			this.selectedCommand = element.commandElements.find(e => e.id == config.commands.sendMessage.commandElementTypeId);

			if (this.selectedCommand === undefined) {

				this.selectedCommand = element.commandElements[0];
			}
		}
		
		this.uid = webix.uid();
		
		this.panelMain = undefined;
		this.panelAlt = undefined;
		this.composer = undefined;
		
		this.hasMessage = false;
		
		this.undoJsonData = undefined;
		
		this.commandEquipmentPermission = util.module.isAllowedPermission(this.config.requiredPermissions.commandEquipment);
	}

	getView() {

		let self = this;		
		let editPanelPermission = util.module.isAllowedPermission(self.config.requiredPermissions.editPanel);
		
		let view = {
			id: "view-main-content-" + self.uid,
			rows: [
				getViewHeader(),
				{ height: 10 },
				{
					cols: [
						{ width: 20 },
						getSignallingTabs(),
						{ width: 20 }
					]
				},
				{
					cols: [
						{ width: 20 },
						getViewActions(),	
						{ width: 20 }
					],	
				},
				{ height: 10 }
	
			]
		};
		
		function getViewHeader() {
			
			let viewHeader;
			
			viewHeader = {
				hidden: self.currentCommandElement && self.currentCommandElement.hideTitles,
				cols: [
					{
						view: "label",
						label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-view-headline"></i></div>`,
						align: "center",
						height: 50,
						width: 56
					},
					{
						id: "elements-selected-label" + self.uid,
						view: "label",
						css: "view-header-details-text",
						label: function() {
							
							let label = self.element.alias;
							
							if (Array.isArray(self.selectedElements) && self.selectedElements.length > 1) {
								
								label += " " + i18n.t("LBL_AND").toLowerCase() + " " + (self.selectedElements.length - 1) + " " + i18n.t("LBL_MORE_SELECTED") + " ...";
							}
							 
							return `<span class="view-header-details-text">${label}</span>`;
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
								
								let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_DIALOG_NEW_VMS_CONFIG_DESCRIPTION"), null, onAccept, null);
								ms.show();
								
								function onAccept() {
								
									self.composer.clear();
									self.hasMessage = false;
									
									let btnSend = $$("btn-send-" + self.uid);
									let btnUndo = $$("btn-undo-" + self.uid);
									
									if (btnSend && btnUndo) {
										
										btnSend.disable();
										btnUndo.disable();
									}									
								}
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
								
								let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_DIALOG_UNDO_CHANGES_DESCRIPTION"), null, onAccept, null);
								ms.show();
								
								function onAccept() {

									if (self.undoJsonData) {
										
										self.composer.update(self.undoJsonData);
									
										let btnSend = $$("btn-send-" + self.uid);
										let btnUndo = $$("btn-undo-" + self.uid);

										self.hasMessage = false;
										btnUndo.disable();
																				
										if (btnSend && btnUndo && self.selectedCommand.id == self.config.commands.sendMessage.commandElementTypeId) {
											
											btnSend.disable();
										}	
									}									
								}
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
					
					if (!self.editPanelPermission) {
						
						onUpdateComposer(jsonData);
					}
				}
			}
		}
		
		function getSignallingTabs() {
			
			let signallingTabs = {};

			signallingTabs = {
				view: "tabview",
				id: "view-signalling-tabs-" + self.uid,
				css: "auxiliary-tab",
				animate: false,
				borderless: true,
				cells: [],
				multiview: {
					keepViews:true
		        },
				tabbar: {
					tabMargin: 6,
					on: {
						onChange: function(newv) {
					        
							if ($$(newv)) {
								
								self.selectedCommand = self.element.commandElements.find(e => e.id == $$(newv).config.commandElementTypeId);
						
								let btnSend = $$("btn-send-" + self.uid);
								let btnUndo = $$("btn-undo-" + self.uid);
												
								if (btnSend) {
									
									if (newv != self.config.commands.sendMessage.commandElementTypeId) {
									
										btnSend.enable();
										
									}
									else {
										
										self.hasMessage ? btnSend.enable() : btnSend.disable();
										self.hasMessage ? btnUndo.enable() : btnUndo.disable();
									}
									
									self.setPriorityOptions();	
								}	
							}
					    },
						onAfterRender: function() {
							
							let tabView = $$("view-signalling-tabs-" + self.uid);
							
							if (tabView) {
								
								setTimeout(function() {
									
									let tabSelected = undefined;
									let tabbarOptions = tabView.getTabbar().config.options;

									if (Array.isArray(tabbarOptions) && tabbarOptions.length > 0) {

										tabSelected = tabbarOptions[0].id;
									}
							
									if (self.isEditAction) {
										
										tabSelected = "view-generic-signaling-command-body-" + self.selectedCommand.id + "-" + self.uid;
									} 	
									
									tabView.setValue(tabSelected);
									
									self.setPriorityOptions();						
	
								}, 100);	
							}
						}
					}
				}
			};
			
			if (Array.isArray(self.element.commandElements) && self.element.commandElements.length > 0) {
				
				// First command sendmessage and the ordered alphabetic
				
				let viewSignalling = undefined;
				let commandElementSendMessage = self.element.commandElements.find(e => e.id === self.config.commands.sendMessage.commandElementTypeId);
				
				if (commandElementSendMessage) {

					viewSignalling = getViewSendMessage();

					signallingTabs.cells.push(viewSignalling);
				}
				
				self.element.commandElements.forEach(cmdElement => {
					
					if (cmdElement.id != self.config.commands.sendMessage.commandElementTypeId) {
						
						viewSignalling = getViewCommandOthers(cmdElement);
						
						signallingTabs.cells.push(viewSignalling);
					}
				});
				
				function getViewSendMessage() {
					
					return {
							
						header: function() {

							return `<div><span>${i18n.t(commandElementSendMessage.labelAlias)}</span></div>`;
						}(),
						body: {
							
							id: "view-generic-signaling-command-body-" + commandElementSendMessage.id + "-" + self.uid,
							commandElementTypeId: commandElementSendMessage.id,
							rows: [
								{ height: 20 },
								getViewToolbar(),
								{ height: 10 },
								getViewComposer(),
								getViewOptions(),
								{}	
							]	
						}
					}
					
				}
				
				function getViewCommandOthers(cmd) {
				
					let commandElement = webix.copy(cmd);
						
					if (self.isEditAction && self.currentCommandElement && commandElement.id === self.currentCommandElement.commandElementType.id &&
						Array.isArray(self.currentCommandElement.commandElementValues)) {
						
						commandElement.commandElementValues = webix.copy(self.currentCommandElement.commandElementValues);	
					}
					
					let signalingCmd = new GenericSignalingCommand(self, self.selectedElements.map(e => { return e.id }), commandElement, true, true);
					
					self.signalingCmds.push(signalingCmd);
					
					return signalingCmd.getView();
				}
				
			}
			
			return signallingTabs;
		}
		
		function getViewComposer() {
			
			let viewComposer;
			let initialJsonValue = undefined;
			
			if (self.isEditAction) { 
				
				if (self.currentCommandElement && Array.isArray(self.currentCommandElement.commandElementValues) && 
					self.currentCommandElement.commandElementValues.length > 0) {
					
					let commandElementValue = self.currentCommandElement.commandElementValues.find(e => e.commandElementTypeParam.id === self.config.commands.sendMessage.commandElementValues[0].commandElementTypeParamId);
				
					if (commandElementValue) {
						
						initialJsonValue = JSON.parse(commandElementValue.value); 
					}	
				}
				else {
					
					initialJsonValue = [];	
				}
			}
			else if (Array.isArray(self.selectedElements) && self.selectedElements.length === 1) {
				
				let vmsSingle = self.selectedElements[0];
				
				if (Array.isArray(vmsSingle.elementValues) && vmsSingle.elementValues.length > 0) {
					
					let vmsSingleElementValue = vmsSingle.elementValues.find(e => e.elementTypeParam.id == self.config.vms.jsonData);
					
					if (vmsSingleElementValue && vmsSingleElementValue.value) {
					
						initialJsonValue = JSON.parse(vmsSingleElementValue.value);	
					}
				}
			}
			
			self.composer = new VmsSignalingComposer(self.config, self.element, initialJsonValue, editPanelPermission ? onUpdateComposer : undefined);
			
			if (!editPanelPermission && Array.isArray(initialJsonValue) && initialJsonValue.length > 0) {
				
				onUpdateComposer(initialJsonValue);
			}
			
			viewComposer = {
				rows: [
					{
						cols: [
							{ width: 40 },
							self.composer.getView(),
							{}
						]
					}
				]
			}
			
			return viewComposer;
		}
		
		function getViewOptions() {
			
			let viewVmsOptions = {};
			
			let property = self.config.properties.find(e => e.id === self.element.elementSubtype.id)
				
			if (property) {
				
				let showViewOptions = (property.alternance && (property.configAlternance === undefined || property.configAlternance === true)) ||
									  (property.blinking && (property.configBlinking === undefined || property.configBlinking === true));
				
				viewVmsOptions = {
					cols: [
						{ width: 20 },
						{
							rows: [
								{ 
									type: "section",
									hidden: !showViewOptions, 
									template: i18n.t("LBL_VMS_SETTINGS")
								},
								getViewOptionForm(),
							]
						},
						{}
					]
				}	
			}
			
			function getViewOptionForm() {
				
				let viewOptionForm;
				
				viewOptionForm = {
					
					view: "form",
					id: "view-form-options-" + self.uid,
					borderless: true,
					width: 700,
					elements: []
				}
				
				
				let decimalPrecision = property.decimalPrecision ? property.decimalPrecision : 0;  
		
				if (property.alternance && (property.configAlternance === undefined || property.configAlternance === true)) {
					
					let alternance = self.composer.getOption(VmsSignalingComposer.FIELD_ALTERNANCE_MS);
					
					if (!alternance) {
					
						alternance = property.defaultAlternanceMs ? property.defaultAlternanceMs / 1000 : DEFAULT_ALTERNANCE_S;	
					}
					else {
						
						alternance /= 1000;
					}
					
					viewOptionForm.elements.push({ 
						view: "slider", 
						label: i18n.t("LBL_ALTERNANCE_SECS"),
						tooltip: i18n.t("LBL_ALTERNANCE_SECS"),
						labelWidth: 250, 
						step: decimalPrecision ? (1 / Math.pow(10, decimalPrecision)) : 1,
						value: alternance, 
						min: MIN_ALTERNANCE_S, 
						max: MAX_ALTERNANCE_S,
						title: function(obj){
					         return parseFloat(obj.value).toFixed(decimalPrecision ? decimalPrecision : 0) + " " + i18n.t("LBL_SECONDS");
					    },
						name: "alternance",
						on: {
							onChange: function(newv) {
								
								let btnSend = $$("btn-send-" + self.uid);
								
								if (btnSend) {
									
									btnSend.enable();
								}
							}
						}
					});
				}
				
				if (property.blinking && (property.configBlinking === undefined || property.configBlinking === true)) {
					
					let flashingOn = self.composer.getOption(VmsSignalingComposer.FIELD_FLASHING_ON_MS);
					
					if (!flashingOn) {
					
						flashingOn = property.defaultFlashingOnMs ? property.defaultFlashingOnMs / 1000 : DEFAULT_FLASHING_ON_S;	
					}
					else {
						
						flashingOn /= 1000;
					}
					
					viewOptionForm.elements.push({ 
						view: "slider", 
						label: i18n.t("LBL_FLASHING_ON_SECS"),
						tooltip: i18n.t("LBL_FLASHING_ON_SECS"),
						labelWidth: 250,
						step: decimalPrecision ? (1 / Math.pow(10, decimalPrecision)) : 1, 
						value: flashingOn, 
						min: MIN_FLASHING_S, 
						max: MAX_FLASHING_S,
						title: function(obj){
					         return parseFloat(obj.value).toFixed(decimalPrecision ? decimalPrecision : 0) + " " + i18n.t("LBL_SECONDS");
					    },
						name: "flashingOn",
						on: {
							onChange: function(newv) {
								
								let btnSend = $$("btn-send-" + self.uid);
								
								if (btnSend) {
									
									btnSend.enable();
								}
							}
						}
					});
					
					let flashingOff = self.composer.getOption(VmsSignalingComposer.FIELD_FLASHING_OFF_MS);
					
					if (!flashingOff) {
					
						flashingOff = property.defaultFlashingOffMs ? property.defaultFlashingOffMs / 1000 : DEFAULT_FLASHING_OFF_S;	
					}
					else {
						
						flashingOff /= 1000;
					}
					
					viewOptionForm.elements.push({ 
						view: "slider", 
						label: i18n.t("LBL_FLASHING_OFF_SECS"),
						tooltip: i18n.t("LBL_FLASHING_OFF_SECS"),
						labelWidth: 250,
						step: decimalPrecision ? (1 / Math.pow(10, decimalPrecision)) : 1, 
						value: flashingOff, 
						min: MIN_FLASHING_S, 
						max: MAX_FLASHING_S,
						title: function(obj){
					         return parseFloat(obj.value).toFixed(decimalPrecision ? decimalPrecision : 0) + " " + i18n.t("LBL_SECONDS");
					    },
						name: "flashingOff",
						on: {
							onChange: function(newv) {
								
								let btnSend = $$("btn-send-" + self.uid);
								
								if (btnSend) {
									
									btnSend.enable();
								}
							}
						}
					});
				}
						
				return viewOptionForm;
			}
			
			return viewVmsOptions;
		} 
		
				
		function onUpdateComposer(jsonData) {
			
			if (self.composer) {
				
				if (!self.undoJsonData) {
				
					self.undoJsonData = webix.copy(self.composer.getCurrentSignalingJsonData());
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
		
		function getViewActions() {
			
			let viewActions = {};
			let hideSelectionButton = self.isEditAction;
			let hideSendButton = (!self.commandEquipmentPermission) ? true : self.isEditAction;
			let timeService = window.sm.getService(Services.TIME_SERVICE);
			
			if(!self.isEditAction){
				
				hideSelectionButton = (self.multiselectAllowed) ? true : false;	
			} 
			
			viewActions = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: [
					{},
					{
						view: "toolbar",
						height: 45,
						css: "toolbar-vms-priority",
						borderless: true,
						elements: [
							{
								id: "vms-content-priority-chk-dt-ini-" + self.uid,
								view: "checkbox",
								width: 30,
								value: false,
								hidden: true,
								//tooltip: (this.value ? i18n.t("LBL_SINCE_ENABLE") : i18n.t("LBL_SINCE_DISABLE")),
								on: {
									onChange: function(newValue) {
										
										let dtIni = $$("vms-content-priority-dt-ini-" + self.uid);
										
										if (dtIni) {
											
											if (newValue) {
												
												dtIni.enable();
												
												if (dtIni.getValue() && typeof dtIni.getValue() === 'object') {
													
													self.startTime = dtIni.getValue().setSeconds(0);
												}
												else {
													
													self.startTime = (new Date(timeService.getEpoch())).setSeconds(0);
													dtIni.setValue(new Date(self.startTime));
												}
											}
											else {
												
												dtIni.disable();
												
												dtIni.setValue(null);
												self.startTime = undefined;
											}
										}
									}
								}
							},
							{
								id: "vms-content-priority-dt-ini-" + self.uid,
								view: "datepicker",
								width: 155,
								placeholder: i18n.t("LBL_SINCE"),
								timepicker: true,
								disabled: true,
								hidden: true,
								//value: new Date(timeService.getEpoch()),
								on: {
									onChange: function(newValue) {
										
										self.startTime = (newValue && typeof newValue === 'object') ? newValue.setSeconds(0) : undefined;
									}
								}
							},
							{
								id: "vms-content-priority-chk-dt-end-" + self.uid,
								view: "checkbox",
								value: false,
								width: 30,
								hidden: true,
								//tooltip: (this.value ? i18n.t("LBL_UNTIL_ENABLE") : i18n.t("LBL_UNTIL_DISABLE")),
								on: {
									onChange: function(newValue) {
										
										const ONE_HOUR_IN_MS = 1000 * 3600;
										let dtEnd = $$("vms-content-priority-dt-end-" + self.uid);
										
										if (dtEnd) {
											
											if (newValue) {
												
												dtEnd.enable();
												
												if (dtEnd.getValue() && typeof dtEnd.getValue() === 'object') {
													
													self.endTime = dtEnd.getValue().setSeconds(0);
												}
												else {
													
													self.endTime = (new Date(timeService.getEpoch() + ONE_HOUR_IN_MS)).setSeconds(0);
													dtEnd.setValue(new Date(self.endTime));
												}
											}
											else {
												
												dtEnd.disable();
												
												dtEnd.setValue(null);
												self.endTime = undefined;
											}
										}
									}
								}
							},
							{
								id: "vms-content-priority-dt-end-" + self.uid,
								view: "datepicker",
								placeholder: i18n.t("LBL_UNTIL"),
								width: 155,
								timepicker: true,
								disabled: true,
								hidden: true,
								//value: new Date(timeService.getEpoch()),
								on: {
									onChange: function(newValue) {
										
										self.endTime = (newValue && typeof newValue === 'object') ? newValue.setSeconds(0) : undefined;
									}
								}
							},
							{
								id: "vms-content-priority-combo-" + self.uid,
								view: "richselect",
								width: 200,
								placeholder: i18n.t("LBL_SELECT_PRIORITY"),
								tooltip: i18n.t("LBL_SELECT_PRIORITY"),
								options: {
									css: "toolbar-vms-priority-options",
									data: []
								},
								hidden: true,
								on: {
									onChange: function(newValue) {
										
										self.priorityId = newValue;
									}
								}
							}
						]
					},
					{ 
						view: "button", 
						id: "btn-multisignaling-" + self.uid,
						css: "action-aux-button", 
						hidden: hideSelectionButton,
						disabled: (self.readOnly === true),
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

									let sameElement = selectedElements.find(element => element.id === self.element.id);

									self.selectedElements = selectedElements;

									$$("elements-selected-label" + self.uid).config.label = function(){
										let label = (sameElement) ? sameElement.alias : selectedElements[0].alias;
										if (Array.isArray(selectedElements) && selectedElements.length > 1) {
											label += " " + i18n.t("LBL_AND").toLowerCase() + " " + (selectedElements.length - 1) + " " + i18n.t("LBL_MORE_SELECTED") + " ...";
										}
									
										return `<span class="view-header-details-text">${label}</span>`;											
										}();
									
									$$("elements-selected-label" + self.uid).refresh();	
									

									let btnMultisignaling = $$("btn-multisignaling-" + self.uid);
									
									if (btnMultisignaling) {
											
										btnMultisignaling.config.badge = selectedElements.length;
										btnMultisignaling.config.tooltip = i18n.t("LBL_VMS_SELECTED") + ": " + selectedElements.length;
										
										btnMultisignaling.refresh();	
									}
									
									self.onUpdate(selectedElements);
								}
							}
						} 
					},
					{ 
						view: "button", 
						id: "btn-send-" + self.uid,
						css: "action-button",
						hidden: hideSendButton,
						disabled: (self.readOnly === true),
						width: 130,
						height: 35,
						label: (self.multiselectAllowed) ? i18n.t("LBL_ACCEPT") : i18n.t("LBL_SEND"),
						on: {
							onItemClick() {
								
								if (self.multiselectAllowed && self.onEdit) {
									
									const win = $$(this.config.id).getTopParentView();
									
									if (win) {
										
										win.hide();	
									}
									
									self.onEdit();
									
								} else {

									let commandSelected = self.element.commandElements.find(e => e.id == self.selectedCommand.id);
									//let commandSelected = self.commandsElement.find(e => e.id == self.selectedCommand);
									//let title = (commandSelected ? commandSelected.value : i18n.t("LBL_SEND_VMS_SIGNALING_CONFIRM")); 
									let title = (commandSelected ? i18n.t(commandSelected.labelAlias) : i18n.t("LBL_SEND_VMS_SIGNALING_CONFIRM"));

									title += " (" + self.selectedElements.length + " " + i18n.t("LBL_SELECTED") + ")";

									let ms = new MessageBox(MessageBox.CONFIRM, title, null, onAccept, null);
									ms.show();

									function onAccept() {

										let isCmdSendMessage = (self.selectedCommand.id == self.config.commands.sendMessage.commandElementTypeId);

										let commands = [];

										let sendMessageCmd = {};

										self.selectedElements.forEach(element => {

											sendMessageCmd = {};

											// Command SendMessage
											if (isCmdSendMessage) {

												sendMessageCmd = webix.copy(self.config.commands.sendMessage);
												sendMessageCmd.elementId = element.id;

												if (Array.isArray(sendMessageCmd.commandElementValues) && sendMessageCmd.commandElementValues.length === 1) {

													let options = $$("view-form-options-" + self.uid).getValues();

													sendMessageCmd.commandElementValues[0].value = JSON.stringify(self.composer.getCurrentSignalingJsonData(options));
												}
											}
											// Other commands
											else {

												sendMessageCmd = {
													elementId: element.id,
													commandElementTypeId: self.selectedCommand.id,
												}

												if (Array.isArray(self.signalingCmds) && self.signalingCmds.length > 0) {

													let signalingCmd = self.signalingCmds.find(e => e.commandElementType.id === sendMessageCmd.commandElementTypeId);

													if (signalingCmd) {

														let genericCmd = signalingCmd.getCommandElement(element.id);

														if (genericCmd && Array.isArray(genericCmd.commandElementValues)) {

															sendMessageCmd.commandElementValues = [];

															genericCmd.commandElementValues.forEach(commandElementValue => {

																sendMessageCmd.commandElementValues.push({

																	commandElementTypeParamId: commandElementValue.commandElementTypeParam.id,
																	value: commandElementValue.value
																});
															});
														}
													}
												}

												if (self.composer) {

													self.composer.clear();
												}
												
												self.hasMessage = false;
											}

											if (self.priorityId) {

												sendMessageCmd.priorityId = self.priorityId;
											}

											if (self.startTime) {

												sendMessageCmd.startTime = self.startTime;
											}

											if (self.endTime) {

												sendMessageCmd.endTime = self.endTime;
											}

											commands.push(webix.copy(sendMessageCmd));

											sendMessageCmd = undefined;
										});

										if (Array.isArray(commands) && commands.length > 0) {

											util.element.sendCommand(commands, function(res) {

												if (res) {

													(new MessageBox(MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null, null)).show();
												}
											});
										}
									}
								}
							}
						}
					}
				]
			};
			
			return viewActions;
		}
				
		return view;
	}
	
	getCommandElement(elementId) {
	
		let self = this;
		let commandElement = undefined;
		
		if (!elementId && Array.isArray(self.selectedElements) && self.selectedElements.length > 0) {
			
			elementId = self.selectedElements[0].id;
		}
		
		if (elementId) {
			
			if (Array.isArray(self.element.commandElements) && self.element.commandElements.length > 0) {

				let selectedCommandElement = self.element.commandElements.find(e => e.id === self.selectedCommand.id);
				
				if (selectedCommandElement) {
					
					commandElement = {
						element: {
							id: elementId 
						},
						commandElementType: {
							id: selectedCommandElement.id
						},
						id: selectedCommandElement.id + ":" + elementId.substring(elementId.lastIndexOf(':') + 1)
						
					};
					
					if (Array.isArray(selectedCommandElement.commandElementTypeParams) && 
						selectedCommandElement.commandElementTypeParams.length > 0) {
							
						// SEND MESSAGE
						if (selectedCommandElement.id === self.config.commands.sendMessage.commandElementTypeId &&
							self.composer) {
								
							let options = $$("view-form-options-" + self.uid).getValues();
								
							commandElement.commandElementValues = [];
							
							commandElement.commandElementValues.push({
								
								commandElementTypeParam: {
									id: selectedCommandElement.commandElementTypeParams[0].id 
								},
								value: self.composer.getCurrentSignalingJsonData(options)
							});
						}
						else {
							
							if (Array.isArray(self.signalingCmds) && self.signalingCmds.length > 0) {
								
								let signalingCmd = self.signalingCmds.find(e => e.commandElementType.id === selectedCommandElement.id);
								
								if (signalingCmd) {
									
									commandElement = signalingCmd.getCommandElement(elementId);
								}
							}		
						}
					}
				}				
			}
		}
						
		return commandElement;
	}
	
	setPriorityOptions() {
		
		let self = this;
		
		let chkDtIni = $$("vms-content-priority-chk-dt-ini-" + self.uid);
		let dtIni = $$("vms-content-priority-dt-ini-" + self.uid);
		let chkDtEnd = $$("vms-content-priority-chk-dt-end-" + self.uid);
		let dtEnd = $$("vms-content-priority-dt-end-" + self.uid);
		let cboPriority = $$("vms-content-priority-combo-" + self.uid);
		
		if (chkDtIni && dtIni && chkDtEnd && dtEnd && cboPriority) {
			
			let priorities = self.element.elementType.priorities;

			if (Array.isArray(priorities) && self.selectedCommand && self.selectedCommand.pmQueueCompatibility && !self.onEdit) {
				
				let data = [];
				
				data.push(...priorities.filter(e => IS_TRUE(e.available)).map(priority => {
					return {
					
						id: priority.id,
						value: i18n.t(priority.labelAlias) + (IS_TRUE(priority.defaultPriority) ? " (" + i18n.t("LBL_DEFAULT") + ")" : ""),
						default: IS_TRUE(priority.defaultPriority)
					}
				}));
				
				cboPriority.define("options", { css: "toolbar-vms-priority-options", data: data });
				
				let defaultData = data.find(e => e.default);
				
				if (defaultData) {
					
					cboPriority.setValue(self.priorityId ? self.priorityId : defaultData.id);
					
					self.priorityId = cboPriority.getValue();	
				}
				
				cboPriority.refresh();
				
				chkDtIni.show();
				dtIni.show();
				chkDtEnd.show();
				dtEnd.show();
				cboPriority.show();
			}	
			else {
				
				chkDtIni.hide();
				dtIni.hide();
				chkDtEnd.hide();
				dtEnd.hide();
				cboPriority.hide();
			}	
		}
	}
}	
