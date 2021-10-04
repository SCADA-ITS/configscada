import IncidentWizardDrawStep from "./incidentWizardDrawStep.js";
import IncidentWizardModule from "./incidentWizardModule.js";
import DataTable from "../../component/datatable.js";
import ListView from "../../component/listview.js";
import { util } from "../../lib/util.js";

export default class IncidentWizardDrawStepIncidentType extends IncidentWizardDrawStep {

	constructor(parent, step) {

		super(parent, step);
	}
	
	getView() {
		
		let parent = this.parent;
		let step = this.step;
		
		let viewStepIncidentType;
			
		viewStepIncidentType = {

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

					webix.extend($$("view-incident-types-" + parent.uid), webix.ProgressBar);

					parent.dtIncidentTypes.clear();

					if (Array.isArray(data) && data.length > 0) {

						$$("view-incident-types-" + parent.uid).showProgress({
							type: "bottom",
							hide: false
						});

						let restUrl;
						if (parent.incidentData.stretchId) {
							restUrl = appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.incidentTypesByStretch, { id: parent.incidentData.stretchId });
						} else {
							restUrl = appConfig.restUrl + parent.config.restEndpoints.incidentTypes;
						}

						webix.ajax().get(restUrl).then(function(dat) {

							if (dat != null) {

								let incidentTypesByStretch = dat.json();

								if (Array.isArray(incidentTypesByStretch) && incidentTypesByStretch.length > 0) {

									let incidentTypes = window.em.getAll(EntityType.IMS_INCIDENT_TYPE);

									let incidentTypesByGroup = incidentTypes.filter(e => e.incidentGroup.id === data[0]);

									if (Array.isArray(incidentTypesByGroup) && incidentTypesByGroup.length > 0) {

										let incidentTypesByGroupFiltered = incidentTypesByGroup.filter(itByGroup => {

											return (incidentTypesByStretch.find(itByStretch => itByStretch.id === itByGroup.id) != undefined);
										});

										let dataIncidentTypes = incidentTypesByGroupFiltered.map(e => {

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

										parent.dtIncidentTypes.add(dataIncidentTypes);
									}
								}
							}

							$$("view-incident-types-" + parent.uid).hideProgress();

							if (callbackEnable) {

								callbackEnable(true);
							}
						});
					}

					parent.incidentWizardAction.validateStep(parent.incidentWizardAction.getCurrentStep());
				};

				parent.listViewIncidentGroups = new ListView(incidentGroupData, options, events);

				viewIncidentGroups = {
					id: "view-incident-groups-" + parent.uid,
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
										parent.listViewIncidentGroups.getView(),
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
					{ id: 1, isPk: true, position: 1, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_TYPE_ID, visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
					{ id: 2, isPk: false, position: 2, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_LEVEL_ID, visible: false, widthPx: 15, gridFieldDataType: { id: 1, name: "string" } },

					{ id: 3, isPk: false, position: 3, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_LEVEL_ICON, visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
					{ id: 4, isPk: false, position: 4, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_LEVEL_COLOR, visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
					{ id: 5, isPk: false, position: 5, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_TYPE_ALIAS, label: "LBL_CODE", visible: true, widthPx: 100, gridFieldDataType: { id: 1, name: "string" } },
					{
						id: 6, isPk: false, position: 6, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_LEVEL_LABEL_ALIAS, label: "LBL_LEVEL", visible: true, widthPx: 200, gridFieldDataType: { id: 1, name: "string" },
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
					{ id: 7, isPk: false, position: 7, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_TYPE_DESCRIPTION, label: "LBL_INCIDENT", visible: true, widthPx: 480, gridFieldDataType: { id: 1, name: "string" } },
					{ id: 8, isPk: false, position: 8, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_USER_ID, visible: false, widthPx: 300, gridFieldDataType: { id: 1, name: "string" } },
					{ id: 9, isPk: false, position: 9, required: true, sourceField: IncidentWizardModule.FIELD_INCIDENT_USER, visible: false, widthPx: 300, gridFieldDataType: { id: 1, name: "string" } }
				]
			};

			let events = {};

			events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

				if (Array.isArray(rows) && rows.length > 0) {

					parent.incidentWizardAction.validateStep(parent.incidentWizardAction.getCurrentStep());

				}
			};

			parent.dtIncidentTypes = new DataTable(null, null, metadata, [], events);

			viewIncidentTypes = {
				id: "view-incident-types-" + parent.uid,
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
								rows: parent.dtIncidentTypes.getView()
							}
						]
					},
					{ height: 1, borderless: true }
				]
			};

			return viewIncidentTypes;
		}

		return viewStepIncidentType;
	}
}	
