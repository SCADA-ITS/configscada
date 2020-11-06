function getViewEquipments(module, instance, args) {

	let moduleInstance = "module-" + module.id + "-" + instance;

	//	let dataEquipmentTypes = webix.ajax().sync().get(appConfig.restUrl + "element_types");

	//	if (dataEquipmentTypes != null && dataEquipmentTypes.responseText != null && dataEquipmentTypes.responseText !== "") {

	//let jsonEquipmentTypes = JSON.parse(dataEquipmentTypes.responseText);
	//let jsonEquipmentTypesWithGridAssigned = jsonEquipmentTypes.filter(elementType => elementType.visible === true && elementType.grid != undefined && elementType.grid !== "");

	let jsonEquipmentTypesWithGridAssigned = window.app.em.getAll(window.app.em.em_types.elementType.id);
	//app.em.getElementTypeState("ElementTypeState:43:2");
	jsonEquipmentTypesWithGridAssigned = jsonEquipmentTypesWithGridAssigned.filter(e => e.visible === true && e.grid != undefined && e.grid !== "");

	let view = {
		//layout: moduleInstance,
		css: "custom_window",
		cols: [
			{ width: 6 },
			{
				rows: [
					{ height: 6 },
					{
						view: "toolbar",
						elements: [
							{ width: 4 },
							{
								view: "search",
								id: "txtSearchEquipmentType-" + moduleInstance,
								placeholder: i18n.t("LBL_EQUIPMENT_TYPE_SEARCH")
							},
							{ width: 4 }
						]
					},
					drawEquipmentTypeList(moduleInstance, jsonEquipmentTypesWithGridAssigned),
					{ height: 6 }
				]
			},
			{ view: "resizer" },
			{
				rows: [
					{ height: 6 },
					{
						cols: [
							{ width: 6 },
							{
								id: moduleInstance,
								//css: "equipment-table",
								//rows: app.datatable.draw(moduleInstance + "-" + metadata.id + "-" + dataGridParams, metadata, data)
								//rows: app.datatable.draw(moduleInstance, metadata, data)
								//rows: drawNoEquipmentData()
								rows: [drawNoEquipmentTypeData()]
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
	//	}
}

function drawEquipmentTypeList(moduleInstance, jsonEquipmentTypesWithGridAssigned) {

	let equipmentTypeList;

	if (jsonEquipmentTypesWithGridAssigned != undefined && jsonEquipmentTypesWithGridAssigned.length > 0) {
		equipmentTypeList = {
			view: "list",
			id: "listEquipmentType-" + moduleInstance,
			width: 350,
			minWidth: 250,
			select: true,
			type: {
				/*template:obj => `<image class="list-photo" src="img/{obj.elementTypePhoto}.png" />
					<div class="text">
				  		<span class="list-first-line">${obj.alias} </span>
				  		<span class="list-second-line">$${obj.description}</span>
					</div>`,*/

				template: function(obj) {

					let elementTypePhoto = obj.elementTypePhoto;

					if (elementTypePhoto == undefined) {
						elementTypePhoto = "not_found_50.png";
					}
					return `<image class="list-photo" src="img/${elementTypePhoto}" />
							<div class="text">
						  		<span class="list-first-line">${i18n.t(obj.labelAlias)} </span>
						  		<span class="list-second-line">${i18n.t(obj.labelDescription)} </span>
							</div>`
				},
				height: 66
			},
			data: jsonEquipmentTypesWithGridAssigned
		};
	}
	else {
		equipmentTypeList = drawNoEquipmentTypeData();
	}

	return equipmentTypeList;
}

function assingEquipmentCallbacks(module, instance, args) {

	let moduleInstance = "module-" + module.id + "-" + instance;

	//app.datatable.callbacks(module, instance);

	let listEquipmentTypes = $$("listEquipmentType-" + moduleInstance);
	let txtSearchEquipmentTypes = $$("txtSearchEquipmentType-" + moduleInstance);

	if (!webix.isUndefined(listEquipmentTypes) && !webix.isUndefined(txtSearchEquipmentTypes)) {

		txtSearchEquipmentTypes.attachEvent("onTimedKeyPress", function() {
			let value = this.getValue().toLowerCase(); // input data is derived
			listEquipmentTypes.filter(function(obj) {  // here it filters all titles from the list

				return (obj.alias.toLowerCase().indexOf(value) !== -1);
			});
		});

		listEquipmentTypes.attachEvent("onAfterSelect", function(id) {

			var list = this;
			
			webix.extend($$(moduleInstance), webix.ProgressBar);

			$$(moduleInstance).showProgress({
				type: "bottom",
				hide: false
			});
				
			webix.delay(function() {
				
				callbackSelectedElemenType(list, id, module, instance);

				//$$(moduleInstance).hideProgress();	
			}, null, null, 1);
		});

		listEquipmentTypes.select(listEquipmentTypes.getFirstId());
	}
}

function callbackSelectedElemenType(list, id, module, instance) {

	let moduleInstance = "module-" + module.id + "-" + instance;
	let item = list.getItem(id);
	let dataGridId = item.grid;
	let dataGridParams = id;
	let equipmentTable = $$(moduleInstance);

	let viewsToRemove = [];
	equipmentTable.getChildViews().forEach(view => {

		viewsToRemove.push(view.config.id);
	});

	if (dataGridId != undefined && dataGridParams != undefined) {

		//let dataEquipments = webix.ajax().sync().get(appConfig.restUrl + "grids/" + dataGridId + "/" + dataGridParams);
		
		webix.ajax().get(appConfig.restUrl + "grids/" + dataGridId + "/" + dataGridParams).then(function(dat){
			
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
				let newTable = app.datatable.draw(dataGridId, dataGridParams, moduleInstance, metadata, data);
	
				newTable.forEach(newView => {
	
					equipmentTable.addView(newView);
				});
	
				app.datatable.callbacks(module, instance);
				
				$$(moduleInstance).hideProgress();
			}
		});

//		if (dataEquipments != null && dataEquipments.responseText != null) {
//
//			let json = JSON.parse(dataEquipments.responseText);
//			let metadata = json.metadata;
//			let data = json.data;
//
//			//let newTable = app.datatable.draw(moduleInstance + "-" + metadata.id + "-" + id, metadata, data);
//			let newTable = app.datatable.draw(dataGridId, dataGridParams, moduleInstance, metadata, data);
//
//			newTable.forEach(newView => {
//
//				equipmentTable.addView(newView);
//			});
//
//			app.datatable.callbacks(module, instance);
//		}
	}
	else {
		equipmentTable.addView(drawNoEquipmentTypeData());
		equipmentTable.addView({ height: 20 });
	}
}

function drawNoEquipmentTypeData() {

	return {
		view: "template",
		template: function(data) {
			let icon = "<img src='img/not_found_150.png' align='center'>";

			return "<div class='empty-app-inner'>" + icon + "</div>"

		}
	}
}