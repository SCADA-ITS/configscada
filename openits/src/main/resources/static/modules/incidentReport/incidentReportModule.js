import GenericModule from "../../modules/genericModule.js";
import { util } from "../../lib/util.js";
import IncidentReportActionPanel from "./incidentReportActionPanel.js";
import IncidentReportContent from "./incidentReportContent.js";

export default class IncidentReportModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.currentSequenceOfState = undefined;

		this.incidentReport = undefined;
		this.stompClient = undefined;
		this.requestReport = true;
		this.disconnectStomp = true;
		this.closed = false;
		this.config = module.config;
		this.incidentReportContent = undefined;

		if (this.moduleAction.id === this.config.moduleAction.edit) {

			if (this.args && this.args.selectedPks && this.args.selectedPks.length > 0) {

				this.incidentReportId = this.args.selectedPks[0];
			}
		}
	}

	getView() {

		let view = undefined;

		if (this.moduleAction.id === this.config.moduleAction.edit) {

			return super.getViewCheckModuleActionAndSelectedPks();
		}

		return view;
	}

	loadData() {

		if (this.moduleAction.id === this.config.moduleAction.edit) {

			this.refresh();
		}
		else if (this.moduleAction.id === this.config.moduleAction.confirm) {

			this.actionConfirm();
		}
		else if (this.moduleAction.id === this.config.moduleAction.cancel) {

			this.actionCancel();
		}

	}

	actionConfirm() {

		let self = this;
		let incidentReportsToConfirm = [];

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			let pendingRequest = this.args.selectedPks.length;

			self.args.selectedPks.forEach(e => {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentReport, { id: e })).then(function(dat) {

					if (dat != null) {

						let incidentReport = dat.json();

						if (!incidentReport.confirm) {

							incidentReportsToConfirm.push(incidentReport.id);
						}
					}

					if (--pendingRequest == 0) {

						if (incidentReportsToConfirm.length > 0) {

							webix.confirm({
								title: i18n.t("LBL_CONFIRM"),
								ok: i18n.t("LBL_YES"),
								cancel: i18n.t("LBL_NO"),
								text: i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM_DESCRIPTION"),
								width: 600
							}).then(function() {

								incidentReportsToConfirm.forEach(incidentReportToConfirmId => {

									let endpoint = util.fillTemplate(self.config.actionEndpoints.confirm, { id: incidentReportToConfirmId });

									console.log(endpoint);

									self.restAction(endpoint);

								});
							});
						}
						else {

							webix.alert({
								title: i18n.t("LBL_INCIDENT_CONFIRM_NOTHING_TO_CONFIRM"),
								ok: i18n.t("LBL_ACCEPT"),
								text: i18n.t("LBL_INCIDENT_CONFIRM_NOTHING_TO_CONFIRM_DESCRIPTION"),
								type: "alert-warning",
								width: 600
							});
						}
					}
				});
			});
		}
	}

	actionCancel() {

		let self = this;

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			webix.confirm({
				title: i18n.t("LBL_INCIDENT_CONFIRM_CANCEL"),
				ok: i18n.t("LBL_YES"),
				cancel: i18n.t("LBL_NO"),
				text: i18n.t("LBL_INCIDENT_CONFIRM_CANCEL_DESCRIPTION"),
				width: 600
			}).then(function() {

				self.args.selectedPks.forEach(e => {

					let endpoint = util.fillTemplate(self.config.actionEndpoints.cancel, { id: e });

					console.log(endpoint)

					self.restAction(endpoint);

				});
			});
		}
	}

	refresh() {

		let self = this;

		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		if (this.requestReport) {

			if (this.moduleAction.id === this.config.moduleAction.edit) {

				this.requestReport = false;

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.incidentReport, { id: self.incidentReportId })).then(function(dat) {

					if (dat != null) {

						self.incidentReport = dat.json();

						self.update();
					}

				}, function(response) { util.ajax.handleError(response); });
			}
		}
		else {

			this.update();
		}
	}

	update() {


		if (this.incidentReport) {

			this.currentSequenceOfState = this.incidentReport.sequenceOfStates.find(e => {

				return (e.state.id == this.incidentReport.incidentState.id);
			});

			if (!this.currentSequenceOfState) {

				this.currentSequenceOfState = this.incidentReport.sequenceOfStates[this.incidentReport.sequenceOfStates.length - 1];
			}

			if (this.currentSequenceOfState) {

				this.closed = (this.currentSequenceOfState.state.id === this.incidentReport.sequenceOfStates[this.incidentReport.sequenceOfStates.length - 1].state.id);
				this.currentSequenceOfState.finalState = this.closed;

				this.incidentType = window.em.getByField(EntityType.IMS_INCIDENT_TYPE, "id", this.incidentReport.incidentType.id);

				let container = $$(this.args.containerId);

				if (container) {

					util.ui.setTitle(container.config.id, i18n.t("LBL_INCIDENT_REPORT") + " [" + util.getSimpleId(this.incidentReport.id) + "]" + " - " + this.incidentReport.incidentType.alias);
				}

				this.drawComponents();
			}

			if (this.moduleAction.id === this.config.moduleAction.edit && !this.stompClient) {

				this.callbacks();
			}

			if (this.incidentReportContent) {

				this.incidentReportContent.init();
			}

			$$("loading-" + this.uid).hide();
			$$(this.uid).show();

			if (this.closed) {

				webix.alert({
					title: i18n.t("LBL_INCIDENT_CANCEL_OR_DELETE"),
					ok: i18n.t("LBL_YES"),
					text: i18n.t("LBL_INCIDENT_CANCEL_OR_DELETE_DESCRIPTION"),
					type: "alert-warning",
					width: 600
				});
			}
		}
	}

	callbacks() {

		this.connect_ws();
		this.on_destroy();
	}

	on_destroy() {

		let self = this;
		let view = $$(this.uid);

		if (view) {

			view.attachEvent("onDestruct", function() {

				self.disconnect_ws();
			});
		}
	}

	drawComponents() {

		let self = this;
		this.dtTasks = undefined;

		let incidentReportActionPanel = new IncidentReportActionPanel(self);

		if (!this.incidentReportContent) {

			this.incidentReportContent = new IncidentReportContent(self);
		}

		let viewMain = {
			id: this.uid,
			cols: [
				{
					rows: [
						getViewHeader(),
						{ height: 20 },
						getViewStateTransitions(),
						{ height: 6 },
						this.incidentReportContent.getView(),
						{ height: 6 }
					]
				},
				{ width: 6 },
				incidentReportActionPanel.getView()
			]
		};

		function getViewHeader() {

			let viewHeader;

			viewHeader = {

				id: "viewHeader-" + self.uid,
				borderless: true,
				rows: [
					{
						id: "view-bar-color-level-" + self.uid,
						height: 15,
						css: { "background": self.incidentReport.incidentLevel.color }
					},
					{
						id: "view-header-info-" + self.uid,
						height: 80,
						template: function() {

							let incidentIcon = self.incidentReport.incidentType.icon;

							if (incidentIcon == undefined) {
								incidentIcon = "not_found_50.png";
							}
							return `<image class="ims-incident-report-icon" src="img/${incidentIcon}" />
									<div class="ims-incident-report-title">
										${self.incidentReport.incidentType.alias} ${self.incidentReport.incidentType.description}
									</div>
									<div class="ims-incident-report-title">
										<span class="ims-incident-badge" style="background: ${self.incidentReport.incidentLevel.color}; color: white;">${i18n.t(self.incidentReport.incidentLevel.labelAlias)}</span>
									</div>`;
						}
					}
				]
			};

			return viewHeader;
		}

		function getViewStateTransitions() {

			let tbStates = {
				view: "toolbar",
				css: "ims-incident-report-state-box",
				borderless: true,
				height: 68,
				id: "tb-states" + self.uid,
				elements: []
			};

			let labelNoCss = false;

			let lblState = {
				rows: [
					{
						height: 66,
						css: { "margin-top": "-24px" },
						borderless: true,
						template: function() {

							if (self.incidentReport.confirm) {

								let steps = `<div class="progressbar-wrapper">
										      <ul class="progressbar">`;

								self.incidentReport.sequenceOfStates.forEach(e => {

									steps += `<li style="width: ${100 / self.incidentReport.sequenceOfStates.length}% !important" `;
									if (e.state.id === self.incidentReport.incidentState.id) {

										labelNoCss = true;
										steps += `class="current">`;
									}
									else {

										if (!labelNoCss) {

											steps += `class="active">`;
										}
										else {

											steps += `>`;
										}
									}

									steps += `${i18n.t(e.state.labelAlias)}</li>`;

								});

								steps += `  </ul>
										  </div>`;

								return steps;
							}
							else {

								return `<div></div>`;
							}
						}
					}
				]
			};

			tbStates.elements.push(lblState);

			return lblState;
		}

		webix.ui(viewMain, $$(this.uid));
	}

	restAction(endpoint) {

		console.log("Incident report action => " + endpoint);

		webix.ajax().post(appConfig.restUrl + endpoint).then(undefined, function(response) {

			util.ajax.handleError(response);
		});
	}

	connect_ws() {

		let self = this;

		function on_message(message) {

			var data = JSON.parse(JSON.parse(message.body));

			self.disconnectStomp = false;

			if (data.action === "UPDATE") {

				console.log(data);

				self.requestReport = true;

				self.refresh();
			}
			else if (data.action === "DELETE") {

				console.log(data);

				self.requestReport = false;

				if (Array.isArray(self.incidentReport.sequenceOfStates) && self.incidentReport.sequenceOfStates.length > 0) {

					let lastState = self.incidentReport.sequenceOfStates[self.incidentReport.sequenceOfStates.length - 1];

					self.incidentReport.incidentState = lastState.state;
					self.incidentReport.incidentState.finalState = true;

					self.closed = true;
					self.refresh();
				}
			} else if (data.action === "BLOCK") {

				console.log(data);

				self.requestReport = true;

				$$("loading-" + self.uid).show();
				$$(self.uid).hide();
			}
		}

		let url = appConfig.stompUrl;
		let topic = this.config.topic + "." + this.incidentReportId;

		this.stompClient = util.stomp.connect(url, topic, undefined, on_message);
	}

	disconnect_ws() {

		if (this.disconnectStomp) {

			if (this.stompClient && this.stompClient.connected) {

				util.stomp.disconnect(this.stompClient);
				this.stompClient = undefined;
			}
		}
		else {

			this.disconnectStomp = true;
		}

	}
}
