//import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";

const DATASOURCE_PARAM_URL = 0;
const DATASOURCE_PARAM_TOPIC = 1;
const BROKER_TYPE = {
	1: {
		name: 'activemq',
		wildcard: '.>'
	},
	2: {
		name: 'rabbitmq',
		wildcard: '.#'
	}
};

function stompConnect(url, topic, onConnected, onMessage) {

	var client = new StompJs.Client({
		brokerURL: url,
		connectHeaders: {
			login: "guest",
			passcode: "guest"
		},
		reconnectDelay: 5000,
		heartbeatIncoming: 4000,
		heartbeatOutgoing: 4000
	});

	client.onConnect = function(frame) {
		// Do something, all subscribes must be done is this callback
		// This is needed because this will be executed after a (re)connect

		if (onConnected) {

			onConnected();
		}

		this.suscription = client.subscribe(topic, onMessage);
	};

	client.onWebSocketClose = function(frame) {
		log.warn("Broker reported close");
	}

	client.onStompError = function(frame) {
		// Will be invoked in case of error encountered at Broker
		// Bad login/passcode typically will cause an error
		// Complaint brokers will set `message` header with a brief message. Body may contain details.
		// Compliant brokers will terminate the connection after any error
		log.error("Broker reported error: " + frame.headers["message"]);
		log.error("Additional details: " + frame.body);
	};

	client.activate();

	return client;
}

function stompDisconnect(client) {

	if (client.connected) {

		client.deactivate();
	}
}

class Subscriptor {

	constructor(dataSource) {

		this.dataSource = dataSource;

		this.stompClient = undefined;
		this.url = dataSource.dataSourceValues[DATASOURCE_PARAM_URL].value;
		this.topic = dataSource.dataSourceValues[DATASOURCE_PARAM_TOPIC].value;
		
		this.clients = [];
	}

	register(callback, fields) {

		let id = webix.uid();

		if (this.clients.length == 0) {

			this.connect();
		}

		this.clients.push({
			// id: Client id
			id: id,
			// callback: function to execute. Argument item received by subscriptor
			callback: callback,
			// Inclusive OR filtered fields to evaluate. 
			// Any value in values matchs with target field value
			// Example:
			// fields: [ {"target": "targetElement.id", "values": ["Element:3:2", "Element:3:1"] ]
			fields: fields
		});

		return id;
	}

	unregister(id) {

		this.clients = this.clients.filter(e => {

			return e.id !== id;
		});

		if (this.clients.length == 0) {

			this.disconnect();
		}
	}

	connect() {

		let self = this;

		function onConnect() {

			//console.log("[StompSubscriptorService] Connected to " + self.url + self.topic);
			log.debug("Connected to " + self.url + self.topic);
		}

		function onMessage(message) {

			var data = JSON.parse(JSON.parse(message.body));

			// RouteMessage
			if (data && Array.isArray(self.clients) && self.clients.length > 0) {
				
				data.basicElementsVO.forEach(be => {
				
					self.clients.forEach(e => {

						if (!e.fields) {
	
							e.callback(be, data.action, data.timestamp);
						}
						else {
	
							if (Array.isArray(e.fields) && e.fields.length > 0) {
								
								// Any field match with value.
								let match = e.fields.find(field => field.values.find(fieldValue => eval("be." + field.target) === fieldValue));
								
								if (match) {
		
									e.callback(be, data.action, data.timestamp);
								}
							}
						}
					});
				});
			}
		}

		//this.stompClient = util.stomp.connect(this.url, this.topic, onConnect, onMessage);
		this.stompClient = stompConnect(this.url, this.topic, onConnect, onMessage);
	}

	disconnect() {
		
		if (this.stompClient && this.stompClient.connected) {

			//util.stomp.disconnect(this.stompClient);
			stompDisconnect(this.stompClient);
			
			//console.log("[StompSubscriptorService] Disconnected from " + this.url + this.topic)
			log.debug("Disconnected from " + this.url + this.topic);
			
			this.stompClient = undefined;
		}
	}
}

export default class StompSubscriptorService extends GenericModule {

	static get ACTION_CREATE() { return "CREATE" }
	static get ACTION_UPDATE() { return "UPDATE" }
	static get ACTION_DELETE() { return "DELETE" }
	static get ACTION_BLOCK() { return "BLOCK" }
		
	constructor(module) {

		super(module, null, null);

		this.module = module;
		this.subscriptors = [];
	}
	
	destroy() {
		
		let self = this;
		
		if (self.subscriptors.length > 0) {
			
			self.subscriptors.forEach(subscriptor => {
			
				if (Array.isArray(subscriptor.clients) && subscriptor.clients.length > 0) {
	
					subscriptor.clients.forEach(client => {
					
						subscriptor.unregister(client.id);
						
						log.debug("Unsubscribe => Id: " + client.id);	
					});
				}
			});
		}
	}

	getView() {

		let views = [];

		return views;
	}

	loadData() {


	}

	subscribe(url, topic, callback, fields) {

		let id = undefined;
		let topicBroker = topic; 
		
		if (appConfig.brokerId) {
			
			let topicWildCard = BROKER_TYPE[appConfig.brokerId].wildcard;
			
			if (topicWildCard) {
				
				topicBroker = topic.replace('.>', topicWildCard)
			}
		}
		
		let dataSource = {
			dataSourceValues: [
				{ value: url ? url : appConfig.stompUrl },
				{ value: topicBroker }
			]
		};
		
		let subscriptor = this.subscriptors.find(e => {

			return (e.dataSource.dataSourceValues[DATASOURCE_PARAM_URL].value === dataSource.dataSourceValues[DATASOURCE_PARAM_URL].value &&
					e.dataSource.dataSourceValues[DATASOURCE_PARAM_TOPIC].value === dataSource.dataSourceValues[DATASOURCE_PARAM_TOPIC].value);
		});

		if (!subscriptor) {

			subscriptor = new Subscriptor(dataSource);

			this.subscriptors.push(subscriptor);

			id = subscriptor.register(callback, fields);
		}
		else {

			id = subscriptor.register(callback, fields);
		}
		
		//console.log("[StompSubscriptorService] Subscribe => Id: " + id + "; DataSource: " + JSON.stringify(dataSource) + "; Fields: " + JSON.stringify(fields));
		log.debug("Subscribe => Id: " + id + "; DataSource: " + JSON.stringify(dataSource) + "; Fields: " + JSON.stringify(fields));

		return id;
	}

	unsubscribe(id) {

		if (this.subscriptors.length > 0) {

			let subscriptorFound = false;

			for (let i = 0; !subscriptorFound && i < this.subscriptors.length; i++) {

				let subscriptor = this.subscriptors[i];

				if (subscriptor.clients.length > 0) {

					let client = subscriptor.clients.find(e => {

						return e.id == id;
					});

					if (client) {

						subscriptor.unregister(client.id);

						subscriptorFound = true;
						
						//console.log("[StompSubscriptorService] Unsubscribe => Id: " + client.id);						
						log.debug("Unsubscribe => Id: " + client.id);
					}
				}
			}
		}
	}
}

