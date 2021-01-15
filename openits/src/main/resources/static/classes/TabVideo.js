import {columnsElementFormList} from "../data/elementForm.js";


export default class TabVideo {
    constructor(elementId,parentId,urlParamVideo) {
        this.elementId = elementId;
        this.parentId = parentId;
        this.urlParamVideo = urlParamVideo;
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
            id: "vvtabVideo_"+this.parentId+"_"+this.elementId,
            cols:[],
        };

        return result;
    }

    loadData(){
        this.elementQuery.load({
            elem: this.elementId,
          }).then((data) => {

            let stremMjpeg = null;
            if(data.elementValues !== null){
                try {
                stremMjpeg = data.elementValues.find(e => e.elementTypeParam["id"] === this.urlParamVideo).value; 
                } 
                catch (e) {console.log(e);}
            }	
            try {
                if(stremMjpeg != null && stremMjpeg.length >= 1){
                    let streamHeight = this.columnsElementFormList[data.elementType.id]["options"]["streamHeight"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamHeight"] : 800 ; //700 por defecto
                    let streamWidth = this.columnsElementFormList[data.elementType.id]["options"]["streamWidth"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamWidth"] : 800 ; //700 por defecto
                    let stream = stremMjpeg;

                    let camera = $$("vvtabVideo_"+this.parentId+"_"+this.elementId).addView({  
                        id: "stream_"+ this.parentId+"_"+this.elementId,
                        height:streamHeight,
                        width: streamWidth,
                        data: {title: "Image One", src: stream },
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
