import { util } from "../../lib/util.js";

export default class SearchPlan {

	constructor(searchModule, openModule) {

		this.config = searchModule.config;
		this.uid = searchModule.uid;
		this.text = searchModule.text;
		this.openModule = openModule;
	}

	drawResults(onReady) {

		let self = this;
		let plans;
		let planOrdersArr = [];

		webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.plans).then(function(response) {

			if (response != null) {

				plans = response.json();

				plans = filterResults();

				if (Array.isArray(planOrdersArr) && planOrdersArr.length > 0) {

					let responseCounter = 0;

					planOrdersArr.forEach(order => {

						webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.plansOrder, { id: order.order.id })).then(function(planOrderResponse) {

							if (planOrderResponse != null) {

								let planOrderComplete = planOrderResponse.json();
								let planOrder = planOrdersArr.find(po => po.order.id === planOrderComplete.id);
								let planOrderType = window.em.getByField(EntityType.PLAN_ORDER_TYPE, "id", planOrderComplete.planOrderType.id);

								if (planOrder) {

									planOrder.order.planOrderType = planOrderType;
								}

								if (++responseCounter === planOrdersArr.length) {

									drawViewResultsPlans();
								}
							}
						});
					});

				} else {

					drawViewResultsPlans();
				}
			}
		});

		function drawViewResultsPlans() {

			let viewResultsPlans;

			viewResultsPlans = {
				id: "results-plans-" + self.uid,
				borderless: true,
				hidden: false,
				cols: [
					{ width: 100 },
					{
						rows: function() {

							let rows = [];

							rows.push({ height: 30 });

							rows.push({
								height: 30,
								cols: [
									{
										view: "icon",
										icon: "mdi mdi-menu-down",
										width: 30,
										collapse: false,
										on: {
											onItemClick: function() {

												this.collapse = !this.collapse;

												this.define("icon", this.collapse ? "mdi mdi-menu-right" : "mdi mdi-menu-down");
												this.refresh();

												if ($$("results-plans-rows-" + self.uid)) {

													this.collapse ? $$("results-plans-rows-" + self.uid).hide() : $$("results-plans-rows-" + self.uid).show();
												}
											}
										}
									},
									{
										view: "label",
										css: "label-search-type-result",
										label: i18n.t("LBL_PLANS") + " (" + plans.length + " " + i18n.t("LBL_RESULTS") + ")"
									}
								]
							});

							rows.push({ height: 20 });

							let rowsResult = [];

							if (Array.isArray(plans) && plans.length > 0) {

								plans.forEach(plan => {

									rowsResult.push(getRowResult(plan));

									if (Array.isArray(planOrdersArr) && planOrdersArr.length > 0) {

										let planOrder = planOrdersArr.filter(po => po.planId === plan.id);

										if (Array.isArray(planOrder) && planOrder.length > 0) {

											planOrder.forEach(order => {

												rowsResult.push(getRowResult(order));
											});
										}
									}
								});
							}

							rows.push({ id: "results-plans-rows-" + self.uid, cols: [{ width: 25 }, { rows: rowsResult }] });

							function getRowResult(plan) {

								let rowResult;
								let lbl = "";
								let img = "./img/tree_document.png";
								let planTitle = plan.id;
								let stateTitle = (plan.planStateType && plan.planStateType.labelAlias) ? i18n.t(plan.planStateType.labelAlias) : "";
								let color;

								if (!plan.isOrder) {
									
									let desc = (plan.description) ? " - " +  plan.description : "";
									lbl = plan.alias + desc;
									color = self.config.types.plan.planStateTypes[plan.planStateType.id].color;

									rowResult = drawRow();

								} else {

									if (plan.order && plan.order.comandElement && plan.order.comandElement.element.id) {

										let element = window.em.getByField(EntityType.ELEMENT, "id", plan.order.comandElement.element.id);
										lbl = element.alias + " - ";
									}

									if (plan.order && plan.order.planOrderType && plan.order.planOrderType.icon) {

										img = plan.order.planOrderType.icon;
										planTitle = i18n.t(plan.order.planOrderType.labelAlias);
										stateTitle = i18n.t(plan.order.planOrderStateType.labelAlias);
									}

									let desc = (plan.planDescription) ? " - " + plan.planDescription : "";
									lbl += plan.order.alias + " - " + plan.planAlias + desc;
									color = self.config.types.plan.planOrderStateTypes[plan.order.planOrderStateType.id].color;
									rowResult = drawRow();
								}

								function drawRow() {

									let row = {
										cols: [
											{
												hidden: (!plan.isOrder) ? true : false,
												width: 25
											},
											{
												id: (plan.isOrder) ? "plan-row-selected-" + plan.planId + "-" + plan.order.id + "-" + self.uid : "plan-row-selected-" + plan.id + "-" + self.uid,
												view: "template",
												borderless: true,
												width: 32,
												template: `<span style='font-size: 30px; color: transparent' class='mdi mdi-menu-right'></span>`
											},
											{
												view: "template",
												borderless: true,
												width: 32,
												hidden: (!plan.isOrder) ? true : false,
												template: `<div><img style='display:block; height:20px; width:auto; padding-top: 5px;' src="./img/down-right.png"></div>`
											},
											{
												view: "template",
												width: 36,
												borderless: true,
												template: function() {

													return `<div><img style='display:block; height:32px; width:auto; margin:auto; padding:1px;' src='${img}' title='${planTitle}' onerror='this.onerror=null;this.src="/img/grid/empty.png"'></div>`;
												}
											},
											{ width: 15 },
											{
												view: "template",
												borderless: true,
												width: 36,
												template: function() {

													let cssCircle = `width: 26px; height: 26px; -webkit-border-radius: 13px; -moz-border-radius: 13px; border-radius: 13px; margin: 5px 0px 0px 5px; background: ${color};`;

													return `<div title="${stateTitle}" style="${cssCircle}"></div>`;
												}
											},
											{ width: 15 },
											{
												view: "label",
												label: lbl,
												tooltip: lbl,
												css: "label-search-result",
												on: {
													onItemClick: function(id) {

														if (self.openModule) {

															let mod = (plan.isOrder) ? self.config.types.plan.planOrder : self.config.types.plan.default;
															let planOrderId = (plan.isOrder) ? plan.order.id : "";
															let planId = (plan.isOrder) ? plan.planId : plan.id;

															self.openModule(mod, {
																selectedPks: [planId],
																selectedPlanOrderPks: {id:planOrderId}
															});
														}
													},
													onAfterRender: function() {

														selectRow(this.getNode(), plan);
													}
												}
											},
											{ width: 50 },
											getViewCommands(plan),
											{ width: 1 }
										]
									}

									return row;
								}

								return rowResult;
							}

							return rows;
						}()
					},
					{ width: 100 }
				]
			}

			if ($$("results-plans-" + self.uid)) {

				webix.ui(viewResultsPlans, $$("results-plans-" + self.uid));
				
				if (onReady) {

					onReady();
				}
			}
		}

		function selectRow(object, plan) {

			let rowSelected = (plan.isOrder) ? $$("plan-row-selected-" + plan.planId + "-" + plan.order.id + "-" + self.uid) : $$("plan-row-selected-" + plan.id + "-" + self.uid);//$$("plan-row-selected-" + plan.id + "-" + self.uid);

			if (rowSelected) {

				object.addEventListener("mouseover", function() {

					rowSelected.define("template", `<span style='font-size: 30px; color: var(--blue-text-default)' class='mdi mdi-menu-right'></span>`);
					rowSelected.refresh();

				});

				object.addEventListener("mouseout", function() {

					rowSelected.define("template", `<span style='font-size: 30px; color: transparent' class='mdi mdi-menu-right'></span>`);
					rowSelected.refresh();
				});
			}
		}

		function getViewCommands(plan) {

			let viewCommands;

			viewCommands = {
				view: "toolbar",
				width: 600,
				borderless: true,
				css: "search-links-toolbar",
				elements: [
					{
						view: "button",
						width: 120,
						type: "icon",
						icon: "mdi mdi-share",
						label: i18n.t("LBL_SEARCH_LINK_VIEW"),
						tooltip: i18n.t("LBL_SEARCH_LINK_VIEW_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {

									let mod = (plan.isOrder) ? self.config.types.plan.planOrder : self.config.types.plan.default;
									let planOrderId = (plan.isOrder) ? plan.order.id : "";
									let planId = (plan.isOrder) ? plan.planId : plan.id;

									self.openModule(mod, {
										selectedPks: [planId],
										selectedPlanOrderPks: {id:planOrderId}
									});
								}
							},
							onAfterRender: function() {

								selectRow(this.getNode(), plan);
							}
						}
					},
					{
						view: "button",
						width: 120,
						type: "icon",
						icon: "mdi mdi-check",
						label: i18n.t("LBL_SEARCH_LINK_EXECUTE"),
						tooltip: i18n.t("LBL_SEARCH_LINK_EXECUTE_DESCRIPTION"),
						hidden: (plan.isOrder) ? true : false,
						on: {
							onItemClick: function() {

								if (self.openModule) {

									let id = (plan.isOrder) ? plan.planId : plan.id;
									self.openModule(self.config.types.plan.planExecutor, { selectedPks: [id] });
								}
							},
							onAfterRender: function() {

								selectRow(this.getNode(), plan);
							}
						}
					},
					{}
				]
			}

			return viewCommands;
		}

		function filterResults() {

			let filteredResults = [];

			if (Array.isArray(plans)) {

				filteredResults = plans.filter(e => {

					let alias = e.alias ? util.getStringNormalized(e.alias) : undefined;
					let description = e.description ? util.getStringNormalized(e.description) : undefined;
					let planStateTypeAlias = e.planStateType.alias ? util.getStringNormalized(e.planStateType.alias) : undefined;
					let planStateTypeLabelAlias = e.planStateType.labelAlias ? util.getStringNormalized(i18n.t(e.planStateType.labelAlias)) : undefined;

					let filteredPlanOrders = [];

					if (Array.isArray(e.planOrders) && e.planOrders.length > 0) {

						filteredPlanOrders = e.planOrders.filter(po => {

							let planOrderAlias;
							let element;
							let elementAlias;
							let elementTypeLabelAlias;

							planOrderAlias = po.alias ? util.getStringNormalized(po.alias) : undefined;

							if (po.comandElement && po.comandElement.element.id) {

								element = window.em.getByField(EntityType.ELEMENT, "id", po.comandElement.element.id);

								if (element) {

									elementAlias = element.alias ? util.getStringNormalized(element.alias) : undefined;
									elementTypeLabelAlias = element.elementType.labelAlias ? util.getStringNormalized(element.elementType.labelAlias) : undefined;
								}
							}

							return (elementAlias && elementAlias.toLowerCase().includes(self.text)) ||
								(planOrderAlias && planOrderAlias.toLowerCase().includes(self.text)) ||
								(elementTypeLabelAlias && elementTypeLabelAlias.toLowerCase().includes(self.text));
						});

						if (Array.isArray(filteredPlanOrders) && filteredPlanOrders.length > 0) {

							for (let order of filteredPlanOrders) {

								planOrdersArr.push({
									planId: e.id,
									planAlias: e.alias,
									planDescription: e.description,
									order: order,
									isOrder: true
								});
							}
						}
					}

					return (alias && alias.toLowerCase().includes(self.text)) ||
						(description && description.toLowerCase().includes(self.text)) ||
						(planStateTypeAlias && planStateTypeAlias.toLowerCase().includes(self.text)) ||
						(planStateTypeLabelAlias && planStateTypeLabelAlias.toLowerCase().includes(self.text)) ||
						(Array.isArray(filteredPlanOrders) && filteredPlanOrders.length > 0);
				});
			}

			return filteredResults;
		}
	}
}