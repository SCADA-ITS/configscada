import { util } from "../../../lib/util.js";
import SearchDialogBox from "../../../component/searchDialogBox.js";
import MessageBox from "../../../component/messageBox.js";
import Decoder from "./decoder.js";
import DecoderMJPEG from "./decoderMJPEG.js";
import DecoderSNAP from "./decoderSNAP.js";
import CCTVModule from "../cctvModule.js";

const SIZE_SINGLE = 30;
const SIZE_MULTI = 50;

export default class CamViewer {

	static get MODE_SINGLE() { return 1 }
	static get MODE_MULTI() { return 2 }

	
	constructor(config, element, w, h, mode, fit, fullScreen, container) {

		this.uid = webix.uid();
		this.config = config;
		this.element = element;
		this.w = w;
		this.h = mode == CamViewer.MODE_SINGLE ? h - 60 : h;
		this.mode = mode;
		this.fit = fit ? fit : false;
		this.container = container;

		this.controlsVisible = false;
		this.expandCam = false;

		this.pan = false;
		this.tilt = false;
		this.zoom = false;

		this.fullScreen = fullScreen;
		this.decoder = undefined;
		this.decoderType = Decoder.MJPEG;
		
		this.commandEquipmentPermission = util.module.isAllowedPermission(this.config.requiredPermissions.commandEquipment);
		
		if (config && config.camConf && config.camConf.brand && config.camConf.model && config.camConf.devices) {
				
			let elementValueBrand = element.elementValues.find(e => e.elementTypeParam.id === config.camConf.brand);
			let elementValueModel = element.elementValues.find(e => e.elementTypeParam.id === config.camConf.model);
			
			let brand = elementValueBrand ? elementValueBrand.value : undefined;
			let model = elementValueModel ? elementValueModel.value : undefined;
			
			if (brand && model) {
				
				let device = config.camConf.devices.find(e => {
					
					return (e.brand.localeCompare(brand, undefined, { sensitivity: 'base' }) === 0 &&
							e.model.localeCompare(model, undefined, { sensitivity: 'base' }) === 0)
				});
				
				if (device) {
					
					this.decoderType = device.decoder;
					this.brand = device.brand;
					this.model = device.model;
				}	
			}
		}
	}
	
	stop() {
		
		let self = this;
		
		if (self.decoder) {
			
			self.decoder.stop();
		}
	}

	getView() {

		let self = this;
		let view;
		
		if (self.decoderType === Decoder.MJPEG) {
			
			self.decoder = new DecoderMJPEG(this);
		}
		else if (self.decoderType === Decoder.SNAP) {
			
			self.decoder = new DecoderSNAP(this);
		}
		
		if (self.decoder) {
			
			view = {
				view: "abslayout",
				id: "camviewer-" + self.element.id + "-" + self.uid,
				cells: [
					self.decoder.getViewNoImage(),
					self.getCamName(10, 10),
					{ id: "camviewer-buttons-" + self.uid },
					{ id: "cam-controls-" + self.uid },
					{
						view: "template",
						height: 1,
						on: {
							onAfterRender: function() {
								
								setTimeout(function() {
								
									self.start();
								}, self.fullScreen ? 2000 : 1000);
							}
						}
					}
				]
				, on: {
					
					onDestruct: function() {
						
						self.decoder.stop();
					} 
				}
			}
		}
		
		return view;
	}
	
	start() {
	
		let self = this;
		
		if (self.decoder) {
	
			self.decoder.start();		
		}	
	}

	drawButtons(containerId) {

		let self = this;
		let camContainer = document.getElementById(self.decoder.camContainerId);
		
		if (!camContainer) return

		let marginRight = 10
		let bottom = 10;

		let w = (self.w == 0) ? containerId.$width : self.w;
		let h = (self.h == 0) ? containerId.$height : self.h;

		if (!self.expandCam) {
			
			if (camContainer) {
				
				bottom += (h - camContainer.clientHeight) / 2;	
			}
		}

		if (camContainer) {
			
			marginRight += (w - camContainer.clientWidth) / 2;	
		}

		if(self.commandEquipmentPermission){
		
			webix.ui(getCamControls(), $$("cam-controls-" + self.uid));	
		}
		
		webix.ui(getButtons(), $$("camviewer-buttons-" + self.uid));
		webix.ui(self.getCamName(marginRight, bottom, containerId), $$("camviewer-name-" + self.uid));
		
		let camExpandButton = $$("cam-expand-button-" + self.uid);
		let className = "webix_view webix_control webix_el_button webix_secondary ";
		
		if (camExpandButton) {
		
			if (self.expandCam) {

				className += (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button-pressed" : "cam-viewer-button-pressed-small";
				camExpandButton._contentobj.className = className;
	
			} else {
	
				className += (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button" : "cam-viewer-button-small";
				camExpandButton._contentobj.className = className;
			}	
		}

		function getButtons() {

			let viewButtons = {
				id: "camviewer-buttons-" + self.uid,
				bottom: bottom,
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

				if ((self.pan || self.tilt || self.zoom) && self.commandEquipmentPermission) {

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
				
				let viewButtonExpandCam = getButtonExpandCam();

				viewButtonExpandCam.height = SIZE_MULTI;
				viewButtonExpandCam.width = SIZE_MULTI;
				viewButtonExpandCam.css = "cam-viewer-button";
				viewButtons.cols.push(viewButtonExpandCam);

				if (self.config.commands.lock && Array.isArray(self.element.commandElements) &&
					self.element.commandElements.find(e => e.id == self.config.commands.lock.commandElementTypeId) && self.commandEquipmentPermission) {

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

				if ((self.pan || self.tilt || self.zoom) && self.commandEquipmentPermission) {

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
					self.element.commandElements.find(e => e.id == self.config.commands.lock.commandElementTypeId) && self.commandEquipmentPermission) {

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

								let cn = "webix_view webix_control webix_el_button webix_secondary ";

								if (self.controlsVisible) {

									cn += (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button-pressed" : "cam-viewer-button-pressed-small";
									this._contentobj.className = cn;
								}
								else {

									cn += (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-button" : "cam-viewer-button-small";
									this._contentobj.className = cn;
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
								
								let taskManagerService = window.sm.getService(Services.TASK_MANAGER_SERVICE);
								
								if (taskManagerService) {
									
									let module = util.module.getFromModuleAction(self.config.moduleAction.singleCam);
									let moduleAction = { id: self.config.moduleAction.singleCam };
									let args = {
										selectedPks: [self.element.id],
										fullScreen: true
									}
									
									let cctvModule = new CCTVModule(module, moduleAction, args)
									taskManagerService.externalize(null, cctvModule);
								}
								// DEPRECATED
								else {
								
									let camViewerFullScreen = new CamViewer(self.config, self.element, mainTab.$width, mainTab.$height + mainTab.getTabbar().config.height, CamViewer.MODE_SINGLE, false, true);

									util.ui.loadTab(self.element.alias, camViewerFullScreen.getView());	
								}
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

							self.expandVideo();
							self.drawButtons();
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

							let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_SEND_TO_VIDEO_WALL_CONFIRM_DESCRIPTION") + ": " + self.element.alias, null, onAccept, null);
							ms.show();

							function onAccept() {

								if (self.config.commands.lock) {

									let sendMessageCmd = webix.copy(self.config.commands.lock);

									sendMessageCmd.elementId = self.element.id;

									let sendMessageCmdCopy = webix.copy(sendMessageCmd);

									util.element.sendCommand([sendMessageCmdCopy]);
								}

//								if (self.config.commands.lock) {
//
//									let body = {
//										commands: []
//									};
//
//									let sendMessageCmd = webix.copy(self.config.commands.lock);
//
//									sendMessageCmd.elementId = self.element.id;
//									body.commands.push(webix.copy(sendMessageCmd));
//
//									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body);
//								}
							}
						}
					}
				}

				return viewButtonLock;
			}
			return viewButtons;
		}

		function getCamControls() {

			const CTRL_ROWS = 5;
			const CTRL_COLS = 3;
			const SPEED_CONFIGS = "ElementTypeParam:2:1:12";
			const SPEED_TYPE_PARAM = "CommandElementTypeParam:3:2:2";
			const SIZE_CORRECTOR_FACTOR = 0.5;
			const MAX_SPEED_CONFIGS = 2;

			let sizeCorrection = (camContainer.clientWidth / h) * 0.6;

			let top = bottom;

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
					}
				}
			}

			let btnWidth = Math.floor((h / 2) / CTRL_ROWS) * sizeCorrection;
			let btnHeight = btnWidth;
			let cssStyle;
			let cssStyleExtraZoomButton;
			
			let speedConfigsValues = undefined;
			let showExtendsControls = false;
			let showCounterControl = false;
			let speedConfigs = self.element.elementValues.find(e => e.elementTypeParam.id === SPEED_CONFIGS);						

			try {

				speedConfigsValues = JSON.parse(speedConfigs.value);

			} catch {

			}
			
			if (Array.isArray(speedConfigsValues) && speedConfigsValues.length > 0) {

				if (speedConfigsValues.length === MAX_SPEED_CONFIGS) {

					showExtendsControls = true;
					
				} else if (speedConfigsValues.length > MAX_SPEED_CONFIGS) {

					showExtendsControls = true;
					showCounterControl = true;
				}
				
				if (speedConfigsValues[0].position) {

					speedConfigsValues.sort((a, b) => (a.position > b.position) ? 1 : (b.position > a.position) ? -1 : 0);
				}
			}			

			if (self.mode === CamViewer.MODE_SINGLE && !showExtendsControls) {

				cssStyle = "cam-viewer-controls";

			} else if (self.mode === CamViewer.MODE_SINGLE && showExtendsControls) {

				cssStyle = "cam-viewer-controls cam-viewer-extends-controls";
				cssStyleExtraZoomButton = "cam-viewer-controls extra-zoom-button";

			} else if (self.mode === CamViewer.MODE_MULTI && showExtendsControls) {

				cssStyle = "cam-viewer-controls-small cam-viewer-extends-controls-small";
				cssStyleExtraZoomButton = "cam-viewer-controls-small extra-zoom-button-small";

			} else {

				cssStyle = "cam-viewer-controls-small";
			}
			
			let viewCamControls = {
				id: "cam-controls-" + self.uid,
				top: top,
				right: marginRight,
				hidden: true,
				rows: [
				//	{ height : top },
					{
						height: camContainer.clientHeight * 0.8,
						cols: [
							{ width: 1 },
							{
								width: Math.floor((CTRL_COLS * (h / 2)) / CTRL_ROWS) * sizeCorrection,
								css: { "background-color": "transparent" },
								rows: [
									{
										cols: [
											(showExtendsControls) ? {} : { width: btnWidth, height: btnHeight },
											getBtnUp(),
											(showExtendsControls) ? {} : { width: btnWidth, height: btnHeight }
										]
									},
									{
										cols: [
											{},
											getBtnLeft(),
											getBtnDefaultPreset(),
											getBtnRight(),
											{}
										]
									},
									{
										cols: [
											(showExtendsControls) ? {} : { width: btnWidth, height: btnHeight },
											getBtnDown(),
											(showExtendsControls) ? {} : { width: btnWidth, height: btnHeight }
										]
									},
									{
										cols: [
											getBtnZoomOut(),
											(showExtendsControls) ? {} : { width: btnWidth, height: btnHeight },
											getBtnZoomIn()
										]
									},
									getBtnPresets(),
									getSpeedCounter()									
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
					rows: [
						{
							view: "button",
							css: cssStyle,
							width: (showExtendsControls) ? btnWidth * SIZE_CORRECTOR_FACTOR : btnWidth,
							height:(showExtendsControls) ? btnHeight * SIZE_CORRECTOR_FACTOR : btnHeight,
							disabled: !self.tilt,
							type: "icon",
							icon: "mdi mdi-chevron-double-up",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_MOVE_UP");

								if (showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue].label);
									}

								} else {

									tooltip += " " + i18n.t(speedConfigsValues[1].label);
								}

								return tooltip;
							},
							hidden: !showExtendsControls,
							on: {
								onItemClick: function() {
							
									let command;

									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.up,
												speedId: speedConfigsValues[counterValue].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.up,
											speedId: speedConfigsValues[1].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.up);
									}	
								}
							}
						},
						{
							view: "button",
							css: cssStyle,
							width: (showExtendsControls) ? btnWidth * SIZE_CORRECTOR_FACTOR : btnWidth,
							height: (showExtendsControls) ? btnHeight * SIZE_CORRECTOR_FACTOR : btnHeight,
							disabled: !self.tilt,
							type: "icon",
							icon: "mdi mdi-chevron-up",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_MOVE_UP");

								if (showExtendsControls && showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue - 1].label);
									}

								} else if (showExtendsControls && !showCounterControl) {

									tooltip += " " + i18n.t(speedConfigsValues[0].label);
								}

								return tooltip;
							},
							on: {
								onItemClick: function() {

									let command;

									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.up,
												speedId: speedConfigsValues[counterValue - 1].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.up,
											speedId: speedConfigsValues[0].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.up);
									}
								}
							}
						}
					]
				};

				return viewBtnUp;
			}

			function getBtnDown() {

				let viewBtnDown;

				viewBtnDown = {
					rows: [
						{
							view: "button",
							css: cssStyle,
							width: (showExtendsControls) ? btnWidth * SIZE_CORRECTOR_FACTOR : btnWidth,
							height: (showExtendsControls) ? btnHeight * SIZE_CORRECTOR_FACTOR : btnHeight,
							disabled: !self.tilt,
							type: "icon",
							icon: "mdi mdi-chevron-down",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_MOVE_DOWN");

								if (showExtendsControls && showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue - 1].label);
									}

								} else if (showExtendsControls && !showCounterControl) {

									tooltip += " " + i18n.t(speedConfigsValues[0].label);
								}

								return tooltip;
							},							
							on: {
								onItemClick: function() {
									
									let command;

									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.down,
												speedId: speedConfigsValues[counterValue - 1].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.down,
											speedId: speedConfigsValues[0].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.down);
									}
								}
							}
						},
						{
							view: "button",
							css: cssStyle,
							width: (showExtendsControls) ? btnWidth * SIZE_CORRECTOR_FACTOR : btnWidth,
							height: (showExtendsControls) ? btnHeight * SIZE_CORRECTOR_FACTOR : btnHeight,
							disabled: !self.tilt,
							type: "icon",
							icon: "mdi mdi-chevron-double-down",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_MOVE_DOWN");

								if (showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue].label);
									}

								} else {

									tooltip += " " + i18n.t(speedConfigsValues[1].label);
								}

								return tooltip;
							},
							hidden: !showExtendsControls,
							on: {
								onItemClick: function() {
									
									let command;

									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.down,
												speedId: speedConfigsValues[counterValue].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.down,
											speedId: speedConfigsValues[1].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.down);
									}
								}
							}
						}
					]
				};

				return viewBtnDown;
			}

			function getBtnLeft() {

				let viewBtnLeft;

				viewBtnLeft = {
					cols: [
						{
							view: "button",
							css: cssStyle,
							width: (showExtendsControls) ? btnWidth * SIZE_CORRECTOR_FACTOR : btnWidth,
							height: (showExtendsControls) ? btnHeight * SIZE_CORRECTOR_FACTOR : btnHeight,
							disabled: !self.pan,
							type: "icon",
							icon: "mdi mdi-chevron-double-left",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_MOVE_LEFT");

								if (showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue].label);
									}

								} else {

									tooltip += " " + i18n.t(speedConfigsValues[1].label);
								}

								return tooltip;
							},
							hidden: !showExtendsControls,
							on: {
								onItemClick: function() {

									let command;

									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.left,
												speedId: speedConfigsValues[counterValue].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.left,
											speedId: speedConfigsValues[1].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.left);
									}
								}
							}
						},
						{
							view: "button",
							css: cssStyle,
							width: (showExtendsControls) ? btnWidth * SIZE_CORRECTOR_FACTOR : btnWidth,
							height: (showExtendsControls) ? btnHeight * SIZE_CORRECTOR_FACTOR : btnHeight,
							disabled: !self.pan,
							type: "icon",
							icon: "mdi mdi-chevron-left",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_MOVE_LEFT");

								if (showExtendsControls && showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue - 1].label);
									}

								} else if (showExtendsControls && !showCounterControl) {

									tooltip += " " + i18n.t(speedConfigsValues[0].label);
								}

								return tooltip;
							},
							on: {
								onItemClick: function() {
									
									let command;

									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.left,
												speedId: speedConfigsValues[counterValue - 1].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.left,
											speedId: speedConfigsValues[0].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.left);
									}
								}
							}
						}
					]
				};

				return viewBtnLeft;
			}

			function getBtnRight() {

				let viewBtnRight;

				viewBtnRight = {
					cols: [
						{
							view: "button",
							css: cssStyle,
							width: (showExtendsControls) ? btnWidth * SIZE_CORRECTOR_FACTOR : btnWidth,
							height: (showExtendsControls) ? btnHeight * SIZE_CORRECTOR_FACTOR : btnHeight,
							disabled: !self.pan,
							type: "icon",
							icon: "mdi mdi-chevron-right",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_MOVE_RIGHT");

								if (showExtendsControls && showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue - 1].label);
									}

								} else if (showExtendsControls && !showCounterControl) {

									tooltip += " " + i18n.t(speedConfigsValues[0].label);
								}

								return tooltip;
							},
							on: {
								onItemClick: function() {

									let command;
									
									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.right,
												speedId: speedConfigsValues[counterValue - 1].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.right,
											speedId: speedConfigsValues[0].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.right);
									}
								}
							}
						},
						{
							view: "button",
							css: cssStyle,
							width: (showExtendsControls) ? btnWidth * SIZE_CORRECTOR_FACTOR : btnWidth,
							height: (showExtendsControls) ? btnHeight * SIZE_CORRECTOR_FACTOR : btnHeight,
							disabled: !self.pan,
							type: "icon",
							icon: "mdi mdi-chevron-double-right",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_MOVE_RIGHT");

								if (showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue].label);
									}

								} else {

									tooltip += " " + i18n.t(speedConfigsValues[1].label);
								}

								return tooltip;
							},
							hidden: !showExtendsControls,
							on: {
								onItemClick: function() {
									
									let command;

									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.right,
												speedId: speedConfigsValues[counterValue].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.right,
											speedId: speedConfigsValues[1].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.right);
									}
								}
							}
						}
					]
				};

				return viewBtnRight;
			}

			function getBtnDefaultPreset() {

				let viewBtnStop;

				if (Array.isArray(presets) && presets.length > 0 && presets.find(e => e.default)) {

					viewBtnStop = {
						view: "button",
						css: cssStyle,
						width: (showExtendsControls) ? btnWidth * SIZE_CORRECTOR_FACTOR : btnWidth,
						height: (showExtendsControls) ? btnHeight * SIZE_CORRECTOR_FACTOR : btnHeight,
						type: "icon",
						icon: "mdi mdi-circle-medium",
						tooltip: i18n.t("LBL_DEFAULT_PRESET"),
						on: {
							onItemClick: function() {
								preset(presets.find(e => e.default).id);
							}
						}
					};
					
				} else {

					viewBtnStop = (showExtendsControls) ? { width: btnWidth * SIZE_CORRECTOR_FACTOR, height: btnHeight * SIZE_CORRECTOR_FACTOR } : { width: btnWidth, height: btnHeight };
				}

				return viewBtnStop;
			}

			function getBtnZoomOut() {

				let viewBtnZoomOut;

				viewBtnZoomOut = {
					cols: [
						{
							view: "button",
							css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
							width: btnWidth,
							height: btnHeight,
							disabled: !self.zoom,
							type: "icon",
							icon: "mdi mdi-magnify-minus-outline",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_ZOOM_OUT");

								if (showExtendsControls && showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue - 1].label);
									}

								} else if (showExtendsControls && !showCounterControl) {

									tooltip += " " + i18n.t(speedConfigsValues[0].label);

								}

								return tooltip;
							},
							on: {
								onItemClick: function() {

									let command;

									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.zoomOut,
												speedId: speedConfigsValues[counterValue - 1].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.zoomOut,
											speedId: speedConfigsValues[0].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.zoomOut);
									}
								}
							}
						},
						{
							hidden: !showExtendsControls,
							rows: [
								{ width: btnWidth * 0.25, height: btnHeight * 0.25 },
								{
									view: "button",
									css: cssStyleExtraZoomButton,
									width: btnWidth / 2,
									height: btnHeight / 2,
									disabled: !self.zoom,
									type: "icon",
									icon: "mdi mdi-magnify-minus",
									tooltip: function() {

										let tooltip = i18n.t("LBL_CAM_ZOOM_OUT");

										if (showCounterControl) {

											if ($$("speed-counter-" + self.uid)) {

												let counterValue = $$("speed-counter-" + self.uid).getValue();
												tooltip += " " + i18n.t(speedConfigsValues[counterValue].label);
											}

										} else {

											tooltip += " " + i18n.t(speedConfigsValues[1].label);
										}

										return tooltip;
									},
									on: {
										onItemClick: function() {

											let command;

											if (showExtendsControls && showCounterControl) {

												if ($$("speed-counter-" + self.uid)) {

													let counterValue = $$("speed-counter-" + self.uid).getValue();
													command = {
														id: self.config.ptzValue.zoomOut,
														speedId: speedConfigsValues[counterValue].id.toString()
													};

													move(command);
												}

											} else if (showExtendsControls && !showCounterControl) {

												command = {
													id: self.config.ptzValue.zoomOut,
													speedId: speedConfigsValues[1].id.toString()
												};

												move(command);

											} else {

												move(self.config.ptzValue.zoomOut);
											}
										}
									}
								}
							]
						}
					]
				};

				return viewBtnZoomOut;
			}

			function getBtnZoomIn() {

				let viewBtnZoomIn;

				viewBtnZoomIn = {
					cols: [
												{
							hidden: !showExtendsControls,
							rows: [
								{ width: btnWidth * 0.25, height: btnHeight * 0.25 },
								{
									view: "button",
									css: cssStyleExtraZoomButton,
									width: btnWidth / 2,
									height: btnHeight / 2,
									disabled: !self.zoom,
									type: "icon",
									icon: "mdi mdi-magnify-plus mdi-rotate-90",
									tooltip: function() {

										let tooltip = i18n.t("LBL_CAM_ZOOM_IN");

										if (showCounterControl) {

											if ($$("speed-counter-" + self.uid)) {

												let counterValue = $$("speed-counter-" + self.uid).getValue();
												tooltip += " " + i18n.t(speedConfigsValues[counterValue].label);
											}

										} else {

											tooltip += " " + i18n.t(speedConfigsValues[1].label);
										}

										return tooltip;
									},
									on: {
										onItemClick: function() {

											let command;

											if (showExtendsControls && showCounterControl) {

												if ($$("speed-counter-" + self.uid)) {

													let counterValue = $$("speed-counter-" + self.uid).getValue();
													command = {
														id: self.config.ptzValue.zoomIn,
														speedId: speedConfigsValues[counterValue].id.toString()
													};

													move(command);
												}

											} else if (showExtendsControls && !showCounterControl) {

												command = {
													id: self.config.ptzValue.zoomIn,
													speedId: speedConfigsValues[1].id.toString()
												};

												move(command);

											} else {

												move(self.config.ptzValue.zoomIn);
											}
										}
									}
								}
							]
						},
						{
							view: "button",
							css: self.mode === CamViewer.MODE_SINGLE ? "cam-viewer-controls" : "cam-viewer-controls-small",
							width: btnWidth,
							height: btnHeight,
							disabled: !self.zoom,
							type: "icon",
							icon: "mdi mdi-magnify-plus-outline mdi-rotate-90",
							tooltip: function() {

								let tooltip = i18n.t("LBL_CAM_ZOOM_IN");

								if (showExtendsControls && showCounterControl) {

									if ($$("speed-counter-" + self.uid)) {

										let counterValue = $$("speed-counter-" + self.uid).getValue();
										tooltip += " " + i18n.t(speedConfigsValues[counterValue - 1].label);
									}

								} else if(showExtendsControls && !showCounterControl) {

									tooltip += " " + i18n.t(speedConfigsValues[0].label);
								}

								return tooltip;
							},	
							on: {
								onItemClick: function() {

									let command;

									if (showExtendsControls && showCounterControl) {

										if ($$("speed-counter-" + self.uid)) {

											let counterValue = $$("speed-counter-" + self.uid).getValue();
											command = {
												id: self.config.ptzValue.zoomIn,
												speedId: speedConfigsValues[counterValue - 1].id.toString()
											};

											move(command);
										}

									} else if (showExtendsControls && !showCounterControl) {

										command = {
											id: self.config.ptzValue.zoomIn,
											speedId: speedConfigsValues[0].id.toString()
										};

										move(command);

									} else {

										move(self.config.ptzValue.zoomIn);
									}
								}
							}
						}
					]
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
			
			function getSpeedCounter() {

				let speedCounter = {};

				if (showCounterControl) {

					speedCounter = {
						id: "speed-counter-" + self.uid,
						view: "counter",
						height: btnHeight,
						step: 1,
						css: (self.mode === CamViewer.MODE_SINGLE) ? "cam-viewer-counter" : "cam-viewer-counter-small",
						min: 1,
						max: speedConfigsValues.length - 1,
						tooltip: function() {

							let tooltip;
							let counterValue = $$("speed-counter-" + self.uid).getValue();
							let doubleIconsLabel = i18n.t(speedConfigsValues[counterValue].label);
							let simpleIconsLabel = i18n.t(speedConfigsValues[counterValue-1].label);
														 
							tooltip = `<div>
										<span>${i18n.t("LBL_SENSIBILITY")}</span><br>
										<span class='mdi mdi-chevron-double-up'>${i18n.t("LBL_CAM_MOVE_UP") + " " + doubleIconsLabel}</span><br>
										<span class='mdi mdi-chevron-up'>${i18n.t("LBL_CAM_MOVE_UP") + " " + simpleIconsLabel}</span><br>
										<span class='mdi mdi-chevron-double-right'>${i18n.t("LBL_CAM_MOVE_RIGHT") + " " + doubleIconsLabel}</span><br>
										<span class='mdi mdi-chevron-right'>${i18n.t("LBL_CAM_MOVE_RIGHT") + " " + simpleIconsLabel}</span><br>
										<span class='mdi mdi-chevron-double-down'>${i18n.t("LBL_CAM_MOVE_DOWN") + " " + doubleIconsLabel}</span><br>
										<span class='mdi mdi-chevron-down'>${i18n.t("LBL_CAM_MOVE_DOWN") + " " + simpleIconsLabel}</span><br>
										<span class='mdi mdi-chevron-double-left'>${i18n.t("LBL_CAM_MOVE_LEFT") + " " + doubleIconsLabel}</span><br>
										<span class='mdi mdi-chevron-left'>${i18n.t("LBL_CAM_MOVE_LEFT") + " " + simpleIconsLabel}</span><br>
										<span class='mdi mdi-magnify-minus'>${i18n.t("LBL_CAM_ZOOM_OUT") + " " + doubleIconsLabel}</span><br>
										<span class='mdi mdi-magnify-minus-outline'>${i18n.t("LBL_CAM_ZOOM_OUT") + " " + simpleIconsLabel}</span><br>
										<span class='mdi mdi-magnify-plus'>${i18n.t("LBL_CAM_ZOOM_IN") + " " + doubleIconsLabel}</span><br>
										<span class='mdi mdi-magnify-plus-outline'>${i18n.t("LBL_CAM_ZOOM_IN") + " " + simpleIconsLabel}</span>
										</div>`; 

							return tooltip;
						}
					};
				}

				return speedCounter;
			}

			function move(id) {
				
				if (self.config.commands.ptz && id) {

					let sendMessageCmd = webix.copy(self.config.commands.ptz);
					sendMessageCmd.elementId = self.element.id;

					if (typeof (id) === "object") {

						sendMessageCmd.commandElementValues[0].value = id.id;
						sendMessageCmd.commandElementValues.push({
							commandElementTypeParamId: SPEED_TYPE_PARAM,
							value: id.speedId
						});

					} else {

						sendMessageCmd.commandElementValues[0].value = id;
					}

					let sendMessageCmdCopy = webix.copy(sendMessageCmd);
console.log(sendMessageCmdCopy)
					util.element.sendCommand([sendMessageCmdCopy]);
				}

//				if (self.config.commands.ptz && id) {
//
//					let body = {
//						commands: []
//					};
//
//					let sendMessageCmd = webix.copy(self.config.commands.ptz);
//
//					sendMessageCmd.elementId = self.element.id;
//					sendMessageCmd.commandElementValues[0].value = id;
//
//					body.commands.push(webix.copy(sendMessageCmd));
//
//					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body);
//				}
			}

			function preset(id) {

				if (self.config.commands.preset && id) {

					let sendMessageCmd = webix.copy(self.config.commands.preset);

					sendMessageCmd.elementId = self.element.id;
					sendMessageCmd.commandElementValues[0].value = id;

					let sendMessageCmdCopy = webix.copy(sendMessageCmd);

					util.element.sendCommand([sendMessageCmdCopy]);
				}
				
//				if (self.config.commands.preset && id) {
//
//					let body = {
//						commands: []
//					};
//
//					let sendMessageCmd = webix.copy(self.config.commands.preset);
//
//					sendMessageCmd.elementId = self.element.id;
//					sendMessageCmd.commandElementValues[0].value = id;
//
//					body.commands.push(webix.copy(sendMessageCmd));
//
//					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body);
//				}
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

	resize() {

		let self = this;
                let contId = $$("camviewer-" + self.uid);
                let camViewerContainer = $$("camviewer-" + self.uid).getNode().querySelector(".camviewer-div");
                let camViewerImg = $$("camviewer-" + self.uid).getNode().querySelector(".camviewer-img");

		if (camViewerContainer && camViewerImg) {

			self.adjust();
			self.drawButtons(contId);
		}
	}

	expandVideo() {

		let self = this;
        let camViewerContainer = $$("camviewer-" + self.uid).getNode().querySelector(".camviewer-div");
        let camViewerImg = $$("camviewer-" + self.uid).getNode().querySelector(".camviewer-img");
                

		if (camViewerContainer && camViewerImg) {
			
			self.expandCam = !self.expandCam;
	                self.fit = !self.fit;
			self.adjust();
		}
	}

	adjust() {
	
		let self = this;
		let contId = $$("camviewer-" + self.uid);
		let camViewerContainer = $$("camviewer-" + self.uid).getNode().querySelector(".camviewer-div");
		let camViewerImg = $$("camviewer-" + self.uid).getNode().querySelector(".camviewer-img");
		let contHeight = $$(contId).$height;
		let contWidth = $$(contId).$width;

		if (camViewerContainer && camViewerImg) {

			camViewerContainer.style.height = (self.expandCam ? contHeight : self.decoder.imgH) + "px";
			camViewerImg.style.height = camViewerContainer.style.height
			
			camViewerContainer.style.width = (self.expandCam ? contWidth : self.decoder.imgW) + "px";
			camViewerImg.style.width = camViewerContainer.style.width;
		}
	}
}

