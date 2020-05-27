webix.ready(function() {

    const language = webix.storage.session.get("app_user_language");
    if (language) {
        $.i18n().locale = language;
    } else {
        $.i18n().locale = "es";
    }
    
    $.i18n().load( {
        en: 'i18n/en.json',
        es: 'i18n/es.json', 
        sk: 'i18n/sk.json'
    });

    const userPlaceholder = "";//$.i18n("login.input.user");

    webix.ui({ 
        rows: [{},
            {
            cols: [{},
                {
                    view: "form",
                        id: "loginForm",
                        elementsConfig: {
                            bottomPadding: 18,
                            labelWidth: 120
                        },
                        elements:[  
                            { "template": "<span class='webix_icon fa-user-circle-o'></span>Login", type: "header" },
                            { view: "text", label: "User", name: "user", id: "user", placeholder: userPlaceholder ,
                            required: true, 
                                on: { "onBlur": function() { 
                                    var result = this.validate()    // validate only this field and show warning message under field if invalid
                                    this.$scope.validateForm()
                                }}
                            },
                            { view: "text", type: "password", label: "Password", name: "password", 
                            required: true, validate: webix.rules.isNotEmpty, invalidMessage: "Please enter your password!", validateEvent: "key",
                                on: { 
                                "onBlur": function() {
                                    this.validate() 
                                    this.$scope.validateForm()
                                }
                            }
                            },
                            {
                                margin: 10,
                                paddingX: 2,
                                borderless: true,
                                cols:[
                                    {},
                                    { view: "button", label: "Login", type: "form", id:"loginFormSubmitButton", width: 150, click: 
                                        function() {
                                            var form = $$('loginForm')
                                            
                                            if (form.validate()) {
                                                const encodedString = btoa((form.elements.user.data.value + ':' + form.elements.password.data.value).toString('utf8')).toString('base64');
                                                webix.ajax().headers({ 'Authorization': 'Basic ' + encodedString }).post(webix.storage.session.get("url.auth.login")).then(function(data){
                                                    
                                                    switch (data.json().access) {
                                                        case "ALLOWED":
                                                            webix.storage.session.put("loginSession", data.json().id);
                                                            var paramUser = data.json().userId;
                                                            webix.proxy("GraphQL", `query($rowId: String!){
                                                                getUser(userId: $rowId){
                                                                    id
                                                                    language
                                                                    name
                                                                    role {
                                                                        id
                                                                        rolePermissions{id alias}
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
                                                                        permissions.push(item.id);
                                                                    });
                                                                if (data2.userPermissions !== null)
                                                                    data2.userPermissions.forEach(item => {
                                                                        permissions.push(item.id);
                                                                    });
                                                                if (permissions !== null)
                                                                try {
                                                                    webix.storage.session.put("app_user_permissions", permissions.toString());
                                                                } catch {}
                                                                window.location = "./home.html";
                                                            });
                                                            
                                                            break;
                                                        case "WRONG_USER_PASSWORD":
                                                            webix.alert({title:"WRONG USER PASSWORD", type:"alert-success", text:"ERROR"})
                                                            break;        
                                                        case "DENY_PASSWORD_EXPIRED":
                                                            webix.alert({title:"DENY_PASSWORD_EXPIRED", type:"alert-success", text:"ERROR"})
                                                            break;
                                                        case "DENY_LOCKED":
                                                            webix.alert({title:"DENY_LOCKED", type:"alert-success", text:"ERROR"})
                                                            break;
                                                        case "DENY_MULTI_SESSION_NOT_ALLOWED":
                                                            webix.alert({title:"DENY_MULTI_SESSION_NOT_ALLOWED", type:"alert-success", text:"ERROR"})
                                                            break;
                                                        case "DENY_OTHERS":
                                                            webix.alert({title:"DENY_OTHERS", type:"alert-success", text:"ERROR"})
                                                            break;                                                            
                                                        default:
                                                            webix.alert({title:"ERROR", type:"alert-success", text:"ERROR"})
                                                            break;
                                                    }
                                                });
                                            }
                                        }
                                    }
                                ]
                            }
                        ],				
                }
            ,{}], 
        },{}]
    });	

});