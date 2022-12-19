import { util } from "../lib/util.js";
import MessageBox from "../component/messageBox.js";

const MAX_INACTIVE_NOTICATIONS = 10;
const COUNTDOWN_RECOGNIZE_MS = 1000;

export default class Notification {

	static get ALARM() { return { id: 1, label: "LBL_NOTIFICATION_ALARM", css: "notification-alarm-window", icon: "alarm.png", windowWidth: 750, windowHeight: 500 } }
	static get INCIDENT() { return { id: 2, label: "LBL_NOTIFICATION_INCIDENT", css: "notification-incident-window", icon: "incident.png", windowWidth: 850, windowHeight: 500 } }
	static get EVENT() { return { id: 3, label: "LBL_NOTIFICATION_EVENT", css: "notification-event-window", icon: "event.png", windowWidth: 850, windowHeight: 500 } }

	constructor(config, notificationType, notifications) {

		this.uid = webix.uid();
		this.config = config;
		this.notificationType = notificationType;
		this.notifications = Array.isArray(notifications) ? notifications : [];
		this.timeoutNotificationMs = config.timeoutNotificationMs > 0 ? config.timeoutNotificationMs : undefined;
		this.maxInactiveNotifications = config.maxInactiveNotifications ? config.maxInactiveNotifications : MAX_INACTIVE_NOTICATIONS;

		this.instance = undefined;
		this.audio = undefined;

		this.recognizedNotifications = [];
		this.intervalNotification = undefined;
		this.intervalCountdownRecognize = undefined;
	}

	load() {

		let view;
		let self = this;

		let notificationId = "notification-" + self.uid;

		let head = {
			view: "toolbar",
			css: self.notificationType.css + "-head",
			elements: [
				{

					view: "label",
					template: i18n.t(self.notificationType.label)
				},
				{
					view: "icon",
					icon: "mdi mdi-close",
					on: {
						onItemClick: function() {

							self.hide();
						}
					}
				}
			],
			borderless: 1
		};

		view = {
			view: "window",
			id: notificationId,
			hidden: true,
			css: self.notificationType.css,
			move: true,
			toFront: true,
			head: head,
			width: self.notificationType.windowWidth,
			height: self.notificationType.windowHeight,
			body: getViewNotification(),
			position: function(state) {
				state.left = Math.floor((document.documentElement.clientWidth - self.notificationType.windowWidth) / 2);
				state.top = Math.floor((document.documentElement.clientHeight - self.notificationType.windowHeight) / 2);
			}
		};

		function getViewNotification() {

			let viewNotification;

			viewNotification = {
				cols: [
					{ width: 20 },
					{
						rows: [
							{ height: 10 },
							getViewToolbar(),
							{ height: 5 },
							getList(),
							{ height: 20 },
							getToolbarActions(),
							{ height: 10 },
						]
					},
					{ width: 20 },
				]
			};

			return viewNotification;
		}

		function getViewToolbar() {

			const TOOLBAR_BUTTONS_WIDTH = 130;

			let viewToolbar = {
				height: 39,
				css: { "margin-left": "2px !important" },
				cols: []
			};

			viewToolbar.cols.push({
				width: 70,
				template: function() {

					return `<image class="notification-icon" src="img/notification/${self.notificationType.icon}" />`;
				},
				borderless: true
			});

			viewToolbar.cols.push({
				template: function() {

					return `<div class="notification-title">${i18n.t(self.notificationType.label)}</div>`;
				},
				borderless: true
			});

			viewToolbar.cols.push({ width: 10 });

			if (self.notificationType.id === Notification.ALARM.id) {

				viewToolbar.cols.push(
					{
						view: "button",
						type: "icon",
						icon: "mdi mdi-check",
						css: "toolbar-button-text",
						width: TOOLBAR_BUTTONS_WIDTH,
						label: i18n.t("LBL_RECOGNIZE"),
						tooltip: i18n.t("LBL_RECOGNIZE_ALARM"),
						on: {
							onItemClick() {

								let alarmIds = getSelectedItems().map(e => { return e.notificationId });

								if (Array.isArray(alarmIds) && alarmIds.length > 0) {

									let moduleActionId = self.config.recognizeAlarms;
									let module = util.module.getFromModuleAction(moduleActionId);

									if (module) {

										let args = { selectedPks: alarmIds };
										let moduleAction = { id: moduleActionId };

										util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
									}
								}
								else {

									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_SELECTED_ALARMS"), null, null, null)).show();
								}
							}
						}
					}
				);

				viewToolbar.cols.push(
					{
						view: "button",
						type: "icon",
						icon: "mdi mdi-volume-off",
						css: "toolbar-button-text",
						width: TOOLBAR_BUTTONS_WIDTH,
						label: i18n.t("LBL_SNOOZE"),
						tooltip: i18n.t("LBL_SNOOZE_ALARM"),
						on: {
							onItemClick() {

								let alarms = getSelectedItems();

								if (Array.isArray(alarms) && alarms.length > 0) {

									(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_SNOOZE_SELECTED_ALARMS"), null,

										// onAccept
										function() {

											alarms.forEach(alarm => {

												if (alarm.sound) {

													alarm.snooze = true;
												}
											});

											let list = $$("list-" + self.uid);

											if (list) {

												list.refresh();

												list.unselectAll();

												list.select(alarms.map(alarm => parseInt(alarm.id)));
											}
										}
									)).show();


								}
								else {

									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_SELECTED_ALARMS"), null, null, null)).show();
								}
							}
						}
					}
				);

				viewToolbar.cols.push(
					{
						view: "button",
						type: "icon",
						icon: "mdi mdi-broom",
						css: "toolbar-button-text",
						width: TOOLBAR_BUTTONS_WIDTH,
						label: i18n.t("LBL_CLEAN"),
						tooltip: i18n.t("LBL_CLEAN_ALARM"),
						on: {
							onItemClick() {

								if (Array.isArray(self.recognizedNotifications) && self.recognizedNotifications.length > 0) {

									let alarmRecognizedSelected = getSelectedItemsRecognized();

									if (Array.isArray(alarmRecognizedSelected) && alarmRecognizedSelected.length > 0) {

										(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_CLEAN_SELECTED_ALARMS"), null,
											function() {

												self.removeRecognized(alarmRecognizedSelected);

												if (window.userControlPanel) {

													window.userControlPanel.updateData();
												}

											})).show();

									} else {

										(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_CLEAN_ALL_CONFIRMED_ALARMS"), null,
											function() {

												self.removeRecognized();

												if (window.userControlPanel) {

													window.userControlPanel.updateData();
												}

											})).show();
									}

								} else {

									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_RECOGNIZED_ELEMENTS"), null, null, null)).show();
								}
							}
						}
					}
				);
			}
			else if (self.notificationType.id === Notification.INCIDENT.id) {

				viewToolbar.cols.push(
					{
						view: "button",
						type: "icon",
						icon: "mdi mdi-check",
						css: "toolbar-button-text",
						width: TOOLBAR_BUTTONS_WIDTH,
						label: i18n.t("LBL_CONFIRM"),
						tooltip: i18n.t("LBL_CONFIRM_INCIDENT"),
						on: {
							onItemClick() {

								let incidents = getSelectedItems().map(e => { return { id: e.notificationId, currentUserId: e.userId }});

								if (Array.isArray(incidents) && incidents.length > 0) {

									let moduleActionId = self.config.confirmIncidents;
									let module = util.module.getFromModuleAction(moduleActionId);

									if (module) {

										let args = { selectedRows: incidents };
										let moduleAction = { id: moduleActionId };

										util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
									}
								}
								else {

									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_SELECTED_INCIDENTS"), null, null, null)).show();
								}
							}
						}
					}
				);

				viewToolbar.cols.push(
					{
						view: "button",
						type: "icon",
						icon: "mdi mdi-volume-off",
						css: "toolbar-button-text",
						width: TOOLBAR_BUTTONS_WIDTH,
						label: i18n.t("LBL_SNOOZE"),
						tooltip: i18n.t("LBL_SNOOZE_INCIDENT"),
						on: {
							onItemClick() {

								let incidents = getSelectedItems();

								if (Array.isArray(incidents) && incidents.length > 0) {

									(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_SNOOZE_SELECTED_INCIDENTS"), null,

										// onAccept
										function() {

											incidents.forEach(incident => {

												if (incident.sound) {

													incident.snooze = true;
												}
											});

											let list = $$("list-" + self.uid);

											if (list) {

												list.refresh();

												list.unselectAll();

												list.select(incidents.map(incident => parseInt(incident.id)));
											}
										}
									)).show();


								}
								else {

									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_SELECTED_INCIDENTS"), null, null, null)).show();
								}
							}
						}
					}
				);

				viewToolbar.cols.push(
					{
						view: "button",
						type: "icon",
						icon: "mdi mdi-broom",
						css: "toolbar-button-text",
						width: TOOLBAR_BUTTONS_WIDTH,
						label: i18n.t("LBL_CLEAN"),
						tooltip: i18n.t("LBL_CLEAN_INCIDENT"),
						on: {
							onItemClick() {

								if (Array.isArray(self.recognizedNotifications) && self.recognizedNotifications.length > 0) {

									let incidentRecognizedSelected = getSelectedItemsRecognized();

									if (Array.isArray(incidentRecognizedSelected) && incidentRecognizedSelected.length > 0) {

										(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_CLEAN_SELECTED_INCIDENTS"), null,
											function() {

												self.removeRecognized(incidentRecognizedSelected);

												if (window.userControlPanel) {

													window.userControlPanel.updateData();
												}

											})).show();
									}
									else {

										(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_CLEAN_ALL_CONFIRMED_INCIDENTS"), null,
											function() {

												self.removeRecognized();

												if (window.userControlPanel) {

													window.userControlPanel.updateData();
												}

											})).show();
									}

								} else {

									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_INCIDENTS_RECOGNIZED"), null, null, null)).show();
								}
							}
						}
					}
				);
			}
			else if (self.notificationType.id === Notification.EVENT.id) {

				viewToolbar.cols.push(
					{
						view: "button",
						type: "icon",
						icon: "mdi mdi-check",
						css: "toolbar-button-text",
						width: TOOLBAR_BUTTONS_WIDTH,
						label: i18n.t("LBL_RECOGNIZE"),
						tooltip: i18n.t("LBL_RECONNIZE_EVENT"),
						on: {
							onItemClick() {

								let selectedNotification = getSelectedItems();
								let eventIds = selectedNotification.map(e => { return e.notificationId });

								if (Array.isArray(eventIds) && eventIds.length > 0) {

									let moduleActionId = self.config.recognizeEvents;
									let module = util.module.getFromModuleAction(moduleActionId);

									if (module) {

										let args = { selectedPks: eventIds, confirm: false };
										let moduleAction = { id: moduleActionId };

										(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_RECOGNIZE_SELECTED_EVENT"), null,

											function() {

												util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);

												selectedNotification.forEach(selNotification => {

													let idxEventNotification = self.notifications.findIndex(e => e.notificationId === selNotification.notificationId);

													if (idxEventNotification !== -1) {

														let eventNotification = self.notifications[idxEventNotification];

														if (eventNotification) {

															self.remove(selNotification);
															self.notifications.splice(idxEventNotification, 1);
														}
													}
												});

												if (self.notifications.length === 0) {

													self.hide();
												}
											},
											null)).show();
									}
								}
								else {

									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_SELECTED_EVENTS"), null, null, null)).show();
								}
							}
						}
					}
				);

				viewToolbar.cols.push(
					{
						view: "button",
						type: "icon",
						icon: "mdi mdi-volume-off",
						css: "toolbar-button-text",
						width: TOOLBAR_BUTTONS_WIDTH,
						label: i18n.t("LBL_SNOOZE"),
						tooltip: i18n.t("LBL_SNOOZE_EVENT"),
						on: {
							onItemClick() {

								let events = getSelectedItems();

								if (Array.isArray(events) && events.length > 0) {

									(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_SNOOZE_SELECTED_EVENTS"), null,

										// onAccept
										function() {

											events.forEach(event => {

												if (event.sound) {

													event.snooze = true;
												}
											});

											let list = $$("list-" + self.uid);

											if (list) {

												list.refresh();

												list.unselectAll();

												list.select(events.map(event => parseInt(event.id)));
											}
										}
									)).show();


								}
								else {

									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_SELECTED_EVENTS"), null, null, null)).show();
								}
							}
						}
					}
				);
			}


			function getSelectedItems() {

				let list = $$("list-" + self.uid);
				let items = [];

				if (list) {

					let ids = list.getSelectedId(true);

					if (Array.isArray(ids) && ids.length > 0) {

						ids = ids.map(e => parseInt(e));

						items = self.notifications.filter(e => ids.includes(parseInt(e.id)));
					}
				}

				return items;
			}

			function getSelectedItemsRecognized() {

				let list = $$("list-" + self.uid);
				let itemsRecognized = [];

				if (list) {

					let ids = list.getSelectedId(true);

					if (Array.isArray(ids) && ids.length > 0) {

						ids = ids.map(e => parseInt(e));

						itemsRecognized = self.recognizedNotifications.filter(e => ids.includes(parseInt(e.id)));
					}
				}

				return itemsRecognized;
			}

			return viewToolbar;
		}

		function getList() {

			let list;

			list = {
				view: "list",
				id: "list-" + self.uid,
				css: self.notificationType.css + "-list",
				select: "multiselect",
				type: {

					template: function(obj) {

						return drawNotification(obj);
					}
				},
				data: self.notifications,
				tooltip: function(obj) {

					let textFirstLineWidth;
					let textSecondLineWidth;
					let nodeFirstLine = $$("list-" + self.uid).getNode().querySelector(".first-line");
					let nodeSecondLine = $$("list-" + self.uid).getNode().querySelector(".second-line");
					let allowedSpace = nodeFirstLine.clientWidth;

					if (nodeFirstLine != null) {

						let elementStyle = window.getComputedStyle(nodeFirstLine);
						let fontSize = elementStyle.getPropertyValue('font-size');
						let fontFamily = elementStyle.getPropertyValue('font-family');
						let letterSpacing = elementStyle.getPropertyValue('letter-spacing');

						textFirstLineWidth = util.ui.calculateTextWidth(nodeFirstLine.innerHTML, fontFamily, fontSize, letterSpacing);
					}

					if (nodeSecondLine != null) {

						let elementStyle = window.getComputedStyle(nodeSecondLine);
						let fontSize = elementStyle.getPropertyValue('font-size');
						let fontFamily = elementStyle.getPropertyValue('font-family');
						let letterSpacing = elementStyle.getPropertyValue('letter-spacing');

						textSecondLineWidth = util.ui.calculateTextWidth(nodeSecondLine.innerHTML, fontFamily, fontSize, letterSpacing);
					}

					if (textFirstLineWidth > allowedSpace || textSecondLineWidth > allowedSpace) {

						let secondLine = util.toLocaleString(obj.created / 1000) + ": " + obj.description;


						return `${obj.alias}:<br>${secondLine}`;

					} else {

						return "";
					}
				},
				on: {
					onAfterSelect: function() {

						let id = this.getSelectedId(true);

						let notification = self.notifications.find(e => e.id == id);

						if (notification && !notification.recognized && !notification.snooze) {

							self.playAudio(notification.sound);
						}
						else {

							self.stopAudio();
						}
					},
					onAfterDelete: function(id) {

						let ids = this.getSelectedId(true);

						if (Array.isArray(ids) && ids.length === 0) {

							self.stopAudio();
						}
					},
					onItemDblClick: function() {

						let ids = this.getSelectedId(true);

						let moduleActionId = self.config.dblClickModuleAction;

						if (moduleActionId) {

							if (Array.isArray(ids) && ids.length === 1) {

								let module = util.module.getFromModuleAction(moduleActionId);

								if (module) {

									let notification = self.notifications.find(e => e.id == ids)

									if (notification) {
										
										let args = { selectedPks: [notification.notificationId]};
										let moduleAction = { id: moduleActionId };

										util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
									}

								}
							}
							else {

								(new MessageBox(MessageBox.ERROR, i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT"), null, null, null)).show();
							}
						}
					}
				}
			};

			function drawNotification(obj) {

				let html;

				let firstLine = obj.alias;

				if (!obj.recognized && obj.countdownRecognize && obj.countdownRecognize.value > 0) {

					firstLine += i18n.t(" (LBL_SECS_TO_RECOGNIZED_ITEM: " + obj.countdownRecognize.value + " LBL_SEC_AB)");
				}

				let secondLine = util.toLocaleString(obj.created / 1000) + ": " + obj.description;

				let widthLevel = isNaN(obj.level) ? 50 : 20;
				let widthImg = obj.img ? 36 : 10;

				html = `<div class="${!obj.recognized ? 'state-enabled' : 'state-disabled'}"></div>
						<image class="image" style="width: ${widthImg}px; padding-left: 5px; padding-right: 5px;" src="${obj.img}" onerror="this.onerror=null;this.src='${NO_IMAGE}';" />
						<div class="level" style="width: ${widthLevel}px; background-color: ${obj.color}">${obj.level}</div>
						<div class="sound"><span style="${obj.recognized ? 'color:rgb(146, 171, 207);' : ''}" class="${obj.sound && !obj.snooze ? "mdi mdi-volume-high" : "mdi mdi-volume-off"}"></span></div>
						<div class="text">
					  		<span class="first-line" style="${obj.recognized ? 'text-decoration:line-through;color:rgb(146, 171, 207);' : ''}" >${firstLine} </span>
				  			<span class="second-line" style="${obj.recognized ? 'text-decoration:line-through;color:rgb(146, 171, 207);' : ''}">${secondLine}</span>
						</div>`;

				return html;
			}

			return list;
		}

		function getToolbarActions() {

			let viewToolbarActions = {};

			viewToolbarActions = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: []
			};


			viewToolbarActions.elements.push({});
			viewToolbarActions.elements.push({
				view: "button",
				css: "dialog-cancel",
				width: 130,
				height: 35,
				label: i18n.t("LBL_CLOSE"),
				on: {
					onItemClick() {

						self.hide();
					}
				}
			});

			return viewToolbarActions;
		}

		self.instance = webix.ui(view);

		this.enableReminder();
	}

	enableReminder() {

		let self = this;

		if (self.intervalNotification) {

			self.disableReminder();
		}

		if (self.timeoutNotificationMs > 0) {

			self.intervalNotification = setInterval(function() {

				if (Array.isArray(self.notifications) && self.notifications.length > 0) {

					self.show();
				}

			}, self.timeoutNotificationMs);
		}
	}

	disableReminder() {

		if (this.intervalNotification) {

			clearInterval(this.intervalNotification);

			this.intervalNotification = undefined;
		}
	}

	enableCountdownRecognize() {

		let self = this;
		let timeService = window.sm.getService(Services.TIME_SERVICE);

		if (self.intervalCountdownRecognize) {

			self.disableCountdownRecognize();
		}

		if (Array.isArray(self.notifications)) {

			let hasCountdownRecognize = (self.notifications.find(e => e.countdownRecognize && e.countdownRecognize.timeConfirmSecs > 0) != null);

			if (hasCountdownRecognize) {

				self.intervalCountdownRecognize = setInterval(function() {

					let timeNowSecs = Math.round(timeService.getEpoch() / 1000);

					self.notifications.forEach(notification => {

						if (notification.countdownRecognize) {

							let timeRefSecs = Math.round(notification[notification.countdownRecognize.timeRef] / 1000);
							let timeTargetSecs = timeRefSecs + notification.countdownRecognize.timeConfirmSecs;

							if (timeNowSecs <= timeTargetSecs) {

								notification.countdownRecognize.value = (timeTargetSecs - timeNowSecs);

								self.update(notification);
							}
						}
					});

				}, COUNTDOWN_RECOGNIZE_MS);
			}
		}
	}

	disableCountdownRecognize() {

		if (this.intervalCountdownRecognize) {

			clearInterval(this.intervalCountdownRecognize);

			this.intervalCountdownRecognize = undefined;
		}
	}

	getRecognizedNotifications() {

		return this.recognizedNotifications;
	}

	show(notification) {

		let list = $$("list-" + this.uid);

		if (this.instance && list) {

			if (notification) {

				list.select(notification.id);
				list.showItem(notification.id);
			}
			else {

				list.unselectAll();
			}

			this.instance.show();

			util.ui.centerWindow(this.instance.config.id);

			this.disableReminder();

			this.enableCountdownRecognize();
		}
	}

	hide() {

		if (this.instance) {

			this.instance.hide();

			this.stopAudio();

			this.enableReminder();

			this.disableCountdownRecognize();
		}
	}

	add(notification) {

		let list = $$("list-" + this.uid);

		if (list) {

			log.debug("Create id = " + list.add(notification, 0));
			log.debug("id = " + notification.id);

			this.show(notification);
		}
	}

	update(notification) {

		let list = $$("list-" + this.uid);

		if (list && list.getItem(notification.id)) {

			list.updateItem(notification.id, notification);
		}
	}

	recognize(notification) {

		let list = $$("list-" + this.uid);

		if (list && Array.isArray(this.notifications) && this.notifications.length > 0) {

			let idxList = list.config.data.findIndex(e => e.notificationId == notification.notificationId);

			if (idxList !== -1) {

				list.remove(list.config.data[idxList].id);
				list.add(notification, idxList);

				this.recognizedNotifications.push(notification);

				// Sort descending
				this.recognizedNotifications.sort((a, b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));

				if (Array.isArray(this.recognizedNotifications) && this.recognizedNotifications.length > this.maxInactiveNotifications) {

					list.remove(this.recognizedNotifications[this.maxInactiveNotifications].id)

					this.recognizedNotifications.splice(this.maxInactiveNotifications, 1);
				}
			}
		}
	}

	remove(notification) {

		let list = $$("list-" + this.uid);

		if (list && Array.isArray(this.notifications) && this.notifications.length > 0) {

			let idxList = -1;

			idxList = list.config.data.findIndex(e => e.notificationId == notification.notificationId);

			if (idxList !== -1) {

				list.remove(list.config.data[idxList].id);
			}
		}
	}

	removeRecognized(notificationsSelected) {

		let list = $$("list-" + this.uid);

		let notificationsRecognizedSelected = Array.isArray(notificationsSelected) ? notificationsSelected : webix.copy(this.recognizedNotifications);

		if (Array.isArray(notificationsRecognizedSelected) && notificationsRecognizedSelected.length > 0) {

			for (let notificationsRecognized of notificationsRecognizedSelected) {

				let notificationToDelete = this.recognizedNotifications.find(notification => notification.id === notificationsRecognized.id);

				if (notificationToDelete) {

					list.remove(notificationToDelete.id);

					let idx = this.recognizedNotifications.indexOf(notificationToDelete);

					if (idx !== -1) {

						this.recognizedNotifications.splice(idx, 1);
					}
				}
			}
		}
	}

	playAudio(sound) {

		this.stopAudio();

		if (sound && !this.audio) {

			this.audio = new Audio('./sound/' + sound);
			this.audio.loop = true;
			this.audio.play();
		}
	}

	stopAudio() {

		if (this.audio) {

			this.audio.pause();
			this.audio.currentTime = 0;
			this.audio = undefined;
		}
	}
}
