import { RULE } from "../data/GLOBAL_PROPERTIES.js"

webix.ready(function() {

	const language = webix.storage.local.get("app_user_language");
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
		on: {
			onKeyPress: function(code, e) {
				if (code == 13) {
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
					onKeyPress: function(code, e) {
						if (code == 13) {
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
							"onBlur": function() {
								var result = this.validate()    // validate only this field and show warning message under field if invalid
								if (this.$scope != null) {
									this.$scope.validateForm()
								}
							},
							onKeyPress: function(code, e) {
								if (code == 13) {
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
							"onBlur": function() {
								this.validate()
								if (this.$scope != null) {
									this.$scope.validateForm()
								}
							},
							onKeyPress: function(code, e) {
								if (code == 13) {
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
									template: function(obj) {
										return "<img src='./img/" + obj.id + ".png' width='25' height='16'></img>" + " " + obj.value;
									},
									body: {
										xCount: 1,
										template: function(obj) {
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
									'onChange': function(obj) {
										switch (obj) {
											case "sk":
												webix.storage.local.put("app_user_language", "sk");
												break;
											case "en":
												webix.storage.local.put("app_user_language", "en");
												webix.i18n.setLocale("en-EN");
												break;
											case "es":
												webix.storage.local.put("app_user_language", "es");
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
		let loginResponse = webix.ajax().headers({ 'Authorization': 'Basic ' + encodedString }).sync().post(appConfig.restUrl + "auth/login");
	
		if (loginResponse != undefined) {
	
			let login = JSON.parse(loginResponse.responseText);
	
			if (login.access === "ALLOWED") {
	
				webix.storage.local.put("loginSession", login.sessionId);
				webix.storage.local.put("userId", login.userId);
	
				let userResponse = webix.ajax().sync().get(appConfig.restUrl + `users/${login.userId}`);
	
				if (userResponse != undefined) {
	
					let user = JSON.parse(userResponse.responseText);
	
					webix.storage.local.put("app_user_language", user.language);
					webix.storage.local.put("app_user_country", user.country);
					webix.storage.local.put("app_user_name", user.name);
					
					var isLogin = webix.storage.local.get("loginSession");
					
					window.location = "./home.html";
				}
			}
			else if (login.access === "WRONG_USER_PASSWORD") {
				webix.alert({ title: "WRONG USER PASSWORD", type: "alert-success", text: "ERROR" })
			}
			else if (login.access === "DENY_PASSWORD_EXPIRED") {
				webix.alert({ title: "DENY_PASSWORD_EXPIRED", type: "alert-success", text: "ERROR" })
			}
			else if (login.access === "DENY_LOCKED") {
				webix.alert({ title: "DENY_LOCKED", type: "alert-success", text: "ERROR" })
			}
			else if (login.access === "DENY_MULTI_SESSION_NOT_ALLOWED") {
				webix.alert({ title: "DENY_MULTI_SESSION_NOT_ALLOWED", type: "alert-success", text: "ERROR" })
			}
			else if (login.access === "DENY_OTHERS") {
				webix.alert({ title: "DENY_OTHERS", type: "alert-success", text: "ERROR" })
			}
			else {
				webix.alert({ title: "ERROR", type: "alert-success", text: "ERROR" })
			}		
		}
	}
}
