import PlanLibraryPlan from "./planLibraryPlan.js";
import InputDialogBox from "../../../component/inputDialogBox.js";
import { util } from "../../../lib/util.js";

export default class PlanLibraryContent {

	constructor(config, uid, plan, onUpdate, selectedPlanOrder) {
		
		this.config = config;
		this.plan = plan;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.planLibraryPlan = undefined;
		this.selectedPlanOrder = (selectedPlanOrder && selectedPlanOrder.id) ? selectedPlanOrder : undefined;
		this.currentHeight = undefined;
	}
	
	getView() {
		
		let self = this;
		
		let view = {
			id: "plan-lib-content-" + self.uid,
			css: { "background-color": "white" },
			rows: [
				drawHeader(),
				drawPlan(),
				drawResizer(),
				drawOrder()
			]
			
		};
		
		function drawHeader() {
			
			const TOOLBAR_BUTTONS_WIDTH = 130;
						
			let viewHeader;				
			let editPermission = util.module.isAllowedPermission(self.config.requiredPermissions.edit);	
			let iconPencil = {};

			if (editPermission) {

				iconPencil = {
					view: "icon",
					icon: "mdi mdi-pencil",
					tooltip: i18n.t("LBL_EDIT_PLAN_NAME"),
					on: {

						onItemClick: function() {

							let planAlias = self.plan.alias;

							(new InputDialogBox(i18n.t("LBL_PLAN_NAME"), i18n.t("LBL_ENTER_NEW_PLAN_NAME"), function(newAlias) {

								if (planAlias != newAlias) {

									let updatedPlan = {
										id: self.plan.id,
										alias: newAlias,
										description: self.plan.description,
										planModeType: self.plan.planModeType
									}

									self.planLibraryPlan.update(updatedPlan);
								}

							}, true, planAlias)).show();
						}
					}
				}
			}

			viewHeader = {
				cols: [
					{ width: 10 },
					{
						rows: [
							{ height: 10 },
							{
								cols: [
									{
										view: "label",
										label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-file-outline"></i></div>`,
										align: "center",
										width: 30
									},
									{ width: 5 },
									{
										view: "label",
										css: "view-header-details-text",
										autowidth: true,
										label: function() {									
											 
											return `<span class="view-header-details-text">${self.plan.alias}</span>`;
										}(),
										align: "left",
									},
									iconPencil,
									{}
								]
							},
							{ height: 5 }
						]
					},
					{ width: 10 }
				]
				
			};
			
			return viewHeader;
		}
		
		function drawPlan() {
			
			let planHeight = (self.currentHeight) ? self.currentHeight : 270;
			let viewPlan = {};
			
			viewPlan = {
				id: "plan-" + self.uid,
				height: planHeight,
				cols: [
					{ width: 10 },
					{
						rows: [
							{ height: 10 },
							drawPlanLibraryPlan(),
							{ height: 10 }
						]
					},
					{ width: 10 }
				]
			}
			
			function drawPlanLibraryPlan() {

				if (Array.isArray(self.plan.planOrders) && self.plan.planOrders.length > 0) {

					self.plan.planOrders.sort((a, b) => (a.planOrderOrder > b.planOrderOrder) ? 1 : ((b.planOrderOrder > a.planOrderOrder) ? -1 : 0));

					if (self.selectedPlanOrder) {

						self.selectedPlanOrder = self.plan.planOrders.find(e => e.id === self.selectedPlanOrder.id);

					} else {

						self.selectedPlanOrder = self.plan.planOrders[0];
					}
				}
				
				self.planLibraryPlan = new PlanLibraryPlan(self.config, self.uid, self.plan, self.selectedPlanOrder, onSelectPlanOrder, onUpdate);
				
				return self.planLibraryPlan.getView();
			}
			
			function onSelectPlanOrder(planOrderId) {
				
				if (Array.isArray(self.plan.planOrders) && self.plan.planOrders.length > 0) {
					
					let planOrder = self.plan.planOrders.find(e => e.id === planOrderId);
					
					if (planOrder) {
						
						try {

							const lazy = async _ => {
								
								let jsFile = util.getContextPath() + "/" + planOrder.planOrderType.jsFile;	
		
								const mod = await import(jsFile);
								
								let planLibraryOrder = new mod.default(self.config, self.uid, self.plan, planOrder, onUpdate);

								if (planLibraryOrder && planLibraryOrder.getView) {
				
									webix.ui(planLibraryOrder.getView(), $$("plan-order-lib-plan-" + self.uid));
									
									planLibraryOrder.loadData();
								}
							}
			
							lazy();
			
						} catch (error) {
			
							console.log(error);
						}
					}
				}
				
				return self.planLibraryPlan.getView();
			}
			
			function onUpdate(plan, planOrder) {
				
				if (plan) {
				
					if (!planOrder) {
						
						self.onUpdate(plan);	
					}
					else {
						
						self.currentHeight = $$("plan-" + self.uid).$height;
						$$("plan-lib-loading-" + self.uid).show();
						$$("plan-lib-content-" + self.uid).hide();
							
						webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.plan, { id: plan.id })).then(function(response) {
		
							let planResponse = response ? response.json() : undefined;
		
							if (planResponse) {
		
								self.plan = planResponse;
								
								self.selectedPlanOrder = planOrder;
		
								webix.ui(self.getView(), $$("plan-lib-content-" + self.uid));
								
								$$("plan-lib-loading-" + self.uid).hide();
								$$("plan-lib-content-" + self.uid).show();

							}
						});
					}
				}
			}
			
			return viewPlan;
		}
		
		function drawResizer() {

			let viewResizer = {};

			viewResizer = {
				view: "resizer"
			}

			return viewResizer;
		}
		
		function drawOrder() {
			
			let viewOrder; 
			
			viewOrder = {
				cols: [
					{ width: 10 },
					{
						rows: [
							{ height: 10 },
							{ 
								rows:[{id: "plan-order-lib-plan-" + self.uid}]	
//								view:"scrollview",
//								scroll: "auto",
//								body: {
//									rows:[{id: "plan-order-lib-plan-" + self.uid}]									
//								}
							},
							{ height: 10 }
						]
					},
					{ width: 10 }
				]
			}
			
			return viewOrder;
		}
			
		return view;
	}
}	
