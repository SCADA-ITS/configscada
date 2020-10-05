if (window.app)
	app.wins = {
		winInstances: new Map(),
		active: null,
		hiddenWindows: [],
		setActiveStyle: function(winId) {
			if (app.wins.active && $$(app.wins.active))
				webix.html.removeCss($$(app.wins.active).$view, "active_win");
			webix.html.addCss($$(winId).$view, "active_win", true);
			app.wins.active = winId;
		},
		forEachWindow: function(func) {
			var views = $$("toolbar").getChildViews();
			for (var i = 1; i < views.length; i++) {
				if (views[i].config.id.indexOf("_button") != -1) {
					var id = views[i].config.id.replace("button", "win");
					if ($$(id))
						func.call(this, id);
				}
			}
		},
		hideAllWindows: function() {
			this.forEachWindow(function(id) {
				if ($$(id).isVisible()) {
					$$(id).hide();
					app.wins.hiddenWindows.push(id);
					webix.html.removeCss($$(id.replace("_win", "_button")).$view, "active");
				}
			});
		},
		restoreAllWindows: function() {

			for (var i = 0; i < app.wins.hiddenWindows.length; i++) {
				$$(app.wins.hiddenWindows[i]).show();
			}

			app.wins.hiddenWindows = [];
		},
		closeAllWindows: function() {
			this.forEachWindow(function(id) {
				if ($$(id).isVisible()) {
					$$("toolbar").removeView(id.replace("_win", "_button"));
					$$(id).close();
					app.buttonCount--;
				}
			});
		},
		getVisibleWinCount: function() {
			var count = 0;
			this.forEachWindow(function(id) {
				if ($$(id).isVisible())
					count++;
			});
			return count;
		},
		getPosition: function(state) {
			state.left = this.config.left;
			state.top = this.config.top;
			if (state.height + 40 >= state.maxHeight) {
				state.height = state.maxHeight - 40;
			}
			if (this.config.fullscreen) {
				if (!this.config.lastWindowPos)
					this.config.lastWindowPos = { top: state.top, left: state.left };
				state.top = state.left = 0;
			}
			else {
				if (this.config.lastWindowPos) {
					var last = this.config.lastWindowPos;
					delete this.config.lastWindowPos;
					state.top = last.top;
					state.left = last.left;
				}
				if (state.left + state.width > state.maxWidth) {
					state.left -= state.left + state.width - state.maxWidth;
				}
				if (state.top + state.height + 40 > state.maxHeight) {
					state.top -= state.top + state.height + 40 - state.maxHeight;
				}
			}

		},

		getWindowParams: function(module) {

			var window_params = webix.copy(window_params_default);

			var hasMinButton = app.module.getWindowParamValue(module, moduleType.window.params.hasMinButton);
			var hasMaxButton = app.module.getWindowParamValue(module, moduleType.window.params.hasMaxButton);
			var isMaximized = app.module.getWindowParamValue(module, moduleType.window.params.isMaximized);
			var heigth = app.module.getWindowParamValue(module, moduleType.window.params.height);
			var width = app.module.getWindowParamValue(module, moduleType.window.params.width);
			var allowMultipleInstances = app.module.getWindowParamValue(module, moduleType.window.params.allowMultipleInstances);
			var icon = app.module.getWindowParamValue(module, moduleType.window.params.icon);
			var image = app.module.getWindowParamValue(module, moduleType.window.params.image);
			var color = app.module.getWindowParamValue(module, moduleType.window.params.color);
			var label = i18n.t(app.module.getWindowParamValue(module, moduleType.window.params.label));

			if (!webix.isUndefined(hasMinButton)) window_params.hasMinButton = hasMinButton;
			if (!webix.isUndefined(hasMaxButton)) window_params.hasMaxButton = hasMaxButton;
			if (!webix.isUndefined(isMaximized)) window_params.isMaximized = isMaximized;
			if (!webix.isUndefined(heigth)) window_params.heigth = heigth; else window_params.heigth = 500;
			if (!webix.isUndefined(width)) window_params.width = width; else window_params.width = 800;
			if (!webix.isUndefined(allowMultipleInstances)) window_params.allowMultipleInstances = allowMultipleInstances;
			if (!webix.isUndefined(icon)) window_params.icon = icon;
			if (!webix.isUndefined(image)) window_params.image = image;
			if (!webix.isUndefined(color)) window_params.color = color;
			if (!webix.isUndefined(label)) window_params.label = label;

			return window_params;
		},

		showWindow: function(module) {

			var window_params = this.getWindowParams(module);
			var winId = "";
			var jsFile = module.jsFile;
			var jsMethodDraw = module.jsMethodDraw;
			var jsMethodCallbacks = module.jsMethodCallbacks;

			winId = module.id + "_win";
			if (window_params.allowMultipleInstances) {

				var currentInstance = this.winInstances.get(module.id);

				if (!currentInstance) {
					winId = "1_" + module.id + "_win";
					this.winInstances.set(module.id, 1);
					currentInstance = 1;
				}
				else {
					currentInstance++;
					winId = currentInstance + "_" + winId;
					this.winInstances.set(module.id, currentInstance);
				}
			}

			if (!$$(winId)) {
				var c = app.wins.getVisibleWinCount();
				var lastLeft = document.documentElement.clientWidth / 2 - window_params.width / 2 + 15 * c;
				var lastTop = document.documentElement.clientHeight / 2 - window_params.heigth / 2 - 40 + 25 * c;
				var lastHeight = window_params.heigth;
				var lastWidth = window_params.width;
				var fullScreen = false;
				var moduleView = {
					id: "splash-" + winId,
					css: "empty-app",
					rows: [
						{
							template: function() {
								if (window_params.image) {
									icon = "<img src='" + window_params.image + "' align='center'>";
								}
								else if (icon) {
									icon = "<span class='webix_icon mdi mdi-" + icon + "'></span>";
								}
								return "<div class='empty-app-inner' style='background-color:" + window_params.color + ";'>" + icon + "</div>"
							}

						}
					]
				};

				webix.ui({
					view: "window",
					id: winId,
					css: "popup-window-custom ",
					position: app.wins.getPosition,
					left: lastLeft,
					top: lastTop,
					move: true,
					resize: true,
					toFront: true,
					height: lastHeight,
					width: lastWidth,
					head: app.wins.ui.toolbar(
						window_params.label,
						// onNewWindow
						function() {
							webix.message({
								text: "Not implemented",
								type: "error",
								expire: 2000,
								id: "msg_not_implementes"
							});
						},
						// onMin
						function() {
							$$(winId).hide();
							webix.html.removeCss($$(obj.id + "_button").$view, "active");
							// onMax
						}, function() {
							fullScreen = !fullScreen;

							if (fullScreen) {
								$$(winId).config.left = 0;
								$$(winId).config.top = 0;
								$$(winId).config.height = document.documentElement.clientHeight - 40;
								$$(winId).config.width = document.documentElement.clientWidth;
							}
							else {
								$$(winId).config.left = lastLeft;
								$$(winId).config.top = lastTop;
								$$(winId).config.height = lastHeight;
								$$(winId).config.width = lastWidth;
							}
							$$(winId).resize();
							// onClose
						}, function() {
							$$("toolbar").removeView(winId.replace("_win", "_button"));
							$$(winId).close();
							app.buttonCount--;
						}
					),
					body: moduleView,
					on: {
						onBeforeShow: function() {
							app.beforeWinShow(module, winId);
							if ($$("module-" + module.id) !== undefined) {
								$$("splash-" + winId).hide();
								$$("module-" + module.id).show();
							}
						},
						onViewMoveEnd: function() {
							if (!fullScreen) {
								lastLeft = $$(winId).config.left;
								lastTop = $$(winId).config.top;
							}
						},
						onViewResize: function() {
							if (!fullScreen) {
								lastHeight = $$(winId).config.height;
								lastWidth = $$(winId).config.width;
							}
						}
					}
				});

				$$(winId).show();

				app.wins.setActiveStyle(winId);

				app.module.loadScript(jsFile, function(path, result) {

					if (result === "ok") {
						webix.delay(function() {

							webix.extend($$(winId), webix.ProgressBar);

							$$(winId).showProgress({
								type: "bottom",
								hide: true
							});

							if (typeof window[jsMethodDraw] === "function") {
								moduleView = window[jsMethodDraw](module, currentInstance, null);

								if (!webix.isUndefined(moduleView)) {
									webix.ui(moduleView, $$("splash-" + winId));

									if (typeof window[jsMethodCallbacks] === "function") {
										window[jsMethodCallbacks](module, currentInstance, null);
									}
								}
								else {

									let viewUnavailable = {

										id: "splash-info-" + winId,
										css: "empty-app",
										rows: [
											{
												template: function() {
													if (window_params.image) {
														icon = "<img src='" + window_params.image + "' align='center'>";
													}
													else if (icon) {
														icon = "<span class='webix_icon mdi mdi-" + icon + "'></span>";
													}
													return "<div class='empty-app-inner' style='background-color:" + window_params.color + ";'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_UNAVAILABLE") + "</span></div>";
												}
											}
										]
									}

									webix.ui(viewUnavailable, $$(winId).getBody());
								}
							}

							$$(winId).hideProgress();
						}, null, null, 1);
					}
				});
			}

			$$("winmenu").hide();
		},
		showEmptyApp: function(obj) {
			var winId = obj.id + "_win";

			if (!$$(winId)) {
				var c = app.wins.getVisibleWinCount();
				var lastLeft = document.documentElement.clientWidth / 2 - 400 + 15 * c;
				var lastTop = document.documentElement.clientHeight / 2 - 225 - 40 + 25 * c;
				var lastHeight = 450;
				var lastWidth = 800;
				var fullScreen = false;
				webix.ui({
					view: "window",
					id: winId,
					css: "popup-window-custom",
					position: app.wins.getPosition,
					left: lastLeft,
					top: lastTop,
					move: true,
					resize: true,
					toFront: true,
					height: lastHeight,
					width: lastWidth,
					head: this.ui.toolbar(
						obj.value + " (Not implemented) ",
						function() {
							$$(winId).hide();
							webix.html.removeCss($$(winId + "_button").$view, "active");
						}, function() {
							fullScreen = !fullScreen;

							if (fullScreen) {
								$$(winId).config.left = 0;
								$$(winId).config.top = 0;
								$$(winId).config.height = document.documentElement.clientHeight - 40;
								$$(winId).config.width = document.documentElement.clientWidth;
							}
							else {
								$$(winId).config.left = lastLeft;
								$$(winId).config.top = lastTop;
								$$(winId).config.height = lastHeight;
								$$(winId).config.width = lastWidth;
							}
							$$(winId).resize();
						}, function() {
							$$("toolbar").removeView(obj.id + "_button");
							$$(winId).close();
							app.buttonCount--;
						}
					),
					body: {
						css: "empty-app",
						template: function(module) {
							var icon = "";
							if (module.img) {
								icon = "<img src='" + obj.img + "' align='center'>";
							}
							else if (obj.icon) {
								icon = "<span class='webix_icon mdi mdi-" + obj.icon + "'></span>";
							}
							return "<div class='empty-app-inner' style='background-color:" + obj.color + ";'>" + icon + "</div>"
						},
						data: obj
					},
					on: {
						onBeforeShow: function() {
							app.beforeWinShow(obj);
						},
						onViewMoveEnd: function() {
							if (!fullScreen) {
								lastLeft = $$(winId).config.left;
								lastTop = $$(winId).config.top;
							}
						},
						onViewResize: function() {
							if (!fullScreen) {
								lastHeight = $$(winId).config.height;
								lastWidth = $$(winId).config.width;
							}
						}
					}
				})
			}
			$$(winId).show();
			$$("winmenu").hide();
			app.wins.setActiveStyle(winId);
		},
		showApp: function(name) {
			var winId = name + "_win";
			var c = app.wins.getVisibleWinCount();
			if (!$$(winId)) {
				var config = app.wins.ui[name];
				webix.ui({
					view: "window",
					id: winId,
					css: "popup-window-custom app " + config.css || "",
					position: app.wins.getPosition,
					resize: true,
					left: document.documentElement.clientWidth / 2 - 400 + 15 * c,
					top: document.documentElement.clientHeight / 2 - 225 - 40 + 25 * c,
					move: true,
					toFront: true,
					height: 450,
					width: 800,
					head: app.wins.ui.toolbar.apply(this, config.toolbar()),
					body: config.body(),
					on: config.events
				});

			}
			$$(winId).show();
			if (name == "scheduler" && $$("scheduler").getScheduler())
				$$("scheduler").getScheduler().updateView();
			else if (name == "gantt" && window.gantt)
				gantt.render();
			app.wins.setActiveStyle(winId);
		},
		ui: {
			toolbar: function(title, onNewWindow, onHide, onMinMax, onClose) {
				return {
					view: "toolbar",
					height: 28,
					css: "window-toolbar",
					margin: 0,
					padding: 0,
					cols: [
						{
							view: "label",
							label: "<img src='img/window-icon.png' class='header-window-icon'/> " + title
						},
						{
							view: "button",
							type: "image",
							image: "img/new_window_button.png",
							width: 36,
							height: 20,
							css: "new_window_button webix_transparent",
							on: {
								onItemClick: onNewWindow
							}
						},
						{
							view: "button",
							type: "image",
							image: "img/hide_button.png",
							width: 36,
							height: 20,
							css: "hide-button webix_transparent",
							on: {
								onItemClick: onHide
							}
						},
						{
							view: "button",
							type: "image",
							image: "img/resize_button.png",
							width: 36,
							height: 20,
							css: "resize-button webix_transparent",
							on: {
								onItemClick: onMinMax
							}
						},
						{
							view: "button",
							type: "image",
							image: "img/close_button.png",
							width: 36,
							height: 20,
							css: "close-button webix_transparent",
							on: {
								onItemClick: onClose
							}
						}
					]
				};
			},
			scheduler: {
				css: "no_border",
				toolbar: function() {
					return [
						"DHTMlX Scheduler",
						function() {
							$$('scheduler_win').hide();
							webix.html.removeCss($$("scheduler_button").$view, "active");
						}, function() {
							$$("scheduler_win").config.fullscreen = !$$("scheduler_win").config.fullscreen;
							$$("scheduler_win").resize();

							$$("scheduler").config.fullscreen = !$$("scheduler").config.fullscreen;
							$$("scheduler").resize();
							$$("scheduler").getScheduler().updateView();
						}, function() {
							$$("toolbar").removeView("scheduler_button");
							$$('scheduler_win').hide();
							app.buttonCount--;
						}
					]
				},
				body: function() {
					return {
						view: "dhx-scheduler",
						id: "scheduler",
						date: new Date(2015, 7, 5),
						mode: "month",
						init: function() {
							this.getScheduler().config.xml_date = "%Y-%m-%d %H:%i";
							this.getScheduler().config.first_hour = 6;
							this.getScheduler().config.multi_day = false;
						},
						ready: function() {
							this.getScheduler().parse(test_data_set_2015);
						}
					}
				},
				events: {
					onBeforeShow: function() {
						app.beforeWinShow("scheduler");
					}
				}
			},
			gantt: {
				toolbar: function() {
					return [
						"DHTMlX Gantt",
						function() {
							$$('gantt_win').hide();
							webix.html.removeCss($$("gantt_button").$view, "active");
						},
						function() {
							$$("gantt_win").config.fullscreen = !$$("gantt_win").config.fullscreen;
							$$("gantt_win").resize();

							$$("gantt").config.fullscreen = !$$("scheduler").config.fullscreen;
							$$("gantt").resize();
							gantt.render();
						}, function() {
							$$("toolbar").removeView("gantt_button");
							$$('gantt_win').hide();
							app.buttonCount--;
						}
					]
				},
				body: function() {
					return {
						view: "dhx-gantt",
						id: "gantt",
						init: function() {
							//do nothing
						},
						ready: function() {
							gantt.parse(tasks);
						}
					}
				},
				events: {
					onBeforeShow: function() {
						app.beforeWinShow("gantt");
					}
				}
			},
			filemanager: {
				css: "no_border ",
				toolbar: function() {
					return [
						"Filemanager",
						function() {
							$$('filemanager_win').hide();
							webix.html.removeCss($$("filemanager_button").$view, "active");
						},
						function() {
							$$("filemanager_win").config.fullscreen = !$$("filemanager_win").config.fullscreen;
							$$("filemanager_win").resize();

						}, function() {
							$$("toolbar").removeView("filemanager_button");
							$$('filemanager_win').hide();
							app.buttonCount--;
						}
					]
				},
				body: function() {
					return {
						view: "filemanager",
						id: "filemanager",
						disabledHistory: true,
						data: filemanagerData
					}
				},
				events: {
					onBeforeShow: function() {
						app.beforeWinShow("filemanager");
					},
					onShow: function() {

						if (!$$("filemanager").$$("tree").getSelectedId())
							$$("filemanager").$$("tree").select($$("filemanager").getFirstChildId(0));
					}
				}
			}
		}
	};


