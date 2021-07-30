import { util } from "../../lib/util.js";

export default class PlanLibraryContent {

	constructor(config, uid, plan) {
		
		this.config = config;
		this.plan = plan;
		this.uid = uid;
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
										label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-view-headline"></i></div>`,
										align: "center",
										width: 30
									},
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
								
													}
												} 
											}
										]
									},
									
								]
							},
							{ height: 10 },
							{
								height: 40,
								cols: [
									{ width: 50 },
									{
										borderless: true,
										view: "template",
										template: `<div class="ventilation-description-box"><span > ${self.plan.description}</span></div>`		
									}
								]
							},
							{ height: 10 }
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
							{},
							{ height: 10 }
						]
					},
					{ width: 10 }
				]
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
							{},
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
