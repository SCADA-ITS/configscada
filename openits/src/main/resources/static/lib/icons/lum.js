import * as elementTypeMap from "../elementTypeMap.js";

export function generateIcon(element, suffix="") {

  let valueToShow = element.elementValues.find(
    (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.LUM_LUMINOSITY
  );

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
      color = "silver";
    } else if (elementState == 2) {
      color = "purple";
    } else if (elementState == 3) {
      color = "red";
    }
  }

  let lux_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-281.12499)">' +
    '<g transform="matrix(0.2822222,0,0,0.2822222,-48.888674,181.52135)" id="g3652-1">' +
    '<rect ry="2.9559755" y="353.55258" x="185.01654" height="29.999998" width="29.999998" id="' +
    element.id + suffix +
    ':IconBackground" fill="' + color + '" />' +
    '<rect fill="' + color + '" y="387.27902" x="173.69635" height="21.42857" width="50.892853" id="' +
    element.id + suffix +
    ':ValueBackground"  />' +
    '<text fill="black" id="text1608-6-1-4-1-9" y="401.78311" x="187.84198" xml:space="preserve">' +
    '<tspan style="font-size:12.49999809px;stroke-width:0.93749994" y="401.78311" x="175.84198" id="' +
    element.id + suffix +
    ':Value" sodipodi:role="line">' +
    valueToShow.value +
    "</tspan>" +
    "</text>" +
    '<text fill="black" id="text2729-8-9-9-3" y="372.26047" x="188.67026" style="font-style:normal;font-weight:normal;font-size:10.71748638px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke-width:0.26793715" xml:space="preserve">' +
    '<tspan style="font-style:normal;font-variant:normal;font-weight:bold;font-stretch:normal;font-family:sans-serif;stroke-width:0.26793715" y="372.26047" x="188.07026" id="tspan2727-5-1-7-8" sodipodi:role="line">LUM</tspan>' +
    "</text>" +
    "</g>" +
    "</g>";

  return lux_str;
}

export function updateLumIcon(element, suffix="") {
  if (
    document.getElementById(element.id + suffix + ":IconBackground") != null &&
    document.getElementById(element.id + suffix + ":ValueBackground") != null
  ) {
    // changes the value
    let new_value = null;
    
    new_value = element.elementValues.find(
      (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.LUM_LUMINOSITY
    );

    if (typeof new_value != "undefined" && new_value != null && document.getElementById(element.id + suffix + ":Value") != null) {
      document.getElementById(element.id + suffix + ":Value").innerHTML = new_value.value;
    }

    // changes the color based on the state
    let color = "";
    let elementState = element.state;

    if(typeof elementState != 'undefined') {
      if (elementState == 0) {
        color = "#ffcc00";
      } else if (elementState == 1) {
        color = "#c0c0c0";
      } else if (elementState == 2) {
        color = "#800080";
      } else if (elementState == 3) {
        color = "#ff0000";
      }

      document.getElementById(element.id + suffix + ":IconBackground").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + ":ValueBackground").style[
        "fill"
      ] = color;
    }
  }
}