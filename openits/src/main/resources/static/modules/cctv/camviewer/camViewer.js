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

			let viewCamControls = {
				id: "cam-controls-" + self.uid,
				top: top,
				right: marginRight,
				hidden: true,
				rows: [
				//	{ height : top },
					{
						height: camContainer.clientHeight * 0.7,
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

					let sendMessageCmd = webix.copy(self.config.commands.ptz);

					sendMessageCmd.elementId = self.element.id;
					sendMessageCmd.commandElementValues[0].value = id;

					let sendMessageCmdCopy = webix.copy(sendMessageCmd);

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

