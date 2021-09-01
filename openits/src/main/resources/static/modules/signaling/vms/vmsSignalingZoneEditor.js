export default class VmsSignalingZoneEditor {
	
	static get TYPE_TEXT() { return "text" }
	static get TYPE_GRAPHIC() { return "graphic" }
	static get TYPE_DUAL() { return "dual" }
	
	static get SINGLE_MODE() { return 1 }
	static get FLASHING_MODE() { return 2 }
	static get ALTERNANCE_MODE() { return 3 }
	
	static get ALIGN_LEFT() { return 1 }
	static get ALIGN_CENTER() { return 2 }
	static get ALIGN_RIGHT() { return 3 }

	constructor(width, height, config, data, onZoneDataChange) {

		if (this.constructor == VmsSignalingZoneEditor) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.width = width;
		this.height = height;
		this.config = config;
		this.data = data;
		this.onZoneDataChange = onZoneDataChange
	}

	getView() {

		throw new Error("Method 'getView()' must be implemented.");
	}
	
	delete() {
	
		let data = {
			zone: this.data.zone,
			texts:  [],
			graphics: []
		};
		
		this.onZoneDataChange(data);
	}
}	
