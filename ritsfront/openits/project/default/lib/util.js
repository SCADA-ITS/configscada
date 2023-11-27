import { synopticData } from "../data/synoptics.js";

export let util = {

	module: {
		
		getParamValue(module, param) {
			
			return module.moduleValues.find(e => e.moduleParam.id === param).value;
		},

		getWindowParamValue(module, param) {
			
			return module.windowValues.find(e => e.windowParam.id === param).value;
		},

		loadInCustom(module, moduleAction, mod, args, view) {

		},

		loadInWindow(module, moduleAction, mod, args) {

			let viewModule;
			let allowMultipleInstances, image, icon, color, label, externalizable;
			let lazyClass;

			if (module.windowValues) {

				allowMultipleInstances = module.windowValues.find(e => e.windowParam.id === WindowParam.ALLOW_MULTIPLE_INSTANCES).value === 'true';
				image = module.windowValues.find(e => e.windowParam.id === WindowParam.IMAGE).value;
				icon = module.windowValues.find(e => e.windowParam.id === WindowParam.ICON).value;
				color = module.windowValues.find(e => e.windowParam.id === WindowParam.COLOR).value;
				label = module.windowValues.find(e => e.windowParam.id === WindowParam.LABEL).value;
				externalizable = module.windowValues.find(e => e.windowParam.id === WindowParam.EXTERNALIZABLE).value === 'true';
			}

			// TODO: Remove this block in future
			if (module.jsMethodCallbacks === "DEPRECATED") {

				if (module.moduleType.id !== MODULE_TYPE_GENERIC_SYNOPTIC_WINDOW) {

					viewModule = mod[module.jsMethodDraw];
				}
				else {

					if (module.moduleValues) {

						let synopticId = module.moduleValues.find(e => e.moduleParam.id === 4).value;
						let item = synopticData.find(e => e.id === synopticId);
						let syn = new mod.default(item);

						viewModule = {
							id: "syn-" + synopticId,
							rows: [{
								view: "template",
								id: item.id,
								url: function() {
									syn.init();
								}
							}]
						};
					}
				}
			}
			else {

				lazyClass = new mod.default(module, moduleAction, args);

				viewModule = lazyClass.runOne(module.jsMethodDraw)
			}

			if (viewModule) {

				let win_id = "win_" + module.id + "_" + webix.uid();
				
				let head = {
					view: "toolbar",
					css: "default-window-head",
					//height: 35,
					elements: [
						{ 
							
							view: "label", 
							//css: "notification-center-title", 
							template: i18n.t(label)},
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
					css: "default-window",
					head: head,
					height: 720,
					width: 1400,
					body: {
						rows: [
							viewModule		
						]	
					}, 
					position: function(state) {
						state.left = (document.documentElement.clientWidth / 2) - 700;
						state.top = (document.documentElement.clientHeight / 2) - 360;
					},
					on: {
						onBeforeShow: function() {
							
						}
					}
				});
				
				if(lazyClass && module.jsMethodCallbacks) {

					lazyClass.runOne(module.jsMethodCallbacks)
					
					$$(win_id).show();
				}
			}
		},

		loadInTab(module, moduleAction, mod, args) {

			let viewModule;
			let allowMultipleInstances, image, icon, color, label, externalizable;
			let lazyClass;

			if (module.windowValues) {

				allowMultipleInstances = module.windowValues.find(e => e.windowParam.id === WindowParam.ALLOW_MULTIPLE_INSTANCES).value === 'true';
				image = module.windowValues.find(e => e.windowParam.id === WindowParam.IMAGE).value;
				icon = module.windowValues.find(e => e.windowParam.id === WindowParam.ICON).value;
				color = module.windowValues.find(e => e.windowParam.id === WindowParam.COLOR).value;
				label = module.windowValues.find(e => e.windowParam.id === WindowParam.LABEL).value;
				externalizable = module.windowValues.find(e => e.windowParam.id === WindowParam.EXTERNALIZABLE).value === 'true';

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

				if (module.moduleType.id !== MODULE_TYPE_GENERIC_SYNOPTIC_WINDOW) {

					viewModule = mod[module.jsMethodDraw];
				}
				else {

					if (module.moduleValues) {

						let synopticId = module.moduleValues.find(e => e.moduleParam.id === 4).value;
						let item = synopticData.find(e => e.id === synopticId);
						let syn = new mod.default(item);

						viewModule = {
							id: "syn-" + synopticId,
							rows: [{
								view: "template",
								id: item.id,
								url: function() {
									syn.init();
								}
							}]
						};
					}
				}
			}
			else {
				
				lazyClass = new mod.default(module, moduleAction, args);
	
				viewModule = lazyClass.runOne(module.jsMethodDraw)
			}

			if (viewModule) {

				let tab_id = "tab_" + module.id + "_" + webix.uid();

				$$(MAIN_TAB_ID).addView({
					id: tab_id,
					header: `<div style='float: left; width: 250px;'>
								<img class='tab-icon' src='${icon}'/>
								<span class='tab-text'>${i18n.t(label)}</span>
								<span style='float: right; padding-top: 2px'><a href='#'><img src='../img/externalize_button.png'></a></span>
							</div>`,
					body: {
						autoheight: true,
						id: tab_id.replace("tab", "tab_body"),
						autowidth: true,
						cols: [
							{
								rows: [
									{ view: "template", role: "placeholder", borderless: 1, "height": 10 },
									viewModule
								]
							}
						]
					}
				});

				$$(MAIN_TAB_ID).getTabbar().setValue(tab_id.replace("tab", "tab_body"));

				if (lazyClass && module.jsMethodCallbacks) {

					lazyClass.runOne(module.jsMethodCallbacks)
				}
			}
		},

		open(module, moduleAction, viewTypeId, customView, args) {

			try {

				const lazy = async _ => {

					const mod = await import(module.jsFile);

					if (viewTypeId === VIEW_TYPE_CUSTOM && customView) {

						util.module.loadInCustom(module, moduleAction, mod, args, customView);
					}
					else if (!viewTypeId && module.viewType.id === VIEW_TYPE_TAB ||
						viewTypeId === VIEW_TYPE_TAB) {

						util.module.loadInTab(module, moduleAction, mod, args);
					}
					else if (!viewTypeId && module.viewType.id === VIEW_TYPE_WINDOW ||
						viewTypeId === VIEW_TYPE_WINDOW) {

						util.module.loadInWindow(module, moduleAction, mod, args);
					}
				}

				lazy();

			} catch (error) {

				console.log(error);
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
			
			let sessionData = util.user.keepAlive();
			let userData = null;
			
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
			
			return userData;
		}
	},

	stomp: {

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


		//		connect(url, topic, onConnected, onMessage) {
		//		
		//			var client = Stomp.client(url);
		//			
		//			function onConnect() {
		//			
		//				console.log("S T O M P   O V E R   W E B S O C K E T   C O N N E C T E D ");
		//				
		//				var subscription = client.subscribe(topic, onMessage);
		//				
		//				if (onConnected) {
		//					onConnected();	
		//				}
		//			}
		//			
		//			function onError(error) {
		//		    	// display the error's message header:
		//		    	console.log(error);
		//				console.log('Socket error. ' + url + "; topic: " + topic);
		//			}
		//		    
		//			function onClose(error) {
		//		    	// display the error's message header:
		//		    	console.log(error);
		//				console.log('Socket closed. ' + url + "; topic: " + topic);
		//		    }
		//			
		//			client.connect("", "", onConnect, onError, onClose);
		//
		//			return client;
		//		},

		//		disconnect(client) {
		//			
		//			if (JSON.stringify(client) !== '{}') {
		//				
		//				console.log("S T O M P   O V E R   W E B S O C K E T   D I S C O N N E C T E D ");
		//				
		//				client.disconnect();	
		//			}
		//		},
	},

	toLocalTime(ts) {

		let userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
		let date = new Date(ts * 1000);

		if (userData) {

			let language = userData.language;
			let country = userData.country;

			if (language && country) {

				date = new Date(ts * 1000).toLocaleDateString(language + "-" + country);
			}
		}

		return date;
	},
	
	getHash(input) {
	  var hash = 0, len = input.length;

	  for (var i = 0; i < len; i++) {
		
	    hash  = ((hash << 5) - hash) + input.charCodeAt(i);
	    hash |= 0; // to 32bit integer
	  }

	  return hash;
	}
};
