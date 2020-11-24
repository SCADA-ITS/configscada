import Synoptic from "../classes/Synoptic.js";
import {synopticData} from "../data/synoptics.js";

export default class TabSynoptic {
    constructor(parentId,idSynoptic,synoptic,width,height) {
        this.parentId = parentId.replaceAll(":","_");
        this.parentId_win = parentId;
        this.idSynoptic = idSynoptic;
        this.synoptic = synoptic; 
        this.width = width !== null ? width : 0;
        this.height = height !== null ? height : 0;
    }

    getComponent(){
        if(this.synoptic !== undefined){
            let vSinoptico = {};
            let parentId = this.parentId;
            let parentId_win = this.parentId_win;
            vSinoptico["id"] = this.idSynoptic + this.parentId;
            Object.entries(this.synoptic).map(([k, v]) => {
                if(k!== "id")
                    vSinoptico[k] = v;
            });
            this.syn = new Synoptic(vSinoptico);
            let idSyn = this.idSynoptic;
            let synoptic = this.synoptic;
            let result = {
                id: "tabtabSyn" + this.parentId,
                //view: "abslayout",
                width: this.width,
                height: this.height,
                rows: [{
                    //view: "template",
                    view: "abslayout",
                    id: this.idSynoptic + this.parentId,
                    cells:[                
                        {
                            //view:"button", value:"+", css:{"height": "44px", "border-radius":"60px", "font-size":"35px", "line-height":"25px", "box-shadow":"0px 3px 0px 1px rgba(200,200,200,0.5)"},
                            view: "icon", icon: "mdi mdi-application", tooltip:$.i18n("synoptic.sendToMainFrame"), css: {  "display": "inline-block", "border-radius": "50px", "box-shadow": "0px 0px 20px #888"}, //, "background": "#666" },
                            bottom: 35, right:35, width:40, height: 40,
                            click: function (message,obj2) {
                                let vSinoptico2 = {};
                                vSinoptico2["id"] = idSyn + parentId + "multiView";
                                Object.entries(synoptic).map(([k, v]) => {
                                    if(k!== "id")
                                        vSinoptico2[k] = v;
                                });
                                let syn2 = new Synoptic(vSinoptico2);
                                let index = Math.floor((Math.random() * (3000 - 1000 + 1)) + 1000);
                                $$("multiview").addView({ 
                                    id: "syn"+index,
                                    rows: [{
                                        view: "template",
                                        id: vSinoptico2.id,
                                        url: function() {
                                            syn2.init();
                                        },

                                    }],
                                });
                                $$('multiview').attachEvent("onViewChange", (prevID, nextID) => {
                                    if(prevID === "syn"+index && prevID !== nextID) {
                                        $$("multiview").removeView("syn"+index);
                                        if($$("menu" + idSyn + parentId + "multiView")){
                                            $$("menu" + idSyn + parentId + "multiView").destructor();
                                        }
                                        $$('multiview').detachEvent("onViewChange");
                                    }
                                });
                                $$("$sidebar1").unselectAll();
                                $$("multiview").setValue("syn"+index);
                                if ($$("win" + parentId_win) != null) 
                                    $$("win" + parentId_win).destructor();
                            }
                        }
                    ]
                    }
                ]
            };
            return result;
        }
    }

    loadData(){
        this.syn.init(false);
    }
}