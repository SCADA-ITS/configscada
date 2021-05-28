import { util } from "../../lib/util.js";

export default class GraphicIcon {
	
	static get CONTAINER_TYPE_SNAP() { return 1 }
	static get CONTAINER_TYPE_OPEN_LAYER() { return 2 }
	static get CONTAINER_TYPE_WEBIX_VIEW() { return 3 }
	
	static get CUSTOM_SVG_FILE() { return "img/graphic_icon/custom.svg" }
	
	static get G_ID_STATE() { return "state" }
	static get G_ID_SELBOX() { return "selBox" }
	
	static get FONT_SIZE() { return 14 }
	
	static DEFAULT_STATE_COLOR(state) {
		
		let colors = ["#800080", "#008000", "#ff0000", "#ffcc00"];
		
		return colors[state];
	}
	
	constructor(layerElement, element, container, events) {

		if (this.constructor == GraphicIcon) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.layerElement = layerElement;
		this.element = element;
		this.elementTypeState = element.elementTypeState;
		this.elementValues = element.elementValues;
		this.alarms = undefined;
		this.container = container;
		this.events = events;
		
		if (container && container.obj && container.obj.node) {
			
			this.absWidth = container.obj.node.getAttribute("width");
			this.absHeight = container.obj.node.getAttribute("height");	
		}
		
		this.g = undefined;
		this.ratio = undefined;
	}
	
	loadRender(onRenderReady) {

		onRenderReady();		
	}
	
	load(onLoaded) {
		
		let self = this;
		let s = self.container.obj;
		let graphicIcon = self.layerElement.graphicIcon;
		
		let svgFile = graphicIcon.svgFile;
		
		if (!svgFile || svgFile == '' || !util.resourceExist(svgFile)) {
			
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
				afterLoad(gWidth, gHeight);
			});
		}
		else {
			
			self.g = s.group().attr( { id: "main" });
			afterLoad(graphicIcon.width, graphicIcon.height);
		}
		
		
		function afterLoad(w, h) {
			
			createSelection(w, h);
			placeInContainer(w, h);
			createText(w, h);
			createTooltip();
			
			self.g.click(onClick);
			//self.g.touchstart(onClick);
			self.g.dblclick(onDblClick);
			
			onLoaded();
			
			function createTooltip() {
				
				if (self.container.type !== GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW && self.layerElement.tooltip) {
						
					let tooltip = document.createElement("div");
					tooltip.setAttribute("id", "graphic-icon-tooltip");
					tooltip.setAttribute("class", "graphic-icon-tooltip");
					tooltip.setAttribute("display", "none");
					tooltip.setAttribute("style", "position: absolute; display: none;");
					document.body.appendChild(tooltip);
									
					self.g.hover(
						function(e) { 
							
							showTooltip(e);
					
									
							if (self.isVisible() && self.events && self.events[GraphicIcon.EventTypes.ON_MOUSE_OVER]) {
					
								eval(self.events[GraphicIcon.EventTypes.ON_MOUSE_OVER])();
							} 
						},
						function() {
							
							hideTooltip();
							
							if (self.isVisible() && self.events && self.events[GraphicIcon.EventTypes.ON_MOUSE_OUT]) {
					
								eval(self.events[GraphicIcon.EventTypes.ON_MOUSE_OUT])();
							}
						}
					);
				}
			}
			
			function showTooltip(e) {
				
				let tooltip = document.getElementById("graphic-icon-tooltip");
				
				let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.element.elementType.id);
				let elementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", self.elementTypeState.id);
								
				if (elementType && elementTypeState) {
					
					let elementTypeStateColor;
				
					if (!(elementTypeStateColor = elementTypeState.color)) {
						
						elementTypeStateColor = GraphicIcon.DEFAULT_STATE_COLOR(elementTypeState.id.substring(elementTypeState.id.lastIndexOf(":") + 1));  
					}
					
					
					tooltip.innerHTML = `<div class="title">${i18n.t(elementType.labelAlias)}</div>
										<image class="icon" src="img/${elementType.elementTypePhoto}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
										<div class="state">
											<span class="badge" style="background: ${elementTypeStateColor} ">${i18n.t(elementTypeState.labelAlias)}</span>
										</div>
										<div class="text">${self.element.alias}</div>`;
										
					tooltip.style.display = "block";
					tooltip.style.left = e.pageX + 10 + 'px';
					tooltip.style.top = e.pageY + 10 + 'px';
					tooltip.style.zIndex = 99999;	
				}
			}
			
			function hideTooltip() {
				
				var tooltip = document.getElementById("graphic-icon-tooltip");
				tooltip.style.display = "none";
			}
		}
		
		function createSelection(gWidth, gHeight) {
			
			if (self.container.type !== GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW) {
				
				let gSel = s.rect(0, 0, gWidth, gHeight);
				gSel.attr({ id: GraphicIcon.G_ID_SELBOX, visibility: "hidden", stroke: "#FF9800", strokeWidth: 10, fill: "#FF9800", opacity: 0.6 });
				self.g.prepend(gSel);	
			}
			
		}

		function createText(gWidth, gHeight) {
			
			if (self.container.type !== GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW && self.layerElement.showText) {
				
				let x = gWidth / 2;			
				let y = gHeight + GraphicIcon.FONT_SIZE;
				
				if (self.container.type === GraphicIcon.CONTAINER_TYPE_OPEN_LAYER) {
					
					x *= self.ratio;
					y = gHeight * self.ratio + GraphicIcon.FONT_SIZE;
				}
							
				let gName = s.text(x, y, self.element.alias);
				let textColor = self.layerElement.textColor ? self.layerElement.textColor : "#ffcc00";
				
				gName.attr({ fontSize: GraphicIcon.FONT_SIZE + "px",fontFamily: "monospace", fontWeight: "bold", fill: textColor, "dominant-baseline": "middle", "text-anchor": "middle" });
				gName.transform("s" + (1 / self.ratio));
			
				self.g.append(gName);	
			}
		}
		
		function placeInContainer(gWidth, gHeight) {
			
			let wRatio = graphicIcon.width / gWidth;
			let hRatio = graphicIcon.height / gHeight;

			//self.ratio = graphicIcon.width < graphicIcon.height ? wRatio : hRatio;
			//self.ratio = gWidth > gHeight ?  wRatio : hRatio;
			
			self.ratio = wRatio > hRatio ? hRatio : wRatio;
			let x = self.layerElement.x ? self.layerElement.x : 0;
			let y = self.layerElement.y ? self.layerElement.y : 0;
			
			if (self.container.type !== GraphicIcon.CONTAINER_TYPE_SNAP) {
				
				x = (self.absWidth - (gWidth * self.ratio)) / 2;
			}

			// Center y
			if (y === 0) {
				
				if (gHeight * self.ratio < graphicIcon.height) {
					
					y = (graphicIcon.height - (gHeight * self.ratio)) / 2;
				}
			}
			
			let position = "t" + [x, y].join();
			
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
			
			if (self.container.type !== GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW) {
			
				// Custom event
				if (self.events && self.events[GraphicIcon.EventTypes.ON_CLIC]) {
					
					eval(self.events[GraphicIcon.EventTypes.ON_CLIC])();
				}
				else {
				
					let gSel = self.g.select("#" + GraphicIcon.G_ID_SELBOX);
			
					if (gSel.attr("visibility") === "hidden") {
						
						self.select();	
					}	
				}	
			}
			
		}
		
		function onDblClick(e) {
			
			if (self.container.type !== GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW) {
				
				// Only custom events
				if (self.events && self.events[GraphicIcon.EventTypes.ON_DBL_CLIC]) {
					
					eval(self.events[GraphicIcon.EventTypes.ON_DBL_CLIC])();
				}	
			}
		}
	}
	
	updateState(elementTypeState) {
		
		this.elementTypeState = elementTypeState; 
	}
	
	updateValues(elementValues) {
		
		this.elementValues = elementValues;
	}
	
	updateAlarms(alarms) {
		
		this.alarms = alarms;
	}

	show() {

		if (this.g.attr("visibility") != "visible") {
			
			this.g.attr({ visibility: "visible" });
		
			let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);
			
			gSel.attr({ visibility: "hidden" });
		}
	}

	hide() {
		
		if (this.g.attr("visibility") != "hidden") {
			
			this.g.attr({ visibility: "hidden" });
		
			let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);
			
			gSel.attr({ visibility: "hidden" });
		}
	}
	
	isVisible() {
		
		return (this.g.attr("visibility") === "visible");
	}
	
	isSelected() {
		
		let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);
		
		return (gSel.attr("visibility") === "visible")
	}
    
	select() {
		
		if (this.g.attr("visibility") === "visible") {
			
			let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);
		
			gSel.attr({ visibility: "visible" });	
		}
	}

	unselect() {
		
		if (this.g.attr("visibility") === "visible") {
		
			let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);
		
			gSel.attr({ visibility: "hidden" });	
		}
	}
	
	runOne(method) {
		return this[method]();
	}
}

Object.defineProperty(GraphicIcon, 'EventTypes', {
    value: {}
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_CLIC", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_DBL_CLIC", {
    value: 2,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_MOUSE_OVER", {
    value: 3,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_MOUSE_OUT", {
    value: 4,
    writable : false,
    enumerable : true,
    configurable : false
});