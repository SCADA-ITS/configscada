import GenericModule from "../modules/genericModule.js";
import { util } from "../lib/util.js";

export default class UserModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;
	}

	getView() {

		let view = undefined;

		return view;
	}

	loadData() {

		let self = this;

		if (this.moduleAction.id === this.config.moduleAction.create) {

			add();
		}
		else if (this.moduleAction.id === this.config.moduleAction.update) {

			edit();
		}
		else if (this.moduleAction.id === this.config.moduleAction.delete) {

			remove();
		}
		else if (this.moduleAction.id === this.config.moduleAction.lock) {

			lock();
		}

		function add() {

			util.ui.notImplementedMessage();
		}

		function edit() {
			
			util.ui.notImplementedMessage();
		}

		function remove() {

			util.ui.notImplementedMessage();
		}

		function lock() {

			util.ui.notImplementedMessage();
		}
	}
}	
