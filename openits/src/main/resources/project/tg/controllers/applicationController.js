var project = 2;
try {
    if(!j_language){
        j_language="en_GB";
    }    
} catch (error) {
    j_language="en_GB";
}

webix.proxy.GraphQL.url = "http://10.148.136.10:8082/graphql";
webix.storage.session.put("url.ws.stomp", "ws://10.148.136.10:61614");
webix.storage.session.put("url.auth.login", "http://10.148.136.10:8190/api/v1/login");
webix.storage.session.put("url.auth.path", "http://10.148.136.10:8190/api/v1/");
webix.storage.session.put("url.jasper", "http://10.148.136.10:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=" + j_language);
webix.storage.session.put("url.grafana", "http://10.148.136.10:3030/d/qQFlxpaZz/detectors?orgId=1&refresh=5s");

//test camera http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1
