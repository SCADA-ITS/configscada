import DataTable from "../component/datatable.js";

export default class EquipmentGrid {

	constructor(module, instance, args){

        this.module = module;
		this.instance = instance;
		this.args = args;
		this.moduleInstance = "module-" + this.module.id + "-" + this.instance;

        // for using the object inside callbacks
        self = this;
    }

	getView() {
		
		let jsonEquipmentTypesWithGridAssigned = window.app.em.getAll(window.app.em.em_types.elementType.id);
		
		jsonEquipmentTypesWithGridAssigned = jsonEquipmentTypesWithGridAssigned.filter(e => e.visible === true && e.grid != undefined && e.grid !== "");

		let view = {
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
									id: "txtSearchEquipmentType-" + this.moduleInstance,
									placeholder: i18n.t("LBL_EQUIPMENT_TYPE_SEARCH")
								},
								{ width: 4 }
							]
						},
						this.drawEquipmentTypeList(jsonEquipmentTypesWithGridAssigned),
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
									id: this.moduleInstance,
									rows: [this.drawNoEquipmentTypeData]
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

		let listEquipmentTypes = $$("listEquipmentType-" + self.moduleInstance);
		let txtSearchEquipmentTypes = $$("txtSearchEquipmentType-" + self.moduleInstance);
	
		if (!webix.isUndefined(listEquipmentTypes) && !webix.isUndefined(txtSearchEquipmentTypes)) {
	
			txtSearchEquipmentTypes.attachEvent("onTimedKeyPress", function() {
				let value = this.getValue().toLowerCase(); // input data is derived
				listEquipmentTypes.filter(function(obj) {  // here it filters all titles from the list
	
					return (obj.alias.toLowerCase().indexOf(value) !== -1);
				});
			});
	
			listEquipmentTypes.attachEvent("onAfterSelect", function(id) {
	
				var list = this;
				
				webix.extend($$(self.moduleInstance), webix.ProgressBar);
	
				$$(self.moduleInstance).showProgress({
					type: "bottom",
					hide: false
				});
					
				webix.delay(function() {
					
					self.callbackSelectedElemenType(list, id);
	
					//$$(moduleInstance).hideProgress();	
				}, null, null, 1);
			});
	
			listEquipmentTypes.select(listEquipmentTypes.getFirstId());
		}
	}
	
	drawEquipmentTypeList(jsonEquipmentTypesWithGridAssigned) {

		let equipmentTypeList;
	
		if (jsonEquipmentTypesWithGridAssigned != undefined && jsonEquipmentTypesWithGridAssigned.length > 0) {
			equipmentTypeList = {
				view: "list",
				id: "listEquipmentType-" + this.moduleInstance,
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
			equipmentTypeList = this.drawNoEquipmentTypeData();
		}
	
		return equipmentTypeList;
	}
	
	callbackSelectedElemenType(list, id) {

		let item = list.getItem(id);
		let dataGridId = item.grid;
		let dataGridParams = id;
		let equipmentTable = $$(self.moduleInstance);
	
		let viewsToRemove = [];
		equipmentTable.getChildViews().forEach(view => {
	
			viewsToRemove.push(view.config.id);
		});
	
		if (dataGridId != undefined && dataGridParams != undefined) {
	
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
		
					//let newTable = app.datatable.draw(dataGridId, dataGridParams, self.moduleInstance, metadata, data);
					let dataTable = new DataTable(dataGridId, dataGridParams, metadata, data);
					
					let newTable = dataTable.getView();
		
					newTable.forEach(newView => {
		
						equipmentTable.addView(newView);
					});
		
					//app.datatable.callbacks(self.module, self.instance);
					dataTable.callbacks();
					
					$$(self.moduleInstance).hideProgress();
				}
			});
		}
		else {
			equipmentTable.addView(self.drawNoEquipmentTypeData);
			equipmentTable.addView({ height: 20 });
		}
	}
	
	drawNoEquipmentTypeData = {
		view: "template",
		template: function(data) {
			let icon = "<img src='img/not_found_150.png' align='center'>";

			return "<div class='empty-app-inner'>" + icon + "</div>"
		}
	}
}







