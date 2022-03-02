import DataTable from "../../component/datatable.js";
import ListView from "../../component/listview.js";
import { util } from "../../lib/util.js";

const FIELD_INCIDENT_TYPE_ID = "id";
const FIELD_INCIDENT_TYPE_ALIAS = "alias";
const FIELD_INCIDENT_TYPE_DESCRIPTION = "description";

const FIELD_INCIDENT_LEVEL_ID = "levelId";
const FIELD_INCIDENT_LEVEL_LABEL_ALIAS = "levelLabelAlias";
const FIELD_INCIDENT_LEVEL_ICON = "levelIcon";
const FIELD_INCIDENT_LEVEL_COLOR = "levelColor";

const FIELD_INCIDENT_USER_ID = "userId";
const FIELD_INCIDENT_USER = "user";

export default class IncidentReportChangeType {

	constructor(config, incidentReport, affectionStretchId, onAccept) {

		this.config = config;
		this.incidentReport = incidentReport;
		this.uid = webix.uid();
		this.listViewIncidentGroups = undefined;
		this.dtIncidentTypes = undefined;
		this.selectedIncidenTypeId = undefined;
		this.affectionStretchId = affectionStretchId;
		this.onAccept = onAccept;
		this.incidentTypes = undefined;
	}


	show() {

		let self = this;

		if (self.affectionStretchId) {

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentTypesByStretch, { id: self.affectionStretchId })).then(function(stretchsResponse) {

				if (stretchsResponse != null) {

					self.incidentTypes = stretchsResponse.json();
					drawContent();
				}
			});

		} else {

			self.incidentTypes = window.em.getAll(EntityType.IMS_INCIDENT_TYPE);
			drawContent();
		}


		function drawContent() {

			let view;

			view = {
				rows: [
					getBody(),
					{ rows: [{ height: 20 }] },
					getBottomView(),
					{ rows: [{ height: 10 }] }
				]
			};

			self.optionWindow = util.ui.loadWindow(i18n.t("LBL_CHANGE_INCIDENT_TYPE_ACTION"), view, 600, 1200, true);

			if (self.dtIncidentTypes) {

				self.dtIncidentTypes.callbacks();
			}

			if (self.listViewIncidentGroups && Array.isArray(self.incidentTypes) && self.incidentTypes.length > 0 && self.incidentReport && self.incidentReport.incidentType.id) {

				let incidentType = self.incidentTypes.find(e => e.id === self.incidentReport.incidentType.id);

				if (incidentType && incidentType.incidentGroup && incidentType.incidentGroup.id) {

					self.listViewIncidentGroups.select(incidentType.incidentGroup.id);
				}
			}

			function getBody() {

				let body;

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
												getViewIncidentGroups(),
												{ width: 25 },
												{
													rows: [
														getViewIncidentTypes(),
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

				function getViewIncidentGroups() {

					let viewIncidentGroups = {};
					let incidentGroups = window.em.getAll(EntityType.IMS_INCIDENT_GROUP);

					if (Array.isArray(incidentGroups) && incidentGroups.length > 0) {

						let incidentGroupData = incidentGroups.map(e => {

							return {
								id: e.id,
								alias: e.alias,
								description: e.description
							};
						});

						let options = {};
						let events = {};

						options[ListView.Options.ICON] = false;
						options[ListView.Options.ROW_HEIGHT] = 41;

						events[ListView.EventTypes.ON_SELECT_ROW] = function(data, callbackEnable) {

							if (callbackEnable) {

								callbackEnable(false);
							}

							webix.extend($$("view-incident-types-" + self.uid), webix.ProgressBar);

							self.dtIncidentTypes.clear();

							if (Array.isArray(data) && data.length > 0) {

								$$("view-incident-types-" + self.uid).showProgress({
									type: "bottom",
									hide: false
								});

								if (Array.isArray(self.incidentTypes) && self.incidentTypes.length > 0) {

									let incidentTypesByGroup = self.incidentTypes.filter(e => e.incidentGroup.id === data[0]);

									if (Array.isArray(incidentTypesByGroup) && incidentTypesByGroup.length > 0) {

										let dataIncidentTypes = incidentTypesByGroup.map(e => {

											return {
												id: e.id,
												alias: e.alias,
												description: e.description,
												userId: e.defaultUser.id,
												user: e.defaultUser.name,
												levelId: e.incidentLevel.id,
												levelLabelAlias: i18n.t(e.incidentLevel.labelAlias),
												levelIcon: e.incidentLevel.icon,
												levelColor: e.incidentLevel.color
											}
										});

										self.dtIncidentTypes.add(dataIncidentTypes);

										self.selectedIncidenTypeId = undefined;

										if (self.incidentReport && self.incidentReport.incidentType && self.incidentReport.incidentType.id) {

											self.dtIncidentTypes.select(self.incidentReport.incidentType.id);
										}
									}
								}

								$$("view-incident-types-" + self.uid).hideProgress();

								if (callbackEnable) {

									callbackEnable(true);
								}
							}
						};

						self.listViewIncidentGroups = new ListView(incidentGroupData, options, events);

						viewIncidentGroups = {
							id: "view-incident-groups-" + self.uid,
							width: 250,
							rows: [
								{
									view: "label",
									borderless: true,
									height: 23,
									css: "headers",
									label: i18n.t("LBL_INCIDENT_GROUPS")
								},
								{
									cols: [
										{
											css: { "border": "1px solid #dadee0 !important", "box-sizing": "border-box" },
											rows: [
												{ height: 1, borderless: true },
												self.listViewIncidentGroups.getView(),
												{ height: 2, borderless: true }
											]
										}
									]
								},
								{ height: 1, borderless: true }
							]
						};
					}

					return viewIncidentGroups;
				}

				function getViewIncidentTypes() {

					let viewIncidentTypes = {};

					const defaultGridOptionValues = [
						{ gridOption: { id: 1 }, value: "false" },
						{ gridOption: { id: 2 }, value: "true" },
						{ gridOption: { id: 3 }, value: "5" },
						{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "false" },
						{ gridOption: { id: 12 }, value: "false" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 21 }, value: "custom" },
						{ gridOption: { id: 22 }, value: "0" },
						{ gridOption: { id: 23 }, value: "false" },
						{ gridOption: { id: 24 }, value: "true" }
					];

					let metadata = {
						filter: true,
						label: "LBL_INCIDENT_TYPES",
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [
							{ id: 1, isPk: true, position: 1, required: true, sourceField: FIELD_INCIDENT_TYPE_ID, visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
							{ id: 2, isPk: false, position: 2, required: true, sourceField: FIELD_INCIDENT_LEVEL_ID, visible: false, widthPx: 15, gridFieldDataType: { id: 1, name: "string" } },

							{ id: 3, isPk: false, position: 3, required: true, sourceField: FIELD_INCIDENT_LEVEL_ICON, visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
							{ id: 4, isPk: false, position: 4, required: true, sourceField: FIELD_INCIDENT_LEVEL_COLOR, visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
							{ id: 5, isPk: false, position: 5, required: true, sourceField: FIELD_INCIDENT_TYPE_ALIAS, label: "LBL_CODE", visible: true, widthPx: 100, gridFieldDataType: { id: 1, name: "string" } },
							{
								id: 6, isPk: false, position: 6, required: true, sourceField: FIELD_INCIDENT_LEVEL_LABEL_ALIAS, label: "LBL_LEVEL", visible: true, widthPx: 200, gridFieldDataType: { id: 1, name: "string" },
								gridFieldBalloons: (function() {

									let incidentLevels = window.em.getAll(EntityType.IMS_INCIDENT_LEVEL);

									let fieldBallons = [];

									if (Array.isArray(incidentLevels) && incidentLevels.length > 0) {

										let fieldBallonId = 0;

										incidentLevels.forEach(e => {

											fieldBallons.push({

												id: ++fieldBallonId,
												color: e.color,
												value: i18n.t(e.labelAlias)
											});
										});
									}

									return fieldBallons;
								})(),
								gridFieldIcons: [
									{
										id: 1,
										badgeColor: true
									}
								]
							},
							{ id: 7, isPk: false, position: 7, required: true, sourceField: FIELD_INCIDENT_TYPE_DESCRIPTION, label: "LBL_INCIDENT", visible: true, widthPx: 480, gridFieldDataType: { id: 1, name: "string" } },
							{ id: 8, isPk: false, position: 8, required: true, sourceField: FIELD_INCIDENT_USER_ID, visible: false, widthPx: 300, gridFieldDataType: { id: 1, name: "string" } },
							{ id: 9, isPk: false, position: 9, required: true, sourceField: FIELD_INCIDENT_USER, visible: false, widthPx: 300, gridFieldDataType: { id: 1, name: "string" } }
						]
					};

					let events = {};

					events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

						if (Array.isArray(rows) && rows.length > 0) {

							self.selectedIncidenTypeId = rows[0];
							checkButtonAccept();
						}
					};

					self.dtIncidentTypes = new DataTable(null, null, metadata, [], events);

					viewIncidentTypes = {
						id: "view-incident-types-" + self.uid,
						rows: [
							{
								view: "label",
								borderless: true,
								height: 23,
								css: "headers",
								label: i18n.t("LBL_INCIDENT_TYPES")
							},
							{ height: 5, css: { "border-left": "1px solid #dadee0 !important", "border-right": "1px solid #dadee0 !important", "box-sizing": "border-box" } },
							{
								borderless: true,
								cols: [
									{
										css: { "border": "1px solid #dadee0 !important", "border-top": "none !important", "box-sizing": "border-box" },
										rows: self.dtIncidentTypes.getView()
									}
								]
							},
							{ height: 1, borderless: true }
						]
					};

					return viewIncidentTypes;
				}

				return body;
			}
		}


		function checkButtonAccept() {

			if (self.selectedIncidenTypeId) {

				$$("button-accept-" + self.uid).enable();

			} else {
				$$("button-accept-" + self.uid).disable();
			}
		}

		function getBottomView() {

			return {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: [
					//{ height: 10 },
//					{
//						cols: [
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

									if (self.onAccept && self.selectedIncidenTypeId) {

										self.onAccept(self.selectedIncidenTypeId);
									}

									let parentWindow = this.getTopParentView();
									parentWindow.close();
								}
							},
							{ width: 36 }
//						]
//					}
				]
			};
		}
	}
}

