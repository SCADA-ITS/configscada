webix.ready(function() {

    const language = webix.storage.session.get("app_user_language");
    const language_default = "sk";
    
    if (language !== null) {
        $.i18n().locale = language;
    } else {
        $.i18n().locale = language_default;
    }

    $.i18n().load( {
        "en": i18_en,
        "es": i18_es, 
        "sk": i18_sk
    });

    const userPlaceholder = "";//$.i18n("login.input.user");

    var options = [
        {id:"sk", value:"Slovenský"}, 
        {id:"en", value:"English"},
        {id:"es", value:"Español"}, 
    ];

    webix.ui({ 
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
                        elements:[  
                            { "template": "<span class='webix_icon fa-user-circle-o'></span>Login", type: "header" },
                            {
                                view: "text", 
                                label: $.i18n("login.input.user"), 
                                name: "user", 
                                id: "user", placeholder: userPlaceholder ,
                                required: true, 
                                on: { "onBlur": function() { 
                                    var result = this.validate()    // validate only this field and show warning message under field if invalid
                                    this.$scope.validateForm()
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
                                    {
                                        view:"richselect", 
                                        value:language !== null ? language : language_default, 
                                        height:40, 
                                        width:150, 
                                        options:{
                                            view:"datasuggest",
                                            textValue:"value",
                                            template:function(obj){
                                                return "<img src='./img/" + obj.id + ".png' width='25' height='16'></img>" + " " + obj.value;
                                                },
                                            body:{
                                                xCount:1,
                                                template:function(obj){
                                                //return obj.value + "" + "<img src='./" + obj.value + ".png' width='100' height='67'></img>"; // "<img src='//docs.webix.com/samples/32_thirdparty/data/image00"+obj.id+".JPG'>";
                                                return "<img src='./img/" + obj.id + ".png' width='25' height='16'></img>" + " " + obj.value;
                                            },
                                                type:{
                                                    height:40, width:150
                                                },
                                                data:options
                                            },
                                        },
                                        on:{
                                            'onChange': function(obj){ 
                                                switch (obj){
                                                    case "sk":
                                                        webix.storage.session.put("app_user_language", "sk");
                                                        break;
                                                    case "en":
                                                        webix.storage.session.put("app_user_language", "en");
                                                        break;
                                                    case "es":
                                                        webix.storage.session.put("app_user_language", "es");
                                                        break;
                                                }
                                                window.location = "./login.html";                                     
                                            }
                                        }
                                    },
                                    {},
                                    { view: "button", label: $.i18n("login.LoginButton"), type: "form", id:"loginFormSubmitButton", width: 150, click: 
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
