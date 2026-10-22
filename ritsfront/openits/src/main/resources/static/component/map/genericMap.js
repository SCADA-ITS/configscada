
export default class GenericMap {
	
	static get MOUSE_EVENT_LEFT_PRESSED() { return 1 }
	static get MOUSE_EVENT_MIDDLE_PRESSED() { return 2 }
	static get MOUSE_EVENT_RIGHT_PRESSED() { return 3 }

	constructor(metadata, data, events, w, h, options) {
		
		if (this.constructor == GenericMap) {

			throw new Error("Abstract classes can't be instantiated.");
		}

		this.metadata = metadata;
		this.data = data;
		this.events = events;
		this.w = w;
		this.h = h;
		this.options = options;
		
		this.uid = webix.uid();
	}

	getView() {

		throw new Error("Method 'getView()' must be implemented.");
	}
} 

Object.defineProperty(GenericMap, 'EventTypes', {
	value: {
	}
});

Object.defineProperty(GenericMap.EventTypes, 'ON_COMMAND_EXEC', {
	value: 1,
	writable: false,
	enumerable: true,
	configurable: false
});