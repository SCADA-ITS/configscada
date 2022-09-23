import { util } from "../../../lib/util.js";
import GenericModule from "../../genericModule.js";
import MessageBox from "../../../component/messageBox.js";

const TAB_OPTION = {
	VENTILATION: "ventilation",
	LIGHTING: "lighting"
};

const RADIO_OPTION_MODE = 1;

export default class TubePlanOrderEditorModule extends GenericModule {

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
		let viewMainContent = {};

		viewMainContent = {
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
				}]
		};

		return viewMainContent;
	}


	loadData() {

		let self = this;
		let tubeParents = [];
		let modeOptions = undefined;

		if ((self.moduleAction && self.moduleAction.id === self.config.moduleAction.commandEdit) && (self.args.selectedPks && self.args.selectedPks.length > 1)) {

			$$(self.args.containerId).close();

			let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_MULTISELECTION_NOT_ALLOWED_IN_TUBE_MODULE"), null, null);
			ms.show();

		} else {

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementByIdWithoutChilds, { id: self.args.selectedPks[0] })).then(function(elementResponse) {

				if (elementResponse != null) {

					self.element = elementResponse.json();

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementByIdWithParents, { id: self.element.id })).then(function(parentsResponse) {

						if (parentsResponse != null) {

							let tube = parentsResponse.json();

							if (Array.isArray(tube.parents) && tube.parents.length > 0) {

								for (let parent of tube.parents) {

									modeOptions = (parent.elementType && parent.elementType.arg_1) ? JSON.parse(parent.elementType.arg_1) : self.config;

									if (parent.elementType.id === self.config.elementTypeVentilation) {

										tubeParents.push({
											id: TAB_OPTION.VENTILATION,
											label: "LBL_VENTILATION_MANAGER"
										});
									}

									if (parent.elementType.id === self.config.elementTypeLighting) {

										tubeParents.push({
											id: TAB_OPTION.LIGHTING,
											label: "LBL_LIGHTING_MANAGER"
										});
									}
								}
							}

							webix.ui(drawContent(), $$("view-content-" + self.uid));

							$$("loading-" + self.uid).hide();

							if (self.args.data.commandElement.commandElementValues) {

								update();
							}
						}
					});
				}
			});

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
						cells: [],
						multiview: {
							keepViews: true
						},
						tabbar: {
							tabMargin: 6
						}
					};

					for (let tube of tubeParents) {

						viewBody.cells.push({
							id: tube.id,
							header: i18n.t(tube.label),
							body: getBody(tube.id)
						});
					}

					function getBody(option) {

						let body;
						let availableRegimes;
						let optionsArr = [];
						let configModeOptions = (option === TAB_OPTION.VENTILATION) ? modeOptions.ventilationModeOptions : modeOptions.lightingModeOptions;
						let paramRegimeConfig = (option === TAB_OPTION.VENTILATION) ? self.config.ventilationParams.ventilationRegimesConfig : self.config.lightingParams.lightingRegimeConfig;
						let regimes = (option === TAB_OPTION.VENTILATION) ? "ventilationRegimes" : "regimes";
						let id = (option === TAB_OPTION.VENTILATION) ? TAB_OPTION.VENTILATION : TAB_OPTION.LIGHTING;

						configModeOptions.forEach(op => {

							optionsArr.push({
								id: op.id,
								value: i18n.t(op.label)
							});
						});

						let regimesConfig = self.element.elementValues.find(elementValue => elementValue.elementTypeParam.id == paramRegimeConfig);

						try {

							regimesConfig = JSON.parse(regimesConfig.value);

							availableRegimes = regimesConfig[regimes].map(function(reg) {
								return {
									id: reg.id.toString(),
									value: i18n.t(reg.labelAlias)
								};
							});

						} catch (error) {

							console.error("El Json de regimenes no esta correctamente formateado.");
						}

						body = {
							id: id + "-" + self.uid,
							rows: [
								{ height: 20 },
								{
									cols: [
										{ width: 35 },
										{
											id: "radio-" + id + "-" + self.uid,
											view: "radio",
											vertical: true,
											width: 250,
											height: 100,
											css: "tube-order-edit-radio-options",
											value: RADIO_OPTION_MODE,
											options: [
												{ id: 1, value: i18n.t("LBL_MODE") },
												{ id: 2, value: i18n.t("LBL_REGIME") }
											],
											on: {
												onChange: function(obj) {
													console.log(obj, $$("view-tabs-" + self.uid).getValue())

													let tabSelectedId = $$("view-tabs-" + self.uid).getValue();

													if (obj == RADIO_OPTION_MODE) {

														$$("mode-" + tabSelectedId).enable()
														$$("regime-" + tabSelectedId).disable();

													} else {

														$$("mode-" + tabSelectedId).disable();
														$$("regime-" + tabSelectedId).enable();
													}
												},
												onAfterRender: function(obj) {
													let tabSelectedId = $$("view-tabs-" + self.uid).getValue();
													console.log(tabSelectedId)
												}
											}
										},
										{
											rows: [
												{
													id: "mode-" + id + "-" + self.uid,
													view: "richselect",
													width: 300,
													value: optionsArr[0],
													options: optionsArr
												},
												{
													height: 10
												},
												{
													id: "regime-" + id + "-" + self.uid,
													view: "richselect",
													width: 300,
													value: availableRegimes[0],
													options: availableRegimes,
													disabled: true
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

				if (commandElementType.id === self.config.ventilationParams.commands.mode) {

					$$("view-tabs-" + self.uid).setValue(TAB_OPTION.VENTILATION + "-" + self.uid);
					$$("radio-" + TAB_OPTION.VENTILATION + "-" + self.uid).setValue(1);
					$$("mode-" + TAB_OPTION.VENTILATION + "-" + self.uid).setValue(value[0]);

				} else if (commandElementType.id === self.config.ventilationParams.commands.regime) {

					$$("view-tabs-" + self.uid).setValue(TAB_OPTION.VENTILATION + "-" + self.uid);
					$$("radio-" + TAB_OPTION.VENTILATION + "-" + self.uid).setValue(2);
					$$("regime-" + TAB_OPTION.VENTILATION + "-" + self.uid).setValue(value[0]);

				} else if (commandElementType.id === self.config.lightingParams.commands.mode) {

					$$("view-tabs-" + self.uid).setValue(TAB_OPTION.LIGHTING + "-" + self.uid);
					$$("radio-" + TAB_OPTION.LIGHTING + "-" + self.uid).setValue(1);
					$$("mode-" + TAB_OPTION.LIGHTING + "-" + self.uid).setValue(value[0]);

				} else {

					$$("view-tabs-" + self.uid).setValue(TAB_OPTION.LIGHTING + "-" + self.uid);
					$$("radio-" + TAB_OPTION.LIGHTING + "-" + self.uid).setValue(2);
					$$("regime-" + TAB_OPTION.LIGHTING + "-" + self.uid).setValue(value[0]);
				}
			}
		}
	}

	getSignaling() {

		let self = this;
		let commandElement = undefined;
		let commands = undefined;
		let tabSelectedValue = $$("view-tabs-" + self.uid).getValue();
		let tabSelected = tabSelectedValue.split("-");

		let tabOption = (tabSelected[0] === TAB_OPTION.VENTILATION) ? TAB_OPTION.VENTILATION : TAB_OPTION.LIGHTING;
		let radioButtonsValue = (tabOption === TAB_OPTION.VENTILATION) ? $$("radio-" + TAB_OPTION.VENTILATION + "-" + self.uid).getValue() : $$("radio-" + TAB_OPTION.LIGHTING + "-" + self.uid).getValue();
		let radioOptionSelected = (radioButtonsValue == RADIO_OPTION_MODE) ? $$("mode-" + tabOption + "-" + self.uid).getValue() : $$("regime-" + tabOption + "-" + self.uid).getValue();

		if (tabOption === TAB_OPTION.VENTILATION) {

			commands = (radioButtonsValue == RADIO_OPTION_MODE) ? self.config.commands.SEND_VENTILATION_MODE : self.config.commands.SEND_VENTILATION_REGIME;

		} else {

			commands = (radioButtonsValue == RADIO_OPTION_MODE) ? self.config.commands.SEND_LIGHTING_MODE : self.config.commands.SEND_LIGHTING_REGIME;
		}

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
					value: [radioOptionSelected]
				}
			]
		};

		return commandElement;
	}
}