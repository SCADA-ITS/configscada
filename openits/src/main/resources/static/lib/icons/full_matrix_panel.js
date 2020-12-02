import * as elementTypeMap from "../elementTypeMap.js";
import { panelData } from "../../data/panelData.js";
import { availablePictos } from "../../data/pmvpicto.js";
import {ICON_COLOR} from "../../data/GLOBAL_PROPERTIES.js";

export function getLinesOfText(panelType, zone, subPanel) {
  let panelConfig = JSON.parse(panelData[panelType]["panel"]);
  let nLines = 0;
  for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
    let n_zones = panelConfig["Subpaneles"][nsubpanel]["NumZonas"];
    let zone_cols = parseInt(n_zones.split("x")[0]);
    let zone_rows = parseInt(n_zones.split("x")[1]);
    for(let r=0; r<zone_rows; r++) {
      for(let c=0; c<zone_cols; c++) {
        if((zone == r) && (subPanel == c) && (panelConfig["Subpaneles"][nsubpanel]["Zonas"][r+c]["Tipo"] == "Texto")) {
          nLines = panelConfig["Subpaneles"][nsubpanel]["Zonas"][r+c]["NumLineas"];
        }
      }
    }
  }
  
  return parseInt(nLines);
}


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

  if(typeof elementState != 'undefined') {
    color = ICON_COLOR[elementState];
  }

  
  let subpanel_matrix = [];
  let total_width = 0;
  let total_height = 0;
  let cummulative_width = 0;  // used when there are several subpanels
  let index= 0;
  let s = Snap("#svgDiv");
  s.clear();
  
  // Generate the matrix with sizes
  for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
    let n_zones = panelConfig["Subpaneles"][nsubpanel]["NumZonas"];
    let zone_cols = parseInt(n_zones.split("x")[0]);
    let zone_rows = parseInt(n_zones.split("x")[1]);

    let n_size = panelConfig["Subpaneles"][nsubpanel]["Tamanio"];
    let panel_width = parseInt(n_size.split("x")[0]);
    let panel_height = parseInt(n_size.split("x")[1]);
    total_width += panel_width;
    if(panel_height > total_height) {
      total_height = panel_height;
    }

    index= 0;
    for(let r=0; r<zone_rows; r++) {
      subpanel_matrix.push([]);
      for(let c=0; c<zone_cols; c++) {
        let subpanel_size = panelConfig["Subpaneles"][nsubpanel]["Zonas"][index]["Tamanio"];
        let subpanel_height = parseInt(subpanel_size.split("x")[1]);
        let subpanel_width = parseInt(subpanel_size.split("x")[0]);

        subpanel_matrix[r].push({width: subpanel_width, height: subpanel_height})
        index++;
      }
    }
  }

  let background_rect = s.rect(0, 0, total_width, total_height);
  background_rect.attr("fill", "black");
  s.attr({ height: total_height, width: total_width});

  let n_elements = 0;

  for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
    let n_zones = panelConfig["Subpaneles"][nsubpanel]["NumZonas"];
    let zone_cols = parseInt(n_zones.split("x")[0]);
    let zone_rows = parseInt(n_zones.split("x")[1]);
    let n_size = panelConfig["Subpaneles"][nsubpanel]["Tamanio"];
    let panel_width = parseInt(n_size.split("x")[0]);
    let panel_height = parseInt(n_size.split("x")[1]);

    let subpanel_x = 0;
    let subpanel_y = 0;
    let last_subpanel_height = 0;
    
    index = 0;
    let subindex = 0;
    for(let r=0; r<zone_rows; r++) {
      // calculate the row covered size
      let row_covered_size = 0;
      for(let c=0; c<zone_cols; c++) {
        let subpanel_size = panelConfig["Subpaneles"][nsubpanel]["Zonas"][index]["Tamanio"];
        let subpanel_height = parseInt(subpanel_size.split("x")[1]);
        let subpanel_width = parseInt(subpanel_size.split("x")[0]);

        row_covered_size += subpanel_width;
        index++;
        
      }
    
      // sets the initial position based on the covered area
      subpanel_x = cummulative_width;
      
      
      for(let c=0; c<zone_cols; c++) {
        let subpanel_size = panelConfig["Subpaneles"][nsubpanel]["Zonas"][subindex]["Tamanio"];
        let subpanel_height = parseInt(subpanel_size.split("x")[1]);
        let subpanel_width = parseInt(subpanel_size.split("x")[0]);

        // find the element with that value
        let value_to_find = 1001 + n_elements;
        let content_value = element.elementValues.find( (value) => value.id === "ElementValue:" + elementTypeMap.getElementId(element.id) + ":" + value_to_find + ":2").value;
        
        let isGraphic=false;
        if(panelConfig["Subpaneles"][nsubpanel]["Zonas"][subindex]["Tipo"] == "Dual"){
          let value_to_find2 = 2001 + n_elements;
          let content_value2 = element.elementValues.find( (value) => value.id === "ElementValue:" + elementTypeMap.getElementId(element.id) + ":" + value_to_find2 + ":2").value;
          
          if(parseInt(content_value2)==1){
            isGraphic=true;
          }
        }else if(panelConfig["Subpaneles"][nsubpanel]["Zonas"][subindex]["Tipo"] == "Grafico"){
          isGraphic=true;
        }
        if(isGraphic) {
          let auxBase64;
          if(availablePictos[content_value]){
            auxBase64=availablePictos[content_value].base64;
          }else{
            auxBase64=availablePictos["0"].base64
          }
          
          //FIX ajuste iconos paneles en el mapa
          // subpanel_width = subpanel_width - 20
          // subpanel_height = subpanel_height - 20
          // subpanel_x = subpanel_x + 1
          // subpanel_y = subpanel_y + 2
          
          let im = s.image("data:image/png;base64," + auxBase64, subpanel_x.toString(), subpanel_y.toString(), subpanel_width.toString(), subpanel_height.toString());
          im.attr("id", element.id + suffix + ":" + value_to_find);
          im.attr("subpanel_type", "graphic");
          if(subpanel_width != subpanel_height) {
            im.attr("preserveAspectRatio", "xMidYMax meet");
          } else {
            im.attr("preserveAspectRatio", "none");
          }
        } else {
          if(content_value != null) {
            let all_lines = content_value.split(":$");
            let g1 = s.group();
            g1.attr("id", element.id + suffix + ":" + value_to_find);
            g1.attr("subpanel_type", "text");
            all_lines.forEach((item, i) => { 
              let r1 = g1.text((subpanel_x + 3).toString(), (10 + subpanel_y + (subpanel_height / all_lines.length * i)).toString(), item);
              r1.attr("id", element.id + suffix + ":" + value_to_find + ":Line" + (i + 1));
              r1.attr("fill", "white");
              r1.attr("font-family", "'VT323', monospace")
              r1.attr("font-size", "10px");
            });
          } else {
            // if there is no text, we create the placeholder based on the panel configuration
            let nLines = getLinesOfText("ElementType:" + elementTypeMap.getElementType(element.id), r, c);
            let all_lines = [];
            for(let iLine=0; iLine<nLines; iLine++) {
              all_lines.push([]);
            }

            let g1 = s.group();
            g1.attr("id", element.id + suffix + ":" + value_to_find);
            g1.attr("subpanel_type", "text");
            all_lines.forEach((item, i) => { 
              let r1 = g1.text((subpanel_x + 3).toString(), (10 + subpanel_y + (subpanel_height / all_lines.length * i)).toString(), item);
              r1.attr("id", element.id + suffix + ":" + value_to_find + ":Line" + (i + 1));
              r1.attr("fill", "white");
              r1.attr("font-family", "'VT323', monospace")
              r1.attr("font-size", "10px");
            });

          }
        }

        last_subpanel_height = subpanel_height;
        subpanel_x += subpanel_width;

        n_elements++;
        subindex++;
      }
      subpanel_y += last_subpanel_height;
    }
    cummulative_width += panel_width;
  }
  
  let foreground_rect = s.rect(0, 0, total_width, total_height).attr({id: element.id + suffix + ":stroke0"});
  foreground_rect.attr("fill", "transparent");
  foreground_rect.attr("stroke", color);
  foreground_rect.attr("stroke-width", "3px");

  let retSVG = s.innerSVG();

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
        (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeParam.toString() + ":2"
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
          if (availablePictos[picToShow.value] && typeof availablePictos[picToShow.value].base64 != "undefined") {
            document.getElementById(element.id + suffix + ":" + elementTypeParam).setAttribute("href","data:image/png;base64," + availablePictos[picToShow.value].base64);
          } else {
            document.getElementById(element.id + suffix + ":" + elementTypeParam).setAttribute("href", "data:image/png;base64," + availablePictos["undefined"].base64);
          }
        } 
        else {
            if(document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
              document.getElementById(element.id + suffix + ":" + elementTypeParam).setAttribute("href","data:image/png;base64," + availablePictos["undefined"].base64);
            }
        }
      } else if(subpanelType == "text") {  // text
        if (typeof picToShow != "undefined" && document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
          if(picToShow.value != null) {
            let all_lines = picToShow.value.split(":$");
            all_lines.forEach((item, i) => { 
              if (document.getElementById(element.id + suffix + ":" + elementTypeParam + ":Line" + (i + 1)) != null) {
                document.getElementById(element.id + suffix + ":" + elementTypeParam + ":Line" + (i + 1)).textContent = item;
              }
            });
          } else if(typeof picToShow.value == 'undefined' || picToShow.value == null) {
            // clean the panel if no data is received
            for(let i=0; i<document.querySelectorAll('[id^="' + element.id + suffix + ':' + elementTypeParam + ':Line"]').length; i++) {
              document.querySelectorAll('[id^="' + element.id + suffix + ':' + elementTypeParam + ':Line"]')[i].textContent = "";
            }
          }
        
        }
      }
    }
  }

   // changes the color based on the status
   let elementState;
   if (typeof element != "undefined") {
     elementState = element.state;
   } else {
     elementState = null;
   }
  let color = ICON_COLOR[elementState];
  if(Snap.select("#"+element.id + suffix +":stroke0") && Snap.select("#"+element.id + suffix +":stroke0").node && Snap.select("#"+element.id + suffix +":stroke0").node.attributes.stroke){
    Snap.select("#"+element.id + suffix +":stroke0").node.attributes.stroke.value=color;
  }
}
