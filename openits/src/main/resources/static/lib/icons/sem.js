import { staticsIcons } from "../../icons/synopticJson.js";
import * as elementTypeMap from "../elementTypeMap.js";

export function generateIcon(element, suffix="") {
  let sem_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-286.68123)">' +
    '<g id="g2866" transform="matrix(0.2822222,0,0,0.2822222,34.461018,264.46835)">' +
    '<rect ry="3.974129" y="80.367165" x="-120.69973" height="33.496231" width="28.197393" id="' + element.id + suffix + ':IconBackground" style="fill:#cccccc;fill-opacity:1;stroke:#00c000;stroke-width:2.8125;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<g style="fill:#000000;stroke:#00ff00;stroke-opacity:1" id="g3-9-9" transform="matrix(0.03113516,0,0,0.03113516,-120.0617,83.098956)">' +
    '<g style="fill:#000000;stroke:#00ff00;stroke-opacity:1" id="g5-9-5">' +
    '<path style="fill:#000000;stroke:#00ff00;stroke-opacity:1" inkscape:connector-curvature="0" id="path7-08-0" d="m 247.111,610.652 h -98.61 c -8.392,0 -15.165,7.066 -14.57,15.438 3.521,49.571 100.078,139.338 122.949,159.948 3.072,2.768 4.824,6.705 4.824,10.84 v 8.817 c 0,33.944 27.517,61.461 61.461,61.461 h 220.81 c 33.948,0 61.47,-27.521 61.47,-61.47 v -8.808 c 0,-4.135 1.752,-8.072 4.824,-10.84 22.871,-20.61 119.427,-110.377 122.948,-159.948 0.596,-8.371 -6.178,-15.438 -14.569,-15.438 h -98.603 c -8.059,0 -14.592,-6.533 -14.592,-14.592 v -66.103 c 0,-4.135 1.752,-8.072 4.824,-10.84 22.87,-20.61 119.424,-110.374 122.948,-159.939 0.595,-8.37 -6.179,-15.438 -14.57,-15.438 h -98.61 c -8.059,0 -14.592,-6.533 -14.592,-14.592 v -66.102 c 0,-4.135 1.752,-8.072 4.824,-10.84 22.871,-20.61 119.427,-110.377 122.949,-159.948 0.595,-8.371 -6.179,-15.438 -14.57,-15.438 h -98.61 c -8.059,0 -14.592,-6.533 -14.592,-14.592 V 61.46 C 605.454,27.518 577.938,0 543.992,0 H 323.164 C 289.22,0 261.702,27.517 261.702,61.462 v 0.768 c 0,8.059 -6.533,14.592 -14.592,14.592 H 148.5 c -8.392,0 -15.165,7.067 -14.57,15.438 3.521,49.571 100.078,139.338 122.949,159.948 3.072,2.768 4.824,6.705 4.824,10.84 v 66.102 c 0,8.059 -6.533,14.592 -14.592,14.592 h -98.61 c -8.392,0 -15.165,7.067 -14.57,15.438 3.524,49.566 100.078,139.329 122.948,159.939 3.072,2.768 4.824,6.705 4.824,10.84 v 66.103 c -10e-4,8.057 -6.534,14.59 -14.592,14.59 z M 434.103,74.082 c 53.213,0 96.35,43.137 96.35,96.35 0,53.213 -43.137,96.35 -96.35,96.35 -53.213,0 -96.351,-43.137 -96.351,-96.35 0,-53.213 43.138,-96.35 96.351,-96.35 z m 0,263.668 c 53.213,0 96.35,43.137 96.35,96.351 0,53.213 -43.137,96.35 -96.35,96.35 -53.213,0 -96.351,-43.137 -96.351,-96.351 0,-53.213 43.138,-96.35 96.351,-96.35 z m 0,263.658 c 53.213,0 96.35,43.137 96.35,96.35 0,53.213 -43.137,96.351 -96.35,96.351 -53.213,0 -96.351,-43.138 -96.351,-96.351 0,-53.213 43.138,-96.35 96.351,-96.35 z" />' +
    '</g>' +
    '</g>' +
    '<path inkscape:connector-curvature="0" id="' + element.id + suffix + 'green" d="m -107.38937,107.61692 c -1.94132,-0.61932 -2.69254,-3.12471 -1.39239,-4.64365 1.53347,-1.79155 4.51483,-1.10614 5.05924,1.16313 0.32251,1.34434 -0.42526,2.84082 -1.68178,3.36584 -0.60731,0.25374 -1.40477,0.29982 -1.98507,0.11468 z" style="fill:#00ff00;fill-opacity:1;stroke:#00ff00;stroke-width:0.11440272;stroke-opacity:1" />' +
    '<path inkscape:connector-curvature="0" id="' + element.id + suffix + 'yellow" d="m -107.38937,99.61692 c -1.94132,-0.61932 -2.69254,-3.12471 -1.39239,-4.64365 1.53347,-1.79155 4.51483,-1.10614 5.05924,1.16313 0.32251,1.34434 -0.42526,2.84082 -1.68178,3.36584 -0.60731,0.25374 -1.40477,0.29982 -1.98507,0.11468 z" style="fill:#ffff00;fill-opacity:1;stroke:#ffff00;stroke-width:0.11440272;stroke-opacity:1" />' +
    '<path inkscape:connector-curvature="0" id="' + element.id + suffix + 'red" d="m -107.38937,90.61692 c -1.94132,-0.61932 -2.69254,-3.12471 -1.39239,-4.64365 1.53347,-1.79155 4.51483,-1.10614 5.05924,1.16313 0.32251,1.34434 -0.42526,2.84082 -1.68178,3.36584 -0.60731,0.25374 -1.40477,0.29982 -1.98507,0.11468 z" style="fill:#ff0000;fill-opacity:1;stroke:#ff0000;stroke-width:0.11440272;stroke-opacity:1" />' +
    '</g>' +
    '</g>';

  return sem_str;
}

export function updateSemIcon(element, suffix = "") {
  // SEM
  if (
    document.getElementById(element.id + suffix + ":IconBackground") != null
  ) {
    // changes the value
    let new_value = null;
    
    new_value = element.elementValues.find(
      (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.SEM_STATUS
    );

    if (typeof new_value != "undefined" && new_value != null) {
      if (new_value.value == "0") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "hidden";
      } else if (new_value.value == "1") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "hidden";
      } else if (new_value.value == "2") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "hidden";
      } else if (new_value.value == "3") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "visible";
      }
    }

    // changes the color based on the status
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

      /*
      document.getElementById(element.id + suffix + ":IconBackground").style[
        "fill"
      ] = color;*/
      document.getElementById(element.id + suffix + ":IconBackground").style[
        "stroke"
      ] = color;
    }
  }
}
