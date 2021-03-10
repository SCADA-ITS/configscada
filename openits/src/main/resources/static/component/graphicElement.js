import ElementWindow from "../classes/ElementWindow.js";
import { util } from "../lib/util.js"

import {ELEMENT_TYPE, HEIGHT_RESOLUTION} from "../data/GLOBAL_PROPERTIES.js";

const SYNOPTIC_TUNNEL_RIOVERDE_ID = 900;
const ELEMENT_REST_ENDPOINT = appConfig.restUrl + "elements/";
const ELMENT_TYPE_STATE_UI_ICON = "icon_synoptic";


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

      if(this.elementUiConfig.customGraphicEquipment){
        import(this.elementUiConfig.customGraphicEquipment.jsDrawFile).then(drawFile =>{
          this.drawFile = drawFile;
          let pictoStr = this.drawFile.generateIcon(this.element, suffix, this.elementUiConfig.customGraphicEquipment);
          // modify the position of the icon and adds it to the DOM
          let snapIcon = Snap.parse(pictoStr);
          let el = this.snapDiv.group().append(snapIcon);

          let scale_x = (elementUiConfig.width / ELEMENT_TYPE.values[this.element.elementType.id].scale_x) * ratioWidth;
          let scale_y = (elementUiConfig.height / ELEMENT_TYPE.values[this.element.elementType.id].scale_y) * ratioHeight;
          if (el != null) {
            el.transform("translate(" + (elementUiConfig.x * ratioWidth) + "," + ((HEIGHT_RESOLUTION - elementUiConfig.y - elementUiConfig.height) * ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");
            el.attr({ id: this.element.id });
            el.attr({ class: "svg_icon" });
            el.attr({ name: this.element.id });
            el.attr({ type: ELEMENT_TYPE.values[this.element.elementType.id].value });
            el.attr({ component: ELEMENT_TYPE.values[this.element.elementType.id].value });
            el.attr({ state: getTrimId(this.element.elementTypeState.id) });
          }            
          // update the icon based on the state and/or the value
          this.drawFile.getElementIcon(this.element, suffix);

          if(el){
            this.addOnClickEvent(el);
          }
        });
      }else{
        // draw elements
        let elem_icon = this.element.elementTypeState[ELMENT_TYPE_STATE_UI_ICON];
        let elem_icon_width = elementUiConfig.width;
        let elem_icon_height = elementUiConfig.height;
        let el = snapDiv.image("./img/" + elem_icon,elementUiConfig.x * ratioWidth, (HEIGHT_RESOLUTION - elem_icon_height - elementUiConfig.y) * ratioHeight, elem_icon_width * ratioWidth, elem_icon_height * ratioHeight )
          .attr({
            id: this.element.id,
            component: ELEMENT_TYPE.values[this.element.elementType.id].value,
            class: "capa_mapa",
            state: getTrimId(this.element.elementTypeState.id)
          });
        if (el) {
          this.addOnClickEvent(el);
        }
      }
    } catch (err) {
      console.error(err);
      this.errorPaint();
    }
  }

  getElementSVG(width, height, suffix=""){
    let elementSVG = "";
    let elementState = "";
    if(this.element.elementTypeState && this.element.elementTypeState.id) {
      elementState = getTrimId(this.element.elementTypeState.id);
    } 
    if(this.elementUiConfig.customGraphicEquipment){
      import(this.elementUiConfig.customGraphicEquipment.jsDrawFile).then(drawFile =>{
        this.drawFile = drawFile;
        elementSVG = this.drawFile.getElementSVG(this.element, width, height, suffix);
      });
    }else{
      let iconStr = '<image xlink:href="./img/' + this.element.elementTypeState[ELMENT_TYPE_STATE_UI_ICON] + '" id="' + this.element.id + suffix + '" state="' + elementState + '" width="' + width + '" height="' + height + '"></image>';
      elementSVG = '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape" xmlns:sodipodi="http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd" version="1.1" width="' + width + 'px" height="' + height + 'px">' + iconStr + '</svg>';
    }
    return elementSVG;
  }

  getElementIcon(suffix=""){
    let result; 
    let width = 0;
    let height = 0;
    if(this.elementUiConfig.customGraphicEquipment){
      import(this.elementUiConfig.customGraphicEquipment.jsDrawFile).then(drawFile =>{
        this.drawFile = drawFile;
        result = this.drawFile.getElementIcon(this.element, suffix);
      });
    }else{
      let elementType = this.element.elementType.id;
      let elementState;
      if(element.elementTypeState && element.elementTypeState.id) {
        elementState = getTrimId(element.elementTypeState.id);
      }
      let staticConfigIcon = null;

      if (typeof staticsIcons[elementType] != "undefined") {
        staticConfigIcon = staticsIcons[elementType]["state"].find(
          (ic) => ic.state === elementState
        );
      }

      if (typeof staticConfigIcon != "undefined") {
        staticConfigIcon = staticConfigIcon.icon;
        width = staticConfigIcon.width;
        height = staticConfigIcon.height;
      }

      if(typeof elementState != 'undefined') {
        if(document.getElementById(element.id + suffix) != null) {
          document.getElementById(element.id + suffix).setAttribute("href", "./img/" +  this.element.elementTypeState[ELMENT_TYPE_STATE_UI_ICON]);
        }
      }
      result = { icon: staticConfigIcon, width: width, height: height };
    }
    return result;
  }

  updatePaint(suffix=""){
    this.getElementIcon(suffix);
  }

  drawInlineSVG(ctx, rawSVG, callback) {
    let svg64 = rawSVG;
    let b64Start = 'data:image/svg+xml;utf8,';
  
    // prepend a "header"
    let image64 = b64Start + svg64;
  
    let img = new Image;
    img.src = image64;
  
    img.onload = function () {
      ctx.drawImage(this, 0, 0);
      callback(this);
    };
  
    img.onerror = function(e) {
      console.log("ERROR: ")
      console.log(e);
    }  
  }

  errorPaint(elementId){
    if(typeof this.element === 'undefined' || !this.element || typeof this.element.id === 'undefined'){
      this.element={id:elementId};
    }
    let response = webix.ajax().sync().get(ELEMENT_REST_ENDPOINT + this.element.id);
    this.element = JSON.parse(response.responseText);
    if (document.getElementById(element.id) != null) {
      let el = document.getElementById(element.id);
      if (el != null && this.element.elementTypeState && this.element.elementTypeState.id) {
        el.setAttribute("state", getTrimId(this.element.elementTypeState.id));
      }
      // check if the element exists
      let elem_icon = this.getElementIcon("").icon;
      // replace the icon
      document.getElementById(element.id).setAttribute("href", "./img/" + elem_icon);
    }
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
              let thisElementValuePosition = this.element.elementValues.findIndex(
                (item) => item.id === element[i][j].id
              );
              Object.assign(this.element[i][thisElementValuePosition], element[i][j]);
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

  addOnClickEvent(el){
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
  }

}
