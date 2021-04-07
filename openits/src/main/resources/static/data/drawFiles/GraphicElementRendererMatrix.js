import * as elementTypeMap from "../../lib/elementTypeMap.js";
import {ELEMENT_TYPE} from "../GLOBAL_PROPERTIES.js";
import { panelData } from "../panelData.js";
import { availablePictos } from "../pmvpicto.js";

const ELMENT_TYPE_STATE_UI_COLOR = "color";

const ALTERNANCE_TIME = 3000;

export function generateIcon(element, suffix="", graphicIcon, snapDiv) {
  let panelConfig = element.elementValues.find((ic) =>
      elementTypeMap.getElementTypeParam(ic.id) == elementTypeMap.PANEL_MANAGER_CONFIG
  );
  // control stomp 5:1
  if (panelConfig === undefined){
    panelConfig = JSON.parse(panelData[element.elementType.id]["panel"]);
  }else{
    panelConfig = JSON.parse(panelConfig.value);
  }

  let color = element.elementTypeState[ELMENT_TYPE_STATE_UI_COLOR];
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
        
        if(content_value !== null && content_value !== undefined 
          && JSON.parse(content_value).value != undefined){
          let valueData =JSON.parse(content_value);
          content_value = valueData.value[0];
          let contentValues;
          if(Array.isArray(valueData.value) && valueData.value.length > 1){
            contentValues = [];
            for (let index = 0; index < valueData.value.length; index++) {
              contentValues[index] = valueData.value[index];
            }
          }
          let isGraphic=false;
          if(panelConfig["Subpaneles"][nsubpanel]["Zonas"][subindex]["Tipo"] == "Dual"){
            if(parseInt(valueData.type)==1){
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

            function changeImgInterval(snapItem, iterator){
              let nextIterator=iterator+1;
              if(!contentValues[nextIterator]){
                nextIterator = 0;
              }
              if(snapItem.node.href.baseVal == "data:image/png;base64,"+availablePictos[contentValues[iterator]].base64){
                snapItem.node.href.baseVal = "data:image/png;base64," + availablePictos[contentValues[nextIterator]].base64;
              }else{
                changeImgInterval(snapItem, nextIterator);
              }
            }

            if(contentValues){
              
              initSynopticService();

              if(!window.synopticService.synopticIntevals[snapDiv.node.id]){
                window.synopticService.synopticIntevals[snapDiv.node.id] = {};
              }
              if(!window.synopticService.synopticIntevals[snapDiv.node.id][element.id + suffix + ":" + value_to_find]){
                window.synopticService.synopticIntevals[snapDiv.node.id][element.id + suffix + ":" + value_to_find] = true;
              }
              if(!window.synopticService.elementIntevals[element.id + suffix + ":" + value_to_find]){
                window.synopticService.elementIntevals[element.id + suffix + ":" + value_to_find] = setInterval( function alternancePanel(){
                  changeImgInterval(Snap.select("#"+element.id + suffix + ":" + value_to_find), 0);
                }, ALTERNANCE_TIME);
              }
            
            }
            
            let im = s.image("data:image/png;base64," + auxBase64, subpanel_x.toString(), subpanel_y.toString(), subpanel_width.toString(), subpanel_height.toString());
            im.attr("id", element.id + suffix + ":" + value_to_find);
            im.attr("subpanel_type", "graphic");
            if(subpanel_width != subpanel_height) {
              im.attr("preserveAspectRatio", "xMidYMax meet");
            } else {
              im.attr("preserveAspectRatio", "none");
            }
          } else {

            function drawTxtLines(txt){
              let all_lines = txt.split(":$");
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

            if(contentValues){
              drawTxtLines(content_value);
              
              function changeTxtInterval(snapItem, iterator){
                let nextIterator=iterator+1;
                let lines = 0;
                let splitCompate;
                let comparelines = 0;

                if(!contentValues[nextIterator]){
                  nextIterator = 0;
                }
                if(snapItem.children() && snapItem.children().length){
                  lines = snapItem.children().length;
                }
                if(contentValues[iterator]){
                  splitCompate = contentValues[iterator].split(":$");
                  if(splitCompate && splitCompate.length){
                    comparelines = splitCompate.length
                  }
                }

                if(lines===comparelines){
                  let compareEquals = true;
                  for (let index = 0; index < lines; index++) {
                    if(snapItem.children()[index].node.innerHTML != splitCompate[index]){
                      compareEquals = false;
                    }
                  }
                  if(compareEquals){
                    for (let index = 0; index < lines; index++) {
                      if(contentValues[nextIterator] && contentValues[nextIterator].split(":$") && contentValues[nextIterator].split(":$")[index] ){
                        snapItem.children()[index].node.innerHTML=contentValues[nextIterator].split(":$")[index];
                      }else{
                        snapItem.children()[index].node.innerHTML="";
                      }
                    }
                  }else{
                    changeTxtInterval(snapItem, nextIterator);
                  }
                }else{
                  changeTxtInterval(snapItem, nextIterator);
                }

              }

              initSynopticService();

              if(!window.synopticService.synopticIntevals[snapDiv.node.id]){
                window.synopticService.synopticIntevals[snapDiv.node.id] = {};
              }
              if(!window.synopticService.synopticIntevals[snapDiv.node.id][element.id + suffix + ":" + value_to_find]){
                window.synopticService.synopticIntevals[snapDiv.node.id][element.id + suffix + ":" + value_to_find] = true;
              }
              if(!window.synopticService.elementIntevals[element.id + suffix + ":" + value_to_find]){
                window.synopticService.elementIntevals[element.id + suffix + ":" + value_to_find] = setInterval( function alternancePanel(){
                  changeTxtInterval(Snap.select("#"+element.id + suffix + ":" + value_to_find), 0);
                }, ALTERNANCE_TIME);
              }
 
            } else if(content_value != null) {
              drawTxtLines(content_value);
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

  let snapIcon = Snap.parse(retSVG);
  let el = snapDiv.group().append(snapIcon);
  return el;
}

export function getElementIcon(element, suffix="") {

  let iconName = "icon.svg";
  let width = 0;
  let height = 0;
  let color = element.elementTypeState[ELMENT_TYPE_STATE_UI_COLOR];

  // search in the values for an id greater than 1001
  for(let i=0; i<element.elementValues.length; i++) {
    let elementTypeParam = parseInt(elementTypeMap.getElementTypeParam(element.elementValues[i].id));
    if((elementTypeParam > 1000) && (elementTypeParam < 2000) && (parseInt(element.elementValues[i].id.split(":")[4]) === 2 )){
      let picToShow = element.elementValues.find(
        (ic) => elementTypeMap.getElementTypeParam(ic.id) == elementTypeParam.toString() + ":2"
      );

      let subpanelType = "";
      let contentValues;

      if(picToShow !== undefined){
        try{ 
          let valueData =JSON.parse(picToShow.value);
          if(valueData){
            subpanelType = valueData.type === "1" ? "graphical" : "text";
            picToShow = valueData;
          }
        } catch(e){console.log(e);}

        if(Array.isArray(picToShow.value) && picToShow.value.length > 1){
          contentValues = [];
          for (let index = 0; index < picToShow.value.length; index++) {
            contentValues[index] = picToShow.value[index];
          }
        }

        //Clear previus interval if exists
        initSynopticService();
        if(window.synopticService.elementIntevals[element.id + suffix + ":" + elementTypeParam]){
          clearInterval(window.synopticService.elementIntevals[element.id + suffix + ":" + elementTypeParam]);
          delete window.synopticService.elementIntevals[element.id + suffix + ":" + elementTypeParam];
        }
        
        if(subpanelType == "graphical") {
          
          if (typeof picToShow != "undefined" && document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
            if (availablePictos[picToShow.value[0]] && typeof availablePictos[picToShow.value[0]].base64 != "undefined") {
              document.getElementById(element.id + suffix + ":" + elementTypeParam).setAttribute("href","data:image/png;base64," + availablePictos[picToShow.value[0]].base64);
            } else {
              document.getElementById(element.id + suffix + ":" + elementTypeParam).setAttribute("href", "data:image/png;base64," + availablePictos["undefined"].base64);
            }
          } else {
              if(document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
                document.getElementById(element.id + suffix + ":" + elementTypeParam).setAttribute("href","data:image/png;base64," + availablePictos["undefined"].base64);
              }
          }

          if(contentValues){
            
            function changeImgInterval(snapItem, iterator){
              let nextIterator=iterator+1;
              if(!contentValues[nextIterator]){
                nextIterator = 0;
              }
              if(snapItem.node.href.baseVal == "data:image/png;base64,"+availablePictos[contentValues[iterator]].base64){
                snapItem.node.href.baseVal = "data:image/png;base64," + availablePictos[contentValues[nextIterator]].base64;
              }else{
                changeImgInterval(snapItem, nextIterator);
              }
            }
                
            if(!window.synopticService.elementIntevals[element.id + suffix + ":" + elementTypeParam]){
              window.synopticService.elementIntevals[element.id + suffix + ":" + elementTypeParam] = setInterval( function alternancePanel(){
                changeImgInterval(Snap.select("#"+element.id + suffix + ":" + elementTypeParam), 0);
              }, ALTERNANCE_TIME);
            }
            
          }

        } else if(subpanelType == "text") {  // text
          
          if (typeof picToShow != "undefined" && document.getElementById(element.id + suffix + ":" + elementTypeParam) != null) {
            if(picToShow.value[0] != null) {
              let all_lines = null;
              all_lines = picToShow.value[0].split(":$");
              all_lines.forEach((item, i) => { 
                if (document.getElementById(element.id + suffix + ":" + elementTypeParam + ":Line" + (i + 1)) != null) {
                  document.getElementById(element.id + suffix + ":" + elementTypeParam + ":Line" + (i + 1)).textContent = item;
                }
              });
            } else if(typeof picToShow.value[0] == 'undefined' || picToShow.value[0] == null) {
              // clean the panel if no data is received
              for(let i=0; i<document.querySelectorAll('[id^="' + element.id + suffix + ':' + elementTypeParam + ':Line"]').length; i++) {
                document.querySelectorAll('[id^="' + element.id + suffix + ':' + elementTypeParam + ':Line"]')[i].textContent = "";
              }
            }
          }

          if(contentValues){
            
            function changeTxtInterval(snapItem, iterator){
              let nextIterator=iterator+1;
              let lines = 0;
              let splitCompate;
              let comparelines = 0;

              if(!contentValues[nextIterator]){
                nextIterator = 0;
              }
              if(snapItem.children() && snapItem.children().length){
                lines = snapItem.children().length;
              }
              if(contentValues[iterator]){
                splitCompate = contentValues[iterator].split(":$");
                if(splitCompate && splitCompate.length){
                  comparelines = splitCompate.length
                }
              }
             
              if(lines===comparelines){
                let compareEquals = true;
                for (let index = 0; index < lines; index++) {
                  if(snapItem.children()[index].node.innerHTML != splitCompate[index]){
                    compareEquals = false;
                  }
                }
                if(compareEquals){
                  for (let index = 0; index < lines; index++) {
                    if(contentValues[nextIterator] && contentValues[nextIterator].split(":$") && contentValues[nextIterator].split(":$")[index] ){
                      snapItem.children()[index].node.innerHTML=contentValues[nextIterator].split(":$")[index];
                    }else{
                      snapItem.children()[index].node.innerHTML="";
                    }
                  }
                }else{
                  changeTxtInterval(snapItem, nextIterator);
                }
              }else{
                changeTxtInterval(snapItem, nextIterator);
              }

            }

            if(!window.synopticService.elementIntevals[element.id + suffix + ":" + elementTypeParam]){
              window.synopticService.elementIntevals[element.id + suffix + ":" + elementTypeParam] = setInterval( function alternancePanel(){
                changeTxtInterval(Snap.select("#"+element.id + suffix + ":" + elementTypeParam), 0);
              }, ALTERNANCE_TIME);
            }

          }

        }
      }
    }

    if(Snap.select("#"+element.id + suffix +":stroke0") && Snap.select("#"+element.id + suffix +":stroke0").node && Snap.select("#"+element.id + suffix +":stroke0").node.attributes.stroke){
      Snap.select("#"+element.id + suffix +":stroke0").node.attributes.stroke.value=color;
    }
  }
  return { icon: iconName, width: width, height: height };
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
  let elementState = "";
  if(element.elementTypeState && element.elementTypeState.id) {
    elementState = getTrimId(element.elementTypeState.id);
  } 
  let iconStr = '<g id="' + element.id + suffix + '" state="' + elementState + '" transform="scale(' + (width / ELEMENT_TYPE.values[element.elementType.id].scale_x) + ',' + (height / ELEMENT_TYPE.values[element.elementType.id].scale_y) + ')">' + generateIcon(element, suffix) + '</g>';
  return '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape" xmlns:sodipodi="http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd" version="1.1" width="' + width + 'px" height="' + height + 'px">' + iconStr + '</svg>';
}


function getLinesOfText(panelType, zone, subPanel) {
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

function initSynopticService(){
  if(!window.synopticService){
    window.synopticService = {};
  }
  if(!window.synopticService.elementIntevals){
    window.synopticService.elementIntevals = {};
  }
  if(!window.synopticService.synopticIntevals){
    window.synopticService.synopticIntevals = {};
  }
}