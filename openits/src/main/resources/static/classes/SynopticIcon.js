import ElementWindow from "./ElementWindow.js";
import { util } from "../lib/util.js"
import {
  generateIcon,
  getElementIcon,
  updateElementIcon,
  updateElementState,
  getElementSVG
} from "../lib/iconGenerator.js";

import {ELEMENT_TYPE, HEIGHT_RESOLUTION} from "../data/GLOBAL_PROPERTIES.js";

const SYNOPTIC_TUNNEL_RIOVERDE_ID = 900;
const ELEMENT_REST_ENDPOINT = appConfig.restUrl + "elements/";

export default class synopticElement {
  constructor(element) {
    this.element = element;
  }

  paintIcon(elementUiConfig, snapDiv, ratioWidth, ratioHeight, suffix) {
    try {
      this.snapDiv = snapDiv;
      this.ratioWidth = ratioWidth;
      this.ratioHeight = ratioHeight;
      this.elementUiConfig = elementUiConfig;
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
          let response = webix.ajax().sync().get(ELEMENT_REST_ENDPOINT + this.element.id);
          this.element = JSON.parse(response.responseText);
        } 
          
        // generate the base icon
        let pictoStr = generateIcon(this.element, suffix);
        // modify the position of the icon and adds it to the DOM
        let snapIcon = Snap.parse(pictoStr);
        el = snapDiv.group().append(snapIcon);

        let scale_x = (elementUiConfig.width / ELEMENT_TYPE.values[this.element.elementType.id].scale_x) * ratioWidth;
        let scale_y = (elementUiConfig.height / ELEMENT_TYPE.values[this.element.elementType.id].scale_y) * ratioHeight;
        if (el != null) {
          el.transform("translate(" + (elementUiConfig.x * ratioWidth) + "," + ((HEIGHT_RESOLUTION - elementUiConfig.y - elementUiConfig.height) * ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");
          el.attr({ id: this.element.id });
          el.attr({ class: "svg_icon" });
          el.attr({ name: this.element.id });
          el.attr({ type: ELEMENT_TYPE.values[this.element.elementType.id].value });
          el.attr({ component: ELEMENT_TYPE.values[this.element.elementType.id].value });
          el.attr({ state: this.element.state });
        }            
        // update the icon based on the state and/or the value
        getElementIcon(this.element, suffix);
      } else if (ELEMENT_TYPE.values[this.element.elementType.id].uiType === UI_ELEMENT_TYPE_STATIC){
        // console.warn(this.element.id);
        // draw elements
        let elem_icon = getElementIcon(this.element, suffix).icon;
        let elem_icon_width = elementUiConfig.width;
        let elem_icon_height = elementUiConfig.height;
        el = snapDiv.image("./img/" + elem_icon,elementUiConfig.x * ratioWidth, (HEIGHT_RESOLUTION - elem_icon_height - elementUiConfig.y) * ratioHeight, elem_icon_width * ratioWidth, elem_icon_height * ratioHeight )
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
            //TODO Deprecated, quedarse con la parte del catch correctamente recuperado el idModule
            try {
              $$("syn0").show();  
            } catch (error) {
              let module = window.em.getByField(EntityType.MODULE, "id", SYNOPTIC_TUNNEL_RIOVERDE_ID);
              util.module.open(module, null, null, null, null);	
            }
          });
        } else {
          let elementId = this.element.id;
          let elementType = this.element.elementType.id;
          el.click(function (e) {
            try {
              let elementWindow = new ElementWindow(elementId,elementType);
              if ($$("win" + elementId) != null){
                $$("win" + elementId).destructor();
              }

              webix.ui({
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
                    $$("win" + elementId).destructor();
                  },
                  onShow: function(obj){
                    elementWindow.loadData();
                  }
                }                            
              })
              .show();
                //webix.ui(elementWindow.getComponent()).show();
            } catch (e) {
              console.error(e);
            }
          });
        }
      }
    } catch (err) {
      console.error(err);
      this.errorPaint();
    }
  }

  getElementSVG(width, height, suffix=""){
    return getElementSVG(this.element, width, height, suffix);
  }

  getElementIcon(suffix=""){
    return getElementIcon(this.element, suffix);
  }

  updatePaint(suffix=""){
    getElementIcon(this.element, suffix);
  }

  errorPaint(elementId){
    if(typeof this.element === 'undefined' || !this.element || typeof this.element.id === 'undefined'){
      this.element={id:elementId};
    }
    let response = webix.ajax().sync().get(ELEMENT_REST_ENDPOINT + this.element.id);
    this.element = JSON.parse(response.responseText);
    updateElementState(this.element);
    updateElementIcon(this.element);
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
