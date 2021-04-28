import GenericModule from "./genericModule.js";
import ElementWindow from "../classes/ElementWindow.js";
import MapGraphicElement from "../component/mapGraphicElement.js";
import ContextMenu from "../component/contextMenu.js";
import CommandsComponent from "../component/commandsComponent.js";
import { util } from "../lib/util.js";

const MAP_REST_ENDPOINT = appConfig.restUrl + "maps/";
const MAP_WS_TOPIC = "/topic/element.>";

export default class MapModule extends GenericModule{

  constructor(module, moduleAction, args) {
    
    super(module, moduleAction, args);
    
    this.elementsMap = new Map(); 
    this.elementsStompMap = new Map();    
    this.mapDivId = "Map_"+this.uid;
    this.treeId = "Tree_"+this.mapDivId;
    this.disconnectStomp = true;
    this.map;
    
    let mapConfigId = module.moduleValues.find(e => e.moduleParam.id === MODULE_PARAM_MAP_ID).value;
    let mapResponse = webix.ajax().sync().get(MAP_REST_ENDPOINT + mapConfigId);
    
    if (mapResponse != undefined) {       

      this.mapConf = JSON.parse(mapResponse.responseText);    
    } else {
    
      webix.message({type:"error", text:"INCORRECT MAP CONFIG. ID: "+mapConfigId});
    }
  }


  getView(){

    return {
      cols: [
        {
          view: "template",
          id: this.mapDivId,
          url: () => {
            this.init();
          }
        },
        { view:"resizer" },
        {
          header: i18n.t(this.mapConf.metadata.name),
          body: this.getViewTree()
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
    elementReady('[view_id="' + this.mapDivId + '"]').then((c) => {

      let divToAdd = document.createElement("div");
      divToAdd.setAttribute("id", "map");
      document.querySelector('[view_id="' + this.mapDivId + '"]').prepend(divToAdd);

      // instantiates the map
      this.map = new ol.Map({
        target: "map",
        layers: [
          new ol.layer.Tile({
            source: new ol.source.OSM(),
          })
        ],
        view: new ol.View({ 
            center: ol.proj.fromLonLat([this.mapConf.metadata.longitude, this.mapConf.metadata.latitude]), 
            zoom: this.mapConf.metadata.zoom
          }),
        interactions : ol.interaction.defaults({doubleClickZoom : false})
      });

      this.addClickEvents();
      this.loadData();
      this.drawContextButtonAction();
    });
  }

  loadData() {
    // layers
    this.mapConf.metadata.mapLayers.forEach(layer => {
      
      try {
        let markers = [];
        // Elements
        layer.mapLayerElements.forEach(layerElement => {
  
          try {
            let elementData = this.mapConf.data.find(item => item.id === layerElement.elementId);
            let mapElement = new MapGraphicElement(elementData, layerElement, this.mapConf, this);

            let workingFeature = new ol.Feature({
              geometry: new ol.geom.Point(ol.proj.fromLonLat([layerElement.longitude, layerElement.latitude])),
              id: elementData.id
            });

            mapElement.paintOnMap("", workingFeature).then(response =>{
              
              this.loadImageCallback(response, this.paintMarker, mapElement, workingFeature);
            });
            // tmpIcon = tmpIcon.replace(/#/g, "%23");
            // let srcIcon = "data:image/svg+xml;utf8," + tmpIcon;
           
            markers.push(workingFeature);
            this.elementsMap.set(mapElement.element.id, mapElement);
        
          } catch (elemenError) {
  
            console.error("Fallo al cargar el elemento: "+ JSON.stringify(layerElement), elemenError);
          }
        });
        
        let vectorSource = new ol.source.Vector({
          wrapX: false,
          features: markers
        });
    
        let markersLayer = new ol.layer.Vector({
          id: "layer_" + layer.id,
          title: i18n.t(layer.label),
          source: vectorSource
        });
  
        this.map.addLayer(markersLayer);
  
      } catch (layerError) {
  
        console.error("Fallo al cargar la capa: "+JSON.stringify(layer), layerError);
      }
  
    });
    
    this.connect_ws();

  }

  getViewTree(){

    let tree = {
      view:"tree",
      width:220,
      id:this.treeId,
      template:"{common.icon()} {common.checkbox()} &nbsp; "+i18n.t("#value#"),
      on:{
        onItemCheck: (id, state)=>{
          this.map.getLayers().forEach(function(l) {
            if(l.get("id") == "layer_" + id) {
              l.setVisible(state);
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
              node.data.push({id:layer.id, value:layer.label, internal_id:layer.id, open:true});
            }else{
              if(node.data != undefined){
                recursiveTreeNode(node.data, layerHierarchy, layer);
              }
            }
          });  
        }

        let treeData=[];
        this.mapConf.metadata.mapLayerRelationships.forEach(layerHierarchy => {
          let layer = this.mapConf.metadata.mapLayers.find(item => item.id === layerHierarchy.childLayerId);
          if(layerHierarchy.parent_layer_id == undefined){
            treeData.push({id:layer.id, value:layer.label, internal_id:layer.id, open:true});
          }else{
            recursiveTreeNode(treeData, layerHierarchy, layer);
          }
        });
        return treeData;
      },
      ready: function(){
        this.checkAll()
      }
    };
    
    let commandsMenu = new CommandsComponent(this.uid, this.mapConf.metadata.mapCommands, this);

    let viewTree = {
      padding:10,
      css: "panel-action",
      rows: [
        tree,
        commandsMenu.getComponent()
      ]
    };

    return viewTree;
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
    console.warn(selectedIds);
    return selectedIds;
	}

  getApplyAllIds(){
    
    let selectedIds = [];

		if (this.elementsMap) { 
      for (let [key, value] of this.elementsMap) {    
          
        selectedIds.push(key);
      }
    }
    console.warn(selectedIds);
    return selectedIds;
  }

  resetSelectedIds(){
    
    if (this.elementsMap) {
      for (let [key, value] of this.elementsMap) {
        if(value.isCtrlClicked){
          
          this.elementsMap.get(key).isCtrlClicked = false;
          let layer = this.mapConf.metadata.mapLayers.find(item => {return (item.mapLayerElements.find(layerElement => {return layerElement.elementId == key}))});
          let layerFeature;
          this.map.getLayers().forEach(function(l) {
            if(l.get("id") == "layer_"+layer.id) {
              layerFeature = l;
            }
          });
          let feature = layerFeature.getSource().getFeatureById(key);
          let srcIcon = feature.get("featureStyle").getImage().iconImage_.getImage().src;
          let iconWidth = this.elementsMap.get(key).graphicIcon.width;
          let scaleIcon = iconWidth / feature.get("featureStyle").getImage().iconImage_.getImage().width;
          let zTopStyle = new ol.style.Style({
            image: new ol.style.Icon({
              opacity: 1,
              scale: scaleIcon,
              src: srcIcon,
            }),
            zIndex: 1,
          });
          feature.setStyle(zTopStyle);
          feature.set('featureStyle', zTopStyle);
        }
      }
    }

  }

  drawContextButtonAction() {

    let contextMenu = new ContextMenu(this.uid, this.mapConf.metadata.mapCommands, this);
    let contextMenuComponent = contextMenu.getComponent();
    contextMenuComponent.master = "map";
    webix.ui(contextMenuComponent);

	}

  addClickEvents() {

    this.addSingleClick();
    this.addDoubleClick();

  }

  addSingleClick() {
    let singleClick = new ol.interaction.Select({
      condition: ol.events.condition.singleClick
    });

    singleClick.on("select", (e) => {
      //Get Feature
      let featureId = e.target.getFeatures().array_[0].id_;
      let layer = this.mapConf.metadata.mapLayers.find(item => {return (item.mapLayerElements.find(layerElement => {return layerElement.elementId == featureId}))});
      let layerFeature;
      this.map.getLayers().forEach(function(l) {
        if(l.get("id") == "layer_"+layer.id) {
          layerFeature = l;
        }
      });
      let feature = layerFeature.getSource().getFeatureById(featureId);
      e.target.getFeatures().clear();

      let isCtrlClicked = ol.events.condition.platformModifierKeyOnly(e.mapBrowserEvent);
      if (isCtrlClicked) {
        if(this.elementsMap.get(featureId).isCtrlClicked){
          this.elementsMap.get(featureId).isCtrlClicked = false;
        } else {
          this.elementsMap.get(featureId).isCtrlClicked = true;
        }
      } else {
        this.resetSelectedIds();
        this.elementsMap.get(featureId).isCtrlClicked = true;
      }
      
      if(this.elementsMap.get(featureId).isCtrlClicked){
        let srcIcon = feature.get("featureStyle").getImage().iconImage_.getImage().src;
        let iconWidth = this.elementsMap.get(featureId).graphicIcon.width;
        let scaleIcon = (iconWidth+10) / feature.get("featureStyle").getImage().iconImage_.getImage().width;
        let zTopStyle = new ol.style.Style({
          image: new ol.style.Icon({
            opacity: 1,
            scale: scaleIcon,
            src: srcIcon,
          }),
          zIndex: 100,
        });
        feature.setStyle(zTopStyle);
        feature.set('featureStyle', zTopStyle);
      } else {
        let srcIcon = feature.get("featureStyle").getImage().iconImage_.getImage().src;
        let iconWidth = this.elementsMap.get(featureId).graphicIcon.width;
        let scaleIcon = iconWidth / feature.get("featureStyle").getImage().iconImage_.getImage().width;
        // let scaleIcon = feature.get("featureStyle").getImage().getScale();      
        let zTopStyle = new ol.style.Style({
          image: new ol.style.Icon({
            opacity: 1,
            scale: scaleIcon,
            src: srcIcon,
          }),
          zIndex: 1,
        });
        feature.setStyle(zTopStyle);
        feature.set('featureStyle', zTopStyle);
      }
      this.getSelectedIds();
    });
    this.map.addInteraction(singleClick);
  }

  addDoubleClick(){
    let doubleClick = new ol.interaction.Select({
      condition: ol.events.condition.doubleClick
    });
    
    doubleClick.on("select", (e) => {
      //GetFeature
      let featureId = e.target.getFeatures().array_[0].id_;
      e.target.getFeatures().clear();

      if($$("winMap" + featureId) != null){
        $$("winMap" + featureId).destructor();
      }
      let elementWindow = new ElementWindow(featureId,"ElementType:" + featureId.split(":")[1]);
      webix.ui({
        id: "winMap" + featureId,
        view: "window",
        move: true,
        close: true,
        position: "right",
        body: elementWindow.getComponent(),
        on: {
          onDestruct: function(obj){
            //elementForm.disconnect();
          },
          onHide: function(obj){
            //elementForm.disconnect();
            //$$("win" + elementId).destructor();
          },
          onShow: function(obj){
            elementWindow.loadData();
          }
        }
      }).show();

    });

    this.map.addInteraction(doubleClick);
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

              let layer = this.mapConf.metadata.mapLayers.find(item => {return (item.mapLayerElements.find(layerElement => {return layerElement.elementId == element.id}))});
              let layerFeature;
              this.map.getLayers().forEach(function(l) {
                if(l.get("id") == "layer_"+layer.id) {
                  layerFeature = l;
                }
              });
              let feature = layerFeature.getSource().getFeatureById(element.id);
              this.elementsMap.get(element.id).paintOnMap("").then(response =>{
                let srcIcon = response;
                let scaleIcon = feature.get("featureStyle").getImage().getScale();
                let opacity = feature.get("featureStyle").getImage().getOpacity();
                let zIndex = feature.get("featureStyle").getZIndex();
                let zTopStyle = new ol.style.Style({
                  image: new ol.style.Icon({
                    opacity: opacity,
                    scale: scaleIcon,
                    src: srcIcon,
                  }),
                  zIndex: zIndex,
                });
                feature.setStyle(zTopStyle);
                feature.set('featureStyle', zTopStyle);
              });

            }

          } catch (error) {

            console.warn(error);

          }
    
        });
    
      } catch (err) {
    
        console.error(err);
    
      }
		
    }

		this.stompClient = util.stomp.connect(appConfig.stompUrl, MAP_WS_TOPIC, ()=>{}, on_message);
	
  }

  disconnect_ws() {
	
    if (this.disconnectStomp) {
	
      if (this.stompClient && this.stompClient.connected) {
	
        util.stomp.disconnect(this.stompClient);
				this.stompClient = undefined;
	
      }
	
    } else {
		
      this.disconnectStomp = true;
		
    }
	
  }

  close_intervals() {
	
    // if (window.synopticService && window.synopticService.synopticIntevals && window.synopticService.synopticIntevals[this.mapDivId + "SVG"]) {
      
    //   for(let element in window.synopticService.synopticIntevals[this.mapDivId + "SVG"]) {
      
    //     delete window.synopticService.synopticIntevals[this.mapDivId + "SVG"][element];
    //     let isRunningOut = false;
    //     for(let synoptic in window.synopticService.synopticIntevals) {
    //       if(window.synopticService.synopticIntevals[synoptic][element]){
    //         isRunningOut = true;
    //       }
    //     }

    //     if(!isRunningOut){
    //       clearInterval(window.synopticService.elementIntevals[element]);
    //       delete window.synopticService.elementIntevals[element];
    //     }
        
    //   }
      
    //   delete window.synopticService.synopticIntevals[this.mapDivId + "SVG"];

    // }
	
  }

  on_destroy(){

    this.disconnect_ws();
    this.close_intervals();
  }

  on_show(){

    if(this.elementsStompMap){

      for (let [key, value] of this.elementsStompMap) {

        this.elementsMap.get(key).updatePaint();
        this.elementsStompMap.delete(key);

      }

    }

  }

  loadImageCallback(url, callback, mapElement, workingFeature) {
    var img = new Image();
    img.src = url;
    img.onload = function() {
      let iconWidth = mapElement.graphicIcon.width;
      let scaleIcon = 1;
      //DEPRECATED
      if(this.width>500){
        scaleIcon = iconWidth / this.width;
      }
      callback(url, scaleIcon, workingFeature, mapElement.element.id); 
    }
  }
  
  paintMarker(url, scaleIcon, workingFeature, id) {   
    let workingStyle = new ol.style.Style({
      image: new ol.style.Icon({
        opacity: 1,
        src: url,
        scale: scaleIcon
      }),
    });

    workingFeature.setStyle(workingStyle);
    workingFeature.set('featureStyle', workingStyle);
    workingFeature.setId(id);
  }

}
