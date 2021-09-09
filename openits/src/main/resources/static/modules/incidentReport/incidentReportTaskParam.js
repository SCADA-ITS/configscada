import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";
import InputDialogBox from "../../component/inputDialogBox.js";

export default class IncidentReportTaskParam {

	constructor(taskState, index, uid) {

		this.taskState = taskState;
		this.index = index;
		this.uid = uid;
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
							needsTranslation: false,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "currentValue",
							visible: true,
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
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "newValue",
							visible: true,
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
						
						webix.confirm({
							title: i18n.t("LBL_DIALOG_UNDO_TASK_PARAMS"),
							ok: i18n.t("LBL_YES"),
							cancel: i18n.t("LBL_NO"),
							text: i18n.t("LBL_ARE_YOU_SURE_UNDO_TASK_PARAMS"),
							width: 600
						}).then(function() {
						
							self.dtParams.clear();
							self.dtParams.add(getInitialData());
						});
					}
				});
				
				return events;
			}
			
			function getInitialData() {
				
				let data = [];
				let taskTypeParams = self.taskState.incidentTypeTask.taskType.taskTypeParams;

				if (Array.isArray(taskTypeParams) && taskTypeParams.length) {
	
					taskTypeParams.forEach(taskTypeParam => {
						
						let row;
						let value = undefined;
						
						if (Array.isArray(self.taskState.incidentTypeTask.incidentTypeTaskValues)) {
							
							let incidentTypeTaskValueResult = self.taskState.incidentTypeTask.incidentTypeTaskValues.find(incidentTypeTaskValue => incidentTypeTaskValue.taskTypeParam.id === taskTypeParam.id);
							
							if (incidentTypeTaskValueResult) {
								
								value = incidentTypeTaskValueResult.value; 
							}
						}
						
						row = {
							id: taskTypeParam.id,
							param: i18n.t(taskTypeParam.labelAlias),
							currentValue: value,
							newValue: undefined,
							paramTypeId: taskTypeParam.paramTypeId						
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
					
					(new InputDialogBox(selectedItem.param, i18n.t("LBL_ENTER_VALUE"), function(value) {
						
						let newItem = webix.copy(selectedItem);
						
						newItem.newValue = value;
						self.dtParams.update(newItem);
						
					}, true, selectedItem.value)).show();
				}
			}

			self.dtParams = new DataTable(null, null, getMetadata(), getInitialData(), getEvents());
			
			return self.dtParams.getView();
		}
		
		return viewParams;
	}
}