var project = 0;

try {
	if (!j_language) {
		j_language = "es_ES";
	}
} catch (error) {
	j_language = "es_ES";
}

const appConfig = {

	restUrl: '../api/v1/',
	stompUrl: 'ws://localhost:61614',
}

const MAIN_TAB_ID = "tabs";

const SESSION_STORAGE_SESSION_DATA = "session.data";
const SESSION_STORAGE_SESSION_LOCKED = "session.locked";
const SESSION_STORAGE_USER_DATA = "user.data";

const STOMP_DATA_SOURCE_TYPE = 2;

const MODULE_TYPE_CUSTOM_WINDOW = 1;
const MODULE_TYPE_GENERIC_GRID_WINDOW = 2;
const MODULE_TYPE_SERVICE = 6;

const EntityType = Object.freeze({
	ELEMENT_TYPE: { id: 1, endpoint: appConfig.restUrl + "element_types" },
	ELEMENT_TYPE_STATE: { id: 2, endpoint: appConfig.restUrl + "element_type_states" },
	ALARM_TYPE: { id: 3, endpoint: appConfig.restUrl + "alarms/alarm_types" },
	ALARM_LEVEL: { id: 4, endpoint: appConfig.restUrl + "alarms/alarm_levels" },
	ALARM_CONFIG: { id: 5, endpoint: appConfig.restUrl + "alarms/alarm_configs" },
	MODULE: { id: 6, endpoint: appConfig.restUrl + "modules" }
});

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

webix.proxy.GraphQL.url = "http://localhost:8082/graphql";
webix.storage.session.put("url.ws.stomp", "ws://localhost:61614");
webix.storage.session.put("url.auth.login", "http://192.168.88.163:8290/api/v1/login");
webix.storage.session.put("url.auth.path", "http://192.168.88.163:8290/api/v1/");
webix.storage.session.put("url.jasper", "http://192.168.88.163:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=" + j_language);
webix.storage.session.put("url.grafana", "http://192.168.88.200:3030/d/qQFlxpaZz/detectors?orgId=1&refresh=5s");