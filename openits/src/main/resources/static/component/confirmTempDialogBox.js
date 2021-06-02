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
						getBody(),
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
				onShow: function(){

					self.interval = setInterval(() => {

						self.timeSec--;
						$$("temp-count-"+self.uid).setValue(self.timeSec + " "+ i18n.t("LBL_SECONDS"));
			
						if(self.timeSec == 0 ){
							clearInterval(self.interval);
							self.defaultCallBack();
							let parentWindow = this.getTopParentView();
							parentWindow.close();
						}
						
					}, 1000);

				},
				onDestruct: () => {
					
					if(this.interval){
					
						clearInterval(this.interval);
					}
				}
			}
		}).show();

		function getBody(){
		
			let body = {
				rows:[
					{
						view: "label",
						label: self.description,
						align:"center"
					},
					{
						id: "temp-count-"+self.uid,
						view: "label",
						label: self.timeSec + " "+ i18n.t("LBL_SECONDS"),
						align:"center"
					}
				]
			};
	
			return body;
		}
		
	}

}

