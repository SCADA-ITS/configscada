import { util } from "../../../../lib/util.js";
import DataTable from "../../../../component/datatable.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";

const STRETCH_PANEL_WIDTH = 300;
const MOVE_TASK_PLAN_ORDER_POSITION_UP = 1;
const MOVE_TASK_PLAN_ORDER_POSITION_DOWN = 2;

export default class IncidentTypeTaskDetailPlan {

	constructor(config, uid, incidentType, incidentTypeTask, onUpdate) {
			
		this.config = config;
		this.uid = uid;
		this.incidentType = incidentType;
		this.incidentTypeTask = incidentTypeTask;
		this.onUpdate = onUpdate;
		
		this.dtStretchs = undefined;
		this.dtTaskPlans = undefined;
	}
	
	draw() {
		
		let self = this;
		
		let viewTaskPlans = {
			id: "incident-type-state-task-detail-content-plans-" + self.uid,
			css: "bordered-layout",
			cols: [
				getViewDatatableStretchs(),
				{
					id: "incident-type-state-task-detail-content-plans-table-" + self.uid,
					rows: [
						
						util.ui.getViewDrawLoading()
					]
				}
			]
		}
		
		webix.ui(viewTaskPlans, $$(viewTaskPlans.id));
		
		self.dtStretchs.callbacks();
		
		self.dtStretchs.selectByIndex(0);
		
		function getViewDatatableStretchs() {

			const defaultGridOptionValues = [
				{ gridOption: { id: 1 }, value: "false" },
				{ gridOption: { id: 2 }, value: "false" },
				{ gridOption: { id: 3 }, value: "1" },
				{ gridOption: { id: 4 }, value: "asc" },
				{ gridOption: { id: 7 }, value: "false" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 9 }, value: "true" },
				{ gridOption: { id: 10 }, value: "false" },
				{ gridOption: { id: 11 }, value: "false" },
				{ gridOption: { id: 12 }, value: "false" },
				{ gridOption: { id: 13 }, value: "false" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" },
				{ gridOption: { id: 21 }, value: "custom" },
				{ gridOption: { id: 23 }, value: "false" },
				{ gridOption: { id: 24 }, value: "false" }
			];

			let metadata = {
				filter: true,
				label: "LBL_STRETCHS",
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
							
			let data = [];

			if (Array.isArray(self.incidentType.incidentTypeStretchs) && self.incidentType.incidentTypeStretchs.length > 0) {

				data = self.incidentType.incidentTypeStretchs.map(e => {
					
					return {
						id: e.stretch.id,
						alias: e.stretch.alias
						
					}	
				});
				
				data.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
			}

			let events = {};
			
			events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

				if (Array.isArray(rows) && rows.length === 1) {
					
					onSelectStretch(rows[0]);
				}
					
			};

			self.dtStretchs = new DataTable(null, null, metadata, data, events);
				
			let viewDatatableStretchs = {
				id: "incident-type-state-task-detail-content-plans-stretchs-" + self.uid,
				width: STRETCH_PANEL_WIDTH,
				rows: [
					{
						view: "template",
						borderless: true,
						height: 38,
						css: { "background-color": "var(--blue-soft-opacity-1)", "font-size": "14px", "font-weight": "lighter", "color": "var(--blue-text-default)" },
						template: function() {
							
							return `<div style="padding-top: 10px; padding-left: 10px">
										<span class='ico webix_icon mdi mdi-road'></span>
										<span>${i18n.t("LBL_STRETCHS")}</span>
									</div>`;
						}
					}
				]
			}
			
			viewDatatableStretchs.rows.push(...self.dtStretchs.getView());
			
			function onSelectStretch(stretchId) {
				
				let requestUrl = appConfig.restUrl + 
								 util.fillTemplate(self.config.restEndpoints.incidentTypeTaskPlans, { 
									stretchId: stretchId,
									incidentTypeId: self.incidentType.id,
									incidentTypeTaskId: self.incidentTypeTask.id
								});
				
				webix.ajax().get(requestUrl).then(function(incidentTypeTaskPlansResponse) {
					
					if (incidentTypeTaskPlansResponse) {
						
						let incidentTypeTaskPlans = incidentTypeTaskPlansResponse.json();
						
						drawTaskPlans(incidentTypeTaskPlans) 
					}
				});
			}
			
			return viewDatatableStretchs;
		}
		
		function drawTaskPlans(incidentTypeTaskPlans) {
				
			const defaultGridOptionValues = [
				{ gridOption: { id: 1 }, value: "true" },
				{ gridOption: { id: 2 }, value: "false" },
				{ gridOption: { id: 3 }, value: 2 },
				{ gridOption: { id: 4 }, value: "asc" },
				{ gridOption: { id: 5 }, value: undefined },
				{ gridOption: { id: 6 }, value: undefined },
				{ gridOption: { id: 7 }, value: "false" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 9 }, value: "true" },
				{ gridOption: { id: 10 }, value: "true" },
				{ gridOption: { id: 11 }, value: "true" },
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
				label: "LBL_TASK_PLANS",
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
						widthPx: 150,
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
						sourceField: "planId",
						visible: false,
						widthPx: 1,
						gridFieldDataType: {
							id: 2,
							name: "string"
						}
					},
					{
						id: 4,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_PLAN",
						labelTooltip: "LBL_PLAN",
						needsTranslation: false,
						parameterized: false,
						position: 4,
						required: true,
						sourceField: "plan",
						visible: true,
						adjust: true,
						widthPx: 250,
						gridFieldDataType: {
							id: 2,
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
					multiselect: true,
					position: 4,
					showText: false,
					itemRequired: true
				}
			);
			
			let data = [];
			
			if (Array.isArray(incidentTypeTaskPlans) && incidentTypeTaskPlans.length > 0) {
				
				data = incidentTypeTaskPlans.map(incidentTypeTaskPlan => {
				
					return {
						id: incidentTypeTaskPlan.id,
						position: incidentTypeTaskPlan.position,
						planId: incidentTypeTaskPlan.plan.id,
						plan: incidentTypeTaskPlan.plan.alias
					}
				});	
			}

			let events = {};
		
			events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
				
			// ADD
			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 1,
				function: (selectedIds) => {

					addTaskPlan();
				}
			});
			
			// MOVE UP SELECTED STATE
			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 2,
				function: (selectedIds) => {

					if (Array.isArray(selectedIds) && selectedIds.length === 1) {
						
						moveTaskPlanOrder(selectedIds[0], MOVE_TASK_PLAN_ORDER_POSITION_UP);
					} 
				}
			});
			
			// MOVE DOWN SELECTED STATE
			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 3,
				function: (selectedIds) => {

					if (Array.isArray(selectedIds) && selectedIds.length === 1) {
						
						moveTaskPlanOrder(selectedIds[0], MOVE_TASK_PLAN_ORDER_POSITION_DOWN);
					} 
				}
			});
			
			// REMOVE
			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 4,
				function: (selectedIds) => {

					removeTaskPlans(selectedIds);
				}
			});
			
			self.dtTaskPlans = new DataTable(null, null, metadata, data, events);
			
			webix.ui(self.dtTaskPlans.getView(), $$("incident-type-state-task-detail-content-plans-table-" + self.uid));
			
			self.dtTaskPlans.callbacks();
			
			function addTaskPlan() {
				
				let dialogPlanEntity = webix.copy(SearchDialogBox.CALLBACK);				
				dialogPlanEntity.fillDialogEntity = fillDialogEntity;
				dialogPlanEntity.dataType = SearchDialogBox.DIALOG_DATA_TYPE_TREE;
				dialogPlanEntity.height = 600;
				dialogPlanEntity.width = 800;
				dialogPlanEntity.data = {};
				
				function fillDialogEntity(dialogEntity, onFilledDialogEntity) {

					let promises = [];

					promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.planNodes));
					promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.planHierarchies));
					promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.plans));

					webix.promise.all(promises).then(function(responses) {

						if (Array.isArray(responses) && responses.length === 3) {
							
							let itemsTree = [];
							let itemRelationships = [];

							let nodes = (responses[0] && responses[0].json()) ? responses[0].json() : [];
							let relationships = responses[1] && responses[1].json() ? responses[1].json() : [];
							let plans = responses[2] && responses[2].json() ? responses[2].json() : [];

							if (Array.isArray(nodes) && nodes.length > 0) {

								itemsTree = nodes.map(e => {

									let plan = plans.find(p => (e.plan && p.id === e.plan.id));

									return {

										id: e.id,
										label: plan ? plan.alias : e.alias,
										plan: e.plan,
										folder: !e.plan
									}
								});
							}

							if (Array.isArray(relationships) && relationships.length > 0) {

								itemRelationships = relationships.map(e => {

									return {

										id: e.id,
										nodeParentId: e.parent.id,
										nodeChildId: e.child.id
									}
								});
							}

							dialogEntity.data.nodes = itemsTree;
							dialogEntity.data.relationships = itemRelationships;
							dialogEntity.data.nodeParentId = "nodeParentId";
							dialogEntity.data.nodeChildId = "nodeChildId";
							
							onFilledDialogEntity(dialogEntity);
						}
					});					
				}
				
				
				let s = new SearchDialogBox(i18n.t("LBL_SELECT_PLAN"), dialogPlanEntity, onAccept);
				s.show();
				
				function onAccept(selection) {

					if (Array.isArray(selection) && selection.length > 0) {
						
						let newPlan = {
							planId: selection[0].id,
							plan: selection[0].alias,
							position: getNextPosition()
						}
						
						self.dtTaskPlans.add(newPlan);
						
						updateTaskPlanPositions();
					}				
				}
			}
			
			function getNextPosition() {
			
				let position = 0;
				
				if (Array.isArray(self.dtTaskPlans.getAllItems())) {
					
					self.dtTaskPlans.getAllItems().forEach(item => {
						
						position = item.position > position ? item.position : position;  
					});
				}
				
				return ++position;
			}
			
			function updateTaskPlanPositions() {
				
				let taskPlans = webix.copy(self.dtTaskPlans.getAllItems());
				
				if (Array.isArray(taskPlans)) {
					
					let position = 1;
					taskPlans.forEach(taskPlan => {
						
						taskPlan.position = position++; 
					});
					
					self.dtTaskPlans.update(taskPlans);
				}
			}
			
			function moveTaskPlanOrder(selectedId, moveTaskPlanOrderType) {
				
				let taskPlans = webix.copy(self.dtTaskPlans.getAllItems());
				let taskPlan = taskPlans.find(e => e.id === selectedId);
				
				if (taskPlan) {
					
					let currentPosition = taskPlan.position; 
					let newPosition = undefined;
				
					if (currentPosition > 0 && moveTaskPlanOrderType === MOVE_TASK_PLAN_ORDER_POSITION_DOWN) {
						
						if (currentPosition + 1 <= taskPlans.length) {
							
							newPosition = currentPosition + 1;	
						}
					}
					else if (currentPosition > 1 && moveTaskPlanOrderType === MOVE_TASK_PLAN_ORDER_POSITION_UP) {
						
						newPosition = currentPosition -1;
					}
					
					if (newPosition) {
						
						let taskPlanAffected = taskPlans.find(e => e.position === newPosition);
						
						taskPlanAffected.position = currentPosition;
						taskPlan.position = newPosition;
						
						self.dtTaskPlans.update([taskPlan, taskPlanAffected]);
					}	
				}
			}
			
			function removeTaskPlans(selectedIds) {
				
				if (Array.isArray(selectedIds)) {
					
					self.dtTaskPlans.delete(self.dtTaskPlans.getAllItems().filter(e => selectedIds.includes(e.id)));
					
					updateTaskPlanPositions();
				}
			}
		}
	}
}	
