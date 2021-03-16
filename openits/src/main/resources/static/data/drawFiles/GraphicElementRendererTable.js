import {ELEMENT_TYPE} from "../GLOBAL_PROPERTIES.js";

const ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_TABLE = "graphic_param_table";
const ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_LABEL = "graphic_param_label";
const ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_COLOR = "graphic_param_color";

export function generateIcon(element, suffix="", graphicIcon, snapDiv) {
  let tableRows = new Map();
  let color= "purple";
  element.elementValues.forEach(item => {
    if(item.elementTypeParam[ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_TABLE]){
      tableRows.set(parseInt(item.elementTypeParam[ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_TABLE]), {value: item.value, label: item.elementTypeParam[ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_LABEL]})
    }
    if(item.elementTypeParam[ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_COLOR]){
      if(getTrimId(element.elementTypeState.id)!=2){
        if (item.value == 1) {
          color = "white";
        } else if (item.value == 2) {
          color = "green";    
        } else if (item.value == 3) {
          color = "gold";
        } else if (item.value == 4) {
          color = "red";
        }
      }
    }
  });
  
  let etd_str =
    '<g inkscape:label ="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> ' +
    '<rect id="'+element.id +':fill0" stroke="black" style="stroke-width:0.2" fill="' + color + '" width="34.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" />' +
    '<text fill="black" xml:space="preserve" x="16.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)">' +
    '<tspan fill="black" x="16.904327" y="272.36899" style="font-size:2.7px;line-height:2.62886882px;text-align:center;text-anchor:middle" id="tspan1693-0">' +
    element.description +
    "</tspan></text>";

  for (let i = 0; i < tableRows.size; i++) {
    etd_str +=
      '<rect stroke="black" style="stroke-width:0.2" fill="lightgray" id="rect4066-6-5-93-0" width="34.228191" height="5.6456451" x="0.041112646" y="' +
      (274.66467 + 6 * i) +
      '" ry="0" />' +
      '<text xml:space="preserve" fill="black" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)">' +
      '<tspan sodipodi:role="line" x="7.465615" y="' +
      (277.66467 + 6 * i) +
      '" fill="black" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;stroke-width:0.26288685" id="tspan4072-0-0-8">' +
      i18n.t(tableRows.get(i).label) +
      "</tspan></text>" +
      '<text fill="black" xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill-opacity:1;stroke-width:0.26288685" x="' +
      13.086432 +
      '" y="' +
      (277.66467 + 0 * i) +
      '" id="galleta_texto' + i + '" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="' +
      26.086432 +
      '" y="' +
      (277.66467 + 6 * i) +
      '" fill="black" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;stroke-width:0.26288685" id="' +
      element.id +":" + i +
      '">' + 
      tableRows.get(i).value +
      "</tspan></text>";
  }

  etd_str += "</g>";

  let snapIcon = Snap.parse(etd_str);
  let el = snapDiv.group().append(snapIcon);
  return el;
}

export function getElementIcon(element, suffix="") {
  
  let iconName = "icon.svg";
  let width = 0;
  let height = 0;
  let tableRows = new Map();
  let color= "purple";

  element.elementValues.forEach(item => {
    if(item.elementTypeParam[ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_TABLE]){
      tableRows.set(parseInt(item.elementTypeParam[ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_TABLE]), {value: item.value, label: item.elementTypeParam[ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_LABEL]})
    }
    if(item.elementTypeParam[ELMENT_TYPE_PARAM_UI_GRAPHIC_PARAM_COLOR]){
      if(getTrimId(element.elementTypeState.id)!=2){
        if (item.value == 1) {
          color = "white";
        } else if (item.value == 2) {
          color = "green";    
        } else if (item.value == 3) {
          color = "gold";
        } else if (item.value == 4) {
          color = "red";
        }
      }
    }
  });
  
  if(Snap.select("#"+element.id + ":fill0") && Snap.select("#"+element.id + ":fill0").node && Snap.select("#"+element.id +":fill0").node.attributes.fill){
    Snap.select("#"+element.id + ":fill0").node.attributes.fill.value=color;
  }

  for (let i = 0; i < tableRows.size ; i++) {
    if ( document.getElementById(element.id + suffix +":"+ i) != null) {
      document.getElementById(element.id + suffix +":"+ i).innerHTML = tableRows.get(i).value;
    }
  }
  return { icon: iconName, width: width, height: height };
}

export function updateElementIcon(element) {
  // check if the element exists
  if (document.getElementById(element.id) != null) {
    let elem_icon = getElementIcon(element).icon;
    // replace the icon
    document.getElementById(element.id).setAttribute("href", "./img/" + elem_icon);
  }
}

export function updateElementState(element) {
  // check if the element exists and we are receiving an state update
  let el = document.getElementById(element.id);
  if (el != null && element.elementTypeState && element.elementTypeState.id) {
    el.setAttribute("state", getTrimId(element.elementTypeState.id));
  }
}

export function getElementSVG(element, width, height, suffix="") {
  let elementState = "";
  if(element.elementTypeState && element.elementTypeState.id) {
    elementState = getTrimId(element.elementTypeState.id);
  } 
  let iconStr = '<g id="' + element.id + suffix + '" state="' + elementState + '" transform="scale(' + (width / ELEMENT_TYPE.values[element.elementType.id].scale_x) + ',' + (height / ELEMENT_TYPE.values[element.elementType.id].scale_y) + ')">' + generateIcon(element, suffix) + '</g>';
  return '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape" xmlns:sodipodi="http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd" version="1.1" width="' + width + 'px" height="' + height + 'px">' + iconStr + '</svg>';
}
