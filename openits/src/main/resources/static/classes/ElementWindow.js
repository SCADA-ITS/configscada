import TabForm from "../classes/ElementForm.js";
import ElementChildsList from "../classes/ElementChildsList.js";
import TabVideo from "../classes/TabVideo.js";
import TabVideoList from "../classes/TabVideoList.js";
import TabSynoptic from "../classes/TabSynoptic.js";
import {columnsElementFormList} from "../data/elementForm.js";
import {ELEMENT_TYPE} from "../data/GLOBAL_PROPERTIES.js";
import {synopticData} from "../data/synoptics.js";

export default class ElementWindow {
    constructor(parentId,parentType) {
        this.parentId = parentId;
        this.parentType = parentType;
    }

    getComponent(){
        let result = {};
        var parentId = this.parentId;
        var parentType = this.parentType;
        this.elementForm = new TabForm(parentId,null,columnsElementFormList);
        var elementF = this.elementForm;
        var v_synoptic = null;
        var tabs = null;
        var idSyn = null;
        try {
            tabs = ELEMENT_TYPE.values[this.parentType] !== undefined && ELEMENT_TYPE.values[this.parentType].tabs !== undefined && ELEMENT_TYPE.values[this.parentType].tabs.length >= 1 ? ELEMENT_TYPE.values[this.parentType].tabs : null;
        } catch (e) {
            console.log(e);
        }

        if (tabs !== null) {
            let tabsArray = [];
            let counter = 0; //Counter para controlar que sino existe ninguna tab en la variable tab pongamos por defecto la tabForm. 
            
            tabs.forEach(element => {
                if(element["ELEMENT_FORM_TAB_FORM"] !== undefined) {
                    tabsArray.push({ id: this.parentId + "_" + this.parentType + "_TAB_FORM" , value: $.i18n("element.window.tab.form"), width: 100 });
                    counter += 1;
                }
                if(element["ELEMENT_FORM_TAB_VIDEO"] !== undefined) {
                    tabsArray.push({ id: this.parentId + "_" + this.parentType + "_TAB_VIDEO", value: $.i18n("element.window.tab.video"), width: 100 });
                    counter += 1;
                }
                if(element["ELEMENT_FORM_TAB_SYN"] !== undefined) {
                    this.idSyn = element["ELEMENT_FORM_TAB_SYN"].idSynoptic;
                    idSyn = this.idSyn;
                    v_synoptic = synopticData.find(x => x.id === element["ELEMENT_FORM_TAB_SYN"].idSynoptic);
                    if(v_synoptic !== undefined && v_synoptic !== null) 
                        tabsArray.push({ id: this.parentId + "_" + this.parentType + "_TAB_SYN", value: $.i18n("element.window.tab.syn"), width: 100 });
                        
                    counter += 1;
                }
            });
            if(tabs[0]["ELEMENT_FORM_TAB_FORM"] !== undefined){
                this.defaultTab = 0;
                this.defaultTabView={             
                    id: "cuerpo"+parentId,
                    cols:[this.elementForm.getComponent()],
                    on: {
                        onDestruct: function(obj){
                            //elementForm.disconnect();
                        },
                        onHide: function(obj){
                            this.elementForm.disconnect();
                        }
                    }
                };
            }

            if(tabs[0]["ELEMENT_FORM_TAB_VIDEO"] !== undefined){
                this.defaultTab = 1;
                let urlStream  = null;
                let videoList = false;
                tabs.forEach(element =>{
                    urlStream = element["ELEMENT_FORM_TAB_VIDEO"] !== undefined ? element["ELEMENT_FORM_TAB_VIDEO"].urlParamVideo : urlStream;
                    videoList = element["ELEMENT_FORM_TAB_VIDEO"] !== undefined ? element["ELEMENT_FORM_TAB_VIDEO"].videoList : videoList;
                });
                this.videoList = videoList;
                if(urlStream !== null) {
                    this.urlStream = urlStream;
                    this.tabVideo = new TabVideo(parentId,parentId,urlStream);
                    this.defaultTabView={             
                        id: "cuerpo"+parentId,
                        cols:[this.tabVideo.getComponent()],
                    };
                }else{
                    webix.message({type:"error", text:"ERROR VIDEO NO CONFIGURADO"});
                }
            }

            if(tabs[0]["ELEMENT_FORM_TAB_SYN"] !== undefined){
                this.defaultTab = 2;
                try {
                    let idSynop  = null;
                    let v_width = null;
                    let v_height = null;
                    tabs.forEach(element =>{
                        idSynop  = element["ELEMENT_FORM_TAB_SYN"] !== undefined ? element["ELEMENT_FORM_TAB_SYN"].idSynoptic : idSynop;
                        v_width  = element["ELEMENT_FORM_TAB_SYN"] !== undefined ? element["ELEMENT_FORM_TAB_SYN"].width      : v_width;
                        v_height = element["ELEMENT_FORM_TAB_SYN"] !== undefined ? element["ELEMENT_FORM_TAB_SYN"].height     : v_height;
                    });
                    if(idSynop !== null) {
                        this.tabSynoptic = new TabSynoptic(parentId,idSynop,v_synoptic,v_width,v_height);
                        this.defaultTabView={        
                            id: "cuerpo"+parentId,
                            cols:[this.tabSynoptic.getComponent()],
                        };
                    }else{
                        webix.message({type:"error", text:"ERROR SINOPTICO NO CONFIGURADO"});
                    }
                } catch (e) { console.log(e); }
            }

            if(counter === 0) {
                this.defaultTab = 0;
                this.defaultTabView={             
                    id: "cuerpo"+parentId,
                    cols:[this.elementForm.getComponent()],
                    on: {
                        onDestruct: function(obj){
                            //elementForm.disconnect();
                        },
                        onHide: function(obj){
                            this.elementForm.disconnect();
                        }
                    }
                };
            }
            if(counter === 1){
                result = {
                    id: "window" + this.parentId,
                    rows:
                    [
                        {
                            id: this.parentId + "_" + this.parentType + "_TAB_FORM", 
                            rows: [this.defaultTabView]  
                        }
                    ]
                };
            }else{
                result = {
                    id: "window" + this.parentId,
                    rows:
                    [
                        {
                            id: "tabbar" + this.parentId,
                            view: "tabbar",
                            options:tabsArray,
                            on: {
                                onAfterTabClick: function(obj){
                                    if($$("cuerpo"+parentId)) {
                                        $$(parentId + "_" + parentType + "_TAB_FORM").removeView("cuerpo"+parentId);
                                        if(idSyn !== undefined && idSyn !== null){
                                            if($$("menu" + idSyn + parentId.replaceAll(":","_"))){
                                                $$("menu" + idSyn + parentId.replaceAll(":","_")).destructor();
                                            }
                                        }
                                    }
                                    createTabView(obj,parentId,parentType,elementF,tabs,v_synoptic);
                                }
                            }
                        },
                        {
                            cells: [
                                {
                                    id: this.parentId + "_" + this.parentType + "_TAB_FORM", 
                                    rows: [this.defaultTabView]  
                                }
                            ]
                        }
                    ]
                };
            }

        } else {
            result = {
                id: "cuerpo"+parentId,
                cols:[this.elementForm.getComponent()],
                on: {
                    onDestruct: function(obj){
                        //elementForm.disconnect();
                    },
                    onHide: function(obj){
                        this.elementForm.disconnect();
                        $$("win" + parentId).destructor();
                    }
                }
            };
        }
        return result;

    }
    loadData(){
        if(this.defaultTab === undefined){
            this.elementForm.loadData();
            let hijos = new ElementChildsList(this.parentId);
            hijos.getComponent();
        }else{
            switch(this.defaultTab){
                case 0:
                    this.elementForm.loadData();
                    let hijos = new ElementChildsList(this.parentId);
                    hijos.getComponent();
                break;
                case 1:
                    this.tabVideo.loadData();
                    let videoList = new TabVideoList(this.elementForm.id,this.parentType,this.videoList,this.urlStream);
                    videoList.getComponent();
                break;
                case 2:
                    this.tabSynoptic.loadData();
                break;
            }
        }
    }
}


function createTabView(id,parentId,parentType,elementF,tabs,synoptic) {
    switch (id) {
        case parentId + "_" + parentType + "_TAB_FORM":
            $$(parentId + "_" + parentType + "_TAB_FORM").addView({             
                id: "cuerpo"+parentId,
                cols:[ elementF.getComponent()],
                on: {
                    onDestruct: function(obj){
                        //elementForm.disconnect();
                    },
                    onHide: function(obj){
                        elementF.disconnect();
                    }
                }
            });
            elementF.loadData();
            let hijos = new ElementChildsList(elementF.id);
            hijos.getComponent();
        break;
        case parentId + "_" + parentType + "_TAB_VIDEO":
            let urlStream  = null;
            let videoList = false;
            tabs.forEach(element =>{
                urlStream = element["ELEMENT_FORM_TAB_VIDEO"] !== undefined ? element["ELEMENT_FORM_TAB_VIDEO"].urlParamVideo : urlStream;
                videoList = element["ELEMENT_FORM_TAB_VIDEO"] !== undefined ? element["ELEMENT_FORM_TAB_VIDEO"].videoList : videoList;
            });
            if(urlStream !== null) {
                let tabVideo = new TabVideo(parentId,parentId,urlStream);
                $$(parentId + "_" + parentType + "_TAB_FORM").addView({             
                    id: "cuerpo"+parentId,
                    cols:[tabVideo.getComponent()]
                });
                tabVideo.loadData();
                let evideoList = new TabVideoList(parentId,parentType,videoList,urlStream);
                evideoList.getComponent();
            }else{
                webix.message({type:"error", text:"ERROR VIDEO NO CONFIGURADO"});
            }
        break;
        case parentId + "_" + parentType + "_TAB_SYN":
            try {
                let idSynop  = null;
                let v_width = null;
                let v_height = null;
                tabs.forEach(element =>{
                    idSynop  = element["ELEMENT_FORM_TAB_SYN"] !== undefined ? element["ELEMENT_FORM_TAB_SYN"].idSynoptic : idSynop;
                    v_width  = element["ELEMENT_FORM_TAB_SYN"] !== undefined ? element["ELEMENT_FORM_TAB_SYN"].width      : v_width;
                    v_height = element["ELEMENT_FORM_TAB_SYN"] !== undefined ? element["ELEMENT_FORM_TAB_SYN"].height     : v_height;
                });
                if(idSynop !== null) {
                    //let v_synoptic = synopticData.find(x => x.id === idSynop);
                    let tabSynoptic = new TabSynoptic(parentId,idSynop,synoptic,v_width,v_height);
                    $$(parentId + "_" + parentType + "_TAB_FORM").addView({             
                        id: "cuerpo"+parentId,
                        cols:[tabSynoptic.getComponent()]
                    });
                    tabSynoptic.loadData();
                }else{
                    webix.message({type:"error", text:"ERROR SINOPTICO NO CONFIGURADO"});
                }
            } catch (e) { console.log(e); }
        break;
    } 
}
