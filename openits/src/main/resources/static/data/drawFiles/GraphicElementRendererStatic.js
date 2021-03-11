const ELMENT_TYPE_STATE_UI_ICON = "icon_synoptic";

export function generateIcon(element, suffix="", customGraphicEquipment, snapDiv) {
  let elem_icon = element.elementTypeState[ELMENT_TYPE_STATE_UI_ICON];
  let el = snapDiv.image("./img/" + elem_icon,0,0,1,1)
  return el;
}

export function getElementIcon(element, suffix="") {
  let iconName = "icon.svg";
  let width = 0;
  let height = 0;

  if(element.elementTypeState != undefined) {
    if(document.getElementById(element.id + suffix) != null) {
      document.getElementById(element.id + suffix).setAttribute("href", "./img/" + element.elementTypeState[ELMENT_TYPE_STATE_UI_ICON]);
    }
  }
  return { icon: iconName, width: width, height: height};
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
  let iconStr = '<image xlink:href="./img/' + element.elementTypeState[ELMENT_TYPE_STATE_UI_ICON] + '" id="' + element.id + suffix + '" width="' + width + '" height="' + height + '"></image>';
  elementSVG = '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape" xmlns:sodipodi="http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd" version="1.1" width="' + width + 'px" height="' + height + 'px">' + iconStr + '</svg>';
  return elementSVG;
}
