import TabForm from "../classes/ElementForm.js";
import {columnsElementFormList} from "../data/elementForm.js";

export default class ElementChildsList {
    constructor(parentId) {
        this.queryId = parentId;
        this.parentId = parentId;
        
        this.graphQuery = webix.proxy(
            "GraphQL",
            `query ($elem: String!) {
              getElement(elementId: $elem)
              {
                id
                alias
                childs{id alias state}
              }
            }`
        );
    }

    getComponent(){
        var parentId = this.parentId;
        this.graphQuery.load({ elem: this.queryId }).then(data => {
            if(data.childs !== null && data.childs !== undefined){
                let vdata = [];
                vdata.push({
                    id: "childList_" + parentId + "_" + data.id,
                    alias: data.alias,
                });
                data.childs.forEach(element => {
                    try {
                        vdata.push({
                            id: "childList_" + parentId + "_" + element.id,
                            alias: element.alias,
                        });
                    } catch (e) { console.log(e); }
                });
                $$("cuerpo" + this.parentId).addView({
                    id: "childList" + this.parentId,
                    view: "list", 
                    width: 150,
                    data:vdata,
                    select: true,
                    template: "#alias#",
                    click: function(obj) {
                        if($$("component_"+parentId) !== undefined) {
                            $$("cuerpo" + parentId).removeView($$("component_"+parentId));
                        } else {
                            $$($$("cuerpo" + parentId).getChildViews()[0].config.id).data.order.forEach(element => {
                                if($$("component_"+parentId+"_"+element.split("_")[2]) !== undefined) {
                                    $$("cuerpo" + parentId).removeView($$("component_"+parentId+"_"+element.split("_")[2]));
                                }
                            });
                        }
                        let elementForm14 = new TabForm(obj.split("_")[2],parentId,columnsElementFormList);
                        $$("cuerpo" + parentId).addView(elementForm14.getComponent(),1);
                        elementForm14.loadData();
                    }
                },0);
                $$("childList" + this.parentId).select("childList_" + parentId + "_" + data.id);
            }
        });
    }
}
