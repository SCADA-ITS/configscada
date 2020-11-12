import TabForm from "../classes/ElementForm.js";
import {columnsElementFormList} from "../data/elementForm.js";

export default class ElementChildsList {
    constructor(parentId) {
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
        this.graphQuery.load({ elem: this.parentId }).then(data => {
            if(data.childs !== null && data.childs !== undefined){
                $$("cuerpo" + this.parentId).addView({
                    id: "childList" + this.parentId,
                    view: "list", 
                    width: 150,
                    data:data.childs,
                    select: true,
                    //template:"#id#. #alias#, #state#",
                    template: "#alias#",
                    click: function(obj) {
                        if($$("component"+parentId) !== undefined) {
                            $$("cuerpo" + parentId).removeView($$("component"+parentId));
                        }
                        else {
                            $$($$("cuerpo" + parentId).getChildViews()[0].config.id).data.order.forEach(element => {
                                if($$("component"+element) !== undefined) {
                                    $$("cuerpo" + parentId).removeView($$("component"+element));
                                }
                            });
                        }
                        let elementForm14 = new TabForm(obj,columnsElementFormList);
                        $$("cuerpo" + parentId).addView(elementForm14.getComponent(),1);
                        elementForm14.loadData();
                    }
                },0);
                $$("childList" + this.parentId).add({id: data.id, alias: data.alias},0);
            }
        });
    }
}