import PlanLibraryOrder from "./planLibraryOrder.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";

const PLAN_ORDER_PARAM_TYPE_LOOP_TIMES = "PlanOrderTypeParam:8:1";

export default class PlanLibraryLoopOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder, onUpdate) {
		
		super(config, uid, plan, planOrder, onUpdate);
		
		this.planOrderValueLoopTimes = undefined;
		if(this.planOrder && this.planOrder.planOrderValues && Array.isArray(this.planOrder.planOrderValues) && this.planOrder.planOrderValues.length > 0){

			this.planOrderValueLoopTimes = webix.copy(this.planOrder.planOrderValues.find(e => e.planOrderTypeParam.id == PLAN_ORDER_PARAM_TYPE_LOOP_TIMES));

		} else if(this.planOrder) {
			
			this.planOrderValueLoopTimes = {
				id: "PlanOrderValue" + this.plan.id.split(":")[1] + this.planOrder.id.split(":")[1] + "8:1",
				planOrderTypeParam : { id: PLAN_ORDER_PARAM_TYPE_LOOP_TIMES, dataType: {id: DataType.INTEGER} }
			}
		}
		
		this.nextPlanOrder = undefined;
		if(planOrder && planOrder.nextPlanOrder){

			this.nextPlanOrder = webix.copy(planOrder.nextPlanOrder);
		}
	}
	
	loadData() {
		
		//
	}
	
	getContent() {
		
		let self = this;		
		let viewParams = {};
		
		viewParams = {
		
			rows: [
				{
					cols: [
						{ width : 30},
						{
							rows: [
								getLoopTimesRow(),
								getLoopOrderRow(),
								{}
							]
						}
					]	
				},
				{ height: 30 }
			]
		}

		function getLoopTimesRow() {

			let loopTimesRow;

			loopTimesRow = {
				id: "loop-times-row-" + self.uid,
				cols: [
					{ view: "text", value: self.planOrderValueLoopTimes.value, required: true, label: i18n.t("LBL_PLAN_ORDER_TYPE_PARAM_NUMBER_OF_TIMES"), disabled: true, labelWidth: 275 },
					{
						view: "icon", icon: "mdi mdi-pencil", disabled: false,
						on: {
							onItemClick: function() {

								getWindowLoopTimesSelected();
							}
						}
					},
					{ width: 90 }
				]
			};

			return loopTimesRow;
		}

		function getLoopOrderRow() {

			let loopOrderRow;

			loopOrderRow = {
				id: "loop-order-row-" + self.uid,
				cols: [
					{ view: "text", value: (self.nextPlanOrder?self.nextPlanOrder.alias:""), required: true, label: i18n.t("LBL_PLAN_ORDER"), disabled: true, labelWidth: 275 },
					{
						view: "icon", icon: "wxi-search", disabled: false,
						on: {
							onItemClick: function() {

								getWindowLoopOrderSelected();
							}
						}
					},
					{ width: 90 }
				]
			};

			return loopOrderRow;
		}

		function getWindowLoopTimesSelected() {
				
			let i = new InputDialogBox(i18n.t("LBL_PLAN_ORDER_TYPE_PARAM_NUMBER_OF_TIMES"), i18n.t("LBL_ENTER_VALUE"), onAccept, true, self.planOrderValueLoopTimes.value, self.planOrderValueLoopTimes.planOrderTypeParam.dataType);

			i.show();

			function onAccept(inputValue) {

				self.planOrderValueLoopTimes.value = inputValue;

				webix.ui(getLoopTimesRow(), $$("loop-times-row-" + self.uid));
			}
		}

		function getWindowLoopOrderSelected() {
	
			let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);

			dialogEntity.data = [];
			
			dialogEntity.pkField = "id"
			dialogEntity.fields = ["order=LBL_PLAN_ORDER", "planOrderType=LBL_PLAN_ORDER_TYPE"];
			dialogEntity.height = 600;
			dialogEntity.width = 800;
			dialogEntity.sortFieldId = 2;
			
			if (Array.isArray(self.plan.planOrders) && self.plan.planOrders.length > 1) {
					
				self.plan.planOrders.forEach(item => {
				
					if(item.id != self.planOrder.id){

						dialogEntity.data.push({
							id: item.id,
							order: item.alias,
							planOrderType: i18n.t(item.planOrderType.labelAlias)
						});
					}
				});
			}
				
			let s = new SearchDialogBox(i18n.t("LBL_SELECT_PLAN_ORDER"), dialogEntity, onAccept);

			s.show();

			function onAccept(selectedIds) {

				self.nextPlanOrder = self.plan.planOrders.find(e => e.id == selectedIds[0].id);
				
				webix.ui(getLoopOrderRow(), $$("loop-order-row-" + self.uid));
			}
		}
		
		return viewParams;
	}

	createCustomPlanOrder(newPlanOrder) {
		
		if (newPlanOrder) {
			
			let planOrderType = window.em.getByField(EntityType.PLAN_ORDER_TYPE, "id", newPlanOrder.planOrderTypeId);
				
			if (planOrderType && Array.isArray(planOrderType.planOrderTypeParams)) {
				
				let planOrderValues = planOrderType.planOrderTypeParams.map(e => {
					
					return {

						planOrderTypeParamId: e.id,
						value: "" 
					}
				});
				
				newPlanOrder.planOrderValues = planOrderValues;
			}
		}
	}

	validateBeforeSavePlanOrder() {

		let self = this;		

		let validate = true;

		if(!self.planOrderValueLoopTimes || !self.planOrderValueLoopTimes.value
			|| !self.nextPlanOrder || !self.nextPlanOrder.id){

			validate = false;
		}

		return validate;
	}
	
	getPlanOrder() {
		
		let self = this;		
		let planOrder;
		
		if (self.planOrder) {
			
			planOrder = {
				
				id: self.planOrder.id,
				planOrderTypeId: self.planOrder.planOrderType.id,
				planOrderValues: [
					{
						id: self.planOrderValueLoopTimes.id,
						planOrderTypeParamId: self.planOrderValueLoopTimes.planOrderTypeParam.id,
						value: self.planOrderValueLoopTimes.value
					}
				],
				nextPlanOrderId: self.nextPlanOrder.id
			}
		}
		
		return planOrder;
	}
}	
