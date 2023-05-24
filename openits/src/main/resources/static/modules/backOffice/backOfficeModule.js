import GenericModule from "../genericModule.js";
import BackOfficeManager from "./backOfficeManager.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import { util } from "../../lib/util.js";

export default class BackOfficeModule extends GenericModule {
	
	static get Config() { 
		return {
			DEFAULT_FORM_WIDTH: 900,
			DEFAULT_FORM_HEIGHT: 700,
			MODULE_ID: 1044,
			MODULE_ACTION_OPEN_ID: 104401
		}
	}

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
	}

	loadData() {

		let self = this;
		let backOffice = undefined; 
		
		$$("loading-" + self.uid).show();
		
		if (self.moduleAction && self.moduleAction.id === BackOfficeModule.Config.MODULE_ACTION_OPEN_ID) {

			backOffice = self.args.backOffice;
			backOffice.containerId = self.args.containerId; 
		}
		else {
			
			let id = undefined;
			let tableName = undefined;
			let filterColumnName = undefined;
			let filterColumnValue = undefined;
			
			let moduleValueId = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_ID);
			
			if (moduleValueId) {
				
				id = moduleValueId.value;
			}
			
			let moduleValueTableName = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_TABLE_NAME);
			
			if (moduleValueTableName) {
				
				tableName = moduleValueTableName.value;
			}
			
			let moduleValueFilterColumnName = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_FILTER_COLUMN_NAME); 
			
			if (moduleValueFilterColumnName) {
				
				filterColumnName = moduleValueFilterColumnName.value;
			}
			
			let moduleValueFilterColumnValue = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_FILTER_COLUMN_VALUE); 
			
			if (moduleValueFilterColumnValue) {
				
				filterColumnValue = moduleValueFilterColumnValue.value;
			}

			backOffice = {
				
				id: id,
				tableName: tableName,
				filterColumnName: filterColumnName,
				filterColumnValue: filterColumnValue,
				containerId: self.args.containerId 
			}
		}
		
		if (backOffice.id && backOffice.tableName) {
					
			BackOfficeHelper.getData(backOffice).then((data) => {
				
				let backOfficeManager = new BackOfficeManager(backOffice, data);
		
				$$("loading-" + self.uid).hide();
				
				let isCreating = false;
				let backOfficeManagerView = backOfficeManager.getView(onCreate);
				
				if (backOfficeManagerView && !isCreating) {
					
					webix.ui(backOfficeManager.getView(), $$(self.uid));
										
					if (self.args.onLoad) {
						
						self.args.onLoad();
					}	
				}
				
				function onCreate() {
			
					const container = $$(backOffice.containerId);
						
					if (container) {
						
						isCreating = true;
						
						container.close();
					}
				}
			});
		}
	}
}
