import TabForm from "../classes/ElementForm.js";
import ElementChildsList from "../classes/ElementChildsList.js";
import TabVideo from "../classes/TabVideo.js";
import {columnsElementFormList} from "../data/elementForm.js";
import {ELEMENT_TYPE} from "../data/GLOBAL_PROPERTIES.js";

export default class ElementWindow {
    constructor(parentId,parentType) {
        this.parentId = parentId;
        this.parentType = parentType;
    }

    getComponent(){
        let result = {};
        var parentId = this.parentId;
        this.elementForm = new TabForm(parentId,columnsElementFormList);
        let tabs = null;
        try {
            tabs = ELEMENT_TYPE.values[this.parentType] !== undefined && ELEMENT_TYPE.values[this.parentType].tabs !== undefined && ELEMENT_TYPE.values[this.parentType].tabs.length > 1 ? ELEMENT_TYPE.values[this.parentType].tabs : null;
        } catch (e) {
            console.log(e);
        }
        this.tabVideo = null;
        if (tabs !== null) {
            let tabsArray = [];
            let tabsCells = [];
            let urlparam = null;
            
            tabs.forEach(element => {
                if(element["ELEMENT_FORM_TAB_FORM"] !== undefined) {
                    tabsArray.push({ id: this.parentId + "_" + this.parentType + "_TAB_FORM" , value: $.i18n("element.window.tab.form"), width: 100 });
                    /*
                    tabsCells.push({    
                        id: this.parentId + "_" + this.parentType + "_TAB_FORM",
                        header: $.i18n("element.window.tab.form"),
                        body: {             
                            id: "cuerpo"+elementForm.id,
                            cols:[ elementForm.getComponent()],
                            on: {
                                onDestruct: function(obj){
                                    //elementForm.disconnect();
                                },
                                onHide: function(obj){
                                    elementForm.disconnect();
                                }
                            }
                        }
                    });
                    */
                }
                if(element["ELEMENT_FORM_TAB_VIDEO"] !== undefined) {
                    tabsArray.push({ id: this.parentId + "_" + this.parentType + "_TAB_VIDEO", value: $.i18n("element.window.tab.video"), width: 100 });
                    /*
                    tabsCells.push({    
                        id: this.parentId + "_" + this.parentType + "_TAB_VIDEO",
                        header: $.i18n("element.window.tab.video"),
                        body: {               
                            cols:[tabVideo.getComponent()],
                        }
                    });
                    */

                    this.tabVideo = new TabVideo(parentId,element["ELEMENT_FORM_TAB_VIDEO"].urlParamVideo);
                }
                if(element["ELEMENT_FORM_TAB_SYN"] !== undefined) {
                    tabsArray.push({ id: this.parentId + "_" + this.parentType + "_TAB_SYN", value: $.i18n("element.window.tab.syn"), width: 100 });
                }
            });
            
/*
            cells:[     
                {
                  header:"Form",
                  body:{
                      template:"Form Content"     
                  }      
                },
                { header:"Empty", 
                  body:{
                    template:"Some content"
                  } 
                }
              ]
*/

            result = {
                id: "window" + this.parentId,
                
                
                /*
                on: {
                    onShow: function(obj, obj2){
                        tabvideo.loadData();
                        tabElement.loadData();
                    }
                },
                */
                //body: {
                    //rows: [
                    //    {
                            view: "tabview",
                            //tabbar: { options:tabsArray },
                            cells: [
                                {
                                    id: this.parentId + "_" + this.parentType + "_TAB_FORM",
                                    header: $.i18n("element.window.tab.form"),
                                    body: {             
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
                                },
                                {
                                    id: this.parentId + "_" + this.parentType + "_TAB_VIDEO",
                                    header: $.i18n("element.window.tab.video"),
                                    body: this.tabVideo.getComponent(),    
                                }
                            ],
                    //    },

                    //],
                //},

            };

        } else {
            result = {
                //id: "window" + parentId,
                //body: {
                id: "cuerpo"+this.elementForm.id,
                cols:[ this.elementForm.getComponent()],
                //},
                on: {
                    onDestruct: function(obj){
                        //elementForm.disconnect();
                    },
                    onHide: function(obj){
                        this.elementForm.disconnect();
                        $$("win" + parentId).destructor();
                    },
                    onShow: function(obj){
                        let hijos = new ElementChildsList(this.elementForm.id);
                        hijos.getComponent();
                    }
                }
            };
        }

        return result;

    }
    loadData(){
        if( this.tabVideo !== null)
            this.tabVideo.loadData();
        this.elementForm.loadData();
        let hijos = new ElementChildsList(this.elementForm.id);
        hijos.getComponent();
    }
}