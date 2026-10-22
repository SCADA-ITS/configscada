export default class WindowManager {

	constructor() {

		this.winIds = new Set();
	}
	
	getView() {
	
		return {
			view: "toolbar",
			id: "toolbar-wins",
			paddingY: 2, height: 40,
			css: "toolbar-bottom",
			cols: [
				
			]
		};
	}
	
	add(winId) {
		
		this.winIds.add(winId);	
	}

	has(winId) {

		return (this.winIds.has(winId));
	}
	
	getAll() {
		
		return this.winIds;
	}
	
	delete(winId) {
		
		this.winIds.delete(winId);
	}
	
	activateWindow(winId) {
		
		$$(winId).show();
	}
}
