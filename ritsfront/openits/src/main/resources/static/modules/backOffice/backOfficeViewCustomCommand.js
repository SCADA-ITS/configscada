import BackOfficeHelper from "./backOfficeHelper.js";
import MessageBox from "../../component/messageBox.js";

import { util } from "../../lib/util.js";

export default class BackOfficeViewCustomCommand {

	constructor(backOffice, data, currentTableData, requestInput, cmd, onAccept) {

		this.backOffice = backOffice;
		this.data = data;
		this.currentTableData = currentTableData;
		this.requestInput = requestInput;
		this.cmd = cmd;
		this.onAccept = onAccept;
		
		this.formHeight = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_HEIGHT, backOffice.metadata, currentTableData);
		this.formWidth = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_WIDTH, backOffice.metadata, currentTableData);
		
		this.containerId = undefined;
		this.formId = webix.uid();
		
		if (this.constructor == BackOfficeViewCustomCommand) {

			throw new Error("Abstract classes can't be instantiated.");
		}
	}
	
	getView() {
		
		let self = this;
		let view;
		
		view = {
			css: { "box-sizing": "border-box" },
			rows: [
				getHeader(),
				{ height: 15 },
				{
					cols: [
						{ width: 15 },
						{
							id: self.formId,
							view: "form",
							css: "bordered-layout-with-border-top",
							elementsConfig: { labelAlign: "left" },
							elements: function() {
								
								let elements = [];
								
								elements.push({});
								elements.push(self.getCustomView());
								elements.push({});
								
								return elements;
							}()
						},
						{ width: 15 }
					]
				},
				{ height: 15 },
				getToolbarActions(),
				{ height: 15 }
			]
		}
		
		function getHeader() {
			
			let viewHeader;
			
			viewHeader = {
				height: 65,
				cols: [
					{
						rows: [
							{},
							{
								height: 56,
								borderless: true,
								template: function() {
									
									const icon = (self.currentTableData.mdiIcon) ? self.currentTableData.mdiIcon : "mdi mdi-application";
									const description = self.currentTableData.labelDescription ?? "";
									
									let windowTitle = i18n.t("LBL_CMD_PARAMS"); 
									
									let res =  `<span style='font-size: 40px; padding-left: 14px' class='${icon}'></span>
												<div class="backoffice-header" id="equipDescription" style="padding-left: 10px !important; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; display: inline-block" title="${description ?? i18n.t("LBL_NO_DESCRIPTION")}">
													${windowTitle}
												</div>`;
												
									return res;
	
								}
							},
							{}
						]
					}
				]
			}
			
			return viewHeader;
		}
		
		function getToolbarActions() {
			
			let viewToolbarActions;
			
			const cancelButtonView = {
				view: "button",
				value: i18n.t("LBL_CANCEL"),
				width: 130,
				height: 41,
				css: "dialog-cancel",
				click: function() {
					
					if (self.containerId) {
						
						$$(self.containerId).close();
					}
				}
			}
			
			const acceptButtonView = {
				view: "button",
				value: i18n.t("LBL_ACCEPT"),
				width: 130,
				height: 41,
				css: "dialog-accept",
				click: function() {
	
					let validation = $$(self.formId).validate({ disabled: true });
	
					if (validation && self.onAccept) {
						
						if (self.cmd.requireConfirmation) {
						
							(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_CONFIRM_COMMAND") + " " + (self.cmd.label ?? self.cmd.name) + "?", null, acceptCmd, null)).show();	
						}
						else {
							
							acceptCmd();
						}
					}
					
					function acceptCmd() {
						
						if (self.containerId) {
							
							self.onAccept(self.getRequestParams());
							
							$$(self.containerId).close();	
						}
					} 
				}
			}
			
			viewToolbarActions = {
				borderless: true,
				css: { "margin-top": "1px !important" },
				rows: [
					{
						cols: [
							{ width: 15 },
							cancelButtonView,
							{},
							acceptButtonView,
							{ width: 15 }
						]
					}
				]
			}
			
			return viewToolbarActions;
		}
		
		return view;
	}
	
	getCustomView() {
		
		throw new Error("Method 'getCustomViews()' must be implemented.");
	}
	
	getRequestParams() {
		
		throw new Error("Method 'getRequestParams()' must be implemented.");
	}
}
