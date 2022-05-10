import DataTable from "../../../../component/datatable.js";
import IncidentTypeStateTask from "./incidentTypeStateTask.js";
import { util } from "../../../../lib/util.js";

const STATES_PANEL_WIDTH = 300;
const MOVE_INCIDENT_TYPE_STATE_UP = 1;
const MOVE_INCIDENT_TYPE_STATE_DOWN = 2;

export default class IncidentTypeState {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.dtFixedStateList = undefined;
		this.dtStateList = undefined;
	}
	
	getView() {
		
		let self = this;
		
		let view;
		
		view = {
			id: "incident-type-tab-state-" + self.uid,
			header: i18n.t("LBL_STATES_AND_TASKS"),
			body: getViewState()
		}
		
		function getViewState() {
			
			let viewState;
			
			viewState = {
				id: "incident-type-state-" + self.uid,
				css: "bordered-layout",
				rows: [
					{ height: 10 },
					{
						cols: [
							{ width: 10 },
							getViewStateList(),
							{ width: 10 },
							getViewStateTask(),
							{ 
								view: "template",
								borderless: true,
								width: 1,
								on: {
									
									onAfterRender: function() {
										
										if (self.dtStateList)  {
											
											self.dtStateList.callbacks();
										}
										
										if (self.dtFixedStateList)  {
											
											self.dtFixedStateList.callbacks();
										}
											
										setTimeout(function() {
								
											self.dtStateList.selectByIndex(0);
											
										}, 50);
									}
								}
							},
							{ width: 10 }
						]
					},
					{ height: 10 }
				]
			}
			
			function getViewStateList() {
				
				let viewStateList;
					
				viewStateList = {
					
					id: "incident-type-states-panel-" + self.uid,
					css: {
						"box-sizing": "border-box",
						"border": "1px solid #dadee0 !important"
					},
					width: STATES_PANEL_WIDTH,
					rows: [
						{
							view: "template",
							borderless: true,
							height: 38,
							css: { "background-color": "var(--blue-soft-opacity-1)", "font-size": "14px", "font-weight": "lighter", "color": "var(--blue-text-default)" },
							template: function() {
								
								return `<div style="padding-top: 10px; padding-left: 10px">
											<span class='ico webix_icon mdi mdi-list-status'></span>
											<span>${i18n.t("LBL_STATES")}</span>
										</div>`;
							}
						}
					]
				}
				
				viewStateList.rows.push({
					height: 100,
					cols: [
						{
							rows: getViewDatatableFixedStates()
						}
					]
				});
				viewStateList.rows.push(...getViewDatatableStates());
				
				function getViewDatatableFixedStates() {
					
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
						label: "LBL_FIXED_STATES",
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
					
					let data = [
						{ id: -1, alias: "LBL_DEFAULT_STATE" },
						{ id: self.config.incidentStates.cancel.id, alias: self.config.incidentStates.cancel.labelAlias }
					];
		
		
					let events = {};
				
					events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {
	
						if (self.dtStateList && Array.isArray(rows) && rows.length > 0) {
							
							self.dtStateList.unselectAll();
							
							onSelectState(rows[0]);
						}
					};
		
					self.dtFixedStateList = new DataTable(null, null, metadata, data, events);
					
					
					return self.dtFixedStateList.getView();
				}
				
				function getViewDatatableStates() {
					
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
						label: "LBL_STATES",
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
								labelTooltip: "LBL_POSITION",
								needsTranslation: false,
								parameterized: false,
								position: 2,
								required: true,
								sourceField: "position",
								visible: true,
								widthPx: 45,
								gridFieldDataType: {
									id: 2,
									name: "int"
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
								position: 3,
								required: true,
								sourceField: "alias",
								visible: true,
								adjust: true,
								widthPx: 200,
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
								labelTooltip: "LBL_TYPE",
								needsTranslation: false,
								parameterized: false,
								position: 4,
								required: true,
								sourceField: "type",
								visible: true,
								widthPx: 45,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: [
									{
										icon: "./img/grid/auto.png",
										showValue: false,
										value: "auto",
										labelValue: "LBL_AUTO"
									},
									{
										icon: "./img/grid/manual.png",
										showValue: false,
										value: "manual",
										labelValue: "LBL_MANUAL"
									}
								]
							}
						]
					};
					
					metadata.gridCommands = [];
					
					metadata.gridCommands.push(
						{
							id: 1,
							default_: false,
							icon: "mdi mdi-plus",
							label: "LBL_NEW",
							multiselect: true,
							position: 1,
							showText: false,
							itemRequired: false
						},
						{
							id: 2,
							default_: false,
							icon: "mdi mdi-arrow-up-bold",
							label: "LBL_MOVE_UP",
							multiselect: false,
							position: 2,
							showText: false,
							itemRequired: true
						},
						{
							id: 3,
							default_: false,
							icon: "mdi mdi-arrow-down-bold",
							label: "LBL_MOVE_DOWN",
							multiselect: false,
							position: 3,
							showText: false,
							itemRequired: true
						},
						{
							id: 4,
							default_: false,
							icon: "mdi mdi-delete-outline",
							label: "LBL_DELETE",
							multiselect: true,
							position: 4,
							showText: false,
							itemRequired: true
						}
					);
					
					let data = [];
	
					if (self.incidentType.iniIncidentState && Array.isArray(self.incidentType.incidentTypeStateTransitions) && self.incidentType.incidentTypeStateTransitions.length > 0) {
						
						let iniIncidentState = self.incidentType.iniIncidentState;
						
						let incidentStateIds = new Set();
						
						incidentStateIds.add(self.incidentType.iniIncidentState.id);
						
						self.incidentType.incidentTypeStateTransitions.forEach(e => {
							
							incidentStateIds.add(e.endIncidentState.id);
						});
						
						let currentIncidentId = iniIncidentState.id;
						let position = 0;
						
						do {
							
							let currentTransition = self.incidentType.incidentTypeStateTransitions.find(e => e.iniIncidentState.id === currentIncidentId);
							
							let currentIncidentState = undefined; 
							let nextIncidentState = undefined;
							
							if (currentTransition) {
								
								currentIncidentState = currentTransition.iniIncidentState; 
								nextIncidentState = currentTransition.endIncidentState;
							}
							else {
								
								currentTransition = self.incidentType.incidentTypeStateTransitions.find(e => e.endIncidentState.id === currentIncidentId);
								
								if (currentTransition) {
									
									currentIncidentState = currentTransition.endIncidentState;	
								}
							}
							
							data.push({
								id: currentIncidentState.id,
								position: ++position,
								alias: i18n.t(currentIncidentState.labelAlias),
								type: currentTransition.auto ? "auto" : "manual"
							});
							
							incidentStateIds.delete(currentIncidentState.id);
						
							if (nextIncidentState) {
								
								currentIncidentId = nextIncidentState.id;	
							}

						} while (incidentStateIds.size > 0);
					}	
		
					let events = {};
					
					events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {
	
						if (self.dtFixedStateList && Array.isArray(rows) && rows.length > 0) {
							
							self.dtFixedStateList.unselectAll();
							
							onSelectState(rows[0]);
						}
					};
					
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
					
					// NEW STATE
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 1,
						function: () => {
		
							newIncidentTypeState();
						}
					});
					
					// MOVE UP SELECTED STATE
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 2,
						function: (selectedIds) => {
		
							if (Array.isArray(selectedIds) && selectedIds.length === 1) {
								
								moveIncidentTypeState(selectedIds[0], MOVE_INCIDENT_TYPE_STATE_UP);
							} 
						}
					});
					
					// MOVE DOWN SELECTED STATE
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 3,
						function: (selectedIds) => {
		
							if (Array.isArray(selectedIds) && selectedIds.length === 1) {
								
								moveIncidentTypeState(selectedIds[0], MOVE_INCIDENT_TYPE_STATE_DOWN);
							} 
						}
					});
					
					// DELETE SELECT STATE
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 4,
						function: (selectedIds) => {
		
							if (Array.isArray(selectedIds) && selectedIds.length > 0) {
								
								deleteIncidentTypeState(selectedIds);	
							}
						}
					});
		
					self.dtStateList = new DataTable(null, null, metadata, data, events);
					
					return self.dtStateList.getView();
				}
				
				return viewStateList;
			}
			
			function newIncidentTypeState() {
				
				// TODO
			}
			
			function moveIncidentTypeState(incidentTypeStateId, moveincidentTypeStateType) {
				
				// TODO
			}
			
			function deleteIncidentTypeState(incidentTypeStateIds) {
				
				// TODO
			}
			
			function onSelectState(stateId) {
				
				let incidentTypeStateTask = new IncidentTypeStateTask(self.config, self.uid, self.incidentType, stateId, self.onUpdate); 
				
				webix.ui(incidentTypeStateTask.getView(), $$("incident-type-state-tasks-" + self.uid));
				
				incidentTypeStateTask.loadData();
			}
			
			function getViewStateTask() {
				
				let viewIncidentTypeStateTasks;
				
				viewIncidentTypeStateTasks = {
					id: "incident-type-state-tasks-" + self.uid,
					rows: [
						util.ui.getViewNoData(),
					]
				} 
				
				return viewIncidentTypeStateTasks;
			}
			
			return viewState;
		}
			
		return view;
	}
}	
