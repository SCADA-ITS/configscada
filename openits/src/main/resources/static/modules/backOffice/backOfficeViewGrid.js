import BackOfficeView from "./backOfficeView.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import DataTable from "../../component/datatable.js";

export default class BackOfficeViewGrid extends BackOfficeView {
	
	static get GRID_COMMANDS() { return { refresh: 1, new: 2, edit: 3, delete: 4 } }

	constructor(backOffice, data, currentTableData) {

		super(backOffice, data, currentTableData);
		
		this.dataTable = undefined;
	}
	
	getView() {
		
		let self = this;
		let view = getDataTableView();
		
		function getDataTableView() {

			let viewDataTable;
			
			self.dataTable = getDataTable();
			
			if (self.dataTable) {
				
				viewDataTable = {
					
					id: "backOffice-view-" + self.uid,
					rows: self.dataTable.getView()
				}
				
				viewDataTable.rows.push({
					view: "template",
					height: 1,
					on: {
						onAfterRender: function() {
		
							self.dataTable.callbacks()
						}
					}	
				});	
			}
			
			function getDataTable() {
				
				function getMetadata() {
					
					const defaultGridOptionValues = [
						{ gridOption: { id: 1 }, value: "true" },
	//					{ gridOption: { id: 2 }, value: "true" },
	//					{ gridOption: { id: 3 }, value: "2" },
	//					{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: "true" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "true" },
						{ gridOption: { id: 12 }, value: "true" },
						{ gridOption: { id: 13 }, value: "true" },
						{ gridOption: { id: 14 }, value: "true" },
						{ gridOption: { id: 21 }, value: "auto" },
						{ gridOption: { id: 23 }, value: "true" },
						{ gridOption: { id: 25 }, value: "true" }
					];
		
					let metadata = {
						filter: true,
						label: self.currentTableData.label,
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [],
						gridCommands: []
					};
					
					if (Array.isArray(self.currentTableData.columns)) {
						
						let idxField = 1;
						
						self.currentTableData.columns.sort((a, b) => (a.position > b.position) ? 1 : (( b.position > a.position) ? -1 : 0));
						
						let gridFields = [];
						
						self.currentTableData.columns.forEach(column => {
							
							let filterColumnName = undefined;
							
							if (self.backOffice.conditions) {
								
								filterColumnName = self.backOffice.conditions.trim().split("=")[0].trim();
							}
								
							if (!filterColumnName || column.name != filterColumnName) {
								
								const referencedColumnName = BackOfficeHelper.getReferencedColumnName(column, self.data);
								const sourceField = referencedColumnName ? referencedColumnName.name : column.name;
								
								const dataTypeId = BackOfficeHelper.getDBTypes(referencedColumnName ? referencedColumnName.colType : column.colType).id; 
								const label = referencedColumnName ? (referencedColumnName.label ?? referencedColumnName.name) : (column.label ?? column.name); 
		
								gridFields.push({
									id: idxField,
									extended: false,
									filtered: false,
									isEpochMilli: dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_TIMESTAMP).id,
									isPk: sourceField === 'id',
									needsTranslation: false,
									parameterized: false,
									position: idxField,
									required: true,
									label: label,
									sourceField: sourceField,
									visible: sourceField !== 'id',
									gridFieldDataType: {
										id: dataTypeId
									}
								});
								
								idxField++;
							}
						});
						
						metadata.gridFields = gridFields;
					}
					
					metadata.gridCommands.push(
						{
							id: BackOfficeViewGrid.GRID_COMMANDS.refresh,
							default_: false,
							icon: "mdi mdi-refresh",
							label: "LBL_TABLE_REFRESH",
							multiselect: true,
							position: 1,
							showText: true,
							itemRequired: false
						},
						{
							id: BackOfficeViewGrid.GRID_COMMANDS.new,
							default_: false,
							icon: "mdi mdi-plus",
							label: "LBL_CMD_ADD",
							multiselect: false,
							position: 2,
							showText: false,
							itemRequired: false
						},
						{
							id: BackOfficeViewGrid.GRID_COMMANDS.edit,
							default_: true,
							icon: "mdi mdi-pencil",
							label: "LBL_CMD_EDIT",
							multiselect: false,
							position: 3,
							showText: false,
							itemRequired: true
						},
						{
							id: BackOfficeViewGrid.GRID_COMMANDS.delete,
							default_: false,
							icon: "mdi mdi-delete",
							label: "LBL_CMD_DELETE",
							multiselect: true,
							position: 4,
							showText: false,
							itemRequired: true
						}
					);
					
					return metadata;
				}
				
				function getEvents() {
				
					let events = {};
		
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
		
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push(
						{
							commandId: 1,
							function: () => {					
									
								if ($$("backOffice-view-" + self.uid) && self.dataTable) {
									
									BackOfficeHelper.getData(self.backOffice).then((data) => {
										
										const selectedIds = self.dataTable.getSelectedIds();
										
										self.data = data;
										self.currentTableData = data.find(e => e.name === self.backOffice.tableName);
	
										self.dataTable = getDataTable();
										
										if (self.dataTable) {
										
											let viewDataTable = {
							
												id: "backOffice-view-" + self.uid,
												rows: self.dataTable.getView()
											}
											
											viewDataTable.rows.push({
												view: "template",
												height: 1,
												on: {
													onAfterRender: function() {
									
														self.dataTable.callbacks()
													}
												}	
											});
											
											webix.ui(viewDataTable, $$("backOffice-view-" + self.uid));
											
											self.dataTable.select(selectedIds);	
										}
									});
								}
							}
						}
					);
					
					return events;	
				}
				
				function getData() {
				
					let data = [];
					
					if (Array.isArray(self.currentTableData.rows) && self.currentTableData.rows.length > 0) {
						
						self.currentTableData.rows.forEach(row => {
					
							if (Array.isArray(row.data) && row.data.length > 0) {
							
								let record = {};
								
								row.data.forEach(e => {
								
									const column = self.currentTableData.columns.find(col => col.id === e.columnId);
									
									if (column && column.name) {
										
										let filterColumnName = undefined;
							
										if (self.backOffice.conditions) {
											
											filterColumnName = self.backOffice.conditions.trim().split("=")[0].trim();
										}
											
										if (!filterColumnName || column.name != filterColumnName) {
											
											const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(column, e.value, self.data);
										
											if (referencedColumnValue) {
											
												record[referencedColumnValue.name] = referencedColumnValue.value;	
											}
											else {
												
												record[column.name] = e.value;	
											}	
										}
										
										
									} 
								});
								
								data.push(record);
							}		
						});
					}
					
					return data;	
				}
				
				return new DataTable(null, null, getMetadata(), getData(), getEvents());;
			}

			return viewDataTable;
		}
		
		return view;
	}
	
	refresh() {
		
		let self = this;
		
		if (self.dataTable) {
			
			self.dataTable.executeCommand(BackOfficeViewGrid.GRID_COMMANDS.refresh)
		}
	}
}
