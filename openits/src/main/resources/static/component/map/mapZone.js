import ListView from "../listview.js";

export default class MapZone {

	constructor(mapZones, onZonesSelectedChange) {

		this.uid = webix.uid();
		this.mapZones = mapZones;

		this.ListView = undefined;
		this.listZones = undefined;

		this.onZonesSelectedChange = onZonesSelectedChange;
	}

	getView() {

		let self = this;
		let view;
		let zones = [];

		for (let mapZone of self.mapZones) {

			mapZone.alias = i18n.t(mapZone.label);
			zones.push(mapZone);
		}

		view = {
			id: "map-zone-" + self.uid,
			rows: [getListZones()]
		};

		function getListZones() {

			let events = {};

			events[ListView.EventTypes.ON_SELECT_ROW] = onSelectItem;

			self.listZones = new ListView(zones, null, events);

			function onSelectItem(item) {

				let zoneSelected = zones.find(e => e.id == item);

				if (zoneSelected) {
					let zoneSelectedData = [
						{
							id: zoneSelected.id,
							lon: zoneSelected.longitude,
							lat: zoneSelected.latitude,
							zoom: zoneSelected.zoom
						}
					];

					self.onZonesSelectedChange(zoneSelectedData);
				}
			}

			return self.listZones.getView();
		}

		return view;
	}

	show() {

		let mapZone = $$("map-zone-" + this.uid);

		if (mapZone) {

			mapZone.show();
		}
	}
}