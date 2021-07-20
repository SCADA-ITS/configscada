import { util } from "../lib/util.js";

export default class OptionButtonList {

	constructor(title, data, onClick, onToggle) {

		this.title = title;
		this.data = data;
		this.onClick = onClick;
		this.onToggle = onToggle;
		this.optionWindow = undefined;

		this.uid = webix.uid();

	}

	show(oldSelection) {

		let self = this;

		let body = {
			id: "button-list-" + self.uid,
			width: 625,
			rows: [
				{ height: 15 },
				{
					cols: [
						{ width: 25 },
						{
							css: { "border": "1px solid #dadee0 !important", "box-sizing": "border-box" },
							rows: getContent()
						},
						{ width: 25 }
					]
				},
				{ height: 15 },
				getToolbarActions()
			]
		}

		if (self.title != undefined || self.title != null) {

			self.optionWindow = util.ui.loadWindow(self.title, body, 500, 650, true);

		} else {

			return body;
		}


		function getContent() {

			let content = [];

			for (let dat of self.data) {

				let contentDetail = {
					rows: [
						{ height: 15 },
						{
							css: "option-button-list-actions",
							cols: [
								getButtons(dat),
								{
									rows: [
										{
											id: "text" + dat.id + "-" + self.uid,
											view: "label",
											css: { "padding-left": "10px", "height": "25px" },
											label: `<span>${dat.label}</span>`
										},
										{
											id: "desc-" + dat.id + "-" + self.uid,
											height: 35,
											scroll: "auto",
											css: { "padding-left": "10px !important", "border": "none" },
											template: `<span style="font-size: 13px !important; line-height: 15px">${dat.description}</span>`
										}
									]
								}
							]
						},
						{ height: 15 }
					]
				};

				content.push(contentDetail);
			}

			return content;


			function getButtons(dat) {

				let componentType = (self.onClick != undefined) ? "button" : "toggle";
				let buttonType = {};

				if (self.onClick != undefined) {

					buttonType = {
						id: "button-" + dat.id + "-" + self.uid,
						view: componentType,
						width: 75,
						height: 75,
						type: "icon",
						icon: (dat.icon) ? dat.icon : "mdi mdi-exit-to-app",
						css: "toggle-option-list",
						on: {
							onItemClick() {

								self.onClick(dat.id);
							}
						}
					}

				} else {

					buttonType = {
						id: "button-" + dat.id + "-" + self.uid,
						view: componentType,
						width: 75,
						height: 75,
						type: "icon",
						icon: (dat.icon) ? dat.icon : "mdi mdi-exit-to-app",
						value: (dat.default) ? 1 : 0,
						name: dat.id,
						css: "toggle-option-list",
						on: {
							onItemClick(obj) {

								for (let dat of self.data) {
									$$("button-" + dat.id + "-" + self.uid).setValue(0);

								}

								$$(obj).setValue(1);

							}
						}
					}
				}

				return buttonType;
			}
		}


		function getToolbarActions() {

			let viewToolbarActions = {};

			if ((self.title != undefined) || (self.title != null)) {

				viewToolbarActions = {
					view: "toolbar",
					height: 45,
					borderless: true,
					css: { "border-top": "1px solid black" },
					elements: [
						{ width: 15 },
						{
							view: "button",
							css: "dialog-cancel",
							width: 130,
							height: 35,
							label: i18n.t("LBL_CANCEL"),
							on: {
								onItemClick() {

									$$(self.optionWindow).close();
								}
							}
						},
						{},
						{
							view: "button",
							css: "dialog-accept",
							width: 130,
							height: 35,
							label: i18n.t("LBL_ACCEPT"),
							on: {
								onItemClick(id) {

									for (let dat of self.data) {

										let value = $$("button-" + dat.id + "-" + self.uid).getValue();

										if (value == 1) {
											let selectionId = $$("button-" + dat.id + "-" + self.uid).config.name;

											if (selectionId === oldSelection) {

												webix.alert({
													title: i18n.t("LBL_SAME_MODE_SELECTED"),
													text: i18n.t("LBL_SAME_MODE_SELECTED_DESCRIPTION"),
													type: "alert-warning",
													width: 600
												});

											} else {

												webix.confirm({
													title: i18n.t("LBL_LIGHTING_CHANGE_OPTION_CONFIRM"),
													ok: i18n.t("LBL_ACCEPT"),
													cancel: i18n.t("LBL_CANCEL"),
													text: i18n.t("LBL_LIGHTING_CHANGE_OPTION_CONFIRM_DESCRIPTION"),
													width: 600
												}).then(function() {

													self.onToggle(selectionId);
													$$(self.optionWindow).close();
												});
											}
										}
									}
								}
							}
						},
						{ width: 15 }
					]
				};
			}

			return viewToolbarActions;
		}

	}

}