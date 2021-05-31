import GenericModule from "../genericModule.js";
import DataTable from "../../component/datatable.js";
import ConfirmTempDialogBox from "../../component/confirmTempDialogBox.js";
import { adapter } from "../../lib/adapter.js";

import { util } from "../../lib/util.js";

const PLAN_STATE_TYPE_ID_0 = "PlanStateType:0";
const PLAN_STATE_TYPE_ID_1 = "PlanStateType:1";
const PLAN_STATE_TYPE_ID_2 = "PlanStateType:2";
const PLAN_STATE_TYPE_ID_3 = "PlanStateType:3";
const PLAN_STATE_TYPE_ID_4 = "PlanStateType:4";
const PLAN_STATE_TYPE_ID_5 = "PlanStateType:5";
const PLAN_STATE_TYPE_ID_6 = "PlanStateType:6";

const PLAN_ORDER_STATE_TYPE_ID_1 = "PlanOrderStateType:1";
const PLAN_ORDER_ACTION_PLAY = "PLAY";
const PLAN_ORDER_ACTION_STOP = "STOP";

export default class PlanExecutor extends GenericModule {


	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.planId = args.selectedPks[0];
		this.data = null;
		this.orders = null;
		this.config = module.config;
		this.ordersDataTable;
		this.idStompSubscriptor;
	}

	getView() {

		let view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: false
								},
								{
									id: "loading-" + this.uid,
									rows: [this.getViewDrawNoData()],
									hidden: true
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};
		return view;
	}

	loadData() {

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.plan, { id: this.planId })).then((data) => {

			if (data != null) {

				this.data = data.json();

				this.orders = eval("adapter." + this.config.adapters.orders)(this.data.planOrders, null);

				this.drawComponents();
				
				this.connectWS();
	
				$$(this.uid).attachEvent("onDestruct", function() {

					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

					if (stompSubscriptorService) {

						stompSubscriptorService.unsubscribe(this.idStompSubscriptor);
					}
				});
			}
		});

		
	}

	connectWS() {

		let self = this;
		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

			let fields = undefined;
			
			if (this.planId) {
				
				fields = [];

				fields.push({
					target: "id",
					values: [this.planId]
				});
								
				this.idStompSubscriptor = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(this.config.topic), onMessage, fields);
			}
		}

		function onMessage(stompData) {

			if (stompData) {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.plan, { id: self.planId })).then((data) => {

					let updateData = data.json();

					//Update header plan state
					$$("label-state-" + self.uid).setValue(self.getPlanStateTemplate(updateData.planStateType));
					
					//Update buttons enable/disable
					self.config.actionButtons.forEach(btnConfig => {

						if(self.config.planStateTransitions[updateData.planStateType.id].includes(btnConfig.action)){

							$$("BTN-"+btnConfig.action+"-"+this.uid).enable();

						} else {

							$$("BTN-"+btnConfig.action+"-"+this.uid).disable();
						}
					});

					//Update orders table
					self.data.orders.forEach(order => {

						let updateOrder = updateData.orders.find(e => e.id === order.id);

						if(updateOrder.planOrderStateType != order.planOrderStateType){

							updateOrder.orderExuctedAt = new Date();
							order = eval("adapter." + self.config.adapters.orders)([updateOrder], null);
							self.ordersDataTable.update(order);

							if(self.config.confirmOrderTypes.includes(order.orderTypeId) && planOrderStateType.id == PLAN_ORDER_STATE_TYPE_ID_1){

								let confirmWindow = new ConfirmTempDialogBox( 
									i18n.t("LBL_PLAN_CONFIRM_PLAN"), 
									i18n.t("LBL_PLAN_CONFIRM_PLAN_DESCRIPTION"), 
									30, 
									callBackAccept, 
									callBackCancel, 
									null);

								function callBackAccept() {

									let btnConfig = self.config.actionButtons.find(e => e.action == PLAN_ORDER_ACTION_PLAY);
									btnConfig.confirm = false;
									
									self.open_module(btnConfig);
								}
			
								function callBackCancel() {
									
									let btnConfig = self.config.actionButtons.find(e => e.action == PLAN_ORDER_ACTION_STOP);
									btnConfig.confirm = false;
									
									self.open_module(btnConfig);
								}		
								confirmWindow.show();
							}
						}
					});
				});
			}
		}
	}

	drawComponents() {

		$$("loading-" + this.uid).show();

		let viewMain = {
			id: this.uid,
			// view : "form",
			rows: [
				this.getHeader(),
				this.getActionButtons(),
				this.getBody()
			]
		};

		$$("loading-" + this.uid).hide();

		webix.ui(viewMain, $$(this.uid));

	}
		
	getHeader() {

		let header = {};

		header = {

			id: "viewHeader-" + this.uid,
			rows:[
				{
					cols: [
						{
							view: "label",
							label: "<img src='../img/grid/plan.png' class='grid-icon'/>",
							height: 75,
							css: "userrole-header-icon"
						},
						{
							view: "label",
							height: 75,
							width: 420,
							id: "label-boddy-" + this.uid,
							template: "<span>" + this.data.planSubtype.planType.alias + " > " + this.data.planSubtype.alias + " > " + this.data.alias + "</span> <br> <span style='font-size: 13px'>" + this.data.description + "</span>",
							css: "userrole-header-label"
						},
						{
							view: "label",
							height: 75,
							id: "label-state-" + this.uid,
							template: this.getPlanStateTemplate(this.data.planStateType),
							css: "userrole-header-label"
						}
					]
				}
			]
		};

		return header;
	};

	getActionButtons(){

		let buttonsGrid = {
			id: "btns-" + this.uid,
			cols: [
			]
		};

		this.config.actionButtons.forEach(button => {

			buttonsGrid.cols.push( this.getButton(button) );
		});
		
		buttonsGrid.cols.push({});

		return buttonsGrid;
	}


	getBody() {

		let dataTable = {
			id: "dt-" + this.uid,
			height: 391,
			css: "user-form",
			rows: [
				{
					cols: [
						{
							rows: this.getTable()
						}
					]
				},
			]
		};

		return dataTable;
	}

	getTable() {

		let data = this.orders;

		const defaultGridOptionValues = [
			{ gridOption: { id: 10 }, value: "false" },
			// { gridOption: { id: 3 }, value: 1 },
			// { gridOption: { id: 4 }, value: "asc" },
			{ gridOption: { id: 5 }, value: undefined },
			{ gridOption: { id: 6 }, value: undefined },
			{ gridOption: { id: 7 }, value: "false" },
			{ gridOption: { id: 8 }, value: "false" },
			{ gridOption: { id: 9 }, value: "true" },
			{ gridOption: { id: 16 }, value: "img/grid/default.png" },
			{ gridOption: { id: 18 }, value: "{'fieldId': '102502', 'value': 'PlanOrderStateType:2', 'backcolor': '00FF00'} }"},
			{ gridOption: { id: 21 }, value: "custom" },
			{ gridOption: { id: 23 }, value: "false" }
		];

		let metadata = {
			filter: true,
			label: "LBL_PLAN_ORDER_PROGRESS",
			visible: true,
			gridOptionValues: defaultGridOptionValues,
			gridFields: [
				{
					id: 102501,
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: true,
					needsTranslation: false,
					parameterized: false,
					position: 1,
					required: true,
					sourceField: "id",
					visible: false,
					widthPx: 1,
					gridFieldDataType: {
						id: 1,
						name: "string"
					}
				},
				{
					id: 102502,
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_STATUS",
					labelTooltip: "LBL_STATUS",
					needsTranslation: false,
					parameterized: false,
					position: 2,
					required: false,
					sourceField: "orderStatusId",
					visible: true,
					widthPx: 60,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},
					gridFieldIcons: [
						{
							icon: "./img/plan/right-arrow.png",
							showValue: false,
							value: "PlanOrderStateType:1"
						},
						{
							icon: "./img/plan/check.png",
							showValue: false,
							value: "PlanOrderStateType:2"
						},
						{
							icon: "./img/plan/error.png",
							showValue: false,
							value: "PlanOrderStateType:3"
						}
					]
				},
				{
					id: 102503,
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					needsTranslation: false,
					parameterized: false,
					position: 3,
					required: true,
					sourceField: "orderTypeId",
					visible: true,
					widthPx: 40,
					// fillspace: true,
					adjust: false,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},
					gridFieldIcons: [
						{
							icon: "./img/grid/confirmed.png",
							showValue: false,
							value: "PlanOrderType:1"
						},
						{
							icon: "./img/grid/not_confirmed.png",
							showValue: false,
							value: null
						}]
				},
				{
					id: 102504,
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_PLAN_TYPE",
					labelTooltip: "LBL_PLAN_TYPE",
					needsTranslation: false,
					parameterized: false,
					position: 4,
					required: true,
					sourceField: "orderTypeLabelAlias",
					visible: true,
					widthPx: 160,
					// fillspace: true,
					adjust: false,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},
					gridFieldIcons: []
				},
				{
					id: 102505,
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_NAME",
					labelTooltip: "LBL_NAME",
					needsTranslation: false,
					parameterized: false,
					position: 5,
					required: true,
					sourceField: "orderAlias",
					visible: true,
					widthPx: 200,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},
					gridFieldIcons: []
				},
				{
					id: 102506,
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_RESULT",
					labelTooltip: "LBL_RESULT",
					needsTranslation: true,
					parameterized: false,
					position: 6,
					required: true,
					sourceField: "orderResult",
					visible: true,
					widthPx: 100,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},
					gridFieldIcons: []
				},
				{
					id: 102507,
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_EXECUTED_AT",
					labelTooltip: "LBL_EXECUTED_AT",
					needsTranslation: false,
					parameterized: false,
					position: 7,
					required: true,
					sourceField: "orderExuctedAt",
					visible: true,
					widthPx: 100,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},
					gridFieldIcons: []
				}
			],
			gridCommands: [
				
			]

		};

		this.ordersDataTable = new DataTable(null, null, metadata, data, null);

		return this.ordersDataTable.getView();
	};

	getViewDrawNoData() {

		return {
			view: "template",
			template: function() {

				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
	};

	getButton(btnConfig){
		
		let self = this;
		
		let button = {
			id: "BTN-"+btnConfig.action+"-"+this.uid ,
			view: "button",
			type: "icon",
			width: 80,
			on: {
                onItemClick: () => {

					if (btnConfig.moduleAction) {
                        this.open_module(btnConfig);		
                    }
                }
            },
			autowidth : true
		};

		button.label = i18n.t(btnConfig.label);
		button.tooltip = i18n.t(btnConfig.label);
		button.icon = btnConfig.icon;
		button.disabled = btnConfig.disabled;
		button.css = btnConfig.css;
		
		return button;
	}

	open_module(btnConfig) {
        
        let module = findModuleByIdAction(btnConfig.moduleAction.id);
        let args = {};
        args.selectedPks = [this.planId];
		args.planAction = btnConfig.action;
		args.confirm = btnConfig.confirm;

        util.module.open(module, btnConfig.moduleAction, null, null, args);
	}

	getPlanStateTemplate(planOrderStateType) {

		let color;
		let planStateTemplate = "";

		if(planOrderStateType){

			if(planOrderStateType.id == PLAN_STATE_TYPE_ID_0){

				color = "#989898";
	
			} else if(planOrderStateType.id == PLAN_STATE_TYPE_ID_1){
	
				color = "#FF2222";
	
			} else if(planOrderStateType.id == PLAN_STATE_TYPE_ID_2){
	
				color = "#EEFF41";
	
			} else if(planOrderStateType.id == PLAN_STATE_TYPE_ID_3){
	
				color = "#11B900";
	
			} else if(planOrderStateType.id == PLAN_STATE_TYPE_ID_4){
	
				color = "#FF8927";
	
			} else if(planOrderStateType.id == PLAN_STATE_TYPE_ID_5){
	
				color = "#6495ed";
	
			} else if(planOrderStateType.id == PLAN_STATE_TYPE_ID_6){
	
				color = "#000000";
	
			}
			
			if (color){
	
				planStateTemplate = '<div><span class="table-badge" style="background:' + color + '3b'+'; color: '+ color + 'a0'+';">'+ i18n.t(planOrderStateType.labelAlias) + '</span></div>';
			}
		}
     	
		return planStateTemplate;
	}

}
