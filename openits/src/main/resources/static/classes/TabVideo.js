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
                id
                state
                elementType {
                  id
                  alias
                }
                elementValues
                {
                  id
                  value
                  alerted
                  elementTypeParam {
                    id
                    description
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
            cols:[]
        };
       
        return result;



/*  

				if(elementTypeMap.getElementType(data.elementType.id) === elementTypeMap.ELEMENT_TYPE_2_CAM) { //"ElementType:2"){
					let stremMjpeg = null;
					if(data.elementValues !== null){
						stremMjpeg = data.elementValues.filter(elementValue => elementValue.id != null && elementTypeMap.getElementParamTypeId(elementValue.id) === elementTypeMap.CAM_STREAM_MJPEG); //"ElementTypeParam:2:1:3" );	
					}	
					if(stremMjpeg != null && stremMjpeg.length >= 1){
						let streamHeight = this.columnsElementFormList[data.elementType.id]["options"]["streamHeight"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamHeight"] : 300 ; //700 por defecto
						let streamWidth = this.columnsElementFormList[data.elementType.id]["options"]["streamWidth"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamWidth"] : 300 ; //700 por defecto
						let stream = stremMjpeg[0].value;						
						let camera = $$("component"+this.id).addView({  
							id: "stream_"+ this.id,
							height:streamHeight,
							width: streamWidth,
							data: {title: "Image One", src: stream },
							template: function (obj) {
							  // obj is a data record object
							  return '<iframe style="width:105%;height:100%;position:relative;margin-top:-13px;margin-left:-9px;" src="'+obj.src+'" />'
							}
						}, 1);
						
						let streamFullViewHeight = this.columnsElementFormList[data.elementType.id]["options"]["streamFullViewHeight"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamFullViewHeight"] : 700 ; //700 por defecto
						let streamFullViewWidth = this.columnsElementFormList[data.elementType.id]["options"]["streamFullViewWidth"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamFullViewWidth"] : 700 ; //700 por defecto


*/



        
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
                console.log(e);
            }
        });



    }
}