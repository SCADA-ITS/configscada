import GenericModule from "../modules/genericModule.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";


export default class Test1Module extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
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
					drawSVG()
				]
			};
			
			function drawSVG() {
				
				let elements = [];
				
				for (let i = 0; i < 20; i++) {
					
					elements.push({
						img: "./img/cam_0.svg",
						x: Math.round(Math.random() * (1700 - 100) + 100),
						y: Math.round(Math.random() * (900 - 100) + 100),
						w: 75,
						h: 75,
						elementId: "Element:9:" + (i + 1),
						layer: undefined
					});
				}
				
				function test1(ctx) {
					
					let snapCanvas = Snap("#" + "snap-" + self.uid);
					snapCanvas.attr({ width: ctx.$width, height: ctx.$height });
					let g = snapCanvas.group();
					let elementsLoaded = 0;
					
					createTooltip();
				
					Snap.load("./img/tunel.svg", function(f) {
						
						let layer0 = snapCanvas.append(f);
						
						elements.forEach(element => {
							
							Snap.load(element.img, function(f) {
								
								onImageLoaded(f, element);
							});
						});
						
						snapCanvas.add(g);
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
						
						var layerElement = f.select("#main");

						// Calculate original width and height 		
						layerElement.attr({
					        visibility: "hidden"
					    });
	
						snapCanvas.append(layerElement);
	
						let camOriginalH = layerElement.getBBox().height;
						let camOriginalW = layerElement.getBBox().width;
						
						layerElement.remove();
						// End Calculate original width and height
	
						let scaleH = element.h / camOriginalH;
						let scaleW = element.w / camOriginalW;
						 
						let position = "t" + [element.x, element.y].join();
						let scale = "s" + [scaleW, scaleH].join();
						
						layerElement.transform(position + ',' + scale);
						
						layerElement.attr({
					        visibility: "visible",
							cursor: "pointer",
							elementId: element.elementId
					    });
	
//						let tooltip = Snap.parse(`<title>${element.elementId}</title>`);
//						layerElement.append(tooltip);
						
						g.add(layerElement);
						
						element.layer = layerElement;
						
						layerElement.click(onClick);
						layerElement.dblclick(onDblClick);
						
						layerElement.hover(function(e) { showTooltip(e, element.elementId) }, hideTooltip)
						
						
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
						
						this.addClass("syn-tooltip-selected-item");
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
							
								let layerElement = element.layer;
								
								if (layerElement) {
									
									let layerState = layerElement.select("#path7");
									
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
