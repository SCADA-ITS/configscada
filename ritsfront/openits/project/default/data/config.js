// Endpoints configuration
const appConfig = {

	restUrl: '../api/v1/',
	stompUrl: 'ws://localhost:61614',
}

const MAIN_TAB_ID = "tabs";

// Browser Session Storage constants
const SESSION_STORAGE_SESSION_DATA = "session.data";
const SESSION_STORAGE_SESSION_LOCKED = "session.locked";
const SESSION_STORAGE_USER_DATA = "user.data";

// Data souce types
const STOMP_DATA_SOURCE_TYPE = 2;

// Module type constants
const MODULE_TYPE_CUSTOM_WINDOW = 1;
const MODULE_TYPE_GENERIC_GRID_WINDOW = 2;
const MODULE_TYPE_GENERIC_MAP_WINDOW = 3;
const MODULE_TYPE_GENERIC_SYNOPTIC_WINDOW = 4;
const MODULE_TYPE_GENERIC_EXTERNAL_APP_WINDOW = 5;
const MODULE_TYPE_SERVICE = 6;

// Module type params constants
const PARAM_MODULE_TYPE_SERVICE_LOAD_PRIORITY = 1
const PARAM_MODULE_TYPE_SERVICE_CONFIG = 2
const PARAM_MODULE_TYPE_GENERIC_GRID_WINDOW_GRID_ID = 3
const PARAM_MODULE_TYPE_GENERIC_SYNOPTIC_WINDOW_SYNOPTIC_ID = 4
const PARAM_MODULE_TYPE_GENERIC_EXTERNAL_APP_WINDOW_URL = 5

// View type constants
const VIEW_TYPE_TAB = 1;
const VIEW_TYPE_WINDOW = 2;
const VIEW_TYPE_CUSTOM = 3;

// Type of entities to load
const EntityType = Object.freeze({
	ELEMENT_TYPE: { id: 1, endpoint: appConfig.restUrl + "element_types" },
	ELEMENT_TYPE_STATE: { id: 2, endpoint: appConfig.restUrl + "element_type_states" },
	ALARM_TYPE: { id: 3, endpoint: appConfig.restUrl + "alarms/alarm_types" },
	ALARM_LEVEL: { id: 4, endpoint: appConfig.restUrl + "alarms/alarm_levels" },
	ALARM_CONFIG: { id: 5, endpoint: appConfig.restUrl + "alarms/alarm_configs" },
	MODULE: { id: 6, endpoint: appConfig.restUrl + "modules" } 
});

// Params for windowed ui
const WindowParam = Object.freeze({
	HAS_MIN_BUTTON: 1,
	HAS_MAX_BUTTON: 2,
	IS_MAXIMIZED: 3,
	HEIGHT: 4,
	WIDTH: 5,
	X: 6,
	Y: 7,
	ALLOW_MULTIPLE_INSTANCES: 8,
	IMAGE: 9,
	ICON: 10,
	COLOR: 11,
	LABEL: 12,
	RESIZABLE: 13,
	EXTERNALIZABLE: 14
});
