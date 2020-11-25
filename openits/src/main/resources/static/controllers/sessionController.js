const language = webix.storage.session.get("app_user_language");
const userPermissions = webix.storage.session.get("app_user_permissions");
const userName = webix.storage.session.get("app_user_name");

if (language) {
    $.i18n().locale = language;
} else {
    $.i18n().locale = "es";
}

var j_language = null;
var j_language_usermanager = null;

switch (language) {
  case "es":
    j_language= "es_ES";
    j_language_usermanager= "es";
    break;
  case "en":
    j_language= "en_EN";
    j_language_usermanager= "en";
    break;
  case "sk":
    j_language= "sk_SK";
    j_language_usermanager= "sk";
    break;
  default:
    j_language= "es_ES";
    j_language_usermanager= "es";
    break;
}


$.i18n().load( {
  "en": i18_en,
  "es": i18_es, 
  "sk": i18_sk
});