import * as elementTypeMap from "../elementTypeMap.js";
import {ELEMENT_TYPE, ICON_COLOR} from "../../data/GLOBAL_PROPERTIES.js";

export function generateIcon(element, suffix) {
  //DEPRECATED
  //Temporalmente se asignan aqui los "values" (son comunes para todos los equipos), esto deberá ir codificado dentro de Entities.ElementTypeUI
  let values = [
    {name: "etd_intensity", label: "I (V/h)"},
    {name: "etd_speed", label: "V (K/h)"},
    {name: "etd_occupancy", label: "O (%)"},
    {name: "etd_veh_length_1_percent", label: "C (%L)"}
  ];
  // changes the color based on the status
  let color = "";
  //let elementState = element.state;
  let elementState;
  if(element.elementTypeState && element.elementTypeState.id) {
    elementState = getTrimId(element.elementTypeState.id);
  }
  
  let params={};
  //param values
  // console.warn(element);
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

  if(typeof elementState != 'undefined' && elementState == 2) {
      color = "purple";
  } else if (params["SERVICE_LEVEL"] == 1) {
    color = "white";
  } else if (params["SERVICE_LEVEL"] == 2) {
    color = "green";    
  } else if (params["SERVICE_LEVEL"] == 3) {
    color = "gold";
  } else if (params["SERVICE_LEVEL"] == 4) {
    color = "red";
  }

  let etd_str =
    '<g inkscape:label ="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> ' +
    '<rect id="'+element.id +':fill0" stroke="black" style="stroke-width:0.2" fill="' + color + '" width="34.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" />' +
    '<text fill="black" xml:space="preserve" x="16.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)">' +
    '<tspan fill="black" x="16.904327" y="272.36899" style="font-size:2.7px;line-height:2.62886882px;text-align:center;text-anchor:middle" id="tspan1693-0">' +
    element.description +
    "</tspan></text>";

  for (let i = 0; i < values.length; i++) {
    etd_str +=
      '<rect stroke="black" style="stroke-width:0.2" fill="lightgray" id="rect4066-6-5-93-0" width="34.228191" height="5.6456451" x="0.041112646" y="' +
      (274.66467 + 6 * i) +
      '" ry="0" />' +
      '<text xml:space="preserve" fill="black" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)">' +
      '<tspan sodipodi:role="line" x="7.465615" y="' +
      (277.66467 + 6 * i) +
      '" fill="black" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;stroke-width:0.26288685" id="tspan4072-0-0-8">' +
      values[i].label +
      "</tspan></text>" +
      '<text fill="black" xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke-width:0.26288685" x="' +
      13.086432 +
      '" y="' +
      (277.66467 + 0 * i) +
      '" id="galleta_texto' + i + '" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="' +
      26.086432 +
      '" y="' +
      (277.66467 + 6 * i) +
      '" fill="black" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;stroke-width:0.26288685" id="' +
      element.id +
      ":" +
      values[i].name +
      '">' +
      "</tspan></text>";
  }

  etd_str += "</g>";

  return etd_str;
}

export function updateEtdIcon(element, suffix = "") {
  let color = "";
  let elementState;
  if(element.elementTypeState && element.elementTypeState.id) {
    elementState = getTrimId(element.elementTypeState.id);
  }
  
  let params={};
  //param values
  // console.warn(element);
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

  if(typeof elementState != 'undefined' && elementState == 2) {
      color = "purple";
  } else if (params["SERVICE_LEVEL"] == 1) {
    color = "white";
  } else if (params["SERVICE_LEVEL"] == 2) {
    color = "green";    
  } else if (params["SERVICE_LEVEL"] == 3) {
    color = "gold";
  } else if (params["SERVICE_LEVEL"] == 4) {
    color = "red";
  }
  if(Snap.select("#"+element.id + ":fill0") && Snap.select("#"+element.id + ":fill0").node && Snap.select("#"+element.id +":fill0").node.attributes.fill){
    Snap.select("#"+element.id + ":fill0").node.attributes.fill.value=color;
  }

  for (let i = 0; i < element.elementValues.length; i++) {
    let ev = element.elementValues[i];
    if (
      document.getElementById(
        element.id + suffix + ":etd_" + ev.elementTypeParam.alias
      ) != null
    ) {
      let domObj = element.id + suffix + ":etd_" + ev.elementTypeParam.alias;
      document.getElementById(domObj).innerHTML = ev.value;
    }
  }
}
