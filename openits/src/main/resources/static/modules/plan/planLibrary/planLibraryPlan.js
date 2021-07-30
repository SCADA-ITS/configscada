import DataTable from "../../../component/datatable.js";
import { util } from "../../../lib/util.js";

const MOVE_PLAN_ORDER_POSITION_UP = 1;
const MOVE_PLAN_ORDER_POSITION_DOWN = 2;

export default class PlanLibraryPlan {

	constructor(config, uid, plan, onSelectPlanOrder) {
		
		this.config = config;
		this.plan = plan;
		this.uid = uid;
		
		this.planTable = undefined;
		this.selectedPlanOrder = undefined;
		this.onSelectPlanOrder = onSelectPlanOrder;
	}
	
	getView() {
		
		let self = this;
		
		let view = {
			id: "plan-lib-plan-" + self.uid,
			rows: drawPlan()
		};
		
		view.rows.push({
			view: "template",
			height: 1,
			on: {
				onAfterRender: function() {
	
					if (self.planTable) {
						
						self.planTable.callbacks();
					}
				}
			}
		})
		
		function drawPlan() {
			
			let viewPlan = {};
			
			const defaultGridOptionValues = [
				{ gridOption: { id: 5 }, value: undefined },
				{ gridOption: { id: 6 }, value: undefined },
				{ gridOption: { id: 7 }, value: "true" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 9 }, value: "true" },
				{ gridOption: { id: 10 }, value: "true" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" },
				{ gridOption: { id: 21 }, value: "custom" },
				{ gridOption: { id: 23 }, value: "true" }
			];
			
			function getMetadata() {
				
				let metadata = {
					filter: true,
					label: "LBL_PLAN_ORDERS",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: true,
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
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "orderTypeId",
							visible: true,
							widthPx: 40,
							adjust: false,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: function() {
								
								let gridFieldIcons = [];
								let planOrderTypes = window.em.getAll(EntityType.PLAN_ORDER_TYPE);
								
								if (Array.isArray(planOrderTypes) && planOrderTypes.length > 0) {
									
									 gridFieldIcons = planOrderTypes.map(e => {
								
										return {
											
											icon: e.icon,
											showValue: false,
											value: e.id
										}							
									});
								} 
								
								return gridFieldIcons;
								
							}(),
							
//							gridFieldIcons: [
//								{
//									icon: "./img/plan/PlanOrderType_1.png",
//									showValue: false,
//									value: "PlanOrderType:1"
//								},
//								{
//									icon: "./img/plan/PlanOrderType_2.png",
//									showValue: false,
//									value: "PlanOrderType:2"
//								},
//								{
//									icon: "./img/plan/PlanOrderType_3.png",
//									showValue: false,
//									value: "PlanOrderType:3"
//								},
//								{
//									icon: "./img/plan/PlanOrderType_4.png",
//									showValue: false,
//									value: "PlanOrderType:4"
//								},
//								{
//									icon: "./img/plan/PlanOrderType_5.png",
//									showValue: false,
//									value: "PlanOrderType:5"
//								},
//								{
//									icon: "./img/plan/PlanOrderType_6.png",
//									showValue: false,
//									value: "PlanOrderType:6"
//								},
//								{
//									icon: "./img/plan/PlanOrderType_7.png",
//									showValue: false,
//									value: "PlanOrderType:7"
//								},
//								{
//									icon: "./img/plan/PlanOrderType_8.png",
//									showValue: false,
//									value: "PlanOrderType:8"
//								}
//							]
						},
						{
							id: 3,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_PLAN_ORDER_TYPE",
							labelTooltip: "LBL_PLAN_ORDER_TYPE",
							needsTranslation: false,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "orderType",
							visible: true,
							widthPx: 400,
							adjust: false,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_POSITION",
							labelTooltip: "LBL_POSITION",
							parameterized: false,
							position: 4,
							required: true,
							sourceField: "position",
							visible: true,
							widthPx: 150,
							adjust: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}
					],
					gridCommands: [
						{
							id: 1,
							default_: false,
							icon: "mdi mdi-file-plus-outline",
							label: "LBL_NEW",
							multiselect: false,
							position: 1,
							showText: false,
							itemRequired: false
						},
						{
							id: 2,
							default_: false,
							icon: "mdi mdi-delete-outline",
							label: "LBL_DELETE",
							multiselect: true,
							position: 2,
							showText: false,
							itemRequired: true
						},
						{
							id: 3,
							default_: false,
							icon: "mdi mdi-arrow-up-bold",
							label: "LBL_MOVE_UP",
							multiselect: false,
							position: 3,
							showText: false,
							itemRequired: true
						},
						{
							id: 4,
							default_: false,
							icon: "mdi mdi-arrow-down-bold",
							label: "LBL_MOVE_DOWN",
							multiselect: false,
							position: 4,
							showText: false,
							itemRequired: true
						}
					]
				};
				
				return metadata;
			}
			function getEvents() {
				
				let events = {};
				
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
				
				events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

					if (Array.isArray(rows) && rows.length === 1) {
						
						self.onSelectPlanOrder(rows[0]);
					}
				};
	
				// NEW ORDER
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: () => {
	
						// TODO: Use SearchDialogBox component to show List of available plan orders
						newPlanOrder();
					}
				});
	
				// DELETE SELECT ORDERS
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: (selectedIds) => {
	
						// TODO: Confirm and call to delete rest endpoint
						if (Array.isArray(selectedIds) && selectedIds.length > 0) {
							
							deletePlanOrders(selectedIds);	
						}
					}
				});
	
				// MOVE UP SELECTED ORDER
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 3,
					function: (selectedIds) => {
	
						// TODO: Move up selected order
						if (Array.isArray(selectedIds) && selectedIds.length === 1) {
							
							movePlanOrder(selectedIds[0], MOVE_PLAN_ORDER_POSITION_UP);
						} 
					}
				});
				
				// MOVE DOWN SELECTED ORDER
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 3,
					function: (selectedIds) => {
	
						// TODO: Move up selected order
						if (Array.isArray(selectedIds) && selectedIds.length === 1) {
							
							movePlanOrder(selectedIds[0], MOVE_PLAN_ORDER_POSITION_DOWN);
						} 
					}
				});
				
				return events;
			}
			
			function getData() {
				
				let data = [];
				
				if (Array.isArray(self.plan.planOrders) && self.plan.planOrders.length > 0) {
					
					self.plan.planOrders.sort((a, b) => (a.planOrderOrder > b.planOrderOrder) ? 1 : ((b.planOrderOrder > a.planOrderOrder) ? -1 : 0));
					
					data = self.plan.planOrders.map(e => {
						
						let planOrder = {
							
							id: e.id,
							position: e.planOrderOrder,
							orderTypeId: e.planOrderType.id,
							orderType: i18n.t(e.planOrderType.labelAlias)
						}
						
						return planOrder;
					})
				} 
				
				return data;
			}

			self.planTable = new DataTable(null, null, getMetadata(), getData(), getEvents());
			
			return self.planTable.getView();
		}
		
		// TODO: Pending implementation
		function newPlanOrder() {
			
		}
		
		// TODO: Pending implementation
		function deletePlanOrders(planOrderIds) {
		
			if (Array.isArray(planOrderIds) && planOrderIds.length > 0) {
				
				
			}
		}		
		
		// TODO: Pending implementation
		function movePlanOrder(planOrderId, movePlanOrder) {
			
		}
		
		return view;
	}
}	
