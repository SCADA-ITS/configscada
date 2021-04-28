import { util } from "../../lib/util.js";

const STREAM_MJPEG = "stream_mjpeg";

export default class CamViewer {

	constructor(parent, element) {

		this.uid = webix.uid();
		this.parent = parent;
		this.element = element;
		this.stream = STREAM_MJPEG;
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
				
				let img = new Image();
			
				let viewStreamMjpeg = undefined;
				
				img.onload = function() {
				
					viewStreamMjpeg = {
						id: "camviewer-" + self.uid,
						template: function() {
					
							return `<div><img style="max-width:100%;width:100%;height:100%;" src="${img.src}"></div> `
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
				
				//let url = 'img/not_found_50.png';
				//let url = 'http://admin:(R4D7)Revenga@192.168.88.251/cgi-bin/mjpg/video.cgi?channel=1&subtype=1';
		
				img.src = url;	
			}
		}

		return view;
	}
}
