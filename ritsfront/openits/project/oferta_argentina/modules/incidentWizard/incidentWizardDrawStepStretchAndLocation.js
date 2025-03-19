import IncidentWizardDrawStep from "./incidentWizardDrawStep.js";
import IncidentWizardModule from "./incidentWizardModule.js";
import DataTable from "../../component/datatable.js";
import ListView from "../../component/listview.js";
import { util } from "../../lib/util.js";

export default class IncidentWizardDrawStepStretchAndLocation extends IncidentWizardDrawStep {

	constructor(parent, step) {

		super(parent, step);
	}

	getView() {

		let view = {};

		view = {
			id: "drawStepStretchAndLocation-" + this.parent.uid
		}

		return view;
	}

	loadData(onReady) {

		let parent = this.parent;
		let step = this.step;
		let viewStepStretchAndLocation;

		viewStepStretchAndLocation = {
			rows: [
				{
					cols: [
						{ width: 45 },
						{
							rows: [
								{ height: 15 },
								{
									view: "label",
									label: `<span class="ico webix_icon mdi mdi-numeric-${step.position}-box" style="font-size: 30px;"></span><span class="content-wizard-label">${i18n.t(step.stepLabel)}</span>`
								},
								{ height: 10 },
								{
									cols: [
										getViewStretchs(),
										{ width: 25 },
										{
											rows: [
												getViewLocationLink(),
												getViewLocations()
											]
										}
									]
								},
								{ height: 10 }
							]
						},
						{ width: 45 }
					]
				}
			]
		};

		webix.ui(viewStepStretchAndLocation, $$("drawStepStretchAndLocation-" + parent.uid));

		if (onReady) {

			onReady();
		}

		function getViewLocationLink() {

			let viewLocationLink = {};

			viewLocationLink = {
				rows: [
					{
						view: "label",
						borderless: true,
						height: 23,
						label: i18n.t("LBL_SELECT_STRETCHS_NUM_2")
					}
				]
			};

			return viewLocationLink;
		}

		function getViewStretchs() {

			let viewStretchs = {};
			let stretchData = [];
			let options = {};
			let events = {};

			options[ListView.Options.ICON] = false;
			options[ListView.Options.ROW_HEIGHT] = 41;

			events[ListView.EventTypes.ON_SELECT_ROW] = function(data, callbackEnable) {

				if (callbackEnable) {

					callbackEnable(false);
				}

				webix.extend($$("view-locations-" + parent.uid), webix.ProgressBar);

				$$("view-locations-" + parent.uid).showProgress({
					type: "bottom",
					hide: false
				});

				parent.dtLocations.clear();

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.locationsByStretch, { id: data })).then(function(dat) {

					let dataLocations;

					if (dat != null) {

						let locations = dat.json();

						if (Array.isArray(locations) && locations.length > 0) {

							locations = locations.filter(e => {
								return (parent.config.locationTypes.indexOf(e.locationType.id) !== -1)
							});

							locations.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
							dataLocations = locations.map(e => {

								return {
									id: e.id,
									alias: e.alias,
									locationType: i18n.t("LBL_PERMANENT_LOCATION")
								}
							});
						}
					}

					$$("view-locations-" + parent.uid).hideProgress();
					parent.incidentWizardAction.validateStep(parent.incidentWizardAction.getCurrentStep(), onValidateStep);

					function onValidateStep(valid) {

						if (Array.isArray(dataLocations) && dataLocations.length > 0) {

							parent.dtLocations.add(dataLocations);
							parent.dtLocations.selectByIndex(0);
						}

						if (parent.newLocationsMap && parent.newLocationsMap.has(data[0])) {

							let temporallyLocationList = parent.newLocationsMap.get(data[0]);

							if (Array.isArray(temporallyLocationList) && temporallyLocationList.length > 0) {

								let temporallyDataLocations = temporallyLocationList.map(e => {

									return {
										id: e.location.id,
										alias: e.location.alias,
										locationType: i18n.t("LBL_TEMPORALLY_LOCATION")
									}
								});

								parent.dtLocations.add(temporallyDataLocations);
							}
						}

						if (callbackEnable) {

							callbackEnable(true);
						}
					}
				});
			};

			parent.listViewStrechs = new ListView(stretchData, options, events);

			viewStretchs = {
				id: "view-stretchs-" + parent.uid,
				width: 250,
				rows: [
					{
						view: "label",
						borderless: true,
						height: 23,
						label: i18n.t("LBL_SELECT_STRETCHS_NUM_1")
					},
					{
						view: "label",
						borderless: true,
						height: 23,
						css: "headers",
						label: i18n.t("LBL_STRETCHS")
					},
					{
						cols: [
							{
								css: { "border": "1px solid #dadee0 !important", "box-sizing": "border-box" },
								rows: [
									{ height: 1, borderless: true },
									parent.listViewStrechs.getView(),
									{ height: 2, borderless: true }
								]
							}
						]
					},
					{ height: 1, borderless: true }
				]
			};

			return viewStretchs;
		}

		function getViewLocations() {

			let viewLocations = {};

			const defaultGridOptionValues = [
				{ gridOption: { id: 1 }, value: "false" },
				{ gridOption: { id: 2 }, value: "false" },
				{ gridOption: { id: 3 }, value: "3" },
				{ gridOption: { id: 4 }, value: "asc" },
				{ gridOption: { id: 7 }, value: "false" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 10 }, value: "true" },
				{ gridOption: { id: 11 }, value: "true" },
				{ gridOption: { id: 12 }, value: "true" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" },
				{ gridOption: { id: 21 }, value: "auto" },
				{ gridOption: { id: 22 }, value: "0" },
				{ gridOption: { id: 23 }, value: "false" },
				{ gridOption: { id: 24 }, value: "true" }
			];

			const metadata = {
				filter: true,
				label: "LBL_LOCATIONS",
				visible: true,
				gridOptionValues: defaultGridOptionValues,
				gridFields: [
					{ id: 1, isPk: true, position: 1, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_GROUP_ID, visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
					{
						id: 3, isPk: false, position: 2, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_LOCATION_TYPE, label: "LBL_LOCATIONS_LOCATION_TYPE", visible: true, widthPx: 250, gridFieldDataType: { id: 1, name: "string" },
						gridFieldBalloons: [],
						gridFieldIcons: [
							{
								id: 1,
								badgeColor: true
							}
						]
					},
					{ id: 2, isPk: false, position: 3, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_GROUP_ALIAS, label: "LBL_LOCATION", visible: true, widthPx: 200, gridFieldDataType: { id: 1, name: "string" } }
				],
				gridCommands: []
			};

			let events = {};

			events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

				if (Array.isArray(rows) && rows.length > 0) {

					parent.dtLocations.disableAll();

					if (parent.incidentWizardAction.autoSelectLocation) {

						parent.incidentWizardAction.validateStep(parent.steps[1], onValidateStep);

					} else {

						parent.incidentWizardAction.validateStep(parent.incidentWizardAction.getCurrentStep(), onValidateStep);
					}
				}

				function onValidateStep(valid) {

					parent.dtLocations.enableAll();
				}
			};

			parent.dtLocations = new DataTable(null, null, metadata, [], events);

			viewLocations = {
				id: "view-locations-" + parent.uid,
				rows: [
					{
						view: "label",
						borderless: true,
						height: 23,
						css: "headers",
						label: i18n.t("LBL_LOCATIONS")
					},
					{ height: 5, css: { "border-left": "1px solid #dadee0 !important", "border-right": "1px solid #dadee0 !important", "box-sizing": "border-box" } },
					{
						borderless: true,
						cols: [
							{
								css: { "border": "1px solid #dadee0 !important", "border-top": "none !important", "box-sizing": "border-box" },
								rows: parent.dtLocations.getView()
							}
						]
					},
					{ height: 1, borderless: true }
				]
			};

			return viewLocations;
		}
	}

	initialize() {

		super.initialize();

		this.parent.listViewStrechs.selectByIndex(0);
	}
}	
