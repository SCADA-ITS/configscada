import DataTable from "../../component/datatable.js";
import ListView from "../../component/listview.js";
import { util } from "../../lib/util.js";

const FIELD_LOCATION_ID = "id";
const FIELD_LOCATION_ALIAS = "alias";

export default class IncidentReportChangeLocation {

	constructor(config, incidentReport, selectedIncidentTypeId, onAccept) {

		this.config = config;
		this.incidentReport = incidentReport;
		this.selectedIncidentTypeId = selectedIncidentTypeId;
		this.uid = webix.uid();
		this.listViewStrechs = undefined;
		this.dtLocations = undefined;
		this.selectedStretchId = undefined;
		this.selectedLocationId = undefined;
		this.onAccept = onAccept;
	}

	show() {

		let self = this;
		let stretchs;

		if (self.selectedIncidentTypeId || self.incidentReport.incidentType.id) {

			let typeId = undefined;

			if (self.selectedIncidentTypeId) {

				typeId = self.selectedIncidentTypeId;

			} else {

				typeId = self.incidentReport.incidentType.id;
			}

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.stretchsByIncidentType, { id: typeId })).then(function(stretchsResponse) {

				if (stretchsResponse != null) {

					stretchs = stretchsResponse.json();
					drawContent();
				}
			});

		} else {

			webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.locationsStretchs).then(function(stretchsResponse) {

				if (stretchsResponse != null) {

					stretchs = stretchsResponse.json();
					drawContent();
				}
			});
		}


		function drawContent() {

			let view = {};

			view = {
				rows: [
					getBody(),
					{ rows: [{ height: 10 }] },
					getBottomView()
				]
			};

			self.optionWindow = util.ui.loadWindow(i18n.t("LBL_CHANGE_LOCATION_ACTION"), view, 500, 600, true);

			if (self.dtLocations) {

				self.dtLocations.callbacks();
			}

			if (self.listViewStrechs && self.incidentReport && self.incidentReport.affectionStretch && self.incidentReport.affectionStretch.id) {

				self.listViewStrechs.select(self.incidentReport.affectionStretch.id);
			}


			function getBody() {

				let body = {};

				body = {
					rows: [
						{
							cols: [
								{ width: 45 },
								{
									rows: [

										{ height: 17 },
										{
											cols: [
												getViewStretchs(),
												{ width: 25 },
												{
													rows: [
														getViewLocations(),
													]
												}
											]
										}
									]
								},
								{ width: 45 }
							]
						}
					]
				};

				function getViewStretchs() {

					let viewStretchs = {};
					let stretchData = [];

					if (Array.isArray(stretchs) && stretchs.length > 0) {


						stretchs = stretchs.filter(e => {
							return (self.config.stretchTypes.indexOf(e.stretchType.id) !== -1);
						});

						stretchs.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));

						stretchData = stretchs.map(e => {

							return {
								id: e.id,
								alias: e.alias,
								description: e.description
							};
						});
					}
					
					let options = {};
					let events = {};

					options[ListView.Options.ICON] = false;
					options[ListView.Options.ROW_HEIGHT] = 41;

					events[ListView.EventTypes.ON_SELECT_ROW] = function(data, callbackEnable) {

						self.selectedStretchId = data;

						if (callbackEnable) {

							callbackEnable(false);
						}

						webix.extend($$("view-locations-" + self.uid), webix.ProgressBar);

						$$("view-locations-" + self.uid).showProgress({
							type: "bottom",
							hide: false
						});

						if (self.dtLocations) {

							self.dtLocations.clear();
						}


						webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.locationsByStretch, { id: data })).then(function(dat) {

							if (dat != null) {

								let locations = dat.json();

								if (Array.isArray(locations) && locations.length > 0) {

									locations = locations.filter(e => {
										return (self.config.locationTypes.indexOf(e.locationType.id) !== -1)
									});

									locations.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
									let dataLocations = locations.map(e => {

										return {
											id: e.id,
											alias: e.alias
										}
									});

									self.dtLocations.add(dataLocations);
									self.selectedLocationId = undefined;

									if (self.incidentReport && self.incidentReport.location && self.incidentReport.location.id) {

										self.dtLocations.select(self.incidentReport.location.id);
									}
								}

								$$("view-locations-" + self.uid).hideProgress();
							}

							if (callbackEnable) {

								callbackEnable(true);
							}

							checkButtonAccept();
						});
					};

					self.listViewStrechs = new ListView(stretchData, options, events);

					viewStretchs = {
						id: "view-stretchs-" + self.uid,
						width: 250,
						rows: [
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
											self.listViewStrechs.getView(),
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
						{ gridOption: { id: 3 }, value: "2" },
						{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "false" },
						{ gridOption: { id: 12 }, value: "false" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 21 }, value: "auto" },
						{ gridOption: { id: 22 }, value: "0" },
						{ gridOption: { id: 23 }, value: "false" },
						{ gridOption: { id: 24 }, value: "false" }
					];

					const metadata = {
						filter: true,
						label: "LBL_LOCATIONS",
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [
							{ id: 1, isPk: true, position: 1, required: true, sourceField: FIELD_LOCATION_ID, visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
							{ id: 2, isPk: false, position: 2, required: true, sourceField: FIELD_LOCATION_ALIAS, visible: true, widthPx: 200, gridFieldDataType: { id: 1, name: "string" } }
						]
					};

					let events = {};

					events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

						if (Array.isArray(rows) && rows.length > 0) {

							self.selectedLocationId = rows[0];
							checkButtonAccept();
						}
					};

					self.dtLocations = new DataTable(null, null, metadata, [], events);

					viewLocations = {
						id: "view-locations-" + self.uid,
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
										rows: self.dtLocations.getView()
									}
								]
							},
							{ height: 1, borderless: true }
						]
					};

					return viewLocations;
				}

				return body;
			}
		}

		function checkButtonAccept() {

			if (self.selectedIncidentTypeId) {

				if (self.selectedStretchId && self.selectedLocationId &&

					(self.selectedStretchId != self.incidentReport.affectionStretch.id
						|| self.selectedLocationId != self.incidentReport.location.id || self.selectedIncidentTypeId != self.incidentReport.incidentType.id)) {

					$$("button-accept-" + self.uid).enable();

				} else {
					$$("button-accept-" + self.uid).disable();
				}

			} else {

				if (self.selectedStretchId && self.selectedLocationId) {

					$$("button-accept-" + self.uid).enable();

				} else {
					$$("button-accept-" + self.uid).disable();
				}
			}
		}

		function getBottomView() {

			let buttons = {};

			buttons = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: [
					{ width: 36 },
					{
						view: "button",
						css: "dialog-cancel",
						width: 130,
						height: 35,
						value: i18n.t("LBL_CANCEL"),
						click: function() {

							let parentWindow = this.getTopParentView();
							parentWindow.close();
						}
					},
					{},
					{
						id: "button-accept-" + self.uid,
						view: "button",
						css: "dialog-accept",
						width: 130,
						height: 35,
						value: i18n.t("LBL_ACCEPT"),
						disabled: true,
						click: function() {

							if (self.onAccept && self.selectedStretchId && self.selectedLocationId) {

								self.onAccept(self.selectedStretchId, self.selectedLocationId);
							}

							let parentWindow = this.getTopParentView();
							parentWindow.close();
						}
					},
					{ width: 36 }
				]
			};

			return buttons;
		}
	}
}

