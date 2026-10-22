import { util } from "../lib/util.js";

const DEFAULT_MIN_INT_VALUE = -65535;
const DEFAULT_MAX_INT_VALUE = 65535;
const HEIGHT = {
	ALPHANUMERIC: 250,
	TIMESTAMP: 425,
	OBJECT: 525
};

const ALERT_HEIGHT = {
	ALPHA_OR_TIMESTAMP: 23,
	NO_ALPHA_OR_TIMESTAMP: 57
};

const TEXTAREA_HEIGHT = 70;
const TEXTAREA_MAX_HEIGHT = 400;

const WINDOW_HEIGHT={
	TIMESTAMP: 400,
	NO_TIMESTAMP: 258
};


export default class InputDialogBox {

	constructor(title, text, onAccept, requiredValue, currentValue, dataType) {

		this.title = title;
		this.text = text;
		this.onAccept = onAccept;
		this.requiredValue = requiredValue;
		this.currentValue = currentValue;
		this.valid = true;
		this.dataType = dataType && dataType.id ? dataType : { id: DataType.ALPHANUMERIC };
		
		if (this.dataType.id === DataType.ALPHANUMERIC && currentValue) {
			
			try {
				
				this.currentValue = JSON.parse(currentValue);
				
				this.isObject = true;	
			}
			catch {
				
			}
		}

		this.uid = webix.uid();
	}

	show() {

		let self = this;
		let height;			
		let unit = self.dataType.labelUnit ? i18n.t(self.dataType.labelUnit) : "";
		let alertHeight = (self.dataType.id === DataType.ALPHANUMERIC || self.dataType.id === DataType.TIMESTAMP) ? ALERT_HEIGHT.ALPHA_OR_TIMESTAMP : ALERT_HEIGHT.NO_ALPHA_OR_TIMESTAMP;
		let textareaHeight = TEXTAREA_HEIGHT;

		if (self.dataType.id === DataType.TIMESTAMP) {

			height = HEIGHT.TIMESTAMP;
		
		} else {

			height = self.isObject ? HEIGHT.OBJECT : HEIGHT.ALPHANUMERIC;
		}

		let body = {
			width: 600,
			height: height,
			maxHeight: 600,
			css: "dialog-input-custom",
			rows: [
				{
					template: (self.dataType.id === DataType.ALPHANUMERIC || self.dataType.id === DataType.TIMESTAMP) ? self.text : "",
					height: 40,
					borderless: true,
					css: "dialog-input-text"
				},
				getBody(),
				{
					id: "alertText-" + self.uid,
					height: alertHeight
				},
				{
					height: 41,
					cols: [
						{ width: 20 },
						{
							view: "button",
							css: "dialog-cancel",
							width: 130,
							height: 35,
							label: i18n.t("LBL_CANCEL"),
							value: self.requiredValue ? i18n.t("LBL_CANCEL") : i18n.t("LBL_NO"),
							click: function() {

								const window = this.getTopParentView();
								window.close();
							}
						},
						{},
						{
							view: "button",
							id: "button-accept-" + self.uid,
							css: "dialog-accept",
							width: 130,
							height: 35,
							label: i18n.t("LBL_ACCEPT"),
							value: self.requiredValue ? i18n.t("LBL_ACCEPT") : i18n.t("LBL_YES"),
							click: function() {

								function getAlert() {

									let alert = {};
									let label = undefined;

									if (self.dataType.id === DataType.DECIMAL) {

										label = i18n.t("LBL_VALUE_OUTSIDE_THRESHOLS") +
											" [" + (self.dataType.threshold1 ? self.dataType.threshold1 : "-") +
											(self.dataType.threshold2 ? ", " + self.dataType.threshold2 : "-") + "]";

									} else if (self.dataType.id === DataType.ALPHANUMERIC) {

										label = i18n.t("LBL_INVALID_VALUE");
									}

									if (label) {
										alert = {
											id: "alertText-" + self.uid,
											view: "label",
											label: `<div"><i style="color:#e8596e; font-size: 16px">` + label + `</i></div>`
										}
									}
									return alert;
								}

								if (validate()) {

									const window = this.getTopParentView();
									const txtDialogReconize = $$("input-dialog-" + self.uid);

									let valueToSend = txtDialogReconize.getValue();

									if (valueToSend !== undefined) {

										if (self.dataType.id === DataType.DECIMAL) {

											valueToSend = parseFloat(valueToSend);

										} else if (self.dataType.id === DataType.BOOLEAN) {

											valueToSend = (valueToSend == 1);

										} else if (self.dataType.id === DataType.TIMESTAMP) {

											valueToSend = valueToSend.getTime();
										}
									}
									
									// Prevent z-index disorder. Bug preventing inputboxes contained in tabs from being selected
									setTimeout(function() {
								
										self.onAccept(valueToSend);						
		
									}, 1);
									
									window.close();

								} else {

									webix.ui(getAlert(), $$("alertText-" + self.uid));
								}
							}
						},
						{ width: 20 }
					]
				},
				{ height: 15 }
			]
		};


		function getBody() {

			let bodyContent = {};

			if (self.dataType.id === DataType.INTEGER) {

				bodyContent = getIntegerBody();

			} else if (self.dataType.id === DataType.BOOLEAN) {

				bodyContent = getBooleanBody();

			} else if (self.dataType.id === DataType.DECIMAL) {

				bodyContent = getDecimalBody();

			} else if (self.dataType.id === DataType.TIMESTAMP) {

				bodyContent = getDateBody();

			} else {

				bodyContent = getStringBody();
			}


			function getIntegerBody() {

				let integerBody;
				
				const r = /^-?[0-9]([0-9])*$/;

				integerBody = {
					view: "form",
					borderless: true,
					cols:
						[
							{ width: 20 },
							{
								view: "label",
								label: self.text,
								width: 260,
								tooltip: true,
								css: "dialog-input-labeltext"
							},
							{
								view: "counter",
								id: "input-dialog-" + self.uid,
								css: "inputdialogbox-counter",
								borderless: true,
								width: 140,
								height: 40,
								step: 1,
								align: "center",
								value: self.currentValue ? self.currentValue : 0,
								min: self.dataType.threshold1 ? self.dataType.threshold1 : DEFAULT_MIN_INT_VALUE,
								max: self.dataType.threshold2 ? self.dataType.threshold2 : DEFAULT_MAX_INT_VALUE,
								on: {
									onKeyPress: function(code, e) {

										if (e.key === "Dead") {

											this.disable();
											this.enable();
											this.focus();
										}

										this.config.previousValue = this.getValue();
										return code == 46 || code == 8 || (code >= 35 && code <= 40) || (code >= 48 && code <= 57) || code == 173;
									},
									onTimedKeyPress: function() {

										if (!r.test(this.getValue())) {

											this.setValue(this.config.previousValue);
											this.focus();
											this.getInputNode().setSelectionRange(this.getValue().length, this.getValue().length);
										}
									},
									onAfterRender: function() {

										this.focus();

										if (this.getValue() !== undefined) {

											this.getInputNode().setSelectionRange(0, String(this.getValue()).length);
										}
									}
								}
							},
							{
								width: 50,
								view: "label",
								label: "<span class='grid-title' style='padding-left:0px'>" + unit + "</span>",
							},
							{}
						]
				};
				return integerBody;
			}

			function getStringBody() {

				let stringBody;
				let currentValue;
				
				const CORRECTION_FACTORS = {
					WINDOW_HEIGHT: 110,
					BODY_HEIGHT: 40,
					TEXTAREA_HEIGHT_ISOBJECT:70,
					TEXTAREA_HEIGHT: 10,
					LINE_HEIGHT: 18,					 					
				};

				if (self.isObject) {

					currentValue = JSON.stringify(self.currentValue, null, 3);
					let numOfLines = currentValue.split(/\n+/).length;

					textareaHeight = TEXTAREA_MAX_HEIGHT;
					//textareaHeight = numOfLines * CORRECTION_FACTORS.LINE_HEIGHT;					
					//textareaHeight = (textareaHeight > HEIGHT.OBJECT - CORRECTION_FACTORS.WINDOW_HEIGHT) ? HEIGHT.OBJECT - CORRECTION_FACTORS.WINDOW_HEIGHT :textareaHeight;
														
				} else {

					currentValue = self.currentValue;
				}

				stringBody = {
					view: "form",
					borderless: true,
					height: self.isObject ? textareaHeight : textareaHeight + CORRECTION_FACTORS.BODY_HEIGHT,
					rows: [
						{ height: 1 },
						{
							cols: [
								{ width: 1 },
								{
									view: "textarea",
									id: "input-dialog-" + self.uid,
									name: "input-dialog-" + self.uid,
									height: self.isObject ? textareaHeight - 20 : textareaHeight + CORRECTION_FACTORS.TEXTAREA_HEIGHT,
									//placeholder: self.text,
									maxHeight: TEXTAREA_MAX_HEIGHT,
									scroll: "auto",
									value: currentValue ? currentValue : undefined,
			
									validate: function(value) {
			
										let regEx = self.dataType.threshold1 ? new RegExp(self.dataType.threshold1) : undefined;
			
										return (!regEx || regEx.test(value));
									},
									on: {
										onAfterRender: function() {
			
											this.focus();
			
											if (this.getValue()) {
			
												this.getInputNode().setSelectionRange(0, this.getValue().length);
											}
										}
									}
								},	
								{ width: 1 }
							]
						},
						{ height: 1 }
					]
				};

				return stringBody;
			}

			function getDecimalBody() {

				const r = /^-?(?:(?:0|[1-9][0-9]*)(?:\.[0-9]*)?|\.[0-9]+)$/;

				let decimalBody;

				decimalBody = {
					view: "form",
					borderless: true,
					cols: [
						{ width: 20 },
						{
							view: "label",
							label: self.text,
							width: 260,
							tooltip: true,
							css: "dialog-input-labeltext"
						},
						{
							view: "text",
							id: "input-dialog-" + self.uid,
							name: "input-dialog-" + self.uid,
							width: 140,
							height: 40,
							css: "inputdialogbox-counter",
							align: "center",
							previousValue: "",
							value: self.currentValue && self.currentValue != "0" ? self.currentValue : "0.0",
							keyPressTimeout: 10,
							validate: function(value) {

								let floatValue = parseFloat(value);
								let floatThreshold1 = self.dataType.threshold1 ? parseFloat(self.dataType.threshold1) : undefined;
								let floatThreshold2 = self.dataType.threshold2 ? parseFloat(self.dataType.threshold2) : undefined;

								return (!floatThreshold1 || floatThreshold1 <= floatValue) && (!floatThreshold2 || floatThreshold2 >= floatValue);
							},
							on: {
								onKeyPress: function(code, e) {

									if (e.key === "Dead") {

										this.disable();
										this.enable();
										this.focus();
									}

									this.config.previousValue = this.getValue();
									console.log(e.key);
									console.log(code);
									return code == 46 || code == 8 || (code >= 35 && code <= 40) || (code >= 48 && code <= 57) || code == 190 || code == 173;
								},
								onTimedKeyPress: function() {

									if (this.getValue() != "" && this.getValue() != "-" && !r.test(this.getValue())) {

										this.setValue(this.config.previousValue);
										this.focus();
										this.getInputNode().setSelectionRange(this.getValue().length, this.getValue().length);
									}
								},
								onAfterRender: function() {

									this.focus();

									if (this.getValue()) {

										this.getInputNode().setSelectionRange(0, this.getValue().length);
									}
								}
							}
						},
						{
							width: 50,
							view: "label",
							label: "<span class='grid-title' style='padding-left:0px'>" + unit + "</span>",
						},
						{}
					]
				};

				return decimalBody;
			}

			function getDateBody() {

				let dateBody;
				let date;
				
				if (self.currentValue instanceof Date) {

					date = self.currentValue;
					 
				} else {

					let timeService = window.sm.getService(Services.TIME_SERVICE);
					date = new Date(timeService.getEpoch());
				}

				dateBody = {
					id: "input-dialog-" + self.uid,
					view: "calendar",
					borderless: true,
					date: date,
					timepicker: true,
					width: 300,
					height: 250,
					weekHeader: true
				};

				return dateBody;
			}

			function getBooleanBody() {

				let booleanBody;

				booleanBody = {
					view: "form",
					borderless: true,
					cols: [
						{
							view: "label",
							label: self.text,
							tooltip: true,
							css: "dialog-input-labeltext"
						},
						{
							id: "input-dialog-" + self.uid,
							name: "input-dialog-" + self.uid,
							view: "radio",
							value: self.currentValue === true ? 1 : 2,
							options: [
								{ id: 1, value: i18n.t("LBL_TRUE") },
								{ id: 2, value: i18n.t("LBL_FALSE") }
							]
						}
					]
				};

				return booleanBody;
			}

			return bodyContent;
		}

		function validate() {

			let valid = true;

			if (self.dataType.id === DataType.DECIMAL || self.dataType.id === DataType.ALPHANUMERIC) {

				let inputDialog = $$("input-dialog-" + self.uid)

				if (inputDialog) {

					valid &= inputDialog.validate();
				}
			}
			return valid;
		}

		let windowHeight = (self.dataType.id === DataType.TIMESTAMP) ? WINDOW_HEIGHT.TIMESTAMP : WINDOW_HEIGHT.NO_TIMESTAMP;

		if (self.isObject) {

			windowHeight = windowHeight + textareaHeight;
		}

		util.ui.loadWindow(self.title, body, windowHeight, 600, true);

		if (self.dataType.id != DataType.TIMESTAMP) {

			$$("input-dialog-" + self.uid).focus();
		}
		else {

			let date;
			
			if (self.currentValue instanceof Date) {

				date = self.currentValue;

			} else {

				let timeService = window.sm.getService(Services.TIME_SERVICE);
				date = new Date(timeService.getEpoch());
			}
			
			$$("input-dialog-" + self.uid).setValue(date);
		}
	}
}
