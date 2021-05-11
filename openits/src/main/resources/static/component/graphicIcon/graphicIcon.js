import { util } from "../../lib/util.js";

export default class GraphicIcon {
	
	static get CONTAINER_TYPE_SNAP() { return 1 }
	static get CONTAINER_TYPE_OPEN_LAYER() { return 2 }
	static get CONTAINER_TYPE_WEBIX_VIEW() { return 3 }
	
	static get CUSTOM_SVG_FILE() { return "img/graphic_icon/custom.svg" }
	
	static get G_ID_STATE() { return "#state" }
	
	static DEFAULT_STATE_COLOR(state) {
		
		let colors = ["#800080", "#008000", "#ff0000", "#ffcc00"];
		
		return colors[state];
	}
	
	constructor(layerElement, element, container) {

		if (this.constructor == GraphicIcon) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.layerElement = layerElement;
		this.element = element;
		this.container = container;
		
		this.g = undefined;
		this.ratio = undefined;
	}
	
	loadRender(onRenderReady) {

		onRenderReady();		
	}
	
	load(onLoaded) {
		
		let self = this;
		
		let s = undefined;
		let graphicIcon = self.layerElement.graphicIcon;
		
		if (self.container.type === GraphicIcon.CONTAINER_TYPE_SNAP) {
						
			s = self.container.obj;
		}
		else if (self.container.type === GraphicIcon.CONTAINER_TYPE_OPEN_LAYER) {
			
			// TODO
		}
		else if (self.container.type === GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW) {
			
			s = Snap("#" + self.container.obj.id);
		}
		
		let svgFile = graphicIcon.svgFile;
		
		if (!svgFile || svgFile == '' || !util.resourceExist(svgFile)) {
			
			//svgFile = GraphicIcon.CUSTOM_SVG_FILE;
			svgFile = undefined;
		}
		
		if (svgFile) {
			
			Snap.load(svgFile, function(f) {
							
				let svg2 = f.select("#svg2");
						
				// Calculate original width and height
				let gWidth = 0;
				let gHeight = 0;
				if (!svg2.attr("viewBox")) {
					
					gWidth = svg2.attr("width");
					gHeight = svg2.attr("height");
				}
				else {
				
					gWidth = svg2.attr("viewBox").width;
					gHeight = svg2.attr("viewBox").height;	
				}
				
				self.g = f.select("#main");
				
				createSelection(gWidth, gHeight);
				
				placeInContainer(gWidth, gHeight);
				
				self.g.click(onClick);
				//self.g.touchstart(onClick);
				self.g.dblclick(onDblClick);
				
				onLoaded();
			});
		}
		else {
			
			self.g = s.group().attr( { id: "main" });
			
			createSelection(graphicIcon.width, graphicIcon.height);
				
			placeInContainer(graphicIcon.width, graphicIcon.height);
			
			self.g.click(onClick);
			//self.g.touchstart(onClick);
			self.g.dblclick(onDblClick);
			
			onLoaded();
		}
		
		
		function afterLoad() {
			
		}
		
		function createSelection(gWidth, gHeight) {
			
			let gSel = s.rect(0, 0, gWidth, gHeight);
			gSel.attr({ id: "selBox", visibility: "hidden", stroke: "#FF9800", strokeWidth: 10, fill: "#FF9800", opacity: 0.6 });
			self.g.prepend(gSel);
		}
		
		function placeInContainer(gWidth, gHeight) {
			
			let wRatio = graphicIcon.width / gWidth;
			let hRatio = graphicIcon.height / gHeight;
			self.ratio = graphicIcon.width > graphicIcon.height ? wRatio : hRatio;
			
			let position = "t" + [self.layerElement.x, self.layerElement.y].join();
			//let scale = "s" + ((graphicIcon.width > graphicIcon.height) ? (graphicIcon.width / gWidth) : (graphicIcon.height / gHeight));
			
			
			
			let scale = "s" + (self.layerElement.horizontalFlip ? "-" : "") + (self.ratio + "," + self.ratio);
			
			//self.g.attr( { transform: "rotateY( 180deg )" });
			self.g.transform(position + ',' + scale);
			
			self.g.attr({
		        visibility: "visible",
				cursor: "pointer"
		    });

			s.add(self.g);
		}
		
		function onClick(e) {
			
			let gSel = self.g.select("#selBox");
		
			if (gSel.attr("visibility") === "hidden") {
				
				self.select();	
			}
//			else {
//				
//				self.unselect();
//			}
		}
		
		function onDblClick(e) {
			
		}
	}
	
	updateState(elementTypeState) {
		
		throw new Error("Method 'updateState(elementState)' must be implemented.");
	}
	
	updateValues(elementValues) {
		
		throw new Error("Method 'updateValues(elementValues)' must be implemented.");
	}
	
	updateAlarms(alarms) {
		
		throw new Error("Method 'updateAlarms(alarms)' must be implemented.");
	}

	show() {
		
		self.g.attr({ visibility: "visible" });
	}

	hide() {
		
		self.g.attr({ visibility: "hidden" });
	}
	
	isSelected() {
		
		let gSel = this.select("#selBox");
		
		return (gSel.attr("visibility") === "visible")
	}
    
	select() {
		
		let gSel = this.g.select("#selBox");
		
		gSel.attr({ visibility: "visible" });
	}

	unselect() {
		
		let gSel = this.g.select("#selBox");
		
		gSel.attr({ visibility: "hidden" });
	}
	
	runOne(method) {
		return this[method]();
	}
}
