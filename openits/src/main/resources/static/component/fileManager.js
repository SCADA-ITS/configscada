import { util } from "../lib/util.js";
import DataView from "../component/dataView.js";

const WINDOW_HEIGHT = 700;
const WINDOW_WIDTH = 1200;

const REST_ENDPOINT_FILES_DIR = "files/getFiles";
const REST_ENDPOINT_DOWNLOAD_FILE = "files/downloadFile/${path}/${fileName}";
const REST_ENDPOINT_UPLOAD_FILE = "files/uploadFile/${path}";
const ICON_SIZE = 48; 

export default class FileManager {
	
	static get MODE_DOWNLOAD() { return 1 }
	static get MODE_DOWNLOAD_UPLOAD() { return 2 }

	constructor(title, description, paths, mode, events) {

		this.uid = webix.uid();
		this.title = title;
		this.description = description;
		this.paths = paths;
		this.files = [];
		this.mode = mode ? mode : FileManager.MODE_DOWNLOAD;
		this.containerId = undefined;
	}

	show() {

		let self = this;
		let body;
		
		body = {
			
			cols: [
				{
					id: "loading-" + self.uid,
					hidden: false,
					rows: [util.ui.getViewDrawLoading()]
				},
				{
					id: self.uid,
					hidden: true
				}
			]
		};
		
		function drawContent() {
			
			let viewContent;
			
			viewContent = {
				id: self.uid,
				cols: [
					{ width: 10 },
					{
						rows: [
							drawHeader(),
							{ height: 10 },
							drawDataView(),
							drawUpload(),
							{ height: 10 },
							drawDetails(),
							{ height: 10 },
							drawFooter()
						]		
					},
					{ width: 10 },
					drawUploader()
				]
			}
			
			function drawHeader() {

				let header = {};
				let label = self.title;
				let description = self.description;
	
				header = {
	
					id: "viewHeader-" + self.uid,
					css: "global-header",
					cols: [
						{
							view: "label",
							label: "<img src='img/folder.png' class='grid-icon'/>",
							height: 75,
							css: "icon"
						},
						{
							view: "label",
							height: 75,
							id: "label-bottom-" + self.uid,
							template: "<span>" + label + "</span> <br> <span style='font-size: 13px'>" + description + "</span>",
							css: "label"
						}
					]
				};
	
				return header;
			}
			
			function drawDataView() {
				
				function getMetadata() {
						
					let metadata;
					
					metadata = {
					
						options: [
							{ id: DataView.Options.ICON_SIZE, value: ICON_SIZE },
							{ id: DataView.Options.SEARCH, value: true },
							{ id: DataView.Options.GROUP, value: false },
							{ id: DataView.Options.SORTED, value: true },
							{ id: DataView.Options.CMD_BUTTONS, value: true }
						],
						commands: [
							{
								value: "LBL_REFRESH",
								icon: "mdi mdi-refresh",
								label: "LBL_REFRESH",
								showText: true,
								position: 1,
								itemRequired: false,
								callback: loadFiles
							},
							{
								value: "LBL_DOWNLOAD",
								icon: "mdi mdi-download",
								label: "LBL_DOWNLOAD",
								showText: true,
								position: 2,
								itemRequired: true,
								callback: downloadFile
							}
						]
					};
					
					if (self.mode === FileManager.MODE_DOWNLOAD_UPLOAD) {
						
						metadata.commands.push({
							
							value: "LBL_UPLOAD",
							icon: "mdi mdi-upload",
							label: "LBL_UPLOAD",
							showText: true,
							position: 3,
							itemRequired: false,
							callback: uploadFile
						});
					}
					
					return metadata;
				}
				
				function getData() {
					
					let data = [];
				
					if (Array.isArray(self.files) && self.files.length > 0) {
						
						data = self.files.map(e => { 
						
							return {
								id: e.id,
								image: "./img/extensions/" + e.fileExtension + ".png",
								imageDefault: "./img/extensions/default.png", 
								value: e.fileName
							};
						});
						
						data.sort((a, b) => (parseInt(a.value, 10) > parseInt(b.value, 10)) ? 1 : ((parseInt(b.value, 10) > parseInt(a.value, 10)) ? -1 : 0));
					}
					
					return data;
				}
				
				function getEvents() {
					
					let events = {};
			
					events[DataView.EventTypes.ON_SELECT_ITEM] = onSelectFile;
					
					return events;
				}
				
				function onSelectFile(item) {

					let labelFileTitle = $$("label-file-title-" + self.uid);
					let labelFileSize = $$("label-file-size-" + self.uid);
					let labelFileDescription = $$("label-file-description-" + self.uid);
					let labelFileModified = $$("label-file-modified-" + self.uid);
					let labelFileAuthor = $$("label-file-author-" + self.uid);
					let labelFile = $$("label-file-" + self.uid);
					
					let file = self.files.find(e => e.id == item.id);
					
					if (file) {
						
						if (labelFile) {
							
							labelFile.setValues(file);
						}
						
						if (labelFileTitle) {
							
							labelFileTitle.setValue(`<div class="title"><span class="section-text" style="color:#475466; font-size: 14px;">${file.alias ? file.alias : file.fileName}</span></div>`);
						}
					
						if (labelFileSize) {
							
							labelFileSize.setValues(file);
						}
						
						if (labelFileDescription) {
							
							labelFileDescription.setValues(file);
						}
						
						if (labelFileModified) {
							
							labelFileModified.setValues(file);
						}
						
						if (labelFileAuthor) {
							
							labelFileAuthor.setValues(file);
						}
						
						$$("viewDetails-" + self.uid).show();
					}
				}
				
				let dataViewFiles = new DataView(getMetadata(), getData(), getEvents());
				
				return {
					
					id: "viewDataView-" + self.uid,
					cols: [
						dataViewFiles.getView()
					]
				}
			}
			
			function drawUpload() {
				
				let header = {
					height: 45,
					cols: [
						{
							type: "header",
							css: "webix_dark",  
							template: i18n.t("LBL_DROP_FILES_HERE"), 
							
						},
						{
							view: "icon",
							width: 50,
							css: { "background": "#657584", "color": "#FFFFFF" },
							icon: "mdi mdi-close",
							tooltip: i18n.t("LBL_CLOSE"),
							on: {
								onItemClick: function(id) {
							
									$$("viewUpload-" + self.uid).hide();
									$$("viewDataView-" + self.uid).show();
									
									loadFiles();
								}
							}
						}
					],
					borderless: true
				};
				
				return {
					
					id: "viewUpload-" + self.uid,
					hidden: true,
					rows:[
						header,
					    { 
							view: "list", 
							id: "upload-list-" + self.uid, 
							type: "uploader",
							scroll: false 
					    }
					]
				}
			}
			
			function drawDetails() {
				
				let viewDetails;
				
				viewDetails = {
					id: "viewDetails-" + self.uid,
					css: { "border-width": "1px !important" },
					hidden: true,
					cols: [
						{
							rows: [
								{ height: 3, css: { "background": "#475466" } },
								{ height: 10 },
								getViewDetailsTitle(),
								{ height: 20 },
								getViewDetailsContent(),
								{ height: 10 }
							]
						},
						{ width: 2, css: { "background": "#DADEE0" } }
					]
				};
				
				function getViewDetailsTitle() {
					
					let viewDetailsTitle;
					
					viewDetailsTitle = {
						height: 60,
						borderless: true,
						cols: [
							{ width: 25 },
							{
								id: "label-file-" + self.uid,
								borderless: true,
								template: function(file) {
									
									let html = `<div class="text" style="margin-top: 0px"></div>`
									
									if (file.fileName) {
										
										let imageDefault = "./img/extensions/default.png";
										
										html = `<image class="list-photo" src="${'./img/extensions/' + file.fileExtension + '.png'}" onerror="this.onerror=null;this.src='${imageDefault}';" />`;
										html += `<div class="text" style="margin-top: 0px">
											  		<span class="list-first-line">${file.fileName ? file.fileName : '-'} </span>
											  		<span class="list-second-line">${file.alias ? file.alias : '-'} </span>
												</div>`;	
									}
									
									return html;		
								}
							}
						]
					}
					
					return viewDetailsTitle;	
				}
				
				function getViewDetailsContent() {
					
					let viewDetailsContent;
					
					viewDetailsContent = {
						borderless: true,
						css: "ims-incident-report-tab-details",
						cols: [
							{ width: 40 },
							{
								width: 400,
								rows: [
									{
										height: 35,
										view: "template",
										borderless: true,
										id: "label-file-author-" + self.uid,
										template:function(file){
									        return `<div><span class="ico webix_icon mdi mdi-account"></span><span class="field">${i18n.t("LBL_AUTHOR")}</span></div>
													 <div class="value">${file.author ? file.author : '-'}</div>`;
									    }
									},
									{
										height: 35,
										view: "template",
										borderless: true,
										id: "label-file-modified-" + self.uid,
										template:function(file){
									        return `<div><span class="ico webix_icon mdi mdi-clock-outline"></span><span class="field">${i18n.t("LBL_LAST_MODIFIED")}</span></div>
													 <div class="value">${file.lastModified ? util.toLocaleDateString(file.lastModified / 1000) : '-'}</div>`;
									    } 
									},
									{
										height: 35,
										view: "template",
										borderless: true,
										id: "label-file-size-" + self.uid,
										template:function(file){
											
									        return `<div><span class="ico webix_icon mdi mdi-database"></span><span class="field">${i18n.t("LBL_SIZE")}</span></div>
													 <div class="value">${file.size ? Math.ceil(file.size / 1024) + " KB" : '-'}</div>`;
									    }
									}
								]
							},
							{
								rows: [
									{
										id: "label-file-description-" + self.uid,
										borderless: true,
										css: { "margin": "0px" },
										height: 90,
										scroll: "auto",
										template: function(file) { 
											return `<div><p style="font-size: 13px; font-weight: 300; margin: 0px; padding: 5px">${file.description ? file.description : ''}</p></div>`;	
										}
									}
								]
							},
							{ width: 40 }
						]
					};
					
					return viewDetailsContent;
				}
				
				return viewDetails;
			}
			
			function drawFooter() {
				
				let viewFooter;
				
				viewFooter = {
					view: "toolbar",
					height: 45,
					borderless: true,
					elements: [{}]
				};
				
				viewFooter.elements.push({ 
					view: "button",
					css: "dialog-cancel", 
					width: 130,
					height: 35, 
					label: i18n.t("LBL_CANCEL"),
					on: {
						onItemClick() {
						
							$$(self.containerId).close();
						}
					}
				});
				
				return viewFooter;
			}
			
			function drawUploader() {
				
				let viewUploader;
				
				viewUploader = {
					view: "uploader", 
				    id: "uploader-" + self.uid,
				    apiOnly: true,
					//autosend: false,
					hidden: true,
					link: "upload-list-" + self.uid,
					upload: appConfig.restUrl + util.fillTemplate(REST_ENDPOINT_UPLOAD_FILE, { path: self.paths[0] })
				}
				
				return viewUploader;
			}
			
			return viewContent;
		}
		
		function loadFiles() {
			
			let params = {
				
				paths: self.paths.join()
			} 
			
			webix.ajax().get(appConfig.restUrl + REST_ENDPOINT_FILES_DIR, params).then(
				
				function(response) {
			
					if (response) {
									
						let files = response.json();
					
						if (Array.isArray(files) && files.length > 0) {
						
							self.files = files.map(e => {
						
								return {

									id: e.filePath + "/" + e.fileName, 									
									fileName: e.fileName,
									filePath: e.filePath,
									fileExtension: e.fileExtension,
									size: e.size,
									lastModified: e.lastModified,
									alias: (e.metadata && e.metadata.alias) ? e.metadata.alias: undefined,
									description: (e.metadata && e.metadata.description) ? e.metadata.description: undefined,
									author: (e.metadata && e.metadata.author) ? e.metadata.author: undefined,
								}
							});	
						}
						
						webix.ui(drawContent(), $$(self.uid));
						
						$$("loading-" + self.uid).hide();
						$$(self.uid).show();
					}
				},
				function(response) {
					
					util.ajax.handleError(response);
				}
			);
		}
		
		function downloadFile(id) {
			
			if (Array.isArray(self.files)) {
				
				let file = self.files.find(e => e.id == id);
				
				if (file) {

					let url = appConfig.restUrl + util.fillTemplate(REST_ENDPOINT_DOWNLOAD_FILE, {path: file.filePath, fileName: file.fileName});
					
					window.open(url.replace(" ", "%20"), '_blank');
				}
			}
		}
		
		function uploadFile() {
			
			// TODO: Implemetation
			
			let url = appConfig.restUrl + util.fillTemplate(REST_ENDPOINT_UPLOAD_FILE, { path: self.paths[0] });
			
			$$("viewUpload-" + self.uid).show();
			$$("viewDataView-" + self.uid).hide();
			$$("viewDetails-" + self.uid).hide();
			
			$$("uploader-" + self.uid).addDropZone($$("upload-list-" + self.uid).$view, i18n.t("LBL_DROP_FILES_HERE"));
		}
		
		self.containerId = util.ui.loadWindow(self.title, body, WINDOW_HEIGHT, WINDOW_WIDTH, true);
		
		loadFiles();
	}
}

Object.defineProperty(FileManager, 'EventTypes', {
    value: {}
});

Object.defineProperty(FileManager.EventTypes, "ON_UPLOAD_FILE", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(FileManager.EventTypes, "ON_DOWNLOAD_FILE", {
    value: 2,
    writable : false,
    enumerable : true,
    configurable : false
});