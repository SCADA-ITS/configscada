import * as elementTypeMap from "../elementTypeMap.js";

export function generateIcon(element, suffix="") {
  let op_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-281.12499)">' +
    '<g transform="matrix(0.2822222,0,0,0.2822222,-48.888674,181.52135)" id="g3652-1">' +
    '<rect ry="2.9559755" y="353.55258" x="185.01654" height="29.999998" width="29.999998" id="' +
    element.id + suffix +
    ':IconBackground" fill="silver" style="fill-opacity:1;stroke:none;stroke-width:0.07384499;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<rect y="387.27902" x="173.69635" height="21.42857" width="50.892853" id="' +
    element.id + suffix +
    ':ValueBackground" fill="silver" style="fill-opacity:1;stroke:#000000;stroke-width:0.93749994;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<text id="text1608-6-1-4-1-9" y="401.78311" x="187.84198" style="font-style:normal;font-weight:normal;font-size:37.49999237px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.93749994" xml:space="preserve">' +
    '<tspan style="font-size:12.49999809px;stroke-width:0.93749994" y="401.78311" x="175.84198" id="' +
    element.id + suffix +
    ':Value" sodipodi:role="line">' +
    0 +
    "</tspan>" +
    "</text>" +
    '<text id="text2729-8-9-9-3" y="372.26047" x="188.67026" style="font-style:normal;font-weight:normal;font-size:10.71748638px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26793715" xml:space="preserve">' +
    '<tspan style="font-style:normal;font-variant:normal;font-weight:bold;font-stretch:normal;font-family:sans-serif;-inkscape-font-specification:"sans-serif Bold";stroke-width:0.26793715" y="372.26047" x="192.67026" id="tspan2727-5-1-7-8" sodipodi:role="line">OP</tspan>' +
    "</text>" +
    "</g>" +
    "</g>";

  return op_str;
}

export function updateOpacIcon(element, suffix="") {
  if (
    document.getElementById(element.id + suffix + ":IconBackground") != null &&
    document.getElementById(element.id + suffix + ":ValueBackground") != null
  ) {
    // changes the value
    let new_value = null;
    
    new_value = element.elementValues.find(
      (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.OPAC_CONCENTRATION
    );

    if (typeof new_value != "undefined" && new_value != null && document.getElementById(element.id + suffix + ":Value") != null) {
      document.getElementById(element.id + suffix + ":Value").innerHTML = new_value.value;
    }

    // change the color based on the state
    let color = "";
    let elementState = element.state;

    if(typeof elementState != 'undefined') {
      if (elementState == 0) {
        color = "#ffcc00";
      } else if (elementState == 1) {
        color = "#008000";
      } else if (elementState == 2) {
        color = "#800080";
      } else if (elementState == 3) {
        color = "#ff0000";
      }

      document.getElementById(element.id + suffix + ":IconBackground").style[
        "stroke"
      ] = color;
      document.getElementById(element.id + suffix + ":ValueBackground").style[
        "stroke"
      ] = color;
    }
  }
}