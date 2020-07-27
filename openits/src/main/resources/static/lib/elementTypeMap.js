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
export const PMV_A_C = "24";
export const ELECTRICAL_CABINET_ELEMENT_TYPE = "25";
export const MATRIX_PANEL_64X64 = "26";
export const PMV_A_B = "27";
export const MATRIX_PANEL_192X64 = "28";
export const PMV_B_D = "29";
export const MATRIX_PANEL_128X64 = "30";
export const MATRIX_PANEL_TEMP = "31";
export const MATRIX_PANEL_TEMP_A = "32";
export const ETD_ELEMENT_TYPE = "33";
export const ELECTRICAL_CABINET_SCN_ELEMENT_TYPE = "34";
export const MATRIX_PANEL_DGT = "35";

export const POSITION_OF_ELEMENT_TYPE_ID = 1;
export const POSITION_OF_ELEMENT_ID = 2;
export const POSITION_OF_ELEMENT_TYPE_PARAM_ID = 3;
export const POSITION_OF_PARAM_TYPE_ID = 4;

//  elementTypeParamId : paramTypeId
export const BAR_COMMAND_FOR_BARRIER = "1:2";
export const FAN_COMMAND_FOR_FAN = "1:2";
export const LUM_LUMINOSITY = "2:2";
export const LUX_LUMINOSITY = "1:2";
export const ANEM_INT_WIND_SPEED = "1:2";
export const ANEM_EXT_WIND_SPEED = "1:2";
export const CO_CONCENTRATION = "1:2";
export const NO_CONCENTRATION = "1:2";
export const OPAC_CONCENTRATION = "1:2";
export const SEM_STATUS = "1:2";
export const ETD_AVERAGE_LENGTH = "1:2";
export const ETD_AVERAGE_SPEED = "2:2";
export const ETD_AVG_DIST_BETWEEN_VEHICLES = "3:2";
export const ETD_CURRENT_INTEGRATION_PERIOD = "4:2";
export const ETD_DAY = "5:2";
export const ETD_DETECTION_CONGESTION = "6:2";
export const ETD_DETECTOR_NUMBER = "7:2";
export const ETD_FAIL_DATA = "8:2";
export const ETD_HOUR = "9:2";
export const ETD_MINUTE = "10:2";
export const ETD_MONTH = "11:2";
export const ETD_OCCUPATION_SPIRAL_HEAD = "12:2";
export const ETD_REVERSE_INTENSITY_DETECTION = "13:2";
export const ETD_SECOND = "14:2";
export const ETD_SENSE_CIRCULATION = "15:2";
export const ETD_TOTAL_INTENSITY = "16:2";
export const ETD_VEH_LONG1 = "17:2";
export const ETD_VEH_LONG10 = "18:2";
export const ETD_VEH_LONG11 = "19:2";
export const ETD_VEH_LONG12 = "20:2";
export const ETD_VEH_LONG13 = "21:2";
export const ETD_VEH_LONG14 = "22:2";
export const ETD_VEH_LONG15 = "23:2";
export const ETD_VEH_LONG16 = "24:2";
export const ETD_VEH_LONG2 = "25:2";
export const ETD_VEH_LONG3 = "26:2";
export const ETD_VEH_LONG4 = "27:2";
export const ETD_VEH_LONG5 = "28:2";
export const ETD_VEH_LONG6 = "29:2";
export const ETD_VEH_LONG7 = "30:2";
export const ETD_VEH_LONG8 = "31:2";
export const ETD_VEH_LONG9 = "32:2";
export const ETD_VEH_SPEED1 = "33:2";
export const ETD_VEH_SPEED10 = "34:2";
export const ETD_VEH_SPEED11 = "35:2";
export const ETD_VEH_SPEED12 = "36:2";
export const ETD_VEH_SPEED13 = "37:2";
export const ETD_VEH_SPEED14 = "38:2";
export const ETD_VEH_SPEED15 = "39:2";
export const ETD_VEH_SPEED16 = "40:2";
export const ETD_VEH_SPEED2 = "41:2";
export const ETD_VEH_SPEED3 = "42:2";
export const ETD_VEH_SPEED4 = "43:2";
export const ETD_VEH_SPEED5 = "44:2";
export const ETD_VEH_SPEED6 = "45:2";
export const ETD_VEH_SPEED7 = "46:2";
export const ETD_VEH_SPEED8 = "47:2";
export const ETD_VEH_SPEED9 = "48:2";
export const ETD_YEAR = "49:2";

export const DMS_COMAND_FOR_DMS = "1:2";
export const DMS_TEXT_FOR_STATUS_1 = "1:1";
export const DMS_TEXT_FOR_STATUS_2 = "2:1";

export const MATRIX_PANEL_PICTOGRAM = "4:2";

export const CAM_STREAM_MJPEG = "2:1:3";


export const COMMAND_TYPE_PMV_A_C = "2:24";
export const COMMAND_TYPE_MATRIX_PANEL_64X64 = "2:26";
export const COMMAND_TYPE_PMV_A_B = "2:27";
export const COMMAND_TYPE_MATRIX_PANEL_192X64 = "2:28";
export const COMMAND_TYPE_PMV_B_D = "2:29";
export const COMMAND_TYPE_MATRIX_PANEL_128X64 = "2:30";
export const COMMAND_TYPE_MATRIX_PANEL_TEMP = "2:31";
export const COMMAND_TYPE_MATRIX_PANEL_TEMP_A = "2:32";
export const COMMAND_TYPE_MATRIX_PANEL_DGT = "2:35";
export const PANEL_MANAGER_CONFIG = "5:1";

export function getElementType(elementIdString) {
  let ret = elementIdString.split(":")[POSITION_OF_ELEMENT_TYPE_ID];
  return ret;
}

export function getElementId(elementIdString) {
  let ret = elementIdString.split(":")[POSITION_OF_ELEMENT_TYPE_ID] + ":" + elementIdString.split(":")[POSITION_OF_ELEMENT_ID];
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
    case MATRIX_ELEMENT_TYPE:
    case FULL_MATRIX_ELEMENT_TYPE:
    case ELECTRICAL_CABINET_ELEMENT_TYPE:
    case MATRIX_PANEL_DGT:
      ret =  $.i18n("ElementType:"+elementTypeIdString+".alias");
      break;
    case ELECTRICAL_CABINET_SCN_ELEMENT_TYPE:
      ret =  $.i18n("ElementType:"+elementTypeIdString+".alias");
      break;
    default:
      break;
  }

  return ret;
}
