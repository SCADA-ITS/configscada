import PlanLibraryPlan from "./planLibraryPlan.js";
//import PlanLibraryOrder from "./planLibraryOrder.js";
import { util } from "../../../lib/util.js";

export default class PlanLibraryContent {

	constructor(config, uid, plan) {
		
		this.config = config;
		this.plan = plan;
		this.uid = uid;
		
		this.planLibraryPlan = undefined;
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
			
			let viewHeader = {
				cols: [
					{ width: 10 },
					{
						rows: [
							{ height: 10 },
							{
								cols: [
									{
										view: "label",
										label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-file-multiple"></i></div>`,
										align: "center",
										width: 30
									},
									{ width: 5 },
									{
										view: "label",
										css: "alarm-view-header-details-text",
										label: function() {
											
											let label = self.plan.alias;
											 
											return `<span class="alarm-view-header-details-text">${label}</span>`;
										}(),
										align: "left",
									},
									{},
									{
										id: "tbMain-" + self.uid,
										height: 39,
										css: {"margin-left":"2px !important"},
										cols: [
											{ 
												view: "button", 
												type: "icon", 
												icon: "mdi mdi-play-circle", 
												css: "toolbar-button-text", 
												width: TOOLBAR_BUTTONS_WIDTH, 
												label: i18n.t("LBL_EXECUTE"), 
												tooltip: i18n.t("LBL_EXECUTE_PLAN_DESCRIPTION"),
												on: {
													onItemClick() {
														
														let module = util.module.getFromModuleAction(self.config.moduleAction.planExecutor);
				
														if (module) {
									
															let moduleAction = { id: self.config.moduleAction.planExecutor };
															let args = { selectedPks: [self.plan.id] };
									
															util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
														}
													}
												} 
											}
										]
									},
									
								]
							},
							{ height: 5 },
							{
								height: 40,
								cols: [
									{ width: 40 },
									{
										borderless: true,
										view: "template",
										template: `<div class="ventilation-description-box"><span > ${self.plan.description}</span></div>`		
									}
								]
							}
						]
					},
					{ width: 10 }
				]
				
			};
			
			return viewHeader;
		}
		
		function drawPlan() {
			
			let viewPlan = {};
			
			viewPlan = {
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
				
				self.planLibraryPlan = new PlanLibraryPlan(self.config, self.uid, self.plan, onSelectPlanOrder);
				
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
								
								let planLibraryOrder = new mod.default(self.config, self.uid, self.plan, planOrder);

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
			
			let viewOrder = {
				cols: [
					{ width: 10 },
					{
						rows: [
							{ height: 10 },
							{ id: "plan-order-lib-plan-" + self.uid, },
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
