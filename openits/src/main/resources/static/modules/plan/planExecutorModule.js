import GenericModule from "../genericModule.js";
import DataTable from "../../component/datatable.js";
import ConfirmTempDialogBox from "../../component/confirmTempDialogBox.js";
import { adapter } from "../../lib/adapter.js";

import { util } from "../../lib/util.js";

export default class PlanExecutor extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.planId = args.selectedPks[0];
		this.data = null;
		this.orders = null;
		this.config = module.config;
		this.ordersDataTable = null;
		this.idStompSubscriptor = null;
		this.confirmWindow = undefined;
	}

	getView() {

		let view = {
			css: "custom_window",
			cols: [
				{
					css: { "background-color": "white" },
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
									rows: [getViewDrawLoading()],
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

		function getViewDrawLoading() {

			return {
				view: "template",
				template: function() {

					let icon = "<img src='img/loading.svg' align='center'>";

					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
				}
			}
		}

		return view;
	}

	loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();
		}


		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.plan, { id: self.planId })).then((data) => {

			if (data != null) {

				self.data = data.json();

				self.orders = eval("adapter." + self.config.adapters.orders)(self.data.planOrders, null);

				drawComponents();

				if (self.ordersDataTable) {

					self.ordersDataTable.callbacks();
				}

				connectWS();

				$$(self.uid).attachEvent("onDestruct", function() {

					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

					if (stompSubscriptorService) {

						stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
					}
				});

				if (self.moduleAction.id === self.config.moduleAction.executeTaskPlan) {

					self.config.actionButtons.forEach(btnConfig => {

						if (btnConfig.visible) {

							$$("BTN-" + btnConfig.action + "-" + self.uid).disable();
						}
					});
				}
				else {

					if (self.data.planStateType) {
						
						self.config.actionButtons.forEach(btnConfig => {

							if (btnConfig.visible) {
	
								if (self.config.planStateTypes[self.data.planStateType.id].actions.includes(btnConfig.action)) {
	
									$$("BTN-" + btnConfig.action + "-" + self.uid).enable();
	
								} else {
	
									$$("BTN-" + btnConfig.action + "-" + self.uid).disable();
								}
							}
						});	
					}
				}
			}
		});

		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (self.planId) {

					let topic = self.config.topic + "." + self.planId;
					self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessage);

				}
			}

			function onMessage(stompData) {

				if (stompData) {

					//Update header plan state
					if (stompData.planStateType && stompData.planStateType.id) {

						//Check for opened confirm window 
						if(self.confirmWindow && stompData.planStateType.id != "PlanStateType:5"){

							self.confirmWindow.close();
							self.confirmWindow = undefined;
						}

						let planStateType = window.em.getByField(EntityType.PLAN_STATE_TYPE, "id", stompData.planStateType.id);

						$$("label-state-" + self.uid).setValue(getPlanStateTemplate(planStateType));
						
						if (stompData.changedAt) {
							
							$$("label-changed-" + self.uid).setValue(getPlanChangedTemplate(stompData.changedAt));	
						}
						
						$$("header-line-color" + self.uid).setValue(getHeaderLineTemplate(planStateType));	

						if (self.moduleAction.id != self.config.moduleAction.executeTaskPlan) {

							self.config.actionButtons.forEach(btnConfig => {

								if (btnConfig.visible) {

									if (self.config.planStateTypes[planStateType.id].actions.includes(btnConfig.action)) {

										$$("BTN-" + btnConfig.action + "-" + self.uid).enable();

									} else {

										$$("BTN-" + btnConfig.action + "-" + self.uid).disable();
									}
								}
							});
						}
					}
					//Update buttons enable/disable

					//Update orders table
					let ids = [];

					if (stompData.planOrders && Array.isArray(stompData.planOrders)){

						self.data.planOrders.forEach(order => {

							let updateOrder = stompData.planOrders.find(e => e.id === order.id);
							if (updateOrder && (updateOrder.planOrderStateType.id != order.planOrderStateType.id || !order.orderExecutedAt)) {

								let planOrderStateType = window.em.getByField(EntityType.PLAN_ORDER_STATE_TYPE, "id", updateOrder.planOrderStateType.id);
	
								if (planOrderStateType.id === self.config.planOrderStateTypes.running) {
	
									ids.push(order.id);
								}
	

								order.planOrderStateType = planOrderStateType;
								order.orderStatusId = planOrderStateType.id;
								order.orderResult = i18n.t(planOrderStateType.labelAlias);
	
								if (order.planOrderStateType.id != self.config.planOrderStateTypes.unknown &&
									order.planOrderStateType.id != self.config.planOrderStateTypes.running) {
	
									order.orderExecutedAt = new Date(updateOrder.changedAt);
								}
								else {
									
									order.orderExecutedAt = undefined;
								}
		
								self.ordersDataTable.update(order);
								if (self.config.confirmationOrder.typeId === order.planOrderType.id &&
									order.planOrderStateType.id === self.config.planOrderStateTypes.success && order.orderExecutedAt) {
	
									let waitingTime = order.planOrderValues ? order.planOrderValues.find(e => e.planOrderTypeParam.id === self.config.confirmationOrder.typeParamWaitingTimeId) : undefined;
									let message = order.planOrderValues ? order.planOrderValues.find(e => e.planOrderTypeParam.id === self.config.confirmationOrder.typeParamMessageId) : undefined;
									
									//&& Array.isArray(self.orderConfirm) && !self.orderConfirm.find(e => e == order.id)
									if (waitingTime && message && waitingTime.value && message.value) {
	
										self.confirmWindow = new ConfirmTempDialogBox(
											i18n.t("LBL_PLAN_ORDER_TYPE_CONFIRM"),
											message.value,
											waitingTime.value,
											callBackAccept,
											callBackCancel,
											callBackAccept);
											
	
										function callBackAccept() {
	
											let btnConfig = webix.copy(self.config.actionButtons.find(e => e.action == PlanAction.PLAY));
											btnConfig.confirm = false;
	
											open_module(btnConfig);
										}
	
										function callBackCancel() {
	
											let btnConfig = webix.copy(self.config.actionButtons.find(e => e.action == PlanAction.CANCEL));
											btnConfig.confirm = false;
	
											open_module(btnConfig);
										}
										self.confirmWindow.show();
									}
	
	
								}
							}
						});
					}

					if (ids && ids.length > 0) {

						self.ordersDataTable.select(ids);
					}

					if (stompData.planStateType && stompData.planStateType.id && self.config.resetTimer.includes(stompData.planStateType.id)) {
						self.data.planOrders.forEach(order => {

							order.orderExecutedAt = undefined;
						});
					}
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
					getBody(),
					getCancelButton()
				]
			};

			function getHeader() {

				let header = {};

				header = {

					id: "viewHeader-" + self.uid,
					rows: [
						{
							view: "label",
							id: "header-line-color" + self.uid,
							height: 15,
							template: getHeaderLineTemplate(self.data.planStateType)
						},
						{
							cols: [
								{
									view: "label",
									label: "<img src='./img/grid/plan.png' class='grid-icon'/>",
									height: 75,
									css: "global-header-icon"
								},
								{
									view: "label",
									height: 75,
									width: 420,
									id: "label-boddy-" + self.uid,
									template: "<span>" + self.data.alias + "</span> <br> <span style='font-size: 13px'>" + self.data.description + "</span>",
									css: "global-header-label"
								}
							]
						}
					]
				};

				return header;
			}

			function getActionButtons() {

				let buttonsGrid = {
					id: "btns-" + self.uid,
					css: { "margin-left": "2px !important" },
					cols: [
					]
				};

				self.config.actionButtons.forEach(button => {

					if (button.visible) {

						buttonsGrid.cols.push(getButton(button));
					}
				});

				function getButton(btnConfig) {

					let button = {
						id: "BTN-" + btnConfig.action + "-" + self.uid,
						view: "button",
						type: "icon",
						css: "toolbar-button-text",
						width: 130,
						on: {
							onItemClick: () => {


								if (btnConfig.moduleAction) {

									open_module(btnConfig);
								}
							}
						},
						//autowidth: true
					};

					button.label = i18n.t(btnConfig.label);
					button.tooltip = i18n.t(btnConfig.label);
					button.icon = btnConfig.icon;
					button.disabled = btnConfig.disabled;

					return button;
				}

				buttonsGrid.cols.push(
					{ width: 1 },
					{
						view: "label",
						align: "right",
						id: "label-changed-" + self.uid,
						template: getPlanChangedTemplate(self.data.changedAt),
						css: { "padding-top": "11px" }
					},
					{
						view: "label",
						width: 150,
						align: "right",
						id: "label-state-" + self.uid,
						template: getPlanStateTemplate(self.data.planStateType),
						css: { "padding-top": "11px" }
					},
					{ width: 10 });

				return buttonsGrid;
			}

			function getBody() {

				let dataTable = {
					id: "dt-" + self.uid,
					rows: [
						{ height: 5 },
						{
							cols: [
								{ width: 5 },
								{
									rows: getTable()
								},
								{ width: 5 }
							]
						},
						{ height: 5 }
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
						{ gridOption: { id: 21 }, value: "custom" },
						{ gridOption: { id: 23 }, value: "true" }
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
								labelTooltip: "LBL_PLAN_ORDER_STATE",
								needsTranslation: false,
								parameterized: false,
								position: 2,
								required: false,
								sourceField: "orderStatusId",
								visible: true,
								widthPx: 40,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: [
									{
										icon: "./img/grid/empty.png",
										showValue: false,
										value: "PlanOrderStateType:0"
									},
									{
										icon: "./img/plan/right-arrow.png",
										showValue: false,
										value: "PlanOrderStateType:1"
									},
									{
										icon: "./img/grid/confirmed.png",
										showValue: false,
										value: "PlanOrderStateType:2"
									},
									{
										icon: "./img/grid/critical.png",
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
								adjust: false,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: [
									{
										icon: "./img/plan/PlanOrderType_1.png",
										showValue: false,
										value: "PlanOrderType:1"
									},
									{
										icon: "./img/plan/PlanOrderType_2.png",
										showValue: false,
										value: "PlanOrderType:2"
									},
									{
										icon: "./img/plan/PlanOrderType_3.png",
										showValue: false,
										value: "PlanOrderType:3"
									},
									{
										icon: "./img/plan/PlanOrderType_4.png",
										showValue: false,
										value: "PlanOrderType:4"
									},
									{
										icon: "./img/plan/PlanOrderType_5.png",
										showValue: false,
										value: "PlanOrderType:5"
									},
									{
										icon: "./img/plan/PlanOrderType_6.png",
										showValue: false,
										value: "PlanOrderType:6"
									},
									{
										icon: "./img/plan/PlanOrderType_7.png",
										showValue: false,
										value: "PlanOrderType:7"
									},
									{
										icon: "./img/plan/PlanOrderType_8.png",
										showValue: false,
										value: "PlanOrderType:8"
									}
								]
							},
							{
								id: 102504,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_PLAN_ORDER_TYPE",
								labelTooltip: "LBL_PLAN_ORDER_TYPE",
								needsTranslation: false,
								parameterized: false,
								position: 4,
								required: true,
								sourceField: "orderTypeLabelAlias",
								visible: true,
								widthPx: 160,
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
								label: "LBL_PLAN_ORDER_ALIAS",
								labelTooltip: "LBL_PLAN_ORDER_ALIAS",
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
								label: "LBL_PLAN_ORDER_STATE",
								labelTooltip: "LBL_PLAN_ORDER_STATE",
								needsTranslation: false,
								parameterized: false,
								position: 6,
								required: true,
								sourceField: "orderResult",
								visible: true,
								widthPx: 200,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: [
									{
										showValue: false,
										badgeColor: true,
										avatar: false,
										enabled: true
									}
								]
							},
							{
								id: 102507,
								extended: false,
								filtered: false,
								isEpochMilli: true,
								isPk: false,
								label: "LBL_EXECUTED_AT",
								labelTooltip: "LBL_EXECUTED_AT",
								needsTranslation: false,
								parameterized: false,
								position: 7,
								required: true,
								sourceField: "orderExecutedAt",
								visible: true,
								widthPx: 200,
								gridFieldDataType: {
									id: 4,
									name: "date"
								},
							}
						],
						gridCommands: [

						]

					};

					self.ordersDataTable = new DataTable(null, null, metadata, data, null);

					return self.ordersDataTable.getView();
				}

				return dataTable;
			}


			function getCancelButton() {

				let cancelButton;

				cancelButton = {
					rows: [
						{ height: 10 },
						{
							cols: [
								{},
								{
									view: "button",
									css: "dialog-cancel",
									height: 41,
									width: 130,
									label: i18n.t("LBL_CLOSE"),
									on: {
										onItemClick() {
											$$(self.args.containerId).close();
										}
									}
								},
								{ width: 5 }
							]

						}
					]
				};

				return cancelButton;
			}


			$$("loading-" + self.uid).hide();

			webix.ui(viewMain, $$(self.uid));

		}

		function open_module(btnConfig) {

			let module = util.module.getFromModuleAction(btnConfig.moduleAction.id);
			let args = {};
			args.selectedPks = [self.planId];
			args.planAction = btnConfig.action;
			args.confirm = btnConfig.confirm;
			args.confirmTitle = btnConfig.confirmTitle;
			args.confirmDescription = btnConfig.confirmDescription;

			util.module.open(module, btnConfig.moduleAction, null, null, args);
		}

		function getPlanChangedTemplate(changedAt) {
			
			let planChangedTemplate = '<div><span> </span></div>';

			if (changedAt) {

				planChangedTemplate = '<div><span class="table-badge" style="background: white; color: black;">' + (changedAt ? i18n.t("LBL_LAST_EXECUTION") + ": " + util.toLocaleString(changedAt / 1000) : "") + '</span></div>';
			}

			return planChangedTemplate;
		}

		function getPlanStateTemplate(planStateType) {

			let planStateTemplate = '<div><span> </span></div>';

			if (planStateType) {

				let color = self.config.planStateTypes[planStateType.id].color;
				planStateTemplate = '<div><span class="table-badge" style="background:' + color + '; color: white;">' + i18n.t(planStateType.labelAlias) + '</span></div>';
			}

			return planStateTemplate;
		}

		function getHeaderLineTemplate(planStateType) {

			let planHeaderLineTemplate = '<div> </div>';

			if (planStateType) {

				let color = self.config.planStateTypes[planStateType.id].color;
				planHeaderLineTemplate = '<div style="background-color:' + color + ';height: 15px;"></div>';
			}

			return planHeaderLineTemplate;
		}

	}

}
