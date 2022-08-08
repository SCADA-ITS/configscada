export default class IncidentReportTaskDetailSignallingSpathInfo {

	constructor(signallingSpath, commandElement, currentStretchIds, window_width, spaths) {

		this.signallingSpath = signallingSpath;
		this.commandElement = commandElement;
		this.currentStretchIds = currentStretchIds;
		this.window_width = window_width;
		this.spaths = spaths;
	}

	getView() {

		let self = this;

		const margin_width = 42;
		const margin_height = 42;
		const first_column_width = 194;
		const label_height = 20;
		const row_height = 24;

		const details = [
			{ name: i18n.t("LBL_ALIAS"), value: self.signallingSpath.alias },
			{ name: i18n.t("LBL_COMMAND"), value: i18n.t(self.commandElement.labelAlias) },
			{ name: i18n.t("LBL_ELEMENT_TYPE"), value: getElementTypeLabel() },
			{ name: i18n.t("LBL_ELEMENT_SUBTYPE"), value: getElementSubtypeLabel() },
			{ name: i18n.t("LBL_SIGNALLING_SPATH_FROM_TO"), value: getFromToLabel() },
		    { name: i18n.t("LBL_SIGNALLING_SPATH"), value: getSpathAlias() },
			{ name: i18n.t("LBL_STRETCHS"), value: getStretchsLabel() },
			{ name: i18n.t("LBL_EQUIPMENTS"), value: getElements() }
		]

		let rowInfo = {
			css: "alarm-borders-top",
			cols: [
				{ width: margin_width },
				{
					width: first_column_width,
					rows: function() {

						let rows = [];

						rows.push({ height: margin_height });

						rows.push({});

						details.forEach(detail => {

							rows.push({
								view: "label",
								label: detail.name,
								align: "left",
								height: label_height
							});

							rows.push({ height: row_height });
						});

						rows.push({});

						rows.push({ height: margin_height });

						return rows;
					}()
				},
				{
					width: self.window_width - first_column_width - (3 * margin_width),
					rows: function() {

						let rows = [];

						rows.push({ height: margin_height });

						rows.push({});

						details.forEach(detail => {

							rows.push({
								view: "label",
								label: `<span class="form-text-font">${detail.value}</span>`,
								align: "left",
								tooltip: detail.value,
								height: label_height
							});

							rows.push({ height: row_height });
						});

						rows.push({});

						rows.push({ height: margin_height });

						return rows;
					}()
				}
			]
		}

		let viewMain = {
			cols: [
				{
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
									view: "label",
									css: "alarm-view-header-details-text",
									label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_DETAILS_DESCRIPTION")}</span>`,
									align: "left",
									height: 50
								},
								{}
							]
						},
						rowInfo
					]
				}
			]
		}

		function getStretchsLabel() {

			let stretchsLabel = self.currentStretchIds.map(currentStretchId => {

				let stretch = window.em.getByField(EntityType.STRETCH, "id", currentStretchId);

				return stretch.alias;
			});

			return stretchsLabel.join(",");
		}

		function getElementTypeLabel() {

			let elementTypeLabel = "";

			if (Array.isArray(self.signallingSpath.elements) && self.signallingSpath.elements.length > 0) {

				let element = self.signallingSpath.elements[0];

				elementTypeLabel = i18n.t(element.elementType.labelAlias);
			}

			return elementTypeLabel;
		}

		function getElementSubtypeLabel() {

			let elementSubtypeLabel = "";

			if (Array.isArray(self.signallingSpath.elements) && self.signallingSpath.elements.length > 0) {

				let element = self.signallingSpath.elements[0];

				if (element.elementSubtype) {

					elementSubtypeLabel = i18n.t(element.elementSubtype.labelAlias);
				}
			}

			return elementSubtypeLabel;
		}

		function getFromToLabel() {

			let fromToLabel = "-";

			if (self.signallingSpath.from !== undefined && self.signallingSpath.to !== undefined) {

				let from = !isNaN(self.signallingSpath.from) ? self.signallingSpath.from : -999999;
				let to = !isNaN(self.signallingSpath.to) ? self.signallingSpath.to : 999999;

				fromToLabel = i18n.t("LBL_SIGNALLING_SPATH_FROM " + from + " LBL_SIGNALLING_SPATH_TO " + to);
			}

			return fromToLabel;
		}


		function getElements() {

			let elements = "";

			if (Array.isArray(self.signallingSpath.elements) && self.signallingSpath.elements.length > 0) {

				elements = self.signallingSpath.elements.map(element => element.alias);

				elements = elements.join(",");
			}

			return elements;
		}


		function getSpathAlias() {

			let spathAlias = "-";

			if (self.signallingSpath.spathId) {

				if (self.spaths && Array.isArray(self.spaths) && self.spaths.length > 0) {

					let spath = self.spaths.find(e => e.id === self.signallingSpath.spathId);
					
					if(spath){
						
						spathAlias = spath.alias;
					}
				}
			}

			return spathAlias;
		}
		return viewMain;
	}
}