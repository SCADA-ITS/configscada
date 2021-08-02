import { util } from "../lib/util.js";

export default class SoundPlayerDialogBox {

	constructor(audio) {

		this.audio = audio;
		
		this.uid = webix.uid();
	}

	show() {

		let self = this;

		self.optionWindow = util.ui.loadWindow( i18n.t("LBL_SOUND_PLAYER"), getBody(), 80, 300, false);

		function getBody() {

			let body = {
				css: "dialog-general-custom",
				rows: [
					{
						view: "template",
						template: `<audio controls autoplay>
								<source src="${self.audio}" type="audio/mpeg">
					  		</audio>`
					}
				]
			};

			return body;
		}

	}

}

