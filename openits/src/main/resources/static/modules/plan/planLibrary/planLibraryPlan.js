import { util } from "../../../lib/util.js";
import PlanLibraryPersist from "./planLibraryPersist.js";
import DataTable from "../../../component/datatable.js";
import SearchDialogBox from "../../../component/searchDialogBox.js";
import MessageBox from "../../../component/messageBox.js";

const MOVE_PLAN_ORDER_POSITION_UP = 1;
const MOVE_PLAN_ORDER_POSITION_DOWN = 2;

export default class PlanLibraryPlan {

	constructor(config, uid, plan, selectedPlanOrder, onSelectPlanOrder, onUpdate) {
		
		this.config = config;
		this.plan = plan;
		this.uid = uid;
		
		this.planTable = undefined;
		this.selectedPlanOrder = selectedPlanOrder;
		this.onSelectPlanOrder = onSelectPlanOrder;
		this.onUpdate = onUpdate;
		this.planLibraryPersist = undefined;
	}
	
	getView() {
		
		let self = this;
		
		let view = {
			id: "plan-lib-plan-" + self.uid,
			//height: 250,
			rows: drawPlan()
		};
		
		view.rows.push({
			view: "template",
			height: 1,
			on: {
				onAfterRender: function() {
	
					if (self.planTable) {
						
						self.planTable.callbacks();
						
						webix.delay(function() {
	
							if (self.selectedPlanOrder) {
								
								self.planTable.select(self.selectedPlanOrder.id);
							}
							
						}, null, null, 100);
						
						
						
						self.planLibraryPersist = new PlanLibraryPersist(self.config);
					}
				}
			}
		})
		
		function drawPlan() {
			
			const defaultGridOptionValues = [
				{ gridOption: { id: 2 }, value: "false" },
				{ gridOption: { id: 3 }, value: "5" },
				{ gridOption: { id: 4 }, value: "asc" },
				{ gridOption: { id: 5 }, value: undefined },
				{ gridOption: { id: 6 }, value: undefined },
				{ gridOption: { id: 7 }, value: "true" },
				{ gridOption: { id: 9 }, value: "false" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" },
				{ gridOption: { id: 21 }, value: "custom" },
				{ gridOption: { id: 23 }, value: "true" }
			];
			
			function getMetadata() {
				
				let metadata;
				let editPermission = util.module.isAllowedPermission(self.config.requiredPermissions.edit);
				let executePermission = util.module.isAllowedPermission(self.config.requiredPermissions.signalling);

				metadata = {
					filter: true,
					label: "LBL_PLAN_ORDERS",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
							id: 1,
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
											value: e.id,
											labelValue: e.labelAlias
										}							
									});
								} 
								
								return gridFieldIcons;
								
							}(),
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
							widthPx: 200,
							adjust: false,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						},
						{
							id: 4,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ORDER",
							labelTooltip: "LBL_PLAN_ORDER",
							parameterized: false,
							position: 4,
							required: true,
							sourceField: "alias",
							visible: true,
							widthPx: 300,
							adjust: false,
							gridFieldDataType: {
								id: 2,
								name: "int"
							}
						},
						{
							id: 5,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_POSITION",
							labelTooltip: "LBL_POSITION",
							parameterized: false,
							position: 5,
							required: true,
							sourceField: "position",
							visible: true,
							widthPx: 150,
							adjust: true,
							gridFieldDataType: {
								id: 2,
								name: "int"
							}
						}
					],
					gridCommands: []
				};

				if (editPermission) {

					metadata.gridCommands.push(
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


				}

				if (executePermission) {

					metadata.gridCommands.push({
						id: 5,
						default_: false,
						icon: "mdi mdi-play",
						label: "LBL_EXECUTE",
						multiselect: true,
						position: 5,
						showText: true,
						itemRequired: false
					});
				}

				return metadata;
			}
			function getEvents() {
				
				let events = {};
				
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
				
				events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

					if (Array.isArray(rows) && rows.length === 1) {
						
						let planOrderId = rows[0];
						self.onSelectPlanOrder(planOrderId);
						
						self.selectedPlanOrder = self.plan.planOrders.find(e => e.id == planOrderId);
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
	
				// MOVE UP SELECTED ORDER
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
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
				
				// DELETE SELECT ORDERS
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 4,
					function: (selectedIds) => {
	
						// TODO: Confirm and call to delete rest endpoint
						if (Array.isArray(selectedIds) && selectedIds.length > 0) {
							
							deletePlanOrders(selectedIds);	
						}
					}
				});
				
				// EXECUTE PLAN
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 5,
					function: () => {
	
						executePlan();
					}
				});
				
				
				return events;
			}
			
			function getData() {
				
				let data = [];
				
				if (Array.isArray(self.plan.planOrders) && self.plan.planOrders.length > 0) {
					
					self.plan.planOrders.sort((a, b) => (a.planOrderOrder > b.planOrderOrder) ? 1 : ((b.planOrderOrder > a.planOrderOrder) ? -1 : 0));
					
					data = self.plan.planOrders.map(e => {
						
						return {
							
							id: e.id,
							position: e.planOrderOrder,
							orderTypeId: e.planOrderType.id,
							orderType: i18n.t(e.planOrderType.labelAlias),
							alias: e.alias
						}
					})
				} 
				
				return data;
			}

			self.planTable = new DataTable(null, null, getMetadata(), getData(), getEvents());
			
			return self.planTable.getView();
		}
		
		function getNextPosition() {
			
			let position = 0;
			
			if (self.plan && Array.isArray(self.plan.planOrders)) {
				
				self.plan.planOrders.forEach(planOrder => {
					
					position = planOrder.planOrderOrder > position ? planOrder.planOrderOrder : position;  
				});
			}
			
			return ++position;
		}
		
		function newPlanOrder() {
			
			let dialogPlanOrderTypes = webix.copy(SearchDialogBox.CALLBACK);
			let label = i18n.t("LBL_PLAN_ORDER_TYPES");
			let planOrderTypes;
			
			dialogPlanOrderTypes.fillDialogEntity = fillDialogEntity;
			dialogPlanOrderTypes.height = 600;
			dialogPlanOrderTypes.width = 800;
				
			function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
			
				planOrderTypes = window.em.getAll(EntityType.PLAN_ORDER_TYPE);
		
				if (Array.isArray(planOrderTypes) && planOrderTypes.length > 0) {
					
					dialogEntity.data = [];
	
					dialogEntity.pkField = "id"
					dialogEntity.fields = ["idIcon=", "alias=LBL_PLAN_ORDER_TYPE_ALIAS", "description=LBL_PLAN_ORDER_TYPE_DESCRIPTION"];
					dialogEntity.sortFieldId = 2; 
					
					dialogEntity.icons = {
						idIcon: []
					}
					
					planOrderTypes.forEach(planOrderType => {
						
						dialogEntity.icons.idIcon.push({
							id: planOrderType.id,
							icon: planOrderType.icon
						});
						
						dialogEntity.data.push({
							id: planOrderType.id,
							idIcon: planOrderType.id,
							alias: i18n.t(planOrderType.labelAlias),
							description: i18n.t(planOrderType.labelDescription),
							jsFile: planOrderType.jsFile
						});
					});
				}
				
				onFilledDialogEntity(dialogEntity);		
			}
			
			let s = new SearchDialogBox(label, dialogPlanOrderTypes, function(items) {

				if (Array.isArray(items) && items.length === 1) {

					let planOrderType = planOrderTypes.find(e => e.id == items[0].id);

					if (planOrderType) {

						try {

							const lazy = async _ => {
								
								let jsFile = util.getContextPath() + "/" + planOrderType.jsFile;	
		
								const mod = await import(jsFile);
								
								let planLibraryOrder = new mod.default(self.config, self.uid, self.plan, undefined, self.onUpdate);
								
								let planOrderToCreate = {
									planOrderType: webix.copy(planOrderType),
									planOrderOrder: getNextPosition()
								}
								
								planLibraryOrder.createPlanOrder(planOrderToCreate);
							}
			
							lazy();
			
						} catch (error) {
			
							console.log(error);
						}
					}
				}
			});

			s.show();
		}
		
		function deletePlanOrders(planOrderIds) {
		
			if (Array.isArray(planOrderIds) && planOrderIds.length > 0) {
				
				let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_DIALOG_DELETE_SELECT_ORDERS_DESCRIPTION"), onAccept, null);
				ms.show();
				
				function onAccept() {
					
					let deleteOrders = 0;
				
					planOrderIds.forEach(planOrderId => {
					
						self.planLibraryPersist.deletePlanOrder(planOrderId).then(function(response) {
						
							if (++deleteOrders == planOrderIds.length) {
								
								self.onUpdate(self.plan);
							}
						});
					});					
				};
			}
		}		
		
		function movePlanOrder(planOrderId, movePlanOrderType) {
			
			let planOrders = webix.copy(self.plan.planOrders);

			if (Array.isArray(planOrders) && planOrders.length > 0) {
				
				planOrders.sort((a, b) => (a.planOrderOrder > b.planOrderOrder) ? 1 : ((b.planOrderOrder > a.planOrderOrder) ? -1 : 0));
				
				let idxSelectedPlanOrder = planOrders.findIndex(e => e.id === planOrderId);
				
				if (idxSelectedPlanOrder > -1) {
					
					let selectedPlanOrder = planOrders[idxSelectedPlanOrder];
					let previousPlanOrder = (idxSelectedPlanOrder > 0) ? planOrders[idxSelectedPlanOrder - 1] : undefined;
					let nextPlanOrder = idxSelectedPlanOrder < (planOrders.length -1) ? planOrders[idxSelectedPlanOrder + 1] : undefined; 
					
					let selectedPlanOrderUpdated = undefined;
					let otherPlanOrderUpdated = undefined;
					
					let allowMoveUp = (movePlanOrderType === MOVE_PLAN_ORDER_POSITION_UP && previousPlanOrder);
					let allowMoveDown = (movePlanOrderType === MOVE_PLAN_ORDER_POSITION_DOWN && nextPlanOrder);
					
					if (allowMoveUp || allowMoveDown) {
						
						let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_DIALOG_MOVE_SELECTED_ORDER_DESCRIPTION"), onAccept, null);
						ms.show();
						
						function onAccept() {
							
							if (movePlanOrderType === MOVE_PLAN_ORDER_POSITION_UP && previousPlanOrder) {
						
								selectedPlanOrderUpdated = {
									
									id: selectedPlanOrder.id,
									planOrderOrder: previousPlanOrder.planOrderOrder,
									planOrderTypeId: selectedPlanOrder.planOrderType.id
								}
								
								otherPlanOrderUpdated = {
									
									id: previousPlanOrder.id,
									planOrderOrder: selectedPlanOrder.planOrderOrder,
									planOrderTypeId: previousPlanOrder.planOrderType.id
								}
							} 	
							else if (movePlanOrderType === MOVE_PLAN_ORDER_POSITION_DOWN && nextPlanOrder) {
								
								selectedPlanOrderUpdated = {
									
									id: selectedPlanOrder.id,
									planOrderOrder: nextPlanOrder.planOrderOrder,
									planOrderTypeId: selectedPlanOrder.planOrderType.id
								}
								
								otherPlanOrderUpdated = {
									
									id: nextPlanOrder.id,
									planOrderOrder: selectedPlanOrder.planOrderOrder,
									planOrderTypeId: nextPlanOrder.planOrderType.id
								}
							}
							
							if (selectedPlanOrderUpdated && otherPlanOrderUpdated) {
								
								self.planLibraryPersist.updatePlanOrder(selectedPlanOrderUpdated)
								.then(function(updatedSelPlanOrder) {
									
									if (updatedSelPlanOrder) {
										
										self.planLibraryPersist.updatePlanOrder(otherPlanOrderUpdated)
										.then(function(updatedOtherPlanOrder) {
										
											if (updatedOtherPlanOrder) {
												
												self.onUpdate(self.plan, updatedSelPlanOrder);	
											}
											else {
												
												let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_ERROR_UPDATING_PLAN_ORDER_DESCRIPTION"), null, null);
												ms.show();
											}
										});	
									}
									else {
										
										let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_ERROR_UPDATING_PLAN_ORDER_DESCRIPTION"), null, null);
										ms.show();
									}
								});
							}							
						};
						
					} else {
						
						let ms = new MessageBox (MessageBox.WARN, i18n.t("LBL_CAN_NOT_MOVE_SELECTED_ORDER_DESCRIPTION"), null, null);
						ms.show();
					}
				} 
			}
		}
		
		function executePlan() {
			
			let module = util.module.getFromModuleAction(self.config.moduleAction.planExecutor);
				
			if (module) {

				let moduleAction = { id: self.config.moduleAction.planExecutor };
				let args = { selectedPks: [self.plan.id] };

				util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
			}
		}
		
		return view;
	}
	
	update(plan) {
		
		let self = this;
		
		if (self.planLibraryPersist) {
			
			self.planLibraryPersist.updatePlan(plan).then(function(response) {
					
				self.onUpdate(plan);
			});	
		}
	}
}	
