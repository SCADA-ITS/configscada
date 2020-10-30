import ElementForm from "../classes/ElementForm.js";
import ElementChildsList from "../classes/ElementChildsList.js";
import {
  generateIcon,
  getElementIcon,
  generateFullMatrixPanelIcon,
  updateElementIcon,
  updateElementState,
} from "../lib/iconGenerator.js";

import {columnsElementFormList} from "../data/elementForm.js";
import {ELEMENT_TYPE, WIDTH_RESOLUTION, HEIGHT_RESOLUTION} from "../data/GLOBAL_PROPERTIES.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";


let elementQuery = webix.proxy(
  "GraphQL",
  `query ($elem: String!) {
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
  }`
);

export default class synopticElement {
  constructor(element) {
    this.element = element;
  }

  paintIcon(synopticConf, snapDiv, ratioWidth, ratioHeight) {
    try {
      if (this.element.id in synopticConf) {
        let synopticConfElement = synopticConf[this.element.id];
        // initializes the svg icon
        let el = null;
        console.log(this.element.id);
        // check for special icons: DMS and ETD
        if (this.element.elementType.id === ELEMENT_TYPE.ID_10_DMS
            || this.element.elementType.id === ELEMENT_TYPE.ID_22_ETD_MODBUS
            || this.element.elementType.id === ELEMENT_TYPE.ID_23_ETD_DETECTOR
            || this.element.elementType.id === ELEMENT_TYPE.ID_12_LUX
            || this.element.elementType.id === ELEMENT_TYPE.ID_14_LUM
            || this.element.elementType.id === ELEMENT_TYPE.ID_19_CO
            || this.element.elementType.id === ELEMENT_TYPE.ID_20_NO
            || this.element.elementType.id === ELEMENT_TYPE.ID_21_OPAC
            || this.element.elementType.id === ELEMENT_TYPE.ID_18_ANEM_INT
            || this.element.elementType.id === ELEMENT_TYPE.ID_17_ANEM_EXT
            || this.element.elementType.id === ELEMENT_TYPE.ID_9_FAN
            || this.element.elementType.id === ELEMENT_TYPE.ID_8_BAR
            || this.element.elementType.id === ELEMENT_TYPE.ID_13_SEM
            || this.element.elementType.id === ELEMENT_TYPE.ID_7_ZONE
            || this.element.elementType.id === ELEMENT_TYPE.ID_53_SEM) {
          
          // TODO a la espera de subir el resto del flujo a generico
          if((this.element.elementType.id === ELEMENT_TYPE.ID_10_DMS 
            || this.element.elementType.id === ELEMENT_TYPE.ID_8_BAR
            || this.element.elementType.id === ELEMENT_TYPE.ID_9_FAN
            || this.element.elementType.id === ELEMENT_TYPE.ID_12_LUX
            || this.element.elementType.id === ELEMENT_TYPE.ID_14_LUM
            || this.element.elementType.id === ELEMENT_TYPE.ID_13_SEM
            || this.element.elementType.id === ELEMENT_TYPE.ID_18_ANEM_INT
            || this.element.elementType.id === ELEMENT_TYPE.ID_17_ANEM_EXT
            || this.element.elementType.id === ELEMENT_TYPE.ID_19_CO
            || this.element.elementType.id === ELEMENT_TYPE.ID_20_NO
            || this.element.elementType.id === ELEMENT_TYPE.ID_21_OPAC
            || this.element.elementType.id === ELEMENT_TYPE.ID_53_SEM
            ) && (typeof this.element.elementValues === 'undefined' || !this.element.elementValues) ){
            elementQuery.load({
              elem: this.element.id,
            }).then((data) => {
              let synElem = new synopticElement(data);
              synElem.paintIcon(synopticConf, snapDiv, ratioWidth, ratioHeight);
            });
            return;
          } else{
            let measures;
            if (typeof synopticConf[this.element.id].values !== 'undefined' && synopticConf[this.element.id].values) {
              measures = synopticConf[this.element.id].values;
            }
              
            // generate the base icon
            let pictoStr = generateIcon(this.element, "", "", measures);
            // modify the position of the icon and adds it to the DOM
            let snapIcon = Snap.parse(pictoStr);
            el = snapDiv.group().append(snapIcon);
            let scale_x = (synopticConf[this.element.id].width / ELEMENT_TYPE.values[this.element.elementType.id].scale_x) * ratioWidth;
            let scale_y = (synopticConf[this.element.id].height / ELEMENT_TYPE.values[this.element.elementType.id].scale_y) * ratioHeight;
            if (el != null) {
              el.transform("translate(" + (synopticConfElement.x * ratioWidth) + "," + ((HEIGHT_RESOLUTION - synopticConfElement.y - synopticConf[this.element.id].height) * ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");
              el.attr({ id: this.element.id });
              el.attr({ class: "svg_icon" });
              el.attr({ name: this.element.id });
              el.attr({ type: ELEMENT_TYPE.values[this.element.elementType.id].value });
              el.attr({ component: ELEMENT_TYPE.values[this.element.elementType.id].value });
              el.attr({ state: this.element.state });
            }
            // update the icon based on the state and/or the value
            getElementIcon(this.element);
          }
        } else if (this.element.elementType.id === ELEMENT_TYPE.ID_24_PMV_A_C 
          || this.element.elementType.id === ELEMENT_TYPE.ID_26_PMV_64X64 
          || this.element.elementType.id === ELEMENT_TYPE.ID_27_PMV_A_B 
          || this.element.elementType.id === ELEMENT_TYPE.ID_28_PMV_192X64
          || this.element.elementType.id === ELEMENT_TYPE.ID_29_PMV_B_D
          || this.element.elementType.id === ELEMENT_TYPE.ID_30_PMV_128X64
          || this.element.elementType.id === ELEMENT_TYPE.ID_31_PMV
          || this.element.elementType.id === ELEMENT_TYPE.ID_32_PMV
          || this.element.elementType.id === ELEMENT_TYPE.ID_35_PMV
          || this.element.elementType.id === ELEMENT_TYPE.ID_36_PMV) {  // MATRIX PANEL
          
            if(typeof this.element.elementValues === 'undefined' || !this.element.elementValues){
            elementQuery.load({
              elem: this.element.id,
            }).then((data) => {
              let synElem = new synopticElement(data);
              synElem.paintIcon(synopticConf, snapDiv, ratioWidth, ratioHeight);
            });
            return;
          }else{
             // generate the base icon
             let matrix_panel_str = generateFullMatrixPanelIcon(this.element);
             let matrix_panel_icon = Snap.parse(matrix_panel_str);
             el = snapDiv.group().append(matrix_panel_icon);
     
             // obtains the panel size in order to calculate the scale
             // check for the panel config
             let panelConfig = this.element.elementValues.find(
               (ic) =>
                 elementTypeMap.getElementTypeParam(ic.id) ==
                 elementTypeMap.PANEL_MANAGER_CONFIG
             );
             panelConfig = JSON.parse(panelConfig.value);
             let total_width = 0;
             let total_height = 0;
             // Get the sizes
             for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
               let n_size = panelConfig["Subpaneles"][nsubpanel]["Tamanio"];
               let panel_width = parseInt(n_size.split("x")[0]);
               let panel_height = parseInt(n_size.split("x")[1]);
               total_width += panel_width;
               if(panel_height > total_height) {
                 total_height = panel_height;
               }
             }
             let scale_x = (synopticConf[this.element.id].width / total_width) * ratioWidth;
             let scale_y = (synopticConf[this.element.id].height / total_height) * ratioHeight;
             if (el != null) {
               el.transform("translate(" + (synopticConfElement.x * ratioWidth) + "," + ((HEIGHT_RESOLUTION - synopticConfElement.y - synopticConf[this.element.id].height) * ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");
               el.attr({ id: this.element.id });
               el.attr({ class: "svg_icon" });
               el.attr({ name: this.element.id });
               el.attr({ type: ELEMENT_TYPE.values[this.element.elementType.id].value });
               el.attr({ component: ELEMENT_TYPE.values[this.element.elementType.id].value });
               el.attr({ state: this.element.state });
              //  el.attr({ visibility: "hidden" });
               // update the icon based on the state and/or the value
               getElementIcon(this.element);
             }
          }
        } else {
          // draw elements
          let elem_icon = getElementIcon(this.element).icon;
          let elem_icon_width = synopticConf[this.element.id].width;
          let elem_icon_height = synopticConf[this.element.id].height;
          let im = snapDiv
            .image("./img/" + elem_icon,synopticConfElement.x * ratioWidth, (HEIGHT_RESOLUTION - elem_icon_height - synopticConfElement.y) * ratioHeight, elem_icon_width * ratioWidth, elem_icon_height * ratioHeight )
            .attr({
              id: this.element.id,
              component: ELEMENT_TYPE.values[this.element.elementType.id].value,
              class: "capa_mapa",
              //visibility: "hidden",
              state: this.element.state,
            });
  
          // check if the item is a tunnel
          // FIXME: ONLY FOR THE DEMO
          if (this.element.elementType.id === ELEMENT_TYPE.ID_7_ZONE) {  // ZONE

          } else {
            let elementId=this.element.id;
            im.click(function (e) {
              try {
                if ($$("win" + elementId) != null) {
                  if ("Element:2:1" === elementId) {
                    $$("win" + elementId).show();
                  } else {
                    $$("win" +elementId).destructor();
                    let elementForm = new ElementForm(elementId,columnsElementFormList);
                    webix
                      .ui({
                        id: "win" + elementId,
                        view: "window",
                        move: true,
                        close: true,
                        position: "right",
                        body: elementForm.getComponent(),
                        on: {
                          onDestruct: function(obj){
                            //elementForm.disconnect();
                          },
                          onHide: function(obj){
                            elementForm.disconnect();
                            $$("win" + elementId).destructor();
                          },
                          onShow: function(obj){
                            let hijos = new ElementChildsList(elementForm.id);
                            hijos.getComponent();
                          }
                        }                            
                      })
                      .show();
                  }
                } else {
                  let elementForm = new ElementForm(elementId,columnsElementFormList);
                  webix
                    .ui({
                      id: "win" + elementId,
                      view: "window",
                      move: true,
                      close: true,
                      position: "right",
                      body: elementForm.getComponent(),
                      on: {
                        onDestruct: function(obj){
                          //elementForm.disconnect();
                        },
                        onHide: function(obj){
                          elementForm.disconnect();
                          $$("win" + elementId).destructor();
                        },
                        onShow: function(obj){
                          let hijos = new ElementChildsList(elementForm.id);
                          hijos.getComponent();
                        }
                      }
                    })
                    .show();
                }
              } catch (e) {
                console.error(e);
              }
            });
          }
        }
  
        if (el != null) {
          if(this.element.elementType.id == ELEMENT_TYPE.ID_7_ZONE) {
            el.click(function (e) {
              $$("syn0").show();
            });
            // FIXME: this is for the global synoptic, adds the title to the tunnel
            if (synopticConf.id == "synopticGlobal") {
              let c = document.querySelector(
                '[view_id="' + synopticConf.id + '"]'
              );
              let total_width = c.offsetWidth;
              let total_height = c.offsetHeight;
              let titleBackground = snapDiv.rect(
                synopticConfElement.x * ratioWidth,
                total_height -
                  30 * ratioHeight -
                  synopticConfElement.y * ratioHeight,
                synopticConfElement.width * ratioWidth,
                synopticConfElement.height * ratioHeight
              );
              el.remove();
              // It's set just for the demo
              if (this.element.state != 3) {
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
                id: this.element.id + ":Bar",
                class: "capa_mapa",
              });
              let titleText = snapDiv.text(
                (synopticConfElement.x + 105) * ratioWidth,
                total_height - 781 * ratioHeight,
                synopticConfElement.tittle
              );
              titleText.attr({
                "font-size": 21 * ratioWidth,
                fill: "#ffffff",
                class: "banner_title",
              });
  
              titleBackground.click(function (e) {
                $$("syn0").show();
              });
            }
          } else {
            let elementId = this.element.id;
            el.click(function (e) {
              try {
                if ($$("win" + elementId) != null) {
                  if ("Element:2:1" === elementId) {
                    $$("win" + elementId).show();
                  } else {
                    $$("win" + elementId).destructor();
                    let elementForm = new ElementForm(elementId, columnsElementFormList);
                    webix.ui({
                        id: "win" + elementId,
                        view: "window",
                        move: true,
                        close: true,
                        position: "right",
                        body: elementForm.getComponent(),
                        on: {
                          onDestruct: function(obj){
                            //elementForm.disconnect();
                          },
                          onHide: function(obj){
                            elementForm.disconnect();
                            $$("win" + elementId).destructor();
                          },
                          onShow: function(obj){
                            let hijos = new ElementChildsList(elementForm.id);
                            hijos.getComponent();
                          }
                        }
                      })
                      .show();
                  }
                } else {
                  let elementForm = new ElementForm(elementId, columnsElementFormList);
                  webix.ui({
                      id: "win" + elementId,
                      view: "window",
                      move: true,
                      close: true,
                      position: "right",
                      body: elementForm.getComponent(),
                      on: {
                        onDestruct: function(obj){
                          //elementForm.disconnect();
                        },
                        onHide: function(obj){
                          elementForm.disconnect();
                          $$("win" + elementId).destructor();
                        },
                        onShow: function(obj){
                          let hijos = new ElementChildsList(elementForm.id);
                          hijos.getComponent();

                        }
                      }
                    }).show();
                }
              } catch (e) {
                console.error(e);
              }
            });
  
          }
  
        }
      }
    } catch (err) {
      console.error(err);
      // loads the data
      elementQuery.load({
        elem: this.element.id,
      })
      .then((data) => {
        // process the received data
        let element = data;
        updateElementState(element);
        updateElementIcon(element);
      });
    }
  }

}
