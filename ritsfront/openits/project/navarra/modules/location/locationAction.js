import MessageBox from "../../component/messageBox.js";

export default class LocationAction {

	constructor(parent) {

		this.parent = parent.parent;
		this.stepsMap = parent.stepsMap;
	}

	createLocation() {

		let self = this;
		let key = self.parent.config.steps.newLocation.length;
		let body = self.getBody(key);

		let bodyToCreate = {
			location: {
				locationTypeId: "LocationType:1",
				alias: body.location.alias,
				description: body.location.description,
				coordinates: body.location.coordinates,
				stretchLocations: body.location.stretchLocations
			}
		};

		webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.actionEndpoints.add, bodyToCreate).then(function(createLocationResponse) {

			if (createLocationResponse != null) {

				let location = createLocationResponse.json();

				if (self.parent.args && self.parent.args.onChange) {

					self.parent.args.onChange([location]);
				}
			}

			$$(self.parent.args.containerId).close();
		});
	}

	selectLocation() {

		let self = this;
		let data = undefined;
		let key = self.parent.config.steps.selectLocation.length;

		if (self.stepsMap.has(key)) {

			data = self.stepsMap.get(key);

			if (data.locationSelected) {

				let sel = data.locationList.find(loc => loc.id === data.locationSelected);

				if (self.parent.args && self.parent.args.onChange) {

					self.parent.args.onChange(sel);
				}
			}
		}

		$$(self.parent.args.containerId).close();
	}

	editLocation() {

		let self = this;
		let key = self.parent.config.steps.editLocation.length;
		let body = self.getBody(key);

		let bodyToEdit = {
			location: {
				id: body.location.id,
				locationTypeId: "LocationType:2",
				alias: body.location.alias,
				description: body.location.description,
				coordinates: body.location.coordinates,
				stretchLocations: body.location.stretchLocations
			}
		};

		webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + self.parent.config.actionEndpoints.edit, bodyToEdit).then(function(editLocationResponse) {

			if (editLocationResponse != null) {

				let location = editLocationResponse.json();

				if (self.parent.args && self.parent.args.onChange) {

					self.parent.args.onChange([location]);
				}
			}

			$$(self.parent.args.containerId).close();
		});
	}

	deleteLocation() {

		let self = this;
		let data = undefined;
		let key = self.parent.config.steps.deleteLocation.length;

		if (self.stepsMap.has(key)) {

			data = self.stepsMap.get(key);
			
			let locationSelectedId = (data.locationSelected.id) ? data.locationSelected.id : data.locationSelected; 

			webix.ajax().del(appConfig.restUrl + util.fillTemplate(self.parent.config.actionEndpoints.delete, { id: locationSelectedId })).then(function(deleteLocationResponse) {

				if (deleteLocationResponse != null) {

					let del = deleteLocationResponse.json();

					if (self.parent.args && self.parent.args.onChange) {

						self.parent.args.onChange([del]);
					}
				}

				$$(self.parent.args.containerId).close();
			});
		}
	}

	getBody(key) {

		let self = this;
		let data = undefined;
		let formValues = undefined;
		let stretchLocations = [];
		let coordinatesArray = [];

		if (self.stepsMap.has(key)) {

			data = self.stepsMap.get(key);

			getFormValues();
			getFeatures();

			if ($$(data.form.id).validate()) {

				let body = {
					location: {
						id: formValues.nameId,
						locationTypeId: "LocationType:1",
						alias: formValues.name,
						description: formValues.description,
						coordinates: coordinatesArray,
						stretchLocations: stretchLocations
					}
				};

				return body;

			} else {

				let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_REQUIRED_FIELDS_NOT_COMPLETED_DESCRIPTION"), null, null, null);
				ms.show();
			}
		}

		function getFormValues() {

			if (data.form) {

				formValues = $$(data.form.id).getValues();

				let stretchArray = formValues.stretchs.split(",");

				if (Array.isArray(stretchArray) && stretchArray.length > 0) {

					for (let i = 0; i < stretchArray.length && stretchArray[i] != ""; i++) {

						stretchLocations.push({
							stretchId: stretchArray[i],
							position: i + 1
						});
					}
				}
			}
		}

		function getFeatures() {

			if (data.source) {

				let features = data.source.getFeatures();

				if (Array.isArray(features) && features.length > 0) {

					features.forEach(f => {

						let coordinates = ol.proj.transform(f.getGeometry().getCoordinates(), 'EPSG:3857', 'EPSG:4326');

						if (Array.isArray(coordinates) && coordinates.length > 0) {

							coordinatesArray.push({
								lat: coordinates[1],
								lon: coordinates[0]
							});
						}
					});
				}
			}
		}
	}
}