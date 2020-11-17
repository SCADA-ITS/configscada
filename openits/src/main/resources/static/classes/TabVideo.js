import {columnsElementFormList} from "../data/elementForm.js";


export default class TabVideo {
    constructor(parentId,urlParamVideo) {
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
            id: "vvtabVideo"+this.parentId,
            cols:[],
            on: {
              onShow: function(obj){
              },
              onHide: function(obj){
              }
          }
        };
       
        return result;

    }

    loadData(){
        this.elementQuery.load({
            elem: this.parentId,
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
                    let streamHeight = this.columnsElementFormList[data.elementType.id]["options"]["streamHeight"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamHeight"] : 300 ; //700 por defecto
                    let streamWidth = this.columnsElementFormList[data.elementType.id]["options"]["streamWidth"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamWidth"] : 300 ; //700 por defecto
                    
                    let stream = stremMjpeg;
                   
                    let camera = $$("vvtabVideo"+this.parentId).addView({  
                        id: "stream_"+ this.parentId,
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