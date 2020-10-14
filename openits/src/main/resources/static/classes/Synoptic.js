import ElementForm from "../classes/ElementForm.js";
import {
  getElementIcon,
  updateElementIcon,
  updateElementState
} from "../lib/iconGenerator.js";
import {columnsElementFormList} from "../data/elementForm.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";
import SynopticElement from "../classes/SynopticElement.js";
import {ELEMENT_TYPE} from "../data/GLOBAL_PROPERTIES.js";

// Size of a row in the layers dropdown
const DROPDOWN_ROW_SIZE = 43;

let graphSynoptic = webix.proxy(
  "GraphQL",
  `query($elem: String!) {
    getElement(elementId: $elem)
    {
      id
      alias
      description
      state
      childs{
        id    
        state
        description
        elementType{
          id
        }
      }
    }
  }
  `
);

export default class Synoptic {
  constructor(synopticConf) {
    this.elementsTypes = [];
    this.elementsToDraw = {};
    this.layersToDraw = {};
    this.baseLayer = "";
    this.ratioWidth = 1.0;
    this.ratioHeight = 1.0;
    this.baseWidth = 0;
    this.baseHeight = 0;
    this.elementId = synopticConf.parentId;
    this.synopticDiv = synopticConf.id;
    this.synopticConf = synopticConf;
    this.synopticType = synopticConf.id;
    this.metadata = {
      "id":"synoptic",
      "view":"view",
      "autoConfig":true,
      "select":true
    };
  }

  getComponent() {
    if (graphSynoptic.includes("http") || graphSynoptic.includes("GraphQL")) {
      this.metadata.url = graphSynoptic;
    } else {
      this.metadata.data = graphSynoptic;
    }
    return this.metadata;
  }


  /*
   *	Tries to get the data from Stomp, but if it doesn't find the needed data,
   * gets it by GraphQL
   */
  processStompMessageWithFallback(message) {
    try {
      let obj = JSON.parse(JSON.parse(message.body));

      
      obj.basicElementsVO.forEach((element) => {
        
        if(elementTypeMap.getElementType(element.id) == elementTypeMap.ELEMENT_TYPE_10_DMS) {
          throw "DMSException";
        }
        getElementIcon(element);
      });

    } catch (err) {
      console.log("Error: " + err.message);
      console.log("Retrying with GraphQL");

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

  // Layer groups
  handleLayers(e, initial=false) {

    if(!initial) {
      if (this.layersToDraw[e] == "visible") {
        this.layersToDraw[e] = "hidden";
      } else {
        this.layersToDraw[e] = "visible";
      }
    }

    if(this.synopticConf["ExclusiveLayers"] == true && e != "BASE_LAYER" && e != "ROAD") {
      
      for (var k in this.layersToDraw) {
        if (k != e && k != "BASE_LAYER" && k != "ROAD") {
          this.layersToDraw[k] = "hidden";

          // check if there is a base layer and changes its visibility
          if(Object.keys(this.synopticConf["BaseLayers"]).includes(k)) {
            let el = document.getElementById("capa_base_" + k);
            if(el != null && typeof el != 'undefined') {
              el.setAttribute("visibility", this.layersToDraw[k]);
              el.style["visibility"] = this.layersToDraw[k];
            }
          }

          // item
          this.synopticConf["Layers"][k]["items"].forEach((item) => {
      
            let el = document.getElementById(item);
            if(el != null && typeof el != 'undefined') {
              el.setAttribute("visibility", this.layersToDraw[k]);
              el.style["visibility"] = this.layersToDraw[k];
      
              // Fix for the FAN
              if (this.layersToDraw[e] == "visible") {
                el.style["opacity"] = 1;
              } else {
                el.style["opacity"] = 0;
              }
            }
      
          });

          let newLabel = ""
          if(this.layersToDraw[k] == "visible") {
            if(this.synopticConf["Layers"][k]["translation_key"] != "") {
              newLabel = "<span class='webix_icon wxi-checkbox-marked'></span>" + $.i18n(this.synopticConf["Layers"][k]["translation_key"]);
            } else {
              newLabel = "<span class='webix_icon wxi-checkbox-marked'></span>" + k;
            }
          } else {
            if(this.synopticConf["Layers"][k]["translation_key"] != "") {
              newLabel = "<span class='webix_icon wxi-checkbox-blank'></span>" + $.i18n(this.synopticConf["Layers"][k]["translation_key"]);
            } else {
              newLabel = "<span class='webix_icon wxi-checkbox-blank'></span>" + k;
            }
          }

          if(document.getElementById("dropDown" + k) != null && typeof document.getElementById("dropDown" + k) != 'undefined') {
            document.getElementById("dropDown" + k).innerHTML = newLabel;
          }
        }
      }

      this.layersToDraw[e] = "visible";  

    } 


    // base layer
    if(e == "BASE_LAYER") {
      let els = this.s.selectAll('[component="' + e + '"]');

      els.forEach((el) => {
        el.attr("visibility", this.layersToDraw[e]);
      });
    } else {

      // check if there is a base layer and changes its visibility
      if(Object.keys(this.synopticConf["BaseLayers"]).includes(e)) {
        let el = document.getElementById("capa_base_" + e);
        if(el != null && typeof el != 'undefined') {
          el.setAttribute("visibility", this.layersToDraw[e]);
          el.style["visibility"] = this.layersToDraw[e];
        }
      }

      // item
      this.synopticConf["Layers"][e]["items"].forEach((item) => {
  
        if(Object.keys(this.synopticConf["Layers"]).includes(item) && (item != e)) {
  
          // reset the layer visibility to set the same as the group layer
          if (this.layersToDraw[e] == "visible") {
            this.layersToDraw[item] = "hidden";
          } else {
            this.layersToDraw[item] = "visible";
          }
  
          this.handleLayers(item);

        } else {
  
          let el = document.getElementById(item);
          if(el != null && typeof el != 'undefined') {
            el.setAttribute("visibility", this.layersToDraw[e]);
            el.style["visibility"] = this.layersToDraw[e];
    
            // Fix for the FAN
            if (this.layersToDraw[e] == "visible") {
              el.style["opacity"] = 1;
            } else {
              el.style["opacity"] = 0;
            }
          }
        }
      });

    }
    


    let newLabel = ""
    if(this.layersToDraw[e] == "visible") {
      if(this.synopticConf["Layers"][e]["translation_key"] != "") {
        newLabel = "<span class='webix_icon wxi-checkbox-marked'></span>" + $.i18n(this.synopticConf["Layers"][e]["translation_key"]);
      } else {
        newLabel = "<span class='webix_icon wxi-checkbox-marked'></span>" + e;
      }
    } else {
      if(this.synopticConf["Layers"][e]["translation_key"] != "") {
        newLabel = "<span class='webix_icon wxi-checkbox-blank'></span>" + $.i18n(this.synopticConf["Layers"][e]["translation_key"]);
      } else {
        newLabel = "<span class='webix_icon wxi-checkbox-blank'></span>" + e;
      }
    }

    if(document.getElementById("dropDown" + e) != null && typeof document.getElementById("dropDown" + e) != 'undefined') {
      document.getElementById("dropDown" + e).innerHTML = newLabel;
    }

  }  // Layer groups

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

  generateLayerTree(layersList, depth=0, father) {

    let self = this;

    Object.keys(layersList).map((n) => {
      
      var caret = 0;
      var linkToAdd = document.createElement("a");
      linkToAdd.id = "dropDown" + n;
  

      if(Object.keys(layersList[n]).length > 0) {
        caret = document.createElement("span");
        caret.setAttribute("class", "caret");
        caret.style["padding-left"] = (10 + 30 * depth) + "px";
      } else {
        linkToAdd.style["padding-left"] = (10 + 30 * depth) + "px";
      }		

      linkToAdd.onclick = function () {
        self.handleLayers(n);
      };

      if(this.synopticConf["Layers"][n]["translation_key"] != "") {
        linkToAdd.innerHTML = $.i18n(this.synopticConf["Layers"][n]["translation_key"]);
      } else {
        linkToAdd.innerHTML = n;
      }

      if(Object.keys(layersList[n]).length > 0) {
        let div = document.createElement("li"); 

        father.appendChild(div);
        div.appendChild(linkToAdd);
        div.appendChild(caret);
    
        self.layersToDraw[n] = self.synopticConf["Layers"][n]["visibility"];
        self.handleLayers(n, true);

        let nested = document.createElement("ul");
        nested.setAttribute("class", "nested");
        div.appendChild(nested);

        self.generateLayerTree(layersList[n], depth + 1, nested);
      } else { 
        father.appendChild(linkToAdd);
        self.layersToDraw[n] = self.synopticConf["Layers"][n]["visibility"];
        self.handleLayers(n, true);
      }		
    });

  }

  loadData() {
    let self = this;

    graphSynoptic
      .load({
        elem: this.elementId,
      }).then((d) => {
        // creates the base layer
        //this.baseLayer = d.icon;
        this.baseLayer = this.synopticConf["BaseLayers"]["base"];

        // get the container size
        let c = document.querySelector('[view_id="' + this.synopticDiv + '"]');
        this.s = Snap("#" + this.synopticDiv + "SVG");
        let total_width = c.offsetWidth;
        let total_height = c.offsetHeight;

        // set the aspect ratios
        this.ratioWidth = total_width / 1920;
        this.ratioHeight = total_height / 1080;

        // set width and height
        this.s.attr({ width: total_width, height: total_height });

        // add basemap and base road to elements_to_draw
        this.elementsTypes.push("BASE_LAYER");
        this.elementsToDraw["BASE_LAYER"] = "visible";
        //this.layersToDraw["BASE_LAYER"] = "visible";
        
        // if we are in a tunnel synoptic, put the title bar
        if (this.synopticType == "synopticTunnel") {
          let titleBackground = this.s.rect(
            36 * this.ratioWidth,
            total_height - 48 * this.ratioHeight - 1004 * this.ratioHeight,
            1722 * this.ratioWidth,
            58 * this.ratioHeight
          );

          // It's set just for the demo
          if (d.state != 3) {
            titleBackground.attr({
              fill: "#ebb700",
            });
          } else {
            titleBackground.attr({
              fill: "#ff0000",
            });
          }

          // FIXME: The id should be: 'id: d.id + ":Bar"' in a generic way
          titleBackground.attr({
            id: d.id + ":Bar",
            class: "capa_mapa",
          });

          // FIXME: there is no "Rio Verde" tunnel in the DB, just "Tunnel 1" so we set it manually
          // We should bring modify the GraphQL query (add "description" field) and bring it from the DB
          let titleText = this.s.text(
            806 * this.ratioWidth,
            total_height - 15 * this.ratioHeight - 1011 * this.ratioHeight,
            "RIO VERDE"
          );
          let subTitleText = this.s.text(
            855 * this.ratioWidth,
            total_height - 1009 * this.ratioHeight,
            "290 m"
          );
          titleText.attr({
            "font-size": 28 * this.ratioHeight,
            fill: "#ffffff",
            class: "banner_title",
          });
          subTitleText.attr({
            "font-size": 18 * this.ratioHeight,
            fill: "#ffffff",
            class: "banner_title",
          });
        }

        // draws the base layer
        this.s
          .image("./img/" + this.baseLayer, 0, 0, total_width, total_height)
          .attr({
            id: "capa_base",
            //visibility: this.elementsToDraw["BASE_LAYER"],
            visibility: this.synopticConf["Layers"]["BASE_LAYER"]["visibility"],
            component: "BASE_LAYER",
          });


        // adds the other base layers
        let self = this;
        Object.keys(this.synopticConf["BaseLayers"]).forEach(function(k) {
          if(k != "base") { // base is already added

            self.elementsToDraw[k] = "visible";
            if(!self.elementsTypes.includes(k)) {
              self.elementsTypes.push(k);
            }

            self.s
            .image("./img/" + self.synopticConf["BaseLayers"][k], 0, 0, total_width, total_height)
            .attr({
              id: "capa_base_" + k,
              visibility: self.elementsToDraw[k],
              component: k,
            });
            
          }

        });

        return d;
      })
      .then((d) => {
        d.childs.forEach((element) => {
          let synopticElement = new SynopticElement(element);
          synopticElement.paintIcon(this.synopticConf, this.s,  this.ratioWidth,  this.ratioHeight);
        });

        return d;
      })
      .then((d) => {
        // draw the buttons for filtering the element type
        let self = this;

        // adds the dropdown button
        let buttonToAdd = document.createElement("button");
        buttonToAdd.innerHTML = "<span class='webix_icon wxi-drag'></span>";
        buttonToAdd.setAttribute("class", "dropbtn");
        document
          .getElementById(this.synopticDiv + "dropdownDiv")
          .appendChild(buttonToAdd);

        // adds the dropdown div
        let divToAdd = document.createElement("div");
        divToAdd.setAttribute("id", this.synopticDiv + "dropdownDivContent");
        divToAdd.setAttribute("class", "dropdown-content");
        document
          .getElementById(this.synopticDiv + "dropdownDiv")
          .appendChild(divToAdd);

        let layersList = {};
        let alreadyAddedLayersList = [];

        // Generate the layer tree
        for(let l in Object.keys(this.synopticConf["Layers"])) {
          let layerName = Object.keys(this.synopticConf["Layers"])[l];
          if(!alreadyAddedLayersList.includes(layerName)) {
            layersList[layerName] = {};
            alreadyAddedLayersList.push(layerName);

            for(let item in this.synopticConf["Layers"][layerName]["items"]) {
              let itemName = this.synopticConf["Layers"][layerName]["items"][item];
              if(Object.keys(this.synopticConf["Layers"]).includes(itemName)) {
                delete layersList[itemName];
                layersList[layerName][itemName] = {};
                alreadyAddedLayersList.push(itemName);
              }
            }
          }

        }

        // Generate the tree
        this.generateLayerTree(layersList, 0, divToAdd);
        var toggler = document.getElementsByClassName("caret");
        var i;
        
        for (i = 0; i < toggler.length; i++) {
          toggler[i].addEventListener("click", function() {
            this.parentElement.querySelector(".nested").classList.toggle("active");
            this.classList.toggle("caret-down");
          });
        }

        // update the dropdown size
        // if the dropdown is taller than the screen size minus 70px, then adjust it and activates overflow
        if((parseInt(document.querySelector('[view_id="' + this.synopticDiv + '"]').style["height"]) - 70) < (alreadyAddedLayersList.length * DROPDOWN_ROW_SIZE)) {
          document.getElementsByClassName("dropdown-content")[0].style["height"] = (parseInt(document.querySelector('[view_id="' + this.synopticDiv + '"]').style["height"]) - 70) + "px";
        }


        // FIXME: once it is loaded, if the synoptic is Bratislava
        // starts only with the Cameras layer
        if (d.description == "SYNOPTIC") {
          if(Object.keys(this.layersToDraw).includes("CAM")) {
            this.handleLayers("CAM");
          }
        }
        else if( d.description == "CABINETS"){
          this.handleLayers("CABINETS");
        }

        document.getElementById(this.synopticDiv + "spinner").style.display = "none";

        return d;
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
        };

        self.client.activate();
      });
  }

  /*
   *	Initializes everything
   */
  init() {
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
      svg.setAttribute("class", "synoptic-svg");

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

      self.baseWidth = c.offsetWidth;
      self.baseHeight = c.offsetHeight;

      this.initResponsive();
    });
  }

  /*
   * Resize the synoptic if the parent element changes its size
   */
  doResponsive(self) {
    // fallback if the self parameter is not received
    if(typeof self == 'undefined') {
      self = this;
    }

    let c = document.querySelector('[view_id="' + self.synopticDiv + '"]');
    let svg = document.getElementById(self.synopticDiv + "SVG");

    if(svg != null) {
      svg.setAttribute("width", c.offsetWidth);
      svg.setAttribute("height", c.offsetHeight);

      let ratioWidth = c.offsetWidth / self.baseWidth;
      let ratioHeight = c.offsetHeight / self.baseHeight;

      // scales the base layer
      let base = document.querySelector('[component="BASE_LAYER"]');
      base.setAttribute("width", self.baseWidth * ratioWidth);
      base.setAttribute("height", self.baseHeight * ratioHeight);

      // scales the other base layers
      Object.keys(this.synopticConf["BaseLayers"]).forEach(function(k) {
        if(k != "base") { // base is already added
          let base = document.getElementById('capa_base_' + k);
          base.setAttribute("width", self.baseWidth * ratioWidth);
          base.setAttribute("height", self.baseHeight * ratioHeight);
        }
      });

      // scales all the images of the svg (class == "capa_mapa")
      for (
        let i = 0;
        i < document.getElementsByClassName("capa_mapa").length;
        i++
      ) {
        let item = document.getElementsByClassName("capa_mapa")[i];

        // update size
        item.setAttribute("width", item.getAttribute("width") * ratioWidth);
        item.setAttribute("height", item.getAttribute("height") * ratioHeight);

        // update position
        item.setAttribute("x", item.getAttribute("x") * ratioWidth);
        item.setAttribute("y", item.getAttribute("y") * ratioHeight);

        // check if the element has a transform attribute (i.e.: for DMS)
        if (item.getAttribute("transform") != null) {
          item.transform.baseVal[0].matrix["e"] =
            item.transform.baseVal[0].matrix["e"] * ratioWidth;
          item.transform.baseVal[0].matrix["f"] =
            item.transform.baseVal[0].matrix["f"] * ratioHeight;
        }

        // check if the element is a text and contains a font-size style attribute
        /*
          if(item.style["font-size"] != null) {
            item.style["font-size"] = item.style["font-size"] * ratioWidth;
          }
          */
      }

      // scales the text of the tunnel banner
      for (
        let i = 0;
        i < document.getElementsByClassName("banner_title").length;
        i++
      ) {
        let item = document.getElementsByClassName("banner_title")[i];

        // update size
        let newSize = (parseFloat(item.style["font-size"].split("px")[0]) * ratioHeight).toString() + "px";
        item.style["font-size"] = newSize;

        // update position
        item.setAttribute("x", item.getAttribute("x") * ratioWidth);
        item.setAttribute("y", item.getAttribute("y") * ratioHeight);
      }

      // updates the svg icon, this is a special case
      for (
        let i = 0;
        i < document.getElementsByClassName("svg_icon").length;
        i++
      ) {
        let item = document.getElementsByClassName("svg_icon")[i];

        if (item.getAttribute("transform") != null) {
          item.transform.baseVal[0].matrix["e"] =
            item.transform.baseVal[0].matrix["e"] * ratioWidth;
          item.transform.baseVal[0].matrix["f"] =
            item.transform.baseVal[0].matrix["f"] * ratioHeight;
          item.transform.baseVal[0].matrix["a"] =
            item.transform.baseVal[0].matrix["a"] * ratioWidth;
          item.transform.baseVal[0].matrix["d"] =
            item.transform.baseVal[0].matrix["d"] * ratioHeight;
        }

        // scales the texts inside the item
        for(let j=0; j<item.children.length; j++) {
          if(item.children[j].tagName == "text") {
            let childItem = item.children[j];
            //childItem.style["font-size"] = (100 * ratioWidth).toString() + "px";
            childItem.style["font-size"] = (parseInt(childItem.style["font-size"]) * ratioWidth).toString() + "px";
            //alert((100 * ratioWidth).toString() + "px");
          }
        }

      }

      // update the base size
      self.baseWidth = self.baseWidth * ratioWidth;
      self.baseHeight = self.baseHeight * ratioHeight;

      // update the dropdown size
      document.getElementsByClassName("dropdown-content")[0].style["height"] = (parseInt(document.querySelector('[view_id="' + this.synopticDiv + '"]').style["height"]) - 70) + "px"
      
    }
  } //loadData

  /*
   * Initializes the responsiveness
   */
  initResponsive() {
    let e = document.getElementsByClassName("webix_sidebar")[0];

    let self = this;

    let observer = new MutationObserver(function (event) {
      self.doResponsive();
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
            
            graphSynoptic.load({
              elem: self.elementId,
            }).then((d) => {
              // check if it's a tunnel synoptic and changes the main bar in the synoptic
              let element_type = d.id.split(":")[1];

              if(element_type == "7") {
                let color = "";
                if (d.state != 3) {
                  color = "#ebb700";
                } else {
                  color = "#ff0000";
                }

                if(document.getElementById(d.id + ":Bar") != null) {
                  document.getElementById(d.id + ":Bar").setAttribute("fill", color);
                }
              }

              d.childs.forEach((element) => {
                updateElementState(element);
                updateElementIcon(element);

                // check if it's a tunnel synoptic and changes the main bar in the synoptic
                let element_type = element.id.split(":")[1];

                
                if(element_type == "7") {
                  let color = "";
                  if (element.state != 3) {
                    color = "#ebb700";
                  } else {
                    color = "#ff0000";
                  }

                  if(document.getElementById(element.id + ":Bar") != null) {
                    document.getElementById(element.id + ":Bar").setAttribute("fill", color);
                  }
                }
              });

            }).then(() => {
              document.getElementById(self.synopticDiv + "spinner").style.display = "none";
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
