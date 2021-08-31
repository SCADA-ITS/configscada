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

	getView() {

		let self = this;

		let view = {
			view: "abslayout",
			cells: [
//				{
//					id: "camviewer-" + self.uid,
//					relative: true
//				},
				getViewNoImage(),
				{ id: "camviewer-name-" + self.uid },
				{ id: "camviewer-buttons-" + self.uid },
				{ id: "cam-controls-" + self.uid }
			]
			, on: {

				onDestruct: function() {
					
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

					return `<div style="background-color: #9e9e9e; border: 2px solid; text-align: center;"><img align='center' style="${imgStyle}" src="${NO_VIDEO_IMAGE}"></div>`;
				}
				, on: {

					onDestruct: onDestruct
				}
			};
			
			return viewNoImage;
		}
		
		function onDestruct() {
			
			if (!self.isReconnecting) {
				
				if (self.img.src != NO_VIDEO_IMAGE) {
				
					self.img.src = '';	
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

					if (!webix.env.isWebKit) {
						
						//self.img = new Image();

						self.img.onload = startStreaming;
						
						function startStreaming() {
							
							self.isOnline = true;
							console.log("[" + (new Date()).toISOString() + "] " + "[CamViewer] Connection success for cam: " + url);
														
							let viewStreamMjpeg = {
								id: "camviewer-" + self.uid,
								relative: true,
								template: function() {

									let w = self.fit ? "width: 100%" : "";
									let imgStyle = `max-width:100%;height:${self.h}px;${w}`;

									return `<div style="background-color: #9e9e9e; border: 2px solid; text-align: center;"><img align='center' style="${imgStyle}" src="${self.img.src}"></div>`;
								}
								, on: {

									onDestruct: onDestruct
								}
							};
							
							webix.ui(viewStreamMjpeg, $$("camviewer-" + self.uid));
							viewButtons();
							self.img.onload = undefined;
							self.isReconnecting = false;
							
						}
						
						function startKeepAliveStream() {
							
							self.timeCheckStream = setInterval(function() {
								
								//console.log("[" + (new Date()).toISOString() + "] " + "[CamViewer] Check connection for cam: " + url);
								
								self.timeoutCheckStream = setInterval(function() {
								
									if (!self.isOnline) {
										
										console.warn("[" + (new Date()).toISOString() + "] " + "[CamViewer] No Connection for cam: " + url);
										
										self.isReconnecting = true;
										
										if ($$("camviewer-" + self.uid)) {
											
											webix.ui(getViewNoImage(), $$("camviewer-" + self.uid));	
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
							
									//console.log("[" + (new Date()).toISOString() + "] " + "[CamViewer] Connection response for cam: " + url);
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
					else {

						let viewStreamMjpeg = {
							id: "camviewer-" + self.uid,
							view: "iframe",
							relative: true,
							src: getUrlWithoutAuth(url)
						};

						viewStreamMjpeg.height = self.h;

						webix.delay(function() {

							let camViewer = $$("camviewer-" + self.uid);

							if (camViewer) {

								webix.ui(viewStreamMjpeg, $$("camviewer-" + self.uid));
							}

						}, null, null, 1000);
					}
				}
			}

			function viewButtons() {

				let imgWidth = self.img.width;
				let imgHeight = self.img.height;
				
				let margin = 10

				if (!self.fit) {

					let ratio = self.h / imgHeight;
					let w = Math.floor(imgWidth * ratio);

					margin = Math.floor((self.w - w) / 2);
				}

				webix.ui(getCamName(margin), $$("camviewer-name-" + self.uid));
				webix.ui(getCamControls(margin), $$("cam-controls-" + self.uid));
				webix.ui(getButtons(margin), $$("camviewer-buttons-" + self.uid));
			}

		}

		function getCamName(marginLeft) {

			let viewCamName;
			
			viewCamName = {
				id: "camviewer-name-" + self.uid,
				left: marginLeft,
				bottom: 10,
				width: self.w / 2,
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

		function getButtons(marginRight) {

			let viewButtons = {
				id: "camviewer-buttons-" + self.uid,
				bottom: 10,
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

				let viewButtonCamControls = getButtonCamControls();
				let viewButtonFullScreen = getButtonFullScreen();

				viewButtonCamControls.height = SIZE_MULTI;
				viewButtonCamControls.width = SIZE_MULTI;
				viewButtonCamControls.css = "cam-viewer-button";

				viewButtonFullScreen.height = SIZE_MULTI;
				viewButtonFullScreen.width = SIZE_MULTI;
				viewButtonFullScreen.css = "cam-viewer-button";

				if (self.pan || self.tilt || self.zoom)
					viewButtons.cols.push(viewButtonCamControls);
					
				if(!self.fullScreen)
					viewButtons.cols.push(viewButtonFullScreen);
			}

			function getViewButtonsMultiMode() {

				viewButtons.height = SIZE_SINGLE;

				let viewButtonCamControls = getButtonCamControls();
				let viewButtonFullScreen = getButtonFullScreen();

				viewButtonCamControls.height = SIZE_SINGLE;
				viewButtonCamControls.width = SIZE_SINGLE;
				viewButtonCamControls.css = "cam-viewer-button-small";

				viewButtonFullScreen.height = SIZE_SINGLE;
				viewButtonFullScreen.width = SIZE_SINGLE;
				viewButtonFullScreen.css = "cam-viewer-button-small";

				if (self.pan || self.tilt || self.zoom)
					viewButtons.cols.push(viewButtonCamControls);
					
				if(!self.fullScreen)
					viewButtons.cols.push(viewButtonFullScreen);
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

			return viewButtons;
		}

		function getCamControls(marginRight) {

			const CTRL_ROWS = 5;
			const CTRL_COLS = 3;

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

					presets = JSON.parse(presetConfigJson);
				}
			}

			let btnWidth = Math.floor((self.h / 2) / CTRL_ROWS);
			let btnHeight = btnWidth;

			let viewCamControls = {
				id: "cam-controls-" + self.uid,
				top: 50,
				right: marginRight,
				hidden: true,
				rows: [
					{},
					{
						height: Math.floor(self.h / 2),
						cols: [
							{ width: 1 },
							{
								width: Math.floor((CTRL_COLS * (self.h / 2)) / CTRL_ROWS),
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
											getBtnStop(),
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

			function getBtnStop() {

				let viewBtnStop;
				
				viewBtnStop = {

					view: "button",
					css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
					width: btnWidth,
					height: btnHeight,
					type: "icon",
					icon: "mdi mdi-circle-medium",
					tooltip: i18n.t("LBL_CAM_STOP"),
					on: {
						onItemClick: function() {

							move(self.config.ptzValue.stop);
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
							dialogPresets.height = 600;
							dialogPresets.width = 800;

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

		function getUrlWithoutAuth(url) {

			let offset = url.indexOf('@');
			let urlWithoutAuth = url;

			if (offset !== -1) {

				let protocol = url.substring(0, url.indexOf('//') + 2);

				urlWithoutAuth = protocol + url.substring(offset + 1);
			}

			return urlWithoutAuth;
		}

		return view;
	}
}
