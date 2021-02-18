import * as elementTypeMap from "../elementTypeMap.js";
import { availablePictos } from "../../data/pmvpicto.js";
import {ICON_COLOR} from "../../data/GLOBAL_PROPERTIES.js";

export function generateIcon(element, suffix = "") {
  // check for the status message we should use
  let picToShow = element.elementValues.find(
    (ic) =>
      elementTypeMap.getElementTypeParam(ic.id) ==
      elementTypeMap.MATRIX_PANEL_PICTOGRAM
  );

  // changes the color based on the status
  let color = "";
  let elementState;
  if(element.elementTypeState && element.elementTypeState.id) {
    elementState = getTrimId(element.elementTypeState.id);
  }

  if(typeof elementState != 'undefined') {
    color = ICON_COLOR[elementState];
  }

  let pmv_str = "";

  pmv_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-272.12915)">' +
    '<g id="g1739-1" transform="matrix(0.40956503,0,0,0.48504268,-500.12204,357.57278)">' +
    '<rect y="-174.73952" x="1222.5098" height="48.450024" width="48.450024" id="' +
    element.id +
    suffix +
    ':rect2250-2-89" stroke="' +
    color +
    '" fill="black" style="fill-opacity:1;stroke-width:2.8125;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />';

  if (availablePictos[picToShow.value] && typeof availablePictos[picToShow.value].base64 != "undefined") {
    pmv_str +=
      '<image id="' +
      element.id +
      suffix +
      ':pic1" xlink:href="data:image/png;base64,' +
      availablePictos[picToShow.value].base64 +
      '" y="-174.73952" x="1222.5098" height="48.450024" width="48.450024"/>';
  } else {
    pmv_str +=
      '<image id="' +
      element.id +
      suffix +
      ':pic1" xlink:href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABAAgMAAADXB5lNAAAADFBMVEUAAAD/AAD///8AAACMA1FsAAAAAWJLR0QAiAUdSAAAAAlwSFlzAAAOwwAADsMBx2+oZAAAAAd0SU1FB+QGGgsuHgy4asEAAAASSURBVDjLY2AYBaNgFIwC8gAABEAAASmN4VUAAAAASUVORK5CYII=" y="-174.73952" x="1222.5098" height="48.450024" width="48.450024"/>';
  }

  pmv_str += "</g>" + "</g>";

  return pmv_str;
}

export function changeMatrixPanelIcon(element, suffix = "") {
  // adds the pictograph
  // currently its fixed to pic1.jpg
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
}

export function updateMatrixPanelIcon(element, suffix = "") {
  // check for the status message we should use
  let picToShow = element.elementValues.find(
    (ic) =>
      elementTypeMap.getElementTypeParam(ic.id) ==
      elementTypeMap.MATRIX_PANEL_PICTOGRAM
  );

  // changes the color based on the status
  let color = "";
  let elementState;
  if(element.elementTypeState && element.elementTypeState.id) {
    elementState = getTrimId(element.elementTypeState.id);
  }

  if(typeof elementState != 'undefined') {
    color = ICON_COLOR[elementState];
  }

  // icon based on status
  if (document.getElementById(element.id + suffix + ":rect2250-2-89") != null) {
    let color = "";
    let elementState;
    if(element.elementTypeState && element.elementTypeState.id) {
      elementState = getTrimId(element.elementTypeState.id);
    }

    if (elementState == 0) {
      color = "#ffcc00";
    } else if (elementState == 1) {
      color = "#008000";
    } else if (elementState == 2) {
      color = "#800080";
    } else if (elementState == 3) {
      color = "#ff0000";
    }

    document.getElementById(element.id + suffix + ":rect2250-2-89").style[
      "stroke"
    ] = color;
  }

  if (typeof picToShow != "undefined") {
    if (availablePictos[picToShow.value] && typeof availablePictos[picToShow.value].base64 != "undefined") {
      document
        .getElementById(element.id + suffix + ":pic1")
        .setAttribute(
          "xlink:href",
          "data:image/png;base64," + availablePictos[picToShow.value].base64
        );
    } else {
      document
        .getElementById(element.id + suffix + ":pic1")
        .setAttribute(
          "xlink:href",
          "data:image/png;base64," + availablePictos["undefined"].base64
        );
    }
  } else {
      document
        .getElementById(element.id + suffix + ":pic1")
        .setAttribute(
          "xlink:href",
          "data:image/png;base64," + availablePictos["undefined"].base64
        );
  }
}
