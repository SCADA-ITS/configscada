import { util } from "../lib/util.js";
import DataView from "../component/dataView.js";

const WINDOW_HEIGHT = 700;
const WINDOW_WIDTH = 1200;

const REST_ENDPOINT_FILES_DIR = "files/getFiles";
const REST_ENDPOINT_DOWNLOAD_FILE = "files/downloadFile?${params}";
const REST_ENDPOINT_UPLOAD_FILE = "files/uploadFile?path=${path}";
const REST_ENDPOINT_DELETE_FILES = "files/deleteFiles?${params}";
const ICON_SIZE = 48; 

export default class FileManager {
	
	static get MODE_DOWNLOAD() { return 1 }
	static get MODE_DOWNLOAD_UPLOAD() { return 2 }
	
	static get FILE_DOWNLOADED() { return 1 }
	static get FILE_UPLOADED() { return 2 }
	static get FILE_DELETED() { return 3 } 

	constructor(title, description, paths, mode, events) {

		this.uid = webix.uid();
		this.title = title;
		this.description = description;
		this.paths = paths;
		this.files = [];
		this.mode = mode ? mode : FileManager.MODE_DOWNLOAD;
		this.events = events;
		this.containerId = undefined;
		this.logFiles = [];
		this.refreshing = false;
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
		
		function drawMain() {
			
			let viewMain;
			
			viewMain = {
				id: self.uid,
				cols: [
					{ width: 10 },
					{
						rows: [
							drawHeader(),
							{ height: 10 },
							drawContent(),
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
			
			function drawContent() {
				
				let viewContent;
				
				viewContent = {
					
					cols: [
						{
							rows: [
							
								drawDataView(),
								drawUpload(),
								{ height: 10 },
								drawDetails()		
							]
						}
					]
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
							
							metadata.commands.push({
								
								value: "LBL_DELETE",
								icon: "mdi mdi-delete",
								label: "LBL_DELETE",
								showText: true,
								position: 4,
								itemRequired: true,
								callback: deleteFile
							});
						}
						
						function downloadFile(id) {
			
							if (Array.isArray(self.files)) {
								
								let file = self.files.find(e => e.id == id);
								
								if (file) {
									
									let fileObj = {
										path: encodeURIComponent(file.filePath),
										fileName: encodeURIComponent(file.fileName)
									}
									
									var params = Object.keys(fileObj).map(key => {
									  return key + '=' + fileObj[key];
									}).join('&');
				
									let url = appConfig.restUrl + util.fillTemplate(REST_ENDPOINT_DOWNLOAD_FILE, { params: params });
									
									window.open(url, '_blank');
									
									self.logFiles.push({
										file: file,
										action: FileManager.FILE_DOWNLOADED
									});
								}
							}
						}
						
						function uploadFile() {
												
							$$("viewUpload-" + self.uid).show();
							$$("viewDataView-" + self.uid).hide();
							$$("viewDetails-" + self.uid).hide();
							
							$$("uploader-" + self.uid).addDropZone($$("upload-list-" + self.uid).$view, i18n.t("LBL_DROP_FILES_HERE"));
						}
						
						function deleteFile(id) {
							
							if (Array.isArray(self.files)) {
								
								let file = self.files.find(e => e.id == id);
								
								if (file) {
									
									webix.confirm({
										title: i18n.t("LBL_FILE_CONFIRM_DELETE") + ": " + file.fileName,
										ok: i18n.t("LBL_YES"),
										cancel: i18n.t("LBL_NO"),
										text: i18n.t("LBL_FILE_CONFIRM_DELETE_DESCRIPTION"),
										width: 600
									}).then(function() {

										let fileObj = {
											paths: encodeURIComponent(file.filePath + "/" + file.fileName)
										}
										
										var params = Object.keys(fileObj).map(key => {
										  return key + '=' + fileObj[key];
										}).join('&');
					
										let url = appConfig.restUrl + util.fillTemplate(REST_ENDPOINT_DELETE_FILES, { params: params });
										
										$$("loading-" + self.uid).show();
										$$(self.uid).hide();
										
										webix.ajax().del(url).then(response => {
											
											if (response) {
										
												let files = response.json();
												
												if (Array.isArray(files) && files.length > 0) {
													
													files.forEach(file => {
														
														self.logFiles.push({
								
															file: file,
															action: FileManager.FILE_DELETED	
														});
													})
													
													
													loadFiles();	
												}
											}
										});
									});
								}
							}
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
											height: 45,
											view: "template",
											borderless: true,
											id: "label-file-modified-" + self.uid,
											template:function(file){
										        return `<div><span class="ico webix_icon mdi mdi-clock-outline"></span><span class="field">${i18n.t("LBL_LAST_MODIFIED")}</span></div>
														 <div class="value">${file.lastModified ? util.toLocaleDateString(file.lastModified / 1000) : '-'}</div>`;
										    } 
										},
										{
											height: 45,
											view: "template",
											borderless: true,
											id: "label-file-size-" + self.uid,
											template:function(file){
												
										        return `<div><span class="ico webix_icon mdi mdi-database"></span><span class="field">${i18n.t("LBL_SIZE")}</span></div>
														 <div class="value">${file.size ? util.fileSize(file.size) : '-'}</div>`;
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
											height: 80,
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
				
				
				
				return viewContent;
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
					label: i18n.t("LBL_CLOSE"),
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
					hidden: true,
					link: "upload-list-" + self.uid,
					upload: appConfig.restUrl + util.fillTemplate(REST_ENDPOINT_UPLOAD_FILE, { path: encodeURIComponent(self.paths[0]) }),
					on: {
						onFileUpload:function(item) {
					    	
							self.logFiles.push({
								
								file: item,
								action: FileManager.FILE_UPLOADED	
							});
					    },
						onDestruct: function() {
							
							if (!self.refreshing && self.events && self.events[FileManager.EventTypes.ON_TRACE_FILES]) {
							
								if (Array.isArray(self.logFiles) && self.logFiles.length > 0) {
								
									self.events[FileManager.EventTypes.ON_TRACE_FILES](self.logFiles);	
								}	
							}
						}
					}					
				}
				
				return viewUploader;
			}
			
			return viewMain;
		}
		
		function loadFiles() {
			
			let params = {
				
				paths: self.paths.join()
			}
			
			$$("loading-" + self.uid).show();
			$$(self.uid).hide();
			
			webix.ajax().get(appConfig.restUrl + REST_ENDPOINT_FILES_DIR, params).then(
				
				function(response) {
			
					if (response) {
									
						let files = response.json();
						
						self.files = [];
					
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
									description: (e.metadata && e.metadata.description) ? e.metadata.description: undefined
								}
							});	
						}
						
						self.refreshing = true;
						
						webix.ui(drawMain(), $$(self.uid));
						
						self.refreshing = false;
						
						$$("loading-" + self.uid).hide();
						$$(self.uid).show();
					}
				},
				function(response) {
					
					util.ajax.handleError(response);
				}
			);
		}
		
		self.containerId = util.ui.loadWindow(self.title, body, WINDOW_HEIGHT, WINDOW_WIDTH, true);
		
		loadFiles();
	}
}

Object.defineProperty(FileManager, 'EventTypes', {
    value: {}
});

Object.defineProperty(FileManager.EventTypes, "ON_TRACE_FILES", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});
