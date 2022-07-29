import { util } from "../../../../lib/util.js";
import PlanLibraryPersist from "../planLibraryPersist.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";
import MessageBox from "../../../../component/messageBox.js";

export default class PlanLibraryOrder {
	
	constructor(config, uid, plan, planOrder, onUpdate) {

		if (this.constructor == PlanLibraryOrder) {
	
			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.config = config;
		this.uid = uid;
		this.plan = plan;
		this.planOrder = planOrder;
		this.onUpdate = onUpdate;
	}
	
	getView() {
		
		let self = this;
		let editPermission = util.module.isAllowedPermission(self.config.requiredPermissions.edit);	

		let view = {
			id: "plan-order-lib-plan-" + self.uid,
			rows: [
				drawHeader(),
				{ height: 10 },
				self.getContent()
//				drawActions()
			] 
		};
		
		function drawHeader() {
			
			let viewHeader;
			let pencilIcon = {};

			if (editPermission) {

				pencilIcon = {
					view: "icon",
					icon: "mdi mdi-pencil",
					tooltip: i18n.t("LBL_EDIT_PLAN_ORDER_NAME"),
					on: {
						onItemClick: function() {

							let planOrderAlias = self.planOrder.alias;

							(new InputDialogBox(i18n.t("LBL_PLAN_ORDER_ALIAS"), i18n.t("LBL_ENTER_NEW_PLAN_ORDER_ALIAS"), function(newPlanOrderAlias) {

								if (planOrderAlias != newPlanOrderAlias) {

									let updatedPlanOrder = {
										id: self.planOrder.id,
										alias: newPlanOrderAlias,
										planOrderTypeId: self.planOrder.planOrderType.id
									}

									self.savePlanOrder(updatedPlanOrder);
								}

							}, true, planOrderAlias)).show();
						}
					}
				}
			}

			viewHeader = {
				cols: [
					{ width: 10 },
					{
						rows: [
							{
								cols: [
									{ 
										view: "label",
										label: "<img src='" + self.planOrder.planOrderType.icon + "' class='grid-icon grid-icon-plan'/><span class='grid-title grid-title-plan'>" + i18n.t(self.planOrder.planOrderType.labelAlias) + " - " + self.planOrder.alias + "</span>",
										autowidth: true
									},
									{ width: 5 },
									pencilIcon,
									{},
									drawActions()
								]
							}
						]
					},
					{ width: 10 }
				]

			};

			return viewHeader;
		}

		function drawActions() {

			let viewActions = { height: 45 };

			if (editPermission) {

				viewActions = {
					view: "toolbar",
					height: 45,
					borderless: true,
					elements: [
						{},
						{
							view: "button",
							css: "action-button",
							width: 130,
							height: 35,
							label: i18n.t("LBL_SAVE"),
							on: {
								onItemClick() {
									
									let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_SAVE_ORDER_CHANGES_DESCRIPTION"), onAccept, null);
									ms.show();
									
									function onAccept() {
									
										self.savePlanOrder();
									};
								}
							}
						}
					]
				};
			}

			return viewActions;
		}
		
		return view;
	}
	
	getContent() {
		
		throw new Error("Method 'getContent()' must be implemented.");
	}
	
	loadData() {
		
		throw new Error("Method 'loadData()' must be implemented.");
	}
	
	getPlanOrder() {
		
		throw new Error("Method 'getPlanOrder()' must be implemented.");
	}
	
	createCustomPlanOrder(newPlanOrder) {
		
		throw new Error("Method 'createCustomPlanOrder()' must be implemented.");
	}
	
	validateBeforeSavePlanOrder() {

		return true;
	}

	createPlanOrder(planOrder) {
		
		let self = this;
		
		if (planOrder && self.plan) {
			
			let planLibraryPersist = new PlanLibraryPersist(self.config);
		
			let newPlanOrder = {
				planId: self.plan.id,
				alias: planOrder.planOrderType.alias,
				description: planOrder.planOrderType.alias,
				planOrderTypeId: planOrder.planOrderType.id,
				planOrderOrder: planOrder.planOrderOrder
			}
			
			self.createCustomPlanOrder(newPlanOrder);
			
			if (newPlanOrder) {
				
				planLibraryPersist.createPlanOrder(newPlanOrder).then(result => {
					
					self.onUpdate(self.plan, result);	
				});
			}	
		}
	}
	
	savePlanOrder(planOrder) {
				
		let self = this;

		if (self.validateBeforeSavePlanOrder()){

			let planLibraryPersist = new PlanLibraryPersist(self.config);
			let newPlanOrder;
			
			if (!planOrder) {
				
				newPlanOrder = self.getPlanOrder();
			}
			else {
				
				newPlanOrder = webix.copy(planOrder);
			}
				
			if (newPlanOrder) {
				
				planLibraryPersist.updatePlanOrder(newPlanOrder).then(result => {
					
					self.onUpdate(self.plan, result);
				});
			}

		} else {

			let ms = new MessageBox (MessageBox.WARN, i18n.t("LBL_PLAN_LIBRARY_REQUIRED_PARAMS"), null, null);
			ms.show();
		}
	}
}	
