import { util } from "../../lib/util.js";
import GenericModule from "../genericModule.js";
import HistoricalChartElementValues from "./historicalChartElementValues.js";
import HistoricalChartAlarms from "./historicalChartAlarms.js";
import HistoricalChartStates from "./historicalChartStates.js";
import HistoricalChartWizard from "./historicalChartWizard.js";
import OptionDialogBox from "../../component/optionDialogBox.js";

export default class HistoricalChartModule extends GenericModule {

	static get MODE_ALARMS() { return { id: 104501, chartType: "line" } }
	static get MODE_STATES() { return { id: 104502, chartType: "line" } }
	static get MODE_ELEMENT_VALUES() { return { id: 104503, chartType: "spline" } }

	constructor(module, moduleAction, args, mode) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.moduleAction = moduleAction;
		this.mode = mode;
		this.dateEnd = Date.now();
		this.dateIni = Date.now() - (this.config.defaultInitRangeInHours * 60 * 60000);
		this.elementsIds = this.args.elementIds;

		this.uid = webix.uid();
	}


	getView() {

		let view;

		view = {
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
									hidden: true
								},
								{
									id: "loading-" + this.uid,
									rows: [util.ui.getViewDrawLoading()]
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

		let self = this;

		if (self.elementsIds && self.mode) {

			startModule();

		} else {

			if (!self.elementsIds && !self.mode) {

				selectElement();

			} else if (!self.elementsIds) {

				selectElement();

			} else if (!self.mode) {

				selectMode();
			}
		}

		function selectElement() {

			let hChartWizard = new HistoricalChartWizard(self, onAcceptWizard);
			hChartWizard.drawComponent();

			function onAcceptWizard() {

				if (!self.mode) {

					selectMode();

				} else {

					self.moduleAction = {
						id: self.mode
					};

					startModule();
				}
			}
		}

		function selectMode() {

			let options = [];

			options.push(
				{
					id: self.config.moduleAction.alarms,
					label: i18n.t("LBL_HISTORICAL_CHART_MODE_ALARMS"),
					icon: null,
					description: i18n.t("LBL_HISTORICAL_CHART_MODE_ALARMS_DESCRIPTION")
				},
				{
					id: self.config.moduleAction.states,
					label: i18n.t("LBL_HISTORICAL_CHART_MODE_STATES"),
					icon: null,
					description: i18n.t("LBL_HISTORICAL_CHART_MODE_STATES_DESCRIPTION"),
				},
				{
					id: self.config.moduleAction.elementValues,
					label: i18n.t("LBL_HISTORICAL_CHART_MODE_ELEMENT_VALUES"),
					icon: null,
					description: i18n.t("LBL_HISTORICAL_CHART_MODE_ELEMENT_VALUES_DESCRIPTION"),
				}
			);

			let optionDialog = new OptionDialogBox(i18n.t("LBL_SELECT_ANY_OPTION"), options, selectModuleAction, OptionDialogBox.MODE_TOGGLE);
			optionDialog.show();

			function selectModuleAction(obj) {

				$$(optionDialog.optionWindow).close();

				self.mode = obj;
				self.moduleAction = {
					id: obj
				};

				if (!self.elementsIds) {

					selectElement();

				} else {

					startModule();
				}
			}
		}


		function startModule() {

			if (self.moduleAction.id === self.config.moduleAction.alarms) {

				let hChartAlarms = new HistoricalChartAlarms(self);
				hChartAlarms.getAlarms();

			} else if (self.moduleAction.id === self.config.moduleAction.states) {

				let hChartStates = new HistoricalChartStates(self);
				hChartStates.getStates();

			} else if (self.moduleAction.id === self.config.moduleAction.elementValues) {

				let hChartEV = new HistoricalChartElementValues(self);
				hChartEV.getElementValues();
			}
		}
	}
}