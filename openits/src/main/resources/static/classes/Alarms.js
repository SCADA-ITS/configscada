import {genericTable} from "../data/genericTable.js";
import {alarmsCfg} from "../data/alarmsCfg.js";

export default class Alarms {
	constructor(data, metadata){
        this.data=data;
		this.alarmas = [];
		if(metadata==null){
			this.metadata=genericTable;
		}else{
            this.metadata=metadata;
        }

        // for using the object inside callbacks
        let self = this;

        // creates the stomp connection
        this.client = new StompJs.Client({
	        brokerURL: webix.storage.session.get("url.ws.stomp"),
	        connectHeaders: {
	            login: "user",
	            passcode: "user"
	        },
	        debug: function(str) {
	        },
	        reconnectDelay: 5000,
	        heartbeatIncoming: 4000,
	        heartbeatOutgoing: 4000
        });

        this.client.onConnect = function(frame) {
	        // Do something, all subscribes must be done is this callback
	        // This is needed because this will be executed after a (re)connect
	        this.suscription = self.client.subscribe(
	            "/topic/alarm.>",
	            self.processStompMessageWithFallback
	        );
        };

        this.client.onStompError = function(frame) {
	        // Will be invoked in case of error encountered at Broker
	        // Bad login/passcode typically will cause an error
	        // Complaint brokers will set `message` header with a brief message. Body may contain details.
	        // Compliant brokers will terminate the connection after any error
	        console.log("Broker reported error: " + frame.headers["message"]);
	        console.log("Additional details: " + frame.body);
        };

    }
   
  /*
   *	Tries to get the data from Stomp, but if it doesn't find the needed data,
   * gets it by GraphQL
   */
  processStompMessageWithFallback(message) {
	  try {
	      let obj = JSON.parse(JSON.parse(message.body));

	      obj.basicElementsVO.forEach(element => {
	    	  
			let control = false;
			let item = $$('alarmsTable').getItem(element.id);
			
		    if (item != null && item != undefined) {
		    	  
		        let data = {
		          id: element.id,
		          activation: element.activation == null ? item.activation : element.activation,
		          recognized: element.recognized == null ? item.recognized : element.recognized,
		          deactivation: element.deactivation == null ? item.deactivation : element.deactivation,
		          alarmConfig: item.alarmConfig,
		          sourceElement: item.sourceElement, 
		          targetElement: item.targetElement
		        };
        
		        // Se borra la alarma
		        if (data.recognized != null && data.deactivation != null) {
		        	$$('alarmsTable').remove(element.id);
		        	
		        }else{
					$$('alarmsTable').updateItem(element.id, data);
					let orden = $$("alarmsTable")._last_sorted;
					let orden_campos = [];
					let orden_values = [];
					Object.entries(orden).map(([k, v]) => {
						orden_campos.push(k);
						orden_values.push(v);
					});
					if (orden_campos.length > 0){
						$$("alarmsTable").sort(orden_values);
					}
		        	
		        	if(data.recognized != null){
		        		$$("alarmsTable").move(element.id, 0, $$("alarmsTable_recon"), {newId: element.id});
		        	}
		        }

	        control = true;
   
		    }else{
				item = $$('alarmsTable_recon').getItem(element.id);
				
		        if (item != null && item != undefined) {  //ALARMA PREVIAMENTE CREADA
		
		            if (element.deactivation != null) { // BORRADA CUANDO SE MODIFICA
		            	$$('alarmsTable_recon').remove(element.id);	
		            }else{
						let data = {
							id_recon: element.id,
							activation_recon: element.activation === null ? item.activation_recon : element.activation,
							recognized_recon: element.recognized === null ? item.recognized_recon : element.recognized,
							deactivation_recon: element.deactivation === null ? item.deactivation_recon : element.deactivation,
							alarmConfig: item.alarmConfig,
							sourceElement: item.sourceElement, 
							targetElement: item.targetElement
						  }
						$$('alarmsTable_recon').updateItem(element.id, data);
						let orden = $$("alarmsTable_recon")._last_sorted;
						let orden_campos = [];
						let orden_values = [];
						Object.entries(orden).map(([k, v]) => {
							orden_campos.push(k);
							orden_values.push(v);
						});
						if (orden_campos.length > 0){
							$$("alarmsTable_recon").sort(orden_values);
						}
		            }
		            control = true;
		        }
		   }

		if (control == false) {	
			//console.log(element.alarmConfig.id);
			//ALARMA NUEVA
			// loads the data
			let alarmConfig = {};
			let sourceElement = {};
			let targetElement = {};
			alarmConfig["id"] = element.alarmConfig.id;
			alarmConfig["alarmLevel"] = alarmsCfg[element.alarmConfig.id].alarmLevel;
			let deactivation = element.deactivation !== undefined ? element.deactivation === null ? null : element.deactivation : null;
			let recognized = element.recognized !== undefined ? element.recognized === null ? null : element.recognized : null;
			sourceElement["id"] = element.sourceElement.id;
			sourceElement["alias"] = "";
			targetElement["id"] = element.targetElement.id;
			targetElement["alias"] = "";

			let data = {id: element.id, 
				activation: element.activation !== null ? new Date(element.activation) : null,
				recognized: recognized, 
				deactivation: deactivation, 
				alarmConfig: alarmConfig , 
				alarmLevel: alarmConfig , 
				sourceElement: element.sourceElement, 
				targetElement: element.targetElement }			
			if(data.recognized != null && data.deactivation == null){

				let data_recon = {
					id: element.id,
					//check_recon: "off",
					activation_recon: element.activation !== null ? new Date(element.activation) : null, 
					recognized_recon: recognized !== null ? new Date(recognized) : null, 
					deactivation_recon: deactivation !== null ? new Date(deactivation) : null, 
					alarmConfig: alarmConfig , 
					alarmLevel: alarmConfig , 
					sourceElement: element.sourceElement, 
					targetElement: element.targetElement,
					//sourceElementId: "",
					//sourceElementAlias: "",
					//targetElementId: "",
					//targetElementAlias: ""
				}

				$$('alarmsTable_recon').add(data_recon, 0);
				$$('alarmsTable_recon').refresh();
				let orden = $$("alarmsTable_recon")._last_sorted;
				let orden_campos = [];
				let orden_values = [];
				Object.entries(orden).map(([k, v]) => {
					orden_campos.push(k);
					orden_values.push(v);
				});
				if (orden_campos.length > 0){
					$$("alarmsTable_recon").sort(orden_values);
				}
			}else if(data.recognized == null){	

				let orden = $$("alarmsTable")._last_sorted;
				let orden_campos = [];
				let orden_values = [];
				Object.entries(orden).map(([k, v]) => {
					orden_campos.push(k);
					orden_values.push(v);
				});
				$$('alarmsTable').add(data, 0); 
				if (orden_campos.length > 0){
					$$("alarmsTable").sort(orden_values);
				}
			}

			//Source Alias
			var graphEquipS = `query { 
				getElement(elementId: "` + element.sourceElement.id + `"){
					alias
				}
			}`;
			var graphqlEquipS = webix.proxy("GraphQL", graphEquipS);
			graphqlEquipS.load().then(equipS => {
				try {
					if(data.recognized != null && data.deactivation == null){
						var tmpItem = $$('alarmsTable_recon').getItem(element.id);
						tmpItem.sourceElement.alias=equipS.alias;
						$$('alarmsTable_recon').updateItem(element.id, tmpItem);
					}else if(data.recognized == null){
						var tmpItem = $$('alarmsTable').getItem(element.id);
						tmpItem.sourceElement.alias=equipS.alias;
						$$('alarmsTable').updateItem(element.id, tmpItem);
					}
				} catch (e) {
				}
				return data;
			});

			//Destiny Alias
			var graphElemD = `query { 
				getElement(elementId: "` + element.targetElement.id + `"){
					alias
				}
			}`;
			var graphqlEquipD = webix.proxy("GraphQL", graphElemD);
			graphqlEquipD.load().then(equipD => {
				try {
					if(data.recognized != null && data.deactivation == null){
						var tmpItem = $$('alarmsTable_recon').getItem(element.id);
						tmpItem.targetElement.alias=equipD.alias;
						$$('alarmsTable_recon').updateItem(element.id, tmpItem);
					}else if(data.recognized == null){
						var tmpItem = $$('alarmsTable').getItem(element.id);
						tmpItem.targetElement.alias=equipD.alias;
						$$('alarmsTable').updateItem(element.id, tmpItem);
					}
				}catch (e) {
				}
				return data;
			});
    	}
  	});
	}catch (err) {
	  console.log("Error: " + err.message);
	  console.log("RETRY WITH GRAPHQL");
	 }
  	}

	getComponent(){
		if(this.data.includes("http") || this.data.includes("GraphQL")){
			this.metadata.url=this.data;
		}else{
			this.metadata.data=this.data;
		}
		return this.metadata;
	}

    getDate(timestamp)
    {
        return timestamp !== null ? new Date(timestamp).toISOString() : '-';
    }
    
    loadData() {

        this.data
        .load()
        .then(d => {
			
			let datos = [];
			let datos_recon = [];
            d.forEach(element => {
               
                let data = {id: element.id, 
                    activation: element.activation !== null ? new Date(element.activation) : null,
                    recognized: element.recognized !== null ? new Date(element.recognized) : null, 
                    deactivation: element.deactivation !== null ? new Date(element.deactivation) : null, 
                    alarmConfig: element.alarmConfig , 
                    alarmLevel:element.alarmConfig , 
                    sourceElement: element.sourceElement, 
                    targetElement: element.targetElement }
                
                	if(data.recognized != null && data.deactivation == null){
						
						let data_recon = {
							id: element.id,
							//check_recon: "off",
							activation_recon: element.activation !== null ? new Date(element.activation) : null, 
							recognized_recon: element.recognized !== null ? new Date(element.recognized) : null, 
							deactivation_recon: element.deactivation !== null ? new Date(element.deactivation) : null, 
							alarmConfig: element.alarmConfig , 
							alarmLevel:element.alarmConfig , 
							sourceElement: element.sourceElement, 
							targetElement: element.targetElement,
							//sourceElementId: "",
							//sourceElementAlias: "",
							//targetElementId: "",
							//targetElementAlias: ""
						}
						datos_recon.push(data_recon);
						//$$('alarmsTable_recon').add(data, 0);
					
                	}else if(data.recognized == null){
						datos.push(data);
                		//$$('alarmsTable').add(data, 0); 
					}
				
			}
			
		  );
		  //$$('alarmsTable_recon').load({ "data": datos_recon, pos: 0, "total_count": datos_recon.length }, function(text, data, http_request){
			// do something with data.json()
			//console.log(text);
			//console.log(data);
			//console.log(http_request);
			//});
		  //$$('alarmsTable_recon').load({ "data": datos_recon, pos: $$('alarmsTable_recon').count(), "total_count": datos_recon.length + $$('alarmsTable_recon').count() });
		  //console.log($$('alarmsTable_recon'));
		  //console.log(datos_recon);
		 
			$$('alarmsTable_recon').parse({
				"data": datos_recon,
				"pos": $$('alarmsTable_recon').count(),
			});
			$$("alarmsTable_recon").refresh();
			$$('alarmsTable').parse({
				"data": datos,
				"pos": $$('alarmsTable').count(),
			});
			$$("alarmsTable").refresh();
			//console.log($$('alarmsTable_recon'));
		  //$$('alarmsTable').load({ "data": datos, pos: 0, "total_count": datos.length });
		  //$$('alarmsTable').sort("activation", "desc");
          this.client.activate();
          return d;
        });
    }

    init() {
        this.loadData();
    }

}
