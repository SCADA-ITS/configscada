export default class MessageBox {

	static get INFO() { return { id: 1, color: "#3498db", icon: "./img/info.png", title: "LBL_INFO" } }
	static get WARN() { return { id: 2, color: "#fdbf4c", icon: "./img/warn.png", title: "LBL_WARN" } }
	static get ERROR() { return { id: 3, color: "#ff5c4c", icon: "./img/error.png", title: "LBL_ERROR" } }
	static get CONFIRM() { return { id: 4, color: "#3498db", icon: "./img/question.png", title: "LBL_CONFIRM" } }
	static get CONFIRM_WARN() { return { id: 5, color: "#fdbf4c", icon: "./img/warn.png", title: "LBL_WARN" } }
	
	constructor(type, message, textMultiline, onAccept, onCancel) {

		this.type = (type) ? type : MessageBox.INFO;
		this.message = message;
		this.textMultiline = textMultiline;
		this.onAccept = onAccept;
		this.onCancel = onCancel;

		this.uid = webix.uid();	 
		
		this.images = {
			1: new Image(),
			2: new Image(),
			3: new Image(),
			4: new Image(),
			5: new Image()
		};
		
		this.images[1].src = MessageBox.INFO.icon;
		this.images[2].src = MessageBox.WARN.icon; 
		this.images[3].src = MessageBox.ERROR.icon;
		this.images[4].src = MessageBox.CONFIRM.icon;  
		this.images[5].src = MessageBox.WARN.icon;   
	}

	show() {

		let self = this;
		let image = this.images[self.type.id].src;
		let withMultiline = (self.textMultiline) ? true : false;
		  
		webix.ui({
			id: "alert-" + self.uid,
			view: "window",
			modal: true,
			height: (withMultiline) ? 350 : 165,
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

									if (self.type.id != MessageBox.CONFIRM.id && self.type.id != MessageBox.CONFIRM_WARN.id && !withMultiline) {

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
						cols: [
							{ width: 35 },
							{
								width: 45,
								height: 40,
								css: { "border": "0px !important" },
								template: `<div><img style='width: 40px; padding: 0px 15px 15px 0px;' src='${image}'></div>`
							},
							{
								rows: [
									{
										view: "template",
										template: `<p style='font-size: 14px; color: #475466; text-align: center'>${self.message}</p>`,
										css: "messagebox"
									}
								]
							},
							{ width: 60 }
						]
					},
					{ height: 15 },
					getTextMultiline(),
					getButtons(),
					{ height: 5 }
				]
			}

		}).show();
		
		function getTextMultiline() {

			let textMultiline = { height: 1 };

			if (withMultiline) {

				textMultiline = {
					rows: [
						{
							cols: [
								{ width: 40 },
								{
									id: "textarea-" + self.uid,
									view: "textarea",
									height: 150,
									readonly: true,
									value: self.textMultiline,
									scroll: true,
									on: {
										onAfterRender: function() {

											let textareaNode = this.getNode().querySelector('.webix_inp_textarea');
											let checkbox = $$("checkbox-" + self.uid);
											let percentage = (textareaNode.scrollTop + textareaNode.offsetHeight) / textareaNode.scrollHeight;

											if (percentage > 1) {

												checkbox.enable();

											} else {

												textareaNode.addEventListener('scroll', function() {

													let scrollPercentage = (textareaNode.scrollTop + textareaNode.offsetHeight) / textareaNode.scrollHeight;

													if (scrollPercentage > 1 && checkbox) {

														checkbox.enable();
													}
												});
											}
										}
									}
								},
								{ width: 40 }
							]
						},
						{
							cols: [
								{ width: 40 },
								{
									id: "checkbox-" + self.uid,
									view: "checkbox",
									label: i18n.t("LBL_READ"),
									labelWidth: 50,
									disabled: true,
									css: "message-box-label",
									on: {
										onChange: function(newv){

											let acceptButton = $$("accept-button-" + self.uid);
											
											if(newv === 1 && acceptButton){
												
												acceptButton.enable();
											}
											
											if(newv === 0 && acceptButton){
												
												acceptButton.disable();
											}
										}
									}
								}
							]
						},
						{ height: 10 }
					]
				};
			}

			return textMultiline;
		}

		function getButtons() {

			let buttons = [];

			let toolbar = {
				view: "toolbar",
				height: 40,
				css: "dialog-input-custom messagebox"
			};

			if (self.type.id != MessageBox.CONFIRM.id && self.type.id != MessageBox.CONFIRM_WARN.id) {

				buttons = [
					{},
					{
						id: "accept-button-" + self.uid,
						view: "button",
						value: i18n.t("LBL_ACCEPT"),
						width: 100,
						disabled: (withMultiline) ? true : false,
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
						value: i18n.t("LBL_NO"),
						width: 100,
						click: function() {

							execute(self.onCancel);
						}
					},
					{
						id: "accept-button-" + self.uid,
						view: "button",
						value: i18n.t("LBL_YES"),
						width: 100,
						disabled: (withMultiline) ? true : false,
						click: function() {

							execute(self.onAccept);
						}
					},
					{}
				];
			}

			toolbar.cols = buttons;

			return toolbar;
		}

		function execute(command) {

			// Prevent z-index disorder. Bug preventing inputboxes contained in tabs from being selected
			setTimeout(function() {
		
				if (command) {

					command();
				}						

			}, 1);

			$$("alert-" + self.uid).close();
		}
	}
	
	getImages() {


	}
}