import DataTable from "../../../../component/datatable.js";

const STRETCH_DETAIL_PANEL_WIDTH = 350;

export default class IncidentTypeStretchDetail {

	constructor(config, uid, incidentTypeStretch, onUpdate) {
		
		this.config = config;
		this.incidentTypeStretch = incidentTypeStretch;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.dtAlarmConditions = undefined;
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
				{
					css: { "background-color": "white !important" },
					cols: [
						{ width: 10 },
						getViewAlarmConditions(),
						{ view: "resizer" },
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
				{ height: 10 }
			]
		}
		
		function getViewStretchDetailInfo() {
			
			let viewStretchDetailInfo;
			
			viewStretchDetailInfo = {
		
				id: "incident-type-main-stretch-detail-info-" + self.uid,
				rows: [
					
					getViewStretchDetailInfoHeader(),
					getViewStretchDetailInfoFields()
				]
			}
			
			function getViewStretchDetailInfoHeader() {
				
				let viewStretchDetailInfoHeader;
				
				viewStretchDetailInfoHeader = {
					height: 42,
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
							label: `<span class="alarm-view-header-details-text">${self.incidentTypeStretch.stretch.alias}</span>`,
							align: "left",
							height: 50
						},
						{}
					]
				};
				
				return viewStretchDetailInfoHeader;
			}
			
			function getViewStretchDetailInfoFields() {
				
				let viewStretchDetailInfoFields;
				
				viewStretchDetailInfoFields = {
					height: 200
				};
				
				return viewStretchDetailInfoFields;
			}
			
			function getViewStretchDetailInfoFields() {
				
				let viewStretchDetailInfoFields;
				
				viewStretchDetailInfoFields = {
					
					height: 100
				}
				
				return viewStretchDetailInfoFields;
			}
			
			return viewStretchDetailInfo;
		}
		
		function getViewAlarmConditions() {
			
			let viewAlarmConditions;
			
			viewAlarmConditions = {
				
				id: "incident-type-main-stretch-alarm-conditions-" + self.uid,
				header: function() {

					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_ALARM_CONDITIONS")}</span>
							</div>`;
				},
				body: {
					width: STRETCH_DETAIL_PANEL_WIDTH,
					rows: getViewDatatableAlarmConditions()
				}
			}
			
			function getViewDatatableAlarmConditions() {

				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 2 }, value: "false" },
					{ gridOption: { id: 3 }, value: undefined },
					{ gridOption: { id: 4 }, value: undefined },
					{ gridOption: { id: 5 }, value: undefined },
					{ gridOption: { id: 6 }, value: undefined },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 10 }, value: "false" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 12 }, value: "false" },
					{ gridOption: { id: 13 }, value: "false" },
					{ gridOption: { id: 14 }, value: "false" },
					{ gridOption: { id: 15 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "auto" },
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
							adjust: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}
					]
				};
	
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
	
				events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {
	
					let viewIncidentTypeAlarmConditionDetail = $$("incident-type-alarm-condition-detail-" + self.uid);
					
					if (Array.isArray(rows) && rows.length > 0 && viewIncidentTypeAlarmConditionDetail) {
						
						let alarmConditionId = rows[0];
						
						let alarmCondition = self.incidentTypeStretch.imsIncidentAlarmConditions.find(e => e.id === alarmConditionId);
						
						if (alarmCondition) {

							// TODO: View alarm condition detail							
						}
					}
				};
	
				self.dtAlarmConditions = new DataTable(null, null, metadata, data, events);
	
				return self.dtAlarmConditions.getView();
			}
			
			return viewAlarmConditions;
		}
		
		function getViewAlarmCondition() {
			
			let viewAlarmCondition;
			
			viewAlarmCondition = {
				
				id: "incident-type-alarm-condition-detail-" + self.uid,
			}
			
			return viewAlarmCondition;
		}
			
		return view;
	}
}	
