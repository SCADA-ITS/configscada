import { util } from "../lib/util.js";

export default class FilesManager {

	constructor(title, files, uploadCode) {

		this.uid = webix.uid();
		this.title = title;
		this.files = files;
		this.uploadCode = uploadCode;
	}

	show() {

		let files = this.files;
		let windowId = webix.uid();
		let filePanelViewList = [];
		let fileDownloadList = [];
		let panelHiddenList = [];
		let panelViewList = [];

		function getDocumentName(documentUrl) {

			let documentNameAux = documentUrl.split("/");
			let documentName = documentNameAux[documentNameAux.length - 1];

			return documentName;
		}


		const getDocumentsTypeWindowsHeader = () => {

			let viewHeader = {
				borderless: false,
				width: 781,
				height: 51,
				rows: [
					{ height: 20 },
					{
						cols: [
							{ width: 10 },
							{
								borderless: true,
								//								height: 15,
								width: 30,
								template: function() {
									let headerIcon = "regular-file-64.png";
									return `<image height="24px"; width="24px" src="img/${headerIcon}" />`;
								}
							},
							{
								rows: [
									{ height: 5 },
									{
										view: "label",
										label: `<div class="title"><span class="section-text" style="font-size: 14px;">${i18n.t("LBL_IMS_INCIDENTS_FILES")}</span></div>`,
										height: 20,
										width: 200,
										align: "left"
									}
								]
							},
							{ width: 322 },
							{
								rows: [
									{},
									{
										id: windowId + "_search",
										view: "search",
										placeholder: i18n.t("LBL_SEARCH"),
										name: "plan",
										icon: "webix_input_icon wxi-search",
										width: 212,
										height: 30,
										align: "left",
										on: {
											onKeyPress(code, code2) {

												let value = $$(windowId + "_search").getValue();
												if (code === 8) {
													value = value.slice(0, -1);
												} else {
													value = value + code2.key;
												}
												value = value.toLowerCase();

												panelViewList.forEach(e => {

													let control = true;

													if (control) {
														if (e.alias.toLowerCase().search(value) !== -1) {
															control = false;
														}
													}
													if (control) {
														if (e.description.toLowerCase().search(value) !== -1) {
															control = false;
														}
													}
													if (control) {
														if (getDocumentName(e.documentUrl).toLowerCase().search(value) !== -1) {
															control = false;
														}
													}

													if (control === true) {
														panelHiddenList.push(e.id);
														$$(e.id + "_absPanel").hide();
													} else {
														panelHiddenList = panelHiddenList.filter((value) => {
															return value !== e.id;
														});
														$$(e.id + "_absPanel").show();
													}
												});
											}
										}

									}
								]


							}
						]
					}]
			};

			return viewHeader;
		}


		const drawpanel = (id, selected) => {
			let v_selected = { "background": "#91aacf" };
			let v_unSelected = { "background": "#979797" };
			let body = {
				view: "panel",
				top: 30,
				left: 20,
				id: id + "_Panel",
				width: 81,
				height: 84,
				type: "line",
				body: {
					id: id + "_PanelTemplate",
					css: selected ? v_selected : v_unSelected,
					template: "<div></div>"
				}
			};

			return body;
		}


		const drawsquare = (id, selected) => {
			let v_selected = { "background": "#91aacf" };
			let v_unSelected = { "background": "#979797" };
			let body = {
				borderless: true,
				height: 50,
				width: 50,
				top: 50,
				left: 38,
				id: id + "_Square",
				css: selected ? v_selected : v_unSelected,
				template: function() {
					let headerIcon = "regular-file-64.png";
					return `<image height="90%" src="img/${headerIcon}" />`;
				}
			};

			return body;
		}


		const getDocumentsTypeWindowsContent = () => {

			let body = [
				{
					height: 195,
					rows: [
						{ height: 5 },
						{
							space: "line",
							cols: [
								{
									type: "space",
									width: 13,
									height: 180
								},
								{
									view: "scrollview",
									width: 755,
									//document.documentElement.clientWidth - 900,
									scroll: "auto",
									height: 180,
									body: {
										cols: filePanelViewList
									}
								}
							]
						}
						//						{	
						//						type: "space", 
						//						height: 10}
					]
				}
			]

			return body;
		}


		const downloadFileAction = (fileDownloadList, panelHiddenList, panelViewList) => {
			fileDownloadList.forEach(element => {

				if (!panelHiddenList.includes(element)) {

					let docToDownload = panelViewList.find(e => e.id === element).documentUrl;
					window.open(docToDownload.replace(" ", "%20"), '_blank');
				}
			});
		}


		const getDocumentsTypeFooterBody = () => {

			let body = [
				{
					height: 220,
					rows: [
						{ height: 15 },
						{
							cols: [
								{ width: 30 },
								{
									view: "label",
									label: `<div class="title"><i style="color:#94a1b3;; font-size: 2em;" class="mdi mdi-view-headline"></i></div>`,
									align: "left",
									height: 25,
									width: 25
								},
								{ width: 11 },
								{
									view: "label",
									label: `<div class="title"><span class="section-text" style="color:#475269; font-size: 14px;">${i18n.t("LBL_DETAILS_DESCRIPTION")}</span></div>`,
									align: "left",
									height: 17
								},
								{}
							]
						},
						{ height: 15 },
						{
							height: 55,
							cols: [
								{ width: 50 },
								{
									width: 100,
									rows: [
										{
											view: "label",
											label: `<div class="title"><span class="section-text" style="color:#94a1b3; font-size: 14px; font-family: Roboto; font-weight: 300;">${i18n.t("LBL_FILE")}:</span></div>`,
											align: "left",
											height: 17

										}
									]
								},
								{
									rows: [
										{
											id: windowId + "_labelFooterName",
											tooltip:
												function() {
													return $$(windowId + "_labelFooterName").data.label;
												},
											view: "label",
											css: "file-manager-description",
											label: getDocumentName(files[0].documentUrl),
											align: "left",
											height: 17
										},
										{ height: 10 },
										{
											id: windowId + "_labelFooterTask",
											tooltip:
												function() {
													return $$(windowId + "_labelFooterTask").data.label;
												},
											view: "label",
											css: "file-manager-description",
											label: files[0].description,
											align: "left",
											height: 20
										}
									]
								}
							]
						},
						{ height: 10 },
						{
							cols: [
								{ width: 13 },
								{
									id: windowId + "_labelFooterDescription",
									borderless: true,
									css: { "background": "#edf0f7ff", "margin": "0px", "border-radius": "4px" },
									width: 573,
									height: 63,
									scroll: "auto",
									template: `<div><p style="font-size: 12px; font-weight: 300; margin: 0px; padding: 5px">${files[0].description}</p></div>`,

								},
								{ width: 34 },
								{
									width: 130,
									rows: [
										{ height: 14 },
										{
											view: "button",
											height: 41,
											css: "dialog-accept",
											label: i18n.t("LBL_DOWNLOAD"),
											type: "icon",
											icon: "webix_icon wxi-download",
											on: {
												onItemClick() {
													downloadFileAction(fileDownloadList, panelHiddenList, panelViewList);
												}
											}
										}
									]
								},
								{}
							]
						},
						{ height: 10 }
					]
				}
			];

			return body;
		}


		const getDocumentBody = () => {

			let body = [
				{
					cols: [
						getDocumentsTypeWindowsHeader()
					]
				},
				{
					type: "line",
					rows: getDocumentsTypeWindowsContent()
				},
				{
					cols: [
						{
							tooltip: true,
							rows: getDocumentsTypeFooterBody()
						}
					]
				}
			]

			return body;
		}

		if (files && files.length > 0) {

			let counter = 0;
			files.map(e => {
				let panelId = "panelDocType" + webix.uid();
				counter === 0 ? fileDownloadList.push(panelId) : null;

				panelViewList.push({ id: panelId, alias: e.file, description: e.description, documentUrl: e.documentUrl });
				filePanelViewList.push(
					{
						id: panelId + "_absPanel",
						view: "abslayout",
						hidden: false,
						width: 107, height: 174, borderless: true,
						cells: [
							drawpanel(panelId, counter === 0 ? true : false),
							drawsquare(panelId, counter === 0 ? true : false),
							{
								view: "checkbox", top: 23, left: 75, id: panelId + "_Check", width: 27, height: 27, css: "dialog-download",
								data: { alias: e.file, description: e.description, documentName: getDocumentName(e.documentUrl) },
								value: counter === 0 ? 1 : 0,
								click: (id) => {

									if ($$(id.split("_")[0] + "_Check").data.value === 1) {

										fileDownloadList.push(id.split("_")[0]);

										$$(windowId + "_labelFooterName").setValue($$(id.split("_")[0] + "_Check").data.data.alias);
										$$(windowId + "_labelFooterTask").setValue($$(id.split("_")[0] + "_Check").data.data.description);

										let description = {
											id: windowId + "_labelFooterDescription",
											borderless: true,
											scroll: "auto",
											css: { "background": "#edf0f7ff", "margin": "0px", "border-radius": "4px" },
											width: 573,
											height: 63,
											template: `<div><p style="font-size: 12px; font-weight: 300; margin: 0px; padding: 5px" >${$$(id.split("_")[0] + "_Check").data.data.description}</p></div>`
										}

										webix.ui(description, $$(windowId + "_labelFooterDescription"));
										webix.ui(drawpanel(id.split("_")[0], true), $$(id.split("_")[0] + "_Panel"));
										webix.ui(drawsquare(id.split("_")[0], true), $$(id.split("_")[0] + "_Square"));
									}

									if ($$(id.split("_")[0] + "_Check").data.value === 0) {

										fileDownloadList = fileDownloadList.filter((value) => {
											return value !== id.split("_")[0];
										});

										webix.ui(drawpanel(id.split("_")[0], false), $$(id.split("_")[0] + "_Panel"));
										webix.ui(drawsquare(id.split("_")[0], false), $$(id.split("_")[0] + "_Square"));
									}
								}
							},
							{
								view: "label",
								tooltip: e.file,
								label: `<div class="title" style="font-size: 13px; color: #475269; max-width: 65px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis"><span class="section-text" style="max-width: 65px">${e.file}</span></div>`,
								align: "left",
								height: 30,
								width: 80,
								top: 115,
								left: 25
							},
							{
								view: "label",
								tooltip: e.description,
								label: `<div class="title" style="font-size: 11px; color: #475269; max-width: 65px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis"><span class="section-text" style="max-width: 65px">${e.description}</span></div>`,
								align: "left",
								height: 30,
								width: 80,
								top: 130,
								left: 25,
							}
						]
					}
				);

				counter = + 1;
			});
		}

		//		let h = document.documentElement.clientHeight;
		//		let w = document.documentElement.clientWidth - 850;

		let body = {
			cols: [
				{ width: 6 },
				{
					rows: [
						{ height: 5 },
						{
							tooltip: true,
							css: { "height": "435px !important", "width": "778px !important", "border": "1px solid #e2e2e2" },
							rows: getDocumentBody()
						},
						{ height: 5 }
					]
				},
				{ width: 6 }
			]
		};

		util.ui.loadWindow(
			i18n.t("LBL_ICNCIDENT_DOCUMENT_TYPE") + " " + this.title,
			body,
			485,
			790,
			true
		);
	}

}