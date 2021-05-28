
export default class GenericMap {

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