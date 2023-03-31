import { util } from "../../lib/util.js";

const PICTOGRAM_TYPE_PATH = "img/incident_type";
const PICTOGRAM_UNKNOWN_TYPE = "img/incident_type/undefined.png";

export default class GraphicIconIncident {
	
	static get CONTAINER_TYPE_SNAP() { return 1 }
	static get CONTAINER_TYPE_OPEN_LAYER() { return 2 }
	static get CONTAINER_TYPE_WEBIX_VIEW() { return 3 }
	
	static get G_ID_LEVEL() { return "level" }
	static get G_ID_TYPE() { return "type" }
	static get G_ID_STATUS() { return "status" }
	
	static get G_ID_SELBOX() { return "selBox" }
	
	static get FONT_SIZE() { return 14 }
	
	constructor(layerIncident, incident, container, events) {
	
		this.layerIncident = layerIncident;
		this.incident = incident;
		this.container = container;
		this.events = events;
		this.graphicIconCustomType = undefined;
		
		if (container && container.obj && container.obj.node) {
			
			this.absWidth = container.obj.node.getAttribute("width");
			this.absHeight = container.obj.node.getAttribute("height");	
		}
		
		this.g = undefined;
		this.ratio = undefined;
		this.unselectEnabled = true;
	}
	
	loadRender(onRenderReady) {
		
		let self = this;
			
		let graphicIcon = self.layerIncident.graphicIcon;
		
		if (graphicIcon.jsCustomTypeRender) {
			
			const lazy = async _ => {

				let jsFile = util.getContextPath() + "/" + graphicIcon.jsCustomTypeRender;

				const mod = await import(jsFile);
				
				self.graphicIconCustomType = new mod.default(self.container.obj, self.g, self.incident, self.events);
				
				onRenderReady();
			}
			
			lazy();
		}
		else {
			
			console.error("Render not configured for graphicIcon = " + graphicIcon.id);
			onRenderReady();
		}
	}
	
	load(onLoaded) {
		
		let self = this;
		let s = self.container.obj;
		let graphicIcon = self.layerIncident.graphicIcon;
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
			
			self.g = s.group().attr( { id: "main" });
			afterLoad(graphicIcon.width, graphicIcon.height);
		}
		
		
		function afterLoad(w, h) {
			
			placeInContainer(w, h);
			createText(w, h);
			createTooltip();
			
			assignEvents();
			
			onLoaded();
			
			function createTooltip() {
				
				if (self.container.type !== GraphicIconIncident.CONTAINER_TYPE_WEBIX_VIEW && self.layerIncident.tooltip) {
						
					let tooltip = document.createElement("div");
					tooltip.setAttribute("id", "graphic-icon-tooltip");
					tooltip.setAttribute("class", "graphic-icon-tooltip");
					tooltip.setAttribute("display", "none");
					tooltip.setAttribute("style", "position: absolute; display: none;");
					document.body.appendChild(tooltip);
									
					self.g.hover(
						function(e) { 
							
							showTooltip(e);
				
							if (self.isVisible() && self.events && self.events[GraphicIconIncident.EventTypes.ON_MOUSE_OVER]) {
					
								eval(self.events[GraphicIconIncident.EventTypes.ON_MOUSE_OVER])();
							}	
 
						},
						function() {
							
							hideTooltip();
							
							if (self.isVisible() && self.events && self.events[GraphicIconIncident.EventTypes.ON_MOUSE_OUT]) {
					
								eval(self.events[GraphicIconIncident.EventTypes.ON_MOUSE_OUT])();
							}	
						}
					);
				}
			}
		}
		
		function showTooltip(e) {
			
			let tooltip = document.getElementById("graphic-icon-tooltip");
			
			if (tooltip) {
				
				let incidentTitle = i18n.t("LBL_INCIDENT_REPORT") + " [" + util.getSimpleId(self.incident.id) + "]" + " - " + self.incident.incidentType.alias;
				let incidentDescription = self.incident.incidentType.description;
				let incidentIcon = self.incident.incidentType.icon ? PICTOGRAM_TYPE_PATH + "/" + self.incident.incidentType.icon : PICTOGRAM_UNKNOWN_TYPE;
				let incidentLevelAlias = i18n.t(self.incident.incidentLevel.labelAlias);
				let incidentLevelColor = self.incident.incidentLevel.color ? self.incident.incidentLevel.color : "#00dddd";
				let incidentLocation = self.incident.location.alias;
				let incidentAffectedStretch = self.incident.affectionStretch.alias;
				let incidentUser = self.incident.currentUser.name;
				let incidentState = i18n.t(self.incident.incidentState.labelAlias);
				let isConfirmed = self.incident.confirm;
				let incidentGeneratedAt = self.incident.generatedAt;
			
				tooltip.innerHTML = `<div class="title">${incidentTitle}</div>
									<image class="icon" src="${incidentIcon}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
									<div class="state">
										<span class="badge" style="background: ${incidentLevelColor} ">${incidentLevelAlias}</span>
										<span style="padding-left: 15px">${util.toLocaleString(incidentGeneratedAt / 1000)}</span>
									</div>
									<div class="text_title">${incidentDescription}</div>
									<div style="padding-top: 10px"></div>
									<div class="text_details">
										<span class="ico webix_icon mdi mdi-list-status"></span><span class="field">${i18n.t("LBL_INCIDENT_STATE")}</span>
										<div class="value">${isConfirmed ? incidentState : i18n.t('LBL_INCIDENT_NO_CONFIRMED')}</div>
									</div>
									<div class="text_details">
										<span class="ico webix_icon  mdi mdi-map-marker"></span><span class="field">${i18n.t("LBL_INCIDENT_LOCATION")}</span>
										<div class="value">${incidentLocation}</div>
									</div>
									<div class="text_details">
										<span class="ico webix_icon  mdi mdi-road"></span><span class="field">${i18n.t("LBL_INCIDENT_STRETCH")}</span>
										<div class="value">${incidentAffectedStretch}</div>
									</div>
									<div class="text_details">
										<span class="ico webix_icon mdi mdi-account"></span><span class="field">${i18n.t("LBL_INCIDENT_USER_ASSIGNED")}</span>
										<div class="value">${incidentUser}</div>
									</div>
									<div style="padding-top: 10px"></div>`;
									
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
		
		function createText(gWidth, gHeight) {
			
			if (self.container.type !== GraphicIconIncident.CONTAINER_TYPE_WEBIX_VIEW && self.layerIncident.showText) {
				
				let x = gWidth / 2;			
				let y = gHeight + GraphicIconIncident.FONT_SIZE;
				
				if (self.container.type === GraphicIconIncident.CONTAINER_TYPE_OPEN_LAYER) {
					
					x *= self.ratio;
					y = gHeight * self.ratio + GraphicIconIncident.FONT_SIZE;
					
					if (self.layerIncident.horizontalFlip) {
					
						x -= (gWidth * self.ratio);
					}
				}
							
				let gName = s.text(x, y, self.incident.location.alias);
				let textColor = "#000000";
				
				gName.attr({ fontSize: GraphicIconIncident.FONT_SIZE + "px",fontFamily: "monospace", fontWeight: "bold", fill: textColor, "dominant-baseline": "middle", "text-anchor": "middle" });
				
				if (graphicIcon.keepAspectRatio !== false) {
					
					gName.transform("s" + (self.layerIncident.horizontalFlip ? "-" : "") + ((1 / self.ratio) + "," + (1 / self.ratio)));
				}
				else {
					
					gName.transform("s" + (self.layerIncident.horizontalFlip ? "-" : "") + ((1 / wRatio) + "," + (1 / hRatio)));					
				}
				
				self.g.append(gName);
			}
		}
		
		function placeInContainer(gWidth, gHeight) {
					
			let x = self.layerIncident.x ? self.layerIncident.x : 0;
			let y = self.layerIncident.y ? self.layerIncident.y : 0;
			
			if (self.container.type !== GraphicIconIncident.CONTAINER_TYPE_SNAP) {
				
				x = (self.absWidth - (gWidth * self.ratio)) / 2;	
			}
			
			if (self.layerIncident.horizontalFlip) {
					
				x += (gWidth * self.ratio);	
			}

			// Center y
			if (y === 0) {
				
				if (gHeight * self.ratio < graphicIcon.height) {
					
					y = (graphicIcon.height - (gHeight * self.ratio)) / 2;
				}	
			}
			
			let position = "t" + [x, y].join();
			
			let scale;
			
			if (graphicIcon.keepAspectRatio !== false) {
				
				scale = "s" + (self.layerIncident.horizontalFlip ? "-" : "") + (self.ratio + "," + self.ratio);
			}
			else {
				
				scale = "s" + (self.layerIncident.horizontalFlip ? "-" : "") + (wRatio + "," + hRatio);
			}
			
			self.g.transform(position + ',' + scale);
			
			self.g.attr({
				cursor: (self.container.type !== GraphicIconIncident.CONTAINER_TYPE_WEBIX_VIEW) ? "pointer" : "auto"
		    });

			s.add(self.g);
		}
		
		function assignEvents() {
			
			if (self.g && self.container.type !== GraphicIconIncident.CONTAINER_TYPE_WEBIX_VIEW) {
				
				self.g.dblclick(onDblClick);
				
				if (self.container.type === GraphicIconIncident.CONTAINER_TYPE_OPEN_LAYER && s &&
					self.events && self.events[GraphicIconIncident.EventTypes.ON_MOUSE_WHEEL]) {
					
					s.node.addEventListener("mousewheel", function() {
						
						eval(self.events[GraphicIconIncident.EventTypes.ON_MOUSE_WHEEL])();	
					}, {passive: true});
				}
			}
			
			function onDblClick(e) {
				
				let module = util.module.getFromModuleAction(self.layerIncident.incidentReportModuleAction.id);
				
				let args = { selectedPks: [self.incident.id] };
				
				util.module.open(module, self.layerIncident.incidentReportModuleAction, VIEW_TYPE_WINDOW, null, args);
			}
		}
	}
	
	updateIncident(incident) {
		
		this.incident = incident; 
		
		if (this.graphicIconCustomType) {
		
			this.graphicIconCustomType.render(this.incident);	
		}
	}
	
	show() {
		
		let graphic = (this.container.type === GraphicIconIncident.CONTAINER_TYPE_SNAP ? this.g : this.container.obj);
		
		if (graphic.attr("display") == "none") {
			
			graphic.attr({ display: "inline" });
		}
	}
	
	hide() {
		
		let graphic = (this.container.type === GraphicIconIncident.CONTAINER_TYPE_SNAP ? this.g : this.container.obj);
		
		if (graphic.attr("display") != "none") {
			
			graphic.attr({ display: "none" });
		}
	}
	
	isVisible() {
		
		let graphic = (this.container.type === GraphicIconIncident.CONTAINER_TYPE_SNAP ? this.g : this.container.obj);
		
		return (graphic.attr("display") != "none");
	}

	runOne(method) {
		return this[method]();
	}
}

Object.defineProperty(GraphicIconIncident, 'EventTypes', {
    value: {}
});

Object.defineProperty(GraphicIconIncident.EventTypes, "ON_CLIC", {
    value: 1,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(GraphicIconIncident.EventTypes, "ON_DBL_CLIC", {
    value: 2,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(GraphicIconIncident.EventTypes, "ON_MOUSE_OVER", {
    value: 3,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(GraphicIconIncident.EventTypes, "ON_MOUSE_OUT", {
    value: 4,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(GraphicIconIncident.EventTypes, "ON_MOUSE_WHEEL", {
    value: 5,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(GraphicIconIncident.EventTypes, "ON_ZONE_EDIT_CLIC", {
    value: 6,
    writable : false,
    enumerable : true,
    configurable : false
});

Object.defineProperty(GraphicIconIncident.EventTypes, "ON_ZONE_DELETE_CLIC", {
    value: 7,
    writable : false,
    enumerable : true,
    configurable : false
});