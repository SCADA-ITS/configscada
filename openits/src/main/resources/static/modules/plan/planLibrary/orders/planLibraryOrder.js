import { util } from "../../../../lib/util.js";
import PlanLibraryPersist from "../planLibraryPersist.js";

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
		
		let view = {
			id: "plan-order-lib-plan-" + self.uid,
			rows: [
				drawHeader(),
				{ height: 10 },
				self.getContent(),
				drawActions()
			] 
		};
		
		function drawHeader() {
			
			let viewHeader = {
				cols: [
					{ width: 10 },
					{
						rows: [
							{
								cols: [
									{
										view: "label",
										label: "<img src='" + self.planOrder.planOrderType.icon + "' class='grid-icon'/><span class='grid-title'>" + i18n.t(self.planOrder.planOrderType.labelAlias) + " - " + self.planOrder.alias + "</span>",
										autowidth: true
									},
									{ width: 5 },
									{
										view: "icon",
										icon: "mdi mdi-pencil",
										tooltip: i18n.t("LBL_EDIT_PLAN_ORDER_NAME"),
										on: {
											onItemClick: function() {
												
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
			
			return viewHeader;
		}
		
		function drawActions() {
			
			let viewActions = {};
			
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
		
								webix.confirm({
									title: i18n.t("LBL_SAVE_ORDER_CHANGES"),
									ok: i18n.t("LBL_YES"),
									cancel: i18n.t("LBL_NO"),
									text: i18n.t("LBL_SAVE_ORDER_CHANGES_DESCRIPTION"),
									width: 600
								}).then(function() {
									
									savePlanOrder();
								});					
							}
						}
					}
				]
			};		

			
			function savePlanOrder() {
				
				let planLibraryPersist = new PlanLibraryPersist(self.config);
				let newPlanOrder = self.getPlanOrder();
				
				if (newPlanOrder) {
					
					planLibraryPersist.updatePlanOrder(newPlanOrder).then(result => {
						
						self.onUpdate(self.plan, newPlanOrder);	
					});
				}
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
}	
