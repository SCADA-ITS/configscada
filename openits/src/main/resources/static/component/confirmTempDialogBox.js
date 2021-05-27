export default class ConfirmTempDialogBox {
	
	constructor(message, description, timeSec, callBackAccept, callBackCancel, defaultCallBack) {

		this.message = message;
		this.description = description;
		this.timeSec = timeSec; 
		this.callBackAccept = callBackAccept;
		this.callBackCancel = callBackCancel;
		this.defaultCallBack = defaultCallBack;
	}
	
	show() {
		
		let self = this;
		
		webix.ui({
			view: "window",
			modal: true,
			height: 300,
			width:  640,
			// css: "dialog-input-custom",
			head: this.message,
			position: "center",
			body: {
			rows: [
					{ template: self.text, borderless: true },
					this.getBody(),
					{},
					{
						cols: [
							{},
							{ 
								view: "button", 
								css:"webix_danger", 
								type:"icon", 
								icon:"mdi mdi-cancel", 
								label: i18n.t("LBL_CANCEL"), 
								value: i18n.t("LBL_CANCEL"), 
								click: function () { 
									self.callBackCancel();
									let parentWindow = this.getTopParentView();
									parentWindow.close();
								} 
							},
							{ width: 5 },
							{ 
								view: "button", 
								css:"webix_primary", 
								type:"icon", 
								icon:"mdi mdi-check", 
								label: i18n.t("LBL_ACCEPT"), 
								value: i18n.t("LBL_ACCEPT"), 
								click: function () { 
									self.callBackAccept();
									let parentWindow = this.getTopParentView();
									parentWindow.close();
								} 
							},
							{}
						]
					}
				],
				type: "form"
			}
		}).show();
		
	}

	getBody(){

		let body = {
			rows:[
				{
					view: "label",
					label: this.description,
					align:"center"
				},
				{
					view: "label",
					label: this.timeSec + " seconds",
					align:"center"
				}
			]
		};

		//TODO countDown and self.defaultCallBack()

		return body;
	}

	
}

