import { synopticData } from "../data/synoptics.js";
import { mapsData } from "../data/mapsData.js";

// Params for windowed ui
const WindowParam = Object.freeze({
	HAS_MIN_BUTTON: 1,
	HAS_MAX_BUTTON: 2,
	IS_MAXIMIZED: 3,
	HEIGHT: 4,
	WIDTH: 5,
	X: 6,
	Y: 7,
	ALLOW_MULTIPLE_INSTANCES: 8,
	IMAGE: 9,
	ICON: 10,
	COLOR: 11,
	LABEL: 12,
	RESIZABLE: 13,
	EXTERNALIZABLE: 14,
	MODAL: 15
});

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
					
					if(module.jsMethodCallbacks) {
	
						lazyClass.runOne(module.jsMethodCallbacks);
					}	
				}
			}
		},

		loadInWindow(module, moduleAction, mod, args) {

			let viewModule;
			let allowMultipleInstances = true;
			let image = 'img/module/default.png';
			let icon = 'img/module/default.png';
			let color = '#464646';
			let label = i18n.t('LBL_UNDEFINED');
			let externalizable = true;
			let height = 768;
			let width = 1280;
			let modal = false;
			let win_id = "win_" + module.id + "_" + webix.uid();

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

			// TODO: Remove this block in future
			if (module.jsMethodCallbacks === "DEPRECATED") {

				if (module.moduleType.id !== MODULE_TYPE_GENERIC_SYNOPTIC_WINDOW) {

					viewModule = mod[module.jsMethodDraw]();
				}
				else {

					if (module.moduleValues) {

						if (module.moduleType.id === MODULE_TYPE_GENERIC_SYNOPTIC_WINDOW) {

							let synopticConfigId = module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_SYNOPTIC_ID).value;
							let item = synopticData.find(e => e.id === synopticConfigId);
							let synId = "syn-" + webix.uid();
							let syn = new mod.default(item, synId);

							viewModule = {
								rows: [{
									view: "template",
									id: syn.synopticDiv,
									url: function() {
										syn.init();
									}
								}],
								on: {
									onDestruct: function(obj) {
										syn.on_destroy();
									}, onViewShow: () => {
										syn.on_show();
									}
								}
							};
						}
						else if (module.moduleType.id === MODULE_TYPE_GENERIC_MAP_WINDOW) {

							let mapConfigId = module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_MAP_ID).value;
							let item = mapsData.find(e => e.id === mapConfigId);
							let mapId = "map-" + webix.uid();
							let map = new mod.default(item, undefined, mapId);

							viewModule = {
								rows: [{
									view: "template",
									id: map.synopticDiv,
									url: function() {
										map.init();
									}
								}]
							};
						}
					}
				}
			}
			else {

				if (!args) {

					args = {};
				}

				args.containerId = win_id;

				lazyClass = new mod.default(module, moduleAction, args);

				viewModule = lazyClass.runOne(module.jsMethodDraw)
			}

			if (viewModule) {

				let head = {
					view: "toolbar",
					css: "default-window-head",
					//height: 35,
					cols: [
						{

							view: "label",
							//css: "notification-center-title", 
							label: i18n.t(label)
						},
						{
							view: "icon",
							//css: "notification-center-close-btn",
							icon: "mdi mdi-close",
							on: {
								onItemClick: function(id) {
									$$(win_id).close();
								}
							}
						}
					],
					borderless: 1
				};

				webix.ui({
					view: "window",
					id: win_id,
					move: true,
					modal: modal,
					css: "default-window",
					head: head,
					height: height,
					width: width,
					body: {
						rows: [
							viewModule
						]
					},
					position: function(state) {
						state.left = (document.documentElement.clientWidth / 2) - (width / 2);
						state.top = (document.documentElement.clientHeight / 2) - (height / 2);
					},
					on: {
						onBeforeShow: function() {

						}
					}
				});
			}

			if ($$(win_id)) {

				$$(win_id).show();
			}
			
			if (module.jsMethodCallbacks && module.jsMethodCallbacks !== "DEPRECATED" && lazyClass) {

				lazyClass.runOne(module.jsMethodCallbacks)
			}
		},

		loadInTab(module, moduleAction, mod, args) {

			let viewModule;
			let allowMultipleInstances = true;
			let image = 'img/module/default.png';
			let icon = 'img/module/default.png';
			let color = '#464646';
			let label = i18n.t('LBL_UNDEFINED');
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

			let tab_body_id = "tab_body_" + module.id;

			let tabItems = $$(MAIN_TAB_ID).getMultiview().getChildViews();

			let moduleOpened = tabItems.find(e => e.config.id.indexOf(tab_body_id) !== -1);

			if (moduleOpened && !allowMultipleInstances) {

				$$(MAIN_TAB_ID).getTabbar().setValue(moduleOpened.config.id);

				return;
			}

			// TODO: Remove this block in future
			if (module.jsMethodCallbacks === "DEPRECATED") {

				if (module.moduleType.id !== MODULE_TYPE_GENERIC_SYNOPTIC_WINDOW &&
					module.moduleType.id !== MODULE_TYPE_GENERIC_MAP_WINDOW) {
					viewModule = mod[module.jsMethodDraw]();
				}
				else {

					if (module.moduleValues) {

						if (module.moduleType.id === MODULE_TYPE_GENERIC_SYNOPTIC_WINDOW) {

							let synopticConfigId = module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_SYNOPTIC_ID).value;
							let item = synopticData.find(e => e.id === synopticConfigId);
							let synId = "syn-" + webix.uid();
							let syn = new mod.default(item, synId);

							viewModule = {
								rows: [{
									view: "template",
									id: syn.synopticDiv,
									url: function() {
										syn.init();
									}
								}],
								on: {
									onDestruct: function(obj) {
										syn.on_destroy();
									},
									onViewShow: () => {
										syn.on_show();
									}
								}
							};
						}
						else if (module.moduleType.id === MODULE_TYPE_GENERIC_MAP_WINDOW) {

							let mapConfigId = module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_MAP_ID).value;
							let item = mapsData.find(e => e.id === mapConfigId);
							let mapId = "map-" + webix.uid();
							let map = new mod.default(item, undefined, mapId);

							viewModule = {
								rows: [{
									view: "template",
									id: map.synopticDiv,
									url: function() {
										map.init();
									}
								}]
							};
						}
					}
				}
			}
			else {

				if (!args) {

					args = {};
				}

				args.containerId = tab_id;

				lazyClass = new mod.default(module, moduleAction, args);

				viewModule = lazyClass.runOne(module.jsMethodDraw)
			}

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
		},

		open(module, moduleAction, viewTypeId, drawCallback, args) {

			try {

				const lazy = async _ => {

					let jsFile = window.location.protocol + '//' + window.location.host + '/' + module.jsFile;
					
					const mod = await import(jsFile);

					if (viewTypeId === VIEW_TYPE_CUSTOM && drawCallback) {

						util.module.loadInCustom(module, moduleAction, mod, args, drawCallback);
					}
					else if (!viewTypeId && module.viewType) {

						if (module.viewType.id === VIEW_TYPE_TAB ||
							viewTypeId === VIEW_TYPE_TAB) {

							util.module.loadInTab(module, moduleAction, mod, args);
						}
						else if (module.viewType.id === VIEW_TYPE_WINDOW ||
							viewTypeId === VIEW_TYPE_WINDOW) {

							util.module.loadInWindow(module, moduleAction, mod, args);
						}
					}
					else {

						if (viewTypeId === VIEW_TYPE_TAB) {

							util.module.loadInTab(module, moduleAction, mod, args);
						}
						else if (viewTypeId === VIEW_TYPE_WINDOW) {

							util.module.loadInWindow(module, moduleAction, mod, args);
						}


					}
				}

				lazy();

			} catch (error) {

				console.log(error);
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

		checkSytemPermision(modules, userData) {

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

			if (modules != null && modules != undefined &&
				moduleActionId != null && moduleActionId != undefined) {

				for (let i = 0; i < modules.length && moduleAction == undefined; i++) {

					moduleAction = modules[i].moduleActions.find(item => item.id == moduleActionId);
				}
			}
			
			if(moduleAction == undefined  || moduleAction == null){
				
				return false;
			}
			else if (moduleAction.isAllowed == undefined || moduleAction.isAllowed == null )
			{
				return true;
			}
			else{
				
				return moduleAction.isAllowed; 
			} 
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
				css: "default-window-head",
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

					}
				}
			}).show();

			return winId;
		},

		setWindowTitle(winId, title) {

			if ($$(winId)) {

				let head = $$(winId).getHead();

				if (head) {

					let titleView = head.queryView({ view: "label" });

					if (titleView) {

						titleView.setHTML(`<div>${title}</div>`);
					}
				}
			}
		}
	},

	datatable: {

		concatTest(value) {

			return "This is a test with " + value;
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
