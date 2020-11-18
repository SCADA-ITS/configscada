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

        this.graphql = webix.proxy("GraphQL", this.graph);

        this.camerasTree = {
            tittle:"Cameras",
            view:"sidebar",
            id:"element_tree:cctv",
            width:200,
            drag:true,
            data:[
               { id:"Element:2:1",  icon: "mdi mdi-video", value:"RV-TV-1CA"}
               , { id:"Element:2:2",  icon: "mdi mdi-video", value:"RV-TV-2CA"}
               , { id:"Element:2:3",  icon: "mdi mdi-video", value:"RV-TV-3CA"}
               , { id:"Element:2:4",  icon: "mdi mdi-video", value:"RV-TV-4CA"}
               , { id:"Element:2:5",  icon: "mdi mdi-video", value:"RV-TVD-1CA"}
               , { id:"Element:2:6",  icon: "mdi mdi-video", value:"RV-TV-1MA"}
               , { id:"Element:2:7",  icon: "mdi mdi-video", value:"RV-TV-2MA"}
               , { id:"Element:2:8",  icon: "mdi mdi-video", value:"RV-TV-3MA"}
               , { id:"Element:2:9",  icon: "mdi mdi-video", value:"RV-TV-4MA"}
               , { id:"Element:2:10",  icon: "mdi mdi-video", value:"RV-TVD-1MA"}
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
                                if($$("element_tree:cctv").isVisible()) {
                                    $$("element_tree:cctv").hide();
                                } else {
                                    $$("element_tree:cctv").show();
                                }
                            }
                        }
                    ]
                },
                { 
                    cols: [
                       this.camerasTree,
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
            ]
        };

        return generic;
    }

    disconnect(){
        this.client.deactivate();
        //$$("schelduleContainer").destructor();
        $$("planSchedulesTable").clearAll();
        unLoadDetailForm()
    }
    
    loadData() {

        let self = this;

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
                        console.warn(value);
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
