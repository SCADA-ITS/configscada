export default class SoundMessageDialogBox {

	constructor(element, callBack) {

		this.element = element;
		this.callBack = callBack;
		
		this.uid = webix.uid();
	}

	show() {

		let self = this;

		webix.ui({
			view: "window",
			modal: true,
			height: 86,
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
								value: i18n.t("LBL_CANCEL"),
								click: function() {

									let parentWindow = this.getTopParentView();
									parentWindow.close();
								}
							},
							{ width: 5 },
							{
								view: "button",
								align: "left",
								css: "dialog-button",
								value: i18n.t("LBL_CONFIRM"),
								click: function() {

									self.callBack(self.messageId);

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

					}

				]
			};

			return body;
		}

	}

}

