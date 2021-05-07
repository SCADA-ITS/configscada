import GenericModule from "../modules/genericModule.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";
import CalendarDialogBox from "../component/calendarDialogBox.js";


export default class Test1Module extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
		
		this.calendar = undefined;
	}

	getView() {

		let view = undefined;

		view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: false
								},
								{
									id: "loading-" + this.uid,
									rows: [getViewDrawNoData()],
									hidden: true
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};

		function getViewDrawNoData() {

			return {
				view: "template",
				template: function() {

					let icon = "<img src='img/loading.svg' align='center'>";

					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
				}
			}
		}

		return view;
	}
	
		loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();

			webix.ajax().get(appConfig.restUrl + util.fillTemplate("trees/${id}", { id: "2" })).then(function(dat) {

				if (dat != null) {

					let metadata = dat.json().metadata;
					let data = dat.json().data;

					drawComponents(metadata, data);
				}
			});
		}

		function drawComponents(metadata, data) {

			let events = {};
			
			events[TreeView.EventTypes.ON_CHECK_ITEM] = function(items) {

				console.log(items.join());
			};

			let treeView = new TreeView(metadata, data, events);

			let viewMain = {
				id: self.uid,
				cols: [
					{
						width: 400,
						rows: [
							treeView.getView()
						]
					},
					drawSVG(),
					drawCalendar(),
					drawButtonCalendar()
				]
			};
			
			function drawButtonCalendar() {
				return {
					view: "button",
					click: function() {
						
						console.log(self.calendar.getSelectedDates());
					}
				}
			}
			
			function drawCalendar() {
		
				self.calendar = new CalendarDialogBox(i18n.t("LBL_DIALOG_MUTE_ALARMS"), i18n.t("LBL_DIALOG_UNMUTE_ALARMS"), false, 
							
					function(date) {
						
						return (Math.floor(date) < Math.floor(new Date()));
					},
					function(dates) {
						
						console.log(dates) ;
		
					}
				); 
				
				return self.calendar.getView();
			}
			
			function drawSVG() {
				
				let elements = [];
				
				for (let i = 0; i < 20; i++) {
					
					elements.push({
						img: "./img/cam_0.svg",
						x: Math.round(Math.random() * (1600 - 100) + 100),
						y: Math.round(Math.random() * (800 - 100) + 100),
						w: 75,
						h: 75,
						elementId: "Element:9:" + (i + 1),
						layer: undefined
					});
				}
				
				function test1(ctx) {
					
					let s = Snap("#" + "snap-" + self.uid);
					s.attr({ width: ctx.$width, height: ctx.$height });
					//let grp = s.group().attr({ id: 'grp' });
					
					
					let elementsLoaded = 0;
					let layerSyn;
					let flagMouseMove = false;
					
					createTooltip();
				
					Snap.load("./img/tunel.svg", function(f) {
						
						//layerSyn = s.append(f);
						
						layerSyn = f.select("#main");
						
						s.append(layerSyn);
						
						//layerSyn.click(unselectAll);
						
						layerSyn.mousemove(function() {
							
							flagMouseMove = true;
						});
						
						layerSyn.mousedown(function() {
							
							flagMouseMove = false;
						});
						
						layerSyn.mouseup(mouseUp);
						
						elements.forEach(element => {
							
							Snap.load(element.img, function(f) {
								
								onImageLoaded(f, element);
							});
						});
						
						//s.add(grp);
					});
					
					function createTooltip() {
						
						let tooltip = document.createElement("div");
						tooltip.setAttribute("id", "syn-tooltip");
						tooltip.setAttribute("class", "syn-tooltip");
						tooltip.setAttribute("display", "none");
						tooltip.setAttribute("style", "position: absolute; display: none;");
						document.body.appendChild(tooltip);
					}
					
					function showTooltip(evt, text) {
						let tooltip = document.getElementById("syn-tooltip");
						tooltip.innerHTML = text;
						tooltip.style.display = "block";
						tooltip.style.left = evt.pageX + 10 + 'px';
						tooltip.style.top = evt.pageY + 10 + 'px';
					}
					
					function hideTooltip() {
						var tooltip = document.getElementById("syn-tooltip");
						tooltip.style.display = "none";
					}
					
					function onImageLoaded(f, element) {
						
						elementsLoaded++;
						
						let svg2 = f.select("#svg2");
						
						// Calculate original width and height
						let gWidth = svg2.attr("viewBox").width;
						let gHeight = svg2.attr("viewBox").height;
						
						let g = f.select("#main");
						
						let gSel = s.rect(0, 0, gWidth, gHeight);
						gSel.attr({ id: "selBox", visibility: "hidden", stroke: "rgb(0, 51, 103)", strokeWidth: 10, fill: "rgb(0, 102, 204)", opacity: 0.2 });
						g.append(gSel);
						
						// End Calculate original width and height
	
						//let scaleH = element.h / gHeight;
						//let scaleW = element.w / gWidth;
						 
						let position = "t" + [element.x, element.y].join();
						//let scale = "s" + [scaleW, scaleH].join();
						let scale = "s" + element.h / gHeight;
						
						g.transform(position + ',' + scale);
						
						g.attr({
					        visibility: "visible",
							cursor: "pointer",
							elementId: element.elementId
					    });
							
						//grp.add(g);
						s.add(g);
						
						element.layer = g;
						
						g.click(onClick);
						g.dblclick(onDblClick);
						
						g.hover(function(e) { showTooltip(e, element.elementId) }, hideTooltip)
						
						
						if (elementsLoaded === elements.length) {
						
							let optionsPanZoom = {
								controlIconsEnabled: true,
								dblClickZoomEnabled: false
							}
							
							var panZoomTiger = svgPanZoom("#" + "snap-" + self.uid, optionsPanZoom);
							
							simulate();	
						}
					}
					
					function onClick(e) {
						
						let elementId = this.attr('elementId');
						
						let gSel = this.select("#selBox");
						
						gSel.attr({ visibility: gSel.attr("visibility") === "visible" ? "hidden" : "visible" });
						
						//let selRect = s.rect(100,100,100,100,20,20).attr({ stroke: '#123456', 'strokeWidth': 20, fill: 'red', 'opacity': 0.2 });
						
						//this.addClass("syn-tooltip-selected-item");
					}
					
					function onDblClick(e) {
						
						let elementId = this.attr('elementId');
						
						console.log(elementId);
						
						let module = window.em.getByField(EntityType.MODULE, "id", 1014);
						
						if (module) {
							
							let args = {
								selectedPks: [
									elementId
								]
							}
							
							let moduleAction = module.moduleActions.find(e => e.id === 101401);
						
							util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
						}
					}
					
					function mouseUp(evt) {
					
						if (!flagMouseMove) {
							
							var allItems = s.selectAll("#grp *");
						
							allItems.forEach(e => {
								
								if (e.attr('id') === 'selBox') {
									
									e.attr({ visibility: "hidden" });
								}
							});	
						}
					}
					
					function simulate() {
						
						const ICON_COLOR = [
						  "gray",
						  "#ffcc00",
						  "#008000",
						  "#800080",
						  "#ff0000"
						]
						
						setInterval(function(){
	
							elements.forEach(element => {
							
								let g = element.layer;
								
								if (g) {
									
									let layerState = g.select("#path7");
									
									if (layerState) {
										
										layerState.attr({
											
											fill: ICON_COLOR[Math.round(Math.random() * ICON_COLOR.length)]
										})
									}
								}
							});
							
						}, 500);
					}

					console.log("w=" + ctx.$width + " h=" + ctx.$height);
				}
							
				let viewSVG = {
					view: "template",
					id: "svg-" + self.uid,
					template: function() {
						return `<svg id=${"snap-" + self.uid}></svg>`
					},
					on: {
						onAfterRender: function() {
				        
							test1(this);
						}
					}
				}
				
				return viewSVG;
			}
			
			$$("loading-" + self.uid).hide();
			
			webix.ui(viewMain, $$(self.uid));
			
			$$(self.uid).show();
			
			
			
//			let w = $$("svg-" + self.uid).$width;
//			let h = $$("svg-" + self.uid).$height;
//			
//			snapMain.attr({ width: w, height: h });
			
			
		    //snapMain.image("./img/tunel.svg", 0, 0, w, h).attr({id: "svg-main-" + self.uid });
		}
	}
}
