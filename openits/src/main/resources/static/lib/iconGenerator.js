import { staticsIcons } from "../icons/synopticJson.js";

import * as meteo from "./icons/meteo.js";
import * as meg from "./icons/meg.js";
import * as cam from "./icons/cam.js";
import * as dms from "./icons/dms.js";
import * as etd from "./icons/etd.js";
import * as matrix_panel from "./icons/matrix_panel.js";
import * as full_matrix_panel from "./icons/full_matrix_panel.js";
import * as matrix_json from "./icons/matrix_json.js";

import * as vis from "./icons/vis.js";
import * as accel from "./icons/accel.js";
import * as radar from "./icons/accel.js";

import * as elementTypeMap from "./elementTypeMap.js";
import {ELEMENT_TYPE, ICON_COLOR} from "../data/GLOBAL_PROPERTIES.js";


export function generateIcon(element, suffix="", state="", values) {
  if (element.elementType.id === ELEMENT_TYPE.ID_10_DMS) {
    return generateDMSIcon(element, suffix, state);
  } else if (ELEMENT_TYPE.values[element.elementType.id].uiType === UI_ELEMENT_TYPE_MATRIX) {
    return generateFullMatrixPanelIcon(element, suffix, state);
  } else if (ELEMENT_TYPE.values[element.elementType.id].uiType === UI_ELEMENT_TYPE_MTX) {
    return generateMatrixJsonIcon(element, suffix, state);
  } else if (element.elementType.id === ELEMENT_TYPE.ID_4_SEC) {  
    return generateETDIcon(element, suffix, state, values);
  } else if (ELEMENT_TYPE.values[element.elementType.id].uiType === UI_ELEMENT_TYPE_GENERIC) {
    return createIcon(element, suffix);
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

export function generateMatrixJsonIcon(element, suffix="", state="") {
  let full_matrix_panel_str = matrix_json.generateIcon(element, suffix, state);
  return full_matrix_panel_str;
}

export function generateETDIcon(element, suffix="", state="",values) {
  let etd_str = etd.generateIcon(element, values, suffix, state);
  return etd_str;
}

export function getElementIcon(element, suffix="") {

  let elem_icon = "";
  let width = 0;
  let height = 0;
  
  let elementType = elementTypeMap.getElementType(element.id);
  let elementState = "";
  
  if (document.getElementById(element.id) != null) {
    elementState = parseInt(document.getElementById(element.id).getAttribute("state"));
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
  if (elementType === elementTypeMap.ELEMENT_TYPE_9_FAN
    || elementType === elementTypeMap.ELEMENT_TYPE_3_DET
    || elementType === elementTypeMap.ELEMENT_TYPE_8_BAR
    || elementType === elementTypeMap.ELEMENT_TYPE_7_ZONE
    || elementType === elementTypeMap.ELEMENT_TYPE_11_SOS
    || elementType === elementTypeMap.ELEMENT_TYPE_12_LUX
    || elementType === elementTypeMap.ELEMENT_TYPE_13_SEM_RAV
    || elementType === elementTypeMap.ELEMENT_TYPE_14_LUM
    || elementType === elementTypeMap.ELEMENT_TYPE_15_FIRE
    || elementType === elementTypeMap.ELEMENT_TYPE_17_ANEM_EXT
    || elementType === elementTypeMap.ELEMENT_TYPE_18_ANEM_INT
    || elementType === elementTypeMap.ELEMENT_TYPE_19_CO
    || elementType === elementTypeMap.ELEMENT_TYPE_20_NO
    || elementType === elementTypeMap.ELEMENT_TYPE_21_OPAC
    || elementType === elementTypeMap.ELEMENT_TYPE_25_CABIN
    || elementType === elementTypeMap.ELEMENT_TYPE_34_CABIN_SCN
    || elementType === elementTypeMap.ELEMENT_TYPE_41_CABIN_ATC
    || elementType === elementTypeMap.ELEMENT_TYPE_42_CABIN_NRL
    || elementType === elementTypeMap.ELEMENT_TYPE_43_CABIN_NRS
    || elementType === elementTypeMap.ELEMENT_TYPE_44_CABIN_TFA3D
    || elementType === elementTypeMap.ELEMENT_TYPE_45_CABIN_TFA
    || elementType === elementTypeMap.ELEMENT_TYPE_46_CABIN_TOLL
    || elementType === elementTypeMap.ELEMENT_TYPE_47_CABIN_TU
    || elementType === elementTypeMap.ELEMENT_TYPE_51_ETD
    || elementType === elementTypeMap.ELEMENT_TYPE_53_SEM_AA
    || elementType === elementTypeMap.ELEMENT_TYPE_54_ETD_KINEO
    || elementType === elementTypeMap.ELEMENT_TYPE_55_CT
    || elementType === elementTypeMap.ELEMENT_TYPE_56_UNIFILAR
    || elementType === elementTypeMap.ELEMENT_TYPE_57_MAGNETOTERMICO
    || elementType === elementTypeMap.ELEMENT_TYPE_58_PULSADOR
    || elementType === elementTypeMap.ELEMENT_TYPE_59_SMOKE_DETECTOR
    || elementType === elementTypeMap.ELEMENT_TYPE_60_SIRENA
    || elementType === elementTypeMap.ELEMENT_TYPE_61_FIRE_DET_CT
    || elementType === elementTypeMap.ELEMENT_TYPE_62_TEMP_PROBE
    || elementType === elementTypeMap.ELEMENT_TYPE_63_AIR_CONDITIONER
    || elementType === elementTypeMap.ELEMENT_TYPE_65_SAI
    || elementType === elementTypeMap.ELEMENT_TYPE_66_VANE
    || elementType === elementTypeMap.ELEMENT_TYPE_67_SEM_RR
    || elementType === elementTypeMap.ELEMENT_TYPE_70_ANALIZADOR_RED) {
    updateIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_4_SEC) {
    etd.updateEtdIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_1_WS) {
    meteo.updateMeteoIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_16_MEG) {
    meg.updateMegIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_10_DMS) {
    dms.updateDmsIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_2_CAM) {
    cam.updateCamIcon(element, suffix);
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
    matrix_json.updateMatrixPanelIcon(element, suffix);
  }  else if (elementType === elementTypeMap.ELEMENT_TYPE_31_PMV) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  }  else if (elementType === elementTypeMap.ELEMENT_TYPE_32_PMV) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  }  else if (elementType === elementTypeMap.ELEMENT_TYPE_35_PMV) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_36_PMV) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_64_VMS_SNOW) {
      full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_69_PMV) {
	  full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_71_VIS) {
    vis.updateVisIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_72_ACCEL) {
    accel.updateAccelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELEMENT_TYPE_73_RADAR) {
    radar.updateRadarIcon(element, suffix);
  } else {
    if (typeof staticsIcons[elementType] != "undefined") {
      elem_icon = staticsIcons[elementType]["state"].find(
        (ic) => ic.state === elementState
      );
    } else {
      elem_icon = staticsIcons["33"]["state"].find(
        (ic) => ic.state === elementState
      );
    }
    if (typeof elem_icon != "undefined") {
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
    document.getElementById(element.id).setAttribute("href", "./img/" + elem_icon);
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
    case elementTypeMap.ELEMENT_TYPE_5_PLC:
    case elementTypeMap.ELEMENT_TYPE_16_MEG:
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case elementTypeMap.ELEMENT_TYPE_11_SOS:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 250) + ',' + (height / 250) + ')">' + createIcon(element, suffix) + '</g>';
    break; 
    case elementTypeMap.ELEMENT_TYPE_15_FIRE:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 100) + ',' + (height / 25) + ')">' + createIcon(element, suffix) + '</g>';
    break; 
    case elementTypeMap.ELEMENT_TYPE_7_ZONE:      
    case elementTypeMap.ELEMENT_TYPE_8_BAR:
    case elementTypeMap.ELEMENT_TYPE_12_LUX:
    case elementTypeMap.ELEMENT_TYPE_13_SEM_RAV:
    case elementTypeMap.ELEMENT_TYPE_14_LUM:
    case elementTypeMap.ELEMENT_TYPE_17_ANEM_EXT:
    case elementTypeMap.ELEMENT_TYPE_18_ANEM_INT:
    case elementTypeMap.ELEMENT_TYPE_19_CO:
    case elementTypeMap.ELEMENT_TYPE_20_NO:
    case elementTypeMap.ELEMENT_TYPE_21_OPAC:
    case elementTypeMap.ELEMENT_TYPE_25_CABIN:
    case elementTypeMap.ELEMENT_TYPE_34_CABIN_SCN:
    case elementTypeMap.ELEMENT_TYPE_41_CABIN_ATC:
    case elementTypeMap.ELEMENT_TYPE_42_CABIN_NRL:
    case elementTypeMap.ELEMENT_TYPE_43_CABIN_NRS:
    case elementTypeMap.ELEMENT_TYPE_44_CABIN_TFA3D:
    case elementTypeMap.ELEMENT_TYPE_45_CABIN_TFA:
    case elementTypeMap.ELEMENT_TYPE_46_CABIN_TOLL:
    case elementTypeMap.ELEMENT_TYPE_47_CABIN_TU:
    case elementTypeMap.ELEMENT_TYPE_55_CT:
    case elementTypeMap.ELEMENT_TYPE_59_SMOKE_DETECTOR:         
    case elementTypeMap.ELEMENT_TYPE_65_SAI:
    case elementTypeMap.ELEMENT_TYPE_66_VANE:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / ELEMENT_TYPE.values[element.elementType.id].scale_x) + ',' + (height / ELEMENT_TYPE.values[element.elementType.id].scale_y) + ')">' + createIcon(element, suffix) + '</g>';
      break; 
   case elementTypeMap.ELEMENT_TYPE_53_SEM_AA:
   case elementTypeMap.ELEMENT_TYPE_67_SEM_RR:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 15) + ',' + (height / 15) + ')">' + createIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_9_FAN:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 522) + ',' + (height / 704) + ')">' + createIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_10_DMS:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateDMSIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_4_SEC:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateETDIcon(element, suffix, state, values) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_24_PMV_A_C:
    case elementTypeMap.ELEMENT_TYPE_26_PMV_64X64:
    case elementTypeMap.ELEMENT_TYPE_32_PMV:  
    case elementTypeMap.ELEMENT_TYPE_31_PMV:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 64) + ',' + (height / 64) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_28_PMV_192X64:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 200) + ',' + (height / 48) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_35_PMV:
    case elementTypeMap.ELEMENT_TYPE_36_PMV:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 200) + ',' + (height / 100) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_27_PMV_A_B:
    case elementTypeMap.ELEMENT_TYPE_29_PMV_B_D:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_30_PMV_128X64:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateMatrixJsonIcon(element, suffix, state) + '</g>';
      break;                              
    case elementTypeMap.ELEMENT_TYPE_3_DET:      
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 5000) + ',' + (height / 5000) + ')">' + createIcon(element, suffix) + '</g>';
      break; 
    case elementTypeMap.ELEMENT_TYPE_54_ETD_KINEO:    
    case elementTypeMap.ELEMENT_TYPE_51_ETD:     
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 18) + ',' + (height / 18) + ')">' + createIcon(element, suffix) + '</g>';
      break; 
    case elementTypeMap.ELEMENT_TYPE_56_UNIFILAR:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 290) + ',' + (height / 290) + ')">' + createIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_57_MAGNETOTERMICO:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 128) + ',' + (height / 128) + ')">' + createIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_58_PULSADOR:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 25) + ',' + (height / 25) + ')">' + createIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_60_SIRENA:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 40) + ',' + (height / 40) + ')">' + createIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_61_FIRE_DET_CT:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 80) + ',' + (height / 80) + ')">' + createIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_64_VMS_SNOW:
        iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 64) + ',' + (height / 64) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_69_PMV:
        iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 128) + ',' + (height / 128) + ')">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_70_ANALIZADOR_RED:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 220) + ',' + (height / 220) + ')">' + createIcon(element, suffix) + '</g>';
      break;    
    case elementTypeMap.ELEMENT_TYPE_71_VIS:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 602) + ',' + (height / 503) + ')">' + createIcon(element, suffix) + '</g>';
      break;  
    case elementTypeMap.ELEMENT_TYPE_72_ACCEL:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 369) + ',' + (height / 248) + ')">' + createIcon(element, suffix) + '</g>';
      break;
    case elementTypeMap.ELEMENT_TYPE_73_RADAR:
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + createIcon(element, suffix) + '</g>';
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
  let elementTypeAlias = $.i18n(element.elementType.id + ".alias");
  let elementAlias = element.alias;
  let color = ICON_COLOR[element.state];
  // override generic color with ElementType color if exist
  if(typeof ELEMENT_TYPE.values[element.elementType.id].color != 'undefined' && ELEMENT_TYPE.values[element.elementType.id].color){
    color = ELEMENT_TYPE.values[element.elementType.id].color[element.state];
  }
  //svg icon String
  let iconStr = ELEMENT_TYPE.values[element.elementType.id].svgRaw;
  let params={
    "elementId": elementId,
    "color": color,
    "elementAlias": elementAlias,
    "elementTypeAlias": elementTypeAlias
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
    // override generic color with ElementType color if exist
    if(typeof ELEMENT_TYPE.values[element.elementType.id].color != 'undefined' && ELEMENT_TYPE.values[element.elementType.id].color){
      color = ELEMENT_TYPE.values[element.elementType.id].color[element.state];
    }
    if(document.getElementById(element.id + suffix + ":IconBackground") != null){
      document.getElementById(element.id + suffix + ":IconBackground").style["stroke"] = color;
    }
    if(document.getElementById(element.id + suffix + ":ValueBackground") != null){
      document.getElementById(element.id + suffix + ":ValueBackground").style["stroke"] = color;
    }
    for (let index = 0; index < 10; index++) {
      if(document.getElementById(element.id + suffix + ":fill"+index) != null){
        document.getElementById(element.id + suffix + ":fill"+index).style["fill"] = color;    
      }
    }
    for (let index = 0; index < 5; index++) {
      if(document.getElementById(element.id + suffix + ":stroke"+index) != null){
        document.getElementById(element.id + suffix + ":stroke"+index).style["stroke"] = color;    
      }
    }
  }

  // change params
  let params;
  if(ELEMENT_TYPE.values[element.elementType.id].elementTypeParams){
    params={};
    ELEMENT_TYPE.values[element.elementType.id].elementTypeParams.forEach(item => {
      let key = Object.keys(item)[0];
      let tempValue;
      if(element.elementValues){
        tempValue = element.elementValues.find(
          (elemValue) => elementTypeMap.getElementTypeParam(elemValue.id) == item[key]
        );        
      }
      if(tempValue != undefined && tempValue){
        params[key]=tempValue.value; 
      }else{
        params[key]=null;
      }
      if(document.getElementById(element.id + suffix + ":Value") != null){
        document.getElementById(element.id + suffix + ":Value").innerHTML = params[key];
      }
    });
  }

  // specific case
  if(element.elementType.id === ELEMENT_TYPE.ID_8_BAR){
    if (typeof params != 'undefined' && params && params["BAR_COMMAND_FOR_BARRIER"]
      && document.getElementById(element.id + suffix + "g3856")!=null) {
      if (params["BAR_COMMAND_FOR_BARRIER"] == "1") {
        document.getElementById(element.id + suffix + "g3856").setAttribute("transform", "rotate(0,0,0)");
      } else if (params["BAR_COMMAND_FOR_BARRIER"] == "0") {
        document.getElementById(element.id + suffix + "g3856").setAttribute("transform", "rotate(22.188461,3.5567155,291.27864)");
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_9_FAN){
    if (params != undefined) {
      if (params["FAN_COMMAND_FOR_FAN"] == 0) {
        document.getElementById(element.id + suffix + ":Animation").setAttribute("dur", "0");
        document.getElementById(element.id + suffix + ":IconMD").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconMI").style["visibility"] = "hidden";
      } else if (params["FAN_COMMAND_FOR_FAN"] == 1) {
        document.getElementById(element.id + suffix + ":Animation").setAttribute("dur", "6s");
        document.getElementById(element.id + suffix + ":IconArrowMD").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + ":IconArrowMI").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconMD").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + ":IconMI").style["visibility"] = "hidden";
      } else if (params["FAN_COMMAND_FOR_FAN"] == 2) {
        document.getElementById(element.id + suffix + ":Animation").setAttribute("dur", "6s");
        document.getElementById(element.id + suffix + ":IconArrowMD").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconArrowMI").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + ":IconMD").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconMI").style["visibility"] = "visible";
      }else {
        document.getElementById(element.id + suffix + ":Animation").setAttribute("dur", "0");
        document.getElementById(element.id + suffix + ":IconMD").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + ":IconMI").style["visibility"] = "hidden";
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_13_SEM_RAV){
    if (typeof params != 'undefined' && params && params["SEM_STATUS"]) {
      if (params["SEM_STATUS"] == "0") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "hidden";
      } else if (params["SEM_STATUS"] == "1") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "hidden";
      } else if (params["SEM_STATUS"] == "2") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "hidden";
      } else if (params["SEM_STATUS"] == "3") {
        document.getElementById(element.id + suffix + "green").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "yellow").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "red").style["visibility"] = "visible";
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_53_SEM_AA){
    if (params != undefined && params && params["SEM_AMBAR_AMBAR_STATUS"]) {
      let svgAmbar = document.getElementById(element.id + suffix);
      if (params["SEM_AMBAR_AMBAR_STATUS"] == 'true') {
        svgAmbar.children[0].children[0].children[1].children[1].style.display = "block"
      } else if (params["SEM_AMBAR_AMBAR_STATUS"] == 'false'){
        svgAmbar.children[0].children[0].children[1].children[1].style.display = "none"
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_67_SEM_RR){
    if (params != undefined && params && params["SEM_ROJO_ROJO_STATUS"]) {
      let svgAmbar = document.getElementById(element.id + suffix);
      if (params["SEM_ROJO_ROJO_STATUS"] == 'true') {
        document.getElementById(element.id + suffix + "ligth1").style["visibility"] = "visible";
        document.getElementById(element.id + suffix + "ligth2").style["visibility"] = "visible";
      } else if (params["SEM_ROJO_ROJO_STATUS"] == 'false'){
        document.getElementById(element.id + suffix + "ligth1").style["visibility"] = "hidden";
        document.getElementById(element.id + suffix + "ligth2").style["visibility"] = "hidden";
      }
    }
  } else if(element.elementType.id === ELEMENT_TYPE.ID_57_MAGNETOTERMICO){
    if (params != undefined && params && params["MAGNETOTERMICO_STATE"]) {
      if (params["MAGNETOTERMICO_STATE"]=="false") {
        if(Snap.select("#"+element.id + suffix +":transform") && Snap.select("#"+element.id + suffix +":transform").node && Snap.select("#"+element.id + suffix +":transform").node.attributes.transform && Snap.select("#"+element.id + suffix +":transform").node.attributes.x && Snap.select("#"+element.id + suffix +":transform").node.attributes.y){
          Snap.select("#"+element.id + suffix +":transform").node.attributes.transform.value="rotate(-24.280147)";
          Snap.select("#"+element.id + suffix +":transform").node.attributes.x.value="5.2098141";
          Snap.select("#"+element.id + suffix +":transform").node.attributes.y.value="12.825212";
        }
       } else{ 
        if(Snap.select("#"+element.id + suffix +":transform") && Snap.select("#"+element.id + suffix +":transform").node && Snap.select("#"+element.id + suffix +":transform").node.attributes.transform && Snap.select("#"+element.id + suffix +":transform").node.attributes.x && Snap.select("#"+element.id + suffix +":transform").node.attributes.y){
          Snap.select("#"+element.id + suffix +":transform").node.attributes.transform.value="rotate(0.11021549)";
          Snap.select("#"+element.id + suffix +":transform").node.attributes.x.value="15.112525";
          Snap.select("#"+element.id + suffix +":transform").node.attributes.y.value="8.1191225";
        } 
      } 
    }
  } 
}
