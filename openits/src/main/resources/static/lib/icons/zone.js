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
      color = "gold";
    } else if (elementState == 1) {
      color = "green";
    } else if (elementState == 2) {
      color = "purple";
    } else if (elementState == 3) {
      color = "red";
    }
  }

  let zone_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,3553.937)">' +
    '<g transform="matrix(12.442344,0,0,-12.442344,395.71032,702.10879)" id="g8">' +
    '<path inkscape:connector-curvature="0" d="M 5,160 4.3910999,79.894707 c 108.1488201,0 217.9325401,0 310.0000001,0 L 315,160 V 315 H 160 5 Z m 255,40 V 110 H 160 60 v 90 90 h 100 100 z" id="path4" fill="black" sodipodi:nodetypes="ccccccccccccccccc" />' +
    '<path inkscape:connector-curvature="0" d="M 102,237 C 87,221 80,201 80,172 v -42 h 75 75 v 43 c 0,78 -79,117 -128,64 z m 26,-14 c -15,-15 -31,-24 -34,-20 -10,10 26,47 45,47 11,0 8,-7 -11,-27 z m 77,7 c 28,-31 8,-37 -23,-7 -19,20 -22,27 -11,27 9,0 24,-9 34,-20 z" id="path6" fill="black" />' +
    "</g>" +
    '<path fill="black" d="m 1491.7867,2422.8674 c 3.8861,-430.2656 8.7069,-480.6674 61.3639,-641.5619 86.2906,-263.6629 281.4018,-489.3072 530.3089,-613.2974 512.4363,-255.26436 1119.063,42.0195 1320.2676,647.011 56.4173,169.6384 65.6617,263.1499 65.6617,664.2049 v 333.5623 H 2478.8269 1488.265 Z m 309.2576,-594.7251 c 199.8555,-96.6568 553.1361,-455.7154 553.1361,-562.1828 0,-48.1678 -93.0337,-61.0038 -189.1862,-26.1023 -235.4117,85.4498 -538.9693,411.1873 -519.3928,557.3429 9.4397,70.4748 54.9645,79.5369 155.4429,30.9422 z m 1506.7201,-1.2649 c 49.788,-49.788 -29.1929,-204.4965 -185.8681,-364.08 -147.1686,-149.9005 -313.1335,-242.0115 -436.0545,-242.0115 -112.9505,0 -106.7319,65.5569 20.7478,218.7223 120.7712,145.1054 254.6713,265.762 378.9412,341.4617 97.2411,59.235 189.7884,78.3528 222.2336,45.9075 z" id="path29" inkscape:connector-curvature="0" transform="matrix(0.93750001,0,0,0.93750001,0,-3553.937)" />' +
    '<path id="' + element.id + suffix + ':IconBackground" fill="' + color + '" d="m 490.1271,2746.7997 c 2.25479,-400.7603 5.37264,-1100.7914 5.37264,-1555.6348 V 364.17659 H 2548.1297 4600.7596 v 984.57481 c 0,541.5162 -2.4159,1241.5519 -5.3686,1555.6349 l 2.7126,837.7403 -2068.7964,-32.3249 -2044.55275,-8.0812 z M 3873.4498,1887.4866 V 687.42539 H 2544.089 1214.7283 V 1882.0991 c 0,657.0706 2.4244,1197.0981 5.3875,1200.0612 2.9631,2.9631 601.1754,5.3875 1329.3608,5.3875 h 1323.9732 z" id="path11" inkscape:connector-curvature="0" transform="matrix(0.93750001,0,0,0.93750001,0,-3553.937)" sodipodi:nodetypes="sscccsscccsscccssscs" />' +
    "</g>";

  return zone_str;
}

export function updateZoneIcon(element, suffix = "") {
  // Tunnel
  if (
    document.getElementById(element.id + suffix + ":IconBackground") != null
  ) {
    // changes the color based on the state
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
        "fill"
      ] = color;
    }
  }
}
