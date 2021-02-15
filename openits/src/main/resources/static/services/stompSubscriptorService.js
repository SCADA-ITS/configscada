import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";

const DATASOURCE_PARAM_URL = 0;
const DATASOURCE_PARAM_TOPIC = 1;

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
			id: id,
			callback: callback,
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

			console.log("[StompSubscriptorService] Connected to " + self.url + self.topic)
		}

		function onMessage(message) {

			var data = JSON.parse(JSON.parse(message.body));

			// RouteMessage
			if (data && Array.isArray(self.clients) && self.clients.length > 0) {
				
				data.basicElementsVO.forEach(be => {
				
					self.clients.forEach(e => {

						if (!e.fields) {
	
							e.callback(be);
						}
						else {
	
							if (typeof e.fields === 'object') {
	
								let fields = Object.keys(e.fields);
	
								if (fields.length > 0) {
	
									let match = true;
	
									for (let i = 0; match && i < fields.length; i++) {
										
										let field = fields[i];
	
										match &= (be[field] === fields);
									}
	
									if (match) {
	
										e.callback(be);
									}
								}
							}
						}
					});
				});
			}
		}

		this.stompClient = util.stomp.connect(this.url, this.topic, onConnect, onMessage);
	}

	disconnect() {
		
		if (this.stompClient && this.stompClient.connected) {

			util.stomp.disconnect(this.stompClient);
			
			console.log("[StompSubscriptorService] Disconnected from " + this.url + this.topic)
			
			this.stompClient = undefined;
		}
	}
}

export default class StompSubscriptorService extends GenericModule {

	constructor(module, mod) {

		super(module, null, null);

		this.module = module;
		this.mod = mod;
		this.subscriptors = [];
	}

	getView() {

		let views = [];

		return views;
	}

	loadData() {


	}

	subscribe(dataSource, callback, fields) {

		let id = undefined;
		let subscriptor = this.subscriptors.find(e => {

			return (e.dataSource[DATASOURCE_PARAM_URL] === dataSource[DATASOURCE_PARAM_URL] &&
				e.dataSource[DATASOURCE_PARAM_TOPIC] === dataSource[DATASOURCE_PARAM_TOPIC]);
		});

		if (!subscriptor) {

			subscriptor = new Subscriptor(dataSource);

			this.subscriptors.push(subscriptor);

			id = subscriptor.register(callback, fields);
		}
		else {

			id = subscriptor.register(callback, fields);
		}
		
		console.log("[StompSubscriptorService] Subscribe => Id: " + id + "; DataSource: " + JSON.stringify(dataSource) + "; Fields: " + JSON.stringify(fields));

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
						
						console.log("[StompSubscriptorService] Unsubscribe => Id: " + client.id);						
					}
				}
			}
		}
	}
}

