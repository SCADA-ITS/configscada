import BackOfficeViewGrid from "./backOfficeViewGrid.js";
import BackOfficeViewForm from "./backOfficeViewForm.js";

export default class BackOfficeManager {
	
	constructor(backOffice, data) {

		this.backOffice = backOffice;
		this.data = data;
	}
	
	getView() {
		
		let self = this;
		let view;
		
		let currentTableData = undefined;
		
		if (Array.isArray(self.data)) {
			
			currentTableData = self.data.find(e => e.name === self.backOffice.tableName);
		}
		
		if (currentTableData) {
			
			if (Array.isArray(currentTableData.rows)) {
				
				if (currentTableData.rows.length > 1 || currentTableData.rows.length == 0) {
					
					loadGrid(currentTableData);
				}
				else {
					
					loadForm(currentTableData);
				}
			}
			else {
				
				loadGrid(currentTableData);
			}
		}
		else {
			
			loadGrid(currentTableData);
		} 
		
		function loadGrid(currentTableData) {
			
			let backOfficeView = new BackOfficeViewGrid(self.backOffice, self.data, currentTableData);
			
			view = backOfficeView.getView();
		}
		
		function loadForm(currentTableData) {
			
			let backOfficeView = new BackOfficeViewForm(self.backOffice, self.data, currentTableData);
			
			view = backOfficeView.getView();
		}
		
		return view;
	}
}