import { synoptic } from "../data/synoptic.js";
import {
  synopticTunnel,
  synopticMetro,
  synopticGlobal,
  synopticBratislava,
  synopticCabinets,
} from "../data/synoptics.js";
import ElementForm from "../classes/ElementForm.js";
import {
  getElementIcon,
  generateDMSIcon,
  generateETDIcon,
  generateLuxIcon,
  generateLumIcon,
  generateCoIcon,
  generateNoIcon,
  generateOpacIcon,
  generateAnemIntIcon,
  generateAnemExtIcon,
  generateFanIcon,
  generateBarIcon,
  generateSemIcon,
  generateZoneIcon,
  updateElementIcon,
  updateElementState,
} from "../lib/iconGenerator.js";

import * as elementTypeMap from "../lib/elementTypeMap.js";
//TODO 
//import {columnsElementFormList} from "../project/ausol/data/elementForm.js";
import {columnsElementFormList} from "../project/bratislava/data/elementForm.js";

export default class Synoptic {
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

    if (metadata == null) {
      this.metadata = synoptic;
    } else {
      this.metadata = metadata;
    }
  }

  /*
   *	Tries to get the data from Stomp, but if it doesn't find the needed data,
   * gets it by GraphQL
   */
  processStompMessageWithFallback(message) {
    try {
      let obj = JSON.parse(JSON.parse(message.body));

      obj.basicElementsVO.forEach((element) => {

        if(elementTypeMap.getElementType(element.id) == elementTypeMap.DMS_ELEMENT_TYPE) {
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

  getComponent() {
    if (this.data.includes("http") || this.data.includes("GraphQL")) {
      this.metadata.url = this.data;
    } else {
      this.metadata.data = this.data;
    }
    return this.metadata;
  }

  handleLayers(e) {
    if(e == "BASE_LAYER" || e == "ROAD") {
      // Hides device
      if (this.elementsToDraw[e] == "visible") {
        this.elementsToDraw[e] = "hidden";
      } else {
        this.elementsToDraw[e] = "visible";
      }  
    } else {
      for (var k in this.elementsToDraw) {
        if (k != e && k != "BASE_LAYER" && k != "ROAD") {
          this.elementsToDraw[k] = "hidden";
        }
      }
      this.elementsToDraw[e] = "visible";
    }

    for (var k in this.elementsToDraw) {
      let els = this.s.selectAll('[component="' + k + '"]');

      els.forEach((el) => {
        el.attr("visibility", this.elementsToDraw[k]);
      });
    }

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
        
        // if we are in a tunnel synoptic, put the title bar
        if (this.synopticType == "tunnel") {
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
            visibility: this.elementsToDraw["BASE_LAYER"],
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

            // draws the base layer for this component
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

          console.log(element);

          if (element.id in this.synopticConf) {
            let synopticElement = this.synopticConf[element.id];
            
            let elementType = elementTypeMap.getElementType(element.id);

            // add the elementType to the array of elements to be drawn and the elementTypes array
            this.elements.push(element);

            if (!this.elementsTypes.includes(elementType)) {
              this.elementsTypes.push(elementType);
              this.elementsToDraw[elementType] = "visible";
            }

            // initializes the svg icon
            let el = null;

            // check for special icons: DMS and ETD
            if (elementType === elementTypeMap.DMS_ELEMENT_TYPE) {
              // generate the base icon
              
              let pmv_str = generateDMSIcon(element);

              // modify the position of the icon and adds it to the DOM
              let pmv_icon = Snap.parse(pmv_str);

              el = this.s.group().append(pmv_icon);


              let scale_x =
                (this.synopticConf[element.id].width / 76) * this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 24) * this.ratioHeight;

              if (el != null) {
                //el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");
                //el.transform("translate(0,0)");

                el.attr({ id: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ name: element.id });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });
              }

              // update the icon based on the state and/or the value
              getElementIcon(element);
            } else if (elementType === elementTypeMap.ETD_MODBUS_ELEMENT_TYPE) {  // ETD Modbus
              // generate the base icon
              let etd_str = generateETDIcon(element);

              // modify the ETD position
              let etd_icon = Snap.parse(etd_str);
              el = this.s.group().append(etd_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 28) * this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 28) * this.ratioHeight;

              if (el != null) {
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.ETD_DETECTOR_ELEMENT_TYPE) {  // ETD Detector
              // generate the base icon
              let measures = this.synopticConf[element.id].values;
              let etd_str = generateETDIcon(element, measures);

              // modify the ETD position
              let etd_icon = Snap.parse(etd_str);
              el = this.s.group().append(etd_icon);
              
              let scale_x =
                (this.synopticConf[element.id].width / 28) * this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 28) * this.ratioHeight;

              if (el != null) {
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.LUX_ELEMENT_TYPE) {  // LUX
              // generate the base icon
              let lux_str = generateLuxIcon(element);
              
              let lux_icon = Snap.parse(lux_str);
              el = this.s.group().append(lux_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 14.628) *
                this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 15.698) *
                this.ratioHeight;

              if (el != null) {
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.LUM_ELEMENT_TYPE) {  // LUM
              // generate the base icon
              let lum_str = generateLumIcon(element);

              
              let lum_icon = Snap.parse(lum_str);
              el = this.s.group().append(lum_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 14.628) *
                this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 15.698) *
                this.ratioHeight;

              if (el != null) {
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.CO_ELEMENT_TYPE) {  // CO
              // generate the base icon
              let co_str = generateCoIcon(element);

              
              let co_icon = Snap.parse(co_str);
              el = this.s.group().append(co_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 14.628) *
                this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 15.698) *
                this.ratioHeight;

              if (el != null) {
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.NO_ELEMENT_TYPE) {  // NO
              // generate the base icon
              let no_str = generateNoIcon(element);

              
              let no_icon = Snap.parse(no_str);
              el = this.s.group().append(no_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 14.628) *
                this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 15.698) *
                this.ratioHeight;

              if (el != null) {
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.OPAC_ELEMENT_TYPE) {  // OPAC
              // generate the base icon
              let opac_str = generateOpacIcon(element);

              
              let opac_icon = Snap.parse(opac_str);
              el = this.s.group().append(opac_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 14.628) *
                this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 15.698) *
                this.ratioHeight;

              if (el != null) {
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.ANEM_INT_ELEMENT_TYPE) {  // ANEM_INT
              // generate the base icon
              let anem_str = generateAnemIntIcon(element);

              
              let anem_icon = Snap.parse(anem_str);
              el = this.s.group().append(anem_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 14.628) *
                this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 15.698) *
                this.ratioHeight;

              if (el != null) {
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.ANEM_EXT_ELEMENT_TYPE) {  // ANEM_EXT
              // generate the base icon
              let anem_str = generateAnemExtIcon(element);

              let anem_icon = Snap.parse(anem_str);
              el = this.s.group().append(anem_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 14.628) *
                this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 15.698) *
                this.ratioHeight;

              if (el != null) {
                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.FAN_ELEMENT_TYPE) {  // FAN
              // generate the base icon
              let fan_str = generateFanIcon(element);

              let fan_icon = Snap.parse(fan_str);
              el = this.s.group().append(fan_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 392) * this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 528) * this.ratioHeight;

              if (el != null) {

                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.BAR_ELEMENT_TYPE) {  // BAR
              // generate the base icon
              let bar_str = generateBarIcon(element);

              let bar_icon = Snap.parse(bar_str);
              el = this.s.group().append(bar_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 30.163) * this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 16.13958) * this.ratioHeight;

              if (el != null) {

                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.SEM_ELEMENT_TYPE) {  // SEM
              // generate the base icon
              let sem_str = generateSemIcon(element);

              let sem_icon = Snap.parse(sem_str);
              el = this.s.group().append(sem_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 8.752) * this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 10.247) * this.ratioHeight;

              if (el != null) {

                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else if (elementType === elementTypeMap.ZONE_ELEMENT_TYPE) {  // ZONE
              // generate the base icon
              let zone_str = generateZoneIcon(element);

              let zone_icon = Snap.parse(zone_str);
              el = this.s.group().append(zone_icon);

              let scale_x =
                (this.synopticConf[element.id].width / 4122.35) * this.ratioWidth;
              let scale_y =
                (this.synopticConf[element.id].height / 3382.97) * this.ratioHeight;

              if (el != null) {

                el.transform("translate(" + (synopticElement.x * this.ratioWidth) + "," + ((1080 - synopticElement.y - this.synopticConf[element.id].height) * this.ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ class: "svg_icon" });
                el.attr({ type: elementType });
                el.attr({ component: elementType });
                el.attr({ state: element.state });

                // update the icon based on the state and/or the value
                getElementIcon(element);
              }
            } else {
              // draw elements
              let elem_icon = getElementIcon(element).icon;
              let elem_icon_width = this.synopticConf[element.id].width;
              let elem_icon_height = this.synopticConf[element.id].height;

              let im = this.s
                .image(
                  "./img/" + elem_icon,
                  synopticElement.x * this.ratioWidth,
                  (1080 - elem_icon_height - synopticElement.y) *
                    this.ratioHeight,
                  elem_icon_width * this.ratioWidth,
                  elem_icon_height * this.ratioHeight
                )
                .attr({
                  id: element.id,
                  component: elementType,
                  class: "capa_mapa",
                  visibility: "visible",
                  state: element.state,
                });

              // check if the item is a tunnel
              // FIXME: ONLY FOR THE DEMO
              if (elementType === elementTypeMap.ZONE_ELEMENT_TYPE) {  // ZONE
                /*
                im.click(function (e) {
                  $$("synoptic1").show();
                });

                // FIXME: this is for the global synoptic, adds the title to the tunnel
                if (this.synopticType == "global") {
                  let c = document.querySelector(
                    '[view_id="' + this.synopticDiv + '"]'
                  );
                  let total_width = c.offsetWidth;
                  let total_height = c.offsetHeight;
                  let titleBackground = this.s.rect(
                    synopticElement.x * this.ratioWidth,
                    total_height -
                      30 * this.ratioHeight -
                      synopticElement.y * this.ratioHeight,
                    synopticElement.width * this.ratioWidth,
                    synopticElement.height * this.ratioHeight
                  );

                  im.remove();

                  // It's set just for the demo
                  if (element.state != 3) {
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
                    id: element.id + ":Bar",
                    class: "capa_mapa",
                  });

                  // FIXME: there is no "Rio Verde" tunnel in the DB, just "Tunnel 1" so we set it manually
                  // We should bring modify the GraphQL query (add "description" field) and bring it from the DB
                  //let titleText = this.s.text((synopticElement.x + 105) * this.ratioWidth, total_height - 781 * this.ratioHeight, element.description);
                  let titleText = this.s.text(
                    (synopticElement.x + 105) * this.ratioWidth,
                    total_height - 781 * this.ratioHeight,
                    "RIO VERDE"
                  );
                  titleText.attr({
                    "font-size": 21 * this.ratioWidth,
                    fill: "#ffffff",
                    class: "banner_title",
                  });

                  titleBackground.click(function (e) {
                    $$("synoptic1").show();
                  });
                }
                */
              } else {
                im.click(function (e) {
                  try {
                    if ($$("win" + element.id) != null) {
                      if ("Element:2:1" === element.id) {
                        $$("win" + element.id).show();
                      } else {
                        $$("win" + element.id).destructor();
                        let elementForm = new ElementForm(element.id,columnsElementFormList);
                        webix
                          .ui({
                            id: "win" + element.id,
                            view: "window",
                            move: true,
                            close: true,
                            position: "right",
                            body: elementForm.getComponent(),
                            on: {
                              onDestruct: function(obj){
                                elementForm.disconnect();
                              },
                              onHide: function(obj){
                                elementForm.disconnect();
                                $$("win" + element.id).destructor();
                              }
                            }                            
                          })
                          .show();
                      }
                    } else {
                      let elementForm = new ElementForm(element.id,columnsElementFormList);
                      webix
                        .ui({
                          id: "win" + element.id,
                          view: "window",
                          move: true,
                          close: true,
                          position: "right",
                          body: elementForm.getComponent(),
                          on: {
                            onDestruct: function(obj){
                              elementForm.disconnect();
                            },
                            onHide: function(obj){
                              elementForm.disconnect();
                              $$("win" + element.id).destructor();
                            }
                          }
                        })
                        .show();
                    }
                  } catch (e) {
                    // TODO: handle exception
                  }
                });
              }
            }

            if (el != null) {
              if(elementTypeMap.getElementType(element.id) == elementTypeMap.ZONE_ELEMENT_TYPE) {
                el.click(function (e) {
                  $$("synoptic1").show();
                });

                // FIXME: this is for the global synoptic, adds the title to the tunnel
                if (this.synopticType == "global") {
                  let c = document.querySelector(
                    '[view_id="' + this.synopticDiv + '"]'
                  );
                  let total_width = c.offsetWidth;
                  let total_height = c.offsetHeight;
                  let titleBackground = this.s.rect(
                    synopticElement.x * this.ratioWidth,
                    total_height -
                      30 * this.ratioHeight -
                      synopticElement.y * this.ratioHeight,
                    synopticElement.width * this.ratioWidth,
                    synopticElement.height * this.ratioHeight
                  );

                  el.remove();

                  // It's set just for the demo
                  if (element.state != 3) {
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
                    id: element.id + ":Bar",
                    class: "capa_mapa",
                  });

                  // FIXME: there is no "Rio Verde" tunnel in the DB, just "Tunnel 1" so we set it manually
                  // We should bring modify the GraphQL query (add "description" field) and bring it from the DB
                  //let titleText = this.s.text((synopticElement.x + 105) * this.ratioWidth, total_height - 781 * this.ratioHeight, element.description);
                  let titleText = this.s.text(
                    (synopticElement.x + 105) * this.ratioWidth,
                    total_height - 781 * this.ratioHeight,
                    "RIO VERDE"
                  );
                  titleText.attr({
                    "font-size": 21 * this.ratioWidth,
                    fill: "#ffffff",
                    class: "banner_title",
                  });

                  titleBackground.click(function (e) {
                    $$("synoptic1").show();
                  });
                }
              } else {
                el.click(function (e) {
                  try {
                    if ($$("win" + element.id) != null) {
                      if ("Element:2:1" === element.id) {
                        $$("win" + element.id).show();
                      } else {
                        $$("win" + element.id).destructor();
                        let elementForm = new ElementForm(element.id,columnsElementFormList);
                        webix
                          .ui({
                            id: "win" + element.id,
                            view: "window",
                            move: true,
                            close: true,
                            position: "right",
                            body: elementForm.getComponent(),
                            on: {
                              onDestruct: function(obj){
                                elementForm.disconnect();
                              },
                              onHide: function(obj){
                                elementForm.disconnect();
                                $$("win" + element.id).destructor();
                              }
                            }
                          })
                          .show();
                      }
                    } else {

                      let elementForm = new ElementForm(element.id,columnsElementFormList);
                      webix
                        .ui({
                          id: "win" + element.id,
                          view: "window",
                          move: true,
                          close: true,
                          position: "right",
                          body: elementForm.getComponent(),
                          on: {
                            onDestruct: function(obj){
                              elementForm.disconnect();
                            },
                            onHide: function(obj){
                              elementForm.disconnect();
                              $$("win" + element.id).destructor();
                            }
                          }
                        })
                        .show();
                    }
                  } catch (e) {
                    // TODO: handle exception
                  }
                });

              }

            }
          }
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

        this.elementsTypes.map((n) => {
          let linkToAdd = document.createElement("a");
          let linkText = document.createTextNode(elementTypeMap.getElementTypeName(n));
          linkToAdd.onclick = function () {
            self.handleLayers(n);
          };
          //linkToAdd.appendChild(linkText);
          linkToAdd.innerHTML = elementTypeMap.getElementTypeName(n);
          document
            .getElementById(this.synopticDiv + "dropdownDivContent")
            .appendChild(linkToAdd);
        });

        // FIXME: once it is loaded, if the synoptic is Bratislava
        // starts only with the Cameras layer
        if (d.description == "SYNOPTIC") {
          this.handleLayers("2");
        }
        else if( d.description == "CABINETS"){
          this.handleLayers("25");
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
  init(synopticConf) {
    if (synopticConf == "tunnel") {
      this.synopticConf = synopticTunnel;
    } else if (synopticConf == "metro") {
      this.synopticConf = synopticMetro;
    } else if (synopticConf == "global") {
      this.synopticConf = synopticGlobal;
    } else if (synopticConf == "bratislava") {
      this.synopticConf = synopticBratislava;
    }else if (synopticConf == "cabinets") {
      this.synopticConf = synopticCabinets;
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
      }

      // update the base size
      self.baseWidth = self.baseWidth * ratioWidth;
      self.baseHeight = self.baseHeight * ratioHeight;
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
        let newNode =
          event[0].addedNodes[0].children[0].children[0].attributes["view_id"]
            .value;

          if (newNode == self.synopticDiv) {
            // Launch the query again
            document.getElementById(self.synopticDiv + "spinner").style.display = "block";
            self.data.load().then((d) => {
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
