import DataTable from "../../../../component/datatable.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import IncidentTypeTaskDetail from "./incidentTypeTaskDetail.js";
import { util } from "../../../../lib/util.js";

const DEFAULT_INCIDENT_TYPE_STATE_ID = -1;
const MOVE_INCIDENT_TYPE_TASK_UP = 1;
const MOVE_INCIDENT_TYPE_TASK_DOWN = 2;

export default class IncidentTypeStateTask {

	constructor(config, uid, incidentType, incidentTypeStateId, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.incidentTypeStateId = incidentTypeStateId;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.incidentTypeTasks = [];

		this.dtTasks = undefined;
		
		this.lastTabViewId = undefined;
		this.incidentTypeTaskDetail = undefined;
	}
	
	getView() {
		
		let self = this;
		let view;
		
		view = {
			id: "incident-type-state-tasks-" + self.uid,
			cols: [
				{
					id: "incident-type-state-tasks-content-" + self.uid,
					hidden: true,
					rows: [
						getViewTasks(),
						{ view: "resizer", height: 10 },
						getViewTaskDetail()
					]		
				},
				{
					id: "incident-type-state-tasks-loading-" + self.uid,
					hidden: false,
					rows: [
						util.ui.getViewDrawLoading()
					]
				}
			]
		}
		
		function getViewTasks() {
		
			let viewTasks;
			
			viewTasks = {
				
				id: "incident-type-state-task-tasks-" + self.uid,
				rows: getViewDatatableTasks()
			}
			
			
			
			function getViewDatatableTasks() {
					
				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 2 }, value: "false" },
					{ gridOption: { id: 3 }, value: "2" },
					{ gridOption: { id: 4 }, value: "asc" },
					{ gridOption: { id: 5 }, value: undefined },
					{ gridOption: { id: 6 }, value: undefined },
					{ gridOption: { id: 7 }, value: "true" },
					{ gridOption: { id: 9 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "custom" },
					{ gridOption: { id: 23 }, value: "true" }
				];
	
				let metadata = {
					filter: true,
					label: "LBL_TASKS",
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
							label: "LBL_POSITION",
							labelTooltip: "LBL_POSITION",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "position",
							visible: true,
							widthPx: 100,
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
							label: "LBL_TASK_TYPE",
							labelTooltip: "LBL_TASK_TYPE",
							needsTranslation: false,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "taskType",
							visible: true,
							widthPx: 200,
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
							label: "LBL_NAME",
							labelTooltip: "LBL_NAME",
							needsTranslation: false,
							parameterized: false,
							position: 4,
							required: true,
							sourceField: "name",
							visible: true,
							adjust: true,
							widthPx: 300,
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
							labelTooltip: "LBL_AUTOMATISM",
							needsTranslation: false,
							parameterized: false,
							position: 5,
							required: true,
							sourceField: "automatism",
							visible: true,
							widthPx: 45,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: [
								{
									icon: "./img/grid/task_automatism.png",
									showValue: false,
									value: "yes",
									labelValue: "LBL_AUTOMATISM"
								},
								{
									icon: "./img/grid/empty.png",
									showValue: false,
									value: "no",
									labelValue: "LBL_WO_AUTOMATISM"
								}
							]
						},
						{
							id: 6,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "",
							labelTooltip: "LBL_TYPE",
							needsTranslation: false,
							parameterized: false,
							position: 6,
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
									icon: "./img/grid/delayed.png",
									showValue: false,
									value: "delayed",
									labelValue: "LBL_DELAYED"
								},
								{
									icon: "./img/grid/manual.png",
									showValue: false,
									value: "manual",
									labelValue: "LBL_MANUAL"
								}
							]
						},
						{
							id: 7,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_REQUIRED",
							labelTooltip: "LBL_REQUIRED",
							needsTranslation: false,
							parameterized: false,
							position: 7,
							required: true,
							sourceField: "required",
							visible: true,
							widthPx: 45,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: [
								{
									icon: "./img/grid/required.png",
									showValue: false,
									value: "true",
									labelValue: "LBL_REQUIRED"
								},
								{
									icon: "./img/grid/not_confirmed.png",
									showValue: false,
									value: "false",
									labelValue: ""
								}
							]
						},
						{
							id: 8,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_INCIDENT_TASK_TYPE",
							labelTooltip: "LBL_INCIDENT_TASK_TYPE",
							needsTranslation: false,
							parameterized: false,
							position: 8,
							required: true,
							sourceField: "executionType",
							visible: true,
							widthPx: 200,
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
						icon: "mdi mdi-delete",
						label: "LBL_DELETE",
						multiselect: false,
						position: 4,
						showText: false,
						itemRequired: true
					}
				);
				
				let data = [];
	
				let events = {};
			
				events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

					if (Array.isArray(rows) && rows.length > 0) {
					
						onSelectIncidentTypeTask(rows[0]);	
					}
				};
				
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
					
				// NEW TASK
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: () => {
	
						newIncidentTypeTask();
					}
				});
				
				// MOVE UP SELECTED STATE
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: (selectedIds) => {
	
						if (Array.isArray(selectedIds) && selectedIds.length === 1) {
							
							moveIncidentTypeTask(selectedIds[0], MOVE_INCIDENT_TYPE_TASK_UP);
						} 
					}
				});
				
				// MOVE DOWN SELECTED STATE
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 3,
					function: (selectedIds) => {
	
						if (Array.isArray(selectedIds) && selectedIds.length === 1) {
							
							moveIncidentTypeTask(selectedIds[0], MOVE_INCIDENT_TYPE_TASK_DOWN);
						} 
					}
				});
				
				// DELETE SELECT STATE
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 4,
					function: (selectedIds) => {
	
						if (Array.isArray(selectedIds) && selectedIds.length === 1) {
							
							deleteIncidentTypeTask();	
						}
					}
				});
	
				self.dtTasks = new DataTable(null, null, metadata, data, events);
				
				return self.dtTasks.getView();
			}
			
			return viewTasks;	
		}
		
		function onSelectIncidentTypeTask(incidentTypeTaskId) {
				
			if (Array.isArray(self.incidentTypeTasks) && self.incidentTypeTasks.length > 0) {
				
				let incidentTypeTask = self.incidentTypeTasks.find(e => e.id === incidentTypeTaskId);
				
				if (incidentTypeTask) {
					
					self.incidentTypeTaskDetail = new IncidentTypeTaskDetail(self.config, self.uid, self.incidentType, incidentTypeTask, self.onUpdate, self.lastTabViewId, function(lastTabViewId) { self.lastTabViewId = lastTabViewId } );
					
					webix.ui(self.incidentTypeTaskDetail.getView(), $$("incident-type-state-task-detail-" + self.uid));
						
					self.incidentTypeTaskDetail.loadData();
				}
			}
		}
		
		function newIncidentTypeTask() {
			
			let dialogIncidentTypeTaskTypes = webix.copy(SearchDialogBox.CALLBACK);
			let label = i18n.t("LBL_TASK_TYPES");
			let incidentTypeTaskTypes;
			
			dialogIncidentTypeTaskTypes.fillDialogEntity = fillDialogEntity;
			dialogIncidentTypeTaskTypes.height = 600;
			dialogIncidentTypeTaskTypes.width = 800;
				
			function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
			
				incidentTypeTaskTypes = window.em.getAll(EntityType.IMS_TASK_TYPES);
		
				if (Array.isArray(incidentTypeTaskTypes) && incidentTypeTaskTypes.length > 0) {
					
					dialogEntity.data = [];
	
					dialogEntity.pkField = "id"
					dialogEntity.fields = ["idIcon=", "alias=LBL_INCIDENT_TYPE_TASK_TYPE_ALIAS"];
					dialogEntity.sortFieldId = 2; 
					
					dialogEntity.icons = {
						idIcon: []
					}
					
					incidentTypeTaskTypes.forEach(incidentTypeTaskType => {
						
						dialogEntity.icons.idIcon.push({
							id: incidentTypeTaskType.id,
							icon: incidentTypeTaskType.icon
						});
						
						dialogEntity.data.push({
							id: incidentTypeTaskType.id,
							idIcon: incidentTypeTaskType.id,
							alias: i18n.t(incidentTypeTaskType.labelAlias)
						});
					});
				}
				
				onFilledDialogEntity(dialogEntity);		
			}
			
			let s = new SearchDialogBox(label, dialogIncidentTypeTaskTypes, function(items) {

				if (Array.isArray(items) && items.length === 1) {

					let incidentTypeTaskType = incidentTypeTaskTypes.find(e => e.id == items[0].id);

					if (incidentTypeTaskType) {
						
						self.incidentTypeTaskDetail = new IncidentTypeTaskDetail(self.config, self.uid, self.incidentType, undefined, self.onUpdate, self.lastTabViewId, function(lastTabViewId) {self.lastTabViewId = lastTabViewId });
								
						let position = 1;
						
						self.incidentTypeTaskDetail.create(incidentTypeTaskType, position, function() {
							
						});
					}
				}
			});

			s.show();
		}
		
		function moveIncidentTypeTask(incidentTypeTaskId, moveincidentTypeTaskType) {
			
			// TODO
		}
		
		function deleteIncidentTypeTask(incidentTypeTaskIds) {
			
			if (Array.isArray(incidentTypeTaskIds) && incidentTypeTaskIds.length > 0) {
				
				let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_DIALOG_DELETE_SELECT_TASK_TYPES_DESCRIPTION"), onAccept, null);
				ms.show();
				
				function onAccept() {
					
					let deleteTaskTypes = 0;
					
					incidentTypeTaskIds.forEach(incidentTypeTaskId => {
						
						let incidentTypeTask = self.incidentTypeTasks.find(e => e.id === incidentTypeTaskId);
						
						if (incidentTypeTask) {
							
							let incidentTypeTaskDetailToDelete = new IncidentTypeTaskDetail(self.config, self.uid, self.incidentType, incidentTypeTask);
							
							incidentTypeTaskDetailToDelete.delete(function() {
							
								if (++deleteTaskTypes == taskTypeIds.length) {
										
										
								}
							});	
						}
					});					
				}
			}
		}
		
		function getViewTaskDetail() {
			
			let viewTaskDetail;
			
			viewTaskDetail = {
				
				id: "incident-type-state-task-detail-" + self.uid,
			}
			
			return viewTaskDetail;
		}
		
		return view;
	}
	
	loadData(onLoad) {
		
		let self = this;
		let requestUrl;
		
		if (self.incidentTypeStateId == DEFAULT_INCIDENT_TYPE_STATE_ID) {
			
			requestUrl = appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentTypeTasksByIncidentType, { incidentTypeId: self.incidentType.id }); 
		}
		else {
			
			requestUrl = appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentTypeTasksByIncidentTypeAndIncidentState, { incidentTypeId: self.incidentType.id, incidentStateId: self.incidentTypeStateId });
		}
		
		$$("incident-type-state-tasks-loading-" + self.uid).show();
		$$("incident-type-state-tasks-content-" + self.uid).hide();
		
		webix.ajax().get(requestUrl).then(function(response) {
		
			let responseTasks = response ? response.json() : undefined;
			
			if (Array.isArray(responseTasks) && responseTasks.length > 0) {
				
				self.incidentTypeTasks = webix.copy(responseTasks);
				
				if (self.incidentTypeStateId == DEFAULT_INCIDENT_TYPE_STATE_ID) {
					
					self.incidentTypeTasks = self.incidentTypeTasks.filter(e => !e.incidentState);
				}
				
				if (Array.isArray(self.incidentTypeTasks)) {
					
					self.incidentTypeTasks.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
				}
				
				if (self.dtTasks) {
				
					self.dtTasks.callbacks();
					
					updateTasks();	
				}
			}
			
			$$("incident-type-state-tasks-loading-" + self.uid).hide();
			$$("incident-type-state-tasks-content-" + self.uid).show();
			
			if (onLoad) {
				
				onLoad();
			}
		});
		
		function updateTasks() {
			
			if (self.dtTasks) {
					
				self.dtTasks.clear();
				
				if (Array.isArray(self.incidentTypeTasks) && self.incidentTypeTasks.length > 0) {
					
					let position = 0;
					let tasks = self.incidentTypeTasks.map(e => {
						
						let automatism = e.onChange ? "yes" : "no";
						
						let type = "delayed";
						
						if (!e.delay) {
						
							type = e.auto ? "auto" : "manual";	
						}	
					
						let executionType = i18n.t("LBL_INCIDENT_TASK_MANUAL");
	
						if (!e.delay) {
							
							if (e.auto) {
	
								executionType = i18n.t("LBL_INCIDENT_TASK_AUTO");
							}	
						}
						else {
							
							executionType = i18n.t("LBL_INCIDENT_TASK_DELAYED") + " " + e.delay + " " + i18n.t("LBL_INCIDENT_TASK_DELAYED_SECONDS");
						}
	
						return {
							
							id: e.id,
							position: ++position,
							taskType: e.taskType ? i18n.t(e.taskType.labelAlias) : undefined,
							name: e.alias,
							automatism: automatism,
							type: type,
							required: e.mandatory ? "true" : "false",
							executionType: executionType						
						}
					});
					
					tasks.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
					
					self.dtTasks.add(tasks);
					
					self.dtTasks.selectByIndex(0);	
				}
			}
		}
	}
}	
