const LOG_LEVEL = 2;

const MAIN_TAB_ID = "tabs";
const MAIN_TOOLBAR_ID = "mainToolbar";
const MAIN_TOOLBAR_ICONS_ID = "mainToolbarIcons";
const TASKBAR_ID = "taskbar";

const START_ON_MONDAY = true;

const SYSTEM_PERMISSION_LOGIN = 1;

const SYSTEM_PROFILES = {
	USER: "User:1000",
	ROLE: "Role:1000"
};

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

const LOGIN_MESSAGE = {
	AUTHORIZED: { id: "AUTHORIZED", label: "LBL_AUTHORIZED" },
	DENY_PASSWORD_EXPIRED: { id: "DENY_PASSWORD_EXPIRED", label: "LBL_DENY_PASSWORD_EXPIRED" },
	DENY_LOCKED: { id: "DENY_LOCKED", label: "LBL_DENY_LOCKED" },
	DENY_MULTI_SESSION_NOT_ALLOWED: { id: "DENY_MULTI_SESSION_NOT_ALLOWED", label: "LBL_DENY_MULTI_SESSION_NOT_ALLOWED" },
	WRONG_USER_PASSWORD: { id: "WRONG_USER_PASSWORD", label: "LBL_WRONG_USER_PASSWORD" },
	DENY_OTHERS: { id: "DENY_OTHERS", label: "LBL_DENY_OTHERS" },
	LOGOUT: { id: "LOGOUT", label: "LBL_LOGOUT" },
	UNAUTHORIZED: { id: "UNAUTHORIZED", label: "LBL_UNAUTHORIZED" }
}

// Browser Session Storage constants
const SESSION_STORAGE_SESSION_DATA = "session.data";

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
const PARAM_MODULE_TYPE_SERVICE_LOAD_PRIORITY = 1;
const PARAM_MODULE_TYPE_SERVICE_CONFIG = 2;
const PARAM_MODULE_TYPE_GENERIC_GRID_WINDOW_GRID_ID = 3;
const PARAM_MODULE_TYPE_GENERIC_EXTERNAL_APP_WINDOW_URL = 5;
const PARAM_MODULE_TYPE_CUSTOM_WINDOW_CONFIG = 8;

const MODULE_PARAM_SYNOPTIC_ID = 4
const MODULE_PARAM_MAP_ID = 6

// View type constants
const VIEW_TYPE_TAB = 1;
const VIEW_TYPE_WINDOW = 2;
const VIEW_TYPE_CUSTOM = 3;
const VIEW_TYPE_EXTERNAL = 4;

const ENTITY_TYPE_MODE_PERMANENT = 1;
const ENTITY_TYPE_MODE_TEMPORARY = 2;

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
	EXTERNALIZABLE: 14,
	MODAL: 15,
	NOTIFICABLE: 16,
	RESPONSIVE: 17
});

// Services
const Services = Object.freeze({
	USER_SERVICE: 10,
	ALARM_SERVICE: 11,
	EVENT_SERVICE: 12,
	TIME_SERVICE: 13,
	STOMP_SUBSCRIPTOR_SERVICE: 14,
	INCIDENT_SERVICE: 15,
	TASK_MANAGER_SERVICE: 16,
	SUBSCRIPTOR_SERVICE: 17
});

// DataTypes
const DataType = Object.freeze({
	INTEGER: "DataType:1",
	ALPHANUMERIC: "DataType:2",
	DECIMAL: "DataType:3",
	TIMESTAMP: "DataType:4",
	BOOLEAN: "DataType:5",
});

const StateValue = Object.freeze({
		INITIALIZED: "ElementValueState:1",
		ONLINE: "ElementValueState:2",
		ALERTED: "ElementValueState:3",
		OFFLINE: "ElementValueState:4"
	});

const ENTITIES_MANAGER_SCHEDULER_SECS = 600;

// Type of entities to load
const EntityType = Object.freeze({
	ELEMENT_TYPE: { id: 1, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "element_types", load: true },
	ELEMENT_TYPE_STATE: { id: 2, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "element_type_states", load: true },
	ALARM_TYPE: { id: 3, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: true, endpoint: appConfig.restUrl + "alarms/alarm_types", load: true },
	ALARM_LEVEL: { id: 4, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: true, endpoint: appConfig.restUrl + "alarms/alarm_levels", load: true },
	ALARM_CONFIG: { id: 5, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: true, endpoint: appConfig.restUrl + "alarms/alarm_configs", load: true },
	MODULE: { id: 6, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: true, endpoint: appConfig.restUrl + "modules", load: true },
	ELEMENT: { id: 7, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "elements", load: true },
	LOG_TYPE: { id: 8, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "log_types", load: true },
	USER: { id: 9, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: true, endpoint: appConfig.restUrl + "users/with_role", load: true },
	PLAN_SCHEDULE_STATE: { id: 11, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "plans/plan_schedule_state_types", load: true },
	IMS_INCIDENT_GROUP: {id: 12, mode: ENTITY_TYPE_MODE_PERMANENT,syncLoad: false, endpoint: appConfig.restUrl + "incidents/imsIncidentGroups", load: true },
	IMS_INCIDENT_TYPE: {id: 13, mode: ENTITY_TYPE_MODE_PERMANENT,syncLoad: false, endpoint: appConfig.restUrl + "incidents/imsIncidentTypes", load: true },
	IMS_INCIDENT_LEVEL: {id: 14, mode: ENTITY_TYPE_MODE_PERMANENT,syncLoad: false, endpoint: appConfig.restUrl + "incidents/incidentLevels", load: true },
	STRETCH: {id: 15, mode: ENTITY_TYPE_MODE_PERMANENT,syncLoad: false, endpoint: appConfig.restUrl + "locations/stretchs", load: true },
	ELEMENT_VALUE_STATE: { id: 16, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "element_type_states/element_values_states", load: true },
	PLAN_STATE_TYPE: { id: 17, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "plans/plan_state_types", load: true },
	PLAN_ORDER_TYPE: { id: 18, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "plans/plan_order_types", load: true },
	PLAN_ORDER_STATE_TYPE:  { id: 19, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "plans/plan_order_state_types", load: true },
	COMMAND_ELEMENT_TYPE:  { id: 20, mode: ENTITY_TYPE_MODE_PERMANENT, syncLoad: false, endpoint: appConfig.restUrl + "plans/plan_order_state_types", load: true }
});


const CONTENT_TYPE_JSON = {"Content-type": "application/json"};

const HEADER_POST = {
	"Content-type": "application/json",
	"accept": "application/json"
}

const USE_VMS_FAKE_DATA = false;

const ENDPOINT_SEND_COMMAND = "elements/send_command_element";
const ENDPOINT_SEND_AUDIT_LOG = "audit_logs/send";

// DataTypes
const LogType = Object.freeze({
	LOG_TYPE_IMS: { 
		id: "LogType:1",
	},
	LOG_TYPE_PLANS: {
		id: "LogType:2",
		subtypes: {
			LOG_SUBTYPE_EXECUTE_PLAN: "LogSubtype:3",
			LOG_SUBTYPE_STOP_PLAN: "LogSubtype:34",
			LOG_SUBTYPE_PAUSE_PLAN: "LogSubtype:35"
		},
		params: {
			PLAN: "LogTypeParam:2:1",
			ARG: "LogTypeParam:2:2",
			COMMENT: "LogTypeParam:2:3"
		}
	},
	LOG_TYPE_COMMANDS: {
		id: "LogType:3",
		subtypes: {
			LOG_SUBTYPE_EXECUTE_COMMAND: "LogSubtype:1"
		},
		params: {
			COMMAND_TYPE: "LogTypeParam:3:1",
			ELEMENT: "LogTypeParam:3:2",
			COMMAND_VALUES: "LogTypeParam:3:3",
			COMMENT: "LogTypeParam:3:4"
		}
	}
});

// Plan actions
const PlanAction = Object.freeze({
	PLAY: "PLAY",
	STOP: "STOP",
	PAUSE: "PAUSE",
	CANCEL: "CANCEL"
});

// DataTypes
const LogLevel = Object.freeze({
	TRACE: { level: 1, color: "#ffc107" },
  	DEBUG: { level: 2, color: "#ffc107" },
  	INFO: { level: 3, color: "#ff9800" },
  	WARN: { level: 4, color: "#ff9800" },
  	ERROR: { level: 5, color: "#e60000" },
  	CRITICAL: { level: 6, color: "#cc0000" }
});