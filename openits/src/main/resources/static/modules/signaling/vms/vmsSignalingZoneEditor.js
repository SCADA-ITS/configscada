export default class VmsSignalingZoneEditor {
	
	static get TYPE_TEXT() { return "text" }
	static get TYPE_GRAPHIC() { return "graphic" }
	static get TYPE_DUAL() { return "dual" }
	
	static get SINGLE_MODE() { return 1 }
	static get FLASHING_MODE() { return 2 }
	static get ALTERNANCE_MODE() { return 3 }

	constructor(config, data, onZoneDataChange) {

		if (this.constructor == VmsSignalingZoneEditor) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.config = config;
		this.data = data;
		this.onZoneDataChange = onZoneDataChange
	}

	getView() {

		throw new Error("Method 'getView()' must be implemented.");
	}
}	
