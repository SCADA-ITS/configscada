import EquipmentTree from "../classes/EquipmentTree.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";

export default class CCTV {
    constructor() {
        // for using the object inside callbacks
        let self = this;

        this.ncols = 2;
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

        // replaces the element tree in the DOM
        let elementTree = new EquipmentTree("ElementType:2", null, "ElementType");

        this.camerasTree = {
            id: "element_tree",
            view: "tree",
            drag: true,
            select: true,
            url: elementTree.getComponent(),
            template: function(obj, common){
                return common.icon(obj,common) + "&nbsp;" + "<span class='elementTreeLeaf'>" + obj.value + "</span>";
            }
        }

    }

    processStompMessageWithFallback(message) {
        try {
            let obj = JSON.parse(JSON.parse(message.body));
            obj.basicElementsVO.forEach(element => {
                console.log(element);
                //Modificar

                //crear

                //Eliminar

            });
        }catch (err) {
            console.log("Error: " + err.message);
            console.log("RETRY WITH GRAPHQL");
        }
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
                                if($$("element_tree").isVisible()) {
                                    $$("element_tree").hide();
                                } else {
                                    $$("element_tree").show();
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
                $$("dropArea").addView({ id: "cam" + i + j, template: i + "," + j + '<br><img src="" id="img_cam' + i + j + '" style="width:100%; height:100%" />', x:j, y:i, dx:1, dy:1 });

                // add the drag&drop event handling
                webix.DragControl.addDrop($$("cam" + i + j).$view, {
                    $drop:function(source, target, event){
                        let dnd = webix.DragControl.getContext();
                        let value = dnd.from.getItem(dnd.source[0]);
                        
                        //alert(JSON.stringify(value.id));
        
                        self.graphql.load({element: value.id})
                        .then((data) => {
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
