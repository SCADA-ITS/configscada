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

import * as elementTypeMap from "./elementTypeMap.js";

export function generateDMSIcon(element, suffix="") {
  let pmv_str = dms.generateIcon(element, suffix);
  return pmv_str;
}

export function generateETDIcon(element, values) {
  let etd_str = etd.generateIcon(element, values);
  return etd_str;
}

export function generateMeteoIcon(element, suffix="") {
  let meteo_str = meteo.generateIcon(element, suffix);    
  return meteo_str;
}

export function generateLuxIcon(element, suffix="") {
  let lux_str = lux.generateIcon(element, suffix);
  return lux_str;
}

export function generateLumIcon(element, suffix="") {
  let lum_str = lum.generateIcon(element, suffix);
  return lum_str;
}

export function generateCoIcon(element, suffix="") {
  let co_str = co.generateIcon(element, suffix);
  return co_str;
}

export function generateNoIcon(element, suffix="") {
  let no_str = no.generateIcon(element, suffix);
  return no_str;
}

export function generateOpacIcon(element, suffix="") {
  let opac_str = opac.generateIcon(element, suffix);
  return opac_str;
}

export function generateAnemIntIcon(element, suffix="") {
  let anem_int_str = anem_int.generateIcon(element, suffix);
  return anem_int_str;
}

export function generateAnemExtIcon(element, suffix="") {
  let anem_ext_str = anem_ext.generateIcon(element, suffix);
  return anem_ext_str;
}

export function generateFanIcon(element, suffix="") {
  let fan_str = fan.generateIcon(element, suffix);
  return fan_str;
}

export function generateBarIcon(element, suffix="") {
  let bar_str = bar.generateIcon(element, suffix);
  return bar_str;
}

export function generateSemIcon(element, suffix="") {
  let sem_str = sem.generateIcon(element, suffix);
  return sem_str;
}

export function generateZoneIcon(element, suffix="") {
  let zone_str = zone.generateIcon(element, suffix);
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
  }else if (elementType === elementTypeMap.ELECTRICAL_CABINET_ELEMENT_TYPE) {
    plc.updatePlcIcon(element, suffix);
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

export function getElementSVG(element, width, height, suffix="") {
  let elementType = element.id.split(":")[1];

  let iconStr = "";
  switch (elementType) {
    case "1":
      //iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateMeteoIcon(element, suffix) + '</g>';
      iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "2":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "3":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "4":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "5":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "7":
      //iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 4122.35) + ',' + (height / 3382.97) + ')">' + generateZoneIcon(element, suffix) + '</g>';
      break;
    case "8":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 30.163) + ',' + (height / 16.13958) + ')">' + generateBarIcon(element, suffix) + '</g>';
      break;
    case "9":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 522) + ',' + (height / 704) + ')">' + generateFanIcon(element, suffix) + '</g>';
      break;
    case "10":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 76) + ',' + (height / 24) + ')">' + generateDMSIcon(element, suffix) + '</g>';
      break;
    case "11":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "12":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateLuxIcon(element, suffix) + '</g>';
      break;
    case "13":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 8.752) + ',' + (height / 10.247) + ')">' + generateSemIcon(element, suffix) + '</g>';
      break;
    case "14":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateLumIcon(element, suffix) + '</g>';
      break;
    case "15":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "16":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
      break;
    case "17":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 19.063) + ')">' + generateAnemExtIcon(element, suffix) + '</g>';
      break;
    case "18":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 19.063) + ')">' + generateAnemIntIcon(element, suffix) + '</g>';
      break;
    case "19":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateCoIcon(element, suffix) + '</g>';
      break;
    case "20":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateNoIcon(element, suffix) + '</g>';
      break;
    case "21":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 14.628) + ',' + (height / 15.698) + ')">' + generateOpacIcon(element, suffix) + '</g>';
      break;
    case "22":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateETDIcon(element) + '</g>';
      break;
    case "23":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateETDIcon(element) + '</g>';
      break;
    case "33":
      iconStr = '<g id="' + element.id + suffix + '" state="' + element.state + '" transform="scale(' + (width / 28) + ',' + (height / 28) + ')">' + generateETDIcon(element) + '</g>';
      break;
    case "25":
      iconStr = '<image xlink:href="./img/' + getElementIcon(element).icon + '" id="' + element.id + suffix + '" state="' + element.state + '" width="' + width + '" height="' + height + '"></image>';
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
