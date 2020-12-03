import DataTable from "../component/datatable.js";

const MODULE_TYPE_GENERIC_PARAM_GRID_ID = 3;

export default class GenericGrid {
	
	constructor(module, instance, args){

        this.module = module;
		this.instance = instance;
		this.args = args;
		this.moduleInstance = "module-" + this.module.id + "-" + this.instance;

        // for using the object inside callbacks
        self = this;
    }

	getView() {

		let	view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.moduleInstance,
									rows: [this.drawNoData]
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		}	
	
		return view;
	}
	
	loadData() {

		let equipmentTable = $$(this.moduleInstance);
		let viewsToRemove = [];
		equipmentTable.getChildViews().forEach(view => {
	
			viewsToRemove.push(view.config.id);
		});
		
		let dataGridId = window.app.module.getParamValue(this.module, MODULE_TYPE_GENERIC_PARAM_GRID_ID);
		
		webix.ajax().get(appConfig.restUrl + "grids/" + dataGridId).then(function(dat){
				
			if (dat != null) {
				
				viewsToRemove.forEach(viewId => {
	
					if (viewId.indexOf("dt-") !== -1) {
						let ctx = viewId.replace("dt-", "ctx_menu-");
			
						if (!webix.isUndefined($$(ctx))) {
			
							$$(ctx).destructor();
						}
					}
			
					equipmentTable.removeView(viewId);
				});
	
	
				let metadata = dat.json().metadata;
				let data = dat.json().data;
	
				//let newTable = app.datatable.draw(moduleInstance + "-" + metadata.id + "-" + id, metadata, data);
				//let newTable = window.app.datatable.draw(dataGridId, null, self.moduleInstance, metadata, data);
				
				let dataTable = new DataTable(dataGridId, null, metadata, data);
				
				let newTable = dataTable.getView();
	
				newTable.forEach(newView => {
	
					equipmentTable.addView(newView);
				});
	
				//window.app.datatable.callbacks(self.module, self.instance);
				
				dataTable.callbacks();
				
				$$(self.moduleInstance).hideProgress();
			}
		});
	}
	
	drawNoData = {
	
		view: "template",
		template: function(data) {
			let icon = "<img src='img/not_found_150.png' align='center'>";

			return "<div class='empty-app-inner'>" + icon + "</div>"

		}
	}
}
