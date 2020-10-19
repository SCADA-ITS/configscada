import { staticsIcons } from "../icons/synopticJson.js";

import * as dms from "./icons/dms.js";
import * as bar from "./icons/bar.js";
import * as etd from "./icons/etd.js";
import * as meteo from "./icons/meteo.js";
import * as lum from "./icons/lum.js";
import * as co from "./icons/co.js";
import * as opac from "./icons/opac.js";
import * as anem_int from "./icons/anem_int.js";
import * as fan from "./icons/fan.js";
import * as zone from "./icons/zone.js";
import * as sos from "./icons/sos.js";

import * as fire from "./icons/fire.js";
import * as meg from "./icons/meg.js";
import * as cam from "./icons/cam.js";
import * as plc from "./icons/plc.js";
import * as scn from "./icons/scn.js";
import * as atc from "./icons/atc.js";
import * as toll from "./icons/toll.js";
import * as nrs from "./icons/nrs.js";
import * as nrl from "./icons/nrl.js";
import * as tfa3d from "./icons/tfa3d.js";
import * as tfa from "./icons/tfa.js";
import * as tu from "./icons/tu.js";
import * as matrix_panel from "./icons/matrix_panel.js";
import * as full_matrix_panel from "./icons/full_matrix_panel.js";

import * as elementTypeMap from "./elementTypeMap.js";
import {ELEMENT_TYPE, ICON_COLOR} from "../data/GLOBAL_PROPERTIES.js";


export function generateIcon(element, suffix="", state="", values) {
  if (element.elementType.id === ELEMENT_TYPE.ID_10_DMS) {
    return generateDMSIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_24_PMV_A_C
      || element.elementType.id === ELEMENT_TYPE.ID_26_PMV_64X64
      || element.elementType.id === ELEMENT_TYPE.ID_27_PMV_A_B
      || element.elementType.id === ELEMENT_TYPE.ID_28_PMV_192X64
      || element.elementType.id === ELEMENT_TYPE.ID_29_PMV_B_D
      || element.elementType.id === ELEMENT_TYPE.ID_30_PMV_128X64
      || element.elementType.id === ELEMENT_TYPE.ID_31_PMV
      || element.elementType.id === ELEMENT_TYPE.ID_32_PMV
      || element.elementType.id === ELEMENT_TYPE.ID_35_PMV
      || element.elementType.id === ELEMENT_TYPE.ID_36_PMV) {
    return generateFullMatrixPanelIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_22_ETD_MODBUS
      || element.elementType.id === ELEMENT_TYPE.ID_23_ETD_DETECTOR) {
    return generateETDIcon(element, suffix, state, values);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_12_LUX) {
    return generateLuxIcon(element, suffix);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_14_LUM) {
    return generateLumIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_19_CO) {
    return generateCoIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_20_NO) {
    return generateNoIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_21_OPAC) {
    return generateOpacIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_18_ANEM_INT) {
    return generateAnemIntIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_17_ANEM_EXT) {
    return generateAnemExtIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_9_FAN) {
    return generateFanIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_8_BAR) {
    return generateBarIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_13_SEM
    || element.elementType.id === ELEMENT_TYPE.ID_53_SEM) {
    return generateSemIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_7_ZONE) {
    return generateZoneIcon(element, suffix, state);
  } else{
    return null;
  }
}

export function generateDMSIcon(element, suffix="", state="") {
  let pmv_str = dms.generateIcon(element, suffix, state);
  return pmv_str;
}

export function generateMatrixPanelIcon(element, suffix="", state="") {
  let matrix_panel_str = matrix_panel.generateIcon(element, suffix, state);
  return matrix_panel_str;
}

export function generateFullMatrixPanelIcon(element, suffix="", state="") {
  let full_matrix_panel_str = full_matrix_panel.generateIcon(element, suffix, state);
  return full_matrix_panel_str;
}

export function generateETDIcon(element, suffix="", state="",values) {
  let etd_str = etd.generateIcon(element, values, suffix, state);
  return etd_str;
}



export function generateLuxIcon(element, suffix="") {
  return createIcon(element, suffix);
}

export function generateLumIcon(element, suffix="", state="") {
  let lum_str = lum.generateIcon(element, suffix, state);
  return lum_str;
}

export function generateCoIcon(element, suffix="", state="") {
  let co_str = co.generateIcon(element, suffix, state);
  return co_str;
}

export function generateNoIcon(element, suffix="") {
  return createIcon(element, suffix);
}

export function generateOpacIcon(element, suffix="", state="") {
  let opac_str = opac.generateIcon(element, suffix, state);
  return opac_str;
}

export function generateAnemIntIcon(element, suffix="", state="") {
  return createIcon(element, suffix);

}

export function generateAnemExtIcon(element, suffix="", state="") {
  return createIcon(element, suffix);
}

export function generateFanIcon(element, suffix="", state="") {
  let fan_str = fan.generateIcon(element, suffix, state);
  return fan_str;
}

export function generateBarIcon(element, suffix="", state="") {
  let bar_str = bar.generateIcon(element, suffix, state);
  return bar_str;
}

export function generateSemIcon(element, suffix="") {
  return createIcon(element, suffix);
}

export function generateZoneIcon(element, suffix="", state="") {
  let zone_str = zone.generateIcon(element, suffix, state);
  return zone_str;
}


export function getElementIcon(element, suffix="") {

  let elem_icon = "";
  let width = 0;
  let height = 0;
  
  let elementType = elementTypeMap.getElementType(element.id);
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
      (ic) => ic.state === elementState
    );
  } else {
    elem_icon = staticsIcons[elementTypeMap.ELEMENT_TYPE_33_ETD]["state"].find(
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
  if (elementType === elementTypeMap.ELEMENT_TYPE_9_FAN) {
    fan.updateFanIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_8_BAR) {
    bar.updateBarIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_7_ZONE) {
    zone.updateZoneIcon(element, suffix);
  } else if(elementType === elementTypeMap.ELEMENT_TYPE_12_LUX) {
    updateIcon(element, suffix);
  }  else if(elementType === elementTypeMap.ELEMENT_TYPE_14_LUM) {
    lum.updateLumIcon(element, suffix);
  } else if(elementType === elementTypeMap.ELEMENT_TYPE_17_ANEM_EXT) {
    updateIcon(element, suffix);
  } else if(elementType === elementTypeMap.ELEMENT_TYPE_18_ANEM_INT) {
    anem_int.updateAnemIntIcon(element, suffix);
  } else if(elementType === elementTypeMap.ELEMENT_TYPE_19_CO) {
    co.updateCoIcon(element, suffix);
  } else if(elementType === elementTypeMap.ELEMENT_TYPE_20_NO) {
    updateIcon(element, suffix);
  } else if(elementType === elementTypeMap.ELEMENT_TYPE_21_OPAC) {
    opac.updateOpacIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_23_ETD_DETECTOR) {
    etd.updateEtdIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_1_WS) {
    meteo.updateMeteoIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_11_SOS) {
    sos.updateSosIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_13_SEM
    || elementType === elementTypeMap.ELEMENT_TYPE_53_SEM) {
    updateIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_15_FIRE) {
    fire.updateFireIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_16_MEG) {
    meg.updateMegIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_10_DMS) {
    dms.updateDmsIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_2_CAM) {
    cam.updateCamIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_5_PLC) {
    plc.updatePlcIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_25_CABIN) {
    plc.updatePlcIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_34_CABIN_SCN) {
    scn.updateScnIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_24_PMV_A_C) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_26_PMV_64X64) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_27_PMV_A_B) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_28_PMV_192X64) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  }  else if (elementType === elementTypeMap.ELEMENT_TYPE_29_PMV_B_D) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  }  else if (elementType === elementTypeMap.ELEMENT_TYPE_30_PMV_128X64) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  }  else if (elementType === elementTypeMap.ELEMENT_TYPE_31_PMV) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  }  else if (elementType === elementTypeMap.ELEMENT_TYPE_32_PMV) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  }  else if (elementType === elementTypeMap.ELEMENT_TYPE_35_PMV) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_36_PMV) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_41_CABIN_ATC) {
    atc.updateAtcIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_46_CABIN_TOLL) {
    toll.updateTollIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_43_CABIN_NRS) {
    nrs.updateNrsIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_42_CABIN_NRL) {
    nrl.updateNrlIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_44_CABIN_TFA3D) {
    tfa3d.updateTfa3dIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_45_CABIN_TFA) {
    tfa.updateTfaIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_47_CABIN_TU) {
    tu.updateTuIcon(element, suffix);
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

export function getElementSVG(element, width, height, suffix="", state="", values=[]) {
  let elementType = element.id.split(":")[1];

  let iconStr = "";
  switch (elementType) {
    case elementTypeMap.ELEMENT_TYPE_1_WS:
    case elementTypeMap.ELEMENT_TYPE_2_CAM:
    case elementTypeMap.ELEMENT_TYPE_3_DET:
    case elementTypeMap.ELEMENT_TYPE_4_SEC:
    case elementTypeMap.ELEMENT_TYPE_5_PLC:
    case elementTypeMap.ELEMENT_TYPE_11_SOS:
    case elementTypeMap.ELEMENT_TYPE_15_FIRE:
    case elementTypeMap.ELEMENT_TYPE_16_MEG:
    case elementTypeMap.ELEMENT_TYPE_25_CABIN:
    case elementTypeMap.ELEMENT_TYPE_34_CABIN_SCN:
    case elementTypeMap.ELEMENT_TYPE_41_CABIN_ATC:
    case elementTypeMap.ELEMENT_TYPE_42_CABIN_NRL:
    case elementTypeMap.ELEMENT_TYPE_43_CABIN_NRS:
    case elementTypeMap.ELEMENT_TYPE_44_CABIN_TFA3D:
    case elementTypeMap.ELEMENT_TYPE_45_CABIN_TFA:
    case elementTypeMap.ELEMENT_TYPE_46_CABIN_TOLL:
    case elementTypeMap.ELEMENT_TYPE_47_CABIN_TU:
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case elementTypeMap.ELEMENT_TYPE_7_ZONE:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 4122.35) + ',' + (height / 3382.97) + ')">' + generateZoneIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_8_BAR:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 30.163) + ',' + (height / 16.13958) + ')">' + generateBarIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_9_FAN:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 522) + ',' + (height / 704) + ')">' + generateFanIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_10_DMS:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateDMSIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_12_LUX:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateLuxIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_13_SEM:
    case elementTypeMap.ELEMENT_TYPE_53_SEM:
          iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 8.752) + ',' + (height / 10.247) + ')">' + generateSemIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_14_LUM:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateLumIcon(element, suffix, state) + '</g>';
      break;
   case elementTypeMap.ELEMENT_TYPE_17_ANEM_EXT:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 19.063) + ')">' + generateAnemExtIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_18_ANEM_INT:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 19.063) + ')">' + generateAnemIntIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_19_CO:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateCoIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_20_NO:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateNoIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_21_OPAC:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateOpacIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_22_ETD_MODBUS:
    case elementTypeMap.ELEMENT_TYPE_23_ETD_DETECTOR:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateETDIcon(element, suffix, state, values) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_24_PMV_A_C:
    case elementTypeMap.ELEMENT_TYPE_26_PMV_64X64:
    case elementTypeMap.ELEMENT_TYPE_32_PMV:  
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 64) + ',' + (height / 64) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_28_PMV_192X64:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 200) + ',' + (height / 48) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_35_PMV:
    case elementTypeMap.ELEMENT_TYPE_36_PMV:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 200) + ',' + (height / 48) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_27_PMV_A_B:
    case elementTypeMap.ELEMENT_TYPE_29_PMV_B_D:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_30_PMV_128X64:
    case elementTypeMap.ELEMENT_TYPE_31_PMV:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateMatrixPanelIcon(element, suffix, state) + '</g>';
      break;                              
    case elementTypeMap.ELEMENT_TYPE_33_ETD:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateETDIcon(element, suffix, state, values) + '</g>';
      break;      
    default:
      console.log("Icon not found");
      break;
  }

  return '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape" xmlns:sodipodi="http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd" version="1.1" width="' + width + 'px" height="' + height + 'px">' + iconStr + '</svg>';
}

export function drawInlineSVG(canvas, ctx, rawSVG, callback) {
  let svg64 = rawSVG;
  let b64Start = 'data:image/svg+xml;utf8,';

  // prepend a "header"
  let image64 = b64Start + svg64;

  let img = new Image;
  img.src = image64;

  img.onload = function () {
    ctx.drawImage(this, 0, 0);
    callback(this);
  };

  img.onerror = function(e) {
    console.log("ERROR: ")
    console.log(e);
  }  
}

function createIcon(element, suffix="") {
  let elementId= element.id+suffix;
  let color = ICON_COLOR[element.state];
  //svg icon String
  let iconStr = ELEMENT_TYPE.values[element.elementType.id].svgRaw;
  let params={
    "elementId": elementId,
    "color": color
  };
  //param values
  if(ELEMENT_TYPE.values[element.elementType.id].elementTypeParams){
    ELEMENT_TYPE.values[element.elementType.id].elementTypeParams.forEach(item => {
      let key = Object.keys(item)[0];
      let tempValue = element.elementValues.find(
        (elemValue) => elementTypeMap.getElementTypeParam(elemValue.id) == item[key]
      );
      if(tempValue){
        params[key]=tempValue.value;
      }
    });
  }
  return prepareStr(iconStr, params);
}

function updateIcon(element, suffix="") {
  // changes the color based on the status
  if (typeof element.state != 'undefined' && element.state) {
    let color = ICON_COLOR[element.state];
    if(document.getElementById(element.id + suffix + ":IconBackground") != null){
      document.getElementById(element.id + suffix + ":IconBackground").style["stroke"] = color;
    }
    if(document.getElementById(element.id + suffix + ":ValueBackground") != null){
      document.getElementById(element.id + suffix + ":ValueBackground").style["stroke"] = color;
    }
  }
  // change params
  if(ELEMENT_TYPE.values[element.elementType.id].elementTypeParams){
    let params={};
    ELEMENT_TYPE.values[element.elementType.id].elementTypeParams.forEach(item => {
      let key = Object.keys(item)[0];
      let tempValue = element.elementValues.find(
        (elemValue) => elementTypeMap.getElementTypeParam(elemValue.id) == item[key]
      );
      if(typeof tempValue != 'undefined' && tempValue && document.getElementById(element.id + suffix + ":Value") != null){
        params[key]=tempValue.value;
        document.getElementById(element.id + suffix + ":Value").innerHTML = tempValue.value;
      }
    });
  }
  // specific case
  if(element.elementType.id === ELEMENT_TYPE.ID_13_SEM){
    if (typeof params != 'undefined' && params) {
      if (params[SEM_STATUS].value == "0") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "hidden";
      } else if (params[SEM_STATUS].value == "1") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "hidden";
      } else if (params[SEM_STATUS].value == "2") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "hidden";
      } else if (params[SEM_STATUS].value == "3") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "visible";
      }
    }
  }
}
