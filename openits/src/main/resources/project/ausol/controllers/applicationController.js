var project = 0;
try {
    if(!j_language){
        j_language="es_ES";
    }    
} catch (error) {
    j_language="es_ES";
}

//webix.proxy.GraphQL.url = "http://192.168.88.231:8082/graphql";
//webix.storage.session.put("url.ws.stomp", "ws://192.168.88.231:61614");
//webix.storage.session.put("url.auth.login", "http://192.168.88.231:8190/api/v1/login");
//webix.storage.session.put("url.auth.path", "http://192.168.88.231:8190/api/v1/");
//webix.storage.session.put("url.jasper", "http://192.168.88.168:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=" + j_language);
//webix.storage.session.put("url.grafana", "http://192.168.88.200:3030/d/qQFlxpaZz/detectors?orgId=1&refresh=5s");
//webix.storage.session.put("url.grafana.speed", "http://192.168.88.200:3030/d/6eYoRJ-Gz/velocidad-media-km-h?orgId=1&refresh=30s");
//webix.storage.session.put("url.grafana.imd", "http://192.168.88.200:3030/d/rNWwSaaMz/imd-veh-h?orgId=1&refresh=30s");

// webix.proxy.GraphQL.url = "http://192.168.88.118:8082/graphql";
// webix.storage.session.put("url.ws.stomp", "ws://192.168.88.118:61614");
// webix.storage.session.put("url.auth.login", "http://192.168.88.118:8190/api/v1/login");
// webix.storage.session.put("url.auth.path", "http://192.168.88.118:8190/api/v1/");
// webix.storage.session.put("url.jasper", "http://192.168.88.118:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=" + j_language);
// webix.storage.session.put("url.grafana", "http://192.168.88.200:3030/d/qQFlxpaZz/detectors?orgId=1&refresh=5s");


webix.proxy.GraphQL.url = "http://192.168.88.163:8182/graphql";
webix.storage.session.put("url.ws.stomp", "ws://192.168.88.163:62614");
webix.storage.session.put("url.auth.login", "http://192.168.88.163:8290/api/v1/login");
webix.storage.session.put("url.auth.path", "http://192.168.88.163:8290/api/v1/");
webix.storage.session.put("url.jasper", "http://192.168.88.163:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=" + j_language);
webix.storage.session.put("url.grafana", "http://192.168.88.200:3030/d/qQFlxpaZz/detectors?orgId=1&refresh=5s");

//test camera http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1
