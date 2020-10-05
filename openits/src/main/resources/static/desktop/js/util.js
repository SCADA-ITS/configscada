if(window.app)
	app.util = {
		
		stomp: {
		
			connect(module, instance, url, topic, onMessage) {
			
				var client = Stomp.client(url);
				
				client.connect("", "", onConnect, onError, onClose);
			
				function onConnect() {
				
					var subscription = client.subscribe(topic, onMessage);
				}
				
				function onError(error) {
			    	// display the error's message header:
			    	console.log(error.headers.message);
					console.log('Socket is closed. Reconnect will be attempted in 5 second.');
				}
			    
				function onClose(error) {
			    	// display the error's message header:
			    	console.log(error.headers.message);
					console.log('Socket is closed. Reconnect will be attempted in 5 second.');
			    }

				return client;	
			},
			
			disconnect(client) {
				
				client.disconnect();
			},
		}
	};
