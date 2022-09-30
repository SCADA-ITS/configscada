import { util } from "../../lib/util.js";
import IncidentWizardDrawStep from "./incidentWizardDrawStep.js";
import SearchDialogBox from "../../component/searchDialogBox.js";
import MessageBox from "../../component/messageBox.js";

export default class IncidentWizardDrawStepSummary extends IncidentWizardDrawStep {

	constructor(parent, step) {

		super(parent, step);
	}

	getView() {

		let parent = this.parent;
		let step = this.step;

		let viewStepSummary;

		viewStepSummary = {

			rows: [
				{
					cols: [
						{ width: 45 },
						{
							rows: [
								{ height: 15 },
								{
									view: "label",
									label: `<span class="ico webix_icon mdi mdi-flag-checkered" style="font-size: 30px;"></span><span class="content-wizard-label">${i18n.t(step.stepLabel)}</span>`
								},
								{ height: 17 },
								getViewSummary()
							]
						},
						{ width: 45 }
					]
				}
			]
		};

		function getViewSummary() {

			let viewSummary = {};

			let maxWidthLabels = 610;

			let container = $$(parent.args.containerId);

			if (container) {

				maxWidthLabels = container.config.width / 2;
			}

			viewSummary = {
				cols: [
					{ width: 1, borderless: false, css: { "border-left": "transparent !important" } },
					{
						rows: [
							{ height: 1, borderless: false, css: { "border-bottom": "transparent !important" } },
							{
								cols: [
									{ width: 60, borderless: true },
									{
										id: "summary-" + parent.uid,
										css: "ims-incident-report-tab-details",
										rows: [
											{ height: 30, borderless: true },
											{
												id: "summary-incident-" + parent.uid,
												height: 50,
												borderless: true,
												template: function(obj) {

													let message = '';

													if (Array.isArray(obj.affectionStretchs) && obj.affectionStretchs.length > 1) {

														message = i18n.t("LBL_INCIDENTS_TO_CREATE_PER_AFFECTION_STRETCH") + ": " + obj.affectionStretchs.length;

													}

													return `<div><span class="summary">${message}</span></div>`
												}
											},
											{
												cols: [
													{ width: 30, borderless: true },
													{
														rows: [
															{
																id: "summary-incident-type-" + parent.uid,
																height: 50,
																borderless: true,
																template: function(obj) {
																	return `<div><span class="ico webix_icon mdi mdi-exclamation-thick"></span><span class="field">${i18n.t("LBL_INCIDENT")}</span></div>
																		   <div class="value" style="max-width: ${maxWidthLabels}px;">${(obj.incidentType && obj.incidentTypeDesc) ? obj.incidentType + ' - ' + obj.incidentTypeDesc : ''}</div>`
																}
															},
															{
																id: "summary-level-" + parent.uid,
																height: 50,
																borderless: true,
																template: function(obj) {
																	return `<div><span class="ico webix_icon mdi mdi-signal-cellular-3"></span><span class="field">${i18n.t("LBL_LEVEL")}</span></div>
																		    <div class="ims-incident-report-title" style="position: absolute; padding-left: 226px !important; margin-top: -15px !important;">
																				<span class="ims-incident-badge" style="background: ${obj.levelColor}; color: white;">${obj.level}</span>
																			</div>`
																}
															},
															{
																id: "summary-stretch-" + parent.uid,
																height: 50,
																borderless: true,
																template: function(obj) {

																	let lblAffectionStretchs = '';

																	if (Array.isArray(obj.affectionStretchs)) {

																		lblAffectionStretchs = obj.affectionStretchs.map(e => e.alias).join(',');
																	}

																	return `<div><span class="ico webix_icon mdi mdi-road"></span><span class="field">${obj.affectionStretchs.length > 1 ? i18n.t("LBL_INCIDENT_AFFECTION_STRETCHS") : i18n.t("LBL_INCIDENT_STRETCH")}</span></div>
																		   <div class="value" style="max-width: ${maxWidthLabels}px;">${lblAffectionStretchs}</div>`
																}
															},
															{
																id: "summary-road-impact-" + parent.uid,
																height: 50,
																borderless: true,
																template: function(obj) {

																	let roadImpact = obj.roadImpact;

																	let summaryRoadImpact = `<div>
																								<span class="ico webix_icon mdi mdi-selection"></span>
																								<span class="field">${i18n.t("LBL_INCIDENT_ROAD_IMPACT")}</span>
																							</div>`;


																	//																	if (Array.isArray(parent.incidentData.roadImpacts) && parent.incidentData.roadImpacts.length > 0) {

																	summaryRoadImpact += `<div class="value" style="max-width: ${maxWidthLabels}px;">${roadImpact && roadImpact.alias ? roadImpact.alias : ''}
																								<span title='${i18n.t('LBL_EDIT')}' class='change-road-impact-button webix_icon mdi mdi-pencil'></span>
																							</div>`;
																	//																	}
																	//																	else {
																	//																		
																	//																		summaryRoadImpact += `<div class="value" style="max-width: ${maxWidthLabels}px;">${i18n.t("LBL_UNAVAILABLE")}</div>`;
																	//																	}

																	return summaryRoadImpact;
																},
																on: {
																	onAfterRender: function() {

																		let changeRoadImpactButton = this.getNode().querySelector(".change-road-impact-button");

																		if (changeRoadImpactButton) {

																			changeRoadImpactButton.addEventListener("click", function() {

																				let dialogRoadImpacts = webix.copy(SearchDialogBox.CUSTOM);
																				let label = i18n.t("LBL_ROAD_IMPACTS");

																				dialogRoadImpacts.data = [];

																				dialogRoadImpacts.pkField = "id"
																				dialogRoadImpacts.showEmptyRowCol = SearchDialogBox.ID_NOT_SELECTED;
																				dialogRoadImpacts.fields = ["alias=LBL_ALIAS", "description=LBL_DESCRIPTION"];
																				dialogRoadImpacts.sortFieldId = 1;

																				if (parent.incidentData.stretchId && parent.incidentData.incidentTypeId) {

																					webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.incidentTypeStretchs, { incidentTypeId: parent.incidentData.incidentTypeId, stretchId: parent.incidentData.stretchId })).then(

																						function(responseIncidentTypeStretchs) {

																							if (responseIncidentTypeStretchs != null) {

																								let incidentTypeStretch = responseIncidentTypeStretchs.json();

																								if (incidentTypeStretch && Array.isArray(incidentTypeStretch.roadImpacts) && incidentTypeStretch.roadImpacts.length > 0) {

																									incidentTypeStretch.roadImpacts.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));

																									incidentTypeStretch.roadImpacts.forEach(p => {

																										dialogRoadImpacts.data.push({
																											id: p.id,
																											alias: p.alias,
																											description: p.description
																										})
																									});

																									let s = new SearchDialogBox(label, dialogRoadImpacts, function(items) {

																										if (Array.isArray(items) && items.length === 1) {

																											let roadImpact = items[0];

																											if (roadImpact.id === SearchDialogBox.ID_NOT_SELECTED) {

																												parent.incidentData.roadImpact = undefined;
																												$$("summary-road-impact-" + parent.uid).setValues({
																													roadImpact: undefined
																												});
																											}
																											else {

																												parent.incidentData.roadImpact = roadImpact;

																												$$("summary-road-impact-" + parent.uid).setValues({
																													roadImpact: roadImpact
																												});
																											}
																										}
																									});
																									s.show();
																								} else {
																									
																									(new MessageBox(MessageBox.INFO, i18n.t("LBL_ROAD_IMPACT_NOT_AVAILABLE"), null, null, null)).show();
																								}
																							}
																						});
																				}
																			});
																		}
																	}
																}
															},
															{
																id: "summary-location-" + parent.uid,
																height: 50,
																borderless: true,
																template: function(obj) {
																	return `<div><span class="ico webix_icon mdi mdi-map-marker"></span><span class="field">${i18n.t("LBL_INCIDENT_LOCATION")}</span></div>
																		   <div class="value" style="max-width: ${maxWidthLabels}px;">${obj.location ? obj.location : ''}</div>`
																}
															},
															{
																id: "summary-user-" + parent.uid,
																height: 50,
																borderless: true,
																template: function(obj) {

																	let user = obj.user ? obj.user : "";

																	if ((parent.autoAssignPermissions || parent.assignAnotherPermissions) && !obj.userUpdate) {

																		let userData = util.user.getUserData();
																		user = userData.name;
																		parent.incidentData.user = userData.name;
																		parent.incidentData.userId = userData.id;
																	}

																	let summaryUser = `<div>
																							<span class="ico webix_icon mdi mdi-account"></span>
																							<span class="field">${i18n.t("LBL_INCIDENT_USER_ASSIGNED")}</span>
																						</div>`;

																	if (parent.assignAnotherPermissions) {

																		summaryUser += `<div class="value" style="max-width: ${maxWidthLabels}px;">${user}<span title='${i18n.t('LBL_CMD_ASSIGN')}' class='changeuser-button webix_icon mdi mdi-pencil'></span></div>`;

																	} else {

																		summaryUser += `<div class="value" style="max-width: ${maxWidthLabels}px;">${user}</div>`;
																	}

																	return summaryUser;

																},
																on: {
																	onAfterRender: function() {

																		let changeUserButton = this.getNode().querySelector(".changeuser-button");

																		if (changeUserButton) {

																			changeUserButton.addEventListener("click", function() {

																				let s = new SearchDialogBox(i18n.t("LBL_SELECT_USER"), SearchDialogBox.USERS, function(obj) {

																					if (obj[0].id != SYSTEM_PROFILES.USER) {

																						$$("summary-user-" + parent.uid).setValues({
																							user: obj[0].name,
																							userUpdate: true
																						});

																						parent.incidentData.user = obj[0].name;
																						parent.incidentData.userId = obj[0].id;

																					} else {

																						let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_NOT_ALLOWED_TO_ASSIGN_SYSTEM_USER_DESCRIPTION"), null, null, null);
																						ms.show();
																					}
																				});

																				s.show();
																			});
																		}
																	}
																}
															}
														]
													}
												]
											},
											{}
										]
									},
									{ width: 60, borderless: true }
								]
							},
							{ height: 1, borderless: false, css: { "border-bottom": "transparent !important" } }
						]
					},
					{ width: 1, borderless: false, css: { "border-right": "transparent !important" } }
				]
			}

			return viewSummary;
		}

		return viewStepSummary;
	}
}	
