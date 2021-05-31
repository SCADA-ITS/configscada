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
			}
		}).show();
		
	}

	getBody(){
		
		let self = this;

		let body = {
			rows:[
				{
					view: "label",
					label: this.description,
					align:"center"
				},
				{
					id: "patataaaa",
					view: "label",
					label: this.timeSec + " seconds",
					align:"center"
				}
			]
		};

		let interval = setInterval(function(){
			self.timeSec--;
			$$("patataaaa").setValue(self.timeSec);

			if(self.timeSec == 0 ){
				clearInterval(interval);
			}
			
		}, 1000);

		return body;
	}

	
}

