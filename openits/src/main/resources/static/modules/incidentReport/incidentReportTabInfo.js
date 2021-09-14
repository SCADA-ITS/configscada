import { util } from "../../lib/util.js";

export default class IncidentReportTabInfo {

	constructor(parent) {
		
		this.parent = parent;
		this.uid = parent.uid;
		this.config = parent.config;
		this.incidentReport = parent.incidentReport;
	}

	getView() {

		let self = this;

		function getConfigRows() {

			let body = []

			if (self.config.infoFields !== undefined && self.config.infoFields.length > 0) {

				let objField = self.config.infoFields.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

				const fieldsValues = {
					fieldId: util.getSimpleId(self.incidentReport.id),
					fieldType: self.incidentReport.incidentType.alias + "-" + self.incidentReport.incidentType.description,
					fieldLocation: self.incidentReport.location.alias !== undefined && self.incidentReport.location.alias !== null ? self.incidentReport.location.alias : "-",
					fieldStretch: self.incidentReport.affectionStretch.alias !== undefined && self.incidentReport.affectionStretch.alias !== null ? self.incidentReport.affectionStretch.alias : "-",
					fieldUser: self.incidentReport.currentUser.name !== undefined && self.incidentReport.currentUser.name !== null ? self.incidentReport.currentUser.name : "-",
					fieldGenerateAt: self.incidentReport.generatedAt !== undefined && self.incidentReport.generatedAt !== null ? util.toLocaleString(self.incidentReport.generatedAt / 1000) : "-",
					fieldLastUpdated: self.incidentReport.lastUpdate !== undefined && self.incidentReport.lastUpdate !== null ? util.toLocaleString(self.incidentReport.lastUpdate / 1000) : "-",
					fieldValidUntil: self.incidentReport.validForMin !== undefined && self.incidentReport.validForMin !== null ? util.toLocaleString(self.incidentReport.validForMin / 1000) : "-",
					fieldCause: self.incidentReport.incidentTypeCause !== undefined && self.incidentReport.incidentTypeCause !== null ? self.incidentReport.incidentTypeCause.alias : "-",
					fieldCauseId: self.incidentReport.incidentTypeCause !== undefined && self.incidentReport.incidentTypeCause !== null ? self.incidentReport.incidentTypeCause.id : null,
					fieldRoad: self.incidentReport.affectionStretch.road.alias !== undefined && self.incidentReport.affectionStretch.road.alias !== null ? self.incidentReport.affectionStretch.road.alias : "-",
					fieldGroup: self.incidentReport.incidentType.incidentGroup.labelAlias !== undefined && self.incidentReport.incidentType.incidentGroup.labelAlias !== null ? i18n.t(self.incidentReport.incidentType.incidentGroup.labelAlias) : "-",
				}

				objField.forEach(e => {

					if (e.objField === "fieldCause") {

						let ireportType = window.em.getByField(EntityType.IMS_INCIDENT_TYPE, "id", self.incidentReport.incidentType.id);
						let causesOptions = [];

						if (Array.isArray(ireportType.incidentTypeCauses) && ireportType.incidentTypeCauses.length > 0) {

							ireportType.incidentTypeCauses.forEach(item => {
								causesOptions.push({ id: item.id, value: item.alias });
							});

							body.push({
								height: 27,
								borderless: true,
								cols: [
									{
										view: "label",
										width: 400,
										template: `<div><span class="${e.icon}"></span><span class="field">${i18n.t(e.label)}</span></div>`
									},
									{
										view: "label",
										id: "fieldCause-label-" + self.uid,
										width: 0,
										template: `<div class="value">${fieldsValues[e.objField]}</div>`
									},
									{
										view: "combo",
										id: "fieldCause-combo-" + self.uid,
										value: fieldsValues[e.objField] !== "-" ? fieldsValues["fieldCauseId"] : "",
										required: true,
										labelWidth: 200,
										width: 0,
										hidden: true,
										options: causesOptions
									},
									{ width: 50 },
									{
										view: "icon",
										id: "fieldCause-edit-" + self.uid,
										icon: "mdi mdi-pencil",
										//width: 0,
										on: {
											onItemClick: function() {
												$$("fieldCause-combo-" + self.uid).show();
												$$("fieldCause-save-" + self.uid).show();
												$$("fieldCause-cancel-" + self.uid).show();
												$$("fieldCause-label-" + self.uid).hide();
												$$("fieldCause-edit-" + self.uid).hide();
											}
										}
									},
									{
										view: "icon",
										id: "fieldCause-save-" + self.uid,
										hidden: true,
										icon: "mdi mdi-content-save",
										width: 30,
										on: {
											onItemClick: function() {

												webix.confirm({
													title: i18n.t("LBL_IMS_CAUSE_CONFIRM"),
													ok: i18n.t("LBL_YES"),
													cancel: i18n.t("LBL_NO"),
													text: i18n.t("LBL_IMS_CAUSE_CONFIRM_DESCRIPTION"),
													width: 600
												}).then(function() {

													let body = {
														id: self.incidentReport.id,
														causeId: $$("fieldCause-combo-" + self.uid).getValue()
													};

													let endpoint = util.fillTemplate(self.config.actionEndpoints.assignCauseIncidentReport, body);
													self.parent.restAction(endpoint);
													$$("loading-" + self.uid).show();
													$$(self.uid).hide();
													
													$$("fieldCause-combo-" + self.uid).hide();
													$$("fieldCause-save-" + self.uid).hide();
													$$("fieldCause-cancel-" + self.uid).hide();
													$$("fieldCause-edit-" + self.uid).show();
													$$("fieldCause-label-" + self.uid).show();

												});
											}
										}
									},
									{
										view: "icon",
										id: "fieldCause-cancel-" + self.uid,
										hidden: true,
										icon: "mdi mdi-close",
										width: 30,
										on: {
											onItemClick: function() {

												$$("fieldCause-combo-" + self.uid).hide();
												$$("fieldCause-save-" + self.uid).hide();
												$$("fieldCause-cancel-" + self.uid).hide();
												$$("fieldCause-edit-" + self.uid).show();
												$$("fieldCause-label-" + self.uid).show();

											}
										}
									},
									{}
								]
							});
							body.push({
								height: 10
							});
						}

					} else {
						body.push(
							{
								height: 27,
								borderless: true,
								cols: [
									{
										view: "label",
										width: 400,
										template: `<div><span class="${e.icon}"></span><span class="field">${i18n.t(e.label)}</span></div>`
									},
									{
										view: "label",
										width: 0,
										align: "left",
										template: `<div class="value">${fieldsValues[e.objField]}</div>`
									},
									{}
								]
							}
						);
						body.push({
							height: 10
						});
					}

				});

			}

			return body

		}

		let tabDetails = {
			id: "tab-details-" + self.uid,
			borderless: true,
			css: "ims-incident-report-tab-details",
			rows: [
				{ height: 40 },
				{
					cols: [
						{ width: 40 },
						{
							borderless: true,
							rows: getConfigRows()
						},
						{ width: 40 }
					]
				},
				{}
			]
		};

		return tabDetails;
	}

}