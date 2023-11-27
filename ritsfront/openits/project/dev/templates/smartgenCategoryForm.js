import BackOfficeViewCustomForm from "../modules/backOffice/backOfficeViewCustomForm.js";
import BackOfficeViewFormField from "../modules/backOffice/backOfficeViewFormField.js";
import BackOfficeViewFormImage from "../modules/backOffice/backOfficeViewFormImage.js";
import BackOfficeViewFormAttachment from "../modules/backOffice/backOfficeViewFormAttachment.js";
import BackOfficeViewGrid from "../modules/backOffice/backOfficeViewGrid.js";
import BackOfficeHelper from "../modules/backOffice/backOfficeHelper.js";
import MessageBox from "../component/messageBox.js";

import { util } from "../lib/util.js";

export default class SmartgenCategoryForm extends BackOfficeViewCustomForm {

	constructor(backOffice, data, currentTableData) {

		super(backOffice, data, currentTableData);
	}
	
	getCustomViews() {
			
		let self = this;	
		
		let customViews = [];
		
		const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);
		const columnName = self.currentTableData.columns.find(e => e.name === "name");
		
		if (columnId && columnName) {

			let backOfficeViewFormField = new BackOfficeViewFormField(self);
						
			customViews = [
				{ height: 15 },
				{
					cols: [
						{
							rows: [backOfficeViewFormField.getField(columnName, { view: { labelWidth: "100" } })]
						},
						{
							width: 50,
						},
						{
							rows: [backOfficeViewFormField.getField(columnId, { view: { labelWidth: "50" } })]
						}
					]
				},
				{},
			]
		}
		
		return customViews;
	}
}
