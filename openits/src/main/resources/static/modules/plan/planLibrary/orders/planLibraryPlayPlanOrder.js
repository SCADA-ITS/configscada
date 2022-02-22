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
					{ view: "text", value: (self.nextPlan.alias) ? self.nextPlan.alias : "", required: true, label: i18n.t("LBL_PLAN"), disabled: true, labelWidth: 275 },
					{
						view: "icon", icon: "wxi-search", disabled: false,
						on: {
							onItemClick: function() {

								getWindowPlayPlanSelected();
							}
						}
					},
					{}
				]
			};

			return nextPlayPlanRow;
		}

		function getWindowPlayPlanSelected() {
			
				let dialogEntity = webix.copy(SearchDialogBox.PLANS);				
				dialogEntity.fillDialogEntity = fillDialogEntity;
				dialogEntity.data = {};
				
				function fillDialogEntity(dialogEntity, onFilledDialogEntity) {

					let promises = [];

					promises.push(webix.ajax().get(appConfig.restUrl + "plans/nodes"));
					promises.push(webix.ajax().get(appConfig.restUrl + "plans/hierarchies"));
					promises.push(webix.ajax().get(appConfig.restUrl + "plans"));

					webix.promise.all(promises).then(function(responses) {

						if (responses != null) {
							
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
				
			let s = new SearchDialogBox(i18n.t("LBL_SELECT_PLAN"), dialogEntity, onAccept);
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
