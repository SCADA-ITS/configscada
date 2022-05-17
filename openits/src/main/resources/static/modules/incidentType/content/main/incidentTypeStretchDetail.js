import DataTable from "../../../../component/datatable.js";
import { util } from "../../../../lib/util.js";

const STRETCH_DETAIL_PANEL_WIDTH = 250;
const FORM_LABEL_WIDTH = 200;
const FORM_FIELD_WIDTH = 400;


export default class IncidentTypeStretchDetail {

	constructor(config, uid, incidentTypeStretch, onUpdate) {
		
		this.config = config;
		this.incidentTypeStretch = incidentTypeStretch;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.dtAlarmConditions = undefined;
		this.dtAlarmConfitionDetails = undefined;
	}
	
	getView() {
		
		let self = this;
		let view;
		
		view = {
			id: "incident-type-stretch-detail-" + self.uid,
			css: {
				"box-sizing": "border-box",
				"border": "1px solid #dadee0 !important"
			},
			rows: [
				{ height: 10 },
				getViewStretchDetailInfo(),
				//{ height: 10 },
				{
					css: { "background-color": "white !important" },
					cols: [
						{ width: 10 },
						getViewAlarmConditions(),
						{ 
							borderless: true,
							width: 10
						},
						getViewAlarmCondition(),
						{ 
							view: "template",
							borderless: true,
							width: 10,
							on: {
								
								onAfterRender: function() {
									
									if (self.dtAlarmConditions)  {
										
										self.dtAlarmConditions.callbacks();
										
										setTimeout(function() {
								
											self.dtAlarmConditions.selectByIndex(0);
										}, 50);
									}
								}
							}
						}
					]
				},
				{ height: 10 },
				{
					height: 38,
					cols: [
						{},
						{
							view: "button",
							css: "action-button",
							width: 130,
							height: 35,
							label: i18n.t("LBL_SAVE"),
							on: {
								onItemClick() {
									
									(new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_SAVE_INCIDENT_TYPE_CHANGES_DESCRIPTION"), onAccept, null)).show();
									
									function onAccept() {
									
										// TODO: Save incident type form
									}
								}
							}
						},
						{ width: 10 }
					]
				},
				{ height: 10 }
			]
		}
		
		function getViewStretchDetailInfo() {
			
			let viewStretchDetailInfo;
			
			viewStretchDetailInfo = {
		
				id: "incident-type-main-stretch-detail-info-" + self.uid,
				css: { "background-color": "white" },
				height: 165,
				rows: [
					
					getViewStretchDetailInfoHeader(),
					{ height: 10 },
					{
						cols: [
							{ width: 10 },
							{
								css: {
									"box-sizing": "border-box",
									"border": "1px solid #dadee0 !important"
								},
								rows: [
									getViewStretchDetailInfoFields()
								]			
							},
							{ width: 10 }
						]
					},
					{ height: 10 }
				]
			}
			
			function getViewStretchDetailInfoHeader() {
				
				let viewStretchDetailInfoHeader;
				
				viewStretchDetailInfoHeader = {
					height: 28,
					cols: [
						{
							view: "label",
							label: `<div><i style="color:#33425e; font-size: 20px" class="mdi mdi-road"></i></div>`,
							align: "center",
							width: 56
						},
						{
							view: "label",
							css: "alarm-view-header-details-text",
							label: `<span style="font-size: 14px">${self.incidentTypeStretch.stretch.alias}</span>`,
							align: "left",
						},
						{}
					]
				};
				
				return viewStretchDetailInfoHeader;
			}
			
			function getViewStretchDetailInfoFields() {
				
				let form;
			
				form = {
					id: "stretch-detaul-info-form-" + self.uid,
					view: "form",
					borderless: true,
					elementsConfig: { labelAlign: "left" },
					elements: [
						{
							cols: [
								{ width: 45 },
								{
									rows: [
										{},
										{
											cols: [
												{ view: "text", id: "stretch-detaul-info-form-default-locationId-" + self.uid, name: "defaultLocationId", hidden: true, value: self.incidentTypeStretch.defaultLocation ? self.incidentTypeStretch.defaultLocation.id : "" },
												{ view: "text", id: "stretch-detaul-info-form-default-location--" + self.uid, name: "defaultLocation", required: false, readonly: true, label: i18n.t("LBL_DEFAULT_LOCATION"), labelWidth: FORM_LABEL_WIDTH, width: FORM_FIELD_WIDTH - 38, value: self.incidentTypeStretch.defaultLocation ? self.incidentTypeStretch.defaultLocation.alias : "" },
												{
													view: "icon", icon: "wxi-search",
													on: {
														onItemClick: function() {
	
															// TODO: Load
														}
													}
												},
												{ width: 45 },
												{ view: "text", id: "stretch-detaul-info-form-related-stretchsId-" + self.uid, name: "relatedStretchsId", hidden: true, value: undefined },
												{ view: "text", id: "stretch-detaul-info-form-related-stretchs--" + self.uid, name: "relatedStretchs", required: false, readonly: true, label: i18n.t("LBL_RELATED_STRETCHS"), labelWidth: FORM_LABEL_WIDTH, value: undefined },
												{
													view: "icon", icon: "wxi-search",
													on: {
														onItemClick: function() {
	
															// TODO: Load cctv scenarios
														}
													}
												}
											]
										},
										{ height: 10 },
										{
											cols: [
												{ view: "text", id: "stretch-detaul-info-form-cctv-scenarioId-" + self.uid, name: "cctvScenarioId", hidden: true, value: self.incidentTypeStretch.scenario ? self.incidentTypeStretch.scenario.id : "" },
												{ view: "text", id: "stretch-detaul-info-form-cctv-scenario-" + self.uid, name: "cctvScenario", required: false, readonly: true, label: i18n.t("LBL_CCTV_SCENARIO"), labelWidth: FORM_LABEL_WIDTH, width: FORM_FIELD_WIDTH - 38, value: self.incidentTypeStretch.scenario ? self.incidentTypeStretch.scenario.alias : "" },
												{
													view: "icon", icon: "wxi-search",
													on: {
														onItemClick: function() {
	
															// TODO: Load cctv scenarios
														}
													}
												},
												{ width: 45 },		
												{ view: "text", id: "stretch-detaul-info-form-operational-plan" + self.uid, name: "operationalPlan", required: false, label: i18n.t("LBL_OPERATIONAL_PLAN"), labelWidth: FORM_LABEL_WIDTH, value: self.incidentTypeStretch.operationalPlanUrl ? self.incidentTypeStretch.operationalPlanUrl : "" }
											]
										},
										{}
									]
								},
								{ width: 45 }
							]
						}
					],
					rules: {
						
					}
				}
		
				return form;
			}
			
			return viewStretchDetailInfo;
		}
		
		function getViewAlarmConditions() {
			
			let viewAlarmConditions;
			
			viewAlarmConditions = {
				
				id: "incident-type-main-stretch-alarm-conditions-" + self.uid,
				css: {
					"box-sizing": "border-box",
					"border": "1px solid #dadee0 !important"
				},
				width: STRETCH_DETAIL_PANEL_WIDTH,
				rows: [
					{
						view: "template",
						borderless: true,
						height: 38,
						css: { "background-color": "var(--blue-soft-opacity-1)", "font-size": "14px", "font-weight": "lighter", "color": "var(--blue-text-default)" },
						template: function() {
							
							return `<div style="padding-top: 10px; padding-left: 10px">
										<span class='ico webix_icon mdi mdi-bell'></span>
										<span>${i18n.t("LBL_ALARMS")}</span>
									</div>`;
						}
					}
				]
			}
			
			viewAlarmConditions.rows.push(...getViewDatatableAlarmConditions());
			
			function getViewDatatableAlarmConditions() {

				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 2 }, value: "false" },
					{ gridOption: { id: 3 }, value: "1" },
					{ gridOption: { id: 4 }, value: "asc" },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 13 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "custom" },
					{ gridOption: { id: 23 }, value: "false" },
					{ gridOption: { id: 24 }, value: "false" }
				];
	
				let metadata = {
					filter: true,
					label: "LBL_ALARM_CONDITIONS",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
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
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "",
							labelTooltip: "LBL_NAME",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "alias",
							visible: true,
							widthPx: 200,
							adjust: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}
					]
				};
				
				metadata.gridCommands = [];
			
				metadata.gridCommands = [
					{
						id: 1,
						value: "LBL_NEW",
						icon: "mdi mdi-plus",
						default_: false,
						label: "LBL_NEW",
						showText: false,
						position: 1,
						multiselect: true,
						itemRequired: false
					},
					{
						id: 2,
						value: "LBL_DELETE",
						icon: "mdi mdi-delete-outline",
						label: "LBL_DELETE",
						default_: false,
						showText: false,
						position: 2,
						multiselect: true,
						itemRequired: true
					}
				]
	
				let data = [];
	
				if (Array.isArray(self.incidentTypeStretch.imsIncidentAlarmConditions) && self.incidentTypeStretch.imsIncidentAlarmConditions.length) {
	
					let conditionCounter = 0;
					data = self.incidentTypeStretch.imsIncidentAlarmConditions.map(e => {
						
						return {
							id: e.id,
							alias: i18n.t("LBL_CONDITION") + "#" + (++conditionCounter)
						}	
					});
				}
	
				let events = {};
			
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [
					{ commandId: 1, function: onNewConditionAlarm},
					{ commandId: 2, function: onDeleteConditionAlarm}
				];
				
				events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {
	
					let viewIncidentTypeAlarmConditionDetail = $$("incident-type-alarm-condition-detail-" + self.uid);
					
					if (Array.isArray(rows) && rows.length > 0 && viewIncidentTypeAlarmConditionDetail) {
						
						let alarmConditionId = rows[0];
						
						let alarmCondition = self.incidentTypeStretch.imsIncidentAlarmConditions.find(e => e.id === alarmConditionId);
						
						if (alarmCondition) {

							drawAlarmCondition(alarmCondition);							
						}
					}
				};
	
				self.dtAlarmConditions = new DataTable(null, null, metadata, data, events);
				
				function onNewConditionAlarm() {
					
				}
				
				function onDeleteConditionAlarm() {
					
				}
	
				return self.dtAlarmConditions.getView();
			}
			
			return viewAlarmConditions;
		}
		
		function drawAlarmCondition(alarmCondition) {
			
			const defaultGridOptionValues = [
				{ gridOption: { id: 1 }, value: "false" },
				{ gridOption: { id: 2 }, value: "false" },
				{ gridOption: { id: 3 }, value: "1" },
				{ gridOption: { id: 4 }, value: "asc" },
				{ gridOption: { id: 7 }, value: "false" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 9 }, value: "true" },
				{ gridOption: { id: 11 }, value: "false" },
				{ gridOption: { id: 13 }, value: "false" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" },
				{ gridOption: { id: 21 }, value: "auto" },
				{ gridOption: { id: 23 }, value: "false" }
			];
	
			let metadata = {
				filter: true,
				label: "LBL_ALARM_CONDITION",
				visible: true,
				gridOptionValues: defaultGridOptionValues,
				gridFields: [
					{
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
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "",
						labelTooltip: "LBL_ALARM_CONFIG_ID",
						needsTranslation: false,
						parameterized: false,
						position: 2,
						required: true,
						sourceField: "alarmConfigId",
						widthPx: 1,
						visible: false,
						adjust: false,
						
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ALARM",
						labelTooltip: "LBL_ALARM",
						needsTranslation: false,
						parameterized: false,
						position: 3,
						required: true,
						sourceField: "alarmConfig",
						widthPx: 200,
						visible: true,
						adjust: true,
						
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "",
						labelTooltip: "LBL_INCIDENT_ALARM_CONFIG_ALARM_TYPE_ID",
						needsTranslation: false,
						parameterized: false,
						position: 4,
						required: true,
						sourceField: "incidentAlarmConditionAlarmTypeId",
						widthPx: 1,
						visible: false,
						adjust: false,
						
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_INCIDENT_ALARM_CONFIG_ALARM_TYPE",
						labelTooltip: "LBL_INCIDENT_ALARM_CONFIG_ALARM_TYPE",
						needsTranslation: false,
						parameterized: false,
						position: 5,
						required: true,
						sourceField: "incidentAlarmConditionAlarmType",
						visible: true,
						adjust: true,
						
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "",
						labelTooltip: "LBL_ELEMENT_TYPE_ID",
						needsTranslation: false,
						parameterized: false,
						position: 6,
						required: true,
						sourceField: "elementTypeId",
						widthPx: 1,
						visible: false,
						adjust: false,
						
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ELEMENT_TYPE",
						labelTooltip: "LBL_ELEMENT_TYPE",
						needsTranslation: false,
						parameterized: false,
						position: 7,
						required: true,
						sourceField: "elementType",
						widthPx: 200,
						visible: true,
						adjust: false,
						
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "",
						labelTooltip: "LBL_ELEMENT_ID",
						needsTranslation: false,
						parameterized: false,
						position: 8,
						required: true,
						sourceField: "elementId",
						widthPx: 1,
						visible: false,
						adjust: false,
						
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ELEMENT",
						labelTooltip: "LBL_ELEMENT",
						needsTranslation: false,
						parameterized: false,
						position: 9,
						required: true,
						sourceField: "element",
						widthPx: 200,
						visible: true,
						adjust: false,
						
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					}
				]
			};
			
			metadata.gridCommands = [];
			
			metadata.gridCommands = [
				{
					id: 1,
					value: "LBL_NEW",
					icon: "mdi mdi-plus",
					default_: false,
					label: "LBL_NEW",
					showText: false,
					position: 1,
					multiselect: true,
					itemRequired: false
				},{
					id: 2,
					value: "LBL_EDIT",
					icon: "mdi mdi-pencil",
					default_: true,
					label: "LBL_EDIT",
					showText: false,
					position: 2,
					multiselect: false,
					itemRequired: true
				},
				{
					id: 3,
					value: "LBL_DELETE",
					icon: "mdi mdi-delete-outline",
					label: "LBL_DELETE",
					default_: false,
					showText: false,
					position: 3,
					multiselect: true,
					itemRequired: true
				}
			]
			
			let data = [];
	
			if (Array.isArray(alarmCondition.incidentAlarmConditionAlarms) && alarmCondition.incidentAlarmConditionAlarms.length > 0) {
			
				data = alarmCondition.incidentAlarmConditionAlarms.map(e => {
				
					return {
						id: e.id,
						alarmConfigId: e.alarmConfig ? e.alarmConfig.id : undefined,
						alarmConfig: e.alarmConfig ? i18n.t(e.alarmConfig.labelAlias) : undefined,
						incidentAlarmConditionAlarmTypeId: e.incidentAlarmConditionAlarmType ? e.incidentAlarmConditionAlarmType.id : undefined,
						incidentAlarmConditionAlarmType: e.incidentAlarmConditionAlarmType ? i18n.t(e.incidentAlarmConditionAlarmType.labelAlias) : undefined,
						elementTypeId: e.elementType ? e.elementType.id : undefined,
						elementType: e.elementType ? i18n.t(e.elementType.labelAlias) : undefined,
						elementId: e.element ? e.element.id : undefined,
						element: e.element ? i18n.t(e.element.alias) : undefined,    
					}	
				});
			}
	
			let events = {};
			
			events[DataTable.EventTypes.ON_COMMAND_EXEC] = [
				{ commandId: 1, function: onNewIncidentConditionAlarm},
				{ commandId: 2, function: onEditIncidentConditionAlarm},
				{ commandId: 3, function: onDeleteIncidentConditionAlarm}
			];

			self.dtAlarmConfitionDetails = new DataTable(null, null, metadata, data, events);
			
			let viewAlarmConfitionDetails = {
				
				id: "incident-type-alarm-condition-detail-" + self.uid,
				css: { "box-sizing": "border-box", "border": "1px solid #dadee0 !important"  },
				cols: [
					{
						rows: self.dtAlarmConfitionDetails.getView()		
					},
					{
						borderless: false,
						width: 1
					}
				]
				
			}
			
			webix.ui(viewAlarmConfitionDetails, $$("incident-type-alarm-condition-detail-" + self.uid));
			
			function onNewIncidentConditionAlarm() {
				
			}
			
			function onEditIncidentConditionAlarm() {
				
			}
			
			function onDeleteIncidentConditionAlarm() {
				
			}
		}
		
		function getViewAlarmCondition() {
			
			let viewAlarmCondition;
			
			viewAlarmCondition = {
				
				id: "incident-type-alarm-condition-detail-" + self.uid,
				css: { "box-sizing": "border-box", "border": "1px solid #dadee0 !important"  },
				cols: [
					util.ui.getViewNoData(),
					{
						borderless: false,
						width: 1
					}
				]
			}
			
			return viewAlarmCondition;
		}
			
		return view;
	}
}	
