import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";
import { adapter } from "../../lib/adapter.js";

export default class VentilationContent {
	
	constructor(tunnelId, tubeId, parent) {

		this.tunnelId = tunnelId;
		this.tubeId = tubeId;
		this.parent = parent;

		this.selectedTube;
		this.tubeFans;
		this.tubeFansDataTable;
		this.proposedRegime;
	}

	async loadData() {

		let data = await webix.ajax().get(appConfig.restUrl + util.fillTemplate(this.parent.config.restEndpoints.elementById, { id: this.tubeId}));
			
		this.selectedTube = data.json();
		this.tubeFans = eval("adapter." + this.parent.config.adapters.fans)(this.selectedTube.childs, null);

		return true;
	}

	getView() {

		let self = this;
			
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
							{
								rows:[
									{height:18},
									{
										cols:[
											{ 	
												width: 135,
												height: 40,
												type:"icon", 
												icon:"mdi mdi-stop",
												view: "button",
												align:"right", 
												css: "button-ventilation-action",
												label: i18n.t("LBL_STOP"),
												click: function () { 
													
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
											},
											{ width: 12 },
											{ 	
												width: 135,
												height: 40,
												type:"icon", 
												icon:"mdi mdi-play",
												view: "button",
												align:"right",
												css: "button-ventilation-action",
												label: i18n.t("LBL_START"),
												click: function () { 
													
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
										]
									}
								]
							},
							{width: 24 }
						]
					}
				]
			};
		
			return header;
		}

		function getContentToolbar() {

			let ventilationModeValue = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.ventilationMode);
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
				value: ventilationModeValue.value,
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
				let actualRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.actualRegime);
				self.proposedRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.proposedRegime);

				try {

					ventilationRegimeConfig = JSON.parse(ventilationRegimeConfig.value);
					
					availableRegimes = ventilationRegimeConfig.ventilationRegimes.map(function (regime) {
						return { 
							id: regime.id, 
							value: i18n.t(regime.labelAlias)
						};
					});

					if(actualRegime.value && ventilationRegimeConfig.ventilationRegimes){

						actualRegime.labelAlias = ventilationRegimeConfig.ventilationRegimes.find(e =>e.id==actualRegime.value).labelAlias;
					} else {
						actualRegime.labelAlias = "LBL_NO_REGIME";
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
											template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(actualRegime.labelAlias) + '</span></div>',
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
													// webix.ui(getViewProposedRegimeLabel(), $$("label-regimen-2-" + self.parent.uid));
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
								{
									height: 58,
    								width: 980,
									borderless: true,
									view: "template",
									template: `<div class="ventilation-description-box"><span > ${self.proposedRegime.description}</span></div>`
								},
								{ height: 12 },
								{ 	
									cols:[
										{},
										{ 	
											width: 130,
											view: "button",
											align:"right", 
											css: "button-cta",
											value: i18n.t("LBL_CHANGE_REGIME"),
											click: function () { 
												
												webix.confirm({
													title: i18n.t("LBL_SEND_VENTILATION_REGIME_CONFIRM"),
													ok: i18n.t("LBL_ACCEPT"),
													cancel: i18n.t("LBL_CANCEL"),
													text: i18n.t("LBL_SEND_VENTILATION_REGIME_CONFIRM_DESCRIPTION"),
													width: 600
												}).then(function() {
													
													let command = self.parent.config.commands.SEND_VENTILATION_REGIME;
													command.elementId = self.tubeId;
													command.commandElementValues[0].value = $$("label-regimen-3-" + self.parent.uid).getValue();

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
									width: 200,
									view: "button",
									type: "icon",
									label: self.proposedRegime.documentName + " " + self.proposedRegime.documentType,
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
							gridFieldIcons: [
							]
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
							gridFieldIcons: [
							]
						},
						// {
						// 	id: 102504,
						// 	extended: false,
						// 	filtered: false,
						// 	isEpochMilli: false,
						// 	isPk: false,
						// 	label: "LBL_PLAN_ORDER_TYPE",
						// 	labelTooltip: "LBL_PLAN_ORDER_TYPE",
						// 	needsTranslation: false,
						// 	parameterized: false,
						// 	position: 4,
						// 	required: true,
						// 	sourceField: "orderTypeLabelAlias",
						// 	visible: true,
						// 	widthPx: 160,
						// 	adjust: false,
						// 	gridFieldDataType: {
						// 		id: 1,
						// 		name: "string"
						// 	},
						// 	gridFieldIcons: []
						// }
					],
					gridCommands: [

					]

				};

				self.tubeFansDataTable = new DataTable(null, null, metadata, data, null);
				
				return {height: 338,rows:self.tubeFansDataTable.getView()};
			};

			function getBottom(){

				let bottom = {
					cols:[
						{},
						{ 	
							width: 130,
							view: "button",
							align:"right", 
							css: "button-cta",
							value: i18n.t("LBL_CHANGE_STATUS"),
							click: function () { 
								
								webix.message("por hacer");
							} 
						}
					]
				};

				return bottom;
			}

			return contentBody;
		}
	}
}
