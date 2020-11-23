import { staticsIcons } from "../../icons/synopticJson.js";
import * as elementTypeMap from "../elementTypeMap.js";

export function generateIcon(element, suffix = "") {
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
      color = "blue";
    } else if (elementState == 1) {
      color = "blue";
    } else if (elementState == 2) {
      color = "blue";
    } else if (elementState == 3) {
      color = "red";
    }
  }

  let radar_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="' + element.id + suffix + ':IconBackground" transform="translate(0,3553.937)" fill="' + color + '">' +
    '<g transform="matrix(12.442344,0,0,-12.442344,395.71032,702.10879)" id="g8">' +
    '<path inkscape:connector-curvature="0" d="M 5,160 4.3910999,79.894707 c 108.1488201,0 217.9325401,0 310.0000001,0 L 315,160 V 315 H 160 5 Z m 255,40 V 110 H 160 60 v 90 90 h 100 100 z" id="path4" fill="black" sodipodi:nodetypes="ccccccccccccccccc" />' +
    '<path inkscape:connector-curvature="0" d="M 102,237 C 87,221 80,201 80,172 v -42 h 75 75 v 43 c 0,78 -79,117 -128,64 z m 26,-14 c -15,-15 -31,-24 -34,-20 -10,10 26,47 45,47 11,0 8,-7 -11,-27 z m 77,7 c 28,-31 8,-37 -23,-7 -19,20 -22,27 -11,27 9,0 24,-9 34,-20 z" id="path6" fill="black" />' +
    "</g>" +
    '<path d="m 490.1271,2746.7997 c 2.25479,-400.7603 5.37264,-1100.7914 5.37264,-1555.6348 V 364.17659 H 2548.1297 4600.7596 v 984.57481 c 0,541.5162 -2.4159,1241.5519 -5.3686,1555.6349 l 2.7126,837.7403 -2068.7964,-32.3249 -2044.55275,-8.0812 z M 3873.4498,1887.4866 V 687.42539 H 2544.089 1214.7283 V 1882.0991 c 0,657.0706 2.4244,1197.0981 5.3875,1200.0612 2.9631,2.9631 601.1754,5.3875 1329.3608,5.3875 h 1323.9732 z" id="path11" transform="matrix(0.93750001,0,0,0.93750001,0,-3553.937)" />' +
    "</g>";

  return radar_str;
}

export function updateRadarIcon(element, suffix = "") {
  // Radar
  if (
    document.getElementById(element.id + suffix + ":IconBackground") != null
  ) {
    // changes the color based on the state
    let color = "";
    let elementState = element.state;

    if(typeof elementState != 'undefined') {
      if (elementState == 0) {
        color = "blue";
      } else if (elementState == 1) {
        color = "blue";
      } else if (elementState == 2) {
        color = "blue";
      } else if (elementState == 3) {
        color = "red";
      }

      document.getElementById(element.id + suffix + ":IconBackground").style[
        "fill"
      ] = color;
    }
  }
}
