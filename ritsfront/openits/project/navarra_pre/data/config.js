// Endpoints configuration
const appConfig = {

	restUrl: './api/v1/',
	stompUrl: function() {
		
		const wsUrl = 'ws://10.251.248.152:61614';
		const wssUrl = 'wss://precontroltrafico.admon-cfnavarra.es:61614';
	
		return document.location.protocol === "https:" ? wssUrl : wsUrl;	
	}() 
}

const APP_TITLE = "OpenITS - NAVARRA";

// APP properties
const APP_DEVMODE_ON = true;

// Ligth Mode
const APP_LIGTH_MODE = true;

// Help available
const APP_HELP_ENABLED = true;
