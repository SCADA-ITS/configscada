import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";

export default class IncidentReportTaskPlan {

	constructor(config, taskState, index, uid) {

		this.config = config;
		this.taskState = taskState;
		this.index = index;
		this.uid = uid;
		this.dtPlans = undefined;
	}

	getView() {
		
		let self = this;
		let viewPlans;
		
		viewPlans = {
			id: "task-details-plans-" + self.index + "-" + self.uid,
			rows: [
				{
					view: "template",
					borderless: true,
					height: 20,
					on: {
						onAfterRender: function() {
			
							if (self.dtPlans) {
								
								self.dtPlans.callbacks();
								
								if (self.taskState.completedAt) {
									
									//self.dtPlans.disable();
								}
							}
						}
					}
				}
			]
		};
		
		
		viewPlans.rows.push(...getTablePlans());
				
		function getTablePlans() {
			
			function getMetadata() {
				
				let metadata;
				
				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 2 }, value: "false" },
					{ gridOption: { id: 3 }, value: "2" },
					{ gridOption: { id: 4 }, value: "asc" },
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
					label: "LBL_PLANS",
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
							label: "LBL_POSITION",
							labelTooltip: "LBL_POSITION",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "position",
							visible: true,
							widthPx: 35,
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
							label: "LBL_PLAN",
							labelTooltip: "LBL_PLAN",
							needsTranslation: false,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "plan",
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
							label: "LBL_PLAN_STATUS",
							labelTooltip: "LBL_PLAN_STATUS",
							needsTranslation: false,
							parameterized: false,
							position: 4,
							required: true,
							sourceField: "status",
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
							label: "LBL_CURRENT_PLAN_ORDER",
							labelTooltip: "LBL_CURRENT_PLAN_ORDER",
							needsTranslation: false,
							parameterized: false,
							position: 5,
							required: true,
							sourceField: "currentPlanOrder",
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
							isEpochMilli: true,
							isPk: false,
							label: "LBL_LAST_UPDATE",
							labelTooltip: "LBL_LAST_UPDATE",
							needsTranslation: false,
							parameterized: false,
							position: 6,
							required: true,
							sourceField: "lastUpdate",
							visible: true,
							widthPx: 25,
							gridFieldDataType: {
								id: 4,
								name: "date"
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
							position: 7,
							required: true,
							sourceField: "incidentTaskPlanId",
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
							value: "LBL_EXECUTE_PLAN",
							icon: "mdi mdi-play",
							default_: false,
							label: "LBL_EXECUTE_PLAN",
							showText: true,
							position: 1,
							multiselect: false,
							itemRequired: true
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
					function: onExecutePlan
				});
				
				return events;
			}
			
			function getInitialData() {
				
				let data = [];
				let taskPlanStates = self.taskState.taskPlanStates;

				let position = 1;
				
				if (Array.isArray(taskPlanStates) && taskPlanStates.length) {
	
					taskPlanStates.forEach(taskPlanState => {
						
						let row;
						
						row = {
							id: taskPlanState.incidentTaskPlan.plan.id,
							position: position++,
							plan: taskPlanState.incidentTaskPlan.plan.alias,
							status: i18n.t(taskPlanState.incidentTaskPlan.plan.planStateType.labelAlias),
							currentPlanOrder: taskPlanState.incidentTaskPlan.plan.currentPlanOrder ? taskPlanState.incidentTaskPlan.plan.currentPlanOrder.alias : undefined,
							lastUpdate: taskPlanState.completedAt,
							incidentTaskPlanId: taskPlanState.incidentTaskPlan.id 
						}
						
						data.push(row);
					});
				}
				
				return data;
			}
			
			function onExecutePlan() {
						
				let selectedItems = self.dtPlans.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
					
					let selectedItem = selectedItems[0];
					
					let module = util.module.getFromModuleAction(self.config.moduleAction.executePlanFromIncident);
					
					if (module) {
						
						let moduleAction = { id: self.config.moduleAction.executePlanFromIncident };
						let args = { selectedPks: [selectedItem.id] };

						util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);	
					}
				}
			}

			self.dtPlans = new DataTable(null, null, getMetadata(), getInitialData(), getEvents());
			
			return self.dtPlans.getView();
		}
		
		return viewPlans;
	}
}