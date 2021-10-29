export default class Decoder {
	
	static get MJPEG() { return "mjpeg" }
	static get SNAP() { return "snap" }
	
	static get NO_VIDEO_IMAGE() { return "./img/no-video.jpg" }

	constructor(camviewer) {

		if (this.constructor == Decoder) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.camviewer = camviewer; 
		
		this.config = camviewer.config; 
		this.element = camviewer.element;
		this.w = camviewer.w;
		this.h = camviewer.h;
		this.fit = camviewer.fit;
		this.uid = camviewer.uid;
		
		this.camContainerId = `cam-${camviewer.element.id}-${camviewer.uid}`;
	}
	
	start() {
		
		throw new Error("Method 'start()' must be implemented.");
	}
	
	stop() {
		
		throw new Error("Method 'stop()' must be implemented.");
	}
	
	onDestruct() {
	
		//throw new Error("Method 'onDestruct()' must be implemented.");	
	}
	
	getViewNoImage() {

		let self = this;
		let viewNoImage;

		viewNoImage = {
			id: "camviewer-" + self.uid,
			relative: true,
			template: function() {

				let w = self.fit ? "width: 100%" : "";
				let imgStyle = `max-width:100%;height:${self.h}px;${w}`;

				return `<div style="background-color: #9e9e9e; border: 2px solid; text-align: center;">
							<img align='center' style="${imgStyle}" src="${Decoder.NO_VIDEO_IMAGE}">
						</div>`;
			}
			, on: {

				onDestruct: function() {
					
					self.onDestruct(self);
				} 
			}
		}

		return viewNoImage;
	}
}
