export default class GraphicIcon {
	
	static get CONTAINER_TYPE_SNAP() { return 1 }
	static get CONTAINER_TYPE_OPEN_LAYER() { return 2 }
	static get CONTAINER_TYPE_WEBIX_VIEW() { return 3 }
	
	constructor(layerElement, element, container) {

		if (this.constructor == GraphicIcon) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.layerElement = layerElement;
		this.element = element;
		this.container = container;
		
		this.g = undefined;
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
		
		Snap.load(graphicIcon.svgPatternVar, function(f) {
							
			let svg2 = f.select("#svg2");
					
			// Calculate original width and height
			let gWidth = svg2.attr("viewBox").width;
			let gHeight = svg2.attr("viewBox").height;
			
			self.g = f.select("#main");
			
			createSelection(gWidth, gHeight);
			
			placeInContainer(gWidth, gHeight);
			
			self.g.click(onClick);
			self.g.dblclick(onDblClick);
			
			onLoaded();
		});
		
		function createSelection(gWidth, gHeight) {
			
			let gSel = s.rect(0, 0, gWidth, gHeight);
			gSel.attr({ id: "selBox", visibility: "hidden", stroke: "rgb(0, 51, 103)", strokeWidth: 10, fill: "rgb(0, 102, 204)", opacity: 0.2 });
			self.g.append(gSel);
		}
		
		function placeInContainer(gWidth, gHeight) {
			
			let position = "t" + [self.layerElement.x, self.layerElement.y].join();
			let scale = "s" + ((graphicIcon.width > graphicIcon.height) ? (graphicIcon.width / gWidth) : (graphicIcon.height / gHeight));
			
			self.g.transform(position + ',' + scale);
			
			self.g.attr({
		        visibility: "visible",
				cursor: "pointer"
		    });

			s.add(self.g);
		}
		
		function onClick(e) {
			
			self.select();
		}
		
		function onDblClick(e) {
			
		}
	}
	
	update(element) {
		
		 throw new Error("Method 'update(element)' must be implemented.");
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
		
		let gSel = self.e.select("#selBox");
		
		gSel.attr({ visibility: "visible" });
	}

	unselect() {
		
		let gSel = self.e.select("#selBox");
		
		gSel.attr({ visibility: "hidden" });
	}
	
	runOne(method) {
		return this[method]();
	}
}

