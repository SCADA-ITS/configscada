import { util } from "../../lib/util.js";
import GenericModule from "../../modules/genericModule.js";
import IncidentReportActionPanel from "./incidentReportActionPanel.js";
import IncidentReportContent from "./incidentReportContent.js";
import MessageBox from "../../component/messageBox.js";
import SearchDialogBox from "../../component/searchDialogBox.js";
import StompSubscriptorService from "../../services/stompSubscriptorService.js";

export default class IncidentReportModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.currentSequenceOfState = undefined;

		this.incidentReport = undefined;
		this.stompClient = undefined;
		this.idStompSubscriptor = undefined;
		this.disconnectStomp = true;
		this.closed = false;
		this.config = module.config;
		this.incidentReportContent = undefined;
		
		this.userData = util.user.getUserData();
		this.isUpdating = false;
		
		this.autoAssignPermissions = util.module.isAllowedPermission(this.config.requiredPermissions.autoassign);
		this.assignAnotherPermissions = util.module.isAllowedPermission(this.config.requiredPermissions.assignAnother);

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

			this.request();
		}
		else if (this.moduleAction.id === this.config.moduleAction.confirm) {

			this.actionConfirm();
		}
		else if (this.moduleAction.id === this.config.moduleAction.cancel) {

			this.actionCancel();
		} 
		else if (this.moduleAction.id === this.config.moduleAction.assign) {
			
			this.actionAssign();
		}

	}

	actionConfirm() {

		let self = this;
		let incidentReportsToConfirm = [];
		let sameUserCounter = 0;

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			let pendingRequest = this.args.selectedPks.length;

			self.args.selectedPks.forEach(e => {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentReport, { id: e })).then(function(dat) {

					if (dat != null) {

						let incidentReport = dat.json();
						
						if (!incidentReport.confirm) {

							incidentReportsToConfirm.push(incidentReport.id);
							sameUserCounter = (incidentReport.currentUser.id === self.userData.id)  ? ++sameUserCounter : sameUserCounter;
						}
					}

					if (--pendingRequest == 0) {

						if (incidentReportsToConfirm.length > 0) {

							if (sameUserCounter === incidentReportsToConfirm.length) {

								let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM_DESCRIPTION"), onAccept, null);
								ms.show();

								function onAccept() {

									incidentReportsToConfirm.forEach(incidentReportToConfirmId => {

										let endpoint = util.fillTemplate(self.config.actionEndpoints.confirm, { id: incidentReportToConfirmId });

										console.log(endpoint);

										self.restAction(endpoint);

									});
								};

							} else {

								let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_INCIDENT_NOT_ASSIGNED_TO_USER_DESCRIPTION"), null, null);
								ms.show();

							}

						} else {

							let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_INCIDENT_CONFIRM_NOTHING_TO_CONFIRM_DESCRIPTION"), null, null);
							ms.show();
						}
					} 
				});
			});
		}
	}

	actionCancel() {

		let self = this;
		let incidentReportsToDelete = [];
		let sameUserCounter = 0;

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			let pendingRequest = this.args.selectedPks.length;

			self.args.selectedPks.forEach(e => {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentReport, { id: e })).then(function(dat) {

					if (dat != null) {

						let incidentReport = dat.json();

						if (incidentReport) {

							incidentReportsToDelete.push(incidentReport.id);
							sameUserCounter = (incidentReport.currentUser.id === self.userData.id) ? ++sameUserCounter : sameUserCounter;
						}
					}

					if (--pendingRequest == 0) {

						if (incidentReportsToDelete.length > 0) {

							if (sameUserCounter === incidentReportsToDelete.length) {

								let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CANCEL_DESCRIPTION"), onAccept, null);
								ms.show();

								function onAccept() {

									incidentReportsToDelete.forEach(e => {

										let endpoint = util.fillTemplate(self.config.actionEndpoints.cancel, { id: e });

										console.log(endpoint)

										self.restAction(endpoint);

									});
								};

							} else {

								let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_INCIDENT_NOT_ASSIGNED_TO_USER_DESCRIPTION"), null, null);
								ms.show();
							}

						} else {

							let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_INCIDENT_CONFIRM_NOTHING_TO_CONFIRM_DESCRIPTION"), null, null);
							ms.show();
						}
					}
				});
			});
		}
	}


	actionAssign() {

		let self = this;

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			if (self.autoAssignPermissions || self.assignAnotherPermissions) {

				let onlyAutoassignIncident = (self.autoAssignPermissions && !self.assignAnotherPermissions) ? true : false;

				if (onlyAutoassignIncident) {

					let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_AUTOASSIGN_CONFIRM_DESCRIPTION"), onAccept, null);
					ms.show();

				} else {

					let s = new SearchDialogBox(i18n.t("LBL_SELECT_USER"), SearchDialogBox.USERS, changeUser);
					s.show();
				}

			} else {

				let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_USER_WITHOUT_PERMISSION_TO_ASSIGN_INCIDENTS_DESCRIPTION"), null, null);
				ms.show();
			}
			
			function onAccept() {

				let incidentReportsToAssign = 0;

				let pendingRequest = self.args.selectedPks.length;

				self.args.selectedPks.forEach(e => {

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentReport, { id: e })).then(function(dat) {

						if (dat != null) {

							let incident = dat.json();

							if (incident) {

								let currentUser = window.em.getByField(EntityType.USER, "id", incident.currentUser.id);
								let currentUserRole = currentUser.role.id;

								if (currentUserRole === self.userData.role.id) {

									incidentReportsToAssign++;
								}
							}
						}

						if (--pendingRequest == 0) {

							if (incidentReportsToAssign === self.args.selectedPks.length) {

								changeUser();

							} else {

								let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_ACTUAL_USER_WITH_DIFFERENT_ROLE_DESCRIPTION"), null, null);
								ms.show();
							}
						}
					});
				});
			}
			
			function changeUser(data) {
			
				let userId = (data) ? data[0].id : self.userData.id;

				if (userId != SYSTEM_PROFILES.USER) {

					self.args.selectedPks.forEach(incident => {

						let body = {
							id: incident,
							userId: userId
						};

						let endpoint = util.fillTemplate(self.config.actionEndpoints.assignUserIncidentReport, body);
						self.restAction(endpoint);
					});

				} else {

					let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_NOT_ALLOWED_TO_ASSIGN_SYSTEM_USER_DESCRIPTION"), null, null);
					ms.show();
				}

			}
		}
	}
	
	
	request() {

		let self = this;

		$$("loading-" + this.uid).show();
		$$(this.uid).hide();
		log.debug("loading...");

		if (this.moduleAction.id === this.config.moduleAction.edit) {

			log.debug("request incident report = " + self.incidentReportId);
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.incidentReport, { id: self.incidentReportId })).then(function(dat) {
				
				log.debug("response incident report = " + self.incidentReportId);

				if (dat != null) {

					self.incidentReport = dat.json();

					self.update();
				}

			}, function(response) { util.ajax.handleError(response); });
		}
	}

	update() {

		this.isUpdating = true;
		
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

			if (this.incidentReportContent) {

				this.incidentReportContent.init();
			}

			$$("loading-" + this.uid).hide();
			$$(this.uid).show();
			log.debug("loading hide");
			
			this.isUpdating = false;
			
			if (this.moduleAction.id === this.config.moduleAction.edit && !this.stompClient) {

				this.callbacks();
			}

			if (this.closed) {

				let ms = new MessageBox (MessageBox.WARN, i18n.t("LBL_INCIDENT_CANCEL_OR_DELETE_DESCRIPTION"), null, null);
				ms.show();
			}
		}
		
		self.isUpdating = false;
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

				if (!self.isUpdating) {
					
					self.disconnect_ws();	
				}
				
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
								incidentIcon = "undefined.png";
							}
							return `<image class="ims-incident-report-icon" src="img/incident_type/${incidentIcon}" />
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
		
		if (!this.idStompSubscriptor) {
			
			let url = appConfig.stompUrl;
			let topic = this.config.topic + "." + this.incidentReportId;
	
			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
		
			if (stompSubscriptorService) {
	
				this.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, on_message);
			}	
		}

		function on_message(be, action, timestamp) {

			//var data = JSON.parse(JSON.parse(message.body));
			
			let data = {
				basicElementsVO: [be],
				action: action,
				timestamp: timestamp
			}
			
			self.disconnectStomp = false;
			
			log.debug(JSON.stringify(data));

			if (action === StompSubscriptorService.ACTION_UPDATE) {

				self.request();
			}
			else if (action === StompSubscriptorService.ACTION_DELETE) {

				if (Array.isArray(self.incidentReport.sequenceOfStates) && self.incidentReport.sequenceOfStates.length > 0) {

					let lastState = self.incidentReport.sequenceOfStates[self.incidentReport.sequenceOfStates.length - 1];

					self.incidentReport.incidentState = lastState.state;
					self.incidentReport.incidentState.finalState = true;

					self.closed = true;
					self.update();
				}
			} 
			else if (action === StompSubscriptorService.ACTION_BLOCK) {

				let loadingView = $$("loading-" + self.uid);
				let mainView = $$(self.uid);
				
				if (loadingView && mainView) {
					
					$$("loading-" + self.uid).show();
					$$(self.uid).hide();
					log.debug("loading...");	
				}
			}
		}
	}

	disconnect_ws() {

//		if (this.disconnectStomp) {
//
//			if (this.stompClient && this.stompClient.connected) {
//
//				util.stomp.disconnect(this.stompClient);
//				this.stompClient = undefined;
//			}
//		}
//		else {
//
//			this.disconnectStomp = true;
//		}

		if (this.idStompSubscriptor) {
			
			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				stompSubscriptorService.unsubscribe(this.idStompSubscriptor);
			}
		}

	}
}
