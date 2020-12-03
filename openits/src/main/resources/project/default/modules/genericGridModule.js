import DataTable from "../component/datatable.js";

const MODULE_TYPE_GENERIC_PARAM_GRID_ID = 3;

export default class GenericGridModule {
	
	constructor(module, args){

        this.module = module;
		this.args = args;
		this.uid = webix.uid();
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
									id: this.uid,
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

		let self = this;
		let equipmentTable = $$(this.uid);
		let viewsToRemove = [];
		equipmentTable.getChildViews().forEach(view => {
	
			viewsToRemove.push(view.config.id);
		});
		
		//let dataGridId = window.app.module.getParamValue(this.module, MODULE_TYPE_GENERIC_PARAM_GRID_ID);
		let dataGridId = module.moduleValues.find(e => e.moduleParam.id === MODULE_TYPE_GENERIC_PARAM_GRID_ID).value;	
		
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
				let dataTable = new DataTable(dataGridId, null, metadata, data);
				let newTable = dataTable.getView();
	
				newTable.forEach(newView => {
	
					equipmentTable.addView(newView);
				});
	
				
				dataTable.callbacks();
				
				$$(self.uid).hideProgress();
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
