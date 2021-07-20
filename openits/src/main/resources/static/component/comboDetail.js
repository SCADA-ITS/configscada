import { util } from "../lib/util.js";

const NON_SELECTED = "nonSelected";

export default class ComboDetail {

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

		let optionsArr = self.data.map(function(item) {
			return {
				id: item.id,
				value: item.label,
				description: item.description,
				default: item.default
			}
		});

		optionsArr.unshift({
			id: NON_SELECTED,
			value: "",
		});

		let body = {
			id: "button-list-" + self.uid,
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

			let content = [
				{ height: 20 },
				{
					cols: [
						{ width: 40 },
						{
							view: "label",
							label: i18n.t("LBL_OPTIONS"),
							align: "center"
						},
						{
							view: "combo",
							id: "selected-mode-" + self.uid,
							width: 350,
							value: " ",
							options: optionsArr,
							on: {
								onChange: function(newv, oldv) {

									let selection = optionsArr.find(e => e.id == newv);

									if (selection && selection.id != NON_SELECTED) {

										$$("description-" + self.uid).setValue(selection.description);

										if ($$("acept-button" + self.uid)) {
											$$("acept-button" + self.uid).enable();
										}

									} else {

										$$("description-" + self.uid).setValue("");

										if ($$("acept-button" + self.uid)) {
											$$("acept-button" + self.uid).disable();
										}
									}
								}
							}

						},
						{ width: 40 }
					]
				},
				{ height: 20 },
				{
					cols: [
						{ width: 40 },
						{
							id: "description-" + self.uid,
							view: "textarea",
							scroll: "auto",
							readonly: true,
							height: 150,
							value: ""
						},
						{ width: 40 }
					]
				},
				{ height: 20 }
			];

			return content;

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
							id: "acept-button" + self.uid,
							view: "button",
							css: "dialog-accept",
							width: 130,
							height: 35,
							disabled: true,
							label: i18n.t("LBL_ACCEPT"),
							on: {
								onItemClick(id) {

									let newSelectedId = $$("selected-mode-" + self.uid).getValue();

									if ((oldSelection && oldSelection == newSelectedId) || (newSelectedId === NON_SELECTED)) {

										webix.alert({
											title: (newSelectedId === NON_SELECTED) ? i18n.t("LBL_WITHOUT_SELECTION") : i18n.t("LBL_SAME_MODE_SELECTED"),
											text: (newSelectedId === NON_SELECTED) ? i18n.t("LBL_WITHOUT_SELECTION_DESCRIPTION") : i18n.t("LBL_SAME_MODE_SELECTED_DESCRIPTION"),
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

											self.onToggle(newSelectedId);
											$$(self.optionWindow).close();
										});
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