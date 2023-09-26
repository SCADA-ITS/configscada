import { util } from "../../lib/util.js";

const MOUSE_EVENT_LEFT_PRESSED = 1;
const MOUSE_EVENT_MIDDLE_PRESSED = 2;
const MOUSE_EVENT_RIGHT_PRESSED = 3;

export default class GraphicIcon {

	static get CONTAINER_TYPE_SNAP() { return 1 }
	static get CONTAINER_TYPE_OPEN_LAYER() { return 2 }
	static get CONTAINER_TYPE_WEBIX_VIEW() { return 3 }

	static get CUSTOM_SVG_FILE() { return "img/graphic_icon/custom.svg" }

	static get G_ID_STATE() { return "state" }
	static get G_ID_SELBOX() { return "selBox" }

	static get FONT_SIZE() { return 14 }

	constructor(layerElement, element, container, events) {

		if (this.constructor == GraphicIcon) {

			throw new Error("Abstract classes can't be instantiated.");
		}

		this.layerElement = layerElement;
		this.element = element;
		this.elementState = element.elementState;
		this.elementTypeState = element.elementState.currentElementTypeState;
		this.elementValues = element.elementValues;
		this.alarms = undefined;
		this.container = container;
		this.events = events;
		this.lastUpdated = window.sm.getService(Services.TIME_SERVICE).getEpoch();

		if (container && container.obj && container.obj.node) {

			this.absWidth = container.obj.node.getAttribute("width");
			this.absHeight = container.obj.node.getAttribute("height");
		}

		this.g = undefined;
		this.ratio = undefined;
		this.unselectEnabled = true;
	}

	loadRender(onRenderReady) {

		onRenderReady();
	}

	load(onLoaded) {

		let self = this;
		let s = self.container.obj;
		let graphicIcon = self.layerElement.graphicIcon;
		let wRatio, hRatio;
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

				wRatio = graphicIcon.width ? (graphicIcon.width / gWidth) : undefined;
				hRatio = graphicIcon.height ? (graphicIcon.height / gHeight) : undefined;

				if (graphicIcon.keepAspectRatio !== false) {

					self.ratio = wRatio > hRatio ? hRatio : wRatio;
				}
				else {

					self.ratio = wRatio ? wRatio : hRatio;
				}

				self.g = f.select("#main");
				afterLoad(gWidth, gHeight);
			});
		}
		else {

			self.g = s.group().attr({ id: "main" });
			afterLoad(graphicIcon.width, graphicIcon.height);
		}


		function afterLoad(w, h) {

			createSelection(w, h);
			placeInContainer(w, h);
			createText(w, h);
			createTooltip();

			assignEvents();

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
		}

		function showTooltip(e) {

			let tooltip = document.getElementById("graphic-icon-tooltip");

			let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.element.elementType.id);
			let elementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", self.elementTypeState.id);


			let label = i18n.t(elementType.labelDescription ? elementType.labelDescription : self.element.elementType.labelAlias);


			if (elementType && self.element.elementSubtype && self.element.elementSubtype.id) {

				let elementSubtype = elementType.elementSubtypes.find(item => item.id === self.element.elementSubtype.id);

				if (elementSubtype) {
					label += " (" + i18n.t(elementSubtype.labelDescription ? elementSubtype.labelDescription : elementSubtype.labelAlias) + ")";
				}

			}

			if (elementType && elementTypeState) {

				let alias = self.elementState ? util.element.getAliasState(self.elementState) : i18n.t(elementTypeState.labelAlias);

				let color = self.elementState ? util.element.getColorByState(self.elementState) : util.element.getColor(elementTypeState);

				tooltip.innerHTML = `<div class="title">${self.element.alias}</div>
									<image class="icon" src="img/${elementType.elementTypePhoto}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
									<div class="state">
										<span class="badge" style="background: ${color} ">${alias}</span>
										<span style="padding-left: 15px">${util.toLocaleString(self.lastUpdated / 1000)}</span>
									</div>
									<div class="text_title">${label}</div>`;

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

		function createSelection(gWidth, gHeight) {

			if (self.container.type !== GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW && (self.layerElement.selectable === true || self.layerElement.selectable == undefined)) {

				let gSel = undefined;
				
				gSel = s.rect(0, 0, gWidth, gHeight);
				
				gSel.attr({ 
					id: GraphicIcon.G_ID_SELBOX, 
					visibility: "hidden", 
					stroke: "#FF9800", 
					strokeWidth: self.container.type === GraphicIcon.CONTAINER_TYPE_OPEN_LAYER ? 0 : 10, 
					fill: "#FF9800", 
					opacity: 0.4 
				});
				
				// self.g.prepend(gSel);
				self.g.append(gSel);
			}

		}

		function createText(gWidth, gHeight) {

			if (self.container.type !== GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW && self.layerElement.showText) {

				let x = gWidth / 2;
				let y = gHeight + GraphicIcon.FONT_SIZE;

				if (self.container.type === GraphicIcon.CONTAINER_TYPE_OPEN_LAYER) {

					x *= self.ratio;
					y = gHeight * self.ratio + GraphicIcon.FONT_SIZE;

					if (self.layerElement.horizontalFlip) {

						x -= (gWidth * self.ratio);
					}
				}

				let gName = s.text(x, y, self.element.alias);
				let textColor = self.layerElement.textColor ? self.layerElement.textColor : "#ffcc00";

				gName.attr({ fontSize: GraphicIcon.FONT_SIZE + "px", fontFamily: "monospace", fontWeight: "bold", fill: textColor, "dominant-baseline": "middle", "text-anchor": "middle" });

				if (graphicIcon.keepAspectRatio !== false) {

					gName.transform("s" + (self.layerElement.horizontalFlip ? "-" : "") + ((1 / self.ratio) + "," + (1 / self.ratio)));
				}
				else {

					gName.transform("s" + (self.layerElement.horizontalFlip ? "-" : "") + ((1 / wRatio) + "," + (1 / hRatio)));
				}

				self.g.append(gName);
			}
		}

		function placeInContainer(gWidth, gHeight) {

			let x = self.layerElement.x ? self.layerElement.x : 0;
			let y = self.layerElement.y ? self.layerElement.y : 0;

			if (self.container.type !== GraphicIcon.CONTAINER_TYPE_SNAP) {

				x = (self.absWidth - (gWidth * self.ratio)) / 2;
			}

			if (self.layerElement.horizontalFlip) {

				x += (gWidth * self.ratio);
			}

			// Center y
			if (y === 0) {

				if (gHeight * self.ratio < graphicIcon.height) {

					y = (graphicIcon.height - (gHeight * self.ratio)) / 2;
				}
			}

			let position = "t" + [x, y].join();


			let scaleW = graphicIcon.keepAspectRatio !== false ? self.ratio : wRatio;
			let scaleH = graphicIcon.keepAspectRatio !== false ? self.ratio : hRatio;
			let scale = "s" + (self.layerElement.horizontalFlip ? "-" : "") + (scaleW + "," + scaleH);

			self.g.transform(position + ',' + scale);

			if (self.layerElement.horizontalFlip) {

				flipTexts(self.g, gWidth);
			}

			self.g.attr({
				cursor: ((self.container.type !== GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW) && (self.layerElement.selectable === true || self.layerElement.selectable == undefined)) ? "pointer" : "auto"
			});

			s.add(self.g);


		}

		function flipTexts(gElement, gWidth) {

			if (gElement) {

				let childs = gElement.selectAll("*");

				if (childs && Array.isArray(childs.items)) {

					childs.items.filter(e => e.type === "text").forEach(textNode => {

						if (textNode.node) {

							let gText = gElement.select("#" + textNode.node.id);

							if (gText) {

								let pos = "t" + [gWidth, 0].join();

								gText.transform(pos + ",s-1,1");
							}
						}
					});
				}
			}
		}

		function assignEvents() {

			if (self.g && self.container.type !== GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW && (self.layerElement.selectable === true || self.layerElement.selectable == undefined)) {

				self.g.click(onClick);
				self.g.dblclick(onDblClick);

				if (self.container.type === GraphicIcon.CONTAINER_TYPE_OPEN_LAYER && s &&
					self.events && self.events[GraphicIcon.EventTypes.ON_MOUSE_WHEEL]) {

					s.node.addEventListener("mousewheel", function() {

						eval(self.events[GraphicIcon.EventTypes.ON_MOUSE_WHEEL])();
					}, { passive: true });


				}

				self.g.mousedown(function(e) {

					if (e.which === MOUSE_EVENT_RIGHT_PRESSED) {

						hideTooltip();
						onClick(e);
					}
				});
			}

			function onClick(e) {
				// Custom event
				if (self.events && self.events[GraphicIcon.EventTypes.ON_CLIC]) {

					eval(self.events[GraphicIcon.EventTypes.ON_CLIC])();
				}
				else {

					const LEFT_BUTTON = 0;
					let gSel = self.g.select("#" + GraphicIcon.G_ID_SELBOX);

					if (gSel && gSel.attr("visibility") === "hidden") {

						self.select();

						self.unselectEnabled = true;

					} else if (gSel && gSel.attr("visibility") === "visible" && e.button == LEFT_BUTTON) {

						webix.delay(function() {

							if (self.unselectEnabled) {

								self.unselect();
							}

						}, null, null, 100);
					}
				}
			}

			function onDblClick(e) {

				// Only custom events
				if (self.events && self.events[GraphicIcon.EventTypes.ON_DBL_CLIC]) {

					eval(self.events[GraphicIcon.EventTypes.ON_DBL_CLIC])();

					self.unselectEnabled = false;
				}
			}
		}
	}

	updateState(elementTypeState) {

		this.elementTypeState = elementTypeState;

		if (this.elementState) {
			this.elementState.currentElementTypeState = elementTypeState;
		}

		this.lastUpdated = window.sm.getService(Services.TIME_SERVICE).getEpoch();
	}

	updateElementState(elementState) {

		this.elementState = elementState;
		this.elementTypeState = this.elementState.currentElementTypeState;

		this.lastUpdated = window.sm.getService(Services.TIME_SERVICE).getEpoch();
	}

	updateValues(elementValues) {

		this.elementValues = elementValues;
		this.lastUpdated = window.sm.getService(Services.TIME_SERVICE).getEpoch();
	}

	updateAlarms(alarms) {

		this.alarms = alarms;
		this.lastUpdated = window.sm.getService(Services.TIME_SERVICE).getEpoch();
	}

	show() {

		let graphic = (this.container.type === GraphicIcon.CONTAINER_TYPE_SNAP ? this.g : this.container.obj);

		if (graphic.attr("display") == "none") {

			graphic.attr({ display: "inline" });

			let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);

			if (gSel) {

				gSel.attr({ visibility: "hidden" });
			}
		}
	}

	stop() {

	}

	hide() {

		let graphic = (this.container.type === GraphicIcon.CONTAINER_TYPE_SNAP ? this.g : this.container.obj);

		if (graphic.attr("display") != "none") {

			graphic.attr({ display: "none" });

			let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);

			if (gSel) {

				gSel.attr({ visibility: "hidden" });
			}
		}
	}

	isVisible() {

		let graphic = (this.container.type === GraphicIcon.CONTAINER_TYPE_SNAP ? this.g : this.container.obj);

		return (graphic.attr("display") != "none");
	}

	isSelected() {

		let selected = false;

		let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);

		if (gSel) {

			selected = (gSel.attr("visibility") === "visible");
		}

		return selected;
	}

	select() {

		let graphic = (this.container.type === GraphicIcon.CONTAINER_TYPE_SNAP ? this.g : this.container.obj);

		if (graphic.attr("display") != "none") {

			let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);

			if (gSel) {

				gSel.attr({ visibility: "visible" });
			}
		}
	}

	unselect() {

		let graphic = (this.container.type === GraphicIcon.CONTAINER_TYPE_SNAP ? this.g : this.container.obj);

		if (graphic.attr("display") != "none") {

			let gSel = this.g.select("#" + GraphicIcon.G_ID_SELBOX);

			if (gSel) {

				gSel.attr({ visibility: "hidden" });
			}
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
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_DBL_CLIC", {
	value: 2,
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_MOUSE_OVER", {
	value: 3,
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_MOUSE_OUT", {
	value: 4,
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_MOUSE_WHEEL", {
	value: 5,
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_ZONE_EDIT_CLIC", {
	value: 6,
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(GraphicIcon.EventTypes, "ON_ZONE_DELETE_CLIC", {
	value: 7,
	writable: false,
	enumerable: true,
	configurable: false
});