import { staticsIcons } from "../icons/synopticJson.js";


import * as elementTypeMap from "./elementTypeMap.js";
import {ELEMENT_TYPE, ICON_COLOR} from "../data/GLOBAL_PROPERTIES.js";


export function generateIcon(element, suffix="") {
  let elementId= element.id+suffix;
  let elementTypeAlias = $.i18n(element.elementType.id + ".alias");
  let elementAlias = element.alias;
  let elementState = "";
  if(element.elementTypeState && element.elementTypeState.id) {
    elementState = getTrimId(element.elementTypeState.id);
  }
  let color = ICON_COLOR[elementState];
  // override generic color with ElementType color if exist
  if(typeof ELEMENT_TYPE.values[element.elementType.id].color != 'undefined' && ELEMENT_TYPE.values[element.elementType.id].color){
    color = ELEMENT_TYPE.values[element.elementType.id].color[elementState];
  }
  //svg icon String
  let iconStr = ELEMENT_TYPE.values[element.elementType.id].svgRaw;
  let params={
    "elementId": elementId,
    "color": color,
    "elementAlias": elementAlias,
    "elementTypeAlias": elementTypeAlias
  };
  //param values
  if(ELEMENT_TYPE.values[element.elementType.id].elementTypeParams){
    ELEMENT_TYPE.values[element.elementType.id].elementTypeParams.forEach(item => {
      let key = Object.keys(item)[0];
      let tempValue = element.elementValues.find(
        (elemValue) => elementTypeMap.getElementTypeParam(elemValue.id) == item[key]
      );
      if(tempValue){
        params[key]=tempValue.value;
      }
    });
  }

  return prepareStr(iconStr, params);
}

export function getElementIcon(element, suffix="") {

  let elem_icon = "";
  let width = 0;
  let height = 0;
  
  let elementType = element.elementType.id;
  let elementState = "";
  
  if (document.getElementById(element.id) != null) {
    elementState = parseInt(document.getElementById(element.id).getAttribute("state"));
  } else {
    elementState = getTrimId(element.elementTypeState.id);
  }
  
  // icon based on status
  if (typeof staticsIcons[elementType] != "undefined") {
    elem_icon = staticsIcons[elementType]["state"].find(
      (ic) => ic.state === elementState
    );
  } else {
    elem_icon = staticsIcons[elementTypeMap.ELEMENT_TYPE_33_ETD]["state"].find(
      (ic) => ic.state === elementState
    );
  }

  if (typeof elem_icon != "undefined") {
    width = elem_icon.width;
    height = elem_icon.height;
    elem_icon = elem_icon.icon;
  } else {
    elem_icon = "";
  }
  // changes the color based on the status
  if (element.elementTypeState && element.elementTypeState.id) {
    let color = ICON_COLOR[getTrimId(element.elementTypeState.id)];
    // override generic color with ElementType color if exist
    if(typeof ELEMENT_TYPE.values[element.elementType.id].color != 'undefined' && ELEMENT_TYPE.values[element.elementType.id].color){
      color = ELEMENT_TYPE.values[element.elementType.id].color[getTrimId(element.elementTypeState.id)];
    }
    if(document.getElementById(element.id + suffix + ":IconBackground") != null){
      document.getElementById(element.id + suffix + ":IconBackground").style["stroke"] = color;
    }
    if(document.getElementById(element.id + suffix + ":ValueBackground") != null){
      document.getElementById(element.id + suffix + ":ValueBackground").style["stroke"] = color;
    }
    for (let index = 0; index < 10; index++) {
      if(document.getElementById(element.id + suffix + ":fill"+index) != null){
        document.getElementById(element.id + suffix + ":fill"+index).style["fill"] = color;    
      }
    }
    for (let index = 0; index < 5; index++) {
      if(document.getElementById(element.id + suffix + ":stroke"+index) != null){
        document.getElementById(element.id + suffix + ":stroke"+index).style["stroke"] = color;    
      }
    }
  }

  // change params
  let params;
  if(ELEMENT_TYPE.values[element.elementType.id].elementTypeParams){
    params={};
    ELEMENT_TYPE.values[element.elementType.id].elementTypeParams.forEach(item => {
      let key = Object.keys(item)[0];
      let tempValue;
      if(element.elementValues){
        tempValue = element.elementValues.find(
          (elemValue) => elementTypeMap.getElementTypeParam(elemValue.id) == item[key]
        );        
      }
      if(tempValue != undefined && tempValue){
        params[key]=tempValue.value; 
      }else{
        params[key]=null;
      }
      if(document.getElementById(element.id + suffix + ":Value") != null){
        document.getElementById(element.id + suffix + ":Value").innerHTML = params[key];
      }
    });
  }

  // specific case
  if(element.elementType.id === ELEMENT_TYPE.ID_8_BAR){
    if (typeof params != 'undefined' && params && params["BAR_COMMAND_FOR_BARRIER"]
      && document.getElementById(element.id + suffix + "g3856")!=null) {
      if (params["BAR_COMMAND_FOR_BARRIER"] == "1") {
        document.getElementById(element.id + suffix + "g3856").setAttribute("transform", "rotate(0,0,0)");
      } else if (params["BAR_COMMAND_FOR_BARRIER"] == "0") {
        document.getElementById(element.id + suffix + "g3856").setAttribute("transform", "rotate(22.188461,3.5567155,291.27864)");
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_9_FAN){
    if (params != undefined) {
      if (params["FAN_COMMAND_FOR_FAN"] == 0) {
        document.getElementById(element.id + suffix + ":Animation").setAttribute("dur", "0");
        document.getElementById(element.id + suffix + ":IconMD").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconMI").style["visibility"] = "hidden";
      } else if (params["FAN_COMMAND_FOR_FAN"] == 1) {
        document.getElementById(element.id + suffix + ":Animation").setAttribute("dur", "6s");
        document.getElementById(element.id + suffix + ":IconArrowMD").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + ":IconArrowMI").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconMD").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + ":IconMI").style["visibility"] = "hidden";
      } else if (params["FAN_COMMAND_FOR_FAN"] == 2) {
        document.getElementById(element.id + suffix + ":Animation").setAttribute("dur", "6s");
        document.getElementById(element.id + suffix + ":IconArrowMD").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconArrowMI").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + ":IconMD").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconMI").style["visibility"] = "visible";
      }else {
        document.getElementById(element.id + suffix + ":Animation").setAttribute("dur", "0");
        document.getElementById(element.id + suffix + ":IconMD").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconMI").style["visibility"] = "hidden";
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_13_SEM_RAV){
    if (typeof params != 'undefined' && params && params["SEM_STATUS"]) {
      if (params["SEM_STATUS"] == "0") {
        document.getElementById(element.id + suffix + "green").style["fill"] = "gray";
        document.getElementById(element.id + suffix + "yellow").style["fill"] = "gray";
        document.getElementById(element.id + suffix + "red").style["fill"] = "gray";
      } else if (params["SEM_STATUS"] == "1") {
        document.getElementById(element.id + suffix + "green").style["fill"] = "#00FF00";
        document.getElementById(element.id + suffix + "yellow").style["fill"] = "gray";
        document.getElementById(element.id + suffix + "red").style["fill"] = "gray";
      } else if (params["SEM_STATUS"] == "2") {
        document.getElementById(element.id + suffix + "green").style["fill"] = "gray";
        document.getElementById(element.id + suffix + "yellow").style["fill"] = "#ffff00";
        document.getElementById(element.id + suffix + "red").style["fill"] = "gray";
      } else if (params["SEM_STATUS"] == "3") {
        document.getElementById(element.id + suffix + "green").style["fill"] = "gray";
        document.getElementById(element.id + suffix + "yellow").style["fill"] = "gray";
        document.getElementById(element.id + suffix + "red").style["fill"] = "#FF0000";
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_53_SEM_AA){
    if (params != undefined && params && params["SEM_AMBAR_AMBAR_STATUS"]) {
      let svgAmbar = document.getElementById(element.id + suffix);
      let circle1 = document.getElementById(element.id + suffix + ":orangeCircle1");
      let circle2 = document.getElementById(element.id + suffix + ":orangeCircle2");
      if (params["SEM_AMBAR_AMBAR_STATUS"] == 'true') {
        circle1.innerHTML = '<animate attributeName="fill" values="#ffff00;gray" dur="2s" repeatCount="indefinite" />';
        circle2.innerHTML = '<animate attributeName="fill" values="#ffff00;gray" begin="1s" dur="2s" repeatCount="indefinite" />';
        svgAmbar.children[0].children[0].children[0].children[1].children[1].style.display = "block"
      } else if (params["SEM_AMBAR_AMBAR_STATUS"] == 'false'){
        circle1.innerHTML = '';
        circle2.innerHTML = '';
        svgAmbar.children[0].children[0].children[0].children[1].style["fill"] = "gray"
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_67_SEM_RR){
    if (params != undefined && params && params["SEM_ROJO_ROJO_STATUS"]) {
      if (params["SEM_ROJO_ROJO_STATUS"] == 'true') {
        document.getElementById(element.id + suffix + "ligth1").style["fill"] = "#FF0000";
        document.getElementById(element.id + suffix + "ligth2").style["fill"] = "#FF0000";
      } else if (params["SEM_ROJO_ROJO_STATUS"] == 'false'){
        document.getElementById(element.id + suffix + "ligth1").style["fill"] = "gray";
        document.getElementById(element.id + suffix + "ligth2").style["fill"] = "gray";
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_57_MAGNETOTERMICO){
    if (params != undefined && params && params["MAGNETOTERMICO_STATE"]) {
      if (params["MAGNETOTERMICO_STATE"]=="true") {
        if(Snap.select("#"+element.id + suffix +":transform") && Snap.select("#"+element.id + suffix +":transform").node && Snap.select("#"+element.id + suffix +":transform").node.attributes.transform && Snap.select("#"+element.id + suffix +":transform").node.attributes.x && Snap.select("#"+element.id + suffix +":transform").node.attributes.y){
          Snap.select("#"+element.id + suffix +":transform").node.attributes.transform.value="rotate(-24.280147)";
          Snap.select("#"+element.id + suffix +":transform").node.attributes.x.value="5.2098141";
          Snap.select("#"+element.id + suffix +":transform").node.attributes.y.value="12.825212";
        }
       } else{ 
        if(Snap.select("#"+element.id + suffix +":transform") && Snap.select("#"+element.id + suffix +":transform").node && Snap.select("#"+element.id + suffix +":transform").node.attributes.transform && Snap.select("#"+element.id + suffix +":transform").node.attributes.x && Snap.select("#"+element.id + suffix +":transform").node.attributes.y){
          Snap.select("#"+element.id + suffix +":transform").node.attributes.transform.value="rotate(0.11021549)";
          Snap.select("#"+element.id + suffix +":transform").node.attributes.x.value="15.112525";
          Snap.select("#"+element.id + suffix +":transform").node.attributes.y.value="8.1191225";
        } 
      } 
    }
  }
  
  return { icon: elem_icon, width: width, height: height };
}

export function updateElementIcon(element) {
  // check if the element exists
  if (document.getElementById(element.id) != null) {
    let elem_icon = getElementIcon(element).icon;
    // replace the icon
    document.getElementById(element.id).setAttribute("href", "./img/" + elem_icon);
  }
}

export function updateElementState(element) {
  // check if the element exists and we are receiving an state update
  let el = document.getElementById(element.id);
  if (el != null && element.elementTypeState && element.elementTypeState.id) {
    el.setAttribute("state", getTrimId(element.elementTypeState.id));
  }
}

export function getElementSVG(element, width, height, suffix="", state="", values=[]) {
  let elementState = "";
  if(element.elementTypeState && element.elementTypeState.id) {
    elementState = getTrimId(element.elementTypeState.id);
  } 
  let iconStr = '<g id="' + element.id + suffix + '" state="' + elementState + '" transform="scale(' + (width / ELEMENT_TYPE.values[element.elementType.id].scale_x) + ',' + (height / ELEMENT_TYPE.values[element.elementType.id].scale_y) + ')">' + generateIcon(element, suffix) + '</g>';
  return '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape" xmlns:sodipodi="http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd" version="1.1" width="' + width + 'px" height="' + height + 'px">' + iconStr + '</svg>';
}
