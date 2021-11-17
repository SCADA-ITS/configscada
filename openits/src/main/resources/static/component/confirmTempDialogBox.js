export default class ConfirmTempDialogBox {

	constructor(message, description, timeSec, callBackAccept, callBackCancel, defaultCallBack) {

		this.message = message;
		this.description = description;
		this.timeSec = timeSec;
		this.callBackAccept = callBackAccept;
		this.callBackCancel = callBackCancel;
		this.defaultCallBack = defaultCallBack;

		this.uid = webix.uid();
		this.interval = undefined;
	}

	show() {

		let self = this;

		webix.ui({
			view: "window",
			id: "confirm-temp-dialog-box-" + self.uid,
			modal: true,
			height: 126,
			width: 600,
			css: { "box-shadow": " 0 0.5rem 1.5rem !important" },
			head: false,
			position: "center",
			body: {
				rows: [
					getBody(),
					{
						css: "dialog-general-custom",
						cols: [
							{ width: 197 },
							{
								view: "button",
								css: "dialog-button",
								align: "right",
								width: 105,
								value: i18n.t("LBL_NO"),
								click: function() {

									self.callBackCancel();

									let parentWindow = this.getTopParentView();
									parentWindow.close();
								}
							},
							{ width: 5 },
							{
								view: "button",
								align: "left",
								css: "dialog-button",
								value: i18n.t("LBL_YES"),
								click: function() {

									self.callBackAccept();

									let parentWindow = this.getTopParentView();
									parentWindow.close();
								}
							}
						]
					},
					{
						height: 8,
						css: "dialog-general-custom"
					}
				],
				type: "form"
			},
			on: {
				onShow: function() {
					
					if(self.interval){

						clearInterval(self.interval);
						self.interval = undefined;
					}

					self.interval = setInterval(() => {

						self.timeSec--;
						$$("temp-count-" + self.uid).setValue(self.description + " " + self.timeSec + " " + i18n.t("LBL_SECONDS"));

						if (self.timeSec <= 0) {
							clearInterval(self.interval);
							self.defaultCallBack();
							let parentWindow = this.getTopParentView();
							parentWindow.close();
						}

					}, 1000);

				},
				onDestruct: () => {

					if (self.interval) {

						clearInterval(self.interval);
						self.interval = undefined;
					}
				}
			}
		}).show();

		function getBody() {

			let body = {
				css: "dialog-general-custom",
				rows: [
					{
						height: 4,
						css: { "background-color": "#1CA1C1", "width": "600px !important" }
					},
					{
						view: "label",
						label: self.message,
						align: "center",
						css: "dialog-header-custom"

					},
					{
						css: "dialog-description-custom",
						cols: [
							{
								id: "temp-count-" + self.uid,
								view: "label",
								label: self.description + " " + self.timeSec + " " + i18n.t("LBL_SECONDS"),
								align: "center",
								width: 600,
							}
						]
					}

				]
			};

			return body;
		}

	}

	close() {

		let thisComponent = $$("confirm-temp-dialog-box-" + this.uid);

		if(thisComponent) {

			thisComponent.close();
		}
	}
}

