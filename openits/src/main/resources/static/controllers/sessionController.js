const language = webix.storage.session.get("app_user_language");

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
  alert(`[error] ${error.message}`);
};

this.client.activate();

function processStompMessageWithFallback(message) {
	try {
		let value = JSON.parse(JSON.parse(message.body)).basicElementsVO[0].value;
		$$("bell").data.badge=value;
		$$("bell").refresh();
		$$("bannerTemp").setHTML("<div class='move-me move-me-3'>Alarmas activas: "+value+"</div>");
	} catch (err) {
		console.log("Error: " + err.message);
	}
}
