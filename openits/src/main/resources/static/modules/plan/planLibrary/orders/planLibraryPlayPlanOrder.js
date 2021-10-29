import PlanLibraryOrder from "./planLibraryOrder.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";

export default class PlanLibraryChangeParamConfigOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder, onUpdate) {
		
		super(config, uid, plan, planOrder, onUpdate);
		
		this.nextPlan = undefined;
		if(planOrder && this.planOrder.nextPlan){
			this.nextPlan = webix.copy(this.planOrder.nextPlan);
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
								getNextPlayPlanRow(),
								{}
							]
						}
					]	
				},
				{ height: 30 }
			]

		}

		function getNextPlayPlanRow() {

			let nextPlayPlanRow;

			nextPlayPlanRow = {
				id: "next-plan-row-" + self.uid,
				cols: [
					{ view: "text", value: (self.nextPlan?self.nextPlan.alias:""), required: true, label: i18n.t("LBL_PLAN"), disabled: true, labelWidth: 275 },
					{
						view: "icon", icon: "wxi-search", disabled: false,
						on: {
							onItemClick: function() {

								getWindowPlayPlanSelected();
							}
						}
					},
					{ width: 90 }
				]
			};

			return nextPlayPlanRow;
		}

		function getWindowPlayPlanSelected() {
				
			let s = new SearchDialogBox(i18n.t("LBL_SELECT_PLAN"), SearchDialogBox.PLANS, onAccept);

			s.show();

			function onAccept(selectedIds) {

				self.nextPlan = selectedIds[0];

				webix.ui(getNextPlayPlanRow(), $$("next-plan-row-" + self.uid));
			}
		}
		
		return viewParams;
	}

	createCustomPlanOrder(newPlanOrder) {

	}
	
	validateBeforeSavePlanOrder() {

		let self = this;		

		let validate = true;

		if(!self.nextPlan || !self.nextPlan.id){
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
				nextPlanId: self.nextPlan.id
			}
		}
		
		return planOrder;
	}
}	
