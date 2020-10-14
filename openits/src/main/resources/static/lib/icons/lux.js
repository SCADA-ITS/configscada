import * as elementTypeMap from "../elementTypeMap.js";
import {ELEMENT_TYPE, ICON_COLOR} from "../../data/GLOBAL_PROPERTIES.js";

export function generateIcon(element, suffix="") {
  let elementId= element.id+suffix;
  let color = ICON_COLOR[element.state];
  //svg icon String
  let iconStr = ELEMENT_TYPE.values[element.elementType.id].svgRaw;
  let params={
    "elementId": elementId,
    "color": color
  }
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

export function updateLuxIcon(element, suffix="") {
  if (document.getElementById(element.id + suffix + ":IconBackground") != null 
      && document.getElementById(element.id + suffix + ":ValueBackground") != null) {
    // changes the value
    let new_value = null;
    if(ELEMENT_TYPE.values[element.elementType.id].elementTypeParams){
      ELEMENT_TYPE.values[element.elementType.id].elementTypeParams.forEach(item => {
        let key = Object.keys(item)[0];
        let tempValue = element.elementValues.find(
          (elemValue) => elementTypeMap.getElementTypeParam(elemValue.id) == item[key]
        );
        if(tempValue){
          new_value=tempValue.value;
        }
      });
    }
    if (typeof new_value != "undefined" && new_value != null && document.getElementById(element.id + suffix + ":Value") != null) {
      document.getElementById(element.id + suffix + ":Value").innerHTML = new_value.value;
    }
    // changes the color based on the status
    let color = ICON_COLOR[element.state];
    document.getElementById(element.id + suffix + ":IconBackground").style["stroke"] = color;
    document.getElementById(element.id + suffix + ":ValueBackground").style["stroke"] = color;
  }
}