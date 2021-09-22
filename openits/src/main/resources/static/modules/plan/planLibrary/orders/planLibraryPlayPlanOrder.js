import PlanLibraryOrder from "./planLibraryOrder.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import { util } from "../../../../lib/util.js";

export default class PlanLibraryChangeParamConfigOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder, onUpdate) {
		
		super(config, uid, plan, planOrder, onUpdate);		
		this.nextPlan = undefined;
	}
	
	loadData() {
		
	}
	
	getContent() {
		
		let self = this;		
		let viewParams = {};
		
		viewParams = {
		
			rows: [
				{
					cols: [
						{
							rows: getNextPlayPlanRow()
						}
					]	
				},
				{ height: 30 }
			]
		}	
		
		function getNextPlayPlanRow() {
			
			let nextPlayPlanLabel = "<span class='grid-title'>  " + i18n.t("LBL_NEXT_PLAN_PLAY_ORDER") + "</span>";

			if(self.elementTypeParam && self.elementTypeParam.id != null){

				nextPlayPlanLabel = "<span class='grid-title'>" + self.nextPlanId + "</span>"
			}

			let nextPlayPlanRow = {
				id: "next-plan-row-" + self.uid,
				cols: [
					{ width: 10 },
					{
						rows: [
							{
								cols: [
									{
										view: "label",
										label: nextPlayPlanLabel,
										autowidth: true
									},
									{ width: 5 },
									{
										view: "icon",
										icon: "mdi mdi-pencil",
										tooltip: i18n.t("LBL_EDIT_NEXT_PLAY_PLAN"),
										on: {
											onItemClick: function() {
												
												getWindowNextPlayPlanSelected();
											}
										}
									},
									{}
								]
							}
						]
					},
					{ width: 10 }
				]
				
			};

			return nextPlayPlanRow;
		}

		function getWindowNextPlayPlanSelected() {
				
			let s = new SearchDialogBox(i18n.t("LBL_SELECT_PLAN"), SearchDialogBox.PLANS, onAccept);

			s.show();

			function onAccept(selectedIds) {

				// self.nextPlanId.plan = selection[0].alias;
				self.nextPlanId = selection[0].id;

				webix.ui(getNextPlayPlanRow(), $$("next-plan-row-" + self.uid));
			}
		}
		
		return viewParams;
	}
	
	getPlanOrder() {
		
		let self = this;		
		let planOrder;
		
		if (self.planOrder) {
			
			planOrder = {
				
				id: self.planOrder.id,
				planOrderTypeId: self.planOrder.planOrderType.id,
				nextPlanId: self.nextPlanId
			}
		}
		
		return planOrder;
	}
}	
