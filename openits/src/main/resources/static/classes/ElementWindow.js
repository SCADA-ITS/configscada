import ElementForm from "../classes/ElementForm.js";
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
        let elementForm = new ElementForm(parentId,columnsElementFormList);
        let tabs = null;
        try {
            tabs = ELEMENT_TYPE.values[this.parentType] !== undefined && ELEMENT_TYPE.values[this.parentType].tabs.length > 1 ? ELEMENT_TYPE.values[this.parentType].tabs : null;
        } catch (e) {
            console.log(e);
        }

        if (tabs !== null) {
            let tabsArray = [];
            let tabsCells = [];
            let urlparam = null;
            let tabVideo = null;
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
                    console.log(element["ELEMENT_FORM_TAB_VIDEO"].urlParamVideo);
                    console.log("sss");
                    tabVideo = new TabVideo(parentId,element["ELEMENT_FORM_TAB_VIDEO"].urlParamVideo);
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
                id: "win" + parentId,
                view: "window",
                move: true,
                head: parentId,
                close: true,
                position: "right",
                on: {
                    onShow: function(obj, obj2){
                        tabVideo.loadData()
                    }
                },
                body: {
                    //rows: [
                    //    {
                            view: "tabview",
                            //tabbar: { options:tabsArray },
                            cells: [
                                {
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
                                },
                                {
                                    id: this.parentId + "_" + this.parentType + "_TAB_VIDEO",
                                    header: $.i18n("element.window.tab.video"),
                                    body: tabVideo.getComponent(),    
                                }
                            ],
                    //    },

                    //],
                },

            };

        } else {
            result = {
                id: "win" + parentId,
                view: "window",
                move: true,
                header: parentId,
                close: true,
                position: "right",
                body: {
                    id: "cuerpo"+elementForm.id,
                    cols:[ elementForm.getComponent()]
                },
                on: {
                    onDestruct: function(obj){
                        //elementForm.disconnect();
                    },
                    onHide: function(obj){
                        elementForm.disconnect();
                        $$("win" + parentId).destructor();
                    },
                    onShow: function(obj){
                        let hijos = new ElementChildsList(elementForm.id);
                        hijos.getComponent();
                    }
                }
            };
        }

        return result;

    }
}