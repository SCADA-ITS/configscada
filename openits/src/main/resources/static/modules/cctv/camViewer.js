import { util } from "../../lib/util.js";

export default class CamViewer {
	
	static get MODE_SINGLE() { return 1 }
	static get MODE_MULTI() { return 2 }
	
	static get STREAM_MJPEG() { return "stream_mjpeg" }
	
	constructor(camConf, element, w, h, mode) {

		this.uid = webix.uid();
		this.camConf = camConf;
		this.element = element;
		//this.stream = CamViewer.STREAM_MJPEG;
		this.w = w ? w : "100%";
		this.h = h ? h : "100%";
		this.mode = mode;
	}

	getView() {

		let self = this;
		
		let view = {
			view: "abslayout",
			cells: [
				{
					id: "camviewer-" + self.uid,
					relative: true		
				},
				getViewControls()
			]
		}
		
		getViewStream();
		
		function getViewStream() {
			
			let streamProtocolParam = self.element.elementValues.find(e => e.elementTypeParam.id === self.camConf.streamProtocol);
			let streamProtocol = CamViewer.STREAM_MJPEG;
				
			if (streamProtocolParam) {
				
				streamProtocol = streamProtocolParam.value; 
			}
			
			if (streamProtocol === CamViewer.STREAM_MJPEG) {
			
				getViewStreamMjpeg();	
			}
			
			function getViewStreamMjpeg(url) {
			
				let config = self.camConf[CamViewer.STREAM_MJPEG];	
				
				if (!url) {
					
					let elementValue = self.element.elementValues.find(e => e.elementTypeParam.id === config.url);
					
					if (elementValue) {
						
						url = elementValue.value; 
					}
				}
				
				if (url) {
					
					if (!webix.env.isWebKit) {
						
						let img = new Image();
								
						img.onload = function() {
						
							let viewStreamMjpeg = {
								id: "camviewer-" + self.uid,
								relative: true,
								template: function() {
		
									//return `<div><img style="max-width:100%;width:100%;height:100%;" src="${img.src}"></div> `
									//return `<div><img style="max-width:100%;width:100%;height:${self.h};" src="${img.src}"></div>`;
									
									let w = (self.w == "100%" ? "width: 100%" : "");
									let imgStyle = `max-width:100%;height:${self.h};${w}`;
									
									return `<div style="background-color: #9e9e9e; border: 2px solid; text-align: center;"><img align='center' style="${imgStyle}" src="${img.src}"></div>`;
								}
								,on: {
									
									onDestruct: function(obj) {
										
										img.src = '';
									}
								}
							};
							
							webix.ui(viewStreamMjpeg, $$("camviewer-" + self.uid));
							img.onload = undefined;	
						};
						
						//let url = 'http://admin:(R4D7)Revenga@192.168.88.251/cgi-bin/mjpg/video.cgi?channel=1&subtype=1';		
						//img.src = !webix.env.isWebKit ? url : getUrlWithoutAuth(url);
						
						img.src = url;
					}
					else {
						
						let h = undefined;
						
						if (self.h.indexOf("%") === -1) {
							
							h = self.h.replace("px", "");
						}
						
						let viewStreamMjpeg = {
							id: "camviewer-" + self.uid,
							view: "iframe",
							relative: true,
							src: getUrlWithoutAuth(url)
						};
						
						if (h) {
					
							viewStreamMjpeg.height = h;
						}
						
						webix.delay(function() {
	
							let camViewer = $$("camviewer-" + self.uid);
							
							if (camViewer) {
								
								webix.ui(viewStreamMjpeg, $$("camviewer-" + self.uid));
							}
							
						}, null, null, 1000);
					}
				}
			}
		}
		
		function getViewControls() {
			
			let viewControls = {
				id: "camviewer-controls-" + self.uid,
				bottom: 20, 
				cols: [
					{ width: 1 }
				]
			};
			
			if (self.mode === CamViewer.MODE_SINGLE) {
				
				getViewControlsSingleMode();
			}
			else if (self.mode === CamViewer.MODE_MULTI) {
				
				getViewControlsMultiMode();
			}
			
			function getViewControlsSingleMode() {
				
				viewControls.height = 100;
				
			}
			
			function getViewControlsMultiMode() {
				
				let viewCamControls = {
					
					view: "button",
					css: "cam-viewer-button",
					type: "icon",
					icon:"mdi mdi-camera-control",
					tooltip: i18n.t("LBL_CAM_CONTROLS"),
					width: 30, height: 30,
					on: {
						onItemClick: function() {
							
						}
					}
				}
				
				let viewCamEquipmentViewer = {
					
					view: "button",
					css: "cam-viewer-button",
					type: "icon",
					icon:"mdi mdi-fullscreen",
					tooltip: i18n.t("LBL_VIEW_IN_EQUIPMENT_VIEWER"),
					width: 30, height: 30,
					on: {
						onItemClick: function() {
							
						}
					}
				}
				
				viewControls.height = 30;
				
				viewControls.cols.push(viewCamControls);
				
				viewControls.cols.push(viewCamEquipmentViewer);
				
				//viewControls.right = ((viewControls.cols.length - 1) * 30) + 10; 
				viewControls.right = 20;
			}
			
			return viewControls;
		}
		
		function getUrlWithoutAuth(url) {
			
			let offset = url.indexOf('@');
			let urlWithoutAuth = url;
			
			if (offset !== -1) {
				
				let protocol = url.substring(0, url.indexOf('//') + 2);
				
				urlWithoutAuth = protocol + url.substring(offset + 1);
			}
			
			return urlWithoutAuth;
		}

		return view;
	}
}
