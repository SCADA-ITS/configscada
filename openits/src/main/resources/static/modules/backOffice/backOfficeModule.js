import GenericModule from "../genericModule.js";
import BackOfficeManager from "./backOfficeManager.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import { util } from "../../lib/util.js";

export default class EquipmentModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
	}

	loadData() {

		let self = this;
		let backOffice = undefined; 
		
		$$("loading-" + self.uid).show();
		
		if (this.moduleAction && this.moduleAction.id === this.config.moduleAction.open) {

			backOffice = self.args.backOffice;
			backOffice.containerId = self.args.containerId; 
		}
		else {
			
			backOffice = {
				
				id: this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_ID).value,
				tableName: this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_TABLE_NAME).value,
				conditions: this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_CONDITIONS).value,
				containerId: self.args.containerId 
			}
		}
		
		if (backOffice.id && backOffice.tableName) {
					
			BackOfficeHelper.getData(backOffice).then((data) => {
				
				let backOfficeManager = new BackOfficeManager(backOffice, data);
					
				$$("loading-" + self.uid).hide();
				webix.ui(backOfficeManager.getView(), $$(self.uid));
				
				let container = $$(backOffice.containerId);

				if (container) {
					
					let currentTableData = data.find(e => e.name === backOffice.tableName);
					
					if (currentTableData) {
						
						util.ui.setTitle(container.config.id, currentTableData.label ?? currentTableData.name);	
					}
				}
			});
		}
	}
}
