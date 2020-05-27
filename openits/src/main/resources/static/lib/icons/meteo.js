import { staticsIcons } from "../../icons/synopticJson.js";
import * as elementTypeMap from "../elementTypeMap.js";

/*
* Version galleta
*/
/*
export function generateIcon(element, suffix="") {
  let meteo_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> <rect class="capa_mapa" style="fill:#c3e6a1;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93" width="28.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" />  class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-0" width="28.228191" height="5.6456451" x="0.041112646" y="280.02197" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-6" width="28.228191" height="5.6456451" x="0.041112646" y="274.37631" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-62" width="28.228191" height="5.6456451" x="0.041112646" y="285.6676" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-61" width="28.228191" height="5.6456451" x="0.041112646" y="291.31323" ry="0" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="2.465615" y="277.72061" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8">I</tspan><tspan sodipodi:role="line" x="2.465615" y="283.56769" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689">V</tspan><tspan sodipodi:role="line" x="2.465615" y="289.41479" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691">O</tspan><tspan sodipodi:role="line" x="2.465615" y="295.26187" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693">C</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)" inkscape:label="#titulo"><tspan sodipodi:role="line" x="13.904327" y="272.36899" style="font-size:3.15464282px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-0">' +
    element.id + suffix +
    '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.086432" y="277.66467" id="galleta_texto1" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="13.086432" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9">' +
    Math.floor(Math.random() * 10001) +
    '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="23.677027" y="277.66467" id="text4070-1-2-2-22" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="23.677027" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8-8">V/h</tspan><tspan sodipodi:role="line" x="23.677027" y="283.51175" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689-9">K/h</tspan><tspan sodipodi:role="line" x="23.677027" y="289.35886" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691-7">%</tspan><tspan sodipodi:role="line" x="23.677027" y="295.20593" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-3">%L</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="283.57147" id="galleta_texto2" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="283.57147" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9-3">' +
    Math.floor(Math.random() * 10001) +
    '</tspan></text> <text transform="scale(0.99833797,1.0016648)" id="galleta_texto3" y="289.38263" x="13.15081" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" xml:space="preserve"><tspan id="tspan2140" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" y="289.38263" x="13.15081" sodipodi:role="line">' +
    Math.floor(Math.random() * 10001) +
    '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="295.19379" id="galleta_texto4" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="295.19379" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan2150">' +
    Math.floor(Math.random() * 10001) +
    "</tspan></text> </g>";

  return meteo_str;
}
*/

export function updateMeteoIcon(element, suffix = "") {
  // METEO
  // icon based on status
  let elementType = elementTypeMap.getElementType(element.id);
  let elementState = element.state;
  let elem_icon = null;

  if (typeof staticsIcons[elementType] != "undefined") {
    elem_icon = staticsIcons[elementType]["state"].find(
      (ic) => ic.state === elementState
    );
  }

  if (typeof elem_icon != "undefined") {
    elem_icon = elem_icon.icon;
  } else {
    elem_icon = "";
  }

  if(typeof elementState != 'undefined') {
    if(document.getElementById(element.id + suffix) != null) {
      document.getElementById(element.id + suffix).setAttribute("href", "./img/" + elem_icon);
    }
  }
}