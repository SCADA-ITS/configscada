import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";

export default class EquipmentModule extends GenericModule {

	constructor(module, moduleAction, args){
		
		super(module, moduleAction, args);
		
		this.drawNoEquipmentTypeData = {
			view: "template",
			template: function(data) {
				
				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
    }

	getView() {
		
		let jsonEquipmentTypesWithGridAssigned = window.em.getAll(EntityType.ELEMENT_TYPE.id);
		
		jsonEquipmentTypesWithGridAssigned = jsonEquipmentTypesWithGridAssigned.filter(e => e.visible === true && e.grid != undefined && e.grid !== "");

		let view = {
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
									id: "txtSearchEquipmentType-" + this.uid,
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
									id: this.uid,
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

		let self = this;
		let listEquipmentTypes = $$("listEquipmentType-" + this.uid );
		let txtSearchEquipmentTypes = $$("txtSearchEquipmentType-" + this.uid);
	
		if (!webix.isUndefined(listEquipmentTypes) && !webix.isUndefined(txtSearchEquipmentTypes)) {
	
			txtSearchEquipmentTypes.attachEvent("onTimedKeyPress", function() {
				let value = this.getValue().toLowerCase(); // input data is derived
				listEquipmentTypes.filter(function(obj) {  // here it filters all titles from the list
	
					return (obj.alias.toLowerCase().indexOf(value) !== -1);
				});
			});
	
			listEquipmentTypes.attachEvent("onAfterSelect", function(id) {
	
				var list = this;
				
				webix.extend($$(self.uid), webix.ProgressBar);
	
				$$(self.uid).showProgress({
					type: "bottom",
					hide: false
				});
					
				webix.delay(function() {
					
					self.callbackSelectedElemenType(list, id);
					
					$$(self.uid).hideProgress();
	
				}, null, null, 1);
			});
	
			listEquipmentTypes.select(listEquipmentTypes.getFirstId());
			
			listEquipmentTypes.refresh();
		}
	}
	
	drawEquipmentTypeList(jsonEquipmentTypesWithGridAssigned) {

		let equipmentTypeList;
	
		if (jsonEquipmentTypesWithGridAssigned != undefined && jsonEquipmentTypesWithGridAssigned.length > 0) {
			equipmentTypeList = {
				view: "list",
				id: "listEquipmentType-" + this.uid,
				width: 350,
				minWidth: 250,
				select: true,
				type: {
	
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
		let equipmentTable = $$(this.uid);
	
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
		
					let dataTable = new DataTable(dataGridId, dataGridParams, metadata, data);
					
					let newTable = dataTable.getView();
		
					newTable.forEach(newView => {
		
						equipmentTable.addView(newView);
					});
		
					//app.datatable.callbacks(self.module, self.instance);
					dataTable.callbacks();
					
					$$(this.uid).hideProgress();
				}
			});
		}
		else {
			equipmentTable.addView(self.drawNoEquipmentTypeData);
			equipmentTable.addView({ height: 20 });
		}
	}
}
