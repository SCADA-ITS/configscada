import GenericModule from "../modules/genericModule.js";
//Simport Draw {} from '../lib/ol/ol.js';

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
		this.drawBox;
		this.marksLayer = [];
		this.interact;
		this.source = undefined;
		this.polygon = undefined;
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
						width: 350,
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
										tooltip: "AÑADIR MARCADOR",
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
										tooltip: "PINTAR LINEA",
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
									},
									{
										id: "drawArea-" + self.uid,
										view: "button",
										type: "icon",
										icon: "wxi-checkbox-blank",
										tooltip: "PINTAR AREA",
										css: "button-zoom-reset",
										width: 40, height: 40,
										on: {
											onItemClick: function(obj) {

												let mapLayer = $$("mapDetail-" + self.uid).getNode().querySelector("#map-" + self.uid);

												if (!self.drawButtonPressed) {

													self.drawButtonPressed = true;
													webix.html.addCss($$(obj).$view, "button-add-pressed");
													webix.html.removeCss($$(obj).$view, "button-zoom-reset");
													$$(obj).refresh();

													// ACTIVAR EL LISTENER SINGLECLICK DEL MAPA MIENTRAS SE AÑADEN LINEAS.
													map.on('singleclick', singleClick);
													addSquare();

													//CAMBIO DE CURSOR
													mapLayer.style.cursor = 'crosshair';
												} else {

													self.drawButtonPressed = false;
													webix.html.addCss($$(obj).$view, "button-zoom-reset");
													webix.html.removeCss($$(obj).$view, "button-add-pressed");
													$$(obj).refresh();

													// DESACTIVAR EL LISTENER SINGLECLICK Y ELIMINAR LA INTERACCION DE DIBUJADO.
													map.un('singleclick', singleClick);
													map.removeInteraction(self.drawBox);

													//CAMBIO DE CURSOR
													mapLayer.style.cursor = 'auto';
												}
											}
										}
									},
									{
										id: "changeMarkers-" + self.uid,
										view: "button",
										type: "icon",
										icon: "wxi-plus-circle",
										tooltip: "CAMBIAR DISEÑO MARCADORES",
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
													changeMarkers(true);


												} else {

													self.drawButtonPressed = false;
													webix.html.addCss($$(obj).$view, "button-zoom-reset");
													webix.html.removeCss($$(obj).$view, "button-add-pressed");
													$$(obj).refresh();
													// DESACTIVAR EL LISTENER SINGLECLICK Y ELIMINAR LA INTERACCION DE DIBUJADO.
													map.un('singleclick', singleClick);
													changeMarkers(false);
												}
											}
										}
									},
									{
										id: "delete-" + self.uid,
										view: "button",
										type: "icon",
										icon: "wxi-trash",
										tooltip: "ELIMINAR",
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
													deleteMarker();



												} else {

													self.drawButtonPressed = false;
													webix.html.addCss($$(obj).$view, "button-zoom-reset");
													webix.html.removeCss($$(obj).$view, "button-add-pressed");
													$$(obj).refresh();
													// DESACTIVAR EL LISTENER SINGLECLICK Y ELIMINAR LA INTERACCION DE DIBUJADO.
													map.un('singleclick', singleClick);
													map.removeInteraction(self.interact);
												}
											}
										}
									},
									{
										id: "visibility-" + self.uid,
										view: "button",
										type: "icon",
										icon: "wxi-eye",
										tooltip: "VISIBILIDAD",
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
													visibleMarker();



												} else {

													self.drawButtonPressed = false;
													webix.html.addCss($$(obj).$view, "button-zoom-reset");
													webix.html.removeCss($$(obj).$view, "button-add-pressed");
													$$(obj).refresh();

													// DESACTIVAR EL LISTENER SINGLECLICK Y ELIMINAR LA INTERACCION DE DIBUJADO.
													map.un('singleclick', singleClick);
													visibleMarker();
												}
											}
										}
									},
									{
										id: "alert-" + self.uid,
										view: "button",
										type: "icon",
										icon: "wxi-dots",
										tooltip: "PINTAR MARCADORES PROGRAMATICAMENTE",
										css: "button-zoom-reset",
										width: 40, height: 40,
										on: {
											onItemClick: function(obj) {

												drawMarkFromCoor();
											}
										}
									},
									{
										id: "ol-" + self.uid,
										view: "button",
										type: "icon",
										icon: "wxi-drag",
										tooltip: "CREAR/ELIMINAR OVERLAYS",
										css: "button-zoom-reset",
										width: 40, height: 40,
										on: {
											onItemClick: function(obj) {

												if (!self.addButtonPressed) {

													self.addButtonPressed = true;
													webix.html.addCss($$(obj).$view, "button-add-pressed");
													webix.html.removeCss($$(obj).$view, "button-zoom-reset");
													$$(obj).refresh();

													drawOverlay();

												} else {

													self.addButtonPressed = false;
													webix.html.addCss($$(obj).$view, "button-zoom-reset");
													webix.html.removeCss($$(obj).$view, "button-add-pressed");
													$$(obj).refresh();

													drawOverlay();
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
			self.source = new ol.source.Vector();

			// SE CREA LA CAPA TIPO "VECTOR" Y SE INCLUYE EL ESTILO.
			let newMark = new ol.layer.Vector({
				source: self.source,
				style:
					new ol.style.Style({
						image: new ol.style.Circle({
							radius: 7,
							fill: new ol.style.Fill({
								color: '#1BE47E'
							}),
							stroke: new ol.style.Stroke({
								color: '#ADE4C8',
								radius: 10,
								width: 5
							})
						})
					})
			});

			// SE AÑADE LA CAPA AL MAPA.
			map.addLayer(newMark);
			self.marksLayer.push(newMark);

			// SE CREA LA INTERACION QUE PERMITE MODIFICAR LOS MARCADORES Y SE AÑADE LA INTERACCCION AL MAPA.
			let modify = new ol.interaction.Modify({ source: self.source });

			map.addInteraction(modify);

			// FUNCION PARA PINTAR LOS MARCADORES Y EL EFECTO ACOPLE AL PINCHAR EN UN MARCADOR, SE AÑADEN LAS INTERACCIONES AL MAPA.
			function addInteractions() {

				self.drawMark = new ol.interaction.Draw({
					source: self.source,
					type: "Point"
				});

				map.addInteraction(self.drawMark);

				self.snap = new ol.interaction.Snap({ source: self.source });
				map.addInteraction(self.snap);
			}

			addInteractions();
		}


		// FUNCION PARA PINTAR LINEAS.
		function addPolyline() {

			// SE CREA LA "SOURCE".
			let source = new ol.source.Vector();

			// SE CREA LA CAPA TIPO "VECTOR" Y SE INYECTA EL ESTILO.
			let lineLayer = new ol.layer.Vector({
				source: source,
				style: new ol.style.Style({
					fill: new ol.style.Fill({
						color: '#900C3F'
					}),
					stroke: new ol.style.Stroke({  // COLOR DE LA LINEA
						color: '#33FFF4',
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

			console.log(evt.coordinate)

			let coor = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');

			// CON INTERSECTSCOODINATE() SE CONFIRMA SI UNAS COORDENADAS SE ENCUENTRAN DENTRO DE UN AREA SIENDO SELF.POLYGON LA ULTIMA AREA PINTADA.
			if (self.polygon) {
				console.log("El punto se encuentra dentro del último área pintada: " + self.polygon.intersectsCoordinate(evt.coordinate))

			}
			console.log("LAT: " + coor[1] + " - " + "LON: " + coor[0])
			//			webix.alert({
			//				title: "COORDENADAS",
			//				text: "LAT: " + coor[1] + " - " + "LON: " + coor[0],
			//				type: "alert-warning",
			//				width: 500
			//			});

			evt.preventDefault();

		}


		// PINTAR AREA EN EL MAPA
		function addSquare() {

			// SI YA EXISTE UN AREA, SE BORRA
			//			if (self.drawBox) {
			//
			//				self.drawBox.source_.clear();
			//			}

			let source = new ol.source.Vector();

			// SEA CREA LA CAPA, SE APLICA ESTILO DEL AREA UNA VEZ PINTADA Y SE AÑADE LA CAPA AL MAPA
			let boxLayer = new ol.layer.Vector({
				source: source,
				style: new ol.style.Style({
					fill: new ol.style.Fill({
						color: 'rgba(255, 255, 255, 0.2)'  // COLOR DEL RELLENO DEL AREA
					}),
					stroke: new ol.style.Stroke({
						color: '#ffcc33', // COLOR DE LA LINEA DEL AREA
						width: 3
					})
				})
			});

			map.addLayer(boxLayer);

			// PARA PERMITIR MODIFICAR EL AREA DIBUJADA
			//			let modify = new ol.interaction.Modify({ source: source });
			//
			//			map.addInteraction(modify);


			function addInteractions() {

				// PINTADO DEL AREA CON EL ESTILO MIENTRAS SE PINTA 
				self.drawBox = new ol.interaction.DragBox({
					source: source,
					type: "Circle",
					geometryFunction: ol.interaction.Draw.createBox(),
					onBoxEnd: dragEnd  // FUNCION QUE SE EJECUTA TRAS TERMINAR EL PINTADO
				});

				map.addInteraction(self.drawBox);

				self.snap = new ol.interaction.Snap({ source: source });
				map.addInteraction(self.snap);
			}


			// CALCULO DEL AREA GEODESICA
			function formatGeodesicArea(polygon) {

				const area = ol.sphere.getArea(polygon);

				let output;
				if (area > 10000) {
					output = Math.round((area / 1000000) * 100) / 100 + ' ' + 'km2';
				} else {
					output = Math.round(area * 100) / 100 + ' ' + 'm2';
				}

				// HAY GRAN DIFERENCIA ENTRE EL AREA DEL PLANO Y EL AREA GEODESICA
				console.log("area geodesica: " + output)

			}

			// CALCULO DEL AREA EN PLANO
			function formatArea(area) {

				let output;
				if (area > 10000) {
					output = Math.round((area / 1000000) * 100) / 100 + ' ' + 'km2';
				} else {
					output = Math.round(area * 100) / 100 + ' ' + 'm2';
				}
				console.log("area en plano: " + output)
			}

			addInteractions();

			// EVENTOS AL TERMINAR DE PINTAR EL AREA
			function dragEnd() {

				let coordinates = self.drawBox.getGeometry().getExtent();
				let coord01 = [coordinates[0], coordinates[3]];
				let coord02 = [coordinates[2], coordinates[1]];

				let startCoordinates = ol.proj.transform(coord01, 'EPSG:3857', 'EPSG:4326');
				let endCoordinates = ol.proj.transform(coord02, 'EPSG:3857', 'EPSG:4326');

				console.log("start: LAT: " + startCoordinates[1] + " - " + "LON: " + startCoordinates[0]);
				console.log("end: LAT: " + endCoordinates[1] + " - " + "LON: " + endCoordinates[0]);
				console.log(startCoordinates, endCoordinates)

				webix.alert({
					title: "COORDENADAS",
					text: "start: LAT: " + startCoordinates[1] + " - " + "LON: " + startCoordinates[0] + "   " + "end: LAT: " + endCoordinates[1] + " - " + "LON: " + endCoordinates[0],
					type: "alert-warning",
					width: 440
				}).then(function() {

					// TRAS ACEPTAR EL ALERT SE DISPARA EL EVENTO CLICK SOBRE EL BOTON DE PINTAR AREA DEJANDOLO RESETEADO
					webix.html.triggerEvent($$("drawArea-" + self.uid).getInputNode(), "MouseEvents", "click");
				});

				self.polygon = self.drawBox.getGeometry();
				formatGeodesicArea(self.polygon);
				formatArea(self.polygon.getArea());
			}
		}


		// FUNCION PARA CAMBIAR ESTILO DE MARCADORES INCLUIDOS CON EL RATON
		function changeMarkers(change) {

			self.marksLayer.forEach(marker => {

				if (change) {

					marker.setStyle(
						new ol.style.Style({
							image: new ol.style.Icon({
								src: './img/incident_level/ausol/incident_level_1.png',
								scale: 1
							})
						})
					);

				} else {

					marker.setStyle(
						new ol.style.Style({
							image: new ol.style.Circle({
								radius: 7,
								fill: new ol.style.Fill({
									color: '#37FF33'
								}),
								stroke: new ol.style.Stroke({
									color: '#F61B64',
									radius: 10,
									width: 5
								})
							})
						})
					);
				}
			});

		}

		// FUNCION PARA ELIMINAR MARCADORES INCLUIDOS CON EL RATON
		function deleteMarker() {

			self.interact = new ol.interaction.Select({ condition: ol.events.condition.click });
			map.addInteraction(self.interact);

			self.interact.on('select', function(evt) {

				if (evt.selected[0].values_.features) {

					self.marksLayer.forEach(marker => {

						let selectedFeature = evt.selected[0].values_.features[0];
						let source = marker.getSource();

						source.removeFeature(selectedFeature);

						// BORRARIA TODOS LOS MARCADORES DE UNA VEZ
						//source.clear();  

					});
				}
			});
		}

		// MOSTRAR/OCULTAR LOS MARCADORES INCLUIDOS CON EL RATON
		function visibleMarker() {

			self.marksLayer.forEach(marker => {

				let isVisible = marker.getVisible();

				if (isVisible) {

					marker.setVisible(false);

				} else {

					marker.setVisible(true);
				}
			});
		}


		// PINTAR MARCADORES PROGRAMATICAMENTE
		function drawMarkFromCoor() {

			// COORDENADAS DE CADA UNO DE LOS MARCADORES A PINTAR
			let coordinates = [
				[-4.883959713719608, 36.51421291340994],
				[-4.8787899903346, 36.51975507063943],
				[-4.899193187322238, 36.51423723063747],
				[-4.919092123430723, 36.51180551524055],
				[-4.912730669840885, 36.524025671074426],
				[-4.8647867459141825, 36.51108459050563]
			];

			let markList = [];

			// PARA CADA UNO DE LOS MARCADORES...
			for (let coord of coordinates) {

				// SE CREA EL MARCADOR EN LAS COORDENADAS INDICADAS
				let mark = new ol.Feature({
					geometry: new ol.geom.Point(
						ol.proj.fromLonLat([coord[0], coord[1]])
					),
				});

				// SE LE APLICA UN ESTILO
				mark.setStyle(new ol.style.Style({
					image: new ol.style.Circle({
						radius: 7,
						fill: new ol.style.Fill({
							color: '#3A4A85'
						}),
						stroke: new ol.style.Stroke({
							color: '#8DA1EA',
							radius: 10,
							width: 5
						})
					})
				}));

				// SE INYECTA EL ID (EN ESTE CASO COMO EJEMPLO UNA DE LAS COORDENADAS)
				mark.setId(coord[0]);

				// SE INCLUYE EN EL ARRAY DE MARCADORES 
				markList.push(mark);
			}

			// SE CREA LA CAPA CON EL ARRAY DE MARCADORES Y SE AÑADE AL MAPA
			let newLayer = new ol.layer.Vector({
				source: new ol.source.Vector({
					features: markList
				})
			});

			map.addLayer(newLayer);


			// SE INYECTA EL EVENTO ONCLICK
			let select = new ol.interaction.Select({ condition: ol.events.condition.click });
			map.addInteraction(select);

			select.on('select', function(evt) {

				// SI HEMOS CLICADO EN UN ELEMENTO Y ADEMAS TIENE UNA ID INYECTADA SE PINTA LA VENTANA
				if (evt.selected[0] && evt.selected[0].getId()) {

					let id = evt.selected[0].getId();

					webix.ui({
						view: "window",
						close: true,
						position: "center",
						head: "Nueva ventana",
						body: "Has pinchado en el id " + id
					}).show();
				}

			});

			drawTooltip();

		}


		// PINTAR TOOLTIP
		function drawTooltip() {

			let tooltipId = undefined;

			//EVENTO POINTERMOVE PARA TODO EL MAPA
			map.on('pointermove', function(e) {

				// SI YA EXISTE TOOLTIP LO DESTRUIMOS Y LE ASIGNAMOS COMO UNDEFINED
				if (tooltipId) {

					$$(tooltipId).destructor();
					tooltipId = undefined;
				}

				// EVENTO PARA CAPTURAR CUANDO EL PUNTERO PASA POR ENCIMA DE UN MARCADOR O FEATURE DEL MAPA
				map.forEachFeatureAtPixel(e.pixel, function(f) {

					//SI TIENE ID INYECTADO MOSTRAMOS EL TOOLTIP
					if (f.id_) {

						webix.ui({
							id: f.id_,
							view: "tooltip",
							template: "My #value#",
							height: 100
						}).show({ id: 1, value: f.id_ }, { x: event.clientX, y: event.clientY });

						tooltipId = f.id_;
						return true;
					}

				});

			});

		}

		// CREAR/BORRAR OVERLAYS
		function drawOverlay() {

			let win = $$("win");

			if (win) {
				win.destructor();
			}

			let overLay = $$("mapDetail-" + self.uid).getNode().querySelector("#overlay-" + self.uid);
			let pos = [-4.883959713719608, 36.51421291340994];

			// SI NO EXISTE EL DIV DEL QUE DEPENDE EL OVERLAY LO CREAMOS
			if (overLay === null) {

				let newOverlay = document.createElement('div');

				newOverlay.setAttribute("id", "overlay-" + self.uid);
				document.querySelector("#map-" + self.uid).appendChild(newOverlay);

				//CREAMOS EL OVERLAY Y SE AÑADE AL MAPA
				let marker = new ol.Overlay({
					id: pos[0],
					element: newOverlay,
					position: pos
				});

				map.addOverlay(marker);


			} else {
				// SI EXISTE LO BORRAMOS
				let olId = map.getOverlayById(pos[0]);
				map.removeOverlay(olId);
			}


			// MOSTRAMOS EN VENTANA EL NUMERO DE OVERLAYS QUE TIENE EL MAPA
			let num = map.getOverlays().getArray().length;

			webix.ui({
				id: "win",
				view: "window",
				position: "center",
				close: true,
				head: "nueva ventana",
				body: "Numero de Overlays: " + num
			}).show();

		}

		//		function drawPolygonFromCoor() {
		//
		//			//			start: LAT: 36.6161497043787 - LON: -4.964760280502155 
		//			//			end: LAT: 36.55415911675141 - LON: -4.811272821466046
		//			//let coordinates = [-5.094085418538154, 36.87499727607941, -4.555755340413153, 36.62742178736384];
		//			let coordinates = [-578842.2972050244, 4396167.345315769, -528240.9844802377, 4423837.549555004];
		//
		//			let polygon = new ol.Feature(
		//				new ol.geom.Polygon(coordinates).transform('EPSG:4326', 'EPSG:3857'));
		//
		//
		//			let source = new ol.source.Vector({
		//				features: [polygon]
		//			});
		//
		//			var layer = new ol.layer.Vector({
		//				source: source,
		//				style: new ol.style.Style({
		//					fill: new ol.style.Fill({
		//						color: 'rgba(255, 255, 255, 0.2)'  // COLOR DEL RELLENO DEL AREA
		//					}),
		//					stroke: new ol.style.Stroke({
		//						color: '#ffcc33', // COLOR DE LA LINEA DEL AREA
		//						width: 2
		//					}),
		//					image: new ol.style.Circle({
		//						radius: 0,
		//						fill: new ol.style.Fill({
		//							color: 'rgba(255, 255, 255, 0.9)',  // COLOR DEL MARCADOR
		//							opacity: 10
		//						})
		//					})
		//				})
		//			});
		//
		//			map.addLayer(layer);
		//		}


		map.on('dblclick', function(evt) {

			let coor = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');

			webix.alert({
				title: "COORDENADAS",
				text: "LAT: " + coor[1] + " - " + "LON: " + coor[0],
				type: "alert-warning",
				width: 500
			});
		});

	}
}
