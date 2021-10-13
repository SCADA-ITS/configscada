import SearchDialogBox from "../../component/searchDialogBox.js";
import { util } from "../../lib/util.js";

const SIZE_SINGLE = 30;
const SIZE_MULTI = 50;

const DEFAULT_TIME_CHECK_STREAM_MS = 60000;
const DEFAULT_TIMEOUT_CONNECT_STREAM_MS = 5000;

const NO_VIDEO_IMAGE = './img/no-video.jpg';

export default class CamViewer {

	static get MODE_SINGLE() { return 1 }
	static get MODE_MULTI() { return 2 }

	static get STREAM_MJPEG() { return "stream_mjpeg" }
	constructor(config, element, w, h, mode, fit, fullScreen) {

		this.uid = webix.uid();
		this.config = config;
		this.element = element;
		this.w = w;
		this.h = mode == CamViewer.MODE_SINGLE ? h - 60 : h;
		this.mode = mode;
		this.fit = fit ? fit : false;

		this.controlsVisible = false;
		this.expandCam = false;

		this.pan = false;
		this.tilt = false;
		this.zoom = false;

		this.fullScreen = fullScreen;

		this.img = new Image();
		this.isOnline = true;
		this.isReconnecting = true;
		this.timeCheckStream = undefined;
		this.timeoutCheckStream = undefined;

		this.timeCheckStreamMs = config.camConf && config.camConf.timeCheckStreamMs ? config.camConf.timeCheckStreamMs : DEFAULT_TIME_CHECK_STREAM_MS;
		this.timeoutCheckStreamMs = config.camConf && config.camConf.timeoutConnectStreamMs ? config.camConf.timeoutConnectStreamMs : DEFAULT_TIMEOUT_CONNECT_STREAM_MS;
	}
	
	stop() {
		
		let viewNoImage;
		let self = this;

		viewNoImage = {
			id: "camviewer-" + self.uid,
			relative: true,
			template: function() {

				let w = self.fit ? "width: 100%" : "";
				let imgStyle = `max-width:100%;height:${self.h}px;${w}`;

				return `<div style="background-color: #9e9e9e; border: 2px solid; text-align: center;">
							<img align='center' style="${imgStyle}" src="${NO_VIDEO_IMAGE}">
						</div>`;
			}
		};

		if (self.timeCheckStream) {

			clearInterval(self.timeCheckStream);
		}

		if (self.timeoutCheckStream) {

			clearInterval(self.timeoutCheckStream);
		}
		
		let img = document.getElementById("img-" + self.element.id + "-" + self.uid);
				
		if (img) {

			img.src = '';
			
			webix.ui(viewNoImage, $$("camviewer-" + self.uid));
			
			console.log("Stopped camera: " + self.element.id);
		}
	}

	getView() {

		let self = this;

		let view = {
			view: "abslayout",
			id: "camviewer-" + self.element.id + "-" + self.uid,
			cells: [
				getViewNoImage(),
				self.getCamName(10, 10),
				{ id: "camviewer-buttons-" + self.uid },
				{ id: "cam-controls-" + self.uid }
			]
			, on: {
				
				onDestruct: function() {
					
					self.img.src = '';
					
					if (self.timeCheckStream) {

						clearInterval(self.timeCheckStream);
					}
			
					if (self.timeoutCheckStream) {
			
						clearInterval(self.timeoutCheckStream);
					}
				}
			}
		}

		getViewStream();

		function getViewNoImage() {

			let viewNoImage;

			viewNoImage = {
				id: "camviewer-" + self.uid,
				relative: true,
				template: function() {

					let w = self.fit ? "width: 100%" : "";
					let imgStyle = `max-width:100%;height:${self.h}px;${w}`;

					return `<div style="background-color: #9e9e9e; border: 2px solid; text-align: center;">
								<img align='center' style="${imgStyle}" src="${NO_VIDEO_IMAGE}">
							</div>`;
				}
				, on: {

					onDestruct: onDestruct
				}
			};

			return viewNoImage;
		}

		function onDestruct() {

			if (!self.isReconnecting) {
				
				let img = document.getElementById("img-" + self.element.id + "-" + self.uid);
				
				if (img) {

					img.src = '';
				}
			}
		}

		function getViewStream() {

			// TODO: Types of protocols for element subtype. Default is stream_mjpeg 
			let streamProtocol = CamViewer.STREAM_MJPEG;

			if (streamProtocol === CamViewer.STREAM_MJPEG) {

				getViewStreamMjpeg();
			}

			function getViewStreamMjpeg(url) {

				let config = self.config.camConf[CamViewer.STREAM_MJPEG];

				if (!url) {

					let elementValue = self.element.elementValues.find(e => e.elementTypeParam.id === config.url);

					if (elementValue) {

						url = elementValue.value;
					}
				}

				if (url) {

					self.img.onload = startStreaming;

					function startStreaming() {

						self.isOnline = true;
						console.log("[" + (new Date()).toISOString() + "] " + "[CamViewer] Connection success for cam: " + url);

						let viewStreamMjpeg = {
							id: "camviewer-" + self.uid,
							relative: true,
							css: "camviewer-matrix",
							template: function() {

								let imgStyle;

								if (self.w > self.h) {

									let w = self.fit ? "width: 100%" : "";
									imgStyle = `max-width:100%;height:${self.h}px;${w}`;
								}
								else {

									let h = self.fit ? "height: 100%" : "";
									imgStyle = `max-height:100%;width:${self.w}px;${h}`;
								}

								return `<div id="cam-${self.element.id}-${self.uid}" class="camviewer-div" style="background-color: #9e9e9e; border: 2px solid; text-align: center; overflow: hidden"><img id="img-${self.element.id}-${self.uid}" class="camviewer-img" align='center' style="${imgStyle}" src="${self.img.src}"></div>`;
							}
							, on: {

								onDestruct: onDestruct
							}
						};

						webix.ui(viewStreamMjpeg, $$("camviewer-" + self.uid));
						self.drawButtons();
						self.img.onload = undefined;
						self.isReconnecting = false;

					}

					function startKeepAliveStream() {

						self.timeCheckStream = setInterval(function() {

							self.timeoutCheckStream = setInterval(function() {

								if (!self.isOnline) {

									console.warn("[" + (new Date()).toISOString() + "] " + "[CamViewer] No Connection for cam: " + url);

									self.isReconnecting = true;

									if ($$("camviewer-" + self.uid)) {

										webix.ui(getViewNoImage(), $$("camviewer-" + self.uid));
										$$("camviewer-buttons-" + self.uid).hide();
									}

									console.warn("[" + (new Date()).toISOString() + "] " + "[CamViewer] Trying to connect to: " + url);

									self.img.onload = startStreaming;

									self.img.src = url;

									if (self.timeoutCheckStream) {

										clearInterval(self.timeoutCheckStream);
									}
								}

							}, self.timeoutCheckStreamMs);

							self.isOnline = false;

							self.img.onload = function() {

								self.isOnline = true;
								self.img.onload = undefined;

								if (self.timeoutCheckStream) {

									clearInterval(self.timeoutCheckStream);
								}
							};

						}, self.timeCheckStreamMs);
					}

					self.img.src = url;

					startKeepAliveStream();
				}
			}



		}

		return view;
	}


	drawButtons(containerId) {

		let self = this;
		let imgWidth = self.img.width;
		let imgHeight = self.img.height;

		let margin = 10
		let bottom = 10;

		let w = (self.w == 0) ? containerId.$width : self.w;
		let h = (self.h == 0) ? containerId.$height : self.h;

		if ((h > w) && !self.expandCam) {

			let img = document.getElementById("img-" + self.element.id + "-" + self.uid);
			
			if (img) {
				
				bottom += (h - img.height) / 2;	
			}
		}

		if (!self.fit) {

			let ratio = h / imgHeight;
			let wRatio = Math.floor(imgWidth * ratio);

			margin = Math.floor((w - wRatio) / 2);

		}

		webix.ui(getCamControls(margin, containerId), $$("cam-controls-" + self.uid));
		webix.ui(getButtons(margin, bottom), $$("camviewer-buttons-" + self.uid));
		webix.ui(self.getCamName(margin, bottom, containerId), $$("camviewer-name-" + self.uid));


		function getButtons(marginRight, btm) {

			let viewButtons = {
				id: "camviewer-buttons-" + self.uid,
				bottom: btm,
				right: marginRight,
				cols: [
					{ width: 1 }
				]
			};

			if (self.mode === CamViewer.MODE_SINGLE) {

				getViewButtonsSingleMode();
			}
			else if (self.mode === CamViewer.MODE_MULTI) {

				getViewButtonsMultiMode();
			}

			function getViewButtonsSingleMode() {

				viewButtons.height = SIZE_MULTI;

				let viewButtonExpandCam = getButtonExpandCam();

				viewButtonExpandCam.height = SIZE_MULTI;
				viewButtonExpandCam.width = SIZE_MULTI;
				viewButtonExpandCam.css = "cam-viewer-button";

				if (self.pan || self.tilt || self.zoom) {

					let viewButtonCamControls = getButtonCamControls();

					viewButtonCamControls.height = SIZE_MULTI;
					viewButtonCamControls.width = SIZE_MULTI;
					viewButtonCamControls.css = "cam-viewer-button";

					viewButtons.cols.push(viewButtonCamControls);
				}

				if (!self.fullScreen) {

					let viewButtonFullScreen = getButtonFullScreen();

					viewButtonFullScreen.height = SIZE_MULTI;
					viewButtonFullScreen.width = SIZE_MULTI;
					viewButtonFullScreen.css = "cam-viewer-button";
					viewButtons.cols.push(viewButtonFullScreen);
				}

				viewButtons.cols.push(viewButtonExpandCam);

				if (self.config.commands.lock && Array.isArray(self.element.commandElements) &&
					self.element.commandElements.find(e => e.id == self.config.commands.lock.commandElementTypeId)) {

					let viewButtonLock = getButtonLock();

					viewButtonLock.height = SIZE_MULTI;
					viewButtonLock.width = SIZE_MULTI;
					viewButtonLock.css = "cam-viewer-button";
					viewButtons.cols.push(viewButtonLock);
				}


			}

			function getViewButtonsMultiMode() {

				viewButtons.height = SIZE_SINGLE;

				let viewButtonExpandCam = getButtonExpandCam();

				viewButtonExpandCam.height = SIZE_SINGLE;
				viewButtonExpandCam.width = SIZE_SINGLE;
				viewButtonExpandCam.css = "cam-viewer-button-small";

				if (self.pan || self.tilt || self.zoom) {

					let viewButtonCamControls = getButtonCamControls();

					viewButtonCamControls.height = SIZE_SINGLE;
					viewButtonCamControls.width = SIZE_SINGLE;
					viewButtonCamControls.css = "cam-viewer-button-small";

					viewButtons.cols.push(viewButtonCamControls);
				}

				if (!self.fullScreen) {

					let viewButtonFullScreen = getButtonFullScreen();

					viewButtonFullScreen.height = SIZE_SINGLE;
					viewButtonFullScreen.width = SIZE_SINGLE;
					viewButtonFullScreen.css = "cam-viewer-button-small";

					viewButtons.cols.push(viewButtonFullScreen);
				}

				viewButtons.cols.push(viewButtonExpandCam);

				if (self.config.commands.lock && Array.isArray(self.element.commandElements) &&
					self.element.commandElements.find(e => e.id == self.config.commands.lock.commandElementTypeId)) {

					let viewButtonLock = getButtonLock();

					viewButtonLock.height = SIZE_SINGLE;
					viewButtonLock.width = SIZE_SINGLE;
					viewButtonLock.css = "cam-viewer-button-small";
					viewButtons.cols.push(viewButtonLock);
				}

			}

			function getButtonCamControls() {

				let viewButtonCamControls;

				viewButtonCamControls = {

					view: "button",
					type: "icon",
					icon: "mdi mdi-camera-control",
					tooltip: function() {

						return (self.controlsVisible ? i18n.t("LBL_HIDE_CAM_CONTROLS") : i18n.t("LBL_SHOW_CAM_CONTROLS"));
					},
					on: {
						onItemClick: function() {

							let camControlsView = $$("cam-controls-" + self.uid);

							if (camControlsView) {

								self.controlsVisible = !self.controlsVisible;

								self.controlsVisible ? camControlsView.show() : camControlsView.hide();

								let className = "webix_view webix_control webix_el_button webix_secondary ";

								if (self.controlsVisible) {

									className += (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button-pressed" : "cam-viewer-button-pressed-small";
									this._contentobj.className = className;
								}
								else {

									className += (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button" : "cam-viewer-button-small";
									this._contentobj.className = className;
								}

							}
						}
					}
				}

				return viewButtonCamControls;
			}

			function getButtonFullScreen() {

				let viewButtonFullScreen;

				viewButtonFullScreen = {

					view: "button",
					type: "icon",
					icon: "mdi mdi-fullscreen",
					tooltip: i18n.t("LBL_VIEW_FULL_SCREEN"),
					on: {
						onItemClick: function() {

							let camViewer = $$("camviewer-" + self.uid);
							let mainTab = $$(MAIN_TAB_ID);

							if (mainTab && camViewer) {

								let camViewerFullScreen = new CamViewer(self.config, self.element, mainTab.$width, mainTab.$height + mainTab.getTabbar().config.height, CamViewer.MODE_SINGLE, false, true);

								util.ui.loadTab(self.element.alias, camViewerFullScreen.getView());
							}
						}
					}
				}

				return viewButtonFullScreen;
			}

			function getButtonExpandCam() {

				let viewButtonExpandCam;

				viewButtonExpandCam = {
					id: "cam-expand-button-" + self.uid,
					view: "button",
					type: "icon",
					icon: "mdi mdi-arrow-expand",
					tooltip: i18n.t("LBL_EXPAND_CAM"),
					css: "buttonExpand",
					on: {
						onItemClick: function(obj) {

							let taller = undefined;
							let contId = $$("camviewer-" + self.uid);
							let camViewerContainer = $$("camviewer-" + self.uid).getNode().querySelector(".camviewer-div");
							let camViewerImg = $$("camviewer-" + self.uid).getNode().querySelector(".camviewer-img");
							let className = "webix_view webix_control webix_el_button webix_secondary ";

							taller = ($$(contId).$height > $$(contId).$width) ? true : false;

							if (camViewerContainer && camViewerImg) {

								if (taller) {

									if (self.expandCam) {

										camViewerContainer.style.height = "initial";
										self.expandCam = false;

									} else {

										camViewerContainer.style.height = "100%";
										camViewerContainer.style.width = "100%";
										camViewerImg.style.width = "100%";
										self.expandCam = true;
									}

								} else {

									if (self.expandCam) {

										camViewerContainer.style.width = "inherit";
										camViewerImg.style.width = "inherit";
										self.expandCam = false;

									} else {

										camViewerContainer.style.width = "100%";
										camViewerImg.style.width = "100%";
										self.expandCam = true;
									}

									self.fit = !self.fit;
								}

								self.drawButtons(contId);

								if (self.expandCam) {

									className += (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button-pressed" : "cam-viewer-button-pressed-small";
									$$(obj)._contentobj.className = className;

								} else {

									className += (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button" : "cam-viewer-button-small";
									$$(obj)._contentobj.className = className;
								}
							}
						}
					}
				}

				return viewButtonExpandCam;
			}

			function getButtonLock() {

				let viewButtonLock;

				viewButtonLock = {

					view: "button",
					type: "icon",
					icon: "mdi mdi-send",
					tooltip: i18n.t("LBL_SEND_TO_VIDEO_WALL"),
					
					on: {
						onItemClick: function() {

							webix.confirm({
								title: i18n.t("LBL_CONFIRM"),
								ok: i18n.t("LBL_YES"),
								cancel: i18n.t("LBL_NO"),
								text: i18n.t("LBL_SEND_TO_VIDEO_WALL_CONFIRM_DESCRIPTION") + ": " + self.element.alias,
								width: 600
							}).then(function() {
								
								if (self.config.commands.lock) {

									let body = {
										commands: []
									};

									let sendMessageCmd = webix.copy(self.config.commands.lock);

									sendMessageCmd.elementId = self.element.id;
									body.commands.push(webix.copy(sendMessageCmd));

									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body);
								}
							});
						}
					}
				}

				return viewButtonLock;
			}
			return viewButtons;
		}

		function getCamControls(marginRight, contId) {

			const CTRL_ROWS = 5;
			const CTRL_COLS = 3;

			let width = (self.w == 0) ? contId.$width : self.w;
			let height = (self.h == 0) ? contId.$height : self.h;
			//let sizeCorrection = (height > width) ? 0.6 : 1;
			let sizeCorrection = (height > 500 || width > 500) ? 1 : 0.6;
			let top = (height > width) ? 100 : 50;

			let presets = [];

			let elementValuePTZ = self.element.elementValues.find(e => e.elementTypeParam.id === self.config.camConf.featuresPTZ);

			if (elementValuePTZ) {

				let ptz = elementValuePTZ.value;

				if (ptz) {

					ptz = ptz.trim().toUpperCase();

					self.pan = (ptz.indexOf("P") !== -1);
					self.tilt = (ptz.indexOf("T") !== -1);
					self.zoom = (ptz.indexOf("Z") !== -1);
				}
			}

			let elementValuePresetConfigJson = self.element.elementValues.find(e => e.elementTypeParam.id === self.config.camConf.presetConfigJson);

			if (elementValuePresetConfigJson) {

				let presetConfigJson = elementValuePresetConfigJson.value;
				if (presetConfigJson) {

					try {

						presets = JSON.parse(presetConfigJson);

					} catch (e) {

						presets = undefined;
						//console.error(e);
					}
				}
			}

			let btnWidth = Math.floor((h / 2) / CTRL_ROWS) * sizeCorrection;
			let btnHeight = btnWidth;

			let viewCamControls = {
				id: "cam-controls-" + self.uid,
				top: top,
				right: marginRight,
				hidden: true,
				rows: [
					{},
					{
						height: Math.floor(h / 2),
						cols: [
							{ width: 1 },
							{
								width: Math.floor((CTRL_COLS * (h / 2)) / CTRL_ROWS) * sizeCorrection,
								css: { "background-color": "transparent" },
								rows: [
									{
										cols: [
											{ width: btnWidth, height: btnHeight },
											getBtnUp(),
											{ width: btnWidth, height: btnHeight }
										]
									},
									{
										cols: [
											getBtnLeft(),
											Array.isArray(presets) && presets.length > 0 && presets.find(e => e.default) ? getBtnDefaultPreset() : {},
											getBtnRight()
										]
									},
									{
										cols: [
											{ width: btnWidth, height: btnHeight },
											getBtnDown(),
											{ width: btnWidth, height: btnHeight }
										]
									},
									{
										cols: [
											getBtnZoomOut(),
											{ width: btnWidth, height: btnHeight },
											getBtnZoomIn()
										]
									},
									getBtnPresets()
								]
							},
							{ width: 1 }
						]
					},
					{}
				]
			}

			function getBtnUp() {

				let viewBtnUp;

				viewBtnUp = {

					view: "button",
					css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
					width: btnWidth,
					height: btnHeight,
					disabled: !self.tilt,
					type: "icon",
					icon: "mdi mdi-chevron-up",
					tooltip: i18n.t("LBL_CAM_MOVE_UP"),
					on: {
						onItemClick: function() {

							move(self.config.ptzValue.up);
						}
					}
				}

				return viewBtnUp;
			}

			function getBtnDown() {

				let viewBtnDown;

				viewBtnDown = {

					view: "button",
					css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
					width: btnWidth,
					height: btnHeight,
					disabled: !self.tilt,
					type: "icon",
					icon: "mdi mdi-chevron-down",
					tooltip: i18n.t("LBL_CAM_MOVE_DOWN"),
					on: {
						onItemClick: function() {

							move(self.config.ptzValue.down);
						}
					}
				}

				return viewBtnDown;
			}

			function getBtnLeft() {

				let viewBtnLeft;

				viewBtnLeft = {

					view: "button",
					css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
					width: btnWidth,
					height: btnHeight,
					disabled: !self.pan,
					type: "icon",
					icon: "mdi mdi-chevron-left",
					tooltip: i18n.t("LBL_CAM_MOVE_LEFT"),
					on: {
						onItemClick: function() {

							move(self.config.ptzValue.left);
						}
					}
				}

				return viewBtnLeft;
			}

			function getBtnRight() {

				let viewBtnRight;

				viewBtnRight = {

					view: "button",
					css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
					width: btnWidth,
					height: btnHeight,
					disabled: !self.pan,
					type: "icon",
					icon: "mdi mdi-chevron-right",
					tooltip: i18n.t("LBL_CAM_MOVE_RIGHT"),
					on: {
						onItemClick: function() {

							move(self.config.ptzValue.right);
						}
					}
				};

				return viewBtnRight;
			}

			function getBtnDefaultPreset() {

				let viewBtnStop;

				viewBtnStop = {

					view: "button",
					css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
					width: btnWidth,
					height: btnHeight,
					type: "icon",
					icon: "mdi mdi-circle-medium",
					tooltip: i18n.t("LBL_DEFAULT_PRESET"),
					on: {
						onItemClick: function() {
							preset(presets.find(e => e.default).id);
						}
					}
				};

				return viewBtnStop;
			}

			function getBtnZoomOut() {

				let viewBtnZoomOut;

				viewBtnZoomOut = {

					view: "button",
					css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
					width: btnWidth,
					height: btnHeight,
					disabled: !self.zoom,
					type: "icon",
					icon: "mdi mdi-magnify-minus-outline",
					tooltip: i18n.t("LBL_CAM_ZOOM_OUT"),
					on: {
						onItemClick: function() {

							move(self.config.ptzValue.zoomOut);
						}
					}
				};

				return viewBtnZoomOut;
			}

			function getBtnZoomIn() {

				let viewBtnZoomIn;

				viewBtnZoomIn = {

					view: "button",
					css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
					width: btnWidth,
					height: btnHeight,
					disabled: !self.zoom,
					type: "icon",
					icon: "mdi mdi-magnify-plus-outline",
					tooltip: i18n.t("LBL_CAM_ZOOM_IN"),
					on: {
						onItemClick: function() {

							move(self.config.ptzValue.zoomIn);
						}
					}
				};

				return viewBtnZoomIn;
			}

			function getBtnPresets() {

				let viewBtnPresets;

				viewBtnPresets = {

					view: "button",
					css: (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-controls-preset" : "cam-viewer-controls-preset-small",
					height: btnHeight,
					hidden: !(Array.isArray(presets) && presets.length > 0),
					value: i18n.t("LBL_CAM_PRESETS"),
					tooltip: i18n.t("LBL_CAM_PRESETS"),
					on: {
						onItemClick: function() {

							let dialogPresets = webix.copy(SearchDialogBox.CUSTOM);
							let label = i18n.t("LBL_PRESETS_FOR_CAM") + ": " + self.element.alias;

							dialogPresets.data = [];

							dialogPresets.pkField = "id"
							dialogPresets.fields = ["alias=LBL_PRESET_ALIAS", "description=LBL_PRESET_DESCRIPTION"];
							dialogPresets.height = 350;
							dialogPresets.width = 400;

							if (Array.isArray(presets) && presets.length > 0) {

								presets.forEach(p => {

									dialogPresets.data.push({
										id: p.id,
										alias: p.alias,
										description: p.description
									})
								});
							}

							let s = new SearchDialogBox(label, dialogPresets, function(items) {

								if (Array.isArray(items) && items.length === 1) {

									let item = items[0];

									if (item && item.id) {

										preset(item.id);
									}
								}
							});

							s.show();
						}
					}
				};

				return viewBtnPresets;
			}

			function move(id) {

				if (self.config.commands.ptz && id) {

					let body = {
						commands: []
					};

					let sendMessageCmd = webix.copy(self.config.commands.ptz);

					sendMessageCmd.elementId = self.element.id;
					sendMessageCmd.commandElementValues[0].value = id;

					body.commands.push(webix.copy(sendMessageCmd));

					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body);
				}
			}

			function preset(id) {

				if (self.config.commands.preset && id) {

					let body = {
						commands: []
					};

					let sendMessageCmd = webix.copy(self.config.commands.preset);

					sendMessageCmd.elementId = self.element.id;
					sendMessageCmd.commandElementValues[0].value = id;

					body.commands.push(webix.copy(sendMessageCmd));

					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body);
				}
			}

			return viewCamControls;
		}
	}


	getCamName(marginLeft, bottom, containerId) {

		let self = this;
		let viewCamName;
		let w = (self.w == 0) ? containerId.$width : self.w;

		viewCamName = {
			id: "camviewer-name-" + self.uid,
			left: marginLeft,
			bottom: bottom,
			width: w / 2,
			css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-name" : "cam-viewer-name-small",
			view: "label",
			label: self.element.alias,
			tooltip: i18n.t("LBL_SHOW_EQUIPMENT_INFO"),
			on: {
				onItemClick: function(id) {

					let module = util.module.getFromModuleAction(self.config.moduleAction.equipmentViewer);

					if (module) {

						let moduleAction = { id: self.config.moduleAction.equipmentViewer };
						let args = { selectedPks: [self.element.id] };

						util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
					}
				}
			}
		}

		return viewCamName;
	}

	resize(camViewerUid) {

		let containerId = $$("camviewer-" + camViewerUid);

		if (containerId) {

			let taller = undefined;
			let camViewerContainer = containerId.getNode().querySelector(".camviewer-div");
			let camViewerImg = containerId.getNode().querySelector(".camviewer-img");
			let expandButton = "cam-expand-button-" + camViewerUid;
			let className = "webix_view webix_control webix_el_button webix_secondary ";

			taller = ($$(containerId).$height > $$(containerId).$width) ? true : false;

			if (camViewerContainer && camViewerImg) {

				if (taller) {

					camViewerContainer.style.width = "100%";

				} else {

					camViewerContainer.style.height = "100%";
				}

				camViewerImg.style.width = "100%";
				this.drawButtons(containerId);

				if (this.expandCam) {

					className += (this.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button-pressed" : "cam-viewer-button-pressed-small";
					$$(expandButton)._contentobj.className = className;

				} else {

					className += (this.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button" : "cam-viewer-button-small";
					$$(expandButton)._contentobj.className = className;
				}
			}
		}
	}
}
