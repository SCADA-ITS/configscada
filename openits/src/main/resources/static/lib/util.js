export let util = {

	module: {

		getParamValue(module, param) {

			return module.moduleValues.find(e => e.moduleParam.id === param).value;
		},

		getWindowParamValue(module, param) {

			return module.windowValues.find(e => e.windowParam.id === param).value;
		},

		loadInCustom(module, moduleAction, mod, args, drawCallback) {

			let viewModule;
			let lazyClass;

			if (drawCallback) {

				lazyClass = new mod.default(module, moduleAction, args);

				if (module.jsMethodDraw) {

					viewModule = lazyClass.runOne(module.jsMethodDraw);

					if (viewModule) {

						drawCallback(viewModule);
					}

					if (module.jsMethodCallbacks) {

						lazyClass.runOne(module.jsMethodCallbacks);
					}
				}
			}

			return lazyClass;
		},

		loadInWindow(module, moduleAction, mod, args) {

			let viewModule;
			let allowMultipleInstances = true;
			let image = 'img/module/default.png';
			let icon = 'img/module/default.png';
			let color = '#464646';
			let label = 'LBL_UNDEFINED';
			let externalizable = true;
			let height = 768;
			let width = 1280;
			let modal = false;
			let winId = "win_" + module.id + "_" + webix.uid();
			let taskManagerService = window.sm.getService(Services.TASK_MANAGER_SERVICE);

			let lazyClass;

			if (module.windowValues) {

				try {

					allowMultipleInstances = module.windowValues.find(e => e.windowParam.id === WindowParam.ALLOW_MULTIPLE_INSTANCES).value === 'true';
					image = module.windowValues.find(e => e.windowParam.id === WindowParam.IMAGE).value;
					icon = module.windowValues.find(e => e.windowParam.id === WindowParam.ICON).value;
					color = module.windowValues.find(e => e.windowParam.id === WindowParam.COLOR).value;
					label = module.windowValues.find(e => e.windowParam.id === WindowParam.LABEL).value;
					externalizable = module.windowValues.find(e => e.windowParam.id === WindowParam.EXTERNALIZABLE).value === 'true';
					height = module.windowValues.find(e => e.windowParam.id === WindowParam.HEIGHT).value;
					width = module.windowValues.find(e => e.windowParam.id === WindowParam.WIDTH).value;
					modal = module.windowValues.find(e => e.windowParam.id === WindowParam.MODAL).value === 'true';
				}
				catch {

				}
			}

			let moduleOpenedId = util.module.getModuleOpenedId(module.id);

			if (moduleOpenedId && !allowMultipleInstances) {

				window.wm.activateWindow(moduleOpenedId);
				return;
			}

			if (!args) {

				args = {};
			}

			args.containerId = winId;

			lazyClass = new mod.default(module, moduleAction, args);

			viewModule = lazyClass.runOne(module.jsMethodDraw)


			if (viewModule) {

				let head = {
					view: "toolbar",
					css: "active-window-head",
					//height: 35,
					cols: [
						{
							id: winId + "-label",
							view: "label",
							//css: "notification-center-title", 
							label: i18n.t(label)
						},
						{
							view: "icon",
							hidden: !taskManagerService,
							icon: "mdi mdi-open-in-new",
							on: {
								onItemClick: function(id) {
									
									if (taskManagerService) {
								
										taskManagerService.externalize(winId, lazyClass);
									}
								}
							}
						},
						{
							view: "icon",
							hidden: !taskManagerService,
							icon: "mdi mdi-window-minimize",
							on: {
								onItemClick: function(id) {
									
									if (taskManagerService) {
								
										taskManagerService.minimize(winId);
									}
								}
							}
						},
						{
							view: "icon",
							//css: "notification-center-close-btn",
							icon: "mdi mdi-close",
							on: {
								onItemClick: function(id) {
									$$(winId).close();
								}
							}
						}
					],
					borderless: 1
				};

				webix.ui({
					view: "window",
					id: winId,
					move: true,
					modal: modal,
					toFront: true,
					css: "default-window",
					head: head,
					height: height,
					width: width,
					body: {
						rows: [
							viewModule
						]
					},
					//					position: function(state) {
					//						state.left = (document.documentElement.clientWidth / 2) - (width / 2);
					//						state.top = (document.documentElement.clientHeight / 2) - (height / 2);
					//					},
					on: {
						onBeforeShow: function() {

							window.wm.add(winId);

							if (taskManagerService) {
								
								taskManagerService.add(winId, lazyClass);
							}
						},
						onDestruct: function() {

							window.wm.delete(winId);
							
							if (taskManagerService) {
								
								taskManagerService.remove(winId);
							}
						}
					}
				});
			}

			if ($$(winId)) {

				$$(winId).show();
			}

			if (module.jsMethodCallbacks && module.jsMethodCallbacks !== "DEPRECATED" && lazyClass) {

				lazyClass.runOne(module.jsMethodCallbacks)
			}

			util.ui.centerWindow(winId);

			return lazyClass;
		},

		loadInTab(module, moduleAction, mod, args) {

			let viewModule;
			let allowMultipleInstances = true;
			let image = 'img/module/default.png';
			let icon = 'img/module/default.png';
			let color = '#464646';
			let label = 'LBL_UNDEFINED';
			let externalizable = true;
			let tab_id = "tab_" + module.id + "_" + webix.uid();

			let lazyClass;

			if (module.windowValues && module.windowValues.length > 0) {

				try {

					allowMultipleInstances = module.windowValues.find(e => e.windowParam.id === WindowParam.ALLOW_MULTIPLE_INSTANCES).value === 'true';
					image = module.windowValues.find(e => e.windowParam.id === WindowParam.IMAGE).value;
					icon = module.windowValues.find(e => e.windowParam.id === WindowParam.ICON).value;
					color = module.windowValues.find(e => e.windowParam.id === WindowParam.COLOR).value;
					label = module.windowValues.find(e => e.windowParam.id === WindowParam.LABEL).value;
					externalizable = module.windowValues.find(e => e.windowParam.id === WindowParam.EXTERNALIZABLE).value === 'true';
				}
				catch {

				}
			}


			let moduleOpenedId = util.module.getModuleOpenedId(module.id);

			if (moduleOpenedId && !allowMultipleInstances) {

				$$(MAIN_TAB_ID).getTabbar().setValue(moduleOpenedId);

				return;
			}

			if (!args) {

				args = {};
			}

			args.containerId = tab_id.replace("tab", "tab_body");

			lazyClass = new mod.default(module, moduleAction, args);

			viewModule = lazyClass.runOne(module.jsMethodDraw)

			if (viewModule) {

				$$(MAIN_TAB_ID).addView({

					id: tab_id,
					header: `<div style='float: left; width: 250px;'>
								<img class='tab-icon' src='${icon}'/>
								<span class='tab-text'>${i18n.t(label)}</span>
								<span style='float: right; padding-top: 2px'><a href='#'><img src='./img/externalize_button.png'></a></span>
							</div>`,
					body: {
						autoheight: true,
						id: tab_id.replace("tab", "tab_body"),
						autowidth: true,
						cols: [
							{
								rows: [
									/*{ view: "template", role: "placeholder", borderless: 1, "height": 10 },*/
									viewModule
								]
							}
						]
					}
				});

				$$(MAIN_TAB_ID).getTabbar().setValue(tab_id.replace("tab", "tab_body"));
			}

			if (module.jsMethodCallbacks && module.jsMethodCallbacks !== "DEPRECATED" && lazyClass) {

				lazyClass.runOne(module.jsMethodCallbacks)
			}

			return lazyClass;
		},

		getModuleOpenedId(moduleId) {

			let tabItems = undefined;
			
			if ($$(MAIN_TAB_ID)) {
				
				tabItems = $$(MAIN_TAB_ID).getMultiview().getChildViews();
			}
			
			let winItems = Array.from(window.wm.getAll());

			if (tabItems) {

				let tabBodyId = "tab_body_" + moduleId;
				let tabBody = tabItems.find(e => e.config.id.indexOf(tabBodyId) !== -1);

				if (tabBody) {

					return tabBody.config.id;
				}
			}

			if (winItems) {

				let winId = "win_" + moduleId;
				return winItems.find(e => e.indexOf(winId) !== -1);
			}

			return undefined;
		},

		open(module, moduleAction, viewTypeId, drawCallback, args, instanceCallback) {

			let instance = undefined;

			try {

				const lazy = async _ => {

					let jsFile = util.getContextPath() + "/" + module.jsFile;

					const mod = await import(jsFile);

					if (viewTypeId === VIEW_TYPE_CUSTOM && drawCallback) {

						instance = util.module.loadInCustom(module, moduleAction, mod, args, drawCallback);
					}
					else if (!viewTypeId && module.viewType) {

						if (module.viewType.id === VIEW_TYPE_TAB ||
							viewTypeId === VIEW_TYPE_TAB) {

							instance = util.module.loadInTab(module, moduleAction, mod, args);
						}
						else if (module.viewType.id === VIEW_TYPE_WINDOW ||
							viewTypeId === VIEW_TYPE_WINDOW) {

							instance = util.module.loadInWindow(module, moduleAction, mod, args);
						}
					}
					else {

						if (viewTypeId === VIEW_TYPE_TAB) {

							instance = util.module.loadInTab(module, moduleAction, mod, args);
						}
						else if (viewTypeId === VIEW_TYPE_WINDOW) {

							instance = util.module.loadInWindow(module, moduleAction, mod, args);
						}
					}

					if (instance && instanceCallback) {

						instanceCallback(instance);
					}
				}

				lazy();

			} catch (error) {

				console.log(error);
			}
		},

		executeCommand(command, allPks, selectedPksCallback, cmdCallbacks) {

			let selectedPks = [];

			if (selectedPksCallback) {

				selectedPks = selectedPksCallback();
			}

			if (command && !commandExecCallback() && command.moduleAction) {

				open_module();
			}

			function open_module() {

				let processPks = selectedPks;
				let viewTypeId = undefined;
				let executeModule = false;

				if (command.viewType) {

					viewTypeId = command.viewType.id;
				}

				if (processPks.length > 0) {

					// Multiselect not allowed with 2 o more selected
					if (processPks.length > 1 && command.multiselect === false) {

						webix.alert({
							title: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT"),
							text: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT_DESCRIPTION"),
							type: "alert-error",
							width: 500
						});
					}
					// One or more selected and multiselect allowed
					else {

						executeModule = true;
					}
				}
				// No rows selected
				else {
					// Required one or more selected
					if (command.itemRequired) {
						webix.alert({
							title: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED"),
							text: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED_DESCRIPTION"),
							type: "alert-warning",
							width: 500
						});
					}
					// No required select rows
					else {
						executeModule = true;
						processPks = allPks;
					}
				}

				let args = {};

				if (executeModule) {

					args.selectedPks = [];

					if (command.args != undefined) {

						try {
							args = JSON.parse(command.args);

						} catch (error) {

							console.error(error);
							args = {};
						}
					}

					if (Array.isArray(args.selectedPks) && args.selectedPks.length == 0 &&
						Array.isArray(processPks) && processPks.length > 0) {

						args.selectedPks = processPks;
					}

					executeCommand();
				}

				// Only single selection
				function executeCommand() {

					let cmd = command;

					if (Array.isArray(command.elementTypeCommand) && command.elementTypeCommand.length > 0 && args.selectedPks.length === 1) {

						let selectedPk = args.selectedPks[0];
						let elementType = window.em.getByField(EntityType.ELEMENT, "id", selectedPk).elementType;

						let elementTypeCommand = command.elementTypeCommand.find(e => e.elementType === elementType.id);

						if (elementTypeCommand) {

							cmd = elementTypeCommand;

							if (cmd.viewType) {

								viewTypeId = cmd.viewType.id;
							}
						}
					}

					let module = util.module.getFromModuleAction(cmd.moduleAction.id);

					util.module.open(module, cmd.moduleAction, viewTypeId, null, args);
				}
			}

			function commandExecCallback() {

				let cmdCallback = undefined;

				if (Array.isArray(cmdCallbacks) && cmdCallbacks.length > 0) {

					cmdCallback = cmdCallbacks.find(cmdCall => cmdCall.commandId === command.id);

					if (cmdCallback) {

						cmdCallback.function(selectedPks);
					}
				}

				return cmdCallback != undefined;
			}
		},

		getFromModuleAction(moduleActionId) {

			let modules = window.em.getAll(EntityType.MODULE);

			if (Array.isArray(modules) && modules.length > 0) {

				return modules.find(module => {

					if (Array.isArray(module.moduleActions) && module.moduleActions.length > 0) {

						return (module.moduleActions.findIndex(moduleAction => moduleAction.id === moduleActionId) !== -1);
					}

					return false;
				});
			}
		},

		checkSystemPermissions(modules, userData) {

			let systemPermissionId = [];

			if (userData.userPermissions != undefined && userData.userPermissions != null) {

				userData.userPermissions.forEach(item => {

					if (item.id != undefined && item.id != null)

						systemPermissionId.push(item.id);
				});
			}

			if (userData.role != undefined && userData.role != null &&
				userData.role.rolePermissions != undefined && userData.role.rolePermissions != null) {

				userData.role.rolePermissions.forEach(item => {

					if (item.id != undefined && item.id != null)

						systemPermissionId.push(item.id);
				});
			}

			for (let i = 0; i < modules.length; i++) {

				// check default system permision
				if (modules[i].defaultSystemPermissionId == null ||
					modules[i].defaultSystemPermissionId == undefined) {

					modules[i].isAllowed = true;

				} else if (systemPermissionId.find(e => e === modules[i].defaultSystemPermissionId) != null) {

					modules[i].isAllowed = true;

				} else {

					modules[i].isAllowed = false;
				}

				// check module action system permision
				if (modules[i].moduleActions != undefined && modules[i].moduleActions != null) {

					let moduleActions = modules[i].moduleActions;

					for (let j = 0; j < moduleActions.length; j++) {

						if (moduleActions[j].systemPermissionId == null ||
							moduleActions[j].systemPermissionId == undefined) {

							moduleActions[j].isAllowed = true;

						} else if (systemPermissionId.find(e => e === moduleActions[j].systemPermissionId) != null) {

							moduleActions[j].isAllowed = true;

						} else {

							moduleActions[j].isAllowed = false;
						}

					}

				}
			}
		},

		isAllowedModuleAction(modules, moduleActionId) {

			let moduleAction = undefined;

			if (modules && moduleActionId) {

				for (let i = 0; i < modules.length && !moduleAction; i++) {

					moduleAction = modules[i].moduleActions.find(item => item.id == moduleActionId);
				}
			}

			if (!moduleAction) {

				return false;

			} else if (moduleAction.isAllowed == null) {

				return true;

			} else {

				return moduleAction.isAllowed;
			}
		},

		isAllowedModule(modules, moduleId) {

			let module = undefined;

			if (modules && moduleId) {

				module = modules.find(item => item.id == moduleId);
			}

			if (!module) {

				return false;

			} else if (module.isAllowed == null) {

				return true;

			} else {

				return module.isAllowed;
			}
		},

		isAllowedPermission(permissionToCheck) {

			let userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
			let rolePermissions = userData.role.rolePermissions;
			let userPermissions = userData.userPermissions;
			let permissionsOK = false;

			let allPermissions = new Set(rolePermissions);

			if (Array.isArray(userPermissions) && userPermissions.length > 0) {

				userPermissions.forEach(userPermission => {

					allPermissions.add(userPermission);
				});
			}

			let arrAllPermissions = Array.from(allPermissions);

			if (Array.isArray(arrAllPermissions) && arrAllPermissions.length > 0) {

				let validPermission = arrAllPermissions.find(permission => permission.id === permissionToCheck);

				if (validPermission) {

					permissionsOK = true;
				}
			}

			return permissionsOK;
		}
	},

	ajax: {

		handleError(response) {

			if (response.status === 401) {

				window.location = "./login.html";
			}
		}
	},

	user: {

		keepAlive() {

			let response = webix.ajax().sync().post(appConfig.restUrl + "auth/keep_alive");

			if (response != undefined) {

				return JSON.parse(response.responseText)
			}

			return undefined;
		},

		auth() {
			let userData;

			if (APP_DEVMODE_ON) {
				userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
			} else {
				let sessionData = util.user.keepAlive();
				userData = null;

				if (!sessionData) {

					return false;
				}
				else {

					webix.storage.session.put(SESSION_STORAGE_SESSION_DATA, sessionData);

					if (sessionData.userId) {

						let userDataResponse = webix.ajax().sync().get(appConfig.restUrl + `users/${sessionData.userId}`);

						if (userDataResponse != undefined) {

							if (userDataResponse.responseText) {

								userData = JSON.parse(userDataResponse.responseText);

								userData.password = undefined;

								webix.storage.session.put(SESSION_STORAGE_USER_DATA, userData);
							}
						}
					}

					if (!userData) {

						return false
					}
				}
			}
			return userData;
		}
	},


	stomp: {

		getDataSourceFromTopic(topic) {

			return {
				dataSourceValues: [
					{ value: appConfig.stompUrl },
					{ value: topic }
				]
			};
		},

		connect(url, topic, onConnected, onMessage) {

			var client = new StompJs.Client({
				brokerURL: url,
				connectHeaders: {
					login: "user",
					passcode: "user"
				},
				reconnectDelay: 5000,
				heartbeatIncoming: 4000,
				heartbeatOutgoing: 4000
			});

			client.onConnect = function(frame) {
				// Do something, all subscribes must be done is this callback
				// This is needed because this will be executed after a (re)connect

				console.log("S T O M P   O V E R   W E B S O C K E T   C O N N E C T E D ");

				if (onConnected) {

					onConnected();
				}

				this.suscription = client.subscribe(topic, onMessage);
			};

			client.onWebSocketClose = function(frame) {
				console.log("Broker reported close");
			}

			client.onStompError = function(frame) {
				// Will be invoked in case of error encountered at Broker
				// Bad login/passcode typically will cause an error
				// Complaint brokers will set `message` header with a brief message. Body may contain details.
				// Compliant brokers will terminate the connection after any error
				console.log("Broker reported error: " + frame.headers["message"]);
				console.log("Additional details: " + frame.body);
			};

			client.activate();

			return client;
		},

		disconnect(client) {

			if (client.connected) {

				console.log("S T O M P   O V E R   W E B S O C K E T   D I S C O N N E C T E D ");

				client.deactivate();
			}
		},

	},

	ui: {

		dataDialog: {

			elements(ids) {

				let viewMain = {
					id: "equipment-selected" + webix.uid(),
					cols: [
						{ width: 20 },
						{
							rows: [
								{ height: 20 },
								getDatatable(),
								{ height: 20 },
								{
									cols: [
										{},
										{
											view: "button",
											css: "dialog-cancel",
											height: 41,
											width: 130,
											label: i18n.t("LBL_CLOSE"),
											on: {
												onItemClick() {

													$$(equipmentWindow).close();
												}
											}
										}
									]
								},
								{ height: 7 }
							]
						},
						{ width: 20 }
					]
				};


				function getDatatable() {

					const defaultGridOptionValues = [
						{ gridOption: { id: 1 }, value: "true" },
						{ gridOption: { id: 2 }, value: "true" },
						{ gridOption: { id: 3 }, value: "1" },
						{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "false" },
						{ gridOption: { id: 12 }, value: "false" },
						{ gridOption: { id: 13 }, value: "true" },
						{ gridOption: { id: 14 }, value: "false" },
						{ gridOption: { id: 15 }, value: "false" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" }
					];

					let metadata = {
						filter: true,
						label: "LBL_EQUIPMENT_SELECTED",
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
								needsTranslation: false,
								parameterized: false,
								position: 2,
								required: true,
								sourceField: "equipment",
								visible: true,
								label: "LBL_EQUIPMENT",
								widthPx: 360,
								gridFieldDataType: {
									id: 1,
									name: "string"
								}
							},
							{
								id: 2,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								needsTranslation: true,
								parameterized: false,
								position: 3,
								required: true,
								sourceField: "equipment_type",
								visible: true,
								label: "LBL_EQUIPMENT_TYPE",
								widthPx: 360,
								gridFieldDataType: {
									id: 1,
									name: "string"
								}
							}
						]
					};

					let data = [];
					let equipSelected = [];
					let elements = window.em.getAll(EntityType.ELEMENT);

					elements.forEach(element => {

						let equipment = self.args.selectedPks.find(item => item === element.id);

						if (equipment) {
							equipSelected.push(element);
						}
					});

					if (equipSelected && equipSelected.length > 0) {

						for (let equip of equipSelected) {

							let row = {};

							row.id = equip.id;
							row.equipment = equip.alias;
							row.equipment_type = equip.elementType.labelAlias;

							data.push(row);
						}
					}

					self.tableEquipmentSelected = new DataTable(null, null, metadata, data);

					let table;
					table = {
						rows:
							self.tableEquipmentSelected.getView()
					}

					return table;
				}

				let equipmentWindow = util.ui.loadWindow(i18n.t("LBL_EQUIPMENT_SELECTED"), viewMain, 600, 800, true);

				if (self.tableEquipmentSelected) {

					self.tableEquipmentSelected.callbacks();
				}
			}
		},

		notImplementedMessage() {

			webix.alert({
				title: i18n.t("LBL_NOT IMPLEMENTED"),
				text: i18n.t("LBL_NOT IMPLEMENTED_DESCRIPTION"),
				type: "alert-error",
				width: 500
			});
		},

		loadWindow(title, body, height, width, modal) {

			let winId = "win_aux_" + webix.uid();

			let head = {
				view: "toolbar",
				css: "active-window-head",
				elements: [
					{
						view: "label",
						label: title
					},
					{
						view: "icon",
						//css: "notification-center-close-btn",
						icon: "mdi mdi-close",
						on: {
							onItemClick: function(id) {
								$$(winId).close();
							}
						}
					}
				],
				borderless: 1
			};

			webix.ui({
				view: "window",
				id: winId,
				move: true,
				css: "default-window",
				head: head,
				height: height,
				width: width,
				modal: modal,
				body: body,
				position: function(state) {
					state.left = (document.documentElement.clientWidth / 2) - (width / 2);
					state.top = (document.documentElement.clientHeight / 2) - (height / 2);
				},
				on: {
					onBeforeShow: function() {

						if (!modal) {

							window.wm.add(winId);
						}
					},
					onDestruct: function() {

						if (!modal) {

							window.wm.delete(winId);
						}
					}
				}
			}).show();

			return winId;
		},

		loadTab(title, body) {

			let tabId = "tab_aux_" + webix.uid();
			let mainTab = $$(MAIN_TAB_ID);
			let icon = 'img/module/default.png';

			if (mainTab) {

				mainTab.addView({
					id: tabId,
					header: `<div style='float: left; width: 250px;'>
								<img class='tab-icon' src='${icon}'/>
								<span class='tab-text'>${title}</span>
								<span style='float: right; padding-top: 2px'><a href='#'><img src='./img/externalize_button.png'></a></span>
							</div>`,
					body: {
						autoheight: true,
						id: tabId.replace("tab", "tab_body"),
						autowidth: true,
						cols: [
							{
								rows: [
									body
								]
							}
						]
					}
				});

				mainTab.getTabbar().setValue(tabId.replace("tab", "tab_body"));
			}
		},

		setWindowTitle(winId, title) {

			let win = $$(winId);

			if (win && win.config.view === "window") {

				let head = win.getHead();

				if (head) {

					let titleView = head.queryView({ view: "label" });

					if (titleView) {

						titleView.setHTML(`<div>${title}</div>`);
					}
				}
			}
		},

		setTitle(containerId, title, shortTitle, icon) {

			let container = $$(containerId);

			if (container && container.config.view === "window") {

				let head = container.getHead();

				if (head) {

					let titleView = head.queryView({ view: "label" });

					if (titleView) {

						titleView.setHTML(`<div>${title}</div>`);
					}
				}

			} else if (container && container.getParentView().config.view === "multiview") {

				let parent = container.getParentView();

				if (parent.getParentView().config.view === "tabview") {

					let topParent = parent.getParentView();
					let tabs = $$(topParent.config.id).getTabbar();
					let tabToChange = tabs.config.options.find(e => e.id === containerId);

					if (tabToChange) {

						let newIcon = icon ? icon : "img/module/default.png";
						let newTabTitle = (shortTitle) ? shortTitle : title;

						tabToChange.value = `<div style='float: left; width: 250px;'>
												<img class='tab-icon' src='${newIcon}'/>
												<span class='tab-text'>${newTabTitle}</span>
												<span style='float: right; padding-top: 2px'>
												<a href='#'><img src='./img/externalize_button.png'></a>
												</span>
											</div>`;

						tabs.render();

					}
				}
			}
		},

		resizeWindow(winId, width, height) {

			let win = $$(winId);

			if (win && win.config.view === "window") {

				let prevWidth = win.config.width;
				let prevHeight = win.config.height;


				win.config.width = width ? width : win.config.width;
				win.config.height = height ? height : win.config.height;

				if (prevWidth != win.config.width || prevHeight != win.config.height) {

					win.resize();
				}
			}
		},

		centerWindow(winId) {

			let win = $$(winId);

			if (win && win.config.view === "window") {

				let x = (document.documentElement.clientWidth / 2) - (win.config.width / 2);
				let y = (document.documentElement.clientHeight / 2) - (win.config.height / 2);

				win.setPosition(Math.floor(x), Math.floor(y));
			}
		},

		getViewDrawLoading(noText) {

			return {
				view: "template",
				template: function() {

					return `<div class='empty-app-inner'><img src='img/loading.svg' align='center'><span class='empty-app-inner-text'>${(!noText ? i18n.t("LBL_LOADING_DATA") : "")}</span></div>`;
				}
			}
		},

		getViewNoData(noText) {

			return {
				view: "template",
				template: function() {

					return `<div class='empty-app-inner'><img src='img/not_found_150.png' align='center'><span class='empty-app-inner-text'>${(!noText ? i18n.t("LBL_UNAVAILABLE") : "")}</span></div>`;
				}
			}
		},
		
		isInside(limits, lon, lat) {
			
			let res = true;
			const Limit = { LON_INI: 0, LAT_INI: 1, LON_END: 2, LAT_END: 3 }
			
			let ext, point, elementPoint, feature, coordsFeatures;
					
			if (Array.isArray(limits) && limits.length === 4) {
	
				let destLoc = [limits[Limit.LON_END], limits[Limit.LAT_END]];
				let currentLoc = [limits[Limit.LON_INI], limits[Limit.LAT_INI]];
				
				ext = ol.extent.boundingExtent([destLoc, currentLoc]);
				ext = ol.proj.transformExtent(ext, ol.proj.get('EPSG:4326'), ol.proj.get('EPSG:3857'));
				
				point = ol.proj.fromLonLat([parseFloat(lon), parseFloat(lat)]);
				elementPoint = new ol.geom.Point(point);
				feature = new ol.Feature(elementPoint);
				coordsFeatures = feature.getGeometry().getCoordinates();
				
				res = ol.extent.containsXY(ext, coordsFeatures[0], coordsFeatures[1]);
			}
			
			return res;
		},

		calculateTextWidth(text, fontFamily, fontSize, letterSpacing) {

			let width = 0;

			let dText = document.createElement("div");
			dText.id = webix.uid();
			dText.style.fontSize = fontSize;

			if (letterSpacing) {

				dText.style.letterSpacing = letterSpacing;
			}

			dText.style.fontFamily = fontFamily;
			dText.style.position = "absolute";
			dText.style.visibility = "hidden";
			dText.style.whiteSpace = "nowrap";
			dText.innerHTML = text;
			document.body.appendChild(dText);

			width = dText.clientWidth;

			dText.remove();

			return width;
		}
	},

	element: {

		getElementTypeParamId(elementValueId) {

			const ELEMENT_TYPE_PARAM = "ElementTypeParam";
			let elementTypeParamArray = [];
			let elementTypeParamId = undefined;

			if (elementValueId) {

				let elementValueArray = elementValueId.split(":");

				if (elementValueArray.length === 5) {

					elementTypeParamArray.push(ELEMENT_TYPE_PARAM);
					elementTypeParamArray.push(elementValueArray[1]);
					elementTypeParamArray.push(elementValueArray[4]);
					elementTypeParamArray.push(elementValueArray[3]);

					elementTypeParamId = elementTypeParamArray.join(":");
				}
			}

			return elementTypeParamId;
		},

		getColor(elementTypeState) {

			let colors = ["#ffcc00", "#008000", "#800080", "#ff0000"]
			let color = colors[0];

			if (elementTypeState) {

				if (elementTypeState.color) {

					color = elementTypeState.color
				}
				else if (elementTypeState.id) {

					let idx = elementTypeState.id.substring(elementTypeState.id.lastIndexOf(":") + 1);

					if (!isNaN(idx)) {

						let colorPos = parseInt(idx, 10);

						if (colorPos < colors.length) {

							color = colors[colorPos];
						}
					}
				}
			}

			return color;
		}
	},

	datatable: {

		concatTest(value) {

			return "This is a test with " + value;
		},

		dateExpired(data) {

			let timeService = window.sm.getService(Services.TIME_SERVICE);
			let actualTimeMs = timeService.getEpoch();

			return (data instanceof Date ? actualTimeMs > data.getTime() : actualTimeMs > data) ? 0 : 1;
		},

		clearDateExpired(data) {

			let timeService = window.sm.getService(Services.TIME_SERVICE);
			let actualTimeMs = timeService.getEpoch();

			if (data instanceof Date) {

				return (actualTimeMs > data.getTime() ? null : data.toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES));
			}
			else {

				return (actualTimeMs > data ? null : data);
			}
		},
		cronDescription(cronExpression) {

			let translate = prettyCron.toString(cronExpression, true);

			if (translate != undefined) {

				return translate;
			}

		},
		getNextExec(cronExpression) {

			let nextDate = moment(prettyCron.getNextDate(cronExpression, true)).format("DD/MM/YYYY HH:mm:ss");

			if (nextDate != undefined) {

				return nextDate
			}
		},
		hasData(data) {

			return (data !== null && data !== undefined) ? "true" : "false";

		},
		boolToString(data) {

			return ((data !== null && data !== undefined) ? (data !== true ? "false" : "true") : "false");

		},

		getLabelToFanState(fanState) {

			return i18n.t(FanSate.find(e => e.value === fanState).label);
		},

		getCurrentPlanOrderAlias(planOrderId, obj) {

			let planOrder = undefined;

			if (planOrderId && obj && Array.isArray(obj.planOrders) && obj.planOrders.length) {

				planOrder = obj.planOrders.find(e => e.id == planOrderId);
			}

			return planOrder ? planOrder.alias : "-";
		},

		getCurrentPlanOrderOrder(planOrderId, obj) {

			let planOrder = undefined;

			if (planOrderId && obj && Array.isArray(obj.planOrders) && obj.planOrders.length) {

				planOrder = obj.planOrders.find(e => e.id == planOrderId);
			}

			return planOrder ? planOrder.planOrderOrder : "-";
		},

		getLocalDateString(value, obj) {

			if (obj["dataTypeId"] == DataType.TIMESTAMP) {

				return util.toLocaleString(value / 1000);

			} else {

				return value;
			}
		}


	},

	tree: {

		getNodes(items, relationships, remapedFields, translate) {

			const CHILD_ID_IDX = 1;

			let nodes = [];
			let mapNodes = new Map();

			if (Array.isArray(items) && items.length > 0) {

				items.forEach(item => {

					let node = {
						id: item.id + "_" + item.id,
						value: item.label,
						id_: item.id,
						data: [],
						params: {},
						props: {
							translate: translate,
							folder: item.folder ? item.folder : false
						}
					};

					mapNodes.set(node.id, node);
				});

				let hierarchies = relationships.map(e => {

					let hierarchy = undefined;
					let parentNodeId = e[remapedFields.parentNodeId] + "_" + e[remapedFields.parentNodeId];
					let childNodeId = e[remapedFields.childNodeId] + "_" + e[remapedFields.childNodeId];

					if (parentNodeId && childNodeId) {

						hierarchy = {
							id: e.id,
							parentNode: mapNodes.get(parentNodeId),
							childNode: mapNodes.get(childNodeId)
						};
					}

					return hierarchy;
				});

				let childNodeIds = fillNodes(hierarchies);

				childNodeIds.forEach(childNodeId => {

					mapNodes.delete(childNodeId);
				});

				mapNodes.forEach(function(value) { // Full params => function(value, key, map) 

					nodes.push(value);
				});
			}

			function fillNodes(hierarchies) {

				let childNodeIds = [];

				if (Array.isArray(hierarchies) && hierarchies.length > 0) {

					hierarchies.forEach(hierarchy => {

						let parentNode = hierarchy.parentNode;
						let childNode = hierarchy.childNode;

						if (parentNode && childNode) {

							let parentNodeId = parentNode.id;
							let childNodeId = childNode.id;

							if (parentNodeId && childNodeId) {

								let parentTreeData = mapNodes.get(parentNodeId);
								let childTreeData = mapNodes.get(childNodeId);

								if (parentTreeData && childTreeData) {

									parentTreeData.props.folder = true;

									let newChild = webix.copy(childTreeData);

									newChild.id = parentTreeData.id.split("_")[CHILD_ID_IDX] + "_" + childTreeData.id.split("_")[CHILD_ID_IDX];
									parentTreeData.data.push(newChild);

									mapNodes.set(childNodeId, newChild)

									childNodeIds.push(childNodeId);
								}
							}
						}
					});
				}

				return childNodeIds;
			}

			return nodes;
		}
	},


	toLocaleString(ts) {

		if (!ts) {

			return undefined;
		}

		let userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
		let date = new Date(ts * 1000);

		if (userData) {

			date = new Date(ts * 1000).toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES);
		}

		return date;
	},

	toLocaleDateString(ts) {

		if (!ts) {

			return undefined;
		}

		let userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
		let date = new Date(ts * 1000);

		if (userData) {

			date = new Date(ts * 1000).toLocaleDateString(i18n.getLocale());
		}

		return date;
	},

	resourceExist(url) {

		var http = new XMLHttpRequest();
		http.open('HEAD', url, false);
		http.send();
		return http.status != 404;
	},

	toLocaleTimeString(ts) {

		if (!ts) {

			return undefined;
		}

		let userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
		let date = new Date(ts * 1000);

		if (userData) {

			date = new Date(ts * 1000).toLocaleTimeString(i18n.getLocale());
		}

		return date;
	},

	getHash(input) {
		var hash = 0, len = input.length;

		for (var i = 0; i < len; i++) {

			hash = ((hash << 5) - hash) + input.charCodeAt(i);
			hash |= 0; // to 32bit integer
		}

		return hash;
	},

	getContextPath() {

		return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
	},

	getDirNameFromBasicElementId(id) {

		let dir;

		if (typeof id === 'string') {

			dir = id.replace(":", "_");
		}

		return dir;
	},

	fileSize(b) {
		let u = 0;
		let s = 1024;

		while (b >= s || -b >= s) {
			b /= s;
			u++;
		}

		return (u ? b.toFixed(1) + ' ' : b) + ' KMGTPEZY'[u] + 'B';
	},

	getSimpleId(key) {

		if (key) {

			if ((key.match(/:/g) || []).length == 1) {

				return key.substring(key.indexOf(':') + 1);
			}
			else {

				return key;
			}
		}
	},

	fillTemplate(templateString, templateVars) {
		var func = new Function(...Object.keys(templateVars), "return `" + templateString + "`;")
		return func(...Object.values(templateVars));
	}
};
