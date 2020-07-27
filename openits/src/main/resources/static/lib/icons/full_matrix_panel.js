import * as elementTypeMap from "../elementTypeMap.js";
import { pictograms } from "../../data/pictograms.js";

export function generateIcon(element, suffix = "") {

  // check for the panel config
  let panelConfig = element.elementValues.find(
    (ic) =>
      elementTypeMap.getElementTypeParam(ic.id) ==
      elementTypeMap.PANEL_MANAGER_CONFIG
  );

  panelConfig = JSON.parse(panelConfig.value);

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
      color = "green";
    } else if (elementState == 2) {
      color = "purple";
    } else if (elementState == 3) {
      color = "red";
    }
  }

  
  let subpanel_matrix = [];
  let total_width = 0;
  let cummulative_width = 0;  // used when there are several subpanels
  
  let s = Snap("#svgDiv");
  s.clear();
  let background_rect = s.rect(0, 0, "100%", "100%");
  background_rect.attr("fill", "black");
  //background_rect.attr("stroke", color);
  //background_rect.attr("stroke-width", "4%");

  // Generate the matrix with sizes
  for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
    let n_zones = panelConfig["Subpaneles"][nsubpanel]["NumZonas"];
    let zone_cols = parseInt(n_zones.split("x")[0]);
    let zone_rows = parseInt(n_zones.split("x")[1]);

    let n_size = panelConfig["Subpaneles"][nsubpanel]["Tamanio"];
    let panel_width = parseInt(n_size.split("x")[0]);
    total_width += panel_width;

    
    for(let r=0; r<zone_rows; r++) {
      subpanel_matrix.push([]);
      for(let c=0; c<zone_cols; c++) {
        let subpanel_size = panelConfig["Subpaneles"][nsubpanel]["Zonas"][r+c]["Tamanio"];
        let subpanel_height = parseInt(subpanel_size.split("x")[1]);
        let subpanel_width = parseInt(subpanel_size.split("x")[0]);

        subpanel_matrix[r].push({width: subpanel_width, height: subpanel_height})
      }
    }
  }

  for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
    let n_zones = panelConfig["Subpaneles"][nsubpanel]["NumZonas"];
    let zone_cols = parseInt(n_zones.split("x")[0]);
    let zone_rows = parseInt(n_zones.split("x")[1]);
    let n_size = panelConfig["Subpaneles"][nsubpanel]["Tamanio"];
    let panel_width = parseInt(n_size.split("x")[0]);
    let panel_height = parseInt(n_size.split("x")[1]);

    s.attr({ height: panel_height, width: panel_width});
    //let background_rect = s.rect(0, 0, "100%", "100%");
    //background_rect.attr("fill", "black");
    
    let subpanel_x = 0;
    let subpanel_y = 0;
    let last_subpanel_height = 0;
    let n_elements = 0;

    for(let r=0; r<zone_rows; r++) {
      // calculate the row covered size
      let row_covered_size = 0;
      for(let c=0; c<zone_cols; c++) {
        let subpanel_size = panelConfig["Subpaneles"][nsubpanel]["Zonas"][r+c]["Tamanio"];
        let subpanel_height = parseInt(subpanel_size.split("x")[1]);
        let subpanel_width = parseInt(subpanel_size.split("x")[0]);

        row_covered_size += (subpanel_width / total_width) * 100;
      }

      // sets the initial position based on the covered area
      //subpanel_x = row_covered_size / 2;
      subpanel_x = cummulative_width;

      for(let c=0; c<zone_cols; c++) {
        let subpanel_size = panelConfig["Subpaneles"][nsubpanel]["Zonas"][r+c]["Tamanio"];
        let subpanel_height = parseInt(subpanel_size.split("x")[1]);
        let subpanel_width = parseInt(subpanel_size.split("x")[0]);

        // find the element with that value
        let value_to_find = 1001 + n_elements;
        let content_value = element.elementValues.find( (value) => value.id === "ElementValue:" + elementTypeMap.getElementId(element.id) + ":" + value_to_find + ":2").value;
        
        //let im = s.image("/img/pictograms/" + content_value + ".bmp" ,subpanel_x, subpanel_y, subpanel_width, subpanel_height);
        if(panelConfig["Subpaneles"][nsubpanel]["Zonas"][r+c]["Tipo"] == "Grafico") {
          //let im = s.image("/img/pictograms/" + content_value + ".bmp", subpanel_x.toString() + "%", subpanel_y.toString() + "%", (subpanel_width / total_width * 100).toString() + "%", (subpanel_height / panel_height * 100).toString() + "%");
          let im = s.image("data:image/png;base64," + pictograms[content_value], subpanel_x.toString() + "%", subpanel_y.toString() + "%", (subpanel_width / total_width * 100).toString() + "%", (subpanel_height / panel_height * 100).toString() + "%");
          im.attr("id", element.id + suffix + ":" + value_to_find);
          im.attr("subpanel_type", "graphic");
          if(subpanel_width != subpanel_height) {
            im.attr("preserveAspectRatio", "xMidYMax meet");
          } else {
            im.attr("preserveAspectRatio", "none");
          }
        } else {
          let all_lines = content_value.split(":$");
          let g1 = s.group();
          g1.attr("id", element.id + suffix + ":" + value_to_find);
          g1.attr("subpanel_type", "text");
          all_lines.forEach((item, i) => { 
            let r1 = g1.text(subpanel_x.toString() + "%", (100 - (subpanel_y + subpanel_height / panel_height * 100 - 25) + (100 / all_lines.length * i)).toString() + "%", item);
            r1.attr("id", element.id + suffix + ":" + value_to_find + ":Line" + (i + 1));
            //r1.attr("subpanel_type", "text");
            r1.attr("fill", "white");
            //r1.attr("font-size", "120px");
            // FIXME
            if(suffix == ":Form") {
              r1.attr("font-size", "0.63em");
            } else {
              r1.attr("font-size", "10em");
            }
            r1.attr("font-weight", "bold");
          });
          /*
          let r1 = s.text(subpanel_x.toString() + "%", (subpanel_y + subpanel_height / panel_height * 100 - 5).toString() + "%", content_value);
          r1.attr("id", element.id + suffix + ":" + value_to_find);
          r1.attr("fill", "white");
          r1.attr("font-size", "90px");
          r1.attr("font-weight", "bold");
          */
        }

        last_subpanel_height = (subpanel_height  / panel_height) * 100;
        subpanel_x += (subpanel_width / total_width) * 100;

        n_elements++;
      }

      subpanel_y += last_subpanel_height;
    }

    cummulative_width += panel_width / total_width * 100;
    
  }

  let foreground_rect = s.rect(0, 0, "100%", "100%");
  foreground_rect.attr("fill", "transparent");
  foreground_rect.attr("stroke", color);
  foreground_rect.attr("stroke-width", "4%");

  let retSVG = s.innerSVG()

  s.clear();

  return retSVG;
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
  // search in the values for an id greater than 1001

  for(let i=0; i<element.elementValues.length; i++) {
    let elementTypeParam = parseInt(elementTypeMap.getElementTypeParam(element.elementValues[i].id));

    if(elementTypeParam > 1000) {
      let picToShow = element.elementValues.find(
        (ic) =>
          elementTypeMap.getElementTypeParam(ic.id) ==
          elementTypeParam.toString() + ":2"
      );

      let subpanelType = "";
      if(document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
        if(document.getElementById(element.id + suffix + ":" + elementTypeParam).getAttribute("subpanel_type") == "text") {
          subpanelType = "text";
        } else {
          subpanelType = "graphical";
        }
      }

      if(subpanelType == "graphical") {
        if (typeof picToShow != "undefined" && document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
          if (typeof pictograms[picToShow.value] != "undefined") {
            document
              .getElementById(element.id + suffix + ":" + elementTypeParam)
              .setAttribute(
                "href",
                "data:image/png;base64," + pictograms[picToShow.value]
              );
          } else {
            document
              .getElementById(element.id + suffix + ":" + elementTypeParam)
              .setAttribute(
                "href",
                "data:image/png;base64," + pictograms["undefined"]
              );
          }
        } 
        else {
            if(document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
              document
              .getElementById(element.id + suffix + ":" + elementTypeParam)
                .setAttribute(
                  "href",
                  "data:image/png;base64," + pictograms["undefined"]
                );
            }
        }
      } else if(subpanelType == "text") {  // text
          if (typeof picToShow != "undefined" && document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
          let all_lines = picToShow.value.split(":$");
          all_lines.forEach((item, i) => { 
            if (document.getElementById(element.id + suffix + ":" + elementTypeParam + ":Line" + (i + 1)) != null) {
              document.getElementById(element.id + suffix + ":" + elementTypeParam + ":Line" + (i + 1)).textContent = item;
            }
          });
          

        }
      }
    }
  }
}

/*
export function updateMatrixPanelIcon(element, suffix = "") {
  // search in the values for an id greater than 1001

  for(let i=0; i<element.elementValues.length; i++) {
    let elementTypeParam = parseInt(elementTypeMap.getElementTypeParam(element.elementValues[i].id));

    if(elementTypeParam > 1000) {
      let picToShow = element.elementValues.find(
        (ic) =>
          elementTypeMap.getElementTypeParam(ic.id) ==
          elementTypeParam.toString() + ":2"
      );

      if (typeof picToShow != "undefined" && document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
        if (typeof pictograms[picToShow.value] != "undefined") {
          document
            .getElementById(element.id + suffix + ":" + elementTypeParam)
            .setAttribute(
              "href",
              "data:image/png;base64," + pictograms[picToShow.value]
            );
        } else {
          document
            .getElementById(element.id + suffix + ":" + elementTypeParam)
            .setAttribute(
              "href",
              "data:image/png;base64," + pictograms["undefined"]
            );
        }
      } 
      else {
          if(document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
            document
            .getElementById(element.id + suffix + ":" + elementTypeParam)
              .setAttribute(
                "href",
                "data:image/png;base64," + pictograms["undefined"]
              );
          }
      }
    }
  }
}
*/
