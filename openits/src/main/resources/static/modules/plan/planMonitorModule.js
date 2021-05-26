import GenericModule from "../genericModule.js";
import DataTable from "../../component/datatable.js";

const MODULE_TYPE_GENERIC_PARAM_GRID_ID = 3;

export default class GenericGridModule extends GenericModule {
	
	constructor(module, moduleAction, args){

		super(module, moduleAction, args);        

		this.drawNoData = {
			view: "template",
			template: function(data) {
				
				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
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
		let genericGridContainer = $$(this.uid);
		let viewsToRemove = [];
		genericGridContainer.getChildViews().forEach(view => {
	
			viewsToRemove.push(view.config.id);
		});
		
		//let dataGridId = window.app.module.getParamValue(this.module, MODULE_TYPE_GENERIC_PARAM_GRID_ID);
		let dataGridId = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_TYPE_GENERIC_PARAM_GRID_ID).value;	
		
		webix.ajax().get(appConfig.restUrl + "grids/" + dataGridId).then(function(dat){
				
			if (dat != null) {
	
				let metadata = dat.json().metadata;
				let data = dat.json().data;

				if(data && self.args && self.args.selectedPks){

					for (let index = data.length-1; index >= 0; index--) {
						
						let exist = false;

						for (var key in data[index]) {

							if(key.endsWith(".comandElementElementId")){

								if (self.args.selectedPks.includes(data[index][key]) ){

									exist=true;
								}
							}
						}

						if(!exist){
							data.splice(index,1);
						}
					}
				}

				let dataTable = new DataTable(dataGridId, null, metadata, data);
				let newTable = dataTable.getView();
	
				newTable.forEach(newView => {
	
					genericGridContainer.addView(newView);
				});
	
				
				dataTable.callbacks();
				
				viewsToRemove.forEach(viewId => {
	
					if (viewId.indexOf("dt-") !== -1) {
						let ctx = viewId.replace("dt-", "ctx_menu-");
			
						if (!webix.isUndefined($$(ctx))) {
			
							$$(ctx).destructor();
						}
					}
			
					genericGridContainer.removeView(viewId);
				});
				
				$$(self.uid).hideProgress();
			}
		}, function(response) { util.ajax.handleError(response); });
	}
}
