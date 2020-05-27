import { mapTG } from "../data/maps.js";
import * as iconGenerator from "../lib/iconGenerator.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";

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

      obj.basicElementsVO.forEach((element) => {
        // check if it's a tunnel synoptic and changes the main bar in the synoptic
        let element_type = element.id.split(":")[1];
        if (element_type == "7") {
          let color = "";
          if (element.state != 3) {
            color = "#ebb700";
          } else {
            color = "#ff0000";
          }

          document
            .getElementById(element.id + ":Bar")
            .setAttribute("fill", color);
        }

        // updates the state and the icon
        /*
        updateElementState(element);
        updateElementIcon(element);
        */
        getElementIcon(element);
      });
    } catch (err) {
      console.log("Error: " + err.message);
      console.log("RETRY WITH GRAPHQL");

      let obj = JSON.parse(JSON.parse(message.body));

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

            updateElementState(element);
            updateElementIcon(element);
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
      console.log(el);
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

        console.log(this.map);
        
        d.childs.forEach((element) => {
          if (element.id in this.synopticConf) {
            let mapElement = this.synopticConf[element.id];

            let workingFeature = new ol.Feature({
              geometry: new ol.geom.Point(ol.proj.fromLonLat([mapElement.lon, mapElement.lat])),
              id: element.id,
            });
           
            if(elementTypeMap.getElementType(element.id) == elementTypeMap.DMS_ELEMENT_TYPE) {
              let tmpIcon = iconGenerator.getElementSVG(element, 150, 40);
              //let tmpIcon = '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape" xmlns:sodipodi="http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd" version="1.1" width="480px" height="480px"> <g id="Element:10:1" state="2" transform="scale(6.315789473684211,20)"> <g id="layer1" transform="translate(0,-272.12915)"> <g id="g1739-1" transform="matrix(0.40956503,0,0,0.48504268,-500.12204,357.57278)"> <rect y="-174.73952" x="1222.5098" height="48.450024" width="48.450024" fill="black" id="Element:10:1:rect2250-2-89" style="fill-opacity:1;stroke-width:2.8125;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;" /> <rect y="-174.73952" x="1270.9597" height="48.44997" width="136.0842" id="Element:10:1:rect2085-1" style="fill-opacity:1;stroke-width:2.8125;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" /> <text xml:space="preserve" y="-161.73952" x="1275.9597" id="text2462-1"> <tspan sodipodi:role="line" id="tspan2460" style="stroke-width:0.06259221"> <tspan fill="yellow" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill-opacity:1;stroke-width:0.06259221" id="Element:10:1:linea1">aaaaaa</tspan> </tspan> </text> <text xml:space="preserve" y="-148.73952" x="1275.9597" id="text2462-1"> <tspan sodipodi:role="line" id="tspan2460" style="stroke-width:0.06259221"> <tspan id="Element:10:1:linea2"></tspan> </tspan> </text> <text xml:space="preserve" y="-134.73952" x="1275.9597" id="text2462-1"> <tspan sodipodi:role="line" id="tspan2460" style="stroke-width:0.06259221"> <tspan id="Element:10:1:linea3"></tspan> </tspan> </text> </g> </g> </g> </svg>';

              console.log(tmpIcon);
              
              //let tmpIcon = '<svg width="120" height="120" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">'
              //+ '<circle cx="60" cy="60" r="60"/>'
              //+ '</svg>';
              

              let canvas = document.createElement('canvas');
              // get canvas context for drawing on canvas
              let context = canvas.getContext('2d');
              // set canvas size
              //canvas.width = width;
              //canvas.height = height;
              
              // call svgString2Image function
              iconGenerator.drawInlineSVG(canvas, context, tmpIcon, function() {
                //alert("HELLO");
                console.log(canvas.toDataURL());
              });
              
              //console.log(tmpIcon);
              
              let workingStyle = new ol.style.Style({
                image: new ol.style.Icon({
                  opacity: 1,
                  src: "data:image/svg+xml;utf8," + tmpIcon,
                  scale: 1
                }),
              });
  
              workingFeature.setStyle(workingStyle);
              workingFeature.set("component", "Incident");
              workingFeature.setId(element.id.toString());
              markers.push(workingFeature);
            }
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
          title: "Incidents",
          source: vectorSource,
        });

        this.map.addLayer(incidentLayer);
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

      this.map = new ol.Map({
        target: "map",
        layers: [
          new ol.layer.Tile({
            source: new ol.source.OSM(),
          }),
        ],
        view: new ol.View({
          center: ol.proj.fromLonLat([174.93851974486, -41.0908343523548]),
          zoom: 12,
        }),
      });

      //this.elementReady("#map").then((c1) => {
        // loads data and draws for first time
        this.loadData();
      //});
    });

    this.synopticConf = mapTG;

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

    let self = this;
    // waits for the element and starts drawing
    this.elementReady('[view_id="' + this.synopticDiv + '"]').then((c) => {
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

      this.initResponsive();
    });
    */
  }
}
