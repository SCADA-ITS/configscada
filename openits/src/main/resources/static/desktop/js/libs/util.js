if(window.app)
	window.app.util = {
		
		stomp: {
		
			connect(url, topic, onConnected, onMessage) {
			
				var client = Stomp.client(url);
				
				client.connect("", "", onConnect, onError, onClose);
			
				function onConnect() {
				
					var subscription = client.subscribe(topic, onMessage);
					
					if (onConnected) {
						onConnected();	
					}
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
				
				if (JSON.stringify(client) !== '{}') {
					client.disconnect();	
				}
			},
		},
		
		toLocalTime(ts) {
			
			let userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
			let date = new Date(ts * 1000);
			
			if (userData) {
				
				let language = userData.language;
				let country = userData.country;
				
				if (language && country) {
				
					date = new Date(ts * 1000).toLocaleDateString(language + "-" + country);
				}
			}
			
			return date;
		}
	};
