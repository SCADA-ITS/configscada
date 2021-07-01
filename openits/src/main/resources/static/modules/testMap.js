import GenericModule from "../modules/genericModule.js";

const CIRCLES = [
	{
		id: "svgContainer-1",
		lonlat: [-4.899232, 36.516884],
		w: 100,
		h: 100,
		r: 2,
		fill: "red"
	},
	{
		id: "svgContainer-2",
		lonlat: [-4.869415, 36.520443],
		w: 100,
		h: 100,
		r: 2,
		fill: "blue"
	},
	{
		id: "svgContainer-3",
		lonlat: [-4.861390, 36.521426],
		w: 100,
		h: 100,
		r: 2,
		fill: "yellow"
	},
	{
		id: "svgContainer-4",
		lonlat: [-4.852636, 36.524414],
		w: 100,
		h: 100,
		r: 2,
		fill: "green"
	}
]


export default class TestMap extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
		this.map = undefined;
		this.addButtonPressed = false;
		this.drawButtonPressed = false;
		this.drawMark;
		this.drawLine;
		this.zoomIn = false;
		this.zoomOut = true;
		this.svgPrincipalLayer;
		this.arraySvgLayer = [];
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
									rows: [getViewDrawLoading()],
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


		function getViewDrawLoading() {

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
								// SE REQUIERE UN DIV CON MISMO ID Y CLASS, QUE ES DONDE SE PINTA EL MAPA.
								// UN UNICO SVG HARDCODEADO DEL QUE DEPENDEN EL RESTO DE SVG'S QUE SE AÑADEN DINAMICAMENTE. 

								return `	<div id='${divMap}' class='${divMap}' style='height: 100vh; width: 100vw'></div>
											<div style="display: none;">
								  			<div style="width: 200px; height: 200px;">
												<svg id="svgContainer-P" viewBox="0 0 200 200" style="width: 100%; height: auto"></svg>
											</div>
											</div> 		`
							},
						on: {
							onAfterRender: function() {

								// TARGET: ES EL DIV DONDE SE INYECTARA EL MAPA, HAY QUA PASAR LA ID DEL DIV.
								// LAYERS: LAS DIFERENTES CAPAS DEL MAPA CON LA FUENTE CORRESPONDIENTE (EN ESTE CASO SOLO UNA DE TIPO OSM).
								// VIEW: LA VISTA INICIAL DEL MAPA SETEANDO EL PUNTO CENTRAL DE LA PANTALLA, LOS LIMITES DEL MAPA Y EL ZOOM INICIAL.
								map = new ol.Map({
									target: divMap,
									layers: [
										new ol.layer.Tile({
											source: new ol.source.OSM()
										})
									],
									view: new ol.View({
										center: ol.proj.fromLonLat([-4.869415, 36.520443]),
										extent: [-553203.4061760335, 4366939.736625225, -531934.8030556832, 4374793.6412815275],
										zoom: 10
									})
								});

								drawSvg();
								zoomEvents(map);
								changeLayers("init");

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
						right: 10,
						width: 120,
						height: 85,
						rows: [
							{ height: 23 },
							{
								cols: [
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
												// ANIMACION PARA CENTRAR AL MAPA.
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

												if (!self.addButtonPressed) {

													self.addButtonPressed = true;
													webix.html.addCss($$(obj).$view, "button-add-pressed");
													webix.html.removeCss($$(obj).$view, "button-zoom-reset");
													$$(obj).refresh();
													// ACTIVAR EL LISTENER SINGLECLICK DEL MAPA MIENTRAS SE AÑADEN MARCADORES.													
													map.on('singleclick', singleClick);
													addMarker();

												} else {

													self.addButtonPressed = false;
													webix.html.addCss($$(obj).$view, "button-zoom-reset");
													webix.html.removeCss($$(obj).$view, "button-add-pressed");
													$$(obj).refresh();
													// DESACTIVAR EL LISTENER SINGLECLICK Y ELIMINAR LA INTERACCION DE DIBUJADO.
													map.removeInteraction(self.drawMark);
													map.un('singleclick', singleClick);
												}
											}
										}
									},
									{
										id: "draw-" + self.uid,
										view: "button",
										type: "icon",
										icon: "wxi-pencil",
										tooltip: i18n.t("LBL_DRAW"),
										css: "button-zoom-reset",
										width: 40, height: 40,
										on: {
											onItemClick: function(obj) {

												if (!self.drawButtonPressed) {

													self.drawButtonPressed = true;
													webix.html.addCss($$(obj).$view, "button-add-pressed");
													webix.html.removeCss($$(obj).$view, "button-zoom-reset");
													$$(obj).refresh();
													// ACTIVAR EL LISTENER SINGLECLICK DEL MAPA MIENTRAS SE AÑADEN LINEAS.
													map.on('singleclick', singleClick);
													addPolyline();

												} else {

													self.drawButtonPressed = false;
													webix.html.addCss($$(obj).$view, "button-zoom-reset");
													webix.html.removeCss($$(obj).$view, "button-add-pressed");
													$$(obj).refresh();
													// DESACTIVAR EL LISTENER SINGLECLICK Y ELIMINAR LA INTERACCION DE DIBUJADO.
													map.un('singleclick', singleClick);
													map.removeInteraction(self.drawLine);
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

			let arrCircles = [];
			// PINTANDO LA CAPA PRINCIPAL.
			// SE CREA EL MARCADOR CON SNAP Y SE INYECTA LA CAPA EN EL MAPA CAPTURANDO EN "ELEMENT" EL CONTENEDOR SVG QUE ES DONDE SE INCLUYE.  
			let sPrincipal = Snap('#svgContainer-P');
			let circleP = sPrincipal.circle(100, 100, 2).attr({
				fill: "black"
			});

			let svgLonLatP = [-4.874449066151304, 36.5200733357933];
			let svgPositionP = ol.proj.fromLonLat(svgLonLatP);

			self.svgPrincipalLayer = new ol.Overlay({
				position: svgPositionP,
				positioning: 'center-center',
				element: $$("mapDetail-" + self.uid).getNode().querySelector('#svgContainer-P'),
				stopEvent: false,
				id: "svgContainer-P",
			});

			map.addOverlay(self.svgPrincipalLayer);


			// SE MONTA DINAMICAMENTE EL RESTO DE CAPAS, PRIMERO CREANDO LOS CONTENEDORES SVG'S EN EL HTML, LUEGO LOS MARCADORES CON SNAP Y LUEGO SE MONTAN 
			// LAS CAPAS Y SE INYECTAN EN EL MAPA.
			for (let circle of CIRCLES) {

				let newSvg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
				newSvg.setAttribute("id", circle.id);
				newSvg.setAttribute("viewBox", "0 0 " + 2 * circle.w + " " + 2 * circle.h);
				newSvg.setAttribute("width", "100%");
				newSvg.setAttribute("height", "auto");
				document.querySelector('#svgContainer-P').appendChild(newSvg);

				let s = Snap('#' + circle.id);
				let c = s.circle(circle.w, circle.h, circle.r).attr({
					fill: circle.fill
				});

				let svgPosition = ol.proj.fromLonLat([circle.lonlat[0], circle.lonlat[1]]);

				let svgLayer = new ol.Overlay({
					position: svgPosition,
					positioning: 'center-center',
					element: $$("mapDetail-" + self.uid).getNode().querySelector('#' + circle.id),
					stopEvent: false,
					id: circle.id,
				});

				map.addOverlay(svgLayer);

				// ARRAY DONDE GUARDO TODAS LAS CAPAS AÑADIDAS AL MAPA.
				self.arraySvgLayer.push(svgLayer);

				// ARRAY DONDE GUARDO TODOS LOS MARCADORES.
				arrCircles.push(c);

			}

			// EFECTO DE PARPADEO DE LOS MARCADORES
			function blink() {

				arrCircles[0].animate({ r: 1 }, 350, function() {
					arrCircles[0].animate({ r: 2 }, 350);
				});

				arrCircles[1].animate({ r: 1 }, 250, function() {
					arrCircles[1].animate({ r: 2 }, 250);
				});

				arrCircles[2].animate({ r: 1 }, 200, function() {
					arrCircles[2].animate({ r: 2 }, 200);
				});

				arrCircles[3].animate({ r: 1 }, 150, function() {
					arrCircles[3].animate({ r: 2 }, 150);
				});

				circleP.animate({ r: 1 }, 150, function() {
					circleP.animate({ r: 2 }, 150);
				});
			};

			setInterval(blink, 3000);

		}


		// FUNCION PARA CONTROLAR EL NIVEL DE ZOOM A CADA MOVIMIENTO DEL MAPA, CON ZOOMIN Y ZOOMOUT CONTROLO CUANDO DEBO LLAMAR A "CHANGELAYERS()" 
		// PARA MOSTRAR/OCULTAR CAPAS.
		function zoomEvents(map) {

			let actualZoom = undefined;
			let newZoom = undefined;
			const ZOOM_LEVEL = 15;

			map.on('movestart', function(e) {
				actualZoom = map.getView().getZoom();
			});

			map.on('moveend', function(e) {

				newZoom = map.getView().getZoom();
				console.log(newZoom)
				
				if (actualZoom != newZoom) {

					if (newZoom >= ZOOM_LEVEL) {

						if (self.zoomOut) {

							self.zoomIn = true;
							self.zoomOut = false;
							changeLayers();
						}

					} else {

						if (self.zoomIn) {

							self.zoomOut = true;
							self.zoomIn = false;
							changeLayers();
						}
					}
				}
			});
		}

		// FUNCION QUE MUESTRA/OCULTA LAS CAPAS EN FUNCION DEL NIVEL DE ZOOM, COMO LOS SVG'S SON ELEMENTOS HTML SE APLICA UN "HIDDEN" O "VISIBLE".
		function changeLayers(init) {

			if (init) {

				$$("mapDetail-" + self.uid).getNode().querySelector('#svgContainer-P').style.visibility = "visible";

				for (let layer of self.arraySvgLayer) {

					let layerId = "#" + layer.id;
					$$("mapDetail-" + self.uid).getNode().querySelector(layerId).style.visibility = "hidden";
				}

			} else {

				if (self.zoomIn) {

					$$("mapDetail-" + self.uid).getNode().querySelector('#svgContainer-P').style.visibility = "hidden";

					for (let layer of self.arraySvgLayer) {

						let layerId = "#" + layer.id;
						$$("mapDetail-" + self.uid).getNode().querySelector(layerId).style.visibility = "visible";
					}

				} else if (self.zoomOut) {

					$$("mapDetail-" + self.uid).getNode().querySelector('#svgContainer-P').style.visibility = "visible";

					for (let layer of self.arraySvgLayer) {

						let layerId = "#" + layer.id;
						$$("mapDetail-" + self.uid).getNode().querySelector(layerId).style.visibility = "hidden";
					}
				}
			}
		}


		// FUNCION PARA AÑADIR MARCADORES.

		function addMarker() {

			// SE CREA LA "SOURCE".
			let source = new ol.source.Vector();

			// SE CREA LA CAPA TIPO "VECTOR".
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

			// SE AÑADE LA CAPA AL MAPA.
			map.addLayer(marksLayer);

			// SE CREA LA INTERACION QUE PERMITE MODIFICAR LOS MARCADORES Y SE AÑADE LA INTERACCCION AL MAPA.
			let modify = new ol.interaction.Modify({ source: source });

			map.addInteraction(modify);

			// FUNCION PARA PINTAR LOS MARCADORES Y EL EFECTO ACOPLE AL PINCHAR EN UN MARCADOR, SE AÑADEN LAS INTERACCIONES AL MAPA.
			function addInteractions() {

				self.drawMark = new ol.interaction.Draw({
					source: source,
					type: "Point"
				});

				map.addInteraction(self.drawMark);

				self.snap = new ol.interaction.Snap({ source: source });
				map.addInteraction(self.snap);

			}

			addInteractions();

		}


		// FUNCION PARA PINTAR LINEAS.
		function addPolyline() {

			// SE CREA LA "SOURCE".
			let source = new ol.source.Vector();

			// SE CREA LA CAPA TIPO "VECTOR".
			let lineLayer = new ol.layer.Vector({
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
						radius: 5,
						fill: new ol.style.Fill({
							color: '#ffcc33'
						})
					})
				})
			});

			// SE AÑADE LA CAPA AL MAPA.
			map.addLayer(lineLayer);

			// SE CREA LA INTERACION QUE PERMITE MODIFICAR LAS LINEAS Y SE AÑADE LA INTERACCCION AL MAPA.

			let modify = new ol.interaction.Modify({ source: source });

			map.addInteraction(modify);

			// FUNCION PARA PINTARLAS LINEAS Y EL EFECTO ACOPLE AL PINCHAR UNA LINEA, SE AÑADEN LAS INTERACCIONES AL MAPA.

			function addInteractions() {

				self.drawLine = new ol.interaction.Draw({
					source: source,
					type: "LineString"
				});

				map.addInteraction(self.drawLine);

				self.snap = new ol.interaction.Snap({ source: source });
				map.addInteraction(self.snap);

			}

			addInteractions();
		}


		// LISTENERS PARA EL SINGLECLICK Y DOUBLECLICK, EL SINGLECLICK SOLO SE ACTIVA MIENTRAS SE ESTA PINTANDO UNA LINEA O UN MARCADOR PARA PODER
		// VER SUS COORDENADAS.
		function singleClick(evt) {

			let coor = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');

			webix.alert({
				title: "COORDENADAS",
				text: "LAT: " + coor[1] + " - " + "LON: " + coor[0],
				type: "alert-warning",
				width: 500
			});

			evt.preventDefault();

		}


		map.on('dblclick', function(evt) {

			let coor = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');

			console.log(coor);

			webix.alert({
				title: "COORDENADAS",
				text: "LAT: " + coor[1] + " - " + "LON: " + coor[0],
				type: "alert-warning",
				width: 500
			});
		});

	}
}
