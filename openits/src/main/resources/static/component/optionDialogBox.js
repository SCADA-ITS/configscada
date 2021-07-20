import OptionButtonList from "./optionButtonList.js";
import ComboDetail from "./comboDetail.js";

const NUM_OPTIONS = 3;

export default class OptionDialogBox {

	constructor(title, options, onAcept) {

		this.title = title;
		this.options = options;
		this.onAcept = onAcept;

		this.uid = webix.uid();

	}


	show(oldSelection) {

		let self = this;

		let functionClick = undefined;
		let functionToggle = undefined;

		if ((self.title != undefined) || (self.title != null)) {
	
			functionToggle = self.onAcept;

		} else {
	
			functionClick = self.onAcept;
		}

		if (self.options.length > NUM_OPTIONS) {

			let comboDetail = new ComboDetail(self.title, self.options, functionClick, functionToggle);
			return comboDetail.show(oldSelection);

		} else {

			let optionButtonList = new OptionButtonList(self.title, self.options, functionClick, functionToggle);
			return optionButtonList.show(oldSelection);
		}

	}

}