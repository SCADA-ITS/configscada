import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import SearchDialogBox from "../component/searchDialogBox.js";
import { util } from "../lib/util.js";

export default class RoleModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;

		this.form;

		this.data = null;

		this.dtPermissions = null;

		this.selectPermissions = [];

		this.oldPermissions = [];

		this.rolPermissions = [];

		this.roleSystem = false;

	}

	getView() {

		let view = undefined;

		if (this.args.selectedPks != undefined) {

			if (this.args.selectedPks[0] === this.config.systemRole) {

				this.roleSystem = true;
			}
		}

		if (this.roleSystem && this.moduleAction.id === this.config.moduleAction.edit) {

			webix.alert({
				title: i18n.t("LBL_SELECTION_NOT_ALLOWED"),
				text: i18n.t("LBL_NOT_ALLOWED_TO_SELECT_SYSTEM_ROLE_DESCRIPTION"),
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
			}
			return view;
		}
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

		if (this.dtPermissions) {

			this.dtPermissions.callbacks();
		}


		function add() {


			let container = $$(self.args.containerId);

			if (container) {

				util.ui.setWindowTitle(container, i18n.t("LBL_ROLE_ADD"));
			}

			drawComponents();
		};


		function edit() {

			if (self.args.selectedPks != undefined && self.args.selectedPks[0] && self.args.selectedPks.length > 0) {

				if ($$("loading-" + self.uid)) {

					$$("loading-" + self.uid).show();
					$$(self.uid).hide();

					webix.ajax().get(appConfig.restUrl + "users/role/" + self.args.selectedPks[0]).then(function(data) {

						if (data != null) {

							self.oldPermissions = data.json();

							if (self.oldPermissions.rolePermissions) {

								self.oldPermissions.rolePermissions.forEach(e => {
									self.rolPermissions.push(e.id);
								});
							}

							let container = $$(self.args.containerId);

							if (container) {

								util.ui.setWindowTitle(container, i18n.t("LBL_ROLE_EDIT") + " - " + self.oldPermissions.alias);
							}


							drawComponents();
							setVal();
														
							if (self.dtPermissions) {

								self.dtPermissions.callbacks();
							}
						}
					});
				}
			}
		};


		function remove() {

			if (self.args.selectedPks != undefined && self.args.selectedPks[0] && self.args.selectedPks.length > 0) {

				if (self.args.selectedPks[0] === self.config.systemRole) {

					webix.alert({
						title: i18n.t("LBL_SELECTION_NOT_ALLOWED"),
						text: i18n.t("LBL_NOT_ALLOWED_TO_SELECT_SYSTEM_ROLE_DESCRIPTION"),
						type: "alert-error",
						width: 600
					});
				} else {

					webix.confirm({
						title: i18n.t("LBL_CONFIRM_DELETE_ROLE"),
						ok: i18n.t("LBL_YES"),
						cancel: i18n.t("LBL_NO"),
						text: i18n.t("LBL_QUESTION_DELETE_ROLE_DESCRIPTION"),
						width: 600
					}).then(function() {
						self.args.selectedPks.forEach(e => {
							webix.ajax().sync().del(appConfig.restUrl + util.fillTemplate(self.config.actionEndpoints.remove, { id: e }));
							self.args.callbacks[0]();
						});
					})
				}
			};
		};


		function drawComponents() {

			$$("loading-" + self.uid).show();

			let oldPermissions = self.oldPermissions;

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
					label = "LBL_NEW_ROLE";
					description = "LBL_NEW_ROLE_DESCRIPTION";

				} else if (self.moduleAction.id === self.config.moduleAction.edit) {
					label = "LBL_EDIT_ROLE";
					description = "LBL_EDIT_ROLE_DESCRIPTION";
				}

				header = {

					id: "viewHeader-" + self.uid,
					cols: [
						{
							view: "label",
							label: "<img src='img/grid/role.png' class='grid-icon'/>",
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
					header: i18n.t("LBL_ROLE_INFO"),
					width: 100,
					body: getForm()
				});

				tabs.cells.push({
					header: i18n.t("LBL_ROLE_PERMISSIONS"),
					width: 100,
					body: getDataTable()
				});

				return tabs;


				function getForm() {

					let form = {
						id: "form-" + self.uid,
						view: "form",
						css: "role-form",
						elementsConfig: { labelAlign: "left" },
						elements: [
							{ height: 38 },
							{ view: "text", id: "rolName-" + self.uid, name: "roleName", required: true, label: i18n.t("LBL_ROLE_NAME"), labelWidth: 275, inputWidth: 600 },
							{ view: "textarea", height: 180, id: "rolDesc-" + self.uid, name: "roleDesc", label: i18n.t("LBL_ROLE_DESCRIPTION"), labelWidth: 275, inputWidth: 600 },
							{ height: 20 }
						]
					}

					self.form = form;

					return form;
				};


				function getDataTable() {

					let dataTable = {
						id: "dt-" + self.uid,
						height: 335,
						css: "role-form",
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
							{ gridOption: { id: 4 }, value: "desc" },
							{ gridOption: { id: 5 }, value: undefined },
							{ gridOption: { id: 6 }, value: undefined },
							{ gridOption: { id: 7 }, value: "false" },
							{ gridOption: { id: 8 }, value: "false" },
							{ gridOption: { id: 9 }, value: "true" },
							{ gridOption: { id: 16 }, value: "img/grid/default.png" },
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
									},
									gridFieldIcons: []
								},
								{
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_LABEL_PERMISSION",
									labelTooltip: "LBL_LABEL_PERMISSION",
									needsTranslation: true,
									parameterized: false,
									position: 2,
									required: true,
									sourceField: "labelAlias",
									visible: true,
									widthPx: 230,
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
									label: "LBL_LABEL_PERMISSION_DESCRIPTION",
									labelTooltip: "LBL_LABEL_PERMISSION_DESCRIPTION",
									needsTranslation: true,
									parameterized: false,
									position: 3,
									required: true,
									sourceField: "labelDescription",
									visible: true,
									fillspace: true,
									widthPx: 400,
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
									multiselect: false,
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

						if (oldPermissions && oldPermissions != undefined) {

							getData(oldPermissions);
						}

						data = self.data;

						let events = {};
						events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

						events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
							commandId: 1,
							function: () => {

								let dialogBox = new SearchDialogBox("LBL_SELECT_PERMISSION", SearchDialogBox.PERMISSIONS, getPermissions);

								dialogBox.show();
							}
						});

						events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
							commandId: 2,
							function: () => {

								let selectPermissions = self.selectPermissions;
								let permToDelete = [];
								let rolPermissions = self.rolPermissions;

								self.dtPermissions.rowsSelected.forEach(e => {

									if (selectPermissions.find(item => item === e)) {

										selectPermissions.splice(selectPermissions.indexOf(e), 1);
									}

									if (rolPermissions.find(item => item === e)) {

										rolPermissions.splice(rolPermissions.indexOf(e), 1);
									}

									permToDelete.push({ id: e });
								});

								self.dtPermissions.delete(permToDelete);
								self.dtPermissions.rowsSelected.length = 0;
							}
						});

						self.dtPermissions = new DataTable(null, null, metadata, data, events);

						return self.dtPermissions.getView();
					};


					function getPermissions(selection) {

						let repeatPermission = self.selectPermissions.find(e => e === selection[0].id);
						let samePermission = self.rolPermissions.find(e => e === selection[0].id);

						if (samePermission || repeatPermission) {

							webix.alert({
								title: i18n.t("LBL_PERMISSION_CANNOT_BE_SET"),
								text: i18n.t("LBL_PERMISSION_IS_ALREADY_IN_USE_DESCRIPTION"),
								type: "alert-error",
								width: 600
							});

							return false;

						} else {

							self.dtPermissions.add(selection);
							self.selectPermissions.push(selection[0].id);
						}
					}
					return dataTable;
				}
			};



			function getButtons() {

				let form = self.form;

				let buttons = {
					id: "buttons-" + self.uid,
					borderless: true,
					rows: [
						{ height: 14 },
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
											let permissions = self.selectPermissions.concat(self.rolPermissions);

											let body = {
												role: {
													alias: values.roleName,
													description: values.roleDesc,
													preset: false,
													rolePermissionIds: permissions

												}
											}

											if (self.moduleAction.id === self.config.moduleAction.add) {

												webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.add, body).then(function(data) {
													$$(self.args.containerId).close();
													self.args.callbacks[0]();
												});

											} else if (self.moduleAction.id === self.config.moduleAction.edit) {

												body.role.id = self.oldPermissions.id;

												webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + self.config.actionEndpoints.edit, body).then(function(data) {
													$$(self.args.containerId).close();
													self.args.callbacks[0]();
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

		function getData(resp) {

			self.data = [];
			let data = self.data;

			if (resp.rolePermissions) {

				resp.rolePermissions.forEach(e => {

					let response = webix.ajax().sync().get(appConfig.restUrl + "users/systemPermission/" + e.id);
					let responsePerm = JSON.parse(response.responseText);
					let row = {};

					row.id = responsePerm.id;
					row.labelAlias = responsePerm.labelAlias;
					row.labelDescription = responsePerm.labelDescription;
					row.inherited = resp.alias;

					data.push(row);
				});
			}
		}


		function setVal() {

			let oldPermissions = self.oldPermissions;
			let form = self.form;

			if (oldPermissions) {
				$$(form.id).setValues({
					roleName: oldPermissions.alias,
					roleDesc: oldPermissions.description
				});
			}
		};
	};


}	
