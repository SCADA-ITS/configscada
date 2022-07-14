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
		this.spaths = undefined;
		this.stretchsInfo = undefined;
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
		this.confirmIncidentPermissions = util.module.isAllowedPermission(this.config.requiredPermissions.confirmIncident);

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
		let currentUser, sameUser, currentUserRole, sameRole, incidentReport = undefined;
		let sameRoleCounter = 0;
		let sameUserCounter = 0;

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			let pendingRequest = this.args.selectedPks.length;

			self.args.selectedPks.forEach(e => {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentReportSummary, { id: e })).then(function(dat) {

					if (dat != null) {

						incidentReport = dat.json();

						if (!incidentReport.confirm) {

							currentUser = window.em.getByField(EntityType.USER, "id", incidentReport.currentUser.id);
							currentUserRole = currentUser.role.id;
							sameUser =(self.userData.id === currentUser.id);
							sameRole = (self.userData.role.id === currentUserRole);
							incidentReportsToConfirm.push(incidentReport.id);
							sameRoleCounter = (sameRole) ? ++sameRoleCounter : sameRoleCounter;
							sameUserCounter = (sameUser) ? ++sameUserCounter : sameUserCounter;							
						}
					}

					if (--pendingRequest == 0) {

						if (incidentReportsToConfirm.length > 0) {

							if (sameUserCounter === incidentReportsToConfirm.length) {

								let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM_DESCRIPTION"), onAccept, null);
								ms.show();

							} else if ((self.autoAssignPermissions && !self.assignAnotherPermissions) && (sameRoleCounter === incidentReportsToConfirm.length) || self.assignAnotherPermissions) {

								let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM_DESCRIPTION"), function() {

									onAccept(true);
								}, null);
								ms.show();

							} else {

								let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_INCIDENT_CONFIRM_NOT_POSSIBLE_TO_CONFIRM_DESCRIPTION"), null, null);
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

		function onAccept(changeUser) {

			incidentReportsToConfirm.forEach(incidentId => {

				let userId = (changeUser) ? self.userData.id : null;

				let body = {
					id: incidentId,
					userId: userId
				};

				let endpoint = util.fillTemplate(self.config.actionEndpoints.confirm, body);

				console.log(endpoint);

				self.restAction(endpoint);
			});
		}
	}

	actionCancel() {

		let self = this;
		let incidentReportsToDelete = [];
		let currentUser, sameUser, currentUserRole, sameRole, incidentReport = undefined;
		let sameRoleCounter = 0;
		let sameUserCounter = 0;
		let incidentConfirmed = 0;

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			let pendingRequest = this.args.selectedPks.length;

			self.args.selectedPks.forEach(e => {
				
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentReportSummary, { id: e })).then(function(dat) {

					if (dat != null) {

						incidentReport = dat.json();

						if (incidentReport) {

							currentUser = window.em.getByField(EntityType.USER, "id", incidentReport.currentUser.id);
							currentUserRole = currentUser.role.id;
							sameUser = (self.userData.id === currentUser.id);
							sameRole = (self.userData.role.id === currentUserRole);
							sameRoleCounter = (sameRole) ? ++sameRoleCounter : sameRoleCounter;
							sameUserCounter = (sameUser) ? ++sameUserCounter : sameUserCounter;
							incidentConfirmed = (incidentReport.confirm) ? ++incidentConfirmed : incidentConfirmed;				
							incidentReportsToDelete.push(incidentReport.id);
						} 
					}

					if (--pendingRequest == 0) {

						if (incidentReportsToDelete.length > 0) {

							if (sameUserCounter === incidentReportsToDelete.length) {

								let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CANCEL_DESCRIPTION"), onAccept, null);
								ms.show();

							} else if ((self.autoAssignPermissions || self.assignAnotherPermissions) && (incidentConfirmed === 0)) {

								if (((self.autoAssignPermissions && !self.assignAnotherPermissions) && (sameRoleCounter === incidentReportsToDelete.length)) || self.assignAnotherPermissions) {

									let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CANCEL_DESCRIPTION"), function() {

										onAccept(true);
									}, null);
									ms.show();

								} else {

									let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_INCIDENT_CONFIRM_NOT_POSSIBLE_TO_CANCEL_DESCRIPTION"), null, null);
									ms.show();
								}

							} else {

								let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_INCIDENT_CONFIRM_NOT_POSSIBLE_TO_CANCEL_DESCRIPTION"), null, null);
								ms.show();
							}

						} else {

							let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_INCIDENT_CONFIRM_NOTHING_TO_CANCEL_DESCRIPTION"), null, null);
							ms.show();
						}
					}
				});
			});
		}

		function onAccept(changeUser) {

			incidentReportsToDelete.forEach(incidentId => {

				let userId = (changeUser) ? self.userData.id : null;

				let body = {
					id: incidentId,
					userId: userId
				};

				let endpoint = util.fillTemplate(self.config.actionEndpoints.cancel, body);

				console.log(endpoint);

				self.restAction(endpoint);
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

					let s = new SearchDialogBox(i18n.t("LBL_SELECT_USER"), SearchDialogBox.USERS, onAccept);
					s.show();
				}

			} else {

				let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_USER_WITHOUT_PERMISSION_TO_ASSIGN_INCIDENTS_DESCRIPTION"), null, null);
				ms.show();

			}
			
			function onAccept(data) {

				let incidentReportsToAssign = 0;
				let pendingRequest = self.args.selectedPks.length;

				self.args.selectedPks.forEach(e => {

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentReportSummary, { id: e })).then(function(dat) {

						if (dat != null) {

							let incident = dat.json();

							if (incident) {

								let currentUser = window.em.getByField(EntityType.USER, "id", incident.currentUser.id);
								let currentUserRole = currentUser.role.id;

								if ((currentUserRole === self.userData.role.id) || self.assignAnotherPermissions) {

									incidentReportsToAssign++;
								}
							}
						}

						if (--pendingRequest == 0) {

							if (incidentReportsToAssign === self.args.selectedPks.length)  {

								changeUser(data);

							} else {

								let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_INCIDENT_CONFIRM_NOT_POSSIBLE_TO_ASSIGN_DESCRIPTION"), null, null);
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
		let mainContent = $$("main-content-" + self.uid);
		let loadingViewBlur = $$("loading-blur-" + self.uid);

		if (loadingViewBlur && mainContent) {

			loadingViewBlur.show();
			webix.html.addCss(mainContent.$view, "main-screen-locked-firefox");
			log.debug("loading...");
		}

		if (this.moduleAction.id === this.config.moduleAction.edit) {

			log.debug("request incident report = " + self.incidentReportId);
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.incidentReport, { id: self.incidentReportId })).then(function(dat) {
				
				log.debug("response incident report = " + self.incidentReportId);

				if (dat != null) {

					self.incidentReport = dat.json();
					
					assignSpaths();
					
					self.update();
				}

			}, function(response) { util.ajax.handleError(response); });
		}
		
		function assignSpaths() {
			
			let spathIds = [];
			
			if (self.incidentReport) {
				
				if (Array.isArray(self.incidentReport.sequenceOfStates) && self.incidentReport.sequenceOfStates.length > 0) {
					
					self.incidentReport.sequenceOfStates.forEach(sequenceOfState => {
						
						if (Array.isArray(sequenceOfState.taskStates) && sequenceOfState.taskStates.length > 0) {
							
							sequenceOfState.taskStates.forEach(taskState => {
								
								spathIds.push(...getSpathIds(taskState));	
							});
						}
					});
				}
				
				if (Array.isArray(self.incidentReport.taskStates) && self.incidentReport.taskStates.length > 0) {
					
					self.incidentReport.taskStates.forEach(taskState => {
								
						spathIds.push(...getSpathIds(taskState));	
					});
				}
				
				if (Array.isArray(spathIds) && spathIds.length > 0) {
				
					webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.spaths, JSON.stringify(spathIds)).then(function(data) {
	
						let responseSpaths = data.json();
		
						if (Array.isArray(responseSpaths) && responseSpaths.length > 0) {
							
							self.spaths = responseSpaths;
						}
					});	
				}
			}
			
			function getSpathIds(taskState) {
				
				let taskStateSpathIds = [];
				
				if (taskState && taskState.incidentTypeTask && taskState.incidentTypeTask.taskType && 
					Array.isArray(taskState.incidentTypeTask.taskType.taskTypeParams) && taskState.incidentTypeTask.taskType.taskTypeParams.length > 0 &&
					Array.isArray(taskState.incidentTypeTask.incidentTypeTaskValues) && taskState.incidentTypeTask.incidentTypeTaskValues.length > 0) {
					
					if (taskState.incidentTypeTask.taskType.taskTypeParams.find(taskTypeParam => taskTypeParam.paramType.id === "ParamType:1")) {
						
						let incidentTypeTaskValues = taskState.incidentTypeTask.incidentTypeTaskValues.filter(incidentTypeTaskValue => incidentTypeTaskValue.taskTypeParam.id === "ImsTaskTypeParam:1:1");
						
						if (Array.isArray(incidentTypeTaskValues) && incidentTypeTaskValues.length > 0) {
							
							incidentTypeTaskValues.forEach(incidentTypeTaskValue => {
								
								let signallingSpaths;
								
								if (incidentTypeTaskValue.value) {
									
									try {
										
										signallingSpaths = JSON.parse(incidentTypeTaskValue.value);
										
										if (Array.isArray(signallingSpaths)) {
											
											signallingSpaths.forEach(signallingSpath => {
												
												if (signallingSpath.spathId) {
													
													taskStateSpathIds.push(signallingSpath.spathId);
												}
											});
										}
									}
									catch (error) {
										
									}
									
								}
							});
						}
					}	
				}
				
				return taskStateSpathIds;
			}
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

					util.ui.setTitle(container.config.id, i18n.t("LBL_INCIDENT_REPORT") + " " + util.getSimpleId(this.incidentReport.id) + " - " + this.incidentReport.incidentType.alias);
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
		
		let	loadingBlurHeight = util.module.getWindowParamValue(self.module, WindowParam.HEIGHT, 773) - 50;
		let loadingBlurWidth = util.module.getWindowParamValue(self.module, WindowParam.WIDTH, 1689);

		let incidentReportActionPanel = new IncidentReportActionPanel(self);

		if (!this.incidentReportContent) {

			this.incidentReportContent = new IncidentReportContent(self);
		}

		let viewMain = {
			view: "abslayout",
			id: this.uid,
			cells: [
				{
					id: "main-content-" + self.uid,
					relative: true,
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
				},
				{
					id: "loading-blur-" + self.uid,
					css: "loading-blur",
					hidden: true,
					width: loadingBlurWidth,
					height: loadingBlurHeight,
					cols: [util.ui.getViewDrawLoading()]				
				}
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

				let mainContent = $$("main-content-" + self.uid);
				let loadingViewBlur = $$("loading-blur-" + self.uid);
				
				if (loadingViewBlur && mainContent) {
					
					loadingViewBlur.show();
					webix.html.addCss(mainContent.$view, "main-screen-locked-firefox");
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
