import { util } from "../../../lib/util.js";
import GenericModule from "../../genericModule.js";
import MessageBox from "../../../component/messageBox.js";

export default class DaliPlanOrderEditorModulue extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.module = module;
		this.moduleAction = moduleAction;
		this.args = args;
		this.config = module.config;
		this.element = undefined;

	}


	getView() {

		let self = this;
		let viewContent = {};

		viewContent = {
			id: "view-main-content-" + self.uid,
			cols: [
				{
					id: "loading-" + self.uid,
					hidden: false,
					rows: [util.ui.getViewDrawLoading()]
				},
				{
					id: "view-content-" + self.uid,
					hidden: true
				}
			]
		};

		return viewContent;
	}

	loadData() {

		let self = this;

		if ((self.moduleAction && self.moduleAction.id === self.config.moduleAction.commandEdit) && (self.args.selectedPks && self.args.selectedPks.length > 1)) {

			$$(self.args.containerId).close();

			let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_MULTISELECTION_NOT_ALLOWED_IN_DALI_MODULE"), null, null);
			ms.show();

		} else {

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementByIdWithoutChilds, { id: self.args.selectedPks[0] })).then(function(elementResponse) {

				if (elementResponse != null) {

					self.element = elementResponse.json();

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementByIdWithParents, { id: self.element.id })).then(function(parentsResponse) {

						if (parentsResponse != null) {

							let tube = parentsResponse.json();
							console.log(tube)

							webix.ui(drawContent(), $$("view-content-" + self.uid));

							$$("loading-" + self.uid).hide();

							if (self.args.data.commandElement.commandElementValues) {

								update();
							}
						}
					});
				}
			});
		}


		function drawContent() {

			let viewContent;

			viewContent = {
				id: "view-content-" + self.uid,
				rows: [
					getViewHeader(),
					{
						cols: [
							{ width: 20 },
							getViewBody()
						]
					}
				]
			};

			function getViewHeader() {

				let viewHeader;

				viewHeader = {
					rows: [
						{
							cols: [
								{
									view: "label",
									label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-view-headline"></i></div>`,
									align: "center",
									height: 50,
									width: 56
								},
								{
									id: "elements-selected-label" + self.uid,
									view: "label",
									css: "view-header-details-text",
									label: self.element.alias,
									align: "left",
									height: 50
								},
								{}
							]
						}
					]
				};

				return viewHeader;
			}

			function getViewBody() {

				let viewBody;

				viewBody = {
					view: "tabview",
					id: "view-tabs-" + self.uid,
					css: "auxiliary-tab",
					animate: false,
					borderless: true,
					cells: [
						{
							id: "lighting-tab-" + self.uid,
							header: i18n.t("LBL_LIGHTING"),
							body: getBody()
						}
					],
					multiview: {
						keepViews: true
					},
					tabbar: {
						tabMargin: 6
					}
				};


				function getBody() {

					let body;
					let availableRegimes;
					let paramRegimeConfig = self.config.lightingParams.lightingRegimeConfig;
					let regimes = "regimes";
					let regimesConfig = self.element.elementValues.find(elementValue => elementValue.elementTypeParam.id == paramRegimeConfig);

					try {

						regimesConfig = JSON.parse(regimesConfig.value);

						availableRegimes = regimesConfig[regimes].map(function(reg) {
							return {
								id: reg.id.toString(),
								value: i18n.t(reg.alias)
							};
						});

					} catch (error) {

						console.error("El Json de regimenes no esta correctamente formateado.");
					}

					body = {
						id: "lighting-" + self.uid,
						rows: [
							{ height: 20 },
							{
								cols: [
									{ width: 35 },
									{
										id: "label-lighting-" + self.uid,
										view: "label",
										css: "view-header-details-text",
										width: 250,
										label: i18n.t("LBL_REGIME"),
									},
									{
										rows: [
											{
												id: "regime-lighting-" + self.uid,
												view: "richselect",
												width: 300,
												value: availableRegimes[0],
												options: availableRegimes
											}
										]
									},
									{}
								]
							}
						]
					};

					return body;
				}

				return viewBody;
			}

			return viewContent;
		}

		function update() {

			let commandElementValues = self.args.data.commandElement.commandElementValues[0];
			let value = JSON.parse(commandElementValues.value);
			let commandElementType = self.element.commandElements.find(commandElement => commandElement.id === self.args.data.commandElement.commandElementType.id);

			if (commandElementType.id === self.config.lightingParams.commands.regime) {

				$$("regime-lighting-" + self.uid).setValue(value[0]);
			}
		}
	}


	getSignaling() {

		let self = this;
		let commandElement = undefined;
		let commands = self.config.commands.SEND_LIGHTING_REGIME;
		let regimeSelected = $$("regime-lighting-" + self.uid).getValue();

		commandElement = {
			id: commands.commandElementTypeId + ":" + self.element.id.substring(self.element.id.lastIndexOf(':') + 1),
			element: {
				id: self.element.id
			},
			commandElementType: {
				id: commands.commandElementTypeId
			},
			commandElementValues: [
				{
					commandElementTypeParam: {
						id: commands.commandElementValues[0].commandElementTypeParamId
					},
					value: [regimeSelected]
				}
			]
		};

		return commandElement;
	}
}