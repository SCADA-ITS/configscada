import { util } from "../../lib/util.js";
import MessageBox from "../../component/messageBox.js";

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

				let stretchs = [];

				if (self.incidentReport.spath && Array.isArray(self.incidentReport.spath) && self.incidentReport.spath.length > 0) {
					
					stretchs = self.incidentReport.spath.reduce((result, stretch) => {
					
						if (stretch.id != self.incidentReport.affectionStretch.id) {
							
							result.push(stretch.alias + " (" + stretch.road.alias + ")");
						}
						
						return result;
						
					}, []).join();
				}

				const fieldsValues = {
					fieldId: util.getSimpleId(self.incidentReport.id),
					fieldType: self.incidentReport.incidentType.alias + "-" + self.incidentReport.incidentType.description,
					fieldLocation: self.incidentReport.location.alias ? self.incidentReport.location.alias : "-",
					fieldStretchs: stretchs,
					fieldStretch: self.incidentReport.affectionStretch.alias ? self.incidentReport.affectionStretch.alias : "-",
					fieldRoadImpact: self.incidentReport.roadImpact ? self.incidentReport.roadImpact.alias : "-",
					fieldUser: self.incidentReport.currentUser.name ? self.incidentReport.currentUser.name : "-",
					fieldGenerateAt: self.incidentReport.generatedAt ? util.toLocaleString(self.incidentReport.generatedAt / 1000) : "-",
					fieldLastUpdated: self.incidentReport.lastUpdate ? util.toLocaleString(self.incidentReport.lastUpdate / 1000) : "-",
					fieldValidUntil: self.incidentReport.validForMin && self.incidentReport.generatedAt ? util.toLocaleString((self.incidentReport.generatedAt / 1000) + (self.incidentReport.validForMin * 60)) : "-",
					fieldCause: self.incidentReport.incidentTypeCause ? self.incidentReport.incidentTypeCause.alias : "-",
					fieldCauseId: self.incidentReport.incidentTypeCause ? self.incidentReport.incidentTypeCause.id : null,
					fieldRoad: self.incidentReport.affectionStretch.road.alias ? self.incidentReport.affectionStretch.road.alias : "-",
					fieldGroup: self.incidentReport.incidentType.incidentGroup.labelAlias ? i18n.t(self.incidentReport.incidentType.incidentGroup.labelAlias) : "-",
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
										hidden: !self.incidentReport.confirm || self.parent.closed,
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

												let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_IMS_CAUSE_CONFIRM_DESCRIPTION"), onAccept, null);
												ms.show();

												function onAccept() {

													let body = {
														id: self.incidentReport.id,
														causeId: $$("fieldCause-combo-" + self.uid).getValue()
													};

													let endpoint = util.fillTemplate(self.config.actionEndpoints.assignCauseIncidentReport, body);
													self.parent.restAction(endpoint);

													$$("fieldCause-combo-" + self.uid).hide();
													$$("fieldCause-save-" + self.uid).hide();
													$$("fieldCause-cancel-" + self.uid).hide();
													$$("fieldCause-edit-" + self.uid).show();
													$$("fieldCause-label-" + self.uid).show();
												};
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
			css: "ims-incident-report-tab-details bordered-layout",
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