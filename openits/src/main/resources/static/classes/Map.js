import * as iconGenerator from "../lib/iconGenerator.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";

import ElementWindow from "../classes/ElementWindow.js";
import ElementCommand from "../classes/ElementCommand.js";
import EquipmentTree from "../classes/EquipmentTree.js";
import {columnsElementFormList} from "../data/elementForm.js";
import { element_tree } from "../widgets/element_tree.js";
import SynopticElement from "../classes/SynopticElement.js";
import { util } from "../lib/util.js"

const SYNOPTIC_TUNNEL_RIOVERDE_ID = 900;

let map = null;
let view = null;
let selectClick = null;
var mapConf = "";
var mapStates = {};
var mapCoordinates = {};

var visibleElements = [];
var mapElements = [];
var mapIcons = {};

// GraphQL
//initialize a proxy
var graphSynoptic = webix.proxy(
  "GraphQL",
  `query($elem: String!)   {
    getElement(elementId: $elem)
    {
      id
      alias
      description
      elementTypeState { id }
      childs{
        id
        alias
        elementTypeState { id }
        elementType {
          id
          alias
        }
        elementValues{
          id
          value
          alerted
          elementTypeParam {
            description
          }
        }
      }
    }
  }
  `
);

export default class Map {
  constructor(synopticConf, elementId, id) {
    this.elements = [];
    this.elementsTypes = [];
    this.elementsToDraw = {};
    this.baseLayer = "";
    this.ratioWidth = 1.0;
    this.ratioHeight = 1.0;
    this.baseWidth = 0;
    this.baseHeight = 0;
    this.data = graphSynoptic;
    if(id){
      this.synopticDiv = id;
    }else{
      this.synopticDiv = synopticConf.id;
    }
    this.synopticConf = synopticConf;
    this.synopticType = "";

    this.map = null;
    if(elementId){
      this.elementId = elementId;
    }else{
      this.elementId = synopticConf.parentId;
    }
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

        // if it's a DMS, ask for the full data
        if(elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_10_DMS ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_24_PMV_A_C ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_26_PMV_64X64 ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_27_PMV_A_B ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_28_PMV_192X64 ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_29_PMV_B_D ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_30_PMV_128X64 ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_31_PMV_TEMP ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_32_PMV_WEIGHT ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_33_PMV_TRUCK_ALPHA ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_35_PMV ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_36_PMV ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_38_PMV_TXT ||
        elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_69_PMV_WEIGTH_LIMIT) {
          throw "DMSETDException";
        }

        // search for the element received in the elements of the layer
        if(retLayer != null) {
          

          let elementToUpdate = retLayer.getSource().getFeatureById(element.id);

          if(element.elementTypeState || element.elementTypeState.id) {
            mapStates[element.id] = element.elementTypeState.id;
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
              || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV_TEMP
              || elementType === elementTypeMap.ELEMENT_TYPE_32_PMV_WEIGHT
              || elementType === elementTypeMap.ELEMENT_TYPE_33_PMV_TRUCK_ALPHA
              || elementType === elementTypeMap.ELEMENT_TYPE_35_PMV
              || elementType === elementTypeMap.ELEMENT_TYPE_36_PMV
              || elementType === elementTypeMap.ELEMENT_TYPE_38_PMV_TXT
              || elementType === elementTypeMap.ELEMENT_TYPE_69_PMV_WEIGTH_LIMIT) {  // MATRIX PANEL

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

            let srcIcon;
            let scaleIcon;
            
            if(element.elementType.id === "ElementType:1"){
              srcIcon = "./img/meteo_"+getTrimId(element.elementTypeState.id)+".svg";
              scaleIcon = 0.008;
              mapIcons[element.id] = srcIcon;
            }else if(element.elementType.id === "ElementType:2"){
              srcIcon = "./img/cam_"+getTrimId(element.elementTypeState.id)+".svg";
              scaleIcon = 0.008;
              mapIcons[element.id] = srcIcon;
            }else{
              let tmpIcon = iconGenerator.getElementSVG(element, total_width, total_height, "");
              tmpIcon = tmpIcon.replace(/#/g, "%23");
              srcIcon = "data:image/svg+xml;utf8," + tmpIcon;
              scaleIcon = mapElement.height / total_height; 
              mapIcons[element.id] = tmpIcon;
            }

            let workingStyle = new ol.style.Style({
              image: new ol.style.Icon({
                opacity: 1,
                src: srcIcon,
                scale: scaleIcon
              }),
            });

            elementToUpdate.setStyle(workingStyle);
          }
        }

      });
    } catch (err) {
      console.error("Error: " + err);
      console.log("RETRY WITH GRAPHQL");

      let obj = JSON.parse(JSON.parse(message.body));

      obj.basicElementsVO.forEach((element) => {
        //initialize the proxy
        let elementQuery = webix.proxy(
          "GraphQL",
          `query ($elem: String!) {
            getElement(elementId: $elem)
            {
              id
              elementTypeState { id }
              description
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
                  alias
                }
              }
            }
          }`
        );

        // loads the data
        //graphSynoptic
        elementQuery
          .load({
            elem: element.id,
          })
          .then((data) => {

            // process the received data
            let element = data;

            let mapElement = mapConf[element.id];

            // get feature layer
            let retLayer = null;
            map.getLayers().forEach(function(l) {
              if(l.get("title") == "Elements") {
                retLayer = l;
              }
            });
            
            if(retLayer != null) {
              
              let elementToUpdate = retLayer.getSource().getFeatureById(element.id);
              if(elementToUpdate != null) {
                
                // let's store the states
                let elementState;
                if(element.elementTypeState && element.elementTypeState.id){
                  elementState = element.elementTypeState.id;
                }
                mapStates[element.id] = elementState;

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
                  || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV_TEMP
                  || elementType === elementTypeMap.ELEMENT_TYPE_32_PMV_WEIGHT
                  || elementType === elementTypeMap.ELEMENT_TYPE_33_PMV_TRUCK_ALPHA
                  || elementType === elementTypeMap.ELEMENT_TYPE_35_PMV
                  || elementType === elementTypeMap.ELEMENT_TYPE_36_PMV
                  || elementType === elementTypeMap.ELEMENT_TYPE_38_PMV_TXT
                  || elementType === elementTypeMap.ELEMENT_TYPE_69_PMV_WEIGTH_LIMIT) {  // MATRIX PANEL


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

                //let tmpIcon = iconGenerator.getElementSVG(element, mapElement.width, mapElement.height, "");
                let tmpIcon = iconGenerator.getElementSVG(element, total_width, total_height, "");
                
                // Fix for OpenLayers not reading the colors. The # character is not allowed, so it has to be replaced by %23
                tmpIcon = tmpIcon.replace(/#/g, "%23");

                // update the icon cache
                mapIcons[element.id] = tmpIcon;

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
                    //console.log(canvas.toDataURL());
                  }
                );

                let opacity = 0;
                if(visibleElements.includes(element.id)) {
                  opacity = 1;
                }

                let srcIcon;
                let scaleIcon;
                
                if(element.elementType.id === "ElementType:1"){
                  srcIcon = "./img/meteo_"+getTrimId(element.elementTypeState.id)+".svg";
                  scaleIcon = 0.008;
                  mapIcons[element.id] = srcIcon;
                }else if(element.elementType.id === "ElementType:2"){
                  srcIcon = "./img/cam_"+getTrimId(element.elementTypeState.id)+".svg";
                  scaleIcon = 0.008;
                  mapIcons[element.id] = srcIcon;
                }else{
                  srcIcon = "data:image/svg+xml;utf8," + tmpIcon;
                  scaleIcon = mapElement.height / total_height; 
                  mapIcons[element.id] = tmpIcon;
                }
    
                let workingStyle = new ol.style.Style({
                  image: new ol.style.Icon({
                    opacity: 1,
                    src: srcIcon,
                    scale: scaleIcon
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

  checkElement() {
    let newVisibleElements = [];

    map.getLayers().forEach(l => {
      if(l.get('id') == "Elements") {

        for (let i=0; i<mapElements.length; i++) {
          let id = mapElements[i];

          if(l.getSource().getFeatureById(id) != null) {
            // check if the element is checked or unchecked
            let currentStyle = l.getSource().getFeatureById(id).getStyle();
            let opacity = 0;
            
            if($$("element_tree").getChecked().includes(id)) {
              // set the opacity
              opacity = 1;
              newVisibleElements.push(id);
            }
            
            let newStyle = currentStyle;
            // update the opacity
            newStyle.getImage().opacity_ = opacity;
  
            l.getSource().getFeatureById(id).setStyle(newStyle);
          }

        }

      }
    });

    visibleElements = newVisibleElements;

    // check if there are objects selected
    let shouldEnableButton = false;

    if($$("element_tree").getChecked().length > 0) {
      let first_item = $$("element_tree").getChecked().find(e => e.match(/Element/g));
      
      if(typeof first_item != 'undefined') {
        for(let i=0; i<$$("element_tree").getChecked().length; i++) {
          let checkedElement = $$("element_tree").getChecked()[i];
          let checkedElementLevel = $$("element_tree").getItem(checkedElement).$level;
  
          if(checkedElementLevel != 1) {
            if(elementTypeMap.getElementType(checkedElement) == elementTypeMap.getElementType(first_item)) {
              shouldEnableButton = true
            } else {
              shouldEnableButton = false;
              break;
            }
          }
        }
      }

    }

    if(shouldEnableButton == true) {
      $$("element_tree_button").enable();
    } else {
      $$("element_tree_button").disable();
    }
    
  }

  loadData() {
    let self = this;

    this.data
      .load({
        elem: this.elementId,
      })
      .then((d) => {
        let markers = [];

        
        // TODO: should we clean the created canvas?
        d.childs.forEach((element) => {
          if (element.id in this.synopticConf) {

            // add to the visible elements list
            mapElements.push(element.id);

            // let's store the states
            let elementState;
            if(element.elementTypeState && element.elementTypeState.id){
              elementState = element.elementTypeState.id;
            }
            mapStates[element.id] = elementState;
            
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
              || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV_TEMP
              || elementType === elementTypeMap.ELEMENT_TYPE_32_PMV_WEIGHT
              || elementType === elementTypeMap.ELEMENT_TYPE_33_PMV_TRUCK_ALPHA
              || elementType === elementTypeMap.ELEMENT_TYPE_35_PMV
              || elementType === elementTypeMap.ELEMENT_TYPE_36_PMV
              || elementType === elementTypeMap.ELEMENT_TYPE_38_PMV_TXT
              || elementType === elementTypeMap.ELEMENT_TYPE_69_PMV_WEIGTH_LIMIT) {  // MATRIX PANEL
           
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

            let canvas = document.createElement("canvas");
            // get canvas context for drawing on canvas
            let context = canvas.getContext("2d");

            let srcIcon;
            let scaleIcon;
            
            if(element.elementType.id === "ElementType:1"){
              srcIcon = "./img/meteo_"+getTrimId(element.elementTypeState.id)+".svg";
              scaleIcon = 0.008;
              mapIcons[element.id] = srcIcon;
            }else if(element.elementType.id === "ElementType:2"){
              srcIcon = "./img/cam_"+getTrimId(element.elementTypeState.id)+".svg";
              scaleIcon = 0.008;
              mapIcons[element.id] = srcIcon;
            }else{
              let tmpIcon = iconGenerator.getElementSVG(element, total_width, total_height, "");
              tmpIcon = tmpIcon.replace(/#/g, "%23");
              srcIcon = "data:image/svg+xml;utf8," + tmpIcon;
              scaleIcon = mapElement.height / total_height; 
              mapIcons[element.id] = tmpIcon;
            }

            let workingStyle = new ol.style.Style({
              image: new ol.style.Icon({
                opacity: 1,
                src: srcIcon,
                scale: scaleIcon
              }),
            });

            workingFeature.setStyle(workingStyle);
            workingFeature.set("component", "Element");
            workingFeature.setId(element.id.toString());
            markers.push(workingFeature);
            
          }
        });

        // replaces the element tree in the DOM
        let elementTree = new EquipmentTree(this.elementId, null);

        // create the sidemenu for the equipment tree
        webix.ui({
          view: "sidemenu-win",
          id: "menu" + this.synopticDiv,
          width: 230,
          position: (position) => {
            var toolbarHeight = $$("mainToolbar").$height;
            position.top = toolbarHeight;
            position.left = $$(this.synopticDiv).$width - 230;
            var mainHeight = $$(this.synopticDiv).$height - toolbarHeight;
            position.height = mainHeight;
            position.width = 230;
          },
          body: element_tree
        });

        webix.ui({
          id: "elementTreeView", 
          rows: [
            /*
            { 
              view: "template", 
              template: $.i18n("defaultheader.equipment"),
              type: "header"
            },
            */
            {
              view:"toolbar", 
              cols:[
                { 
                  view: "label", 
                  label: $.i18n("defaultheader.equipment")
                },
                { 
                  view: "button", 
                  value: "Close", 
                  width: 38, 
                  type:"icon", 
                  icon:"wxi-angle-double-up",
                  click: function() {
                    // hide the panel
                    $$("menu" + self.synopticDiv).hide();
                    // hide the children
                    $$("menu" + self.synopticDiv).getChildViews()[0].getChildViews().forEach(e => { e.hide() });
                  }
                }
              ]
            },
            {
              id: "element_tree_filter",
              view: "text",
              label: "Filter",
              labelPosition: "left"
            },
            {
              id: "element_tree",
              view: "tree",
              borderless: true,
              scroll: "auto",
              select: true,
              multiselect: true,
              threeState: true,
              template: function(obj, common){
                if (obj.$level > 1) {
                  return common.icon(obj,common) + common.checkbox(obj, common) +"&nbsp;"+ "<span class='elementTreeLeaf'>" + obj.value + "</span>";
                } else {
                  return common.icon(obj,common) + common.checkbox(obj, common) +"&nbsp;"+ "<span class='elementTreeLeaf'>" + obj.value + "</span>";
                }
              }
            },
            {
              id: "element_tree_button",
              view: "button",
              disabled: true,
              value: "Command",
              on: {
                "onItemClick": function(id, e) {
                  let filteredElements = $$("element_tree").getChecked().filter(e => e.match(/Element/g));
                  let elementCommand = new ElementCommand(filteredElements, columnsElementFormList);
                  webix
                    .ui({
                      id: "win_command" + filteredElements[0],
                      view: "window",
                      move: true,
                      close: true,
                      position: "right",
                      body: elementCommand.getComponent(),
                      on: {
                        onDestruct: function (obj) {
                          
                        },
                        onHide: function (obj) {
                          $$("win_command" + filteredElements[0]).destructor();
                        },
                      },
                    })
                    .show();
                }
              }
            }
          ],
          url: elementTree.getComponent()
        }, $$("elementTreeView"));

        // as we are in the map, centers the item on click
        $$("element_tree").attachEvent("onSelectChange", function(id) {
          if(typeof self.synopticConf[id] != 'undefined') {
            let lonlat = ol.proj.fromLonLat([self.synopticConf[id].lon, self.synopticConf[id].lat]);
            view.setCenter(lonlat);

            //selectInteraction.getFeatures().clear();
            selectClick.getFeatures().clear();

            // get feature layer
            let retLayer = null;
            map.getLayers().forEach(function(l) {
              if(l.get("title") == "Elements") {
                retLayer = l;
              }
            });

            let elementToUpdate = retLayer.getSource().getFeatureById(id);
            selectClick.getFeatures().push(elementToUpdate);

            selectClick.dispatchEvent({
              type: 'select',
              selected: [elementToUpdate],
              deselected: []
           });

          }
          
        });

        $$("element_tree").attachEvent("onItemCheck", self.checkElement);
        
        return markers;
      })
      .then((markers) => {
        let vectorSource = new ol.source.Vector({
          wrapX: false,
          features: markers,
        });

        let incidentLayer = new ol.layer.Vector({
          id: "Elements",
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
  init() {
    this.elementReady('[view_id="' + this.synopticDiv + '"]').then((c) => {
      let divToAdd = document.createElement("div");
      divToAdd.setAttribute("id", "map");
      divToAdd.setAttribute("class", "ol-map");
      document
        .querySelector('[view_id="' + this.synopticDiv + '"]')
        .prepend(divToAdd);

      view = new ol.View({ center: ol.proj.fromLonLat([this.synopticConf["lon"], this.synopticConf["lat"]]), zoom: this.synopticConf["zoom"]});

      // treeMap Button
      var TreeMapControl = /*@__PURE__*/(function (Control) {
        function TreeMapControl(opt_options) {
          var options = opt_options || {};
      
          var button = document.createElement('button');
          button.innerHTML = '<span class="webix_icon wxi-angle-double-down">&nbsp;</span>';
      
          var element = document.createElement('div');
          element.className = 'tagsButtonMap ol-unselectable ol-control';
          element.appendChild(button);
      
          Control.call(this, {
            element: element,
            target: options.target,
          });
      
          button.addEventListener('click', this.handleTreeMap.bind(this), false);
        }
      
        if ( Control ) TreeMapControl.__proto__ = Control;
        TreeMapControl.prototype = Object.create( Control && Control.prototype );
        TreeMapControl.prototype.constructor = TreeMapControl;
      
        TreeMapControl.prototype.handleTreeMap = function handleTreeMap () {
          //$$("menu").show();
          if (!$$("menu" + self.synopticDiv).isVisible()){
            // show the panel
            $$("menu" + self.synopticDiv).show();
            // show the children
            $$("menu" + self.synopticDiv).getChildViews()[0].getChildViews().forEach(e => { e.show() });
          }
          else {
            // hide the panel
            $$("menu" + self.synopticDiv).hide();
            // hide the children
            $$("menu" + self.synopticDiv).getChildViews()[0].getChildViews().forEach(e => { e.hide() });
          }
        };
      
        return TreeMapControl;
      }(ol.control.Control));

      // instantiates the map
      map = new ol.Map({
        controls: ol.control.defaults().extend([new TreeMapControl()]),
        target: "map",
        layers: [
          new ol.layer.Tile({
            source: new ol.source.OSM(),
          }),
        ],
        view: view,
      });

      // select interaction working on "click"
      let zTopStyle = new ol.style.Style({
        //zIndex: -1000,
        fill: new ol.style.Fill({
          color: 'rgba(255,255,255,0.7)'
        }),
        stroke: new ol.style.Stroke({
          color: '#3399CC',
          width: 3
        }),
        opacity: 0
      });

      selectClick = new ol.interaction.Select({
        condition: ol.events.condition.singleClick,
        //style: zTopStyle
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

        let srcIcon;
        let scaleIcon;
        
        if(elementTypeMap.getElementType(element_id) == "1"){
          srcIcon = mapIcons[element_id];
          scaleIcon = 0.010;
        }else if(elementTypeMap.getElementType(element_id) == "2"){
          srcIcon = mapIcons[element_id];
          scaleIcon = 0.010;
        }else{
          srcIcon = "data:image/svg+xml;utf8," + mapIcons[element_id];
          scaleIcon = 1.25;  
        }
        
        let zTopStyle = new ol.style.Style({
          image: new ol.style.Icon({
            opacity: 1,
            scale: scaleIcon,
            src: srcIcon,
          }),
          zIndex: 100,
        });
        
        feature.setStyle(zTopStyle);
        
        if (
          elementTypeMap.getElementType(element_id) !=
          elementTypeMap.ELEMENT_TYPE_7_ZONE
        ) {
          if($$("winMap" + element_id) != null){
            $$("winMap" + element_id).destructor();
        }
        let elementWindow = new ElementWindow(element_id,"ElementType:" + element_id.split(":")[1]);
        webix
        .ui({
          id: "winMap" + element_id,
          view: "window",
          move: true,
          close: true,
          position: "right",
          body: elementWindow.getComponent(),
          on: {
          onDestruct: function(obj){
            //elementForm.disconnect();
          },
          onHide: function(obj){
            //elementForm.disconnect();
            //$$("win" + elementId).destructor();
          },
          onShow: function(obj){
            elementWindow.loadData();
          }
          }
        })
        .show();
          
            // auto de-select
            //e.target.getFeatures().remove(feature);
        } else {
          //TODO Deprecated, quedarse con la parte del catch correctamente recuperado el idModule
          try {
            $$("syn0").show();  
          } catch (error) {
            let module = window.em.getByField(EntityType.MODULE, "id", SYNOPTIC_TUNNEL_RIOVERDE_ID);
            util.module.open(module, null, null, null, null);	
          }
        }
      });

      //this.map.addInteraction(selectClick);
      map.addInteraction(selectClick);

      // loads data and draws for first time
      this.loadData();
    });

    mapConf = this.synopticConf;

    let self = this;
    // waits for the element and starts drawing
    this.elementReady('[view_id="' + this.synopticDiv + '"]').then((c) => {
      let spinnerDivToAdd = document.createElement("div");
      spinnerDivToAdd.setAttribute("id", this.synopticDiv + "spinner");
      spinnerDivToAdd.setAttribute("class", "spinner");
      document
        .querySelector('[view_id="' + this.synopticDiv + '"]')
        .prepend(spinnerDivToAdd);

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

    try {
      observer.observe(e, {
        attributes: true,
        attributeFilter: ["class"],
        childList: false,
        characterData: false,
      });  
    } catch (error) {
      
    }
    

    // observer for the change multiview event (show or hide)
    // it has to filter if the event is related to the synoptic instance
    let showOrHideObserver = new MutationObserver(function (event) {
      try {
        if ($$("menu" + self.synopticDiv).isVisible()){
          // show the panel
          $$("menu" + self.synopticDiv).hide();
          // show the children
          $$("menu" + self.synopticDiv).getChildViews()[0].getChildViews().forEach(e => { e.hide() });
        }
        
        let newNode = null;
        if(event[0].addedNodes.length > 0) {
          Array.from(event[0].addedNodes[0].children).forEach(e => {
            if(typeof e.attributes["view_id"] != 'undefined') {
              if(e.attributes["view_id"].value == self.synopticDiv) {
                newNode = e.attributes["view_id"].value;
              }
            }
          });          
        }

          if (newNode == self.synopticDiv) {
            // Launch the query again
            document.getElementById(self.synopticDiv + "spinner").style.display = "block";
            self.data.load({
              elem: self.elementId,
            }).then((d) => {
              
              let markers = [];

              // TODO: should we clean the created canvas?
              d.childs.forEach((element) => {
                if (element.id in self.synopticConf) {

                  // let's store the states
                  let elementState;
                  if(element.elementTypeState && element.elementTypeState.id){
                    elementState = element.elementTypeState.id;
                  }
                  mapStates[element.id] = elementState;
                  
                  let mapElement = self.synopticConf[element.id];

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
                    || elementType === elementTypeMap.ELEMENT_TYPE_31_PMV_TEMP
                    || elementType === elementTypeMap.ELEMENT_TYPE_32_PMV_WEIGHT
                    || elementType === elementTypeMap.ELEMENT_TYPE_33_PMV_TRUCK_ALPHA
                    || elementType === elementTypeMap.ELEMENT_TYPE_35_PMV
                    || elementType === elementTypeMap.ELEMENT_TYPE_36_PMV
                    || elementType === elementTypeMap.ELEMENT_TYPE_38_PMV_TXT
                    || elementType === elementTypeMap.ELEMENT_TYPE_69_PMV_WEIGTH_LIMIT) {  // MATRIX PANEL
                
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


                  let tmpIcon = iconGenerator.getElementSVG(element, total_width, total_height, "");

                  // Fix for OpenLayers not reading the colors. The # character is not allowed, so it has to be replaced by %23
                  tmpIcon = tmpIcon.replace(/#/g, "%23");

                  // update the icon cache
                  mapIcons[element.id] = tmpIcon;

                  let canvas = document.createElement("canvas");
                  // get canvas context for drawing on canvas
                  let context = canvas.getContext("2d");

                  let srcIcon;
                  let scaleIcon;
                  
                  if(element.elementType.id === "ElementType:1"){
                    srcIcon = "./img/meteo_"+getTrimId(element.elementTypeState.id)+".svg";
                    scaleIcon = 0.008;
                  }else if(element.elementType.id === "ElementType:2"){
                    srcIcon = "./img/cam_"+getTrimId(element.elementTypeState.id)+".svg";
                    scaleIcon = 0.008;
                  }else{
                    srcIcon = "data:image/svg+xml;utf8," + tmpIcon;
                    scaleIcon = mapElement.height / total_height;  
                  }
                
                  let workingStyle = new ol.style.Style({
                    image: new ol.style.Icon({
                      opacity: 1,
                      src: srcIcon,
                      scale: scaleIcon
                    }),
                  });

                  workingFeature.setStyle(workingStyle);
                  workingFeature.set("component", "Element");
                  workingFeature.setId(element.id.toString());
                  markers.push(workingFeature);
                  
                }
              });

              return markers;

            }).then((markers) => {
              let vectorSource = new ol.source.Vector({
                wrapX: false,
                features: markers,
              });
      
              let incidentLayer = new ol.layer.Vector({
                id: "Elements",
                title: "Elements",
                source: vectorSource,
              });
      
              // clean the current layers
              map.getLayers().forEach(layer => {
                if (layer && layer.get('id') === 'Elements') {
                  map.removeLayer(layer);
                }
              });

              // add the new layer
              map.addLayer(incidentLayer);
            })
            .then(() => {
              

              document.getElementById(self.synopticDiv + "spinner").style.display = "none";
            });
          }
      } catch (e) {
        console.error(e);
      }
    });

    // obtains the parent of the synoptic
    let showOrHideObserverTarget = document.querySelector(
      '[view_id="multiview"]'
    );

    try {
      showOrHideObserver.observe(showOrHideObserverTarget, {
        attributes: false,
        childList: true,
        characterData: false,
      });  
    } catch (error) {
      console.warn(error);
    }
    

    // Attaching the event listener function to window's resize event
    window.addEventListener("resize", function() { self.doResponsive(self) });
  }
}
