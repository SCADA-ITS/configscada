import { util } from "../../lib/util.js";

const STREAM_MJPEG = "stream_mjpeg";

export default class CamViewer {

	constructor(parent, element) {

		this.uid = webix.uid();
		this.parent = parent;
		this.element = element;
		this.stream = STREAM_MJPEG;
		this.w = $$(parent.args.containerId) ? $$(parent.args.containerId).$width : "100%";
		this.h = $$(parent.args.containerId) ? $$(parent.args.containerId).$height : "100%";
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
			
			if (!url) {
				
				let elementValue = self.element.elementValues.find(e => e.elementTypeParam.id === self.parent.config.camConf.stream_mjpeg.url);
				
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
								return `<div><img style="max-width:100%;width:100%;height:${self.h};" src="${img.src}"></div>`;
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
					
					let offset = url.indexOf('@');
					
					if (offset !== -1) {
						
						let protocol = url.substring(0, url.indexOf('//') + 2);
						
						url = protocol + url.substring(offset + 1);
					}
					
					view = {
						id: "camviewer-" + self.uid,
						view: "iframe",
						height: self.h,
						src: url
					};
				}
			}
		}

		return view;
	}
}
