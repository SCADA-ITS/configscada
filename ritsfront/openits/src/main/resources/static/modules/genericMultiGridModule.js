import {util} from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";

const MODULE_TYPE_GENERIC_MULTI_GRID_PARAM_ENTITY_TYPE = 7;

export default class GenericMultiGridModule extends GenericModule {

	constructor(module, moduleAction, args){
		
		super(module, moduleAction, args);
		
		this.dataTable = undefined;
		this.listView = undefined;
		
    }

	getViewDrawLoading() {
		
		return {
			view: "template",
			id: "no-data-" + this.uid,
			template: function(data) {
				
				let icon = "<img src='img/loading.svg' align='center'>";
	
				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
	}

	getView() {
		
		let entity = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_TYPE_GENERIC_MULTI_GRID_PARAM_ENTITY_TYPE).value;	
		let jsonEquipmentTypesWithGridAssigned = window.em.getAll(EntityType[entity]);
		let jsonEquipmentTypesWithGridAssignedCopy = undefined;
		
		jsonEquipmentTypesWithGridAssigned = jsonEquipmentTypesWithGridAssigned.filter(e => e.visible === true && e.grid != undefined && e.grid !== "");
		
		jsonEquipmentTypesWithGridAssignedCopy = webix.copy(jsonEquipmentTypesWithGridAssigned);
			
		jsonEquipmentTypesWithGridAssignedCopy.forEach(item => {
			
			item.labelAlias = i18n.t(item.labelAlias);
			item.alias = item.labelAlias; 
			item.labelAlias = i18n.t(item.labelAlias);
			item.labelDescription = i18n.t(item.labelDescription);
			item.description = item.labelDescription;
		});
		
		jsonEquipmentTypesWithGridAssignedCopy.sort((a, b) => (a.labelAlias > b.labelAlias) ? 1 : ((b.labelAlias > a.labelAlias) ? -1 : 0));

		let view = {
			cols: [
				{ width: 6 },
				{
					header: i18n.t("LBL_" + entity),
					body: {
						width: 350,
						minWidth: 250,
						rows: [
							{ height: 6 },
							this.drawEquipmentTypeList(jsonEquipmentTypesWithGridAssignedCopy),
							{ height: 6 }
						]		
					}
				},
				{ view: "resizer" },
				{
					body: {
						rows: [
							{ height: 6 },
							{
								cols: [
									{ width: 6 },
									{
										id: this.uid,
										rows: [this.getViewDrawLoading()]
									},
									{ width: 6 }
								]
							},
							{ height: 6 }
						]
					}
				}
			]
		}
	
		return view;
	}
	
	loadData() {

		let self = this;
		let listEquipmentTypes = $$("listview-" + self.listView.uid);

		if (!webix.isUndefined(listEquipmentTypes)) {

			listEquipmentTypes.attachEvent("onAfterSelect", function(id) {

				webix.extend($$(self.uid), webix.ProgressBar);

				$$(self.uid).showProgress({
					type: "bottom",
					hide: false
				});

				webix.delay(function() {
					
					self.callbackSelectedElemenType(id[0]);

					//$$(self.uid).hideProgress();

				}, null, null, 1);
			});

			self.listView.select(self.listView.data[0].id);
		}
	}
	
	drawEquipmentTypeList(jsonEquipmentTypesWithGridAssigned) {

		let self = this;
	
		if (jsonEquipmentTypesWithGridAssigned != undefined && jsonEquipmentTypesWithGridAssigned.length > 0) {
			
			let options = {};
					
			options[ListView.Options.MODE] = ListView.DrawMode.DEFAULT;
			
			let events = {};
			
			events[ListView.EventTypes.ON_SELECT_ROW] = function(ids) {

				self.callbackSelectedElemenType(ids);
			};
			
			this.listView = new ListView(jsonEquipmentTypesWithGridAssigned, options, events);
		
		}
		else {
		
			this.listView = this.getViewDrawLoading();
		}
	
		return this.listView.getView();
	}
	
	callbackSelectedElemenType(id) {

		let self = this;
		let item = self.listView.getSelectedItems();
		let dataGridId = item[0].grid;
		let dataGridParams = id;
		let equipmentTable = $$(this.uid);
		webix.extend($$(self.uid), webix.ProgressBar);
		
		if (dataGridId != undefined && dataGridParams != undefined) {
			
			if (self.dataTable) {
				
				webix.ui(self.getViewDrawLoading(), $$(self.dataTable.getViewId()));

				$$(self.uid).showProgress({
					type: "bottom",
					hide: false
				});
				
				let viewsToRemove = [];
				equipmentTable.getChildViews().forEach(view => {
			
					viewsToRemove.push(view.config.id);
				});
				
				viewsToRemove.forEach(viewId => {
	
					if (viewId.indexOf("no-data-") === -1) {
						equipmentTable.removeView(viewId);
					}
				});
			}

			self.listView.enable();
			
			webix.ajax().get(appConfig.restUrl + "grids/" + dataGridId + "/" + dataGridParams).then(function(dat){
				
				if (dat != null) {
	
					let metadata = dat.json().metadata;
					let data = dat.json().data;
		
					self.dataTable = new DataTable(dataGridId, dataGridParams, metadata, data);
					
					let newTable = self.dataTable.getView();
					
					webix.ui(newTable, equipmentTable);
		
					self.dataTable.callbacks();
					
					$$(self.uid).hideProgress();
					
					self.listView.enable(true);
				}
			}, function(response) { util.ajax.handleError(response); });
		}
		else {
			
			$$(self.uid).showProgress({
				type: "bottom",
				hide: false
			});
				
			equipmentTable.addView(self.getViewDrawLoading());
			equipmentTable.addView({ height: 20 });
		}
	}
}
