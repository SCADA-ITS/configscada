import * as elementTypeMap from "../lib/elementTypeMap.js";
import {ELEMENT_TYPE_PARAM} from  "../data/GLOBAL_PROPERTIES.js";

export default class EquipmentTable {
    constructor(id,mode,columns,data,columnsElementConfig,columnsElementList, parentId) {
        this.id = id;
        this.mode = mode; //measure = 2, parameter = 1
        this.columns = columns;
        this.data = data;
        this.modeType = this.mode === 1 ? "parameter" : "measure";
        this.columnsElementConfig = columnsElementConfig;
        this.columnsElementList = columnsElementList;
        this.parentId=parentId;
        //this.id === null ? this.id = columns.id : null ;

        let self = this;

        var graphEType = `query {
            getElementType(elementTypeId: "` + this.id + `"){
				id
				elementTypeParams{
			   		id
                    alias
                    paramType
                }
            }
        }`;

        var graphElements = `query {
            getElementsByType(elementTypeId: "` + this.id + `"){
                id
                alias
                description
                state
                elementValues {
                  id
                  value
                  alerted
                }
            }            
        }`;
        this.graphqlColumms  = webix.proxy("GraphQL", graphEType);
        this.graphqlElements = webix.proxy("GraphQL", graphElements);

        this.table = {
            id:this.modeType +this.parentId,	
            view:"datatable",
            editable:true,
            //scroll: true,
            //maxWidth: 430,
            //autowidth:true,
            yCount:20,
            leftSplit:1,
            autoConfig:true,
            tooltip:true,
            //resizeColumn: { headerOnly:true },
            columns:[],
            data:[]
        };

        // creates the stomp connection
        this.client = new StompJs.Client({
            brokerURL: webix.storage.session.get("url.ws.stomp"),
            connectHeaders: {
            login: "user",
            passcode: "user"
            },
            //debug: function(str) {
            //console.log(str);
            //},
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000
        });

        this.client.onConnect = function(frame) {
            // Do something, all subscribes must be done is this callback
            // This is needed because this will be executed after a (re)connect
            this.suscription = self.client.subscribe(
            "/topic/element.>",
            self.update_table
            );
        };
  
        this.client.onStompError = function(frame) {
            // Will be invoked in case of error encountered at Broker
            // Bad login/passcode typically will cause an error
            // Complaint brokers will set `message` header with a brief message. Body may contain details.
            // Compliant brokers will terminate the connection after any error
            console.log("Broker reported error: " + frame.headers["message"]);
            console.log("Additional details: " + frame.body);
            alert(`[error] ${error.message}`);
        };
  
        this.client.activate();
    }

    // got from Github
    elementReady(selector) {
        return new Promise((resolve, reject) => {
            let el = document.querySelector(selector);
            if (el) {
                resolve(el);
            }
            new MutationObserver((mutationRecords, observer) => {
                // Query for elements matching the specified selector
                Array.from(document.querySelectorAll(selector)).forEach((element) => {
                    resolve(element);
                    //Once we have resolved we don't need the observer anymore.
                    observer.disconnect();
                });
            }).observe(document.documentElement, {
                childList: true,
                subtree: true,
            });
        });
    }

    getState(state) {
        let status = ["state.unknown","state.connected","state.noconnected","state.alarm"];
        return status[state];
    }

    update_table(message){
        try {
            let obj = JSON.parse(JSON.parse(message.body));
            obj.basicElementsVO.forEach((element) => {
                try {
                    let item = $$("measure"+this.parentId).getItem(element.id);
                    if(item != null) {
                        let status = ["state.unknown","state.connected","state.noconnected","state.alarm"];
                        let colorList = item["$cellCss"];
                        let state = element.state !== undefined ? element.state : null;
                        if(state !== null) {
                            if (state === 0 )
                                colorList["alias"] = {"background-color":"Gold"};
                            if (state === 1 )
                                colorList["alias"] = null;
                            if (state === 2 )
                                colorList["alias"] = {"background-color":"RebeccaPurple" };
                            if (state === 3 )
                                colorList["alias"] = {"background-color":"LightCoral" };       
                            item["state"] = $.i18n(status[state]);
                        }
                        let evalues = element.elementValues !== undefined ? element.elementValues : null;
                        if(evalues !== null) {
                            element.elementValues.forEach(evalue => {
                                try {
                                    let eparam =  elementTypeMap.getElementParamTypeIdi18(evalue.id);
                                    let itemValue = evalue.value;
                                    try {
                                        if(typeof ELEMENT_TYPE_PARAM.values[eparam] != 'undefined'){
                                            let itemParamTypeId = ELEMENT_TYPE_PARAM.values[eparam].dataTypeId;
                                            itemValue = parseUIParam(itemValue, itemParamTypeId);
                                        }  
                                    } catch (error) {
                                        console.warn(error);
                                    }
                                    item[eparam] = itemValue !== undefined ? itemValue === null ? "-" : itemValue : "-";

                                    if (evalue.alerted !== undefined) {
                                        colorList[eparam] = evalue.alerted ? {"background-color":"red" } : null;
                                    }     
                                } catch (err) { console.log("Error: " + err.message); }                         
                            });
                        }
                        item["$cellCss"] = colorList;
                        $$("measure"+this.parentId).updateItem(element.id, item); 
                    }
                } catch (err) { console.log("Error: " + err.message); }
            });
    
        } catch (err) {
          console.log("Error: " + err.message);
          console.log("RETRY WITH GRAPHQL");
        }
    }

    getComponent() {
		let generictable = {
			rows: [
                this.table,
				{}
			]
        };
        let status = ["state.unknown","state.connected","state.noconnected","state.alarm"];
        if (this.mode === 2) {
            // waits for the element and starts drawing
            this.elementReady('[view_id="' + this.modeType +this.parentId+ '"]').then((c) => {
                let spinnerDivToAdd = document.createElement("div");
                spinnerDivToAdd.setAttribute("id", this.modeType+ this.parentId + "spinner");
                spinnerDivToAdd.setAttribute("class", "spinner");
                document
                .querySelector('[view_id="' + this.modeType + this.parentId + '"]')
                .prepend(spinnerDivToAdd);
            });
        }

        if ( this.data === null ) {
                
                this.graphqlElements.load({ 
                }).then(data => {

                    try {
                        this.columnsElementList[this.id][this.mode].forEach(obj => {
                            $$(this.modeType + this.parentId)
                                .config.columns.push({ id: obj,  
                                    header: this.columnsElementConfig[this.id][this.mode][obj]["header"],
                                    rotate: this.columnsElementConfig[this.id][this.mode][obj]["rotate"], 
                                    adjust:true, 
                                    autowidth:true,
                                    autoheigth: true,
                                    sort:"text", 
                                    tooltip:function(item){
                                        let dato = obj === "alias" ? item.description : $.i18n(obj + ".description");
                                        return dato;
                                    }
                           });
                        });
                    } catch {}

                    data.forEach(item => {

                        let colorList = {};
                        if (item.state === 0 )
                            colorList["alias"] = {"background-color":"Gold"};
                        if (item.state === 1 )
                            colorList["alias"] = null;
                        if (item.state === 2 )
                            colorList["alias"] = {"background-color":"RebeccaPurple" };
                        if (item.state === 3 )
                            colorList["alias"] = {"background-color":"LightCoral" };       

                        let row = {id: item.id, alias: item.alias, description: item.description, state: $.i18n(status[item.state])};
                        if(item.elementValues) {
                            item.elementValues.forEach(obj => {
                                let itemValue = obj.value;
                                try {
                                    if(typeof ELEMENT_TYPE_PARAM.values[elementTypeMap.getElementParamTypeIdi18(obj.id)] != 'undefined' && typeof ELEMENT_TYPE_PARAM.values[elementTypeMap.getElementParamTypeIdi18(obj.id)].dataTypeId != 'undefined'){
                                        let itemParamTypeId = ELEMENT_TYPE_PARAM.values[elementTypeMap.getElementParamTypeIdi18(obj.id)].dataTypeId;
                                        itemValue = parseUIParam(itemValue, itemParamTypeId);
                                    }
                                } catch (error) {
                                    console.warn("Eparam: "+ elementTypeMap.getElementParamTypeIdi18(obj.id) + ", evalue.id: "+obj.id);
                                    console.warn(error);
                                }
                                if (elementTypeMap.getParamTypeId(obj.id) === this.mode) {
                                    row[elementTypeMap.getElementParamTypeIdi18(obj.id)] = itemValue === null ? "-" : itemValue;
                                    if (obj.alerted) {
                                        colorList[elementTypeMap.getElementParamTypeIdi18(obj.id)] = {"background-color":"red" };
                                    } else
                                        colorList[elementTypeMap.getElementParamTypeIdi18(obj.id)] = null;
                                } else {
                                    if(this.mode === 2 && elementTypeMap.getParamTypeId(obj.id) === 3) {
                                        row[elementTypeMap.getElementParamTypeIdi18(obj.id)] = itemValue === null ? "-" : itemValue;
                                        if (obj.alerted) {
                                            colorList[elementTypeMap.getElementParamTypeIdi18(obj.id)] = {"background-color":"red" };
                                        } else
                                            colorList[elementTypeMap.getElementParamTypeIdi18(obj.id)] = null;
                                    }
                                }
                            });	
                        }
                        row["$cellCss"] = colorList;
                        $$(this.modeType + this.parentId).add(row);
                        $$(this.modeType + this.parentId).sort({
                            as:"string",
                            dir:"asc",
                            by:"id"
                        });
                    });
                }).then(() => {
                    if (this.mode === 2) 
                        document.getElementById(this.modeType + this.parentId + "spinner").style.display = "none";
                });
                  
        } else {
            try {

                this.columnsElementList[this.id][this.mode].forEach(obj => {

                    $$(this.modeType + this.parentId)
                        .config.columns.push({ id: obj,  
                            header: this.columnsElementConfig[this.id][this.mode][obj]["header"],
                            rotate: this.columnsElementConfig[this.id][this.mode][obj]["rotate"], 
                            adjust:true, 
                            autowidth:true,
                            sort:"text", 
                    });   
                });

            } catch {}

            this.data.forEach(item => {
                let colorList = {};
                if (item.state === 0 )
                    colorList["alias"] = {"background-color":"Gold"};
                if (item.state === 1 )
                    colorList["alias"] = null;
                if (item.state === 2 )
                    colorList["alias"] = {"background-color":"RebeccaPurple" };
                if (item.state === 3 )
                    colorList["alias"] = {"background-color":"LightCoral" };       

                let row = {id: item.id, alias: item.alias, state: item.state};
                if(item.elementValues) {
                    item.elementValues.forEach(obj => {
                        let itemValue = obj.value;
                        try {
                            if(typeof ELEMENT_TYPE_PARAM.values[elementTypeMap.getElementParamTypeIdi18(obj.id)] != 'undefined' && typeof ELEMENT_TYPE_PARAM.values[elementTypeMap.getElementParamTypeIdi18(obj.id)].dataTypeId != 'undefined' ){
                                let itemParamTypeId = ELEMENT_TYPE_PARAM.values[elementTypeMap.getElementParamTypeIdi18(obj.id)].dataTypeId;
                                itemValue = parseUIParam(itemValue, itemParamTypeId);
                            }
                        } catch (error) {
                            console.warn("Eparam: "+ elementTypeMap.getElementParamTypeIdi18(obj.id) + ", evalue.id: "+obj.id);
                            console.warn(error);
                        }
                        if (obj.elementTypeParam.paramType === this.mode) {
                            row[obj.elementTypeParam.id] = itemValue === null ? "-" : itemValue;
                            if (obj.alerted) {
                                colorList[obj.elementTypeParam.id] = {"background-color":"red" };
                            }
                            else
                                colorList[obj.elementTypeParam.id] = null;
                        }
                    });	
                }

                row["$cellCss"] = colorList;
                $$(this.modeType + this.parentId).add(row);
                $$(this.modeType + this.parentId).sort({
                    as:"string",
                    dir:"asc",
                    by:"id"
                });
            });

        }
        
        return generictable
    }

}
