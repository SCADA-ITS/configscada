import BackOfficeViewGenericForm from "./backOfficeViewGenericForm.js";
import BackOfficeViewFormField from "./backOfficeViewFormField.js";
import BackOfficeViewFormImage from "./backOfficeViewFormImage.js";
import BackOfficeViewFormAttachment from "./backOfficeViewFormAttachment.js";
import BackOfficeViewGrid from "./backOfficeViewGrid.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import MessageBox from "../../component/messageBox.js";

import { util } from "../../lib/util.js";

export default class BackOfficeViewCustomForm extends BackOfficeViewGenericForm {

	constructor(backOffice, data, currentTableData) {

		super(backOffice, data, currentTableData);
	}
	
	getView() {
		
		let self = this;
		
		return super.getView(function() {
			
			let view;
			
			view = {
				rows: [
					{ height: 15 },
					{
						id: self.formId,
						view: "form",
						css: "bordered-layout-with-border-top",
						elementsConfig: { labelAlign: "left" },
						elements: function() {
							
							let elements = [];
							
							if ((Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) ||
								self.currentTableData.metadata && self.currentTableData.metadata.autorefresh && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH_FORM.name] && self.currentTableData.metadata.autorefresh > 0) {
								
								let actionButtons = {
									cols: [
										self.getActionButtons()
									]
								}
									
								elements.push(actionButtons);
							}
							
							const customViews = self.getCustomViews();
							
							if (Array.isArray(customViews) && customViews.length > 0) {
								
								elements.push(...customViews);	
							}
							
							return elements;
						}(),
					},
					{ height: 15 },
					self.getToolbarActions()
				]
			}

			return view;
		})
	}
	
	getCustomViews() {
		
		throw new Error("Method 'getCustomViews()' must be implemented.");
	}
	
}
