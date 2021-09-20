export default class InputDialogBox {

	constructor(title, text, onAccept, requiredValue, currentValue, dataType) {

		this.title = title;
		this.text = text;
		this.onAccept = onAccept;
		this.requiredValue = requiredValue;
		this.currentValue = currentValue;
		this.dataType = dataType ? dataType : DataType.ALPHANUMERIC;
		this.uid = webix.uid();
	}

	show() {

		let self = this;

		let height = (self.dataType === DataType.TIMESTAMP) ? 425 : 250;

		webix.ui({
			view: "window",
			modal: true,
			height: height,
			css: "dialog-input-custom",
			head: self.title,
			position: "center",
			body: {
				width: 600,
				rows: [
					{ template: self.text, borderless: true },
					getBody(),
					{},
					{
						cols: [
							{},
							{
								view: "button",
								value: self.requiredValue ? i18n.t("LBL_CANCEL") : i18n.t("LBL_NO"),
								width: 100,
								click: function() {

									const window = this.getTopParentView();
									window.close();
								}
							},
							{ width: 5 },
							{
								view: "button",
								id: "button-accept-" + self.uid,
								value: self.requiredValue ? i18n.t("LBL_ACCEPT") : i18n.t("LBL_YES"),
								width: 100,
								click: function() {

									const window = this.getTopParentView();
									const txtDialogReconize = $$("input-dialog-" + self.uid);

									let valueToSend = txtDialogReconize.getValue();
									
									if (valueToSend) {
										
										if (self.dataType === DataType.DECIMAL) {
											
											valueToSend = parseFloat(valueToSend);
										}
										else if (self.dataType === DataType.BOOLEAN) {
											
											valueToSend = (valueToSend == 1);
										}
									}
									

									self.onAccept(valueToSend);
									window.close();
								}
							},
							{}
						]
					}
				],
				type: "form"
			}
		}).show();

		if (self.dataType != DataType.TIMESTAMP) {

			$$("input-dialog-" + self.uid).focus();
		}
		else {
			
			$$("input-dialog-" + self.uid).setValue(self.currentValue instanceof Date ? self.currentValue : new Date());
		}

		function getBody() {

			let body = {};

			if (self.dataType === DataType.INTEGER) {

				body = getIntegerBody();

			} else if (self.dataType === DataType.BOOLEAN) {

				body = getBooleanBody();

			} else if (self.dataType === DataType.DECIMAL) {

				body = getDecimalBody();

			} else if (self.dataType === DataType.TIMESTAMP) {

				body = getDateBody();

			} else {

				body = getStringBody();
			}


			function getIntegerBody() {

				let integerBody;
				
				integerBody = {
					view: "counter",
					id: "input-dialog-" + self.uid,
					css: "inputdialogbox-counter",
					width: 140,
					height: 40,
					step: 1,
					align: "center",
					value: self.currentValue ? self.currentValue : 0,
					on: {
						onKeyPress: function(code, e) {

							const r = /^([0-9])*$/;

							if (e.key === "Dead") {

								this.disable();
								this.enable();
								this.focus();
							}

							return code == 46 || code == 8 || (code >= 35 && code <= 40) || r.test(this.getValue() + e.key);
						},
						onAfterRender: function() {
							
							this.focus();
							
							if (this.getValue() !== undefined) {
							
								this.getInputNode().setSelectionRange(0, String(this.getValue()).length);	
							}
						}
					}
				};

				return integerBody;
			}

			function getStringBody() {

				let stringBody;
				
				stringBody = {
					view: "textarea",
					id: "input-dialog-" + self.uid,
					height: 80,
					placeholder: self.requiredValue ? "" : i18n.t("LBL_COMMENTS_HERE_OPTIONAL"),
					value: self.currentValue ? self.currentValue : undefined,
					on: {
						onAfterRender: function() {
							
							this.focus();
							
							if (this.getValue()) {
							
								this.getInputNode().setSelectionRange(0, this.getValue().length);	
							}
						}
					}
				};

				return stringBody;
			}

			function getDecimalBody() {

				const r = /^(?:(?:0|[1-9][0-9]*)(?:\.[0-9]*)?|\.[0-9]+)$/;

				let decimalBody;
				
				decimalBody = {
					cols: [
						{},
						{
							view: "text",
							id: "input-dialog-" + self.uid,
							width: 140,
							height: 40,
							css: "inputdialogbox-counter",
							align: "center",
							previousValue: "",
							value: self.currentValue ? self.currentValue : 0,
							keyPressTimeout: 10,
							on: {
								onKeyPress: function(code, e) {

									if (e.key === "Dead") {

										this.disable();
										this.enable();
										this.focus();
									}

									this.config.previousValue = this.getValue();
									return code == 46 || code == 8 || (code >= 35 && code <= 40) || r.test(this.getValue() + e.key);
								},
								onTimedKeyPress: function() {

									if (this.getValue() != "" && !r.test(this.getValue())) {

										this.setValue(this.config.previousValue);
										this.focus();
										this.getInputNode().setSelectionRange(0, 0);
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
						{}
					]
				};

				return decimalBody;
			}

			function getDateBody() {

				let dateBody;
				
				dateBody = {
					id: "input-dialog-" + self.uid,
					view: "calendar",
					date: self.currentValue instanceof Date ? self.currentValue : new Date(),
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
					css: { "border": "1px solid #dadee0 !important" },
					cols: [
						{ width: 150 },
						{
							id: "input-dialog-" + self.uid,
							view: "radio",
							width: 300,
							value: self.currentValue === true ? 1 : 2,
							options: [
								{ id: 1, value: i18n.t("LBL_TRUE") },
								{ id: 2, value: i18n.t("LBL_FALSE") }
							]
						},
						{}
					]
				};

				return booleanBody;
			}

			return body;
		}
	}
}
