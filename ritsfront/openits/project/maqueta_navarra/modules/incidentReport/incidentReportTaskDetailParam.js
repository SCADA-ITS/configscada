import IncidentReportTaskDetail from "./incidentReportTaskDetail.js";
import DataTable from "../../component/datatable.js";
import InputDialogBox from "../../component/inputDialogBox.js";
import MessageBox from "../../component/messageBox.js";

export default class IncidentReportTaskDetailParam extends IncidentReportTaskDetail {
	
	constructor(config, incidentReport, taskState, index, uid) {

		super(config, incidentReport, taskState, index, uid);
		
		this.dtParams = undefined;
	}

	getView() {
		
		let self = this;
		let viewParams;
		
		viewParams = {
			id: "task-details-params-" + self.index + "-" + self.uid,
			rows: [
				{
					view: "template",
					borderless: true,
					height: 20,
					on: {
						onAfterRender: function() {
			
							if (self.dtParams) {
								
								self.dtParams.callbacks();
								
								if (!self.taskState.incidentTypeTask.rerun && ( self.taskState.incidentTypeTask.auto || self.taskState.completedAt)) {
									
									self.dtParams.disable();
								}
							}
						}
					}
				}
			]
		};
		
		
		viewParams.rows.push(...getTableParams());
				
		function getTableParams() {
			
			function getMetadata() {
				
				let metadata;
				
				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "auto" },
					{ gridOption: { id: 23 }, value: "false" }
				];
	
				metadata = {
					filter: true,
					label: "LBL_PARAMS",
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
							label: "LBL_PARAM",
							labelTooltip: "LBL_PARAM",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "param",
							visible: true,
							widthPx: 35,
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
							label: "LBL_PARAM_CURRENT_VALUE",
							labelTooltip: "LBL_PARAM_CURRENT_VALUE",
							replacementFunction: "getLocalDateString",
							needsTranslation: false,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "currentValue",
							visible: self.taskState.completedAt,
							widthPx: 25,
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
							label: "LBL_PARAM_NEW_VALUE",
							labelTooltip: "LBL_PARAM_NEW_VALUE",
							needsTranslation: false,
							replacementFunction: "getLocalDateString",
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "newValue",
							visible: self.taskState.incidentTypeTask.rerun === true || (!self.taskState.incidentTypeTask.auto && !self.taskState.completedAt),
							widthPx: 25,
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
							labelTooltip: "",
							needsTranslation: false,
							parameterized: false,
							position: 5,
							required: true,
							sourceField: "paramTypeId",
							visible: false,
							widthPx: 1,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}
					],
					gridCommands: [
						{
							id: 1,
							value: "LBL_EDIT",
							icon: "mdi mdi-pencil",
							default_: true,
							label: "LBL_EDIT",
							showText: true,
							position: 1,
							multiselect: false,
							itemRequired: true
						},
						{
							id: 2,
							value: "LBL_UNDO_ALL",
							icon: "mdi mdi-undo",
							label: "LBL_UNDO_ALL",
							default_: false,
							showText: true,
							position: 2,
							multiselect: false,
							itemRequired: false
						}
					]
				};
				
				return metadata;
			}
			
			function getEvents() {
				
				let events = {};
			
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
	
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: onEdit
				});
				
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: function() {
						
						let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_UNDO_TASK_PARAMS"), null, onAccept, null);
						ms.show();
						
						function onAccept() {

							self.dtParams.clear();
							self.dtParams.add(getInitialData());							
						};
					}
				});
				
				return events;
			}
			
			function getInitialData() {
				
				let data = [];
				let taskTypeParams = self.taskState.incidentTypeTask.taskType.taskTypeParams;

				if (Array.isArray(taskTypeParams) && taskTypeParams.length) {
					
					taskTypeParams = taskTypeParams.filter(e => self.config.taskParamTypes.includes(e.paramType.id));
	
					taskTypeParams.forEach(taskTypeParam => {
						
						let row;
						let newValue = undefined;
						let currentValue = undefined;
						
						if (Array.isArray(self.taskState.incidentReportTaskValues)) {
							
							let incidentTypeTaskValueResult = self.taskState.incidentReportTaskValues.find(incidentTypeTaskValue => incidentTypeTaskValue.taskTypeParam.id === taskTypeParam.id);
							
							if (incidentTypeTaskValueResult) {
								
								currentValue = incidentTypeTaskValueResult.value; 
							}
						}
						
						if (Array.isArray(self.taskState.incidentTypeTask.incidentTypeTaskValues)) {
							
							let incidentTypeTaskValueResult = self.taskState.incidentTypeTask.incidentTypeTaskValues.find(incidentTypeTaskValue => incidentTypeTaskValue.taskTypeParam.id === taskTypeParam.id);
							
							if (incidentTypeTaskValueResult) {
								
								newValue = incidentTypeTaskValueResult.value; 
							}
						}
						
						row = {
							id: taskTypeParam.id,
							param: i18n.t(taskTypeParam.labelAlias),
							currentValue: currentValue,
							newValue:  (self.taskState.incidentTypeTask.rerun === true || (!self.taskState.incidentTypeTask.auto && !self.taskState.completedAt)) ? newValue : undefined,
							paramTypeId: taskTypeParam.paramTypeId	,
							dataTypeId: taskTypeParam.dataType.id,
							labelUnit: taskTypeParam.labelUnit,
							threshold1: taskTypeParam.threshold1,
							threshold2: taskTypeParam.threshold2				
						}
						
						data.push(row);
					});
				}
				
				return data;
			}
			
			function onEdit() {
						
				let selectedItems = self.dtParams.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
					
					let selectedItem = selectedItems[0];
					
					(new InputDialogBox(selectedItem.param, i18n.t("LBL_ENTER_VALUE_TAGS"), function(value) {
						
						let newItem = webix.copy(selectedItem);
						
						newItem.newValue = value;
						self.dtParams.update(newItem);
						
					}, true, selectedItem.newValue, 
						{id: selectedItem.dataTypeId,
						labelUnit: selectedItem.labelUnit,
						threshold1: selectedItem.threshold1,
						threshold2: selectedItem.threshold2})).show();
				}
			}

			self.dtParams = new DataTable(null, null, getMetadata(), getInitialData(), getEvents());
			
			return self.dtParams.getView();
		}
		
		return viewParams;
	}
	
	getIncidentTypeTaskValues() {
		
		let self = this;
		let incidentTypeTaskValues = undefined;
		
		if (self.dtParams) {
			
			let dtItems = self.dtParams.getAllItems();
			
			if (Array.isArray(dtItems)) {
				
				incidentTypeTaskValues = dtItems.filter(e => e.currentValue != e.newValue); 
				
				incidentTypeTaskValues = incidentTypeTaskValues.map(e => {
						
					return {
						
						taskTypeParamId: e.id,
						value: e.newValue
					}	
				});
			}
		}
		
		return incidentTypeTaskValues;
	}
}