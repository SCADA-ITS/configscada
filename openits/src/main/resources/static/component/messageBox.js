
const BAR_COLOR = {
	MODE_INFO: "#3498db",
	MODE_WARN: "#fdbf4c",
	MODE_ERROR: "#ff5c4c",
	MODE_QUESTION: "#3498db"
}

export default class MessageBox {

	static get MODE_INFO() { return 1 };
	static get MODE_WARN() { return 2 };
	static get MODE_ERROR() { return 3 };
	static get MODE_QUESTION() { return 4 };


	constructor(type, message, onAccept, onCancel) {

		this.type = (type) ? type : MessageBox.MODE_INFO;
		this.message = message;
		this.onAccept = onAccept;
		this.onCancel = onCancel;
	}


	show() {

		let self = this;

		let title = undefined;
		let barColor = undefined;
		let icon = undefined;
		let oneButton = true;

		if (self.type === MessageBox.MODE_WARN) {

			title = "LBL_WARN";
			barColor = BAR_COLOR.MODE_WARN;
			icon = "./img/warn.png";

		} else if (self.type === MessageBox.MODE_ERROR) {

			title = "LBL_ERROR";
			barColor = BAR_COLOR.MODE_ERROR;
			icon = "./img/error.png";

		} else if (self.type === MessageBox.MODE_QUESTION) {

			title = "LBL_QUESTION";
			barColor = BAR_COLOR.MODE_QUESTION;
			icon = "./img/question.png";
			oneButton = false;

		} else if (self.type === MessageBox.MODE_INFO) {

			title = "LBL_INFO";
			barColor = BAR_COLOR.MODE_INFO;
			icon = "./img/info.png";
		}


		webix.ui({
			view: "window",
			modal: true,
			height: 250,
			width: 600,
			position: "center",
			head: {
				view: "toolbar",
				rows: [
					{ height: 4, css: { "margin": "0px !important", "background-color": barColor, "width": "600px !important" } },
					{
						css: { "margin-top": "0px !important" },
						cols: [
							{
								view: "label",
								label: i18n.t(title),
								align: "center",
								css: { "padding-left": "3%" }
							},
							{
								view: "icon",
								icon: "wxi-close",
								align: 'right',
								click: function() {

									if (oneButton) {

										self.onAccept();

									} else {

										self.onCancel();
									}

									const window = this.getTopParentView();
									window.close();
								}
							}
						]
					}
				]
			},
			body: {
				rows: [
					{ height: 10 },
					{
						height: 105,
						cols: [
							{ width: 35 },
							{
								width: 95,
								css: { "border": "0px !important"},
								template: `<div><img style='padding: 15px 15px 15px 0px;' src=${icon}></div>`
							},
							{
								rows: [
									{
										view: "template",
										template: `<p style='font-size: 14px; color: #475466;'>${self.message}</p>`,
										css: "messagebox"
									},
									{ height: 10 },
								]
							},
							{width: 105}
						]
					},
					{ height: 5 },
					getButtons()
				]
			}

		}).show();


		function getButtons() {

			let buttons = [];

			let toolbar = {
				view: "toolbar",
				height: 38,
				css: "dialog-input-custom messagebox"
			};

			if (oneButton) {

				buttons = [
					{},
					{
						view: "button",
						value: i18n.t("LBL_ACCEPT"),
						width: 100,
						click: function() {

							self.onAccept();
							const window = this.getTopParentView();
							window.close();
						}
					},
					{}
				];

			} else {

				buttons = [
					{},
					{
						view: "button",
						value: i18n.t("LBL_NO"),
						width: 100,
						click: function() {

							self.onCancel();
							const window = this.getTopParentView();
							window.close();
						}
					},
					{
						view: "button",
						value: i18n.t("LBL_YES"),
						width: 100,
						click: function() {

							self.onAccept();
							const window = this.getTopParentView();
							window.close();
						}
					},
					{}
				];
			}

			toolbar.cols = buttons;

			return toolbar;
		}

	}

}