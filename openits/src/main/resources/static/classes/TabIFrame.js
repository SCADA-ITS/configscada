import {columnsElementFormList} from "../data/elementForm.js";


export default class TabIFrame {
    constructor(elementId,parentId,urlIFrame,index,iframeWidth,iframeHeight) {
        this.elementId = elementId;
        this.parentId = parentId;
        this.urlIFrame = urlIFrame;
        this.index = index;
        this.iframeWidth = iframeWidth;
        this.iframeHeight = iframeHeight;
        this.columnsElementFormList = columnsElementFormList;

        this.elementQuery = webix.proxy(
            "GraphQL",`
            query ($elem: String!) {
              getElement(elementId: $elem)
              {
                elementType {
                  id
                }
                elementValues
                {
                  value
                  elementTypeParam {
                    id
                  }
                }
              }
            }
            `
          );

    }

    getComponent(){

        let result = {
            id: "vvtabIFrame_"+this.parentId+"_"+this.elementId + "_" + this.index,
            cols:[],
        };

        return result;
    }

    loadData(){
        this.elementQuery.load({
            elem: this.elementId,
          }).then((data) => {

            let view = null;
            if(data.elementValues !== null){
                try {
                  view = data.elementValues.find(e => e.elementTypeParam["id"] === this.urlIFrame).value; 
                } 
                catch (e) {console.log(e);}
            }	
            try {
                if(view != null && view.length >= 1){
                    let iframeHeight = this.iframeHeight;
                    let iframeWidth = this.iframeWidth;
                    let iframe = view;

                    let camera = $$("vvtabIFrame_"+this.parentId+"_"+this.elementId+"_"+this.index).addView({  
                        id: "stream_"+ this.parentId+"_"+this.elementId+"_"+this.index,
                        height:iframeHeight,
                        width: iframeWidth,
                        data: {title: "Image One", src: iframe },
                        template: function (obj) {
                        // obj is a data record object
                        return '<iframe style="width:105%;height:100%;border:none;overflow:hidden;" src="'+obj.src+'" />'
                        }
                    }, 1);
                }
            } catch (e) {
                console.warn(e);
            }
        });
    }
}
