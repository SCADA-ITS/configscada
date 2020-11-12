import ElementWindow from "../classes/ElementWindow.js";
import {
  generateIcon,
  getElementIcon,
  updateElementIcon,
  updateElementState,
  getElementSVG
} from "../lib/iconGenerator.js";

import {columnsElementFormList} from "../data/elementForm.js";
import {ELEMENT_TYPE, HEIGHT_RESOLUTION} from "../data/GLOBAL_PROPERTIES.js";


let elementQuery = webix.proxy(
  "GraphQL",
  `query ($elem: String!) {
    getElement(elementId: $elem)
    {
      id
      state
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

export default class synopticElement {
  constructor(element) {
    this.element = element;
  }

  paintIcon(synopticConf, snapDiv, ratioWidth, ratioHeight, suffix) {
    try {
      if (this.element.id in synopticConf) {
        let synopticConfElement = synopticConf[this.element.id];
        // initializes the svg icon
        let el = null;
        console.log(this.element.id);
        if (this.element.elementType.id === ELEMENT_TYPE.ID_4_SEC
            || this.element.elementType.id === ELEMENT_TYPE.ID_7_ZONE
            || ELEMENT_TYPE.values[this.element.elementType.id].uiType === UI_ELEMENT_TYPE_GENERIC
            || ELEMENT_TYPE.values[this.element.elementType.id].uiType === UI_ELEMENT_TYPE_MATRIX) {
          // If complex icon need measures
          if((ELEMENT_TYPE.values[this.element.elementType.id].elementTypeParams || ELEMENT_TYPE.values[this.element.elementType.id].uiType === UI_ELEMENT_TYPE_MATRIX || this.element.elementType.id === ELEMENT_TYPE.ID_4_SEC) 
              && (typeof this.element.elementValues === 'undefined' || !this.element.elementValues) ){
            elementQuery.load({
              elem: this.element.id,
            }).then((data) => {
              this.element=data;
              this.paintIcon(synopticConf, snapDiv, ratioWidth, ratioHeight, suffix);
            });
            return;
          } else{
            let measures;
            if (typeof synopticConf[this.element.id].values !== 'undefined' && synopticConf[this.element.id].values) {
              measures = synopticConf[this.element.id].values;
            }
              
            // generate the base icon
            let pictoStr = generateIcon(this.element, suffix, "", measures);
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
            getElementIcon(this.element, suffix);
          }
        } else if (ELEMENT_TYPE.values[this.element.elementType.id].uiType === UI_ELEMENT_TYPE_STATIC){
          // console.warn(this.element.id);
          // draw elements
          let elem_icon = getElementIcon(this.element, suffix).icon;
          let elem_icon_width = synopticConf[this.element.id].width;
          let elem_icon_height = synopticConf[this.element.id].height;
          el = snapDiv.image("./img/" + elem_icon,synopticConfElement.x * ratioWidth, (HEIGHT_RESOLUTION - elem_icon_height - synopticConfElement.y) * ratioHeight, elem_icon_width * ratioWidth, elem_icon_height * ratioHeight )
            .attr({
              id: this.element.id,
              component: ELEMENT_TYPE.values[this.element.elementType.id].value,
              class: "capa_mapa",
              state: this.element.state,
            });
  
        }
  
        if (el != null) {
          if(this.element.elementType.id == ELEMENT_TYPE.ID_7_ZONE) {
            el.click(function (e) {
              $$("syn0").show();
            });
            // FIXME: this is for the global synoptic, adds the title to the tunnel
            if (synopticConf.id == "synopticGlobal") {
              let c = document.querySelector('[view_id="' + synopticConf.id + '"]');
              let total_width = c.offsetWidth;
              let total_height = c.offsetHeight;
              let titleBackground = snapDiv.rect(
                synopticConfElement.x * ratioWidth,
                total_height - 30 * ratioHeight - synopticConfElement.y * ratioHeight,
                synopticConfElement.width * ratioWidth,
                synopticConfElement.height * ratioHeight
              );
              el.remove();
              // It's set just for the demo
              if (this.element.state != 3) {
                titleBackground.attr({fill: "#ebb700",});
              } else {
                titleBackground.attr({fill: "#ff0000",});
              }
              // FIXME: The id should be: 'id: d.id + ":Bar"' in a generic way
              titleBackground.attr({id: this.element.id + ":Bar",class: "capa_mapa",});
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
            let elementType = this.element.elementType.id;
            el.click(function (e) {
              try {
                let elementWindow = new ElementWindow(elementId,elementType);
                if ($$("win" + elementId) != null) {
                  $$("win" + elementId).destructor();
                  webix
                  .ui({
                    id: "win" + elementId,
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
                  //webix.ui(elementWindow.getComponent()).show();
                } else {
                  webix
                  .ui({
                    id: "win" + elementId,
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
                  //webix.ui(elementWindow.getComponent()).show();
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
      this.errorPaint();
    }
  }

  getElementSVG(width, height, suffix=""){
    return getElementSVG(this.element, width, height, suffix) 
  }

  updatePaint(suffix){
    getElementIcon(this.element, suffix);
  }

  errorPaint(elementId){
    if(typeof this.element === 'undefined' || !this.element || typeof this.element.id === 'undefined'){
      this.element={id:elementId};
    }
    elementQuery.load({
      elem: this.element.id,
    }).then((data) => {
      // process the received data
      this.element = data;
      updateElementState(this.element);
      updateElementIcon(this.element);
    });
  }

  updateElement(element){
    if(this.element){
      if(element){
        for (let i in element) {
          if(i==="elementValues"){
            if(typeof this.element[i] === 'undefined'){
              this.element.elementValues=[];
            }
            for (let j in element[i]) {
              this.element[i][j]=element[i][j];
            }
          }else{
            this.element[i]=element[i];
          }
        }        
      }
    }else{
      this.element=element;
    }
  }

}
