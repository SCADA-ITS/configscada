import GenericModule from "../modules/genericModule.js";

const CIRCLES = [
	{
		id: "svgContainer-1",
		lonlat: [-4.899232, 36.516884],
		w: 200,
		h: 200,
		r: 100
	},
	{
		id: "svgContainer-2",
		lonlat: [-4.869415, 36.520443],
		w: 200,
		h: 200,
		r: 100
	},
	{
		id: "svgContainer-3",
		lonlat: [-4.861390, 36.521426],
		w: 200,
		h: 200,
		r: 100
	},
	{
		id: "svgContainer-4",
		lonlat: [-4.852636, 36.524414],
		w: 200,
		h: 200,
		r: 100
	}
]

export default class TestMap extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
		this.map = undefined;
		this.buttonPressed = false;
		this.draw;


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
										//extent: [ -553203.4061760335, 4366939.736625225, -531934.8030556832, 4374793.6412815275 ],
										zoom: 13
									})
								});

								drawSvg();

								return map;
							}
						}
					},
					{
						view: "toolbar",
						id: "syn-toolbar-main-" + self.uid,
						css: "syn-toolbar-zoom",
						borderless: true,
						top: 10,
						left: 10,
						width: 120,
						height: 85,
						rows: [
							{ height: 23 },
							{
								cols: [
									{ width: 40 },
									{
										id: "reset-" + self.uid,
										view: "button",
										type: "icon",
										icon: "mdi mdi-restore",
										tooltip: i18n.t("LBL_RESET"),
										css: "button-zoom-reset",
										width: 40, height: 40,
										on: {
											onItemClick: function(obj) {

												map.getView().animate({
													center: ol.proj.fromLonLat([-4.869415, 36.520443]),
													zoom: 15,
													duration: 3000
												});
											}
										}
									},
									{
										id: "add-" + self.uid,
										view: "button",
										type: "icon",
										icon: "wxi-radiobox-marked",
										tooltip: i18n.t("LBL_ADD"),
										css: "button-zoom-reset",
										width: 40, height: 40,
										on: {
											onItemClick: function(obj) {
												console.log($$("add-" + self.uid).config);

													($$("add-" + self.uid)).config.css = "button-add-pressed";

												if (self.buttonPressed === false) {
													self.buttonPressed = true;
													addMarker();
												} else {
													self.buttonPressed = false;
													map.removeInteraction(self.draw);
												}
											}
										}
									}
								]
							}]
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
					circle2.animate({ r: 2 }, 250);
				});

				circle3.animate({ r: 1 }, 200, function() {
					circle3.animate({ r: 2 }, 200);
				});

				circle4.animate({ r: 1 }, 150, function() {
					circle4.animate({ r: 2 }, 150);
				});
			};

			setInterval(blink, 3000);


			//			let arraySvgLayer = [];
			//
			//			arraySvgLayer.push(new ol.Overlay({
			//				position: svgPosition1,
			//				positioning: 'center-center',
			//				element: document.getElementById('svgContainer-1'),
			//				//element: $$("mapDetail-" + self.uid).innerHTML.getElementById('svgContainer-1'),
			//				stopEvent: false
			//			}));
			//
			//			arraySvgLayer.forEach(svgLayer => {
			//
			//				map.addOverlay(svgLayer);
			//			});

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

		map.on('dblclick', function(evt) {

			let coor = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');

			console.log(evt, coor);

			webix.alert({
				title: "COORDENADAS",
				text: "LAT: " + coor[1] + " - " + "LON: " + coor[0],
				type: "alert-warning",
				width: 500
			});
		});


		function addMarker() {

			let source = new ol.source.Vector();

			let marksLayer = new ol.layer.Vector({
				source: source,
				style: new ol.style.Style({
					fill: new ol.style.Fill({
						color: 'rgba(255, 255, 255, 0.2)'
					}),
					stroke: new ol.style.Stroke({
						color: '#ffcc33',
						width: 2
					}),
					image: new ol.style.Circle({
						radius: 7,
						fill: new ol.style.Fill({
							color: '#ffcc33'
						})
					})
				})
			});

			map.addLayer(marksLayer);

			let modify = new ol.interaction.Modify({ source: source });

			map.addInteraction(modify);

			function addInteractions() {

				self.draw = new ol.interaction.Draw({
					source: source,
					type: "Point"
				});

				map.addInteraction(self.draw);

				self.snap = new ol.interaction.Snap({ source: source });
				map.addInteraction(self.snap);

			}

			addInteractions();

			map.on('singleclick', function(evt) {

				let coor = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');

				webix.alert({
					title: "COORDENADAS",
					text: "LAT: " + coor[1] + " - " + "LON: " + coor[0],
					type: "alert-warning",
					width: 500
				});

				evt.preventDefault();
			});
		}


	}
}
