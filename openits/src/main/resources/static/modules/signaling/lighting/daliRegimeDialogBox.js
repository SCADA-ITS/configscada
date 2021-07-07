export default class DaliRegimeDialogBox {

	constructor(dali, parent) {

		this.dali = dali;
		this.parent = parent;

		this.uid = webix.uid();
	}

	show() {

		let self = this;

		webix.ui({
			view: "window",
			modal: true,
			height: 180,
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

									if(self.proposedWindowRegimeValue){

										let command = self.parent.config.commands.SEND_DALI_REGIME;
										
										command.elementId = self.dali.id;
										command.commandElementValues[0].value = self.proposedWindowRegimeValue;
	
										let body = {
											commands: []
										};
										body.commands.push(command);
			
										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
	
											if (!data.json()) {
	
												webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
											}
										});
										
										let parentWindow = this.getTopParentView();
										parentWindow.close();

									} else {

										webix.message(i18n.t("LBL_NO_DATA_SELECTED"))
									}

								}
							}
						]
					},
					{
						height: 8,
						css: "dialog-general-custom"
					}
				],
				type: "line"
			}
		}).show();

		function getBody() {

			let daliRegimeConfig = self.dali[self.parent.config.params.daliRegimeConfig];
			daliRegimeConfig = JSON.parse(daliRegimeConfig);

			let availableRegimes = daliRegimeConfig.regimes.map(function(regime) {
				return { 
					id: regime.id, 
					value: regime.alias
				};
			});

			let combo = {
				rows: [
					{
						view: "label",
						height: 24,
						width: 180,
						template: "<span>" + i18n.t("LBL_CHANGE_REGIME_TO") + "</span>",
						css: "global-text"
					},
					{ height: 24 },
					{
						width: 180,
						height: 28,
						view: "combo", 
						options: availableRegimes,
						on: {
							onChange(newv, oldv) {

								updateProposedRegime(newv, daliRegimeConfig.regimes);
								webix.ui(getViewDocumentText(), $$("view-windows-regime-document-text-" + self.uid));
								webix.ui(getViewDocument(), $$("view-windows-regime-document-" + self.uid));
							}
						}
					}
				]
			};

			let docs = {
				rows: [
					getViewDocument(),
					getViewDocumentText()
				]
			};

			let viewRegimesWindow = {
				cols: [
					{width: 6},
					combo,
					{width: 12},
					docs,
					{width: 6},
				]
			};

			let body = {
				css: "dialog-general-custom",
				rows: [
					{
						height: 4,
						css: { "background-color": "#1CA1C1", "width": "600px !important" }
					},
					{ height: 12 },
					viewRegimesWindow,
					{ height: 24 }
				]
			};

			return body;

			function updateProposedRegime(value, illuminationRegimes) {

				self.proposedWindowRegimeValue = value;

				if (self.proposedWindowRegimeValue && illuminationRegimes) {

					let proposedConfig = illuminationRegimes.find(e => e.id == self.proposedWindowRegimeValue);
					self.proposedWinRegimeDescription = proposedConfig.description;
					self.proposedWinRegimeDocumentUrl = proposedConfig.documentUrl

					if (self.proposedWinRegimeDocumentUrl) {

						self.proposedWinRegimeDocumentName = self.proposedWinRegimeDocumentUrl.substring(self.proposedWinRegimeDocumentUrl.lastIndexOf("/") + 1, self.proposedWinRegimeDocumentUrl.lastIndexOf("."));
						self.proposedWinRegimeDocumentType = self.proposedWinRegimeDocumentUrl.substring(self.proposedWinRegimeDocumentUrl.lastIndexOf(".") + 1, self.proposedWinRegimeDocumentUrl.length);
					}
				} else {

					self.proposedWinRegimeDescription = "";
				}
			}

			function getViewDocument() {

				let viewDocument = {
					id: "view-windows-regime-document-" + self.uid,
					height: 26 
				};
			
				if(self.proposedWinRegimeDocumentUrl) {
			
					viewDocument = {
						id: "view-windows-regime-document-" + self.uid,
						type:"line",
						cols: [
							{},
							{
								height: 26,
								align: "right",
								width: 500,
								view: "button",
								type: "icon",
								label: self.proposedWinRegimeDocumentName + " " + self.proposedWinRegimeDocumentType,
								value: self.proposedWinRegimeDocumentName + " " + self.proposedWinRegimeDocumentType,
								tooltip: true,
								icon: "mdi mdi-file",
								click: function() {
									window.open(self.proposedWinRegimeDocumentUrl, '_blank');
								},
								css: "button-icon-transparent"
							}		
						]
					};
				}
			
				return viewDocument;
			}
			
			function getViewDocumentText() {
				return {
					id: "view-windows-regime-document-text-"+ self.uid,
					height: 57,
					width: 400,
					borderless: true,
					view: "template",
					template: `<div class="ventilation-description-box"><span > ${self.proposedWinRegimeDescription}</span></div>`
				};
			}
		}

	}

}

