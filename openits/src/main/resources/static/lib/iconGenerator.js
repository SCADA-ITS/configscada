import { staticsIcons } from "../icons/synopticJson.js";

import * as dms from "./icons/dms.js";
import * as bar from "./icons/bar.js";
import * as etd from "./icons/etd.js";
import * as meteo from "./icons/meteo.js";
import * as lux from "./icons/lux.js";
import * as lum from "./icons/lum.js";
import * as co from "./icons/co.js";
import * as no from "./icons/no.js";
import * as opac from "./icons/opac.js";
import * as anem_int from "./icons/anem_int.js";
import * as anem_ext from "./icons/anem_ext.js";
import * as fan from "./icons/fan.js";
import * as zone from "./icons/zone.js";
import * as sos from "./icons/sos.js";
import * as sem from "./icons/sem.js";
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
import * as matrix_panel from "./icons/matrix_panel.js";
import * as full_matrix_panel from "./icons/full_matrix_panel.js";

import * as elementTypeMap from "./elementTypeMap.js";

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

export function generateETDIcon(element, values, suffix="", state="") {
  let etd_str = etd.generateIcon(element, values, suffix, state);
  return etd_str;
}

export function generateMeteoIcon(element, suffix="", state="") {
  let meteo_str = meteo.generateIcon(element, suffix, state);    
  return meteo_str;
}

export function generateLuxIcon(element, suffix="", state="") {
  let lux_str = lux.generateIcon(element, suffix, state);
  return lux_str;
}

export function generateLumIcon(element, suffix="", state="") {
  let lum_str = lum.generateIcon(element, suffix, state);
  return lum_str;
}

export function generateCoIcon(element, suffix="", state="") {
  let co_str = co.generateIcon(element, suffix, state);
  return co_str;
}

export function generateNoIcon(element, suffix="", state="") {
  let no_str = no.generateIcon(element, suffix, state);
  return no_str;
}

export function generateOpacIcon(element, suffix="", state="") {
  let opac_str = opac.generateIcon(element, suffix, state);
  return opac_str;
}

export function generateAnemIntIcon(element, suffix="", state="") {
  let anem_int_str = anem_int.generateIcon(element, suffix, state);
  return anem_int_str;
}

export function generateAnemExtIcon(element, suffix="", state="") {
  let anem_ext_str = anem_ext.generateIcon(element, suffix, state);
  return anem_ext_str;
}

export function generateFanIcon(element, suffix="", state="") {
  let fan_str = fan.generateIcon(element, suffix, state);
  return fan_str;
}

export function generateBarIcon(element, suffix="", state="") {
  let bar_str = bar.generateIcon(element, suffix, state);
  return bar_str;
}

export function generateSemIcon(element, suffix="", state="") {
  let sem_str = sem.generateIcon(element, suffix, state);
  return sem_str;
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
    elem_icon = staticsIcons[elementTypeMap.ETD_ELEMENT_TYPE]["state"].find(
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
  if (elementType === elementTypeMap.FAN_ELEMENT_TYPE) {
    fan.updateFanIcon(element, suffix);
  } else if (elementType === elementTypeMap.BAR_ELEMENT_TYPE) {
    bar.updateBarIcon(element, suffix);
  } else if (elementType === elementTypeMap.ZONE_ELEMENT_TYPE) {
    zone.updateZoneIcon(element, suffix);
  } else if(elementType === elementTypeMap.LUX_ELEMENT_TYPE) {
    lux.updateLuxIcon(element, suffix);
  }  else if(elementType === elementTypeMap.LUM_ELEMENT_TYPE) {
    lum.updateLumIcon(element, suffix);
  } else if(elementType === elementTypeMap.ANEM_EXT_ELEMENT_TYPE) {
    anem_ext.updateAnemExtIcon(element, suffix);
  } else if(elementType === elementTypeMap.ANEM_INT_ELEMENT_TYPE) {
    anem_int.updateAnemIntIcon(element, suffix);
  } else if(elementType === elementTypeMap.CO_ELEMENT_TYPE) {
    co.updateCoIcon(element, suffix);
  } else if(elementType === elementTypeMap.NO_ELEMENT_TYPE) {
    no.updateNoIcon(element, suffix);
  } else if(elementType === elementTypeMap.OPAC_ELEMENT_TYPE) {
    opac.updateOpacIcon(element, suffix);
  } else if (elementType === elementTypeMap.ETD_DETECTOR_ELEMENT_TYPE) {
    etd.updateEtdIcon(element, suffix);
  } else if (elementType === elementTypeMap.WS_ELEMENT_TYPE) {
    meteo.updateMeteoIcon(element, suffix);
  } else if (elementType === elementTypeMap.SOS_ELEMENT_TYPE) {
    sos.updateSosIcon(element, suffix);
  } else if (elementType === elementTypeMap.SEM_ELEMENT_TYPE) {
    sem.updateSemIcon(element, suffix);
  } else if (elementType === elementTypeMap.FIRE_ELEMENT_TYPE) {
    fire.updateFireIcon(element, suffix);
  } else if (elementType === elementTypeMap.MEG_ELEMENT_TYPE) {
    meg.updateMegIcon(element, suffix);
  } else if (elementType === elementTypeMap.DMS_ELEMENT_TYPE) {
    dms.updateDmsIcon(element, suffix);
  } else if (elementType === elementTypeMap.CAM_ELEMENT_TYPE) {
    cam.updateCamIcon(element, suffix);
  } else if (elementType === elementTypeMap.PLC_ELEMENT_TYPE) {
    plc.updatePlcIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELECTRICAL_CABINET_ELEMENT_TYPE) {
    plc.updatePlcIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELECTRICAL_CABINET_SCN_ELEMENT_TYPE) {
    scn.updateScnIcon(element, suffix);
  } else if (elementType === elementTypeMap.MATRIX_PANEL_64X64) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.MATRIX_PANEL_192X64) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.PMV_A_C) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.MATRIX_PANEL_DGT) {
    full_matrix_panel.updateMatrixPanelIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELECTRICAL_CABINET_ATC_ELEMENT_TYPE) {
    atc.updateAtcIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELECTRICAL_CABINET_TOLL_ELEMENT_TYPE) {
    toll.updateTollIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELECTRICAL_CABINET_NRS_ELEMENT_TYPE) {
    nrs.updateNrsIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELECTRICAL_CABINET_NRL_ELEMENT_TYPE) {
    nrl.updateNrlIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELECTRICAL_CABINET_TFA3D_ELEMENT_TYPE) {
    tfa3d.updateTfa3dIcon(element, suffix);
  } else if (elementType === elementTypeMap.ELECTRICAL_CABINET_TFA_ELEMENT_TYPE) {
    tfa.updateTfaIcon(element, suffix);
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
    case "1":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "2":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "3":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "4":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "5":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "7":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 4122.35) + ',' + (height / 3382.97) + ')">' + generateZoneIcon(element, suffix, state) + '</g>';
      break;
    case "8":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 30.163) + ',' + (height / 16.13958) + ')">' + generateBarIcon(element, suffix, state) + '</g>';
      break;
    case "9":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 522) + ',' + (height / 704) + ')">' + generateFanIcon(element, suffix, state) + '</g>';
      break;
    case "10":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateDMSIcon(element, suffix, state) + '</g>';
      break;
    case "11":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "12":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateLuxIcon(element, suffix, state) + '</g>';
      break;
    case "13":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 8.752) + ',' + (height / 10.247) + ')">' + generateSemIcon(element, suffix, state) + '</g>';
      break;
    case "14":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateLumIcon(element, suffix, state) + '</g>';
      break;
    case "15":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "16":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "17":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 19.063) + ')">' + generateAnemExtIcon(element, suffix, state) + '</g>';
      break;
    case "18":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 19.063) + ')">' + generateAnemIntIcon(element, suffix, state) + '</g>';
      break;
    case "19":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateCoIcon(element, suffix, state) + '</g>';
      break;
    case "20":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateNoIcon(element, suffix, state) + '</g>';
      break;
    case "21":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateOpacIcon(element, suffix, state) + '</g>';
      break;
    case "22":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateETDIcon(element, values, suffix, state) + '</g>';
      break;
    case "23":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateETDIcon(element, values, suffix, state) + '</g>';
      break;
    case "24":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case "25":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "26":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case "27":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case "28":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case "29":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case "30":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case "31":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateMatrixPanelIcon(element, suffix, state) + '</g>';
      break;                              
    case "33":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateETDIcon(element, values, suffix, state) + '</g>';
      break;      
    case "34":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "35":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '">' + generateFullMatrixPanelIcon(element, suffix, state) + '</g>';
      break;
    case "41":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "42":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "43":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "44":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "45":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "46":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element, suffix, state).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
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
