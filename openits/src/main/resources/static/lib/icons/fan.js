import * as elementTypeMap from "../elementTypeMap.js";

export function generateIcon(element, suffix = "") {
  let fan_str =
    '<defs id="defs3446">' +
    '<marker  inkscape:stockid="Arrow1Sstart" orient="auto" refY="0" refX="0" id="Arrow1Sstart-7-7-4-9-9-4-8" style="overflow:visible" inkscape:isstock="true">' +
    '<path id="' +
    element.id +
    suffix +
    ':IconArrowMD" inkscape:connector-curvature="0" d="M 0,0 5,-5 -12.5,0 5,5 Z" style="fill:#ffff00;fill-opacity:1;fill-rule:evenodd;stroke:#ffff00;stroke-width:1.00000003pt;stroke-opacity:1" transform="matrix(0.2,0,0,0.2,1.2,0)" />' +
    '<path id="' +
    element.id +
    suffix +
    ':IconArrowMI" inkscape:connector-curvature="0" d="M 0,0 5,-5 -12.5,0 5,5 Z" style="fill:#ffff00;fill-opacity:1;fill-rule:evenodd;stroke:#ffff00;stroke-width:1.00000003pt;stroke-opacity:1" transform="matrix(0.2,0,0,0.2,1.2,0)" />' +
    "</marker>" +
    "</defs>" +
    '<path id="' +
    element.id +
    suffix +
    ':Icon5" style="fill:#cccccc" d="m 139.98687,561.74217 c -4.68007,-1.13295 -11.28841,-4.45184 -16.07392,-8.07276 -5.29801,-4.0087 -16.07857,-15.03315 -22.30303,-22.8076 -16.669941,-20.82102 -28.391611,-44.16154 -35.613212,-70.91404 -1.584484,-5.86974 -1.730197,-7.08094 -1.763998,-14.66268 -0.03153,-7.0715 0.137124,-8.86466 1.168391,-12.42285 3.91296,-13.50091 14.094758,-24.55324 27.320335,-29.65618 4.946944,-1.90873 7.485554,-2.29094 73.046794,-10.99802 16.86207,-2.23943 31.01822,-4.28236 31.4581,-4.53986 0.57583,-0.33708 0.60434,-0.47272 0.10183,-0.48439 -0.38387,-0.009 -0.55314,-0.25053 -0.37615,-0.5369 0.17699,-0.28638 -6.79577,-17.8616 -15.49504,-39.05604 -20.24955,-49.33496 -21.95505,-53.68596 -22.68352,-57.86909 -0.93165,-5.34998 -0.32438,-16.65742 1.12268,-20.90441 4.27266,-12.53979 12.6159,-22.06046 24.04782,-27.44155 6.51424,-3.0663 21.17695,-6.8789 34.34332,-8.92995 8.93441,-1.3918 33.04801,-1.93612 44.17432,-0.99716 12.80926,1.08099 30.97691,4.98241 41.30607,8.8703 9.664,3.63752 18.6122,11.81985 23.55518,21.53912 3.03289,5.9635 3.87065,9.4999 4.13369,17.44935 0.41156,12.43811 2.35436,6.76307 -25.67611,75.00141 -7.21237,17.55801 -13.11339,32.26818 -13.11339,32.68925 0,0.42106 0.23993,0.61729 0.53319,0.43605 0.29325,-0.18124 0.53319,-0.10805 0.53319,0.16265 0,0.2707 1.13969,0.65148 2.53264,0.84618 1.39295,0.1947 20.28781,2.73909 41.98857,5.65419 59.00105,7.92572 56.12735,7.43231 62.91622,10.80267 10.56798,5.24652 18.81803,14.66615 22.76224,25.98915 1.72865,4.9626 1.76379,5.24364 1.73369,13.86289 -0.0293,8.38985 -0.12049,9.13131 -1.96761,15.99736 -8.66351,32.2036 -23.71569,58.9316 -46.48426,82.54161 -10.74904,11.14629 -16.65008,15.19076 -26.20297,17.95908 -3.84576,1.11446 -5.73781,1.33656 -11.38606,1.33656 -8.17866,0 -11.59763,-0.58022 -17.10447,-2.90271 -5.64417,-2.38041 -10.95243,-5.99813 -14.73142,-10.03985 -3.336,-3.56793 -13.64567,-16.67899 -39.71523,-50.50684 -23.01429,-29.86333 -22.55271,-29.27557 -23.0115,-29.30166 -0.22915,-0.013 -0.57325,0.39619 -0.76468,0.90938 -0.19142,0.5132 -0.78774,1.24942 -1.32515,1.63605 -0.53742,0.38663 -0.83897,0.92648 -0.67013,1.19967 0.16884,0.27319 0.0708,0.49671 -0.21793,0.49671 -0.67805,0 -7.11243,8.14278 -34.7591,43.98803 -12.4399,16.12894 -23.92682,30.61992 -25.52649,32.20218 -5.5802,5.51948 -15.21715,10.66447 -22.05355,11.77399 -4.72995,0.76764 -16.06096,0.59601 -19.75935,-0.29929 z m 17.34602,-29.20951 c 1.07902,-0.52628 2.75856,-1.73246 3.73232,-2.68039 3.32975,-3.24142 53.22315,-67.88254 53.22315,-68.95509 0,-0.23926 -2.20061,-2.46446 -4.89024,-4.9449 -5.78173,-5.33204 -10.07157,-10.92899 -13.11384,-17.1096 -2.42228,-4.92107 -4.73939,-12.33563 -5.27251,-16.87161 -0.21482,-1.82777 -0.63439,-3.01862 -1.13066,-3.20905 -0.85865,-0.3295 -0.93909,-0.31994 -32.91049,3.91062 -30.17485,3.99284 -48.90411,6.60854 -49.8531,6.96241 -0.43988,0.16403 -1.6689,0.3713 -2.73116,0.4606 -2.6998,0.22698 -4.687696,1.37897 -7.334436,4.25034 -4.198813,4.55518 -4.924463,8.14352 -3.27813,16.21035 3.181484,15.58886 11.220696,34.15111 21.726136,50.16486 6.51066,9.9244 10.00341,14.20514 18.77459,23.01035 6.53367,6.559 8.81063,8.51302 10.69936,9.18186 3.16973,1.12247 9.68882,0.92163 12.35901,-0.38075 z m 188.45762,0.0408 c 3.97356,-2.08131 16.55412,-14.9273 22.7138,-23.19302 13.84206,-18.57478 25.30921,-43.43529 28.14566,-61.01911 0.7552,-4.68164 0.51726,-6.97978 -1.05998,-10.23793 -1.88972,-3.90365 -7.00893,-8.26798 -8.86336,-7.55637 -0.41341,0.15864 -0.75166,0.0389 -0.75166,-0.26617 0,-0.30504 -0.53985,-0.50177 -1.19967,-0.43718 -0.65982,0.0646 -1.62314,-0.0652 -2.14072,-0.28854 -0.51758,-0.22328 -1.41733,-0.28142 -1.99946,-0.12919 -0.58212,0.15225 -1.05841,0.103 -1.05841,-0.10942 0,-0.44134 1.64431,-0.20536 -43.14331,-6.19159 -18.59689,-2.48562 -34.52055,-4.51931 -35.38591,-4.51931 -1.31088,0 -1.52686,0.17791 -1.29452,1.06638 0.15337,0.5865 0.0886,1.06637 -0.14387,1.06637 -0.2325,0 -0.59207,1.12922 -0.79903,2.50938 -1.79719,11.98448 -8.56428,23.86586 -18.97089,33.30827 -2.32879,2.11303 -4.23417,4.05339 -4.23417,4.31191 0,1.02536 50.41731,65.9749 51.21324,65.9749 0.27831,0 0.50602,0.31121 0.50602,0.69158 0,1.14107 3.97422,4.66171 6.19323,5.48639 2.7192,1.01056 9.97924,0.73412 12.27301,-0.46733 z M 250.15583,439.703 c 11.28321,-2.39096 19.25972,-11.68446 19.99119,-23.2919 0.92083,-14.61238 -10.39538,-26.57759 -25.13601,-26.57759 -9.7304,0 -17.71493,4.70545 -22.30739,13.1462 -6.77415,12.45061 -1.72383,28.32807 11.00583,34.60064 5.87505,2.89496 10.17984,3.45055 16.44638,2.12265 z m -22.00458,-76.36488 5.59848,-2.03018 10.93036,0.005 c 11.64213,0.005 12.45189,0.12684 20.13692,3.02469 2.42434,0.91417 4.48812,1.56622 4.58619,1.44902 0.30268,-0.36171 5.73169,-13.28074 19.83923,-47.20999 l 13.55518,-32.60072 -0.0234,-3.92258 c -0.0265,-4.44938 -0.80252,-7.07314 -2.90844,-9.83413 -1.56023,-2.04558 -4.44281,-4.07098 -5.16161,-3.62674 -0.24065,0.14873 -0.43756,-0.0237 -0.43756,-0.38309 0,-0.35944 -0.14853,-0.50499 -0.33007,-0.32346 -0.18153,0.18154 -0.64142,0.0187 -1.02197,-0.36183 -0.78348,-0.78348 -8.16921,-2.90274 -15.70998,-4.50781 -18.01699,-3.83496 -39.54266,-4.34111 -57.58433,-1.35403 -6.45503,1.06873 -20.31447,4.58883 -23.51808,5.97325 -6.57322,2.84057 -10.79488,11.65711 -8.39172,17.52529 0.33808,0.82554 0.70404,1.97043 0.81325,2.54421 0.10921,0.57378 0.37245,1.17362 0.58499,1.33297 0.21253,0.15936 0.46863,0.76057 0.56909,1.33602 0.35454,2.03073 29.65782,73.59797 30.64201,74.83682 0.30742,0.38695 0.9356,0.58073 1.39596,0.43061 0.46037,-0.15011 3.35635,-1.18652 6.43551,-2.30312 z" inkscape:connector-curvature="0">' +
    '<animateTransform id="' +
    element.id +
    suffix +
    ':Animation" attributeType="xml" attributeName="transform" type="rotate" from="360 245 410" to="0 245 410" dur="6s" repeatCount="indefinite" />' +
    "</path>" +
    '<g transform="matrix(42.490516,0,0,42.490516,-9.9529807,-11912.934)" id="' +
    element.id +
    suffix +
    ':IconInterior" style="fill:#cccccc">' +
    '<path id="' +
    element.id +
    suffix +
    ':Icon1" inkscape:connector-curvature="0" d="m 0.83169546,292.33918 c -0.30860772,-0.66605 -0.48117516,-1.4009 -0.48117516,-2.17393 0,-0.16513 0.009132,-0.32804 0.0243514,-0.48939 H 0 v 2.66332 h 0.19106517 z" style="fill:#cccccc;stroke-width:0.2279644" />' +
    '<path id="' +
    element.id +
    suffix +
    ':Icon2" inkscape:connector-curvature="0" d="m 11.316397,292.33918 h 0.625176 v -2.66332 h -0.168586 c 0.01522,0.16135 0.02435,0.32426 0.02435,0.48939 2.34e-4,0.77303 -0.172567,1.50788 -0.48094,2.17393 z" style="fill:#cccccc;stroke-width:0.2279644" />' +
    '<path id="' +
    element.id +
    suffix +
    ':Icon3" inkscape:connector-curvature="0" d="m 12.038277,297 -0.282149,-4.43888 H 11.20705 c -0.935189,1.79287 -2.8842416,3.0291 -5.1332379,3.0291 -2.2489964,0 -4.1980485,-1.23623 -5.13323758,-3.0291 H 0.41163305 L 0.12901581,297 H 1.404891 v -0.66583 h 9.36594 V 297 Z" style="fill:#cccccc;stroke-width:0.2279644" />' +
    '<path id="' +
    element.id +
    suffix +
    ':Icon4" inkscape:connector-curvature="0" d="m 6.0740462,295.36827 c 3.0268377,0 5.4893778,-2.33418 5.4893778,-5.20302 0,-2.86907 -2.4625401,-5.20325 -5.4893778,-5.20325 -3.0268376,0 -5.4893774,2.33418 -5.4893774,5.20325 0,2.86906 2.4625398,5.20302 5.4893774,5.20302 z m -0.024351,-10.00899 c 2.7866013,0 5.0458998,2.14131 5.0458998,4.78288 0,2.64157 -2.2595327,4.78267 -5.0458998,4.78267 -2.7868355,0 -5.0459002,-2.1411 -5.0459002,-4.78267 0,-2.64157 2.2588306,-4.78288 5.0459002,-4.78288 z" style="fill:#cccccc;stroke-width:0.2279644" />' +
    "</g>" +
    '<path id="' +
    element.id +
    suffix +
    ':IconMD" style="fill:#ffff00;fill-opacity:1;stroke:#ffff00;stroke-width:67.85962677;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:5;stroke-dasharray:none;stroke-opacity:1;marker-start:url(#Arrow1Sstart-7-7-4-9-9-4-8)" d="M 105.97212,87.97389 H 507.6238" inkscape:connector-curvature="0" />' +
    '<path id="' +
    element.id +
    suffix +
    ':IconMI" style="fill:#ffff00;fill-opacity:1;stroke:#ffff00;stroke-width:67.85962677;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:5;stroke-dasharray:none;stroke-opacity:1;marker-start:url(#Arrow1Sstart-7-7-4-9-9-4-8)" d="M 386.47125,87.97389 H -15.180432" inkscape:connector-curvature="0" />';

  return fan_str;
}

export function updateFanIcon(element, suffix = "") {
  if (
    document.getElementById(element.id + suffix + ":Icon1") != null &&
    document.getElementById(element.id + suffix + ":Icon2") != null &&
    document.getElementById(element.id + suffix + ":Icon3") != null &&
    document.getElementById(element.id + suffix + ":Icon4") != null &&
    document.getElementById(element.id + suffix + ":Icon5") != null
  ) {
    // changes icon animation based on value
    //let fan_cmd = element.elementValues.find(
    //  (ic) => ic.elementTypeParam.description === "comand for fan"
    //);

    let fan_cmd = element.elementValues.find(
      (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.FAN_COMMAND_FOR_FAN
    );

    if (typeof fan_cmd != "undefined") {
      if (fan_cmd.value == 0) {
        document
          .getElementById(element.id + suffix + ":Animation")
          .setAttribute("dur", "0");
        document.getElementById(element.id + suffix + ":IconMD").style[
          "visibility"
        ] = "hidden";
        document.getElementById(element.id + suffix + ":IconMI").style[
          "visibility"
        ] = "hidden";
      } else if (fan_cmd.value == 1) {
        document
          .getElementById(element.id + suffix + ":Animation")
          .setAttribute("dur", "6s");
        document.getElementById(element.id + suffix + ":IconArrowMD").style[
          "visibility"
        ] = "visible";
        document.getElementById(element.id + suffix + ":IconArrowMI").style[
          "visibility"
        ] = "hidden";
        document.getElementById(element.id + suffix + ":IconMD").style[
          "visibility"
        ] = "visible";
        document.getElementById(element.id + suffix + ":IconMI").style[
          "visibility"
        ] = "hidden";
      } else if (fan_cmd.value == 2) {
        document
          .getElementById(element.id + suffix + ":Animation")
          .setAttribute("dur", "6s");
        document.getElementById(element.id + suffix + ":IconArrowMD").style[
          "visibility"
        ] = "hidden";
        document.getElementById(element.id + suffix + ":IconArrowMI").style[
          "visibility"
        ] = "visible";
        document.getElementById(element.id + suffix + ":IconMD").style[
          "visibility"
        ] = "hidden";
        document.getElementById(element.id + suffix + ":IconMI").style[
          "visibility"
        ] = "visible";
      }
    }

    let color = "";
    let elementState = element.state;

    if(typeof elementState != 'undefined') {
      if (elementState == 0) {
        color = "#ffcc00";
      } else if (element.state == 1) {
        color = "#c0c0c0";
      } else if (element.state == 2) {
        color = "#800080";
      } else if (element.state == 3) {
        color = "#ff0000";
      }
  
      document.getElementById(element.id + suffix + ":Icon1").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + ":Icon2").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + ":Icon3").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + ":Icon4").style[
        "fill"
      ] = color;
      document.getElementById(element.id + suffix + ":Icon5").style[
        "fill"
      ] = color;
    }
  }
}
