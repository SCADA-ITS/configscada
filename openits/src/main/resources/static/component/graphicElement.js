import ElementWindow from "../classes/ElementWindow.js";
import { util } from "../lib/util.js"

import {ELEMENT_TYPE, HEIGHT_RESOLUTION} from "../data/GLOBAL_PROPERTIES.js";

const SYNOPTIC_TUNNEL_RIOVERDE_ID = 900;
const ELEMENT_REST_ENDPOINT = appConfig.restUrl + "elements/";

export default class synopticElement {
  constructor(element) {
    this.element = element;
  }

  paintIcon(elementUiConfig, snapDiv, ratioWidth, ratioHeight, suffix) {
    try {
      this.elementUiConfig = elementUiConfig;
      if(this.elementUiConfig.customGraphicEquipment){
        import(this.elementUiConfig.customGraphicEquipment.jsDrawFile).then(drawFile =>{
          let el = drawFile.generateIcon(this.element, suffix, this.elementUiConfig.customGraphicEquipment, snapDiv);
          let scale_x = this.elementUiConfig.width * ratioWidth;
          let scale_y = this.elementUiConfig.height * ratioHeight;
          if (el != null) {
            el.transform("translate(" + (this.elementUiConfig.x * ratioWidth) + "," + ((HEIGHT_RESOLUTION - this.elementUiConfig.y - this.elementUiConfig.height) * ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");
            el.attr({ id: this.element.id });
          }            
          drawFile.getElementIcon(this.element, suffix);
          if(el){
            this.addOnClickEvent(el);
          }
        });
      }else{
        webix.message({type:"error", text:"Es necesario asignar un recurso grafico al elemento: "+this.element.id});
      }
    } catch (err) {
      console.error(err);
      this.errorPaint();
    }
  }

  getElementSVG(width, height, suffix=""){
    let elementSVG = "";
    if(this.elementUiConfig.customGraphicEquipment){
      import(this.elementUiConfig.customGraphicEquipment.jsDrawFile).then(drawFile =>{
        elementSVG = drawFile.getElementSVG(this.element, width, height, suffix);
      });
    }else{
      webix.message({type:"error", text:"Es necesario asignar un recurso grafico al elemento: "+this.element.id});
    }
    return elementSVG;
  }

  getElementIcon(suffix=""){
    let result; 
    if(this.elementUiConfig.customGraphicEquipment){
      import(this.elementUiConfig.customGraphicEquipment.jsDrawFile).then(drawFile =>{
        result = drawFile.getElementIcon(this.element, suffix);
      });
    }else{
      webix.message({type:"error", text:"Es necesario asignar un recurso grafico al elemento: "+this.element.id});
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
    if (document.getElementById(this.element.id) != null) {
      let el = document.getElementById(this.element.id);
      if (el != null && this.element.elementTypeState && this.element.elementTypeState.id) {
        el.setAttribute("state", getTrimId(this.element.elementTypeState.id));
      }
      // check if the element exists
      let elem_icon = this.getElementIcon("").icon;
      // replace the icon
      document.getElementById(this.element.id).setAttribute("href", "./img/" + elem_icon);
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
            }).show();
          } catch (e) {
            console.error(e);
          }
        });
      }
    }
  }

}
