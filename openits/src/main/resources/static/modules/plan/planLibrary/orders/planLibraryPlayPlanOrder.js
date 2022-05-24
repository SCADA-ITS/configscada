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
					{ view: "text", value: (self.nextPlan && self.nextPlan.alias) ? self.nextPlan.alias : "", required: true, label: i18n.t("LBL_PLAN"), disabled: true, labelWidth: 275 },
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
										aux: {
											plan: e.plan
										},
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

			function onAccept(selectedIds) {

				if (Array.isArray(selectedIds) && selectedIds.length > 0 && selectedIds[0].aux && selectedIds[0].aux.plan) {
					
					self.nextPlan = selectedIds[0].aux.plan;
					
					webix.ui(getNextPlayPlanRow(), $$("next-plan-row-" + self.uid)); 
				}
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
