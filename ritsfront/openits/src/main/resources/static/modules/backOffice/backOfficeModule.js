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
			let filters = [];
			let filtersRequired = false;
			
			const moduleValueId = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_ID);
			
			if (moduleValueId) {
				
				id = moduleValueId.value;
			}
			
			const moduleValueTableName = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_TABLE_NAME);
			
			if (moduleValueTableName) {
				
				tableName = moduleValueTableName.value;
			}
			
			const moduleValueFilterColumnName = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_FILTER_COLUMN_NAME); 
			
			if (moduleValueFilterColumnName) {
				
				filterColumnName = moduleValueFilterColumnName.value;
			}
			
			const moduleValueFilterColumnValue = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_FILTER_COLUMN_VALUE); 
			
			if (moduleValueFilterColumnValue) {
				
				filterColumnValue = moduleValueFilterColumnValue.value;
			}
			
			const moduleValueFilters = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_FILTERS); 
			
			if (moduleValueFilters && moduleValueFilters.value) {
				
				filters = JSON.parse(moduleValueFilters.value);
				
				if (Array.isArray(filters)) {
					
					const userData = util.user.getUserData();
					
					if (userData) {
						
						filters = filters.filter(e => e.userId == userData.id);	
					}
				}
			}
			
			const moduleValueFilterRequired = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_FILTERS_REQUIRED); 
			
			if (moduleValueFilterRequired) {
				
				filtersRequired = IS_TRUE(moduleValueFilterRequired.value);
			}

			backOffice = {
				
				id: id,
				tableName: tableName,
				filterColumnName: filterColumnName,
				filterColumnValue: filterColumnValue,
				filters: filters,
				filtersRequired: filtersRequired,
				moduleId: self.module.id,
				containerId: self.args.containerId 
			}
		}
		
		if (backOffice.id && backOffice.tableName) {
			
			BackOfficeHelper.getBackOfficeData(backOffice).then((backOfficeData) => {
				
				if (backOfficeData) {
					
					backOffice.metadata = {
						
						defaultMetadataBackoffice: JSON.parse(backOfficeData.defaultMetadataBackoffice),
						defaultMetadataTable: JSON.parse(backOfficeData.defaultMetadataTable),
						defaultMetadataColumn: JSON.parse(backOfficeData.defaultMetadataColumn)
					}	
				}
			
				BackOfficeHelper.getData(backOffice).then((data) => {
					
					let backOfficeManager = new BackOfficeManager(backOffice, data);
			
					$$("loading-" + self.uid).hide();
					
					let isCreating = false;
					backOfficeManager.getView(onCreate, onGetView);
					
					function onCreate() {
				
						const container = $$(backOffice.containerId);
							
						if (container) {
							
							isCreating = true;
							
							container.close();
						}
					}
					
					function onGetView(backOfficeManagerView) {
					
						if (backOfficeManagerView && !isCreating) {
						
							webix.ui(backOfficeManagerView, $$(self.uid));
												
							if (self.args.onLoad) {
								
								self.args.onLoad();
							}	
						}		
					}
				});	
			});
		}
	}
}
