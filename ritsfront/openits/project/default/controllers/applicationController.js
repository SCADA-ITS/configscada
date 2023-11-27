var project = 0;

try {
	if (!j_language) {
		j_language = "es_ES";
	}
} catch (error) {
	j_language = "es_ES";
}


webix.proxy.GraphQL.url = "http://192.168.243.70:8082/graphql";
webix.storage.session.put("url.ws.stomp", "ws://192.168.243.70:61614");
webix.storage.session.put("url.auth.login", "http://192.168.88.163:8290/api/v1/login");
webix.storage.session.put("url.auth.path", "http://192.168.88.163:8290/api/v1/");
webix.storage.session.put("url.jasper", "http://192.168.88.163:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=" + j_language);
webix.storage.session.put("url.grafana", "http://192.168.88.200:3030/d/qQFlxpaZz/detectors?orgId=1&refresh=5s");