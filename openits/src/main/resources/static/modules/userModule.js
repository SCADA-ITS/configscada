import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import SearchDialogBox from "../component/searchDialogBox.js";
import CalendarDialogBox from "../component/calendarDialogBox.js";
import { util } from "../lib/util.js";

export default class UserModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;

		this.module = moduleAction;

		this.args = args;

		this.form = {};

		this.data = null;

		this.dtPermissions = null;

		this.oldUser = null;

		this.newUser = null;

		this.userPermissions = [];

		this.rolPermissions = [];

		this.userSystem = false;
	}


	getView() {

		let view = undefined;

		if (this.args.selectedPks != undefined) {

			if (this.args.selectedPks[0] === this.config.systemUser) {

				this.userSystem = true;
			}
		}


		if (this.userSystem && this.moduleAction.id === this.config.moduleAction.edit) {

			webix.alert({
				title: i18n.t("LBL_SELECTION_NOT_ALLOWED"),
				text: i18n.t("LBL_NOT_ALLOWED_TO_SELECT_SYSTEM_USER_DESCRIPTION"),
				type: "alert-error",
				width: 600
			});
		} else if (this.moduleAction.id === this.config.moduleAction.add || this.moduleAction.id === this.config.moduleAction.edit) {

			view = {
				css: "custom_window",
				cols: [
					{
						rows: [
							{ height: 6 },
							{
								cols: [
									{ width: 6 },
									{
										id: this.uid,
										rows: [],
										hidden: false
									},
									{
										id: "loading-" + this.uid,
										rows: [getViewDrawLoading()],
										hidden: true
									},
									{ width: 6 }
								]
							},
							{ height: 6 }
						]
					}
				]
			};


			function getViewDrawLoading() {

				return {
					view: "template",
					template: function() {

						let icon = "<img src='img/loading.svg' align='center'>";

						return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
					}
				}
			};
		}

		return view;
	};


	loadData() {

		let self = this;

		if (this.moduleAction.id === this.config.moduleAction.add) {

			add();
		}
		else if (this.moduleAction.id === this.config.moduleAction.edit) {

			edit();
		}
		else if (this.moduleAction.id === this.config.moduleAction.remove) {

			remove();
		}
		else if (this.moduleAction.id === this.config.moduleAction.lock) {

			lock();
		}

		if (this.dtPermissions) {

			this.dtPermissions.callbacks();
		}


		function add() {

			$$("loading-" + self.uid).show();

			let container = $$(self.args.containerId);

			if (container) {

				util.ui.setTitle(container.config.id, i18n.t("LBL_USER_ADD"));
			}

			drawComponents();
		};


		function edit() {

			$$("loading-" + self.uid).show();
			$$(self.uid).hide();

			if (self.args.selectedPks != undefined && self.args.selectedPks[0] && self.args.selectedPks.length > 0) {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.user, { id: self.args.selectedPks[0] })).then(function(data) {

					if (data != null) {

						self.oldUser = data.json();

						if (self.oldUser.role.rolePermissions) {

							self.oldUser.role.rolePermissions.forEach(e => {
								self.rolPermissions.push(e);
							});
						}

						if (self.oldUser.userPermissions) {

							self.oldUser.userPermissions.forEach(e => {
								self.userPermissions.push(e)
							});
						}

						let container = $$(self.args.containerId);

						if (container) {
							util.ui.setTitle(container.config.id, i18n.t("LBL_USER_EDIT") + " - " + self.oldUser.name);
						}
	
						drawComponents();
						setVal(self.oldUser);

						if (self.dtPermissions) {

							self.dtPermissions.callbacks();
						}
					}
				});
			}
		};


		function remove() {

			if (self.args.selectedPks != undefined && self.args.selectedPks[0] && self.args.selectedPks.length > 0) {

				if (self.args.selectedPks[0] === self.config.systemUser) {

					webix.alert({
						title: i18n.t("LBL_SELECTION_NOT_ALLOWED"),
						text: i18n.t("LBL_NOT_ALLOWED_TO_SELECT_SYSTEM_USER_DESCRIPTION"),
						type: "alert-error",
						width: 600
					});
				} else {

					webix.confirm({
						title: i18n.t("LBL_CONFIRM_DELETE_USER"),
						ok: i18n.t("LBL_YES"),
						cancel: i18n.t("LBL_NO"),
						text: i18n.t("LBL_QUESTION_DELETE_USER_DESCRIPTION"),
						width: 600
					}).then(function() {
						self.args.selectedPks.forEach(e => {
							webix.ajax().sync().del(appConfig.restUrl + util.fillTemplate(self.config.actionEndpoints.remove, { id: e }));
							self.args.callbacks[0]();
						});
					});

				}

			}
		};

		function lock() {

			const LOCK_ACTION = 1;
			const UNLOCK_ACTION = 2;

			if (self.args.selectedPks != undefined && self.args.selectedPks[0] && self.args.selectedPks.length > 0) {

				if (self.args.selectedPks[0] === self.config.systemUser) {

					webix.alert({
						title: i18n.t("LBL_SELECTION_NOT_ALLOWED"),
						text: i18n.t("LBL_NOT_ALLOWED_TO_SELECT_SYSTEM_USER_DESCRIPTION"),
						type: "alert-error",
						width: 600
					});
				} else {

					let timeService = window.sm.getService(Services.TIME_SERVICE);
					let users = [];
					let actualDate = new Date(timeService.getEpoch());
					let action = -1;
					let isDiffActions = false;

					for (let i = 0; !isDiffActions && i < self.args.selectedPks.length; i++) {

						let currentAction = -1;
						let response = webix.ajax().sync().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.user, { id: self.args.selectedPks[i] }));
						let responseUser = JSON.parse(response.responseText);

						currentAction = (responseUser.lockedUntil && actualDate < responseUser.lockedUntil) ? UNLOCK_ACTION : LOCK_ACTION;

						// Establece la acción de trabajo
						if (i === 0) {

							action = currentAction;
						}
						else {

							isDiffActions = (currentAction !== action);
						}

						users.push(responseUser);
					}

					if (!isDiffActions) {

						if (action === LOCK_ACTION) {

							lockUsers(users);
						}
						else {

							webix.confirm({
								title: i18n.t("LBL_CONFIRM_UNLOCK_USER"),
								ok: i18n.t("LBL_YES"),
								cancel: i18n.t("LBL_NO"),
								text: i18n.t("LBL_QUESTION_UNLOCK_USER_DESCRIPTION"),
								width: 600
							}).then(function() {

								unlockUsers(users);
							});
						}
					}
					else {

						webix.alert({
							title: i18n.t("LBL_INCOMPATIBLE_SELECTION"),
							text: i18n.t("LBL_SELECTION_OF_LOCKED_AND_UNLOCKED_USERS_DESCRIPTION"),
							type: "alert-error",
							width: 600
						});
					}

					function lockUsers(users) {

						new CalendarDialogBox(i18n.t("LBL_CONFIRM_LOCK_USER"), i18n.t("LBL_QUESTION_LOCK_USER"), false,

							function(date) {

								return (Math.floor(date) < Math.floor(new Date()));
							},
							function(dates) {

								let itemsReponse = 0;
								let usersToUpdate = adapterUserToUpdate(users);

								usersToUpdate.forEach(user => {

									user.user.lockedUntil = dates.from.getTime();

									webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + self.config.actionEndpoints.edit, user)
										.then(function(data) {

											shouldRefresh();
										},
											function(response) {

												shouldRefresh();
											});
								});

								function shouldRefresh() {

									if (users.length === ++itemsReponse) {

										self.args.callbacks[0]();
									}
								}
							}).show();
					}

					function unlockUsers(users) {

						let usersToUpdate = adapterUserToUpdate(users);

						usersToUpdate.forEach(user => {

							user.user.lockedUntil = timeService.getEpoch();

							webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + self.config.actionEndpoints.edit, user).then(function(data) {

								self.args.callbacks[0]();
							});
						});
					}
				}
			}
		}


		function adapterUserToUpdate(users) {

			let usersToUpdate = [];
			let permissions = [];
			let arrayUsers = [];

			if (Array.isArray(users) && users.length > 0) {

				arrayUsers = users;
			} else {

				arrayUsers.push(users);
			}

			arrayUsers.forEach(user => {

				let body = {
					user: {
						id: user.id,
						name: user.name,
						fullName: user.fullName,
						password: user.password,
						email: user.email,
						preset: false,
						allowMultipleSessions: user.allowMultipleSessions,
					}
				}

				if (user.localeId && user.roleId) {

					body.user.localeId = user.localeId;
					body.user.roleId = user.roleId;
				} else {

					body.user.localeId = user.locale.id;
					body.user.roleId = user.role.id;
				}

				if (user.userPermissions && user.userPermissions.length > 0) {

					user.userPermissions.forEach(e => {
						permissions.push(e.id);
					});
				} else {

					permissions = [];
				}

				body.user.userPermissionIds = permissions;
				usersToUpdate.push(body);
			});

			return usersToUpdate;
		};


		function drawComponents() {

			$$("loading-" + self.uid).show();

			let newUser = self.newUser;
			let oldUser = self.oldUser;

			let viewMain = {
				id: self.uid,
				rows: [
					getHeader(),
					getTabs(),
					getButtons()
				]
			};

			$$("loading-" + self.uid).hide();

			webix.ui(viewMain, $$(self.uid));


			function getHeader() {

				let header = {};
				let label = undefined;
				let description = undefined;

				if (self.moduleAction.id === self.config.moduleAction.add) {

					label = "LBL_NEW_USER";
					description = "LBL_NEW_USER_DESCRIPTION";

				} else if (self.moduleAction.id === self.config.moduleAction.edit) {

					label = "LBL_EDIT_USER";
					description = "LBL_EDIT_USER_DESCRIPTION";
				}

				header = {

					id: "viewHeader-" + self.uid,
					cols: [
						{
							view: "label",
							label: "<img src='img/grid/user.png' class='grid-icon'/>",
							height: 75,
							css: "global-header-icon"
						},
						{
							view: "label",
							height: 75,
							id: "label-bottom-" + self.uid,
							template: "<span>" + i18n.t(label) + "</span> <br> <span style='font-size: 13px'>" + i18n.t(description) + "</span>",
							css: "global-header-label"
						}
					]
				};

				return header;
			};


			function getTabs() {

				let tabs = {
					view: "tabview",
					id: "tabview-" + self.uid,
					borderless: true,
					css: "secondary-tab",
					cells: [],
					tabbar: {
						tabMargin: 6,
					}
				}

				tabs.cells.push({
					header: i18n.t("LBL_USER_INFO"),
					body: getForm(),
				});

				tabs.cells.push({
					header: i18n.t("LBL_USER_PERMISSIONS"),
					body: getDataTable()
				});

				return tabs;


				function getForm() {

					let form = {
						id: "form-" + self.uid,
						view: "form",
						height: 400,
						elementsConfig: { labelAlign: "left" },
						css: "global-form",
						elements: [
							{ view: "text", id: "name-" + self.uid, name: "name", required: true, label: i18n.t("LBL_USER_NAME"), labelWidth: 275, inputWidth: 600 },
							{ view: "text", id: "password-" + self.uid, name: "password", type: "password", required: true, label: i18n.t("LBL_USER_PASSWORD"), labelWidth: 275, inputWidth: 600 },
							{ view: "text", id: "password_conf-" + self.uid, name: "confirmPass", type: "password", required: true, label: i18n.t("LBL_USER_PASSWORD_CONFIRM"), labelWidth: 275, inputWidth: 600 },
							{ view: "text", id: "fullName-" + self.uid, name: "fullName", label: i18n.t("LBL_USER_FULL_NAME"), labelWidth: 275, inputWidth: 600 },
							{ view: "text", id: "email-" + self.uid, name: "email", type: "email", label: i18n.t("LBL_USER_EMAIL"), labelWidth: 275, inputWidth: 600 },
							{
								cols: [
									{ view: "text", id: "localeId-" + self.uid, name: "localeId", hidden: true },
									{ view: "text", id: "locale-" + self.uid, name: "localeName", required: true, label: i18n.t("LBL_USER_LOCALE"), disabled: true, labelWidth: 275 },
									{
										view: "icon", icon: "wxi-search",
										on: {
											onItemClick: function() {

												newUser = $$(form.id).getValues();
												let dialogBox = new SearchDialogBox(i18n.t("LBL_SELECT_LOCALE"), SearchDialogBox.LOCALES, selectLocale);

												dialogBox.show();
											}
										}
									},
									{ width: 90 }
								]
							},
							{
								cols: [
									{ view: "text", id: "roleId-" + self.uid, name: "roleId", hidden: true },
									{ view: "text", id: "role-" + self.uid, name: "role", required: true, label: i18n.t("LBL_USER_ROLE"), disabled: true, labelWidth: 275 },
									{
										view: "icon", icon: "wxi-search",
										on: {
											onItemClick: function() {

												newUser = $$(form.id).getValues();
												let dialogBox = new SearchDialogBox(i18n.t("LBL_SELECT_ROLE"), SearchDialogBox.ROLES, selectRole);

												dialogBox.show();
											}
										}
									},
									{ width: 90 }
								]
							},
							{
								view: "checkbox",
								id: "allowMultipleSessions-" + self.uid,
								name: "allowMultipleSessions",
								label: i18n.t("LBL_ALLOW_MULTIPLE_SESSIONS"),
								labelWidth: 275,
								inputWidth: 600,
								value: true,
							},

						],
						rules: {
							"name": webix.rules.isNotEmpty,
							"password": webix.rules.isNotEmpty,
							"confirmPass": webix.rules.isNotEmpty,
							"localeName": webix.rules.isNotEmpty,
							"localeId": webix.rules.isNotEmpty,
							"role": webix.rules.isNotEmpty,
							"roleId": webix.rules.isNotEmpty,
						}
					};

					self.form = form;


					function selectLocale(selection) {

						if (Array.isArray(selection) && selection.length > 0) {

							newUser.localeName = selection[0].name;
							newUser.localeId = selection[0].id;
						}

						self.newUser = newUser;
						setVal();
					};


					function selectRole(selection) {
						
						if (Array.isArray(selection) && selection.length > 0) {

							if (selection[0].id === self.config.systemRole) {

								webix.alert({
									title: i18n.t("LBL_SELECTION_NOT_ALLOWED"),
									text: i18n.t("LBL_NOT_ALLOWED_TO SELECT_SYSTEM_ROLE_DESCRIPTION"),
									type: "alert-error",
									width: 600
								});

								return false;

							} else {

								let response = webix.ajax().sync().get(appConfig.restUrl + "users/role/" + selection[0].id);
								let responseRole = JSON.parse(response.responseText);

								drawFromRole(responseRole)
								newUser.role = selection[0].alias;
								newUser.roleId = selection[0].id;
							}

							self.newUser = newUser;
							setVal();
						}
					};

					return form;
				};


				function getDataTable() {

					let dataTable = {
						id: "dt-" + self.uid,
						height: 401,
						css: "global-form",
						rows: [
							{
								cols: [
									{
										rows: getTable()
									}
								]
							},
						]
					};


					function getTable() {

						let data = self.data;

						const defaultGridOptionValues = [
							{ gridOption: { id: 3 }, value: 1 },
							{ gridOption: { id: 4 }, value: "asc" },
							{ gridOption: { id: 5 }, value: undefined },
							{ gridOption: { id: 6 }, value: undefined },
							{ gridOption: { id: 7 }, value: "false" },
							{ gridOption: { id: 8 }, value: "false" },
							{ gridOption: { id: 9 }, value: "true" },
							{ gridOption: { id: 16 }, value: "img/grid/default.png" },
							{ gridOption: { id: 21 }, value: "custom" },
							{ gridOption: { id: 23 }, value: "false" }
						];

						let metadata = {
							filter: true,
							label: "LBL_ROLE",
							visible: true,
							gridOptionValues: defaultGridOptionValues,
							gridFields: [
								{
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: true,
									needsTranslation: false,
									parameterized: false,
									position: 1,
									required: true,
									sourceField: "id",
									visible: false,
									widthPx: 1,
									gridFieldDataType: {
										id: 1,
										name: "string"
									}
								},
								{
									id: 1,
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_LABEL_ALIAS",
									labelTooltip: "LBL_LABEL_ALIAS",
									needsTranslation: true,
									parameterized: false,
									position: 2,
									required: true,
									sourceField: "labelAlias",
									visible: true,
									widthPx: 200,
									gridFieldDataType: {
										id: 1,
										name: "string"
									},
									gridFieldIcons: []
								},
								{
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_LABEL_ALIAS_DESCRIPTION",
									labelTooltip: "LBL_LABEL_ALIAS_DESCRIPTION",
									needsTranslation: true,
									parameterized: false,
									position: 3,
									required: true,
									sourceField: "labelDescription",
									visible: true,
									widthPx: 215,
									fillspace: true,
									adjust: true,
									gridFieldDataType: {
										id: 1,
										name: "string"
									},
									gridFieldIcons: []
								},
								{
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_ROLE_INHERITED",
									labelTooltip: "LBL_ROLE_INHERITED",
									needsTranslation: false,
									parameterized: false,
									position: 4,
									required: true,
									sourceField: "inherited",
									visible: true,
									widthPx: 185,
									gridFieldDataType: {
										id: 1,
										name: "string"
									},
									gridFieldIcons: []
								}
							],
							gridCommands: [
								{
									id: 1,
									default_: false,
									icon: "mdi mdi-plus",
									label: "LBL_CMD_ADD",
									multiselect: true,
									position: 1,
									showText: false,
									itemRequired: false
								},
								{
									id: 2,
									default_: false,
									icon: "mdi mdi-delete",
									label: "LBL_CMD_DELETE",
									multiselect: true,
									position: 2,
									showText: false,
									itemRequired: true
								}
							]

						};

						if (oldUser && oldUser != null) {

							data = drawFromEdit();
						}

						let events = {};
						events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

						events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
							commandId: 1,
							function: () => {

								let dialogBox = new SearchDialogBox(i18n.t("LBL_SELECT_PERMISSION"), SearchDialogBox.PERMISSIONS, userPermissionsToAdd);

								dialogBox.show();
							}
						});

						events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
							commandId: 2,
							function: () => {

								let permInherited = undefined;
								let roles = undefined;

								if (self.rolPermissions.length > 0 && self.rolPermissions[0].rolePermissions) {

									roles = self.rolPermissions[0].rolePermissions;
								} else if (self.rolPermissions.length > 0) {

									roles = self.rolPermissions;
								}

								if (roles) {

									self.dtPermissions.rowsSelected.forEach(row => {
										permInherited = roles.find(e => e.id === row);
									});
								}

								if (permInherited) {

									webix.alert({
										title: i18n.t("LBL_DELETION_NOT_ALOWED"),
										text: i18n.t("LBL_PERMISSION_INHERITED_FROM_A_ROLE_DESCRIPTION"),
										type: "alert-error",
										width: 600
									});

								} else {

									let toDelete = [];
									let userPermissions = self.userPermissions;

									self.dtPermissions.rowsSelected.forEach(e => {

										let permToFind = userPermissions.find(item => item.id === e);

										if (permToFind) {

											userPermissions.splice(userPermissions.indexOf(permToFind), 1);
											toDelete.push({ id: permToFind.id })
										}
									});

									self.dtPermissions.delete(toDelete);
								}
							}
						});

						self.dtPermissions = new DataTable(null, null, metadata, data, events);

						return self.dtPermissions.getView();
					};


					function userPermissionsToAdd(selection) {

						let userPermissions = self.userPermissions;
						let rolPermissions = self.rolPermissions;
						let repeatRolePermission = undefined;
						let roles = undefined;

						if (rolPermissions.length > 0 && rolPermissions[0].rolePermissions) {

							roles = rolPermissions[0].rolePermissions;
						} else if (rolPermissions.length > 0) {

							roles = rolPermissions;
						}

						let repeatUserPermission = userPermissions.find(e => e.id === selection[0].id);

						if (roles != undefined) {

							repeatRolePermission = roles.find(e => e.id === selection[0].id);
						}


						if (repeatRolePermission || repeatUserPermission) {

							webix.alert({
								title: i18n.t("LBL_PERMISSION_CANNOT_BE_SET"),
								text: i18n.t("LBL_PERMISSION_IS_ALREADY_IN_USE_DESCRIPTION"),
								type: "alert-error",
								width: 600
							});

							return false;

						} else {

							self.dtPermissions.add(selection);
							self.userPermissions.push(selection[0]);
						}
					}
					return dataTable;
				}
			};


			function getButtons() {

				let form = self.form;

				const TRUE = 1;
				const FALSE = 0;

				let buttons = {
					id: "buttons-" + self.uid,
					borderless: true,
					rows: [
						{ height: 10 },
						{
							cols: [
								{ width: 15 },
								{
									view: "button", value: i18n.t("LBL_CANCEL"), width: 130, height: 41, css: "dialog-cancel",
									click:
										function() {
											$$(self.args.containerId).close();
										}
								},
								{},
								{
									view: "button", value: i18n.t("LBL_ACCEPT"), width: 130, height: 41, css: "dialog-accept",
									click:
										function() {

											let values = $$(form.id).getValues();

											if (values.password === values.confirmPass) {

												let validation = $$(form.id).validate({ disabled: true });

												if (validation) {

													let permissionsIds = [];

													if (values.allowMultipleSessions === TRUE) {

														values.allowMultipleSessions = true;

													} else if (values.allowMultipleSessions === FALSE) {

														values.allowMultipleSessions = false;
													}

													self.userPermissions.forEach(permission => {

														let rolOperator = undefined;
														let repeatPermissions = undefined;

														if (self.rolPermissions.length > 0 && self.rolPermissions[0].rolePermissions) {

															rolOperator = self.rolPermissions[0].rolePermissions;
														} else if (self.rolPermissions.length > 0) {

															rolOperator = self.rolPermissions;
														}

														if (rolOperator != undefined) {

															repeatPermissions = rolOperator.find(e => e.id === permission.id);
														}

														if (repeatPermissions) {

															self.userPermissions.splice(self.userPermissions.indexOf(repeatPermissions), 1);
														} else {

															permissionsIds.push(permission.id);
														}
													});

													let body = adapterUserToUpdate(values);
													body[0].user.userPermissionIds = permissionsIds;

													if (self.moduleAction.id === self.config.moduleAction.add) {

														webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.add, body[0]).then(function(data) {
															$$(self.args.containerId).close();
															self.args.callbacks[0]();
														});

													} else if (self.moduleAction.id === self.config.moduleAction.edit) {

														body[0].user.id = self.oldUser.id;

														webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + self.config.actionEndpoints.edit, body[0]).then(function(data) {
															$$(self.args.containerId).close();
															self.args.callbacks[0]();
														});
													}
												} else {
													webix.alert({
														title: i18n.t("LBL_FIELDS_NOT_COMPLETE"),
														text: i18n.t("LBL_REQUIRED_FIELDS_NOT_COMPLETED_DESCRIPTION"),
														type: "alert-error",
														width: 600
													});
												}
											} else {
												webix.alert({
													title: i18n.t("LBL_PASSWORDS_DO_NOT_MATCH"),
													text: i18n.t("LBL_PASSWORD_AND_CONFIRM_PASSWORD_MUST_BE_THE_SAME_DESCRIPTION"),
													type: "alert-error",
													width: 600
												});

											}
										}
								},
								{ width: 15 }
							]
						}
					]
				}

				return buttons;
			};
		};


		function drawFromRole(role) {

			let rolPermissions = self.rolPermissions;
			let userPermissions = [];
			let rolePermissions = [];
			let allPermissions = [];
			let permissionsToAdd = [];

			rolPermissions.length = 0;
			rolPermissions.push(role);

			if (role.rolePermissions) {

				role.rolePermissions.forEach(e => {

					rolePermissions.push(e.id);
				});

				allPermissions = role.rolePermissions.concat(self.userPermissions);
			} else {

				allPermissions = self.userPermissions;
			}

			if (self.userPermissions.length > 0) {

				self.userPermissions.forEach(e => {
					userPermissions.push(e.id);
				});
			}

			let permissions = new Set(rolePermissions);

			userPermissions.forEach(userPermission => {

				permissions.add(userPermission);
			});

			permissions.forEach(permission => {

				let perm = allPermissions.find(e => e.id === permission);

				if (perm) {
					
					perm.labelAlias = i18n.t(perm.labelAlias);
					perm.labelDescription = i18n.t(perm.labelDescription);
					
					permissionsToAdd.push(perm);
				}

				if (rolePermissions.indexOf(permission) !== -1) {
					perm.inherited = role.alias;
					perm.$css = { "color": "#808B96 !important" };
				}

				return perm;
			});

			if (self.dtPermissions) {
				
				self.dtPermissions.clear();
				self.dtPermissions.add(permissionsToAdd);
			}
		}

		function drawFromEdit() {

			self.data = [];
			let userPermissions = self.userPermissions;
			let rolPermissions = self.rolPermissions;
			let oldUser = self.oldUser;
			let data = self.data;
			let permissionsToAdd = [];

			permissionsToAdd = rolPermissions.concat(userPermissions);

			permissionsToAdd.forEach(permission => {

				if (rolPermissions.indexOf(permission) !== -1) {
					permission.inherited = oldUser.role.alias;
					permission.$css = { "color": "#808B96 !important" };
				}
			});

			if (self.dtPermissions) {
				self.dtPermissions.add(permissionsToAdd);
			}

			data = permissionsToAdd;
			return data;
		}


		function setVal(user) {

			let newUser = self.newUser;
			let form = self.form;

			if (user) {

				$$(form.id).setValues({
					name: user.name,
					password: user.password,
					confirmPass: user.password,
					fullName: user.fullName,
					email: user.email,
					localeName: user.locale.name,
					localeId: user.locale.id,
					role: user.role.alias,
					roleId: user.role.id,
					allowMultipleSessions: user.allowMultipleSessions
				});

			} else {

				$$(form.id).setValues(newUser)
			}
		};
	};


}	
