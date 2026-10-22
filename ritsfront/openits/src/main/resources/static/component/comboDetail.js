const NON_SELECTED = "nonSelected";

export default class ComboDetail {

	constructor(options, onChange) {

		this.options = options;
		this.onChange = onChange;

		this.uid = webix.uid();

	}


	getView() {

		let self = this;

		let optionsArr = self.options.map(function(item) {
			return {
				id: item.id,
				value: item.label,
				description: item.description,
				disabled: item.disabled,
				default: item.default
			}
		});
		
		optionsArr = optionsArr.filter(e => !e.disabled);

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
				{ height: 15 }
			]
		};


		function getContent() {

			let initialSelection = "";
			let initialDescription = "";
			let initialValues = optionsArr.find(e => (e.default));

			if (initialValues) {

				initialSelection = initialValues.id;
				initialDescription = initialValues.description;
			}

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
							value: initialSelection,
							options: optionsArr,
							on: {
								onChange: function(newv, oldv) {

									let selection = optionsArr.find(e => e.id == newv);

									if (selection && selection.id != NON_SELECTED) {

										$$("description-" + self.uid).setValue(selection.description);

										if ($$("dialog-accept-" + self.uid)) {
											$$("dialog-accept-" + self.uid).enable();
										}

									} else {

										$$("description-" + self.uid).setValue("");

										if ($$("dialog-accept-" + self.uid)) {
											$$("dialog-accept-" + self.uid).disable();
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
							value: initialDescription
						},
						{ width: 40 }
					]
				},
				{ height: 20 }
			];

			return content;
		}

		return body;
	}

}