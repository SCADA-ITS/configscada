import { staticsIcons } from "../../icons/synopticJson.js";
import * as elementTypeMap from "../elementTypeMap.js";

export function updateSosIcon(element, suffix = "") {
  // SOS
  // icon based on status
  let elementType = elementTypeMap.getElementType(element.id);
  let elementState;
  if(element.elementTypeState && element.elementTypeState.id) {
    elementState = getTrimId(element.elementTypeState.id);
  }
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
