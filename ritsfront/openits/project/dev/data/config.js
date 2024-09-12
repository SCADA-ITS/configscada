// Endpoints configuration
const appConfig = {

	restUrl: './api/v1/',
	stompUrl: function() {
		
		const wsUrl = 'ws://localhost:61614';
		const wssUrl = 'wss://openits.server/ws';
	
		return document.location.protocol === "https:" ? wssUrl : wsUrl;	
	}(),
	osrm: 'http://192.168.88.179:5001/route/v1/driving/${ini};${end}?overview=full&geometries=geojson'
}

const APP_TITLE = "OpenITS - DEV";

// APP properties
const APP_DEVMODE_ON = true;

// Help available
const APP_HELP_ENABLED = true;

// Ligth Mode
//const APP_LIGTH_MODE = true;