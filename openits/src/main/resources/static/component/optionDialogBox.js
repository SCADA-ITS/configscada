import { util } from "../lib/util.js";
import OptionButtonList from "./optionButtonList.js";
import ComboDetail from "./comboDetail.js";
import MessageBox from "./messageBox.js";

const NUM_OPTIONS = 3;

export default class OptionDialogBox {

	static get MODE_TOGGLE() { return 1 }
	static get MODE_COMBO() { return 2 }

	constructor(title, options, onChange, mode) {

		this.title = title;
		this.options = options;
		this.onChange = onChange;
		this.mode = OptionDialogBox.MODE_TOGGLE;
		
		if (mode === OptionDialogBox.MODE_COMBO || mode === OptionDialogBox.MODE_TOGGLE) {
			
			this.mode = mode;	
		}
		else {
			
			if (options.length > NUM_OPTIONS) {
				
				this.mode = OptionDialogBox.MODE_COMBO;
			}
		}

		this.uid = webix.uid();

		this.optionWindow = undefined;
	}


	show(oldSelection) {

		let self = this;
		let content = {};
		let bodyContent = {};
		let height = undefined;

		if (self.mode === OptionDialogBox.MODE_TOGGLE) {

			content = new OptionButtonList(self.options, self.onChange, self.mode);

			height = (self.options.length <= 2) ? 240 : 336;

			bodyContent = {
				rows: [
					{},
					{
						view: "scrollview",
						height: height,
						scroll: "y",
						body: {
							rows: [content.getView()]
						}
					},
					{ height: 10 },
					{},
					getToolbarActions(),
					{ height: 10 }
				]
			};

		} else if (self.mode === OptionDialogBox.MODE_COMBO) {

			content = new ComboDetail(self.options, self.onChange);

			bodyContent = content.getView();
			bodyContent.rows.push(getToolbarActions());
		}

		self.optionWindow = util.ui.loadWindow(self.title, bodyContent, 430, 650, true);


		function getToolbarActions() {

			let viewToolbarActions = {
				id: "toolbar-" + self.uid,
				view: "toolbar",
				height: 45,
				borderless: true,
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
						id: "dialog-accept-" + content.uid,
						view: "button",
						css: "dialog-accept",
						width: 130,
						height: 35,
						label: i18n.t("LBL_ACCEPT"),
						on: {
							onItemClick(id) {

								if (self.mode === OptionDialogBox.MODE_TOGGLE) {

									for (let opt of content.options) {

										let button = $$("button-" + opt.id + "-" + content.uid);

										if (button) {

											let value = button.getValue();

											if (value == 1) {

												let selectionId = button.config.name;

												if (selectionId === oldSelection) {

													let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_SAME_MODE_SELECTED_DESCRIPTION"), null, null);
													ms.show();

												} else {

													let res = self.onChange(selectionId, function(close) {

														console.log(res)

														if (close === undefined || close === true) {
															$$(self.optionWindow).close();
														}
													});
												}
											}
										}
									}

								} else if (self.mode === OptionDialogBox.MODE_COMBO) {

									let selectionId = $$("selected-mode-" + content.uid).getValue();

									if (selectionId === oldSelection) {

										let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_SAME_MODE_SELECTED_DESCRIPTION"), null, null);
										ms.show();

									} else {

										let res = self.onChange(selectionId, function(close) {

											if (res === undefined || res === true) {
												$$(self.optionWindow).close();
											}
										});
									}
								}
							}
						}
					},
					{ width: 15 }
				]
			};

			return viewToolbarActions;
		}
	}

}