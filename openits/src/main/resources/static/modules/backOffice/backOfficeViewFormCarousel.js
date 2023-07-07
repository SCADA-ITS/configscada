import BackOfficeHelper from "./backOfficeHelper.js";
import MessageBox from "../../component/messageBox.js";
import InputDialogBox from "../../component/inputDialogBox.js";
import { util } from "../../lib/util.js";

const REST_BACKOFFICE_DOWNLOAD_IMAGE = "backoffice/${backOfficeId}/attachments/image/${tableName}/${imageId}";
const REST_BACKOFFICE_UPLOAD_IMAGE = "backoffice/${backOfficeId}/attachments/image/upload/${tableName}/${id}/${groupName}/${position}";
const REST_BACKOFFICE_DELETE_IMAGE = "backoffice/${backOfficeId}/attachments/image/delete/${tableName}/${imageId}";
const REST_BACKOFFICE_EDIT_IMAGE = "backoffice/${backOfficeId}/attachments/image/edit/${tableName}";


export default class BackOfficeViewFormCarousel  {

	constructor(backOffice, groupImage, tableName, rowId, images, onChange) {

		this.backOffice = backOffice;
		this.groupImage = groupImage;
		this.tableName = tableName;
		this.rowId = rowId;
		
		this.images = images;
		this.onChange = onChange;
		this.uid = webix.uid();	
		
		this.currentImage = undefined;
	}
	
	getView() {
		
		let self = this;
		let view;
		
		if (Array.isArray(this.images) && this.images.length > 0) {
			
			self.images.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
			
			self.currentImage = self.images[0]; 
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
												getViewCarousel(),
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
				{ height: 5 }
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
						label: util.getStringCapitalized(BackOfficeHelper.translate(self.backOffice, self.groupImage.label) ?? i18n.t("LBL_IMAGE_GROUP"))
					},
					{
						view: "label",
						css: "backoffice-image-box-title-description",
						label: self.groupImage.description ?? i18n.t("LBL_IMAGE_GROUP_DESCRIPTION")
					},
					{}
				]
				
			}
			
			return viewTitle;
		}
		
		function getViewCarousel() {
			
			let viewCarouselGroup;

			const features = `toolbar=no,scrollbars=no,location=no,statusbar=no,menubar=no,noreferrer=yes,resizable=0,width=${window.screen.availWidth},height=${window.screen.availHeight},left=0,top=0`;
			
			let viewCarousel = {
				id: "carousel-" + self.uid,
				css: "backoffice-image-carousel",
				view: "carousel",
				borderless: true,
				hidden: (Array.isArray(self.images) && self.images.length === 0) || !self.images,
				height: 300,
				cols: [],
				navigation:{
			    	type: "side"
			    },
			    on: {
				
					onShow: function(id) {
						
						log.debug("Carousel image id: " + id);
						
						let idArray = id.split("-");
						
						if (Array.isArray(self.images) && self.images.length > 0 && Array.isArray(idArray) && idArray.length === 3) {
							
							self.currentImage = self.images.find(e => e.id == idArray[2]);
							
							refresh();
						}
					}
				}
			}
			
			if (Array.isArray(self.images) && self.images.length > 0) {
				
				for (let image of self.images) {
					
					let item = {
						id: "image-" + self.uid + "-" + image.id,
						css: "backoffice-image",
						template: img,
						data: {
							src: appConfig.restUrl + util.fillTemplate(REST_BACKOFFICE_DOWNLOAD_IMAGE, { backOfficeId: self.backOffice.id, tableName: self.tableName, imageId: image.id }),
							title: image.fileName
						}
					}
					
					viewCarousel.cols.push(item);	
				}
			}
			
			viewCarouselGroup = {
				id: "carousel-group-" + self.uid,
				cols: [
					viewCarousel,
					{
						height: 350,
						hidden: Array.isArray(self.images) && self.images.length > 0,
						borderless: true,
						rows: [
							util.ui.getViewNoData(true),		
						]
					}
				]
			}
			
			function img(obj){

				return `<img src="${obj.src}" title="${obj.title}" class="backoffice-image-content" ondragstart="return false" onclick="window.open('${obj.src}', '_blank', '${features}')"/>`;
			}
			
			return viewCarouselGroup;
		}
		
		function getViewDescription() {
			
			let viewDescription;
			let description = "";
			
			if (Array.isArray(self.images) && self.images.length > 0) {
				
				description = self.images[0].description;
			}
			
			viewDescription = {
				id: "image-description-" + self.uid,
				view: "textarea",
				css: "backoffice-textarea",
				hidden: !self.currentImage,
				height: 50,
				readonly: true,
				placeholder: i18n.t("LBL_BACK_OFFICE_IMAGE_WO_DESCRIPTION"),
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
					accept: "image/jpeg",
					autosend: true,
					multiple: false,
					//apiOnly: true,
					//hidden:true,
					on: {
						onBeforeFileAdd: function(item) {

							let res = true;							
							if (Array.isArray(self.images) && self.images.length > 0) {
								
								if (self.images.find(e => e.fileName === item.name)) {
									
									(new MessageBox(MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_IMAGE_ALREADY_EXISTS") + " " + item.name, null, null, null)).show();
									
									res = false;
								}
							}
							
							if (res) {
								
								this.config.upload = getUrl();	
							}
							
							return res;
						},
						onFileUpload: function(item) {
							
							const carousel = $$("carousel-" + self.uid);
							const carouselGroup = $$("carousel-group-" + self.uid);
							
							if (Array.isArray(self.images) && carousel && carouselGroup) {
								
								let newImage = {
									description: item.description,
									fileName: item.fileName,
									groupName: item.groupName,
									id: item.id,
									idValue: item.idValue,
									position: item.position,
									tableId: item.tableId
								} 
								
								self.images.push(newImage);
								
								webix.ui(getViewCarousel(), carouselGroup);
								
								carousel.setActive("image-" + self.uid + "-" + item.id);
								
								self.currentImage = newImage;
								
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
				
					if (Array.isArray(self.images) && self.images.length > 0) {
						
						nextPosition = self.images[self.images.length - 1].position + 1;
					}
					
					return appConfig.restUrl + 
							  util.fillTemplate(REST_BACKOFFICE_UPLOAD_IMAGE, { 
									backOfficeId: self.backOffice.id, 
									tableName: self.tableName, 
									id: self.rowId,
									groupName: self.groupImage.name,
									position: nextPosition
							});
				}
				
				return viewUploader;
			}
			
			function getButtonEdit() {
				
				let viewButtonEdit;
				
				const actions = {
					1: {
						label: "LBL_BACKOFFICE_IMAGE_EDIT_DESCRIPTION",
						callback: editDescription 
					},
					2: {
						label: "LBL_BACKOFFICE_IMAGE_EDIT_POSITION",
						callback: editPosition
					}
				}
				
				viewButtonEdit = {
					view: "menu",
					id: "btn-edit-" + self.uid,
					openAction: "click",
					width: 125,
					autowidth: true,
					css: "edit-img-button-text",
					tooltip: i18n.t("LBL_CMD_EDIT"),
					subMenuPos: "down",
					data: [
						{ 
							value: i18n.t("LBL_CMD_EDIT"),
							icon: "mdi mdi-pencil",
							submenu: function() {
								
								let submenus = [];
								
								const keys = Object.keys(actions);

								if (Array.isArray(keys) && keys.length > 0) {
									
									keys.forEach(key => {
									
										submenus.push({
											id: key,
											value: i18n.t(actions[key].label)
										});
									});
								}
								
								return submenus;
							}()
						}
					],
					on: {
						onMenuItemClick: function(id) {
							
							if (actions[id]) {
								
								actions[id].callback();
							}
						}
					}
				}
				
				function editDescription() {
					
					(new InputDialogBox (i18n.t("LBL_BACKOFFICE_IMAGE_EDIT_DESCRIPTION"), i18n.t("LBL_BACKOFFICE_IMAGE_EDIT_DESCRIPTION"), onAccept, true, self.currentImage ? self.currentImage.description : undefined, { id: DataType.ALPHANUMERIC })).show();
					
					function onAccept(newDescription){
						
						if (self.currentImage) {
							
							let editedImage = webix.copy(self.currentImage);
							
							editedImage.description = newDescription;
							
							editImage(editedImage, function(newImage) {
								
								self.currentImage = newImage;
								
								refresh();
								
								self.onChange();
							});
						}
					}
				}
				
				function editPosition() {
					
					(new InputDialogBox (i18n.t("LBL_BACKOFFICE_IMAGE_EDIT_POSITION"), i18n.t("LBL_BACKOFFICE_IMAGE_EDIT_POSITION"), onAccept, true, self.currentImage ? self.currentImage.position : undefined, { id: DataType.INTEGER })).show();
					
					function onAccept(newPosition){
						
						if (self.currentImage) {
							
							if (newPosition <= self.images.length && newPosition > 0) {
								
								let imagesToUpdate = [];
								
								let imagesCopy = webix.copy(self.images);
								
								if (Array.isArray(imagesCopy) && imagesCopy.length > 0) {
								
									let imageNewPosition = imagesCopy.find(e => e.id == self.currentImage.id);
									
									if (imageNewPosition) {
										
										let colateralImageNewPosition = imagesCopy.find(e => e.position == newPosition);
										
										let oldPosition = imageNewPosition.position;
										imageNewPosition.position = newPosition;
										
										imagesToUpdate.push(imageNewPosition);
										
										if (colateralImageNewPosition) {
											
											colateralImageNewPosition.position = oldPosition;
											
											imagesToUpdate.push(colateralImageNewPosition);
										}	
									}
									
									reOrder(imagesToUpdate, imageNewPosition.id);
								}
							}
							else {
								
								(new MessageBox(MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_IMAGE_WRONG_POSITION") + " " + newPosition, null, null, null)).show();
							}
						}
					}
				}
				
				function editImage(image, onPersist) {
					
					const url = appConfig.restUrl + 
									util.fillTemplate(REST_BACKOFFICE_EDIT_IMAGE, { 
										backOfficeId: self.backOffice.id,
										tableName: self.tableName
									});
					
					webix.ajax().headers(CONTENT_TYPE_JSON).post(url, image).then(
						function(newImage) {
							
							if (newImage) {
								
								let img = newImage.json();
								
								if (img) {
									
									const idxImg = self.images.findIndex(e => e.id === img.id);
									
									if (idxImg > -1) {
										
										self.images[idxImg] = img;
									}
									
									if (onPersist) {
									
										onPersist(img);	
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
					disabled: !self.currentImage,
					tooltip: i18n.t("LBL_CMD_DELETE"),
					icon: "mdi mdi-delete",
					on: {
						onItemClick: function() {
							
							(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_DELETE_IMAGE") + " " + self.currentImage.fileName, null, onAccept , null)).show();
							
							function onAccept() {
								
								const url = appConfig.restUrl + 
									util.fillTemplate(REST_BACKOFFICE_DELETE_IMAGE, { 
										backOfficeId: self.backOffice.id, 
										tableName: self.tableName, 
										imageId: self.currentImage.id
									});
			
								webix.ajax().headers(CONTENT_TYPE_JSON).del(url).then(
									function(item) {
										
										if (Array.isArray(self.images) && item) {
											
											const image = item.json();
											
											if (image) {
												
												let idxDelete = self.images.findIndex(e => e.id == image.id);
											
												if (idxDelete > -1) {
													
													self.images.splice(idxDelete, 1);
													
													const carouselGroup = $$("carousel-group-" + self.uid);
								
													if (carouselGroup) {
														
														webix.ui(getViewCarousel(), carouselGroup);
													}
													
													if (Array.isArray(self.images) && self.images.length > 0) {
													
														self.currentImage = self.images[0];
														
														let imagesToUpdate = [];
														let imagesCopy = webix.copy(self.images);
														
														for (let i = 0; i < imagesCopy.length; i++) {
															
															let imageCopy = imagesCopy[i];
															
															if (imageCopy.position != (i + 1)) {
																
																imageCopy.position = (i + 1);
																
																imagesToUpdate.push(imageCopy);
															}
														}
														
														if (Array.isArray(imagesToUpdate) && imagesToUpdate.length > 0) {
															
															reOrder(imagesToUpdate);
														}
													}
													else {
														
														self.currentImage = undefined;
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
			
			function reOrder(imagesToUpdate, selectedId) {
				
				let promises = [];
				
				if (Array.isArray(imagesToUpdate) && imagesToUpdate.length > 0) {
				
					const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_EDIT_IMAGE, { 
							backOfficeId: self.backOffice.id,
							tableName: self.tableName
						});
					
					imagesToUpdate.forEach(imageToUpdate => {
						
						promises.push(webix.ajax().headers(CONTENT_TYPE_JSON).post(url, imageToUpdate));	
					});
					
					webix.promise.all(promises).then(
						
						function(responses) {
							
							for (let response of responses) {
								
								if (response) {
									
									let img = response.json(); 	
									
									if (img) {
									
										const idxImg = self.images.findIndex(e => e.id === img.id);
										
										if (idxImg > -1) {
											
											self.images[idxImg] = img;
										}
									}
								}
							}
							
							self.images.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
							
							const carouselGroup = $$("carousel-group-" + self.uid);
			
							if (carouselGroup) {
																
								webix.ui(getViewCarousel(), carouselGroup);
								
								if (selectedId) {
									
									const carousel = $$("carousel-" + self.uid);
									
									if (carousel) {
										
										carousel.setActive("image-" + self.uid + "-" + selectedId);	
									}
								}
								
								self.onChange();	
							}
						}
					);	
				}
			}
			
			return viewActions;
		}
		
		function refresh() {
			
			const btnEdit = $$("btn-edit-" + self.uid);
			const btnDelete = $$("btn-delete-" + self.uid);
			
			if (btnEdit && btnDelete) {
				
				if (Array.isArray(self.images) && self.images.length > 0) {
					
					btnEdit.enable();
					btnDelete.enable();
				}
				else {
					
					btnEdit.disable();
					btnDelete.disable();
				}
				
				btnEdit.refresh();
			}
			
			const descriptionView = $$("image-description-" + self.uid);
		
			if (descriptionView) {
				
				if (self.currentImage) {
					
					descriptionView.setValue(self.currentImage.description);
					
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
