import GenericModule from "../modules/genericModule.js";
import { util } from "../lib/util.js";

export default class WelcomeScreenModule extends GenericModule{
	
	constructor(module, moduleAction, args){

		super(module, moduleAction, args);
//        this.module = module;
//		this.args = args;
//		this.uid = webix.uid();
    }

	getView() {

		let view = {
			view: "template",
			template: function(data) {
				
				let logo = "<img src='img/open_its_full.png' align='center'>";

				return `<div class='welcome-screen-inner'>${logo}</div>`;
			}  
		};
			
		return view;
	}
}
