import GenericModule from "../modules/genericModule.js";

export default class EquipmentModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
	}

	loadData() {

		let self = this;
	}
}
