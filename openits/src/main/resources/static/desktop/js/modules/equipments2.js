function getViewEquipments(module, instance, args) {

	var dataGridId = "2";
	var dataGridParams = "ElementType:3";
	var moduleInstance = "module-" + module.id + "-" + instance;
	
	var dataEquipments = webix.ajax().sync().get(appConfig.restUrl + "grid/"+ dataGridId + "/" + dataGridParams);
	var dataTypeEquipments = webix.ajax().sync().get(appConfig.restUrl + "element_type/list");

	if (dataEquipments != null && dataEquipments.responseText != null &&
	    dataTypeEquipments != null && dataTypeEquipments.responseText != null) {
		
		var jsonTypeEquipments = JSON.parse(dataTypeEquipments.responseText);

		var json = JSON.parse(dataEquipments.responseText);
		var metadata = json.metadata;
		var data = json.data;

		return {
			//layout: moduleInstance,
			css: "custom_window",
			cols: [
				{ width:8 },
				{
					css: "equipment-list",
					rows: [
						{
							view:"toolbar",
							elements:[
								{ width:4 },
								{ view:"search", 
									id: "txtSearchEquipmentType-" + moduleInstance, 
									placeholder:i18n.t("LBL_EQUIPMENT_TYPE_SEARCH") },
								{ width:4 }
							]
						},
						{
							view: "list",
							id: "listEquipmentType-" + moduleInstance,
							width: 350,
							minWidth:250,
							select:true,
							type:{
								/*template:obj => `<image class="list-photo" src="img/{obj.elementTypePhoto}.png" />
									<div class="text">
								  		<span class="list-first-line">${obj.alias} </span>
								  		<span class="list-second-line">$${obj.description}</span>
									</div>`,*/
									
								template: function (obj) { 
								
									var elementTypePhoto = obj.elementTypePhoto;
								
									if (elementTypePhoto == undefined) {
										elementTypePhoto = "not_found_50.png";
									}
									return `<image class="list-photo" src="img/${elementTypePhoto}" />
											<div class="text">
										  		<span class="list-first-line">${i18n.t(obj.labelAlias)} </span>
										  		<span class="list-second-line">${i18n.t(obj.labelDescription)} </span>
											</div>`
									},
								height:66
							},
							data: jsonTypeEquipments
						},
						{ height:16 }
					]
				},
				{view:"resizer"},
				/*{ width:8 },*/
				{
					id: moduleInstance,
					css: "equipment-table",
					//rows: app.datatable.draw(moduleInstance + "-" + metadata.id + "-" + dataGridParams, metadata, data)
					rows: app.datatable.draw(moduleInstance, metadata, data)
				},
				{ width:12 }
			]
		}
	}
}

function assingEquipmentCallbacks(module, instance) {

	var moduleInstance = "module-" + module.id + "-" + instance;	
	app.datatable.callbacks(module, instance);

	var listEquipmentTypes = $$("listEquipmentType-" + moduleInstance);
	var txtSearchEquipmentTypes = $$("txtSearchEquipmentType-" + moduleInstance);
	
	if (!webix.isUndefined(listEquipmentTypes) && !webix.isUndefined(txtSearchEquipmentTypes)) {

		txtSearchEquipmentTypes.attachEvent("onTimedKeyPress", function() {
			var value = this.getValue().toLowerCase(); // input data is derived
			listEquipmentTypes.filter(function(obj) {  // here it filters all titles from the list
				
				return (obj.alias.toLowerCase().indexOf(value) !== -1);
			});
		});
		
		listEquipmentTypes.attachEvent("onAfterSelect", function(id){
			
			callbackSelectedElemenType(this, id, module, instance);
		});
	}
}

function callbackSelectedElemenType(list, id, module, instance) {
	
	var moduleInstance = "module-" + module.id + "-" + instance;
	var item = list.getItem(id);
	var dataGridId = item.grid;
	var dataGridParams = id;
	var equipmentTable = $$(moduleInstance);
	
	var viewsToRemove = [];
	equipmentTable.getChildViews().forEach(view => {

		viewsToRemove.push(view.config.id);
	});
	
	viewsToRemove.forEach(viewId => {

		if (viewId.indexOf("dt-") !== -1) {
			var ctx = viewId.replace("dt-", "ctx_menu-");
			$$(ctx).destructor();  
		}
		
		equipmentTable.removeView(viewId);
	});
	
	if (dataGridId != undefined && dataGridParams != undefined) {
	
		var dataEquipments = webix.ajax().sync().get(appConfig.restUrl + "grid/"+ dataGridId + "/" + dataGridParams);
			
		if (dataEquipments != null && dataEquipments.responseText != null) {

			var json = JSON.parse(dataEquipments.responseText);
			var metadata = json.metadata;
			var data = json.data;
			
			//var newTable = app.datatable.draw(moduleInstance + "-" + metadata.id + "-" + id, metadata, data);
			var newTable = app.datatable.draw(moduleInstance, metadata, data);
			
			newTable.forEach(newView => {

				equipmentTable.addView(newView);
			});
			
			app.datatable.callbacks(module, instance);
		}
	}
	else {
		equipmentTable.addView({
			view: "template",
			template: function(data) {
				var icon = "<img src='img/not_found_150.png' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "</div>"
					
			} 
		});
		equipmentTable.addView({ height: 20 });
	}
}
