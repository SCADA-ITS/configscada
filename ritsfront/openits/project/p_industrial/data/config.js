// Endpoints configuration
const appConfig = {

	restUrl: './api/v1/',
	stompUrl: function() {
		
		const wsUrl = 'ws://192.168.56.103:61614';
		const wssUrl = 'wss://openits.server/ws';
	
		return document.location.protocol === "https:" ? wssUrl : wsUrl;	
	}()
}

const APP_TITLE = "OpenITS - PUENTE INDUSTRIAL";

// APP properties
const APP_DEVMODE_ON = true;
