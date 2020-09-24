const GENERIC_DATA_ACTIVE_ALARMS = "/topic/generic_data.GenericData:1";
const GENERIC_DATA_NO_RECOGNIZE_ALARMS = "/topic/generic_data.GenericData:2";

export default class AlarmNotification {
    constructor(data, metadata){
        this.data=data;
        self = this;

        //creates the stomp connection
        this.client = new StompJs.Client({
            brokerURL: webix.storage.session.get("url.ws.stomp"),
            connectHeaders: {
            login: "user",
            passcode: "user"
            },
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000
        });
        
        this.client.onConnect = function(frame) {
            // Do something, all subscribes must be done is this callback
            // This is needed because this will be executed after a (re)connect
            this.suscription = self.client.subscribe(
            "/topic/generic_data.>",
            self.processStompMessageWithFallback
            );
            webix.storage.session.put("system.conected", true);
            changeConnectedIcon();
        };
        
        this.client.onWebSocketClose = function(frame){
            webix.storage.session.put("system.conected", false);
            changeConnectedIcon();
        }
        
        this.client.onStompError = function(frame) {
            // Will be invoked in case of error encountered at Broker
            // Bad login/passcode typically will cause an error
            // Complaint brokers will set `message` header with a brief message. Body may contain details.
            // Compliant brokers will terminate the connection after any error
            console.log("Broker reported error: " + frame.headers["message"]);
            console.log("Additional details: " + frame.body);
        };
        
    }
        
    processStompMessageWithFallback(message) {
        try {
            let value = JSON.parse(JSON.parse(message.body)).basicElementsVO[0].value;
            if (message.headers.destination == GENERIC_DATA_ACTIVE_ALARMS){
                $$("bell").data.badge=value;
                $$("bell").refresh();
            }
            else if (message.headers.destination == GENERIC_DATA_NO_RECOGNIZE_ALARMS){
                if(value == null || value == 0){
                    $$("bannerTemp").setHTML("<div class='move-me move-me-3'></div>");

                }
                else{
                    $$("bannerTemp").setHTML("<div class='move-me move-me-3'>" + $.i18n("defaultheader.activealarms") + ": "+value+"</div>");
                }
            }
            
        } catch (err) {
            console.log("Error: " + err.message);
        }
    }
  
    getAlarmsNotificationBell(){
        let getActiveAlarms = webix.proxy(  "GraphQL", `query {
            getGenericData(genericDataId:"GenericData:1") {
                  id
                  value
                  }
              }`);
        getActiveAlarms.load().then(data => {
            try {
                $$("bell").data.badge=data.value;
                $$("bell").refresh();
            } catch (err) {
                console.log("Error: " + err.message);
            }
        });
    }

    getAlarmsNotificationBanner(){
        let getNoRecognizeAlarms = webix.proxy(  "GraphQL", `query {
            getGenericData(genericDataId:"GenericData:2") {
                  id
                  value
                  }
              }`);

        getNoRecognizeAlarms.load().then(data => {
            try {
                if(data == null ||  data.value == 0){
                    $$("bannerTemp").setHTML("<div class='move-me move-me-3'></div>");
                }
                else{
                    $$("bannerTemp").setHTML("<div class='move-me move-me-3'>" + $.i18n("defaultheader.activealarms") + ": "+data.value+"</div>");
                }
            } catch (err) {
                console.log("Error: " + err.message);
            }
        });
    }

    stompClientActivated(){
        this.client.activate();
    }

    getComponent(){
        let banner ={
            id: "bannerTemp",
            view: "template",
            height:30,
            template: "<div class='move-me move-me-3'>" + $.i18n("defaultheader.activealarms") + ":0</div>"
        };
        return banner;
    }

    loadData(){
        this.getAlarmsNotificationBell();
        this.getAlarmsNotificationBanner();
        this.stompClientActivated();
    }
}