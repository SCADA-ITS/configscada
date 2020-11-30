import * as elementTypeMap from "../lib/elementTypeMap.js";

export default class CCTV {
    constructor() {
        // for using the object inside callbacks
        let self = this;

        this.ncols = 3;
        this.nrows = 2;

        this.graph = `query($element: String!) { 
			getElement(elementId: $element){
				id
				alias
				description
				state
				elementType {
			        id
			        alias
		      	}
				elementValues {
					id 
					value 
					alerted 
					elementTypeParam {
						id 
						alias
						representation
					}
				}
			}
		}
        `;
        this.graphElementType2 = `query($elem: String!) { 
            getElementsByType(elementTypeId: $elem){
                id
                alias
                description
                state
                elementValues {id value alerted elementTypeParam {
                  id 
                  alias  
                  representation
                  graphics {id alias graphicType graphicValues {id value}}
                }}
            
              }
		}
        `;
        
        this.graphql = webix.proxy("GraphQL", this.graph);
        this.grpahqlElementType2 = webix.proxy("GraphQL", this.graphElementType2)

        
        this.rows={
            rows:[
                this.camerasTree
            ]
        };
    }

    getComponent() {
        let generic = {
            id: "cctvContainer",
            rows: [
                {
                    view:"toolbar", 
                    cols:[
                        { 
                            view: "label", 
                            label: $.i18n("menu.cctv.title")
                        },
                        { 
                            view: "button", 
                            value: "Show/Hide", 
                            width: 108,
                            icon:"wxi-close",
                            click: function() {
                                // hide the panel
                                if($$("element_tree_cctv").isVisible()) {
                                    $$("element_tree_cctv").hide();
                                } else {
                                    $$("element_tree_cctv").show();
                                }
                            }
                        }
                    ]
                },
                
            ]
        };

        return generic;
    }

    disconnect(){
        $$("planSchedulesTable").clearAll();
        unLoadDetailForm()
    }
    
    loadData() {
        let self = this;
        this.grpahqlElementType2.load({ elem: "ElementType:2"}).then(data => {
            
            let vdata = []
            data.forEach(ele => {
                vdata.push({
                    id: ele.id,
                    icon: "mdi mdi-video",
                    value: ele.alias
                })
            });
        try {
            if ($$('element_tree_cctv') != undefined){
                $$('cctvContainer').removeView('cctv_controller_layout')
            }
        } catch (error) {
            console.log(error)
        }

        $$('cctvContainer').addView(
        {
            id: "cctv_controller_layout",
            cols: [
                {
                    tittle:"Cameras",
                    view:"list",
                    id:"element_tree_cctv",
                    width:200,
                    drag:true,
                    data:vdata
                },
               {
                    id: "dropArea",
                    drag: true,
                    view: "gridlayout",
                    gridColumns: this.ncols, 
                    gridRows: this.nrows,
                    cells:[
                    ]
                }
            ]
        }
        );
        // adds the required cells to the layout
        for(let i=0; i<this.nrows; i++) {
            for(let j=0; j<this.ncols; j++) {
                if($$("cam"+ i + j)=== undefined || !$$("cam"+ i + j)){
                    $$("dropArea").addView({ id: "cam" + i + j, template: i + "," + j + '<br><img src="" id="img_cam' + i + j + '" style="width:100%; height:96%" />', x:j, y:i, dx:1, dy:1 });    
                }

                // add the drag&drop event handling
                webix.DragControl.addDrop($$("cam" + i + j).$view, {
                    $drop:function(source, target, event){
                        let dnd = webix.DragControl.getContext();
                        let value = dnd.from.getItem(dnd.source[0]);
                        self.graphql.load({element: value.id}).then((data) => {
                            let streamMjpeg = null;
                            if(data.elementValues !== null){
                                streamMjpeg = data.elementValues.filter(elementValue => elementValue.id != null && elementTypeMap.getElementParamTypeId(elementValue.id) === elementTypeMap.CAM_STREAM_MJPEG); //"ElementTypeParam:2:1:3" );	
                            }
                            if(streamMjpeg != null) {
                                //alert(JSON.stringify(streamMjpeg[0].value));
                                $("#img_cam" + i + j).attr("src", streamMjpeg[0].value);
                            } else {
                                alert("Stream null");
                            }
                        })
                    }
                });

            }
        }
       
    })

    }

    init() {
        //this.loadData();
    }
}


function unLoadDetailForm(){
	$$("planSchedulesTable").unselect();
	$$("b_delete_planSchedule").hide();
	if($$("scheduleDetailForm")){
		$$("schelduleContainer").removeView("scheduleResizer");
		$$("schelduleContainer").removeView("scheduleDetailForm");
	}
}
