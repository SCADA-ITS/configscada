import { staticsIcons } from "../icons/synopticJson.js";

export function generateDMSIcon(element) {
  // check for the status message we should use
  // FIXME: this is just for the demo, it is not a real DMS
  let messageToShow = element.elementValues.find(
    (ic) => ic.elementTypeParam.description === "comand for rotapanel"
  );

  let message1 = "~~~######~~~";

  if (messageToShow.value == "1") {
    let tmp_message1 = element.elementValues.find(
      (ic) => ic.elementTypeParam.description === "text for status 1"
    );

    if (typeof tmp_message1 != "undefined") {
      message1 =
        "~~~###" +
        element.elementValues.find(
          (ic) => ic.elementTypeParam.description === "text for status 1"
        ).value +
        "###~~~";
    }
  } else if (messageToShow.value == "2") {
    let tmp_message1 = element.elementValues.find(
      (ic) => ic.elementTypeParam.description === "text for status 2"
    );

    if (typeof tmp_message1 != "undefined") {
      message1 =
        "~~~###" +
        element.elementValues.find(
          (ic) => ic.elementTypeParam.description === "text for status 2"
        ).value +
        "###~~~";
    }
  }

  let pmv_str = "";

  if (typeof message1 != "undefined") {
    let linea1 = message1.split("~~~")[1].split("###")[0];
    let linea2 = message1.split("~~~")[1].split("###")[1];
    let linea3 = message1.split("~~~")[1].split("###")[2];

    /*
    pmv_str =
      '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" > <rect style="fill:#000000;fill-opacity:1;stroke:#c0c0c0;stroke-width:0.79649729;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="' +
      element.id +
      ':rect2250-2-0" width="13.686104" height="13.755985" x="0.39483935" y="282.84232" /> <rect style="fill:#000000;fill-opacity:1;stroke:#c0c0c0;stroke-width:0.79649729;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="' +
      element.id +
      ':rect2085-9" width="38.440899" height="13.75597" x="14.080923" y="282.84232" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:2.50368834px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.06259221" x="15.905216" y="286.23163" id="text2462" transform="scale(0.99745676,1.0025497)"><tspan sodipodi:role="line" x="15.905216" y="286.23163" id="tspan2460" style="stroke-width:0.06259221"><tspan x="15.905216" y="286.23163" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1;stroke-width:0.06259221" id="' +
      element.id +
      ':linea1">' +
      linea1 +
      ' </tspan></tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:2.50368834px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.06259221" x="15.960894" y="289.95462" id="text2468" transform="scale(0.99745676,1.0025497)"><tspan sodipodi:role="line" x="15.960894" y="289.95462" id="tspan2466" style="stroke-width:0.06259221"><tspan x="15.960894" y="289.95462" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1;stroke-width:0.06259221" id="' +
      element.id +
      ':linea2">' +
      linea2 +
      '</tspan></tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:2.50368834px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.06259221" x="15.943024" y="293.65649" id="text2474" transform="scale(0.99745676,1.0025497)"><tspan sodipodi:role="line" x="15.943024" y="293.65649" id="tspan2472" style="stroke-width:0.06259221"><tspan x="15.943024" y="293.65649" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1;stroke-width:0.06259221" id="' +
      element.id +
      ':linea3">' +
      linea3 +
      "</tspan></tspan></text></g>";
    */

    pmv_str = 
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-272.12915)">' +
    '<g id="g1739-1" transform="matrix(0.40956503,0,0,0.48504268,-500.12204,357.57278)">' +
      '<rect y="-174.73952" x="1222.5098" height="48.450024" width="48.450024" id="' + element.id + ':rect2250-2-89" style="fill:#000000;fill-opacity:1;stroke:#c0c0c0;stroke-width:2.8125;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
      '<rect y="-174.73952" x="1270.9597" height="48.44997" width="136.0842" id="' + element.id + ':rect2085-1" style="fill:#000000;fill-opacity:1;stroke:#c0c0c0;stroke-width:2.8125;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
      '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:8px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.06259221" y="-161.73952" x="1275.9597" id="text2462-1"><tspan sodipodi:role="line" id="tspan2460" style="stroke-width:0.06259221"><tspan style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1;stroke-width:0.06259221" id="' +
      element.id +
      ':linea1">' +
      linea1 +
      '</tspan></tspan></text>' +
      '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:8px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.06259221" y="-148.73952" x="1275.9597" id="text2462-1"><tspan sodipodi:role="line" id="tspan2460" style="stroke-width:0.06259221"><tspan style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1;stroke-width:0.06259221" id="' +
      element.id +
      ':linea2">' +
      linea2 +
      '</tspan></tspan></text>' +
      '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:8px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.06259221" y="-134.73952" x="1275.9597" id="text2462-1"><tspan sodipodi:role="line" id="tspan2460" style="stroke-width:0.06259221"><tspan style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1;stroke-width:0.06259221" id="' +
      element.id +
      ':linea3">' +
      linea3 +
      '</tspan></tspan></text>' +
      /*
      '<flowRoot transform="matrix(0.22101843,0,0,0.22101843,1218.5468,-254.95386)" style="font-style:normal;font-weight:normal;font-size:40px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none" id="flowRoot2065-8" xml:space="preserve">' +
        '<flowRegion id="flowRegion2067-1">' +
          '<rect y="408.57144" x="265.71429" height="368.57144" width="1365.7142" id="rect2069-09" />' +
        '</flowRegion>' +
        '<flowPara style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1" id="' + element.id + ':linea1">' + "AAAAAAA" + '</flowPara>' +
      '</flowRoot>' +
      '<flowRoot style="font-style:normal;font-weight:normal;font-size:40px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none" id="flowRoot2065-3-39" xml:space="preserve" transform="matrix(0.22101843,0,0,0.22101843,1218.7434,-241.80776)">' +
      '<flowRegion id="flowRegion2067-6-1">' +
      '<rect y="408.57144" x="265.71429" height="368.57144" width="1365.7142" id="rect2069-7-2" />' +
      '</flowRegion>' +
      '<flowPara style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:"Erbos Draco Open NBP";-inkscape-font-specification:"Erbos Draco Open NBP";fill:#ffff00;fill-opacity:1" id="' + element.id + ':linea2">' + "BBBBBBBB" + '</flowPara>' +
      '</flowRoot>' +
      '<flowRoot style="font-style:normal;font-weight:normal;font-size:40px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none" id="flowRoot2065-35-26" xml:space="preserve" transform="matrix(0.22101843,0,0,0.22101843,1218.6803,-228.7361)">' +
      '<flowRegion id="flowRegion2067-62-6">' +
      '<rect y="408.57144" x="265.71429" height="368.57144" width="1365.7142" id="rect2069-9-5" />' +
      '</flowRegion>' +
      '<flowPara style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:"Erbos Draco Open NBP";-inkscape-font-specification:"Erbos Draco Open NBP";fill:#ffff00;fill-opacity:1" id="' + element.id + ':linea3">' + "3" + '</flowPara>' +
      '</flowRoot>' +
      */
    '</g>' +
    '</g>';
  }

  return pmv_str;
}

export function changeDMSIcon(element) {
  // adds the pictograph
  // currently its fixed to pic1.jpg
  let pmv_pic = document.getElementById(element.id + ":rect2250-2-89"); // selects the pictograph rectangle
  let r = Snap(pmv_pic);
  let rpg = r.parent().group();
  let rpg_image = rpg.image(
    "./img/pic1.jpg",
    r.node.getAttribute("x"),
    r.node.getAttribute("y"),
    r.node.getAttribute("width"),
    r.node.getAttribute("height")
  );

  // adds a class to the image so it can be resized in a responsive way
  //rpg_image.addClass("capa_mapa");
}

export function generateETDIcon(element, values) {
  /*
  let etd_str =
    '<g inkscape:label="Capa 1" class="capa_mapa" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> <rect style="fill:#c3e6a1;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93" width="28.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-0" width="28.228191" height="5.6456451" x="0.041112646" y="280.02197" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-6" width="28.228191" height="5.6456451" x="0.041112646" y="274.37631" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-62" width="28.228191" height="5.6456451" x="0.041112646" y="285.6676" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-61" width="28.228191" height="5.6456451" x="0.041112646" y="291.31323" ry="0" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="2.465615" y="277.72061" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8">I</tspan><tspan sodipodi:role="line" x="2.465615" y="283.56769" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689">V</tspan><tspan sodipodi:role="line" x="2.465615" y="289.41479" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691">O</tspan><tspan sodipodi:role="line" x="2.465615" y="295.26187" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693">C</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)" inkscape:label="#titulo"><tspan sodipodi:role="line" x="13.904327" y="272.36899" style="font-size:3.15464282px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-0">' +
    element.id +
    '</tspan></text>';
  */

 let etd_str =
 '<g inkscape:label ="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> <rect style="fill:#c3e6a1;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93" width="28.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)" inkscape:label="#titulo"><tspan sodipodi:role="line" x="13.904327" y="272.36899" style="font-size:3.15464282px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-0">' +
 element.id +
 '</tspan></text>';

 //<rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-0" width="28.228191" height="5.6456451" x="0.041112646" y="280.02197" ry="0" />' <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-6" width="28.228191" height="5.6456451" x="0.041112646" y="274.37631" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-62" width="28.228191" height="5.6456451" x="0.041112646" y="285.6676" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-61" width="28.228191" height="5.6456451" x="0.041112646" y="291.31323" ry="0" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="2.465615" y="277.72061" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8">I</tspan><tspan sodipodi:role="line" x="2.465615" y="283.56769" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689">V</tspan><tspan sodipodi:role="line" x="2.465615" y="289.41479" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691">O</tspan><tspan sodipodi:role="line" x="2.465615" y="295.26187" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693">C</tspan></text> 

  for(let i=0; i<values.length; i++) {
    etd_str += '<rect style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-0" width="28.228191" height="5.6456451" x="0.041112646" y="' + (274.66467 + (6 * i)) + '" ry="0" />' +
    '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="5.465615" y="' + (277.66467 + (6 * i)) + '" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8">' + values[i].label + '</tspan></text>' +
    '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="' + 13.086432 + '" y="' + (277.66467 + (0 * i)) + '" id="galleta_texto1" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="' + 15.086432 + '" y="' + (277.66467 + (6 * i)) + '" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="' + element.id + ':' + values[i].name + '">' +
    "" +
    '</tspan></text>';
  }

  /*
  etd_str += '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.086432" y="277.66467" id="galleta_texto1" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="13.086432" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9">' +
    Math.floor(Math.random() * 10001) +
    '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="23.677027" y="277.66467" id="text4070-1-2-2-22" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="23.677027" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8-8">V/h</tspan><tspan sodipodi:role="line" x="23.677027" y="283.51175" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689-9">K/h</tspan><tspan sodipodi:role="line" x="23.677027" y="289.35886" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691-7">%</tspan><tspan sodipodi:role="line" x="23.677027" y="295.20593" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-3">%L</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="283.57147" id="galleta_texto2" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="283.57147" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9-3">' +
    Math.floor(Math.random() * 10001) +
    '</tspan></text> <text transform="scale(0.99833797,1.0016648)" id="galleta_texto3" y="289.38263" x="13.15081" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" xml:space="preserve"><tspan id="tspan2140" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" y="289.38263" x="13.15081" sodipodi:role="line">' +
    Math.floor(Math.random() * 10001) +
    '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="295.19379" id="galleta_texto4" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="295.19379" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan2150">' +
    Math.floor(Math.random() * 10001) +
    "</tspan></text> </g>";
  */

  etd_str += '</g>';

  return etd_str;
}

export function generateMeteoIcon(element) {
  let meteo_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> <rect class="capa_mapa" style="fill:#c3e6a1;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93" width="28.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" />  class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-0" width="28.228191" height="5.6456451" x="0.041112646" y="280.02197" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-6" width="28.228191" height="5.6456451" x="0.041112646" y="274.37631" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-62" width="28.228191" height="5.6456451" x="0.041112646" y="285.6676" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-61" width="28.228191" height="5.6456451" x="0.041112646" y="291.31323" ry="0" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="2.465615" y="277.72061" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8">I</tspan><tspan sodipodi:role="line" x="2.465615" y="283.56769" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689">V</tspan><tspan sodipodi:role="line" x="2.465615" y="289.41479" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691">O</tspan><tspan sodipodi:role="line" x="2.465615" y="295.26187" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693">C</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)" inkscape:label="#titulo"><tspan sodipodi:role="line" x="13.904327" y="272.36899" style="font-size:3.15464282px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-0">' +
    element.id +
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

/*
export function generateAnemExtIcon(element) {
  let anem_ext_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> <rect class="capa_mapa" style="fill:#c3e6a1;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93" width="28.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" />  class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-0" width="28.228191" height="5.6456451" x="0.041112646" y="280.02197" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-6" width="28.228191" height="5.6456451" x="0.041112646" y="274.37631" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-62" width="28.228191" height="5.6456451" x="0.041112646" y="285.6676" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-61" width="28.228191" height="5.6456451" x="0.041112646" y="291.31323" ry="0" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="2.465615" y="277.72061" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8">I</tspan><tspan sodipodi:role="line" x="2.465615" y="283.56769" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689">V</tspan><tspan sodipodi:role="line" x="2.465615" y="289.41479" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691">O</tspan><tspan sodipodi:role="line" x="2.465615" y="295.26187" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693">C</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)" inkscape:label="#titulo"><tspan sodipodi:role="line" x="13.904327" y="272.36899" style="font-size:3.15464282px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-0">' +
    element.id +
    '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.086432" y="277.66467" id="galleta_texto1" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="13.086432" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9">' +
    Math.floor(Math.random() * 10001) +
    '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="23.677027" y="277.66467" id="text4070-1-2-2-22" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="23.677027" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8-8">V/h</tspan><tspan sodipodi:role="line" x="23.677027" y="283.51175" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689-9">K/h</tspan><tspan sodipodi:role="line" x="23.677027" y="289.35886" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691-7">%</tspan><tspan sodipodi:role="line" x="23.677027" y="295.20593" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-3">%L</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="283.57147" id="galleta_texto2" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="283.57147" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9-3">' +
    Math.floor(Math.random() * 10001) +
    '</tspan></text> <text transform="scale(0.99833797,1.0016648)" id="galleta_texto3" y="289.38263" x="13.15081" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" xml:space="preserve"><tspan id="tspan2140" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" y="289.38263" x="13.15081" sodipodi:role="line">' +
    Math.floor(Math.random() * 10001) +
    '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="295.19379" id="galleta_texto4" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="295.19379" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan2150">' +
    Math.floor(Math.random() * 10001) +
    "</tspan></text> </g>";

  return anem_ext_str;
}
*/

export function generateLuxIcon(element) {
  let lux_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-281.12499)">' +
    '<g transform="matrix(0.2822222,0,0,0.2822222,-48.888674,181.52135)" id="g3652-1">' +
    '<rect ry="2.9559755" y="353.55258" x="185.01654" height="29.999998" width="29.999998" id="' +
    element.id +
    ':IconBackground" style="fill:#ffffff;fill-opacity:1;stroke:none;stroke-width:0.07384499;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<rect y="387.27902" x="173.69635" height="21.42857" width="50.892853" id="' +
    element.id +
    ':ValueBackground" style="fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.93749994;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<text id="text1608-6-1-4-1-9" y="401.78311" x="187.84198" style="font-style:normal;font-weight:normal;font-size:37.49999237px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.93749994" xml:space="preserve">' +
    '<tspan style="font-size:12.49999809px;stroke-width:0.93749994" y="401.78311" x="175.84198" id="' +
    element.id +
    ':Value" sodipodi:role="line">' +
    0 +
    "</tspan>" +
    "</text>" +
    '<text id="text2729-8-9-9-3" y="372.26047" x="188.67026" style="font-style:normal;font-weight:normal;font-size:10.71748638px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26793715" xml:space="preserve">' +
    '<tspan style="font-style:normal;font-variant:normal;font-weight:bold;font-stretch:normal;font-family:sans-serif;-inkscape-font-specification:"sans-serif Bold";stroke-width:0.26793715" y="372.26047" x="189.67026" id="tspan2727-5-1-7-8" sodipodi:role="line">LUX</tspan>' +
    "</text>" +
    "</g>" +
    "</g>";

  return lux_str;
}

export function generateLumIcon(element) {
  let lux_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-281.12499)">' +
    '<g transform="matrix(0.2822222,0,0,0.2822222,-48.888674,181.52135)" id="g3652-1">' +
    '<rect ry="2.9559755" y="353.55258" x="185.01654" height="29.999998" width="29.999998" id="' +
    element.id +
    ':IconBackground" style="fill:#ffffff;fill-opacity:1;stroke:none;stroke-width:0.07384499;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<rect y="387.27902" x="173.69635" height="21.42857" width="50.892853" id="' +
    element.id +
    ':ValueBackground" style="fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.93749994;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<text id="text1608-6-1-4-1-9" y="401.78311" x="187.84198" style="font-style:normal;font-weight:normal;font-size:37.49999237px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.93749994" xml:space="preserve">' +
    '<tspan style="font-size:12.49999809px;stroke-width:0.93749994" y="401.78311" x="175.84198" id="' +
    element.id +
    ':Value" sodipodi:role="line">' +
    0 +
    "</tspan>" +
    "</text>" +
    '<text id="text2729-8-9-9-3" y="372.26047" x="188.67026" style="font-style:normal;font-weight:normal;font-size:10.71748638px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26793715" xml:space="preserve">' +
    '<tspan style="font-style:normal;font-variant:normal;font-weight:bold;font-stretch:normal;font-family:sans-serif;-inkscape-font-specification:"sans-serif Bold";stroke-width:0.26793715" y="372.26047" x="188.07026" id="tspan2727-5-1-7-8" sodipodi:role="line">LUM</tspan>' +
    "</text>" +
    "</g>" +
    "</g>";

  return lux_str;
}

export function generateCoIcon(element) {
  let co_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-281.12499)">' +
    '<g transform="matrix(0.2822222,0,0,0.2822222,-48.888674,181.52135)" id="g3652-1">' +
    '<rect ry="2.9559755" y="353.55258" x="185.01654" height="29.999998" width="29.999998" id="' +
    element.id +
    ':IconBackground" style="fill:#ffffff;fill-opacity:1;stroke:none;stroke-width:0.07384499;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<rect y="387.27902" x="173.69635" height="21.42857" width="50.892853" id="' +
    element.id +
    ':ValueBackground" style="fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.93749994;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<text id="text1608-6-1-4-1-9" y="401.78311" x="187.84198" style="font-style:normal;font-weight:normal;font-size:37.49999237px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.93749994" xml:space="preserve">' +
    '<tspan style="font-size:12.49999809px;stroke-width:0.93749994" y="401.78311" x="175.84198" id="' +
    element.id +
    ':Value" sodipodi:role="line">' +
    0 +
    "</tspan>" +
    "</text>" +
    '<text id="text2729-8-9-9-3" y="372.26047" x="188.67026" style="font-style:normal;font-weight:normal;font-size:10.71748638px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26793715" xml:space="preserve">' +
    '<tspan style="font-style:normal;font-variant:normal;font-weight:bold;font-stretch:normal;font-family:sans-serif;-inkscape-font-specification:"sans-serif Bold";stroke-width:0.26793715" y="372.26047" x="192.67026" id="tspan2727-5-1-7-8" sodipodi:role="line">CO</tspan>' +
    "</text>" +
    "</g>" +
    "</g>";

  return co_str;
}

export function generateNoIcon(element) {
  let no_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-281.12499)">' +
    '<g transform="matrix(0.2822222,0,0,0.2822222,-48.888674,181.52135)" id="g3652-1">' +
    '<rect ry="2.9559755" y="353.55258" x="185.01654" height="29.999998" width="29.999998" id="' +
    element.id +
    ':IconBackground" style="fill:#ffffff;fill-opacity:1;stroke:none;stroke-width:0.07384499;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<rect y="387.27902" x="173.69635" height="21.42857" width="50.892853" id="' +
    element.id +
    ':ValueBackground" style="fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.93749994;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<text id="text1608-6-1-4-1-9" y="401.78311" x="187.84198" style="font-style:normal;font-weight:normal;font-size:37.49999237px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.93749994" xml:space="preserve">' +
    '<tspan style="font-size:12.49999809px;stroke-width:0.93749994" y="401.78311" x="175.84198" id="' +
    element.id +
    ':Value" sodipodi:role="line">' +
    0 +
    "</tspan>" +
    "</text>" +
    '<text id="text2729-8-9-9-3" y="372.26047" x="188.67026" style="font-style:normal;font-weight:normal;font-size:10.71748638px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26793715" xml:space="preserve">' +
    '<tspan style="font-style:normal;font-variant:normal;font-weight:bold;font-stretch:normal;font-family:sans-serif;-inkscape-font-specification:"sans-serif Bold";stroke-width:0.26793715" y="372.26047" x="192.67026" id="tspan2727-5-1-7-8" sodipodi:role="line">NO</tspan>' +
    "</text>" +
    "</g>" +
    "</g>";

  return no_str;
}

export function generateOpacIcon(element) {
  let op_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-281.12499)">' +
    '<g transform="matrix(0.2822222,0,0,0.2822222,-48.888674,181.52135)" id="g3652-1">' +
    '<rect ry="2.9559755" y="353.55258" x="185.01654" height="29.999998" width="29.999998" id="' +
    element.id +
    ':IconBackground" style="fill:#ffffff;fill-opacity:1;stroke:none;stroke-width:0.07384499;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<rect y="387.27902" x="173.69635" height="21.42857" width="50.892853" id="' +
    element.id +
    ':ValueBackground" style="fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.93749994;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    '<text id="text1608-6-1-4-1-9" y="401.78311" x="187.84198" style="font-style:normal;font-weight:normal;font-size:37.49999237px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.93749994" xml:space="preserve">' +
    '<tspan style="font-size:12.49999809px;stroke-width:0.93749994" y="401.78311" x="175.84198" id="' +
    element.id +
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

export function generateAnemIntIcon(element) {
  let anem_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-278.47915)">' +
    '<g transform="matrix(0.2822222,0,0,0.2822222,-223.71911,238.10826)" id="g6408">' +
    '<g id="' +
    element.id +
    ':IconBackground" transform="matrix(0.09080811,0,0,0.09080811,795.71435,140.59232)">' +
    '<path d="m 66.127973,170.11744 c -0.76605,-0.25909 -3.085092,-0.91873 -5.153427,-1.46587 -2.068335,-0.54714 -3.76061,-1.18596 -3.76061,-1.41961 0,-0.23364 -0.501414,-0.4248 -1.114254,-0.4248 -0.61284,0 -1.114255,-0.19931 -1.114255,-0.44291 0,-0.24359 -1.128183,-0.90814 -2.507073,-1.47677 -1.37889,-0.56863 -2.507073,-1.2468 -2.507073,-1.50704 0,-0.26024 -0.376061,-0.47317 -0.835691,-0.47317 -0.45963,0 -0.835691,-0.19666 -0.835691,-0.43701 0,-0.24035 -0.689445,-0.85714 -1.5321,-1.37064 -3.561638,-2.17039 -11.010642,-10.33945 -13.553197,-14.86332 -0.702116,-1.24925 -1.481117,-2.27135 -1.731112,-2.27135 -0.249995,0 -0.454537,-0.50141 -0.454537,-1.11425 0,-0.61284 -0.250708,-1.11426 -0.557128,-1.11426 -0.30642,0 -0.557127,-0.2884 -0.557127,-0.64088 0,-0.56837 -2.013197,-5.22724 -3.391182,-7.84775 -0.27979,-0.53208 -0.508709,-1.42209 -0.508709,-1.9778 0,-0.55571 -0.228712,-1.24856 -0.508248,-1.53966 -0.470023,-0.48947 -0.767056,-1.88647 -2.36323,-11.11469 -0.848507,-4.90561 -1.199013,-17.00947 -0.531652,-18.35925 0.310062,-0.627116 0.675291,-2.393747 0.81162,-3.925847 0.13633,-1.5321 0.662658,-4.540588 1.169618,-6.685528 0.506961,-2.14494 1.062734,-4.589336 1.235053,-5.431991 0.172318,-0.842655 0.535558,-1.5321 0.8072,-1.5321 0.271641,0 0.493893,-0.379463 0.493893,-0.843251 0,-0.463788 0.222559,-1.278586 0.494575,-1.810664 0.272016,-0.532077 1.199689,-2.471656 2.061494,-4.310177 0.861806,-1.83852 2.832936,-5.22231 4.38029,-7.519533 3.053568,-4.533371 11.185424,-13.056369 14.095197,-14.773175 0.995866,-0.587574 1.810664,-1.26497 1.810664,-1.505323 0,-0.240354 0.501415,-0.437007 1.114255,-0.437007 0.61284,0 1.114254,-0.195516 1.114254,-0.43448 0,-0.238965 1.253537,-0.920924 2.785637,-1.515465 1.5321,-0.594541 2.785637,-1.276501 2.785637,-1.515465 0,-0.238965 0.504816,-0.434481 1.121814,-0.434481 0.616998,0 1.55715,-0.217101 2.089227,-0.482448 3.95998,-1.974837 12.928759,-2.785752 19.631183,-1.774959 6.90487,1.041326 7.79978,1.243401 7.79978,1.761233 0,0.272896 0.45892,0.496174 1.01983,0.496174 0.56091,0 2.25318,0.625719 3.76061,1.390487 1.50742,0.764767 3.05416,1.391536 3.43719,1.392818 0.38302,0.0013 0.6964,0.253039 0.6964,0.559459 0,0.30642 0.35517,0.557127 0.78927,0.557127 0.43409,0 0.87283,0.220187 0.97497,0.489304 0.10214,0.269118 1.188542,1.164597 2.414222,1.989956 2.20083,1.482013 7.29504,6.174799 9.48418,8.736822 2.1542,2.521146 6.39395,8.707144 6.39395,9.329097 0,0.344113 0.25071,0.625659 0.55713,0.625659 0.30642,0 0.55712,0.243854 0.55712,0.541898 0,0.298044 0.65272,1.990318 1.45047,3.76061 0.79776,1.770291 1.67524,3.782802 1.94995,4.472247 1.19201,2.991596 1.76926,4.371075 2.15648,5.153428 0.34743,0.701954 2.56605,0.859566 13.86362,0.984878 l 13.44999,0.149186 v 1.628598 c 0,0.895728 -0.2507,1.783542 -0.55712,1.97292 -0.30642,0.189378 -0.55713,1.156862 -0.55713,2.149964 0,0.993102 -0.23818,2.043825 -0.5293,2.334941 -0.86201,0.862015 -1.27905,14.535408 -0.60237,19.750138 1.01836,7.84779 1.22737,8.91403 1.74732,8.91403 0.27423,0 0.4986,1.00283 0.4986,2.22851 v 2.22851 h -13.92818 -13.92818 v 1.39282 c 0,0.76605 -0.25071,1.39282 -0.55713,1.39282 -0.30642,0 -0.55713,0.45997 -0.55713,1.02217 0,0.56219 -0.87747,2.82518 -1.94994,5.02887 -1.07247,2.20369 -1.94995,4.25078 -1.94995,4.5491 0,0.29833 -0.1895,0.54241 -0.42112,0.54241 -0.23162,0 -0.78149,0.81479 -1.22192,1.81066 -0.44044,0.99585 -1.37912,2.43486 -2.08595,3.19778 -0.70683,0.76292 -1.28515,1.59245 -1.28515,1.84339 0,1.06085 -10.95236,12.09048 -12.00577,12.09048 -0.16369,0 -0.83956,0.50489 -1.501912,1.12197 -1.6771,1.56245 -8.39812,4.74248 -12.39874,5.86643 -1.83852,0.51652 -3.91865,1.16109 -4.62252,1.43238 -1.60077,0.61699 -14.943645,0.54059 -16.826885,-0.0964 z" id="path3400-0" inkscape:connector-curvature="0" />' +
    '<path d="m 225.46639,170.16428 c -0.45963,-0.30493 -1.46246,-0.55616 -2.22851,-0.55827 -1.86299,-0.005 -9.14394,-1.85367 -9.37831,-2.381 -0.10214,-0.22982 -0.60356,-0.41493 -1.11426,-0.41135 -1.18287,0.008 -11.16197,-4.95375 -12.59399,-6.26227 -0.59401,-0.54279 -1.2451,-0.98689 -1.44685,-0.98689 -0.20176,0 -0.87833,-0.43873 -1.50349,-0.97497 -0.62516,-0.53623 -2.06912,-1.71347 -3.2088,-2.61607 -3.58901,-2.84243 -9.29823,-9.2168 -11.70316,-13.06661 -0.30642,-0.49051 -0.71137,-1.0172 -0.89988,-1.17041 -0.18852,-0.15321 -0.5188,-0.84265 -0.73395,-1.5321 -0.21516,-0.68944 -0.62502,-1.25354 -0.91079,-1.25354 -0.28577,0 -0.51958,-0.48052 -0.51958,-1.06782 0,-0.58731 -0.19985,-1.1514 -0.4441,-1.25354 -0.66925,-0.27986 -2.61608,-4.77394 -3.70064,-8.54262 -0.5291,-1.83852 -1.19187,-3.7781 -1.47283,-4.31017 -0.28096,-0.53208 -0.51083,-1.6728 -0.51083,-2.53493 0,-0.86214 -0.26951,-1.83703 -0.59891,-2.16643 -0.81961,-0.81961 -0.81961,-21.270492 0,-22.090102 0.3294,-0.329402 0.59891,-1.429648 0.59891,-2.444992 0,-1.015344 0.22627,-2.281416 0.50283,-2.813493 0.27655,-0.532077 0.82356,-2.095596 1.21557,-3.474486 1.56371,-5.500296 7.00743,-15.549345 10.92501,-20.167433 2.03754,-2.401872 7.20009,-7.523859 9.71521,-9.638878 2.18182,-1.834734 2.67769,-2.168746 7.16913,-4.829028 4.66938,-2.765679 5.06668,-2.960208 8.35691,-4.091722 1.68531,-0.579581 3.30237,-1.25391 3.59347,-1.498508 0.2911,-0.244599 1.1059,-0.444725 1.81066,-0.444725 0.70477,0 1.28139,-0.218102 1.28139,-0.484671 0,-0.26657 1.06551,-0.642084 2.3678,-0.834477 1.30228,-0.192393 3.74668,-0.582756 5.43199,-0.867475 3.94602,-0.666645 14.43918,-0.666645 18.3852,0 1.68531,0.284719 4.1297,0.675082 5.43199,0.867475 1.30228,0.192393 2.36779,0.567907 2.36779,0.834477 0,0.266569 0.48962,0.484671 1.08804,0.484671 0.59842,0 1.78928,0.28594 2.64636,0.635421 0.85707,0.349482 2.43579,0.988395 3.50826,1.419808 1.07247,0.431412 2.38527,1.022948 2.91735,1.314524 0.53208,0.291576 1.34688,0.530138 1.81067,0.530138 0.46378,0 0.84325,0.219973 0.84325,0.488828 0,0.268854 1.37829,1.209007 3.06287,2.089227 1.68459,0.88022 3.06348,1.788431 3.0642,2.018246 5.6e-4,0.229815 0.28531,0.417845 0.63239,0.417845 0.34708,0 1.03653,0.451631 1.5321,1.003625 0.49557,0.551993 1.90387,1.742853 3.12955,2.646355 1.22568,0.903501 2.48416,1.893437 2.79663,2.199857 1.55657,1.526445 6.99115,8.249412 7.86686,9.731871 0.54882,0.929081 1.19451,1.689239 1.43487,1.689239 0.24035,0 0.437,0.355168 0.437,0.789264 0,0.434094 0.18803,0.872832 0.41785,0.974972 0.22981,0.10214 1.16997,1.815307 2.08923,3.807037 0.91926,1.99173 1.79879,3.746681 1.95452,3.899891 0.55235,0.543417 2.2294,5.868628 2.22669,7.070493 -0.002,0.671362 0.24795,1.375602 0.55437,1.56498 0.30642,0.189379 0.55713,1.156862 0.55713,2.149964 0,0.993102 0.23306,2.043812 0.51792,2.334911 0.41642,0.425544 1.09169,7.472921 1.09169,11.393251 0,3.92034 -0.67527,10.96771 -1.09169,11.39326 -0.28486,0.2911 -0.51792,1.15077 -0.51792,1.91039 0,1.32494 -0.25773,2.30288 -2.0733,7.86719 -0.69044,2.11601 -2.04415,5.03452 -5.44075,11.72987 -0.30248,0.59623 -0.74122,1.08406 -0.97498,1.08406 -0.23376,0 -0.42501,0.24854 -0.42501,0.55231 0,1.55092 -8.71569,11.37083 -12.79109,14.41164 -1.08453,0.80921 -2.45809,1.91003 -3.05236,2.44627 -0.59426,0.53623 -1.266,0.97497 -1.49275,0.97497 -0.22676,0 -0.74358,0.32907 -1.14849,0.73127 -0.64694,0.64259 -9.97892,5.33314 -12.993,6.53069 -1.40539,0.55839 -5.3008,1.57663 -8.91404,2.33009 -3.84194,0.80115 -20.39681,1.14893 -21.4494,0.4506 z" id="path3402-3" inkscape:connector-curvature="0" />' +
    '<path d="m 386.47618,170.12241 c -0.76605,-0.25636 -2.89706,-0.88212 -4.73558,-1.39057 -4.15559,-1.14924 -6.40697,-2.06913 -6.40697,-2.61781 0,-0.23162 -0.50141,-0.42112 -1.11425,-0.42112 -0.61284,0 -1.11426,-0.25071 -1.11426,-0.55713 0,-0.30642 -0.35682,-0.55713 -0.79294,-0.55713 -0.43612,0 -1.53782,-0.62677 -2.44822,-1.39282 -0.9104,-0.76605 -1.87768,-1.39282 -2.14951,-1.39282 -0.63399,0 -11.88027,-11.5043 -11.88027,-12.15284 0,-0.26828 -0.60264,-1.18911 -1.3392,-2.0463 -1.88636,-2.1953 -6.79637,-12.20202 -8.13636,-16.58213 l -0.63915,-2.08923 H 331.8345 317.94952 V 126.694 c 0,-1.22568 0.25071,-2.22851 0.55713,-2.22851 0.30642,0 0.55713,-0.86267 0.55713,-1.91706 0,-1.05439 0.21736,-2.0514 0.48303,-2.21559 0.57223,-0.35367 1.32507,-7.46205 1.33244,-12.58117 0.007,-4.89972 -0.74298,-11.659719 -1.33421,-12.025119 -0.2647,-0.163592 -0.48126,-1.285472 -0.48126,-2.493068 0,-1.207597 -0.25071,-2.19563 -0.55713,-2.19563 -0.30642,0 -0.55713,-0.752121 -0.55713,-1.671381 V 87.69509 h 13.28471 c 14.78954,0 14.57813,0.05323 15.41618,-3.881605 0.22628,-1.062412 0.60491,-2.315949 0.8414,-2.785637 0.23649,-0.469687 1.62564,-3.298373 3.087,-6.285968 1.46136,-2.987596 2.86559,-5.431992 3.1205,-5.431992 0.25492,0 0.46349,-0.261649 0.46349,-0.581442 0,-0.319794 0.62676,-1.257384 1.39281,-2.083535 0.76605,-0.82615 1.39282,-1.69943 1.39282,-1.940621 0,-0.922889 7.68593,-8.4251 11.14255,-10.876204 1.99173,-1.412345 3.7049,-2.768742 3.80704,-3.014214 0.10214,-0.245472 0.5553,-0.446312 1.00703,-0.446312 0.45173,0 1.94398,-0.626769 3.31611,-1.392819 1.37213,-0.76605 2.98729,-1.392818 3.58926,-1.392818 0.60197,0 1.09449,-0.223278 1.09449,-0.496174 0,-0.583696 2.19547,-1.007737 9.63051,-1.860065 4.12022,-0.47233 6.56994,-0.463152 10.02829,0.03757 6.82151,0.987661 8.17726,1.277959 8.51417,1.823098 0.16846,0.272564 0.7929,0.495571 1.38766,0.495571 0.59475,0 1.08137,0.250707 1.08137,0.557127 0,0.30642 0.30978,0.557128 0.68841,0.557128 0.66878,0 6.55231,2.763565 7.11137,3.340297 0.15321,0.158053 1.28139,1.028978 2.50707,1.935387 1.22568,0.90641 2.54054,1.951229 2.92191,2.32182 0.38138,0.370592 1.25886,1.131801 1.94995,1.691577 3.27851,2.655524 11.5634,13.571269 11.5634,15.235338 0,0.300471 0.18951,0.54631 0.42113,0.54631 0.43834,0 1.08233,1.332029 2.69349,5.571273 0.52406,1.37889 1.16584,2.745245 1.42618,3.036344 0.26034,0.291099 0.47335,1.091101 0.47335,1.777784 0,0.686682 0.2507,1.403458 0.55712,1.592836 0.30642,0.189378 0.55713,0.834344 0.55713,1.433258 0,0.598913 0.23989,1.52427 0.53309,2.056347 0.96262,1.746917 1.89152,10.855931 1.88826,18.516921 -0.003,7.76236 -1.02514,17.70498 -1.91528,18.63591 -0.27834,0.2911 -0.50607,1.23125 -0.50607,2.08923 0,0.85798 -0.25071,1.55996 -0.55713,1.55996 -0.30642,0 -0.55712,0.50482 -0.55712,1.12181 0,0.617 -0.22964,1.55715 -0.51029,2.08923 -0.28067,0.53208 -0.87388,1.84489 -1.31826,2.91736 -2.00256,4.83299 -2.36889,5.57127 -2.76447,5.57127 -0.23162,0 -0.42113,0.50142 -0.42113,1.11426 0,0.61284 -0.1937,1.11425 -0.43044,1.11425 -0.23673,0 -0.92141,0.94014 -1.52149,2.08922 -2.26901,4.34483 -9.17697,11.82024 -14.67282,15.87813 -1.97127,1.45549 -3.90503,2.64635 -4.29725,2.64635 -0.39221,0 -0.79668,0.18803 -0.89882,0.41785 -0.32427,0.7296 -5.07837,2.82923 -8.54262,3.77282 -1.83852,0.50077 -4.11268,1.14162 -5.0537,1.42411 -2.09091,0.62769 -14.46803,0.57408 -16.3957,-0.071 z" id="path3404-9" inkscape:connector-curvature="0" />' +
    '<path d="m 102.61981,469.32729 c -0.18937,-0.30642 -1.56826,-0.55713 -3.064202,-0.55713 -1.49593,0 -2.87482,-0.2507 -3.0642,-0.55712 -0.18937,-0.30642 -0.9415,-0.55713 -1.67138,-0.55713 -0.72988,0 -1.482,-0.25071 -1.67138,-0.55713 -0.18938,-0.30642 -0.85494,-0.55713 -1.47902,-0.55713 -0.98605,0 -4.11804,-1.41691 -4.92794,-2.22939 -0.15321,-0.1537 -1.02721,-0.65512 -1.94221,-1.11426 -0.915,-0.45914 -2.29389,-1.32696 -3.0642,-1.92848 -2.53082,-1.97629 -9.269641,-9.17205 -10.058649,-10.74071 -0.425106,-0.84517 -1.18161,-2.16343 -1.681121,-2.92948 -0.499511,-0.76605 -1.353618,-2.77171 -1.898015,-4.45702 -0.544398,-1.68531 -1.210248,-3.49954 -1.479667,-4.03162 -0.269419,-0.53207 -0.489853,-1.58278 -0.489853,-2.33491 0,-0.75212 -0.250707,-1.52244 -0.557127,-1.71182 -0.770479,-0.47618 -0.770479,-143.81979 0,-144.29597 0.30642,-0.18938 0.557127,-1.44292 0.557127,-2.78564 0,-1.34272 0.250708,-2.59626 0.557128,-2.78564 0.30642,-0.18937 0.557127,-1.06685 0.557127,-1.94994 0,-0.88309 0.228893,-1.74709 0.50865,-1.91999 0.279758,-0.17289 0.677633,-1.31456 0.884168,-2.53703 0.206535,-1.22247 0.604411,-2.36414 0.884168,-2.53704 0.279758,-0.17289 0.50865,-0.80098 0.50865,-1.39573 0,-0.59476 0.250708,-1.08138 0.557128,-1.08138 0.30642,0 0.557127,-0.50141 0.557127,-1.11425 0,-0.61284 0.194519,-1.11426 0.432264,-1.11426 0.237746,0 0.927191,-1.1258 1.5321,-2.50179 0.60491,-1.37599 1.287867,-2.50417 1.517682,-2.50708 0.229815,-0.003 0.417845,-0.25318 0.417845,-0.55618 0,-2.01948 11.327838,-13.97209 16.713818,-17.63564 4.15724,-2.82777 9.416462,-5.76993 10.313952,-5.76993 0.40466,0 0.81931,-0.19432 0.92145,-0.43183 0.2466,-0.57343 8.91694,-3.46806 10.38794,-3.46806 0.63422,0 1.30807,-0.2507 1.49745,-0.55712 0.18937,-0.30642 1.56826,-0.55713 3.0642,-0.55713 1.49593,0 2.87482,-0.25071 3.0642,-0.55713 0.22509,-0.36421 16.44103,-0.55713 46.83157,-0.55713 h 46.48725 v -22.79179 -22.79179 l 2.92492,0.77865 c 1.6087,0.42826 3.72492,0.78163 4.7027,0.78526 0.97778,0.004 1.93273,0.25732 2.12211,0.56374 0.46376,0.75039 20.70707,0.75039 21.17084,0 0.18937,-0.30642 1.15236,-0.55712 2.13997,-0.55712 0.98761,0 3.10383,-0.33934 4.7027,-0.75408 l 2.90705,-0.75408 0.008,21.50707 c 0.004,11.82889 0.15878,22.07116 0.34355,22.76061 l 0.33593,1.25353 h 46.14371 c 30.1615,0 46.26297,0.19297 46.48803,0.55713 0.18938,0.30642 1.56827,0.55713 3.0642,0.55713 1.49593,0 2.87482,0.25071 3.0642,0.55713 0.18938,0.30642 0.87478,0.55712 1.5231,0.55712 2.91561,0 12.2499,3.76258 17.56933,7.08206 1.61466,1.00759 3.18158,1.83198 3.48205,1.83198 0.30047,0 0.54631,0.19666 0.54631,0.43701 0,0.24035 0.76441,0.88855 1.69869,1.44045 1.59697,0.94335 12.22949,11.32368 12.22949,11.93941 0,0.15659 0.62677,1.10644 1.39282,2.11078 0.76605,1.00435 1.39281,2.09351 1.39281,2.42038 0,0.32687 0.25071,0.5943 0.55713,0.5943 0.30642,0 0.55713,0.37606 0.55713,0.83569 0,0.45963 0.18843,0.83569 0.41874,0.83569 0.36241,0 2.24397,3.77872 4.16121,8.35691 0.3208,0.76605 0.79596,1.89423 1.0559,2.50707 0.49221,1.16042 1.27014,4.16542 2.30765,8.91404 0.90451,4.13985 0.91017,146.36818 0.006,149.03156 -0.36411,1.07247 -0.97202,3.20348 -1.35092,4.73558 -0.37889,1.5321 -0.84778,2.91099 -1.04198,3.0642 -0.1942,0.15321 -0.70759,1.09336 -1.14086,2.08923 -0.43328,0.99586 -0.95891,1.81066 -1.16806,1.81066 -0.20915,0 -0.46134,0.38981 -0.56041,0.86625 -0.2838,1.36479 -6.60722,8.77025 -9.03788,10.58441 -4.47587,3.34064 -9.1997,5.92478 -12.59165,6.88819 -1.37889,0.39164 -2.94241,0.93835 -3.47449,1.2149 -0.53208,0.27656 -2.0842,0.50283 -3.44916,0.50283 -1.36497,0 -2.6367,0.25071 -2.82608,0.55713 -0.47711,0.77198 -263.60123,0.77198 -264.07835,0 z M 346.64158,364.03023 c 0.18938,-0.30642 0.81767,-0.55713 1.39621,-0.55713 1.70002,0 6.21547,-2.66065 8.79595,-5.18286 2.56059,-2.50277 5.12884,-6.87268 5.12884,-8.7268 0,-0.62303 0.25071,-1.13278 0.55713,-1.13278 0.36383,0 0.55712,-9.47116 0.55712,-27.29923 0,-17.82808 -0.19329,-27.29924 -0.55712,-27.29924 -0.30642,0 -0.55713,-0.51141 -0.55713,-1.13645 0,-3.66497 -8.13486,-12.79174 -11.40151,-12.79174 -0.47041,0 -0.85529,-0.2507 -0.85529,-0.55712 0,-0.30642 -0.73733,-0.55713 -1.6385,-0.55713 -0.90118,0 -1.79345,-0.25071 -1.98283,-0.55713 -0.47691,-0.77165 -222.37402,-0.77165 -222.85093,0 -0.18937,0.30642 -1.08165,0.55713 -1.98282,0.55713 -0.90118,0 -1.6385,0.25071 -1.6385,0.55713 0,0.30642 -0.41508,0.55712 -0.92239,0.55712 -3.0754,0 -11.33441,9.35149 -11.33441,12.8337 0,0.60197 -0.25071,1.09449 -0.55713,1.09449 -0.36384,0 -0.55713,9.47116 -0.55713,27.29924 0,17.82807 0.19329,27.29923 0.55713,27.29923 0.30642,0 0.55713,0.49252 0.55713,1.09449 0,4.48931 9.63183,13.94795 14.20334,13.94795 0.73176,0 1.48541,0.25071 1.67478,0.55713 0.47691,0.77165 222.93115,0.77165 223.40806,0 z" id="path3406-1" inkscape:connector-curvature="0" />' +
    "</g>" +
    '<g id="g1655">' +
    '<rect style="fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.93750006;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="' +
    element.id +
    ':ValueBackground" width="50.892857" height="21.428572" x="793.17432" y="186.77423" />' +
    '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:37.5px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.93750006" x="801.69501" y="201.27823" id="text1608">' +
    '<tspan sodipodi:role="line" id="' +
    element.id +
    ':Value" x="796.69501" y="201.27823" style="font-size:12.5px;stroke-width:0.93750006">' +
    0 +
    " m/s</tspan>" +
    "</text>" +
    "</g>" +
    "</g>";

  return anem_str;
}

export function generateAnemExtIcon(element) {
  let anem_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-278.47915)">' +
    '<g transform="matrix(0.2822222,0,0,0.2822222,-223.71911,238.10826)" id="g6408">' +
    '<g id="' +
    element.id +
    ':IconBackground" transform="matrix(0.09080811,0,0,0.09080811,795.71435,140.59232)">' +
    '<path d="m 66.127973,170.11744 c -0.76605,-0.25909 -3.085092,-0.91873 -5.153427,-1.46587 -2.068335,-0.54714 -3.76061,-1.18596 -3.76061,-1.41961 0,-0.23364 -0.501414,-0.4248 -1.114254,-0.4248 -0.61284,0 -1.114255,-0.19931 -1.114255,-0.44291 0,-0.24359 -1.128183,-0.90814 -2.507073,-1.47677 -1.37889,-0.56863 -2.507073,-1.2468 -2.507073,-1.50704 0,-0.26024 -0.376061,-0.47317 -0.835691,-0.47317 -0.45963,0 -0.835691,-0.19666 -0.835691,-0.43701 0,-0.24035 -0.689445,-0.85714 -1.5321,-1.37064 -3.561638,-2.17039 -11.010642,-10.33945 -13.553197,-14.86332 -0.702116,-1.24925 -1.481117,-2.27135 -1.731112,-2.27135 -0.249995,0 -0.454537,-0.50141 -0.454537,-1.11425 0,-0.61284 -0.250708,-1.11426 -0.557128,-1.11426 -0.30642,0 -0.557127,-0.2884 -0.557127,-0.64088 0,-0.56837 -2.013197,-5.22724 -3.391182,-7.84775 -0.27979,-0.53208 -0.508709,-1.42209 -0.508709,-1.9778 0,-0.55571 -0.228712,-1.24856 -0.508248,-1.53966 -0.470023,-0.48947 -0.767056,-1.88647 -2.36323,-11.11469 -0.848507,-4.90561 -1.199013,-17.00947 -0.531652,-18.35925 0.310062,-0.627116 0.675291,-2.393747 0.81162,-3.925847 0.13633,-1.5321 0.662658,-4.540588 1.169618,-6.685528 0.506961,-2.14494 1.062734,-4.589336 1.235053,-5.431991 0.172318,-0.842655 0.535558,-1.5321 0.8072,-1.5321 0.271641,0 0.493893,-0.379463 0.493893,-0.843251 0,-0.463788 0.222559,-1.278586 0.494575,-1.810664 0.272016,-0.532077 1.199689,-2.471656 2.061494,-4.310177 0.861806,-1.83852 2.832936,-5.22231 4.38029,-7.519533 3.053568,-4.533371 11.185424,-13.056369 14.095197,-14.773175 0.995866,-0.587574 1.810664,-1.26497 1.810664,-1.505323 0,-0.240354 0.501415,-0.437007 1.114255,-0.437007 0.61284,0 1.114254,-0.195516 1.114254,-0.43448 0,-0.238965 1.253537,-0.920924 2.785637,-1.515465 1.5321,-0.594541 2.785637,-1.276501 2.785637,-1.515465 0,-0.238965 0.504816,-0.434481 1.121814,-0.434481 0.616998,0 1.55715,-0.217101 2.089227,-0.482448 3.95998,-1.974837 12.928759,-2.785752 19.631183,-1.774959 6.90487,1.041326 7.79978,1.243401 7.79978,1.761233 0,0.272896 0.45892,0.496174 1.01983,0.496174 0.56091,0 2.25318,0.625719 3.76061,1.390487 1.50742,0.764767 3.05416,1.391536 3.43719,1.392818 0.38302,0.0013 0.6964,0.253039 0.6964,0.559459 0,0.30642 0.35517,0.557127 0.78927,0.557127 0.43409,0 0.87283,0.220187 0.97497,0.489304 0.10214,0.269118 1.188542,1.164597 2.414222,1.989956 2.20083,1.482013 7.29504,6.174799 9.48418,8.736822 2.1542,2.521146 6.39395,8.707144 6.39395,9.329097 0,0.344113 0.25071,0.625659 0.55713,0.625659 0.30642,0 0.55712,0.243854 0.55712,0.541898 0,0.298044 0.65272,1.990318 1.45047,3.76061 0.79776,1.770291 1.67524,3.782802 1.94995,4.472247 1.19201,2.991596 1.76926,4.371075 2.15648,5.153428 0.34743,0.701954 2.56605,0.859566 13.86362,0.984878 l 13.44999,0.149186 v 1.628598 c 0,0.895728 -0.2507,1.783542 -0.55712,1.97292 -0.30642,0.189378 -0.55713,1.156862 -0.55713,2.149964 0,0.993102 -0.23818,2.043825 -0.5293,2.334941 -0.86201,0.862015 -1.27905,14.535408 -0.60237,19.750138 1.01836,7.84779 1.22737,8.91403 1.74732,8.91403 0.27423,0 0.4986,1.00283 0.4986,2.22851 v 2.22851 h -13.92818 -13.92818 v 1.39282 c 0,0.76605 -0.25071,1.39282 -0.55713,1.39282 -0.30642,0 -0.55713,0.45997 -0.55713,1.02217 0,0.56219 -0.87747,2.82518 -1.94994,5.02887 -1.07247,2.20369 -1.94995,4.25078 -1.94995,4.5491 0,0.29833 -0.1895,0.54241 -0.42112,0.54241 -0.23162,0 -0.78149,0.81479 -1.22192,1.81066 -0.44044,0.99585 -1.37912,2.43486 -2.08595,3.19778 -0.70683,0.76292 -1.28515,1.59245 -1.28515,1.84339 0,1.06085 -10.95236,12.09048 -12.00577,12.09048 -0.16369,0 -0.83956,0.50489 -1.501912,1.12197 -1.6771,1.56245 -8.39812,4.74248 -12.39874,5.86643 -1.83852,0.51652 -3.91865,1.16109 -4.62252,1.43238 -1.60077,0.61699 -14.943645,0.54059 -16.826885,-0.0964 z" id="path3400-0" inkscape:connector-curvature="0" />' +
    '<path d="m 225.46639,170.16428 c -0.45963,-0.30493 -1.46246,-0.55616 -2.22851,-0.55827 -1.86299,-0.005 -9.14394,-1.85367 -9.37831,-2.381 -0.10214,-0.22982 -0.60356,-0.41493 -1.11426,-0.41135 -1.18287,0.008 -11.16197,-4.95375 -12.59399,-6.26227 -0.59401,-0.54279 -1.2451,-0.98689 -1.44685,-0.98689 -0.20176,0 -0.87833,-0.43873 -1.50349,-0.97497 -0.62516,-0.53623 -2.06912,-1.71347 -3.2088,-2.61607 -3.58901,-2.84243 -9.29823,-9.2168 -11.70316,-13.06661 -0.30642,-0.49051 -0.71137,-1.0172 -0.89988,-1.17041 -0.18852,-0.15321 -0.5188,-0.84265 -0.73395,-1.5321 -0.21516,-0.68944 -0.62502,-1.25354 -0.91079,-1.25354 -0.28577,0 -0.51958,-0.48052 -0.51958,-1.06782 0,-0.58731 -0.19985,-1.1514 -0.4441,-1.25354 -0.66925,-0.27986 -2.61608,-4.77394 -3.70064,-8.54262 -0.5291,-1.83852 -1.19187,-3.7781 -1.47283,-4.31017 -0.28096,-0.53208 -0.51083,-1.6728 -0.51083,-2.53493 0,-0.86214 -0.26951,-1.83703 -0.59891,-2.16643 -0.81961,-0.81961 -0.81961,-21.270492 0,-22.090102 0.3294,-0.329402 0.59891,-1.429648 0.59891,-2.444992 0,-1.015344 0.22627,-2.281416 0.50283,-2.813493 0.27655,-0.532077 0.82356,-2.095596 1.21557,-3.474486 1.56371,-5.500296 7.00743,-15.549345 10.92501,-20.167433 2.03754,-2.401872 7.20009,-7.523859 9.71521,-9.638878 2.18182,-1.834734 2.67769,-2.168746 7.16913,-4.829028 4.66938,-2.765679 5.06668,-2.960208 8.35691,-4.091722 1.68531,-0.579581 3.30237,-1.25391 3.59347,-1.498508 0.2911,-0.244599 1.1059,-0.444725 1.81066,-0.444725 0.70477,0 1.28139,-0.218102 1.28139,-0.484671 0,-0.26657 1.06551,-0.642084 2.3678,-0.834477 1.30228,-0.192393 3.74668,-0.582756 5.43199,-0.867475 3.94602,-0.666645 14.43918,-0.666645 18.3852,0 1.68531,0.284719 4.1297,0.675082 5.43199,0.867475 1.30228,0.192393 2.36779,0.567907 2.36779,0.834477 0,0.266569 0.48962,0.484671 1.08804,0.484671 0.59842,0 1.78928,0.28594 2.64636,0.635421 0.85707,0.349482 2.43579,0.988395 3.50826,1.419808 1.07247,0.431412 2.38527,1.022948 2.91735,1.314524 0.53208,0.291576 1.34688,0.530138 1.81067,0.530138 0.46378,0 0.84325,0.219973 0.84325,0.488828 0,0.268854 1.37829,1.209007 3.06287,2.089227 1.68459,0.88022 3.06348,1.788431 3.0642,2.018246 5.6e-4,0.229815 0.28531,0.417845 0.63239,0.417845 0.34708,0 1.03653,0.451631 1.5321,1.003625 0.49557,0.551993 1.90387,1.742853 3.12955,2.646355 1.22568,0.903501 2.48416,1.893437 2.79663,2.199857 1.55657,1.526445 6.99115,8.249412 7.86686,9.731871 0.54882,0.929081 1.19451,1.689239 1.43487,1.689239 0.24035,0 0.437,0.355168 0.437,0.789264 0,0.434094 0.18803,0.872832 0.41785,0.974972 0.22981,0.10214 1.16997,1.815307 2.08923,3.807037 0.91926,1.99173 1.79879,3.746681 1.95452,3.899891 0.55235,0.543417 2.2294,5.868628 2.22669,7.070493 -0.002,0.671362 0.24795,1.375602 0.55437,1.56498 0.30642,0.189379 0.55713,1.156862 0.55713,2.149964 0,0.993102 0.23306,2.043812 0.51792,2.334911 0.41642,0.425544 1.09169,7.472921 1.09169,11.393251 0,3.92034 -0.67527,10.96771 -1.09169,11.39326 -0.28486,0.2911 -0.51792,1.15077 -0.51792,1.91039 0,1.32494 -0.25773,2.30288 -2.0733,7.86719 -0.69044,2.11601 -2.04415,5.03452 -5.44075,11.72987 -0.30248,0.59623 -0.74122,1.08406 -0.97498,1.08406 -0.23376,0 -0.42501,0.24854 -0.42501,0.55231 0,1.55092 -8.71569,11.37083 -12.79109,14.41164 -1.08453,0.80921 -2.45809,1.91003 -3.05236,2.44627 -0.59426,0.53623 -1.266,0.97497 -1.49275,0.97497 -0.22676,0 -0.74358,0.32907 -1.14849,0.73127 -0.64694,0.64259 -9.97892,5.33314 -12.993,6.53069 -1.40539,0.55839 -5.3008,1.57663 -8.91404,2.33009 -3.84194,0.80115 -20.39681,1.14893 -21.4494,0.4506 z" id="path3402-3" inkscape:connector-curvature="0" />' +
    '<path d="m 386.47618,170.12241 c -0.76605,-0.25636 -2.89706,-0.88212 -4.73558,-1.39057 -4.15559,-1.14924 -6.40697,-2.06913 -6.40697,-2.61781 0,-0.23162 -0.50141,-0.42112 -1.11425,-0.42112 -0.61284,0 -1.11426,-0.25071 -1.11426,-0.55713 0,-0.30642 -0.35682,-0.55713 -0.79294,-0.55713 -0.43612,0 -1.53782,-0.62677 -2.44822,-1.39282 -0.9104,-0.76605 -1.87768,-1.39282 -2.14951,-1.39282 -0.63399,0 -11.88027,-11.5043 -11.88027,-12.15284 0,-0.26828 -0.60264,-1.18911 -1.3392,-2.0463 -1.88636,-2.1953 -6.79637,-12.20202 -8.13636,-16.58213 l -0.63915,-2.08923 H 331.8345 317.94952 V 126.694 c 0,-1.22568 0.25071,-2.22851 0.55713,-2.22851 0.30642,0 0.55713,-0.86267 0.55713,-1.91706 0,-1.05439 0.21736,-2.0514 0.48303,-2.21559 0.57223,-0.35367 1.32507,-7.46205 1.33244,-12.58117 0.007,-4.89972 -0.74298,-11.659719 -1.33421,-12.025119 -0.2647,-0.163592 -0.48126,-1.285472 -0.48126,-2.493068 0,-1.207597 -0.25071,-2.19563 -0.55713,-2.19563 -0.30642,0 -0.55713,-0.752121 -0.55713,-1.671381 V 87.69509 h 13.28471 c 14.78954,0 14.57813,0.05323 15.41618,-3.881605 0.22628,-1.062412 0.60491,-2.315949 0.8414,-2.785637 0.23649,-0.469687 1.62564,-3.298373 3.087,-6.285968 1.46136,-2.987596 2.86559,-5.431992 3.1205,-5.431992 0.25492,0 0.46349,-0.261649 0.46349,-0.581442 0,-0.319794 0.62676,-1.257384 1.39281,-2.083535 0.76605,-0.82615 1.39282,-1.69943 1.39282,-1.940621 0,-0.922889 7.68593,-8.4251 11.14255,-10.876204 1.99173,-1.412345 3.7049,-2.768742 3.80704,-3.014214 0.10214,-0.245472 0.5553,-0.446312 1.00703,-0.446312 0.45173,0 1.94398,-0.626769 3.31611,-1.392819 1.37213,-0.76605 2.98729,-1.392818 3.58926,-1.392818 0.60197,0 1.09449,-0.223278 1.09449,-0.496174 0,-0.583696 2.19547,-1.007737 9.63051,-1.860065 4.12022,-0.47233 6.56994,-0.463152 10.02829,0.03757 6.82151,0.987661 8.17726,1.277959 8.51417,1.823098 0.16846,0.272564 0.7929,0.495571 1.38766,0.495571 0.59475,0 1.08137,0.250707 1.08137,0.557127 0,0.30642 0.30978,0.557128 0.68841,0.557128 0.66878,0 6.55231,2.763565 7.11137,3.340297 0.15321,0.158053 1.28139,1.028978 2.50707,1.935387 1.22568,0.90641 2.54054,1.951229 2.92191,2.32182 0.38138,0.370592 1.25886,1.131801 1.94995,1.691577 3.27851,2.655524 11.5634,13.571269 11.5634,15.235338 0,0.300471 0.18951,0.54631 0.42113,0.54631 0.43834,0 1.08233,1.332029 2.69349,5.571273 0.52406,1.37889 1.16584,2.745245 1.42618,3.036344 0.26034,0.291099 0.47335,1.091101 0.47335,1.777784 0,0.686682 0.2507,1.403458 0.55712,1.592836 0.30642,0.189378 0.55713,0.834344 0.55713,1.433258 0,0.598913 0.23989,1.52427 0.53309,2.056347 0.96262,1.746917 1.89152,10.855931 1.88826,18.516921 -0.003,7.76236 -1.02514,17.70498 -1.91528,18.63591 -0.27834,0.2911 -0.50607,1.23125 -0.50607,2.08923 0,0.85798 -0.25071,1.55996 -0.55713,1.55996 -0.30642,0 -0.55712,0.50482 -0.55712,1.12181 0,0.617 -0.22964,1.55715 -0.51029,2.08923 -0.28067,0.53208 -0.87388,1.84489 -1.31826,2.91736 -2.00256,4.83299 -2.36889,5.57127 -2.76447,5.57127 -0.23162,0 -0.42113,0.50142 -0.42113,1.11426 0,0.61284 -0.1937,1.11425 -0.43044,1.11425 -0.23673,0 -0.92141,0.94014 -1.52149,2.08922 -2.26901,4.34483 -9.17697,11.82024 -14.67282,15.87813 -1.97127,1.45549 -3.90503,2.64635 -4.29725,2.64635 -0.39221,0 -0.79668,0.18803 -0.89882,0.41785 -0.32427,0.7296 -5.07837,2.82923 -8.54262,3.77282 -1.83852,0.50077 -4.11268,1.14162 -5.0537,1.42411 -2.09091,0.62769 -14.46803,0.57408 -16.3957,-0.071 z" id="path3404-9" inkscape:connector-curvature="0" />' +
    '<path d="m 102.61981,469.32729 c -0.18937,-0.30642 -1.56826,-0.55713 -3.064202,-0.55713 -1.49593,0 -2.87482,-0.2507 -3.0642,-0.55712 -0.18937,-0.30642 -0.9415,-0.55713 -1.67138,-0.55713 -0.72988,0 -1.482,-0.25071 -1.67138,-0.55713 -0.18938,-0.30642 -0.85494,-0.55713 -1.47902,-0.55713 -0.98605,0 -4.11804,-1.41691 -4.92794,-2.22939 -0.15321,-0.1537 -1.02721,-0.65512 -1.94221,-1.11426 -0.915,-0.45914 -2.29389,-1.32696 -3.0642,-1.92848 -2.53082,-1.97629 -9.269641,-9.17205 -10.058649,-10.74071 -0.425106,-0.84517 -1.18161,-2.16343 -1.681121,-2.92948 -0.499511,-0.76605 -1.353618,-2.77171 -1.898015,-4.45702 -0.544398,-1.68531 -1.210248,-3.49954 -1.479667,-4.03162 -0.269419,-0.53207 -0.489853,-1.58278 -0.489853,-2.33491 0,-0.75212 -0.250707,-1.52244 -0.557127,-1.71182 -0.770479,-0.47618 -0.770479,-143.81979 0,-144.29597 0.30642,-0.18938 0.557127,-1.44292 0.557127,-2.78564 0,-1.34272 0.250708,-2.59626 0.557128,-2.78564 0.30642,-0.18937 0.557127,-1.06685 0.557127,-1.94994 0,-0.88309 0.228893,-1.74709 0.50865,-1.91999 0.279758,-0.17289 0.677633,-1.31456 0.884168,-2.53703 0.206535,-1.22247 0.604411,-2.36414 0.884168,-2.53704 0.279758,-0.17289 0.50865,-0.80098 0.50865,-1.39573 0,-0.59476 0.250708,-1.08138 0.557128,-1.08138 0.30642,0 0.557127,-0.50141 0.557127,-1.11425 0,-0.61284 0.194519,-1.11426 0.432264,-1.11426 0.237746,0 0.927191,-1.1258 1.5321,-2.50179 0.60491,-1.37599 1.287867,-2.50417 1.517682,-2.50708 0.229815,-0.003 0.417845,-0.25318 0.417845,-0.55618 0,-2.01948 11.327838,-13.97209 16.713818,-17.63564 4.15724,-2.82777 9.416462,-5.76993 10.313952,-5.76993 0.40466,0 0.81931,-0.19432 0.92145,-0.43183 0.2466,-0.57343 8.91694,-3.46806 10.38794,-3.46806 0.63422,0 1.30807,-0.2507 1.49745,-0.55712 0.18937,-0.30642 1.56826,-0.55713 3.0642,-0.55713 1.49593,0 2.87482,-0.25071 3.0642,-0.55713 0.22509,-0.36421 16.44103,-0.55713 46.83157,-0.55713 h 46.48725 v -22.79179 -22.79179 l 2.92492,0.77865 c 1.6087,0.42826 3.72492,0.78163 4.7027,0.78526 0.97778,0.004 1.93273,0.25732 2.12211,0.56374 0.46376,0.75039 20.70707,0.75039 21.17084,0 0.18937,-0.30642 1.15236,-0.55712 2.13997,-0.55712 0.98761,0 3.10383,-0.33934 4.7027,-0.75408 l 2.90705,-0.75408 0.008,21.50707 c 0.004,11.82889 0.15878,22.07116 0.34355,22.76061 l 0.33593,1.25353 h 46.14371 c 30.1615,0 46.26297,0.19297 46.48803,0.55713 0.18938,0.30642 1.56827,0.55713 3.0642,0.55713 1.49593,0 2.87482,0.25071 3.0642,0.55713 0.18938,0.30642 0.87478,0.55712 1.5231,0.55712 2.91561,0 12.2499,3.76258 17.56933,7.08206 1.61466,1.00759 3.18158,1.83198 3.48205,1.83198 0.30047,0 0.54631,0.19666 0.54631,0.43701 0,0.24035 0.76441,0.88855 1.69869,1.44045 1.59697,0.94335 12.22949,11.32368 12.22949,11.93941 0,0.15659 0.62677,1.10644 1.39282,2.11078 0.76605,1.00435 1.39281,2.09351 1.39281,2.42038 0,0.32687 0.25071,0.5943 0.55713,0.5943 0.30642,0 0.55713,0.37606 0.55713,0.83569 0,0.45963 0.18843,0.83569 0.41874,0.83569 0.36241,0 2.24397,3.77872 4.16121,8.35691 0.3208,0.76605 0.79596,1.89423 1.0559,2.50707 0.49221,1.16042 1.27014,4.16542 2.30765,8.91404 0.90451,4.13985 0.91017,146.36818 0.006,149.03156 -0.36411,1.07247 -0.97202,3.20348 -1.35092,4.73558 -0.37889,1.5321 -0.84778,2.91099 -1.04198,3.0642 -0.1942,0.15321 -0.70759,1.09336 -1.14086,2.08923 -0.43328,0.99586 -0.95891,1.81066 -1.16806,1.81066 -0.20915,0 -0.46134,0.38981 -0.56041,0.86625 -0.2838,1.36479 -6.60722,8.77025 -9.03788,10.58441 -4.47587,3.34064 -9.1997,5.92478 -12.59165,6.88819 -1.37889,0.39164 -2.94241,0.93835 -3.47449,1.2149 -0.53208,0.27656 -2.0842,0.50283 -3.44916,0.50283 -1.36497,0 -2.6367,0.25071 -2.82608,0.55713 -0.47711,0.77198 -263.60123,0.77198 -264.07835,0 z M 346.64158,364.03023 c 0.18938,-0.30642 0.81767,-0.55713 1.39621,-0.55713 1.70002,0 6.21547,-2.66065 8.79595,-5.18286 2.56059,-2.50277 5.12884,-6.87268 5.12884,-8.7268 0,-0.62303 0.25071,-1.13278 0.55713,-1.13278 0.36383,0 0.55712,-9.47116 0.55712,-27.29923 0,-17.82808 -0.19329,-27.29924 -0.55712,-27.29924 -0.30642,0 -0.55713,-0.51141 -0.55713,-1.13645 0,-3.66497 -8.13486,-12.79174 -11.40151,-12.79174 -0.47041,0 -0.85529,-0.2507 -0.85529,-0.55712 0,-0.30642 -0.73733,-0.55713 -1.6385,-0.55713 -0.90118,0 -1.79345,-0.25071 -1.98283,-0.55713 -0.47691,-0.77165 -222.37402,-0.77165 -222.85093,0 -0.18937,0.30642 -1.08165,0.55713 -1.98282,0.55713 -0.90118,0 -1.6385,0.25071 -1.6385,0.55713 0,0.30642 -0.41508,0.55712 -0.92239,0.55712 -3.0754,0 -11.33441,9.35149 -11.33441,12.8337 0,0.60197 -0.25071,1.09449 -0.55713,1.09449 -0.36384,0 -0.55713,9.47116 -0.55713,27.29924 0,17.82807 0.19329,27.29923 0.55713,27.29923 0.30642,0 0.55713,0.49252 0.55713,1.09449 0,4.48931 9.63183,13.94795 14.20334,13.94795 0.73176,0 1.48541,0.25071 1.67478,0.55713 0.47691,0.77165 222.93115,0.77165 223.40806,0 z" id="path3406-1" inkscape:connector-curvature="0" />' +
    "</g>" +
    '<g id="g1655">' +
    '<rect style="fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.93750006;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="' +
    element.id +
    ':ValueBackground" width="50.892857" height="21.428572" x="793.17432" y="186.77423" />' +
    '<text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:37.5px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.93750006" x="801.69501" y="201.27823" id="text1608">' +
    '<tspan sodipodi:role="line" id="' +
    element.id +
    ':Value" x="796.69501" y="201.27823" style="font-size:12.5px;stroke-width:0.93750006">' +
    0 +
    " m/s</tspan>" +
    "</text>" +
    "</g>" +
    "</g>";

  return anem_str;
}

export function generateFanIcon(element) {
  let fan_str =
    '<defs id="defs3446">' +
    '<marker  inkscape:stockid="Arrow1Sstart" orient="auto" refY="0" refX="0" id="Arrow1Sstart-7-7-4-9-9-4-8" style="overflow:visible" inkscape:isstock="true">' +
    '<path id="' + element.id + ':IconArrowMD" inkscape:connector-curvature="0" d="M 0,0 5,-5 -12.5,0 5,5 Z" style="fill:#ffff00;fill-opacity:1;fill-rule:evenodd;stroke:#ffff00;stroke-width:1.00000003pt;stroke-opacity:1" transform="matrix(0.2,0,0,0.2,1.2,0)" />' +
    '<path id="' + element.id + ':IconArrowMI" inkscape:connector-curvature="0" d="M 0,0 5,-5 -12.5,0 5,5 Z" style="fill:#ffff00;fill-opacity:1;fill-rule:evenodd;stroke:#ffff00;stroke-width:1.00000003pt;stroke-opacity:1" transform="matrix(0.2,0,0,0.2,1.2,0)" />' +
    "</marker>" +
    "</defs>" +
    '<path id="' +
    element.id +
    ':Icon5" style="fill:#cccccc" d="m 139.98687,561.74217 c -4.68007,-1.13295 -11.28841,-4.45184 -16.07392,-8.07276 -5.29801,-4.0087 -16.07857,-15.03315 -22.30303,-22.8076 -16.669941,-20.82102 -28.391611,-44.16154 -35.613212,-70.91404 -1.584484,-5.86974 -1.730197,-7.08094 -1.763998,-14.66268 -0.03153,-7.0715 0.137124,-8.86466 1.168391,-12.42285 3.91296,-13.50091 14.094758,-24.55324 27.320335,-29.65618 4.946944,-1.90873 7.485554,-2.29094 73.046794,-10.99802 16.86207,-2.23943 31.01822,-4.28236 31.4581,-4.53986 0.57583,-0.33708 0.60434,-0.47272 0.10183,-0.48439 -0.38387,-0.009 -0.55314,-0.25053 -0.37615,-0.5369 0.17699,-0.28638 -6.79577,-17.8616 -15.49504,-39.05604 -20.24955,-49.33496 -21.95505,-53.68596 -22.68352,-57.86909 -0.93165,-5.34998 -0.32438,-16.65742 1.12268,-20.90441 4.27266,-12.53979 12.6159,-22.06046 24.04782,-27.44155 6.51424,-3.0663 21.17695,-6.8789 34.34332,-8.92995 8.93441,-1.3918 33.04801,-1.93612 44.17432,-0.99716 12.80926,1.08099 30.97691,4.98241 41.30607,8.8703 9.664,3.63752 18.6122,11.81985 23.55518,21.53912 3.03289,5.9635 3.87065,9.4999 4.13369,17.44935 0.41156,12.43811 2.35436,6.76307 -25.67611,75.00141 -7.21237,17.55801 -13.11339,32.26818 -13.11339,32.68925 0,0.42106 0.23993,0.61729 0.53319,0.43605 0.29325,-0.18124 0.53319,-0.10805 0.53319,0.16265 0,0.2707 1.13969,0.65148 2.53264,0.84618 1.39295,0.1947 20.28781,2.73909 41.98857,5.65419 59.00105,7.92572 56.12735,7.43231 62.91622,10.80267 10.56798,5.24652 18.81803,14.66615 22.76224,25.98915 1.72865,4.9626 1.76379,5.24364 1.73369,13.86289 -0.0293,8.38985 -0.12049,9.13131 -1.96761,15.99736 -8.66351,32.2036 -23.71569,58.9316 -46.48426,82.54161 -10.74904,11.14629 -16.65008,15.19076 -26.20297,17.95908 -3.84576,1.11446 -5.73781,1.33656 -11.38606,1.33656 -8.17866,0 -11.59763,-0.58022 -17.10447,-2.90271 -5.64417,-2.38041 -10.95243,-5.99813 -14.73142,-10.03985 -3.336,-3.56793 -13.64567,-16.67899 -39.71523,-50.50684 -23.01429,-29.86333 -22.55271,-29.27557 -23.0115,-29.30166 -0.22915,-0.013 -0.57325,0.39619 -0.76468,0.90938 -0.19142,0.5132 -0.78774,1.24942 -1.32515,1.63605 -0.53742,0.38663 -0.83897,0.92648 -0.67013,1.19967 0.16884,0.27319 0.0708,0.49671 -0.21793,0.49671 -0.67805,0 -7.11243,8.14278 -34.7591,43.98803 -12.4399,16.12894 -23.92682,30.61992 -25.52649,32.20218 -5.5802,5.51948 -15.21715,10.66447 -22.05355,11.77399 -4.72995,0.76764 -16.06096,0.59601 -19.75935,-0.29929 z m 17.34602,-29.20951 c 1.07902,-0.52628 2.75856,-1.73246 3.73232,-2.68039 3.32975,-3.24142 53.22315,-67.88254 53.22315,-68.95509 0,-0.23926 -2.20061,-2.46446 -4.89024,-4.9449 -5.78173,-5.33204 -10.07157,-10.92899 -13.11384,-17.1096 -2.42228,-4.92107 -4.73939,-12.33563 -5.27251,-16.87161 -0.21482,-1.82777 -0.63439,-3.01862 -1.13066,-3.20905 -0.85865,-0.3295 -0.93909,-0.31994 -32.91049,3.91062 -30.17485,3.99284 -48.90411,6.60854 -49.8531,6.96241 -0.43988,0.16403 -1.6689,0.3713 -2.73116,0.4606 -2.6998,0.22698 -4.687696,1.37897 -7.334436,4.25034 -4.198813,4.55518 -4.924463,8.14352 -3.27813,16.21035 3.181484,15.58886 11.220696,34.15111 21.726136,50.16486 6.51066,9.9244 10.00341,14.20514 18.77459,23.01035 6.53367,6.559 8.81063,8.51302 10.69936,9.18186 3.16973,1.12247 9.68882,0.92163 12.35901,-0.38075 z m 188.45762,0.0408 c 3.97356,-2.08131 16.55412,-14.9273 22.7138,-23.19302 13.84206,-18.57478 25.30921,-43.43529 28.14566,-61.01911 0.7552,-4.68164 0.51726,-6.97978 -1.05998,-10.23793 -1.88972,-3.90365 -7.00893,-8.26798 -8.86336,-7.55637 -0.41341,0.15864 -0.75166,0.0389 -0.75166,-0.26617 0,-0.30504 -0.53985,-0.50177 -1.19967,-0.43718 -0.65982,0.0646 -1.62314,-0.0652 -2.14072,-0.28854 -0.51758,-0.22328 -1.41733,-0.28142 -1.99946,-0.12919 -0.58212,0.15225 -1.05841,0.103 -1.05841,-0.10942 0,-0.44134 1.64431,-0.20536 -43.14331,-6.19159 -18.59689,-2.48562 -34.52055,-4.51931 -35.38591,-4.51931 -1.31088,0 -1.52686,0.17791 -1.29452,1.06638 0.15337,0.5865 0.0886,1.06637 -0.14387,1.06637 -0.2325,0 -0.59207,1.12922 -0.79903,2.50938 -1.79719,11.98448 -8.56428,23.86586 -18.97089,33.30827 -2.32879,2.11303 -4.23417,4.05339 -4.23417,4.31191 0,1.02536 50.41731,65.9749 51.21324,65.9749 0.27831,0 0.50602,0.31121 0.50602,0.69158 0,1.14107 3.97422,4.66171 6.19323,5.48639 2.7192,1.01056 9.97924,0.73412 12.27301,-0.46733 z M 250.15583,439.703 c 11.28321,-2.39096 19.25972,-11.68446 19.99119,-23.2919 0.92083,-14.61238 -10.39538,-26.57759 -25.13601,-26.57759 -9.7304,0 -17.71493,4.70545 -22.30739,13.1462 -6.77415,12.45061 -1.72383,28.32807 11.00583,34.60064 5.87505,2.89496 10.17984,3.45055 16.44638,2.12265 z m -22.00458,-76.36488 5.59848,-2.03018 10.93036,0.005 c 11.64213,0.005 12.45189,0.12684 20.13692,3.02469 2.42434,0.91417 4.48812,1.56622 4.58619,1.44902 0.30268,-0.36171 5.73169,-13.28074 19.83923,-47.20999 l 13.55518,-32.60072 -0.0234,-3.92258 c -0.0265,-4.44938 -0.80252,-7.07314 -2.90844,-9.83413 -1.56023,-2.04558 -4.44281,-4.07098 -5.16161,-3.62674 -0.24065,0.14873 -0.43756,-0.0237 -0.43756,-0.38309 0,-0.35944 -0.14853,-0.50499 -0.33007,-0.32346 -0.18153,0.18154 -0.64142,0.0187 -1.02197,-0.36183 -0.78348,-0.78348 -8.16921,-2.90274 -15.70998,-4.50781 -18.01699,-3.83496 -39.54266,-4.34111 -57.58433,-1.35403 -6.45503,1.06873 -20.31447,4.58883 -23.51808,5.97325 -6.57322,2.84057 -10.79488,11.65711 -8.39172,17.52529 0.33808,0.82554 0.70404,1.97043 0.81325,2.54421 0.10921,0.57378 0.37245,1.17362 0.58499,1.33297 0.21253,0.15936 0.46863,0.76057 0.56909,1.33602 0.35454,2.03073 29.65782,73.59797 30.64201,74.83682 0.30742,0.38695 0.9356,0.58073 1.39596,0.43061 0.46037,-0.15011 3.35635,-1.18652 6.43551,-2.30312 z" inkscape:connector-curvature="0">' +
    '<animateTransform id="' +
    element.id +
    ':Animation" attributeType="xml" attributeName="transform" type="rotate" from="360 245 410" to="0 245 410" dur="6s" repeatCount="indefinite" />' +
    "</path>" +
    '<g transform="matrix(42.490516,0,0,42.490516,-9.9529807,-11912.934)" id="' +
    element.id +
    ':IconInterior" style="fill:#cccccc">' +
    '<path id="' +
    element.id +
    ':Icon1" inkscape:connector-curvature="0" d="m 0.83169546,292.33918 c -0.30860772,-0.66605 -0.48117516,-1.4009 -0.48117516,-2.17393 0,-0.16513 0.009132,-0.32804 0.0243514,-0.48939 H 0 v 2.66332 h 0.19106517 z" style="fill:#cccccc;stroke-width:0.2279644" />' +
    '<path id="' +
    element.id +
    ':Icon2" inkscape:connector-curvature="0" d="m 11.316397,292.33918 h 0.625176 v -2.66332 h -0.168586 c 0.01522,0.16135 0.02435,0.32426 0.02435,0.48939 2.34e-4,0.77303 -0.172567,1.50788 -0.48094,2.17393 z" style="fill:#cccccc;stroke-width:0.2279644" />' +
    '<path id="' +
    element.id +
    ':Icon3" inkscape:connector-curvature="0" d="m 12.038277,297 -0.282149,-4.43888 H 11.20705 c -0.935189,1.79287 -2.8842416,3.0291 -5.1332379,3.0291 -2.2489964,0 -4.1980485,-1.23623 -5.13323758,-3.0291 H 0.41163305 L 0.12901581,297 H 1.404891 v -0.66583 h 9.36594 V 297 Z" style="fill:#cccccc;stroke-width:0.2279644" />' +
    '<path id="' +
    element.id +
    ':Icon4" inkscape:connector-curvature="0" d="m 6.0740462,295.36827 c 3.0268377,0 5.4893778,-2.33418 5.4893778,-5.20302 0,-2.86907 -2.4625401,-5.20325 -5.4893778,-5.20325 -3.0268376,0 -5.4893774,2.33418 -5.4893774,5.20325 0,2.86906 2.4625398,5.20302 5.4893774,5.20302 z m -0.024351,-10.00899 c 2.7866013,0 5.0458998,2.14131 5.0458998,4.78288 0,2.64157 -2.2595327,4.78267 -5.0458998,4.78267 -2.7868355,0 -5.0459002,-2.1411 -5.0459002,-4.78267 0,-2.64157 2.2588306,-4.78288 5.0459002,-4.78288 z" style="fill:#cccccc;stroke-width:0.2279644" />' +
    "</g>" +
    '<path id="' + element.id + ':IconMD" style="fill:#ffff00;fill-opacity:1;stroke:#ffff00;stroke-width:67.85962677;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:5;stroke-dasharray:none;stroke-opacity:1;marker-start:url(#Arrow1Sstart-7-7-4-9-9-4-8)" d="M 105.97212,87.97389 H 507.6238" inkscape:connector-curvature="0" />' +
    '<path id="' + element.id + ':IconMI" style="fill:#ffff00;fill-opacity:1;stroke:#ffff00;stroke-width:67.85962677;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:5;stroke-dasharray:none;stroke-opacity:1;marker-start:url(#Arrow1Sstart-7-7-4-9-9-4-8)" d="M 386.47125,87.97389 H -15.180432" inkscape:connector-curvature="0" />';

  return fan_str;
}

export function getElementIcon(element) {
  let elem_icon = "";
  let width = 0;
  let height = 0;

  let elementType = "ElementType:" + element.id.split(":")[1];
  let elementState = "";
  if (document.getElementById(element.id) != null) {
    elementState = parseInt(
      document.getElementById(element.id).getAttribute("state")
    );
  } else {
    elementState = element.state;
  }

  // icon based on status
  if (typeof staticsIcons[elementType] != "undefined") {
    elem_icon = staticsIcons[elementType]["state"].find(
      //(ic) => ic.state === element.state
      (ic) => ic.state === elementState
    );
  } else {
    elem_icon = staticsIcons["ElementType:33"]["state"].find(
      //(ic) => ic.state === element.state
      (ic) => ic.state === elementState
    );
  }

  if (typeof elem_icon != "undefined") {
    width = elem_icon.width;
    height = elem_icon.height;
    elem_icon = elem_icon.icon;
  } else {
    elem_icon = "";
  }

  // icon based on element value
  if (elementType === "ElementType:9") {
    // Fan
    if (
      document.getElementById(element.id + ":Icon1") != null &&
      document.getElementById(element.id + ":Icon2") != null &&
      document.getElementById(element.id + ":Icon3") != null &&
      document.getElementById(element.id + ":Icon4") != null &&
      document.getElementById(element.id + ":Icon5") != null
    ) {
      
      // changes icon animation based on value
      let fan_cmd = element.elementValues.find(
        (ic) => ic.elementTypeParam.description === "comand for fan"
      );

      if (typeof fan_cmd != "undefined") {
        if(fan_cmd.value == 0) {
          document.getElementById(element.id + ":Animation").setAttribute("dur", "0");
          document.getElementById(element.id + ":IconMD").style["visibility"] = "hidden";
          document.getElementById(element.id + ":IconMI").style["visibility"] = "hidden";
        } else if(fan_cmd.value == 1) {
          document.getElementById(element.id + ":Animation").setAttribute("dur", "6s");
          document.getElementById(element.id + ":IconArrowMD").style["visibility"] = "visible";
          document.getElementById(element.id + ":IconArrowMI").style["visibility"] = "hidden";
          document.getElementById(element.id + ":IconMD").style["visibility"] = "visible";
          document.getElementById(element.id + ":IconMI").style["visibility"] = "hidden";
        } else if(fan_cmd.value == 2) {
          document.getElementById(element.id + ":Animation").setAttribute("dur", "6s");
          document.getElementById(element.id + ":IconArrowMD").style["visibility"] = "hidden";
          document.getElementById(element.id + ":IconArrowMI").style["visibility"] = "visible";
          document.getElementById(element.id + ":IconMD").style["visibility"] = "hidden";
          document.getElementById(element.id + ":IconMI").style["visibility"] = "visible";
        }
      }

      let color = "";
      
      //if (element.state == "0") {
      if (elementState == 0) {
        color = "#ffcc00";
        //} else if (element.state == "1") {
      } else if (element.state == 1) {
        color = "#c0c0c0";
        //} else if (element.state == "2") {
      } else if (element.state == 2) {
        color = "#800080";
        //} else if (element.state == "3") {
      } else if (element.state == 3) {
        color = "#ff0000";
      }

      document.getElementById(element.id + ":Icon1").style[
        "fill"
      ] = color;
      document.getElementById(element.id + ":Icon2").style[
        "fill"
      ] = color;
      document.getElementById(element.id + ":Icon3").style[
        "fill"
      ] = color;
      document.getElementById(element.id + ":Icon4").style[
        "fill"
      ] = color;
      document.getElementById(element.id + ":Icon5").style[
        "fill"
      ] = color;

    }
  } else if (elementType === "ElementType:8") {
    // Barrier
    //if (element.state === 1) {
    if (elementState === 1) {
      let bar_cmd = element.elementValues.find(
        (ic) => ic.elementTypeParam.description === "comand for barrier"
      );

      elem_icon = staticsIcons[elementType]["state"].find(
        (ic) => ic.bar_cmd === parseInt(bar_cmd.value)
      );
    } else {
      // states 0, 2 and 3
      if (typeof staticsIcons[elementType] != "undefined") {
        elem_icon = staticsIcons[elementType]["state"].find(
          //(ic) => ic.state === element.state
          (ic) => ic.state === elementState
        );
      } else {
        elem_icon = staticsIcons["ElementType:33"]["state"].find(
          //(ic) => ic.state === element.state
          (ic) => ic.state === elementState
        );
      }
    }

    if (typeof elem_icon != "undefined") {
      elem_icon = elem_icon.icon;
    } else {
      elem_icon = "";
    }
  } else if (elementType === "ElementType:7") {
    // Tunnel
    // icon based on status
    if (typeof staticsIcons[elementType] != "undefined") {
      elem_icon = staticsIcons[elementType]["state"].find(
        //(ic) => ic.state === element.state
        (ic) => ic.state === elementState
      );
    } else {
      elem_icon = staticsIcons["ElementType:33"]["state"].find(
        //(ic) => ic.state === element.state
        (ic) => ic.state === elementState
      );
    }

    if (typeof elem_icon != "undefined") {
      //width = elem_icon.width;
      //height = elem_icon.height;
      elem_icon = elem_icon.icon;
    } else {
      elem_icon = "";
    }

    // Change title bar color if it's in alarm state
    if (elementType == "7") {
      if (document.getElementById(element.id + ":Bar") != null) {
        //if (element.state == 3) {
        if (elementState == 3) {
          document
            .getElementById(element.id + ":Bar")
            .setAttribute("fill", "#ff0000");
        } else {
          document
            .getElementById(element.id + ":Bar")
            .setAttribute("fill", "#ebb700");
        }
      }
    }
  } else if (
    elementType === "ElementType:12" ||
    elementType === "ElementType:14" ||
    elementType === "ElementType:17" ||
    elementType === "ElementType:18" ||
    elementType === "ElementType:19" ||
    elementType === "ElementType:20" ||
    elementType === "ElementType:21"
  ) {
    if (
      document.getElementById(element.id + ":IconBackground") != null &&
      document.getElementById(element.id + ":ValueBackground") != null
    ) {
      let color = "";

      //if (element.state == "0") {
      if (elementState == 0) {
        color = "#ffcc00";
        //} else if (element.state == "1") {
      } else if (elementState == 1) {
        color = "#c0c0c0";
        //} else if (element.state == "2") {
      } else if (elementState == 2) {
        color = "#800080";
        //} else if (element.state == "3") {
      } else if (elementState == 3) {
        color = "#ff0000";
      }

      document.getElementById(element.id + ":IconBackground").style[
        "fill"
      ] = color;
      document.getElementById(element.id + ":ValueBackground").style[
        "fill"
      ] = color;

      // changes the value
      let new_value = null;
      switch(elementType) {
        case "ElementType:12":
          new_value = element.elementValues.find(
            (ic) => ic.elementTypeParam.description === "luminosity"
          );
          break;
        case "ElementType:14":
          new_value = element.elementValues.find(
            (ic) => ic.elementTypeParam.description === "luminosity"
          );
          break;
        case "ElementType:17":
          new_value = element.elementValues.find(
            (ic) => ic.elementTypeParam.description === "wind_speed"
          );
          break;
        case "ElementType:18":
          new_value = element.elementValues.find(
            (ic) => ic.elementTypeParam.description === "wind_speed"
          );
          break;
        case "ElementType:19":
          new_value = element.elementValues.find(
            (ic) => ic.elementTypeParam.description === "CO_concentration"
          );
          break;
        case "ElementType:20":
          new_value = element.elementValues.find(
            (ic) => ic.elementTypeParam.description === "NO_concentration"
          );
          break;
        case "ElementType:21":
          new_value = element.elementValues.find(
            (ic) => ic.elementTypeParam.description === "OPAC_concentration"
          );
          break;
        default:
          break;
      }

      if (typeof new_value != "undefined" && new_value != null) {
        document.getElementById(element.id + ":Value").innerHTML = new_value.value;
      }
      
    }
  } else if (elementType === "ElementType:23") {
    for(let i=0; i<element.elementValues.length; i++) {
      let ev = element.elementValues[i];
      if(document.getElementById(element.id + ":" + ev.elementTypeParam.description) != null) {
        let domObj = element.id + ":" + ev.elementTypeParam.description;
        document.getElementById(domObj).innerHTML = ev.value;
      }
    }
  } else if (elementType === "ElementType:10") {
    // DMS
    // icon based on status
    //if (element.state != "1") {
    // Reset text lines
    /*
      if (document.getElementById(element.id + ":linea1") != null) {
        document.getElementById(element.id + ":linea1").innerHTML = "";
      }
      if (document.getElementById(element.id + ":linea2") != null) {
        document.getElementById(element.id + ":linea2").innerHTML = "";
      }
      if (document.getElementById(element.id + ":linea3") != null) {
        document.getElementById(element.id + ":linea3").innerHTML = "";
      }
      */

    if (
      document.getElementById(element.id + ":rect2085-1") != null &&
      document.getElementById(element.id + ":rect2250-2-89") != null
    ) {
      let color = "";

      //if (element.state == "0") {
      if (elementState == 0) {
        color = "#ffcc00";
        //} else if (element.state == "1") {
      } else if (elementState == 1) {
        color = "#c0c0c0";
        //} else if (element.state == "2") {
      } else if (elementState == 2) {
        color = "#800080";
        //} else if (element.state == "3") {
      } else if (elementState == 3) {
        color = "#ff0000";
      }

      document.getElementById(element.id + ":rect2085-1").style[
        "stroke"
      ] = color;
      document.getElementById(element.id + ":rect2250-2-89").style[
        "stroke"
      ] = color;
    }
    //} else {
    // check for the status message we should use
    // FIXME: this is just for the demo, it is not a real DMS
    let messageToShow = element.elementValues.find(
      (ic) => ic.elementTypeParam.description === "comand for rotapanel"
    );

    let message1 = "~~~######~~~";

    if (messageToShow.value == "1") {
      let tmp_message1 = element.elementValues.find(
        (ic) => ic.elementTypeParam.description === "text for status 1"
      );

      if (typeof tmp_message1 != "undefined") {
        message1 =
          "~~~###" +
          element.elementValues.find(
            (ic) => ic.elementTypeParam.description === "text for status 1"
          ).value +
          "###~~~";
      }
    } else if (messageToShow.value == "2") {
      let tmp_message1 = element.elementValues.find(
        (ic) => ic.elementTypeParam.description === "text for status 2"
      );

      if (typeof tmp_message1 != "undefined") {
        message1 =
          "~~~###" +
          element.elementValues.find(
            (ic) => ic.elementTypeParam.description === "text for status 2"
          ).value +
          "###~~~";
      }
    }

    if (typeof message1 != "undefined") {
      let linea1 = message1.split("~~~")[1].split("###")[0];
      let linea2 = message1.split("~~~")[1].split("###")[1];
      let linea3 = message1.split("~~~")[1].split("###")[2];

      document.getElementById(element.id + ":linea1").innerHTML = linea1;
      document.getElementById(element.id + ":linea2").innerHTML = linea2;
      document.getElementById(element.id + ":linea3").innerHTML = linea3;

    }
    //}
  } else {
    // FIX FOR CALLBACK HELL
    // icon based on status
    if (typeof staticsIcons[elementType] != "undefined") {
      elem_icon = staticsIcons[elementType]["state"].find(
        //(ic) => ic.state === element.state
        (ic) => ic.state === elementState
      );
    } else {
      elem_icon = staticsIcons["ElementType:33"]["state"].find(
        //(ic) => ic.state === element.state
        (ic) => ic.state === elementState
      );
    }

    if (typeof elem_icon != "undefined") {
      //width = elem_icon.width;
      //height = elem_icon.height;
      elem_icon = elem_icon.icon;
    } else {
      elem_icon = "";
    }
  }

  return { icon: elem_icon, width: width, height: height };
}

export function updateElementIcon(element) {
  // check if the element exists
  if (document.getElementById(element.id) != null) {
    let elem_icon = getElementIcon(element).icon;

    // replace the icon
    document
      .getElementById(element.id)
      .setAttribute("href", "./img/" + elem_icon);
  }
}

export function updateElementState(element) {
  // check if the element exists and we are receiving an state update
  let el = document.getElementById(element.id);
  if (el != null && element.state != null) {
    el.setAttribute("state", element.state);
  }
}
