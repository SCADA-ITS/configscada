import {util} from "../../../../lib/util.js";
import PlanLibraryOrder from "./planLibraryOrder.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";

const PLAN_ORDER_AUTOMATISM_SCRIPT = "PlanOrderTypeParam:10:1";
const PLAN_ORDER_AUTOMATISM_TEXT = "PlanOrderTypeParam:10:2";

export default class PlanLibraryAutomatismOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder, onUpdate) {

		super(config, uid, plan, planOrder, onUpdate);

		this.planOrderAutomatism = undefined;
		this.planOrderPermission = true;
		this.config = config;
		
		if (this.planOrder && this.planOrder.planOrderType && this.planOrder.planOrderType.planOrderPermission) {

			this.planOrderPermission = util.module.isAllowedPermission(this.planOrder.planOrderType.planOrderPermission);
		}		
		
		if (this.planOrder && this.planOrder.planOrderValues && Array.isArray(this.planOrder.planOrderValues) && this.planOrder.planOrderValues.length > 0) {

			this.planOrderAutomatism = webix.copy(this.planOrder.planOrderValues);

		}
	}

	getContent() {

		let self = this;
		let viewParams;

		viewParams = {
			rows: [
				{
					cols: [
						{ width: 30 },
						{
							rows: [
								getScriptPath(),
								getTextarea(),
								{}
							]
						},
						{}
					]
				},
				{ height: 30 }
			]
		};

		function getScriptPath() {

			let scriptPathRow;
			let planOrderValue;
			let scriptValue;

			if (self.planOrderAutomatism) {

				planOrderValue = self.planOrderAutomatism.find(e => e.planOrderTypeParam.id === PLAN_ORDER_AUTOMATISM_SCRIPT);
				scriptValue = planOrderValue.value;

				if (planOrderValue.value.startsWith(self.config.resources.incidentScripts)) {

					scriptValue = planOrderValue.value.replace(self.config.resources.incidentScripts, "").replace("." + self.config.resources.incidentExtension, "");
				}
			}

			scriptPathRow = {
				cols: [
					{ view: "text", id: "groovy-path-row-" + self.uid, value: scriptValue, required: true, label: i18n.t("LBL_SCRIPT"), disabled: true, labelWidth: 275 },
					{
						view: "icon", icon: "wxi-search", disabled: !self.planOrderPermission,
						on: {
							onItemClick: function() {

								searchScript();
							}
						}
					}
				]
			};

			return scriptPathRow;
		}

		function getTextarea() {

			let textarea;
			let planOrderValue;
			let textareaValue;
			let textareaObject;
				
			if (self.planOrderAutomatism) {

				planOrderValue = self.planOrderAutomatism.find(e => e.planOrderTypeParam.id === PLAN_ORDER_AUTOMATISM_TEXT);
				textareaValue = planOrderValue.value; 
			}
			
			try {

				textareaObject =JSON.parse(textareaValue);
				
			} catch {

			}
			
			textareaValue = (textareaObject) ? textareaObject : textareaValue;
			
			textarea = {
				cols: [
					{ view: "textarea", id: "params-textarea-row-" + self.uid, value: textareaValue, label: i18n.t("LBL_PLAN_ORDER_TYPE_PARAM_AUTOMATISM_PARAMS"), labelWidth: 275 , disabled: !self.planOrderPermission }
				]
			};

			return textarea;
		}

		function searchScript() {

			let dialogImages = webix.copy(SearchDialogBox.CALLBACK);
			let label = i18n.t("LBL_SELECT_SCRIPT");

			dialogImages.fillDialogEntity = fillDialogEntity;
			dialogImages.height = 600;
			dialogImages.width = 800;

			function fillDialogEntity(dialogEntity, onFilledDialogEntity) {

				let resource = {
					relativePath: self.config.resources.planScripts,
					extension: self.config.resources.planExtension
				}

				webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.serverResources, JSON.stringify(resource)).then(function(response) {

					if (response) {

						let scripts = response.json();

						if (Array.isArray(scripts)) {

							dialogEntity.data = [];

							dialogEntity.pkField = "id"
							dialogEntity.fields = ["script=LBL_SCRIPT"];
							dialogEntity.sortFieldId = 2;

							scripts.forEach(script => {

								dialogEntity.data.push({
									id: script,
									script: script
								});
							});
						}

						onFilledDialogEntity(dialogEntity);
					}
				});
			}

			let s = new SearchDialogBox(label, dialogImages, function(items) {

				if (Array.isArray(items) && items.length === 1 && items[0].script) {

					//let fileScript = items[0].script.replace(self.config.resources.planScripts, "").replace("." + self.config.resources.planExtension, "");

					let substringArr = items[0].script.split("/");
					let fileScript = substringArr[substringArr.length - 1];

					$$("groovy-path-row-" + self.uid).setValue(fileScript);
				}
			});

			s.show();
		}

		return viewParams;
	}

	loadData() {

	}

	createCustomPlanOrder(newPlanOrder) {

		if (newPlanOrder) {

			let planOrderType = window.em.getByField(EntityType.PLAN_ORDER_TYPE, "id", newPlanOrder.planOrderTypeId);

			if (planOrderType && Array.isArray(planOrderType.planOrderTypeParams)) {

				let planOrderValues = planOrderType.planOrderTypeParams.map(e => {

					return {

						planOrderTypeParamId: e.id,
						value: ""
					}
				});

				newPlanOrder.planOrderValues = planOrderValues;
			}
		}
	}

	getPlanOrder() {

		let self = this;
		let planOrder;

		if (self.planOrder) {

			planOrder = {
				id: self.planOrder.id,
				planOrderTypeId: self.planOrder.planOrderType.id,
				planOrderValues: []
			};

			if (Array.isArray(self.planOrderAutomatism) && self.planOrderAutomatism.length > 0) {

				for (let automatism of self.planOrderAutomatism) {

					let newValue;

					if (automatism.planOrderTypeParam.id === PLAN_ORDER_AUTOMATISM_SCRIPT) {

						newValue = $$("groovy-path-row-" + self.uid).getValue();

					} else if (automatism.planOrderTypeParam.id === PLAN_ORDER_AUTOMATISM_TEXT) {

						newValue = $$("params-textarea-row-" + self.uid).getValue();
					}

					planOrder.planOrderValues.push({
						id: automatism.id,
						planOrderTypeParamId: automatism.planOrderTypeParam.id,
						value: newValue
					});
				}
			}
		}

		return planOrder;
	}
}