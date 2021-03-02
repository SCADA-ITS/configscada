const MAIN_TAB_ID = "tabs";
const MAIN_TOOLBAR_ID = "mainToolbar";

const START_ON_MONDAY = true;

const FORMAT_NUMERIC_DATES = {
	month: '2-digit',
	day: '2-digit',
	year: 'numeric',
	hour: '2-digit',
	minute: '2-digit',
	second: '2-digit'
};
const FORMAT_STRING_DATES = {
	weekday: 'long',
	month: 'long',
	day: '2-digit',
	year: 'numeric',
	hour: '2-digit',
	minute: '2-digit',
	second: '2-digit'
};

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
const PARAM_MODULE_TYPE_CUSTOM_WINDOW_CONFIG = 8;

const MODULE_PARAM_SYNOPTIC_ID = 4
const MODULE_PARAM_MAP_ID = 6

// View type constants
const VIEW_TYPE_TAB = 1;
const VIEW_TYPE_WINDOW = 2;
const VIEW_TYPE_CUSTOM = 3;

const ENTITY_TYPE_MODE_PERMANENT = 1;
const ENTITY_TYPE_MODE_TEMPORARY = 2;

// Services
const Services = Object.freeze({
	USER_SERVICE: 10,
	ALARM_SERVICE: 11,
	NOTIFICATION_SERVICE: 12,
	TIME_SERVICE: 13,
	STOMP_SUBSCRIPTOR_SERVICE: 14
});

const ENTITIES_MANAGER_SCHEDULER_SECS = 60;

// Type of entities to load
const EntityType = Object.freeze({
	ELEMENT_TYPE: { id: 1, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "element_types", load: true },
	ELEMENT_TYPE_STATE: { id: 2, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "element_type_states", load: true },
	ALARM_TYPE: { id: 3, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: true, endpoint: appConfig.restUrl + "alarms/alarm_types", load: true },
	ALARM_LEVEL: { id: 4, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: true, endpoint: appConfig.restUrl + "alarms/alarm_levels", load: true },
	ALARM_CONFIG: { id: 5, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: true, endpoint: appConfig.restUrl + "alarms/alarm_configs", load: true },
	MODULE: { id: 6, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: true, endpoint: appConfig.restUrl + "modules", load: true },
	ELEMENT: { id: 7, mode: ENTITY_TYPE_MODE_TEMPORARY, syncLoad: false, expireSeconds: 50, endpoint: appConfig.restUrl + "elements", load: true },
	LOG_TYPE: { id: 8, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "log_types", load: true },
	USER: { id: 9, mode: ENTITY_TYPE_MODE_TEMPORARY, syncLoad: false, expireSeconds: 50, endpoint: appConfig.restUrl + "users/with_role", load: true },
	PLAN: { id: 10, mode: ENTITY_TYPE_MODE_TEMPORARY, syncLoad: false, expireSeconds: 50, endpoint: appConfig.restUrl + "plans/summary", load: true },
	PLAN_SCHEDULE_STATE: { id: 11, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "plans/plan_schedule_state_types", load: true },
});


const CONTENT_TYPE_JSON = {"Content-type": "application/json"};
