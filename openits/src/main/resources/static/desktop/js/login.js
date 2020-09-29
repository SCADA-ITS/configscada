if(window.app)
	app.login = {
		
		getView : function() {
			
			var view = {
				id: "sign-in",
				css: "sign-in",
				rows: [
					{
					},
					{
						cols: [
							{},
							{
								paddingY: 50,
								paddingX: 60,
								width: 600,
								css: "sing-in-form",
								rows: [
									{
										data:{
									        userImage: "img/user_logo.png", 
									        userAlias: "",
											locked: false
									    },
										template: function(data) {
											
											var userTemplate = "<img class='user-logo' src='" + data.userImage + "'/><br/>" + data.userAlias;
											
											if (data.locked) {
												userTemplate += "<div class='locked'>" + i18n.t("LBL_LOCK_SCREEN") + "</div>";
											} 
											
											return userTemplate;
										},  
										id: "login-template",
										height: 130, 
										borderless: true
									},
									{
										view: "form",
				                        id: "loginForm",
				                        elementsConfig: {
				                            bottomPadding: 18,
				                            labelWidth: 150
										},
										elements: [
											{},
											{
				                                view: "text",
												label: i18n.t("LBL_LOGIN_USER"),
				                                name: "user", 
				                                id: "login-user", 
				                                required: true,
												validate: webix.rules.isNotEmpty, invalidMessage: i18n.t("LBL_PLEASE_ENTER_USER"), validateEvent: "key"
				                            },
				                            { 
				                                view: "text", 
				                                type: "password", 
				                                label: i18n.t("LBL_LOGIN_PASSWORD"), 
				                                name: "password",
												id: "login-password", 
				                                required: true, 
				                                validate: webix.rules.isNotEmpty, invalidMessage: i18n.t("LBL_PLEASE_ENTER_PASSWORD"), validateEvent: "key"
				                            },
											{
												margin: 10,
				                                paddingX: 2,
				                                borderless: true,
												cols: [
													{},
													{
														view: "button", 
														type: "form", 
														label: i18n.t("LBL_LOGIN_SIGN_IN"), 
														width: 150, 
														height: 45,
														hotkey: "enter",
														click: function() {
															var form = $$('loginForm');
															
															if (form.validate()) {
																if (app.login.signIn(form.elements.user.data.value, form.elements.password.data.value)) {
																													
																}
															}
														} 
													},
													{}												
												]
											}
										]
			                        },
								]
							},
							{}
						]
					},
					{}
				]
			}; 
			
			return view;
		},
		init: function() {
			
			var userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
			
			if (userData != null) {

				$$("loginForm").clear();				
				$$("login-user").config.text = userData.user;
				$$("login-user").config.value = userData.user;
				$$("login-user").hide();
				$$("login-template").setValues({userAlias: userData.user, locked: true}, true);
			}
			else {
				$$("loginForm").clear();
				$$("login-user").config.text = "";
				$$("login-user").config.value = "";
				$$("login-user").show();
				$$("login-template").setValues({userAlias: "", locked: false}, true);
			}
		},
		signIn : function(user, password) {

			var userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);

			if (!userData) {
				// TODO: Make request with user information and storage in session
				if (user === admin_usr.user && password === admin_usr.password) {
					userData = webix.storage.session.put(SESSION_STORAGE_USER_DATA, {
						user: user,
						password: password,
						userSession: "UserSession:69103CE63359D3B1BB16C1DB1B794C1B"
					});
					app.signIn();	
				}
			}
			else {
				if (password === userData.password) {
					app.signIn();	
				}
			}
			return true;
		} 
	}
