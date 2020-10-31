const MODULE_TYPE_GENERIC_PARAM_GRID_ID = 3;

function getGenericGridView(module, instance, args) {

	let moduleInstance = "module-" + module.id + "-" + instance;
	let view;
	
	if (module.moduleType.id == MODULE_TYPE_GENERIC_GRID_WINDOW) {
	
		view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: moduleInstance,
									rows: [drawNoData()]
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		}	
	}

	return view;
}

function assingGenericGridCallbacks(module, instance, args) {

	let moduleInstance = "module-" + module.id + "-" + instance;
	let equipmentTable = $$(moduleInstance);
	let viewsToRemove = [];
	equipmentTable.getChildViews().forEach(view => {

		viewsToRemove.push(view.config.id);
	});
	
	let dataGridId = app.module.getParamValue(module, MODULE_TYPE_GENERIC_PARAM_GRID_ID);
	
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
			let newTable = app.datatable.draw(dataGridId, null, moduleInstance, metadata, data);

			newTable.forEach(newView => {

				equipmentTable.addView(newView);
			});

			app.datatable.callbacks(module, instance);
			
			$$(moduleInstance).hideProgress();
		}
	});
}

function drawNoData() {

	return {
		view: "template",
		template: function(data) {
			let icon = "<img src='img/not_found_150.png' align='center'>";

			return "<div class='empty-app-inner'>" + icon + "</div>"

		}
	}
}