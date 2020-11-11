import ElementCommand from "../classes/ElementCommand.js";
import {columnsElementFormList} from "../data/elementForm.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";
import EquipmentTree from "./EquipmentTree.js";
import SynopticElement from "../classes/SynopticElement.js";
import {ELEMENT_TYPE, WIDTH_RESOLUTION, HEIGHT_RESOLUTION} from "../data/GLOBAL_PROPERTIES.js";

let graphSynoptic = webix.proxy(
  "GraphQL",
  `query($elem: String!) {
    getElement(elementId: $elem)
    {
      id
      alias
      description
      state
      elementType {
        id
      }
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

// in case of exclusive layers, manages the onItemCheck event
var shouldICheck = true;
let elementsMap = new Map();

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
    this.elementTree = null;
    this.layerTree = {};
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
        try {
          // console.warn(element);
          // console.warn(elementsMap);
            if(elementsMap && elementsMap.get(element.id)){
              elementsMap.get(element.id).updateElement(element);
              elementsMap.get(element.id).updatePaint();
            }else{
              let synopticElement = new SynopticElement(element);
              synopticElement.paintIcon(this.synopticConf, this.s,  this.ratioWidth,  this.ratioHeight);
              elementsMap.set(synopticElement.element.id, synopticElement);
            }
        } catch (error) {
          console.log(error);
          console.log("Retrying with GraphQL");
          if(typeof element != 'undefined' && elementsMap && elementsMap.get(element.id)){
            elementsMap.get(element.id).errorPaint();
          }else{
            let synopticElement = new SynopticElement(element);
            synopticElement.errorPaint();
            elementsMap.set(element.id, synopticElement);
          }
        }
      });
    } catch (err) {
      console.error(err);
    }
  }

  // Layer groups
  // TODO: this method is only called by the exclusive-layers synoptics
  // Work in progress
  handleLayers(e, initial=false) {
    if(!initial) {
      if (this.layersToDraw[e] == "visible") {
        this.layersToDraw[e] = "hidden";
      } else {
        this.layersToDraw[e] = "visible";
      }
    }

    this.layersToDraw[e] = this.synopticConf["Layers"][e]["visibility"];
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
              // Fix for the FAN
              if (this.layersToDraw[e] == "visible") {
                el.style["opacity"] = 1;
              } else {
                el.style["opacity"] = 0;
              }
            }
          });
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
            // Fix for the FAN
            if (this.synopticConf["Layers"][e]["visibility"] == "visible") {
              el.style["opacity"] = 1;
            } else {
              el.style["opacity"] = 0;
            }
          }
        }
      });
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

  clickElement(id) {
    // clean the current selection
    Array.prototype.forEach.call(document.querySelectorAll(".synoptic-highlight"), (e) => { e.classList.remove("synoptic-highlight") });
    // highlights the selected item
    document.getElementById(id).classList.add("synoptic-highlight");
  }

  checkElement(id) {
    // we can't pass the synoptic name to the callback
    // so we have to find it in the DOM
    
    // find the id of the synoptic, replacing the SVG added by Snap.svg in the ID
    let synopticId = $$("element_tree" + document.getElementsByClassName("synoptic-svg")[0].id.replace("SVG", ''));
    let synopticButtonId = $$("element_tree_button" + document.getElementsByClassName("synoptic-svg")[0].id.replace("SVG", ''));

    // all the elements of the tree
    //let checkedElements = synopticId.getChecked();

    // all the elements of the tree
    let checkedElements = synopticId.getChecked();

    // loop all the tree items to find what items should be shown
    synopticId.data.each(function(obj) { 
      let checkedElement = document.getElementById(obj.id);
      if(checkedElement != null) {
        if(checkedElements.includes(obj.id)) {
          checkedElement.style.opacity = 1;
          checkedElement.setAttribute("visibility", "visible");
        } else {
          checkedElement.style.opacity = 0;
          checkedElement.setAttribute("visibility", "hidden");
        }
      }
    });

    // check if there are objects selected
    let shouldEnableButton = false;
    if(synopticId.getChecked().length > 0) {
      let first_item = synopticId.getChecked().find(e => e.match(/Element/g));
      if(typeof first_item != 'undefined') {
        for(let i=0; i<synopticId.getChecked().length; i++) {
          let checkedElement = synopticId.getChecked()[i];
          let checkedElementLevel = synopticId.getItem(checkedElement).$level;
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
      synopticButtonId.enable();
    } else {
      synopticButtonId.disable();
    }
  }

  clickElementExclusiveLayers(id) {
    // we can't pass the synoptic name to the callback
    // so we have to find it in the DOM
    
    // find the id of the synoptic, replacing the SVG added by Snap.svg in the ID
    let synopticId = $$("element_tree" + document.getElementsByClassName("synoptic-svg")[0].id.replace("SVG", ''));
    let synopticButtonId = $$("element_tree_button" + document.getElementsByClassName("synoptic-svg")[0].id.replace("SVG", ''));

    // all the elements of the tree
    //let checkedElements = synopticId.getChecked();
    if(!id.match("Element")) {
      // checks if the base layer is selected and mantains its selection
      let baseLayerChecked = false;
      let roadLayerChecked = false;

      if(synopticId.getChecked().includes("BASE_LAYER")) {
        baseLayerChecked = true;
      }

      if(synopticId.getChecked().includes("ROAD")) {
        roadLayerChecked = true;
      }

      shouldICheck = false;
      // unselect all
      synopticId.uncheckAll();
      // (un)select the base layer
      if(baseLayerChecked) {
        synopticId.checkAll("BASE_LAYER");
      }

      // check if the ROAD layer exists and (un)checks
      if(synopticId.data.exists("ROAD") && roadLayerChecked) {
        synopticId.checkAll("ROAD");
      }
      
      // select only the element/branch
      synopticId.checkAll(id);
      shouldICheck = true;

      // all the elements of the tree
      let checkedElements = synopticId.getChecked();

      // loop all the tree items to find what items should be shown
      synopticId.data.each(function(obj) { 
        let checkedElement = document.getElementById(obj.id);
        if(checkedElement != null) {
          if(checkedElements.includes(obj.id)) {
            checkedElement.style.opacity = 1;
            checkedElement.setAttribute("visibility", "visible");
          } else {
            checkedElement.style.opacity = 0;
            checkedElement.setAttribute("visibility", "hidden");
          }
        }
      });
    } else {
      // clean the current selection
      Array.prototype.forEach.call(document.querySelectorAll(".synoptic-highlight"), (e) => { e.classList.remove("synoptic-highlight") });
      // highlights the selected item
      document.getElementById(id).classList.add("synoptic-highlight");
    }

  }

  // Check element event for a synoptic with exclusive layers
  checkElementExclusiveLayers(id) {
    if(shouldICheck == true) {
      // find the id of the synoptic, replacing the SVG added by Snap.svg in the ID
      let synopticId = $$("element_tree" + document.getElementsByClassName("synoptic-svg")[0].id.replace("SVG", ''));
      let synopticButtonId = $$("element_tree_button" + document.getElementsByClassName("synoptic-svg")[0].id.replace("SVG", ''));

      if(!id.match("Element")) {
        if(id == "BASE_LAYER" || id == "ROAD") {
          // checks if the base layer is selected and mantains its selection
          shouldICheck = false;

          let baseLayerChecked = false;
          let roadLayerChecked = false;

          if(synopticId.getChecked().includes("BASE_LAYER")) {
            baseLayerChecked = true;
          }
  
          if(synopticId.getChecked().includes("ROAD")) {
            roadLayerChecked = true;
          }

          if(id == "BASE_LAYER") {
            if(baseLayerChecked) {
              synopticId.checkAll(id);
            } else {
              synopticId.uncheckAll(id);
            }
          }
  
          if(id == "ROAD") {
            if(roadLayerChecked) {
              synopticId.checkAll(id);
            } else {
              synopticId.uncheckAll(id);
            }
          }

          shouldICheck = true;

          // all the elements of the tree
          let checkedElements = synopticId.getChecked();
    
          // loop all the tree items to find what items should be shown
          synopticId.data.each(function(obj) { 
            let checkedElement = document.getElementById(obj.id);
            if(checkedElement != null) {
              if(checkedElements.includes(obj.id)) {
                checkedElement.style.opacity = 1;
                checkedElement.setAttribute("visibility", "visible");
              } else {
                checkedElement.style.opacity = 0;
                checkedElement.setAttribute("visibility", "hidden");
              }
            }
          });

        } else {
          shouldICheck = false;
  
          let baseLayerChecked = false;
          let roadLayerChecked = false;

          if(synopticId.getChecked().includes("BASE_LAYER")) {
            baseLayerChecked = true;
          }
  
          if(synopticId.getChecked().includes("ROAD")) {
            roadLayerChecked = true;
          }

          // unselect all
          synopticId.uncheckAll();
          
          // (un)select the base layer
          if(baseLayerChecked) {
            synopticId.checkAll("BASE_LAYER");
          }

          // check if the ROAD layer exists and (un)checks
          if(roadLayerChecked) {
            synopticId.checkAll("ROAD");
          }
          
          
          // select only the element/branch
          synopticId.checkAll(id);
          
          shouldICheck = true;
    
          // all the elements of the tree
          let checkedElements = synopticId.getChecked();
    
          // loop all the tree items to find what items should be shown
          synopticId.data.each(function(obj) { 
            let checkedElement = document.getElementById(obj.id);
            if(checkedElement != null) {
              if(checkedElements.includes(obj.id)) {
                checkedElement.style.opacity = 1;
                checkedElement.setAttribute("visibility", "visible");
              } else {
                checkedElement.style.opacity = 0;
                checkedElement.setAttribute("visibility", "hidden");
              }
            }
          });

        }
      }

      // check if there are objects selected
      let shouldEnableButton = false;
      if(synopticId.getChecked().length > 0) {
        let first_item = synopticId.getChecked().find(e => e.match(/Element/g));
        if(typeof first_item != 'undefined') {
          for(let i=0; i<synopticId.getChecked().length; i++) {
            let checkedElement = synopticId.getChecked()[i];
            let checkedElementLevel = synopticId.getItem(checkedElement).$level;
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
        synopticButtonId.enable();
      } else {
        synopticButtonId.disable();
      }
    }
  }

  generateLayerTree2(layersList) {
    let self = this;
    Object.keys(layersList).map((n) => {
      this.layerTree[n] = [];
      this.elementTree.elementTree.push({
        id: n,
        value: n,
        data: [],
      });

      for (let e in this.synopticConf["Layers"][n]["items"]) {
        if(Object.keys(this.synopticConf["Layers"]).includes(this.synopticConf["Layers"][n]["items"][e])) {
          let elementTypeKeyIndex = self.elementTree.elementTree.findIndex(
            (e) => e.id === n
          );
          this.elementTree.elementTree[elementTypeKeyIndex]["data"].push({
            id: this.synopticConf["Layers"][n]["items"][e],
            value: "<div style=\"display: inline;vertical-align: text-top;\">" + this.synopticConf["Layers"][n]["items"][e] + "</div>",
            // icon: "",
            data: [],
          });
        } else {
          // search for the elementType key
          let elementTypeKeyIndex = self.elementTree.elementTree.findIndex(
            (e) => e.id === n
          );
  
          // set the width and height based on the elementType
          let height = 32;
          //TODO get de icon
          // let width = this.getIconWidth(thisElementType, height);
          let width = 32;
          
          if (typeof this.elementTree.elementTree[elementTypeKeyIndex]["data"] == "undefined") {
            this.elementTree.elementTree[elementTypeKeyIndex]["data"] = [];
          }
          
          // search for the elementType key
          let elementId = this.synopticConf["Layers"][n]["items"][e];
          if(elementsMap && elementsMap.get(elementId)){
            this.elementTree.elementTree[elementTypeKeyIndex]["data"].push({
              id: elementId,
              value: "<div style=\"display: inline;vertical-align: text-top;\">" + elementsMap.get(elementId).element.description + "</div>",
              // icon: "<div style=\"float: right\">" + getElementSVG(elementsMap.get(elementId).element, width, height, ":Tree") + "</div>",
              data: [],
            });
          } else {
            this.elementTree.elementTree[elementTypeKeyIndex]["data"].push({
              id: this.synopticConf["Layers"][n]["items"][e],
              value: "<div style=\"display: inline;vertical-align: text-top;\">" + this.synopticConf["Layers"][n]["items"][e] + "</div>",
              // icon: "",
              data: [],
            });
          }
        }
      };
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
    graphSynoptic.load({
        elem: this.elementId,
      }).then((d) => {
        // creates the base layer
        this.baseLayer = this.synopticConf["BaseLayers"]["base"];

        // get the container size
        let c = document.querySelector('[view_id="' + this.synopticDiv + '"]');
        this.s = Snap("#" + this.synopticDiv + "SVG");
        let total_width = c.offsetWidth;
        let total_height = c.offsetHeight;

        // set the aspect ratios
        this.ratioWidth = total_width / WIDTH_RESOLUTION;
        this.ratioHeight = total_height / HEIGHT_RESOLUTION;

        // set width and height
        this.s.attr({ width: total_width, height: total_height });

        // add basemap and base road to elements_to_draw
        this.elementsTypes.push("BASE_LAYER");
        this.elementsToDraw["BASE_LAYER"] = "visible";
        
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

          let titleText = this.s.text(
            806 * this.ratioWidth,
            total_height - 15 * this.ratioHeight - 1011 * this.ratioHeight,
            this.synopticConf.tittle
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
        this.s.image("./img/" + this.baseLayer, 0, 0, total_width, total_height).attr({
          id: "capa_base",
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
            self.s.image("./img/" + self.synopticConf["BaseLayers"][k], 0, 0, total_width, total_height).attr({
              //id: "capa_base_" + k,
              id: k,
              visibility: self.elementsToDraw[k],
              component: k,
            });
          }
        });
        return d;
      }).then((d) => {
        d.childs.forEach((element) => {
          let synopticElement = new SynopticElement(element);
          synopticElement.paintIcon(this.synopticConf, this.s,  this.ratioWidth,  this.ratioHeight);
          elementsMap.set(synopticElement.element.id, synopticElement);
          // alert(JSON.stringify(elementsMap));
        });
        
        // replaces the element tree in the DOM
        this.elementTree = new EquipmentTree(this.elementId, null);

        // create the sidemenu for the equipment tree
        webix.ui({
          view: "sidemenu-win",
          id: "menu" + this.synopticDiv,
          width: 200,
          position: function(position) {
            var toolbarHeight = $$("mainToolbar").$height;
            position.top = toolbarHeight;
            position.left = $$("mainBody").$width - 200;
            var mainHeight = $$("mainBody").$height - toolbarHeight;
            position.height = mainHeight;
            position.width = 200;
          },
          body: {
            id: "elementTreeView" + this.synopticDiv, 
            rows: [
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
                    icon:"wxi-close",
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
                id: "element_tree_filter" + self.synopticDiv,
                view: "text",
                label: "Filter",
                labelPosition: "left"
              },
              {
                id: "element_tree" + self.synopticDiv,
                view: "tree",
                borderless: true,
                scroll: "auto",
                select: true,
                multiselect: true,
                threeState: true,
                template: function(obj, common){
                  // if (obj.$level > 1) {
                  //   return common.icon(obj,common) + common.checkbox(obj, common) +"&nbsp;"+ "<span class='elementTreeLeaf'>" + obj.value + "</span>" +"&nbsp;"+ obj.icon;
                  // } else {
                    return common.icon(obj,common) + common.checkbox(obj, common) +"&nbsp;"+ "<span class='elementTreeLeaf'>" + obj.value + "</span>";
                  // }
                }
              },
              {
                id: "element_tree_button" + self.synopticDiv,
                view: "button",
                disabled: true,
                value: "Command",
                on: {
                  "onItemClick": function(id, e) {
                    let filteredElements = $$("element_tree" + self.synopticDiv).getChecked().filter(e => e.match(/Element/g));
                    let elementCommand = new ElementCommand(filteredElements, columnsElementFormList);
                    webix.ui({
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
            url: this.elementTree.getComponent(this.synopticDiv)
          }
        });

        if(document.getElementsByClassName("synoptic-svg")[0].attributes["exclusive"].value == "false") {
          // general case
          $$("element_tree" + self.synopticDiv).attachEvent("onItemCheck", self.checkElement);
          $$("element_tree" + self.synopticDiv).attachEvent("onItemClick", self.clickElement);
        } else {
          // for exclusive layers
          $$("element_tree" + self.synopticDiv).attachEvent("onItemClick", self.clickElementExclusiveLayers);
          $$("element_tree" + self.synopticDiv).attachEvent("onItemCheck", self.checkElementExclusiveLayers);
        }
        return d;
      })
      .then((d) => {
        // draw the buttons for filtering the element type
        let self = this;

        // adds the dropdown button
        let buttonToAdd = document.createElement("button");
        buttonToAdd.innerHTML = "<span class='webix_icon wxi-folder-open'></span>";
        buttonToAdd.setAttribute("class", "dropbtn");
        document.getElementById(this.synopticDiv + "dropdownDiv").appendChild(buttonToAdd);

        // adds the dropdown div
        let divToAdd = document.createElement("div");
        divToAdd.setAttribute("id", this.synopticDiv + "dropdownDivContent");
        divToAdd.setAttribute("class", "dropdown-content");
        document.getElementById(this.synopticDiv + "dropdownDiv").appendChild(divToAdd);

        let layersList = {};
        let alreadyAddedLayersList = [];

        // Generate the layer tree
        for(let l in Object.keys(this.synopticConf["Layers"])) {
          let layerName = Object.keys(this.synopticConf["Layers"])[l];
          if(!alreadyAddedLayersList.includes(layerName)) {
            layersList[layerName] = {};
            for(let item in this.synopticConf["Layers"][layerName]["items"]) {
              let itemName = this.synopticConf["Layers"][layerName]["items"][item];
              if(Object.keys(this.synopticConf["Layers"]).includes(itemName)) {
                delete layersList[itemName];
                layersList[layerName][itemName] = {};
              }
            }
          }
        }

        // Generate the tree
        this.generateLayerTree2(layersList, 0);

        // For Bratislava, clean all layers and show only the ones marked as visible
        if(this.synopticConf["ExclusiveLayers"] == true) {
          for(let layerName in this.synopticConf["Layers"]) {
            let isVisible = this.synopticConf["Layers"][layerName]["visibility"];

            // updates the base layer visibility
            if(document.getElementById(layerName) != null && isVisible == "hidden") {
              document.getElementById(layerName).style.opacity = 0;
            }

            for(let item in this.synopticConf["Layers"][layerName]["items"]) {
              let itemName = this.synopticConf["Layers"][layerName]["items"][item];
              if(document.getElementById(itemName) != null && isVisible == "hidden") {
                document.getElementById(itemName).style.opacity = 0;
              }
            }
          }
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
      document.querySelector('[view_id="' + this.synopticDiv + '"]').prepend(spinnerDivToAdd);

      var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
      document.querySelector('[view_id="' + this.synopticDiv + '"]').prepend(svg);
      svg.setAttribute("id", this.synopticDiv + "SVG");
      svg.setAttribute("width", c.offsetWidth);
      svg.setAttribute("height", c.offsetHeight);
      svg.setAttribute("class", "synoptic-svg");
      svg.setAttribute("exclusive", this.synopticConf["ExclusiveLayers"]);

      let divToAdd = document.createElement("div");
      divToAdd.setAttribute("id", this.synopticDiv + "dropdownDiv");
      divToAdd.setAttribute("class", "treemap-button ol-unselectable ol-control");
      document.querySelector('[view_id="' + this.synopticDiv + '"]').prepend(divToAdd);

      this.elementReady("#" + this.synopticDiv + "SVG").then((c1) => {
        // loads data and draws for first time
        this.loadData();
      });

      $("#" + this.synopticDiv + "dropdownDiv").click(function() {
        if ($$("menu" + self.synopticDiv).isVisible()){
          // show the panel
          $$("menu" + self.synopticDiv).hide();
          // show the children
          $$("menu" + self.synopticDiv).getChildViews()[0].getChildViews().forEach(e => { e.hide() });
        } else {
          // show the panel
          $$("menu" + self.synopticDiv).show();
          // show the children
          $$("menu" + self.synopticDiv).getChildViews()[0].getChildViews().forEach(e => { e.show() });
        }
        
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
      if(base != null) {
        base.setAttribute("width", self.baseWidth * ratioWidth);
        base.setAttribute("height", self.baseHeight * ratioHeight);
      }

      // scales the other base layers
      Object.keys(this.synopticConf["BaseLayers"]).forEach(function(k) {
        if(k != "base") { // base is already added
          let base = document.getElementById(k);
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
          item.transform.baseVal[0].matrix["e"] = item.transform.baseVal[0].matrix["e"] * ratioWidth;
          item.transform.baseVal[0].matrix["f"] = item.transform.baseVal[0].matrix["f"] * ratioHeight;
          item.transform.baseVal[0].matrix["a"] = item.transform.baseVal[0].matrix["a"] * ratioWidth;
          item.transform.baseVal[0].matrix["d"] = item.transform.baseVal[0].matrix["d"] * ratioHeight;
        }

        // scales the texts inside the item
        for(let j=0; j<item.children.length; j++) {
          if(item.children[j].tagName == "text") {
            let childItem = item.children[j];
            childItem.style["font-size"] = (parseInt(childItem.style["font-size"]) * ratioWidth).toString() + "px";
          }
        }
      }

      // update the base size
      self.baseWidth = self.baseWidth * ratioWidth;
      self.baseHeight = self.baseHeight * ratioHeight;

      // update the dropdown size
      document.getElementsByClassName("dropdown-content")[0].style["height"] = (parseInt(document.querySelector('[view_id="' + this.synopticDiv + '"]').style["height"]) - 70) + "px"
    }
  } 

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
        if (typeof $$("menu" + self.synopticDiv) != 'undefined' && $$("menu" + self.synopticDiv).isVisible()){
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
                if(elementsMap && elementsMap.get(element.id)){
                  // console.warn("existe: "+element.id);
                  elementsMap.get(element.id).errorPaint();
                }else{
                  // console.warn("NO existe: "+element.id);
                  let synopticElement = new SynopticElement(element);
                  synopticElement.errorPaint();
                  elementsMap.set(element.id, synopticElement);
                }
              });
            }).then(() => {
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

    showOrHideObserver.observe(showOrHideObserverTarget, {
      attributes: false,
      childList: true,
      characterData: false,
    });

    // Attaching the event listener function to window's resize event
    window.addEventListener("resize", function() { self.doResponsive(self) });
  }
	
}
