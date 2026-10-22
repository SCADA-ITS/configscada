import BackOfficeHelper from "./backOfficeHelper.js";
import MessageBox from "../../component/messageBox.js";
import InputDialogBox from "../../component/inputDialogBox.js";
import DataView from "../../component/dataView.js";
import { util } from "../../lib/util.js";

const REST_BACKOFFICE_DOWNLOAD_ATTACHMENT = "backoffice/${backOfficeId}/attachments/other/${tableName}/${attachmentId}";
const REST_BACKOFFICE_UPLOAD_ATTACHMENT = "backoffice/${backOfficeId}/attachments/other/upload/${tableName}/${id}/${groupName}/${position}";
const REST_BACKOFFICE_DELETE_ATTACHMENT = "backoffice/${backOfficeId}/attachments/other/delete/${tableName}/${attachmentId}";
const REST_BACKOFFICE_EDIT_ATTACHMENT = "backoffice/${backOfficeId}/attachments/other/edit/${tableName}";

export default class BackOfficeViewFormAttachment {

	constructor(backOffice, groupAttachment, currentTableData, rowId, attachments, onChange) {

		this.backOffice = backOffice;
		this.groupAttachment = groupAttachment;
		this.tableName = currentTableData.name;
		this.currentTableData = currentTableData;
		this.rowId = rowId;
		
		this.attachments = attachments;
		this.onChange = onChange;
		this.uid = webix.uid();
		
		this.dataViewFiles = undefined;	
		this.currentAttachment = undefined;
	}
	
	getView() {
		
		let self = this;
		let view;
		
		if (Array.isArray(self.attachments) && self.attachments.length > 0) {
			
			self.attachments.sort((a, b) => (a.fileName > b.fileName) ? 1 : ((b.fileName > a.fileName) ? -1 : 0));
			
			self.currentAttachment = self.attachments[0]; 
		}
		
		view = {
			rows: [
				{ height: 10 },
				{
					cols: [
						getViewTitle(),
						{ width: 5 },
						{
							rows: [
								{
									css: "backoffice-image-box",
									cols: [
										{ width: 10 },
										{
											rows: [
												getViewFiles(),
												{ height: 2 },
												getViewDescription(),
												{ height: 5 },
												getViewActions(),
												{ height: 5 }		
											]
										},
										{ width: 5 }
									]	
								},
								{ height: 10 }
							]
						},
						{ width: 2 }
					]
				},
				{ height: 5 },
				{
					view: "template",
					height: 1,
					borderless: true,
					on: {

						onAfterRender: function() {

							if (self.dataViewFiles && self.currentAttachment) {
								
								self.dataViewFiles.select([self.currentAttachment.id]);
							}
						}
					}
				}
			]
		}
		
		function getViewTitle() {
			
			let viewTitle;
			
			viewTitle = {
				width: 275,
				rows: [
					{},
					{
						view: "label",
						label: util.getStringCapitalized(BackOfficeHelper.translate(self.backOffice, self.groupAttachment.label) ?? i18n.t("LBL_ATTACHMENT_GROUP"))
					},
					{
						view: "textarea",
						css: "backoffice-textarea",
						height: 100,
						readonly: true,
						hidden: !self.groupAttachment.description,
						value: BackOfficeHelper.translate(self.backOffice, self.groupAttachment.description)
					},
					{}
				]
				
			}
			
			return viewTitle;
		}
		
		function getViewFiles() {
			
			let viewFilesGroup;
			let viewFiles;
			
			viewFiles = {
				
				id: "files-" + self.uid,
				height: self.groupAttachment.height ?? 110,
				hidden: (Array.isArray(self.attachments) && self.attachments.length === 0) || !self.attachments,
				rows: [
					{ height: 10 },
					getDataView()
				]
			}
			
			viewFilesGroup = {
				id: "files-group-" + self.uid,
				cols: [
					viewFiles,
					{
						height: self.groupAttachment.height ? self.groupAttachment.height + 50 : 160,
						hidden: Array.isArray(self.attachments) && self.attachments.length > 0,
						borderless: true,
						rows: [
							util.ui.getViewNoData(true),		
						]
					}
				]
			}
			
			function getDataView() {
				
				function getMetadata() {
						
					let metadata;
					
					metadata = {
					
						options: [
							{ id: DataView.Options.ICON_SIZE, value: 48 },
							{ id: DataView.Options.SEARCH, value: false },
							{ id: DataView.Options.GROUP, value: false },
							{ id: DataView.Options.SORTED, value: true },
							{ id: DataView.Options.CMD_BUTTONS, value: false }
						],
						commands: [
							{
								default: true,
								hidden: true,
								value: "LBL_DOWNLOAD",
								icon: "mdi mdi-download",
								label: "LBL_DOWNLOAD",
								showText: true,
								position: 1,
								itemRequired: true,
								callback: downloadFile
							}
						]
					}
					
					function downloadFile(id) {
			
						if (Array.isArray(self.attachments)) {
							
							let attachment = self.attachments.find(e => e.id == id);
							
							if (attachment) {
								
								const url = appConfig.restUrl + 
									util.fillTemplate(REST_BACKOFFICE_DOWNLOAD_ATTACHMENT, { 
										backOfficeId: self.backOffice.id, 
										tableName: self.tableName, 
										attachmentId: attachment.id 
									});
									
								let elem = document.createElement('a');
								elem.href = url;
								elem.download = url.substr(url.lastIndexOf('/') + 1);
								document.body.appendChild(elem);
								elem.click();
								document.body.removeChild(elem);
							}
						}
					}
					
					return metadata;
				}
				
				function getData() {
						
					let data = [];
				
					if (Array.isArray(self.attachments) && self.attachments.length > 0) {
						
						data = self.attachments.map(e => { 
						
							return {
								id: e.id,
								image: e.fileName.indexOf('.') > -1 ? "./img/extensions/" + e.fileName.split('.').pop() + ".png" : undefined,
								imageDefault: "./img/extensions/default.png", 
								value: e.fileName
							};
						});
						
						data.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));
					}
					
					return data;
				}
				
				function getEvents() {
						
					let events = {};
			
					events[DataView.EventTypes.ON_SELECT_ITEM] = onSelectFile;
					
					function onSelectFile(item) {
	
						self.currentAttachment = self.attachments.find(e => e.id == item.id);
						
						refresh();
					}
					
					return events;
				}
				
				self.dataViewFiles = new DataView(getMetadata(), getData(), getEvents());
				
				return self.dataViewFiles.getView();
			}
			
			return viewFilesGroup;
		}
		
		function getViewDescription() {
			
			let viewDescription;
			let description = "";
			
			if (Array.isArray(self.attachments) && self.attachments.length > 0) {
				
				description = self.attachments[0].description;
			}
			
			viewDescription = {
				id: "attachment-description-" + self.uid,
				view: "textarea",
				css: "backoffice-textarea",
				hidden: !self.currentAttachment,
				height: 50,
				readonly: true,
				placeholder: i18n.t("LBL_BACK_OFFICE_ATTACHMENT_WO_DESCRIPTION"),
				value: description,
				scroll: true
			}
			
			return viewDescription;
		}
		
		function getViewActions() {
			
			let viewActions;
			
			viewActions = {
				css: { "margin-left": "2px !important" },
				height: 45,
				cols: [
					{},
					getUploader(),
					getButtonEdit(),
					getButtonDelete(),
					getButtonPreview(),
					{}
				]
			}
			
			function getUploader() {
				
				let viewUploader;

				viewUploader = {
					
					view: "uploader",
					id: "uploader-" + self.uid,
					type: "icon",
					css: "toolbar-button-text",
					width: 130,
					label: i18n.t("LBL_CMD_ADD"),
					tooltip: i18n.t("LBL_CMD_ADD"),
					icon: "mdi mdi-plus",
					autosend: true,
					multiple: false,
					on: {
						onBeforeFileAdd: function(item) {

							let res = true;							
							if (Array.isArray(self.attachments) && self.attachments.length > 0) {
								
								if (self.attachments.find(e => e.fileName === item.name)) {
									
									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ATTACHMENT_ALREADY_EXISTS") + " " + item.name, null, null, null)).show();
									
									res = false;
								}
							}
							
							if (res) {
								
								this.config.upload = getUrl();	
							}
							
							return res;
						},
						onFileUpload: function(item) {
							
							const filesGroup = $$("files-group-" + self.uid);
							
							if (Array.isArray(self.attachments) && filesGroup) {
								
								let newAttachment = {
									description: item.description,
									fileName: item.fileName,
									groupName: item.groupName,
									id: item.id,
									idValue: item.idValue,
									position: item.position,
									tableId: item.tableId
								} 
								
								self.attachments.push(newAttachment);
								
								webix.ui(getViewFiles(), filesGroup);
								
								self.dataViewFiles.select([newAttachment.id]);
								
								self.currentAttachment = newAttachment;
								
								self.onChange();
								
								refresh();
							}
						},
						onFileUploadError: function(item) {
							
							log.debug(item);
						}
					}
				}
				
				function getUrl() {
					
					let nextPosition = 1;
				
					if (Array.isArray(self.attachments) && self.attachments.length > 0) {
						
						nextPosition = self.attachments[self.attachments.length - 1].position + 1;
					}
					
					return appConfig.restUrl + 
							  util.fillTemplate(REST_BACKOFFICE_UPLOAD_ATTACHMENT, { 
									backOfficeId: self.backOffice.id, 
									tableName: self.tableName, 
									id: self.rowId,
									groupName: self.groupAttachment.name,
									position: nextPosition
							});
				}
				
				return viewUploader;
			}
			
			function getButtonEdit() {
				
				let viewButtonEdit;
				
				viewButtonEdit = {
					id: "btn-edit-" + self.uid,
					view: "button",
					type: "icon",
					css: "toolbar-button-text",
					width: 130,
					label: i18n.t("LBL_CMD_EDIT"),
					disabled: !self.currentAttachment,
					tooltip: i18n.t("LBL_CMD_EDIT"),
					icon: "mdi mdi-pencil",
					on: {
						onItemClick: editDescription
					}
				}
				
				function editDescription() {
					
					(new InputDialogBox (i18n.t("LBL_BACKOFFICE_ATTACHMENT_EDIT_DESCRIPTION"), i18n.t("LBL_BACKOFFICE_ATTACHMENT_EDIT_DESCRIPTION"), onAccept, true, self.currentAttachment ? self.currentAttachment.description : undefined, { id: DataType.ALPHANUMERIC })).show();
					
					function onAccept(newDescription){
						
						if (self.currentAttachment) {
							
							let editedAttachment = webix.copy(self.currentAttachment);
							
							editedAttachment.description = newDescription;
							
							editAttachment(editedAttachment, function(newAttachment) {
								
								const filesGroup = $$("files-group-" + self.uid);
							
								if (Array.isArray(self.attachments) && filesGroup) {
									
									webix.ui(getViewFiles(), filesGroup);
									
									self.dataViewFiles.select([newAttachment.id]);
								
									self.currentAttachment = newAttachment;
									
									refresh();
									
									self.onChange();
								}
							});
						}
					}
				}
				
				function editAttachment(attachment, onPersist) {
					
					const url = appConfig.restUrl + 
									util.fillTemplate(REST_BACKOFFICE_EDIT_ATTACHMENT, { 
										backOfficeId: self.backOffice.id,
										tableName: self.tableName
									});
					
					webix.ajax().headers(CONTENT_TYPE_JSON).post(url, attachment).then(
						function(newAttachment) {
							
							if (newAttachment) {
								
								let attachment = newAttachment.json();
								
								if (attachment) {
									
									const idxAttachment = self.attachments.findIndex(e => e.id === attachment.id);
									
									if (idxAttachment > -1) {
										
										self.attachments[idxAttachment] = attachment;
									}
									
									if (onPersist) {
									
										onPersist(attachment);	
									}	
								}
							}
						}
					);
				}
				
				return viewButtonEdit;
			}
			
			function getButtonDelete() {
				
				let viewButtonDelete;
				
				viewButtonDelete = {
					id: "btn-delete-" + self.uid,
					view: "button",
					type: "icon",
					css: "toolbar-button-text",
					width: 130,
					label: i18n.t("LBL_CMD_DELETE"),
					disabled: !self.currentAttachment,
					tooltip: i18n.t("LBL_CMD_DELETE"),
					icon: "mdi mdi-delete",
					on: {
						onItemClick: function() {
							
							(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_DELETE_ATTACHMENT") + " " + self.currentAttachment.fileName + "?", null, onAccept , null)).show();
							
							function onAccept() {
								
								const url = appConfig.restUrl + 
									util.fillTemplate(REST_BACKOFFICE_DELETE_ATTACHMENT, { 
										backOfficeId: self.backOffice.id, 
										tableName: self.tableName, 
										attachmentId: self.currentAttachment.id
									});
			
								webix.ajax().headers(CONTENT_TYPE_JSON).del(url).then(
									function(item) {
										
										if (Array.isArray(self.attachments) && item) {
											
											const attachment = item.json();
											
											if (attachment) {
												
												let idxDelete = self.attachments.findIndex(e => e.id == attachment.id);
											
												if (idxDelete > -1) {
													
													self.attachments.splice(idxDelete, 1);
													
													const filesGroup = $$("files-group-" + self.uid);
								
													if (filesGroup) {
														
														webix.ui(getViewFiles(), filesGroup);
													}
													
													if (Array.isArray(self.attachments) && self.attachments.length > 0) {
													
														self.currentAttachment = self.attachments[0];
														
														self.dataViewFiles.select([self.currentAttachment.id]);
													}
													else {
														
														self.currentAttachment = undefined;
													}
													
													self.onChange();
													
													refresh();
												}
											}
										}
									}
								);
							}
						}
					}
				}
				
				return viewButtonDelete;
			}
			
			
			function getButtonPreview() {

				let viewButtonPreview;

				viewButtonPreview = {
					id: "btn-preview-" + self.uid,
					view: "button",
					type: "icon",
					css: "toolbar-button-text",
					width: 130,
					label: i18n.t("LBL_CMD_PREVIEW"),
					disabled: !self.currentAttachment,
					tooltip: i18n.t("LBL_CMD_PREVIEW"),
					icon: "mdi mdi-file-document",
					on: {
						onItemClick: function() {
							
							const url = appConfig.restUrl +
								util.fillTemplate(REST_BACKOFFICE_DOWNLOAD_ATTACHMENT, {
									backOfficeId: self.backOffice.id,
									tableName: self.tableName,
									attachmentId: self.currentAttachment.id
								});
								
							let filePreviewHeight = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FILE_PREVIEW_HEIGHT, self.backOffice.metadata, self.currentTableData);
							let filePreviewWidth = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FILE_PREVIEW_WIDTH, self.backOffice.metadata, self.currentTableData);
							
							fetch(url)
								.then(res => res.text())
								.then(content => {
									
									let multiline = {
										id: "textarea-" + self.uid,
										view: "textarea",
										readonly: true,
										value: content,
										scroll: true
									};
								
									util.ui.loadWindow(self.currentAttachment.fileName, multiline, filePreviewHeight, filePreviewWidth, true);
								});
						}
					}
				}

				return viewButtonPreview;
			}
			
			return viewActions;
		}
		
		function refresh() {
			
			const btnEdit = $$("btn-edit-" + self.uid);
			const btnDelete = $$("btn-delete-" + self.uid);
			
			if (btnEdit && btnDelete) {
				
				if (Array.isArray(self.attachments) && self.attachments.length > 0) {
					
					btnEdit.enable();
					btnDelete.enable();
				}
				else {
					
					btnEdit.disable();
					btnDelete.disable();
				}
				
				btnEdit.refresh();
			}
			
			const descriptionView = $$("attachment-description-" + self.uid);
		
			if (descriptionView) {
				
				if (self.currentAttachment) {
					
					descriptionView.setValue(self.currentAttachment.description);
					
					descriptionView.show();					
				}
				else {
					
					descriptionView.hide();
				}
			}
		}
		
		return view;
	}
}
