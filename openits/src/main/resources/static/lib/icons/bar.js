import { staticsIcons } from "../../icons/synopticJson.js";
import * as elementTypeMap from "../elementTypeMap.js";

export function generateIcon(element, suffix = "") {
  let bar_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="' +
    element.id +
    suffix +
    '" transform="translate(0,-280.8604)">' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path625-5" d="M -3.042642e-7,297 H 2.9365365 v -7.91505 H -3.042642e-7 Z m 0,0" style="fill:#47474a;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path627-6" d="M -3.042642e-7,289.92111 H 2.9365365 v -1.67232 H -3.042642e-7 Z m 0,0" style="fill:#c7c7c5;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path629-2" d="m 30.1625,297 h -1.307879 v -7.91505 H 30.1625 V 297" style="fill:#47474a;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path631-9" d="m 30.1625,289.92111 h -1.307879 v -1.67368 H 30.1625 v 1.67368" style="fill:#c7c7c5;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<g id="' +
    element.id +
    suffix +
    'g3856" transform="rotate(22.188461,3.5567155,291.27864)">' +
    '<g id="g3841">' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path633-1" d="M 27.205311,280.86041 27.707813,282.0495 2.9241494,292.2002 2.4230221,291.00976 27.205311,280.86041" style="fill:#e8e8e6;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path635-2" d="m 3.9195155,290.39756 -0.7048788,1.68455 -0.2904873,0.11809 -0.3772201,-0.89453 0.1748412,-0.41809 1.197745,-0.49002" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path637-7" d="m 6.3673181,289.39444 -0.7048787,1.68454 -1.197745,0.49139 0.7062575,-1.68591 1.1963662,-0.49002" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path639-0" d="m 8.8151208,288.39267 -0.7062534,1.68455 -1.1936149,0.48866 0.7048797,-1.68455 1.1949886,-0.48866" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path641-9" d="m 11.26568,287.38819 -0.706258,1.68454 -1.1963668,0.49139 0.7062578,-1.68591 1.196367,-0.49002" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path643-3" d="m 13.713483,286.38506 -0.706258,1.68591 -1.196367,0.49002 0.70488,-1.68455 1.197745,-0.49138" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path645-6" d="m 16.164038,285.38194 -0.706255,1.68454 -1.196366,0.49139 0.704879,-1.68591 1.197742,-0.49002" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path647-0" d="m 18.611843,284.38017 -0.706257,1.68454 -1.196366,0.49003 0.704878,-1.68455 1.197745,-0.49002" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path649-6" d="m 21.059646,283.37705 -0.706258,1.68453 -1.194988,0.49004 0.706253,-1.68591 1.194993,-0.48866" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path651-2" d="m 23.510202,282.37392 -0.706254,1.68454 -1.197745,0.49003 0.706257,-1.68455 1.197742,-0.49002" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path653-6" d="m 25.958008,281.3708 -0.706258,1.68455 -1.197745,0.49137 0.706258,-1.6859 1.197745,-0.49002" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path655-1" d="m 27.208065,280.86448 0.495619,1.17824 -0.0041,0.0108 -1.194991,0.48867 0.703501,-1.67776" style="fill:#dc1e23;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    "</g>" +
    "</g>" +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path657-8" d="m 3.8162615,291.13735 c 0.2629518,0.62305 -0.037174,1.33841 -0.6690851,1.59632 -0.6305361,0.25926 -1.356068,-0.0367 -1.6190198,-0.65971 -0.261577,-0.62169 0.037174,-1.33705 0.6690852,-1.59495 0.6319137,-0.25927 1.356065,0.0353 1.6190197,0.65834" style="fill:#c7c7c5;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<g transform="matrix(0.35243965,0,0,0.34749658,-34.123319,48.287461)" style="clip-rule:nonzero" id="g663-7" clip-path="url(#clip10-3)">' +
    '<g style="clip-rule:nonzero" id="g661-9" clip-path="url(#clip11-7)">' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path659-2" d="m 100.98438,715.72656 h 4.16796 v -14.69531 h -4.16796 z m 0,0" style="fill:#545457;fill-opacity:1;fill-rule:nonzero;stroke:none" />' +
    "</g>" +
    "</g>" +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path665-0" d="m 1.4675828,291.3206 v -1.39949 h 1.4689537 v 0.49138 c -0.086737,-0.019 -0.1748413,-0.0285 -0.2643294,-0.0285 -0.1583191,0 -0.3193986,0.0312 -0.4749653,0.0951 -0.3799755,0.15474 -0.6387962,0.47509 -0.729659,0.84159" style="fill:#545457;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path667-2" d="m 1.4675828,289.92111 h 1.4689537 v -1.67232 H 1.4675828 Z m 0,0" style="fill:#c7c7c5;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    '<path inkscape:connector-curvature="0" id="' +
    element.id +
    suffix +
    'path669-3" d="m 2.6722071,292.82732 c -0.4846041,0 -0.9458036,-0.2837 -1.1440505,-0.75336 -0.024776,-0.0597 -0.045436,-0.12081 -0.060572,-0.18053 v -0.57283 c 0.090863,-0.3665 0.3496835,-0.68685 0.7296589,-0.84159 0.1555668,-0.0638 0.3166463,-0.0951 0.4749654,-0.0951 0.089484,0 0.1775976,0.01 0.2643294,0.0285 v 2.38633 c -0.088106,0.019 -0.1762189,0.0285 -0.2643294,0.0285" style="fill:#c7c7c5;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:0.34995937" />' +
    "</g>";

  return bar_str;
}

export function updateBarIcon(element, suffix = "") {
  if (document.getElementById(element.id + suffix) != null) {
    // changes the value
    let new_value = null;

    new_value = element.elementValues.find(
      (ic) =>
        elementTypeMap.getElementTypeParam(ic.id) ==
        elementTypeMap.BAR_COMMAND_FOR_BARRIER
    );

    if (typeof new_value.value != "undefined" && new_value.value != null) {
      if (new_value.value == "1") {
        document
          .getElementById(element.id + suffix + "g3856")
          .setAttribute("transform", "rotate(0,0,0)");
      } else if (new_value.value == "0") {
        document
          .getElementById(element.id + suffix + "g3856")
          .setAttribute("transform", "rotate(22.188461,3.5567155,291.27864)");
      }
    }

    // changes the color based on the status
    let color = "";
    let elementState = element.state;

    if (typeof elementState != "undefined") {
      if (elementState == 0) {
        color = "#ffcc00";
      } else if (elementState == 1) {
        color = "#c0c0c0";
      } else if (elementState == 2) {
        color = "#800080";
      } else if (elementState == 3) {
        color = "#ff0000";
      }

      document.getElementById(element.id + suffix + "path625-5").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + "path627-6").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + "path629-2").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + "path631-9").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + "path633-1").style[
        "fill"
      ] = color;
      /*
      document.getElementById(element.id + suffix + "path635-2").style["fill"] = color;
      document.getElementById(element.id + suffix + "path637-7").style["fill"] = color;
      document.getElementById(element.id + suffix + "path639-0").style["fill"] = color;
      document.getElementById(element.id + suffix + "path641-9").style["fill"] = color;
      document.getElementById(element.id + suffix + "path643-3").style["fill"] = color;
      document.getElementById(element.id + suffix + "path645-6").style["fill"] = color;
      document.getElementById(element.id + suffix + "path647-0").style["fill"] = color;
      document.getElementById(element.id + suffix + "path649-6").style["fill"] = color;
      document.getElementById(element.id + suffix + "path651-2").style["fill"] = color;
      document.getElementById(element.id + suffix + "path653-6").style["fill"] = color;
      document.getElementById(element.id + suffix + "path655-1").style["fill"] = color;
      */
      document.getElementById(element.id + suffix + "path657-8").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + "path659-2").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + "path665-0").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + "path667-2").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + "path669-3").style[
        "fill"
      ] = color;
    }
  }
}
