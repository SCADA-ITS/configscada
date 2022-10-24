import CamViewer from "./camViewer.js";
import Decoder from "./decoder.js";

const DEFAULT_FPS = 10;
const DEFAULT_TIMEOUT_CONNECT_MS = 10;

export default class DecoderSNAP extends Decoder {

	constructor(camviewer) {

		super(camviewer);
		
		this.config = this.config.camConf.decoders[Decoder.SNAP];
		this.img = undefined;
		this.fps = this.config.defaultFps ? this.config.defaultFps : DEFAULT_FPS;
	
		this.timeoutConnectMs = this.config.timeoutConnectMs ? this.config.timeoutConnectMs : DEFAULT_TIMEOUT_CONNECT_MS;
		this.initTimeElapsedWOConn = 0;
		this.timeElapsedWOConn = 0;
		  
		let elementValueFps = this.element.elementValues.find(e => e.elementTypeParam.id === this.config.fps);

		if (elementValueFps) {

			this.fps = elementValueFps.value;
		}
		
		this.timeCheckStreamMs = Math.floor(1000 / this.fps);
		this.running = false;
		this.loading = false;
		this.error = false;
		this.hndStream = undefined;
		this.previousFrame = false;
		
		this.firstConnection = true;
	}
	
	start() {
		
		let self = this;
		let cnv = undefined;
		let url = undefined;
		let elementValueUrl = undefined;
		
		self.img = new Image;
		elementValueUrl = self.element.elementValues.find(e => e.elementTypeParam.id === self.config.url);

		if (elementValueUrl) {

			url = elementValueUrl.value;
		}

		if (url) {
			
			drawCanvas();
			
			cnv = document.getElementById(`img-${self.element.id}-${self.uid}`);
			
			if (cnv) {
				
				self.running = true;
				self.img.src = Decoder.NO_VIDEO_IMAGE;
				
				if ($$("camviewer-buttons-" + self.uid)) {
						
					$$("camviewer-buttons-" + self.uid).hide();	
				}
				
				setTimeout(function() {
					
					self.img.onerror = onErrorImage;
					self.img.onload = onLoadImage;
					
					updateImage();
						
				}, 1000);
			}
		}
		
		function drawCanvas() {
				
			let viewStreamMjpeg = {
				id: "camviewer-" + self.uid,
				relative: true,
				css: "camviewer-matrix",
				template: function() {
					
					return `<div id="${self.camContainerId}" class="camviewer-div" style="background-color: #9e9e9e; overflow: hidden"><canvas class="camviewer-img" id="img-${self.element.id}-${self.uid}" width="${self.w}" height="${self.h}"></canvas></div>`;
				}
			};
			
			webix.ui(viewStreamMjpeg, $$("camviewer-" + self.uid));
		}
		
		function onLoadImage() {
			
			self.loading = false;
			var ctx = cnv.getContext("2d");
			
			if (self.img != null && cnv) {
				
				let ratioW = self.w / self.img.width;
				let ratioH = self.h / self.img.height;
						
				let ratio = ratioW < ratioH ? ratioW : ratioH;
				
				self.imgW = self.fit ? self.w : self.img.width * ratio;
				self.imgH = self.camviewer.fullScreen ? (self.h - 24) : (self.img.height * ratio);
				
				cnv.width = self.imgW;
        		cnv.height = self.imgH;
        		
        		if (!document.hidden) {
	
					if (self.container ) {
					
						if (self.container.isVisible()) {
							
							ctx.drawImage(self.img, 0, 0, self.img.width, self.img.height, 0, 0, self.imgW, self.imgH);	
						}	
					}
					else {
						
						ctx.drawImage(self.img, 0, 0, self.img.width, self.img.height, 0, 0, self.imgW, self.imgH);
					}
				}

				if (self.firstConnection && !self.error) {
					
					self.firstConnection = !self.firstConnection;
					self.camviewer.drawButtons();		
				}
				
				if (self.error) {
				
					self.error = !self.error;
					self.firstConnection = true;
					
					if ($$("camviewer-buttons-" + self.uid)) {
						
						$$("camviewer-buttons-" + self.uid).hide();	
					}
				}
            }

            if (self.running && self.timeCheckStreamMs > 0) {
                
				self.hndStream = setTimeout(updateImage, self.timeCheckStreamMs);
            }
		}
		
		function onErrorImage() {
			
			self.loading = false;
			self.error = true;
			
            if (self.running && self.timeCheckStreamMs > 0) {
	
				self.hndStream = setTimeout(updateImage, self.timeCheckStreamMs);	
            }
		}
		
		function updateImage() {
			
	        if (self.loading) return;

	        if (self.img) {
		
				if (!self.error) {

					self.timeElapsedWOConn = 0;
					self.initTimeElapsedWOConn = 0;
										
					self.img.src = getNewUrl();
				}
				else {
					
					if (self.initTimeElapsedWOConn == 0) {
						
						self.initTimeElapsedWOConn = Date.now();
					}
					
					self.timeElapsedWOConn = Date.now() - self.initTimeElapsedWOConn;
					
					if (self.timeElapsedWOConn > self.timeoutConnectMs) {
							
						self.img.src = Decoder.NO_VIDEO_IMAGE;
					}
				}
			}
			
			self.loading = true;
	    }

		function getNewUrl() {
						
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
	}
	
	stop() {
		
		let self = this;
		
		self.running = false;

		if (self.timeCheckStream) {

			clearInterval(self.hndStream);
		}
	}
}
