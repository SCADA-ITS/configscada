import TabForm from "../classes/ElementForm.js";
import ElementChildsList from "../classes/ElementChildsList.js";
import TabVideo from "../classes/TabVideo.js";
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
        this.elementForm = new TabForm(parentId,columnsElementFormList);
        var elementF = this.elementForm;
        
        var tabs = null;
        try {
            tabs = ELEMENT_TYPE.values[this.parentType] !== undefined && ELEMENT_TYPE.values[this.parentType].tabs !== undefined && ELEMENT_TYPE.values[this.parentType].tabs.length > 1 ? ELEMENT_TYPE.values[this.parentType].tabs : null;
        } catch (e) {
            console.log(e);
        }

        if (tabs !== null) {
            let tabsArray = [];
            
            tabs.forEach(element => {
                if(element["ELEMENT_FORM_TAB_FORM"] !== undefined) {
                    tabsArray.push({ id: this.parentId + "_" + this.parentType + "_TAB_FORM" , value: $.i18n("element.window.tab.form"), width: 100 });
                }
                if(element["ELEMENT_FORM_TAB_VIDEO"] !== undefined) {
                    tabsArray.push({ id: this.parentId + "_" + this.parentType + "_TAB_VIDEO", value: $.i18n("element.window.tab.video"), width: 100 });
                }
                if(element["ELEMENT_FORM_TAB_SYN"] !== undefined) {
                    let synoptic = synopticData.find(x => x.id === element["ELEMENT_FORM_TAB_SYN"].idSynoptic);
                    if(synoptic !== undefined && synoptic !== null) 
                        tabsArray.push({ id: this.parentId + "_" + this.parentType + "_TAB_SYN", value: $.i18n("element.window.tab.syn"), width: 100 });
                }
            });

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
                                if($$("cuerpo"+elementF.id))
                                    $$(parentId + "_" + parentType + "_TAB_FORM").removeView("cuerpo"+elementF.id);
                                switch (obj) {
                                    case parentId + "_" + parentType + "_TAB_FORM":
                                        $$(parentId + "_" + parentType + "_TAB_FORM").addView({             
                                            id: "cuerpo"+elementF.id,
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
                                        tabs.forEach(element =>{
                                            urlStream = element["ELEMENT_FORM_TAB_VIDEO"] !== undefined ? element["ELEMENT_FORM_TAB_VIDEO"].urlParamVideo : urlStream;
                                        });
                                        if(urlStream !== null) {
                                            let tabVideo = new TabVideo(parentId,urlStream);
                                            $$(parentId + "_" + parentType + "_TAB_FORM").addView({             
                                                id: "cuerpo"+elementF.id,
                                                cols:[tabVideo.getComponent()]
                                            });
                                            tabVideo.loadData();
                                        }else{
                                            webix.message({type:"error", text:"ERROR VIDEO NO CONFIGURADO"});
                                        }
                                    break;
                                    case parentId + "_" + parentType + "_TAB_SYN":
                                        try {
                                            let idSynop  = null;
                                            tabs.forEach(element =>{
                                                idSynop = element["ELEMENT_FORM_TAB_SYN"] !== undefined ? element["ELEMENT_FORM_TAB_SYN"].idSynoptic : idSynop;
                                            });
                                            if(idSynop !== null) {
                                                let tabSynoptic = new TabSynoptic(parentId,idSynop);
                                                $$(parentId + "_" + parentType + "_TAB_FORM").addView({             
                                                    id: "cuerpo"+elementF.id,
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
                        }
                    },
                    {
                        cells: [
                            {
                                id: this.parentId + "_" + this.parentType + "_TAB_FORM", 
                                rows: [
                                    {             
                                        id: "cuerpo"+this.elementForm.id,
                                        cols:[ this.elementForm.getComponent()],
                                        on: {
                                            onDestruct: function(obj){
                                                //elementForm.disconnect();
                                            },
                                            onHide: function(obj){
                                                this.elementForm.disconnect();
                                            }
                                        }
                                    }
                                ]  
                            }
                        ]
                    }
                ]
            };

        } else {
            result = {
                id: "cuerpo"+this.elementForm.id,
                cols:[ this.elementForm.getComponent()],
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
        this.elementForm.loadData();
        let hijos = new ElementChildsList(this.elementForm.id);
        hijos.getComponent();
    }
}