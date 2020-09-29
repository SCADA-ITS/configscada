app = {
	buttonCount: 1,
	menuData: [],
	init: function() {
		webix.env.codebase = "./";
		this.load();
		this.createLayout();
		this.createToolbar();
		this.startTime();
		this.createMenu();
		
		if (!webix.storage.session.get(SESSION_STORAGE_USER_DATA)) {
			app.signOut();
		}

		webix.attachEvent("onFocusChange", function(view) {
			if (view) {
				var win = view.getTopParentView();
				// filemanager case
				if (win.getParentView())
					win = win.getParentView().getTopParentView();
				var id = win.config.id;
				if (id.indexOf("_win") != -1) {
					app.setActiveWindow(id);
				}
			}
		});
	},
	load: function() {

		var response = webix.ajax().sync().get(appConfig.restUrl + "menu/list");
		
		if (response != null && response.responseText != null) {
			this.menuData = JSON.parse(response.responseText);
		}
	},
	createLayout: function() {
		webix.ui({
			id: "screens",
			animate: false,
			cells: [
				{
					view: "layout",
					id: "main",
					css: "desktop-layout",
					type: "clean",
					cols: [
//						{
//							view: "list",
//							id: "desktop",
//							width: 105,
//							css: "desktop-items",
//
//							type: {
//								height: 110,
//								template: "<div class='desktop-item-inner'><img src='#image#'><div class='desktop-icons'> #title#</div></div>",
//								css: "desktop-item"
//							},
//							select: "multiselect",
//							drag: true,
//							data: webix.copy(desktop_icons),
//							on: {
//								onItemDblClick: app.wins.showApp
//							}
//						},
						{}
					]
				},
				app.login.getView(),
			]
		});
	},
	signIn: function() {
		webix.$$('main').show();
		webix.$$('toolbar').show();
		app.wins.restoreAllWindows();
	},
	signOut: function() {
		app.wins.closeAllWindows();
		webix.storage.session.remove(SESSION_STORAGE_USER_DATA);
		webix.$$("toolbar").hide();
		app.login.init();
		webix.$$("sign-in").show();
		webix.UIManager.setFocus("login-user"); //to set focus
	},
	lockScreen: function() {
		app.wins.hideAllWindows();
		webix.$$("toolbar").hide();
		app.login.init();
		webix.$$("sign-in").show();
		webix.UIManager.setFocus("login-password"); //to set focus
	},
	createToolbar: function() {
		webix.ui({
			view: "toolbar",
			id: "toolbar",
			paddingY: 2, height: 40,
			css: "toolbar-bottom",
			cols: [
				{
					view: "button",
					id: "start_button",
					css: "webix_transparent",
					type: "image",
					image: "img/start.png",
					width: 100,
					on: {
						onItemClick: function() {
							if ($$("winmenu").config.hidden === false) {
								$$("winmenu").hide();
							} else {
								$$("winmenu").show();
							}
						}
					}
				},
				{},
				{ view: "template", id: "time", width: 95, css: "time-template" }
			]
		});
	},
	fullScreen: function(mode) {
		var el = mode ? document.documentElement : document,
			rfs;
		if (mode) {
			rfs = (
				el.requestFullScreen ||
				el.webkitRequestFullScreen ||
				el.mozRequestFullScreen ||
				el.msRequestFullScreen ||
				el.msRequestFullscreen
			);
		}
		else {
			rfs = (
				el.cancelFullScreen ||
				el.webkitExitFullscreen ||
				el.mozCancelFullScreen ||
				el.msExitFullscreen
			);
		}

		if (rfs) {
			rfs.call(el);
			return true;
		}
		return false;
	},

	createMenu: function() {
		webix.require("./js/views/winmenu.css");
		webix.require("./js/views/winmenu.js", function() {

			var customMenu = app.drawMenu();
			webix.ui({
				view: "popup",
				id: "winmenu",
				hidden: true,
				css: "winmenu",
				body: {
					view: "layout",
					height: 547,
					width: (customMenu.length * 250 / 2) + 50,
					id: "lay",
					rows: [
						{
							view: "template",
							css: "content-user-logo",
							height: 60,
							template: function(data) {
								var userData = userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
								
								if (userData && userData.user) {
									return "<div><img class='user-logo' src='img/user_logo.png'/> <span>" + userData.user + "</span></div>"	
								}
								
							} 
						},
						{ height: 17 },
						{
							view: "layout",
							cols: customMenu
						},
						{ height: 17 },
						{
							view: "list",
							width: 400,		
							layout:"x", // responsible for horizontal arrangement
							id: "winmenu-options-list",
							css: "start-menu-list icon",
							template: function(obj) {
								var icon = "image",
									value = "value";
								if (obj.state) {
									icon += obj.state;
									value += obj.state;
								}
								return "<div class='menu-sys-icon' style='background-image:url(" + obj[icon] + ")'></div>" + obj[value];
							},
							type: {
								height: 36
							},
							select: false,
							data: [
								{ id: "lock-screen", value: i18n.t("LBL_LOCK_SCREEN"), image: "img/lock.png" },
								{ id: "sign-out", value: i18n.t("LBL_SIGN_OUT"), image: "img/signout.png" }
							],
							on: {
								onItemClick: function(id) {
									$$("winmenu").hide();
									if (id == "sign-out") {
										app.signOut();	
									}
									else if (id == "lock-screen") {
										app.lockScreen();
									}
								}
							}
						}
					]
				},
				position: function(state) {
					state.left = 0;
					state.top = document.documentElement.clientHeight - 588;

				}
			});

			app.attachEventsMenu();
		});
	},
	updateTime: function() {
		var tm = new Date();
		var h = tm.getHours();
		var m = webix.Date.toFixed(tm.getMinutes());
		var s = webix.Date.toFixed(tm.getSeconds());
		var day = tm.getDate();
		var month = tm.getMonth() + 1;
		var year = tm.getFullYear();
		var time = +h + ":" + m + ":" + s;
		var date = month + "/" + day + "/" + year;
		$$("time").setHTML("<div class='toolbar-time'>" + time + "</div><div class='toolbar-time'>" + date + "</div>");
	},
	startTime: function() {
		setInterval(this.updateTime, 100);
	},
	deleteActiveBg: function() {
		var views = $$("toolbar").getChildViews();
		for (var i = 0; i < views.length; i++) {
			var id = views[i].config.id;
			webix.html.removeCss($$(id).$view, "active");
		}
	},
	setActiveWindow: function(id) {
		app.wins.setActiveStyle(id);
		var btn = $$(id.replace("win", "button"));
		if (btn) {
			app.deleteActiveBg();
			webix.html.addCss(btn.$view, "active");
		}
	},
	// show toolbar button for a window
	beforeWinShow: function(module, winId) {
		var allowMultipleInstances = app.modules.getWindowParamValue(module, moduleType.window.params.allowMultipleInstances);
		var icon = app.modules.getWindowParamValue(module, moduleType.window.params.icon);
		var image = app.modules.getWindowParamValue(module, moduleType.window.params.image);
		var color = app.modules.getWindowParamValue(module, moduleType.window.params.color);
		var label = i18n.t(app.modules.getWindowParamValue(module, moduleType.window.params.label));
			
		var btnId = winId.replace("_win", "_button"); 
		
		var btn = $$(btnId);
		if (btn == webix.undefined) {
			var template = "";
			
			template = "<div class='" + module.$css + "'><img class='" + module.$css + "' src='" + image + "'></div>";
			
			webix.ui({
				view: "button",
				id: btnId,
				css: "toolbar-icon",
				type: "htmlbutton",
				label: template,
				tooltip: label,
				width: 40,
				on: {
					onItemClick: function() {
						$$(winId).show();

						app.deleteActiveBg();
						webix.html.addCss(btn.$view, "active");
					}
				}
			});
			btn = $$(btnId);
			$$("toolbar").addView(btn, this.buttonCount);
			app.deleteActiveBg();
			webix.html.addCss(btn.$view, "active");
			this.buttonCount++;
		}
		else {
			app.deleteActiveBg();
			webix.html.addCss(btn.$view, "active");
		}
	},

	attachEventsMenu: function() {

		var data = this.menuData;

		for (var listIdx = 0; listIdx < data.length; listIdx++) {
			var menuList = $$("menu-list" + listIdx);
			menuList.attachEvent("onItemClick", function(id) {
				var module = app.getModule(id);
				if (module) {
					app.openModule(module);
					$$("winmenu").hide();
				}
			});
		}
	},

	getModule: function(id) {

		var data = this.menuData;

		for (var pos = 0; pos < data.length; pos++) {
			for (var posMenuItm = 0; posMenuItm < data[pos].menuItems.length; posMenuItm++) {
				var menuItemData = data[pos].menuItems[posMenuItm];
				if (menuItemData.id == id) {
					return menuItemData.module;
				}
			}
		}
	},
	
	openModule: function(module) {
		
		if (module != null && module.moduleType != null) {
			if (module.moduleType.id == moduleType.window.id) {
				var local = this;
				app.wins.showWindow(module);
			}
			else {
				webix.message({
				    text:"Unknown module type = " + module.moduleType ,
				    type:"error", 
				    expire: 5000
				});	
			}
		}
	},

	drawMenu: function() {

		var menus = [];
		
		if (this.menuData) {
		
			var data = this.menuData;	
			
			for (var pos = 0; pos < data.length; pos++) {
				var menuMargin = { width: 17 };
	
				menus.push(menuMargin);
	
				var menu = {
					view: "layout",
					rows: []
				};
	
				menu.id = "menu-" + pos;
	
				var menuTitle = {
					view: "label",
					css: "start-image-menu-title"
				};
				menuTitle.label = i18n.t(data[pos].label);
	
				var menuList = {
					view: "list",
					height: 335,
					width: 240,
					css: "start-menu-list",
					template: "<div class='desktop-icons start-menu-item'><div class='start-menu-item-image-bg'><img class='start-menu-item-image' src='#image#' ></div><div class='start-menu-item-image-title'>#title#</div></div>",
					type: {
						height: 35,
					},
					select: false,
					scroll: "auto"
				};
				menuList.id = "menu-list" + pos;
				menuList.label = i18n.t(data[pos].label);
	
				menu.rows.push(menuTitle);
				menu.rows.push({ height: 17 });
				menu.rows.push(menuList);
	
				var menuListData = [];
	
				for (var posMenuItm = 0; posMenuItm < data[pos].menuItems.length; posMenuItm++) {
	
					var menuItemData = data[pos].menuItems[posMenuItm];
					var menuItem = {};
	
					menuItem.title = i18n.t(menuItemData.label);
					menuItem.id = menuItemData.id;
					menuItem.image = menuItemData.icon;
					menuItem.color = "#107c10";
					menuItem.win = "menu-" + pos + "-" + menuItemData.id;
	
					menuListData.push(menuItem);
				}
	
				menuList.data = menuListData;
	
				menus.push(menu);
			}
			
			return menus;
		}
	}
};