var project = 1;
try {
    if(!j_language){
        j_language="en_EN";
    }    
} catch (error) {
    j_language="en_EN";
}

// webix.proxy.GraphQL.url = "http://192.168.88.166:8082/graphql";
// webix.storage.session.put("url.ws.stomp", "ws://192.168.88.166:61614");
// webix.storage.session.put("url.auth.login", "http://192.168.88.166:8190/api/v1/login");

webix.proxy.GraphQL.url = "http://10.120.50.248:8082/graphql";
webix.storage.session.put("url.ws.stomp", "ws://10.120.50.248:61614");
webix.storage.session.put("url.auth.login", "http://10.120.50.248:8190/api/v1/login");
webix.storage.session.put("url.auth.path", "http://10.120.50.248:8190/api/v1/");
webix.storage.session.put("url.jasper",  "http://192.168.88.168:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=" + j_language);
webix.storage.session.put("url.grafana", "http://192.168.88.200:3030/d/qQFlxpaZz/detectors?orgId=1&refresh=5s");
webix.storage.session.put("url.map", "http://umap.openstreetmap.fr/sk-sk/map/d4r7_vsetko_513495#12/48.1071/17.4065");

const keyLayout = [
    "↑","done"
];

//test camera http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1
