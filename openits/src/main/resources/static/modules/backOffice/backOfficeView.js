export default class BackOfficeView {

	constructor(backOffice, data, currentTableData) {

		if (this.constructor == BackOfficeView) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.backOffice = backOffice;
		this.data = data;
		this.currentTableData = currentTableData;
		this.uid = webix.uid();
	}
	
	getView() {

		throw new Error("Method 'getView()' must be implemented.");
	}
	
	refresh() {
		
		throw new Error("Method 'refresh()' must be implemented.");
	}
}