const language = webix.storage.session.get("app_user_language");
const userPermissions = webix.storage.session.get("app_user_permissions");
const userName = webix.storage.session.get("app_user_name");

const GENERIC_DATA_ACTIVE_ALARMS = "/topic/generic_data.GenericData:1";
const GENERIC_DATA_NO_RECOGNIZE_ALARMS = "/topic/generic_data.GenericData:2";

if (language) {
    $.i18n().locale = language;
} else {
    $.i18n().locale = "es";
}

$.i18n().load( {
  "en": i18_en,
  "es": i18_es, 
  "sk": i18_sk
});

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
};

this.client.onStompError = function(frame) {
  // Will be invoked in case of error encountered at Broker
  // Bad login/passcode typically will cause an error
  // Complaint brokers will set `message` header with a brief message. Body may contain details.
  // Compliant brokers will terminate the connection after any error
  console.log("Broker reported error: " + frame.headers["message"]);
  console.log("Additional details: " + frame.body);
};

this.client.activate();

function processStompMessageWithFallback(message) {
	try {
		let value = JSON.parse(JSON.parse(message.body)).basicElementsVO[0].value;
		if (message.headers.destination == GENERIC_DATA_ACTIVE_ALARMS){
			$$("bell").data.badge=value;
			$$("bell").refresh();
		}
		else if (message.headers.destination == GENERIC_DATA_NO_RECOGNIZE_ALARMS){
    		if(value == null || value == 0){
    			$$("bannerTemp").hide();
    		}
    		else{
        		$$("bannerTemp").setHTML("<div class='move-me move-me-3'>Alarmas no reconocidas: "+value+"</div>");
        		$$("bannerTemp").show();
    		}
		}
		
	} catch (err) {
		console.log("Error: " + err.message);
	}
}
