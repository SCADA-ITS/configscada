import * as elementTypeMap from "../elementTypeMap.js";
import {ICON_COLOR} from "../../data/GLOBAL_PROPERTIES.js";

export function generateIcon(element, suffix = "") {
  // check for the status message we should use
  let messageToShow = element.elementValues.find(
    (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_COMAND_FOR_DMS
  );

  let message1 = "~~~######~~~";

  if (messageToShow.value == "1") {
    let tmp_message1 = element.elementValues.find(
      (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_TEXT_FOR_STATUS_1
    );

    if (typeof tmp_message1 != "undefined") {
      message1 =
        "~~~###" +
        element.elementValues.find(
          (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_TEXT_FOR_STATUS_1
        ).value +
        "###~~~";
    }
  } else if (messageToShow.value == "2") {
    let tmp_message1 = element.elementValues.find(
      (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_TEXT_FOR_STATUS_2
    );

    if (typeof tmp_message1 != "undefined") {
      message1 =
        "~~~###" +
        element.elementValues.find(
          (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_TEXT_FOR_STATUS_2
        ).value +
        "###~~~";
    }
  }

  // changes the color based on the status
  let color = "";
  let elementState;
  if(typeof element.state != 'undefined') {
    elementState = element.state;
  } 
  
  if(typeof elementState != 'undefined') {
    color = ICON_COLOR[elementState];
  }

  let pmv_str = "";

  let linea1 = "";
  let linea2 = "";
  let linea3 = "";

  if (typeof message1 != "undefined") {
    linea1 = message1.split("~~~")[1].split("###")[0];
    linea2 = message1.split("~~~")[1].split("###")[1];
    linea3 = message1.split("~~~")[1].split("###")[2];
  }

  pmv_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-272.12915)">' +
    '<g id="g1739-1" transform="matrix(0.40956503,0,0,0.48504268,-500.12204,357.57278)">' +
    '<rect y="-174.73952" x="1222.5098" height="48.450024" width="48.450024" id="' +
    element.id +
    suffix +
    ':rect2250-2-89" stroke="' + color + '" fill="black" style="fill-opacity:1;stroke-width:2.8125;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<rect y="-174.73952" x="1270.9597" height="48.44997" width="136.0842" id="' +
    element.id +
    suffix +
    ':rect2085-1" stroke="' + color + '" fill="black" style="fill-opacity:1;stroke-width:2.8125;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:8px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke:none;stroke-width:0.06259221" y="-161.73952" x="1275.9597" id="text2462-1"><tspan sodipodi:role="line" id="tspan2460" style="stroke-width:0.06259221"><tspan fill="yellow" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill-opacity:1;stroke-width:0.06259221" id="' +
    element.id +
    suffix +
    ':linea1">' +
    linea1 +
    "</tspan></tspan></text>" +
    '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:8px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke:none;stroke-width:0.06259221" y="-148.73952" x="1275.9597" id="text2462-1"><tspan sodipodi:role="line" id="tspan2460" style="stroke-width:0.06259221"><tspan fill="yellow" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill-opacity:1;stroke-width:0.06259221" id="' +
    element.id +
    suffix +
    ':linea2">' +
    linea2 +
    "</tspan></tspan></text>" +
    '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:8px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke:none;stroke-width:0.06259221" y="-134.73952" x="1275.9597" id="text2462-1"><tspan sodipodi:role="line" id="tspan2460" style="stroke-width:0.06259221"><tspan fill="yellow" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill-opacity:1;stroke-width:0.06259221" id="' +
    element.id +
    suffix +
    ':linea3">' +
    linea3 +
    "</tspan></tspan></text>" +
    "</g>" +
    '<g>' +
    '<rect fill="white" id="rect847" width="12.787097" height="13.634683" x="4.4404564" y="274.40842" />' +
    '<path fill="black" d="M 9.8549071,286.82406 H 8.2364542 l -0.3488429,-1.04338 -0.3488446,-1.04336 0.7110826,-1.52497 0.7110825,-1.52497 H 8.5196664 8.0784024 l 1.1572161,-1.46761 1.1572165,-1.46763 h 0.872934 0.872935 l -1.291703,2.906 -1.2917039,2.90602 v 0.39615 0.39614 h 0.9590319 0.959033 v 0.7338 0.73381 z m 1.6184549,-8.80574 h -0.56389 l -0.429574,-0.79776 -0.429572,-0.79774 0.711517,-0.81663 0.711519,-0.81664 0.711517,0.81664 0.711517,0.81663 -0.429572,0.79774 -0.429574,0.79776 z" id="path854" inkscape:connector-curvature="0" sodipodi:nodetypes="cccccccccccccccccccccccccccccccc" />' +
    '<path fill="blue" id="path845" d="M 10.834006,295.99673 H 0.92400599 v -11.741 -11.041 h 9.91000001 9.91 v 11.741 10.741 z m 0,-7.9536 h 6.393547 v -6.81735 -6.81733 H 10.834006 4.4404568 v 6.81733 6.81735 z" />' +
    "</g>" +
    "</g>";

  return pmv_str;
}

export function changeDMSIcon(element, suffix="") {
  // adds the pictograph
  // currently its fixed to pic1.jpg
  /*
  let pmv_pic = document.getElementById(element.id + suffix + ":rect2250-2-89"); // selects the pictograph rectangle
  let r = Snap(pmv_pic);
  let rpg = r.parent().group();
  let rpg_image = rpg.image(
    "./img/pic1.jpg",
    r.node.getAttribute("x"),
    r.node.getAttribute("y"),
    r.node.getAttribute("width"),
    r.node.getAttribute("height")
  );
  */
}

export function updateDmsIcon(element, suffix = "") {
  // DMS
  // check for the status message we should use
  let messageToShow = element.elementValues.find(
    (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_COMAND_FOR_DMS
  );

  let message1 = "~~~######~~~";

  if (messageToShow.value == "1") {
    let tmp_message1 = element.elementValues.find(
      (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_TEXT_FOR_STATUS_1
    );

    if (typeof tmp_message1 != "undefined") {
      message1 =
        "~~~###" +
        element.elementValues.find(
          (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_TEXT_FOR_STATUS_1
        ).value +
        "###~~~";
    }
  } else if (messageToShow.value == "2") {
    let tmp_message1 = element.elementValues.find(
      (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_TEXT_FOR_STATUS_2
    );

    if (typeof tmp_message1 != "undefined") {
      message1 =
        "~~~###" +
        element.elementValues.find(
          (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.DMS_TEXT_FOR_STATUS_2
        ).value +
        "###~~~";
    }
  }

  if (typeof message1 != "undefined") {
    let linea1 = message1.split("~~~")[1].split("###")[0];
    let linea2 = message1.split("~~~")[1].split("###")[1];
    let linea3 = message1.split("~~~")[1].split("###")[2];
    
    document.getElementById(element.id + suffix + ":linea1").innerHTML = linea1;
    document.getElementById(element.id + suffix + ":linea2").innerHTML = linea2;
    document.getElementById(element.id + suffix + ":linea3").innerHTML = linea3;
  }

  // icon based on status
  if (
    document.getElementById(element.id + suffix + ":rect2085-1") != null &&
    document.getElementById(element.id + suffix + ":rect2250-2-89") != null
  ) {
    let color = "";
    let elementState = element.state;

    if (elementState == 0) {
      color = "#ffcc00";
    } else if (elementState == 1) {
      color = "#008000";
    } else if (elementState == 2) {
      color = "#800080";
    } else if (elementState == 3) {
      color = "#ff0000";
    }

    document.getElementById(element.id + suffix + ":rect2085-1").style["stroke"] = color;
    document.getElementById(element.id + suffix + ":rect2250-2-89").style["stroke"] = color;

  }

  // update icon
  changeDMSIcon(element, suffix);
}