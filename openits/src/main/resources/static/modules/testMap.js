import GenericModule from "../modules/genericModule.js";

export default class TestMap extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
		this.map = undefined;

	}


	getView() {

		let view = {
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
		let map = self.map;
		let divMap = "map-" + self.uid;

		if ($$(self.uid)) {

			$$("loading-" + self.uid).show();
			$$(self.uid).hide();

			let viewMain = {
				view: "abslayout",
				id: self.uid,
				cells: [
					{
						view: "template",
						relative: true,
						id: "mapDetail-" + self.uid,
						template:
							function() {

								return `	<div id='${divMap}' class='${divMap}' style='height: 100vh; width: 100vw'></div>
								<div style="display: none;">
								  <div style="width: 200px; height: 200px;">
									<svg id="svgContainer-1" viewBox="0 0 200 200" style="width: 100%; height: auto"></svg>
									<svg id="svgContainer-2" viewBox="0 0 200 200" style="width: 100%; height: auto"></svg>
									<svg id="svgContainer-3" viewBox="0 0 200 200" style="width: 100%; height: auto"></svg>
									<svg id="svgContainer-4" viewBox="0 0 200 200" style="width: 100%; height: auto"></svg>
									</div>
								</div>
										`
								//<svg id="svgContainer-2" viewBox="0 0 200 200" style="width: 100%; height: auto"></svg>
								//return "<div id='" + divMap + "' class='" + divMap + "' style='height: 100vh; width: 100vw'></div><svg id='svg'></svg>"
							},
						on: {
							onAfterRender: function() {

								map = new ol.Map({
									target: divMap,
									layers: [
										new ol.layer.Tile({
											source: new ol.source.OSM()
										})
									],
									view: new ol.View({
										center: ol.proj.fromLonLat([-4.869415, 36.520443]),
										zoom: 13
									}),
								});

								drawSvg();

								return map;
							}
						}
					}
				]
			}

			$$("loading-" + self.uid).hide();
			$$(self.uid).show();

			webix.ui(viewMain, $$(self.uid));
		}


		function drawSvg() {

			let s1 = Snap('#svgContainer-1')
			let circle1 = s1.circle(100, 100, 1).attr({
				fill: "red"
			});

			let s2 = Snap('#svgContainer-2')
			let circle2 = s2.circle(100, 100, 1).attr({
				fill: "blue"
			});

			let s3 = Snap('#svgContainer-3')
			let circle3 = s3.circle(100, 100, 1).attr({
				fill: "yellow"
			});

			let s4 = Snap('#svgContainer-4')
			let circle4 = s4.circle(100, 100, 1).attr({
				fill: "green"
			});


			function blink() {

				circle1.animate({ r: 1 }, 350, function() {
					circle1.animate({ r: 2 }, 350);
				});

				circle2.animate({ r: 1 }, 250, function() {
					circle2.animate({ r: 2 },250);
				});

				circle3.animate({ r: 1 }, 200, function() {
					circle3.animate({ r: 2 }, 200);
				});

				circle4.animate({ r: 1 }, 150, function() {
					circle4.animate({ r: 2 }, 150);
				});
			};

			setInterval(blink, 3000);

			let svgLonLat1 = [-4.899232, 36.516884];
			let svgPosition1 = ol.proj.fromLonLat(svgLonLat1);

			let svgLayer1 = new ol.Overlay({
				position: svgPosition1,
				positioning: 'center-center',
				element: document.getElementById('svgContainer-1'),
				stopEvent: false
			});

			let svgLonLat2 = [-4.869415, 36.520443];
			let svgPosition2 = ol.proj.fromLonLat(svgLonLat2);

			let svgLayer2 = new ol.Overlay({
				position: svgPosition2,
				positioning: 'center-center',
				element: document.getElementById('svgContainer-2'),
				stopEvent: false
			});

			let svgLonLat3 = [-4.861390, 36.521426];
			let svgPosition3 = ol.proj.fromLonLat(svgLonLat3);

			let svgLayer3 = new ol.Overlay({
				position: svgPosition3,
				positioning: 'center-center',
				element: document.getElementById('svgContainer-3'),
				stopEvent: false
			});

			let svgLonLat4 = [-4.852636, 36.524414];
			let svgPosition4 = ol.proj.fromLonLat(svgLonLat4);

			let svgLayer4 = new ol.Overlay({
				position: svgPosition4,
				positioning: 'center-center',
				element: document.getElementById('svgContainer-4'),
				stopEvent: false
			});

			map.addOverlay(svgLayer1);
			map.addOverlay(svgLayer2);
			map.addOverlay(svgLayer3);
			map.addOverlay(svgLayer4);

		}

	}
}
