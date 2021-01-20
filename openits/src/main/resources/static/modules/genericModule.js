export default class GenericModule {

	constructor(module, moduleAction, args){
		
		if (this.constructor == GenericModule) {

			throw new Error("Abstract classes can't be instantiated.");
	    }

        this.module = module;
		this.moduleAction = moduleAction;
		this.args = args;
		this.uid = webix.uid();
    }

	getView() {
		
		throw new Error("Method 'getView()' must be implemented.");
	}
	
	loadData() {
		
		throw new Error("Method 'loadData()' must be implemented.");
	}

	runOne(method) {
        return this[method]();
    }

}
