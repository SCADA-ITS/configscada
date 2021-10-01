export default class MessageBox {

	static get INFO() { return { id: 1, color: "#3498db", icon: "./img/info.png", title: "LBL_INFO" } }
	static get WARN() { return { id: 2, color: "#fdbf4c", icon: "./img/warn.png", title: "LBL_WARN" } }
	static get ERROR() { return { id: 3, color: "#ff5c4c", icon: "./img/error.png", title: "LBL_ERROR" } }
	static get CONFIRM() { return { id: 4, color: "#3498db", icon: "./img/question.png", title: "LBL_CONFIRM" } }
	
	constructor(type, message, onAccept, onCancel) {

		this.type = (type) ? type : MessageBox.INFO;
		this.message = message;
		this.onAccept = onAccept;
		this.onCancel = onCancel;

		this.uid = webix.uid();
	}

	show() {

		let self = this;

		webix.ui({
			id: "alert-" + self.uid,
			view: "window",
			modal: true,
			height: 160,
			width: 600,
			position: "center",
			head: {
				view: "toolbar",
				rows: [
					{ height: 4, css: { "margin": "0px !important", "background-color": self.type.color, "width": "600px !important" } },
					{
						css: { "margin-top": "0px !important" },
						cols: [
							{
								view: "label",
								label: i18n.t(self.type.title),
								align: "center",
								css: { "padding-left": "3%" }
							},
							{
								view: "icon",
								icon: "wxi-close",
								align: 'right',
								click: function() {

									if (self.type.id != MessageBox.CONFIRM.id) {

										execute(self.onAccept);

									} else {

										execute(self.onCancel);
									}
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
						//height: 105,
						cols: [
							{ width: 35 },
							{
								width: 70,
								css: { "border": "0px !important" },
								template: `<div><img style='width: 40px; padding: 0px 15px 15px 0px;' src=${self.type.icon}></div>`
							},
							{
								rows: [
									{
										view: "template",
										template: `<p style='font-size: 14px; color: #475466;'>${self.message}</p>`,
										css: "messagebox"
									}
								]
							},
							{ width: 50 }
						]
					},
					{ height: 10 },
					getButtons(),
					{ height: 5 }
				]
			}

		}).show();


		function getButtons() {

			let buttons = [];

			let toolbar = {
				view: "toolbar",
				height: 40,
				css: "dialog-input-custom messagebox"
			};

			if (self.type.id != MessageBox.CONFIRM.id) {

				buttons = [
					{},
					{
						view: "button",
						value: i18n.t("LBL_ACCEPT"),
						width: 100,
						click: function() {

							execute(self.onAccept);
						}
					},
					{}
				];

			} else {

				buttons = [
					{},				
					{
						view: "button",
						value: i18n.t("LBL_YES"),
						width: 100,
						click: function() {

							execute(self.onAccept);
						}
					},
					{
						view: "button",
						value: i18n.t("LBL_NO"),
						width: 100,
						click: function() {

							execute(self.onCancel);
						}
					},
					{}
				];
			}

			toolbar.cols = buttons;

			return toolbar;
		}

		function execute(command) {

			if (command) {

				command();
			}

			$$("alert-" + self.uid).close();
		}
	}
}