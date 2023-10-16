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
		this.isEmbed = this.args.isEmbed;

		this.uid = webix.uid();
	}


	getView() {

		let view;

		view = {
			id: "historical-chart-" + this.uid,
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

			let optionDialog = new OptionDialogBox(i18n.t("LBL_SELECT_ANY_OPTION"), options, selectModuleAction, OptionDialogBox.MODE_TOGGLE, closeOptionDialog);
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
			
			function closeOptionDialog() {

				$$(MAIN_TAB_ID).removeView(self.args.containerId);
			}
		}


		function startModule() {

			let args = {
				dates: {
					dateIni: self.dateIni,
					dateEnd: self.dateEnd
				},
				elementsIds: self.elementsIds,
				isEmbed: self.isEmbed
			};

			if (self.moduleAction.id === self.config.moduleAction.alarms) {

				args.url = self.config.restEndpoints.alarms;
				args.mode = HistoricalChartModule.MODE_ALARMS;

				let hChartAlarms = new HistoricalChartAlarms(args, self);
				hChartAlarms.initialize();

			} else if (self.moduleAction.id === self.config.moduleAction.states) {

				args.url = self.config.restEndpoints.states;
				args.mode = HistoricalChartModule.MODE_STATES;

				let hChartStates = new HistoricalChartStates(args, self);
				hChartStates.initialize();

			} else if ((self.moduleAction.id === self.config.moduleAction.elementValues) || (self.moduleAction.id === self.config.moduleAction.equipmentViewer)) {

				args.url = self.config.restEndpoints.elementValues;
				args.mode = HistoricalChartModule.MODE_ELEMENT_VALUES;

				let hChartEV = new HistoricalChartElementValues(args, self);
				hChartEV.initialize();
			}
		}
	}
}