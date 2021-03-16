import GenericModule from "./genericModule.js";
import SynopticGraphicElement from "../component/synopticGraphicElement.js";
import {WIDTH_RESOLUTION, HEIGHT_RESOLUTION, ELEMENT_TYPE} from "../data/GLOBAL_PROPERTIES.js";
import { util } from "../lib/util.js";

const SYNOPTIC_REST_ENDPOINT = appConfig.restUrl + "synoptics/";
const SYNOPTIC_WS_TOPIC = "/topic/element.>";

export default class SynopticModule extends GenericModule{

  constructor(module, moduleAction, args) {
    
    super(module, moduleAction, args);
    
    this.elementsMap = new Map(); 
    this.elementsStompMap = new Map();    
    this.synopticDivId = "Syn_"+this.uid;
    this.treeId = "Tree_"+this.synopticDivId;
    this.disconnectStomp = true;
    
    this.synConfigId = module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_SYNOPTIC_ID).value;
    let synopticResponse = webix.ajax().sync().get(SYNOPTIC_REST_ENDPOINT + this.synConfigId);
    if (synopticResponse != undefined) {       
      this.synopticConf = JSON.parse(synopticResponse.responseText);
    }else{
      webix.message({type:"error", text:"INCORRECT SYNOPTIC CONFIG. ID: "+this.synConfigId});
    }
  }

  getView(){
    return {
      cols: [
        {
          header: i18n.t(this.synopticConf.metadata.name),
          body: {
            view:"tree",
            width:220,
            id:this.treeId,
            template:"{common.icon()} {common.checkbox()} &nbsp; "+i18n.t("#value#"),
            on:{
              onItemCheck: (id,state)=>{
                //Layer
                let snapItem = Snap("#"+id);
                if(snapItem != undefined){
                  snapItem.attr({visibility: (state)?"visible":"hidden"}); 
                }
                //child elements
                let layer = this.synopticConf.metadata.synopticLayers.find(item => item.name === id);
                layer.synopticLayerElements.forEach(element => {
                  snapItem = document.getElementById(element.elementId);
                  if(snapItem != undefined){
                    snapItem.style.visibility=(state)?"visible":"hidden"; 
                  }
                });
              }
            },
            url: ()=>{
              let recursiveTreeNode = (treeNode, layerHierarchy, layer) =>{
                treeNode.forEach(node => {
                  if(node.internal_id===layerHierarchy.parent_layer_id){
                    if(!node.data){
                      node.data=[];
                    }
                    node.data.push({id:layer.name, value:layer.label, internal_id:layer.id, open:true});
                  }else{
                    if(node.data != undefined){
                      recursiveTreeNode(node.data, layerHierarchy, layer);
                    }
                  }
                });  
              }

              let treeData=[];
              this.synopticConf.metadata.synopticLayerRelationships.forEach(layerHierarchy => {
                let layer = this.synopticConf.metadata.synopticLayers.find(item => item.id === layerHierarchy.child_layer_id);
                if(layerHierarchy.parent_layer_id == undefined){
                  treeData.push({id:layer.name, value:layer.label, internal_id:layer.id, open:true});
                }else{
                  recursiveTreeNode(treeData, layerHierarchy, layer);
                }
              });
              return treeData;
            },
            ready: function(){
              this.checkAll()
            }
          }
        },
        { view:"resizer" },
        {
          view: "template",
          id: this.synopticDivId,
          url: () => {
            this.init();
          }
        }
      ],
      on: {
        onDestruct: (obj) => {
          this.on_destroy();
        },
        onViewShow: ()=>{
          this.on_show();
        }
      }
    };
  }

  init() {
    // waits for the element and starts drawing
    elementReady('[view_id="' + this.synopticDivId + '"]').then((c) => {
      let spinnerDivToAdd = document.createElement("div");
      spinnerDivToAdd.setAttribute("id", this.synopticDivId + "spinner");
      spinnerDivToAdd.setAttribute("class", "spinner");
      document.querySelector('[view_id="' + this.synopticDivId + '"]').prepend(spinnerDivToAdd);

      var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
      document.querySelector('[view_id="' + this.synopticDivId + '"]').prepend(svg);
      svg.setAttribute("id", this.synopticDivId + "SVG");
      svg.setAttribute("width", c.offsetWidth);
      svg.setAttribute("height", c.offsetHeight);
      svg.setAttribute("class", "synoptic-svg");

      elementReady("#" + this.synopticDivId + "SVG").then((c1) => {
        this.loadData();
        this.doResponsive();
        document.getElementById(this.synopticDivId + "spinner").style.display = "none";
      });
    });
  }

  loadData() {
    let synopticContainer = document.querySelector('[view_id="' + this.synopticDivId + '"]');
    let total_width = synopticContainer.offsetWidth-1;
    let total_height = synopticContainer.offsetHeight-3;
    let ratioWidth = total_width / WIDTH_RESOLUTION;
    let ratioHeight = total_height / HEIGHT_RESOLUTION;

    // Main Layer
    this.snapMainCanvas = Snap("#" + this.synopticDivId + "SVG");
    this.snapMainCanvas.attr({ width: total_width, height: total_height });
    this.snapMainCanvas.image("./img/" + this.synopticConf.metadata.baseLayer, 0, 0, total_width, total_height).attr({id: this.synopticConf.metadata.name });
    
    // layers
    this.synopticConf.metadata.synopticLayers.forEach(layer => {
      try {
        if(layer.baseLayer != undefined){
          this.snapMainCanvas.image("./img/" + layer.baseLayer, 0, 0, total_width, total_height).attr({id: layer.name});
        }
        // Elements
        layer.synopticLayerElements.forEach(layerElement => {
          try {
            let elementData = this.synopticConf.data.find(item => item.id === layerElement.elementId);
            let synopticElement = new SynopticGraphicElement(elementData, layerElement, this.synopticConf);
            synopticElement.paintIcon(layerElement, this.snapMainCanvas,  ratioWidth,  ratioHeight);
            this.elementsMap.set(synopticElement.element.id, synopticElement);
          } catch (elemenError) {
            console.error("Fallo al cargar el elemento: "+ JSON.stringify(layerElement), elemenError);
          }
        });
      } catch (layerError) {
        console.error("Fallo al cargar la capa: "+JSON.stringify(layer), layerError);
      }
    });
    this.connect_ws();
  }

  doResponsive(){
    let self = this;
    $$(this.synopticDivId).$setSize=function(x,y){
      let mainSvg = Snap("#"+self.synopticDivId + "SVG"); 
      let ratioWidth = x / mainSvg.node.attributes.width.value;
      if(ratioWidth == 0) {ratioWidth = 1;}
      let ratioHeight = y / mainSvg.node.attributes.height.value;
      if(ratioHeight == 0) {ratioHeight = 1;}
      
      // Main Svg container
      webix.ui.template.prototype.$setSize.call(this, x,y);
      mainSvg.node.attributes.width.value=x
      mainSvg.node.attributes.height.value=y

      // BaseLayer
      let mainLayerSvg = Snap("#"+self.synopticConf.metadata.name);
      mainLayerSvg.node.attributes.width.value = mainLayerSvg.node.attributes.width.value * ratioWidth;
      mainLayerSvg.node.attributes.height.value = mainLayerSvg.node.attributes.height.value * ratioHeight;

      // Layers
      self.synopticConf.metadata.synopticLayers.forEach(layer => {
        try {
          if(layer.baseLayer != undefined){
            let layerSvg = Snap("#"+layer.name);
            layerSvg.node.attributes.width.value = layerSvg.node.attributes.width.value * ratioWidth;
            layerSvg.node.attributes.height.value = layerSvg.node.attributes.height.value * ratioHeight;
          }
          // Elements
          layer.synopticLayerElements.forEach(layerElement => {
            try {
              let elementSvg = document.getElementById(layerElement.elementId);
              if(elementSvg.attributes.width != undefined) {
                elementSvg.setAttribute("width", parseFloat(elementSvg.getAttribute("width") * ratioWidth) || 0);
              }
              if(elementSvg.attributes.height != undefined) {
                elementSvg.setAttribute("height", parseFloat(elementSvg.getAttribute("height") * ratioHeight) || 0);
              }
              if(elementSvg.attributes.x != undefined) {
                elementSvg.setAttribute("x", parseFloat(elementSvg.getAttribute("x") * ratioWidth) || 0);
              }
              if(elementSvg.attributes.y != undefined) {
                elementSvg.setAttribute("y", parseFloat(elementSvg.getAttribute("y") * ratioHeight) || 0);
              }
              if(elementSvg.attributes.transform != undefined) {
                elementSvg.transform.baseVal[0].matrix["e"] = parseFloat(elementSvg.transform.baseVal[0].matrix["e"] * ratioWidth) || 0;
                elementSvg.transform.baseVal[0].matrix["f"] = parseFloat(elementSvg.transform.baseVal[0].matrix["f"] * ratioHeight) || 0;
                elementSvg.transform.baseVal[0].matrix["a"] = parseFloat(elementSvg.transform.baseVal[0].matrix["a"] * ratioWidth) || 0;
                elementSvg.transform.baseVal[0].matrix["d"] = parseFloat(elementSvg.transform.baseVal[0].matrix["d"] * ratioHeight) || 0;
              }
            } catch (elemenError) {
              console.error("Fallo al reescalar el elemento: "+ layerElement.elementId, elemenError);
            }
          });
        } catch (layerError) {
          console.error("Fallo al reescalar la capa: "+ layer.name, layerError);
        }
      });
    }
  }

  getSelectedIds() {
		
    let selectedIds = [];

		if (this.elementsMap) {
    
      for (let [key, value] of this.elementsMap) {
    
        if(value.isCtrlClicked){
    
          selectedIds.push(key);
     
        }
     
      }
		
    }
		
    return selectedIds;

	}

  connect_ws() {

		let on_message = (message) => {
			this.disconnectStomp = false;
			try {
        let obj = JSON.parse(JSON.parse(message.body));
        obj.basicElementsVO.forEach((element) => {
          try {
            if(this.elementsMap && this.elementsMap.get(element.id)){
              this.elementsMap.get(element.id).updateElement(element);
              if(document.getElementById(this.synopticDivId + "SVG") && document.getElementById(element.id)){
                //Deprecated
                // soporte para dual mezclado con visibility del arbol antiguo, bajar a mtx tanto aqui como en elementForm
                if(this.elementsMap.get(element.id).element.elementType.id===ELEMENT_TYPE.ID_30_PMV_128X64){
                  document.getElementById(element.id).remove();
                  this.elementsMap.get(element.id).paintIcon(this.elementsMap.get(element.id).elementUiConfig, this.elementsMap.get(element.id).snapDiv,  this.elementsMap.get(element.id).ratioWidth,  this.elementsMap.get(element.id).ratioHeight);  
                  let checkedElement = document.getElementById(element.id);
                  if(checkedElement != null) {
                    checkedElement.style.opacity = 1;
                    checkedElement.setAttribute("visibility", "visible");
                  }
                }else{
                  this.elementsMap.get(element.id).updatePaint();              
                }
              }else{
                this.elementsStompMap.set(element.id, this.elementsMap.get(element.id).element);
              }
            }
          } catch (error) {
            console.warn(error);
          }
        });
      } catch (err) {
        console.error(err);
      }
		}

		this.stompClient = util.stomp.connect(appConfig.stompUrl, SYNOPTIC_WS_TOPIC, ()=>{}, on_message);
	}

  disconnect_ws() {
		if (this.disconnectStomp) {
			if (this.stompClient && this.stompClient.connected) {
				util.stomp.disconnect(this.stompClient);
				this.stompClient = undefined;
			}
		}
		else {
			this.disconnectStomp = true;
		}
	}

  on_destroy(){
    this.disconnect_ws()
  }

  on_show(){
    if(this.elementsStompMap){
      for (let [key, value] of this.elementsStompMap) {
        this.elementsMap.get(key).updatePaint();
        this.elementsStompMap.delete(key);
      }
    }
  }

}
