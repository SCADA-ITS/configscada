import { util } from "../../../lib/util.js";
import VmsSignalingZoneEditor from "./vmsSignalingZoneEditor.js";
import MessageBox from "../../../component/messageBox.js";

const DEFAULT_INPUT_FONT_SIZE = "14";
const TEXT_ALIGN_CENTER = "center";
const TEXT_ALIGN_LEFT = "left";
const TEXT_ALIGN_RIGHT = "right";


export default class VmsSignalingTextEditor extends VmsSignalingZoneEditor {

	constructor(width, height, config, data, onZoneDataChange) {

		super(width, height, config, data, onZoneDataChange);
		
		this.uid = webix.uid();
		this.containerId = undefined;
	}

	show() {

		let self = this;	
		let view = {};
		
		view = {
			
			rows: [
				drawHeader(),
				{
					cols: [				
						{ width: 20 },
						{
							rows: [
								
								{ height: 20 },
								drawOptionModes(),
								{ height: 10 },
								drawOptionAligns(),
								{ height: 30 },
								drawTabs(),
								{ height: 10 },
								//{},
								drawActionButtons(),
								{ height: 10 },
							]
						},
						{ width: 20 }
					]		
				}
			]
		};
		
		function drawHeader() {

			let header = {};
			let label = i18n.t("LBL_TEXT_EDITOR");
			let description = i18n.t("LBL_TEXT_EDITOR_DESCRIPTION");

			header = {

				id: "viewHeader-" + self.uid,
				css: "global-header",
				cols: [
					{
						view: "label",
						label: "<img src='img/grid/default.png' class='grid-icon'/>",
						height: 75,
						css: "icon"
					},
					{
						view: "label",
						height: 75,
						id: "label-bottom-" + self.uid,
						template: "<span>" + label + "</span> <br> <span style='font-size: 13px'>" + description + "</span>",
						css: "label"
					}
				]
			};

			return header;
		};
		
		function drawOptionModes() {
			
			let viewOptionModes;
			
			viewOptionModes = {
				
				cols: [
					{
						rows: [
							{
								cols: [
									{
										view: "label",
										label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-text-box-multiple-outline"></i></div>`,
										align: "center",
										height: 50,
										width: 56
									},
									{
										view: "label",
										css: "alarm-view-header-details-text",
										label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_SIGNALING_MODE")}</span>`,
										align: "left",
										height: 50
									},
									{}
								]
							},
							{
								cols: [
									{ width: 55 },
									{
										id: "view-signalling-text-editor-option-modes-" + self.uid,
										view: "radio",
										vertical: false,
										options: function() {
											
											let options = [
												{ "id": VmsSignalingZoneEditor.SINGLE_MODE, "value": i18n.t("LBL_VMS_SINGLE_MODE") }
											];
											
											if (self.config.properties.blinking) {
												
												options.push({ "id": VmsSignalingZoneEditor.FLASHING_MODE, "value": i18n.t("LBL_VMS_FLASHING_MODE") });
											}
											
											if (self.config.properties.alternance) {
												
												options.push({ "id": VmsSignalingZoneEditor.ALTERNANCE_MODE, "value": i18n.t("LBL_VMS_ALTERNANCE_MODE") });
											}
											
											return options;
										}(),
										on: {
											onChange: function(newv) {
												
												let tabInstance = $$("view-signalling-text-editor-tabs-" + self.uid);
												
												if (tabInstance) {
												
													if (newv == VmsSignalingZoneEditor.ALTERNANCE_MODE) {
													
														tabInstance.getTabbar().showOption("view-signalling-text-editor-alt-" + self.uid);
													}
													else {
														
														tabInstance.getTabbar().hideOption("view-signalling-text-editor-alt-" + self.uid);								
													}	
												}
											}
										}
									}
								]
							}
						]
					}
				]
			};
			
			return viewOptionModes;
		}
		
		function drawOptionAligns() {
			
			let viewOptionAligns = { height: 1 };
				
			if (self.config.properties.textAlign) {
				
				viewOptionAligns = {
					cols: [
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-format-align-left"></i></div>`,
											align: "center",
											height: 50,
											width: 56
										},
										{
											view: "label",
											css: "alarm-view-header-details-text",
											label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_SIGNALING_TEXT_ALIGN")}</span>`,
											align: "left",
											height: 50
										},
										{}
									]
								},
								{
									cols: [
										{ width: 55 },
										{
											id: "view-signalling-text-editor-option-aligns-" + self.uid,
											view: "radio",
											vertical: false,
											options: [
												{ "id": VmsSignalingZoneEditor.ALIGN_LEFT, "value": i18n.t("LBL_ALIGN_LEFT") },
												{ "id": VmsSignalingZoneEditor.ALIGN_CENTER, "value": i18n.t("LBL_ALIGN_CENTER") },
												{ "id": VmsSignalingZoneEditor.ALIGN_RIGHT, "value": i18n.t("LBL_ALIGN_RIGHT") }
											]
										}
									]
								}
								
							]
						}	
					]
				};	
			}
				
			return viewOptionAligns;
		}
		
		function drawTabs() {
			
			let viewTabs = {
				id: "view-signalling-text-editor-tabs-" + self.uid,
				view: "tabview",
				css: "secondary-tab",
				animate: false,
				borderless: true,
				cells: [drawTextEditor(false), drawTextEditor(true)],
				multiview: {
					keepViews:true
		        },
				tabbar: {
					tabMargin: 6
				}
			};
			
			function drawTextEditor(alternance) {
				
				let viewTextEditor = {
					
					header: !alternance ? i18n.t("LBL_VMS_MAIN") : i18n.t("LBL_VMS_ALTERNANCE"),
					body: {
						id: (!alternance ? "view-signalling-text-editor-main-" : "view-signalling-text-editor-alt-") + self.uid,
						rows: [
							{
								cols: [
									{ width: 1, css: { "border-left": "solid 1px #475466 !important" } },
									{ width: 10 },
									{
										rows: [
											{},
											drawTextEditorLines(),
											{}
										]
									},
									{ width: 6 },
									{ width: 1, css: { "border-left": "solid 1px #475466 !important" } },
									{ width: 3 }
									
								]		
							},
							{ height: 1, css: { "border-top": "solid 1px #475466 !important" } },
							{ height: 1 }
						]
					}
				}
				
				function drawTextEditorLines() {
					
					let lineNum = 1;
					let lineData = undefined;
					let viewLines = {
						cols: [
							{
								view: "form",
								id: (!alternance ? "view-signalling-text-editor-main-form-" : "view-signalling-text-editor-alt-form-") + self.uid,
								borderless: true,
								elements: []
								//rows: []
							}
						]
					};
					
					let inputWidth = (DEFAULT_INPUT_FONT_SIZE * (self.config.w - self.config.w * 0.1)) / parseFloat(self.config.fontInfo["font-size"]);
					
					if (Array.isArray(self.data.texts)) {
						
						while (lineData = self.data.texts.find(e => e.id === lineNum)) {
						
							viewLines.cols[0].elements.push(drawTextEditorLine());
	
							lineNum++;
						}	
					} 
					
					
					function drawTextEditorLine() {
					
						let viewTextEditorLine;
						
						viewTextEditorLine = {
							
							cols: [
								{},
								{
									id: (!alternance ? "view-signalling-text-editor-main-input-" : "view-signalling-text-editor-alt-input-") + lineData.id + "-" + self.uid,
									view: "text",
									//width: 400, 
									width: (DEFAULT_INPUT_FONT_SIZE * self.config.w) / parseFloat(self.config.fontInfo["font-size"]) + 150 + 12,
									name: (alternance ? "alt-" : "main-") + lineData.id,
									validate: function(value) {
										
										let ratioLetter = parseFloat(DEFAULT_INPUT_FONT_SIZE) / parseFloat(self.config.fontInfo["font-size"]);
										
										console.log("Input value is " + value);
										let textWidth = util.ui.calculateTextWidth(value, self.config.fontInfo["font-family"], DEFAULT_INPUT_FONT_SIZE + "px",  (parseFloat(self.config.fontInfo["letter-spacing"]) * ratioLetter) + "px");
										
										console.log("Input width is " + inputWidth + "px.")
										console.log("Text width is " + textWidth + "px.")
											
										return inputWidth > textWidth;
									},
									label: i18n.t("LBL_LINE") + lineData.id,
									labelWidth: 150,
									value: alternance ? lineData.alternance : lineData.value,
									on: {
										onAfterRender: function() {
											
											this.getNode().getElementsByTagName("input")[0].style["font-family"] = self.config.fontInfo["font-family"];
											this.getNode().getElementsByTagName("input")[0].style["font-size"] = DEFAULT_INPUT_FONT_SIZE;
										},
										onChange: function(newValue, oldValue, config) {
											
											$$((!alternance ? "view-signalling-text-editor-main-form-" : "view-signalling-text-editor-alt-form-") + self.uid).validate();
										}
									}
	
								},
								{
									id: (!alternance ? "keyboard-button-main-" : "keyboard-button-alt-") + lineData.id + "-" + self.uid,
									view: "button",
									type: "icon",
									icon: "mdi mdi-keyboard",
									css: "keyboard-text-editor",
									width: 40,
									height: 35,
									hidden: (!self.config.properties.specialCharacters || self.config.properties.specialCharacters.length == 0) ? true : false,
									on: {
										onItemClick(obj) {

											let numberOfButtons = self.data.texts.length;
											let buttonId = !alternance ? "keyboard-button-main-" : "keyboard-button-alt-";

											if ($$("popup-id-" + obj)) {

												$$("popup-id-" + obj).destructor();

												for (let i = 1; i <= numberOfButtons; i++) {

													$$(buttonId + i + "-" + self.uid).enable();
												}

											} else {

												for (let i = 1; i <= numberOfButtons; i++) {

													$$(buttonId + i + "-" + self.uid).disable();
												}

												$$(obj).enable();
												createPopup(obj, buttonId, numberOfButtons);

											}
										}
									}
								},
								{
									id: (!alternance ? "view-signalling-text-editor-main-color-" : "view-signalling-text-editor-alt-color-") + lineData.id + "-" + self.uid,
									view: "colorpicker",
									hidden: !self.config.properties.textColor,
									value: lineData.color ? lineData.color : self.config.properties.defaultTextColor,
									on:{
										onChange: function(newValue, oldValue, config){
												
											let colorInstance;
											
											if (this.config.id.indexOf("alt-") !== -1) {
												
												colorInstance = $$(this.config.id.replace("alt-", "main-"));	
											}
											else {
												
												colorInstance = $$(this.config.id.replace("main-", "alt-"));
											}
											
											if (colorInstance) {
												
												colorInstance.setValue(newValue);
											}
									    }
									}
								},
								{}
							]
						};
						
						return viewTextEditorLine;
					}

					function createPopup(obj, buttonId, numberOfButtons) {

						let sizes = calculateSizes();

						drawPopup();
						drawCharacters();

						function calculateSizes() {

							let windowPosition = $$(obj).getTopParentView().getNode().getBoundingClientRect();
							let buttonPosition = $$(obj).getNode().getBoundingClientRect();
							let bottonWindow = windowPosition.top + windowPosition.height;
							let bottonPopup = buttonPosition.top + 225;

							let top = ((bottonPopup - bottonWindow) < 0) ? buttonPosition.top : ((bottonPopup - bottonWindow) < 50) ? buttonPosition.top - 95 : buttonPosition.top - 190;
							let left = buttonPosition.left + 50;

							return { top, left };
						}

						function drawPopup() {

							let top = sizes.top;
							let left = sizes.left;

							webix.ui({
								view: "popup",
								id: "popup-id-" + obj,
								width: 300,
								height: 225,
								top: top,
								left: left,
								body: {
									template: `<div style="height: 225px; display:flex; justify-content:center; flex-wrap: wrap; background-color:#eef2f8;">
															<div style="margin: 12px 0px 0px"><span class="special-character-title">${i18n.t("LBL_SPECIAL_CHARACTERS")}</span></div>
															<div id="characters-listId" class="special-characters-list"></div>
														</div>`
								},
								on: {
									onHide: function() {

										$$("popup-id-" + obj).destructor();

										for (let i = 1; i <= numberOfButtons; i++) {

											$$(buttonId + i + "-" + self.uid).enable();
										}
									}
								}
							}).show();
						}

						function drawCharacters() {

							let popUp = $$("popup-id-" + obj).getNode().querySelector('#characters-listId');

							if (Array.isArray(self.config.properties.specialCharacters) && self.config.properties.specialCharacters.length > 0) {

								let asciiCodes = self.config.properties.specialCharacters;
								let fontFamily = self.config.fontInfo["font-family"];

								for (let asciiCode of asciiCodes) {

									let newCharacter = document.createElement("div");
									let chart = '&#x' + asciiCode;

									newCharacter.innerHTML = `<span class="special-character" style="font-family:${fontFamily}">${chart}</span>`;
									newCharacter.setAttribute("class", "special-character");
									newCharacter.setAttribute("style", "font-family:" + fontFamily);

									popUp.appendChild(newCharacter);

									newCharacter.addEventListener("click", function() { characterSelected(newCharacter.textContent, obj) });

								}
							}

							function characterSelected(character, obj) {

								let inputContainer = $$(obj.replace(buttonId, (!alternance) ? "view-signalling-text-editor-main-input-" : "view-signalling-text-editor-alt-input-"));
								let cursorPosition = inputContainer.getNode().getElementsByTagName("input")[0].selectionStart;
								let firstPart =inputContainer.getValue().substring(0, cursorPosition);
								let lastPart = inputContainer.getValue().substring(cursorPosition, inputContainer.getValue().length);

								inputContainer.setValue(firstPart + character + lastPart);

							}
						}
					}



					return viewLines;
				}

				return viewTextEditor;
			}
			
			return viewTabs;
		}
		
		function drawActionButtons() {
			
			let viewToolbarActions = {};
			
			viewToolbarActions = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: [
					{ 
						view: "button",
						css: "dialog-cancel", 
						width: 130,
						height: 35, 
						label: i18n.t("LBL_CANCEL"),
						on: {
							onItemClick() {
							
								$$(self.containerId).close();
							}
						}
					},
					{},
					{ 
						view: "button", 
						css: "dialog-accept",
						width: 130,
						height: 35,
						label: i18n.t("LBL_ACCEPT"),
						on: {
							onItemClick() {
		
								if (validate()) {
									
									self.data = getData();
								
									self.onZoneDataChange(self.data);
									
									$$(self.containerId).close();	
								}
								else {
									
									let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_VMS_MAX_CHARS_EXCEEDED_DESCRIPTION"), null, null);
									ms.show();
								}
							}
						}
					}
				]
			};
			
			return viewToolbarActions;
		}
		
		function validate() {
			
			let textEditorMainForm;
			let valid = true;
			
			if (textEditorMainForm = $$("view-signalling-text-editor-main-form-" + self.uid)) {
				
				valid &= textEditorMainForm.validate();
			}
			
			let textEditorAltForm;
			
			if (valid && (textEditorAltForm = $$("view-signalling-text-editor-alt-form-" + self.uid))) {
				
				valid &= textEditorAltForm.validate();
			}
			
			return valid;
		}
		
		function getData() {
			
			let data = {};
			let optionModesInstance = $$("view-signalling-text-editor-option-modes-" + self.uid);
				
			data.zone = self.data.zone;
			
			if (optionModesInstance) {
				
				if (optionModesInstance.getValue() == VmsSignalingZoneEditor.ALTERNANCE_MODE) {
				
					data["alternance_ms"] = self.config.properties.defaultAlternanceMs;
				}
				else if (optionModesInstance.getValue() == VmsSignalingZoneEditor.FLASHING_MODE) {
					
					data["flashing_on_ms"] = self.config.properties.defaultFlashingOnMs;
					data["flashing_off_ms"] = self.config.properties.defaultFlashingOffMs;
				}
				
				let inputDataMainInstance = undefined;
				let lineNum = 1;
				
				data.align = function() {
					
					let optionAligns = $$("view-signalling-text-editor-option-aligns-" + self.uid);
					
					let align = TEXT_ALIGN_CENTER;
					
					if (optionAligns && optionAligns.getValue() == VmsSignalingZoneEditor.ALIGN_LEFT) {
						
						align = TEXT_ALIGN_LEFT;
					}
					else if (optionAligns && optionAligns.getValue() == VmsSignalingZoneEditor.ALIGN_RIGHT) {
						
						align = TEXT_ALIGN_RIGHT;
					}
					
					return align;
				}();
				
				data.texts = [];
				
				while (inputDataMainInstance = $$("view-signalling-text-editor-main-input-" + lineNum + "-" + self.uid)) {
					
					let lineData = {};
					
					lineData.id = lineNum;
					lineData.value = inputDataMainInstance.getValue() ? inputDataMainInstance.getValue() : "";
					
					if (optionModesInstance.getValue() == VmsSignalingZoneEditor.ALTERNANCE_MODE) {
						
						let inputDataAltInstance = $$("view-signalling-text-editor-alt-input-" + lineNum + "-" + self.uid);
						
						if (inputDataAltInstance) {
							
							lineData.alternance = inputDataAltInstance.getValue() ? inputDataAltInstance.getValue() : ""; 		
						}		
					}
					
					let colorDataInstance = $$("view-signalling-text-editor-main-color-" + lineNum + "-" + self.uid);
					
					if (colorDataInstance) {
						
						lineData.color = colorDataInstance.getValue();
					}
					
					data.texts.push(lineData);
					
					lineNum++;
				}
			}
			
			return data;
		}
		
		function isAlternance() {
			
			let isAlt = false;
			let lineNum = 1;
			let lineData = undefined;
			
			if (Array.isArray(self.data.texts)) {
				
				while ((lineData = self.data.texts.find(e => e.id === lineNum)) && !isAlt) {
							
					isAlt |= lineData.alternance ? true : false; 
					lineNum++; 
				}	
			}
			
			return isAlt;
		}
		
		function getAlign() {
			
			return self.data.align;
		}
		
		function isFlashing() {
			
			return self.data["flashing_on_ms"] ? true : false;
		}
		
		function initialize() {
			
			let optionModes = $$("view-signalling-text-editor-option-modes-" + self.uid);
			let optionAligns = $$("view-signalling-text-editor-option-aligns-" + self.uid);
			
			if (optionModes) {
				
				if (isAlternance()) {
				
					optionModes.setValue(VmsSignalingZoneEditor.ALTERNANCE_MODE);
				}
				else if(isFlashing()) {
					
					optionModes.setValue(VmsSignalingZoneEditor.FLASHING_MODE);
				}
				else {
					
					optionModes.setValue(VmsSignalingZoneEditor.SINGLE_MODE);
				}	
			}
			
			if (optionAligns) {
				
				let align;
				
				if (align = getAlign()) {
				
					if (align === TEXT_ALIGN_LEFT) {
				
						optionAligns.setValue(VmsSignalingZoneEditor.ALIGN_LEFT);
					}
					else if (align === TEXT_ALIGN_CENTER) {
						
						optionAligns.setValue(VmsSignalingZoneEditor.ALIGN_CENTER);
					}
					else if (align === TEXT_ALIGN_RIGHT) {
						
						optionAligns.setValue(VmsSignalingZoneEditor.ALIGN_RIGHT);
					}	
				}
				else {
					
					optionAligns.setValue(VmsSignalingZoneEditor.ALIGN_CENTER);
					optionAligns.disable();
				}
			}
		}
		
		function getWindowHeight() {
			
			let lineNum = 1;
			let h = self.height;
			
			if (Array.isArray(self.data.texts)) {
				
				while (self.data.texts.find(e => e.id === lineNum)) {
						
					lineNum++; 
				}	
			}
			
			h += lineNum * 30;
			
			return h;
		}
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_EDIT_TEXT") + " - " + i18n.t("LBL_PANEL_ZONE") + " " + self.config.zone, view, getWindowHeight(), self.width, true);
		
		initialize();
	}
	
//	delete() {
//		
//		let self = this;
//	
//		let data = {
//			zone: this.data.zone,
//			graphics:  this.data.graphics,
//			texts: function() {
//				
//				let textArray = [];
//				
//				if (Array.isArray(self.data.texts) && self.data.texts.length > 0) {
//					
//					let id = 0;
//					
//					while (id++ < self.data.texts.length) {
//					
//						textArray.push({id: id});	
//					} 
//				}
//				
//				return textArray;
//			}()
//		};
//		
//		this.onZoneDataChange(data);
//	}
}	
