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
const PLAN_ORDER_ACTION_CANCEL = "CANCEL";

const CONFIRM_WINDOW_SECONDS = 30;

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
									rows: [getViewDrawNoData()],
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

		function getViewDrawNoData() {

			return {
				view: "template",
				template: function() {
	
					let icon = "<img src='img/loading.svg' align='center'>";
	
					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
				}
			}
		};

		return view;
	}

	loadData() {

		let self = this;

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.plan, { id: self.planId })).then((data) => {

			if (data != null) {

				self.data = data.json();

				self.orders = eval("adapter." + self.config.adapters.orders)(self.data.planOrders, null);

				drawComponents();
				connectWS();
	
				$$(self.uid).attachEvent("onDestruct", function() {

					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

					if (stompSubscriptorService) {

						stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
					}
				});
			}
		});

		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				let fields = undefined;
				
				if (self.planId) {
					
					fields = [];
	
					fields.push({
						target: "id",
						values: [self.planId]
					});
									
					self.idStompSubscriptor = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.config.topic), onMessage, fields);
				}
			}
	
			function onMessage(stompData) {
	
				if (stompData) {
	
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.plan, { id: self.planId })).then((data) => {
	
						let updateData = data.json();
	
						//Update header plan state
						$$("label-state-" + self.uid).setValue(getPlanStateTemplate(updateData.planStateType));
						
						//Update buttons enable/disable
						self.config.actionButtons.forEach(btnConfig => {
	
							if(btnConfig.visible){
								
								if(self.config.planStateTypes[updateData.planStateType.id].actions.includes(btnConfig.action)){
	
									$$("BTN-"+btnConfig.action+"-"+self.uid).enable();
		
								} else {
		
									$$("BTN-"+btnConfig.action+"-"+self.uid).disable();
								}
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
										CONFIRM_WINDOW_SECONDS, 
										callBackAccept, 
										callBackCancel, 
										callBackAccept);
	
									function callBackAccept() {
	
										let btnConfig = self.config.actionButtons.find(e => e.action == PLAN_ORDER_ACTION_PLAY);
										btnConfig.confirm = false;
										
										open_module(btnConfig);
									}
				
									function callBackCancel() {
										
										let btnConfig = self.config.actionButtons.find(e => e.action == PLAN_ORDER_ACTION_CANCEL);
										btnConfig.confirm = false;
										
										open_module(btnConfig);
									}		
									confirmWindow.show();
								}
							}
						});
					});
				}
			}
		}

		function drawComponents() {

			$$("loading-" + self.uid).show();
	
			let viewMain = {
				id: self.uid,
				// view : "form",
				rows: [
					getHeader(),
					getActionButtons(),
					getBody()
				]
			};

			function getHeader() {

				let header = {};
		
				header = {
		
					id: "viewHeader-" + self.uid,
					rows:[
						{
							cols: [
								{
									view: "label",
									label: "<img src='../img/grid/plan.png' class='grid-icon'/>",
									height: 75,
									css: "global-header-icon"
								},
								{
									view: "label",
									height: 75,
									width: 420,
									id: "label-boddy-" + self.uid,
									template: "<span>" + self.data.planSubtype.planType.alias + " > " + self.data.planSubtype.alias + " > " + self.data.alias + "</span> <br> <span style='font-size: 13px'>" + self.data.description + "</span>",
									css: "global-header-label"
								},
								{
									view: "label",
									height: 75,
									id: "label-state-" + self.uid,
									template: getPlanStateTemplate(self.data.planStateType),
									css: "global-header-label"
								}
							]
						}
					]
				};
		
				return header;
			};

			function getActionButtons(){

				let buttonsGrid = {
					id: "btns-" + self.uid,
					cols: [
					]
				};
		
				self.config.actionButtons.forEach(button => {
		
					if(button.visible){
					
						buttonsGrid.cols.push( getButton(button) );
					}
				});
	
				function getButton(btnConfig) {
						
					let button = {
						id: "BTN-"+btnConfig.action+"-"+self.uid ,
						view: "button",
						type: "icon",
						width: 80,
						on: {
							onItemClick: () => {
			
								if (btnConfig.moduleAction) {
								
									open_module(btnConfig);		
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
				
				buttonsGrid.cols.push({});
		
				return buttonsGrid;
			}

			function getBody() {

				let dataTable = {
					id: "dt-" + self.uid,
					height: 391,
					css: "global-form",
					rows: [
						{
							cols: [
								{
									rows: getTable()
								}
							]
						},
					]
				};

				function getTable() {

					let data = self.orders;
			
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
			
					self.ordersDataTable = new DataTable(null, null, metadata, data, null);
					let orderDataTableView = self.ordersDataTable.getView();
					// self.ordersDataTable.callbacks();
		
					return orderDataTableView;
				};
		
				return dataTable;
			}
	
			$$("loading-" + self.uid).hide();
	
			webix.ui(viewMain, $$(self.uid));
	
		}
		
		function open_module(btnConfig) {
        
			let module = findModuleByIdAction(btnConfig.moduleAction.id);
			let args = {};
			args.selectedPks = [self.planId];
			args.planAction = btnConfig.action;
			args.confirm = btnConfig.confirm;
	
			util.module.open(module, btnConfig.moduleAction, null, null, args);
		}

		function getPlanStateTemplate(planStateType) {

			let planStateTemplate = "";
	
			if(planStateType){
		
				let color = self.config.planStateTypes[planStateType.id].color;		
				planStateTemplate = '<div><span class="table-badge" style="background:' + color + '3b'+'; color: '+ color + 'a0'+';">'+ i18n.t(planStateType.labelAlias) + '</span></div>';
			}
			 
			return planStateTemplate;
		}

	}

}
