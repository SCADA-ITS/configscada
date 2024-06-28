// Endpoints configuration
const appConfig = {

	restUrl: './api/v1/',
	stompUrl: function() {
		
		const wsUrl = 'ws://192.168.88.190:61614';
		const wssUrl = 'wss://openits.server/ws';
	
		return document.location.protocol === "https:" ? wssUrl : wsUrl;	
	}()
}

const APP_TITLE = "OpenITS - NOGALES";

// APP properties
const APP_DEVMODE_ON = true;
