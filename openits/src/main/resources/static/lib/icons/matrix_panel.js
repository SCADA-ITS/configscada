import * as elementTypeMap from "../elementTypeMap.js";
import { pictograms } from "../../data/pictograms.js";

export function generateIcon(element, suffix = "") {
  // check for the status message we should use
  let picToShow = element.elementValues.find(
    (ic) =>
      elementTypeMap.getElementTypeParam(ic.id) ==
      elementTypeMap.MATRIX_PANEL_PICTOGRAM
  );

  // changes the color based on the status
  let color = "";
  let elementState = "";
  if (typeof element.state != "undefined") {
    elementState = element.state;
  } else {
    elementState = state;
  }

  if (typeof elementState != "undefined") {
    if (elementState == 0) {
      color = "gold";
    } else if (elementState == 1) {
      color = "silver";
    } else if (elementState == 2) {
      color = "purple";
    } else if (elementState == 3) {
      color = "red";
    }
  }

  let pmv_str = "";

  pmv_str =
    '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-272.12915)">' +
    '<g id="g1739-1" transform="matrix(0.40956503,0,0,0.48504268,-500.12204,357.57278)">' +
    '<rect y="-174.73952" x="1222.5098" height="48.450024" width="48.450024" id="' +
    element.id +
    suffix +
    ':rect2250-2-89" stroke="' +
    color +
    '" fill="black" style="fill-opacity:1;stroke-width:2.8125;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" />' +
    //'<image href="/img/' + picToShow.value + '.png" y="-174.73952" x="1222.5098" height="48.450024" width="48.450024"/>' +
    //'<image xlink:href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACIAAAAeCAYAAABJ/8wUAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAAGAAAABgBh/sOCwAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAARFSURBVFiFxZZbTBRXGMd/M7O77HBbNlAs1wBaLsUlGncpuEuzLiCYIG31oVEfxKYR4QVaqgnW1FlBuSTYXVIaG9IISR9q0z41xmgCqVH6YjUpmDQmbU1jmrSNtukLWQKyfWDPMoSFvWDS72Xm/53/+c5vzpk5cyS2El7eJogfAIkuznMt0VJSwhAXsLHMA8AQyiwhY+cjfkyknJwwyDIjOggAA8t8QoIPlxiIl8NAI4AiKRjkMI8LjcOJlIyffpQk/uYhsAOg09GJhMTYvTHheAKUozEfT9n4Z+QfPhAQVrMVr9tLn6ePTDVTOAqA9+Mtq8Tl7iePZb4ETABDDUPsK96HalBRjSo3fr4hnDW4+YLv+DfW0vHNyBKDQCpARVYFHY6OcFOnoxNbtk3IZOBiPKVjB9GoAY4JebnpMkbZGG5WJAVfs0/f4xgarhcNIgH+0JXWslaadzTz9cQELXY7LXY730xO4in20FrWuraPFtsYsX01Gm3AVQCTYmKuY45ns7/zzsGDa2xXr1/npap8KscqWXi+INIn0JiINkR0Wo1UdOvdXdNNaWYpM1NT66wzU1Nst26nq6ZLnx5kEMvWQeBDIBcgOyWbs3VnAcjOzV1nzM7JAeDc6+fISc0R6W0E6N0aiEYJ0C3kQP0AlqSVh3M4nevsDtfKu5lmSqPf069veg+N0sRBYAQwA+x+eTdtu9rCDWU2G+kZGWFtsVoprawM67ZdbVTnVQtpAoYTA9HwAG8CSEj4D/iRpVW7LMvYdbNid7mQZV27JONr9iGtfg9v4KUpPpCvUIDwpnDUdpS6wrp1NrEUANWu9VtGbX4tR2xHVhNBPuYzjOuMG4L8RCdgA1ANKpfqL0W06UEcEUAAhhuHSTGmCFnBH5yKDWQAK0HOC9lb10uhpTDiIOVVVaRZLKRZLJTu3BnRk5eWxxnnmdVEEC8aWdFBFugDMgEK0gvoqe2JOACAoijs2bsXh8uFomz8/zztPE1RRpGQVkDbHETjVaBdyJGmEZKNyRsOACtLstGyiFANKkMNQ/rUKTSq9Im1W7zGTWA/gLPAyZ0Td5Ckzf8CS4uLABiMEd/BNeGecHP7t9tCTqNRL8TqjHh5S0DIkoz/gD8qxL27dznkdHLI6eSHmZmoIL5mH4oUXkJPaExAzIiGCXgIvALQvqedKy1Xohbeb7Px5PFjAAqLi7k5Nxe1z8lvTzL+YFzIX4FKNAJyCKdHQKQnpaO5tagFARYCgfB9QHe/WfR7+skwh3fkEkLHSoWLbOM514AkgIGGARpLGmMqml9UxPfT05hVlQujo5SUlUXtk2JKwagYufXLLZF6DTeTEhrjwLsA5VnlzHbMrjl5RYtgMAgQ9X3Sx+LyIrZPbTx69kikPpfQeEpo3zAbzKgGNeaCW4n5xXn94empAfhLgASWAgSWYlvrFxx/ysgcB+4Dwf8BIAjcR+b4f6x87Y5JImrSAAAAAElFTkSuQmCC" y="-174.73952" x="1222.5098" height="48.450024" width="48.450024"/>' +
    '<image id="' +
    element.id +
    suffix +
    ':pic1" xlink:href="data:image/png;base64,' +
    pictograms[picToShow.value] +
    '" y="-174.73952" x="1222.5098" height="48.450024" width="48.450024"/>' +
    "</g>" +
    "</g>";

  return pmv_str;
}

export function changeMatrixPanelIcon(element, suffix = "") {
  // adds the pictograph
  // currently its fixed to pic1.jpg
  let pmv_pic = document.getElementById(element.id + suffix + ":rect2250-2-89"); // selects the pictograph rectangle
  let r = Snap(pmv_pic);
  let rpg = r.parent().group();
  let rpg_image = rpg.image(
    "./img/pic1.jpg",
    r.node.getAttribute("x"),
    r.node.getAttribute("y"),
    r.node.getAttribute("width"),
    r.node.getAttribute("height")
  );
}

export function updateMatrixPanelIcon(element, suffix = "") {
  // check for the status message we should use
  let picToShow = element.elementValues.find(
    (ic) =>
      elementTypeMap.getElementTypeParam(ic.id) ==
      elementTypeMap.MATRIX_PANEL_PICTOGRAM
  );

  // changes the color based on the status
  let color = "";
  let elementState = "";
  if (typeof element.state != "undefined") {
    elementState = element.state;
  } else {
    elementState = state;
  }

  if (typeof elementState != "undefined") {
    if (elementState == 0) {
      color = "gold";
    } else if (elementState == 1) {
      color = "silver";
    } else if (elementState == 2) {
      color = "purple";
    } else if (elementState == 3) {
      color = "red";
    }
  }
  
  // icon based on status
  if (document.getElementById(element.id + suffix + ":rect2250-2-89") != null) {
    let color = "";
    let elementState = element.state;

    if (elementState == 0) {
      color = "#ffcc00";
    } else if (elementState == 1) {
      color = "#c0c0c0";
    } else if (elementState == 2) {
      color = "#800080";
    } else if (elementState == 3) {
      color = "#ff0000";
    }

    document.getElementById(element.id + suffix + ":rect2250-2-89").style[
      "stroke"
    ] = color;
  }
  
  if (typeof picToShow != "undefined") {
    document
      .getElementById(element.id + suffix + ":pic1")
      .setAttribute(
        "xlink:href",
        "data:image/png;base64," + pictograms[picToShow.value]
      );
  } else {
    alert("INVALID");
  }

}
