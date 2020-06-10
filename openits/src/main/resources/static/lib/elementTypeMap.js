export const WS_ELEMENT_TYPE = "1";
export const CAM_ELEMENT_TYPE = "2";
export const DET_ELEMENT_TYPE = "3";
export const SEC_ELEMENT_TYPE = "4";
export const PLC_ELEMENT_TYPE = "5";
export const ZONE_ELEMENT_TYPE = "7";
export const BAR_ELEMENT_TYPE = "8";
export const FAN_ELEMENT_TYPE = "9";
export const DMS_ELEMENT_TYPE = "10";
export const SOS_ELEMENT_TYPE = "11";
export const LUX_ELEMENT_TYPE = "12";
export const SEM_ELEMENT_TYPE = "13";
export const LUM_ELEMENT_TYPE = "14";
export const FIRE_ELEMENT_TYPE = "15";
export const MEG_ELEMENT_TYPE = "16";
export const ANEM_EXT_ELEMENT_TYPE = "17";
export const ANEM_INT_ELEMENT_TYPE = "18";
export const CO_ELEMENT_TYPE = "19";
export const NO_ELEMENT_TYPE = "20";
export const OPAC_ELEMENT_TYPE = "21";
export const ETD_MODBUS_ELEMENT_TYPE = "22";
export const ETD_DETECTOR_ELEMENT_TYPE = "23";
export const ELECTRICAL_CABINET_ELEMENT_TYPE = "25";
export const ETD_ELEMENT_TYPE = "33";

export const POSITION_OF_ELEMENT_TYPE_ID = 1;
export const POSITION_OF_ELEMENT_TYPE_PARAM_ID = 3;
export const POSITION_OF_PARAM_TYPE_ID = 4;

//  elementTypeParamId : paramTypeId
export const BAR_COMMAND_FOR_BARRIER = "1:3";
export const FAN_COMMAND_FOR_FAN = "1:3";
export const LUM_LUMINOSITY = "2:2";
export const LUX_LUMINOSITY = "1:2";
export const ANEM_INT_WIND_SPEED = "1:2";
export const ANEM_EXT_WIND_SPEED = "1:2";
export const CO_CONCENTRATION = "1:2";
export const NO_CONCENTRATION = "1:2";
export const OPAC_CONCENTRATION = "1:2";
export const SEM_STATUS = "1:2";

export const DMS_COMAND_FOR_DMS = "1:3";
export const DMS_TEXT_FOR_STATUS_1 = "1:1";
export const DMS_TEXT_FOR_STATUS_2 = "2:1";

export const CAM_STREAM_MJPEG = "2:1:3";

export function getElementType(elementIdString) {
  let ret = elementIdString.split(":")[POSITION_OF_ELEMENT_TYPE_ID];
  return ret;
}

export function getElementTypeParam(elementParamIdString) {
  let ret = elementParamIdString.split(":")[POSITION_OF_ELEMENT_TYPE_PARAM_ID] + ":" + elementParamIdString.split(":")[POSITION_OF_PARAM_TYPE_ID];
  return ret;
}

export function getElementParamTypeIdi18(elementValueid) {
	let ret = "ElementTypeParam:" + elementValueid.split(":")[POSITION_OF_ELEMENT_TYPE_ID] + ":" + elementValueid.split(":")[POSITION_OF_PARAM_TYPE_ID] + ":" + elementValueid.split(":")[POSITION_OF_ELEMENT_TYPE_PARAM_ID]; 
	return ret;
}

export function getElementParamTypeId(elementValueid) {
	let ret = elementValueid.split(":")[POSITION_OF_ELEMENT_TYPE_ID] + ":" + elementValueid.split(":")[POSITION_OF_PARAM_TYPE_ID] + ":" + elementValueid.split(":")[POSITION_OF_ELEMENT_TYPE_PARAM_ID]; 
	return ret;
}

export function getParamTypeId(elementValueid) {
  let ret = elementValueid.split(":")[POSITION_OF_PARAM_TYPE_ID]; 
  return parseInt(ret);
}

export function getElementTypeName(elementTypeIdString) {
  let ret = "";

  switch (elementTypeIdString.toString()) {
    case "BASE_LAYER":
      ret =  $.i18n("synoptic.layer.base");
      break;
    case "ROAD":
      ret = $.i18n("synoptic.layer.road");
      break;
    case WS_ELEMENT_TYPE:
    case CAM_ELEMENT_TYPE:
    case DET_ELEMENT_TYPE:
    case SEC_ELEMENT_TYPE:
    case PLC_ELEMENT_TYPE:
    case ZONE_ELEMENT_TYPE:
    case BAR_ELEMENT_TYPE:
    case FAN_ELEMENT_TYPE:
    case DMS_ELEMENT_TYPE:
    case SOS_ELEMENT_TYPE:
    case LUX_ELEMENT_TYPE:
    case SEM_ELEMENT_TYPE:
    case LUM_ELEMENT_TYPE:
    case FIRE_ELEMENT_TYPE:
    case MEG_ELEMENT_TYPE:
    case ANEM_EXT_ELEMENT_TYPE:
    case ANEM_INT_ELEMENT_TYPE:
    case CO_ELEMENT_TYPE:
    case NO_ELEMENT_TYPE:
    case OPAC_ELEMENT_TYPE:
    case ETD_MODBUS_ELEMENT_TYPE:
    case ETD_DETECTOR_ELEMENT_TYPE:
    case ETD_ELEMENT_TYPE:
    case ELECTRICAL_CABINET_ELEMENT_TYPE:
        ret =  $.i18n("ElementType:"+elementTypeIdString+".alias");
        break;
    default:
      break;
  }

  return ret;
}
