import DataTable from "../component/datatable.js";
import DialogEntitiesRepository from "../managers/dialogEntitiesRepository.js";
import { util } from "../lib/util.js";

export default class SearchDialogBox {

	constructor(title, dialogEntity, onAccept) {

		this.uid = webix.uid();
		this.title = title;
		this.dialogEntity = dialogEntity;
		this.onAccept = onAccept;
		this.dialogInfo = undefined;
		this.searchDataTable = undefined;
		this.searchTreeView = undefined;
		this.containerId = undefined;
	}

	show() {

		let self = this;

		let body = {
			id: "search-dialog-" + this.uid,
			cols: [
				{ width: 20 },
				{
					rows: [
						{ height: 20 },
						getSearchComponent(),
						{ height: 20 },
						getToolbarActions(),
						{ height: 10 },			
					]
				},	
				{ width: 20 },
			]
		};
		
		self.containerId = util.ui.loadWindow(self.title, body, self.dialogEntity.height, self.dialogEntity.width, true);
		
		// Post draw views actions
		if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE && self.searchDataTable) {
		
			self.searchDataTable.callbacks();
		}
		
		function getSearchComponent() {
			
			self.dialogInfo = (new DialogEntitiesRepository()).get(self.dialogEntity);
			
			if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE) {
			
				return {
					
					cols: [
						{
							rows: getDataTable()
						}
					]  
				};
			}	
			else if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TREE) {
			
				return getTreeView();
			}
			
			function getDataTable() {
				
				self.searchDataTable = new DataTable(null, null, self.dialogInfo.metadata, self.dialogInfo.data, null);
				
				return self.searchDataTable.getView();
			}
			
			function getTreeView() {
				
				return {};
			}
		}
		
		function getToolbarActions() {
		
			let viewToolbarActions = {};
			
			viewToolbarActions = {
				view: "toolbar",
				height: 50,
				borderless: true,
				elements: [
					{ 
						view: "button",
						css: "dialog-cancel", 
						width: 150, 
						label: i18n.t("LBL_CANCEL"),
						on: {
							onItemClick() {
							
								$$(self.containerId).close();
							}
						}
					},
					{},
					{ 
						view: "button", 
						css: "dialog-accept",
						width: 150, 
						label: i18n.t("LBL_ACCEPT"),
						on: {
							onItemClick() {
							
								if (self.onAccept && self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE && self.searchDataTable) {
									
									self.onAccept(self.searchDataTable.getSelectedIds());
								}
								
								$$(self.containerId).close();
							}
						}
					}
				]
			};
			
			return viewToolbarActions;	
		}
	}
}
