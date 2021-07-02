export default class InputDialogBox {
	
	constructor(title, text, onAccept, requiredValue, currentValue) {

		this.title = title;
		this.text = text;
		this.onAccept = onAccept;
		this.requiredValue = requiredValue;
		this.currentValue = currentValue;
	}

	show() {
		
		let self = this;
		
		webix.ui({
			view: "window",
			modal: true,
			height: 250,
			width: 600,
			css: "dialog-input-custom",
			head: self.title,
			position: "center",
			body: {
				rows: [
					{ template: self.text, borderless: true },
					{ 
						view: "textarea", 
						id: "txt-dialog-" + self.uid, 
						height: 80,
						placeholder: self.requiredValue ? "" : i18n.t("LBL_COMMENTS_HERE_OPTIONAL"),
						value: self.currentValue ? self.currentValue : undefined 
					},
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
								value: self.requiredValue ? i18n.t("LBL_ACCEPT") : i18n.t("LBL_YES"),
								width: 100, 
								click: function() {
									
									const window = this.getTopParentView();
									const txtDialogReconize = $$("txt-dialog-" + self.uid);
									
									self.onAccept(txtDialogReconize.getValue());
									
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
	}
}

