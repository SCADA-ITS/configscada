export default class ConfirmTempDialogBox {
	
	constructor(message, description, timeSec, callBackAccept, callBackCancel, defaultCallBack) {

		this.message = message;
		this.description = description;
		this.timeSec = timeSec; 
		this.callBackAccept = callBackAccept;
		this.callBackCancel = callBackCancel;
		this.defaultCallBack = defaultCallBack;

		this.uid = webix.uid();
		this.interval;
	}
	
	show() {
		
		let self = this;
		
		webix.ui({
			view: "window",
			modal: true,
			height: 240,
			width:  640,
			css: "dialog-input-custom",
			head: this.message,
			position: "center",
			body: {
				rows: [
						{},
						this.getBody(),
						{
							cols: [
								{ 
									view: "button", 
									css: "dialog-cancel", 
									align:"left",
									value: i18n.t("LBL_CANCEL"),
									width: 130, 
									height: 41,
									click: function () { 

										self.callBackCancel();
										
										let parentWindow = this.getTopParentView();
										parentWindow.close();
									} 
								},
								{ },
								{ 
									view: "button",
									align:"right", 
									css: "dialog-accept",
									value: i18n.t("LBL_ACCEPT"),
									width: 130, 
									height: 41,
									click: function () { 
										
										self.callBackAccept();
										
										let parentWindow = this.getTopParentView();
										parentWindow.close();
									} 
								}
							]
						}
					],
				type: "form"
			},
			on: {
				onShow: () => {

					this.interval = setInterval(() => {

						this.timeSec--;
						$$("temp-count-"+this.uid).setValue(this.timeSec + " "+ i18n.t("LBL_SECONDS"));
			
						if(this.timeSec == 0 ){
							clearInterval(this.interval);
							this.defaultCallBack();
							$$("temp-count-"+this.uid).setValue(i18n.t("LBL_CONFIRM_TEMP_DIALOG_BOX_TIMEOUT"));
						}
						
					}, 1000);

				},
				onDestruct: () => {

					clearInterval(this.interval);
				}
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
					id: "temp-count-"+this.uid,
					view: "label",
					label: this.timeSec + " "+ i18n.t("LBL_SECONDS"),
					align:"center"
				}
			]
		};

		return body;
	}

	
}

