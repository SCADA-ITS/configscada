export default class InputDialogBox {
	
	constructor(title, text, onAccept) {

		this.title = title;
		this.text = text;
		this.onAccept = onAccept;
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
						placeholder: i18n.t("LBL_COMMENTS_HERE_OPTIONAL") 
					},
					{},
					{
						cols: [
							{},
							{
								view: "button", 
								value: i18n.t("LBL_NO"),
								width: 100, 
								click: function() {
									
									const window = this.getTopParentView();
									window.close();
								}
							},
							{ width: 5 },
							{
								view: "button", 
								value: i18n.t("LBL_YES"),
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

