import { mapTG } from "../data/maps.js";
import * as iconGenerator from "../lib/iconGenerator.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";

import ElementForm from "../classes/ElementForm.js";
import { columnsElementFormListAusol } from "../data/elementForm.js";

let map = null;
var mapConf = "";
var mapStates = {};

export default class Map {
  constructor(data, metadata, synopticDiv) {
    this.elements = [];
    this.elementsTypes = [];
    this.elementsToDraw = {};
    this.baseLayer = "";
    this.ratioWidth = 1.0;
    this.ratioHeight = 1.0;
    this.baseWidth = 0;
    this.baseHeight = 0;
    this.data = data;

    this.synopticDiv = synopticDiv;
    this.synopticConf = "";
    this.synopticType = "";

    this.map = null;

    /*
    if (metadata == null) {
      this.metadata = synoptic;
    } else {
      this.metadata = metadata;
    }
    */
  }

  /*
   *	Tries to get the data from Stomp, but if it doesn't find the needed data,
   * gets it by GraphQL
   */
  processStompMessageWithFallback(message) {
    try {
      let obj = JSON.parse(JSON.parse(message.body));

      // get feature layer
      let retLayer = null;
      map.getLayers().forEach(function(l) {
        if(l.get("title") == "Elements") {
          retLayer = l;
        }
      });
     
      obj.basicElementsVO.forEach((element) => {

        // check if the element exists
        /*
        if(!Object.keys(mapStates).includes(element.id)) {
          alert("NO EXISTE EL ELEMENTO " + element.id);

          let l = map.getLayers().array_[0];

          let workingFeature = new ol.Feature({
            geometry: new ol.geom.Point(
              ol.proj.fromLonLat([-4.889977, 36.422382])
            ),
            id: element.id,
          });

          let workingStyle = new ol.style.Style({
            image: new ol.style.Icon({
              opacity: 1,
              src: "/img/icons/checkbox-blank-circle_green.png",
              scale: 1,
            }),
          });

          workingFeature.setStyle(workingStyle);
          workingFeature.set("component", "Element");
          workingFeature.setId(element.id.toString());

          console.log(l);

          let vectorSource = new ol.source.Vector({
            wrapX: false,
            features: [workingFeature],
          });

          let incidentLayer = new ol.layer.Vector({
            title: "Elements",
            source: vectorSource,
          });

          map.addLayer(incidentLayer);

        }
        */

        // if it's a DMS, ask for the full data
        if(elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_10_DMS ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_24_PMV_A_C ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_26_PMV_64X64 ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_27_PMV_A_B ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_28_PMV_192X64 ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_29_PMV_B_D ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_30_PMV_128X64 ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_31_PMV ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_32_PMV ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_35_PMV) {
          throw "DMSETDException";
        }

        // search for the element received in the elements of the layer
        if(retLayer != null) {
          let elementToUpdate = retLayer.getSource().getFeatureById(element.id);

          if(typeof element.state != 'undefined') {
            mapStates[element.id] = element.state;
          }

          let mapElement = mapConf[element.id];


          if(elementToUpdate != null) {

            let elementType = elementTypeMap.getElementType(element.id);
            let total_width = mapElement.width;
            let total_height = mapElement.height;
            
            if (elementType === elementTypeMap.ELEMENT_TYPE_24_PMV_A_C ||
              elementType === elementTypeMap.ELEMENT_TYPE_26_PMV_64X64 ||
              elementType === elementTypeMap.ELEMENT_TYPE_27_PMV_A_B ||
              elementType === elementTypeMap.ELEMENT_TYPE_28_PMV_192X64
              || elementType === elementTypeMap.ELEMENT_TYPE_29_PMV_B_D
              || elementType === elementTypeMap.ELEMENT_TYPE_30_PMV_128X64
              || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV
              || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV
              || elementType === elementTypeMap.ELEMENT_TYPE_35_PMV) {  // MATRIX PANEL

              total_width = 0;
              total_height = 0;

              // check for the panel config
              let panelConfig = element.elementValues.find(
                (ic) =>
                  elementTypeMap.getElementTypeParam(ic.id) ==
                  elementTypeMap.PANEL_MANAGER_CONFIG
              );

              if(typeof panelConfig != 'undefined') {
                panelConfig = JSON.parse(panelConfig.value);
    
                // Generate the matrix with sizes
                for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
                  let n_size = panelConfig["Subpaneles"][nsubpanel]["Tamanio"];
                  let panel_width = parseInt(n_size.split("x")[0]);
                  let panel_height = parseInt(n_size.split("x")[1]);
                  total_width += panel_width;
                  if(panel_height > total_height) {
                    total_height = panel_height;
                  }              
                }
              }
            }

            let tmpIcon = iconGenerator.getElementSVG(element, total_width, total_height, "", mapStates[element.id], mapElementValues);

            // Fix for OpenLayers not reading the colors. The # character is not allowed, so it has to be replaced by %23
            tmpIcon = tmpIcon.replace(/#/g, "%23");

            let workingStyle = new ol.style.Style({
              image: new ol.style.Icon({
                opacity: 1,
                src: "data:image/svg+xml;utf8," + tmpIcon,
                scale: mapElement.height / total_height,
              }),
            });

            elementToUpdate.setStyle(workingStyle);
          }
        }

      });
    } catch (err) {
      console.log("Error: " + err.message);
      console.log("RETRY WITH GRAPHQL");
      
      let obj = JSON.parse(JSON.parse(message.body));

      // get feature layer
      let retLayer = null;
      map.getLayers().forEach(function(l) {
        if(l.get("title") == "Elements") {
          retLayer = l;
        }
      });

      obj.basicElementsVO.forEach((element) => {
        //initialize the proxy
        let elementQuery = webix.proxy(
          "GraphQL",
          `
					query ($elem: String!) {
						getElement(elementId: $elem)
						{
							id
              state
              elementType {
                id
                alias
              }
							elementValues
							{
								id
								value
								alerted
								elementTypeParam {
									description
								}
							}
						}
					}
					`
        );

        // loads the data
        elementQuery
          .load({
            elem: element.id,
          })
          .then((data) => {
            // process the received data
            let element = data;

            let mapElement = mapConf[element.id];

            if(retLayer != null) {
              let elementToUpdate = retLayer.getSource().getFeatureById(element.id);
              if(elementToUpdate != null) {
                
                // let's store the states
                mapStates[element.id] = element.state;

                // the values are only valid for ETDs
                let mapElementValues = mapElement.values;
                if(typeof mapElementValues == 'undefined') {
                  mapElementValues = [];
                }

                let workingFeature = new ol.Feature({
                  geometry: new ol.geom.Point(
                    ol.proj.fromLonLat([mapElement.lon, mapElement.lat])
                  ),
                  id: element.id,
                });

                let elementType = elementTypeMap.getElementType(element.id);
                let total_width = mapElement.width;
                let total_height = mapElement.height;

                if (elementType === elementTypeMap.ELEMENT_TYPE_24_PMV_A_C ||
                  elementType === elementTypeMap.ELEMENT_TYPE_26_PMV_64X64 ||
                  elementType === elementTypeMap.ELEMENT_TYPE_27_PMV_A_B ||
                  elementType === elementTypeMap.ELEMENT_TYPE_28_PMV_192X64
                  || elementType === elementTypeMap.ELEMENT_TYPE_29_PMV_B_D
                  || elementType === elementTypeMap.ELEMENT_TYPE_30_PMV_128X64
                  || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV
                  || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV
                  || elementType === elementTypeMap.ELEMENT_TYPE_35_PMV) {  // MATRIX PANEL

                  total_width = 0;
                  total_height = 0;

                  // check for the panel config
                  let panelConfig = element.elementValues.find(
                    (ic) =>
                      elementTypeMap.getElementTypeParam(ic.id) ==
                      elementTypeMap.PANEL_MANAGER_CONFIG
                  );

                  if(typeof panelConfig != 'undefined') {
                    panelConfig = JSON.parse(panelConfig.value);
        
                    // Generate the matrix with sizes
                    for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
                      let n_size = panelConfig["Subpaneles"][nsubpanel]["Tamanio"];
                      let panel_width = parseInt(n_size.split("x")[0]);
                      let panel_height = parseInt(n_size.split("x")[1]);
                      total_width += panel_width;
                      if(panel_height > total_height) {
                        total_height = panel_height;
                      }              
                    }
                  }
                }


                //let tmpIcon = iconGenerator.getElementSVG(element, mapElement.width, mapElement.height, "", mapStates[element.id], mapElementValues);
                let tmpIcon = iconGenerator.getElementSVG(element, total_width, total_height, "", mapStates[element.id], mapElementValues);

                // Fix for OpenLayers not reading the colors. The # character is not allowed, so it has to be replaced by %23
                tmpIcon = tmpIcon.replace(/#/g, "%23");

                let canvas = document.createElement("canvas");
                // get canvas context for drawing on canvas
                let context = canvas.getContext("2d");
                // set canvas size
                //canvas.width = width;
                //canvas.height = height;

                // call svgString2Image function
                iconGenerator.drawInlineSVG(
                  canvas,
                  context,
                  tmpIcon,
                  function () {
                    console.log(canvas.toDataURL());
                  }
                );

                let workingStyle = new ol.style.Style({
                  image: new ol.style.Icon({
                    opacity: 1,
                    src: "data:image/svg+xml;utf8," + tmpIcon,
                    scale: mapElement.height / total_height,
                  }),
                });

                elementToUpdate.setStyle(workingStyle);
              }
            }
          });
      });
    }
  }

  getComponent() {
    if (this.data.includes("http") || this.data.includes("GraphQL")) {
      this.metadata.url = this.data;
    } else {
      this.metadata.data = this.data;
    }
    return this.metadata;
  }

  handleLayers(e) {
    let els = this.s.selectAll('[component="' + e + '"]');

    // Hides device
    if (this.elementsToDraw[e] == "visible") {
      this.elementsToDraw[e] = "hidden";
    } else {
      this.elementsToDraw[e] = "visible";
    }

    els.forEach((el) => {
      el.attr("visibility", this.elementsToDraw[e]);
    });
  }

  // got from Github
  elementReady(selector) {
    return new Promise((resolve, reject) => {
      let el = document.querySelector(selector);
      if (el) {
        resolve(el);
      }
      new MutationObserver((mutationRecords, observer) => {
        // Query for elements matching the specified selector
        Array.from(document.querySelectorAll(selector)).forEach((element) => {
          resolve(element);
          //Once we have resolved we don't need the observer anymore.
          observer.disconnect();
        });
      }).observe(document.documentElement, {
        childList: true,
        subtree: true,
      });
    });
  }

  loadData() {
    let self = this;

    this.data
      .load()
      .then((d) => {
        let markers = [];

        // TODO: should we clean the created canvas?
        d.childs.forEach((element) => {
          if (element.id in this.synopticConf) {

            // let's store the states
            mapStates[element.id] = element.state;

            let mapElement = this.synopticConf[element.id];

            // the values are only valid for ETDs
            let mapElementValues = mapElement.values;
            if(typeof mapElementValues == 'undefined') {
              mapElementValues = [];
            }

            let workingFeature = new ol.Feature({
              geometry: new ol.geom.Point(
                ol.proj.fromLonLat([mapElement.lon, mapElement.lat])
              ),
              id: element.id,
            });

            
            let elementType = elementTypeMap.getElementType(element.id);
            let total_width = mapElement.width;
            let total_height = mapElement.height;
            
            if (elementType === elementTypeMap.ELEMENT_TYPE_24_PMV_A_C ||
              elementType === elementTypeMap.ELEMENT_TYPE_26_PMV_64X64 ||
              elementType === elementTypeMap.ELEMENT_TYPE_27_PMV_A_B ||
              elementType === elementTypeMap.ELEMENT_TYPE_28_PMV_192X64
              || elementType === elementTypeMap.ELEMENT_TYPE_29_PMV_B_D
              || elementType === elementTypeMap.ELEMENT_TYPE_30_PMV_128X64
              || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV
              || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV
              || elementType === elementTypeMap.ELEMENT_TYPE_35_PMV) {  // MATRIX PANEL
           
              total_width = 0;
              total_height = 0;

              // check for the panel config
              let panelConfig = element.elementValues.find(
                (ic) =>
                  elementTypeMap.getElementTypeParam(ic.id) ==
                  elementTypeMap.PANEL_MANAGER_CONFIG
              );

              if(typeof panelConfig != 'undefined') {
                panelConfig = JSON.parse(panelConfig.value);
    
                // Generate the matrix with sizes
                for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
                  let n_size = panelConfig["Subpaneles"][nsubpanel]["Tamanio"];
                  let panel_width = parseInt(n_size.split("x")[0]);
                  let panel_height = parseInt(n_size.split("x")[1]);
                  total_width += panel_width;
                  if(panel_height > total_height) {
                    total_height = panel_height;
                  }              
                }
              }
            }


            let tmpIcon = iconGenerator.getElementSVG(element, total_width, total_height, "", mapStates[element.id], mapElementValues);

            // Fix for OpenLayers not reading the colors. The # character is not allowed, so it has to be replaced by %23
            tmpIcon = tmpIcon.replace(/#/g, "%23");

            let canvas = document.createElement("canvas");
            // get canvas context for drawing on canvas
            let context = canvas.getContext("2d");
            // set canvas size
            //canvas.width = mapElement.width;
            //canvas.height = mapElement.height;

            // call svgString2Image function
            /*
            iconGenerator.drawInlineSVG(
              canvas,
              context,
              tmpIcon,
              function () {
                //console.log(canvas.toDataURL());
              }
            );
            */

            //alert(element.id + "\tSCALE: " + mapElement.height / total_height);

            let workingStyle = new ol.style.Style({
              image: new ol.style.Icon({
                opacity: 1,
                src: "data:image/svg+xml;utf8," + tmpIcon,
                scale: mapElement.height / total_height,
              }),
            });

            workingFeature.setStyle(workingStyle);
            workingFeature.set("component", "Element");
            workingFeature.setId(element.id.toString());
            markers.push(workingFeature);
            
          }
        });

        return markers;
      })
      .then((markers) => {
        let vectorSource = new ol.source.Vector({
          wrapX: false,
          features: markers,
        });

        let incidentLayer = new ol.layer.Vector({
          title: "Elements",
          source: vectorSource,
        });

        //this.map.addLayer(incidentLayer);
        map.addLayer(incidentLayer);
      })
      .then((d) => {
        // creates the stomp connection
        self.client = new StompJs.Client({
          brokerURL: webix.storage.session.get("url.ws.stomp"),
          connectHeaders: {
            login: "user",
            passcode: "user",
          },
          reconnectDelay: 5000,
          heartbeatIncoming: 4000,
          heartbeatOutgoing: 4000,
        });

        self.client.onConnect = function (frame) {
          // Do something, all subscribes must be done is this callback
          // This is needed because this will be executed after a (re)connect
          self.suscription = self.client.subscribe(
            "/topic/element.>",
            self.processStompMessageWithFallback
          );
        };

        self.client.onStompError = function (frame) {
          // Will be invoked in case of error encountered at Broker
          // Bad login/passcode typically will cause an error
          // Complaint brokers will set `message` header with a brief message. Body may contain details.
          // Compliant brokers will terminate the connection after any error
          console.log("Broker reported error: " + frame.headers["message"]);
          console.log("Additional details: " + frame.body);
          alert(`[error] ${error.message}`);
        };

        self.client.activate();
      });
  }

  /*
   *	Initializes everything
   */
  init(synopticConf) {
    this.elementReady('[view_id="' + this.synopticDiv + '"]').then((c) => {
      let divToAdd = document.createElement("div");
      divToAdd.setAttribute("id", "map");
      document
        .querySelector('[view_id="' + this.synopticDiv + '"]')
        .prepend(divToAdd);

      //this.map = new ol.Map({
      map = new ol.Map({
        target: "map",
        layers: [
          new ol.layer.Tile({
            source: new ol.source.OSM(),
          }),
        ],
        view: new ol.View({
          center: ol.proj.fromLonLat([-4.936173, 36.457911]),
          zoom: 12,
        }),
      });

      // select interaction working on "click"
      let selectClick = new ol.interaction.Select({
        condition: ol.events.condition.singleClick,
      });

      
      selectClick.on("select", function (e) { 
        let highlightStyle = new ol.style.Style({
          fill: new ol.style.Fill({
            color: 'rgba(255,255,255,0.7)'
          }),
          stroke: new ol.style.Stroke({
            color: '#3399CC',
            width: 3
          })
        });
       
        let feature = e.target.getFeatures().array_[0];
        let element_id = feature.id_;
        if (
          elementTypeMap.getElementType(element_id) !=
          elementTypeMap.ZONE_ELEMENT_TYPE
        ) {
          if($$("win" + element_id) != null){
              $$("win" + element_id).destructor();
          }
          let elementForm = new ElementForm(element_id, columnsElementFormListAusol);
          webix
            .ui({
              id: "win" + element_id,
              view: "window",
              move: true,
              close: true,
              position: "right",
              body: elementForm.getComponent(),
              on: {
                onDestruct: function (obj) {
                  elementForm.disconnect();
                },
                onHide: function (obj) {
                  elementForm.disconnect();
                },
              },
            })
            .show();
          
            // auto de-select
            e.target.getFeatures().remove(feature);
        } else {
          $$("synoptic1").show();
        }
      });

      //this.map.addInteraction(selectClick);
      map.addInteraction(selectClick);

      //this.elementReady("#map").then((c1) => {
      // loads data and draws for first time
      this.loadData();
      //});
    });

    this.synopticConf = mapTG;
    mapConf = mapTG;

    /*
    if (synopticConf == "tunnel") {
      this.synopticConf = synopticTunnel;
    } else if (synopticConf == "metro") {
      this.synopticConf = synopticMetro;
    } else if (synopticConf == "global") {
      this.synopticConf = synopticGlobal;
    } else if (synopticConf == "bratislava") {
      this.synopticConf = synopticBratislava;
    }

    this.synopticType = synopticConf;

    */

    let self = this;
    // waits for the element and starts drawing
    this.elementReady('[view_id="' + this.synopticDiv + '"]').then((c) => {
      /*
      let spinnerDivToAdd = document.createElement("div");
      spinnerDivToAdd.setAttribute("id", this.synopticDiv + "spinner");
      spinnerDivToAdd.setAttribute("class", "spinner");
      document
        .querySelector('[view_id="' + this.synopticDiv + '"]')
        .prepend(spinnerDivToAdd);

      var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
      document
        .querySelector('[view_id="' + this.synopticDiv + '"]')
        .prepend(svg);
      svg.setAttribute("id", this.synopticDiv + "SVG");
      svg.setAttribute("width", c.offsetWidth);
      svg.setAttribute("height", c.offsetHeight);

      self.baseWidth = c.offsetWidth;
      self.baseHeight = c.offsetHeight;

      let divToAdd = document.createElement("div");
      divToAdd.setAttribute("id", this.synopticDiv + "dropdownDiv");
      divToAdd.setAttribute("class", "dropdown");
      document
        .querySelector('[view_id="' + this.synopticDiv + '"]')
        .prepend(divToAdd);

      this.elementReady("#" + this.synopticDiv + "SVG").then((c1) => {
        // loads data and draws for first time
        this.loadData();
      });
      */

      this.initResponsive();
    });

  }

  /*
   * Initializes the responsiveness
   */
  initResponsive() {
    let e = document.getElementsByClassName("webix_sidebar")[0];

    let self = this;

    let observer = new MutationObserver(function (event) {
      //self.doResponsive();
      setTimeout( function() { self.map.updateSize();}, 10);
    });

    observer.observe(e, {
      attributes: true,
      attributeFilter: ["class"],
      childList: false,
      characterData: false,
    });

    // observer for the change multiview event (show or hide)
    // it has to filter if the event is related to the synoptic instance
    let showOrHideObserver = new MutationObserver(function (event) {
      try {
        let newNode =
          event[0].addedNodes[0].children[0].children[0].attributes["view_id"]
            .value;

          if (newNode == self.synopticDiv) {
            // Launch the query again
            //document.getElementById(self.synopticDiv + "spinner").style.display = "block";
            self.data.load().then((d) => {
              
              d.childs.forEach((element) => {
                updateElementState(element);
                updateElementIcon(element);
              });

            }).then(() => {
              //document.getElementById(self.synopticDiv + "spinner").style.display = "none";
            });
          }
      } catch (e) {}
    });

    // obtains the parent of the synoptic
    let showOrHideObserverTarget = document.querySelector(
      '[view_id="multiview"]'
    );

    showOrHideObserver.observe(showOrHideObserverTarget, {
      attributes: false,
      childList: true,
      characterData: false,
    });

    // Attaching the event listener function to window's resize event
    window.addEventListener("resize", function() { self.doResponsive(self) });
  }
}
