import DataTable from "../../../../component/datatable.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";

export default class IncidentTypeTaskDetailParam {

	constructor(config, uid, incidentType, incidentTypeTask, onUpdate) {
			
		this.config = config;
		this.uid = uid;
		this.incidentType = incidentType;
		this.incidentTypeTask = incidentTypeTask;
		this.onUpdate = onUpdate;
		
		this.dtTaskTypeParams = undefined;
	}
	
	draw() {
		
		let self = this;
		
		let viewTaskParams = {
				
			id: "incident-type-state-task-detail-content-params-" + self.uid,
			css: "bordered-layout",
			rows: [{}]
		}
		
		let taskType = window.em.getByField(EntityType.IMS_TASK_TYPES, "id", self.incidentTypeTask.taskType.id);
		
		if (taskType && Array.isArray(taskType.taskTypeParams)) {
			
			viewTaskParams.rows = getViewDatatableTaskParams();
			
			$$(viewTaskParams.id).show();
			
			webix.ui(viewTaskParams, $$(viewTaskParams.id));
		
			self.dtTaskTypeParams.callbacks();
		}
		else {
			
			$$(viewTaskParams.id).hide();
		}
		
		function getViewDatatableTaskParams() {
				
			const defaultGridOptionValues = [
				{ gridOption: { id: 1 }, value: "false" },
				{ gridOption: { id: 2 }, value: "false" },
				{ gridOption: { id: 3 }, value: 2 },
				{ gridOption: { id: 4 }, value: "asc" },
				{ gridOption: { id: 5 }, value: undefined },
				{ gridOption: { id: 6 }, value: undefined },
				{ gridOption: { id: 7 }, value: "false" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 9 }, value: "true" },
				{ gridOption: { id: 10 }, value: "true" },
				{ gridOption: { id: 11 }, value: "false" },
				{ gridOption: { id: 12 }, value: "true" },
				{ gridOption: { id: 13 }, value: "false" },
				{ gridOption: { id: 14 }, value: "false" },
				{ gridOption: { id: 15 }, value: "false" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" },
				{ gridOption: { id: 21 }, value: "custom" },
				{ gridOption: { id: 23 }, value: "false" }
			];

			let metadata = {
				filter: true,
				label: "LBL_TASK_PARAMS",
				visible: true,
				gridOptionValues: defaultGridOptionValues,
				gridFields: [
					{
						id: 1,
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
						id: 2,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ALIAS",
						labelTooltip: "LBL_ALIAS",
						needsTranslation: false,
						parameterized: false,
						position: 2,
						required: true,
						sourceField: "alias",
						visible: true,
						widthPx: 250,
						gridFieldDataType: {
							id: 2,
							name: "int"
						}
					},
					{
						id: 3,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "",
						labelTooltip: "",
						needsTranslation: false,
						parameterized: false,
						position: 3,
						required: true,
						sourceField: "dataTypeId",
						visible: false,
						widthPx: 1,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						id: 4,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_DATATYPE",
						labelTooltip: "LBL_DATATYPE",
						needsTranslation: false,
						parameterized: false,
						position: 4,
						required: true,
						sourceField: "dataType",
						visible: false,
						widthPx: 100,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						id: 5,
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
					},
					{
						id: 6,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_VALUE",
						labelTooltip: "LBL_VALUE",
						needsTranslation: false,
						parameterized: false,
						position: 6,
						required: true,
						sourceField: "value",
						visible: true,
						widthPx: 300,
						adjust: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					}
				]
			};
			
			metadata.gridCommands = [];
				
			metadata.gridCommands.push(
				{
					id: 1,
					default_: true,
					icon: "mdi mdi-pencil",
					label: "LBL_EDIT",
					multiselect: false,
					position: 1,
					showText: true,
					itemRequired: true
				}
			);
			
			let data = [];
			
			data = taskType.taskTypeParams.map(taskTypeParam => {
				
				let incidentTypeTaskValue = undefined;
				
				if (Array.isArray(self.incidentTypeTask.incidentTypeTaskValues) && self.incidentTypeTask.incidentTypeTaskValues.length > 0) {
					
					incidentTypeTaskValue = self.incidentTypeTask.incidentTypeTaskValues.find(e => e.taskTypeParam.id === taskTypeParam.id);
				}
				
				return {
					id: taskTypeParam.id,
					alias: i18n.t(taskTypeParam.labelAlias),
					dataTypeId: taskTypeParam.dataType.id,
					dataType: i18n.t(taskTypeParam.dataType.labelAlias),
					paramTypeId: taskTypeParam.paramType.id,
					value: incidentTypeTaskValue ? incidentTypeTaskValue.value: undefined
				}
			});

			let events = {};
		
			events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
				
			// NEW TASK
			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 1,
				function: () => {
					
					let selectedItems = self.dtTaskTypeParams.getSelectedItems();

					if (Array.isArray(selectedItems) && selectedItems.length === 1) {
						
						editTaskParam(selectedItems[0]);	
					}
				}
			});
			
			self.dtTaskTypeParams = new DataTable(null, null, metadata, data, events);
			
			function editTaskParam(taskParam) {
				
				(new InputDialogBox(i18n.t("LBL_DIALOG_EDIT_TASK_PARAM"), i18n.t("LBL_DIALOG_EDIT_TASK_PARAM_TEXT"), onAccept, false, taskParam.value, { id: taskParam.dataTypeId })).show();
				
				function onAccept(value) {
					
					let newTaskParam = webix.copy(taskParam);
					
					newTaskParam.value = value;
					
					self.dtTaskTypeParams.update(newTaskParam);
				}
			}
			
			return self.dtTaskTypeParams.getView();
		}
	}
}	
