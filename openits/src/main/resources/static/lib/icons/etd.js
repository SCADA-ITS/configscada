import * as elementTypeMap from "../elementTypeMap.js";

export function generateIcon(element, values, suffix, state) {
  // changes the color based on the status
  let color = "";
  //let elementState = element.state;
  let elementState = "";
  if(typeof element.state != 'undefined') {
    elementState = element.state;
  } else {
    elementState = state;
  }
  
  if(typeof elementState != 'undefined') {
    if (elementState == 0) {
      color = "gold";
    } else if (elementState == 1) {
      color = "green";
    } else if (elementState == 2) {
      color = "purple";
    } else if (elementState == 3) {
      color = "red";
    }
  }

  let etd_str =
    '<g inkscape:label ="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> ' +
    '<rect stroke="black" style="stroke-width:0.2" fill="' + color + '" id="rect4066-6-5-93" width="28.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" />' +
    '<text fill="black" xml:space="preserve" x="13.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)">' +
    '<tspan fill="white" x="13.904327" y="272.36899" style="font-size:3.15464282px;line-height:2.62886882px;text-align:center;text-anchor:middle" id="tspan1693-0">' +
    element.description +
    "</tspan></text>";

  for (let i = 0; i < values.length; i++) {
    let valueToShow = element.elementValues.find(
      (ic) => elementTypeMap.getElementTypeParam(ic.id) == eval("elementTypeMap." + values[i].name.toUpperCase())
    );
    
    etd_str +=
      '<rect stroke="black" style="stroke-width:0.2" fill="lightgray" id="rect4066-6-5-93-0" width="28.228191" height="5.6456451" x="0.041112646" y="' +
      (274.66467 + 6 * i) +
      '" ry="0" />' +
      '<text xml:space="preserve" fill="black" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)">' +
      '<tspan sodipodi:role="line" x="8.465615" y="' +
      (277.66467 + 6 * i) +
      '" fill="black" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;stroke-width:0.26288685" id="tspan4072-0-0-8">' +
      values[i].label +
      "</tspan></text>" +
      '<text fill="black" xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke-width:0.26288685" x="' +
      13.086432 +
      '" y="' +
      (277.66467 + 0 * i) +
      '" id="galleta_texto' + i + '" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="' +
      22.086432 +
      '" y="' +
      (277.66467 + 6 * i) +
      '" fill="black" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;stroke-width:0.26288685" id="' +
      element.id +
      ":" +
      values[i].name +
      '">' +
      valueToShow.value +
      "</tspan></text>";
  }

  etd_str += "</g>";

  return etd_str;
}

export function updateEtdIcon(element, suffix = "") {
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
