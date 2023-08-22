import CamViewer from "./camViewer.js";
import Decoder from "./decoder.js";

const DEFAULT_TIME_CHECK_STREAM_MS = 60000;
const DEFAULT_TIMEOUT_CONNECT_STREAM_MS = 5000;

export default class DecoderMJPEG extends Decoder {

	constructor(camviewer) {

		super(camviewer);
		
		this.config = this.config.camConf.decoders[Decoder.MJPEG];
		
		this.img = new Image();
		this.isOnline = true;
		this.timeCheckStream = undefined;
		
		this.timeCheckStreamMs = this.config.timeCheckStreamMs ? this.config.timeCheckStreamMs : DEFAULT_TIME_CHECK_STREAM_MS;
		this.timeoutCheckStreamMs = this.config.timeoutConnectStreamMs ? this.config.timeoutConnectStreamMs : DEFAULT_TIMEOUT_CONNECT_STREAM_MS;
		
		this.firstConnection = true;
	}
	
	start() {
		
		let self = this;
		
		
		let url;

		let elementValueUrl = self.element.elementValues.find(e => e.elementTypeParam.id === self.config.url);

		if (elementValueUrl) {

			url = elementValueUrl.value;
		}

		if (url) {

			function startStreaming() {

				self.isOnline = true;
				
				//console.log("[" + (new Date()).toISOString() + "] " + "[CamViewer] Connection success for cam: " + self.img.src);

				let viewStreamMjpeg = {
					id: "camviewer-" + self.uid,
					relative: true,
					css: "camviewer-matrix",
					template: function() {

						let imgStyle;

						let ratioW = self.w / self.img.width;
						let ratioH = self.h / self.img.height;
						
						let ratio = ratioW < ratioH ? ratioW : ratioH;  
						
						self.imgW = self.fit ? self.w : self.img.width * ratio;
						self.imgH = self.camviewer.fullScreen ? (self.h - 24) : (self.img.height * ratio);

						imgStyle = `max-width:100%;height:${self.imgH}px;width:${self.imgW}px`;

						return `<div id="${self.camContainerId}" class="camviewer-div" style="background-color: #9e9e9e; overflow: hidden ${imgStyle}"><img id="img-${self.element.id}-${self.uid}" class="camviewer-img" align='center' style="${imgStyle}" src="${self.img.src}"></div>`;
					}
					, on: {

						onDestruct: function() {
						
							self.onDestruct(self);
						},
						onViewShow: function() {
						
							setTimeout(function() {
					
								if (self.camviewer.expandCam) {
									
									self.camviewer.expandCam = !self.camviewer.expandCam;
									self.camviewer.expandVideo();
									self.camviewer.drawButtons();
								}
			
							}, 2000);
						}
					}
				};
	
				webix.ui(viewStreamMjpeg, $$("camviewer-" + self.uid));
				
				if ($$("camviewer-" + self.uid).isVisible()) {
					
					if ($$("camviewer-buttons-" + self.uid)) {

						$$("camviewer-buttons-" + self.uid).show();
					}
				}
					
				self.img.onload = undefined;
				
				if (self.firstConnection) {
					
					self.firstConnection = !self.firstConnection;
					self.camviewer.drawButtons();		
				}
			}

			self.img.src = self.getNewUrl(url);
			self.img.onload = startStreaming;

			self.startKeepAliveStream(startStreaming, url);
		}
	}
	
	startKeepAliveStream(startStreaming, url) {

		let self = this;
		
		self.timeCheckStream = setInterval(function() {

			if ($$("camviewer-" + self.uid)) {
				
				self.isOnline = false;
				
				self.img.onload = startStreaming;
				
				self.img.src = self.getNewUrl(url);
				
				setTimeout(function() {
					
					if (!self.isOnline) {
						
						webix.ui(self.getViewNoImage(), $$("camviewer-" + self.uid));
						
						if ($$("camviewer-buttons-" + self.uid)) {
							
							$$("camviewer-buttons-" + self.uid).hide();	
						}
							
					}

				}, self.timeoutCheckStreamMs);	
			}

		}, self.timeCheckStreamMs);
	}
	
	getNewUrl(url) {
				
		let newUrl = url;
		
		let urlParams = new URLSearchParams(url);
		
		if (Array.from(urlParams).length > 0 && url.indexOf("?") !== -1) {
			
			newUrl += "&rnd=";  
		}
		else if (url.indexOf("?") === -1) {
			
			newUrl += "?rnd=";
		}
		
		let timeService = window.sm.getService(Services.TIME_SERVICE);
		let date = timeService.getEpoch();

		newUrl += date;

		return newUrl;
	}
	
	onDestruct(ctx) {
		
		let self = ctx ? ctx : this;

		let img = document.getElementById("img-" + self.element.id + "-" + self.uid);
		
		if (img) {

			img.src = '';
		}
	}
	
	stop() {
		
		let self = this;

		if (self.timeCheckStream) {

			clearInterval(self.timeCheckStream);
		}
	
		self.img.src = '';
	}
}


