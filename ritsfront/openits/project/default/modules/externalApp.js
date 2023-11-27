import GenericModule from "../modules/genericModule.js";
import { util } from "../lib/util.js";

export default class ExternalApp extends GenericModule{
	
	constructor(module, moduleAction, args){

		super(module, moduleAction, args);
//        this.module = module;
//		this.args = args;
//		this.uid = webix.uid();
    }

	getView() {

		let view = {
			view:"iframe", 
			src: util.module.getParamValue(this.module, PARAM_MODULE_TYPE_GENERIC_EXTERNAL_APP_WINDOW_URL) 
		};
			
		return view;
	}
}
