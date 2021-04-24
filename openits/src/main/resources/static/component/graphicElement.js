import { util } from "../lib/util.js"
import {HEIGHT_RESOLUTION} from "../data/GLOBAL_PROPERTIES.js";


export default class GraphicElement {
  
  constructor(element, graphicIcon) {
    this.element = element;
    this.graphicIcon = graphicIcon;
  }

  addEvents(el){
    this.addOnClickEvent(el);
    this.addOnDblClickEvent(el);
  }

  paintIcon(elementUiConfig, snapDiv, ratioWidth, ratioHeight, suffix) {
    try {
      this.elementUiConfig = elementUiConfig;
      if(this.elementUiConfig.graphicIcon){
        import(this.elementUiConfig.graphicIcon.jsDrawFile).then(drawFile =>{
          let el = drawFile.generateIcon(this.element, suffix, this.elementUiConfig.graphicIcon, snapDiv);
          let scale_x = this.elementUiConfig.graphicIcon.width * ratioWidth;
          let scale_y = this.elementUiConfig.graphicIcon.height * ratioHeight;
          if (el != null) {
            el.transform("translate(" + (this.elementUiConfig.x * ratioWidth) + "," + ((HEIGHT_RESOLUTION - this.elementUiConfig.y - this.elementUiConfig.graphicIcon.height) * ratioHeight) + "), scale(" + scale_x + "," + scale_y + ")");
            el.attr({ id: this.element.id });
          }            
          drawFile.getElementIcon(this.element, suffix);
          if(el){
            this.addEvents(el);
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

  async paintOnMap(suffix=""){
    let elementSVG = "";
    if(this.graphicIcon){
      let drawFile = await import(this.graphicIcon.jsDrawFile);
      elementSVG = drawFile.getElementSVG(this.element);
    }else{
      webix.message({type:"error", text:"Es necesario asignar un recurso grafico al elemento: "+this.element.id});
    }
    return elementSVG;
  }

  getElementIcon(suffix=""){
    let result; 
    if(this.graphicIcon){
      import(this.graphicIcon.jsDrawFile).then(drawFile =>{
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
             if(this.element[i][thisElementValuePosition] == undefined){
              this.element[i][thisElementValuePosition] = {};
             }
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
    //Abstract

  }

  addOnDblClickEvent(el){
    //Abstract
    
  }

}
