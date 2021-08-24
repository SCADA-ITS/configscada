export default class OptionButtonList {

	static get MODE_TOGGLE() { return 1 };
	static get MODE_BUTTONS() { return 2 };

	constructor(options, onChange, mode) {

		this.options = options;
		this.onChange = onChange;
		this.mode = mode;

		this.uid = webix.uid();

		this.allIconsIncluded = undefined;

	}


	getView() {

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
							//css: { "border": "1px solid #dadee0 !important", "box-sizing": "border-box" },
							rows: getContent()
						},
						{ width: 25 }
					]
				},
				{ height: 15 }
			]
		};


		function getContent() {

			let content = [];
			let iconsArray = [];

			for (let opt of self.options) {

				if (opt.icon != undefined) {

					iconsArray.push(opt.icon);

				}
			}

			if (iconsArray.length === self.options.length) {

				self.allIconsIncluded = true;

			} else if (iconsArray.length === 0) {

				self.allIconsIncluded = false;
			}


			for (let option of self.options) {

				let contentDetail = {
					rows: [
						{ height: 15 },
						{
							css: "option-button-list-actions",
							cols: [
								getButtons(option),
								{
									rows: [
										{
											id: "text" + option.id + "-" + self.uid,
											view: "label",
											css: { "padding-left": "10px", "height": "25px" },
											label: `<span>${option.label}</span>`
										},
										{
											id: "desc-" + option.id + "-" + self.uid,
											height: 35,
											scroll: "auto",
											css: { "padding-left": "10px !important", "border": "none" },
											template: `<span style="font-size: 13px !important; line-height: 15px">${option.description}</span>`
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
		}


		function getButtons(option) {
			
			let buttonType = {}
			let icon = undefined;

			if (self.allIconsIncluded) {

				icon = option.icon;

			} else if (self.allIconsIncluded === false) {

				icon = (option.default) ? "mdi mdi-checkbox-marked-outline" : "mdi mdi-checkbox-blank-outline";

			} else {

				icon = "mdi mdi-checkbox-blank-circle";
			}

			if (self.mode === OptionButtonList.MODE_BUTTONS) {

				buttonType = {
					id: "button-" + option.id + "-" + self.uid,
					view: "button",
					width: 75,
					height: 75,
					type: "icon",
					icon: icon,
					css: "toggle-option-list",
					on: {
						onItemClick() {

							self.onChange(option.id);
						}
					}
				}

			} else {

				buttonType = {
					id: "button-" + option.id + "-" + self.uid,
					view: "toggle",
					width: 75,
					height: 75,
					type: "icon",
					icon: icon,
					value: (option.default) ? 1 : 0,
					name: option.id,
					css: "toggle-option-list",
					on: {
						onItemClick(obj) {

							for (let opt of self.options) {

								$$("button-" + opt.id + "-" + self.uid).setValue(0);

								if (self.allIconsIncluded === false ) {
									
									$$("button-" + opt.id + "-" + self.uid).config.icon = "mdi mdi-checkbox-blank-outline";
									$$("button-" + opt.id + "-" + self.uid).refresh();
								}

							}

							$$(obj).setValue(1);

							if (self.allIconsIncluded === false) {
							
								$$(obj).config.icon = "mdi mdi-checkbox-marked-outline";
								$$(obj).refresh();
							}
						}
					}
				}
			}

			return buttonType;
		}

		return body;
	}

}