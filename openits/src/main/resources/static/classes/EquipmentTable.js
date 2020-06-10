import * as elementTypeMap from "../lib/elementTypeMap.js";

export default class EquipmentTable {
    constructor(id,mode,columns,data,columnsElementConfig,columnsElementList) {
        this.id = id;
        this.mode = mode; //measure = 2, parameter = 1
        this.columns = columns;
        this.data = data;
        this.modeType = this.mode === 1 ? "parameter" : "measure";
        this.columnsElementConfig = columnsElementConfig;
        this.columnsElementList = columnsElementList;
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
            id:"tabla_element_" + this.modeType,	
            view:"datatable",
            editable:true,
            yCount:20,
            autoConfig:true,
            tooltip:true,
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
                    let item = $$("tabla_element_measure").getItem(element.id);
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
                                    item[eparam] = evalue.value !== undefined ? evalue.value === null ? "-" : evalue.value : "-";

                                    if (evalue.alerted !== undefined) {
                                        colorList[eparam] = evalue.alerted ? {"background-color":"red" } : null;
                                    }     
                                } catch (err) { console.log("Error: " + err.message); }                         
                            });
                        }
                        item["$cellCss"] = colorList;
                        $$("tabla_element_measure").updateItem(element.id, item); 
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
            this.elementReady('[view_id="' + "tabla_element_" + this.modeType + '"]').then((c) => {
                let spinnerDivToAdd = document.createElement("div");
                spinnerDivToAdd.setAttribute("id", "tabla_element_" + this.modeType + "spinner");
                spinnerDivToAdd.setAttribute("class", "spinner");
                document
                .querySelector('[view_id="' + "tabla_element_" + this.modeType + '"]')
                .prepend(spinnerDivToAdd);
            });
        }

        if ( this.data === null ) {
                
                this.graphqlElements.load({ 
                }).then(data => {

                    try {
                        //document.getElementById("tabla_element_" + this.modeType + "spinner").style.display = "block";
                        this.columnsElementList[this.id][this.mode].forEach(obj => {
                            $$("tabla_element_" + this.modeType)
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
                                if (elementTypeMap.getParamTypeId(obj.id) === this.mode) {
                                    row[elementTypeMap.getElementParamTypeIdi18(obj.id)] = obj.value === null ? "-" : obj.value;
                                    if (obj.alerted) {
                                        colorList[elementTypeMap.getElementParamTypeIdi18(obj.id)] = {"background-color":"red" };
                                    }
                                    else
                                        colorList[elementTypeMap.getElementParamTypeIdi18(obj.id)] = null;
                                } else {
                                    if(this.mode === 2 && elementTypeMap.getParamTypeId(obj.id) === 3) {
                                        row[elementTypeMap.getElementParamTypeIdi18(obj.id)] = obj.value === null ? "-" : obj.value;
                                        if (obj.alerted) {
                                            colorList[elementTypeMap.getElementParamTypeIdi18(obj.id)] = {"background-color":"red" };
                                        }
                                        else
                                            colorList[elementTypeMap.getElementParamTypeIdi18(obj.id)] = null;
                                    }
                                }
                            });	
                        }
                        row["$cellCss"] = colorList;
                        $$("tabla_element_" + this.modeType).add(row);
                        $$("tabla_element_" + this.modeType).sort({
                            as:"string",
                            dir:"asc",
                            by:"id"
                        });
                    });
                }).then(() => {
                    if (this.mode === 2) 
                        document.getElementById("tabla_element_" + this.modeType + "spinner").style.display = "none";
                });
                  
        } else {
            try {

                this.columnsElementList[this.id][this.mode].forEach(obj => {

                    $$("tabla_element_" + this.modeType)
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
                        if (obj.elementTypeParam.paramType === this.mode) {
                            row[obj.elementTypeParam.id] = obj.value === null ? "-" : obj.value;
                            if (obj.alerted) {
                                colorList[obj.elementTypeParam.id] = {"background-color":"red" };
                            }
                            else
                                colorList[obj.elementTypeParam.id] = null;
                        }
                    });	
                }

                row["$cellCss"] = colorList;
                $$("tabla_element_" + this.modeType).add(row);
                $$("tabla_element_" + this.modeType).sort({
                    as:"string",
                    dir:"asc",
                    by:"id"
                });
            });

        }
        
        return generictable
    }

}
