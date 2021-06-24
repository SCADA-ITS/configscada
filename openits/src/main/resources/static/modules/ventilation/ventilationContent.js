import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";
import { adapter } from "../../lib/adapter.js";

const FAN_COMMAND_SEND_FANS_STOP = "SEND_FANS_STOP";
const FAN_COMMAND_SEND_FANS_START = "SEND_FANS_START";
const FAN_COMMAND_SEND_FANS_START_REVERSE = "SEND_FANS_START_REVERSE"; 

export default class VentilationContent {
	
	constructor(tunnelId, tubeId, parent) {

		this.tunnelId = tunnelId;
		this.tubeId = tubeId;
		this.parent = parent;

		this.selectedTube;
		this.tubeFans;
		this.tubeFansDataTable;
		this.actualRegime;
		this.proposedRegime;
		this.selectedTunnel;
		this.ventilationModeValueParam;

		this.idStompSubscriptorTunnel;
		this.idStompSubscriptorTube;
		this.idStompSubscriptorFans;
	}

	loadComponent(parentContainnerId, enabledFanIds){

		let self = this;

		webix.ui(util.ui.getViewDrawNoData(), $$(parentContainnerId), 0);

		if(this.tubeId){

			loadData().then( (data) => {

				webix.ui(getView(), $$(parentContainnerId), 0);

				if(this.tubeFansDataTable){

					this.tubeFansDataTable.callbacks();

					if(enabledFanIds){
						
						this.tubeFansDataTable.select(enabledFanIds);
					}

					if(this.ventilationModeValueParam && this.parent.config.ventilationModeOptions.find(option => option.id == this.ventilationModeValueParam.value).disable){
						
						this.tubeFansDataTable.disable();
					}
				}
			});
		} else {

			loadModule(parentContainnerId);
		}

		function loadModule(parentContainnerId) {

			let args = {};
			args.selectedPks = [self.tunnelId];
	
			let tunnelResponse = webix.ajax().sync().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.elementById, { id: self.tunnelId}));		
			if (tunnelResponse) {
		
				self.selectedTunnel = JSON.parse(tunnelResponse.responseText);
			}
	
			let module = findModuleByIdAction(self.parent.config.containerAction.tunnel);
			let moduleAction = {id:self.parent.config.containerAction.tunnel};
			if (module.moduleActions){
	
				moduleAction = module.moduleActions.find(action => action.id == moduleAction.id);
			}
			util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawContent, args);
	
			function drawContent(view) {
	
				view.relative = true;
	
				let viewContent = {
					view: "abslayout",
					cells: [
						view,
						{
							top: 20, right:35,
							rows:[
								{},
								{
									view: "toolbar",
									height: 70,
									borderless: true,
									css: "equip-viewer-header-actions",
									elements: [
										{
											id : "view-content-ventilation-stop-" + self.parent.uid,
											view: "button",
											width: 135,
											label: i18n.t("LBL_STOP"),
											tooltip: i18n.t("LBL_STOP_DESCRIPTION"),
											type: "iconTop",
											icon: "mdi mdi-stop",
											disabled: !self.parent.config.ventilationStates[self.selectedTunnel.elementTypeState.id].actions.includes("SEND_VENTILATION_STOP"),
											on: {
												onItemClick() {
	
													webix.confirm({
														title: i18n.t("LBL_SEND_VENTILATION_STOP_CONFIRM"),
														ok: i18n.t("LBL_ACCEPT"),
														cancel: i18n.t("LBL_CANCEL"),
														text: i18n.t("LBL_SEND_VENTILATION_STOP_CONFIRM_DESCRIPTION"),
														width: 600
													}).then(function() {
														
														let command = self.parent.config.commands.SEND_VENTILATION_STOP;
														command.elementId = self.tunnelId;
							
														let body = {
															commands : []
														};
														body.commands.push(command);
							
														webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
															
															if(!data.json()){
		
																webix.message({type:"error", text:i18n.t("LBL_ERROR_OPERATION")});
															}
														});
													});
												}
											}
										},
										{ width: 12 },
										{
											id : "view-content-ventilation-start-" + self.parent.uid,
											view: "button",
											width: 135,
											label: i18n.t("LBL_START"),
											tooltip: i18n.t("LBL_START_DESCRIPTION"),
											type: "iconTop",
											icon: "mdi mdi-play",
											disabled: !self.parent.config.ventilationStates[self.selectedTunnel.elementTypeState.id].actions.includes("SEND_VENTILATION_START"),
											on: {
												onItemClick() {
	
													webix.confirm({
														title: i18n.t("LBL_SEND_VENTILATION_START_CONFIRM"),
														ok: i18n.t("LBL_ACCEPT"),
														cancel: i18n.t("LBL_CANCEL"),
														text: i18n.t("LBL_SEND_VENTILATION_START_CONFIRM_DESCRIPTION"),
														width: 600
													}).then(function() {
														
														let command = self.parent.config.commands.SEND_VENTILATION_START;
														command.elementId = self.tunnelId;
							
														let body = {
															commands : []
														};
														body.commands.push(command);
							
														webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
															
															if(!data.json()){
																
																webix.message({type:"error", text:i18n.t("LBL_ERROR_OPERATION")});
															}
														});
													});
												}
											}
										}
									]
								},
								{}
							]
						}
					]
				}
	
				webix.ui(viewContent, $$(parentContainnerId), 0);
				
				connectWS();
			}
		}
	
		async function loadData() {
	
			let data = await webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.elementById, { id: self.tubeId}));
				
			self.selectedTube = data.json();
			self.tubeFans = eval("adapter." + self.parent.config.adapters.fans)(self.selectedTube.childs, null);
	
			connectWS();
	
			return true;
		}
	
		function getView() {
				
			let viewContent = {
				rows: [
					{ height: 6 },
					getContentHeader(),
					{ height: 12 },
					{
						cols: [
							{ width: 6 },
							getContentToolbar(),
							{ width: 6 }
						]
					},
					{
						cols: [
							{ width: 6 },
							getContentBody(),
							{ width: 6 }
						]
					},
					{}
				]
			};
	
			return viewContent;
	
			function getContentBody(){
	
				let contentBody = {
					view:"form", 
					id: "view-content-body-" + self.parent.uid,
					rows:[
						getRegime(),
						getButtons(),
						getTable(),
						getBottom(),
						{}
					]
				}
	
				function getRegime(){
	
					let ventilationRegimeConfig = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.ventilationRegimeConfig);
					let availableRegimes;
					self.actualRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.actualRegime);
					self.proposedRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.proposedRegime);
	
					try {
	
						ventilationRegimeConfig = JSON.parse(ventilationRegimeConfig.value);
						
						availableRegimes = ventilationRegimeConfig.ventilationRegimes.map(function (regime) {
							return { 
								id: regime.id, 
								value: i18n.t(regime.labelAlias)
							};
						});
	
						if(self.actualRegime.value && ventilationRegimeConfig.ventilationRegimes){
	
							self.actualRegime.labelAlias = ventilationRegimeConfig.ventilationRegimes.find(e =>e.id==self.actualRegime.value).labelAlias;
						} else {
							self.actualRegime.labelAlias = "LBL_NO_REGIME";
						}
	
						updateProposedRegime(self.proposedRegime.value, ventilationRegimeConfig.ventilationRegimes);
						
					} catch (error) {
	
						console.error("El Json de regimenes no esta correctamente formateado.")
					}
	
					let regime = {
						cols:[
							{
								rows: [
									{ height: 26 },
									{
										cols:[
											{
												view: "label",
												height: 24,
												width: 180,
												template: "<span>" +i18n.t("LBL_ACTUAL_REGIME") + "</span>",
												css: "global-text"
											},
											{ width: 12 },
											{
												view: "label",
												height: 24,
												width: 180,
												template: "<span>" +i18n.t("LBL_PROPOSED_REGIME") + "</span> ",
												css: "global-text"
											},
											{ width: 12 },
											{
												view: "label",
												height: 24,
												width: 180,
												template: "<span>" +i18n.t("LBL_CHANGE_REGIME_TO") + "</span>",
												css: "global-text"
											}
										]
									},
									{ height: 12 },
									{
										cols:[
											{
												view: "label",
												width: 180,
												height: 24,
												id: "label-regimen-1-" + self.parent.uid,
												template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.actualRegime.labelAlias) + '</span></div>',
												css: "global-text"
											},
											{ width: 12 },
											getViewProposedRegimeLabel(),
											{ width: 12 },
											{
												id: "label-regimen-3-" + self.parent.uid,
												width: 180,
												height: 28,
												view:"combo", 
												// css: {"margin-top": "-5px"},
												value: self.proposedRegime.value, 
												options: availableRegimes,
												on: {
													onChange(newv, oldv) {
	
														updateProposedRegime(newv, ventilationRegimeConfig.ventilationRegimes);
														webix.ui(getViewDocumentText(), $$("view-content-regime-document-text-" + self.parent.uid));
														webix.ui(getViewDocument(), $$("view-content-regime-document-" + self.parent.uid));
													}
												}
											}										
										]
									},
									{}
								] 
							},
							{},
							{
								rows:[
									getViewDocument(),
									getViewDocumentText(),
									{ height: 12 },
									{ 	
										cols:[
											{},
											{ 	
												id:"view-content-button-change-regime-" + self.parent.uid,
												width: 130,
												view: "button",
												align:"right", 
												css: "button-cta",
												value: i18n.t("LBL_CHANGE_REGIME"),
												disabled: self.parent.config.ventilationModeOptions.find(option => option.id == self.ventilationModeValueParam.value).disable,
												click: function () {

													let command = self.parent.config.commands.SEND_VENTILATION_REGIME;
													command.elementId = self.tubeId;
													command.commandElementValues[0].value = $$("label-regimen-3-" + self.parent.uid).getValue();

													if (!command.commandElementValues[0].value || command.commandElementValues[0].value ==0) {

														webix.alert({
															title: i18n.t("LBL_NO_REGIME_SELECTED"),
															text: i18n.t("LBL_NO_REGIME_SELECTED_DESCRIPTION"),
															type: "alert-warning",
															width: 500
														});

													} else {
														
														webix.confirm({
															title: i18n.t("LBL_SEND_VENTILATION_REGIME_CONFIRM"),
															ok: i18n.t("LBL_ACCEPT"),
															cancel: i18n.t("LBL_CANCEL"),
															text: i18n.t("LBL_SEND_VENTILATION_REGIME_CONFIRM_DESCRIPTION"),
															width: 600
														}).then(function() {
	
															let body = {
																commands : []
															};
															body.commands.push(command);
								
															webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
																
																if(!data.json()){
		
																	webix.message({type:"error", text:i18n.t("LBL_ERROR_OPERATION")});
																}
															});													
														});
													}	
												}
											}
										]
									}
								]
							}
						]
					};
	
					function getViewProposedRegimeLabel(){
	
						let viewProposedRegimeLabel = {
							view: "label",
							width: 180,
							height: 24,
							id: "label-regimen-2-" + self.parent.uid,
							template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.proposedRegime.labelAlias) + '</span></div>',
							css: "global-text"
						};
	
						return viewProposedRegimeLabel;
					}
	
					function getViewDocument(){
	
						let viewDocument = { 
							id: "view-content-regime-document-" + self.parent.uid,
							height: 26 
						};
	
						if(self.proposedRegime.documentUrl){
	
							viewDocument = {
								id: "view-content-regime-document-" + self.parent.uid,
								cols:[
									{},
									{
										height: 26,
										align: "right",
										width: 500,
										view: "button",
										type: "icon",
										label: self.proposedRegime.documentName + " " + self.proposedRegime.documentType,
										value: self.proposedRegime.documentName + " " + self.proposedRegime.documentType,
										tooltip: true,
										icon: "mdi mdi-file",
										click: function (){
											window.open(self.proposedRegime.documentUrl, '_blank');
										},
										css: "button-icon-transparent"
									}		
								]
							};
						}
	
						return viewDocument;
					}
	
					function getViewDocumentText(){
						return {
							id: "view-content-regime-document-text-"+ self.parent.uid,
							height: 58,
							width: 980,
							borderless: true,
							view: "template",
							template: `<div class="ventilation-description-box"><span > ${self.proposedRegime.description}</span></div>`
						};
					}
	
					function updateProposedRegime(value, ventilationRegimes){
	
						self.proposedRegime.value = value;
	
						if(self.proposedRegime.value && ventilationRegimes){
	
							let proposedConfig = ventilationRegimes.find(e =>e.id==self.proposedRegime.value);
							self.proposedRegime.labelAlias = proposedConfig.labelAlias;
							self.proposedRegime.description = proposedConfig.description;
							self.proposedRegime.documentUrl = proposedConfig.documentUrl
	
							if (self.proposedRegime.documentUrl){
	
								self.proposedRegime.documentName = self.proposedRegime.documentUrl.substring(self.proposedRegime.documentUrl.lastIndexOf("/")+1, self.proposedRegime.documentUrl.lastIndexOf("."));
								self.proposedRegime.documentType = self.proposedRegime.documentUrl.substring(self.proposedRegime.documentUrl.lastIndexOf(".")+1, self.proposedRegime.documentUrl.length);
							}
						} else {
	
							self.proposedRegime.labelAlias = "LBL_NO_REGIME";
							self.proposedRegime.description = "";
						}
					}
	
					return regime;
				}
	
				function getButtons(){
	
					let buttons = {
						cols:[
							{
								view: "segmented",
								css: "segment-custom-2",
								value: "action-panel-switcher-fan" + self.parent.uid,
								width: 200,
								options: [
									{
										id: "action-panel-switcher-fan" + self.parent.uid,
										value: "<span>" + i18n.t("LBL_FAN") + "</span>",
									},
									{
										id: "action-panel-switcher-tube" + self.parent.uid,
										value: "<span>" + i18n.t("LBL_TUBE") + "</span>",
									}
								],
								tooltip: function(obj) {
				
									if (obj.id === "action-panel-switcher-fan" + self.parent.uid) {
				
										return i18n.t("LBL_FAN");
									}
									else if (obj.id === "action-panel-switcher-tube" + self.parent.uid) {
				
										return i18n.t("LBL_TUBE");
									}
								},
								on: {
									onChange(newv, oldv) {
				
										if (newv === "action-panel-switcher-fan" + self.parent.uid) {
											
											webix.message("show-all");
										}
										else if (newv === "action-panel-switcher-tube" + self.parent.uid) {
				
											webix.message("show-top");
										}
									}
								}
							},
							{}
						]
					};
	
					return buttons;
				}
	
				function getTable() {
	
					let data = self.tubeFans;
	
					const defaultGridOptionValues = [
						{ gridOption: { id: 5 }, value: undefined },
						{ gridOption: { id: 6 }, value: undefined },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 21 }, value: "custom" },
						{ gridOption: { id: 23 }, value: "true" }
					];
	
					let metadata = {
						filter: true,
						label: "LBL_FAN",
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [
							{
								id: 102701,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: true,
								needsTranslation: false,
								parameterized: false,
								position: 1,
								required: true,
								sourceField: "id",
								visible: false,
								widthPx: 1,
								gridFieldDataType: {
									id: 1,
									name: "string"
								}
							},
							{
								id: 102702,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_FAN_ALIAS",
								labelTooltip: "LBL_FAN_ALIAS",
								needsTranslation: false,
								parameterized: false,
								position: 2,
								required: false,
								sourceField: "alias",
								visible: true,
								widthPx: 100,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: []
							},
							{
								id: 102503,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_FAN_STATUS",
								labelTooltip: "LBL_FAN_STATUS",
								needsTranslation: true,
								parameterized: false,
								position: 3,
								required: true,
								sourceField: "statusLabelAlias",
								visible: true,
								widthPx: 200,
								adjust: false,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: []
							},
							{
								id: 102504,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								needsTranslation: false,
								parameterized: false,
								position: 4,
								required: false,
								sourceField: "statusSendId",
								visible: false,
								widthPx: 1,
								gridFieldDataType: {
									id: 1,
									name: "string"
								}
							},{
								id: 102505,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_FAN_SEND_STATUS",
								labelTooltip: "LBL_FAN_SEND_STATUS",
								needsTranslation: false,
								parameterized: false,
								position: 5,
								required: true,
								sourceField: "statusSendAlias",
								visible: true,
								widthPx: 400,
								adjust: false,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: []
							}
						],
						gridCommands: [
							{
								id: 1,
								default_: false,
								icon: "mdi mdi-rotate-right",
								label: "LBL_FAN_PLAY",
								multiselect: true,
								position: 1,
								showText: false,
								itemRequired: true
							},
							{
								id: 2,
								default_: false,
								icon: "mdi mdi-rotate-left",
								label: "LBL_FAN_REVERSE",
								multiselect: true,
								position: 2,
								showText: false,
								itemRequired: true
							},
							{
								id: 3,
								default_: false,
								icon: "mdi mdi-stop-circle-outline",
								label: "LBL_FAN_STOP",
								multiselect: true,
								position: 3,
								showText: false,
								itemRequired: true
							},
							{
								id: 4,
								default_: false,
								icon: "mdi mdi-cancel",
								label: "LBL_CLEAR",
								multiselect: true,
								position: 4,
								showText: false,
								itemRequired: true
							}
						]
	
					};
	
					let events = {};
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
	
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 1,
						function: () => {
	
							self.tubeFansDataTable.getSelectedItems().forEach(item => {
								
								item.statusSendId = FAN_COMMAND_SEND_FANS_START;
								item.statusSendAlias = i18n.t("LBL_SEND_FAN_START");
								self.tubeFansDataTable.update(item);
							});						
						}
					});
	
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 2,
						function: () => {
	
							self.tubeFansDataTable.getSelectedItems().forEach(item => {
	
								item.statusSendId = FAN_COMMAND_SEND_FANS_START_REVERSE;
								item.statusSendAlias = i18n.t("LBL_SEND_FAN_START_REVERSE");
								self.tubeFansDataTable.update(item);
							});
						}
					});
	
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 3,
						function: () => {
	
							self.tubeFansDataTable.getSelectedItems().forEach(item => {
	
								item.statusSendId = FAN_COMMAND_SEND_FANS_STOP;
								item.statusSendAlias = i18n.t("LBL_SEND_FAN_STOP");
								self.tubeFansDataTable.update(item);
							});
						}
					});
	
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 4,
						function: () => {
	
							self.tubeFansDataTable.getSelectedItems().forEach(item => {
	
								item.statusSendId = null;
								item.statusSendAlias = "-";
								self.tubeFansDataTable.update(item);
							});
						}
					});
	
					self.tubeFansDataTable = new DataTable(null, null, metadata, data, events);
					
					return {height: 338,rows:self.tubeFansDataTable.getView()};
				};
	
				function getBottom(){
	
					let bottom = {
						cols:[
							{},
							{ 	
								id:"view-content-button-change-fans-status-" + self.parent.uid,
								width: 130,
								view: "button",
								align:"right", 
								css: "button-cta",
								value: i18n.t("LBL_VENTILATION_CHANGE_STATUS"),
								disabled: self.parent.config.ventilationModeOptions.find(option => option.id == self.ventilationModeValueParam.value).disable,
								click: function () { 
									
									webix.confirm({
										title: i18n.t("LBL_VENTILATION_CHANGE_STATUS_CONFIRM"),
										ok: i18n.t("LBL_ACCEPT"),
										cancel: i18n.t("LBL_CANCEL"),
										text: i18n.t("LBL_VENTILATION_CHANGE_STATUS_CONFIRM_DESCRIPTION"),
										width: 600
									}).then(function() {
										
										let allTableItems = self.tubeFansDataTable.getAllItems();
										
										let fansStopCommand = [];
										let fansStartCommand = [];
										let fansStartReverseCommand = [];
	
										allTableItems.forEach(row => {
	
											if(row.statusSendId == FAN_COMMAND_SEND_FANS_STOP){
	
												fansStopCommand.push(row.id);
											
											} else if(row.statusSendId == FAN_COMMAND_SEND_FANS_START){
	
												fansStartCommand.push(row.id);
											
											} else if(row.statusSendId == FAN_COMMAND_SEND_FANS_START_REVERSE){
	
												fansStartReverseCommand.push(row.id);
											}
										});
	
										if(fansStopCommand.length > 0) {
	
											executeFansCommand(FAN_COMMAND_SEND_FANS_STOP, fansStopCommand);
										}  
										
										if(fansStartCommand.length > 0) {
	
											executeFansCommand(FAN_COMMAND_SEND_FANS_START, fansStartCommand);
										}
										
										if(fansStartReverseCommand.length > 0) {
	
											executeFansCommand(FAN_COMMAND_SEND_FANS_START_REVERSE, fansStartReverseCommand);
										}
	
										function executeFansCommand(commandAction, values){
	
											let command = self.parent.config.commands[commandAction];
											command.elementId = self.selectedTube.id;
											command.commandElementValues[0].value = JSON.stringify(values);
				
											let body = {
												commands : []
											};
											body.commands.push(command);
				
											webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
												
												if(!data.json()){
													
													webix.message({type:"error", text:i18n.t("LBL_ERROR_OPERATION")});
												}
											});
										}
									});
								} 
							}
						]
					};
	
					return bottom;
				}
	
				return contentBody;
			}
		}
	
		function getContentHeader() {
	
			let header = {
				id: "view-content-header-" + self.parent.uid,
				rows: [
					{
						id: "view-content-header-bar-color-" + self.parent.uid,
						height: 15,
						css: { "background": util.element.getColor(self.selectedTube.elementTypeState) }
					},
					{ height: 4 },
					{
						cols:[
							{
								borderless:true,
								id: "view-content-header-info" + self.parent.uid,
								height: 80,
								template: function() {
	
									return `<image class="ims-incident-report-icon" src="./img/grid/element_type_07.png" />
											<div class="ims-incident-report-title">
												${self.selectedTube.alias}
											</div>
											<div class="ims-incident-report-title">
												<span class="ims-incident-badge" style="background: ${util.element.getColor(self.selectedTube.elementTypeState)}; color: white;">${i18n.t(self.selectedTube.elementTypeState.labelAlias)}</span>
											</div>`;
								}
							},
							{width: 24 }
						]
					}
				]
			};
		
			return header;
		}
	
		function getContentToolbar() {
	
			self.ventilationModeValueParam = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.ventilationMode);
	
			let ventilationModeOptions = [];
	
			self.parent.config.ventilationModeOptions.forEach(option => {
	
				ventilationModeOptions.push({
					id : option.id, 
					value: i18n.t(option.label) 
				});
			});
					
			let viewContentToolbar = {
				
				id: "view-content-toolbar-" + self.parent.uid,
				view: "toolbar",
				height: 28,
				elements: [],
				borderless: true
			}
			
			let actionPanelSwitcher = {
				view: "segmented",
				css: "segment-custom-2",
				value: self.ventilationModeValueParam.value,
				width: 500,
				options: ventilationModeOptions,
				tooltip: true,
				on: {
					onChange(newv, oldv) {
	
						webix.confirm({
							title: i18n.t("LBL_VENTILATION_CHANGE_MODE_CONFIRM"),
							ok: i18n.t("LBL_ACCEPT"),
							cancel: i18n.t("LBL_CANCEL"),
							text: i18n.t("LBL_VENTILATION_CHANGE_MODE_CONFIRM_DESCRIPTION"),
							width: 600
						}).then(function() {
							
							let command = self.parent.config.commands.SEND_VENTILATION_MODE;
							command.elementId = self.selectedTube.id;
							command.commandElementValues[0].value = newv;
	
							let body = {
								commands : []
							};
							body.commands.push(command);
	
							webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
								
								if(!data.json()){
									
									webix.message({type:"error", text:i18n.t("LBL_ERROR_OPERATION")});
								}
							});
							
						}).fail(() => {
	
							this.blockEvent();
							this.setValue(oldv); 
							this.unblockEvent();
						});
					}
				}
			};
			
			viewContentToolbar.elements.push({});
			viewContentToolbar.elements.push(actionPanelSwitcher);
			
			return viewContentToolbar;
		}
	
		function connectWS() {
	
			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				if (self.tubeId) {
	
					let topic = self.parent.config.topics.element + "." + self.tubeId;
					self.idStompSubscriptorTube = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(topic), onMessageTube);
				
				} else {
	
					let topic = self.parent.config.topics.element + "." + self.tunnelId;
					self.idStompSubscriptorTunnel = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(topic), onMessageTunnel);
				}
	
				if (self.tubeFans && Array.isArray(self.tubeFans) && self.tubeFans.length > 0) {
	
					let fields = [];
					fields.push({
						target: "id",
						values: []
					});
					self.tubeFans.forEach(element => fields[0].values.push(element.id));
									
					self.idStompSubscriptorTube = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.parent.config.topics.fans), onMessageFan, fields);
				}	
			}
	
			function onMessageTunnel(data) {
	
				if (data) {
	
					self.selectedTunnel = eval("adapter." + self.parent.config.adapters.element)(data, self.selectedTunnel);
					
					if(self.parent.config.ventilationStates[self.selectedTunnel.elementTypeState.id].actions.includes("SEND_VENTILATION_STOP")){
	
						$$("view-content-ventilation-stop-" + self.parent.uid).enable();				
					} else {
	
						$$("view-content-ventilation-stop-" + self.parent.uid).disable();
					}
					
					if(self.parent.config.ventilationStates[self.selectedTunnel.elementTypeState.id].actions.includes("SEND_VENTILATION_START")){
	
						$$("view-content-ventilation-start-" + self.parent.uid).enable();				
					} else {
	
						$$("view-content-ventilation-start-" + self.parent.uid).disable();
					}
				}
			}
	
			function onMessageTube(data) {
	
				if (data) {
	
					self.selectedTube = eval("adapter." + self.parent.config.adapters.element)(data, self.selectedTube);
					self.actualRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.actualRegime);
					self.proposedRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.proposedRegime);
					self.ventilationModeValueParam = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.ventilationMode);
	
					webix.ui(getContentHeader(), $$("view-content-header-" + self.parent.uid));
					webix.ui(getContentToolbar(), $$("view-content-toolbar-" + self.parent.uid));
					$$("label-regimen-1-"+self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">'+ i18n.t(self.actualRegime.labelAlias) +'</span></div>')
					$$("label-regimen-2-"+self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">'+ i18n.t(self.proposedRegime.labelAlias) +'</span></div>')
				
					if(!self.ventilationModeValueParam || self.parent.config.ventilationModeOptions.find(option => option.id == self.ventilationModeValueParam.value).disable){
							
						self.tubeFansDataTable.disable();
						$$("view-content-button-change-regime-" + self.parent.uid).disable();
						$$("view-content-button-change-fans-status-" + self.parent.uid).disable();
					} else {
	
						self.tubeFansDataTable.enable();
						$$("view-content-button-change-regime-" + self.parent.uid).enable();
						$$("view-content-button-change-fans-status-" + self.parent.uid).enable();
					}
				}
			}
	
			function onMessageFan(data) {
	
				if (data) {
	
					let oldFanData = self.tubeFansDataTable.getItem(data.id);
					data.statusSendId = oldFanData.statusSendId;
					data.statusSendAlias = oldFanData.statusSendAlias;
	
					let fanData = eval("adapter." + self.parent.config.adapters.fans)([data], null);
					self.tubeFansDataTable.update(fanData);
				}
			}
		}
	}
}
