
import { RULE } from "../data/GLOBAL_PROPERTIES.js"

webix.ready(function () {

    const language = webix.storage.session.get("app_user_language");
    const language_default = "es";

    if (language !== null) {
        $.i18n().locale = language;
    } else {
        $.i18n().locale = language_default;
    }

    $.i18n().load({
        "es": i18_es,
        "en": i18_en
    });

    const userPlaceholder = "";//$.i18n("login.input.user");

    var options = [
        { id: "es", value: "Español" },
        { id: "en", value: "English" }
    ];

    webix.ui({
        on:{
            onKeyPress: function(code, e){
                if (code == 13){
                    login()
                }
            }
        },
        rows: [{},
        {
            cols: [{},
            {
                view: "form",
                id: "loginForm",
                elementsConfig: {
                    bottomPadding: 18,
                    labelWidth: 150
                    
                },
                on: {
                    onKeyPress: function(code, e){
                        if (code == 13){
                            login()
                        }
                    }
                },
                elements: [
                    { "template": "<span class='webix_icon fa-user-circle-o'></span>Login", type: "header" },
                    {
                        view: "text",
                        label: $.i18n("login.input.user"),
                        name: "user",
                        id: "user", placeholder: userPlaceholder,
                        required: true,
                        on: {
                            "onBlur": function () {
                                var result = this.validate()    // validate only this field and show warning message under field if invalid
                                if (this.$scope != null){
                                    this.$scope.validateForm()
                                }
                            },
                            onKeyPress: function(code, e){
                                if (code == 13){
                                    login()
                                }
                            }
                        }
                    },
                    {
                        view: "text",
                        type: "password",
                        label: $.i18n("login.input.password"),
                        name: "password",
                        required: true,
                        validate: webix.rules.isNotEmpty, invalidMessage: "Please enter your password!", validateEvent: "key",
                        on: {
                            "onBlur": function () {
                                this.validate()
                                if (this.$scope != null){
                                    this.$scope.validateForm()
                                }
                            },
                            onKeyPress: function(code, e){
                                if (code == 13){
                                    login()
                                }
                            }
                        }
                    },
                    {
                        margin: 10,
                        paddingX: 2,
                        borderless: true,
                        cols: [
                            {
                                view: "richselect",
                                value: language !== null ? language : language_default,
                                height: 40,
                                width: 150,
                                options: {
                                    view: "datasuggest",
                                    textValue: "value",
                                    template: function (obj) {
                                        return "<img src='./img/" + obj.id + ".png' width='25' height='16'></img>" + " " + obj.value;
                                    },
                                    body: {
                                        xCount: 1,
                                        template: function (obj) {
                                            //return obj.value + "" + "<img src='./" + obj.value + ".png' width='100' height='67'></img>"; // "<img src='//docs.webix.com/samples/32_thirdparty/data/image00"+obj.id+".JPG'>";
                                            return "<img src='./img/" + obj.id + ".png' width='25' height='16'></img>" + " " + obj.value;
                                        },
                                        type: {
                                            height: 40, width: 150
                                        },
                                        data: options
                                    },
                                },
                                on: {
                                    'onChange': function (obj) {
                                        switch (obj) {
                                            case "sk":
                                                webix.storage.session.put("app_user_language", "sk");
                                                break;
                                            case "en":
                                                webix.storage.session.put("app_user_language", "en");
                                                webix.i18n.setLocale("en-EN");
                                                break;
                                            case "es":
                                                webix.storage.session.put("app_user_language", "es");
                                                webix.i18n.setLocale("es-ES");
                                                break;
                                        }
                                        window.location = "./login.html";
                                    }
                                }
                            },
                            {},
                            {
                                view: "button", label: $.i18n("login.LoginButton"), type: "form", id: "loginFormSubmitButton", width: 150, click:
                                    login
                            }
                        ]
                    }
                ],
            }
                , {}],
        }, {}]
    });

});

function login() {
    var form = $$('loginForm')
    if (form.validate()) {
        const encodedString = btoa((form.elements.user.data.value + ':' + form.elements.password.data.value).toString('utf8')).toString('base64');
        webix.ajax().headers({ 'Authorization': 'Basic ' + encodedString }).post(webix.storage.session.get("url.auth.path") + "login").then(function (data) {


            switch (data.json().access) {
                case "ALLOWED":
                    webix.storage.session.put("loginSession", data.json().id);
                    webix.storage.session.put("userId", data.json().userId);
                    var paramUser = data.json().userId;
                    webix.proxy("GraphQL", `query($rowId: String!){
                            getUser(userId: $rowId){
                                id
                                language
                                name
                                role {
                                    id
                                    rolePermissions{
                                        id 
                                        alias
                                    }
                                }
                                userPermissions{id alias}
                            }
                        }`).load({
                        rowId: paramUser
                    }).then(data2 => {
                        webix.storage.session.put("app_user_language", data2.language);
                        webix.storage.session.put("app_user_name", data2.name);


                        let permissions = [];
                        if (data2.role.rolePermissions !== null)
                            data2.role.rolePermissions.forEach(item => {
                                permissions.push({
                                    'id': item.id.split(":")[1],
                                    'alias': item.alias
                                });
                            });
                        if (data2.userPermissions !== null)
                            data2.userPermissions.forEach(item => {
                                permissions.push(item.id);
                            });
                        if (permissions !== null)
                            try {
                                webix.storage.session.put("app_user_permissions", permissions);
                            } catch { }

                        if (checkUserPermission(RULE.LOGIN_ROLE)) {
                            window.location = "./home.html";
                        } else {
                            webix.alert({ title: "USER_LOGIN_DENY", type: "alert-success", text: "ERROR" })
                            webix.storage.session.put("loginSession", "");
                            webix.storage.session.put("userId", "");
                            webix.storage.session.put("app_user_name", "");
                            webix.storage.session.put("app_user_permissions", "");
                        }
                    });

                    break;
                case "WRONG_USER_PASSWORD":
                    webix.alert({ title: "WRONG USER PASSWORD", type: "alert-success", text: "ERROR" })
                    break;
                case "DENY_PASSWORD_EXPIRED":
                    webix.alert({ title: "DENY_PASSWORD_EXPIRED", type: "alert-success", text: "ERROR" })
                    break;
                case "DENY_LOCKED":
                    webix.alert({ title: "DENY_LOCKED", type: "alert-success", text: "ERROR" })
                    break;
                case "DENY_MULTI_SESSION_NOT_ALLOWED":
                    webix.alert({ title: "DENY_MULTI_SESSION_NOT_ALLOWED", type: "alert-success", text: "ERROR" })
                    break;
                case "DENY_OTHERS":
                    webix.alert({ title: "DENY_OTHERS", type: "alert-success", text: "ERROR" })
                    break;
                default:
                    webix.alert({ title: "ERROR", type: "alert-success", text: "ERROR" })
                    break;
            }

        });
    }
}