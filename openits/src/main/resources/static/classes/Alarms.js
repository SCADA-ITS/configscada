import {genericTable} from "../data/genericTable.js";

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
            console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000
        });

        this.client.onConnect = function(frame) {
        // Do something, all subscribes must be done is this callback
        // This is needed because this will be executed after a (re)connect
        this.suscription = self.client.subscribe(
            "/topic/alarm",
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
        alert(`[error] ${error.message}`);
        };

        this.client.activate();
    }
    
  /*
   *	Stomp without GraphQL
   */
  processStompMessage(message) {
    try {
      let obj = JSON.parse(JSON.parse(message.body));

      obj.basicElementsVO.forEach(element => {
        console.log(element);


      });
    } catch (err) {
      console.log("Error: " + err.message);
    }
  }

  /*
   *	queries GraphQL based on the element ID received by Stomp
   */
  processStompMessageGraphQL(message) {
    try {
      let obj = JSON.parse(JSON.parse(message.body));

      obj.basicElementsVO.forEach(element => {
        //initialize the proxy
        let elementQuery = webix.proxy(
          "GraphQL",
          `
					query ($elem: String!) {
						getElement(elementId: $elem)
						{
							id
							state
							elementValues
							{
								id
								value
								alerted
								elementTypeParam {
									description
								}
							}
						}
					}
					`
        );

        // loads the data
        elementQuery
          .load({
            elem: element.id
          })
          .then(data => {
            // process the received data
            console.log(data);

          });
      });
    } catch (err) {
      console.log("Error: " + err.message);
    }
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
        //this.alarmas.forEach(alarm => {
        $$('alarmsTable').data.each(function(alarm){
           
            if (alarm.id === element.id) {  //ALARMA PREVIAMENTE CREADA
                if (element.deactivation === undefined) //ES UNA MODIFICACIÓN 
                {   
                    
                    //this.alarmas.push(element);
                    let data = {
                        id: element.id, 
                        activation: element.activation, 
                        recognized: element.recognized === undefined ? null : element.recognized, 
                        deactivation: null ,
                        alarmConfig: element.alarmConfig , 
                        alarmLevel:element.alarmConfig , 
                        sourceElement: element.sourceElement, 
                        targetElement: element.targetElement 
                    }
                    $$('alarmsTable').updateItem(element.id, data);
            //$$('alarmsTable').editStop();
                    //let id = $$('alarmsTable').add(data,0);  
                    control = true;
                } else { //DESACTIVAMOS
                    if (element.deactivationComments != "" && element.deactivationComments != undefined) {
                        $$('alarmsTable').remove(element.id);
                    }
                    control = true;
                }

//  Object { id: "Alarm:1587118186077", alarmConfig: {…}, activation: 1587123750137, deactivation: 1587123760142, deactivationComments: "Desactivate for rule at 2020-04-17T11:42:40.142874Z", sourceElement: {…}, targetElement: {…} }

//Object { id: "Alarm:1587118186078", alarmConfig: {…}, activation: 1587123770141, sourceElement: {…}, targetElement: {…} }
            } 

        });

        if (control === false) {  //ALARMA NUEVA
            
            let elementQuery1 = webix.proxy(
                "GraphQL",
                `
                query getalarm($alarmId: String!) {
                  getAlarm(alarmId: $alarmId) {
                      id
                      alarmConfig {id alias alarmLevel}
                      recognized
                      recognizedComments
                      activation
                      activationComments
                      deactivation
                      deactivationComments
                      sourceElement{id alias}
                      targetElement{id alias} 
                  }
                 }
                          `
              );
      
              // loads the data
              elementQuery1
                .load({
                  alarmId: element.id
                })
                .then(data1 => {
                    let data = {id: data1.id, 
                        activation: data1.activation, 
                        recognized: data1.recognized, 
                        deactivation: data1.deactivation,
                        alarmConfig: data1.alarmConfig , 
                        alarmLevel:data1.alarmConfig , 
                        sourceElement: data1.sourceElement, 
                        targetElement: data1.targetElement }
            //$$('alarmsTable').editStop();
           
                    let id = $$('alarmsTable').add(data,0); 
                    
                            
                });
        }

      });
    } catch (err) {
      console.log("Error: " + err.message);
      console.log("RETRY WITH GRAPHQL");
      //console.log(message.body);

      let obj = JSON.parse(JSON.parse(message.body));
/*
      console.log("OBJ");
      console.log(obj);
        console.log("AAOUU");
      let self = this;
      console.log("AAOUU2");
      obj.basicElementsVO.forEach(element => {
        //initialize the proxy
        let elementQuery = webix.proxy(
          "GraphQL",
          `
          query getalarm($alarmId: String!) {
            getAlarm(alarmId: $alarmId) {
                id
                alarmConfig {id alias}
                recognized
                recognizedComments
                activation
                activationComments
                deactivation
                deactivationComments
                sourceElement{id alias}
                targetElement{id alias} 
            }
           }
					`
        );

        // loads the data
        elementQuery
          .load({
            alarmId: element.id
          })
          .then(data => {
            // process the received data
            console.log(data); 
            //console.log(this.alarmas);
            console.log("AQUI1");  
            //this.alarmas.forEach(eo =>{
            //    console.log(eo);
            //});         
            //this.alarmas.push(data);
            let data2 = {id: data.id, 
                activation: data.activation, 
                recognized: data.recognized, 
                deactivation: data.deactivation,
                alarmConfig: data.alarmConfig , 
                alarmLevel:data.alarmConfig , 
                sourceElement: data.sourceElement, 
                targetElement: data.targetElement }
            console.log("AQUI");
                $$('alarmsTable').editStop();
            let id = $$('alarmsTable').add(data2,0); 
            $$('alarmsTable').editRow(id);  
          });
      });
      */
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
            console.log(d);
            d.forEach(element => {
                
                //this.alarmas.push(element);

                let data = {id: element.id, 
                    activation: element.activation, 
                    recognized: element.recognized, 
                    deactivation: element.deactivation ,
                    alarmConfig: element.alarmConfig , 
                    alarmLevel:element.alarmConfig , 
                    sourceElement: element.sourceElement, 
                    targetElement: element.targetElement }
                //$$('alarmsTable').editStop();
                let id = $$('alarmsTable').add(data,0); 
                //$$('alarmsTable').editRow(id);            
                //$$("alarmsTable").add
               
            });
            /*
            console.log(alarmas);
            this.alarmas.forEach(element => {
            let data = {id: element.id, 
                        activation: getDate(element.activation), 
                        recognized: element.recognized, 
                        deactivation: getDate(element.deactivation) ,
                        alarmConfig: element.alarmConfig.alias , 
                        alarmLevel:element.alarmConfig.alarmLevel , 
                        sourceElement: element.sourceElement.alias, 
                        targetElement: element.targetElement.alias }
            //$$('alarmsTable').editStop();
            let id = $$('alarmsTable').add(data,0);            
            //$$("alarmsTable").add
            console.log($$('alarmsTable'));
          });
          console.log(this.alarmas);
          */
          return d;
        });

    }

    init() {
        this.loadData();
    }

}