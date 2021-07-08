import { util } from "../../lib/util.js";

const STREAM_MJPEG = "stream_mjpeg";

export default class CamViewer {

	constructor(camConf, element, w, h) {

		this.uid = webix.uid();
		this.camConf = camConf;
		this.element = element;
		this.stream = STREAM_MJPEG;
		//this.w = $$(parent.args.containerId) ? $$(parent.args.containerId).$width : "100%";
		//this.h = $$(parent.args.containerId) ? $$(parent.args.containerId).$height : "100%";
		this.w = w ? w : "100%";
		this.h = h ? h : "100%";
	}

	getView() {

		let self = this;
		
		let view = {
			id: "camviewer-" + self.uid
		}
		
		if (this.stream === STREAM_MJPEG) {
		
			getViewStreamMjpeg();	
		}
		
		function getViewStreamMjpeg(url) {
			
			let config = self.camConf[STREAM_MJPEG];	
			
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
					img.src = url;
				}
				else {
					
					let h = undefined;
					
					if (self.h.indexOf("%") === -1) {
						
						h = h.replace("px", "");
					}
					
					
					let offset = url.indexOf('@');
					
					if (offset !== -1) {
						
						let protocol = url.substring(0, url.indexOf('//') + 2);
						
						url = protocol + url.substring(offset + 1);
					}
					
					view = {
						id: "camviewer-" + self.uid,
						view: "iframe",
						src: url
					};
					
					if (h) {
						
						view.height = h;
					}
				}
			}
		}

		return view;
	}
}
